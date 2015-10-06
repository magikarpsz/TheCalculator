package com.bxcalculator.thecalculator;

public class Result {

    private String _input, _result, _time;
    private int _id;

    public Result(){}

    public Result(String input, String result, String time){
        this._input = input;
        this._result = result;
        this._time = time;
    }

    public String get_result() {
        return _result;
    }

    public String get_time() {
        return _time;
    }

    public void set_time(String _time) {
        this._time = _time;
    }

    public void set_result(String _result) {
        this._result = _result;
    }

    public String get_input() {
        return _input;
    }

    public void set_input(String _input) {
        this._input = _input;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
