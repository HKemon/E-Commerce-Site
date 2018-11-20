package util;

public enum GenderConstantEnum {   // &pvId=
    MenUnisex("284-100006038;284-361190"),
    WomenUnisex("284-100006040;284-361190"),
    lovers("284-200000069"),  // lovers'
    Girls("284-100006000"),
    Boys("284-100006216");

    private final String code;

    GenderConstantEnum(String code) {
        this.code = code;
    }

    public String getGenderConstantCode() {
        return this.code;
    }
}