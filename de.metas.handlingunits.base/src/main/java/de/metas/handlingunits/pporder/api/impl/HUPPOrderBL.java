package de.metas.handlingunits.pporder.api.impl;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.X_PP_Order;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMultimap;

import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.impl.GenericAllocationSourceDestination;
import de.metas.handlingunits.impl.DocumentLUTUConfigurationManager;
import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;
import de.metas.handlingunits.model.I_PP_Order;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.pporder.api.HUPPOrderIssueReceiptCandidatesProcessor;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.IHUPPOrderIssueProducer;
import lombok.NonNull;

public class HUPPOrderBL implements IHUPPOrderBL
{
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

	@Override
	public IHUQueryBuilder createHUsAvailableToIssueQuery(@NonNull final I_PP_Order_BOMLine ppOrderBomLine)
	{
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		return handlingUnitsDAO
				.createHUQueryBuilder()

				.addOnlyWithProductId(ppOrderBomLine.getM_Product_ID())
				.addOnlyInWarehouseId(ppOrderBomLine.getM_Warehouse_ID())

				.addHUStatusToInclude(X_M_HU.HUSTATUS_Active)
				.setOnlyTopLevelHUs()
				.onlyNotLocked();
	}

	private static final ImmutableMultimap<String, String> fromPlanningStatus2toPlanningStatusAllowed = ImmutableMultimap.<String, String> builder()
			.put(X_PP_Order.PLANNINGSTATUS_Planning, X_PP_Order.PLANNINGSTATUS_Review)
			.put(X_PP_Order.PLANNINGSTATUS_Planning, X_PP_Order.PLANNINGSTATUS_Complete)
			.put(X_PP_Order.PLANNINGSTATUS_Review, X_PP_Order.PLANNINGSTATUS_Planning)
			.put(X_PP_Order.PLANNINGSTATUS_Review, X_PP_Order.PLANNINGSTATUS_Complete)
			.put(X_PP_Order.PLANNINGSTATUS_Complete, X_PP_Order.PLANNINGSTATUS_Planning) // mainly for testing
			.build();

	@Override
	public boolean canChangePlanningStatus(final String fromPlanningStatus, final String toPlanningStatus)
	{
		return fromPlanningStatus2toPlanningStatusAllowed.get(fromPlanningStatus).contains(toPlanningStatus);
	}

	@Override
	public void processPlanning(String targetPlanningStatus, int ppOrderId)
	{
		Services.get(ITrxManager.class).assertThreadInheritedTrxExists();

		final I_PP_Order ppOrder = InterfaceWrapperHelper.load(ppOrderId, I_PP_Order.class);
		final String planningStatus = ppOrder.getPlanningStatus();
		if (Objects.equal(planningStatus, targetPlanningStatus))
		{
			throw new IllegalStateException("Already " + targetPlanningStatus);
		}
		if (!canChangePlanningStatus(planningStatus, targetPlanningStatus))
		{
			throw new IllegalStateException("Cannot chagen planning status from " + planningStatus + " to " + targetPlanningStatus);
		}

		if (X_PP_Order.PLANNINGSTATUS_Planning.equals(targetPlanningStatus))
		{
			// nothing
		}
		else if (X_PP_Order.PLANNINGSTATUS_Review.equals(targetPlanningStatus))
		{
			// nothing
		}
		else if (X_PP_Order.PLANNINGSTATUS_Complete.equals(targetPlanningStatus))
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
		ppOrder.setPlanningStatus(targetPlanningStatus);
		InterfaceWrapperHelper.save(ppOrder);
	}
}
