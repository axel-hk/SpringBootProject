package com.example.mybookshopapp.struct.book.file;

public enum BoolFileType {
    PDF(".pdf"),
    EPUB(".ePUB"),
    FB2(".fb2");

    private final String fileExtensionString;

    BoolFileType(String fileExtensionString) {
        this.fileExtensionString = fileExtensionString;
    }

    public static String getFileExtensionByTypeId(Integer typeId) {
        return switch (typeId) {
            case 1 -> BoolFileType.PDF.fileExtensionString;
            case 2 -> BoolFileType.EPUB.fileExtensionString;
            case 3 -> BoolFileType.FB2.fileExtensionString;
            default -> "";
        };
    }
}
