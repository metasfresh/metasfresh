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
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.dimension.DimensionService;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.shipping.MPackageRepository;
import de.metas.shipping.ShipperRepository;
import de.metas.shipping.ShipperTransportationDocSubTypeGuard;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_M_Delivery_Planning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * How {@link DeliveryPlanningService#getBySelection(IQueryFilter)} resolves the loading and delivery
 * addresses of a whole selection.
 * <p>
 * Also pinned: the whole selection costs one load per collaborator - this runs on every grid selection
 * change, so a per-row load would put the round trips back that it exists to remove.
 */
class DeliveryPlanningAddressLoadingTest
{
	private static final int WAREHOUSE_BPARTNER_ID = 100;
	private static final int WAREHOUSE_BPARTNER_LOCATION_ID = 101;
	private static final int RECEIPT_BPARTNER_ID = 200;
	private static final int RECEIPT_BPARTNER_LOCATION_ID = 201;
	private static final int SHIPMENT_BPARTNER_ID = 300;
	private static final int SHIPMENT_BPARTNER_LOCATION_ID = 301;

	private IReceiptScheduleDAO receiptScheduleDAO;
	private IShipmentScheduleBL shipmentScheduleBL;
	private IWarehouseDAO warehouseDAO;

	private DeliveryPlanningRepository deliveryPlanningRepository;
	private DeliveryPlanningAllocRepository deliveryPlanningAllocRepository;
	private DeliveryInstructionRepository deliveryInstructionRepository;
	private DeliveryInstructionService deliveryInstructionService;
	private DeliveryPlanningService deliveryPlanningService;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		// spy the real services, so the loads actually happen against the in-memory records
		// while the call pattern stays countable
		receiptScheduleDAO = Mockito.spy(Services.get(IReceiptScheduleDAO.class));
		Services.registerService(IReceiptScheduleDAO.class, receiptScheduleDAO);
		shipmentScheduleBL = Mockito.spy(Services.get(IShipmentScheduleBL.class));
		Services.registerService(IShipmentScheduleBL.class, shipmentScheduleBL);
		warehouseDAO = Mockito.spy(Services.get(IWarehouseDAO.class));
		Services.registerService(IWarehouseDAO.class, warehouseDAO);

		deliveryPlanningRepository = Mockito.mock(DeliveryPlanningRepository.class);
		deliveryPlanningAllocRepository = Mockito.mock(DeliveryPlanningAllocRepository.class);
		deliveryInstructionRepository = new DeliveryInstructionRepository(Mockito.mock(DimensionService.class));
		deliveryInstructionService = new DeliveryInstructionService(
				deliveryPlanningRepository, deliveryPlanningAllocRepository, deliveryInstructionRepository, new MPackageRepository());
		deliveryPlanningService = new DeliveryPlanningService(
				new ShipperRepository(),
				deliveryPlanningRepository,
				deliveryPlanningAllocRepository,
				deliveryInstructionRepository,
				deliveryInstructionService,
				Mockito.mock(DeliveryStatusColorPaletteService.class),
				Mockito.mock(DimensionService.class),
				Mockito.mock(MeansOfTransportationService.class),
				new ShipperTransportationDocSubTypeGuard());
	}

	// ------------------------------------------------------------------ helpers

	private int createWarehouse()
	{
		final I_M_Warehouse warehouse = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class);
		warehouse.setValue("WH");
		warehouse.setName("WH");
		warehouse.setC_BPartner_ID(WAREHOUSE_BPARTNER_ID);
		warehouse.setC_BPartner_Location_ID(WAREHOUSE_BPARTNER_LOCATION_ID);
		InterfaceWrapperHelper.save(warehouse);
		return warehouse.getM_Warehouse_ID();
	}

	private int createReceiptSchedule()
	{
		final I_M_ReceiptSchedule receiptSchedule = InterfaceWrapperHelper.newInstance(I_M_ReceiptSchedule.class);
		receiptSchedule.setC_BPartner_ID(RECEIPT_BPARTNER_ID);
		receiptSchedule.setC_BPartner_Location_ID(RECEIPT_BPARTNER_LOCATION_ID);
		InterfaceWrapperHelper.save(receiptSchedule);
		return receiptSchedule.getM_ReceiptSchedule_ID();
	}

	private int createShipmentSchedule()
	{
		final I_M_ShipmentSchedule shipmentSchedule = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class);
		shipmentSchedule.setC_BPartner_ID(SHIPMENT_BPARTNER_ID);
		shipmentSchedule.setC_BPartner_Location_ID(SHIPMENT_BPARTNER_LOCATION_ID);
		InterfaceWrapperHelper.save(shipmentSchedule);
		return shipmentSchedule.getM_ShipmentSchedule_ID();
	}

	private I_M_Delivery_Planning createDeliveryPlanning(
			@NonNull final String type,
			final int receiptScheduleId,
			final int shipmentScheduleId,
			final int warehouseId)
	{
		final I_M_Delivery_Planning record = InterfaceWrapperHelper.newInstance(I_M_Delivery_Planning.class);
		record.setTransportDirection(type);
		record.setM_ReceiptSchedule_ID(receiptScheduleId);
		record.setM_ShipmentSchedule_ID(shipmentScheduleId);
		record.setM_Warehouse_ID(warehouseId);
		InterfaceWrapperHelper.save(record);
		return record;
	}

	private DeliveryPlanningList getBySelection(@NonNull final List<I_M_Delivery_Planning> records)
	{
		return getBySelection(records, ImmutableMap.of());
	}

	/**
	 * @param allocatedInstructionIds the instruction each planning sits on - at most one in every case here
	 */
	private DeliveryPlanningList getBySelection(
			@NonNull final List<I_M_Delivery_Planning> records,
			@NonNull final Map<DeliveryPlanningId, ShipperTransportationId> allocatedInstructionIds)
	{
		final ImmutableListMultimap.Builder<DeliveryPlanningId, DeliveryPlanningAlloc> allocations = ImmutableListMultimap.builder();
		allocatedInstructionIds.forEach((deliveryPlanningId, deliveryInstructionId) -> allocations.put(
				deliveryPlanningId,
				DeliveryPlanningAllocTestHelper.allocationTo(deliveryInstructionId)));

		@SuppressWarnings("unchecked") final IQueryFilter<I_M_Delivery_Planning> filter = Mockito.mock(IQueryFilter.class);
		Mockito.doAnswer(invocation -> records.iterator())
				.when(deliveryPlanningRepository).extractDeliveryPlannings(filter);
		Mockito.doReturn(allocations.build())
				.when(deliveryPlanningAllocRepository).getAllocationsByPlanningId(Mockito.any());

		return deliveryPlanningService.getBySelection(filter);
	}

	private static DeliveryPlanningId idOf(@NonNull final I_M_Delivery_Planning record)
	{
		return DeliveryPlanningId.ofRepoId(record.getM_Delivery_Planning_ID());
	}

	private static final BPartnerLocationId WAREHOUSE_LOCATION =
			BPartnerLocationId.ofRepoId(WAREHOUSE_BPARTNER_ID, WAREHOUSE_BPARTNER_LOCATION_ID);
	private static final BPartnerLocationId RECEIPT_LOCATION =
			BPartnerLocationId.ofRepoId(RECEIPT_BPARTNER_ID, RECEIPT_BPARTNER_LOCATION_ID);
	private static final BPartnerLocationId SHIPMENT_LOCATION =
			BPartnerLocationId.ofRepoId(SHIPMENT_BPARTNER_ID, SHIPMENT_BPARTNER_LOCATION_ID);

	// ------------------------------------------------------------------ tests

	@Test
	@DisplayName("Incoming: loading address from the receipt schedule, delivery address from the warehouse")
	void incoming()
	{
		final I_M_Delivery_Planning record = createDeliveryPlanning(
				X_M_Delivery_Planning.TRANSPORTDIRECTION_Incoming,
				createReceiptSchedule(), 0, createWarehouse());

		final DeliveryPlanning deliveryPlanning = getBySelection(ImmutableList.of(record)).stream().findFirst().orElseThrow(AssertionError::new);

		assertThat(deliveryPlanning.getLoadingLocationId()).isEqualTo(RECEIPT_LOCATION);
		assertThat(deliveryPlanning.getDeliveryLocationId()).isEqualTo(WAREHOUSE_LOCATION);
	}

	@Test
	@DisplayName("Outgoing: loading address from the warehouse, delivery address from the shipment schedule")
	void outgoing()
	{
		final I_M_Delivery_Planning record = createDeliveryPlanning(
				X_M_Delivery_Planning.TRANSPORTDIRECTION_Outgoing,
				0, createShipmentSchedule(), createWarehouse());

		final DeliveryPlanning deliveryPlanning = getBySelection(ImmutableList.of(record)).stream().findFirst().orElseThrow(AssertionError::new);

		assertThat(deliveryPlanning.getLoadingLocationId()).isEqualTo(WAREHOUSE_LOCATION);
		assertThat(deliveryPlanning.getDeliveryLocationId()).isEqualTo(SHIPMENT_LOCATION);
	}

	/**
	 * A Dropship planning has a receipt but carries no shipment schedule of its own, so the delivery address
	 * falls back to the warehouse; reading its (unset) shipment schedule would throw.
	 */
	@Test
	@DisplayName("Dropship: loading address from the receipt schedule, delivery address from the warehouse")
	void dropship()
	{
		final I_M_Delivery_Planning record = createDeliveryPlanning(
				X_M_Delivery_Planning.TRANSPORTDIRECTION_Dropship,
				createReceiptSchedule(), 0, createWarehouse());

		final DeliveryPlanning deliveryPlanning = getBySelection(ImmutableList.of(record)).stream().findFirst().orElseThrow(AssertionError::new);

		assertThat(deliveryPlanning.getLoadingLocationId()).isEqualTo(RECEIPT_LOCATION);
		assertThat(deliveryPlanning.getDeliveryLocationId()).isEqualTo(WAREHOUSE_LOCATION);
	}

	@Test
	@DisplayName("an unset source record reads as 'no address' instead of throwing")
	void unresolvableAddressesReadAsNull()
	{
		final I_M_Delivery_Planning record = createDeliveryPlanning(
				X_M_Delivery_Planning.TRANSPORTDIRECTION_Incoming,
				0, 0, 0);

		final DeliveryPlanning deliveryPlanning = getBySelection(ImmutableList.of(record)).stream().findFirst().orElseThrow(AssertionError::new);

		assertThat(deliveryPlanning.getLoadingLocationId()).isNull();
		assertThat(deliveryPlanning.getDeliveryLocationId()).isNull();
	}

	@Test
	@DisplayName("the whole selection costs one load per collaborator, never one per row")
	void loadsEachCollaboratorOnceForTheWholeSelection()
	{
		final int warehouseId = createWarehouse();
		final ImmutableList<I_M_Delivery_Planning> records = ImmutableList.of(
				createDeliveryPlanning(X_M_Delivery_Planning.TRANSPORTDIRECTION_Incoming, createReceiptSchedule(), 0, warehouseId),
				createDeliveryPlanning(X_M_Delivery_Planning.TRANSPORTDIRECTION_Incoming, createReceiptSchedule(), 0, warehouseId),
				createDeliveryPlanning(X_M_Delivery_Planning.TRANSPORTDIRECTION_Outgoing, 0, createShipmentSchedule(), warehouseId),
				createDeliveryPlanning(X_M_Delivery_Planning.TRANSPORTDIRECTION_Outgoing, 0, createShipmentSchedule(), warehouseId),
				createDeliveryPlanning(X_M_Delivery_Planning.TRANSPORTDIRECTION_Dropship, createReceiptSchedule(), 0, warehouseId));

		final DeliveryPlanningList deliveryPlannings = getBySelection(records);

		assertThat(deliveryPlannings.size()).isEqualTo(5);
		assertThat(deliveryPlannings.stream().map(DeliveryPlanning::getLoadingLocationId))
				.containsExactly(RECEIPT_LOCATION, RECEIPT_LOCATION, WAREHOUSE_LOCATION, WAREHOUSE_LOCATION, RECEIPT_LOCATION);
		assertThat(deliveryPlannings.stream().map(DeliveryPlanning::getDeliveryLocationId))
				.containsExactly(WAREHOUSE_LOCATION, WAREHOUSE_LOCATION, SHIPMENT_LOCATION, SHIPMENT_LOCATION, WAREHOUSE_LOCATION);

		Mockito.verify(receiptScheduleDAO, Mockito.times(1)).getByIds(Mockito.any());
		Mockito.verify(receiptScheduleDAO, Mockito.never()).getById(Mockito.any());
		Mockito.verify(shipmentScheduleBL, Mockito.times(1)).getByIds(Mockito.any());
		Mockito.verify(shipmentScheduleBL, Mockito.never()).getById(Mockito.any());
		Mockito.verify(warehouseDAO, Mockito.times(1)).getByIds(Mockito.any());
		Mockito.verify(warehouseDAO, Mockito.never()).getById(Mockito.any());
		Mockito.verify(warehouseDAO, Mockito.never()).getById(Mockito.any(), Mockito.any());
		Mockito.verify(deliveryPlanningAllocRepository, Mockito.times(1)).getAllocationsByPlanningId(Mockito.any());
	}

	@Test
	@DisplayName("allocated means an active allocation names an instruction, not that M_ShipperTransportation_ID is set on the planning")
	void allocationIsTheSourceOfIsAllocated()
	{
		final I_M_Delivery_Planning record = createDeliveryPlanning(
				X_M_Delivery_Planning.TRANSPORTDIRECTION_Incoming,
				createReceiptSchedule(), 0, createWarehouse());
		record.setM_ShipperTransportation_ID(540099);
		InterfaceWrapperHelper.save(record);

		final DeliveryPlanning deliveryPlanning = getBySelection(ImmutableList.of(record)).stream().findFirst().orElseThrow(AssertionError::new);

		assertThat(deliveryPlanning.getAllocations()).isEmpty();
		assertThat(deliveryPlanning.isAllocated()).isFalse();
	}

	@Test
	@DisplayName("the allocation's instruction is the one reported, even when the planning names none")
	void allocationSuppliesTheInstruction()
	{
		final I_M_Delivery_Planning record = createDeliveryPlanning(
				X_M_Delivery_Planning.TRANSPORTDIRECTION_Incoming,
				createReceiptSchedule(), 0, createWarehouse());
		final ShipperTransportationId allocatedTo = ShipperTransportationId.ofRepoId(540021);

		final DeliveryPlanning deliveryPlanning = getBySelection(ImmutableList.of(record), ImmutableMap.of(idOf(record), allocatedTo))
				.stream().findFirst().orElseThrow(AssertionError::new);

		assertThat(deliveryPlanning.getDeliveryInstructionIds()).containsExactly(allocatedTo);
		assertThat(deliveryPlanning.getAllocationCount()).isEqualTo(1);
		assertThat(deliveryPlanning.isAllocated()).isTrue();
	}
}
