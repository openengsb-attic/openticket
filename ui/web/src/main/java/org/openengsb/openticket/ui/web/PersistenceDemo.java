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
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.SimpleFormComponentLabel;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.validation.validator.StringValidator;
import org.openengsb.core.persistence.PersistenceException;
import org.openengsb.openticket.ui.web.gateway.PersistenceGateway;
import org.openengsb.openticket.ui.web.model.TestObject;

public class PersistenceDemo extends BasePage {
    private TestObject value = new TestObject("affe");

    public PersistenceDemo() {
        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        feedback.setOutputMarkupId(true);
        add(feedback);

        Form<TestObject> form = new Form<TestObject>("form", new CompoundPropertyModel<TestObject>(value));
        add(form);
        form.setOutputMarkupId(true);

        FormComponent<String> fc = new RequiredTextField<String>("value");
        fc.add(StringValidator.minimumLength(2));
        fc.setLabel(new ResourceModel("value.text"));
        form.add(fc);
        form.add(new SimpleFormComponentLabel("value-label", fc));

        form.add(new AjaxButton("button", form)
        {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                try {
                    new PersistenceGateway().saveTestObject(value);
                    info("Value successfully written.");
                } catch (PersistenceException e) {
                }
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.addComponent(feedback);
            }
        });

        form.add(new AjaxButton("button2", form)
        {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                info(new PersistenceGateway().readTestObject(new TestObject("affe")).getValue());
            }
        });
    }
}
