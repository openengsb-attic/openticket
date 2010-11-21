/**

   Copyright 2010 OpenEngSB Division, Vienna University of Technology

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */

package org.openengsb.openticket.model;

import java.io.Serializable;

import org.apache.wicket.markup.html.panel.Panel;
import org.openengsb.ui.taskbox.model.WebTaskStep;

public class DeveloperTaskStep implements WebTaskStep, Serializable {

    // name of this step
    private String name;

    // description of this step
    private String description;

    /*
     * Specific DeveloperTaskStep properties: attended working hours, comments
     * of the developer
     */
    private Integer workingHours;
    private String developerComment;

    // flag, if step is done or not
    private boolean doneFlag;

    @Override
    public boolean getDoneFlag() {
        return this.doneFlag;
    }

    @Override
    public void setDoneFlag(boolean doneFlag) {
        this.doneFlag = doneFlag;
    }

    public DeveloperTaskStep(String name, String description) {
        this.name = name;
        this.description = description;
        this.doneFlag = false;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setWorkingHours(Integer workingHours) {
        this.workingHours = workingHours;
    }

    public Integer getWorkingHours() {
        return workingHours;
    }

    public void setDeveloperComment(String developerComment) {
        this.developerComment = developerComment;
    }

    public String getDeveloperComment() {
        return developerComment;
    }

    @Override
	public Panel getPanel(String id) {
	    return new DeveloperTaskStepPanel(id, this);
    }

    @Override
    public String getTaskStepTypeDescription() {
        return "Development";
    }

    @Override
    public String getTaskStepType() {
        return TaskStepType.DeveloperTaskStep.toString();
    }

    // return ID of the According UI Panel
    // WicketPanel createEditingPanel();

    // return ID of the According UI Panel
    // WicketPanel createViewingPanel();
}
