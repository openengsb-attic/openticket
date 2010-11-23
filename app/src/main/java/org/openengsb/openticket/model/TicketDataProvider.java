package org.openengsb.openticket.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.util.SingleSortState;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.openengsb.openticket.ui.web.gateway.PersistenceGateway;

public class TicketDataProvider implements ISortableDataProvider<Ticket>, IFilterStateLocator {

    ISortState sortState;
    TicketFilter filter = new TicketFilter();
    List<Ticket> list;
    
    private PersistenceGateway gateway;

    public void setGateway(PersistenceGateway gateway) {
        this.gateway = gateway;
    }

    public TicketDataProvider() {
        sortState = new SingleSortState();
        sortState.setPropertySortOrder("id", ISortState.ASCENDING);
    }

    @Override
    public Object getFilterState() {
        return filter;
    }

    @Override
    public void setFilterState(Object state) {
        filter = (TicketFilter) state;
    }

    @Override
    public Iterator<? extends Ticket> iterator(int first, int count) {
        initList();

        List<Ticket> ret = list;
        if (ret.size() > (first + count)) {
            ret = ret.subList(first, first + count);
        } else {
            ret = ret.subList(first, ret.size());
        }
        return ret.iterator();
    }

    @Override
    public int size() {
        initList();
        return list.size();
    }

    @Override
    public IModel<Ticket> model(Ticket object) {
        return Model.of(object);
    }

    @Override
    public void detach() {
        list = null;
    }

    @Override
    public ISortState getSortState() {
        return sortState;
    }

    @Override
    public void setSortState(ISortState state) {
        sortState = state;

    }

    private void initList() {
        if (list == null) {
            final int idSort;
            final int typeSort;

            if (sortState != null) {
                idSort = sortState.getPropertySortOrder("id");
                typeSort = sortState.getPropertySortOrder("type");
            } else {
                idSort = ISortState.NONE;
                typeSort = ISortState.NONE;
            }
            list = getSortedList(idSort, typeSort, filter);
        }
    }

    private List<Ticket> getSortedList(final int idSort, final int typeSort, TicketFilter filter) {

        List<Ticket> result = gateway.readAllTickets();

        if (result != null) {
            filterTickets(result, filter);
            Collections.sort(result, new Comparator<Ticket>() {

                @Override
                public int compare(Ticket t1, Ticket t2) {
                    int compId = t1.getId().compareTo(t2.getId());
                    int compType = t1.getType().compareTo(t2.getType());

                    switch (idSort) {
                        case ISortState.NONE:
                       compId = 0;
                       break;
                   case ISortState.DESCENDING:
                       compId = -compId;
                       break;
               }
               switch (typeSort) {
                   case ISortState.NONE:
                       compType = 0;
                       break;
                   case ISortState.DESCENDING:
                       compType = -compType;
                       break;
               }
               if (compId != 0)
                   return compId;
               return compType;
           }

            });
        }else{
            result = new ArrayList<Ticket>();
        }
        return result;
    }

    private void filterTickets(List<Ticket> tickets, TicketFilter filter) {
        List<Ticket> tmp = new ArrayList<Ticket>();
        for (Iterator<Ticket> iterator = tickets.iterator(); iterator.hasNext();) {
            Ticket ticket = iterator.next();
            if (!filter.match(ticket)) {
                tmp.add(ticket);
            }
        }
        tickets.removeAll(tmp);
    }
}
