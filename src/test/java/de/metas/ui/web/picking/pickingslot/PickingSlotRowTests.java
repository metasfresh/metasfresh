package de.metas.ui.web.picking.pickingslot;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import de.metas.ui.web.handlingunits.HUEditorRowType;
import de.metas.ui.web.picking.pickingslot.PickingSlotRow;

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
	public void testCreateSourceHURow()
	{
		final PickingSlotRow sourceHURow = PickingSlotRow.fromSourceHUBuilder()
				.huCode("123")
				.huId(2)
				.huEditorRowType(HUEditorRowType.LU)
				.build();
		assertThat(sourceHURow.isPickingSourceHURow()).isTrue();
		assertThat(sourceHURow.isPickedHURow()).isFalse();
		assertThat(sourceHURow.isPickingSlotRow()).isFalse();
		assertThat(sourceHURow.getType().getName()).isEqualTo(HUEditorRowType.LU.getName());
	}
	
	@Test
	public void testCreatePickedHURow()
	{
		final PickingSlotRow sourceHURow = PickingSlotRow.fromSourceHUBuilder()
				.huCode("123")
				.huId(2)
				.huEditorRowType(HUEditorRowType.TU)
				.build();
		assertThat(sourceHURow.isPickingSourceHURow()).isTrue();
		assertThat(sourceHURow.isPickedHURow()).isFalse();
		assertThat(sourceHURow.isPickingSlotRow()).isFalse();
		assertThat(sourceHURow.getType().getName()).isEqualTo(HUEditorRowType.TU.getName());
	}
}
