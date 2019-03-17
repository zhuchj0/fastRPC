//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.fastRPC.model;

import java.io.Serializable;
import java.util.Arrays;

public class MessageRequest implements Serializable {
    private String messageId;
    private String className;
    private String methodName;
    private String[] typeParameters;
    private Object[] parameters;

    public MessageRequest() {
    }

    public String getMessageId() {
        return this.messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String[] getTypeParameters() {
        return this.typeParameters;
    }

    public void setTypeParameters(String[] typeParameters) {
        this.typeParameters = typeParameters;
    }

    public Object[] getParameters() {
        return this.parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public String toString() {
        return "MessageRequest{messageId='" + this.messageId + '\'' + ", className='" + this.className + '\'' + ", methodName='" + this.methodName + '\'' + ", typeParameters=" + Arrays.toString(this.typeParameters) + ", parameters=" + Arrays.toString(this.parameters) + '}';
    }
}
