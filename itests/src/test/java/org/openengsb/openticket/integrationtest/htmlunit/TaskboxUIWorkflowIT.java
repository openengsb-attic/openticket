package org.openengsb.openticket.integrationtest.htmlunit;

import static org.junit.Assert.assertEquals;
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
import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

@RunWith(JUnit4TestRunner.class)
public class TaskboxUIWorkflowIT extends AbstractExamTestHelper {

    private WebClient webClient;
    private String startPageUrl = "http://localhost:8091/openticket/";
    private static long WAIT_PAGE_REFRESH = 2000L;

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
    public void testTicketPassingThroughWorkflow() throws Exception {
        final HtmlPage startPage = webClient.getPage(startPageUrl);
        final HtmlPage loginPage = startPage.getAnchorByText("Login").click();
        HtmlForm form = loginPage.getForms().get(0);
        HtmlSubmitInput loginButton = form.getInputByValue("Login");
        form.getInputByName("username").setValueAttribute("admin");
        form.getInputByName("password").setValueAttribute("password");
        HtmlPage indexPage = loginButton.click();

        HtmlPage createTicketPage = indexPage.getAnchorByText("Create Ticket").click();
        HtmlForm createForm = createTicketPage.getForms().get(0);
        createForm.getInputByName("ticketname").setValueAttribute("testname");
        createForm.getInputByName("ticketcustomer").setValueAttribute("testcustomer");
        createForm.getInputByName("ticketcontactEmailAddress").setValueAttribute("test@contact.com");
        createForm.getTextAreaByName("ticketdescription").setText("something is not right.");
        createForm.getSelectByName("tickettype").getOptions().get(2).setSelected(true);
        createForm.getSelectByName("ticketpriority").getOptions().get(2).setSelected(true);
        HtmlSubmitInput createButton = createForm.getInputByName("submitButton");
        createButton.click();

        Thread.sleep(WAIT_PAGE_REFRESH);

        indexPage = webClient.getPage(startPageUrl);
        HtmlPage overviewPage = indexPage.getAnchorByText("Ticket-Overview").click();
        HtmlTable table = overviewPage.getFirstByXPath("//table");
        HtmlTableRow ticketRow = table.getRow(2);
        HtmlPage developerTicketPage = ticketRow.getCell(0).getHtmlElementsByTagName("a").get(0).click();

        assertTrue(developerTicketPage.asText().contains("testname"));
        assertTrue(developerTicketPage.asText().contains("something is not right."));
        assertTrue(developerTicketPage.asText().contains("DeveloperTicket"));

        HtmlForm develForm = developerTicketPage.getForms().get(1);
        develForm.getInputByName("developerComment").setValueAttribute("done");
        develForm.getInputByName("problemsOccurred").setValueAttribute("none");
        develForm.getInputByName("workingHours").setValueAttribute("10");
        develForm.getInputByValue("Finish Ticket").click();

        Thread.sleep(WAIT_PAGE_REFRESH);

        indexPage = webClient.getPage(startPageUrl);
        overviewPage = indexPage.getAnchorByText("Ticket-Overview").click();
        table = overviewPage.getFirstByXPath("//table");
        ticketRow = table.getRow(2);
        HtmlPage reviewerTicketPage = ticketRow.getCell(0).getHtmlElementsByTagName("a").get(0).click();

        assertTrue(reviewerTicketPage.asText().contains("testname"));
        assertTrue(reviewerTicketPage.asText().contains("something is not right."));
        assertTrue(reviewerTicketPage.asText().contains("ReviewerTicket"));

        HtmlForm reviewForm = reviewerTicketPage.getForms().get(1);
        reviewForm.getTextAreaByName("feedback").setText("well done!");
        HtmlCheckBoxInput box = reviewForm.getInputByName("ticketResolved");
        box.setChecked(true);
        reviewForm.getInputByValue("Finish Ticket").click();

        Thread.sleep(WAIT_PAGE_REFRESH);

        indexPage = webClient.getPage(startPageUrl);
        overviewPage = indexPage.getAnchorByText("Ticket-Overview").click();
        table = overviewPage.getFirstByXPath("//table");
        ticketRow = table.getRow(2);
        HtmlPage finalTaskViewPage = ticketRow.getCell(0).getHtmlElementsByTagName("a").get(0).click();

        assertTrue(finalTaskViewPage.asText().contains("TaskFinalView"));
        assertTrue(finalTaskViewPage.asText().contains("something is not right."));
        assertTrue(finalTaskViewPage.asText().contains("done"));

        HtmlForm closeForm = finalTaskViewPage.getForms().get(1);
        closeForm.getInputByValue("Close").click();

        Thread.sleep(WAIT_PAGE_REFRESH);

        indexPage = webClient.getPage(startPageUrl);
        overviewPage = indexPage.getAnchorByText("Ticket-Overview").click();
        table = overviewPage.getFirstByXPath("//table");

        assertEquals(3, table.getRows().size());
    }

}
