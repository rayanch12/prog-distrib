package com.cherifi.frontend;

public class NumberDTO {
    private Long id;
    private int value;

    public NumberDTO() {
    }

    public NumberDTO(int value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
