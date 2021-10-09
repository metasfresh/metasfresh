/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.ui.web.window.descriptor;

import com.google.common.collect.ImmutableSet;
import de.metas.adempiere.service.impl.TooltipType;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFetcher;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

public interface LookupDescriptor
{
	default Optional<String> getTableName()
	{
		return Optional.empty();
	}

	default Optional<WindowId> getZoomIntoWindowId()
	{
		return Optional.empty();
	}

	@Override
	boolean equals(Object obj);

	@Override
	int hashCode();

	LookupDataSourceFetcher getLookupDataSourceFetcher();

	boolean isHighVolume();

	LookupSource getLookupSourceType();

	boolean hasParameters();

	boolean isNumericKey();

	Set<String> getDependsOnFieldNames();

	default Set<String> getDependsOnTableNames()
	{
		return ImmutableSet.of();
	}

	default Class<?> getValueClass()
	{
		return isNumericKey() ? IntegerLookupValue.class : StringLookupValue.class;
	}

	default int getSearchStringMinLength()
	{
		return -1;
	}

	default Optional<Duration> getSearchStartDelay()
	{
		return Optional.empty();
	}

	default <T extends LookupDescriptor> T cast(final Class<T> lookupDescriptorClass)
	{
		@SuppressWarnings("unchecked")
		final T thisCasted = (T)this;
		return thisCasted;
	}

	default <T extends LookupDescriptor> T castOrNull(final Class<T> lookupDescriptorClass)
	{
		if (lookupDescriptorClass.isAssignableFrom(getClass()))
		{
			@SuppressWarnings("unchecked")
			final T thisCasted = (T)this;
			return thisCasted;
		}

		return null;
	}

	default TooltipType getTooltipType()
	{
		return TooltipType.DEFAULT;
	}
}
