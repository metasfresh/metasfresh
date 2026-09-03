/*
 * #%L
 * de.metas.deliveryplanning.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.deliveryplanning;

import com.google.common.collect.ImmutableList;
import de.metas.document.DocBaseType;
import de.metas.document.DocSubType;
import de.metas.document.dimension.DimensionService;
import de.metas.document.engine.DocStatus;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.notification.INotificationBL;
import de.metas.product.ProductId;
import de.metas.shipping.ShipperRepository;
import de.metas.shipping.ShipperTransportationDocSubTypeGuard;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.I_M_Delivery_Planning_Alloc;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_M_Delivery_Planning;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * What a whole-selection action costs in round trips.
 * <p>
 * Combine, Add to and Remove from all run synchronously on a grid selection of up to a hundred rows that the
 * planner is waiting on, and all three read the same delivery-planning records twice over - once to build the
 * allocation requests, once to stamp the {@code ReleaseNo}. Every one of those reads has to be ONE batch load, so
 * each test here asserts {@code times(n)} on the batch method AND pins the per-row one to the fixed number of
 * calls the action may legitimately make - none, except the single seed-header load of {@code combine}.
 */
class DeliveryPlanningBatchLoadingTest
{
	/** Only ever read back as a {@code ProductId}: the planning carries its own UOM, so no product record is needed. */
	private static final int PRODUCT_ID = 540010;

	/** Only ever written to the instruction header and read back as a {@code ShipperId} - no {@code M_Shipper} record is loaded. */
	private static final int SHIPPER_ID = 540001;
	private static final int BPARTNER_ID = 540020;
	private static final int BPARTNER_LOCATION_ID = 540021;

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private DeliveryPlanningRepository deliveryPlanningRepository;
	private DeliveryPlanningService deliveryPlanningService;
	private I_C_UOM uom;

	private I_M_Warehouse loadingWarehouse;
	private I_M_ShipmentSchedule deliveryShipmentSchedule;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		// combine notifies the instruction's creator (CreatedBy, stamped from the logged user)
		Env.setLoggedUserId(Env.getCtx(), UserId.METASFRESH);
		Services.registerService(INotificationBL.class, Mockito.mock(INotificationBL.class));

		deliveryPlanningRepository = Mockito.spy(new DeliveryPlanningRepository(Mockito.mock(DimensionService.class)));
		deliveryPlanningService = new DeliveryPlanningService(
				Mockito.mock(ShipperRepository.class),
				deliveryPlanningRepository,
				Mockito.mock(DeliveryStatusColorPaletteService.class),
				Mockito.mock(DimensionService.class),
				Mockito.mock(MeansOfTransportationService.class),
				new ShipperTransportationDocSubTypeGuard());

		uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		InterfaceWrapperHelper.save(uom);
	}

	// ------------------------------------------------------------------ helpers

	private I_M_Delivery_Planning deliveryPlanning()
	{
		final I_M_Delivery_Planning record = InterfaceWrapperHelper.newInstance(I_M_Delivery_Planning.class);
		record.setTransportDirection(X_M_Delivery_Planning.TRANSPORTDIRECTION_Outgoing);
		record.setM_Product_ID(PRODUCT_ID);
		record.setC_UOM_ID(uom.getC_UOM_ID());
		record.setPlannedLoadedQuantity(BigDecimal.TEN);
		record.setPlannedDischargeQuantity(BigDecimal.ONE);
		InterfaceWrapperHelper.save(record);
		return record;
	}

	/** A planning a whole selection can be combined from: it names the forwarder, and the two records an {@code Outgoing} planning reads its loading and delivery address from. */
	private I_M_Delivery_Planning combinableDeliveryPlanning()
	{
		final I_M_Delivery_Planning record = deliveryPlanning();
		record.setM_Shipper_ID(SHIPPER_ID);
		record.setC_BPartner_ID(BPARTNER_ID);
		record.setC_BPartner_Location_ID(BPARTNER_LOCATION_ID);
		record.setM_Warehouse_ID(loadingWarehouseId());
		record.setM_ShipmentSchedule_ID(deliveryShipmentScheduleId());
		InterfaceWrapperHelper.save(record);
		return record;
	}

	private int loadingWarehouseId()
	{
		if (loadingWarehouse == null)
		{
			loadingWarehouse = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class);
			loadingWarehouse.setValue("WH");
			loadingWarehouse.setName("WH");
			loadingWarehouse.setC_BPartner_ID(BPARTNER_ID);
			loadingWarehouse.setC_BPartner_Location_ID(BPARTNER_LOCATION_ID);
			InterfaceWrapperHelper.save(loadingWarehouse);
		}
		return loadingWarehouse.getM_Warehouse_ID();
	}

	private int deliveryShipmentScheduleId()
	{
		if (deliveryShipmentSchedule == null)
		{
			deliveryShipmentSchedule = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class);
			deliveryShipmentSchedule.setC_BPartner_ID(BPARTNER_ID);
			deliveryShipmentSchedule.setC_BPartner_Location_ID(BPARTNER_LOCATION_ID);
			InterfaceWrapperHelper.save(deliveryShipmentSchedule);
		}
		return deliveryShipmentSchedule.getM_ShipmentSchedule_ID();
	}

	private void createDeliveryInstructionDocType()
	{
		final I_C_DocType docType = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
		docType.setName("Delivery Instruction");
		docType.setDocBaseType(DocBaseType.ShipperTransportation.getCode());
		docType.setDocSubType(DocSubType.DeliveryInstruction.getCode());
		InterfaceWrapperHelper.save(docType);
	}

	private I_M_ShipperTransportation draftDeliveryInstruction(@NonNull final String documentNo)
	{
		final I_M_ShipperTransportation record = InterfaceWrapperHelper.newInstance(I_M_ShipperTransportation.class);
		record.setDocumentNo(documentNo);
		record.setDocStatus(DocStatus.Drafted.getCode());
		InterfaceWrapperHelper.save(record);
		return record;
	}

	/** The selection the process would hand over; {@code extractDeliveryPlannings} is the one seam stubbed here. */
	private IQueryFilter<I_M_Delivery_Planning> selectionOf(@NonNull final List<I_M_Delivery_Planning> records)
	{
		@SuppressWarnings("unchecked") final IQueryFilter<I_M_Delivery_Planning> filter = Mockito.mock(IQueryFilter.class);
		Mockito.doAnswer(invocation -> records.iterator())
				.when(deliveryPlanningRepository).extractDeliveryPlannings(filter);
		return filter;
	}

	private static DeliveryPlanningId idOf(@NonNull final I_M_Delivery_Planning record)
	{
		return DeliveryPlanningId.ofRepoId(record.getM_Delivery_Planning_ID());
	}

	private static I_M_Delivery_Planning reload(@NonNull final I_M_Delivery_Planning record)
	{
		return InterfaceWrapperHelper.load(idOf(record), I_M_Delivery_Planning.class);
	}

	/**
	 * In CREATION order: the allocations are saved one after the other, so their ids follow the order they were
	 * created in - which is what the order assertions read.
	 */
	private List<I_M_Delivery_Planning_Alloc> allocationsInCreationOrder()
	{
		return queryBL.createQueryBuilder(I_M_Delivery_Planning_Alloc.class)
				.orderBy().addColumnAscending(I_M_Delivery_Planning_Alloc.COLUMNNAME_M_Delivery_Planning_Alloc_ID).endOrderBy()
				.create()
				.list();
	}

	/**
	 * One batch load per read of the selection, and not a single per-row one.
	 */
	private void assertBatchLoadedExactly(final int batchLoads)
	{
		assertBatchLoadedExactly(batchLoads, 0);
	}

	/**
	 * As above, for an action that also makes a fixed number of single-row loads: {@code combine} builds the
	 * instruction header from ONE seed planning, and that load neither can be nor needs to be batched.
	 */
	private void assertBatchLoadedExactly(final int batchLoads, final int singleRowLoads)
	{
		Mockito.verify(deliveryPlanningRepository, Mockito.times(batchLoads)).getByIds(Mockito.any());
		Mockito.verify(deliveryPlanningRepository, Mockito.times(singleRowLoads)).getById(Mockito.any());
	}

	// ------------------------------------------------------------------ tests

	@Test
	@DisplayName("combine reads the selection in four batch loads - the only single-row load is the one seed header")
	void combineBatchLoadsTheSelection()
	{
		createDeliveryInstructionDocType();
		final ImmutableList<I_M_Delivery_Planning> records = ImmutableList.of(
				combinableDeliveryPlanning(), combinableDeliveryPlanning(), combinableDeliveryPlanning());
		final IQueryFilter<I_M_Delivery_Planning> selection = selectionOf(records);

		// a draft, which is the default: a combined instruction is assembled over days
		final ShipperTransportationId deliveryInstructionId = deliveryPlanningService.combine(selection, false);

		// one batch for the allocation requests of the plannings behind the seed, one for the ReleaseNo stamping,
		// plus TWO for DeliveredState (Task Q9): createAllocations recomputes ONCE per call, and combine makes
		// two calls here - the seed's (inside generateDeliveryInstruction) and the other two plannings' - not
		// once per planning (that would have been 3, one per row; batched per call it is 2) -
		// plus the single-row load of the ONE seed planning the header is built from
		assertBatchLoadedExactly(4, 1);

		// the batch did not cost the outcome: all three are on the one instruction and carry their own ReleaseNo
		final I_M_ShipperTransportation deliveryInstruction = InterfaceWrapperHelper.load(deliveryInstructionId, I_M_ShipperTransportation.class);
		for (final I_M_Delivery_Planning record : records)
		{
			final I_M_Delivery_Planning stamped = reload(record);
			assertThat(stamped.getM_ShipperTransportation_ID()).isEqualTo(deliveryInstruction.getM_ShipperTransportation_ID());
			// read back rather than hard-coded: unlike the instructions the other tests hand over ready-made, this
			// one is created inside combine, so its DocumentNo is handed out on save and is not known beforehand
			assertThat(stamped.getReleaseNo()).startsWith(deliveryInstruction.getDocumentNo() + "-" + record.getM_Delivery_Planning_ID() + "-");
		}

		// nor the ORDER: the seed is allocated by generateDeliveryInstruction and the rest follow it in the
		// selection's allocation order (DeliveryPlanningList's, not the batch query's encounter order). That the
		// GIVEN order beats the encounter order is pinned by DeliveryPlanningAllocLifecycleTest, which hands
		// createAllocations a request list deliberately out of id order; here the two coincide, so this only
		// shows the pipeline carries that order end to end
		assertThat(allocationsInCreationOrder())
				.extracting(I_M_Delivery_Planning_Alloc::getM_Delivery_Planning_ID)
				.containsExactly(
						records.get(0).getM_Delivery_Planning_ID(),
						records.get(1).getM_Delivery_Planning_ID(),
						records.get(2).getM_Delivery_Planning_ID());
	}

	@Test
	@DisplayName("add-to reads the selection in three batch loads - never one planning at a time")
	void addToBatchLoadsTheSelection()
	{
		final ImmutableList<I_M_Delivery_Planning> records = ImmutableList.of(deliveryPlanning(), deliveryPlanning(), deliveryPlanning());
		final IQueryFilter<I_M_Delivery_Planning> selection = selectionOf(records);
		final I_M_ShipperTransportation target = draftDeliveryInstruction("TARGET-1");

		deliveryPlanningService.addTo(selection, ShipperTransportationId.ofRepoId(target.getM_ShipperTransportation_ID()));

		// one for the allocation requests, one for the ReleaseNo stamping, one for DeliveredState (Task Q9) -
		// createAllocations recomputes ONCE per call, for the whole selection, not once per planning
		assertBatchLoadedExactly(3);

		// the batch did not cost the outcome: every planning is on the target and carries its own ReleaseNo
		for (final I_M_Delivery_Planning record : records)
		{
			final I_M_Delivery_Planning stamped = reload(record);
			assertThat(stamped.getM_ShipperTransportation_ID()).isEqualTo(target.getM_ShipperTransportation_ID());
			assertThat(stamped.getReleaseNo()).startsWith("TARGET-1-" + record.getM_Delivery_Planning_ID() + "-");
		}

		// nor the ORDER: the requests reach createAllocations in the selection's allocation order - see
		// combineBatchLoadsTheSelection on what this assertion does and does not show
		assertThat(allocationsInCreationOrder())
				.extracting(I_M_Delivery_Planning_Alloc::getM_Delivery_Planning_ID)
				.containsExactly(
						records.get(0).getM_Delivery_Planning_ID(),
						records.get(1).getM_Delivery_Planning_ID(),
						records.get(2).getM_Delivery_Planning_ID());
	}

	@Test
	@DisplayName("remove-from reads the selection in one batch load - never one planning at a time")
	void removeFromBatchLoadsTheSelection()
	{
		final ImmutableList<I_M_Delivery_Planning> records = ImmutableList.of(deliveryPlanning(), deliveryPlanning(), deliveryPlanning());
		final IQueryFilter<I_M_Delivery_Planning> selection = selectionOf(records);
		final I_M_ShipperTransportation source = draftDeliveryInstruction("SOURCE-2");
		deliveryPlanningService.addTo(selection, ShipperTransportationId.ofRepoId(source.getM_ShipperTransportation_ID()));

		Mockito.clearInvocations(deliveryPlanningRepository);
		deliveryPlanningService.removeFrom(selection);

		// one batch to recompute the removed plannings' dates from the order and its schedule, one for the
		// ReleaseNo/instruction-reference clearing - each a single round trip for the whole selection, never one
		// per row
		assertBatchLoadedExactly(2);

		for (final I_M_Delivery_Planning record : records)
		{
			final I_M_Delivery_Planning cleared = reload(record);
			assertThat(cleared.getReleaseNo()).isNull();
			assertThat(cleared.getM_ShipperTransportation_ID()).isLessThanOrEqualTo(0);
		}
	}

	@Test
	@DisplayName("stamping from an already-created instruction reads the selection in one batch load")
	void stampingFromAnAlreadyLoadedInstructionBatchLoadsTheSelection()
	{
		// the overload combine() uses: it has just created the instruction, so it already holds the record
		final ImmutableList<I_M_Delivery_Planning> records = ImmutableList.of(deliveryPlanning(), deliveryPlanning(), deliveryPlanning());
		final I_M_ShipperTransportation deliveryInstruction = draftDeliveryInstruction("COMBINED-3");

		deliveryPlanningRepository.updateDeliveryPlanningsFromInstruction(
				records.stream().map(DeliveryPlanningBatchLoadingTest::idOf).collect(ImmutableList.toImmutableList()),
				deliveryInstruction);

		assertBatchLoadedExactly(1);

		for (final I_M_Delivery_Planning record : records)
		{
			assertThat(reload(record).getReleaseNo()).startsWith("COMBINED-3-" + record.getM_Delivery_Planning_ID() + "-");
		}
	}

	@Test
	@DisplayName("a dangling id throws instead of dropping that planning out of the batch")
	void aDanglingIdThrows()
	{
		final I_M_Delivery_Planning present = deliveryPlanning();
		final DeliveryPlanningId dangling = DeliveryPlanningId.ofRepoId(999999);
		final I_M_ShipperTransportation deliveryInstruction = draftDeliveryInstruction("COMBINED-4");

		assertThatThrownBy(() -> deliveryPlanningRepository.updateDeliveryPlanningsFromInstruction(
				ImmutableList.of(idOf(present), dangling),
				deliveryInstruction))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(I_M_Delivery_Planning.Table_Name);

		assertThat(reload(present).getReleaseNo())
				.as("the whole batch is resolved before the first write, so nothing is half-stamped")
				.isNull();
	}

	/**
	 * The half of add-to's all-or-nothing guarantee that a unit test can prove: the rejection is decided for the
	 * WHOLE selection before the first write, so an inadmissible row leaves the admissible ones untouched rather
	 * than half-moved.
	 */
	@Test
	@DisplayName("an inadmissible row in the selection leaves the admissible ones untouched")
	void anInadmissibleRowStopsTheWholeMove()
	{
		final ImmutableList<I_M_Delivery_Planning> records = ImmutableList.of(deliveryPlanning(), deliveryPlanning(), deliveryPlanning());
		final IQueryFilter<I_M_Delivery_Planning> selection = selectionOf(records);

		// one of them is already on a COMPLETED instruction, which neither action may move it off
		final I_M_ShipperTransportation completed = draftDeliveryInstruction("COMPLETED-5");
		completed.setDocStatus(DocStatus.Completed.getCode());
		InterfaceWrapperHelper.save(completed);
		deliveryPlanningRepository.createAllocations(
				ShipperTransportationId.ofRepoId(completed.getM_ShipperTransportation_ID()),
				ImmutableList.of(DeliveryPlanningAllocCreateRequest.builder()
						.deliveryPlanningId(idOf(records.get(1)))
						.shippingPackage(DeliveryPlanningAllocCreateRequest.ShippingPackageData.builder()
								.productId(ProductId.ofRepoId(PRODUCT_ID))
								.uomId(UomId.ofRepoId(uom.getC_UOM_ID()))
								.build())
						.build()));
		final int allocationsBefore = allocationsInCreationOrder().size();

		final I_M_ShipperTransportation target = draftDeliveryInstruction("TARGET-5");
		assertThatThrownBy(() -> deliveryPlanningService.addTo(selection, ShipperTransportationId.ofRepoId(target.getM_ShipperTransportation_ID())))
				.isInstanceOf(AdempiereException.class);

		assertThat(allocationsInCreationOrder())
				.as("nothing was allocated to the target, and nothing was taken off the completed instruction")
				.hasSize(allocationsBefore);
		for (final I_M_Delivery_Planning record : records)
		{
			assertThat(reload(record).getReleaseNo())
					.as("no planning of the selection was re-stamped")
					.isNull();
		}
	}
}
