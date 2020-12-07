package de.metas.handlingunits.picking.candidate.commands;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.X_C_DocType;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.model.I_PP_Order_BOM;
import org.eevolution.model.X_PP_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.metas.adempiere.model.I_M_Product;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.contracts.flatrate.interfaces.I_C_DocType;
import de.metas.dimension.model.I_DIM_Dimension_Spec;
import de.metas.handlingunits.HUConstants;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.allocation.IHUProducerAllocationDestination;
import de.metas.handlingunits.allocation.impl.AbstractAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.expectations.HUStorageExpectation;
import de.metas.handlingunits.expectations.ShipmentScheduleQtyPickedExpectations;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Locator;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.handlingunits.model.I_PP_Cost_Collector;
import de.metas.handlingunits.model.I_PP_Order;
import de.metas.handlingunits.model.I_PP_Order_BOMLine;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.picking.PickFrom;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.handlingunits.picking.PickingCandidateIssueToBOMLine;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.material.planning.pporder.PPOrderBOMLineId;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

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
public class ProcessPickingCandidatesCommand_PickingOrder_Test
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private HUTestHelper helper;
	private PickingCandidateRepository pickingCandidateRepository;

	private I_C_UOM uomEach;
	private LocatorId shipFromLocatorId;
	private final ResourceId plantId = ResourceId.ofRepoId(666);
	private final BPartnerLocationId shipToBPLocationId = BPartnerLocationId.ofRepoId(666, 666);

	@BeforeEach
	public void init()
	{
		helper = HUTestHelper.newInstanceOutOfTrx();
		pickingCandidateRepository = new PickingCandidateRepository();

		createDocType(X_C_DocType.DOCBASETYPE_ManufacturingCostCollector);
		createDimensionSpec(HUConstants.DIM_PP_Order_ProductAttribute_To_Transfer);

		uomEach = helper.uomEach;
		final WarehouseId warehouseId = createWarehouseId("warehouse");
		shipFromLocatorId = createLocatorId(warehouseId);
	}

	private ProductId createProduct(final String name, final I_C_UOM uom)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setValue(name);
		product.setName(name);
		product.setC_UOM_ID(uom.getC_UOM_ID());
		saveRecord(product);
		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	private WarehouseId createWarehouseId(final String name)
	{
		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		warehouse.setValue(name);
		warehouse.setName(name);
		saveRecord(warehouse);
		return WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID());
	}

	private LocatorId createLocatorId(final WarehouseId warehouseId)
	{
		final I_M_Locator locator = newInstance(I_M_Locator.class);
		locator.setM_Warehouse_ID(warehouseId.getRepoId());
		saveRecord(locator);
		return LocatorId.ofRepoId(warehouseId, locator.getM_Locator_ID());
	}

	private void createDocType(final String docBaseType)
	{
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setName(docBaseType);
		docType.setDocBaseType(docBaseType);
		saveRecord(docType);
	}

	private HuId createVHU(final ProductId productId, final Quantity qty)
	{
		final AbstractAllocationSourceDestination source = helper.createDummySourceDestination(
				productId,
				new BigDecimal("99999"),
				qty.getUOM(),
				true/* fullyLoaded */
		);

		final IHUProducerAllocationDestination destination = HUProducerDestination.ofVirtualPI()
				.setHUStatus(X_M_HU.HUSTATUS_Active)
				.setLocatorId(shipFromLocatorId);

		final boolean forceQtyAllocation = true;
		HULoader.of(source, destination)
				.load(AllocationUtils.createQtyRequest(
						helper.createMutableHUContextOutOfTransaction(),
						productId,
						qty,
						helper.getTodayZonedDateTime(),
						helper.createDummyReferenceModel(),
						forceQtyAllocation));

		final I_M_HU vhu = destination.getCreatedHUs().get(0);
		return HuId.ofRepoId(vhu.getM_HU_ID());
	}

	private I_DIM_Dimension_Spec createDimensionSpec(final String internalName)
	{
		final I_DIM_Dimension_Spec dim = InterfaceWrapperHelper.newInstance(I_DIM_Dimension_Spec.class);
		dim.setInternalName(internalName);
		InterfaceWrapperHelper.save(dim);
		return dim;
	}

	@Builder(builderMethodName = "preparePickingCandidate", buildMethodName = "createAndProcess", builderClassName = "PickingCandidateBuilder")
	private PickingCandidateId createAndProcessPickingCandidate(
			@NonNull final ProductId finishedGoodProductId,
			@NonNull final Quantity finishedGoodQtyToPick,
			@NonNull final ProductId componentProductId,
			@NonNull final Quantity componentQtyToIssue,
			@NonNull final HuId componentPickFromHUId)
	{
		final ShipmentScheduleId shipmentScheduleId;
		{
			final I_M_ShipmentSchedule shipmentSchedule = newInstance(I_M_ShipmentSchedule.class);
			shipmentSchedule.setM_Warehouse_ID(shipFromLocatorId.getWarehouseId().getRepoId());
			shipmentSchedule.setC_BPartner_ID(shipToBPLocationId.getBpartnerId().getRepoId());
			shipmentSchedule.setC_BPartner_Location_ID(shipToBPLocationId.getRepoId());
			shipmentSchedule.setM_Product_ID(finishedGoodProductId.getRepoId());
			saveRecord(shipmentSchedule);
			shipmentScheduleId = ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID());
		}

		final PPOrderId pickingOrderId;
		final PPOrderBOMLineId pickingOrderBOMLineId_chocolate;
		{
			final I_PP_Order pickingOrder = newInstance(I_PP_Order.class);
			pickingOrder.setS_Resource_ID(plantId.getRepoId());
			pickingOrder.setM_Product_ID(finishedGoodProductId.getRepoId());
			pickingOrder.setC_UOM_ID(finishedGoodQtyToPick.getUomId().getRepoId());
			pickingOrder.setM_Warehouse_ID(shipFromLocatorId.getWarehouseId().getRepoId());
			pickingOrder.setM_Locator_ID(shipFromLocatorId.getRepoId());
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
			pickingOrderBOMLine_chocolate.setM_Warehouse_ID(shipFromLocatorId.getWarehouseId().getRepoId());
			pickingOrderBOMLine_chocolate.setM_Locator_ID(shipFromLocatorId.getRepoId());
			saveRecord(pickingOrderBOMLine_chocolate);
			pickingOrderBOMLineId_chocolate = PPOrderBOMLineId.ofRepoId(pickingOrderBOMLine_chocolate.getPP_Order_BOMLine_ID());
		}

		final PickingCandidateId pickingCandidateId;
		{
			final PickingCandidate pickingCandidate = PickingCandidate.builder()
					.shipmentScheduleId(shipmentScheduleId)
					.pickFrom(PickFrom.ofPickingOrderId(pickingOrderId))
					.qtyPicked(finishedGoodQtyToPick)
					.packToInstructionsId(HuPackingInstructionsId.VIRTUAL)
					.issueToPickingOrder(PickingCandidateIssueToBOMLine.builder()
							.issueToOrderBOMLineId(pickingOrderBOMLineId_chocolate)
							.issueFromHUId(componentPickFromHUId)
							.productId(componentProductId)
							.qtyToIssue(componentQtyToIssue)
							.build())
					.build();
			pickingCandidateRepository.save(pickingCandidate);
			pickingCandidateId = pickingCandidate.getId();
		}

		ProcessPickingCandidatesCommand.builder()
				.pickingCandidateRepository(pickingCandidateRepository)
				.pickingCandidateId(pickingCandidateId)
				.build()
				.perform();

		return pickingCandidateId;
	}

	private List<I_PP_Cost_Collector> getCostCollectors(final PickingCandidateId pickingCandidateId)
	{
		return queryBL.createQueryBuilder(I_PP_Cost_Collector.class)
				.addEqualsFilter(I_PP_Cost_Collector.COLUMNNAME_M_Picking_Candidate_ID, pickingCandidateId)
				.orderBy(I_PP_Cost_Collector.COLUMNNAME_PP_Cost_Collector_ID)
				.create()
				.list();
	}

	@Test
	public void pick3_issue6()
	{
		final ProductId christmasBoxProductId = createProduct("christmasBox", uomEach);
		final ProductId chocolateProductId = createProduct("chocolate", uomEach);
		final HuId chocolatesHUId = createVHU(chocolateProductId, Quantity.of(10000, uomEach));

		final PickingCandidateId pickingCandidateId = preparePickingCandidate()
				.finishedGoodProductId(christmasBoxProductId)
				.finishedGoodQtyToPick(Quantity.of(3, uomEach))
				//
				.componentProductId(chocolateProductId)
				.componentQtyToIssue(Quantity.of(6, uomEach))
				.componentPickFromHUId(chocolatesHUId)
				//
				.createAndProcess();

		final PickingCandidate pickingCandidate = pickingCandidateRepository.getById(pickingCandidateId);
		System.out.println("pickingCandidate=" + pickingCandidate);

		final HuId packedToHuId = pickingCandidate.getPackedToHuId();
		assertThat(packedToHuId).isNotNull();

		final List<I_PP_Cost_Collector> costCollectors = getCostCollectors(pickingCandidateId);
		assertThat(costCollectors).hasSize(2);

		//
		// Check the what was received from MO and packed
		{
			HUStorageExpectation.newExpectation()
					.product(christmasBoxProductId)
					.qty(Quantity.of(3, uomEach))
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
					.qty(Quantity.of(10000 - 6, uomEach))
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
