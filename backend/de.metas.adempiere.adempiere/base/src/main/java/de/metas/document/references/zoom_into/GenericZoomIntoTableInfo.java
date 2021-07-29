/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.document.references.zoom_into;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Value
public class GenericZoomIntoTableInfo
{
	@NonNull String tableName;
	@NonNull ImmutableSet<String> keyColumnNames;
	boolean hasIsSOTrxColumn;

	@Nullable GenericZoomIntoTableWindow defaultWindow;
	@Nullable GenericZoomIntoTableWindow defaultSOWindow;
	@Nullable GenericZoomIntoTableWindow defaultPOWindow;
	@NonNull ImmutableList<GenericZoomIntoTableWindow> otherWindows;

	@Nullable String parentTableName;
	@Nullable String parentLinkColumnName;

	@Builder
	private GenericZoomIntoTableInfo(
			@NonNull final String tableName,
			@NonNull final Collection<String> keyColumnNames,
			final boolean hasIsSOTrxColumn,
			@NonNull final List<GenericZoomIntoTableWindow> windows,
			@Nullable final String parentTableName,
			@Nullable final String parentLinkColumnName)
	{
		this.tableName = tableName;
		this.keyColumnNames = ImmutableSet.copyOf(keyColumnNames);
		this.hasIsSOTrxColumn = hasIsSOTrxColumn;

		GenericZoomIntoTableWindow defaultSOWindow = null;
		GenericZoomIntoTableWindow defaultPOWindow = null;
		final ArrayList<GenericZoomIntoTableWindow> otherWindows = new ArrayList<>(windows.size());

		for (final GenericZoomIntoTableWindow window : windows)
		{
			boolean isDefaultWindow = false;
			if (window.isDefaultSOWindow())
			{
				defaultSOWindow = window;
				isDefaultWindow = true;
			}
			if (window.isDefaultPOWindow())
			{
				defaultPOWindow = window;
				isDefaultWindow = true;
			}

			if (!isDefaultWindow)
			{
				otherWindows.add(window);
			}
		}

		// IMPORTANT: have them sorted by Priority
		otherWindows.sort(Comparator.comparing(GenericZoomIntoTableWindow::getPriority));

		//
		// Determine default window:
		if (defaultSOWindow != null && defaultPOWindow != null)
		{
			// Case: we have a window for Sales documents and another window for Purchase documents
			// => default window is the
			this.defaultWindow = defaultSOWindow;
		}
		else if (defaultSOWindow != null)
		{
			final GenericZoomIntoTableWindow firstOtherWindow = !otherWindows.isEmpty() ? otherWindows.get(0) : null;
			if (firstOtherWindow != null && firstOtherWindow.hasHigherPriorityThen(defaultSOWindow))
			{
				this.defaultWindow = firstOtherWindow;
				otherWindows.remove(firstOtherWindow);
			}
			else
			{
				this.defaultWindow = defaultSOWindow;
			}
		}
		else if (!otherWindows.isEmpty())
		{
			this.defaultWindow = otherWindows.remove(0);
		}
		else
		{
			this.defaultWindow = null;
		}

		this.defaultSOWindow = defaultSOWindow;
		this.defaultPOWindow = defaultPOWindow;

		this.otherWindows = ImmutableList.copyOf(otherWindows);

		this.parentTableName = parentTableName;
		this.parentLinkColumnName = parentLinkColumnName;
	}

	private GenericZoomIntoTableInfo(
			@NonNull final GenericZoomIntoTableInfo from,
			@NonNull final CustomizedWindowInfoMap customizedWindowInfoMap)
	{
		this.tableName = from.tableName;
		this.keyColumnNames = from.keyColumnNames;
		this.hasIsSOTrxColumn = from.hasIsSOTrxColumn;

		this.defaultWindow = withCustomizedWindow(from.defaultWindow, customizedWindowInfoMap);
		this.defaultSOWindow = withCustomizedWindow(from.defaultSOWindow, customizedWindowInfoMap);
		this.defaultPOWindow = withCustomizedWindow(from.defaultPOWindow, customizedWindowInfoMap);
		this.otherWindows = from.otherWindows
				.stream()
				.map(window -> withCustomizedWindow(window, customizedWindowInfoMap))
				.collect(ImmutableList.toImmutableList());
		this.parentTableName = from.parentTableName;
		this.parentLinkColumnName = from.parentLinkColumnName;
	}

	@Nullable
	private static GenericZoomIntoTableWindow withCustomizedWindow(
			@Nullable final GenericZoomIntoTableWindow window,
			@NonNull final CustomizedWindowInfoMap customizedWindowInfoMap)
	{
		if (window == null)
		{
			return null;
		}

		final CustomizedWindowInfo customizedWindow = customizedWindowInfoMap.getCustomizedWindowInfo(window.getAdWindowId()).orElse(null);
		if (customizedWindow == null)
		{
			return window;
		}

		return window.withAdWindowId(customizedWindow.getCustomizationWindowId());
	}

	public GenericZoomIntoTableInfo withCustomizedWindowIds(@NonNull final CustomizedWindowInfoMap customizedWindowInfoMap)
	{
		return new GenericZoomIntoTableInfo(this, customizedWindowInfoMap);
	}

	public String getSingleKeyColumnName()
	{
		if (keyColumnNames.size() != 1)
		{
			throw new AdempiereException("Table does not have a single key column: " + this);
		}
		return keyColumnNames.iterator().next();
	}
}
