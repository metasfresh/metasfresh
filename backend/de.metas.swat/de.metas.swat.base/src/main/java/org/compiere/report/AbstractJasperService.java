package org.compiere.report;

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


import java.io.ByteArrayOutputStream;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.compiere.model.PrintInfo;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfoParameter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

public abstract class AbstractJasperService implements IJasperService
{
	protected final transient Logger logger = LogManager.getLogger(getClass());
	
	@Override
	public byte[] exportToPdf(final JasperPrint jasperPrint)
	{
		final JRPdfExporter exporter = new JRPdfExporter();

		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF8");

		try
		{
			exporter.exportReport();
		}
		catch (JRException e)
		{
			throw new AdempiereException("Cannot create PDF from " + jasperPrint, e);
		}

		return outputStream.toByteArray();
	}

	@Override
	public void setJrPrintExporter(final JRExporter jrExporter)
	{
		throw new UnsupportedOperationException();
	}

	protected PrintInfo extractPrintInfo(final ProcessInfo pi)
	{
		Check.assumeNotNull(pi, "pi not null");
		
		// make sure that we never have zero copies. Apparently ADempiere
		// thinks of "copies" as the number of printouts _additional_ to the
		// original document while the java printing API thinks of copies as
		// the absolute number of printouts and thus doesn't accept any
		// number <=0.
		int numberOfPrintouts = 1;
		PrintInfo printInfo = null;

		if (pi.getParameter() != null)
		{
			for (final ProcessInfoParameter param : pi.getParameter())
			{
				final String parameterName = param.getParameterName();
				final Object objParam = param.getParameter();

				if (objParam == null)
				{
					continue;
				}

				if (objParam instanceof PrintInfo)
				{
					printInfo = (PrintInfo)objParam;
					numberOfPrintouts = printInfo.getCopies();

					if (numberOfPrintouts <= 0)
					{
						logger.debug("Setting numberOfPrintouts from 0 (specified by printInfo) to 1");
						numberOfPrintouts = 1;
					}
					break;
				}
				else if (PARAM_PrintCopies.equals(parameterName))
				{
					numberOfPrintouts = param.getParameterAsInt();
					if (numberOfPrintouts <= 0)
					{
						logger.debug("Setting numberOfPrintouts from 0 (specified by " + PARAM_PrintCopies + ") to 1");
						numberOfPrintouts = 1;
					}
				}
			}
		}

		//
		// Do a copy of found print info, or create a new one
		if (printInfo == null)
		{
			printInfo = new PrintInfo(pi);
		}
		else
		{
			printInfo = new PrintInfo(printInfo);
		}

		// Update printInfo
		printInfo.setCopies(numberOfPrintouts < 1 ? 1 : numberOfPrintouts);

		return printInfo;
	}

}
