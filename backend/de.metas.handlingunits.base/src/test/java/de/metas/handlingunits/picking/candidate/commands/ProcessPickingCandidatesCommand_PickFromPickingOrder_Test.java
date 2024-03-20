package de.metas.handlingunits.picking.candidate.commands;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.expectations.HUStorageExpectation;
import de.metas.handlingunits.expectations.ShipmentScheduleQtyPickedExpectations;
import de.metas.handlingunits.model.I_PP_Cost_Collector;
import de.metas.handlingunits.model.I_PP_Order;
import de.metas.handlingunits.model.I_PP_Order_BOMLine;
import de.metas.handlingunits.picking.PackToSpec;
import de.metas.handlingunits.picking.PickFrom;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.handlingunits.picking.PickingCandidateIssueToBOMLine;
import de.metas.inout.ShipmentScheduleId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestWatcher;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order_BOM;
import org.eevolution.model.X_PP_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@ExtendWith(AdempiereTestWatcher.class)
public class ProcessPickingCandidatesCommand_PickFromPickingOrder_Test
{
	private ProcessPickingCandidatesCommandTestHelper helper;
	private final ResourceId plantId = ResourceId.ofRepoId(666);

	@BeforeEach
	public void init()
	{
		helper = new ProcessPickingCandidatesCommandTestHelper();
	}

	@Builder(builderMethodName = "preparePickingCandidate", buildMethodName = "createAndProcess", builderClassName = "PickingCandidateBuilder")
	private PickingCandidateId createAndProcessPickingCandidate(
			@NonNull final ProductId finishedGoodProductId,
			@NonNull final Quantity finishedGoodQtyToPick,
			@NonNull final ProductId componentProductId,
			@NonNull final Quantity componentQtyToIssue,
			@NonNull final HuId componentPickFromHUId)
	{
		final ShipmentScheduleId shipmentScheduleId = helper.createShipmentSchedule(finishedGoodProductId);

		final PPOrderId pickingOrderId;
		final PPOrderBOMLineId pickingOrderBOMLineId_chocolate;
		{
			final I_PP_Order pickingOrder = newInstance(I_PP_Order.class);
			pickingOrder.setS_Resource_ID(plantId.getRepoId());
			pickingOrder.setM_Product_ID(finishedGoodProductId.getRepoId());
			pickingOrder.setC_UOM_ID(finishedGoodQtyToPick.getUomId().getRepoId());
			pickingOrder.setM_Warehouse_ID(helper.shipFromLocatorId.getWarehouseId().getRepoId());
			pickingOrder.setM_Locator_ID(helper.shipFromLocatorId.getRepoId());
			saveRecord(pickingOrder);
			pickingOrderId = PPOrderId.ofRepoId(pickingOrder.getPP_Order_ID());

			final I_PP_Order_BOM pickingOrderBOM = newInstance(I_PP_Order_BOM.class);
			pickingOrderBOM.setPP_Order_ID(pickingOrderId.getRepoId());
			saveRecord(pickingOrderBOM);

			final I_PP_Order_BOMLine pickingOrderBOMLine_chocolate = newInstance(I_PP_Order_BOMLine.class);
			pickingOrderBOMLine_chocolate.setPP_Order_BOM_ID(pickingOrderBOM.getPP_Order_BOM_ID());
			pickingOrderBOMLine_chocolate.setPP_Order_ID(pickingOrderId.getRepoId());
			pickingOrderBOMLine_chocolate.setComponentType(BOMComponentType.Component.getCode());
			pickingOrderBOMLine_chocolate.setM_Product_ID(componentProductId.getRepoId());
			pickingOrderBOMLine_chocolate.setC_UOM_ID(componentQtyToIssue.getUomId().getRepoId());
			pickingOrderBOMLine_chocolate.setM_Warehouse_ID(helper.shipFromLocatorId.getWarehouseId().getRepoId());
			pickingOrderBOMLine_chocolate.setM_Locator_ID(helper.shipFromLocatorId.getRepoId());
			saveRecord(pickingOrderBOMLine_chocolate);
			pickingOrderBOMLineId_chocolate = PPOrderBOMLineId.ofRepoId(pickingOrderBOMLine_chocolate.getPP_Order_BOMLine_ID());
		}

		final PickingCandidateId pickingCandidateId;
		{
			final PickingCandidate pickingCandidate = PickingCandidate.builder()
					.shipmentScheduleId(shipmentScheduleId)
					.pickFrom(PickFrom.ofPickingOrderId(pickingOrderId))
					.qtyPicked(finishedGoodQtyToPick)
					.packToSpec(PackToSpec.VIRTUAL)
					.issueToPickingOrder(PickingCandidateIssueToBOMLine.builder()
							.issueToOrderBOMLineId(pickingOrderBOMLineId_chocolate)
							.issueFromHUId(componentPickFromHUId)
							.productId(componentProductId)
							.qtyToIssue(componentQtyToIssue)
							.build())
					.build();
			helper.pickingCandidateRepository.save(pickingCandidate);
			pickingCandidateId = pickingCandidate.getId();
		}

		ProcessPickingCandidatesCommand.builder()
				.pickingCandidateRepository(helper.pickingCandidateRepository)
				.inventoryService(helper.inventoryService)
				.request(ProcessPickingCandidatesRequest.builder()
						.pickingCandidateId(pickingCandidateId)
						.build())
				.build()
				.execute();

		return pickingCandidateId;
	}

	@Test
	public void pick3items_issue6items()
	{
		final ProductId christmasBoxProductId = helper.createProduct("christmasBox", helper.uomEach);
		final ProductId chocolateProductId = helper.createProduct("chocolate", helper.uomEach);
		final HuId chocolatesHUId = helper.createVHU(chocolateProductId, Quantity.of(10000, helper.uomEach));

		final PickingCandidateId pickingCandidateId = preparePickingCandidate()
				.finishedGoodProductId(christmasBoxProductId)
				.finishedGoodQtyToPick(Quantity.of(3, helper.uomEach))
				//
				.componentProductId(chocolateProductId)
				.componentQtyToIssue(Quantity.of(6, helper.uomEach))
				.componentPickFromHUId(chocolatesHUId)
				//
				.createAndProcess();

		final PickingCandidate pickingCandidate = helper.pickingCandidateRepository.getById(pickingCandidateId);
		System.out.println("pickingCandidate=" + pickingCandidate);

		final HuId packedToHuId = pickingCandidate.getPackedToHuId();
		assertThat(packedToHuId).isNotNull();

		final List<I_PP_Cost_Collector> costCollectors = helper.getCostCollectors(pickingCandidateId);
		assertThat(costCollectors).hasSize(2);

		//
		// Check the what was received from MO and packed
		{
			HUStorageExpectation.newExpectation()
					.product(christmasBoxProductId)
					.qty(Quantity.of(3, helper.uomEach))
					.assertExpected(packedToHuId);

			final I_PP_Cost_Collector costCollector_FinishedGoodReceipt = costCollectors.get(1);
			assertThat(costCollector_FinishedGoodReceipt.getM_Product_ID()).isEqualTo(christmasBoxProductId.getRepoId());
			assertThat(costCollector_FinishedGoodReceipt.getMovementQty()).isEqualByComparingTo("3");

			ShipmentScheduleQtyPickedExpectations.newInstance()
					.shipmentSchedule(pickingCandidate.getShipmentScheduleId())
					//
					.newShipmentScheduleQtyPickedExpectation()
					.noLU().noTU().vhu(packedToHuId).qtyPicked(3)
					.endExpectation()
					//
					.assertExpected();
		}

		//
		// Checked what was issued to MO
		{
			HUStorageExpectation.newExpectation()
					.product(chocolateProductId)
					.qty(Quantity.of(10000 - 6, helper.uomEach))
					.assertExpected(chocolatesHUId);

			final I_PP_Cost_Collector costCollector_ComponentIssue = costCollectors.get(0);
			assertThat(costCollector_ComponentIssue.getM_Product_ID()).isEqualTo(chocolateProductId.getRepoId());
			assertThat(costCollector_ComponentIssue.getMovementQty()).isEqualByComparingTo("6");
		}

		//
		// Check the MO
		{
			final I_PP_Order pickingOrder = Services.get(IPPOrderDAO.class).getById(pickingCandidate.getPickFrom().getPickingOrderId(), I_PP_Order.class);
			assertThat(pickingOrder.getDocStatus()).isEqualTo(X_PP_Order.DOCSTATUS_Closed);
			assertThat(pickingOrder.getDocAction()).isEqualTo(X_PP_Order.DOCACTION_None);
		}
	}
}
