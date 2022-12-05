/*
 * #%L
 * de.metas.monitoring
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.monitoring.adapter;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.adempiere.util.lang.IAutoCloseable;

import javax.annotation.Nullable;
import java.util.ArrayList;

@EqualsAndHashCode
@ToString
public class PerformanceMonitoringData
{
	private final ArrayList<PerformanceMonitoringService.Metadata> calledBy = new ArrayList<>();

	public String getInitiatorFunctionNameFQ()
	{
		for (final PerformanceMonitoringService.Metadata metadata : calledBy)
		{
			if (!metadata.isGroupingPlaceholder())
			{
				return metadata.getFunctionNameFQ();
			}
		}
		return !calledBy.isEmpty() ? calledBy.get(0).getFunctionNameFQ() : "";
	}

	@Nullable
	public String getLastCalledFunctionNameFQ()
	{
		if (calledBy.isEmpty())
		{
			return null;
		}
		else
		{
			return calledBy.get(calledBy.size() - 1).getFunctionNameFQ();
		}
	}

	public String getInitiatorWindow()
	{
		final String windowName = !calledBy.isEmpty() ? calledBy.get(0).getWindowNameAndId() : "";
		return windowName != null ? windowName : "NONE";
	}

	public boolean isInitiator() {return calledBy.isEmpty();}

	public PerformanceMonitoringService.Type getEffectiveType(final PerformanceMonitoringService.Metadata metadata)
	{
		return !calledBy.isEmpty() ? calledBy.get(0).getType() : metadata.getType();
	}

	public int getDepth() {return calledBy.size();}

	public IAutoCloseable addCalledByIfNotNull(@Nullable final PerformanceMonitoringService.Metadata metadata)
	{
		if (metadata == null)
		{
			return () -> {};
		}

		final int sizeBeforeAdd = calledBy.size();
		calledBy.add(metadata);

		return () -> {
			while (calledBy.size() > sizeBeforeAdd)
			{
				calledBy.remove(calledBy.size() - 1);
			}
		};

	}
}
