/**
 * Copyright 2010 OpenEngSB Division, Vienna University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openengsb.openticket.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.openengsb.core.common.taskbox.TaskboxService;
import org.openengsb.ui.taskbox.model.WebTaskStep;

public class TicketPanel extends Panel {

  
     private Panel taskPanel; private Panel currentTaskPanel; 
     private List<Link> list = new ArrayList<Link>();
     private List<WebTaskStep> stepList = new ArrayList<WebTaskStep>();
     private Ticket ticket;
     
     @SpringBean
     private TaskboxService service;
  

    public TicketPanel(String id, Ticket t) {
        super(id);
        this.ticket = t;
        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        feedback.setOutputMarkupId(true);
        add(feedback);
        
        add(new Label("ticketid", ticket.getId()));
        add(new Label("tickettype", ticket.getType() != null ? ticket.getType(): "N/A"));
        add(new Label("ticketcreationTimestamp", ticket.getCreationTimestamp() != null ? ticket.getCreationTimestamp().toString(): "N/A"));
        add(new Label("ticketpriority", ticket.getPriority()!= null ? ticket.getPriority(): "N/A"));
        add(new Label("ticketcustomer", ticket.getCustomer()!= null ? ticket.getCustomer(): "N/A"));
        add(new Label("ticketcontactEmailAddress", ticket.getContactEmailAddress()!= null ? ticket.getContactEmailAddress(): "N/A"));
        add(new Label("ticketdescription", ticket.getDescription()!= null ? ticket.getDescription(): "N/A"));
        
        
        add(new ListView("notesList", ticket.getNotes()){
            @Override
            protected void populateItem(ListItem item) {
                item.add(new Label("notesLabel",item.getModel()));
            }
        });
        
        add(new ListView("historyList", ticket.getHistory()){
            @Override
            protected void populateItem(ListItem item) {
                item.add(new Label("historyLabel",item.getModel()));
            }
        });
        
        WebTaskStep currentStep = ticket.getCurrentTaskStep();
        if(currentStep!=null){
            taskPanel = currentTaskPanel = currentStep.getPanel("taskPanel");
        }else{
            taskPanel = currentTaskPanel = new EmptyPanel("taskPanel");
        }
        
        taskPanel.setOutputMarkupId(true);
        currentTaskPanel.setOutputMarkupId(true);
        stepList = ticket.getHistoryTaskSteps();
        Link currentStepLink = new Link<Object>("currentTask") {
            @Override
            public void onClick() {
                taskPanel.replaceWith(currentTaskPanel);
                taskPanel = currentTaskPanel;
            }
        };
        if(currentStep!=null){
            add(new Label("ticketcurrentTaskStep", currentStep.getName()));
            
        }else{
            add(new Label("ticketcurrentTaskStep", "N/A"));
            currentStepLink.setVisible(false);
        }
        add(currentStepLink);
        IModel<? extends List<? extends WebTaskStep>> stepModel = new LoadableDetachableModel<List<WebTaskStep>>() {
            @Override
            protected List<WebTaskStep> load() {
                return stepList;
            }
        };
        add(new ListView<WebTaskStep>("historyStepList", stepModel) {
            @Override
            protected void populateItem(ListItem<WebTaskStep> item) {
                item.add(new Link<WebTaskStep>("taskStepLink", item.getModel()) {
                    @Override
                    public void onClick() {
                        Panel newPanel = getModelObject().getPanel("taskPanel");
                        newPanel.setOutputMarkupId(true);
                        taskPanel.replaceWith(newPanel);
                        taskPanel = newPanel;
                    }
                });
                item.add(new Label("tickethistoryTaskStep", item.getModelObject().getName()));
            }
        });
        add(currentTaskPanel);
        Link link = new Link("finishCurrentTask"){
            @Override
            public void onClick() {
                ticket.finishCurrentTaskStep();
                //TODO: Process event
            }
        };
        if(currentStep==null){
            link.setVisible(false);
        }
        add(link);
    }
}
