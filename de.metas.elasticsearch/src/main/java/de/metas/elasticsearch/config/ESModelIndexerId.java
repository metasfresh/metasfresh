package de.metas.elasticsearch.config;

import java.util.List;

import org.adempiere.exceptions.AdempiereException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

import lombok.Builder;
import lombok.NonNull;
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

@Builder
@Value
public class ESModelIndexerId
{
	private static final String SEPARATOR = "#";

	@NonNull
	String indexName;
	@NonNull
	String modelTableName;
	@NonNull
	ESModelIndexerProfile profile;

	@JsonCreator
	public static ESModelIndexerId fromJson(final String str)
	{
		final List<String> parts = Splitter.on(SEPARATOR).splitToList(str);
		if (parts.size() != 3)
		{
			throw new AdempiereException("Cannot convert string '" + str + "' to " + ESModelIndexerId.class.getSimpleName());
		}

		return builder()
				.indexName(parts.get(0))
				.modelTableName(parts.get(1))
				.profile(ESModelIndexerProfile.valueOf(parts.get(2)))
				.build();
	}

	@JsonValue
	public String toJson()
	{
		return Joiner.on(SEPARATOR).join(indexName, modelTableName, profile.name());
	}

	public ESModelIndexerId includedModel(final String includedAttributeName)
	{
		// FIXME: derive a new ID. for now, it's OK to return this
		return this;
	}

	public String getIndexType()
	{
		return getModelTableName();
	}
}
