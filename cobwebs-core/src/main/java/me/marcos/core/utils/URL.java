package me.marcos.core.utils;

import lombok.Builder;
import lombok.Data;

/**
 * Created with cobwebs.
 * User:marcos
 * Date:2017/7/11
 * Time:下午10:02
 * Description:
 */
@Data
@Builder
public class URL {

    private String host;
    private int port;
    public static URL of(String address){
        int index = address.indexOf(":");
        return URL.builder()
                .host(address.substring(0,index))
                .port(Integer.valueOf(address.substring(index+1)))
                .build();
    }

    @Override
    public String toString() {
        return host+":"+port;
    }
}
