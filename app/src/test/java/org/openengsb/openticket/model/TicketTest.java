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

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class TicketTest {
    private Ticket ticket;
    private DeveloperTicket devTicket;
    private ReviewerTicket revTicket;
    private InformationTicket infTicket;

    @Before
    public void init() throws Exception {
        ticket = new Ticket();
        ticket.setPriority(TicketPriority.Medium);
        ticket.setContactEmailAddress("test@domain.at");
        devTicket = new DeveloperTicket();
        devTicket.setWorkingHours(27);
        revTicket = new ReviewerTicket();
        revTicket.setTicketResolved(true);
        revTicket.setFeedback("some feedback");
        infTicket = new InformationTicket();
        infTicket.setInformation("42");
    }

    @Test
    public void initTicket_shouldInitializeProperties() throws Exception {
        assertTrue(ticket.getTaskId().length() > 0);
        assertTrue(ticket.getTaskCreationTimestamp().before(new Date(System.currentTimeMillis() + 10)));
        assertTrue(ticket.getPriority().equals(new String(TicketPriority.Medium.toString())));
        assertTrue(ticket.getContactEmailAddress().equals(new String("test@domain.at")));
    }

    @Test
    public void initDeveloperTicket_shouldInitializeProperties() throws Exception {
        assertTrue(devTicket.getTaskId().length() > 0);
        assertTrue(devTicket.getTaskCreationTimestamp().before(new Date(System.currentTimeMillis() + 10)));
        assertTrue(devTicket.getWorkingHours().equals(new Integer(27)));
    }

    @Test
    public void initReviewerTicket_shouldInitializeProperties() throws Exception {
        assertTrue(revTicket.getTaskId().length() > 0);
        assertTrue(revTicket.getTaskCreationTimestamp().before(new Date(System.currentTimeMillis() + 10)));
        assertTrue(revTicket.getTicketResolved());
        assertTrue(revTicket.getFeedback().equals(new String("some feedback")));
    }

    @Test
    public void initInformationTicket_shouldInitializeProperties() throws Exception {
        assertTrue(infTicket.getTaskId().length() > 0);
        assertTrue(infTicket.getTaskCreationTimestamp().before(new Date(System.currentTimeMillis() + 10)));
        assertTrue(infTicket.getInformation().equals(new String("42")));
    }
}