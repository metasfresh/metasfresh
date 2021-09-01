/*
 * #%L
 * de.metas.elasticsearch
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

package de.metas.fulltextsearch.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.metas.JsonObjectMapperHolder;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Value
public class ESDocumentToIndex
{
	@NonNull String documentId;
	@NonNull String json;

	@Builder
	private ESDocumentToIndex(
			@NonNull final String documentId,
			@NonNull final String json)
	{
		this.documentId = documentId;
		this.json = json;
	}

	public Set<ESFieldName> getESFieldNames()
	{
		try
		{
			final Map<String, Object> map = JsonObjectMapperHolder.sharedJsonObjectMapper().readValue(json, Map.class);

			final HashSet<ESFieldName> fieldNames = new HashSet<>();
			fieldNames.add(ESFieldName.ID);
			map.keySet().stream().map(ESFieldName::ofString).forEach(fieldNames::add);
			return fieldNames;
		}
		catch (final JsonProcessingException ex)
		{
			throw new AdempiereException("Invalid JSON: " + json, ex);
		}

	}
}
