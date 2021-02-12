package com.example.fileapplication.enums;

public enum ContentType {
    PDF("application/pdf"),
    JPG("image/jpeg"),
    JPEG("image/jpeg"),
    PNG("image/png"),
    DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

    private String name;

    ContentType (String name) {
        this.name = name;
    }

    public static ContentType findByName(String name) {
        for (ContentType contentType : ContentType.values()) {
            if (contentType.name.equals(name)) {
                return contentType;
            }
        }
        return null;
    }

}
