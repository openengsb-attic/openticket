package org.openengsb.openticket.ui.web;

import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.openengsb.core.common.taskbox.TaskboxException;
import org.openengsb.core.common.taskbox.TaskboxService;
import org.openengsb.openticket.core.TicketService;
import org.openengsb.openticket.model.Ticket;

@AuthorizeInstantiation("CASEWORKER")
public class TaskboxMessage extends BasePage {

    @SpringBean
    private TaskboxService service;

    public TaskboxMessage() {
        try {
            add(new Label("output", service.getWorkflowMessage()));
        } catch (TaskboxException e) {
            add(new Label("output", "Taskbox leer"));
        }
    }
}
