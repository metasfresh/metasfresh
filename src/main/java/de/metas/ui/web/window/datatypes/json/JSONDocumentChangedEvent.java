package de.metas.ui.web.window.datatypes.json;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DisplayType;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.Values;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import io.swagger.annotations.ApiModel;
import lombok.Value;

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

/**
 * Document changed event.
 *
 * Event sent by frontend when the user wants to change some fields.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@ApiModel("document-change-event")
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class JSONDocumentChangedEvent
{
	@JsonCreator
	public static final JSONDocumentChangedEvent of(@JsonProperty("op") final JSONOperation operation, @JsonProperty("path") final String path, @JsonProperty("value") final Object value)
	{
		return new JSONDocumentChangedEvent(operation, path, value);
	}

	public static final JSONDocumentChangedEvent replace(final String fieldName, final Object valueJson)
	{
		return new JSONDocumentChangedEvent(JSONOperation.replace, fieldName, valueJson);
	}

	@ApiModel("operation")
	public static enum JSONOperation
	{
		replace;
	}

	@JsonProperty("op")
	private final JSONOperation operation;
	@JsonProperty("path")
	private final String path;
	@JsonProperty("value")
	private final Object value;

	public boolean isReplace()
	{
		return operation == JSONOperation.replace;
	}

	public String getValueAsString(final String defaultValueIfNull)
	{
		return value != null ? value.toString() : defaultValueIfNull;
	}

	public Boolean getValueAsBoolean(final Boolean defaultValue)
	{
		return DisplayType.toBoolean(value, defaultValue);
	}

	public int getValueAsInteger(final int defaultValueIfNull)
	{
		return Values.toInt(value, defaultValueIfNull);
	}

	public List<Integer> getValueAsIntegersList()
	{
		if (value == null)
		{
			return ImmutableList.of();
		}

		if (value instanceof List<?>)
		{
			@SuppressWarnings("unchecked")
			final List<Integer> intList = (List<Integer>)value;
			return intList;
		}
		else if (value instanceof String)
		{
			return ImmutableList.copyOf(DocumentIdsSelection.ofCommaSeparatedString(value.toString()).toIntSet());
		}
		else
		{
			throw new AdempiereException("Cannot convert value to int list").setParameter("event", this);
		}
	}

	public BigDecimal getValueAsBigDecimal()
	{
		return Values.toBigDecimal(value);
	}

	public java.util.Date getValueAsDateTime()
	{
		return JSONDate.fromObject(value, DocumentFieldWidgetType.DateTime);
	}

}
