package de.metas.data.export.api.impl;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import de.metas.data.export.api.IExportDataDestination;
import de.metas.data.export.api.IExportDataSource;

/**
 * Export {@link IExportDataSource} to CSV format.
 * 
 * @author tsa
 * 
 */
/* package */class CSVExporter extends AbstractExporter
{
	public CSVExporter()
	{
		super();
	}

	@Override
	protected CSVWriter createDataDestination(final OutputStream out) throws UnsupportedEncodingException
	{
		final CSVWriter csvWriter = new CSVWriter(out, getConfig());
		csvWriter.setHeader(getDataSource().getFieldNames());
		return csvWriter;
	}

	@Override
	protected void appendRow(final IExportDataDestination dataDestination, final List<Object> values) throws IOException
	{
		final CSVWriter csvWriter = (CSVWriter)dataDestination;
		csvWriter.appendLine(values);
	}
}
