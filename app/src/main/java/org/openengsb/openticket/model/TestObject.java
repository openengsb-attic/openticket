package org.openengsb.openticket.model;

import java.io.Serializable;

public class TestObject implements Serializable {
    private String objid;
    private String value;

    public TestObject() {
    }

    public TestObject(String objid) {
        this.objid = objid;
    }

    public TestObject(String objid, String value) {
        this.objid = objid;
        this.value = value;
    }

    public String getObjid() {
        return objid;
    }

    public void setObjid(String objid) {
        this.objid = objid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
