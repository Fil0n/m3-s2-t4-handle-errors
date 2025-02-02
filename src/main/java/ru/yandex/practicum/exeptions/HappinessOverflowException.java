package ru.yandex.practicum.exeptions;

public class HappinessOverflowException extends RuntimeException {

    private final Integer happinessLevel;

    public HappinessOverflowException(Integer hapiness) {
        super("Счастье больше " + hapiness);
        this.happinessLevel = hapiness;
    }


    public Integer getHappinessLevel() {
        return happinessLevel;
    }
}
