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

package org.openengsb.openticket.ui.web.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.openengsb.openticket.model.CompleteTicketInformationStep;
import org.openengsb.openticket.model.DeveloperTaskStep;
import org.openengsb.openticket.model.InformationTaskStep;
import org.openengsb.openticket.model.ReviewerTaskStep;
import org.openengsb.openticket.model.TaskStepType;
import org.openengsb.openticket.model.Ticket;
import org.openengsb.ui.taskbox.model.WebTaskStep;

public class OpenticketModelTest {
    private Ticket t;

    @Before
    public void init() throws Exception {
        t = new Ticket("1");
    }

    @Test
    public void testCreateNewTicket_shouldNotFail() throws Exception {
        Ticket nt = new Ticket("nt1");
        if (nt == null)
            fail();
        assertThat(nt.getId(), is("nt1"));
    }

    @Test
    public void testTicketId_shouldEqualId2() throws Exception {
        t.setId("2");
        assertThat(t.getId(), is("2"));
    }

    @Test
    public void testTicketType_shouldEqualTestTicketType() throws Exception {
        t.setType("TestTicketType");
        assertThat(t.getType(), is("TestTicketType"));
    }

    @Test
    public void testTicketHistoryNotes_shouldAssert15() throws Exception {
        t = new Ticket("thn");
        t.setType("type");
        t.addNoteEntry("note1");
        t.addNoteEntry("note2");
        t.addNoteEntry("note3");
        // Should be 5 HistoryEntrys and 3 NoteEntrys
        int testPrimeProduct = t.getHistory().size() * t.getNotes().size();
        assertThat(testPrimeProduct, is(15));
    }

    @Test
    public void testCompleteTicketInformationStep_shouldAssertTaskStepType() throws Exception {
        WebTaskStep ts = new CompleteTicketInformationStep("name", "desc");
        assertThat(ts.getTaskStepType(), is("CompleteTicketInformationStep"));
    }

    @Test
    public void testDeveloperTaskStep_shouldAssertTaskStepType() throws Exception {
        WebTaskStep ts = new DeveloperTaskStep("name", "desc");
        assertThat(ts.getTaskStepType(), is(TaskStepType.DeveloperTaskStep.toString()));
    }

    @Test
    public void testInformationTaskStep_shouldAssertTaskStepType() throws Exception {
        WebTaskStep ts = new InformationTaskStep("name", "desc");
        assertThat(ts.getTaskStepType(), is("InformationTaskStep"));
    }

    @Test
    public void testReviewerTaskStep_shouldAssertTaskStepType() throws Exception {
        WebTaskStep ts = new ReviewerTaskStep("name", "desc");
        System.out.println(ts.getTaskStepType());
        assertThat(ts.getTaskStepType(), is("ReviewerTaskStep"));
    }

    @Test
    public void testTicketCurrentTaskStep_shouldAssertTaskStepName() throws Exception {
        WebTaskStep test, ts = new DeveloperTaskStep("dev-name", "dev-desc");
        t.setCurrentTaskStep(ts);
        test = t.getCurrentTaskStep();
        assertThat(ts.getName(), is(test.getName()));
    }

    @Test
    public void testTicketHistoryTaskSteps_shouldAssert3HistoryTaskSteps() throws Exception {
        WebTaskStep ts = new ReviewerTaskStep("rev-name", "rev-desc");
        t.setCurrentTaskStep(ts);
        ts = new DeveloperTaskStep("dev-name", "dev-desc");
        t.finishCurrentTaskStep(ts);
        ts = new InformationTaskStep("inf-name", "inf-desc");
        t.finishCurrentTaskStep(ts);
        t.finishCurrentTaskStep();
        assertThat(t.getHistoryTaskSteps().size(), is(3));
    }

    @Test
    public void testTicketFinishTaskSteps_shouldAssertDoneFlagTrue() throws Exception {
        WebTaskStep ts = new ReviewerTaskStep("rev-name", "rev-desc");
        t.setCurrentTaskStep(ts);
        ts = t.finishCurrentTaskStep();
        assertThat(ts.getDoneFlag(), is(true));
    }
}
