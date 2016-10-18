package de.metas.ui.web.window.datatypes.json.filters;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.ui.web.window.model.filters.DocumentFilterParam;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@SuppressWarnings("serial")
final class JSONDocumentFilterParam implements Serializable
{
	/* package */static final JSONDocumentFilterParam of(final DocumentFilterParam filterParam)
	{
		if(filterParam.isSqlFilter())
		{
			throw new IllegalArgumentException("Sql filters are not allowed to be converted to JSON filters: " + filterParam);
		}
		return new JSONDocumentFilterParam(filterParam.getFieldName(), filterParam.getValue(), filterParam.getValueTo());
	}

	@JsonProperty("parameterName")
	private final String parameterName;

	@JsonProperty("value")
	private final Object value;

	@JsonProperty("valueTo")
	private final Object valueTo;

	@JsonCreator
	private JSONDocumentFilterParam(
			@JsonProperty("parameterName") final String parameterName //
			, @JsonProperty("value") final Object value //
			, @JsonProperty("valueTo") final Object valueTo //
	)
	{
		super();
		this.parameterName = parameterName;
		this.value = value;
		this.valueTo = valueTo;
	}

	public String getParameterName()
	{
		return parameterName;
	}

	public Object getValue()
	{
		return value;
	}

	public Object getValueTo()
	{
		return valueTo;
	}
}
