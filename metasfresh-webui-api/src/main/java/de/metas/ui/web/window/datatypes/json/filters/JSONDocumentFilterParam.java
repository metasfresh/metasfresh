package de.metas.ui.web.window.datatypes.json.filters;

import java.io.Serializable;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

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
	/**
	 * Creates {@link JSONDocumentFilterParam} from {@link DocumentFilterParam} if the given filter is not internal.
	 * 
	 * @param filterParam
	 * @return JSON document filter parameter
	 */
	/* package */static final Optional<JSONDocumentFilterParam> of(final DocumentFilterParam filterParam)
	{
		// Don't convert internal filters
		if (filterParam.isSqlFilter())
		{
			// throw new IllegalArgumentException("Sql filters are not allowed to be converted to JSON filters: " + filterParam);
			return Optional.empty();
		}

		final JSONDocumentFilterParam jsonFilterParam = new JSONDocumentFilterParam(filterParam.getFieldName(), filterParam.getValue(), filterParam.getValueTo());
		return Optional.of(jsonFilterParam);
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

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("parameterName", parameterName)
				.add("value", value)
				.add("valueTo", valueTo)
				.toString();
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
