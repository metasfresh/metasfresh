package de.metas.handlingunits.pporder.api.impl;

import java.util.Collection;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Product;

import com.google.common.base.Preconditions;

import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;

public class CostCollectorCandidateFinishedGoodsHUProducer extends AbstractPPOrderReceiptHUProducer
{
	private final transient IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);
	
	private final I_PP_Order _ppOrder;
	private final I_M_Product _product;

	public CostCollectorCandidateFinishedGoodsHUProducer(final org.eevolution.model.I_PP_Order ppOrder)
	{
		super(Preconditions.checkNotNull(ppOrder).getPP_Order_ID());
		// TODO: validate:
		// * if is a completed PP_Order
		
		_ppOrder = InterfaceWrapperHelper.create(ppOrder, I_PP_Order.class);

		_product = ppOrder.getM_Product();
		Preconditions.checkNotNull(_product); // shall not happen
	}

	private final I_PP_Order getPP_Order()
	{
		return _ppOrder;
	}

	@Override
	protected I_M_Product getM_Product()
	{
		return _product;
	}

	@Override
	protected Object getAllocationRequestReferencedModel()
	{
		return getPP_Order();
	}

	@Override
	protected IAllocationSource createAllocationSource()
	{
		final de.metas.handlingunits.model.I_PP_Order ppOrder = InterfaceWrapperHelper.create(getPP_Order(), de.metas.handlingunits.model.I_PP_Order.class);

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
	protected I_PP_Order_Qty newCandidate()
	{
		final I_PP_Order_Qty ppOrderQty = InterfaceWrapperHelper.newInstance(I_PP_Order_Qty.class);
		
		final I_PP_Order ppOrder = getPP_Order();
		ppOrderQty.setAD_Org_ID(ppOrder.getAD_Org_ID());
		ppOrderQty.setPP_Order(ppOrder);
		ppOrderQty.setPP_Order_BOMLine(null);
		
		return ppOrderQty;
	}

	@Override
	protected void setAssignedHUs(final Collection<I_M_HU> hus)
	{
		final I_PP_Order ppOrder = getPP_Order();

		Services.get(IHUAssignmentBL.class).setAssignedHandlingUnits(ppOrder, hus, ITrx.TRXNAME_ThreadInherited);

	}
}
