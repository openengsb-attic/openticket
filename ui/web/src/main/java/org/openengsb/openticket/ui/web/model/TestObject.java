package org.openengsb.openticket.ui.web.model;

import java.io.Serializable;

public class TestObject implements Serializable {
    private String id;
    private String value;

    public TestObject(String id) {
        this.id = id;
    }

    public TestObject(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
