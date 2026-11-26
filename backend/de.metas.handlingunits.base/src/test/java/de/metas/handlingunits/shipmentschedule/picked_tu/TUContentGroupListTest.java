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
import de.metas.handlingunits.model.I_M_HU;
import de.metas.quantity.Quantity;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Pins what the content of a TU (groups of CUs) says about itself. No HU harness needed.
 */
class TUContentGroupListTest
{
	private final I_C_UOM each = uom(1);
	private final I_C_UOM kg = uom(2);

	private static I_C_UOM uom(final int id)
	{
		final I_C_UOM uom = mock(I_C_UOM.class);
		when(uom.getC_UOM_ID()).thenReturn(id);
		when(uom.getStdPrecision()).thenReturn(0);
		return uom;
	}

	private TUContentGroup group(final I_C_UOM uom, final int... cuQtys)
	{
		final ImmutableList.Builder<TUContentCU> cus = ImmutableList.builder();
		for (final int qty : cuQtys)
		{
			cus.add(new TUContentCU(mock(I_M_HU.class), Quantity.of(BigDecimal.valueOf(qty), uom)));
		}
		return new TUContentGroup(cus.build());
	}

	@Test
	void ofNoGroups_isEMPTY()
	{
		assertThat(TUContentGroupList.of(ImmutableList.of())).isSameAs(TUContentGroupList.EMPTY);
		assertThat(TUContentGroupList.EMPTY.isEmpty()).isTrue();
	}

	@Test
	void ofTwoGroups_addsUpAndSaysTheStockingUOM()
	{
		final TUContentGroupList list = TUContentGroupList.of(ImmutableList.of(group(each, 2, 2), group(each, 3)));

		assertThat(list.size()).isEqualTo(2);
		assertThat(list.hasSeveralGroups()).isTrue();
		assertThat(list.totalQty()).isEqualTo(Quantity.of(new BigDecimal("7"), each));
		assertThat(list.stockingUOM()).isSameAs(each);
		assertThat(list.get(1).totalQty()).isEqualTo(Quantity.of(new BigDecimal("3"), each));
	}

	@Test
	void ofGroupsInDifferentUOMs_isRejected()
	{
		assertThatThrownBy(() -> TUContentGroupList.of(ImmutableList.of(group(each, 2), group(kg, 2))))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("same UOM");
	}

	@Test
	void groupWithoutCUs_isRejected()
	{
		assertThatThrownBy(() -> new TUContentGroup(ImmutableList.of())).isInstanceOf(AdempiereException.class);
	}
}
