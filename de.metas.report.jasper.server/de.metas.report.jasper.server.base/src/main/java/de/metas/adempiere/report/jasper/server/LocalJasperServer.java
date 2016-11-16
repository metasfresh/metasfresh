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

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.I_AD_Process;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.io.Files;

import de.metas.adempiere.report.jasper.IJasperServer;
import de.metas.adempiere.report.jasper.JasperEngine;
import de.metas.adempiere.report.jasper.OutputType;
import de.metas.logging.LogManager;
import de.metas.process.IADPInstanceDAO;
import de.metas.report.engine.IReportEngine;
import de.metas.report.engine.ReportContext;
import de.metas.report.xls.engine.XlsReportEngine;

public class LocalJasperServer implements IJasperServer
{
	private static final Logger logger = LogManager.getLogger(LocalJasperServer.class);

	@Override
	public byte[] report(int processId, int pinstanceId, final String language, final OutputType outputType) throws Exception
	{
		final Properties ctx = Env.newTemporaryCtx();

		//
		// Load processId, tableId, recordId
		int tableId = -1;
		int recordId = -1;
		if (pinstanceId <= 0)
		{
			final I_AD_PInstance pinstance = Services.get(IADPInstanceDAO.class).createAD_PInstance(ctx, processId, 0, 0);
			logger.info("Given AD_PInstance_ID was 0; Created new {}", pinstance);
			pinstanceId = pinstance.getAD_PInstance_ID();
		}
		else
		{
			final I_AD_PInstance pinstance = Services.get(IADPInstanceDAO.class).retrieveAD_PInstance(ctx, pinstanceId);
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
			Env.setContext(ctx, Env.CTXNAME_AD_Role_ID, pinstance.getAD_Role_ID());

			// Override the context using the values from record (if any)
			// NOTE: setting the AD_Org_ID from document (if any) is very important for retrieving things like organization logo which is displayed in reports.
			updateContextFromRecord(ctx, tableId, recordId);
		}

		//
		// Create report context
		final ReportContext reportContext = ReportContext.builder()
				.setCtx(ctx)
				.setAD_Process(InterfaceWrapperHelper.create(ctx, processId, I_AD_Process.class, ITrx.TRXNAME_None))
				.setAD_PInstance_ID(pinstanceId)
				.setRecord(tableId, recordId)
				.setAD_Language(language)
				.setOutputType(outputType)
				.build();

		//
		// Create the report
		try (IAutoCloseable contextRestorer = Env.switchContext(ctx))
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
	private static final void updateContextFromRecord(final Properties ctx, final int adTableId, final int recordId)
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
		catch (Exception e)
		{
			logger.warn("Failed while populating the context from record. Ignored", e);
		}
	}

	@Override
	public void cacheReset()
	{
		// nothing to do. In case of LocalJasperServer, the "server" is running in same JVM as ADempiere application
		// CacheMgt.get().reset();
	}
}
