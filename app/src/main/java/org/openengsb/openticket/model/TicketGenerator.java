package org.openengsb.openticket.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class temporary creates random tickets to test the overview display It will be replaced by a connection to
 * persistance.
 */
public class TicketGenerator {
    public static List<Ticket> getBeans(int size, TicketFilter filter) {

        List<Ticket> ret = new ArrayList<Ticket>();
        for (int i = 0; i < size; i++) {
            Ticket ticket = new Ticket(i + "");
            switch (i % 3) {
                case 0:
                    ticket.setType("a");
                    break;
                case 1:
                    ticket.setType("ab");
                    break;
                case 2:
                    ticket.setType("abc");
            }
            if (filter != null) {
                if (filter.match(ticket)) {
                    ret.add(ticket);
                }
            } else {
                ret.add(ticket);
            }
        }
        return ret;
    }
}
