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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;
import org.openengsb.core.common.persistence.PersistenceException;
import org.openengsb.core.common.taskbox.TaskboxService;
import org.openengsb.core.common.taskbox.model.Task;
import org.openengsb.core.common.workflow.WorkflowException;
import org.openengsb.core.common.workflow.WorkflowService;
import org.openengsb.core.common.workflow.model.ProcessBag;
import org.openengsb.openticket.model.Ticket;
import org.openengsb.openticket.model.TicketPriority;
import org.openengsb.openticket.model.TicketType;
import org.openengsb.ui.common.wicket.taskbox.WebTaskboxService;

public class TicketPanel extends Panel {

     private Ticket ticket;
     
     @SpringBean(name="webtaskboxService")
     private WebTaskboxService taskboxService;
     
     @SpringBean
     private WorkflowService workflowService;
  

    public TicketPanel(String id, Task t) {
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
        form.add(new DropDownChoice("tickettype", ticketModel.bind("taskType"),Arrays.asList(TicketType.values())));
        form.add(new TextField("ticketcustomer", ticketModel.bind("customer")));
        form.add(new TextField("ticketcontactEmailAddress", ticketModel.bind("contactEmailAddress")));
        form.add(new DropDownChoice("ticketpriority", ticketModel.bind("priority"),Arrays.asList(TicketPriority.values())).setRequired(true));
        form.add(new TextArea("ticketdescription", ticketModel.bind("description")).setRequired(true));
        
        
        form.add(new AjaxButton("submitButton", form)
        {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                try {
                    ProcessBag bag = new ProcessBag(ticket);
                    Map<String, Object> map = new HashMap<String,Object>();
                    map.put("processBag", bag);
                    workflowService.startFlow("TaskDemoWorkflow",map);
                    info("Workflow started!");
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
       /* 
        add(new Label("ticketid", ticket.getTaskId()));
        add(new Label("tickettype", ticket.getTaskType() != null ? ticket.getTaskType(): "N/A"));
        add(new Label("ticketcreationTimestamp", ticket.getTaskCreationTimestamp() != null ? ticket.getTaskCreationTimestamp().toString(): "N/A"));
        add(new Label("ticketpriority", ticket.getPriority()!= null ? ticket.getPriority(): "N/A"));
        add(new Label("ticketcustomer", ticket.getCustomer()!= null ? ticket.getCustomer(): "N/A"));
        add(new Label("ticketcontactEmailAddress", ticket.getContactEmailAddress()!= null ? ticket.getContactEmailAddress(): "N/A"));
        add(new Label("ticketdescription", ticket.getDescription()!= null ? ticket.getDescription(): "N/A"));
        
        Link link = new Link("finishCurrentTask"){
            @Override
            public void onClick() {
                try {
                    taskboxService.finishTask(ticket);
                } catch (WorkflowException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //TODO: Process event
            }
        };
        add(link);*/
    }
}

