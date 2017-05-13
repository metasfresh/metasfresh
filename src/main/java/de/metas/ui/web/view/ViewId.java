package de.metas.ui.web.view;

import java.util.Objects;
import java.util.UUID;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
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
		final WindowId expectedWindowId = windowIdStr == null ? null : WindowId.fromJson(windowIdStr);
		return ofViewIdString(viewIdStr, expectedWindowId);
	}

	/** @return ViewId from given viewId string; the WindowId will be extracted from viewId string */
	@JsonCreator
	public static ViewId ofViewIdString(@NonNull String viewIdStr)
	{
		final WindowId windowId = null; // N/A
		return ofViewIdString(viewIdStr, windowId);
	}

	public static ViewId ofViewIdString(@NonNull String viewIdStr, @Nullable WindowId expectedWindowId)
	{
		final WindowId windowId = extractWindowIdFromViewId(viewIdStr);
		if(expectedWindowId != null)
		{
			Preconditions.checkArgument(Objects.equals(windowId, expectedWindowId), "Invalid windowId: %s (viewId=%s)", windowId, viewIdStr);
		}

		return new ViewId(windowId, viewIdStr);
	}

	public static ViewId random(@NonNull final WindowId windowId)
	{
		// TODO: find a way to generate smaller viewIds
		final String viewIdStr = windowId.toJson() + SEPARATOR_AFTER_WindowId + UUID.randomUUID().toString();
		return new ViewId(windowId, viewIdStr);
	}

	private static final WindowId extractWindowIdFromViewId(@NonNull final String viewIdStr)
	{
		final int idx = viewIdStr.indexOf(SEPARATOR_AFTER_WindowId);
		final String windowIdStr = viewIdStr.substring(0, idx);
		return WindowId.fromJson(windowIdStr);
	}
	
	private static final String SEPARATOR_AFTER_WindowId = "-";

	private final WindowId windowId;
	private final String viewId;

	private ViewId(@NonNull final WindowId windowId, @NonNull final String viewId)
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

	@JsonValue
	public String toJson()
	{
		return viewId;
	}
}
