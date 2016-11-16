package de.metas.adempiere.report.jasper;

import java.io.File;
import java.io.IOException;

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

import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Process;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Language;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableSet;

import de.metas.adempiere.report.jasper.server.MetasJRXlsExporter;
import de.metas.logging.LogManager;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessInfoParameter;
import de.metas.report.engine.AbstractReportEngine;
import de.metas.report.engine.ReportContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.XlsReportConfiguration;

public class JasperEngine extends AbstractReportEngine
{
	public static final Set<String> REPORT_FILE_EXTENSIONS = ImmutableSet.of("jasper", "jrxml");

	private static final OutputType DEFAULT_OutputType = OutputType.PDF;

	private static final String PARAM_REPORT_LANGUAGE = "REPORT_LANGUAGE";
	private static final String PARAM_REPORT_LOCALE = JRParameter.REPORT_LOCALE;
	private static final String PARAM_RECORD_ID = "RECORD_ID";
	private static final String PARAM_AD_PINSTANCE_ID = "AD_PINSTANCE_ID";
	private static final String PARAM_BARCODE_URL = "barcodeURL";

	private static final String JRPROPERTY_ReportPath = JasperEngine.class.getName() + ".ReportPath";

	/**
	 * Desired output type.
	 *
	 * @see OutputType
	 */
	private static final String PARAM_OUTPUTTYPE = "OUTPUTTYPE";

	// services
	private final transient Logger log = LogManager.getLogger(getClass());

	@Override
	public void report(final ReportContext reportContext, final OutputStream out)
	{
		try
		{
			final JasperPrint jasperPrint = createJasperPrint(reportContext);
			createOutput(out, jasperPrint, reportContext.getOutputType());
		}
		catch (Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}

	}

	/**
	 * Retrieves the server's direct database connection.
	 *
	 * @return database connection
	 */
	private final Connection getConnection()
	{
		return DB.getConnectionRW();
	}

	private JasperPrint createJasperPrint(final ReportContext reportContext) throws JRException
	{
		log.info("{}", reportContext);


		//
		// Get the classloader to be used when loading jasper resources
		final Properties ctx = reportContext.getCtx();
		final ClassLoader jasperLoader = createReportClassLoader(reportContext);

		final Map<String, Object> jrParameters = createJRParameters(reportContext);
		final JasperReport jasperReport = createJasperReport(ctx, reportContext.getAD_Process_ID(), jrParameters, jasperLoader);

		Connection conn = null;
		try
		{
			//
			// Create jasper's JDBC connection
			conn = getConnection();
			final String sqlQueryInfo = "jasper main report=" + jasperReport.getProperty(JRPROPERTY_ReportPath)
					+ ", AD_PInstance_ID=" + reportContext.getAD_PInstance_ID();

			final String securityWhereClause;
			if (reportContext.isApplySecuritySettings())
			{
				final IUserRolePermissions userRolePermissions = reportContext.getUserRolePermissions();
				final String tableName = reportContext.getTableNameOrNull();
				securityWhereClause = userRolePermissions.getOrgWhere(tableName, false);
			}
			else
			{
				securityWhereClause = null;
			}

			final JasperJdbcConnection jasperConn = new JasperJdbcConnection(conn, sqlQueryInfo, securityWhereClause);

			//
			// Fill the report
			final JasperPrint jasperPrint = ADJasperFiller.getInstance().fillReport(jasperReport, jrParameters, jasperConn, jasperLoader);
			return jasperPrint;
		}
		finally
		{
			DB.close(conn);
			conn = null;
		}
	}

	private final JasperReport createJasperReport(final Properties ctx, final int adProcessId, final Map<String, Object> jrParameters, final ClassLoader jasperLoader) throws JRException
	{
		final String reportPath = getReportPath(adProcessId, jrParameters);
		final InputStream jasperInputStream;
		if (reportPath.startsWith("resource:"))
		{
			// load the jasper file(s) using an ordinary class loader.
			final String name = reportPath.substring("resource:".length()).trim();
			log.info("reportPath = " + reportPath);
			log.info("getting resource from = " + jasperLoader.getResource(name));

			jasperInputStream = jasperLoader.getResourceAsStream(name);
		}
		else
		{
			jasperInputStream = jasperLoader.getResourceAsStream(reportPath);
			jrParameters.put(JRParameter.REPORT_CLASS_LOADER, jasperLoader);
		}

		// Make sure the jasper input stream is not null
		if (jasperInputStream == null)
		{
			// TODO ->A AD_Message
			throw new AdempiereException("Berichtsdatei '" + reportPath + "' konnte nicht geöffnet werden");
		}

		//
		// Load the jasper report from stream
		final JasperReport jasperReport = (JasperReport)JRLoader.loadObject(jasperInputStream);

		//
		// Load report's resource bundles, if any.
		// NOTE: we need to load them here because jasper engine is not using our "jasperLoader" to load the bundles, but the thread context one.
		// (see net.sf.jasperreports.engine.fill.JRFillDataset.loadResourceBundle() )
		{
			final String reportDir = extractReportDir(reportPath);
			final String resourceBundleName = getResourceBundleName(jasperReport, reportDir);
			loadJasperReportResourceBundle(resourceBundleName, jrParameters, jasperLoader);
		}

		jasperReport.setProperty(JRPROPERTY_ReportPath, reportPath);

		return jasperReport;
	}

	private final Map<String, Object> createJRParameters(final ReportContext reportContext)
	{
		final Properties ctx = reportContext.getCtx();
		final int AD_PInstance_ID = reportContext.getAD_PInstance_ID();
		final int Record_ID = reportContext.getRecord_ID();

		final Map<String, Object> jrParameters = new HashMap<String, Object>(ctx.size() + 10);
		for (final Map.Entry<Object, Object> e : ctx.entrySet())
		{
			final Object key = e.getKey();
			jrParameters.put(key == null ? null : key.toString(), e.getValue());
		}

		addProcessInfoParameters(jrParameters, reportContext.getProcessInfoParameters());

		jrParameters.put(PARAM_REPORT_LANGUAGE, reportContext.getAD_Language());
		jrParameters.put(PARAM_OUTPUTTYPE, reportContext.getOutputType());

		if (Record_ID > 0)
		{
			jrParameters.put(PARAM_RECORD_ID, Record_ID);
		}

		// contribution from Ricardo (ralexsander)
		// in iReports you can 'SELECT' AD_Client_ID, AD_Org_ID and
		// AD_User_ID using only AD_PINSTANCE_ID
		jrParameters.put(PARAM_AD_PINSTANCE_ID, AD_PInstance_ID);

		final Language currLang = getParam_Language(jrParameters);
		jrParameters.put(PARAM_REPORT_LOCALE, currLang.getLocale());

		// task 05978: only overwrite from AD_Sysconfig if the parameter has not been provided via AD_PInstance_Para
		if (!jrParameters.containsKey(JasperConstants.SYSCONFIG_BarcodeServlet))
		{
			final String barcodeURL = Services.get(ISysConfigBL.class).getValue(JasperConstants.SYSCONFIG_BarcodeServlet, JasperConstants.SYSCONFIG_BarcodeServlet_DEFAULT);
			jrParameters.put(PARAM_BARCODE_URL, barcodeURL);
		}

		return jrParameters;
	}

	/**
	 * Extracts {@link Language} parameter
	 *
	 * @param ctx
	 * @param jrParameters
	 * @return {@link Language}; never returns null
	 */
	private final Language getParam_Language(final Map<String, Object> jrParameters)
	{
		Object langParam = jrParameters.get(PARAM_REPORT_LANGUAGE);
		Language currLang = null;
		if (langParam instanceof String)
		{
			currLang = Language.getLanguage((String)langParam);
		}
		else if (langParam instanceof Language)
		{
			currLang = (Language)langParam;
		}

		if (currLang == null)
		{
			currLang = Env.getLanguage(Env.getCtx());
		}

		return currLang;
	}

	/**
	 * Gets desired output type
	 *
	 * @param jrParameters
	 * @return {@link OutputType}; never returns null
	 */
	private final OutputType getParam_OutputType(final Map<String, Object> jrParameters)
	{
		final Object outputTypeParam = jrParameters.get(PARAM_OUTPUTTYPE);
		OutputType outputType = null;
		if (outputTypeParam instanceof String)
		{
			outputType = OutputType.valueOf(outputTypeParam.toString());
		}
		else if (outputTypeParam instanceof OutputType)
		{
			outputType = (OutputType)outputTypeParam;
		}

		if (outputType == null)
		{
			outputType = OutputType.JasperPrint;
		}

		return outputType;
	}

	private final String getReportPath(final int adProcessId, final Map<String, Object> jrParameters) throws JRException
	{
		final I_AD_Process process = Services.get(IADProcessDAO.class).retrieveProcessById(Env.getCtx(), adProcessId);
		final String reportPath = process.getJasperReport();
		final String reportPath_Tabular = process.getJasperReport_Tabular();

		if (Check.isEmpty(reportPath, true))
		{
			throw new JRException("Unable to find report for process " + process);
		}

		//
		// If our output type is tabular and we have a tabular report provided, use that one
		final OutputType outputType = getParam_OutputType(jrParameters);
		if (outputType.isTabular() && !Check.isEmpty(reportPath_Tabular, true))
		{
			return reportPath_Tabular;
		}

		return reportPath;
	}

	private void addProcessInfoParameters(final Map<String, Object> jrParameters, final List<ProcessInfoParameter> processParams)
	{
		if (processParams == null || processParams.isEmpty())
		{
			return;
		}

		for (final ProcessInfoParameter processParam : processParams)
		{
			addProcessInfoParameter(jrParameters, processParam);
		}
	}

	private void addProcessInfoParameter(final Map<String, Object> jrParameters, final ProcessInfoParameter processParam)
	{
		if (processParam == null)
		{
			return;
		}

		// NOTE: just to be on the safe side, for each process info parameter we are setting the ParameterName even if it's a range parameter
		jrParameters.put(processParam.getParameterName(), processParam.getParameter());

		// If we are dealing with a range parameter we are setting ParameterName1 and ParameterName2 too.
		if (processParam.getParameter_To() != null)
		{
			jrParameters.put(processParam.getParameterName() + "1", processParam.getParameter());
			jrParameters.put(processParam.getParameterName() + "2", processParam.getParameter_To());
		}
	}

	/**
	 * Extracts the fully qualified report directory from given report path.
	 *
	 * e.g.
	 * <ul>
	 * <li>for "@PREFIX@/de/metas/docs/order/report.jasper" it will return "@PREFIX@/de/metas/docs/order".
	 * <li>for "report.jasper" will return "" (empty string)
	 * </ul>
	 *
	 * @param reportPath
	 * @return report directory or empty string.
	 */
	private static final String extractReportDir(final String reportPath)
	{
		final int idx = reportPath.lastIndexOf("/");
		if (idx < 0)
		{
			return "";
		}
		final String reportDir = reportPath.substring(0, idx);
		return reportDir;
	}

	/**
	 * Gets fully qualified report's resource bundle name.
	 *
	 * @param jasperReport
	 * @param reportDir
	 * @return resource bundle name or <code>null</code>
	 */
	private static final String getResourceBundleName(final JasperReport jasperReport, final String reportDir)
	{
		String resourceBundleBaseName = jasperReport.getResourceBundle();
		if (Check.isEmpty(resourceBundleBaseName, true))
		{
			return null;
		}

		//
		// Case: report's resource bundle contains NO '/'.
		// In this case we assume the resources are in the same folder as the report, so we will prefix it with "reportDir".
		// e.g. "report"
		if (resourceBundleBaseName.indexOf('/') < 0)
		{
			resourceBundleBaseName = reportDir + "/" + resourceBundleBaseName;
		}
		//
		// Case: report's resource bundle contains '/'.
		// In this case we assume the resources are on the same server as the report, but not in the same folder.
		// So we would prefix it with "@PREFIX@" placeholder.
		// e.g. "de/metas/docs/order/report"
		else
		{
			resourceBundleBaseName = JasperClassLoader.PLACEHOLDER + resourceBundleBaseName;
		}

		return resourceBundleBaseName;
	}

	/**
	 * Loads the resource bundle for given <code>resourceBundleName</code>.
	 *
	 * If the {@link ResourceBundle} was found it will be set as {@link JRParameter#REPORT_RESOURCE_BUNDLE} parameter in <code>jrParameters</code>.
	 *
	 * @param resourceBundleName
	 * @param jrParameters
	 * @param classLoader class loader to be used for loading the resource
	 * @return true if resource bundle was loaded
	 */
	private boolean loadJasperReportResourceBundle(final String resourceBundleName, final Map<String, Object> jrParameters, final ClassLoader classLoader)
	{
		if (Check.isEmpty(resourceBundleName, true))
		{
			return false; // not loaded
		}

		final Locale locale = (Locale)jrParameters.get(PARAM_REPORT_LOCALE);

		try
		{
			final ResourceBundle resourceBundle = ResourceBundle.getBundle(resourceBundleName, locale, classLoader);
			jrParameters.put(JRParameter.REPORT_RESOURCE_BUNDLE, resourceBundle);
			return true;
		}
		catch (Exception e)
		{
			log.warn("Failed loading resource bundle for base name: " + resourceBundleName + ", " + locale + ". Skipping", e);
		}

		return false; // not loaded
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
		jasperPrint.setProperty(XlsReportConfiguration.PROPERTY_CELL_LOCKED, "true");

		// there are cases when we don't want the cells to be blocked by password
		// in those cases we put in jrxml the password property with empty value,  which will indicate we don't want password
		// if there is no such property we take default password. If empty we set no password and if set, we use that password from the report
		if(jasperPrint.getProperty(XlsReportConfiguration.PROPERTY_PASSWORD) == null)
		{
			//do nothing;
		}
		else if(jasperPrint.getProperty(XlsReportConfiguration.PROPERTY_PASSWORD).isEmpty())
		{
			exporter.setParameter(JRXlsAbstractExporterParameter.PASSWORD, null);
		}
		else
		{
			exporter.setParameter(JRXlsAbstractExporterParameter.PASSWORD, jasperPrint.getProperty(XlsReportConfiguration.PROPERTY_PASSWORD));
		}
		
		exporter.exportReport();
	}
}
