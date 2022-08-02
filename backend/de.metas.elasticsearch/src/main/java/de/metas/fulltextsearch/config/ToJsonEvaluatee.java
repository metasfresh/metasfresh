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
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Evaluatee;
import org.compiere.util.ForwardingEvaluatee;

import javax.annotation.Nullable;
import java.util.HashSet;

@ToString
public class ToJsonEvaluatee extends ForwardingEvaluatee
{
	private final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();
	private final HashSet<String> skipConvertingToJsonVariableNames = new HashSet<>();

	public ToJsonEvaluatee(@NonNull final Evaluatee delegate)
	{
		super(delegate);
	}

	public void skipConvertingToJson(@NonNull final String variableName)
	{
		this.skipConvertingToJsonVariableNames.add(variableName);
	}

	@Nullable
	@Override
	public String get_ValueAsString(final String variableName)
	{
		final Object valueObj = super.get_ValueAsObject(variableName);
		if (skipConvertingToJsonVariableNames.contains(variableName))
		{
			return valueObj != null ? valueObj.toString() : "";
		}
		else
		{
			return toJsonString(valueObj);
		}
	}

	private String toJsonString(@Nullable final Object valueObj)
	{
		if (valueObj == null)
		{
			return "null";
		}

		try
		{
			return jsonObjectMapper.writeValueAsString(valueObj);
		}
		catch (final JsonProcessingException ex)
		{
			throw new AdempiereException("Cannot convert `" + valueObj + "` to JSON", ex);
		}
	}
}
