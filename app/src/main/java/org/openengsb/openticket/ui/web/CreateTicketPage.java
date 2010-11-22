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
import org.openengsb.core.common.taskbox.TaskboxService;
import org.openengsb.openticket.model.Ticket;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

@AuthorizeInstantiation("CASEWORKER")
public class CreateTicketPage extends BasePage {
    @SpringBean
    private TaskboxService service;

    private Ticket ticket = new Ticket("");
    private Panel panel;

    public CreateTicketPage() {
        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        feedback.setOutputMarkupId(true);
        add(feedback);
        
        
        CompoundPropertyModel<Ticket> ticketModel = new CompoundPropertyModel<Ticket>(ticket);
        Form<Ticket> form = new Form<Ticket>("inputForm", ticketModel);
        form.setOutputMarkupId(true);
        

        form.add(new TextField<String>("id").setRequired(true));
        form.add(new TextField<String>("type").setRequired(true));
        form.add(new TextField<String>("note").setRequired(true));
        
        
        form.add(new AjaxButton("submitButton", form)
        {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                 info(new Integer(ticket.getNotes().size()).toString());
                 System.out.println("success");
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                
                target.addComponent(feedback);
            }
        });
        add(form);
    }
}
