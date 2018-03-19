package de.metas.datev;

import java.util.List;

import org.adempiere.util.Check;

import com.google.common.collect.ImmutableList;

import lombok.Builder;
import lombok.Singular;
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
public class DATEVExportFormat
{
	int id;
	String name;
	String csvEncoding;
	String csvFieldDelimiter;
	String csvFieldQuote;
	List<DATEVExportFormatColumn> columns;

	@Builder
	private DATEVExportFormat(
			final int id,
			final String name,
			final String csvEncoding,
			final String csvFieldDelimiter,
			final String csvFieldQuote,
			@Singular final List<DATEVExportFormatColumn> columns)
	{
		Check.assumeNotEmpty(name, "name is not empty");
		Check.assumeNotEmpty(columns, "columns is not empty");

		this.id = id;
		this.name = name;
		this.csvEncoding = !Check.isEmpty(csvEncoding, true) ? csvEncoding : "UTF-8";
		this.csvFieldDelimiter = csvFieldDelimiter != null ? csvFieldDelimiter : "\t";
		this.csvFieldQuote = !Check.isEmpty(csvFieldQuote, true) ? csvFieldQuote : "";
		this.columns = ImmutableList.copyOf(columns);
	}

}
