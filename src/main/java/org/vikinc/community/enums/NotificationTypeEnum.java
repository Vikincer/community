package org.vikinc.community.enums;

public enum NotificationTypeEnum {
    REPLY_QUESTION("1","回复了问题"),
    REPLY_COMMENT("2","回复了评论");
    private String type;
    private String name;

    NotificationTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String nameOfType(String type) {
        for (NotificationTypeEnum notificationTypeEnum : NotificationTypeEnum.values()) {
            if (notificationTypeEnum.getType().equals(type)) {
                return notificationTypeEnum.getName();
            }
        }
        return "";
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
