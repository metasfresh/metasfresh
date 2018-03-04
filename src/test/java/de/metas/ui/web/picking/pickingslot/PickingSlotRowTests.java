package de.metas.ui.web.picking.pickingslot;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import de.metas.ui.web.handlingunits.HUEditorRowType;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class PickingSlotRowTests
{
	@Test
	public void createSourceHURow()
	{
		final PickingSlotRow sourceHURow = PickingSlotRow.fromSourceHUBuilder()
				.huCode("123")
				.huId(123)
				.huEditorRowType(HUEditorRowType.LU)
				.build();
		assertThat(sourceHURow.isPickingSlotRow()).isFalse();
		assertThat(sourceHURow.isPickedHURow()).isFalse();
		assertThat(sourceHURow.isPickingSourceHURow()).isTrue();
		assertThat(sourceHURow.getType().getName()).isEqualTo(HUEditorRowType.LU.getName());
		assertThat(sourceHURow.getDocumentPath().getDocumentId().toInt()).isEqualTo(123);
	}
	
	@Test
	public void createPickedHURow()
	{
		final PickingSlotRow pickedHURow = PickingSlotRow.fromPickedHUBuilder()
				.huCode("124")
				.pickingSlotId(54)
				.huId(124)
				.huEditorRowType(HUEditorRowType.TU)
				.topLevelHU(true)
				.build();
		assertThat(pickedHURow.isPickingSlotRow()).isFalse();
		assertThat(pickedHURow.isPickedHURow()).isTrue();
		assertThat(pickedHURow.isPickingSourceHURow()).isFalse();
		assertThat(pickedHURow.getType().getName()).isEqualTo(HUEditorRowType.TU.getName());
		assertThat(pickedHURow.getDocumentPath().getDocumentId().toInt()).isEqualTo(124);
		assertThat(pickedHURow.isTopLevelHU()).isTrue();
	}
	
	@Test
	public void createPickingSlotRow()
	{
		final PickingSlotRow pickingSlotRow = PickingSlotRow.fromPickingSlotBuilder()
				.pickingSlotId(55)
				.build();
		assertThat(pickingSlotRow.isPickingSlotRow()).isTrue();
		assertThat(pickingSlotRow.isPickingSourceHURow()).isFalse();
		assertThat(pickingSlotRow.isPickedHURow()).isFalse();
		assertThat(pickingSlotRow.getType().getName()).isEqualTo(PickingSlotRowType.M_PICKING_SLOT);
		assertThat(pickingSlotRow.getDocumentPath().getDocumentId().toInt()).isEqualTo(55);
	}
}
