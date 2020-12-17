package de.metas.handlingunits.pporder.api.impl;

import java.util.Collection;

import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.impl.GenericAllocationSourceDestination;
import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order_BOMLine;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.material.planning.pporder.PPOrderBOMLineId;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.Services;

final class CostCollectorCandidateCoProductHUProducer extends AbstractPPOrderReceiptHUProducer
{
	private final transient IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);

	private final I_PP_Order_BOMLine coByProductOrderBOMLine;
	private final ProductId productId;

	public CostCollectorCandidateCoProductHUProducer(
			final org.eevolution.model.I_PP_Order_BOMLine coByProductOrderBOMLine)
	{
		super(PPOrderId.ofRepoId(coByProductOrderBOMLine.getPP_Order_ID()));

		// TODO: validate:
		// * if is a completed PP_Order
		// * if is really a receipt (i.e. it's a co/by product line)
		this.coByProductOrderBOMLine = InterfaceWrapperHelper.create(coByProductOrderBOMLine, I_PP_Order_BOMLine.class);

		productId = ProductId.ofRepoId(coByProductOrderBOMLine.getM_Product_ID());
	}

	private I_PP_Order_BOMLine getCoByProductOrderBOMLine()
	{
		return coByProductOrderBOMLine;
	}

	@Override
	protected ProductId getProductId()
	{
		return productId;
	}

	@Override
	protected Object getAllocationRequestReferencedModel()
	{
		return getCoByProductOrderBOMLine();
	}

	@Override
	protected IAllocationSource createAllocationSource()
	{
		final I_PP_Order_BOMLine coByProductOrderBOMLine = getCoByProductOrderBOMLine();
		final PPOrderBOMLineProductStorage ppOrderBOMLineProductStorage = new PPOrderBOMLineProductStorage(coByProductOrderBOMLine);
		return new GenericAllocationSourceDestination(
				ppOrderBOMLineProductStorage,
				coByProductOrderBOMLine // referenced model
		);
	}

	@Override
	protected IDocumentLUTUConfigurationManager createReceiptLUTUConfigurationManager()
	{
		final I_PP_Order_BOMLine coByProductOrderBOMLine = getCoByProductOrderBOMLine();
		return huPPOrderBL.createReceiptLUTUConfigurationManager(coByProductOrderBOMLine);
	}

	@Override
	protected ReceiptCandidateRequestProducer newReceiptCandidateRequestProducer()
	{
		final I_PP_Order_BOMLine coByProductOrderBOMLine = getCoByProductOrderBOMLine();
		final PPOrderBOMLineId coByProductOrderBOMLineId = PPOrderBOMLineId.ofRepoId(coByProductOrderBOMLine.getPP_Order_BOMLine_ID());
		final PPOrderId orderId = PPOrderId.ofRepoId(coByProductOrderBOMLine.getPP_Order_ID());
		final OrgId orgId = OrgId.ofRepoId(coByProductOrderBOMLine.getAD_Org_ID());

		return ReceiptCandidateRequestProducer.builder()
				.orderId(orderId)
				.coByProductOrderBOMLineId(coByProductOrderBOMLineId)
				.orgId(orgId)
				.date(getMovementDate())
				.locatorId(getLocatorId())
				.pickingCandidateId(getPickingCandidateId())
				.build();
	}

	@Override
	protected void addAssignedHUs(final Collection<I_M_HU> hus)
	{
		final I_PP_Order_BOMLine bomLine = getCoByProductOrderBOMLine();
		huPPOrderBL.addAssignedHandlingUnits(bomLine, hus);
	}
}
