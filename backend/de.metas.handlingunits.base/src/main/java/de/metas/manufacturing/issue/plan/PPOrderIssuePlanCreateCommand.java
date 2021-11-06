package de.metas.manufacturing.issue.plan;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.picking.plan.generator.pickFromHUs.PickFromHUsSupplier;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.i18n.AdMessageKey;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import java.util.ArrayList;
import java.util.stream.Stream;

public class PPOrderIssuePlanCreateCommand
{
	//
	// Services
	private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	private final IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);

	private static final AdMessageKey MSG_CannotFullAllocate = AdMessageKey.of("PPOrderIssuePlanCreateCommand.CannotFullyAllocated");

	//
	// Params
	private final PPOrderId ppOrderId;

	//
	// State
	private ImmutableSet<LocatorId> orderPickFromLocatorIds;
	private final AllocableHUsMap allocableHUsMap;

	@Builder
	private PPOrderIssuePlanCreateCommand(
			final @NonNull HUReservationService huReservationService,
			//
			final @NonNull PPOrderId ppOrderId)
	{
		this.ppOrderId = ppOrderId;

		this.allocableHUsMap = AllocableHUsMap.builder()
				.storageFactory(Services.get(IHandlingUnitsBL.class).getStorageFactory())
				.uomConverter(Services.get(IUOMConversionBL.class))
				.pickFromHUsSupplier(PickFromHUsSupplier.builder()
						.huReservationService(huReservationService)
						.build())
				.build();
	}

	public PPOrderIssuePlan execute()
	{
		final I_PP_Order order = ppOrderBL.getById(ppOrderId);

		final WarehouseId warehouseId = WarehouseId.ofRepoId(order.getM_Warehouse_ID());
		this.orderPickFromLocatorIds = ImmutableSet.copyOf(warehouseDAO.getLocatorIds(warehouseId));

		final ImmutableList<PPOrderIssuePlanStep> steps = ppOrderBOMBL.retrieveOrderBOMLines(ppOrderId, I_PP_Order_BOMLine.class)
				.stream()
				.flatMap(this::createSteps)
				.collect(ImmutableList.toImmutableList());

		return PPOrderIssuePlan.builder()
				.orderId(ppOrderId)
				.steps(steps)
				.build();
	}

	private Stream<PPOrderIssuePlanStep> createSteps(final I_PP_Order_BOMLine orderBOMLine)
	{
		if (!BOMComponentType.ofCode(orderBOMLine.getComponentType()).isIssue())
		{
			return Stream.of();
		}

		final PPOrderBOMLineId orderBOMLineId = PPOrderBOMLineId.ofRepoId(orderBOMLine.getPP_Order_BOMLine_ID());
		final ProductId productId = ProductId.ofRepoId(orderBOMLine.getM_Product_ID());
		final ImmutableSet<LocatorId> pickFromLocatorIds = getPickFromLocatorIds(orderBOMLine);

		final Quantity targetQty = ppOrderBOMBL.getQuantities(orderBOMLine).getRemainingQtyToIssue();
		Quantity allocatedQty = targetQty.toZero();
		final ArrayList<PPOrderIssuePlanStep> planSteps = new ArrayList<>();

		final AllocableHUsList availableHUs = allocableHUsMap.getAllocableHUs(AllocableHUsGroupingKey.of(productId, pickFromLocatorIds));
		for (final AllocableHU allocableHU : availableHUs)
		{
			final Quantity remainingQtyToAllocate = targetQty.subtract(allocatedQty);
			if (remainingQtyToAllocate.signum() <= 0)
			{
				// if we allocated entire qty, exit
				break;
			}
			else // if (remainingQtyToAllocate.signum() > 0)
			{
				final Quantity huQtyAvailable = allocableHU.getQtyAvailableToAllocate(remainingQtyToAllocate.getUomId());

				final PPOrderIssuePlanStep planStep;
				if (huQtyAvailable.isGreaterThan(remainingQtyToAllocate))
				{
					planStep = PPOrderIssuePlanStep.builder()
							.orderBOMLineId(orderBOMLineId)
							.productId(productId)
							.qtyToIssue(remainingQtyToAllocate)
							.pickFromLocatorId(allocableHU.getLocatorId())
							.pickFromHU(allocableHU.getHu())
							.build();

					allocableHU.addQtyAllocated(remainingQtyToAllocate);
					allocatedQty = targetQty; // fully allocated
				}
				else // huQtyAvailable <= remainingQtyToAllocate
				{
					planStep = PPOrderIssuePlanStep.builder()
							.orderBOMLineId(orderBOMLineId)
							.productId(productId)
							.qtyToIssue(huQtyAvailable)
							//.isPickWholeHU(true)
							.pickFromLocatorId(allocableHU.getLocatorId())
							.pickFromHU(allocableHU.getHu())
							.build();

					allocableHU.addQtyAllocated(huQtyAvailable);
					allocatedQty = allocatedQty.add(huQtyAvailable);
				}

				planSteps.add(planStep);
			}
		}

		final Quantity qtyToAllocate = targetQty.subtract(allocatedQty);
		if (qtyToAllocate.signum() != 0)
		{
			throw new AdempiereException(MSG_CannotFullAllocate)
					.appendParametersToMessage()
					.setParameter("M_Product_ID", productBL.getProductName(productId))
					.setParameter("QtyToIssue", targetQty)
					.setParameter("l", qtyToAllocate);
		}

		return planSteps.stream();
	}

	private ImmutableSet<LocatorId> getPickFromLocatorIds(@NonNull final I_PP_Order_BOMLine orderBOMLine)
	{
		final WarehouseId linePickFromWarehouseId = WarehouseId.ofRepoIdOrNull(orderBOMLine.getM_Warehouse_ID());
		if (linePickFromWarehouseId != null)
		{
			final LocatorId linePickFromLocatorId = LocatorId.ofRepoIdOrNull(linePickFromWarehouseId, orderBOMLine.getM_Locator_ID());
			if (linePickFromLocatorId != null)
			{
				return ImmutableSet.of(linePickFromLocatorId);
			}
			else
			{
				return ImmutableSet.copyOf(warehouseDAO.getLocatorIds(linePickFromWarehouseId));
			}
		}
		else
		{
			return orderPickFromLocatorIds;
		}
	}
}
