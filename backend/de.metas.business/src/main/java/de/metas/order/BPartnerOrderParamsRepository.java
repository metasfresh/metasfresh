/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.order;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.effective.BPartnerEffective;
import de.metas.bpartner.effective.BPartnerEffectiveBL;
import de.metas.cache.CCache;
import de.metas.lang.SOTrx;
import de.metas.payment.PaymentRule;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Incoterms;
import org.compiere.model.I_C_PaymentTerm;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BPartnerOrderParamsRepository
{
	@NonNull private final BPartnerEffectiveBL bPartnerEffectiveBL;

	private final CCache<BPartnerOrderParamsQuery, BPartnerOrderParams> cache = CCache
			.<BPartnerOrderParamsQuery, BPartnerOrderParams>builder()
			.cacheName(this.getClass().getSimpleName())
			.tableName(I_C_BPartner.Table_Name)
			.additionalTableNameToResetFor(I_C_BP_Group.Table_Name)
			.additionalTableNameToResetFor(I_C_PaymentTerm.Table_Name) // effective payment term default might come from here
			.additionalTableNameToResetFor(I_AD_OrgInfo.Table_Name) // pricingSystemId might be coming from here
			.additionalTableNameToResetFor(I_C_Incoterms.Table_Name) // effective incoterms default + its fields come from here
			.additionalTableNameToResetFor(I_AD_SysConfig.Table_Name)
			.build();

	public static BPartnerOrderParamsRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return SpringContextHolder.getBeanOrSupply(BPartnerOrderParamsRepository.class, () -> new BPartnerOrderParamsRepository(BPartnerEffectiveBL.newInstanceForUnitTesting()));
	}

	@NonNull
	public BPartnerOrderParams getBy(@NonNull final BPartnerOrderParamsQuery query)
	{
		return cache.getOrLoadNonNull(query, this::getBy0);
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

	@NonNull
	private BPartnerOrderParams getBy0(@NonNull final BPartnerOrderParamsQuery query)
	{
		final SOTrx soTrx = query.getSoTrx();
		final BPartnerEffective billBPartnerEffective = bPartnerEffectiveBL.getById(query.getBillBPartnerId());
		final BPartnerEffective shipBPartnerEffective = bPartnerEffectiveBL.getById(query.getShipBPartnerId());
		return BPartnerOrderParams.builder()
				.deliveryRule(Optional.ofNullable(shipBPartnerEffective.getDeliveryRule(soTrx)))
				.deliveryViaRule(Optional.ofNullable(shipBPartnerEffective.getDeliveryViaRule(soTrx)))
				.freightCostRule(Optional.ofNullable(shipBPartnerEffective.getFreightCostRule()))
				.invoiceRule(billBPartnerEffective.getInvoiceRule(soTrx))
				.paymentRule(getPaymentRule(billBPartnerEffective, soTrx))
				.paymentTermId(billBPartnerEffective.getPaymentTermId(soTrx))
				.pricingSystemId(billBPartnerEffective.getPricingSystemId(soTrx))
				.shipperId(Optional.ofNullable(shipBPartnerEffective.getShipperId())) //FIXME doesn't consider possibility of overwrite in c_bp_location
				.isAutoInvoice(billBPartnerEffective.isAutoInvoice(soTrx))
				.incoterms(shipBPartnerEffective.getIncoterms(soTrx))
				.build();
	}

	private PaymentRule getPaymentRule(@NonNull final BPartnerEffective bpartnerRecord, @NonNull final SOTrx soTrx)
	{
		// note that we fall back to a default because while the column is mandatory in the DB, it might be null in unit tests
		final PaymentRule paymentRule = bpartnerRecord.getPaymentRule(soTrx);
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
}
