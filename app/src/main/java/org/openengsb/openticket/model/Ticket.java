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
import org.openengsb.core.api.workflow.model.Task;

@SuppressWarnings("serial")
public class Ticket extends Task {

    public Ticket() {
        super();
    }

    public Ticket(ProcessBag bag) {
        super(bag);
    }

    public void setPriority(TicketPriority priority) {
        addOrReplaceProperty("priority", priority.toString());
    }
    
    public void setPriority(String priority) {
        addOrReplaceProperty("priority", priority);
    }

    public String getPriority() {
        return (String) getProperty("priority");
    }

    public void setCustomer(String customer) {
        addOrReplaceProperty("customer", customer);
    }

    public String getCustomer() {
        return (String) getProperty("customer");
    }

    public void setContactEmailAddress(String contactEmailAddress) {
        addOrReplaceProperty("contactEmailAddress", contactEmailAddress);
    }

    public String getContactEmailAddress() {
        return (String) getProperty("contactEmailAddress");
    }
}