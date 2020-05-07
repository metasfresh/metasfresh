package de.metas.handlingunits.pporder.api.impl;

import java.util.Collection;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Product;

import com.google.common.base.Preconditions;

import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.impl.GenericAllocationSourceDestination;
import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order_BOMLine;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;

public class CostCollectorCandidateCoProductHUProducer extends AbstractPPOrderReceiptHUProducer
{
	private final transient IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);
	
	private final I_PP_Order_BOMLine _ppOrderBOMLine;
	private final I_M_Product _product;

	public CostCollectorCandidateCoProductHUProducer(final org.eevolution.model.I_PP_Order_BOMLine ppOrderBOMLine)
	{
		super(Preconditions.checkNotNull(ppOrderBOMLine, "ppOrderBOMLine not null").getPP_Order_ID());
		
		// TODO: validate:
		// * if is a completed PP_Order
		// * if is really a receipt (i.e. it's a co/by product line)
		_ppOrderBOMLine = InterfaceWrapperHelper.create(ppOrderBOMLine, I_PP_Order_BOMLine.class);

		_product = ppOrderBOMLine.getM_Product();
		Check.assumeNotNull(_product, "Parameter product is not null");
	}

	private final I_PP_Order_BOMLine getPP_Order_BOMLine()
	{
		return _ppOrderBOMLine;
	}

	@Override
	protected I_M_Product getM_Product()
	{
		return _product;
	}

	@Override
	protected Object getAllocationRequestReferencedModel()
	{
		return getPP_Order_BOMLine();
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
	protected I_PP_Order_Qty newCandidate()
	{
		final I_PP_Order_Qty ppOrderQty = InterfaceWrapperHelper.newInstance(I_PP_Order_Qty.class);
		
		final I_PP_Order_BOMLine ppOrderBOMLine = getPP_Order_BOMLine();
		final org.eevolution.model.I_PP_Order ppOrder = ppOrderBOMLine.getPP_Order();
		ppOrderQty.setAD_Org_ID(ppOrderBOMLine.getAD_Org_ID());
		ppOrderQty.setPP_Order(ppOrder);
		ppOrderQty.setPP_Order_BOMLine(ppOrderBOMLine);
		
		return ppOrderQty;
	}


	@Override
	protected void setAssignedHUs(final Collection<I_M_HU> hus)
	{
		final I_PP_Order_BOMLine bomLine = getPP_Order_BOMLine();
		Services.get(IHUAssignmentBL.class).setAssignedHandlingUnits(bomLine, hus, ITrx.TRXNAME_ThreadInherited);
	}

}
