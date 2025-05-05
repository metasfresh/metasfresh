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
import com.adekia.exchange.transformer.OrderPaymentTransformer;
import de.metas.common.rest_api.v2.order.JsonOrderPaymentCreateRequest;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_23.PaymentTermsType;
import oasis.names.specification.ubl.schema.xsd.order_23.OrderType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

// todo : check if OrderPaymentTransformer could be a PaymentTransformer
@Component
public class OrderPaymentTransformerImpl implements OrderPaymentTransformer {
    @Override
    public List<Object> transform(final OrderType order) {
        if (order == null || order.getPaymentTermsCount() == 0)
            return new ArrayList<Object>();

        return order.getPaymentTerms().stream()
                //				.filter(pay -> (pay != null && pay.getAmount() != null))
                .map(pay -> process(order.getSalesOrderIDValue(), "", pay))
                .collect(Collectors.toList());
    }

    private JsonOrderPaymentCreateRequest process(final String orderID, final String bpID, final PaymentTermsType payment) {
        return JsonOrderPaymentCreateRequest.builder()
                .amount(Objects.requireNonNull(payment.getAmountValue()))
                .bpartnerIdentifier(bpID)                                                    // todo ExternalIdentifierFormat.formatExternalId(bPartnerIdentifier)
                .currencyCode(payment.getAmount().getCurrencyID())
                .discountAmt(BigDecimal.ZERO)
                //				.docBaseType(OrderCandidateConstants.MF_PAY_DOC_BASE_TYPE)  					// todo needed ? bug declared as BigDecimal
                .docSubType(MetasfreshOrderConstants.MF_PAY_DOC_SUB_TYPE)
                .externalPaymentId((payment.getPaymentMeansIDCount() > 0) ? orderID + "_" + payment.getPaymentMeansIDAtIndex(0).getValue() : "")
                .orderIdentifier(orderID)                                                        // todo ExternalIdentifierFormat.formatOldStyleExternalId(order.getId())
                .orgCode(MetasfreshOrderConstants.MF_ORG_CODE)
                .transactionDate(payment.getPaymentDueDateValueLocal())
                .writeOffAmt(BigDecimal.ZERO)
                .build();
    }
}
