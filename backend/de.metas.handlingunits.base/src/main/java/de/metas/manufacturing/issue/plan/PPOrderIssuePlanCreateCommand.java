package de.metas.manufacturing.issue.plan;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.picking.plan.generator.pickFromHUs.PickFromHUsSupplier;
import de.metas.handlingunits.pporder.source_hu.PPOrderSourceHUService;
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
import org.adempiere.warehouse.api.IWarehouseBL;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order_BOMLine;

import java.util.ArrayList;
import java.util.stream.Stream;

public class PPOrderIssuePlanCreateCommand
{
	//
	// Services
	private final PPOrderSourceHUService ppOrderSourceHUService;
	private final IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);

	private static final AdMessageKey MSG_CannotFullAllocate = AdMessageKey.of("PPOrderIssuePlanCreateCommand.CannotFullyAllocated");

	//
	// Params
	private final PPOrderId ppOrderId;

	//
	// State
	private final AllocableHUsMap allocableHUsMap;

	@Builder
	private PPOrderIssuePlanCreateCommand(
			final @NonNull PPOrderSourceHUService ppOrderSourceHUService,
			final @NonNull HUReservationService huReservationService,
			//
			final @NonNull PPOrderId ppOrderId)
	{
		this.ppOrderSourceHUService = ppOrderSourceHUService;
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
		allocableHUsMap.addSourceHUs(ppOrderSourceHUService.getSourceHUIds(ppOrderId));

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
		final BOMComponentType bomComponentType = BOMComponentType.optionalOfNullableCode(orderBOMLine.getComponentType()).orElse(BOMComponentType.Component);
		if (!bomComponentType.isIssue())
		{
			return Stream.of();
		}

		final PPOrderBOMLineId orderBOMLineId = PPOrderBOMLineId.ofRepoId(orderBOMLine.getPP_Order_BOMLine_ID());
		final ProductId productId = ProductId.ofRepoId(orderBOMLine.getM_Product_ID());
		final LocatorId pickFromLocatorId = getPickFromLocatorId(orderBOMLine);

		final Quantity targetQty = ppOrderBOMBL.getQuantities(orderBOMLine).getRemainingQtyToIssue();
		Quantity allocatedQty = targetQty.toZero();
		final ArrayList<PPOrderIssuePlanStep> planSteps = new ArrayList<>();
		final AllocableHUsList availableHUs = allocableHUsMap.getAllocableHUs(AllocableHUsGroupingKey.of(productId, pickFromLocatorId));
		for (final AllocableHU allocableHU : availableHUs)
		{
			final Quantity remainingQtyToAllocate = targetQty.subtract(allocatedQty);

			//
			// Case 1: We fully allocated everything,
			// but we are just creating these placeholder lines for possible alternatives
			if (remainingQtyToAllocate.signum() <= 0)
			{
				planSteps.add(PPOrderIssuePlanStep.builder()
						.orderBOMLineId(orderBOMLineId)
						.productId(productId)
						.qtyToIssue(targetQty.toZero())
						.pickFromLocatorId(allocableHU.getLocatorId())
						.pickFromTopLevelHU(allocableHU.getTopLevelHU())
						.isAlternative(true)
						.build());
			}
			else // if (remainingQtyToAllocate.signum() > 0)
			{
				final Quantity huQtyAvailable = allocableHU.getQtyAvailableToAllocate(remainingQtyToAllocate.getUomId());

				//
				// Case 2: Current HU has enough qty to allocate the remaining Qty
				final PPOrderIssuePlanStep planStep;
				if (huQtyAvailable.isGreaterThan(remainingQtyToAllocate))
				{
					planStep = PPOrderIssuePlanStep.builder()
							.orderBOMLineId(orderBOMLineId)
							.productId(productId)
							.qtyToIssue(remainingQtyToAllocate)
							.pickFromLocatorId(allocableHU.getLocatorId())
							.pickFromTopLevelHU(allocableHU.getTopLevelHU())
							.isAlternative(false)
							.build();

					allocableHU.addQtyAllocated(remainingQtyToAllocate);
					allocatedQty = targetQty; // fully allocated
				}
				//
				// Case 3: current HU has not enough qty to allocate the remaining Qty
				else
				{
					planStep = PPOrderIssuePlanStep.builder()
							.orderBOMLineId(orderBOMLineId)
							.productId(productId)
							.qtyToIssue(huQtyAvailable)
							//.isPickWholeHU(true)
							.pickFromLocatorId(allocableHU.getLocatorId())
							.pickFromTopLevelHU(allocableHU.getTopLevelHU())
							.isAlternative(false)
							.build();

					allocableHU.addQtyAllocated(huQtyAvailable);
					allocatedQty = allocatedQty.add(huQtyAvailable);
				}

				planSteps.add(planStep);
			}
		}

		final Quantity qtyToAllocate = targetQty.subtract(allocatedQty);
		if (qtyToAllocate.signum() > 0)
		{
			throw new AdempiereException(MSG_CannotFullAllocate,
					productBL.getProductName(productId),
					warehouseBL.getLocatorNameById(pickFromLocatorId),
					qtyToAllocate,
					targetQty)
					.markAsUserValidationError();
		}

		return planSteps.stream();
	}

	private static LocatorId getPickFromLocatorId(@NonNull final I_PP_Order_BOMLine orderBOMLine)
	{
		return LocatorId.ofRepoId(orderBOMLine.getM_Warehouse_ID(), orderBOMLine.getM_Locator_ID());
	}
}
