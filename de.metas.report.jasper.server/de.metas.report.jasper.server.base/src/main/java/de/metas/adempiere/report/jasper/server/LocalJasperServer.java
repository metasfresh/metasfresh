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
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.io.Files;

import de.metas.adempiere.report.jasper.IJasperServer;
import de.metas.adempiere.report.jasper.JasperEngine;
import de.metas.adempiere.report.jasper.OutputType;
import de.metas.logging.LogManager;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.ProcessInfo;
import de.metas.report.engine.IReportEngine;
import de.metas.report.engine.ReportContext;
import de.metas.report.xls.engine.XlsReportEngine;

public class LocalJasperServer implements IJasperServer
{
	private static final Logger logger = LogManager.getLogger(LocalJasperServer.class);

	@Override
	public byte[] report(int processId, int pinstanceId, final String language, final OutputType outputType) throws Exception
	{
		//
		// Load process info
		final ProcessInfo processInfo = ProcessInfo.builder()
				.setCtx(Env.newTemporaryCtx())
				.setCreateTemporaryCtx()
				.setAD_Process_ID(processId)
				.setAD_PInstance_ID(pinstanceId)
				.setJRDesiredOutputType(outputType)
				.build();
		
		//
		// If there is no AD_PInstance already, we need to create it now
		if(processInfo.getAD_PInstance_ID() <= 0)
		{
			Services.get(IADPInstanceDAO.class).saveProcessInfoOnly(processInfo);
		}
		
		// Override the context using the values from record (if any)
		// NOTE: setting the AD_Org_ID from document (if any) is very important for retrieving things like organization logo which is displayed in reports.
		updateContextFromRecord(processInfo);

		//
		// Create report context based on processInfo
		final ReportContext reportContext = ReportContext.builder()
				.setCtx(processInfo.getCtx())
				.setAD_Process_ID(processInfo.getAD_Process_ID())
				.setAD_PInstance_ID(processInfo.getAD_PInstance_ID())
				.setRecord(processInfo.getTable_ID(), processInfo.getRecord_ID())
				.setAD_Language(language)
				.setOutputType(processInfo.getJRDesiredOutputType())
				.setReportTemplatePath(processInfo.getReportTemplate().orElse(null))
				.setSQLStatement(processInfo.getSQLStatement().orElse(null))
				.setApplySecuritySettings(processInfo.isReportApplySecuritySettings())
				.build();

		//
		// Create the report
		try (final IAutoCloseable contextRestorer = Env.switchContext(reportContext.getCtx()))
		{
			final ByteArrayOutputStream out = new ByteArrayOutputStream();
			final IReportEngine engine = createReportEngine(reportContext);
			engine.report(reportContext, out);
			return out.toByteArray();
		}
		catch (Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	private IReportEngine createReportEngine(final ReportContext reportContext)
	{
		//
		// Get report template's file extension
		final String reportTemplatePath = reportContext.getReportTemplatePath();
		Check.assumeNotEmpty(reportTemplatePath, "reportTemplatePath not empty for {}", reportContext);
		final String reportFileExtension = Files.getFileExtension(reportTemplatePath);
		
		if (JasperEngine.REPORT_FILE_EXTENSIONS.contains(reportFileExtension))
		{
			return new JasperEngine();
		}
		else if (XlsReportEngine.REPORT_FILE_EXTENSIONS.contains(reportFileExtension))
		{
			return new XlsReportEngine();
		}
		else
		{
			throw new AdempiereException("Cannot determine " + IReportEngine.class + " for report file extension '" + reportFileExtension + "'."
					+ "\nContext: " + reportContext);
		}
	}

	/**
	 * Populate given context (AD_Client_ID, AD_Org_ID) from given record.
	 * 
	 * @param ctx
	 * @param adTableId record's AD_Table_ID
	 * @param recordId record's ID
	 */
	private static final void updateContextFromRecord(final ProcessInfo processInfo)
	{
		if(!processInfo.isRecordSet())
		{
			return;
		}

		try
		{
			final Properties ctx = processInfo.getCtx();
			final Object record = processInfo.getRecord(Object.class);
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
		catch (final Exception ex)
		{
			logger.warn("Failed while populating the context from record. Ignored. \n ProcessInfo: {}", processInfo, ex);
		}
	}

	@Override
	public void cacheReset()
	{
		// nothing to do. In case of LocalJasperServer, the "server" is running in same JVM as ADempiere application
		// CacheMgt.get().reset();
	}
}
