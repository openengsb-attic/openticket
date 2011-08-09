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

package org.openengsb.openticket.integrationtest.htmlunit;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openengsb.openticket.integrationtest.util.AbstractExamTestHelper;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

@RunWith(JUnit4TestRunner.class)
public class BaseUiInfrastructureIT extends AbstractExamTestHelper {
    private WebClient webClient;
    private String startPageUrl = "http://localhost:8091/openticket/";

    @Before
    public void setUp() throws Exception {
        webClient = new WebClient();
    }

    @After
    public void tearDown() throws Exception {
        webClient.closeAllWindows();
        FileUtils.deleteDirectory(new File(getWorkingDirectory()));
    }

    @Test
    public void testIfAllMainNavigationLinksWork() throws Exception {
        final HtmlPage page = webClient.getPage(startPageUrl);
        final HtmlPage loginPage=page.getAnchorByText("Login").click();
        HtmlForm form = loginPage.getForms().get(0);
        HtmlSubmitInput loginButton = form.getInputByValue("Login");
        form.getInputByName("username").setValueAttribute("user");
        form.getInputByName("password").setValueAttribute("password");
        HtmlPage indexPage = loginButton.click();
        assertTrue(indexPage.asText().contains("OpenTicket is a solution based "));
    }
}
