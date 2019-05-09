package de.metas.dataentry.rest_api;

import java.util.function.Function;

import org.adempiere.ad.element.api.AdWindowId;

import de.metas.cache.CCache;
import de.metas.dataentry.model.I_DataEntry_Field;
import de.metas.dataentry.model.I_DataEntry_Line;
import de.metas.dataentry.model.I_DataEntry_ListValue;
import de.metas.dataentry.model.I_DataEntry_Record;
import de.metas.dataentry.model.I_DataEntry_Section;
import de.metas.dataentry.model.I_DataEntry_SubTab;
import de.metas.dataentry.model.I_DataEntry_Tab;
import de.metas.dataentry.rest_api.dto.JsonDataEntry;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business.rest-api
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

public class JsonDataEntryCache
{
	private final CCache<CacheKey, JsonDataEntry> cache;

	public JsonDataEntryCache(final int cacheCapacity)
	{
		cache = CCache.<CacheKey, JsonDataEntry> builder()
				.initialCapacity(cacheCapacity)
				.additionalTableNameToResetFor(I_DataEntry_Tab.Table_Name)
				.additionalTableNameToResetFor(I_DataEntry_SubTab.Table_Name)
				.additionalTableNameToResetFor(I_DataEntry_Section.Table_Name)
				.additionalTableNameToResetFor(I_DataEntry_Line.Table_Name)
				.additionalTableNameToResetFor(I_DataEntry_Field.Table_Name)
				.additionalTableNameToResetFor(I_DataEntry_Record.Table_Name)
				.additionalTableNameToResetFor(I_DataEntry_ListValue.Table_Name)
				.build();
	}

	public JsonDataEntry getOrLoad(
			@NonNull final CacheKey key,
			@NonNull final Function<CacheKey, JsonDataEntry> dataEntryLoader)
	{
		return cache.getOrLoad(key, dataEntryLoader);
	}

	@Value
	@Builder
	public static class CacheKey
	{
		@NonNull
		final AdWindowId windowId;

		int recordId;

		@NonNull
		String adLanguage;
	}

}
