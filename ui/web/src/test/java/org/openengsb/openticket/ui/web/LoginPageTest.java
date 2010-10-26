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

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.Serializable;
import java.util.List;

import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.util.tester.FormTester;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openengsb.core.taskbox.TaskboxException;
import org.openengsb.core.taskbox.TaskboxService;

public class LoginPageTest extends AuthenticatedPageTest {
    private TaskboxService taskbox;

    @Before
    public void setup() throws TaskboxException {
        taskbox = mock(TaskboxService.class);
        when(taskbox.getWorkflowMessage()).thenReturn("demo");
        appContext.putBean(taskbox);
    }

    @Test
    public void testLoginPageIsDisplayed() {
        tester.startPage(LoginPage.class);
        tester.assertRenderedPage(LoginPage.class);
    }

    @Test
    public void testRedirectToLogin() {
        tester.startPage(Index.class);
        tester.assertRenderedPage(LoginPage.class);
    }

    @Test
    public void testEnterLogin() {
        tester.startPage(LoginPage.class);
        FormTester formTester = tester.newFormTester("form");
        formTester.setValue("username", "test");
        formTester.setValue("password", "password");
        formTester.submit();
        tester.assertNoErrorMessage();
        tester.assertRenderedPage(Index.class);
    }

    @Ignore
    @Test
    public void testLogout() {
        tester.startPage(LoginPage.class);
        FormTester formTester = tester.newFormTester("form");
        formTester.setValue("username", "test");
        formTester.setValue("password", "password");
        formTester.submit();
        tester.clickLink("logout");
        tester.assertRenderedPage(LoginPage.class);
    }

    @Test
    public void testInvalidLogin() {
        tester.startPage(LoginPage.class);
        FormTester formTester = tester.newFormTester("form");
        formTester.setValue("username", "test");
        formTester.setValue("password", "wrongpassword");
        formTester.submit();
        tester.assertRenderedPage(LoginPage.class);
        List<Serializable> messages = tester.getMessages(FeedbackMessage.ERROR);
        assertFalse(messages.isEmpty());
    }
}
