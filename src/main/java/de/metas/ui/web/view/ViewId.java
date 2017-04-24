package de.metas.ui.web.view;

import java.util.Objects;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.base.Preconditions;

import de.metas.ui.web.window.datatypes.WindowId;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

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

@EqualsAndHashCode
@ToString
public final class ViewId
{
	public static final ViewId of(@Nullable final String windowIdStr, @NonNull final String viewIdStr)
	{
		final WindowId windowIdExpected = extractWindowIdFromViewId(viewIdStr);
		final WindowId windowId;
		if (windowIdStr == null)
		{
			windowId = windowIdExpected;
		}
		else
		{
			windowId = WindowId.fromJson(windowIdStr);
			Preconditions.checkArgument(Objects.equals(windowId, windowIdExpected), "Invalid windowId: %s (viewId=%s)", windowId, viewIdStr);
		}
		
		return new ViewId(windowId, viewIdStr);
	}

	public static ViewId of(@Nullable WindowId windowId, @NonNull String viewIdStr)
	{
		final WindowId windowIdExpected = extractWindowIdFromViewId(viewIdStr);
		final WindowId windowIdEffective;
		if(windowId == null)
		{
			windowIdEffective = windowIdExpected;
		}
		else
		{
			windowIdEffective = windowId;
			Preconditions.checkArgument(Objects.equals(windowIdEffective, windowIdExpected), "Invalid windowId: %s (viewId=%s)", windowId, viewIdStr);
		}
		
		return new ViewId(windowIdEffective, viewIdStr);
	}
	
	public static ViewId random(@NonNull final WindowId windowId)
	{
		// TODO: find a way to generate smaller viewIds
		final String viewIdStr = windowId.toJson() + "-" + UUID.randomUUID().toString();
		return new ViewId(windowId, viewIdStr);
	}
	
	private static final WindowId extractWindowIdFromViewId(@NonNull final String viewIdStr)
	{
		final int idx = viewIdStr.indexOf("-");
		final String windowIdStr = viewIdStr.substring(0, idx);
		return WindowId.fromJson(windowIdStr);
	}

	private final WindowId windowId;
	private final String viewId;

	public ViewId(@NonNull final WindowId windowId, @NonNull final String viewId)
	{
		super();
		this.windowId = windowId;
		this.viewId = viewId;
	}
	
	public WindowId getWindowId()
	{
		return windowId;
	}
	
	public String getViewId()
	{
		return viewId;
	}
}
