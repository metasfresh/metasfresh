package de.metas.procurement.base.order.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util;

import de.metas.interfaces.I_C_OrderLine;
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

public final class PurchaseCandidate
{
	public static final PurchaseCandidate of(final I_PMM_PurchaseCandidate candidateModel)
	{
		return new PurchaseCandidate(candidateModel);
	}

	private final transient IPMMPurchaseCandidateBL purchaseCandidateBL = Services.get(IPMMPurchaseCandidateBL.class);

	private final I_PMM_PurchaseCandidate model;

	private PurchaseCandidate(I_PMM_PurchaseCandidate candidateModel)
	{
		super();
		this.model = candidateModel;
	}

	public int getAD_Org_ID()
	{
		return model.getAD_Org_ID();
	}

	public int getM_Warehouse_ID()
	{
		return model.getM_Warehouse_ID();
	}

	public I_C_BPartner getC_BPartner()
	{
		return model.getC_BPartner();
	}

	private int getC_BPartner_ID()
	{
		return model.getC_BPartner_ID();
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
		return model.getPrice();
	}

	public I_M_Product getM_Product()
	{
		return model.getM_Product();
	}

	private final int getM_Product_ID()
	{
		return model.getM_Product_ID();
	}

	public I_C_UOM getC_UOM()
	{
		return model.getC_UOM();
	}

	private int getC_UOM_ID()
	{
		return model.getC_UOM_ID();
	}

	public int getM_HU_PI_Item_Product_ID()
	{
		return model.getM_HU_PI_Item_Product_ID();
	}

	public BigDecimal getQtyToOrder()
	{
		return model.getQtyToOrder();
	}

	public Timestamp getDatePromised()
	{
		return TimeUtil.trunc(model.getDatePromised(), TimeUtil.TRUNC_DAY);
	}

	public final Object getHeaderAggregationKey()
	{
		return Util.mkKey(
				getAD_Org_ID()
				, getM_Warehouse_ID()
				, getC_BPartner_ID()
				, getDatePromised().getTime()
				, getM_PricingSystem_ID()
				, getM_PriceList_ID()
				, getC_Currency_ID()
				);
	}

	public final Object getLineAggregationKey()
	{
		return Util.mkKey(
				getDatePromised().getTime()
				, getM_Product_ID()
				, getC_UOM_ID()
				, getM_HU_PI_Item_Product_ID()
				, getPrice()
				);
	}

	public void createAllocation(final I_C_OrderLine orderLine)
	{
		final BigDecimal qtyToOrder = getQtyToOrder();

		//
		// Create allocation
		{
			final I_PMM_PurchaseCandidate_OrderLine alloc = InterfaceWrapperHelper.newInstance(I_PMM_PurchaseCandidate_OrderLine.class, orderLine);
			alloc.setC_OrderLine(orderLine);
			alloc.setPMM_PurchaseCandidate(model);
			alloc.setQtyOrdered(qtyToOrder);
			InterfaceWrapperHelper.save(alloc);
		}

		//
		// Update candidate's quantities
		{
			model.setQtyOrdered(model.getQtyOrdered().add(qtyToOrder));

			final BigDecimal qtyToOrderNew = purchaseCandidateBL.calculateQtyToOrder(model);
			model.setQtyToOrder(qtyToOrderNew);

			InterfaceWrapperHelper.save(model);
		}
	}
}