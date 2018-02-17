package de.metas.datev;

import java.time.format.DateTimeFormatter;

import org.adempiere.util.Check;
import org.adempiere.util.ThreadLocalDecimalFormatter;

import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * metasfresh-datev
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

@Value
public class DATEVExportFormatColumn
{
	String columnName;
	String csvHeaderName;
	DateTimeFormatter dateFormatter;
	ThreadLocalDecimalFormatter numberFormatter;

	@Builder
	private DATEVExportFormatColumn(
			final String columnName,
			final String csvHeaderName,
			DateTimeFormatter dateFormatter,
			ThreadLocalDecimalFormatter numberFormatter)
	{
		Check.assumeNotEmpty(columnName, "columnName is not empty");
		Check.assumeNotEmpty(csvHeaderName, "csvHeaderName is not empty");

		this.columnName = columnName;
		this.csvHeaderName = csvHeaderName;
		this.dateFormatter = dateFormatter;
		this.numberFormatter = numberFormatter;
	}

}
