package de.metas.report.jasper.client.process;

import de.metas.common.util.CoalesceUtil;
import de.metas.logging.LogManager;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessInfo;
import de.metas.report.ExecuteReportStrategy;
import de.metas.report.client.ReportsClient;
import de.metas.report.server.OutputType;
import de.metas.report.server.ReportResult;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_AD_Process;
import org.slf4j.Logger;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * This is the default strategy that is always used, unless specified differently.
 * See {@link ExecuteReportStrategy} on how to invoke your on implementation.
 */
@Component
public class JasperExecuteReportStrategy implements ExecuteReportStrategy
{
	private static final Logger logger = LogManager.getLogger(JasperExecuteReportStrategy.class);
	private static final String SYS_CONFIG_MOCK_REPORT_SERVICE = "de.metas.report.jasper.IsMockReportService";
	private static final String MOCK_CUCUMBER_REPORT_FILENAME = "test_filename.pdf";
	private static final String MOCK_CUCUMBER_REPORT_DATA = "dGVzdA==";
	private static final String MOCK_PDFA3_FIXTURE_CLASSPATH = "de/metas/report/jasper/mock_pdfa3_fixture.pdf";

	@Override
	public ExecuteReportResult executeReport(
			@NonNull final ProcessInfo processInfo,
			@Nullable final OutputType outputType)
	{
		final OutputType outputTypeEffective = Check.assumeNotNull(
				CoalesceUtil.coalesce(
						outputType,
						processInfo.getJRDesiredOutputType()),
				"From the given parameters, either outputType or processInfo.getJRDesiredOutputType() need to be non-null; processInfo={}",
				processInfo);

		final ReportResult reportResult = getReportResult(processInfo, outputTypeEffective);
		final byte[] reportData = reportResult.getReportContent();
		final String reportFilename = reportResult.getReportFilename();

		if (Check.isBlank(reportFilename)) // if the report returns some blanks, we ignore them
		{
			return ExecuteReportResult.of(outputTypeEffective, new ByteArrayResource(reportData));
		}
		else
		{
			return ExecuteReportResult.of(reportFilename, outputTypeEffective, new ByteArrayResource(reportData));
		}
	}

	private ReportResult getReportResult(
			@NonNull final ProcessInfo processInfo,
			@NonNull final OutputType outputType)
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

		//dev-note: workaround to mock jasper reports during cucumber tests
		if (sysConfigBL.getBooleanValue(SYS_CONFIG_MOCK_REPORT_SERVICE, false))
		{
			// When the AD_Process requires a PDF/A-3 output (e.g. for ZUGFeRD embedding),
			// return a minimal but structurally valid PDF/A-3B fixture instead of the 4-byte stub,
			// so that ZugferdAssembler.embed() (which calls PDFBox ZUGFeRDExporterFromA3.load())
			// receives parseable PDF/A-3 bytes.
			if (isPdfA3OutputRequired(processInfo))
			{
				return ReportResult.builder()
						.outputType(outputType)
						.reportFilename(MOCK_CUCUMBER_REPORT_FILENAME)
						.reportContentBase64(loadMockPdfA3Base64())
						.build();
			}

			return ReportResult.builder()
					.outputType(outputType)
					.reportFilename(MOCK_CUCUMBER_REPORT_FILENAME)
					.reportContentBase64(MOCK_CUCUMBER_REPORT_DATA)
					.build();
		}

		final ReportsClient reportsClient = ReportsClient.get();
		return reportsClient.report(processInfo, outputType);
	}

	/**
	 * Returns {@code true} when the AD_Process referenced by the given {@link ProcessInfo}
	 * has {@code IsPdfA3Output = true} — signals that the mock report service should return
	 * a valid PDF/A-3 fixture instead of the default 4-byte stub.
	 */
	private static boolean isPdfA3OutputRequired(@NonNull final ProcessInfo processInfo)
	{
		final AdProcessId adProcessId = processInfo.getAdProcessId();
		if (adProcessId == null)
		{
			return false;
		}
		try
		{
			final I_AD_Process process = Services.get(IADProcessDAO.class).getById(adProcessId);
			return process != null && process.isPdfA3Output();
		}
		catch (final Exception e)
		{
			logger.warn("isPdfA3OutputRequired: cannot load AD_Process for adProcessId={} — falling back to standard mock stub", adProcessId, e);
			return false;
		}
	}

	/**
	 * Loads the minimal PDF/A-3B fixture from the classpath resource
	 * {@value MOCK_PDFA3_FIXTURE_CLASSPATH} and returns it as a Base64-encoded string.
	 *
	 * <p>The fixture is generated once (see {@code ZugferdAssemblerTest.buildFixturePdfA3()})
	 * and bundled as a classpath resource so that {@code ZugferdAssembler.embed()} receives
	 * structurally valid PDF/A-3 bytes during Cucumber tests (the 4-byte stub would fail PDFBox
	 * parsing).
	 */
	@NonNull
	private static String loadMockPdfA3Base64()
	{
		try (final InputStream in = JasperExecuteReportStrategy.class.getClassLoader()
				.getResourceAsStream(MOCK_PDFA3_FIXTURE_CLASSPATH))
		{
			if (in == null)
			{
				throw new AdempiereException("Mock PDF/A-3 fixture not found on classpath: " + MOCK_PDFA3_FIXTURE_CLASSPATH);
			}
			final ByteArrayOutputStream buf = new ByteArrayOutputStream();
			final byte[] tmp = new byte[8192];
			int n;
			while ((n = in.read(tmp)) != -1)
			{
				buf.write(tmp, 0, n);
			}
			return Base64.getEncoder().encodeToString(buf.toByteArray());
		}
		catch (final AdempiereException e)
		{
			throw e;
		}
		catch (final Exception e)
		{
			throw new AdempiereException("Failed to load mock PDF/A-3 fixture from classpath: " + MOCK_PDFA3_FIXTURE_CLASSPATH, e);
		}
	}
}
