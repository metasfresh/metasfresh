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

import com.google.common.collect.ImmutableMap;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.IShipmentSchedulePickingInfoService;
import de.metas.order.OrderId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers both of the close-guard's single-schedule naming helpers:
 * <ul>
 * <li>{@link M_ShipmentSchedule_CloseShipmentSchedules#toHumanReadableIdentifier}: order-found → order
 * {@code DocumentNo}; fallback (no order, or order not in the resolved map) → {@code M_ShipmentSchedule_ID}.</li>
 * <li>{@link M_ShipmentSchedule_CloseShipmentSchedules#resolveDocumentNoByOrderId}: schedule referencing an existing
 * order → one-entry {@code OrderId -> DocumentNo} map; no order → empty map (so the identifier falls back to the
 * {@code M_ShipmentSchedule_ID}).</li>
 * </ul>
 */
class M_ShipmentSchedule_CloseShipmentSchedulesTest
{
	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();

		// The class under test resolves this bean eagerly in a field initializer (via SpringContextHolder), so
		// instantiating it needs a registered bean. The stub's filter is never exercised by the helpers under test.
		SpringContextHolder.registerJUnitBean(IShipmentSchedulePickingInfoService.class, () -> null);
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
	void resolveDocumentNoByOrderId_orderFound_resolvesToOrderDocumentNo()
	{
		// the single-offender path: the order is resolved directly (no batch load) and the rejection message names it
		final OrderId orderId = createOrder("SO-5001");
		final I_M_ShipmentSchedule schedule = newSchedule(9401, orderId);

		final Map<OrderId, String> documentNoByOrderId = new M_ShipmentSchedule_CloseShipmentSchedules().resolveDocumentNoByOrderId(schedule);

		assertThat(documentNoByOrderId).hasSize(1).containsEntry(orderId, "SO-5001");
		assertThat(M_ShipmentSchedule_CloseShipmentSchedules.toHumanReadableIdentifier(schedule, documentNoByOrderId)).isEqualTo("SO-5001");
	}

	@Test
	void resolveDocumentNoByOrderId_noOrder_fallsBackToScheduleId()
	{
		final I_M_ShipmentSchedule schedule = newSchedule(9402, null);

		final Map<OrderId, String> documentNoByOrderId = new M_ShipmentSchedule_CloseShipmentSchedules().resolveDocumentNoByOrderId(schedule);

		assertThat(documentNoByOrderId).isEmpty();
		assertThat(M_ShipmentSchedule_CloseShipmentSchedules.toHumanReadableIdentifier(schedule, documentNoByOrderId)).isEqualTo("M_ShipmentSchedule_ID=9402");
	}
}
