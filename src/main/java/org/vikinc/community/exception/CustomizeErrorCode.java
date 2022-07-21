package org.vikinc.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND("你找的问题不在了，要不换个试试？",2001),
    TAGET_PARAM_NOT_FOUND("未选中任何问题或评论进行回复",2002),
    NO_LOGIN("未登录不能进行评论，请先登录",2003),
    SYSTEM_ERROR("服务冒烟了，要不然等会再试试！",2004),
    TYPE_PARAM_WRONG("评论类型错误或不存在",2005),
    COMMENT_NOT_FOUND("你回复的评论不在了，要不换个试试！",2006),
    TIMEOUT("连接超时，要不稍后再试试！",2007),
    NO_LOGIN_GITHUB("未能找到相对应的账户，请重新登录！",2008),
    CONTENT_IS_EMPTY("输入的内容不能为空！",2009);


    private String message;
    private Integer code;

    CustomizeErrorCode(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
