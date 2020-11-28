package de.metas.report.server;

import com.google.common.io.Files;
import de.metas.logging.LogManager;
import de.metas.process.AdProcessId;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessInfo;
import de.metas.report.jasper.JasperEngine;
import de.metas.report.xls.engine.XlsReportEngine;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.Env;
import org.slf4j.Logger;

import java.util.Properties;

public class LocalReportServer implements IReportServer
{
	private static final Logger logger = LogManager.getLogger(LocalReportServer.class);

	@Override
	public ReportResult report(
			final int processId,
			final int pinstanceRepoId,
			final String adLanguage,
			final OutputType outputType)
	{
		//
		// Load process info
		final ProcessInfo processInfo = ProcessInfo.builder()
				.setCtx(Env.newTemporaryCtx())
				.setCreateTemporaryCtx()
				.setAD_Process_ID(AdProcessId.ofRepoIdOrNull(processId))
				.setPInstanceId(PInstanceId.ofRepoIdOrNull(pinstanceRepoId))
				.setReportLanguage(adLanguage)
				.setJRDesiredOutputType(outputType)
				.build();

		//
		// If there is no AD_PInstance already, we need to create it now
		if (processInfo.getPinstanceId() == null)
		{
			final IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);
			adPInstanceDAO.saveProcessInfoOnly(processInfo);
		}

		// Override the context using the values from record (if any)
		// NOTE: setting the AD_Org_ID from document (if any) is very important for retrieving things like organization logo which is displayed in reports.
		updateContextFromRecord(processInfo);

		//
		// Create report context based on processInfo
		final ReportContext reportContext = ReportContext.builder()
				.setCtx(processInfo.getCtx())
				.setAD_Process_ID(processInfo.getAdProcessId())
				.setPInstanceId(processInfo.getPinstanceId())
				.setRecord(processInfo.getTable_ID(), processInfo.getRecord_ID())
				.setAD_Language(processInfo.getReportAD_Language())
				.setOutputType(processInfo.getJRDesiredOutputType())
				.setReportTemplatePath(processInfo.getReportTemplate().orElse(null))
				.setSQLStatement(processInfo.getSQLStatement().orElse(null))
				.setApplySecuritySettings(processInfo.isReportApplySecuritySettings())
				.setType(processInfo.getType())
				.setJSONPath(processInfo.getJsonPath().orElse(null))
				.build();

		//
		// Create the report
		try (final IAutoCloseable ignored = Env.switchContext(reportContext.getCtx()))
		{
			final IReportEngine engine = createReportEngine(reportContext);
			return engine.report(reportContext);
		}
		catch (final Exception e)
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
	 */
	private static void updateContextFromRecord(final ProcessInfo processInfo)
	{
		if (!processInfo.isRecordSet())
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
