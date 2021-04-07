package de.metas.elasticsearch.config;

import com.google.common.collect.ImmutableList;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.elasticsearch
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
public class FTSIndexConfig
{
	int id;

	@NonNull
	String indexName;

	@NonNull
	String tableName;

	@NonNull
	@Singular
	ImmutableList<FTSIndexIncludeConfig> indexIncludes;

	@NonNull
	FTSIndexTemplate template;

	public String getSettingsJson()
	{
		return getTemplate().getSettingsJson();
	}

	public ESTextAnalyzer getIndexStringFullTextSearchAnalyzer()
	{
		return getTemplate().getIndexStringFullTextSearchAnalyzer();
	}

	public ESModelIndexerId getESModelIndexerId()
	{
		return ESModelIndexerId.builder()
				.indexName(getIndexName())
				.indexType(getTableName())
				.profile(ESModelIndexerProfile.FULL_TEXT_SEARCH)
				.build();
	}
}
