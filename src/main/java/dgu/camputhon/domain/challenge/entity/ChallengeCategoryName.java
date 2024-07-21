package dgu.camputhon.domain.challenge.entity;

public enum ChallengeCategoryName {
    HEALTH("운동"),
    STUDY("공부"),
    LEISURE("여가");

    private final String description;

    ChallengeCategoryName(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
