package com.flowable.springboot.config;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Builder
@Slf4j
public class ErrorHandle extends DefaultResponseErrorHandler {

    private final Logger logger = LoggerFactory.getLogger(ErrorHandle.class);

    @Override
    public void handleError(ClientHttpResponse response, HttpStatus statusCode) throws IOException {
        int status = statusCode.value();
        if (status == 200 || status == 400 || status == 500) {
            //do what u want to do
            //HttpHeaders headers = response.getHeaders();
            InputStream is = null;
            InputStreamReader isr = null;
            BufferedReader bufr = null;
            String line = null;
            try {
                is = response.getBody();
                //将字节流向字符流的转换。
                isr = new InputStreamReader(is);//读取
                //创建字符流缓冲区
                bufr = new BufferedReader(isr);
                while ((line =bufr.readLine()) != null){
                    System.out.println(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(null != bufr){
                    bufr.close();
                }
                if(null != isr){
                    isr.close();
                }
                if(null != is){
                    is.close();
                }
            }
        } else {
            super.handleError(response, statusCode);
        }
    }
}

