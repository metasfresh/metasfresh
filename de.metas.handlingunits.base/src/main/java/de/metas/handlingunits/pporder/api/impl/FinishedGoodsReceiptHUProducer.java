package de.metas.handlingunits.pporder.api.impl;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_Cost_Collector;

import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order;

/**
 * HU Producer used for creating {@link I_M_HU}s from {@link I_PP_Cost_Collector} which is about main product (i.e. finish good).
 *
 * @author tsa
 *
 */
/* package */class FinishedGoodsReceiptHUProducer extends AbstractPPOrderReceiptHUProducer
{
	private final I_PP_Cost_Collector _costCollector;
	private final I_PP_Order _ppOrder;

	public FinishedGoodsReceiptHUProducer(final I_PP_Cost_Collector cc)
	{
		super(cc);

		Check.assumeNotNull(cc, "cc not null");
		Check.assume(cc.getPP_Order_BOMLine_ID() <= 0, "No Order BOM Line shall be set to cost collector when receiving finished goods: {}", cc);
		_costCollector = cc;

		_ppOrder = InterfaceWrapperHelper.create(cc.getPP_Order(), I_PP_Order.class);
	}

	private final I_PP_Cost_Collector getPP_Cost_Collector()
	{
		return _costCollector;
	}

	protected final I_PP_Order getPP_Order()
	{
		return _ppOrder;
	}

	@Override
	protected final IAllocationSource createAllocationSource()
	{
		final I_PP_Order ppOrder = getPP_Order();

		final IAllocationSource allocationSource = huPPOrderBL.createAllocationSourceForPPOrder(ppOrder);

		return allocationSource;
	}

	@Override
	protected IDocumentLUTUConfigurationManager createReceiptLUTUConfigurationManager()
	{
		final I_PP_Order ppOrder = getPP_Order();
		return huPPOrderBL.createReceiptLUTUConfigurationManager(ppOrder);
	}

	@Override
	protected IAllocationRequest createAllocationRequest(final IHUContext huContext)
	{
		final I_PP_Cost_Collector cc = getPP_Cost_Collector();
		final I_C_UOM uom = cc.getC_UOM();
		final BigDecimal qtyReceived = cc.getMovementQty();
		final Timestamp date = cc.getMovementDate();
		final I_M_Product product = cc.getM_Product();

		final IAllocationRequest allocationRequest = AllocationUtils.createQtyRequest(huContext,
				product, // product
				qtyReceived, // the quantity we received
				uom,
				date, // transaction date
				cc.getPP_Order() // referenced model
				);

		return allocationRequest;
	}

	@Override
	protected void setAssignedHUs(final Collection<I_M_HU> hus, final String trxName)
	{
		final I_PP_Cost_Collector cc = getPP_Cost_Collector();
		Services.get(IHUAssignmentBL.class).setAssignedHandlingUnits(cc, hus, trxName);

	}

}
