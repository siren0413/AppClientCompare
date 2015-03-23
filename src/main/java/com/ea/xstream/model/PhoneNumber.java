package com.ea.xstream.model;

public class PhoneNumber {
    private int code;
    private String number;

    public PhoneNumber() {
    }

    public PhoneNumber(int code, String number) {
        this.code = code;
        this.number = number;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhoneNumber)) return false;

        PhoneNumber that = (PhoneNumber) o;

        if (code != that.code) return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;

        return true;
    }

}
