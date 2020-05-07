package de.metas.procurement.base.order.interceptor;

import java.math.BigDecimal;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;

import de.metas.logging.LogManager;
import de.metas.procurement.base.balance.IPMMBalanceChangeEventProcessor;
import de.metas.procurement.base.balance.PMMBalanceChangeEvent;
import de.metas.procurement.base.model.I_PMM_PurchaseCandidate;
import de.metas.procurement.base.model.I_PMM_PurchaseCandidate_OrderLine;
import de.metas.procurement.base.order.IPMMPurchaseCandidateBL;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Interceptor(I_PMM_PurchaseCandidate_OrderLine.class)
public class PMM_PurchaseCandidate_OrderLine
{
	private static final Logger logger = LogManager.getLogger(PMM_PurchaseCandidate_OrderLine.class);

	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void onCreate(final I_PMM_PurchaseCandidate_OrderLine alloc)
	{
		//
		// Update candidate: QtyOrdered->QtyToOrder
		{
			final I_PMM_PurchaseCandidate candidate = alloc.getPMM_PurchaseCandidate();
			final BigDecimal qtyOrdered = alloc.getQtyOrdered();
			final BigDecimal qtyOrderedTU = alloc.getQtyOrdered_TU();
			Services.get(IPMMPurchaseCandidateBL.class).addQtyOrderedAndResetQtyToOrder(candidate, qtyOrdered, qtyOrderedTU);
			InterfaceWrapperHelper.save(candidate);
		}

		//
		// Update PMM balance
		Services.get(IPMMBalanceChangeEventProcessor.class).addEvent(createPMMBalanceChangeEvent(alloc, false));
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void onDelete(final I_PMM_PurchaseCandidate_OrderLine alloc)
	{
		//
		// Update candidate: QtyOrdered->QtyToOrder
		{
			final I_PMM_PurchaseCandidate candidate = alloc.getPMM_PurchaseCandidate();
			final BigDecimal qtyOrdered = alloc.getQtyOrdered();
			final BigDecimal qtyOrderedTU = alloc.getQtyOrdered_TU();
			Services.get(IPMMPurchaseCandidateBL.class).subtractQtyOrdered(candidate, qtyOrdered, qtyOrderedTU);
			InterfaceWrapperHelper.save(candidate);
		}

		//
		// Update PMM balance
		Services.get(IPMMBalanceChangeEventProcessor.class).addEvent(createPMMBalanceChangeEvent(alloc, true));
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE //
	, ifColumnsChanged = { I_PMM_PurchaseCandidate_OrderLine.COLUMNNAME_QtyOrdered, I_PMM_PurchaseCandidate_OrderLine.COLUMNNAME_QtyOrdered_TU })
	public void onQtyChanges(final I_PMM_PurchaseCandidate_OrderLine alloc)
	{
		throw new AdempiereException("Changing quantities is not allowed: " + alloc);
	}

	@VisibleForTesting
	public static PMMBalanceChangeEvent createPMMBalanceChangeEvent(final I_PMM_PurchaseCandidate_OrderLine alloc, final boolean isReversal)
	{
		final I_PMM_PurchaseCandidate candidate = alloc.getPMM_PurchaseCandidate();
		final BigDecimal qtyOrdered = isReversal ? alloc.getQtyOrdered().negate() : alloc.getQtyOrdered();
		final BigDecimal qtyOrderedTU = isReversal ? alloc.getQtyOrdered_TU().negate() : alloc.getQtyOrdered_TU();

		final PMMBalanceChangeEvent event = PMMBalanceChangeEvent.builder()
				.setC_BPartner_ID(candidate.getC_BPartner_ID())
				.setM_Product_ID(candidate.getM_Product_ID())
				.setM_AttributeSetInstance_ID(candidate.getM_AttributeSetInstance_ID())
				.setM_HU_PI_Item_Product_ID(Services.get(IPMMPurchaseCandidateBL.class).getM_HU_PI_Item_Product_Effective_ID(candidate))
				.setC_Flatrate_DataEntry_ID(candidate.getC_Flatrate_DataEntry_ID())
				//
				.setDate(candidate.getDatePromised())
				//
				.setQtyOrdered(qtyOrdered, qtyOrderedTU)
				//
				.build();

		logger.trace("Created {} for {}, isReversal={}", event, alloc, isReversal);
		return event;
	}

}
