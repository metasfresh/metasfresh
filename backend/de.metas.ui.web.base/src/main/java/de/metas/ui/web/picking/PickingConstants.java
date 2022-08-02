package de.metas.ui.web.picking;

import de.metas.common.util.WindowConstants;
import de.metas.i18n.AdMessageKey;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@UtilityClass
public final class PickingConstants
{
	public static final WindowId WINDOWID_PackageableView = WindowId.of(WindowConstants.PACKAGEABLE_VIEW_AD_WINDOW_ID); // FIXME: HARDCODED
	public static final String WINDOWID_PickingView_String = "540350"; // FIXME: HARDCODED
	public static final WindowId WINDOWID_PickingView = WindowId.fromJson(WINDOWID_PickingView_String);
	public static final String WINDOWID_PickingSlotView_String = "pickingSlot";
	public static final WindowId WINDOWID_PickingSlotView = WindowId.fromJson(WINDOWID_PickingSlotView_String);

	public static final AdMessageKey MSG_WEBUI_PICKING_NO_PROCESSED_RECORDS = AdMessageKey.of("WEBUI_Picking_No_Processed_Records");
	public static final AdMessageKey MSG_WEBUI_PICKING_SELECT_PICKED_HU = AdMessageKey.of("WEBUI_Picking_SelectPickedHU");

	/**
	 * Tells the user that an action is only applicable if the selected row is a source HU.
	 */
	public static final AdMessageKey MSG_WEBUI_PICKING_SELECT_SOURCE_HU = AdMessageKey.of("WEBUI_Picking_SelectSourceHU");

	/**
	 * Tells the user that an action is only possible if there is a an available source HU to pick from (i.e. it also needs to have a quantity) or to return a quantity back into.
	 */
	public static final AdMessageKey MSG_WEBUI_PICKING_MISSING_SOURCE_HU = AdMessageKey.of("WEBUI_Picking_MissingSourceHU");

	/**
	 * Tells the user that an action is only possible if a picked CU (not a picking slot or a picked TU) is selected
	 */
	public static final AdMessageKey MSG_WEBUI_PICKING_SELECT_PICKED_CU = AdMessageKey.of("WEBUI_Picking_SelectPickedCU");

	public static final AdMessageKey MSG_WEBUI_PICKING_SELECT_PICKING_SLOT = AdMessageKey.of("WEBUI_Picking_SelectPickingSlot");

	public static final AdMessageKey MSG_WEBUI_PICKING_PICK_SOMETHING = AdMessageKey.of("WEBUI_Picking_PickSomething");
	public static final AdMessageKey MSG_WEBUI_PICKING_NO_UNPROCESSED_RECORDS = AdMessageKey.of("WEBUI_Picking_No_Unprocessed_Records");
	public static final AdMessageKey MSG_WEBUI_PICKING_NOT_TOP_LEVEL_HU = AdMessageKey.of("WEBUI_Picking_Not_TopLevelHU");
	public static final AdMessageKey MSG_WEBUI_PICKING_DIVERGING_LOCATIONS = AdMessageKey.of("WEBUI_Picking_Diverging_Locations");
	public static final AdMessageKey MSG_WEBUI_PICKING_TOO_MANY_PACKAGEABLES_1P = AdMessageKey.of("WEBUI_Picking_Too_Many_Packageables");
	public static final AdMessageKey MSG_WEBUI_PICKING_CANNOT_PICK_INCLUDED_ROWS = AdMessageKey.of("WEBUI_Picking_CannotPickIncludedRows");

	public static final AdMessageKey MSG_WEBUI_PICKING_NO_PICKED_HU_FOUND = AdMessageKey.of("WEBUI_Picking_NoPickedHuFound");
	public static final AdMessageKey MSG_WEBUI_PICKING_TO_EXISTING_CUS_NOT_ALLOWED = AdMessageKey.of("WEBUI_Picking_PickingToExistingCUsNotAllowed");

	public static final String SYS_CONFIG_SHOW_ALL_PICKING_CANDIDATES_ON_PICKING_SLOTS = "de.metas.pickingSlots.showAllPickingCandidates";
}
