package de.metas.ui.web.window.datatypes.json;

import java.util.List;

import org.adempiere.util.GuavaCollectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.model.DocumentQueryFilterParam;

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

public class JSONDocumentQueryFilterParam
{
	public static List<DocumentQueryFilterParam> unwrapList(final List<JSONDocumentQueryFilterParam> jsonFilterParams)
	{
		if (jsonFilterParams == null || jsonFilterParams.isEmpty())
		{
			return ImmutableList.of();
		}
		return jsonFilterParams
				.stream()
				.map(jsonFilter -> unwrap(jsonFilter))
				.filter(filter -> filter != null)
				.collect(GuavaCollectors.toImmutableList());
	}

	private static final DocumentQueryFilterParam unwrap(final JSONDocumentQueryFilterParam jsonFilterParam)
	{
		return DocumentQueryFilterParam.builder()
				.setFieldName(jsonFilterParam.getField())
				.setValue(jsonFilterParam.getValue())
				.setValueTo(jsonFilterParam.getValueTo())
				.build();
	}

	@JsonProperty("field")
	private final String field;

	@JsonProperty("value")
	private final Object value;

	@JsonProperty("valueTo")
	private final Object valueTo;

	@JsonCreator
	public JSONDocumentQueryFilterParam(
			@JsonProperty("field") final String field //
			, @JsonProperty("value") final Object value //
			, @JsonProperty("valueTo") final Object valueTo //
	)
	{
		super();
		this.field = field;
		this.value = value;
		this.valueTo = valueTo;
	}

	public String getField()
	{
		return field;
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
