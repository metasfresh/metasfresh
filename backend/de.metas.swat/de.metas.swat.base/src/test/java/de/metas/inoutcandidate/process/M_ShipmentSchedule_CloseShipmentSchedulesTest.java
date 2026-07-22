package de.metas.inoutcandidate.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.OrderId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers the close-guard's message-formatting helpers ({@link M_ShipmentSchedule_CloseShipmentSchedules#toHumanReadableIdentifiersCsv}
 * / {@link M_ShipmentSchedule_CloseShipmentSchedules#toHumanReadableIdentifier}): order-found → order {@code DocumentNo};
 * fallback (no order) → {@code M_ShipmentSchedule_ID}; and dedup when several offending schedules share the same order.
 */
class M_ShipmentSchedule_CloseShipmentSchedulesTest
{
	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
	}

	private static I_M_ShipmentSchedule newSchedule(final int scheduleId, final OrderId orderId)
	{
		// not persisted -- the helpers under test only read these two getters, no DB round-trip needed for the schedule itself
		final I_M_ShipmentSchedule schedule = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class);
		schedule.setM_ShipmentSchedule_ID(scheduleId);
		if (orderId != null)
		{
			schedule.setC_Order_ID(orderId.getRepoId());
		}
		return schedule;
	}

	private static OrderId createOrder(final String documentNo)
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setDocumentNo(documentNo);
		InterfaceWrapperHelper.saveRecord(order);
		return OrderId.ofRepoId(order.getC_Order_ID());
	}

	@Test
	void toHumanReadableIdentifier_orderFound_returnsOrderDocumentNo()
	{
		final OrderId orderId = createOrder("SO-1001");
		final I_M_ShipmentSchedule schedule = newSchedule(9001, orderId);
		final Map<OrderId, String> documentNoByOrderId = ImmutableMap.of(orderId, "SO-1001");

		final String identifier = M_ShipmentSchedule_CloseShipmentSchedules.toHumanReadableIdentifier(schedule, documentNoByOrderId);

		assertThat(identifier).isEqualTo("SO-1001");
	}

	@Test
	void toHumanReadableIdentifier_noOrder_fallsBackToScheduleId()
	{
		final I_M_ShipmentSchedule schedule = newSchedule(9002, null);
		final Map<OrderId, String> documentNoByOrderId = ImmutableMap.of();

		final String identifier = M_ShipmentSchedule_CloseShipmentSchedules.toHumanReadableIdentifier(schedule, documentNoByOrderId);

		assertThat(identifier).isEqualTo("M_ShipmentSchedule_ID=9002");
	}

	@Test
	void toHumanReadableIdentifier_orderReferencedButNotInMap_fallsBackToScheduleId()
	{
		// defensive branch: the order id is set on the schedule but wasn't batch-loaded (e.g. deleted concurrently)
		final I_M_ShipmentSchedule schedule = newSchedule(9003, OrderId.ofRepoId(777));
		final Map<OrderId, String> documentNoByOrderId = ImmutableMap.of();

		final String identifier = M_ShipmentSchedule_CloseShipmentSchedules.toHumanReadableIdentifier(schedule, documentNoByOrderId);

		assertThat(identifier).isEqualTo("M_ShipmentSchedule_ID=9003");
	}

	@Test
	void toHumanReadableIdentifiersCsv_ordersFound_batchLoadsAndJoinsDocumentNos()
	{
		final OrderId order1 = createOrder("SO-2001");
		final OrderId order2 = createOrder("SO-2002");

		final List<I_M_ShipmentSchedule> offendingSchedules = ImmutableList.of(
				newSchedule(9101, order1),
				newSchedule(9102, order2));

		final String csv = M_ShipmentSchedule_CloseShipmentSchedules.toHumanReadableIdentifiersCsv(offendingSchedules);

		assertThat(csv).isEqualTo("SO-2001, SO-2002");
	}

	@Test
	void toHumanReadableIdentifiersCsv_mixedOrderAndFallback_joinsBoth()
	{
		final OrderId order1 = createOrder("SO-3001");

		final List<I_M_ShipmentSchedule> offendingSchedules = ImmutableList.of(
				newSchedule(9201, order1),
				newSchedule(9202, null));

		final String csv = M_ShipmentSchedule_CloseShipmentSchedules.toHumanReadableIdentifiersCsv(offendingSchedules);

		assertThat(csv).isEqualTo("SO-3001, M_ShipmentSchedule_ID=9202");
	}

	@Test
	void toHumanReadableIdentifiersCsv_sharedOrder_dedupsToOneIdentifier()
	{
		// two offending schedules on the SAME sales order -- the order's DocumentNo must appear only once
		final OrderId sharedOrder = createOrder("SO-4001");

		final List<I_M_ShipmentSchedule> offendingSchedules = ImmutableList.of(
				newSchedule(9301, sharedOrder),
				newSchedule(9302, sharedOrder));

		final String csv = M_ShipmentSchedule_CloseShipmentSchedules.toHumanReadableIdentifiersCsv(offendingSchedules);

		assertThat(csv).isEqualTo("SO-4001");
	}
}
