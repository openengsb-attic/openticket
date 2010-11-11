package org.openengsb.openticket.ui.web;

import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.openengsb.core.taskbox.TaskboxException;
import org.openengsb.core.taskbox.TaskboxService;

@AuthorizeInstantiation("CASEWORKER")
public class TaskboxMessage extends BasePage {

    @SpringBean
    private TaskboxService service;
    
    public TaskboxMessage(){
        try {
            add(new Label("output", service.getWorkflowMessage()));
        } catch (TaskboxException e) {
            add(new Label("output", "Taskbox leer"));
        }
    }
}
