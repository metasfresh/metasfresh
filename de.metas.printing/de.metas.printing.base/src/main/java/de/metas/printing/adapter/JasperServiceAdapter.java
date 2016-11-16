package de.metas.printing.adapter;

/*
 * #%L
 * de.metas.printing.base
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


import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperPrint;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.PrintInfo;
import org.compiere.report.AbstractJasperService;
import org.compiere.report.IJasperService;

import de.metas.printing.model.I_AD_Archive;
import de.metas.process.ProcessInfo;



/**
 * Adapt the old school Jasper Printing Service ( {@link IJasperService}) to our printing module.
 * 
 * @author tsa
 *
 */
public final class JasperServiceAdapter extends AbstractJasperService
{
	public static final transient JasperServiceAdapter instance = new JasperServiceAdapter();

	private JasperServiceAdapter()
	{
		super();
	}

	/**
	 * Exports the given data to PDF and creates an <code>AD_Archive</code> with <code>IsDirectPrint='Y'</code> (to trigger a <code>C_Printing_Queue</code> record being created) and
	 * <code>IsCreatePrintJob='Y'</code> to also trigger a direct <code>C_PrintJob</code> creation.
	 * 
	 */
	@Override
	public void print(final JasperPrint jasperPrint, final ProcessInfo pi, final boolean displayDialog)
	{
		Check.assumeNotNull(jasperPrint, "jasperPrint not null");
		Check.assumeNotNull(pi, "pi not null");

		// services
		final IArchiveBL archiveService = Services.get(IArchiveBL.class);

		final PrintInfo printInfo = extractPrintInfo(pi);

		final byte[] exportData = exportToPdf(jasperPrint);

		//
		// Create the archive
		
		// archive it even if AutoArchive says no
		final boolean forceArchiving = true; 
		
		// task 09752: don't let the API save it, 
		// because we want to first set the two flags below, and we want the model interceptor to be fired only once
		final boolean save = false; 
		
		final String trxName = ITrx.TRXNAME_None;
		final I_AD_Archive archive = InterfaceWrapperHelper.create(
				archiveService.archive(exportData, printInfo, forceArchiving, save, trxName),
				I_AD_Archive.class);
		Check.assumeNotNull(archive, "archive not null");

		//
		// Ask our printing service to printing it right now
		archive.setIsDirectEnqueue(true);
		archive.setIsCreatePrintJob(true); // create the print job not only enqueue to printing queue

		//
		// Save archive. This will trigger the printing...
		InterfaceWrapperHelper.save(archive);
		logger.debug("Archive: {}", archive);
	}

	@Override
	public void setJrPrintExporter(final JRExporter jrExporter)
	{
		throw new UnsupportedOperationException();
	}
}
