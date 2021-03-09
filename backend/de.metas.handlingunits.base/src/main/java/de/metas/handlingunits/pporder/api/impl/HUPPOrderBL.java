package de.metas.handlingunits.pporder.api.impl;

import com.google.common.collect.ImmutableMultimap;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.impl.GenericAllocationSourceDestination;
import de.metas.handlingunits.impl.DocumentLUTUConfigurationManager;
import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.pporder.api.HUPPOrderIssueProducer;
import de.metas.handlingunits.pporder.api.HUPPOrderIssueReceiptCandidatesProcessor;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.IPPOrderReceiptHUProducer;
import de.metas.handlingunits.pporder.api.PPOrderIssueServiceProductRequest;
import de.metas.manufacturing.generatedcomponents.ManufacturingComponentGeneratorService;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.PPOrderPlanningStatus;
import org.eevolution.model.I_PP_Order_BOMLine;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class HUPPOrderBL implements IHUPPOrderBL
{
	private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	private final IPPOrderDAO ppOrderDAO = Services.get(IPPOrderDAO.class);
	private final IHUAssignmentBL huAssignmentBL = Services.get(IHUAssignmentBL.class);
	private final IPPOrderBOMDAO ppOrderBOMsRepo = Services.get(IPPOrderBOMDAO.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	@Override
	public I_PP_Order getById(@NonNull final PPOrderId ppOrderId)
	{
		return ppOrderDAO.getById(ppOrderId, I_PP_Order.class);
	}

	@Override
	public List<I_PP_Order> getByIds(@NonNull final Set<PPOrderId> ppOrderIds)
	{
		return ppOrderDAO.getByIds(ppOrderIds, I_PP_Order.class);
	}

	@Override
	public IDocumentLUTUConfigurationManager createReceiptLUTUConfigurationManager(@NonNull final org.eevolution.model.I_PP_Order ppOrder)
	{
		final de.metas.handlingunits.model.I_PP_Order documentLine = InterfaceWrapperHelper.create(ppOrder, de.metas.handlingunits.model.I_PP_Order.class);
		return new DocumentLUTUConfigurationManager<>(documentLine, PPOrderDocumentLUTUConfigurationHandler.instance);
	}

	@Override
	public IDocumentLUTUConfigurationManager createReceiptLUTUConfigurationManager(@NonNull final org.eevolution.model.I_PP_Order_BOMLine ppOrderBOMLine)
	{
		final de.metas.handlingunits.model.I_PP_Order_BOMLine documentLine = InterfaceWrapperHelper.create(ppOrderBOMLine, de.metas.handlingunits.model.I_PP_Order_BOMLine.class);
		return new DocumentLUTUConfigurationManager<>(documentLine, PPOrderBOMLineDocumentLUTUConfigurationHandler.instance);
	}

	@Override
	public IAllocationSource createAllocationSourceForPPOrder(final I_PP_Order ppOrder)
	{
		final PPOrderProductStorage ppOrderProductStorage = new PPOrderProductStorage(ppOrder);
		return new GenericAllocationSourceDestination(
				ppOrderProductStorage,
				ppOrder // referenced model
		);
	}

	@Override
	public HUPPOrderIssueProducer createIssueProducer(@NonNull final PPOrderId ppOrderId)
	{
		return new HUPPOrderIssueProducer(ppOrderId);
	}

	@Override
	public IPPOrderReceiptHUProducer receivingMainProduct(@NonNull final PPOrderId ppOrderId)
	{
		final I_PP_Order ppOrder = getById(ppOrderId);
		return new CostCollectorCandidateFinishedGoodsHUProducer(ppOrder);
	}

	@Override
	public IPPOrderReceiptHUProducer receivingByOrCoProduct(@NonNull final PPOrderBOMLineId orderBOMLineId)
	{
		final I_PP_Order_BOMLine orderBOMLine = ppOrderBOMsRepo.getOrderBOMLineById(orderBOMLineId);
		return new CostCollectorCandidateCoProductHUProducer(orderBOMLine);
	}

	@Override
	public void issueServiceProduct(final PPOrderIssueServiceProductRequest request)
	{
		final ManufacturingComponentGeneratorService manufacturingComponentGeneratorService = SpringContextHolder.instance.getBean(ManufacturingComponentGeneratorService.class);

		PPOrderIssueServiceProductCommand.builder()
				.manufacturingComponentGeneratorService(manufacturingComponentGeneratorService)
				.request(request)
				.build()
				.execute();
	}

	@Override
	public IHUQueryBuilder createHUsAvailableToIssueQuery(@NonNull final I_PP_Order_BOMLine ppOrderBomLine)
	{
		return handlingUnitsDAO
				.createHUQueryBuilder()
				.addOnlyWithProductId(ProductId.ofRepoId(ppOrderBomLine.getM_Product_ID()))
				.addOnlyInWarehouseId(WarehouseId.ofRepoId(ppOrderBomLine.getM_Warehouse_ID()))
				.addHUStatusToInclude(X_M_HU.HUSTATUS_Active)
				.setExcludeReserved()
				.setOnlyTopLevelHUs()
				.onlyNotLocked();
	}

	private static final ImmutableMultimap<PPOrderPlanningStatus, PPOrderPlanningStatus> fromPlanningStatus2toPlanningStatusAllowed = ImmutableMultimap.<PPOrderPlanningStatus, PPOrderPlanningStatus>builder()
			.put(PPOrderPlanningStatus.PLANNING, PPOrderPlanningStatus.REVIEW)
			.put(PPOrderPlanningStatus.PLANNING, PPOrderPlanningStatus.COMPLETE)
			.put(PPOrderPlanningStatus.REVIEW, PPOrderPlanningStatus.PLANNING)
			.put(PPOrderPlanningStatus.REVIEW, PPOrderPlanningStatus.COMPLETE)
			// .put(PPOrderPlanningStatus.COMPLETE, PPOrderPlanningStatus.PLANNING) // don't allow this transition unless https://github.com/metasfresh/metasfresh/issues/2708 is done
			.build();

	@Override
	public boolean canChangePlanningStatus(final PPOrderPlanningStatus fromPlanningStatus, final PPOrderPlanningStatus toPlanningStatus)
	{
		return fromPlanningStatus2toPlanningStatusAllowed.get(fromPlanningStatus).contains(toPlanningStatus);
	}

	@Override
	public void processPlanning(@NonNull final PPOrderPlanningStatus targetPlanningStatus, @NonNull final PPOrderId ppOrderId)
	{
		trxManager.assertThreadInheritedTrxExists();

		final I_PP_Order ppOrder = getById(ppOrderId);
		final PPOrderPlanningStatus planningStatus = PPOrderPlanningStatus.ofCode(ppOrder.getPlanningStatus());
		if (Objects.equals(planningStatus, targetPlanningStatus))
		{
			throw new IllegalStateException("Already " + targetPlanningStatus);
		}
		if (!canChangePlanningStatus(planningStatus, targetPlanningStatus))
		{
			throw new IllegalStateException("Cannot change planning status from " + planningStatus + " to " + targetPlanningStatus);
		}

		if (PPOrderPlanningStatus.PLANNING.equals(targetPlanningStatus))
		{
			// nothing
		}
		else if (PPOrderPlanningStatus.REVIEW.equals(targetPlanningStatus))
		{
			// nothing
		}
		else if (PPOrderPlanningStatus.COMPLETE.equals(targetPlanningStatus))
		{
			HUPPOrderIssueReceiptCandidatesProcessor.newInstance()
					.setCandidatesToProcessByPPOrderId(ppOrderId)
					.process();
		}
		else
		{
			throw new IllegalArgumentException("Unknown target planning status: " + targetPlanningStatus);
		}

		//
		// Update ppOrder's planning status
		ppOrder.setPlanningStatus(targetPlanningStatus.getCode());
		InterfaceWrapperHelper.save(ppOrder);
	}

	@Override
	public void addAssignedHandlingUnits(@NonNull final I_PP_Order ppOrder, @NonNull final Collection<I_M_HU> hus)
	{
		huAssignmentBL.addAssignedHandlingUnits(ppOrder, hus);
	}

	@Override
	public void addAssignedHandlingUnits(@NonNull final I_PP_Order_BOMLine ppOrderBOMLine, @NonNull final Collection<I_M_HU> hus)
	{
		huAssignmentBL.addAssignedHandlingUnits(ppOrderBOMLine, hus);
	}

	@Override
	public void closeOrder(@NonNull final PPOrderId ppOrderId)
	{
		ppOrderBL.closeOrder(ppOrderId);
	}

}
