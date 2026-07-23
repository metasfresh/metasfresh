package de.metas.inoutcandidate.process;

/*
 * #%L
 * de.metas.swat.base
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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.OrderId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
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

	@Test
	void isEligibleForClose_zeroQtyPickList_isEligible()
	{
		final I_M_ShipmentSchedule schedule = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class);
		schedule.setQtyPickList(BigDecimal.ZERO);

		assertThat(M_ShipmentSchedule_CloseShipmentSchedules.isEligibleForClose(schedule)).isTrue();
	}

	@Test
	void isEligibleForClose_nonZeroQtyPickList_isNotEligible()
	{
		final I_M_ShipmentSchedule schedule = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class);
		schedule.setQtyPickList(new BigDecimal("5"));

		assertThat(M_ShipmentSchedule_CloseShipmentSchedules.isEligibleForClose(schedule)).isFalse();
	}

	@Test
	void isEligibleForClose_nullQtyPickList_isNotEligible()
	{
		// QtyPickList left unset -> NULL. The pre-existing SQL filter `QtyPickList = 0` excludes NULL rows, so the
		// in-memory eligibility must too (guards against getQtyPickList() masking NULL as ZERO and wrongly closing).
		final I_M_ShipmentSchedule schedule = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class);

		assertThat(M_ShipmentSchedule_CloseShipmentSchedules.isEligibleForClose(schedule)).isFalse();
	}
}
