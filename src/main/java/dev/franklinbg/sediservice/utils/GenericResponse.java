package dev.franklinbg.sediservice.utils;

public class GenericResponse<T> {
    private String type = "";
    private int rpta;
    private String message = "";
    private T body;

    public GenericResponse() {
    }

    public GenericResponse(String type, int rpta, String message, T body) {
        this.type = type;
        this.rpta = rpta;
        this.message = message;
        this.body = body;
    }
}
