package org.compiere.print.impl;

/*
 * #%L
 * de.metas.report.jasper.client
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


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.model.PrintInfo;
import org.compiere.print.IJasperReportEngineAdapter;
import org.compiere.print.MPrintFormat;
import org.compiere.print.ReportEngine;

import de.metas.adempiere.report.jasper.JasperUtil;
import de.metas.adempiere.report.jasper.OutputType;
import de.metas.adempiere.report.jasper.client.JRClient;
import de.metas.process.ProcessInfo;

public class JasperReportEngineAdapter implements IJasperReportEngineAdapter
{

	@Override
	public byte[] createPDFData(final ReportEngine reportEngine)
	{
		final MPrintFormat printFormat = reportEngine.getPrintFormat();
		
		final Properties ctx = InterfaceWrapperHelper.getCtx(printFormat);
		//final Properties ctx = reportEngine.getCtx(); // don't use this method because that method is triggering ReportEngine.layout()

		final int jasperProcessId = printFormat.getJasperProcess_ID();
		Check.assume(jasperProcessId > 0, "No jasper process found");

		final PrintInfo printInfo = reportEngine.getPrintInfo();
		
		final ProcessInfo jasperProcessInfo = ProcessInfo.builder()
				.setCtx(ctx)
				.setAD_Process_ID(jasperProcessId)
				.setRecord(printInfo.getAD_Table_ID(), printInfo.getRecord_ID())
				.setReportLanguage(printFormat.getLanguage()) //04430 - the language will be taken from the print format, instead of the Env
				.setJRDesiredOutputType(OutputType.PDF)
				.build();

		final JRClient jrClient = JRClient.get();
		final byte[] pdf = jrClient.report(jasperProcessInfo, jasperProcessInfo.getJRDesiredOutputType());

		return pdf;
	}

	@Override
	public void createPDF(ReportEngine reportEngine, File file)
	{
		final byte[] data = createPDFData(reportEngine);

		ByteArrayInputStream in = new ByteArrayInputStream(data == null ? new byte[] {} : data);
		try
		{
			JasperUtil.copy(in, file);
		}
		catch (IOException e)
		{
			throw new AdempiereException("Cannot write file " + file, e);
		}
	}

}
