package org.vikinc.community.dto;

import lombok.Data;
import org.springframework.web.servlet.ModelAndView;
import org.vikinc.community.exception.CustomizeErrorCode;
import org.vikinc.community.exception.CustomizeException;

@Data
public class DTOResult<T> {
    private Integer code;
    private String message;
    private T data;
    public static DTOResult errorOf(Integer code,String message){
        DTOResult result = new DTOResult();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static DTOResult errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(),errorCode.getMessage());
    }

    public static DTOResult errorOf(CustomizeException ex) {
        return errorOf(ex.getCode(),ex.getMessage());
    }

    public static DTOResult okOf() {
        DTOResult result = new DTOResult();
        result.setCode(200);
        result.setMessage("请求成功");
        return result;
    }

    public static <T> DTOResult okOf(T t) {
        DTOResult result = new DTOResult();
        result.setCode(200);
        result.setMessage("请求成功");
        result.setData(t);
        return result;
    }
}
