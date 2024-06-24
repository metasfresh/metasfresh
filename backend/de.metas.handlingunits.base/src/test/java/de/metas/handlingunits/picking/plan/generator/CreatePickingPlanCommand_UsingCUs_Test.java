package de.metas.handlingunits.picking.plan.generator;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.picking.plan.model.PickingPlan;
import de.metas.handlingunits.reservation.HUReservationDocRef;
import de.metas.handlingunits.reservation.HUReservationRepository;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.reservation.ReserveHUsRequest;
import de.metas.inout.ShipmentScheduleId;
import de.metas.order.OrderAndLineId;
import de.metas.organization.OrgId;
import de.metas.picking.api.Packageable;
import de.metas.picking.api.PackageableList;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.test.SnapshotFunctionFactory;
import de.metas.user.UserRepository;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Warehouse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;

@ExtendWith(AdempiereTestWatcher.class)
class CreatePickingPlanCommand_UsingCUs_Test
{
	//
	// Services
	HUTestHelper helper;
	BPartnerBL bpartnersService;
	HUReservationService huReservationService;
	PickingCandidateRepository pickingCandidateRepository;

	//
	// Master data
	I_C_UOM uomKg;
	ProductId productId;
	private LocatorId wh1_loc1;
	private final BPartnerLocationId customerLocationId = BPartnerLocationId.ofRepoId(3, 4);
	private final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(2);
	private final OrderAndLineId salesOrderAndLineId = OrderAndLineId.ofRepoIds(300, 301);

	@BeforeAll
	static void beforeAll() {start(AdempiereTestHelper.SNAPSHOT_CONFIG, SnapshotFunctionFactory.newFunction());}

	@BeforeEach
	void beforeEach()
	{
		helper = HUTestHelper.newInstanceOutOfTrx();
		bpartnersService = new BPartnerBL(new UserRepository());
		huReservationService = new HUReservationService(new HUReservationRepository());
		pickingCandidateRepository = new PickingCandidateRepository();

		uomKg = BusinessTestHelper.createUOM("Kg", 3, 3);
		productId = BusinessTestHelper.createProductId("Product", uomKg);

		final I_M_Warehouse wh1 = BusinessTestHelper.createWarehouse("WH1");
		this.wh1_loc1 = LocatorId.ofRecord(BusinessTestHelper.createLocator("wh1_loc1", wh1));
	}

	private HuId createCU(String qtyInKg, LocatorId locatorId)
	{
		final I_M_HU cu = helper.newVHU()
				.productId(productId)
				.qty(Quantity.of(qtyInKg, uomKg))
				.huStatus(X_M_HU.HUSTATUS_Active)
				.locatorId(locatorId)
				.build();
		return HuId.ofRepoId(cu.getM_HU_ID());
	}

	private void makeHUReservation(final HuId huId, Quantity qtyToReserve)
	{
		huReservationService.makeReservation(ReserveHUsRequest.builder()
						.qtyToReserve(qtyToReserve)
						.documentRef(HUReservationDocRef.ofSalesOrderLineId(salesOrderAndLineId))
						.productId(productId)
						.customerId(customerLocationId.getBpartnerId())
						.huId(huId)
						.build())
				.orElseThrow(() -> new AdempiereException("Cannot reserve"));
	}

	@Builder(builderMethodName = "packageable", builderClassName = "TestPackageableBuilder")
	private Packageable createPackageable(
			@NonNull final Quantity qtyToDeliver,
			@NonNull final WarehouseId warehouseId)
	{
		final Quantity zero = qtyToDeliver.toZero();

		return Packageable.builder()
				.orgId(OrgId.ofRepoId(1))
				.salesOrderId(salesOrderAndLineId.getOrderId())
				.salesOrderLineIdOrNull(salesOrderAndLineId.getOrderLineId())
				.shipmentScheduleId(shipmentScheduleId)
				.qtyOrdered(qtyToDeliver)
				.qtyToDeliver(qtyToDeliver)
				.qtyDelivered(zero)
				.qtyPickedAndDelivered(zero)
				.qtyPickedNotDelivered(zero)
				.qtyPickedPlanned(zero)
				.customerId(customerLocationId.getBpartnerId())
				.customerLocationId(customerLocationId)
				.handoverLocationId(customerLocationId)
				.warehouseId(warehouseId)
				.bestBeforePolicy(Optional.of(ShipmentAllocationBestBeforePolicy.Expiring_First))
				.productId(productId)
				.asiId(AttributeSetInstanceId.NONE)
				.build();
	}

	private PickingPlan createPlan(final Packageable... packageables)
	{
		return CreatePickingPlanCommand.builder()
				.bpartnersService(bpartnersService)
				.huReservationService(huReservationService)
				.pickingCandidateRepository(pickingCandidateRepository)
				.request(CreatePickingPlanRequest.builder()
						.packageables(PackageableList.ofCollection(ImmutableList.copyOf(packageables)))
						.build())
				.fallbackLotNumberToHUValue(false) // keep lotNumbers null, just to keep our test assertions short
				.build().execute();
	}

	@Test
	void scenario_3HUs_1KgAlreadyReserved()
	{
		createCU("30", wh1_loc1);
		createCU("30", wh1_loc1);
		final HuId huId3 = createCU("1000", wh1_loc1);
		makeHUReservation(huId3, Quantity.of("1", uomKg));
		POJOLookupMap.get().dumpStatus("HUs", "M_HU_Storage");

		final PickingPlan plan = createPlan(
				packageable().qtyToDeliver(Quantity.of("100", uomKg)).warehouseId(wh1_loc1.getWarehouseId()).build()
		);

		System.out.println("PLAN:\n" + Joiner.on("\n").join(plan.getLines()));
		POJOLookupMap.get().dumpStatus("After run", "M_HU", "M_HU_Storage", "M_HU_Reservation");

		expect(plan).toMatchSnapshot();
	}
}