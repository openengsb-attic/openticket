/**
 * Copyright 2010 OpenEngSB Division, Vienna University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openengsb.openticket.ui.web.panel;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.openengsb.core.api.workflow.WorkflowException;
import org.openengsb.core.api.workflow.WorkflowService;
import org.openengsb.core.api.workflow.model.ProcessBag;
import org.openengsb.core.api.workflow.model.Task;
import org.openengsb.openticket.model.Ticket;
import org.openengsb.openticket.model.TicketPriority;
import org.openengsb.openticket.model.TicketType;
import org.openengsb.openticket.ui.web.OverviewDemo;

@SuppressWarnings("serial")
public class CreateTicketPanel extends Panel {

    private Ticket ticket;

    @SpringBean
    private WorkflowService workflowService;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public CreateTicketPanel(String id, Task t) {
        super(id);
        this.ticket = new Ticket(t);
        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        feedback.setOutputMarkupId(true);
        add(feedback);
        ticket.setPriority(TicketPriority.Low);
        CompoundPropertyModel<Ticket> ticketModel = new CompoundPropertyModel<Ticket>(ticket);
        Form<Ticket> form = new Form<Ticket>("inputForm", ticketModel);
        form.setOutputMarkupId(true);
        ticket.setPriority(TicketPriority.High);
        form.add(new Label("ticketid", ticket.getTaskId()));
        form.add(new TextField("ticketname", ticketModel.bind("name")));
        form.add(new DropDownChoice("tickettype", ticketModel.bind("taskType"), Arrays.asList(TicketType.values())));
        form.add(new TextField("ticketcustomer", ticketModel.bind("customer")));
        form.add(new TextField("ticketcontactEmailAddress", ticketModel.bind("contactEmailAddress")));
        form.add(new DropDownChoice("ticketpriority", ticketModel.bind("priority"), Arrays.asList(TicketPriority
            .values())).setRequired(true));
        form.add(new TextArea("ticketdescription", ticketModel.bind("description")).setRequired(true));

        form.add(new AjaxButton("submitButton", form)
        {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                try {
                    ProcessBag bag = new ProcessBag(ticket);
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("processBag", bag);
                    workflowService.startFlow("TaskDemoWorkflow", map);
                    info("Workflow started!");
                    setResponsePage(OverviewDemo.class);
                } catch (WorkflowException e) {
                    info(e.getMessage());
                }
                target.addComponent(feedback);

            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {

                target.addComponent(feedback);
            }
        });
        add(form);

    }
}
