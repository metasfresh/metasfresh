package de.metas.report.jasper.fonts;

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

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.fonts.FontFamily;
import net.sf.jasperreports.engine.fonts.FontInfo;
import net.sf.jasperreports.engine.fonts.FontUtil;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies the invoice font families are registered as embeddable, as PDF/A-3 output requires.
 *
 * <p>The invoice JRXMLs use three font family names:
 * <ul>
 *   <li>{@code Arial} — must resolve to Liberation Sans (metrics-compatible, open-licensed), pdfEmbedded=true</li>
 *   <li>{@code SansSerif} — same as Arial, pdfEmbedded=true</li>
 *   <li>{@code OCRB} — must resolve to the OCR-B TTF, pdfEmbedded=true (the jp.osdn.ocrb jar ships pdfEmbedded=false)</li>
 * </ul>
 *
 * <p>PDF/A-3 (required for ZUGFeRD) mandates that all fonts are embedded.
 * This test verifies that the font extension registered in this module's resources
 * satisfies that requirement for all family names used in the invoice report family.
 */
public class InvoiceEmbeddableFontsTest
{
	private static final JasperReportsContext JR_CONTEXT = DefaultJasperReportsContext.getInstance();
	private static final FontUtil FONT_UTIL = FontUtil.getInstance(JR_CONTEXT);

	@Test
	void arial_family_resolves_to_embeddable_font()
	{
		final FontInfo fontInfo = FONT_UTIL.getFontInfo("Arial", Locale.getDefault());
		assertThat(fontInfo)
				.as("Font family 'Arial' must be registered as a JasperReports font extension")
				.isNotNull();

		final FontFamily family = fontInfo.getFontFamily();
		assertThat(family.isPdfEmbedded())
				.as("Font family 'Arial' must have pdfEmbedded=true for PDF/A-3 compliance")
				.isEqualTo(Boolean.TRUE);
	}

	@Test
	void sansSerif_family_resolves_to_embeddable_font()
	{
		final FontInfo fontInfo = FONT_UTIL.getFontInfo("SansSerif", Locale.getDefault());
		assertThat(fontInfo)
				.as("Font family 'SansSerif' must be registered as a JasperReports font extension")
				.isNotNull();

		final FontFamily family = fontInfo.getFontFamily();
		assertThat(family.isPdfEmbedded())
				.as("Font family 'SansSerif' must have pdfEmbedded=true for PDF/A-3 compliance")
				.isEqualTo(Boolean.TRUE);
	}

	@Test
	void ocrb_family_resolves_to_embeddable_font()
	{
		final FontInfo fontInfo = FONT_UTIL.getFontInfo("OCRB", Locale.getDefault());
		assertThat(fontInfo)
				.as("Font family 'OCRB' must be registered as a JasperReports font extension")
				.isNotNull();

		final FontFamily family = fontInfo.getFontFamily();
		assertThat(family.isPdfEmbedded())
				.as("Font family 'OCRB' must have pdfEmbedded=true for PDF/A-3 compliance "
						+ "(jp.osdn.ocrb jar ships pdfEmbedded=false — our extension must override it)")
				.isEqualTo(Boolean.TRUE);
	}

	@Test
	void arial_family_has_normal_font_face()
	{
		final FontInfo fontInfo = FONT_UTIL.getFontInfo("Arial", Locale.getDefault());
		assertThat(fontInfo).isNotNull();
		assertThat(fontInfo.getFontFamily().getNormalFace())
				.as("Font family 'Arial' must have a normal face (Liberation Sans Regular)")
				.isNotNull();
	}

	@Test
	void sansSerif_family_has_normal_font_face()
	{
		final FontInfo fontInfo = FONT_UTIL.getFontInfo("SansSerif", Locale.getDefault());
		assertThat(fontInfo).isNotNull();
		assertThat(fontInfo.getFontFamily().getNormalFace())
				.as("Font family 'SansSerif' must have a normal face (Liberation Sans Regular)")
				.isNotNull();
	}

	@Test
	void ocrb_family_has_normal_font_face()
	{
		final FontInfo fontInfo = FONT_UTIL.getFontInfo("OCRB", Locale.getDefault());
		assertThat(fontInfo).isNotNull();
		assertThat(fontInfo.getFontFamily().getNormalFace())
				.as("Font family 'OCRB' must have a normal face (OCRB TTF)")
				.isNotNull();
	}
}
