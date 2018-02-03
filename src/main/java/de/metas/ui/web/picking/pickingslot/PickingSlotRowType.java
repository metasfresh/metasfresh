package de.metas.ui.web.picking.pickingslot;

import javax.annotation.Nullable;

import com.google.common.annotations.VisibleForTesting;

import de.metas.ui.web.handlingunits.HUEditorRowType;
import de.metas.ui.web.view.IViewRowType;
import lombok.NonNull;
import lombok.Value;

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
@Value
public class PickingSlotRowType implements IViewRowType
{
	/** Name of a dedicated picking slot row's type. Other possible name types are borrowed from {@link HUEditorRowType}. */
	@VisibleForTesting
	static final String M_PICKING_SLOT = "M_Picking_Slot";

	public static PickingSlotRowType forPickingSlotRow()
	{
		final HUEditorRowType huEditorRowType = null;
		return new PickingSlotRowType(M_PICKING_SLOT, huEditorRowType);
	}

	public static PickingSlotRowType forPickingHuRow(@NonNull final HUEditorRowType huEditorRowType)
	{
		return new PickingSlotRowType(huEditorRowType.getName(), huEditorRowType);
	}

	@NonNull
	String name;
	@Nullable
	HUEditorRowType huEditorRowType;

	public boolean isLU()
	{
		return huEditorRowType != null && huEditorRowType == HUEditorRowType.LU;
	}

	public boolean isTU()
	{
		return huEditorRowType != null && huEditorRowType == HUEditorRowType.TU;
	}
}
