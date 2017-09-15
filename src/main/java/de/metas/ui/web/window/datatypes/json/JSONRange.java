package de.metas.ui.web.window.datatypes.json;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.window.datatypes.DateRangeValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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
public class JSONRange
{
	public static final JSONRange of(final DateRangeValue range)
	{
		final Date from = range.getFrom();
		final String jsonFrom = from != null ? JSONDate.toJson(from) : null;

		final Date to = range.getTo();
		final String jsonTo = to != null ? JSONDate.toJson(to) : null;

		return new JSONRange(jsonFrom, jsonTo);
	}

	public static DateRangeValue dateRangeFromJSONMap(final Map<String, String> map)
	{
		final String jsonFrom = map.get("value");
		final Date from = Check.isEmpty(jsonFrom) ? null : JSONDate.fromJson(jsonFrom, DocumentFieldWidgetType.Date);

		final String jsonTo = map.get("valueTo");
		final Date to = Check.isEmpty(jsonTo) ? null : JSONDate.fromJson(jsonTo, DocumentFieldWidgetType.Date);

		return DateRangeValue.of(from, to);
	}

	@JsonProperty("value")
	private final String value;
	@JsonProperty("valueTo")
	private final String valueTo;

	@JsonCreator
	@Builder
	private JSONRange(@JsonProperty("value") final String value, @JsonProperty("valueTo") final String valueTo)
	{
		this.value = value;
		this.valueTo = valueTo;
	}
}
