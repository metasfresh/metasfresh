package de.metas.adempiere.report.jasper;

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
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.adempiere.ad.service.IADPInstanceDAO;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.MProcess;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Language;

public class JasperEngine
{
	public static final String PARAM_REPORT_LANGUAGE = "REPORT_LANGUAGE";
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
	public static final String PARAM_OUTPUTTYPE = "OUTPUTTYPE";

	// services
	private final transient CLogger log = CLogger.getCLogger(getClass());

	/**
	 * Retrieves the server's direct database connection.
	 * 
	 * @return database connection
	 */
	private final Connection getConnection()
	{
		return DB.getConnectionRW();
	}

	public JasperPrint createJasperPrint(final Properties ctx, final ProcessInfo pi) throws JRException
	{
		log.log(Level.INFO, "{0}", pi);

		final Map<String, Object> jrParameters = createJRParameters(ctx, pi);

		final JasperReport jasperReport = createJasperReport(ctx, pi.getAD_Process_ID(), jrParameters);

		Connection conn = null;
		try
		{
			//
			// Create jasper's JDBC connection
			conn = getConnection();
			final String sqlQueryInfo = "main report=" + jasperReport.getProperty(JRPROPERTY_ReportPath)
					+ ", AD_PInstance_ID=" + pi.getAD_PInstance_ID();
			final JasperJdbcConnection jasperConn = new JasperJdbcConnection(conn, sqlQueryInfo);

			//
			// Fill the report
			final JasperPrint jasperPrint = ADJasperFiller.getInstance().fillReport(jasperReport, jrParameters, jasperConn);
			return jasperPrint;
		}
		finally
		{
			DB.close(conn);
			conn = null;
		}
	}

	private final JasperReport createJasperReport(final Properties ctx, final int adProcessId, final Map<String, Object> jrParameters) throws JRException
	{
		final String reportPath = getReportPath(adProcessId, jrParameters);

		final ClassLoader jasperLoader = getJasperClassLoader(ctx);

		final InputStream jasperInputStream;
		if (reportPath.startsWith("resource:"))
		{
			// load the jasper file(s) using an ordinary class loader.
			final String name = reportPath.substring("resource:".length()).trim();
			log.info("reportPath = " + reportPath);
			log.info("getting resource from = " + getClass().getClassLoader().getResource(name));

			jasperInputStream = getClass().getClassLoader().getResourceAsStream(name);
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
		// NOTE: we need to load them here beause jasper engine is not using our "jasperLoader" to load the bundles, but the thread context one.
		// (see net.sf.jasperreports.engine.fill.JRFillDataset.loadResourceBundle() )
		{
			final String reportDir = extractReportDir(reportPath);
			final String resourceBundleName = getResourceBundleName(jasperReport, reportDir);
			loadJasperReportResourceBundle(resourceBundleName, jrParameters, jasperLoader);
		}

		jasperReport.setProperty(JRPROPERTY_ReportPath, reportPath);

		return jasperReport;
	}

	private final Map<String, Object> createJRParameters(final Properties ctx, final ProcessInfo pi)
	{
		final int AD_PInstance_ID = pi.getAD_PInstance_ID();
		final int Record_ID = pi.getRecord_ID();
		log.info("Name=" + pi.getTitle() + "  AD_PInstance_ID=" + AD_PInstance_ID + " Record_ID=" + Record_ID);

		final Map<String, Object> jrParameters = new HashMap<String, Object>(ctx.size() + 10);

		for (final Map.Entry<Object, Object> e : ctx.entrySet())
		{
			final Object key = e.getKey();
			jrParameters.put(key == null ? null : key.toString(), e.getValue());
		}

		addProcessParameters(jrParameters, ctx, AD_PInstance_ID);
		addProcessInfoParameters(jrParameters, pi.getParameter());

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
			currLang = (Language)jrParameters.get(PARAM_REPORT_LANGUAGE);
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
		final MProcess process = MProcess.get(Env.getCtx(), adProcessId);
		Check.assumeNotNull(process, "process not null");

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

	private ClassLoader getJasperClassLoader(final Properties ctx)
	{
		final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

		final ClassLoader parentClassLoader;
		final boolean useJasperCompileClassLoader = Services.get(IDeveloperModeBL.class).isEnabled();
		if (useJasperCompileClassLoader)
		{
			parentClassLoader = new JasperCompileClassLoader(contextClassLoader);
		}
		else
		{
			parentClassLoader = contextClassLoader;
		}

		final int adOrgId = Env.getAD_Org_ID(ctx);
		final JasperClassLoader jasperLoader = new JasperClassLoader(adOrgId, parentClassLoader);
		return jasperLoader;
	}

	private void addProcessParameters(final Map<String, Object> jrParameters, final Properties ctx, final int fromAD_PInstance_ID)
	{
		final List<ProcessInfoParameter> processParams = Services.get(IADPInstanceDAO.class).retrieveProcessInfoParameters(ctx, fromAD_PInstance_ID);
		final ProcessInfoParameter[] processParamsArr = processParams.toArray(new ProcessInfoParameter[processParams.size()]);
		addProcessInfoParameters(jrParameters, processParamsArr);
	}

	private void addProcessInfoParameters(final Map<String, Object> jrParameters, final ProcessInfoParameter[] processParams)
	{
		if (processParams == null || processParams.length == 0)
		{
			return;
		}

		for (final ProcessInfoParameter processParam : processParams)
		{
			// NOTE: just to be on the safe side, for each process info parameter we are setting the ParameterName even if it's a range parameter
			jrParameters.put(processParam.getParameterName(), processParam.getParameter());

			// If we are dealing with a range parameter we are setting ParameterName1 and ParameterName2 too.
			if (processParam.getParameter_To() != null)
			{
				jrParameters.put(processParam.getParameterName() + "1", processParam.getParameter());
				jrParameters.put(processParam.getParameterName() + "2", processParam.getParameter_To());
			}
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
			log.log(Level.WARNING, "Failed loading resource bundle for base name: " + resourceBundleName + ", " + locale + ". Skipping", e);
		}

		return false; // not loaded
	}
}
