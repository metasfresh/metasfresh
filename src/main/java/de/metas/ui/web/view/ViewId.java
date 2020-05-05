package de.metas.ui.web.view;

import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.lang.UIDStringUtil;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

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
public final class ViewId
{
	public static ViewId of(@Nullable final String windowIdStr, @NonNull final String viewIdStr)
	{
		final WindowId expectedWindowId = windowIdStr == null ? null : WindowId.fromJson(windowIdStr);
		return ofViewIdString(viewIdStr, expectedWindowId);
	}

	@JsonCreator
	public static ViewId fromJson(@NonNull final String json)
	{
		final WindowId expectedWindowId = null; // N/A
		return ofViewIdString(json, expectedWindowId);
	}

	/** @return ViewId from given viewId string; the WindowId will be extracted from viewId string */
	public static ViewId ofViewIdString(@NonNull final String viewIdStr)
	{
		final WindowId expectedWindowId = null; // N/A
		return ofViewIdString(viewIdStr, expectedWindowId);
	}

	public static ViewId ofViewIdString(@NonNull final String viewIdStr, @Nullable final WindowId expectedWindowId)
	{
		final List<String> parts = SPLITTER.splitToList(viewIdStr);
		if (parts.size() < 2)
		{
			throw new AdempiereException("Invalid viewId: " + viewIdStr);
		}

		final WindowId windowId = WindowId.fromJson(parts.get(0));
		if (expectedWindowId != null)
		{
			Preconditions.checkArgument(Objects.equals(windowId, expectedWindowId), "Invalid windowId: %s (viewId=%s). Expected windowId was %s", windowId, viewIdStr, expectedWindowId);
		}

		return new ViewId(viewIdStr, ImmutableList.copyOf(parts), windowId);
	}

	public static ViewId random(@NonNull final WindowId windowId)
	{
		final String viewIdPart = UIDStringUtil.createNext();
		final ImmutableList<String> parts = ImmutableList.of(windowId.toJson(), viewIdPart);
		final String viewIdStr = JOINER.join(parts);
		return new ViewId(viewIdStr, parts, windowId);
	}

	/**
	 * Creates a ViewId from parts.
	 *
	 * @param windowId
	 * @param viewIdPart viewId part (without WindowId!)
	 * @param otherParts optional other parts
	 * @return ViewId
	 */
	public static ViewId ofParts(@NonNull final WindowId windowId, @NonNull final String viewIdPart, @NonNull final String... otherParts)
	{
		final ImmutableList.Builder<String> partsBuilder = ImmutableList.<String> builder()
				.add(windowId.toJson()) // 0
				.add(viewIdPart); // 1

		if (otherParts != null && otherParts.length > 0)
		{
			partsBuilder.add(otherParts); // 2..
		}

		final ImmutableList<String> parts = partsBuilder.build();
		final String viewIdStr = JOINER.join(parts);
		return new ViewId(viewIdStr, parts, windowId);
	}

	private static final String SEPARATOR = "-";
	private static final Splitter SPLITTER = Splitter.on(SEPARATOR).trimResults();
	private static final Joiner JOINER = Joiner.on(SEPARATOR);

	private final WindowId windowId;
	private final String viewId;
	private final ImmutableList<String> parts;

	private ViewId(
			@NonNull final String viewIdStr,
			@NonNull final ImmutableList<String> parts,
			@NonNull final WindowId windowId)
	{
		this.windowId = windowId;
		viewId = viewIdStr;
		this.parts = parts;
	}

	/**
	 * @return never {@code null}
	 */
	public WindowId getWindowId()
	{
		return windowId;
	}

	/** @return full viewId string (including WindowId, including other parts etc) */
	public String getViewId()
	{
		return viewId;
	}

	@Override
	@Deprecated
	public String toString()
	{
		return toJson();
	}

	@JsonValue
	public String toJson()
	{
		return viewId;
	}

	public String getPart(final int index)
	{
		return parts.get(index);
	}

	public int getPartAsInt(final int index)
	{
		try
		{
			final String partStr = getPart(index);
			return Integer.parseInt(partStr);
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Cannot extract part with index " + index + " as integer from " + this, ex);
		}
	}

	/** @return just the viewId part (without the leading WindowId, without other parts etc) */
	public String getViewIdPart()
	{
		return parts.get(1);
	}

	/** @return other parts (those which come after viewId part) */
	@JsonIgnore // IMPORTANT: for some reason, without this annotation the json deserialization does not work even if we have toJson() method annotated with @JsonValue
	public ImmutableList<String> getOtherParts()
	{
		return parts.size() > 2 ? parts.subList(2, parts.size()) : ImmutableList.of();
	}

	public ViewId withWindowId(@NonNull final WindowId newWindowId)
	{
		if (windowId.equals(newWindowId))
		{
			return this;
		}

		final ImmutableList<String> newParts = ImmutableList.<String> builder()
				.add(newWindowId.toJson())
				.addAll(parts.subList(1, parts.size()))
				.build();

		final String newViewId = JOINER.join(newParts);

		return new ViewId(newViewId, newParts, newWindowId);
	}

	public void assertWindowId(@NonNull final WindowId expectedWindowId)
	{
		if (!windowId.equals(expectedWindowId))
		{
			throw new AdempiereException("" + this + " does not have expected windowId: " + expectedWindowId);
		}
	}
}
