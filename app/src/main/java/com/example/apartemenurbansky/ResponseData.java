package com.example.apartemenurbansky;

        import java.util.List;

public class ResponseData {
    private String value;
    private String message;
    private List<DataPameran> data;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataPameran> getData() {
        return data;
    }

    public void setData(List<DataPameran> data) {
        this.data = data;
    }
}
