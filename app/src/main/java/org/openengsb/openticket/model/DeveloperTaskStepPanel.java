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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.SimpleFormComponentLabel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;
import org.openengsb.core.common.persistence.PersistenceException;
import org.openengsb.openticket.ui.web.gateway.PersistenceGateway;

@AuthorizeInstantiation("CASEWORKER")
public class DeveloperTaskStepPanel extends Panel {
    @SpringBean
    private PersistenceGateway gateway;
    
    private DeveloperTaskStep tempStep, origStep;
    
    public DeveloperTaskStepPanel(String id, DeveloperTaskStep step) {
        super(id);
        this.origStep = step;
        this.tempStep = step;
        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        feedback.setOutputMarkupId(true);
        add(feedback);
        
        add(new Label("type-label", "Developer Task Step"));
        
        Form<DeveloperTaskStep> form = new Form<DeveloperTaskStep>("editStep", new CompoundPropertyModel<DeveloperTaskStep>(tempStep));
        form.setOutputMarkupId(true);
        add(form);

        FormComponent<String> fcName = new RequiredTextField<String>("name");
        fcName.add(StringValidator.lengthBetween(3, 15));
        fcName.setRequired(true);
        fcName.setLabel(new ResourceModel("edit.label.name"));
        form.add(fcName);
        form.add(new SimpleFormComponentLabel("edit-label-name", fcName));
        
        FormComponent<String> fcDesc = new RequiredTextField<String>("description");
        fcDesc.add(StringValidator.maximumLength(120));
        fcDesc.setRequired(true);
        fcDesc.setLabel(new ResourceModel("edit.label.desc"));
        form.add(fcDesc);
        form.add(new SimpleFormComponentLabel("edit-label-desc", fcDesc));
        
        FormComponent<Integer> fcH = new TextField<Integer>("workingHours", Integer.class);
        fcH.setType(Integer.class);
        //fcH.add(NumberValidator<String>.POSITIVE);
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
        
        AjaxButton saveButton = new AjaxButton("save", form)
        {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                try {
                    tempStep.setDoneFlag(true);
                    gateway.saveDeveloperTaskStep(tempStep);
                    origStep = tempStep;
                    info("This step was successfully saved");
                    target.addComponent(feedback);
                } catch (PersistenceException e) {
                }
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.addComponent(feedback);
            }
        };
        form.add(saveButton);
        
        form.add(new Button("reset"));
        
        if(tempStep.getDoneFlag()) {
            form.add(new Label("doneFlag-label", new ResourceModel("edit.label.doneFlag.closed")));
        }
        else {
            form.add(new Label("doneFlag-label", new ResourceModel("edit.label.doneFlag.open")));
        }
        
        AjaxButton closeButton = new AjaxButton("close", form)
        {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                try {
                    tempStep.setDoneFlag(true);
                    gateway.saveDeveloperTaskStep(tempStep);
                    origStep = tempStep;
                    info("This step is now closed");
                    target.addComponent(feedback);
                    
                    this.setEnabled(false);
                    this.setVisible(false);
                    
                } catch (PersistenceException e) {
                }
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.addComponent(feedback);
            }
        };
        if(tempStep.getDoneFlag()) {
            closeButton.setEnabled(false);
            closeButton.setVisible(false);
        }
        form.add(closeButton);
    }
    
    /*
    private Panel updatePanel() {
        Panel newPanel = tempStep.getPanel("taskPanel");
        newPanel.setOutputMarkupId(true);
        replaceWith(newPanel);
        return newPanel;
    }
    
    private Panel resetPanel() {
        Panel newPanel = origStep.getPanel("taskPanel");
        newPanel.setOutputMarkupId(true);
        replaceWith(newPanel);
        return newPanel;
    }*/
}
