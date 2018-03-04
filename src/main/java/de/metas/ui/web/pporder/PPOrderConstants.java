package de.metas.ui.web.pporder;

import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DetailId;

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


public final class PPOrderConstants
{
	public static final String AD_WINDOW_ID_IssueReceipt_String = "540328"; // Manufacturing Issue/Receipt
	public static final WindowId AD_WINDOW_ID_IssueReceipt = WindowId.fromJson("540328"); // Manufacturing Issue/Receipt
	public static final WindowId AD_WINDOW_ID_PP_Order = WindowId.fromJson("53009"); // Manufacturing order standard window
	public static final DetailId TABID_ID_PP_Order_BOMLine = DetailId.fromAD_Tab_ID(53039); // Manufacturing order standard window - BOM line tab

}
