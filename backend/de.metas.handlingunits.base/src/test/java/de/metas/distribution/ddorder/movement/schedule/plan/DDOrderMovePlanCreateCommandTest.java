package de.metas.distribution.ddorder.movement.schedule.plan;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import de.metas.business.BusinessTestHelper;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelDAO;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.reservation.HUReservationRepository;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.inoutcandidate.api.IReceiptScheduleProducerFactory;
import de.metas.inoutcandidate.api.impl.ReceiptScheduleProducerFactory;
import de.metas.inoutcandidate.filter.GenerateReceiptScheduleForModelAggregateFilter;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Warehouse;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(AdempiereTestWatcher.class)
class DDOrderMovePlanCreateCommandTest
{
	//
	// Services
	HUTestHelper helper;
	HUReservationService huReservationService;
	IHandlingUnitsDAO handlingUnitsDAO;

	//
	// Master data
	I_C_UOM uomKg;
	ProductId productId;
	private LocatorId wh1_loc1;
	private LocatorId wh2_loc1;
	private DDOrderLowLevelDAO ddOrderLowLevelDAO;

	@BeforeEach
	void beforeEach()
	{
		helper = HUTestHelper.newInstanceOutOfTrx();
		ddOrderLowLevelDAO = new DDOrderLowLevelDAO();
		huReservationService = new HUReservationService(new HUReservationRepository());
		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		uomKg = BusinessTestHelper.createUOM("Kg", 3, 3);
		productId = BusinessTestHelper.createProductId("Product", uomKg);

		final I_M_Warehouse wh1 = BusinessTestHelper.createWarehouse("WH1");
		this.wh1_loc1 = LocatorId.ofRecord(BusinessTestHelper.createLocator("wh1_loc1", wh1));

		final I_M_Warehouse wh2 = BusinessTestHelper.createWarehouse("WH2");
		this.wh2_loc1 = LocatorId.ofRecord(BusinessTestHelper.createLocator("wh2_loc1", wh2));
	}

	private HuId createCU(String qtyInKg, LocatorId locatorId)
	{
		final Quantity qty = Quantity.of(qtyInKg, uomKg);
		final I_M_HU cu = helper.newVHU()
				.productId(productId)
				.qty(qty)
				.huStatus(X_M_HU.HUSTATUS_Active)
				.locatorId(locatorId)
				.build();
		final HuId huId = HuId.ofRepoId(cu.getM_HU_ID());

		System.out.println("Created CU: huId=" + huId.getRepoId() + ", qty=" + qty + ", locator=" + locatorId);
		return huId;
	}

	private I_DD_Order createDDOrder()
	{
		final I_DD_Order ddOrder = InterfaceWrapperHelper.newInstance(I_DD_Order.class);
		InterfaceWrapperHelper.saveRecord(ddOrder);
		return ddOrder;
	}

	private void createDDOrderLine(
			@NonNull final I_DD_Order ddOrder,
			@NonNull final Quantity qty,
			@NonNull final LocatorId fromLocatorId,
			@NonNull final LocatorId toLocatorId)
	{
		final I_DD_OrderLine line = InterfaceWrapperHelper.newInstance(I_DD_OrderLine.class);
		line.setDD_Order_ID(ddOrder.getDD_Order_ID());
		line.setM_Product_ID(productId.getRepoId());
		line.setC_UOM_ID(qty.getUomId().getRepoId());
		line.setQtyEntered(qty.toBigDecimal());
		line.setM_Locator_ID(fromLocatorId.getRepoId());
		line.setM_LocatorTo_ID(toLocatorId.getRepoId());
		InterfaceWrapperHelper.saveRecord(line);
	}

	private DDOrderMovePlan createPlan(final I_DD_Order ddOrder)
	{
		return DDOrderMovePlanCreateCommand.builder()
				.ddOrderLowLevelDAO(ddOrderLowLevelDAO)
				.huReservationService(huReservationService)
				.request(DDOrderMovePlanCreateRequest.builder()
						.ddOrder(ddOrder)
						.failIfNotFullAllocated(true)
						.build())
				.build().execute();
	}

	@Test
	void scenario_3_TopLevelHUs()
	{
		// NOTE: we use createCU for convenience, but we could also create LU. That would be more appropriate.
		final HuId huId1 = createCU("30", wh1_loc1);
		final HuId huId2 = createCU("30", wh1_loc1);
		final HuId huId3 = createCU("999", wh1_loc1);

		final I_DD_Order ddOrder = createDDOrder();
		createDDOrderLine(ddOrder, Quantity.of("100", uomKg), wh1_loc1, wh2_loc1);

		final DDOrderMovePlan plan = createPlan(ddOrder);

		final ImmutableList<DDOrderMovePlanLine> lines = plan.getLines();
		assertThat(lines).hasSize(1);
		final DDOrderMovePlanLine line = lines.get(0);
		assertThat(line.getQtyToPick()).isEqualTo(Quantity.of(30 + 30 + 999, uomKg));

		final ImmutableList<DDOrderMovePlanStep> steps = line.getSteps();
		System.out.println("PLAN Steps:\n" + Joiner.on("\n").join(steps));
		POJOLookupMap.get().dumpStatus("After run", "M_HU", "M_HU_Storage", "M_HU_Reservation");
		assertThat(steps).hasSize(3);

		final DDOrderMovePlanStep.DDOrderMovePlanStepBuilder expectedLineBuilder = DDOrderMovePlanStep.builder()
				.productId(productId)
				.pickFromLocatorId(wh1_loc1)
				.dropToLocatorId(wh2_loc1);

		assertThat(steps.get(0))
				.usingRecursiveComparison()
				.isEqualTo(expectedLineBuilder
						.pickFromHU(handlingUnitsDAO.getById(huId1))
						.qtyToPick(Quantity.of("30.000", uomKg))
						.isPickWholeHU(true)
						.build());
		assertThat(steps.get(1))
				.usingRecursiveComparison()
				.isEqualTo(expectedLineBuilder
						.pickFromHU(handlingUnitsDAO.getById(huId2))
						.qtyToPick(Quantity.of("30.000", uomKg))
						.isPickWholeHU(true)
						.build());
		assertThat(steps.get(2))
				.usingRecursiveComparison()
				.isEqualTo(expectedLineBuilder
						.pickFromHU(handlingUnitsDAO.getById(huId3))
						.qtyToPick(Quantity.of("999.000", uomKg))
						.isPickWholeHU(true)
						.build());
	}

}