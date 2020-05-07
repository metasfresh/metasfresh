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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.slf4j.Logger;

import de.metas.data.export.api.IExportDataDestination;
import de.metas.data.export.api.IExportDataSource;
import de.metas.data.export.api.IExporter;
import de.metas.data.export.api.IExporterMonitor;
import de.metas.logging.LogManager;

/**
 * Abstract implementation of {@link IExporter} which take care about statistic information and status.
 * 
 * NOTE: this implementation is thread safe
 * 
 * @author tsa
 * 
 */
public abstract class AbstractExporter implements IExporter
{
	private static final Logger logger = LogManager.getLogger(AbstractExporter.class);

	private IExportDataSource dataSource;
	private Properties config = new Properties();
	private IExporterMonitor monitor = NullExporterMonitor.instance;

	private final AtomicInteger exportedRowCount = new AtomicInteger(0);
	private ExportStatus exportStatus = ExportStatus.NotStarted;
	private Throwable error = null;

	@Override
	public void setDataSource(IExportDataSource dataSource)
	{
		Check.assumeNotNull(dataSource, "dataSource not null");
		this.dataSource = dataSource;
	}

	@Override
	public IExportDataSource getDataSource()
	{
		return dataSource;
	}

	@Override
	public void setConfig(final Properties config)
	{
		this.config = config;
	}

	protected Properties getConfig()
	{
		return config;
	}

	@Override
	public void setMonitor(IExporterMonitor monitor)
	{
		Check.assumeNotNull(monitor, "monitor not null");
		this.monitor = monitor;
	}

	@Override
	public int getExportedRowCount()
	{
		return exportedRowCount.intValue();
	}

	protected int incrementExportedRowCount()
	{
		return exportedRowCount.incrementAndGet();
	}

	@Override
	public ExportStatus getExportStatus()
	{
		return exportStatus;
	}

	protected void setExportStatus(ExportStatus newStatus)
	{
		exportStatus = newStatus;
	}

	@Override
	public Throwable getError()
	{
		return error;
	}

	@Override
	public final void export(final OutputStream out)
	{
		Check.assumeNotNull(out, "out is not null");

		final IExportDataSource dataSource = getDataSource();
		Check.assumeNotNull(dataSource, "dataSource not null");

		Check.assume(ExportStatus.NotStarted.equals(getExportStatus()), "ExportStatus shall be " + ExportStatus.NotStarted + " and not {}", getExportStatus());

		IExportDataDestination dataDestination = null;
		try
		{
			// Init dataDestination
			// NOTE: make sure we are doing this as first thing, because if anything fails, we need to close this "destination"
			dataDestination = createDataDestination(out);

			// Init status
			error = null;
			setExportStatus(ExportStatus.Running);

			monitor.exportStarted(this);

			while (dataSource.hasNext())
			{
				final List<Object> values = dataSource.next();
				// for (int i = 1; i <= 3000; i++) // debugging
				// {
				appendRow(dataDestination, values);
				incrementExportedRowCount();
				// }
			}
		}
		catch (Exception e)
		{
			final AdempiereException ex = new AdempiereException("Error while exporting line " + getExportedRowCount() + ": " + e.getLocalizedMessage(), e);
			error = ex;
			throw ex;
		}
		finally
		{
			close(dataSource);
			close(dataDestination);
			dataDestination = null;

			setExportStatus(ExportStatus.Finished);

			// Notify the monitor but discard all exceptions because we don't want to throw an "false" exception in finally block
			try
			{
				monitor.exportFinished(this);
			}
			catch (Exception e)
			{
				logger.error("Error while invoking monitor(finish): " + e.getLocalizedMessage(), e);
			}
		}

		logger.info("Exported " + getExportedRowCount() + " rows");
	}

	protected abstract void appendRow(IExportDataDestination dataDestination, List<Object> values) throws IOException;

	protected abstract IExportDataDestination createDataDestination(OutputStream out) throws IOException;

	/**
	 * Close {@link Closeable} object.
	 * 
	 * NOTE: if <code>closeableObj</code> is not implementing {@link Closeable} or is null, nothing will happen
	 * 
	 * @param closeableObj
	 */
	private static final void close(Object closeableObj)
	{
		if (closeableObj instanceof Closeable)
		{
			final Closeable closeable = (Closeable)closeableObj;
			try
			{
				closeable.close();
			}
			catch (IOException e)
			{
				e.printStackTrace(); // NOPMD by tsa on 3/17/13 1:30 PM
			}
		}
	}

}
