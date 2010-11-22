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

package org.openengsb.openticket.ui.web;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;
import org.openengsb.core.common.Event;
import org.openengsb.core.common.context.ContextCurrentService;
import org.openengsb.core.common.persistence.PersistenceException;
import org.openengsb.core.common.taskbox.TaskboxException;
import org.openengsb.core.common.taskbox.TaskboxService;
import org.openengsb.core.common.workflow.WorkflowException;
import org.openengsb.openticket.model.Ticket;
import org.openengsb.openticket.ui.web.gateway.PersistenceGateway;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

@AuthorizeInstantiation("CASEWORKER")
public class CreateTicketPage extends BasePage {
    @SpringBean
    private TaskboxService service;
    
    @SpringBean
    private PersistenceGateway gateway;
    
    @SpringBean
    private ContextCurrentService ccservice;

    private Ticket ticket = new Ticket("");

    public CreateTicketPage() {
        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        feedback.setOutputMarkupId(true);
        add(feedback);
        
        CompoundPropertyModel<Ticket> ticketModel = new CompoundPropertyModel<Ticket>(ticket);
        Form<Ticket> form = new Form<Ticket>("inputForm", ticketModel);
        form.setOutputMarkupId(true);
        
        final String context = ccservice.getThreadLocalContext();

        form.add(new TextField<String>("id").setRequired(true).add(StringValidator.minimumLength(2)));
        form.add(new TextField<String>("type").setRequired(true).add(StringValidator.minimumLength(2)));
        form.add(new TextArea<String>("note").setRequired(true));
        
        
        form.add(new AjaxButton("submitButton", form)
        {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                try {
                    info("Ticket created");
                    target.addComponent(feedback);
                    service.startWorkflow("", "ticket", ticket);
                    gateway.saveObject(ticket);
                } catch (PersistenceException e) {
                    info(e.getMessage());
                } catch (TaskboxException e) {
                    info(e.getMessage());
                }
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                
                target.addComponent(feedback);
            }
        });
        add(form);
    }
}
