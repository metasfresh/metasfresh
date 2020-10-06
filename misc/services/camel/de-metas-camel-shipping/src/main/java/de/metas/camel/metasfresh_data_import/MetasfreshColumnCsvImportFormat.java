package de.metas.camel.metasfresh_data_import;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.annotation.Nullable;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de-metas-camel-shipping
 * %%
 * Copyright (C) 2020 metas GmbH
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
public class MetasfreshColumnCsvImportFormat
{
	@NonNull
	String columnName;

	public enum DataType
	{
		STRING, NUMBER, DATE
	}

	@NonNull
	DataType dataType;

	@Nullable
	DateTimeFormatter dateFormat;

	@Builder
	private MetasfreshColumnCsvImportFormat(
			@NonNull final String columnName,
			@NonNull final DataType dataType,
			@Nullable final DateTimeFormatter dateFormat)
	{
		this.columnName = columnName;
		this.dataType = dataType;
		this.dateFormat = dateFormat;

		if (dataType == DataType.DATE && dateFormat == null)
		{
			throw new IllegalArgumentException("dateFormat shall be provided when dataType is " + dataType);
		}
	}

	public String formatValue(final Object value)
	{
		if (value == null)
		{
			return null;
		}

		if (dataType == DataType.STRING)
		{
			return value.toString();
		}
		else if (dataType == DataType.NUMBER)
		{
			return value.toString();
		}
		else if (dataType == DataType.DATE)
		{
			if (value instanceof LocalDate)
			{
				final LocalDate localDate = (LocalDate)value;
				return localDate.format(dateFormat);
			}
			else
			{
				return value.toString();
			}
		}
		else
		{
			throw new IllegalStateException("dataType `" + dataType + "` is not handled");
		}
	}
}
