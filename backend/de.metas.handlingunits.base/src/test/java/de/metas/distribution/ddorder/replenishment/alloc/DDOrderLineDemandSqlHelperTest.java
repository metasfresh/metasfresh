package de.metas.distribution.ddorder.replenishment.alloc;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.model.I_DD_OrderLine_PickingJobSchedule;
import de.metas.inoutcandidate.model.I_M_Picking_Job_Schedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.OrderId;
import de.metas.shipping.CarrierProductId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_OrderLine;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * The two demand routes to a job's carrier ({@code DD_OrderLine_PickingJobSchedule} association and
 * {@code DD_OrderLine.C_OrderLineSO_ID}) must be OR-ed within {@link DDOrderLineDemandSqlHelper#byCarrierProductIds},
 * so a line reached by either route is matched and a line reached by neither is not.
 */
@ExtendWith(AdempiereTestWatcher.class)
class DDOrderLineDemandSqlHelperTest
{
	private static final CarrierProductId CARRIER_A = CarrierProductId.ofRepoId(540101);
	private static final CarrierProductId CARRIER_B = CarrierProductId.ofRepoId(540102);
	private static final CarrierProductId CARRIER_NOT_SELECTED = CarrierProductId.ofRepoId(540103);

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	private static int newDDOrderLine(final int ddOrderId)
	{
		final I_DD_OrderLine line = newInstance(I_DD_OrderLine.class);
		line.setDD_Order_ID(ddOrderId);
		saveRecord(line);
		return line.getDD_OrderLine_ID();
	}

	/**
	 * {@code cOrderLineIdSentinel} must be an explicit, non-zero, unique value even when the schedule's route-2 link
	 * is irrelevant to the caller's scenario: {@code C_OrderLine_ID} is nullable with no DB default, so real Postgres
	 * never matches two unset (NULL) values against each other — but the in-memory test harness represents "unset"
	 * as the Java default {@code 0}, so two schedules (or a schedule and an unrelated line's unset
	 * {@code C_OrderLineSO_ID}) left unset would spuriously collide on {@code 0} here, which cannot happen in
	 * production.
	 */
	private static int newShipmentScheduleWithCarrier(final CarrierProductId carrierProductId, final int cOrderLineIdSentinel)
	{
		final I_M_ShipmentSchedule schedule = newInstance(I_M_ShipmentSchedule.class);
		schedule.setCarrier_Product_ID(carrierProductId.getRepoId());
		schedule.setC_OrderLine_ID(cOrderLineIdSentinel);
		saveRecord(schedule);
		return schedule.getM_ShipmentSchedule_ID();
	}

	/** Route 1: {@code DD_OrderLine} -> {@code DD_OrderLine_PickingJobSchedule} -> {@code M_Picking_Job_Schedule} -> the schedule. */
	private static void assignViaPickingJobSchedule(final int ddOrderLineId, final int shipmentScheduleId)
	{
		final I_M_Picking_Job_Schedule pickingJobSchedule = newInstance(I_M_Picking_Job_Schedule.class);
		pickingJobSchedule.setM_ShipmentSchedule_ID(shipmentScheduleId);
		saveRecord(pickingJobSchedule);

		final I_DD_OrderLine_PickingJobSchedule contributor = newInstance(I_DD_OrderLine_PickingJobSchedule.class);
		contributor.setDD_OrderLine_ID(ddOrderLineId);
		contributor.setM_Picking_Job_Schedule_ID(pickingJobSchedule.getM_Picking_Job_Schedule_ID());
		saveRecord(contributor);
	}

	@Test
	void carrierRestrictionOrsBothRoutes()
	{
		final I_DD_Order ddOrder = newInstance(I_DD_Order.class);
		saveRecord(ddOrder);
		final int ddOrderId = ddOrder.getDD_Order_ID();

		// route 1: the association route, carrying CARRIER_A
		final int lineViaAssociation = newDDOrderLine(ddOrderId);
		final int scheduleA = newShipmentScheduleWithCarrier(CARRIER_A, 800001);
		assignViaPickingJobSchedule(lineViaAssociation, scheduleA);

		// route 2: DD_OrderLine.C_OrderLineSO_ID -> M_ShipmentSchedule.C_OrderLine_ID, carrying CARRIER_B
		final int lineViaSalesOrderLine = newDDOrderLine(ddOrderId);
		final int salesOrderLineId = 700001;
		final I_M_ShipmentSchedule scheduleB = newInstance(I_M_ShipmentSchedule.class);
		scheduleB.setCarrier_Product_ID(CARRIER_B.getRepoId());
		scheduleB.setC_OrderLine_ID(salesOrderLineId);
		saveRecord(scheduleB);
		final I_DD_OrderLine lineB = load(lineViaSalesOrderLine, I_DD_OrderLine.class);
		lineB.setC_OrderLineSO_ID(salesOrderLineId);
		saveRecord(lineB);

		// neither route: no association row, no C_OrderLineSO_ID
		final int lineWithNoRoute = newDDOrderLine(ddOrderId);

		final IQuery<I_DD_OrderLine> query = DDOrderLineDemandSqlHelper.byCarrierProductIds(ImmutableSet.of(CARRIER_A, CARRIER_B));

		assertThat(query.listIds())
				.as("both routes matched, the route-less line excluded")
				.containsExactlyInAnyOrder(lineViaAssociation, lineViaSalesOrderLine)
				.doesNotContain(lineWithNoRoute);
	}

	@Test
	void aThirdCarrierNotInTheSetMatchesNoLine()
	{
		final I_DD_Order ddOrder = newInstance(I_DD_Order.class);
		saveRecord(ddOrder);
		final int ddOrderId = ddOrder.getDD_Order_ID();

		final int lineViaAssociation = newDDOrderLine(ddOrderId);
		final int scheduleOther = newShipmentScheduleWithCarrier(CARRIER_NOT_SELECTED, 800002);
		assignViaPickingJobSchedule(lineViaAssociation, scheduleOther);

		final IQuery<I_DD_OrderLine> query = DDOrderLineDemandSqlHelper.byCarrierProductIds(ImmutableSet.of(CARRIER_A, CARRIER_B));

		assertThat(query.listIds()).isEmpty();
	}

	private static I_C_OrderLine newSalesOrderLine(final OrderId salesOrderId)
	{
		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setC_Order_ID(salesOrderId.getRepoId());
		saveRecord(orderLine);
		return orderLine;
	}

	/**
	 * Proves all THREE routes of {@link DDOrderLineDemandSqlHelper#bySalesOrderIds} are OR-ed, including the
	 * header route — {@code lineViaHeader}'s order has neither an association row nor a
	 * {@code C_OrderLineSO_ID}, so it is matched ONLY because {@code DD_Order.C_Order_ID} is set directly.
	 */
	@Test
	void salesOrderRestrictionOrsAllThreeRoutes()
	{
		final OrderId orderA = OrderId.ofRepoId(900101);
		final OrderId orderB = OrderId.ofRepoId(900102);
		final OrderId orderC = OrderId.ofRepoId(900103);

		final I_DD_Order ddOrder = newInstance(I_DD_Order.class);
		saveRecord(ddOrder);
		final int ddOrderId = ddOrder.getDD_Order_ID();

		// route 1: the association route -- the schedule's own sales-order line belongs to orderA
		final int lineViaAssociation = newDDOrderLine(ddOrderId);
		final I_C_OrderLine orderLineA = newSalesOrderLine(orderA);
		final I_M_ShipmentSchedule scheduleA = newInstance(I_M_ShipmentSchedule.class);
		scheduleA.setC_OrderLine_ID(orderLineA.getC_OrderLine_ID());
		saveRecord(scheduleA);
		assignViaPickingJobSchedule(lineViaAssociation, scheduleA.getM_ShipmentSchedule_ID());

		// route 2: DD_OrderLine.C_OrderLineSO_ID -> C_OrderLine.C_Order_ID, belonging to orderB
		final int lineViaSalesOrderLine = newDDOrderLine(ddOrderId);
		final I_C_OrderLine orderLineB = newSalesOrderLine(orderB);
		final I_DD_OrderLine lineB = load(lineViaSalesOrderLine, I_DD_OrderLine.class);
		lineB.setC_OrderLineSO_ID(orderLineB.getC_OrderLine_ID());
		saveRecord(lineB);

		// route 3: the header, DD_Order.C_Order_ID = orderC -- NO association row, NO C_OrderLineSO_ID at all
		final I_DD_Order ddOrderViaHeader = newInstance(I_DD_Order.class);
		ddOrderViaHeader.setC_Order_ID(orderC.getRepoId());
		saveRecord(ddOrderViaHeader);
		final int lineViaHeader = newDDOrderLine(ddOrderViaHeader.getDD_Order_ID());

		// neither route: no association, no C_OrderLineSO_ID, header unset
		final int lineWithNoRoute = newDDOrderLine(ddOrderId);

		final IQuery<I_DD_OrderLine> query = DDOrderLineDemandSqlHelper.bySalesOrderIds(ImmutableSet.of(orderA, orderB, orderC));

		assertThat(query.listIds())
				.as("all three routes matched, the route-less line excluded")
				.containsExactlyInAnyOrder(lineViaAssociation, lineViaSalesOrderLine, lineViaHeader)
				.doesNotContain(lineWithNoRoute);
	}

	/**
	 * The header route in isolation: a line whose order has {@code C_Order_ID} set and NEITHER of the other two
	 * routes must still match — this is the case {@code C_OrderLineSO_ID} cannot cover (it is only populated while
	 * the line-level aggregation sysconfig is on), which is why the header route stays.
	 */
	@Test
	void headerRouteAloneMatchesWithNoLineLevelSignal()
	{
		final OrderId orderHeader = OrderId.ofRepoId(900201);
		final OrderId orderNotSelected = OrderId.ofRepoId(900299);

		final I_DD_Order ddOrder = newInstance(I_DD_Order.class);
		ddOrder.setC_Order_ID(orderHeader.getRepoId());
		saveRecord(ddOrder);
		final int lineViaHeaderOnly = newDDOrderLine(ddOrder.getDD_Order_ID());

		final IQuery<I_DD_OrderLine> query = DDOrderLineDemandSqlHelper.bySalesOrderIds(ImmutableSet.of(orderHeader));
		assertThat(query.listIds())
				.as("the header route alone -- no association row, no C_OrderLineSO_ID -- still matches")
				.containsExactly(lineViaHeaderOnly);

		final IQuery<I_DD_OrderLine> queryNotSelected = DDOrderLineDemandSqlHelper.bySalesOrderIds(ImmutableSet.of(orderNotSelected));
		assertThat(queryNotSelected.listIds()).isEmpty();
	}
}
