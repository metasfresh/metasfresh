package de.metas.data.export.api;

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


import java.io.OutputStream;
import java.util.Properties;

/**
 * Implementations of this interface will do the actual data export task in a particular document format.
 * 
 * @author tsa
 * 
 */
public interface IExporter
{
	public static enum ExportStatus
	{
		NotStarted,
		Running,
		Finished,
	}

	/**
	 * Sets data source to be used.
	 * 
	 * NOTE: don't call this method directly because it's called by factory API.
	 * 
	 * @param dataSource
	 */
	void setDataSource(IExportDataSource dataSource);

	/**
	 * Gets data source that was configured for this exporter
	 * 
	 * @return data source
	 */
	IExportDataSource getDataSource();

	/**
	 * Sets configuration parameters to be used.
	 * 
	 * NOTE: don't call this method directly because it's called by factory API.
	 * 
	 * @param config
	 */
	void setConfig(Properties config);

	/**
	 * Sets optional monitor to be used during the export process.
	 * 
	 * @param monitor
	 */
	void setMonitor(IExporterMonitor monitor);

	/**
	 * Execute the actual export
	 * 
	 * @param out
	 */
	void export(final OutputStream out);

	/**
	 * 
	 * @return current number of rows exported
	 */
	int getExportedRowCount();

	/**
	 * 
	 * @return current export status
	 */
	ExportStatus getExportStatus();

	/**
	 * Gets error that occurred during export process.
	 * 
	 * @return error or null if export was successful
	 */
	Throwable getError();
}
