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


import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPOrderBOMBL;
import org.eevolution.model.I_PP_Cost_Collector;

import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.impl.GenericAllocationSourceDestination;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.impl.DocumentLUTUConfigurationManager;
import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.IHUPPOrderIssueProducer;
import de.metas.handlingunits.pporder.api.IPPOrderReceiptHUProducer;

public class HUPPOrderBL implements IHUPPOrderBL
{
	@Override
	public List<I_M_HU> createReceiptHandlingUnits(final I_PP_Cost_Collector cc)
	{
		final IPPOrderReceiptHUProducer producer = createReceiptHUProducer(cc);
		final List<I_M_HU> createdHUs = producer.createHUs();
		return createdHUs;
	}

	private IPPOrderReceiptHUProducer createReceiptHUProducer(final I_PP_Cost_Collector cc)
	{
		assertMaterialReceipt(cc);

		final IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);
		final org.eevolution.model.I_PP_Order_BOMLine ppOrderBOMLine = cc.getPP_Order_BOMLine();

		//
		// Case: we are receiving co/by-products from a BOM Order Line
		if (ppOrderBOMLine != null && ppOrderBOMBL.isReceipt(ppOrderBOMLine))
		{
			return new CoProductReceiptHUProducer(cc);
		}
		//
		// Case: we are receiving finished goods from a BOM Order Line
		else
		{
			Check.assume(cc.getPP_Order_BOMLine_ID() <= 0, "No Order BOM Line shall be set to cost collector when receiving finished goods: {0}", cc);
			return new FinishedGoodsReceiptHUProducer(cc);
		}
	}

	private final void assertMaterialReceipt(final I_PP_Cost_Collector cc)
	{
		Check.assumeNotNull(cc, "cc not null");
		final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);
		if (!ppCostCollectorBL.isMaterialReceipt(cc))
		{
			throw new HUException("Cost collector shall be of type Receipt"
					+ "\n @PP_Cost_Collector_ID@: " + cc
					+ "\n @CostCollectorType@: " + cc.getCostCollectorType());
		}
	}

	@Override
	public IDocumentLUTUConfigurationManager createReceiptLUTUConfigurationManager(final org.eevolution.model.I_PP_Order ppOrder)
	{
		final de.metas.handlingunits.model.I_PP_Order documentLine = InterfaceWrapperHelper.create(ppOrder, de.metas.handlingunits.model.I_PP_Order.class);
		return new DocumentLUTUConfigurationManager<>(documentLine, PPOrderDocumentLUTUConfigurationHandler.instance);
	}

	@Override
	public IDocumentLUTUConfigurationManager createReceiptLUTUConfigurationManager(final org.eevolution.model.I_PP_Order_BOMLine ppOrderBOMLine)
	{
		final de.metas.handlingunits.model.I_PP_Order_BOMLine documentLine = InterfaceWrapperHelper.create(ppOrderBOMLine, de.metas.handlingunits.model.I_PP_Order_BOMLine.class);
		return new DocumentLUTUConfigurationManager<>(documentLine, PPOrderBOMLineDocumentLUTUConfigurationHandler.instance);
	}

	@Override
	public IAllocationSource createAllocationSourceForPPOrder(final I_PP_Order ppOrder)
	{
		final PPOrderProductStorage ppOrderProductStorage = new PPOrderProductStorage(ppOrder);
		final IAllocationSource ppOrderAllocationSource = new GenericAllocationSourceDestination(
				ppOrderProductStorage,
				ppOrder // referenced model
		);
		return ppOrderAllocationSource;
	}

	@Override
	public IHUPPOrderIssueProducer createIssueProducer(final Properties ctx)
	{
		return new HUPPOrderIssueProducer(ctx);
	}
}
