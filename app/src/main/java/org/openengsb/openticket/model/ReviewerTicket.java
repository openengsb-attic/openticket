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

import org.openengsb.core.common.workflow.model.ProcessBag;

public class ReviewerTicket extends Ticket {

    public ReviewerTicket() {
        super();
        init();
    }

    public ReviewerTicket(ProcessBag bag) {
        super(bag);
        init();
    }
    
    private void init() {
        setTicketResolved(false);
    }

    public void setTicketResolved(boolean ticketResolved) {
        addOrReplaceProperty("ticketResolved", new Boolean(ticketResolved));
    }

    public boolean getTicketResolved() {
        return ((Boolean) getProperty("ticketResolved")).booleanValue();
    }

    public void setFeedback(String feedback) {
        addOrReplaceProperty("feedback", feedback);
    }

    public String getFeedback() {
        return (String) getProperty("feedback");
    }
}