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

package com.adekia.exchange.metasfresh.transformer;

import com.adekia.exchange.metasfresh.constant.MetasfreshOrderConstants;
import com.adekia.exchange.metasfresh.util.MetasfreshOrderBPartner;
import com.adekia.exchange.metasfresh.util.MetasfreshOrderBPartnerContact;
import com.adekia.exchange.metasfresh.util.MetasfreshOrderBPartnerLocation;
import com.adekia.exchange.model.BPartnerIdentifier;
import com.adekia.exchange.transformer.OrderBPTransformer;
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.common.bpartner.v2.request.*;
import de.metas.common.rest_api.v2.SyncAdvise;
import oasis.names.specification.ubl.schema.xsd.order_23.OrderType;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class OrderBPTransformerImpl implements OrderBPTransformer {
    @Override
    public BPUpsertCamelRequest transform(final OrderType order) throws Exception {

        if (order == null || order.getBuyerCustomerParty() == null || order.getBuyerCustomerParty().getParty() == null)
            throw new IllegalStateException("customer Party is required !");

        BPartnerIdentifier partnerIdentifier = new BPartnerIdentifier(order.getBuyerCustomerParty().getParty());

        JsonRequestBPartner jsonPartner = new MetasfreshOrderBPartner().from(order);
        JsonRequestContact jsonPartnerContact = new MetasfreshOrderBPartnerContact().from(order);
        JsonRequestLocation jsonPartnerLocation = new MetasfreshOrderBPartnerLocation().from(order);

        JsonRequestLocationUpsertItem jsonRequestLocationUpsertItem = JsonRequestLocationUpsertItem.builder()
                .location(jsonPartnerLocation)
                .locationIdentifier(partnerIdentifier.getPartnerLocationIdentifier())
                //				.externalVersion()	// todo
                .build();

        JsonRequestContactUpsertItem jsonRequestContactUpsertItem = JsonRequestContactUpsertItem.builder()
                .contact(jsonPartnerContact)
                .contactIdentifier(partnerIdentifier.getPartnerContactIdentifier())
                .build();

        JsonRequestLocationUpsert jsonRequestLocationUpsert = JsonRequestLocationUpsert.builder()
                .requestItem(jsonRequestLocationUpsertItem)
                .build();

        JsonRequestContactUpsert jsonRequestContactUpsert = JsonRequestContactUpsert.builder()
                .requestItem(jsonRequestContactUpsertItem)
                .build();

        JsonRequestComposite partnerComposite = JsonRequestComposite.builder()
                .bpartner(jsonPartner)
                .contacts(jsonRequestContactUpsert)
                .locations(jsonRequestLocationUpsert)
                .orgCode(MetasfreshOrderConstants.MF_ORG_CODE)        // todo
                .build();

        JsonRequestBPartnerUpsertItem jsonRequestBPartnerUpsertItem = JsonRequestBPartnerUpsertItem.builder()
                .bpartnerComposite(partnerComposite)
                .bpartnerIdentifier(partnerIdentifier.getPartnerIdentifier())
                .build();

        JsonRequestBPartnerUpsert jsonRequestBPartnerUpsert = JsonRequestBPartnerUpsert.builder()
                .requestItems(Arrays.asList(jsonRequestBPartnerUpsertItem))
                .syncAdvise(SyncAdvise.JUST_CREATE_IF_NOT_EXISTS)
                .build();

        return BPUpsertCamelRequest.builder()
                .jsonRequestBPartnerUpsert(jsonRequestBPartnerUpsert)
                .orgCode(MetasfreshOrderConstants.MF_ORG_CODE)        // todo
                .build();
    }
}
