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

    @Before
    public void init() throws Exception {
        ticket = new Ticket();
        ticket.setPriority(TicketPriority.Medium);
        ticket.setContactEmailAddress("test@domain.at");
    }

    @Test
    public void init_shouldInitializeProperties() throws Exception {
        assertTrue(ticket.getTaskId().length() > 0);
        assertTrue(ticket.getTaskCreationTimestamp().before(new Date(System.currentTimeMillis() + 10)));
        assertTrue(ticket.getPriority().equals(new String("Medium")));
        assertTrue(ticket.getContactEmailAddress().equals(new String("test@domain.at")));
    }
}