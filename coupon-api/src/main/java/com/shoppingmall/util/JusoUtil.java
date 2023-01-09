package com.shoppingmall.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JusoUtil {

    public String jusoUtil(String str){
        String answer="";
        int m=Integer.MIN_VALUE, pos;
        while((pos=str.indexOf(' '))!=-1){
            String tmp=str.substring(0, pos);
            int len=tmp.length();
            if(len>m){
                m=len;
                answer=tmp;
            }
            str=str.substring(pos+1);
        }
        if(str.length()>m) answer=str;
        return answer;
    }



    public String jusoUtil2(String str){
        String answer="";
        int m=Integer.MIN_VALUE, pos;

        while((pos = str.indexOf(' ')) != -1){
            String tmp = str.substring(0, pos);
            int len=tmp.length();
            if(len > m){
                m = len;
                //answer = tmp;
            }

            if (tmp.indexOf("로") > 0){
                m = len;
                answer = tmp;
            }
            str=str.substring(pos+1);
        }
        if(str.length()>m) answer=str;
        return answer;
    }



}
