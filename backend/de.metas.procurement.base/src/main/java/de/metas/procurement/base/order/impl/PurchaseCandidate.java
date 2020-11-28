package de.metas.procurement.base.order.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util;

import com.google.common.base.MoreObjects;

import de.metas.interfaces.I_C_OrderLine;
import de.metas.procurement.base.model.I_PMM_PurchaseCandidate;
import de.metas.procurement.base.model.I_PMM_PurchaseCandidate_OrderLine;
import de.metas.procurement.base.order.IPMMPurchaseCandidateBL;
import de.metas.util.Check;
import de.metas.util.Services;

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

public final class PurchaseCandidate
{
	public static PurchaseCandidate of(final I_PMM_PurchaseCandidate candidateModel)
	{
		return new PurchaseCandidate(candidateModel);
	}

	private final I_PMM_PurchaseCandidate model;

	private PurchaseCandidate(final I_PMM_PurchaseCandidate candidateModel)
	{
		super();
		model = candidateModel;
	}

	public int getAD_Org_ID()
	{
		return model.getAD_Org_ID();
	}

	public int getM_Warehouse_ID()
	{
		return model.getM_Warehouse_ID();
	}

	public int getC_BPartner_ID()
	{
		return model.getC_BPartner_ID();
	}

	public int getC_Flatrate_DataEntry_ID()
	{
		return model.getC_Flatrate_DataEntry_ID();
	}

	public int getM_PricingSystem_ID()
	{
		return model.getM_PricingSystem_ID();
	}

	public int getM_PriceList_ID()
	{
		return model.getM_PriceList_ID();
	}

	public int getC_Currency_ID()
	{
		return model.getC_Currency_ID();
	}

	public BigDecimal getPrice()
	{
		final BigDecimal price = InterfaceWrapperHelper.getValueOverrideOrValue(model, I_PMM_PurchaseCandidate.COLUMNNAME_Price);
		return MoreObjects.firstNonNull(price, BigDecimal.ZERO);
	}

	public int getM_Product_ID()
	{
		return model.getM_Product_ID();
	}

	public AttributeSetInstanceId getAttributeSetInstanceId()
	{
		return AttributeSetInstanceId.ofRepoIdOrNone(model.getM_AttributeSetInstance_ID());
	}

	public int getC_UOM_ID()
	{
		return model.getC_UOM_ID();
	}

	public int getM_HU_PI_Item_Product_ID()
	{
		return Services.get(IPMMPurchaseCandidateBL.class).getM_HU_PI_Item_Product_Effective_ID(model);
	}

	public BigDecimal getQtyToOrder()
	{
		return model.getQtyToOrder();
	}

	public BigDecimal getQtyToOrder_TU()
	{
		return model.getQtyToOrder_TU();
	}

	public boolean isZeroQty()
	{
		return getQtyToOrder().signum() == 0
				&& getQtyToOrder_TU().signum() == 0;
	}

	/**
	 *
	 * @return the <code>model</code>'s date promised value, truncated to "day".
	 */
	public Timestamp getDatePromised()
	{
		return TimeUtil.trunc(model.getDatePromised(), TimeUtil.TRUNC_DAY);
	}

	public Object getHeaderAggregationKey()
	{
		// the pricelist is no aggregation criterion, because
		// the orderline's price is manually set, i.e. the pricing system is not invoked
		// and we often want to combine candidates with C_Flatrate_Terms (-> no pricelist, price take from the term)
		// and candidates without a term, where the candidate's price is computed by the pricing system
		return Util.mkKey(getAD_Org_ID(),
				getM_Warehouse_ID(),
				getC_BPartner_ID(),
				getDatePromised().getTime(),
				getM_PricingSystem_ID(),
				// getM_PriceList_ID(),
				getC_Currency_ID());
	}

	/**
	 * This method is actually used by the item aggregation key builder of {@link OrderLinesAggregator}.
	 * 
	 * @return
	 */
	public Object getLineAggregationKey()
	{
		return Util.mkKey(
				getM_Product_ID(),
				getAttributeSetInstanceId().getRepoId(),
				getC_UOM_ID(),
				getM_HU_PI_Item_Product_ID(),
				getPrice());
	}

	/**
	 * Creates and {@link I_PMM_PurchaseCandidate} to {@link I_C_OrderLine} line allocation using the whole candidate's QtyToOrder.
	 *
	 * @param orderLine
	 */
	/* package */void createAllocation(final I_C_OrderLine orderLine)
	{
		Check.assumeNotNull(orderLine, "orderLine not null");

		final BigDecimal qtyToOrder = getQtyToOrder();
		final BigDecimal qtyToOrderTU = getQtyToOrder_TU();

		//
		// Create allocation
		final I_PMM_PurchaseCandidate_OrderLine alloc = InterfaceWrapperHelper.newInstance(I_PMM_PurchaseCandidate_OrderLine.class, orderLine);
		alloc.setC_OrderLine(orderLine);
		alloc.setPMM_PurchaseCandidate(model);
		alloc.setQtyOrdered(qtyToOrder);
		alloc.setQtyOrdered_TU(qtyToOrderTU);
		InterfaceWrapperHelper.save(alloc);

		// NOTE: on alloc's save we expect the model's quantities to be updated
		InterfaceWrapperHelper.markStaled(model); // FIXME: workaround because we are modifying the model from alloc's model interceptor
	}
}