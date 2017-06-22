package de.metas.ui.web.picking;

import de.metas.ui.web.window.datatypes.WindowId;
import lombok.experimental.UtilityClass;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@UtilityClass
public final class PickingConstants
{
	public static final WindowId WINDOWID_PackageableView = WindowId.of(540345); // FIXME: HARDCODED
	public static final String WINDOWID_PickingView_String = "540350"; // FIXME: HARDCODED
	public static final WindowId WINDOWID_PickingView = WindowId.fromJson(WINDOWID_PickingView_String);
	public static final String WINDOWID_PickingSlotView_String = "pickingSlot";
	public static final WindowId WINDOWID_PickingSlotView = WindowId.fromJson(WINDOWID_PickingSlotView_String);

}
