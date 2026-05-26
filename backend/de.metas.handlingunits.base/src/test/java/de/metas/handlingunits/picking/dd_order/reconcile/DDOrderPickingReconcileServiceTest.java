package de.metas.handlingunits.picking.dd_order.reconcile;

import de.metas.distribution.ddorder.DDOrderId;
import de.metas.handlingunits.model.I_M_Picking_Job_Line;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.material.planning.ddorder.DistributionNetworkId;
import de.metas.material.planning.ddorder.DistributionNetworkRepository;
import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Warehouse;
import org.eevolution.model.I_DD_NetworkDistribution;
import org.eevolution.model.I_DD_NetworkDistributionLine;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.X_DD_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DDOrderPickingReconcileServiceTest
{
	private DDOrderPickingReconcileService service;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final DDOrderPickingReconcileRepository repository = new DDOrderPickingReconcileRepository(queryBL);
		final DistributionNetworkRepository distributionNetworkRepository = new DistributionNetworkRepository();
		service = new DDOrderPickingReconcileService(repository, distributionNetworkRepository);
	}

	@Test
	void scaffold_compiles()
	{
		// placeholder — actual tests come in T8-T16
	}

	@Test
	void isPickerBusy_returnsTrueWhenPickingJobLineReferencesMovement()
	{
		// create a shipment schedule (simulated by repoId = 100)
		final int shipmentScheduleRepoId = 100;

		// create DD_Order linked to that shipment schedule
		final I_DD_Order ddOrder = newInstance(I_DD_Order.class);
		ddOrder.setM_ShipmentSchedule_ID(shipmentScheduleRepoId);
		save(ddOrder);
		final DDOrderId ddOrderId = DDOrderId.ofRepoId(ddOrder.getDD_Order_ID());

		// create a PickingJobLine referencing the same shipment schedule
		final I_M_Picking_Job_Line pickingJobLine = newInstance(I_M_Picking_Job_Line.class);
		pickingJobLine.setM_ShipmentSchedule_ID(shipmentScheduleRepoId);
		save(pickingJobLine);

		// expect: picker is busy
		assertThat(service.isPickerBusy(ddOrderId)).isTrue();
	}

	@Test
	void isPickerBusy_returnsFalseWhenNoPickingJobLine()
	{
		// create a shipment schedule (simulated by repoId = 200)
		final int shipmentScheduleRepoId = 200;

		// create DD_Order linked to that shipment schedule
		final I_DD_Order ddOrder = newInstance(I_DD_Order.class);
		ddOrder.setM_ShipmentSchedule_ID(shipmentScheduleRepoId);
		save(ddOrder);
		final DDOrderId ddOrderId = DDOrderId.ofRepoId(ddOrder.getDD_Order_ID());

		// no PickingJobLine created for this shipment schedule

		// expect: picker is NOT busy
		assertThat(service.isPickerBusy(ddOrderId)).isFalse();
	}

	@Test
	void isPickerBusy_ignoresInactivePickingJobLine()
	{
		// create a shipment schedule (simulated by repoId = 300)
		final int shipmentScheduleRepoId = 300;

		// create DD_Order linked to that shipment schedule
		final I_DD_Order ddOrder = newInstance(I_DD_Order.class);
		ddOrder.setM_ShipmentSchedule_ID(shipmentScheduleRepoId);
		save(ddOrder);
		final DDOrderId ddOrderId = DDOrderId.ofRepoId(ddOrder.getDD_Order_ID());

		// create an inactive PickingJobLine referencing the same shipment schedule
		final I_M_Picking_Job_Line line = newInstance(I_M_Picking_Job_Line.class);
		line.setM_ShipmentSchedule_ID(shipmentScheduleRepoId);
		line.setIsActive(false);
		save(line);

		// expect: picker is NOT busy (inactive line is ignored)
		assertThat(service.isPickerBusy(ddOrderId)).isFalse();
	}

	// -----------------------------------------------------------------------
	// assertCanChange tests (T9)
	// -----------------------------------------------------------------------

	@Test
	void assertCanChange_doesNothing_whenWarehouseNotPacking()
	{
		// warehouse with IsPackingWarehouse = false (default)
		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		warehouse.setIsPackingWarehouse(false);
		save(warehouse);
		final WarehouseId warehouseId = WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID());

		final I_M_ShipmentSchedule schedule = newInstance(I_M_ShipmentSchedule.class);
		schedule.setM_Warehouse_ID(warehouseId.getRepoId());
		save(schedule);

		// expect: no exception — warehouse is not a packing warehouse
		service.assertCanChange(schedule);
	}

	@Test
	void assertCanChange_doesNothing_whenNoExistingDDOrder()
	{
		// warehouse with IsPackingWarehouse = true
		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		warehouse.setIsPackingWarehouse(true);
		save(warehouse);
		final WarehouseId warehouseId = WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID());

		final I_M_ShipmentSchedule schedule = newInstance(I_M_ShipmentSchedule.class);
		schedule.setM_Warehouse_ID(warehouseId.getRepoId());
		save(schedule);
		final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(schedule.getM_ShipmentSchedule_ID());

		// no DD_Order linked to this schedule

		// expect: no exception — no existing DD_Order
		service.assertCanChange(schedule);
	}

	@Test
	void assertCanChange_doesNothing_whenPickerNotBusy()
	{
		// warehouse with IsPackingWarehouse = true
		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		warehouse.setIsPackingWarehouse(true);
		save(warehouse);
		final WarehouseId warehouseId = WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID());

		final I_M_ShipmentSchedule schedule = newInstance(I_M_ShipmentSchedule.class);
		schedule.setM_Warehouse_ID(warehouseId.getRepoId());
		save(schedule);
		final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(schedule.getM_ShipmentSchedule_ID());

		// create a live DD_Order (not voided) linked to this schedule
		final I_DD_Order ddOrder = newInstance(I_DD_Order.class);
		ddOrder.setM_ShipmentSchedule_ID(scheduleId.getRepoId());
		ddOrder.setDocStatus(X_DD_Order.DOCSTATUS_Completed);
		save(ddOrder);

		// no PickingJobLine => picker is NOT busy

		// expect: no exception
		service.assertCanChange(schedule);
	}

	@Test
	void assertCanChange_throws_whenPickerBusy()
	{
		// warehouse with IsPackingWarehouse = true
		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		warehouse.setIsPackingWarehouse(true);
		save(warehouse);
		final WarehouseId warehouseId = WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID());

		final I_M_ShipmentSchedule schedule = newInstance(I_M_ShipmentSchedule.class);
		schedule.setM_Warehouse_ID(warehouseId.getRepoId());
		save(schedule);
		final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(schedule.getM_ShipmentSchedule_ID());

		// create a live DD_Order (not voided) linked to this schedule
		final I_DD_Order ddOrder = newInstance(I_DD_Order.class);
		ddOrder.setM_ShipmentSchedule_ID(scheduleId.getRepoId());
		ddOrder.setDocStatus(X_DD_Order.DOCSTATUS_Completed);
		save(ddOrder);

		// create a PickingJobLine referencing the same shipment schedule => picker IS busy
		final I_M_Picking_Job_Line pickingJobLine = newInstance(I_M_Picking_Job_Line.class);
		pickingJobLine.setM_ShipmentSchedule_ID(scheduleId.getRepoId());
		save(pickingJobLine);

		// expect: AdempiereException thrown because picker is busy
		assertThatThrownBy(() -> service.assertCanChange(schedule))
				.isInstanceOf(AdempiereException.class);
	}

	// -----------------------------------------------------------------------
	// resolveSourceWarehouse tests (T10)
	// -----------------------------------------------------------------------

	@Test
	void resolveSourceWarehouse_returns_resolvedWarehouse_whenNetworkLineMatches()
	{
		final WarehouseId sourceWarehouseId = WarehouseId.ofRepoId(10);
		final WarehouseId packingWarehouseId = WarehouseId.ofRepoId(20);
		final ProductId productId = ProductId.ofRepoId(30);

		// create distribution network with a line: source → packing
		final I_DD_NetworkDistribution network = newInstance(I_DD_NetworkDistribution.class);
		network.setName("TestNetwork");
		save(network);

		final I_DD_NetworkDistributionLine line = newInstance(I_DD_NetworkDistributionLine.class);
		line.setDD_NetworkDistribution_ID(network.getDD_NetworkDistribution_ID());
		line.setM_WarehouseSource_ID(sourceWarehouseId.getRepoId());
		line.setM_Warehouse_ID(packingWarehouseId.getRepoId());
		line.setM_Shipper_ID(1);
		save(line);

		final DistributionNetworkId networkId = DistributionNetworkId.ofRepoId(network.getDD_NetworkDistribution_ID());

		final java.util.Optional<WarehouseId> result = service.resolveSourceWarehouse(packingWarehouseId, productId, networkId);

		assertThat(result).isPresent();
		assertThat(result.get()).isEqualTo(sourceWarehouseId);
	}

	@Test
	void resolveSourceWarehouse_returnsEmpty_whenNoMatchingLine()
	{
		final WarehouseId packingWarehouseId = WarehouseId.ofRepoId(21);
		final WarehouseId differentWarehouseId = WarehouseId.ofRepoId(22);
		final ProductId productId = ProductId.ofRepoId(31);

		// create distribution network with a line that does NOT target packingWarehouseId
		final I_DD_NetworkDistribution network = newInstance(I_DD_NetworkDistribution.class);
		network.setName("TestNetwork2");
		save(network);

		final I_DD_NetworkDistributionLine line = newInstance(I_DD_NetworkDistributionLine.class);
		line.setDD_NetworkDistribution_ID(network.getDD_NetworkDistribution_ID());
		line.setM_WarehouseSource_ID(WarehouseId.ofRepoId(50).getRepoId());
		line.setM_Warehouse_ID(differentWarehouseId.getRepoId()); // target is NOT packingWarehouseId
		line.setM_Shipper_ID(1);
		save(line);

		final DistributionNetworkId networkId = DistributionNetworkId.ofRepoId(network.getDD_NetworkDistribution_ID());

		final java.util.Optional<WarehouseId> result = service.resolveSourceWarehouse(packingWarehouseId, productId, networkId);

		assertThat(result).isNotPresent();
	}

	@Test
	void resolveSourceWarehouse_returnsEmpty_whenNetworkIsNull()
	{
		final WarehouseId packingWarehouseId = WarehouseId.ofRepoId(23);
		final ProductId productId = ProductId.ofRepoId(32);

		final java.util.Optional<WarehouseId> result = service.resolveSourceWarehouse(packingWarehouseId, productId, null);

		assertThat(result).isNotPresent();
	}
}
