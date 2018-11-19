package org.eevolution.api.impl;

import java.time.Duration;
import java.time.LocalDateTime;

import org.compiere.model.I_C_UOM;
import org.compiere.util.TimeUtil;
import org.eevolution.api.ActivityControlCreateRequest;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.IPPOrderWorkflowBL;
import org.eevolution.api.IPPOrderWorkflowDAO;
import org.eevolution.api.PPOrderRouting;
import org.eevolution.api.PPOrderRoutingActivity;
import org.eevolution.api.PPOrderRoutingActivityStatus;
import org.eevolution.model.I_PP_Order;

import de.metas.material.planning.WorkingTime;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.material.planning.pporder.PPRoutingId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;
import lombok.NonNull;

public class PPOrderWorkflowBL implements IPPOrderWorkflowBL
{
	@Override
	public void createOrderWorkflow(@NonNull final I_PP_Order ppOrderRecord)
	{
		final PPOrderRouting orderRouting = CreateOrderRoutingCommand.builder()
				.routingId(PPRoutingId.ofRepoId(ppOrderRecord.getAD_Workflow_ID()))
				.ppOrderId(PPOrderId.ofRepoId(ppOrderRecord.getPP_Order_ID()))
				.dateStartSchedule(TimeUtil.asLocalDateTime(ppOrderRecord.getDateStartSchedule()))
				.qtyOrdered(getQtyOrdered(ppOrderRecord))
				.build()
				.execute();

		final IPPOrderWorkflowDAO orderRoutingsRepo = Services.get(IPPOrderWorkflowDAO.class);
		orderRoutingsRepo.save(orderRouting);
	}

	private Quantity getQtyOrdered(final I_PP_Order ppOrderRecord)
	{
		final ProductId mainProductId = ProductId.ofRepoId(ppOrderRecord.getM_Product_ID());
		final I_C_UOM mainProductUOM = Services.get(IProductBL.class).getStockingUOM(mainProductId);
		final Quantity qtyOrdered = Quantity.of(ppOrderRecord.getQtyOrdered(), mainProductUOM);
		return qtyOrdered;
	}

	@Override
	public void closeAllActivities(@NonNull final PPOrderId orderId)
	{
		reportQtyToProcessOnNotStartedActivities(orderId);

		final IPPOrderWorkflowDAO orderRoutingsRepo = Services.get(IPPOrderWorkflowDAO.class);
		final PPOrderRouting orderRouting = orderRoutingsRepo.getByOrderId(orderId);

		for (final PPOrderRoutingActivity activity : orderRouting.getActivities())
		{
			final PPOrderRoutingActivityStatus activityStatus = activity.getStatus();
			if (activityStatus == PPOrderRoutingActivityStatus.IN_PROGRESS
					|| activityStatus == PPOrderRoutingActivityStatus.COMPLETED)
			{
				orderRouting.closeActivity(activity.getId());
			}
		}

		orderRoutingsRepo.save(orderRouting);
	}

	private void reportQtyToProcessOnNotStartedActivities(final PPOrderId orderId)
	{
		final IPPOrderDAO ordersRepo = Services.get(IPPOrderDAO.class);
		final IPPOrderWorkflowDAO orderRoutingsRepo = Services.get(IPPOrderWorkflowDAO.class);
		final IPPCostCollectorBL costCollectorsService = Services.get(IPPCostCollectorBL.class);

		final PPOrderRouting orderRouting = orderRoutingsRepo.getByOrderId(orderId);
		final I_PP_Order orderRecord = ordersRepo.getById(orderId);
		final LocalDateTime reportDate = SystemTime.asLocalDateTime();

		for (final PPOrderRoutingActivity activity : orderRouting.getActivities())
		{
			final PPOrderRoutingActivityStatus activityStatus = activity.getStatus();
			if (activityStatus == PPOrderRoutingActivityStatus.NOT_STARTED)
			{
				final Quantity qtyToProcess = activity.getQtyToDeliver();
				if (qtyToProcess.signum() <= 0)
				{
					// TODO: should we create a negate CC?
					continue;
				}

				final Duration setupTimeRemaining = activity.getSetupTimeRemaining();
				final WorkingTime durationRemaining = WorkingTime.builder()
						.durationPerOneUnit(activity.getDurationPerOneUnit())
						.unitsPerCycle(activity.getUnitsPerCycle())
						.qty(qtyToProcess.getAsBigDecimal())
						.activityTimeUnit(activity.getDurationUnit())
						.build();

				costCollectorsService.createActivityControl(ActivityControlCreateRequest.builder()
						.order(orderRecord)
						.orderActivity(activity)
						.movementDate(reportDate)
						.qtyMoved(qtyToProcess)
						.durationSetup(setupTimeRemaining)
						.duration(durationRemaining.getDuration())
						.build());
			}
		}
	}

}
