package de.metas.ordercandidate.api;

import de.metas.async.AsyncBatchId;
import de.metas.bpartner.BPartnerId;
import de.metas.document.DocTypeId;
import de.metas.freighcost.FreightCostRule;
import de.metas.order.*;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentRule;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.PricingSystemId;
import de.metas.quantity.Quantity;
import de.metas.shipping.ShipperId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.service.ISysConfigBL;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;

/*
 * #%L
 * de.metas.salescandidate.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@ToString
final class OLCandFactory 
{
    public static final String SYSCONFIG_USE_DATE_CANDIDATE_AS_DATE_ORDERED = "de.metas.ordercandidate.Use_DateCandidate_As_DateOrdered";
    private final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);
    private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
    private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
    private final IOLCandBL olCandBL = Services.get(IOLCandBL.class);

    public OLCand toOLCand(@NonNull final I_C_OLCand olCandRecord)
    {
        return toOLCand(olCandRecord, null);
    }
    
    public OLCand toOLCand(
            @NonNull final I_C_OLCand olCandRecord,
            @Nullable OLCandOrderDefaults orderDefaults) 
    {
        final BPartnerOrderParams params = olCandBL.getBPartnerOrderParams(olCandRecord);

        final DeliveryRule deliveryRule = olCandBL.getDeliveryRule(olCandRecord, params, orderDefaults);
        final DeliveryViaRule deliveryViaRule = olCandBL.getDeliveryViaRule(olCandRecord, params, orderDefaults);
        final FreightCostRule freightCostRule = olCandBL.getFreightCostRule(params, orderDefaults);
        final InvoiceRule invoiceRule = olCandBL.getInvoiceRule(params, orderDefaults);
        final PaymentRule paymentRule = olCandBL.getPaymentRule(params, orderDefaults, olCandRecord);
        final PaymentTermId paymentTermId = olCandBL.getPaymentTermId(params, orderDefaults, olCandRecord);
        final PricingSystemId pricingSystemId = olCandBL.getPricingSystemId(olCandRecord, params, orderDefaults);
        final ShipperId shipperId = olCandBL.getShipperId(params, orderDefaults, olCandRecord);
        final DocTypeId orderDocTypeId = olCandBL.getOrderDocTypeId(orderDefaults, olCandRecord);
        final Quantity qtyItemCapacity = olCandEffectiveValuesBL.getQtyItemCapacity_Effective(olCandRecord);
        final BPartnerId salesRepId = BPartnerId.ofRepoIdOrNull(olCandRecord.getC_BPartner_SalesRep_ID());
        final BPartnerId salesRepInternalId = BPartnerId.ofRepoIdOrNull(olCandRecord.getC_BPartner_SalesRep_Internal_ID());
        final AssignSalesRepRule assignSalesRepRule = AssignSalesRepRule.ofCode(olCandRecord.getApplySalesRepFrom());

        final ZoneId tz = orgDAO.getTimeZone(OrgId.ofRepoId(olCandRecord.getAD_Org_ID()));
        final LocalDate presetDateInvoiced = TimeUtil.asLocalDate(olCandRecord.getPresetDateInvoiced(), tz);
        final LocalDate presetDateShipped = TimeUtil.asLocalDate(olCandRecord.getPresetDateShipped(), tz);

        final OrderLineGroup orderLineGroup = Check.isBlank(olCandRecord.getCompensationGroupKey())
                ? null
                : OrderLineGroup.builder()
				.groupKey(Objects.requireNonNull(olCandRecord.getCompensationGroupKey()))
				.isGroupMainItem(olCandRecord.isGroupCompensationLine())
                .isGroupingError(olCandRecord.isGroupingError())
                .groupingErrorMessage(olCandRecord.getGroupingErrorMessage())
                .discount(Percent.ofNullable(olCandRecord.getGroupCompensationDiscountPercentage()))
                .build();

        final OLCand.OLCandBuilder builder = OLCand.builder()
                .olCandEffectiveValuesBL(olCandEffectiveValuesBL)
                .olCandRecord(olCandRecord)
                .pricingSystemId(pricingSystemId)
                .deliveryRule(deliveryRule)
                .deliveryViaRule(deliveryViaRule)
				.freightCostRule(freightCostRule)
				.invoiceRule(invoiceRule)
                .shipperId(shipperId)
                .paymentRule(paymentRule)
                .paymentTermId(paymentTermId)
                .salesRepId(salesRepId)
                .orderDocTypeId(orderDocTypeId)
                .orderLineGroup(orderLineGroup)
                .asyncBatchId(AsyncBatchId.ofRepoIdOrNull(olCandRecord.getC_Async_Batch_ID()))
                .qtyItemCapacityEff(qtyItemCapacity)
                .salesRepInternalId(salesRepInternalId)
                .assignSalesRepRule(assignSalesRepRule)
                .bpartnerName(olCandRecord.getBPartnerName())
                .email(olCandRecord.getEMail())
                .phone(olCandRecord.getPhone())
                .presetDateShipped(presetDateShipped)
                .presetDateInvoiced(presetDateInvoiced);

        final boolean useDateCandidate = sysConfigBL.getBooleanValue(SYSCONFIG_USE_DATE_CANDIDATE_AS_DATE_ORDERED, false, olCandRecord.getAD_Client_ID(), olCandRecord.getAD_Org_ID());
        if (useDateCandidate) 
        {
            builder.dateOrdered(TimeUtil.asLocalDate(olCandRecord.getDateCandidate(), tz));
        } 
        else 
        {
            // this is how it was; but note that DateOrdered was not in the "EDI_Imp_C_OLCand" EXP_Format, 
            // so in the EDI ORDERS-case, it was always null.
            builder.dateOrdered(TimeUtil.asLocalDate(olCandRecord.getDateOrdered(), tz)); 
        }
        return builder.build();
    }
}
