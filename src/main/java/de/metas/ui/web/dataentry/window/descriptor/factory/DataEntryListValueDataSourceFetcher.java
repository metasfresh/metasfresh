package de.metas.ui.web.dataentry.window.descriptor.factory;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.compiere.util.CtxName;
import org.compiere.util.CtxNames;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.dataentry.DataEntryFieldId;
import de.metas.dataentry.DataEntryListValue;
import de.metas.dataentry.DataEntryListValueId;
import de.metas.dataentry.model.I_DataEntry_ListValue;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext.Builder;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFetcher;
import de.metas.util.collections.CollectionUtils;
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

public class DataEntryListValueDataSourceFetcher implements LookupDataSourceFetcher
{

	private static final CtxName CTX_NAME_FIELD_ID = CtxNames.ofNameAndDefaultValue(I_DataEntry_ListValue.COLUMNNAME_DataEntry_Field_ID, "-1");
	private static final CtxName CTX_NAME_LIST_VALUE_ID = CtxNames.ofNameAndDefaultValue(I_DataEntry_ListValue.COLUMNNAME_DataEntry_ListValue_ID, "-1");

	private static final String LOOKUP_TABLE_NAME = I_DataEntry_ListValue.Table_Name;
	private final ImmutableSet<CtxName> dependsOnContextVariables = ImmutableSet.of(CTX_NAME_FIELD_ID);

	private final ImmutableMap<DataEntryListValueId, IntegerLookupValue> id2listValue;

	private final DataEntryFieldId dataEntryFieldId;

	public DataEntryListValueDataSourceFetcher(@NonNull final List<DataEntryListValue> listValues)
	{
		this.dataEntryFieldId = CollectionUtils.extractSingleElement(listValues, DataEntryListValue::getDataEntryFieldId);

		final ImmutableMap.Builder<DataEntryListValueId, IntegerLookupValue> map = ImmutableMap.builder();
		for (final DataEntryListValue listValue : listValues)
		{
			map.put(listValue.getId(), createLookupValue(listValue));
		}
		this.id2listValue = map.build();
	}

	@Override
	public boolean isNumericKey()
	{
		return false;
	}

	@Override
	public Builder newContextForFetchingById(Object id)
	{
		return LookupDataSourceContext.builder(LOOKUP_TABLE_NAME);
	}

	@Override
	public Builder newContextForFetchingList()
	{
		return prepareNewContext()
				.requiresParameters(dependsOnContextVariables)
				.requiresFilterAndLimit();
	}

	private LookupDataSourceContext.Builder prepareNewContext()
	{
		return LookupDataSourceContext.builder(LOOKUP_TABLE_NAME);
	}

	@Override
	public LookupValue retrieveLookupValueById(@NonNull final LookupDataSourceContext evalCtx)
	{
		final Integer listValueIdAsInt = CTX_NAME_LIST_VALUE_ID.getValueAsInteger(evalCtx);
		if (listValueIdAsInt <= 0)
		{
			return null;
		}

		final DataEntryListValueId listValueId = DataEntryListValueId.ofRepoId(listValueIdAsInt);

		return id2listValue.get(listValueId);
	}

	@Override
	public LookupValuesList retrieveEntities(@NonNull final LookupDataSourceContext evalCtx)
	{
		final Integer fieldIdAsInt = CTX_NAME_FIELD_ID.getValueAsInteger(evalCtx);
		if (fieldIdAsInt <= 0)
		{
			return LookupValuesList.EMPTY;
		}
		final DataEntryFieldId dataEntryFieldId = DataEntryFieldId.ofRepoId(fieldIdAsInt);
		if (!Objects.equals(dataEntryFieldId, this.dataEntryFieldId))
		{
			return LookupValuesList.EMPTY;
		}

		return LookupValuesList.fromCollection(id2listValue.values());
	}

	private IntegerLookupValue createLookupValue(@NonNull final DataEntryListValue dataEntryListValue)
	{
		return IntegerLookupValue.of(
				dataEntryListValue.getId(),
				dataEntryListValue.getName(),
				dataEntryListValue.getDescription());
	}

	@Override
	public boolean isCached()
	{
		return true;
	}

	@Override
	public String getCachePrefix()
	{
		return DataEntryListValueDataSourceFetcher.class.getSimpleName();
	}

	@Override
	public Optional<String> getLookupTableName()
	{
		return Optional.of(LOOKUP_TABLE_NAME);
	}

	@Override
	public Optional<WindowId> getZoomIntoWindowId()
	{
		return Optional.empty();
	}

	/** Does nothing; this class doesn't use a cache; it is disposed as one. */
	@Override
	public void cacheInvalidate()
	{
	}
}
