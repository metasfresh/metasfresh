package de.metas.ui.web.pickingV2;

import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.window.datatypes.WindowId;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class PickingConstantsV2
{
	public static final String WINDOWID_PackageableView_String = "540485"; // FIXME: HARDCODED
	public static final WindowId WINDOWID_PackageableView = WindowId.fromJson(WINDOWID_PackageableView_String);

	public static final String WINDOWID_ProductsToPickView_String = "picking_v2_productsToPick";
	public static final WindowId WINDOWID_ProductsToPickView = WindowId.fromJson(WINDOWID_ProductsToPickView_String);

	public static final ViewProfileId PROFILE_ID_ProductsToPickView_Review = ViewProfileId.fromJson("review");
}
