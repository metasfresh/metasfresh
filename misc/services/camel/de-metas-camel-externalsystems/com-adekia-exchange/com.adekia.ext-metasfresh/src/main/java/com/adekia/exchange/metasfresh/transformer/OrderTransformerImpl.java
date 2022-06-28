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
import com.adekia.exchange.transformer.OrderTransformer;
import com.helger.commons.string.StringHelper;
import de.metas.common.ordercandidates.v2.request.JsonOLCandCreateBulkRequest;
import de.metas.common.ordercandidates.v2.request.JsonOLCandCreateRequest;
import de.metas.common.ordercandidates.v2.request.JsonRequestBPartnerLocationAndContact;
import de.metas.common.ordercandidates.v2.request.JsonSalesPartner;
import de.metas.common.rest_api.v2.JSONPaymentRule;
import de.metas.common.rest_api.v2.JsonDocTypeInfo;
import lombok.NonNull;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.OrderLineType;
import oasis.names.specification.ubl.schema.xsd.order_23.OrderType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;

@Component
public class OrderTransformerImpl implements OrderTransformer {

    @Override
    public JsonOLCandCreateBulkRequest transform(OrderType order) throws Exception {
        if (order == null || CollectionUtils.isEmpty(order.getOrderLine()))
            return null;        // todo best beahaviour ?

        JsonOLCandCreateBulkRequest.JsonOLCandCreateBulkRequestBuilder olCandCreateBulkRequestBuilder = JsonOLCandCreateBulkRequest.builder();
        JsonOLCandCreateRequest.JsonOLCandCreateRequestBuilder olCandCreateRequestBuilder = JsonOLCandCreateRequest.builder();

        // Setup Order Level info
        olCandCreateRequestBuilder
                .orgCode(MetasfreshOrderConstants.MF_ORG_CODE)
                .bpartner(getBPartnerInfo())
                .billBPartner(getBPartnerInfo())
                .currencyCode(order.getDocumentCurrencyCodeValue())
                .dateCandidate(order.getIssueDateValueLocal())
                .dateOrdered(order.getIssueDateValueLocal())
                .deliveryRule(MetasfreshOrderConstants.MF_DELIVERY_RULE)
                .discount(BigDecimal.ZERO)
                .externalHeaderId(order.getSalesOrderIDValue())
                //.importWarningMessage(order.getDeliveryAtIndex(0).getDeliveryTermsAtIndex(0).getSpecialTermsAtIndex(0).getValue())  // //todo NPE && ArrayOutOfBoundException
                .invoiceDocType(JsonDocTypeInfo.builder().docBaseType(MetasfreshOrderConstants.MF_ORDER_DOC_BASE_TYPE).build())
                .isImportedWithIssues(false)
                .isManualPrice(true)
                .paymentRule(JSONPaymentRule.ofCodeOrNull(MetasfreshOrderConstants.MF_PAYMENT_RULE))
                .paymentTerm(MetasfreshOrderConstants.MF_PAYMENT_TERM)
                .poReference(StringHelper.hasText(order.getCustomerReferenceValue()) ? order.getCustomerReferenceValue() : " ")
                .pricingSystemCode(MetasfreshOrderConstants.MF_PRICING_SYSTEM_CODE)
                .salesPartner(JsonSalesPartner.builder().salesPartnerCode(MetasfreshOrderConstants.MF_SALES_PARTNER_CODE).build())
                .uomCode(MetasfreshOrderConstants.MF_UOM_CODE)
                .deliveryViaRule("S")
                .dataSource(MetasfreshOrderConstants.MF_DATA_SOURCE_INT_AMAZON)
        //				.dataDest()																									// todo  inutilisé ?
        ;

        // Setup OrderLine Level info
        order.getOrderLine().stream()
                .map(orderLine -> processOrderLine(olCandCreateRequestBuilder, orderLine))
                .forEach(olCandCreateBulkRequestBuilder::request);

        return olCandCreateBulkRequestBuilder.build();

    }

    private JsonOLCandCreateRequest processOrderLine(
            @NonNull final JsonOLCandCreateRequest.JsonOLCandCreateRequestBuilder olCandCreateRequestBuilder,
            @NonNull final OrderLineType orderLine) {
        return olCandCreateRequestBuilder
                .dateRequired(orderLine.getLineItem().getDeliveryAtIndex(0).getLatestDeliveryDateValueLocal())            //todo NPE && ArrayOutOfBoundException
                .description(orderLine.getLineItem().getItem().getNameValue())                                                //todo NPE
                .externalLineId(orderLine.getLineItem().getID().getValue())                                                    //todo NPE
                //.line(orderLine.getLineItem())																			//todo numérotation gérée par Metasfresh à valider
                .presetDateShipped(orderLine.getLineItem().getDeliveryAtIndex(0).getActualDeliveryDateValueLocal())        //todo NPE && ArrayOutOfBoundException
                .price(orderLine.getLineItem().getLineExtensionAmountValue())
                .productIdentifier(orderLine.getLineItem().getItem().getSellersItemIdentification().getIDValue())            //todo NPE
                .qty(orderLine.getLineItem().getQuantityValue())
                //.productIdentifier(ExternalIdentifierFormat.formatExternalId(orderLine.getProductId()))					//todo on fait quoi avec ceci
                .build();
    }

    @NonNull
    //todo : Get from bp upsert response see OLCandRequestProcessor.getBPartnerInfo  @shopware
    private JsonRequestBPartnerLocationAndContact getBPartnerInfo() {
        return JsonRequestBPartnerLocationAndContact.builder()
                .bPartnerIdentifier("1000000")
                .bPartnerLocationIdentifier("1000000")
                .contactIdentifier("1000000")
                .build();
    }
}
