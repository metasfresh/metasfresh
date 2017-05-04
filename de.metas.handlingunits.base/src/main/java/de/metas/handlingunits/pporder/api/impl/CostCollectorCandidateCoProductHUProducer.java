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
import org.eevolution.api.IReceiptCostCollectorCandidate;
import org.eevolution.model.I_PP_Order;

import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.GenericAllocationSourceDestination;
import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order_BOMLine;

public class CostCollectorCandidateCoProductHUProducer extends AbstractPPOrderReceiptHUProducer
{
	private final IReceiptCostCollectorCandidate _costCollectorCandidate;
	private final I_PP_Order_BOMLine _ppOrderBOMLine;

	public CostCollectorCandidateCoProductHUProducer(final IReceiptCostCollectorCandidate cand)
	{
		super(cand);

		Check.assumeNotNull(cand, "Cost collector candidate not null");

		_costCollectorCandidate = cand;

		Check.assumeNotNull(cand.getPP_Order_BOMLine(), "Order BOM Line shall be set to cost collector  candidate when receiving co/bu products: {}", cand);
		_ppOrderBOMLine = InterfaceWrapperHelper.create(cand.getPP_Order_BOMLine(), I_PP_Order_BOMLine.class);
		Check.assumeNotNull(_ppOrderBOMLine, "ppOrderBOMLine not null");
	}

	protected final I_PP_Order_BOMLine getPP_Order_BOMLine()
	{
		return _ppOrderBOMLine;
	}

	protected final IReceiptCostCollectorCandidate getReceiptCostCollectorCandidate()
	{
		return _costCollectorCandidate;
	}

	protected final I_PP_Order getPPOrder()
	{
		return _costCollectorCandidate.getPP_Order();
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
		final IReceiptCostCollectorCandidate candidate = getReceiptCostCollectorCandidate();
		final I_C_UOM uom = candidate.getC_UOM();
		final BigDecimal qtyReceived = candidate.getQtyToReceive();
		final Timestamp date = candidate.getMovementDate();
		final I_M_Product product = candidate.getM_Product();

		final IAllocationRequest allocationRequest = AllocationUtils.createQtyRequest(huContext,
				product, // product
				qtyReceived, // the quantity we received
				uom,
				date, // transaction date
				getPP_Order_BOMLine() // referenced model
				);

		return allocationRequest;
	}

	@Override
	protected void setAssignedHUs(final Collection<I_M_HU> hus, final String trxName)
	{
		final I_PP_Order_BOMLine bomLine = getPP_Order_BOMLine();
		Services.get(IHUAssignmentBL.class).setAssignedHandlingUnits(bomLine, hus, trxName);
	}

}
