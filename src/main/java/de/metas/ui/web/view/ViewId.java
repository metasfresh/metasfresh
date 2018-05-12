package de.metas.ui.web.view;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;

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
	public static ViewId ofViewIdString(@NonNull final String viewIdStr)
	{
		final WindowId windowId = null; // N/A
		return ofViewIdString(viewIdStr, windowId);
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
		final String viewIdPart = encodeUsingDigits(nextViewId.getAndIncrement(), VIEW_DIGITS);
		final ImmutableList<String> parts = ImmutableList.of(windowId.toJson(), viewIdPart);
		final String viewIdStr = JOINER.join(parts);
		return new ViewId(viewIdStr, parts, windowId);
	}

	@VisibleForTesting
	static String encodeUsingDigits(final long value, char[] digits)
	{
		if (value == 0)
		{
			return String.valueOf(digits[0]);
		}

		final int base = digits.length;
		final StringBuilder buf = new StringBuilder();
		long currentValue = value;
		while (currentValue > 0)
		{
			final int remainder = (int)(currentValue % base);
			currentValue = currentValue / base;
			buf.append(digits[remainder]);
		}

		return buf.reverse().toString();
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

	private static final AtomicLong nextViewId = new AtomicLong(1);
	private static final char[] VIEW_DIGITS = {
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
			'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
			'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
	};

	private final WindowId windowId;
	private final String viewId;
	private final ImmutableList<String> parts;

	private ViewId(
			@NonNull final String viewIdStr,
			@NonNull final ImmutableList<String> parts,
			@NonNull final WindowId windowId)
	{
		this.windowId = windowId;
		this.viewId = viewIdStr;
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
	public ImmutableList<String> getOtherParts()
	{
		return parts.size() > 2 ? parts.subList(2, parts.size()) : ImmutableList.of();
	}

	public ViewId deriveWithWindowId(@NonNull final WindowId windowId)
	{
		if (this.windowId.equals(windowId))
		{
			return this;
		}

		final ImmutableList<String> parts = ImmutableList.<String> builder()
				.add(windowId.toJson())
				.addAll(this.parts.subList(1, this.parts.size()))
				.build();

		final String viewId = JOINER.join(parts);

		return new ViewId(viewId, parts, windowId);
	}
}
