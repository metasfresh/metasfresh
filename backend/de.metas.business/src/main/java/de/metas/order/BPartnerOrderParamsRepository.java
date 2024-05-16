package de.metas.order;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.cache.CCache;
import de.metas.freighcost.FreightCostRule;
import de.metas.lang.SOTrx;
import de.metas.payment.PaymentRule;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.PricingSystemId;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
 * #%L
 * de.metas.business
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

@Repository
public class BPartnerOrderParamsRepository
{
	private final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);

	private final CCache<BPartnerOrderParamsQuery, BPartnerOrderParams> cache = CCache
			.<BPartnerOrderParamsQuery, BPartnerOrderParams>builder()
			.cacheName(this.getClass().getSimpleName())
			.tableName(I_C_BPartner.Table_Name)
			.additionalTableNameToResetFor(I_C_BP_Group.Table_Name)
			.additionalTableNameToResetFor(I_AD_OrgInfo.Table_Name) // procingSysteId might be coming from here
			.build();

	public BPartnerOrderParams getBy(@NonNull final BPartnerOrderParamsQuery query)
	{
		return cache.getOrLoad(query, this::getBy0);
	}

	@Value
	@Builder
	public static class BPartnerOrderParamsQuery
	{
		@NonNull
		BPartnerId shipBPartnerId;

		@NonNull
		BPartnerId billBPartnerId;

		@NonNull
		SOTrx soTrx;
	}

	private BPartnerOrderParams getBy0(@NonNull final BPartnerOrderParamsQuery query)
	{
		final I_C_BPartner billBPartnerRecord = bpartnersRepo.getById(query.getBillBPartnerId());
		final I_C_BPartner shipBPartnerRecord = bpartnersRepo.getById(query.getShipBPartnerId());
		return ofRecord(billBPartnerRecord, shipBPartnerRecord, query.getSoTrx());
	}

	private BPartnerOrderParams ofRecord(
			@NonNull final I_C_BPartner billBPartnerRecord,
			@NonNull final I_C_BPartner shipBPartnerRecord,
			@NonNull final SOTrx soTrx)
	{
		return BPartnerOrderParams.builder()
				.deliveryRule(getDeliveryRuleOrNull(shipBPartnerRecord, soTrx))
				.deliveryViaRule(getDeliveryViaRuleOrNull(shipBPartnerRecord, soTrx))
				.freightCostRule(getFreightCostRule(shipBPartnerRecord))
				.invoiceRule(getInvoiceRule(billBPartnerRecord, soTrx))
				.paymentRule(getPaymentRule(billBPartnerRecord, soTrx))
				.paymentTermId(getPaymentTermId(billBPartnerRecord, soTrx))
				.pricingSystemId(getPricingSystemId(billBPartnerRecord, soTrx))
				.shipperId(getShipperId(shipBPartnerRecord))
				.build();
	}

	private Optional<PaymentTermId> getPaymentTermId(@NonNull final I_C_BPartner bpartnerRecord, @NonNull final SOTrx soTrx)
	{
		final PaymentTermId paymentTermId = PaymentTermId.ofRepoIdOrNull(
				soTrx.isSales()
						? bpartnerRecord.getC_PaymentTerm_ID()
						: bpartnerRecord.getPO_PaymentTerm_ID());
		return Optional.ofNullable(paymentTermId);
	}

	private Optional<FreightCostRule> getFreightCostRule(@NonNull final I_C_BPartner bpartnerRecord)
	{
		return Optional.ofNullable(FreightCostRule.ofNullableCode(bpartnerRecord.getFreightCostRule()));
	}

	private Optional<InvoiceRule> getInvoiceRule(@NonNull final I_C_BPartner bpartnerRecord, @NonNull final SOTrx soTrx)
	{
		final InvoiceRule invoiceRule = soTrx.isSales()
				? InvoiceRule.ofNullableCode(bpartnerRecord.getInvoiceRule())
				: InvoiceRule.ofNullableCode(bpartnerRecord.getPO_InvoiceRule());

		return Optional.ofNullable(invoiceRule);
	}

	private Optional<DeliveryRule> getDeliveryRuleOrNull(@NonNull final I_C_BPartner bpartnerRecord, @NonNull final SOTrx soTrx)
	{
		if (soTrx.isSales())
		{
			return Optional.ofNullable(DeliveryRule.ofNullableCode(bpartnerRecord.getDeliveryRule()));
		}

		return Optional.empty(); // shall not happen
	}

	private Optional<DeliveryViaRule> getDeliveryViaRuleOrNull(@NonNull final I_C_BPartner bpartnerRecord, @NonNull final SOTrx soTrx)
	{
		if (soTrx.isSales())
		{
			return Optional.ofNullable(DeliveryViaRule.ofNullableCode(bpartnerRecord.getDeliveryViaRule()));
		}
		else if (soTrx.isPurchase())
		{
			return Optional.ofNullable(DeliveryViaRule.ofNullableCode(bpartnerRecord.getPO_DeliveryViaRule()));
		}

		return Optional.empty(); // shall not happen
	}

	private Optional<ShipperId> getShipperId(@NonNull final I_C_BPartner bpartnerRecord)
	{
		final int shipperId = bpartnerRecord.getM_Shipper_ID();

		return Optional.ofNullable(ShipperId.ofRepoIdOrNull(shipperId));
	}

	private PaymentRule getPaymentRule(@NonNull final I_C_BPartner bpartnerRecord, @NonNull final SOTrx soTrx)
	{
		final PaymentRule paymentRule = soTrx.isSales()
				? PaymentRule.ofCode(bpartnerRecord.getPaymentRule())
				: PaymentRule.ofCode(bpartnerRecord.getPaymentRulePO());

		if (soTrx.isSales() && paymentRule.isCashOrCheck()) // No Cash/Check/Transfer:
		{
			// for SO_Trx
			return PaymentRule.OnCredit; // Payment Term
		}
		if (soTrx.isPurchase() && paymentRule.isCash())  // No Cash for PO_Trx
		{
			return PaymentRule.OnCredit; // Payment Term
		}
		return paymentRule;
	}

	private Optional<PricingSystemId> getPricingSystemId(@NonNull final I_C_BPartner bpartnerRecord, @NonNull final SOTrx soTrx)
	{
		final IBPartnerDAO bpartnersDAO = Services.get(IBPartnerDAO.class);

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(bpartnerRecord.getC_BPartner_ID());
		final PricingSystemId pricingSysId = bpartnersDAO.retrievePricingSystemIdOrNull(bpartnerId, soTrx);

		return Optional.ofNullable(pricingSysId);
	}
}
