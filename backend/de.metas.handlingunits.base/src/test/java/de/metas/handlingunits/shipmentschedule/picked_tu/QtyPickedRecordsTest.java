package de.metas.handlingunits.shipmentschedule.picked_tu;

/*
 * #%L
 * de.metas.handlingunits.base
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
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.inout.ShipmentScheduleId;
import de.metas.quantity.Quantity;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Pins the questions the shipment code asks about a list of picked rows. No HU harness needed.
 */
class QtyPickedRecordsTest
{
	private static I_M_ShipmentSchedule_QtyPicked row(final int id, final int scheduleId, final int cuId, final String qty)
	{
		final I_M_ShipmentSchedule_QtyPicked row = mock(I_M_ShipmentSchedule_QtyPicked.class);
		when(row.getM_ShipmentSchedule_QtyPicked_ID()).thenReturn(id);
		when(row.getM_ShipmentSchedule_ID()).thenReturn(scheduleId);
		when(row.getVHU_ID()).thenReturn(cuId);
		when(row.getQtyPicked()).thenReturn(new BigDecimal(qty));
		return row;
	}

	@Test
	void ofNoRows_isEMPTY()
	{
		assertThat(QtyPickedRecords.of(ImmutableList.of())).isSameAs(QtyPickedRecords.EMPTY);
		assertThat(QtyPickedRecords.EMPTY.isEmpty()).isTrue();
	}

	@Test
	void withCUAndTuOnly_splitRowsByTheirCUReference_andTuOnlyAreSortedById()
	{
		final I_M_ShipmentSchedule_QtyPicked withCU = row(1, 10, 100, "2");
		final I_M_ShipmentSchedule_QtyPicked tuOnly3 = row(3, 10, 0, "2");
		final I_M_ShipmentSchedule_QtyPicked tuOnly2 = row(2, 11, 0, "2");
		final QtyPickedRecords rows = QtyPickedRecords.of(ImmutableList.of(withCU, tuOnly3, tuOnly2));

		assertThat(rows.withCU().toList()).containsExactly(withCU);
		assertThat(rows.tuOnlySortedById().toList()).containsExactly(tuOnly2, tuOnly3);
		assertThat(rows.extractCUIds()).containsExactly(HuId.ofRepoId(100));
		assertThat(rows.extractScheduleIds()).containsExactlyInAnyOrder(ShipmentScheduleId.ofRepoId(10), ShipmentScheduleId.ofRepoId(11));
	}

	@Test
	void hasNegativeQty_andTotalQtyPicked()
	{
		final I_C_UOM productStockingUOM = mock(I_C_UOM.class);
		when(productStockingUOM.getC_UOM_ID()).thenReturn(1);
		when(productStockingUOM.getStdPrecision()).thenReturn(0);

		final QtyPickedRecords rows = QtyPickedRecords.of(ImmutableList.of(row(1, 10, 0, "4"), row(2, 10, 0, "-1")));

		assertThat(rows.hasNegativeQty()).isTrue();
		assertThat(rows.totalQtyPicked(productStockingUOM)).isEqualTo(Quantity.of(new BigDecimal("3"), productStockingUOM));
		assertThat(QtyPickedRecords.EMPTY.hasNegativeQty()).isFalse();
		assertThat(QtyPickedRecords.EMPTY.totalQtyPicked(productStockingUOM).isZero()).isTrue();
	}

	@Test
	void filter_keepsTheOrder()
	{
		final I_M_ShipmentSchedule_QtyPicked first = row(5, 10, 0, "1");
		final I_M_ShipmentSchedule_QtyPicked second = row(4, 10, 0, "2");

		assertThat(QtyPickedRecords.of(ImmutableList.of(first, second)).filter(row -> row.getQtyPicked().signum() > 0).toList()).containsExactly(first, second);
	}
}
