package de.metas.handlingunits.picking.dd_order.reconcile;

import de.metas.distribution.ddorder.DDOrderId;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
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
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.eevolution.model.I_DD_NetworkDistribution;
import org.eevolution.model.I_DD_NetworkDistributionLine;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.X_DD_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

class DDOrderPickingReconcileServiceTest
{
	private DDOrderPickingReconcileService service;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		// Stub the document engine: completing a DD_Order just flips its DocStatus to Completed.
		// Full doc-engine completion needs DB infrastructure not available in AdempiereTestHelper unit tests.
		final IDocumentBL documentBL = mock(IDocumentBL.class);
		doAnswer(invocation -> {
			final Object document = invocation.getArgument(0);
			if (document instanceof I_DD_Order)
			{
				final I_DD_Order ddOrder = (I_DD_Order)document;
				ddOrder.setDocStatus(X_DD_Order.DOCSTATUS_Completed);
				save(ddOrder);
			}
			return null;
		}).when(documentBL).processEx(any(), any(), any());
		Services.registerService(IDocumentBL.class, documentBL);

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

		// expect: AdempiereException thrown because picker is busy, with the correct message key
		assertThatThrownBy(() -> service.assertCanChange(schedule))
				.isInstanceOf(AdempiereException.class)
				.extracting(t -> ((AdempiereException) t).getErrorCode())
				.isEqualTo("DDOrderPickingReconcile_PickerBusy");
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

	// -----------------------------------------------------------------------
	// reconcile() relevance gating + action classification tests (T11)
	// -----------------------------------------------------------------------

	@Test
	void reconcile_doesNothing_whenScheduleNotOnPackingWarehouse()
	{
		// warehouse with IsPackingWarehouse = false (default)
		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		warehouse.setIsPackingWarehouse(false);
		save(warehouse);
		final WarehouseId warehouseId = WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID());

		final I_M_ShipmentSchedule schedule = newInstance(I_M_ShipmentSchedule.class);
		schedule.setM_Warehouse_ID(warehouseId.getRepoId());
		schedule.setIsActive(true);
		save(schedule);
		final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(schedule.getM_ShipmentSchedule_ID());

		// count DD_Orders before
		final int ddOrderCountBefore = Services.get(IQueryBL.class)
				.createQueryBuilder(org.eevolution.model.I_DD_Order.class)
				.create()
				.count();

		// reconcile should return normally (no exception) because non-packing → NONE
		service.reconcile(scheduleId);

		// no DD_Order created
		final int ddOrderCountAfter = Services.get(IQueryBL.class)
				.createQueryBuilder(org.eevolution.model.I_DD_Order.class)
				.create()
				.count();
		assertThat(ddOrderCountAfter).isEqualTo(ddOrderCountBefore);
	}

	@Test
	void reconcile_doesNothing_whenScheduleInactive_andNoExistingDDOrder()
	{
		// warehouse with IsPackingWarehouse = true
		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		warehouse.setIsPackingWarehouse(true);
		save(warehouse);
		final WarehouseId warehouseId = WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID());

		// schedule is INACTIVE + no DD_Order => NONE
		final I_M_ShipmentSchedule schedule = newInstance(I_M_ShipmentSchedule.class);
		schedule.setM_Warehouse_ID(warehouseId.getRepoId());
		schedule.setIsActive(false);
		save(schedule);
		final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(schedule.getM_ShipmentSchedule_ID());

		// count DD_Orders before
		final int ddOrderCountBefore = Services.get(IQueryBL.class)
				.createQueryBuilder(org.eevolution.model.I_DD_Order.class)
				.create()
				.count();

		// reconcile should return normally (no exception)
		service.reconcile(scheduleId);

		// no DD_Order created
		final int ddOrderCountAfter = Services.get(IQueryBL.class)
				.createQueryBuilder(org.eevolution.model.I_DD_Order.class)
				.create()
				.count();
		assertThat(ddOrderCountAfter).isEqualTo(ddOrderCountBefore);
	}

	@Test
	void reconcile_returnsActionNONE_whenAlreadyInDesiredState()
	{
		// non-packing warehouse => classifyAction should return NONE
		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		warehouse.setIsPackingWarehouse(false);
		save(warehouse);
		final WarehouseId warehouseId = WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID());

		final I_M_ShipmentSchedule schedule = newInstance(I_M_ShipmentSchedule.class);
		schedule.setM_Warehouse_ID(warehouseId.getRepoId());
		schedule.setIsActive(true);
		save(schedule);

		final DDOrderReconcileAction action = service.classifyAction(schedule);
		assertThat(action).isEqualTo(DDOrderReconcileAction.NONE);
	}

	@Test
	void resolveSourceWarehouse_returns_highestPriority_whenMultipleLinesMatch()
	{
		final WarehouseId sourceA = WarehouseId.ofRepoId(40);  // priorityNo=10 → highest priority
		final WarehouseId sourceB = WarehouseId.ofRepoId(41);  // priorityNo=20 → lower priority
		final WarehouseId packingWarehouseId = WarehouseId.ofRepoId(42);
		final ProductId productId = ProductId.ofRepoId(33);

		// create distribution network with two lines targeting the same packing warehouse
		final I_DD_NetworkDistribution network = newInstance(I_DD_NetworkDistribution.class);
		network.setName("TestNetworkPriority");
		save(network);

		final I_DD_NetworkDistributionLine lineA = newInstance(I_DD_NetworkDistributionLine.class);
		lineA.setDD_NetworkDistribution_ID(network.getDD_NetworkDistribution_ID());
		lineA.setM_WarehouseSource_ID(sourceA.getRepoId());
		lineA.setM_Warehouse_ID(packingWarehouseId.getRepoId());
		lineA.setM_Shipper_ID(1);
		lineA.setPriorityNo(10);
		save(lineA);

		final I_DD_NetworkDistributionLine lineB = newInstance(I_DD_NetworkDistributionLine.class);
		lineB.setDD_NetworkDistribution_ID(network.getDD_NetworkDistribution_ID());
		lineB.setM_WarehouseSource_ID(sourceB.getRepoId());
		lineB.setM_Warehouse_ID(packingWarehouseId.getRepoId());
		lineB.setM_Shipper_ID(1);
		lineB.setPriorityNo(20);
		save(lineB);

		final DistributionNetworkId networkId = DistributionNetworkId.ofRepoId(network.getDD_NetworkDistribution_ID());

		final java.util.Optional<WarehouseId> result = service.resolveSourceWarehouse(packingWarehouseId, productId, networkId);

		// expect: sourceA is returned because priorityNo=10 < 20
		assertThat(result).isPresent();
		assertThat(result.get()).isEqualTo(sourceA);
	}

	// -----------------------------------------------------------------------
	// reconcile() CREATE branch tests (T12)
	// -----------------------------------------------------------------------

	private static WarehouseId createWarehouse(final boolean packing, final int networkRepoId)
	{
		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		warehouse.setIsPackingWarehouse(packing);
		if (networkRepoId > 0)
		{
			warehouse.setDD_NetworkDistribution_ID(networkRepoId);
		}
		save(warehouse);
		final WarehouseId warehouseId = WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID());
		// create a default locator so getOrCreateDefaultLocatorId resolves deterministically
		final I_M_Locator locator = newInstance(I_M_Locator.class);
		locator.setM_Warehouse_ID(warehouseId.getRepoId());
		locator.setIsDefault(true);
		locator.setValue("loc-" + warehouseId.getRepoId());
		save(locator);
		return warehouseId;
	}

	@Test
	void reconcile_creates_completed_DDOrder_for_newSchedule()
	{
		final WarehouseId sourceWarehouseId = createWarehouse(false, 0);
		final ProductId productId = ProductId.ofRepoId(555);

		// distribution network: source → packing
		final I_DD_NetworkDistribution network = newInstance(I_DD_NetworkDistribution.class);
		network.setName("CreateBranchNetwork");
		save(network);

		final WarehouseId packingWarehouseId = createWarehouse(true, network.getDD_NetworkDistribution_ID());

		final I_DD_NetworkDistributionLine line = newInstance(I_DD_NetworkDistributionLine.class);
		line.setDD_NetworkDistribution_ID(network.getDD_NetworkDistribution_ID());
		line.setM_WarehouseSource_ID(sourceWarehouseId.getRepoId());
		line.setM_Warehouse_ID(packingWarehouseId.getRepoId());
		line.setM_Shipper_ID(1);
		save(line);

		// active schedule on the packing warehouse
		final I_M_ShipmentSchedule schedule = newInstance(I_M_ShipmentSchedule.class);
		schedule.setM_Warehouse_ID(packingWarehouseId.getRepoId());
		schedule.setM_Product_ID(productId.getRepoId());
		schedule.setQtyToDeliver(new BigDecimal("17"));
		schedule.setIsActive(true);
		save(schedule);
		final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(schedule.getM_ShipmentSchedule_ID());

		service.reconcile(scheduleId);

		// exactly one DD_Order, completed, linked to the schedule
		final java.util.List<I_DD_Order> ddOrders = Services.get(IQueryBL.class)
				.createQueryBuilder(I_DD_Order.class)
				.addEqualsFilter(I_DD_Order.COLUMNNAME_M_ShipmentSchedule_ID, scheduleId)
				.create()
				.list(I_DD_Order.class);
		assertThat(ddOrders).hasSize(1);

		final I_DD_Order ddOrder = ddOrders.get(0);
		assertThat(ddOrder.getDocStatus()).isEqualTo(X_DD_Order.DOCSTATUS_Completed);
		assertThat(ddOrder.getM_Warehouse_From_ID()).isEqualTo(sourceWarehouseId.getRepoId());
		assertThat(ddOrder.getM_Warehouse_To_ID()).isEqualTo(packingWarehouseId.getRepoId());
		assertThat(ddOrder.getM_ShipmentSchedule_ID()).isEqualTo(scheduleId.getRepoId());

		final java.util.List<I_DD_OrderLine> lines = Services.get(IQueryBL.class)
				.createQueryBuilder(I_DD_OrderLine.class)
				.addEqualsFilter(I_DD_OrderLine.COLUMNNAME_DD_Order_ID, ddOrder.getDD_Order_ID())
				.create()
				.list(I_DD_OrderLine.class);
		assertThat(lines).hasSize(1);

		final I_DD_OrderLine ddOrderLine = lines.get(0);
		assertThat(ddOrderLine.getM_Product_ID()).isEqualTo(productId.getRepoId());
		assertThat(ddOrderLine.getQtyOrdered()).isEqualByComparingTo(new BigDecimal("17"));
		assertThat(ddOrderLine.getM_ShipmentSchedule_ID()).isEqualTo(scheduleId.getRepoId());
	}

	@Test
	void reconcile_marks_eventAsError_whenSourceWarehouseUnresolved()
	{
		// packing warehouse WITH a network, but NO network line targeting it → source unresolvable
		final I_DD_NetworkDistribution network = newInstance(I_DD_NetworkDistribution.class);
		network.setName("EmptyNetwork");
		save(network);

		final WarehouseId packingWarehouseId = createWarehouse(true, network.getDD_NetworkDistribution_ID());

		final I_M_ShipmentSchedule schedule = newInstance(I_M_ShipmentSchedule.class);
		schedule.setM_Warehouse_ID(packingWarehouseId.getRepoId());
		schedule.setM_Product_ID(556);
		schedule.setQtyToDeliver(new BigDecimal("5"));
		schedule.setIsActive(true);
		save(schedule);
		final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(schedule.getM_ShipmentSchedule_ID());

		final int ddOrderCountBefore = Services.get(IQueryBL.class)
				.createQueryBuilder(I_DD_Order.class)
				.create()
				.count();

		assertThatThrownBy(() -> service.reconcile(scheduleId))
				.isInstanceOf(AdempiereException.class)
				.extracting(t -> ((AdempiereException)t).getErrorCode())
				.isEqualTo("DDOrderPickingReconcile_NetworkGap");

		final int ddOrderCountAfter = Services.get(IQueryBL.class)
				.createQueryBuilder(I_DD_Order.class)
				.create()
				.count();
		assertThat(ddOrderCountAfter).isEqualTo(ddOrderCountBefore);
	}
}
