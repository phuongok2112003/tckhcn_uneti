package com.example.tapchikhcn.exceptions;




import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class ApiEntityNotFoundError implements ApiSubError, Serializable  {

    private final String className;
    private final List<FieldValue> fieldValues;

    public ApiEntityNotFoundError(String className, String field, Object value) {
        this.className = className;
        this.fieldValues = Collections.singletonList(new FieldValue(field, value));
    }

    public ApiEntityNotFoundError(String className, FieldValue fieldValue) {
        this.className = className;
        this.fieldValues = Collections.singletonList(fieldValue);
    }

    public ApiEntityNotFoundError(String className, List<FieldValue> fieldValues) {
        this.className = className;
        this.fieldValues = fieldValues;
    }

    public String getClassName() {
        return this.className;
    }

    public List<FieldValue> getFieldValues() {
        return this.fieldValues;
    }
}
