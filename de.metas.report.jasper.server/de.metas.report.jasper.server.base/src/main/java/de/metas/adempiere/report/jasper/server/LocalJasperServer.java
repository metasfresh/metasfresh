package de.metas.adempiere.report.jasper.server;

/*
 * #%L
 * de.metas.report.jasper.server.base
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
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.MPInstance;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.util.Env;

import de.metas.adempiere.report.jasper.IJasperServer;
import de.metas.adempiere.report.jasper.JasperEngine;
import de.metas.adempiere.report.jasper.JasperUtil;
import de.metas.adempiere.report.jasper.OutputType;

public class LocalJasperServer implements IJasperServer
{
	private final Logger logger = LogManager.getLogger(getClass());

	@Override
	public byte[] report(int processId, int pinstanceId, final String language, OutputType outputType) throws Exception
	{
		final Properties ctx = Env.deriveCtx(Env.getCtx());

		//
		// Load processId, tableId, recordId
		int tableId = -1;
		int recordId = -1;
		if (pinstanceId <= 0)
		{
			final MPInstance pinstance = new MPInstance(ctx, processId, 0, 0);
			pinstance.saveEx();
			logger.info("Given AD_PInstance_ID was 0; Created new {}", pinstance);
			pinstanceId = pinstance.getAD_PInstance_ID();
		}
		else
		{
			final I_AD_PInstance pinstance = new MPInstance(ctx, pinstanceId, ITrx.TRXNAME_None);
			if (pinstance.getAD_PInstance_ID() != pinstanceId)
			{
				throw new AdempiereException("@NotFound@ @AD_PInstance_ID@ " + pinstanceId);
			}
			if (processId > 0 && pinstance.getAD_Process_ID() != processId)
			{
				throw new AdempiereException("@Invalid@ @AD_Process_ID@ (" + processId + " != " + pinstance.getAD_Process_ID());
			}
			processId = pinstance.getAD_Process_ID();
			tableId = pinstance.getAD_Table_ID();
			recordId = pinstance.getRecord_ID();
			
			// Update context from AD_PInstance
			Env.setContext(ctx, Env.CTXNAME_AD_Client_ID, pinstance.getAD_Client_ID());
			Env.setContext(ctx, Env.CTXNAME_AD_Org_ID, pinstance.getAD_Org_ID());
			Env.setContext(ctx, Env.CTXNAME_AD_User_ID, pinstance.getAD_User_ID());
			
			// Override the context using the values from record (if any)
			// NOTE: setting the AD_Org_ID from document (if any) is very important for retrieving things like organization logo which is displayed in reports.
			updateContextFromRecord(ctx, tableId, recordId);
		}
		

		//
		// Create ProcessInfo
		final ProcessInfo pi = new ProcessInfo("JasperReport", processId);
		pi.setAD_PInstance_ID(pinstanceId);
		pi.setAD_Client_ID(Env.getAD_Client_ID(ctx));
		pi.setAD_Org_ID(Env.getAD_Org_ID(ctx));
		pi.setAD_User_ID(Env.getAD_User_ID(ctx));
		pi.setTable_ID(tableId);
		pi.setRecord_ID(recordId);
		pi.setParameter(new ProcessInfoParameter[] {
				createProcessInfoParameter(JasperEngine.PARAM_REPORT_LANGUAGE, language)
				, createProcessInfoParameter(JasperEngine.PARAM_OUTPUTTYPE, outputType)
		});

		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final JasperPrint jasperPrint;
		final JasperEngine engine = new JasperEngine();
		try
		{
			jasperPrint = engine.createJasperPrint(ctx, pi);
			createOutput(out, jasperPrint, outputType);
		}
		catch (JRException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}

		return out.toByteArray();
	}

	/**
	 * Populate given context (AD_Client_ID, AD_Org_ID) from given record.
	 * 
	 * @param ctx
	 * @param adTableId record's AD_Table_ID
	 * @param recordId record's ID
	 */
	private final void updateContextFromRecord(final Properties ctx, final int adTableId, final int recordId)
	{
		if (adTableId <= 0 || recordId <= 0)
		{
			return;
		}

		try
		{
			final IContextAware context = new PlainContextAware(ctx);
			final Object record = new TableRecordReference(adTableId, recordId).getModel(context);
			final Integer adClientId = InterfaceWrapperHelper.getValueOrNull(record, "AD_Client_ID");
			if (adClientId != null)
			{
				Env.setContext(ctx, Env.CTXNAME_AD_Client_ID, adClientId);
			}
			final Integer adOrgId = InterfaceWrapperHelper.getValueOrNull(record, "AD_Org_ID");
			if (adOrgId != null)
			{
				Env.setContext(ctx, Env.CTXNAME_AD_Org_ID, adOrgId);
			}
		}
		catch(Exception e)
		{
			logger.warn("Failed while populating the context from record. Ignored", e);
		}
	}

	private final ProcessInfoParameter createProcessInfoParameter(final String parameterName, final Object value)
	{
		final Object valueTo = null;
		final String info = null;
		final String infoTo = null;
		return new ProcessInfoParameter(parameterName, value, valueTo, info, infoTo);
	}

	private void createOutput(final OutputStream out, final JasperPrint jasperPrint, OutputType outputType) throws JRException, IOException
	{
		if (outputType == null)
			outputType = DEFAULT_OutputType;

		if (OutputType.PDF == outputType)
		{
			byte[] data = JasperExportManager.exportReportToPdf(jasperPrint);
			out.write(data);
		}
		else if (OutputType.HTML == outputType)
		{
			File file = File.createTempFile("JasperPrint", ".html");
			JasperExportManager.exportReportToHtmlFile(jasperPrint, file.getAbsolutePath());
			// TODO: handle image links

			JasperUtil.copy(file, out);
		}
		else if (OutputType.XML == outputType)
		{
			JasperExportManager.exportReportToXmlStream(jasperPrint, out);
		}
		else if (OutputType.JasperPrint == outputType)
		{
			exportAsJasperPrint(jasperPrint, out);
		}
		else if (OutputType.XLS == outputType)
		{
			exportAsExcel(jasperPrint, out);
		}
		else
		{
			throw new RuntimeException("Output type " + outputType + " not supported");
		}
		out.flush();
		out.close();
	}

	private void exportAsJasperPrint(final JasperPrint jasperPrint, final OutputStream out) throws IOException
	{
		final ObjectOutputStream oos = new ObjectOutputStream(out);
		oos.writeObject(jasperPrint);
	}

	private void exportAsExcel(final JasperPrint jasperPrint, final OutputStream out) throws JRException
	{
		final MetasJRXlsExporter exporter = new MetasJRXlsExporter();
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out); // Output
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); // Input

		// Make sure our cells will be locked by default
		// and assume that cells which shall not be locked are particularly specified.
		jasperPrint.setProperty(JRXlsExporter.PROPERTY_CELL_LOCKED, "true");

		exporter.exportReport();
	}

	@Override
	public void cacheReset()
	{
		// nothing to do. In case of LocalJasperServer, the "server" is running in same JVM as ADempiere application
		// CacheMgt.get().reset();
	}
}
