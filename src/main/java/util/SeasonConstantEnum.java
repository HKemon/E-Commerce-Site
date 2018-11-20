package util;

public enum SeasonConstantEnum {
    Winter("281-366078"),
    SpringAutumn("281-200005928"), // Spring/Autumn
    Summer("281-366077");

    private final String code;

    SeasonConstantEnum(String code) {
        this.code = code;
    }

    public String getSeasonConstantCode() {
        return this.code;
    }
}