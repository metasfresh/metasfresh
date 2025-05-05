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
import de.metas.common.bpartner.v2.request.JsonRequestBPartner;
import de.metas.common.rest_api.v2.SyncAdvise;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.PartyNameType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.PartyType;
import oasis.names.specification.ubl.schema.xsd.order_23.OrderType;
import org.springframework.util.CollectionUtils;

public class MetasfreshOrderBPartner {
    public JsonRequestBPartner from(OrderType order) {
        JsonRequestBPartner requestBPartner = new JsonRequestBPartner();
        requestBPartner.setActive(true);
        requestBPartner.setCode(MetasfreshOrderBPartnerConstant.MF_BP_CODE_PREFIX + order.getSalesOrderIDValue());
        requestBPartner.setGlobalId(MetasfreshOrderBPartnerConstant.MF_BP_CODE_PREFIX + order.getSalesOrderIDValue());
        requestBPartner.setCustomer(true);
        requestBPartner.setGroup(MetasfreshOrderBPartnerConstant.MF_BP_GROUP);
        requestBPartner.setInvoiceRule(MetasfreshOrderBPartnerConstant.MF_BP_INVOICE_RULE);
        requestBPartner.setLanguage(MetasfreshOrderBPartnerConstant.MF_BP_LANGUAGE);
        requestBPartner.setVendor(false);

        if (order.getBuyerCustomerParty() != null
                && order.getBuyerCustomerParty().getParty() != null) {
            PartyType buyerParty = order.getBuyerCustomerParty().getParty();
            if (!CollectionUtils.isEmpty(buyerParty.getPartyName())) {
                PartyNameType buyerPnp = order.getBuyerCustomerParty().getParty().getPartyNameAtIndex(0);
                requestBPartner.setCompanyName(buyerPnp.getNameValue());
            }
            if (buyerParty.getContact() != null) {
                // no mail info ? buyerParty.getContact().getElectronicMailValue();
                requestBPartner.setPhone(buyerParty.getContact().getTelephoneValue());
            }

            if (!CollectionUtils.isEmpty(buyerParty.getPartyIdentification()))
                requestBPartner.setVatId(buyerParty.getPartyIdentificationAtIndex(0).getIDValue());
        }

        requestBPartner.setSyncAdvise(SyncAdvise.JUST_CREATE_IF_NOT_EXISTS);
        return requestBPartner;
    }
}
