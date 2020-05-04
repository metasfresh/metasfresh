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
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.adempiere.exceptions.AdempiereException;

import de.metas.data.export.api.IExportDataSource;
import de.metas.data.export.api.IExporter;
import de.metas.data.export.api.IExporterMonitor;
import de.metas.util.Check;

/**
 * Execute asynchronously given {@link IExporter} and monitors it's state.
 * 
 * @author tsa
 * 
 */
public class ASyncExporterWrapper implements IExporter
{
	private final IExporter exporter;

	private Future<?> futureExportResult;
	private ExecutorService executorService;

	public ASyncExporterWrapper(final IExporter exporter)
	{
		super();
		this.exporter = exporter;
	}

	@Override
	public void export(final OutputStream out)
	{
		executeAsync(out);
	}

	@Override
	public void setDataSource(final IExportDataSource dataSource)
	{
		exporter.setDataSource(dataSource);
	}

	@Override
	public IExportDataSource getDataSource()
	{
		return exporter.getDataSource();
	}

	@Override
	public void setConfig(final Properties config)
	{
		exporter.setConfig(config);
	}

	@Override
	public void setMonitor(IExporterMonitor monitor)
	{
		exporter.setMonitor(monitor);
	}

	@Override
	public int getExportedRowCount()
	{
		return exporter.getExportedRowCount();
	}

	@Override
	public ExportStatus getExportStatus()
	{
		return exporter.getExportStatus();
	}

	@Override
	public Throwable getError()
	{
		return exporter.getError();
	}

	/**
	 * Execute export asynchronous and return the online {@link InputStream} with the result. Internally {@link PipedInputStream} and {@link PipedOutputStream} will be used.
	 * 
	 * @return online {@link InputStream} to be used for reading the exported data
	 */
	public InputStream export()
	{
		final PipedOutputStream pipeOut = new PipedOutputStream();

		PipedInputStream pipeIn = null;
		try
		{
			pipeIn = new PipedInputStream(pipeOut, 1024 * 16);
		}
		catch (IOException e)
		{
			throw new AdempiereException(e.getLocalizedMessage(), e);
		}

		executeAsync(pipeOut);
		return pipeIn;
	}

	/**
	 * Prepare datasource first.
	 * 
	 * This part will be run synchronously so we will make sure that we will get here the first error and also no InputStream will be sent to file downloaders or other streaming tools
	 */
	private void prepareDataSource()
	{
		IExportDataSource dataSource = exporter.getDataSource();
		boolean dataSourcePrepared = false;
		try
		{
			dataSource.prepare();

			// Make sure we have something to export
			final int size = dataSource.size();
			if (size <= 0)
			{
				throw new AdempiereException("No data found");
			}

			dataSourcePrepared = true;
		}
		finally
		{
			if (!dataSourcePrepared)
			{
				try
				{
					dataSource.close();
				}
				catch (Exception e)
				{
					// do nothing;
				}
			}
		}

	}

	public void executeAsync(final OutputStream out)
	{
		Check.assume(executorService == null, "No other execution started: {}", executorService);

		prepareDataSource();

		//
		// Prepare & execute asynchronously
		executorService = Executors.newFixedThreadPool(2);

		final Runnable exportRunnable = new Runnable()
		{

			@Override
			public void run()
			{
				exporter.export(out);
			}
		};

		final Runnable exportMonitor = new Runnable()
		{

			@Override
			public void run()
			{
				int prevRowCount = exporter.getExportedRowCount();

				ExportStatus exportStatus = exporter.getExportStatus();
				while (exportStatus != ExportStatus.Finished)
				{
					try
					{
						Thread.sleep(1000 * 10); // wait 10sec
					}
					catch (InterruptedException e)
					{
						// interruption required, just stop it
						break;
					}

					exportStatus = exporter.getExportStatus();
					final int rowCount = exporter.getExportedRowCount();

					if (exportStatus == ExportStatus.Finished)
					{
						break;
					}

					if (prevRowCount >= rowCount)
					{
						// the export thread is stagnating... we will need to stop everything
						break;
					}
					prevRowCount = rowCount;
				}

				executorService.shutdownNow();
			}
		};

		futureExportResult = executorService.submit(exportRunnable);
		executorService.submit(exportMonitor);
	}

	public void waitToFinish()
	{
		try
		{
			futureExportResult.get();
		}
		catch (InterruptedException e)
		{
			throw new AdempiereException(e.getLocalizedMessage(), e);
		}
		catch (ExecutionException e)
		{
			Throwable ex = e.getCause();
			if (ex == null)
			{
				ex = e;
			}

			throw ex instanceof AdempiereException ? (AdempiereException)ex : new AdempiereException(ex.getLocalizedMessage(), ex); // NOPMD by tsa on 3/15/13 7:44 PM
		}
		finally
		{
			executorService.shutdownNow();
			executorService = null;
		}
	}
}
