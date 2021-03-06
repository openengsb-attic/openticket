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
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.List;

import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.util.tester.FormTester;
import org.junit.Test;
import org.openengsb.ui.common.OpenEngSBWebSession;

public class LoginPageTest extends AuthenticatedPageTest {
    @Test
    public void testLoginPageIsDisplayed() {
        tester.startPage(LoginPage.class);
        tester.assertRenderedPage(LoginPage.class);
    }


    @Test
    public void testEnterLogin() {
        tester.startPage(LoginPage.class);
        FormTester formTester = tester.newFormTester("form");
        formTester.setValue("username", "user");
        formTester.setValue("password", "password");
        formTester.submit();
        tester.assertNoErrorMessage();
        assertTrue(OpenEngSBWebSession.get().isSignedIn());
        tester.assertRenderedPage(Welcome.class);
    }

    @Test
    public void testLogout() {
        tester.startPage(LoginPage.class);
        FormTester formTester = tester.newFormTester("form");
        formTester.setValue("username", "user");
        formTester.setValue("password", "password");
        formTester.submit();
        tester.clickLink("header:logout");
        assertFalse(OpenEngSBWebSession.get().isSignedIn());
        tester.assertRenderedPage(Welcome.class);
    }

    @Test
    public void testInvalidLogin() {
        tester.startPage(LoginPage.class);
        FormTester formTester = tester.newFormTester("form");
        formTester.setValue("username", "user");
        formTester.setValue("password", "wrongpassword");
        formTester.submit();
        tester.assertRenderedPage(LoginPage.class);
        assertFalse(OpenEngSBWebSession.get().isSignedIn());
        List<Serializable> messages = tester.getMessages(FeedbackMessage.ERROR);
        assertFalse(messages.isEmpty());
    }
}
