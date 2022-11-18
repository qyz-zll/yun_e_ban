package com.xxx.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公共返回对象
 *
 *
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {
    private long cede;
    private  String message;
    private Object obj;

    /**
     * 成功返回结果
     * @param message
     * @return
     */
    public static RespBean success(String message) {
        return new RespBean(200,message,null);


    }
}
