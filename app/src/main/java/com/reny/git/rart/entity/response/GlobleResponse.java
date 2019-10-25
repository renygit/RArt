package com.reny.git.rart.entity.response;

/**
 * Created by reny on 2019/10/24.
 */
public class GlobleResponse {
    private int ErrorCode;
    private String ErrorMessage;
    private String CommandName;
    private String TripleDesKey;
    private String TripleDesIV;
    private String EncryptData;

    public int getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(int ErrorCode) {
        this.ErrorCode = ErrorCode;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String ErrorMessage) {
        this.ErrorMessage = ErrorMessage;
    }

    public String getCommandName() {
        return CommandName;
    }

    public void setCommandName(String CommandName) {
        this.CommandName = CommandName;
    }

    public String getTripleDesKey() {
        return TripleDesKey;
    }

    public void setTripleDesKey(String TripleDesKey) {
        this.TripleDesKey = TripleDesKey;
    }

    public String getTripleDesIV() {
        return TripleDesIV;
    }

    public void setTripleDesIV(String TripleDesIV) {
        this.TripleDesIV = TripleDesIV;
    }

    public String getEncryptData() {
        return EncryptData;
    }

    public void setEncryptData(String EncryptData) {
        this.EncryptData = EncryptData;
    }
}
