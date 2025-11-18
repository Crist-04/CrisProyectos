
package com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Result {
    public boolean correct;
    public String errorMessage;
    public Object object;
    public Object objects;
    public Object data;
    public Exception ex;
    @JsonIgnore
    public int status;
    
}
