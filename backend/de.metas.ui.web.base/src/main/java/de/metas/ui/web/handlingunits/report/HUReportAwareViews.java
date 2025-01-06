/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.ui.web.handlingunits.report;

import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewRow;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class HUReportAwareViews
{
	public static boolean isHUReportAwareView(final IView view)
	{
		return view instanceof HUReportAwareView;
	}

	public static HUReportAwareView cast(@NonNull final IView view)
	{
		return (HUReportAwareView)view;
	}

	public static boolean isHUReportAwareViewRow(final IViewRow row)
	{
		return row instanceof HUReportAwareViewRow;
	}

	public static HUReportAwareViewRow cast(@NonNull final IViewRow row)
	{
		return (HUReportAwareViewRow)row;
	}
}
