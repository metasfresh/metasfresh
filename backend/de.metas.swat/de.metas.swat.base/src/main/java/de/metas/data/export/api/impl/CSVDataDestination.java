/*
 * #%L
 * de.metas.swat.base
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

package de.metas.data.export.api.impl;

import de.metas.data.export.api.IExportDataDestination;
import de.metas.impexp.export.csv.CSVWriter;

public class CSVDataDestination implements IExportDataDestination
{
	public static CSVDataDestination cast(final IExportDataDestination dataDestination)
	{
		return (CSVDataDestination)dataDestination;
	}

	private final CSVWriter csvWriter;

	public CSVDataDestination(@NonNull final List CSVWriter csvWriter)
	{
		this.csvWriter = csvWriter;
	}

	@Override
	public void appendLine(final List<Object> values) throws IOException
	{
		csvWriter.appendLine(values);
	}

	@Override
	public void close() throws IOException
	{
		csvWriter.close();
	}
}
