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

import org.openengsb.core.api.workflow.model.ProcessBag;

public class DeveloperTicket extends Ticket {

    public DeveloperTicket() {
        super();
    }

    public DeveloperTicket(ProcessBag bag) {
        super(bag);
    }

    public void setWorkingHours(Integer workingHours) {
        addOrReplaceProperty("workingHours", workingHours);
    }

    public Integer getWorkingHours() {
        return (Integer) getProperty("workingHours");
    }

    public void setDeveloperComment(String developerComment) {
        addOrReplaceProperty("developerComment", developerComment);
    }

    public String getDeveloperComment() {
        return (String) getProperty("developerComment");
    }

    public void setProblemsOccurred(String problemsOccurred) {
        addOrReplaceProperty("problemsOccurred", problemsOccurred);
    }

    public String getProblemsOccurred() {
        return (String) getProperty("problemsOccurred");
    }
}
