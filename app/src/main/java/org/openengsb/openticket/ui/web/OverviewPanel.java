package org.openengsb.openticket.ui.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilteredAbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.GoAndClearFilter;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.TextFilteredPropertyColumn;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.openengsb.openticket.model.Ticket;
import org.openengsb.openticket.model.TicketDataProvider;

@AuthorizeInstantiation("CASEWORKER")
public class OverviewPanel extends BasePage {

    public OverviewPanel() {
        List<IColumn<Ticket>> columns = new ArrayList<IColumn<Ticket>>();

        add(new Link("createLink") {

            public void onClick() {
                // TODO: ADD Link to CreatePage
            }

        });

        IColumn actionsColumn = new FilteredAbstractColumn<Ticket>(Model.of("Actions")) {

            @Override
            public Component getFilter(String componentId, FilterForm form) {
                return new GoAndClearFilter(componentId, form);
            }

            @Override
            public void populateItem(Item cellItem, String componentId, IModel rowModel) {
                final Ticket ticket = (Ticket) rowModel.getObject();
                cellItem.add(new UserActionsPanel(componentId, ticket));
            }
        };
        columns.add(actionsColumn);

        columns.add(new TextFilteredPropertyColumn<Ticket, String>(Model.of("Id"), "id", "id"));
        columns.add(new TextFilteredPropertyColumn<Ticket, String>(Model.of("Type"), "type", "type"));

        TicketDataProvider dataProvider = new TicketDataProvider();

        FilterForm form = new FilterForm("form", dataProvider);

        DefaultDataTable<Ticket> dataTable = new DefaultDataTable<Ticket>("dataTable", columns, dataProvider, 10);
        dataTable.addTopToolbar(new FilterToolbar(dataTable, form, dataProvider));
        form.add(dataTable);

        add(form);
    }

    private static class UserActionsPanel extends Panel {

        public UserActionsPanel(String id, Ticket ticket) {
            super(id);
            final String ticketId = ticket.getId();

            add(new Link("editLink") {
                /**
                 * Go to the Edit page, passing this page and the id of the Contact involved.
                 */
                public void onClick() {
                    // TODO:set Link to Editsite with ticketId
                }

            });
        }

    }
}
