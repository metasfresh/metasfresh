/*
 * #%L
 * camel-routes
 * %%
 * Copyright (C) 2022 Adekia
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package com.adekia.exchange.camel.provider;

import com.adekia.exchange.context.Ctx;
import com.adekia.exchange.provider.GetOrderProvider;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.AddressType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.ContactType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.CustomerPartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.ItemType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.LineItemType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.OrderLineType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.PartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.PriceType;
import oasis.names.specification.ubl.schema.xsd.order_23.OrderType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Component
@ConditionalOnSingleCandidate(GetOrderProvider.class)
public class GetOrderProviderSimpleImpl implements GetOrderProvider {
    @Override
    public OrderType getOrder(Ctx ctx) {
        OrderType order = new OrderType();
        order.setUUID(UUID.randomUUID().toString());

        CustomerPartyType cpt = new CustomerPartyType();
        PartyType pt = new PartyType();
        ContactType ct = new ContactType();
        ct.setElectronicMail("sauron@barad.dur");
        pt.setContact(ct);
        AddressType at = new AddressType();
        at.setID("Mordor");
        pt.setPostalAddress(at);
        cpt.setParty(pt);
        order.setBuyerCustomerParty(cpt);
        order.setOrderLine(createOrderLines());
        return order;
    }

    private String[][] products = {
            {"One Ring", "To rule them all"},
            {"Three rings", "For the elven-kings under the sky"},
            {"Seven rings", "For the Dwarf-lords in their halls of stone"},
            {"Nine rings", "For mortal men doomed to die"}
    };

    private List<OrderLineType> createOrderLines() {

        return
                new Random().ints(ThreadLocalRandom.current().nextInt(4) + 1, 0, 4)
                        .mapToObj(i -> createLine(i)
                        ).collect(Collectors.toList());
    }

    private OrderLineType createLine(int i) {
        OrderLineType line = new OrderLineType();
        LineItemType lit = new LineItemType();
        lit.setID("" + i);
        PriceType pt = new PriceType();
        pt.setPriceAmount(BigDecimal.valueOf(ThreadLocalRandom.current().nextInt(999) + 1));
        lit.setPrice(pt);
        ItemType it = new ItemType();
        it.setName(products[i][0]);
        lit.setItem(it);
        line.setLineItem(lit);
        return line;
    }

    private String[] partners = {"Gandalf the White", "Frodo Baggins", "Aragorn"};

    //	private Partner createPartner()
    //	{
    //		int random = ThreadLocalRandom.current().nextInt(3);

    //		return Partner.builder()
    //						.id(random)
    //						.name(partners[random])
    //						.build();
    //	}

}
