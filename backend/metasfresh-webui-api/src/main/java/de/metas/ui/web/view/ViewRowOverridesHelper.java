package de.metas.ui.web.view;

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
public final class ViewRowOverridesHelper
{
	public static final IViewRowOverrides getViewRowOverrides(final IView view)
	{
		if (view instanceof IViewRowOverrides)
		{
			return (IViewRowOverrides)view;
		}
		else
		{
			return NULL;
		}
	}

	public static final boolean extractSupportIncludedViews(final IViewRow row, final IViewRowOverrides rowOverrides)
	{
		if (rowOverrides != null)
		{
			if(rowOverrides.getIncludedViewId(row) != null)
			{
				return true;
			}
		}
		
		return row.getIncludedViewId() != null;
	}

	public static final ViewId extractIncludedViewId(final IViewRow row, final IViewRowOverrides rowOverrides)
	{
		if (rowOverrides != null)
		{
			final ViewId includedViewId = rowOverrides.getIncludedViewId(row);
			if (includedViewId != null)
			{
				return includedViewId;
			}
		}
		
		return row.getIncludedViewId();
	}

	private static final class NullViewRowOverrides implements IViewRowOverrides
	{
	};

	public static final NullViewRowOverrides NULL = new NullViewRowOverrides();
}
