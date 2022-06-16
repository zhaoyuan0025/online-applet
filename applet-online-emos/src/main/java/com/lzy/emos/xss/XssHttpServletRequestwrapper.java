package com.lzy.emos.xss;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HtmlUtil;
import cn.hutool.json.JSONUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @program: applet-online-emos
 * @description: 抵御xss的恶意攻击
 * @author: lzy
 * @create: 2022-06-16 21:36
 **/
public class XssHttpServletRequestwrapper extends HttpServletRequestWrapper {

    /**
     * 默认的构造方法
     * @param request
     */
    public XssHttpServletRequestwrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if (StringUtils.isNotEmpty(value)){
            value = HtmlUtil.filter(value);
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if(null != values){
            for (int i = 0; i < values.length; i++) {
                String value = values[i];
                if (StringUtils.isNotEmpty(value)){
                    value = HtmlUtil.filter(value);
                }
                //将过滤之后的value的值重新覆盖数组中的值
                values[i] = value;
            }
        }
        return values;
    }

    @Override
    public Map<String, String[]> getParameterMap() {

        Map<String, String[]> parametMap = super.getParameterMap();

        //构造linkhashmap linkHashMap 可以保持插入的顺序
        LinkedHashMap<String,String[]> linkedHashMap = new LinkedHashMap<>();

        //遍历parametMap 中的数据 过滤xss
        if (CollectionUtil.isNotEmpty(parametMap)){
            for (String key : parametMap.keySet()){
                //通过key获取value
                String[] values = parametMap.get(key);
                //for循环values
                for (int i = 0; i < values.length; i++) {
                    String value = values[i];
                    if (StringUtils.isNotEmpty(value)){
                        value = HtmlUtil.filter(value);
                    }
                    values[i] = value;
                }
                //将过滤后的数据添加到新的LinkedHashMap 中返回
                linkedHashMap.put(key,values);
            }
        }

        return linkedHashMap;
    }

    /**
     * 重写 过滤请求头的数据 抵御xss的攻击
     * @param name
     * @return
     */
    @Override
    public String getHeader(String name) {
        String header = super.getHeader(name);
        if (StringUtils.isNotEmpty(header)){
            header = HtmlUtil.filter(header);
        }
        return header;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        InputStream inputStream =  super.getInputStream();

        InputStreamReader reader = new InputStreamReader(inputStream,"UTF-8");
        //构造一个换从流
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer body = new StringBuffer();
        String line = bufferedReader.readLine();

        while (StringUtils.isNotEmpty(line)){
            body.append(line);
            line = bufferedReader.readLine();
        }

        //关闭流
        inputStream.close();
        reader.close();
        bufferedReader.close();

        //将body的数据转换为map
        Map<String,Object> map = JSONUtil.parseObj(body);
        Map<String,Object> result = new LinkedHashMap<>();
        //对map进行遍历
        for (String key : map.keySet()){
            Object val = map.get(key);
            //判断val是否是string类型的
            if (val instanceof String){
                if (StringUtils.isNotEmpty(val.toString())){
                    //添加到linkHashMap中
                    result.put(key,HtmlUtil.filter(val.toString()));
                }
            }else {
                result.put(key,val.toString());
            }
        }

        String json = JSONUtil.toJsonStr(result);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(json.getBytes());
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
    }

}
