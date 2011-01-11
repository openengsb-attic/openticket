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

package org.openengsb.openticket.ui.web.panel;

import java.util.ArrayList;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.SimpleFormComponentLabel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;
import org.openengsb.core.common.taskbox.TaskboxService;
import org.openengsb.core.common.taskbox.model.Task;
import org.openengsb.core.common.workflow.WorkflowException;
import org.openengsb.openticket.model.DeveloperTicket;

@AuthorizeInstantiation("CASEWORKER")
public class DeveloperTicketPanel extends Panel {

    /*
    @SpringBean
    private PersistenceGateway gateway;
    */
    @SpringBean(name="taskboxService")
    private TaskboxService service;
    
    private DeveloperTicket orig, temp;

    public DeveloperTicketPanel(String id, Task task) {
        super(id);
        orig = new DeveloperTicket(task);
        temp = new DeveloperTicket(orig);
        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        feedback.setOutputMarkupId(true);
        add(feedback);
        
        CompoundPropertyModel<DeveloperTicket> ticketModel = new CompoundPropertyModel<DeveloperTicket>(temp);
        Form<DeveloperTicket> form = new Form<DeveloperTicket>("editTicket", ticketModel);
        form.setOutputMarkupId(true);
        add(form);
        
        
        FormComponent<String> fcName = new TextField<String>("name");
        fcName.add(StringValidator.lengthBetween(3, 15));
        fcName.setEnabled(false);
        fcName.setLabel(new ResourceModel("edit.label.name"));
        form.add(fcName);
        form.add(new SimpleFormComponentLabel("edit-label-name", fcName));
        
        FormComponent<String> fcDesc = new TextField<String>("description");
        fcDesc.add(StringValidator.maximumLength(120));
        fcDesc.setEnabled(false);
        fcDesc.setLabel(new ResourceModel("edit.label.desc"));
        form.add(fcDesc);
        form.add(new SimpleFormComponentLabel("edit-label-desc", fcDesc));
        
        
        FormComponent<Integer> fcH = new TextField<Integer>("workingHours", Integer.class);
        fcH.setType(Integer.class);
        fcH.setLabel(new ResourceModel("edit.label.h"));
        form.add(fcH);
        form.add(new SimpleFormComponentLabel("edit-label-h", fcH));
        
        FormComponent<String> fcDC = new TextField<String>("developerComment");
        fcDC.add(StringValidator.maximumLength(100));
        fcDC.setLabel(new ResourceModel("edit.label.dc"));
        form.add(fcDC);
        form.add(new SimpleFormComponentLabel("edit-label-dc", fcDC));
        
        FormComponent<String> fcPO = new TextField<String>("problemsOccurred");
        fcPO.add(StringValidator.maximumLength(100));
        fcPO.setLabel(new ResourceModel("edit.label.po"));
        form.add(fcPO);
        form.add(new SimpleFormComponentLabel("edit-label-po", fcPO));
        
        AjaxButton saveButton = new AjaxButton("save", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                try {
                    //gateway.saveDeveloperTaskStep(temp);
                    orig = temp;
                    
                    form.remove("listContainer");
                    form.add(printTicketProperties());
                    
                    form.setOutputMarkupId(true);
                    target.addComponent(form);
                    
                    info("Ticket was saved successfully");
                    target.addComponent(feedback);
                } catch (/*Persistence*/Exception e) {
                    e.printStackTrace();
                    //TODO: ???
                    error(e.toString()+" - - - ");
                    target.addComponent(feedback);
                }
            }
            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.addComponent(feedback);
            }
        };
        form.add(saveButton);
        
        form.add(new Button("reset"));
        
        /*
        if (temp.getDoneFlag()) {
            form.add(new Label("doneFlag-label", new ResourceModel("edit.label.doneFlag.closed")));
        } else {
            form.add(new Label("doneFlag-label", new ResourceModel("edit.label.doneFlag.open")));
        }
        */
        form.add(new Label("finished-label", new ResourceModel("edit.label.finished.false")));
        
        AjaxButton closeButton = new AjaxButton("close", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                try {
                    service.finishTask(temp);
                    
                    //gateway.saveDeveloperTaskStep(temp);
                    orig = temp;
                    info("This Ticket is now closed");
                    target.addComponent(feedback);
                    
                    this.setEnabled(false);
                    this.setVisible(false);
                    form.remove("close");
                    form.add(this);
                    
                    Label finished_label = new Label("finished-label", new ResourceModel("edit.label.finished.true"));
                    form.remove("finished-label");
                    form.add(finished_label);
                    
                    form.setOutputMarkupId(true);
                    target.addComponent(form);
                } catch (WorkflowException e) {
                    e.printStackTrace();
                    //TODO: ???
                    error(e.toString()+" - - - ");
                    target.addComponent(feedback);
                }
            }
            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.addComponent(feedback);
            }
        };
        
        /*
        if (temp.getDoneFlag()) {
            closeButton.setEnabled(false);
            closeButton.setVisible(false);
        }
        */
        
        form.add(closeButton);
        form.add(printTicketProperties());
    }
    
    @SuppressWarnings("unchecked")
    public WebMarkupContainer printTicketProperties() {
        ArrayList<String> ticket_properties = new ArrayList<String>();
        String propertyName;
        for(int i =0; i<temp.propertyCount(); i++) {
            propertyName = new String(temp.propertyKeySet().toArray()[i].toString());
            ticket_properties.add(propertyName + " : " + temp.getProperty(propertyName));
        }
        
        ListView lv = new ListView("propertiesList", new ArrayList<String>(ticket_properties)) {
            @Override
            protected void populateItem(ListItem item) {
                item.add(new Label("propertiesLabel", item.getModel()));
            }
        };
        
        WebMarkupContainer listContainer = new WebMarkupContainer("listContainer");
        listContainer.setOutputMarkupId(true);
        listContainer.add(lv);

        return listContainer;
    }
}