package de.metas.handlingunits.pporder.api.impl;

import java.util.Collection;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.Services;

public class CostCollectorCandidateFinishedGoodsHUProducer extends AbstractPPOrderReceiptHUProducer
{
	private final transient IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);

	private final I_PP_Order ppOrder;
	private final ProductId productId;

	public CostCollectorCandidateFinishedGoodsHUProducer(final org.eevolution.model.I_PP_Order ppOrder)
	{
		super(PPOrderId.ofRepoId(ppOrder.getPP_Order_ID()));
		// TODO: validate:
		// * if is a completed PP_Order

		this.ppOrder = InterfaceWrapperHelper.create(ppOrder, I_PP_Order.class);
		productId = ProductId.ofRepoId(ppOrder.getM_Product_ID());
	}

	private final I_PP_Order getPP_Order()
	{
		return ppOrder;
	}

	@Override
	protected ProductId getProductId()
	{
		return productId;
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
	protected ReceiptCandidateRequestProducer newReceiptCandidateRequestProducer()
	{
		final I_PP_Order order = getPP_Order();
		final PPOrderId orderId = PPOrderId.ofRepoId(order.getPP_Order_ID());
		final OrgId orgId = OrgId.ofRepoId(order.getAD_Org_ID());

		return ReceiptCandidateRequestProducer.builder()
				.orderId(orderId)
				.orderBOMLineId(null)
				.orgId(orgId)
				.date(getMovementDate())
				.build();
	}

	@Override
	protected void setAssignedHUs(final Collection<I_M_HU> hus)
	{
		final I_PP_Order ppOrder = getPP_Order();

		Services.get(IHUAssignmentBL.class).setAssignedHandlingUnits(ppOrder, hus, ITrx.TRXNAME_ThreadInherited);

	}
}
