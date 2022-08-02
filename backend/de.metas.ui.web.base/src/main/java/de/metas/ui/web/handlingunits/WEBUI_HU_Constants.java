package de.metas.ui.web.handlingunits;

import de.metas.i18n.AdMessageKey;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.experimental.UtilityClass;

import static de.metas.ui.web.handlingunits.HUEditorRow.SYSCFG_PREFIX;

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

@UtilityClass
public final class WEBUI_HU_Constants
{
	public static final String WEBUI_HU_Window_ID_String = "540189"; // FIXME: hardcoded
	public static final WindowId WEBUI_HU_Window_ID = WindowId.fromJson(WEBUI_HU_Window_ID_String);

	public static final WindowId WEBUI_HU_Trace_Window_ID = WindowId.fromJson("540353");

	/**
	 * This message has one parameter: QtyTU>
	 */
	public static final AdMessageKey MSG_NotEnoughTUsFound = AdMessageKey.of("WEBUI_M_HU_MoveTUsToDirectWarehouse.NotEnoughTUsFound");

	public static final AdMessageKey MSG_WEBUI_ONLY_TOP_LEVEL_HU = AdMessageKey.of("WEBUI_Only_TopLevelHU");

	public static final AdMessageKey MSG_WEBUI_SELECT_ACTIVE_UNSELECTED_HU = AdMessageKey.of("WEBUI_Picking_Select_Active_UnSelected_HUs");

	public static final AdMessageKey MSG_WEBUI_ONLY_CU = AdMessageKey.of("WEBUI_Only_CU");

	public static final String SYS_CONFIG_CLEARANCE = SYSCFG_PREFIX + ".ClearanceStatus.IsDisplayed";
}
