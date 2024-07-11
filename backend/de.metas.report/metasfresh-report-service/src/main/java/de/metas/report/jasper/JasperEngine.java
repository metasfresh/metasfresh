package de.metas.report.jasper;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableSet;
import de.metas.logging.LogManager;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.report.jasper.class_loader.JasperClassLoader;
import de.metas.report.jasper.data_source.ReportDataSource;
import de.metas.report.jasper.data_source.JasperDataSourceProvider;
import de.metas.report.jasper.exporter.MetasJRXlsExporter;
import de.metas.report.server.AbstractReportEngine;
import de.metas.report.server.OutputType;
import de.metas.report.server.ReportConstants;
import de.metas.report.server.ReportContext;
import de.metas.report.server.ReportResult;
import de.metas.util.Check;
import de.metas.util.FileUtil;
import de.metas.util.Services;
import lombok.NonNull;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.XlsReportConfiguration;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Process;
import org.compiere.util.DB;
import org.compiere.util.Util;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.util.Locale;
import java.util.ResourceBundle;

public class JasperEngine extends AbstractReportEngine
{
	public static final ImmutableSet<String> REPORT_FILE_EXTENSIONS = ImmutableSet.of("jasper", "jrxml");

	private static final OutputType DEFAULT_OutputType = OutputType.PDF;

	private static final String JRPROPERTY_ReportPath = JasperEngine.class.getName() + ".ReportPath";

	// services
	private static final Logger logger = LogManager.getLogger(JasperEngine.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	private final JasperDataSourceProvider dataSourceProvider = SpringContextHolder.instance.getBean(JasperDataSourceProvider.class);

	@Override
	public ReportResult report(@NonNull final ReportContext reportContext)
	{
		try
		{
			final JasperPrint jasperPrint = createJasperPrint(reportContext);
			return createOutput(jasperPrint, reportContext.getOutputType());
		}
		catch (final Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}

	}

	private JasperPrint createJasperPrint(final ReportContext reportContext) throws JRException
	{
		logger.debug("Creating jasper print for {}", reportContext);

		final ClassLoader jasperLoader = createReportClassLoader(reportContext);
		final JRParametersCollector jrParameters = createJRParameters(reportContext);
		final JasperReport jasperReport = createJasperReport(reportContext.getAD_Process_ID(), jrParameters, jasperLoader);

		//
		// Fill the report
		final ReportDataSource dataSource = dataSourceProvider.getDataSource(reportContext);
		final Connection dbConnection = dataSource.createDBConnection(reportContext, jasperReport.getProperty(JRPROPERTY_ReportPath)).orElse(null);
		try
		{
			return JasperReportFiller.getInstance().fillReport(jasperReport, jrParameters.toMap(), dbConnection, jasperLoader);
		}
		finally
		{
			DB.close(dbConnection);
		}
	}

	private JasperReport createJasperReport(
			@NonNull final AdProcessId adProcessId,
			@NonNull final JRParametersCollector jrParameters,
			@NonNull final ClassLoader jasperLoader) throws JRException
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();
		final String reportPath = getReportPath(adProcessId, jrParameters);
		final InputStream jasperInputStream;
		if (reportPath.startsWith("resource:"))
		{
			// load the jasper file(s) using an ordinary class loader.
			final String name = reportPath.substring("resource:".length()).trim();
			logger.debug("reportPath = {}", reportPath);
			// logger.debug("getting resource from = {}", jasperLoader.getResource(name));

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
			// TODO -> AD_Message
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

		logger.debug("Created JasperReport for {} in {}", adProcessId, stopwatch.stop());
		return jasperReport;
	}

	private JRParametersCollector createJRParameters(final ReportContext reportContext)
	{
		final JRParametersCollector jrParameters = new JRParametersCollector();
		jrParameters.putAll(reportContext.getCtx());
		jrParameters.putAllProcessInfoParameters(reportContext.getProcessInfoParameters());
		jrParameters.putReportLanguage(reportContext.getAD_Language());
		jrParameters.putOutputType(reportContext.getOutputType());
		if (reportContext.getRecord_ID() > 0)
		{
			jrParameters.putRecordId(reportContext.getRecord_ID());
		}
		jrParameters.putPInstanceId(reportContext.getPinstanceId());
		jrParameters.putLocale(jrParameters.getReportLanguageEffective().getLocale());

		// task 05978: only overwrite from AD_Sysconfig if the parameter has not been provided via AD_PInstance_Para
		if (jrParameters.getBarcodeUrl() == null)
		{
			final String barcodeURL = sysConfigBL.getValue(ReportConstants.SYSCONFIG_BarcodeServlet, ReportConstants.SYSCONFIG_BarcodeServlet_DEFAULT);
			jrParameters.putBarcodeURL(barcodeURL);
		}

		final ReportDataSource dataSource = dataSourceProvider.getDataSource(reportContext);
		jrParameters.putAll(dataSource.createJRParameters(reportContext));

		return jrParameters;
	}

	private String getReportPath(@NonNull final AdProcessId adProcessId, final JRParametersCollector jrParameters) throws JRException
	{
		final I_AD_Process process = adProcessDAO.getById(adProcessId);
		final String reportPath = process.getJasperReport();
		final String reportPath_Tabular = process.getJasperReport_Tabular();

		if (reportPath == null || Check.isBlank(reportPath))
		{
			throw new JRException("Unable to find report for process " + process);
		}

		//
		// If our output type is tabular and we have a tabular report provided, use that one
		final OutputType outputType = jrParameters.getOutputTypeEffective();
		if (outputType.isTabular() && !Check.isBlank(reportPath_Tabular))
		{
			return reportPath_Tabular;
		}

		return reportPath;
	}

	/**
	 * Extracts the fully qualified report directory from given report path.
	 * <p>
	 * e.g.
	 * <ul>
	 * <li>for "@PREFIX@/de/metas/docs/order/report.jasper" it will return "@PREFIX@/de/metas/docs/order".
	 * <li>for "report.jasper" will return "" (empty string)
	 * </ul>
	 *
	 * @return report directory or empty string.
	 */
	private static String extractReportDir(final String reportPath)
	{
		final int idx = reportPath.lastIndexOf("/");
		if (idx < 0)
		{
			return "";
		}
		return reportPath.substring(0, idx);
	}

	/**
	 * Gets fully qualified report's resource bundle name.
	 *
	 * @return resource bundle name or <code>null</code>
	 */
	@Nullable
	private static String getResourceBundleName(final JasperReport jasperReport, final String reportDir)
	{
		String resourceBundleBaseName = jasperReport.getResourceBundle();
		if (resourceBundleBaseName == null || Check.isBlank(resourceBundleBaseName))
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
	 * <p>
	 * If the {@link ResourceBundle} was found it will be set to <code>jrParameters</code>.
	 *
	 * @param classLoader class loader to be used for loading the resource
	 */
	private void loadJasperReportResourceBundle(
			@Nullable final String resourceBundleName,
			@NonNull final JRParametersCollector jrParameters,
			@NonNull final ClassLoader classLoader)
	{
		if (resourceBundleName == null || Check.isBlank(resourceBundleName))
		{
			return; // not loaded
		}

		final Locale locale = jrParameters.getLocale();
		if (locale == null)
		{
			// shall not happen
			logger.warn("Skip loading resource bundle because Locale was not set");
			return;
		}

		try
		{
			final ResourceBundle resourceBundle = ResourceBundle.getBundle(resourceBundleName, locale, classLoader);
			jrParameters.put(JRParameter.REPORT_RESOURCE_BUNDLE, resourceBundle);
		}
		catch (final Exception e)
		{
			logger.warn("Failed loading resource bundle for base name: {}, {}. Skipping", resourceBundleName, locale, e);
		}
	}

	private ReportResult createOutput(final JasperPrint jasperPrint, OutputType outputType) throws JRException, IOException
	{
		if (outputType == null)
		{
			outputType = DEFAULT_OutputType;
		}

		Stopwatch stopwatch = Stopwatch.createStarted();
		try
		{
			if (OutputType.PDF == outputType)
			{
				final byte[] data = JasperExportManager.exportReportToPdf(jasperPrint);
				return ReportResult.builder()
						.outputType(outputType)
						.reportContentBase64(Util.encodeBase64(data))
						.reportFilename(buildReportFilename(jasperPrint, outputType))
						.build();
			}
			else if (OutputType.HTML == outputType)
			{
				final File file = File.createTempFile("JasperPrint", ".html");
				JasperExportManager.exportReportToHtmlFile(jasperPrint, file.getAbsolutePath());
				// TODO: handle image links

				final ByteArrayOutputStream out = new ByteArrayOutputStream();
				FileUtil.copy(file, out);

				return ReportResult.builder()
						.outputType(outputType)
						.reportContentBase64(Util.encodeBase64(out.toByteArray()))
						.reportFilename(buildReportFilename(jasperPrint, outputType))
						.build();
			}
			else if (OutputType.XML == outputType)
			{
				final ByteArrayOutputStream out = new ByteArrayOutputStream();
				JasperExportManager.exportReportToXmlStream(jasperPrint, out);

				return ReportResult.builder()
						.outputType(outputType)
						.reportContentBase64(Util.encodeBase64(out.toByteArray()))
						.reportFilename(buildReportFilename(jasperPrint, outputType))
						.build();
			}
			else if (OutputType.JasperPrint == outputType)
			{
				return exportAsJasperPrint(jasperPrint);
			}
			else if (OutputType.XLS == outputType)
			{
				return exportAsExcel(jasperPrint);
			}
			else
			{
				throw new RuntimeException("Output type " + outputType + " not supported");
			}
		}
		finally
		{
			logger.debug("Took {} to export report to {}", stopwatch.stop(), outputType);
		}
	}

	private static String buildReportFilename(@NonNull final JasperPrint jasperPrint, @NonNull final OutputType outputType)
	{
		final String fileBasename = FileUtil.stripIllegalCharacters(jasperPrint.getName());
		return FileUtil.changeFileExtension(fileBasename, outputType.getFileExtension());
	}

	private ReportResult exportAsJasperPrint(final JasperPrint jasperPrint) throws IOException
	{
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final ObjectOutputStream oos = new ObjectOutputStream(out);
		oos.writeObject(jasperPrint);

		return ReportResult.builder()
				.outputType(OutputType.JasperPrint)
				.reportFilename(buildReportFilename(jasperPrint, OutputType.JasperPrint))
				.reportContentBase64(Util.encodeBase64(out.toByteArray()))
				.build();
	}

	private ReportResult exportAsExcel(final JasperPrint jasperPrint) throws JRException
	{
		final ByteArrayOutputStream out = new ByteArrayOutputStream();

		final MetasJRXlsExporter exporter = new MetasJRXlsExporter();
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out); // Output
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); // Input

		// Make sure our cells will be locked by default
		// and assume that cells which shall not be locked are particularly specified.
		jasperPrint.setProperty(XlsReportConfiguration.PROPERTY_CELL_LOCKED, "true");

		// there are cases when we don't want the cells to be blocked by password
		// in those cases we put in jrxml the password property with empty value, which will indicate we don't want password
		// if there is no such property we take default password. If empty we set no password and if set, we use that password from the report
		//noinspection StatementWithEmptyBody
		if (jasperPrint.getProperty(XlsReportConfiguration.PROPERTY_PASSWORD) == null)
		{
			// do nothing;
		}
		else if (jasperPrint.getProperty(XlsReportConfiguration.PROPERTY_PASSWORD).isEmpty())
		{
			exporter.setParameter(JRXlsAbstractExporterParameter.PASSWORD, null);
		}
		else
		{
			exporter.setParameter(JRXlsAbstractExporterParameter.PASSWORD, jasperPrint.getProperty(XlsReportConfiguration.PROPERTY_PASSWORD));
		}

		exporter.exportReport();

		return ReportResult.builder()
				.outputType(OutputType.XLS)
				.reportContentBase64(Util.encodeBase64(out.toByteArray()))
				.build();
	}
}
