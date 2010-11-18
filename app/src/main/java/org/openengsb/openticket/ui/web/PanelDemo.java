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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;
import org.openengsb.core.common.taskbox.TaskboxService;
import org.openengsb.openticket.model.DeveloperTaskStep;
import org.openengsb.openticket.model.TestObject;
import org.openengsb.openticket.model.Ticket;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.SimpleFormComponentLabel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;

@AuthorizeInstantiation("CASEWORKER")
public class PanelDemo extends BasePage {
    @SpringBean
    private TaskboxService service;

    private TestObject value = new TestObject();
    private Panel panel;

    public PanelDemo() {
        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        feedback.setOutputMarkupId(true);
        add(feedback);
        add(new Label("headeroutput", "PanelDemo Page"));
        
        
        
        Form<TestObject> form = new Form<TestObject>("form", new CompoundPropertyModel<TestObject>(value));
        form.setOutputMarkupId(true);
        add(form);

        FormComponent<String> fc = new RequiredTextField<String>("objid");
        fc.add(StringValidator.minimumLength(2));
        fc.setLabel(new ResourceModel("id.text"));
        form.add(fc);
        form.add(new SimpleFormComponentLabel("id-label", fc));
        
        form.add(new AjaxButton("button", form)
        {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    Ticket t = new Ticket(value.getObjid());
                    t.setCurrentTaskStep(new DeveloperTaskStep("first", "first"));
                    t.finishCurrentTaskStep(new DeveloperTaskStep("second", "second"));
                    Panel newPanel = t.getPanel("panel");
                    newPanel.setOutputMarkupId(true);
                    panel.replaceWith(newPanel);
                    panel = newPanel;
                    target.addComponent(panel);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.addComponent(feedback);
            }
        });
        Ticket t = new Ticket("test");
        t.setCurrentTaskStep(new DeveloperTaskStep("first", "first"));
        t.finishCurrentTaskStep(new DeveloperTaskStep("second", "second"));
        panel = t.getPanel("panel");
        panel.setOutputMarkupId(true);
        add(panel);
    }
}
