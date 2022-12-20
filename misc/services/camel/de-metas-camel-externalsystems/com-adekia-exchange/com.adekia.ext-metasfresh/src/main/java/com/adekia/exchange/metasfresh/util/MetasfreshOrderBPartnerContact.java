/*
 * #%L
 * ext-metasfresh
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

package com.adekia.exchange.metasfresh.util;

import com.adekia.exchange.metasfresh.constant.MetasfreshOrderBPartnerConstant;
import de.metas.common.bpartner.v2.request.JsonRequestContact;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.PartyNameType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.PartyType;
import oasis.names.specification.ubl.schema.xsd.order_23.OrderType;
import org.springframework.util.CollectionUtils;

public class MetasfreshOrderBPartnerContact {
    public JsonRequestContact from(OrderType order) {
        JsonRequestContact buyerContact = new JsonRequestContact();
        buyerContact.setActive(true);
        buyerContact.setBillToDefault(true);
        buyerContact.setCode(MetasfreshOrderBPartnerConstant.MF_BP_CONTACT_CODE_PREFIX + order.getSalesOrderIDValue());
        buyerContact.setDefaultContact(true);
        buyerContact.setInvoiceEmailEnabled(false);
        buyerContact.setNewsletter(false);
        buyerContact.setPurchase(false);
        buyerContact.setPurchaseDefault(false);
        buyerContact.setSales(false);
        buyerContact.setSalesDefault(false);
        buyerContact.setShipToDefault(true);
        buyerContact.setSubjectMatter(false);

        if (order.getBuyerCustomerParty() != null
                && order.getBuyerCustomerParty().getParty() != null) {
            PartyType buyerParty = order.getBuyerCustomerParty().getParty();
            if (!CollectionUtils.isEmpty(buyerParty.getPartyName())) {
                PartyNameType buyerPnp = order.getBuyerCustomerParty().getParty().getPartyNameAtIndex(0);
                buyerContact.setName(buyerPnp.getNameValue());
            }
            if (buyerParty.getContact() != null) {
                buyerContact.setEmail(buyerParty.getContact().getElectronicMailValue());
                buyerContact.setPhone(buyerParty.getContact().getTelephoneValue());
                buyerContact.setMobilePhone(buyerParty.getContact().getTelephoneValue());
            }
        }

        return buyerContact;
    }

}
