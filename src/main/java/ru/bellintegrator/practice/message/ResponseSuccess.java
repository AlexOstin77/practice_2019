package ru.bellintegrator.practice.message;


/**
 * Ответ контроллера
 * в случае успеха
 * data JSON value
 */
public class ResponseSuccess implements Response {

    /**
     * Сообщение об успехе
     */
    private String result;

    /**
     * Передаваемые данные
     * data JSON value
     */
    private Object data;

    public ResponseSuccess() {

    }

    public ResponseSuccess(String result) {
        this.result = result;
    }

    public ResponseSuccess(String result, Object data) {
        this.result = result;
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
