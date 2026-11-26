package de.metas.handlingunits.shipmentschedule.api.impl;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.quantity.Quantity;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Pins when a box-content group gives its VHU to a QtyPicked row ({@code M_ShipmentSchedule_QtyPicked.VHU_ID}):
 * only if the group has exactly one VHU and the row's part is that VHU's whole quantity.
 */
class HUShipmentScheduleBL_BoxContentGroup_Test
{
	private final I_C_UOM uom = mock(I_C_UOM.class);

	private Quantity qty(final int qty)
	{
		return Quantity.of(BigDecimal.valueOf(qty), uom);
	}

	private IHUProductStorage storage(final I_M_HU vhu, final int qty)
	{
		final IHUProductStorage storage = mock(IHUProductStorage.class);
		when(storage.getM_HU()).thenReturn(vhu);
		when(storage.getQtyInStockingUOM()).thenReturn(Quantity.of(BigDecimal.valueOf(qty), uom));
		return storage;
	}

	@Test
	void singleVhuGroup_partIsWholeVhu_returnsVhu()
	{
		final I_M_HU cu1 = mock(I_M_HU.class);
		final HUShipmentScheduleBL.BoxContentGroup group = new HUShipmentScheduleBL.BoxContentGroup(ImmutableList.of(storage(cu1, 2)));

		assertThat(group.wholeVhuOrNull(qty(2))).isSameAs(cu1);
	}

	@Test
	void singleVhuGroup_partIsLessThanVhu_returnsNull()
	{
		final HUShipmentScheduleBL.BoxContentGroup group = new HUShipmentScheduleBL.BoxContentGroup(ImmutableList.of(storage(mock(I_M_HU.class), 5)));

		assertThat(group.wholeVhuOrNull(qty(2))).isNull();
	}

	@Test
	void severalVhusOfOneCombination_partEqualsFirstVhuQty_returnsNull()
	{
		// two CUs of 2 and 3 of one lot: a part of 2 equals the first CU's qty, but the CU cannot be told from another one
		final HUShipmentScheduleBL.BoxContentGroup group = new HUShipmentScheduleBL.BoxContentGroup(
				ImmutableList.of(storage(mock(I_M_HU.class), 2), storage(mock(I_M_HU.class), 3)));

		assertThat(group.wholeVhuOrNull(qty(2))).isNull();
		assertThat(group.wholeVhuOrNull(qty(5))).isNull();
	}

	@Test
	void singleVhuGroup_afterPartConsumed_returnsNull()
	{
		final HUShipmentScheduleBL.BoxContentGroup group = new HUShipmentScheduleBL.BoxContentGroup(ImmutableList.of(storage(mock(I_M_HU.class), 4)));
		group.consume(qty(2));

		assertThat(group.remainingQty().toBigDecimal()).isEqualByComparingTo("2");
		assertThat(group.wholeVhuOrNull(qty(2))).isNull();
	}

	@Test
	void storageInOtherUom_groupCountsInStockUom()
	{
		// the rows' QtyPicked is in the stock UOM; a storage kept in another UOM must not be compared in that UOM
		final I_C_UOM storageUom = mock(I_C_UOM.class);
		final IHUProductStorage storage = mock(IHUProductStorage.class);
		when(storage.getM_HU()).thenReturn(mock(I_M_HU.class));
		when(storage.getQty()).thenReturn(Quantity.of(BigDecimal.ONE, storageUom));
		when(storage.getQtyInStockingUOM()).thenReturn(qty(10));
		final HUShipmentScheduleBL.BoxContentGroup group = new HUShipmentScheduleBL.BoxContentGroup(ImmutableList.of(storage));

		assertThat(group.uom()).isSameAs(uom);
		assertThat(group.totalQty().toBigDecimal()).isEqualByComparingTo("10");
		assertThat(group.wholeVhuOrNull(qty(10))).isSameAs(storage.getM_HU());
	}
}
