package com.sj.config.security.handler.utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by yangrd on 2017/7/4.
 */
public class ResponseUtils {


    /**
     * @param response
     * @param objects   需要打印的對象
     * @throws IOException
     * @throws ServletException
     */
    public static void print(HttpServletResponse response, Object... objects) throws IOException, ServletException {
        response.setContentType("text/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        for (Object o : objects) {
            writer.print(o);
        }
        writer.flush();
        writer.close();
    }

}
