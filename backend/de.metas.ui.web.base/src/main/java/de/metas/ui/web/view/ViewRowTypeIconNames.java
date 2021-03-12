package de.metas.ui.web.view;

import lombok.experimental.UtilityClass;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

/**
 * Defines all row icons which are available on frontend side.
 * <p>
 * NOTE to developers: On frontend side, the icons are defined in:
 * <ul>
 * <li>/src/assets/css/font-meta.css
 * <li>/src/assets/css/fonts/metasfresh.ttf
 * <li>/src/components/table/TableItem.js - getIconClassName function
 * </ul>
 */
@UtilityClass
public class ViewRowTypeIconNames
{
	public static final String ICONNAME_LU = "LU";
	public static final String ICONNAME_TU = "TU";
	public static final String ICONNAME_CU = "CU";

	// Required by issue: https://github.com/metasfresh/metasfresh-webui-frontend/issues/675#issuecomment-297016790
	public static final String ICONNAME_PP_Order_Receive = "PP_Order_Receive";
	public static final String ICONNAME_PP_Order_Issue = "PP_Order_Issue";
	public static final String ICONNAME_PP_Order_Issue_Service = "PP_Order_Issue_Service";
}
