package de.metas.printing.process;

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


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.collections.IteratorUtils;
import org.compiere.model.I_AD_Archive;
import org.compiere.util.Env;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;

import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.model.I_C_Print_Job;
import de.metas.printing.model.I_C_Print_Job_Line;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.process.SvrProcess;

public class ConcatenatePdfs extends SvrProcess
{

	private int printJobID;

	private File outputFile = null;

	private static final String SYSCONFIG_PdfDownloadPath = "de.metas.printing.process.ConcatenatePdfs.OutputDir";

	@Override
	protected void prepare()
	{
		if (I_C_Print_Job.Table_Name.equals(getTableName()) && getRecord_ID() > 0)
		{
			printJobID = getRecord_ID();
		}
	}

	@Override
	protected String doIt() throws IOException, DocumentException
	{
		final Properties ctx = Env.getCtx();
		final String trxName = ITrx.TRXNAME_None;

		final String outputDir = Services.get(ISysConfigBL.class).getValue(SYSCONFIG_PdfDownloadPath);
		final String fileName = "printjobs_" + getAD_PInstance_ID();

		final I_C_Print_Job job = InterfaceWrapperHelper.create(ctx, printJobID, I_C_Print_Job.class, trxName);

		final Iterator<I_C_Print_Job_Line> jobLines = Services.get(IPrintingDAO.class).retrievePrintJobLines(job);

		if (!jobLines.hasNext())
		{
			return "No print job lines found. Pdf not generated.";
		}

		final File file;
		if (Check.isEmpty(outputDir, true))
		{
			file = File.createTempFile(fileName, ".pdf");
		}
		else
		{
			file = new File(outputDir, fileName + ".pdf");
		}

		final Document document = new Document();

		final FileOutputStream fos = new FileOutputStream(file, false);
		final PdfCopy copy = new PdfCopy(document, fos);

		document.open();

		for (final I_C_Print_Job_Line jobLine : IteratorUtils.asIterable(jobLines))
		{
			final I_C_Printing_Queue queue = jobLine.getC_Printing_Queue();
			Check.assume(queue != null, jobLine + " references a C_Printing_Queue");

			final I_AD_Archive archive = queue.getAD_Archive();
			Check.assume(archive != null, queue + " references an AD_Archive record");

			final byte[] data = Services.get(IArchiveBL.class).getBinaryData(archive);
			final PdfReader reader = new PdfReader(data);

			for (int page = 0; page < reader.getNumberOfPages();)
			{
				copy.addPage(copy.getImportedPage(reader, ++page));
			}
			copy.freeReader(reader);
			reader.close();
		}
		document.close();
		fos.close();
		outputFile = new File(outputDir);

		return "@Created@ " + fileName + ".pdf" + " in " + outputDir;
	}

	public File getOutputFile()
	{
		return outputFile;
	}
}
