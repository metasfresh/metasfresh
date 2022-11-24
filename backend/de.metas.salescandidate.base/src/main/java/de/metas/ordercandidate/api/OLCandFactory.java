package de.metas.ordercandidate.api;

import de.metas.async.AsyncBatchId;
import de.metas.bpartner.BPartnerId;
import de.metas.document.DocTypeId;
import de.metas.order.DeliveryRule;
import de.metas.order.DeliveryViaRule;
import de.metas.order.OrderLineGroup;
import de.metas.ordercandidate.model.I_C_OLCand;
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
	private final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);

	public OLCand toOLCand(@NonNull final I_C_OLCand record)
	{
		final OrderLineGroup orderLineGroup = Check.isBlank(record.getCompensationGroupKey())
				? null
				: OrderLineGroup.builder()
				.groupKey(record.getCompensationGroupKey())
				.isGroupingError(record.isGroupingError())
				.groupingErrorMessage(record.getGroupingErrorMessage())
				.discount(Percent.ofNullable(record.getGroupCompensationDiscountPercentage()))
				.build();

		final Quantity qtyItemCapacity = olCandEffectiveValuesBL.getQtyItemCapacity_Effective(record);
		
		return OLCand.builder()
				.olCandEffectiveValuesBL(olCandEffectiveValuesBL)
				.olCandRecord(record)
				.pricingSystemId(PricingSystemId.ofRepoIdOrNull(record.getM_PricingSystem_ID()))
				.deliveryRule(DeliveryRule.ofNullableCode(record.getDeliveryRule()))
				.deliveryViaRule(DeliveryViaRule.ofNullableCode(record.getDeliveryViaRule()))
				.shipperId(ShipperId.ofRepoIdOrNull(record.getM_Shipper_ID()))
				.paymentRule(PaymentRule.ofNullableCode(record.getPaymentRule()))
				.paymentTermId(PaymentTermId.ofRepoIdOrNull(record.getC_PaymentTerm_ID()))
				.salesRepId(BPartnerId.ofRepoIdOrNull(record.getC_BPartner_SalesRep_ID()))
				.orderDocTypeId(DocTypeId.ofRepoIdOrNull(record.getC_DocTypeOrder_ID()))
				.orderLineGroup(orderLineGroup)
				.asyncBatchId(AsyncBatchId.ofRepoIdOrNull(record.getC_Async_Batch_ID()))
				.qtyItemCapacityEff(qtyItemCapacity)
				.salesRepInternalId(BPartnerId.ofRepoIdOrNull(record.getC_BPartner_SalesRep_Internal_ID()))
				.assignSalesRepRule(AssignSalesRepRule.ofCode(record.getApplySalesRepFrom()))
				.bpartnerName(record.getBPartnerName())
				.email(record.getEMail())
				.phone(record.getPhone())
				.build();
	}
}
