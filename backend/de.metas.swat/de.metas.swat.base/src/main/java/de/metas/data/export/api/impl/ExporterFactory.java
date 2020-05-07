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


import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;

import de.metas.data.export.api.IExportDataSource;
import de.metas.data.export.api.IExporter;
import de.metas.data.export.api.IExporterFactory;
import de.metas.util.Check;

public class ExporterFactory implements IExporterFactory
{
	private final Map<String, Class<? extends IExporter>> exporterClasses = new HashMap<String, Class<? extends IExporter>>();

	public ExporterFactory()
	{
		// Register defaults
		registerExporter(MIMETYPE_CSV, CSVExporter.class);
	}

	@Override
	public IExporter createExporter(final String mimeType, final IExportDataSource dataSource, final Properties config)
	{
		Check.assumeNotNull(dataSource, "dataSource not null");

		final Class<? extends IExporter> exporterClass = exporterClasses.get(mimeType);
		if (exporterClass == null)
		{
			throw new AdempiereException("Exporter class not found for MimeType '" + mimeType + "'");
		}

		IExporter exporter = null;
		try
		{
			exporter = exporterClass.newInstance();
		}
		catch (Exception e)
		{
			throw new AdempiereException(e.getLocalizedMessage(), e);
		}

		exporter.setDataSource(dataSource);
		exporter.setConfig(config);

		return exporter;
	}

	@Override
	public void registerExporter(String mimeType, Class<? extends IExporter> exporterClass)
	{
		Check.assume(!Check.isEmpty(mimeType, true), "mimeType not empty");
		Check.assumeNotNull(exporterClass, "exporterClass not null");

		exporterClasses.put(mimeType.trim(), exporterClass);
		// TODO Auto-generated method stub

	}

}
