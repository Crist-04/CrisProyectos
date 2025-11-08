package com.digis01.CAlvarezProgramacionNCapasOctubre2025.ML;

import java.util.List;

public class Result {
    public boolean correct;
    public String errorMessage;
    public Exception ex;
    public Object object;
    public List<Object> objects;
    
    public Result() {
        this.correct = false;
    }
}