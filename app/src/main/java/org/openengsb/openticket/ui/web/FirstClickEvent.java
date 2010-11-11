package org.openengsb.openticket.ui.web;

import org.openengsb.core.common.Event;

public class FirstClickEvent extends Event {

    @Override
    public String getType() {
        return "FirstClick";
    }
}
