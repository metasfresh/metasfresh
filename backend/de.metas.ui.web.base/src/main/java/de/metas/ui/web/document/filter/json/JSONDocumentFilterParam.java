package de.metas.ui.web.document.filter.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.window.datatypes.Values;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import lombok.Builder;
import lombok.Value;

import java.util.Optional;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class JSONDocumentFilterParam
{
	/**
	 * Creates {@link JSONDocumentFilterParam} from {@link DocumentFilterParam} if the given filter is not internal.
	 * 
	 * @return JSON document filter parameter
	 */
	/* package */static Optional<JSONDocumentFilterParam> of(final DocumentFilterParam filterParam, final JSONOptions jsonOpts)
	{
		// Don't convert internal filters
		if (filterParam.isSqlFilter())
		{
			// throw new IllegalArgumentException("Sql filters are not allowed to be converted to JSON filters: " + filterParam);
			return Optional.empty();
		}

		final String fieldName = filterParam.getFieldName();
		final Object jsonValue = Values.valueToJsonObject(filterParam.getValue(), jsonOpts);
		final Object jsonValueTo = Values.valueToJsonObject(filterParam.getValueTo(), jsonOpts);
		final JSONDocumentFilterParam jsonFilterParam = new JSONDocumentFilterParam(fieldName, jsonValue, jsonValueTo);
		return Optional.of(jsonFilterParam);
	}

	@JsonProperty("parameterName")
	String parameterName;

	@JsonProperty("value")
	Object value;

	@JsonProperty("valueTo")
	Object valueTo;

	@JsonCreator
	@Builder
	private JSONDocumentFilterParam(
			@JsonProperty("parameterName") final String parameterName,
			@JsonProperty("value") final Object value,
			@JsonProperty("valueTo") final Object valueTo)
	{
		this.parameterName = parameterName;
		this.value = value;
		this.valueTo = valueTo;
	}
}
