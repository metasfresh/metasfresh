package de.metas.handlingunits.pporder.api.impl;

import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.impl.GenericAllocationSourceDestination;
import de.metas.handlingunits.impl.DocumentLUTUConfigurationManager;
import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;
import de.metas.handlingunits.model.I_PP_Order;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.IHUPPOrderIssueProducer;

public class HUPPOrderBL implements IHUPPOrderBL
{
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
	public IHUPPOrderIssueProducer createIssueProducer()
	{
		return new HUPPOrderIssueProducer();
	}
}
