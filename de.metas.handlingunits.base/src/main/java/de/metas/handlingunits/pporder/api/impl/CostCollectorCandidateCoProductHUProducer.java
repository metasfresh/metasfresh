package de.metas.handlingunits.pporder.api.impl;

import java.util.Collection;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.impl.GenericAllocationSourceDestination;
import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order_BOMLine;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.material.planning.pporder.PPOrderBOMLineId;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;

public class CostCollectorCandidateCoProductHUProducer extends AbstractPPOrderReceiptHUProducer
{
	private final transient IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);

	private final I_PP_Order_BOMLine _ppOrderBOMLine;
	private final I_M_Product _product;

	public CostCollectorCandidateCoProductHUProducer(final org.eevolution.model.I_PP_Order_BOMLine ppOrderBOMLine)
	{
		super(PPOrderId.ofRepoId(ppOrderBOMLine.getPP_Order_ID()));

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
	protected ReceiptCandidateRequestProducer newReceiptCandidateRequestProducer()
	{
		final I_PP_Order_BOMLine orderBOMLine = getPP_Order_BOMLine();
		final PPOrderBOMLineId orderBOMLineId = PPOrderBOMLineId.ofRepoId(orderBOMLine.getPP_Order_BOMLine_ID());
		final PPOrderId orderId = PPOrderId.ofRepoId(orderBOMLine.getPP_Order_ID());
		final OrgId orgId = OrgId.ofRepoId(orderBOMLine.getAD_Org_ID());

		return ReceiptCandidateRequestProducer.builder()
				.orderId(orderId)
				.orderBOMLineId(orderBOMLineId)
				.orgId(orgId)
				.date(getMovementDate())
				.build();
	}

	@Override
	protected void setAssignedHUs(final Collection<I_M_HU> hus)
	{
		final I_PP_Order_BOMLine bomLine = getPP_Order_BOMLine();
		Services.get(IHUAssignmentBL.class).setAssignedHandlingUnits(bomLine, hus, ITrx.TRXNAME_ThreadInherited);
	}

}
