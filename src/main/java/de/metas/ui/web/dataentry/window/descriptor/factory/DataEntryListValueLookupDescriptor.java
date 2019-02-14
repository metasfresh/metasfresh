package de.metas.ui.web.dataentry.window.descriptor.factory;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;
import de.metas.dataentry.layout.DataEntryListValue;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFetcher;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class DataEntryListValueLookupDescriptor implements LookupDescriptor
{
	private final DataEntryListValueDataSourceFetcher dataEntryListValueDataSourceFetcher;

	public static DataEntryListValueLookupDescriptor of(@NonNull final List<DataEntryListValue> listValues)
	{
		return new DataEntryListValueLookupDescriptor(listValues);
	}

	private DataEntryListValueLookupDescriptor(@NonNull final List<DataEntryListValue> listValues)
	{
		dataEntryListValueDataSourceFetcher = new DataEntryListValueDataSourceFetcher(listValues);
	}

	@Override
	public LookupDataSourceFetcher getLookupDataSourceFetcher()
	{
		return dataEntryListValueDataSourceFetcher;
	}

	@Override
	public boolean isHighVolume()
	{
		return false;
	}

	@Override
	public LookupSource getLookupSourceType()
	{
		return LookupSource.list;
	}

	@Override
	public boolean hasParameters()
	{
		return false;
	}

	@Override
	public boolean isNumericKey()
	{
		return true;
	}

	@Override
	public Set<String> getDependsOnFieldNames()
	{
		return ImmutableSet.of();
	}

}
