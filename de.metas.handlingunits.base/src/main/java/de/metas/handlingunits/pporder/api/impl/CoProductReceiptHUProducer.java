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
import org.eevolution.api.IPPOrderBOMBL;
import org.eevolution.model.I_PP_Cost_Collector;

import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.GenericAllocationSourceDestination;
import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order_BOMLine;

/**
 * HU Producer used for creating {@link I_M_HU}s from {@link I_PP_Cost_Collector} which is about co/by-product.
 *
 * @author tsa
 *
 */
/* package */class CoProductReceiptHUProducer extends AbstractPPOrderReceiptHUProducer
{
	private final I_PP_Cost_Collector _costCollector;
	private final I_PP_Order_BOMLine _ppOrderBOMLine;

	public CoProductReceiptHUProducer(final I_PP_Cost_Collector cc)
	{
		super(cc);

		Check.assumeNotNull(cc, "cc not null");
		_costCollector = cc;

		Check.assume(cc.getPP_Order_BOMLine_ID() > 0, "Order BOM Line shall be set to cost collector when receiving co/bu products: {}", cc);
		_ppOrderBOMLine = InterfaceWrapperHelper.create(cc.getPP_Order_BOMLine(), I_PP_Order_BOMLine.class);
		Check.assumeNotNull(_ppOrderBOMLine, "ppOrderBOMLine not null");
	}

	private final I_PP_Cost_Collector getPP_Cost_Collector()
	{
		return _costCollector;
	}

	private final I_PP_Order_BOMLine getPP_Order_BOMLine()
	{
		return _ppOrderBOMLine;
	}

	@Override
	protected IAllocationSource createAllocationSource()
	{
		final I_PP_Order_BOMLine ppOrderBOMLine = getPP_Order_BOMLine();
		final PPOrderBOMLineProductStorage ppOrderBOMLineProductStorage = new PPOrderBOMLineProductStorage(ppOrderBOMLine);
		final IAllocationSource ppOrderAllocationSource = new GenericAllocationSourceDestination(
				ppOrderBOMLineProductStorage,
				ppOrderBOMLine // referenced model
		);
		return ppOrderAllocationSource;
	}

	@Override
	protected IDocumentLUTUConfigurationManager createReceiptLUTUConfigurationManager()
	{
		final I_PP_Order_BOMLine ppOrderBOMLine = getPP_Order_BOMLine();
		return huPPOrderBL.createReceiptLUTUConfigurationManager(ppOrderBOMLine);
	}

	@Override
	protected IAllocationRequest createAllocationRequest(final IHUContext huContext)
	{
		final I_PP_Cost_Collector cc = getPP_Cost_Collector();
		final I_C_UOM uom = cc.getC_UOM();

		final BigDecimal qtyReceived = Services.get(IPPOrderBOMBL.class).adjustCoProductQty(cc.getMovementQty());

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
