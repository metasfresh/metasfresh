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
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Value
public class GenericZoomIntoTableInfo
{
	String tableName;
	String keyColumnName;
	boolean hasIsSOTrxColumn;

	GenericZoomIntoTableWindow defaultWindow;
	GenericZoomIntoTableWindow defaultSOWindow;
	GenericZoomIntoTableWindow defaultPOWindow;
	ImmutableList<GenericZoomIntoTableWindow> otherWindows;

	@Builder
	private GenericZoomIntoTableInfo(
			@NonNull final String tableName,
			@NonNull final String keyColumnName,
			final boolean hasIsSOTrxColumn,
			@NonNull final List<GenericZoomIntoTableWindow> windows)
	{
		this.tableName = tableName;
		this.keyColumnName = keyColumnName;
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
		else if(!otherWindows.isEmpty())
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
	}
}
