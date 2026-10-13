package de.metas.report.jasper;

/*
 * #%L
 * metasfresh-report-service
 * %%
 * Copyright (C) 2026 metas GmbH
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

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.type.PdfaConformanceEnum;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Exports a {@link JasperPrint} to PDF or PDF/A-3B.
 *
 * <p>PDF/A-3B requirements satisfied here (ISO 19005-3:2012 conformance level B):
 * <ul>
 *   <li>All fonts embedded — guaranteed by the {@code metas.invoice} JasperReports font extension
 *       registered in this module's resources (Liberation Sans for Arial/SansSerif, OCR-B for OCRB,
 *       all with {@code pdfEmbedded=true}).</li>
 *   <li>An sRGB ICC profile declared as an {@code /OutputIntent} in the PDF catalog.</li>
 *   <li>XMP metadata declaring {@code pdfaid:part=3} and {@code pdfaid:conformance=B}.</li>
 *   <li>Tagged PDF (accessibility structure tree) — required by PDF/A.</li>
 * </ul>
 *
 * <p>The sRGB ICC profile is loaded from the classpath resource {@code icc/sRGB.icc},
 * extracted from the JDK ({@code ICC_Profile.getInstance(ColorSpace.CS_sRGB).getData()})
 * and bundled as a static resource so the export does not depend on the JDK at runtime.
 *
 * <p>The classpath-relative path {@code icc/sRGB.icc} is resolved by JasperReports'
 * {@code RepositoryUtil.getBytesFromLocation()}, which searches registered repository services
 * (including a classpath-aware one) before falling back to a file-system lookup.
 */
@UtilityClass
public class JasperPdfA3Exporter
{
	/** Classpath path to the bundled sRGB ICC profile (dumped from JDK CS_sRGB). */
	static final String SRGB_ICC_CLASSPATH = "icc/sRGB.icc";

	/**
	 * Exports {@code jasperPrint} as PDF/A-3B.
	 *
	 * <p>Uses {@link JRPdfExporter} with {@link PdfaConformanceEnum#PDFA_3B},
	 * an sRGB ICC {@code OutputIntent}, tagged PDF, and document metadata title.
	 *
	 * @throws JRException if JasperReports encounters an export error (e.g. ICC profile not found
	 *                     on classpath — indicates a packaging problem in the deployment artifact)
	 * @throws IOException if the sRGB ICC resource pre-check fails
	 */
	public static byte[] exportAsPdfA3(@NonNull final JasperPrint jasperPrint) throws JRException, IOException
	{
		// Fail fast if the ICC resource is missing — indicates a packaging problem
		verifySrgbIccProfileAccessible();

		final ByteArrayOutputStream out = new ByteArrayOutputStream();

		final JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));

		final SimplePdfExporterConfiguration config = new SimplePdfExporterConfiguration();
		// PDF/A conformance level B (embeds fonts, OutputIntent, XMP metadata)
		config.setPdfaConformance(PdfaConformanceEnum.PDFA_3B);
		// Classpath path; JasperReports RepositoryUtil resolves this via its registered services
		config.setIccProfilePath(SRGB_ICC_CLASSPATH);
		// PDF/A requires a non-empty metadata title
		final String title = jasperPrint.getName() != null && !jasperPrint.getName().isEmpty()
				? jasperPrint.getName()
				: "Report";
		config.setMetadataTitle(title);
		// Tagged PDF is required for PDF/A conformance
		config.setTagged(Boolean.TRUE);

		exporter.setConfiguration(config);
		exporter.exportReport();

		return out.toByteArray();
	}

	/**
	 * Exports {@code jasperPrint} as a plain PDF (no PDF/A conformance).
	 * This is the legacy path used when {@code AD_Process.IsPdfA3Output = false}.
	 */
	public static byte[] exportAsPlainPdf(@NonNull final JasperPrint jasperPrint) throws JRException
	{
		return JasperExportManager.exportReportToPdf(jasperPrint);
	}

	/**
	 * Verifies the sRGB ICC classpath resource is loadable.
	 * Fails fast at call time rather than deep inside JasperReports.
	 */
	private static void verifySrgbIccProfileAccessible() throws IOException
	{
		try (InputStream is = JasperPdfA3Exporter.class.getClassLoader().getResourceAsStream(SRGB_ICC_CLASSPATH))
		{
			if (is == null)
			{
				throw new IOException(
						"sRGB ICC profile not found on classpath: " + SRGB_ICC_CLASSPATH
								+ ". The resource must be bundled in the metasfresh-report-service JAR.");
			}
		}
	}
}
