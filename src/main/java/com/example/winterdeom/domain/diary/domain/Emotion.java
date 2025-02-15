package com.example.winterdeom.domain.diary.domain;

public enum Emotion {
    HAPPY("행복"),
    SAD("슬픔"),
    ANGRY("화남"),
    EXCITED("흥분"),
    STRESSED("스트레스");

    private final String displayName;

    Emotion(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
