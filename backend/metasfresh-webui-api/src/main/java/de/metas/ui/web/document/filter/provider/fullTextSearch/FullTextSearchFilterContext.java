package de.metas.ui.web.document.filter.provider.fullTextSearch;

import org.adempiere.model.InterfaceWrapperHelper;
import org.elasticsearch.client.Client;

import com.google.common.collect.ImmutableSet;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
@Builder
public class FullTextSearchFilterContext
{
	@NonNull
	final Client elasticsearchClient;
	@NonNull
	final String modelTableName;
	@NonNull
	final String esIndexName;
	@NonNull
	@Singular
	final ImmutableSet<String> esSearchFieldNames;

	public String[] getEsSearchFieldNamesAsArray()
	{
		return esSearchFieldNames.toArray(new String[esSearchFieldNames.size()]);
	}

	public String getKeyColumnName()
	{
		return InterfaceWrapperHelper.getKeyColumnName(getModelTableName());
	}

	public String getEsKeyColumnName()
	{
		return getKeyColumnName();
	}

}
