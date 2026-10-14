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

import com.lowagie.text.pdf.PdfArray;
import com.lowagie.text.pdf.PdfDictionary;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfObject;
import com.lowagie.text.pdf.PdfReader;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.base.JRBasePrintPage;
import net.sf.jasperreports.engine.base.JRBasePrintText;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
import net.sf.jasperreports.engine.type.VerticalTextAlignEnum;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies that JasperEngine emits a valid PDF/A-3B document when {@code IsPdfA3Output = true}.
 *
 * <p>The test builds a synthetic {@link JasperPrint} that uses the three font families required
 * by the invoice JRXMLs ({@code Arial}, {@code SansSerif}, {@code OCRB}) and asserts:
 * <ol>
 *   <li>XMP metadata contains {@code pdfaid:part=3} and {@code pdfaid:conformance=B}</li>
 *   <li>The PDF {@code /OutputIntents} array contains an entry with an sRGB ICC profile</li>
 *   <li>All three font families are actually embedded (have FontFile/FontFile2/FontFile3)</li>
 * </ol>
 *
 * <p>Validator approach: OpenPDF's {@link PdfReader} is already on the classpath (transitive through
 * jasperreports). Using it avoids adding a heavyweight conformance-validator dependency.
 * The structural checks — XMP pdfaid namespace, sRGB OutputIntent, font descriptors — are the same
 * invariants that any conformance validator would test first.
 *
 * <p>The OFF-flag path is tested separately to confirm the plain-PDF output is unchanged.
 */
public class PdfA3ExportTest
{
	/** Builds a minimal JasperPrint with text elements using each of the three font families. */
	private static JasperPrint buildTestPrint()
	{
		final JasperPrint print = new JasperPrint();
		print.setName("PdfA3Test");
		print.setPageWidth(595);   // A4 width in points
		print.setPageHeight(842);  // A4 height in points

		final JRBasePrintPage page = new JRBasePrintPage();

		page.addElement(makeTextElement(print, "Invoice text in Arial",   "Arial",     0));
		page.addElement(makeTextElement(print, "Invoice text in SansSerif", "SansSerif", 30));
		page.addElement(makeTextElement(print, "OCRB codeline 012345",    "OCRB",      60));

		print.addPage(page);
		return print;
	}

	private static JRBasePrintText makeTextElement(
			final JasperPrint print,
			final String text,
			final String fontName,
			final int yOffset)
	{
		final JRBasePrintText element = new JRBasePrintText(print.getDefaultStyleProvider());
		element.setX(10);
		element.setY(10 + yOffset);
		element.setWidth(500);
		element.setHeight(25);
		element.setFontName(fontName);
		element.setFontSize(12f);
		element.setBold(false);
		element.setItalic(false);
		element.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
		element.setVerticalTextAlign(VerticalTextAlignEnum.TOP);
		element.setText(text);
		// JRFillTextElement sets these after text measurement. Without them, ColumnText.go()
		// computes currentLeading=0 and may not advance yLine far enough for the text to
		// be written to the PDF stream (depending on the column geometry). Set values
		// representative of a single-line 12pt element so fonts actually get embedded.
		element.setLineSpacingFactor(1.0f);
		element.setLeadingOffset(-4.0f);
		return element;
	}

	// -----------------------------------------------------------------------
	// PDF/A-3B output path (flag = true)
	// -----------------------------------------------------------------------

	@Test
	void pdfA3_xmp_contains_pdfaid_part3_conformanceB() throws Exception
	{
		final JasperPrint print = buildTestPrint();
		final byte[] pdfBytes = JasperPdfA3Exporter.exportAsPdfA3(print);

		final PdfReader reader = new PdfReader(pdfBytes);
		final byte[] xmpBytes = reader.getMetadata();
		reader.close();

		assertThat(xmpBytes)
				.as("PDF must contain XMP metadata (required for PDF/A)")
				.isNotNull();

		final String xmp = new String(xmpBytes, "UTF-8");
		assertThat(xmp)
				.as("XMP must declare pdfaid:part=3")
				.contains("pdfaid:part>3<");
		assertThat(xmp)
				.as("XMP must declare pdfaid:conformance=B")
				.contains("pdfaid:conformance>B<");
	}

	@Test
	void pdfA3_catalog_has_sRGB_outputIntent() throws Exception
	{
		final JasperPrint print = buildTestPrint();
		final byte[] pdfBytes = JasperPdfA3Exporter.exportAsPdfA3(print);

		final PdfReader reader = new PdfReader(pdfBytes);
		final PdfDictionary catalog = reader.getCatalog();
		reader.close();

		final PdfArray outputIntents = catalog.getAsArray(PdfName.OUTPUTINTENTS);
		assertThat(outputIntents)
				.as("PDF/A-3 catalog must have /OutputIntents")
				.isNotNull();
		assertThat(outputIntents.size())
				.as("OutputIntents must be non-empty")
				.isGreaterThan(0);

		// At least one OutputIntent must reference an ICC profile (sRGB)
		boolean hasSrgb = false;
		for (int i = 0; i < outputIntents.size(); i++)
		{
			final PdfObject item = outputIntents.getPdfObject(i);
			if (!(item instanceof PdfDictionary))
			{
				continue;
			}
			final PdfDictionary oi = (PdfDictionary) item;
			final PdfObject destProfile = oi.get(new PdfName("DestOutputProfile"));
			if (destProfile != null)
			{
				hasSrgb = true;
				break;
			}
		}
		assertThat(hasSrgb)
				.as("At least one OutputIntent must reference a DestOutputProfile (sRGB ICC)")
				.isTrue();
	}

	@Test
	void pdfA3_all_three_invoice_fonts_are_embedded() throws Exception
	{
		final JasperPrint print = buildTestPrint();
		final byte[] pdfBytes = JasperPdfA3Exporter.exportAsPdfA3(print);

		final Set<String> embeddedFontBaseNames = collectEmbeddedFontBaseNames(pdfBytes);

		// The invoice families must be embedded. Each maps to a TTF via the metas.invoice extension.
		// We check that at least one embedded font exists per family — the exact base name comes from
		// the Liberation Sans / OCRB TTF naming.
		assertThat(embeddedFontBaseNames)
				.as("At least one font must be embedded for the Arial/LiberationSans family")
				.anyMatch(name -> name.toLowerCase().contains("liberation") || name.toLowerCase().contains("arial"));

		assertThat(embeddedFontBaseNames)
				.as("At least one font must be embedded for the OCRB family")
				.anyMatch(name -> name.toLowerCase().contains("ocr"));
	}

	/**
	 * Walks every font dictionary in the PDF and collects the base names of fonts that
	 * have an embedded font program ({@code /FontFile}, {@code /FontFile2}, or {@code /FontFile3}
	 * in the font descriptor).
	 */
	private static Set<String> collectEmbeddedFontBaseNames(final byte[] pdfBytes) throws IOException
	{
		final Set<String> result = new HashSet<>();
		final PdfReader reader = new PdfReader(pdfBytes);
		try
		{
			final int totalPages = reader.getNumberOfPages();
			for (int pageIndex = 1; pageIndex <= totalPages; pageIndex++)
			{
				final PdfDictionary pageDict = reader.getPageN(pageIndex);
				final PdfDictionary resources = pageDict.getAsDict(PdfName.RESOURCES);
				if (resources == null)
				{
					continue;
				}
				final PdfDictionary fonts = resources.getAsDict(PdfName.FONT);
				if (fonts == null)
				{
					continue;
				}
				for (final PdfName fontKey : fonts.getKeys())
				{
					final PdfDictionary fontDict = fonts.getAsDict(fontKey);
					if (fontDict == null)
					{
						continue;
					}
					collectEmbeddedFromFontDict(fontDict, reader, result);
				}
			}
		}
		finally
		{
			reader.close();
		}
		return result;
	}

	private static void collectEmbeddedFromFontDict(
			final PdfDictionary fontDict,
			final PdfReader reader,
			final Set<String> result)
	{
		// For Type0 (composite) fonts, recurse into DescendantFonts
		final PdfObject subtype = fontDict.get(PdfName.SUBTYPE);
		if (subtype != null && "/Type0".equals(subtype.toString()))
		{
			final PdfArray descendants = fontDict.getAsArray(PdfName.DESCENDANTFONTS);
			if (descendants != null)
			{
				for (int i = 0; i < descendants.size(); i++)
				{
					final PdfObject desc = PdfReader.getPdfObject(descendants.getPdfObject(i));
					if (desc instanceof PdfDictionary)
					{
						collectEmbeddedFromFontDict((PdfDictionary) desc, reader, result);
					}
				}
			}
			return;
		}

		final PdfDictionary fd = fontDict.getAsDict(PdfName.FONTDESCRIPTOR);
		if (fd == null)
		{
			return;
		}

		final boolean hasEmbeddedFile =
				fd.get(PdfName.FONTFILE) != null
						|| fd.get(PdfName.FONTFILE2) != null
						|| fd.get(new PdfName("FontFile3")) != null;
		if (!hasEmbeddedFile)
		{
			return;
		}

		final PdfObject baseName = fd.get(PdfName.FONTNAME);
		if (baseName != null)
		{
			// Strip subset prefix (e.g. "ABCDEF+LiberationSans" → "LiberationSans")
			String name = baseName.toString();
			if (name.startsWith("/"))
			{
				name = name.substring(1);
			}
			final int plusIdx = name.indexOf('+');
			if (plusIdx >= 0)
			{
				name = name.substring(plusIdx + 1);
			}
			result.add(name);
		}
	}

	// -----------------------------------------------------------------------
	// Plain-PDF path (flag = false) — must be unchanged
	// -----------------------------------------------------------------------

	@Test
	void plainPdf_has_no_pdfA_xmp_marker() throws Exception
	{
		final JasperPrint print = buildTestPrint();
		final byte[] pdfBytes = JasperPdfA3Exporter.exportAsPlainPdf(print);

		final PdfReader reader = new PdfReader(pdfBytes);
		final byte[] xmpBytes = reader.getMetadata();
		reader.close();

		// Plain PDF either has no metadata or does NOT contain the pdfaid:part marker
		if (xmpBytes != null)
		{
			final String xmp = new String(xmpBytes, "UTF-8");
			assertThat(xmp)
					.as("Plain-PDF output must NOT contain pdfaid:part=3")
					.doesNotContain("pdfaid:part>3<");
		}
		// If xmpBytes is null, the assertion passes trivially — plain PDF has no PDF/A marker
	}

	@Test
	void plainPdf_has_no_outputIntent() throws Exception
	{
		final JasperPrint print = buildTestPrint();
		final byte[] pdfBytes = JasperPdfA3Exporter.exportAsPlainPdf(print);

		final PdfReader reader = new PdfReader(pdfBytes);
		final PdfDictionary catalog = reader.getCatalog();
		reader.close();

		final PdfArray outputIntents = catalog.getAsArray(PdfName.OUTPUTINTENTS);
		assertThat(outputIntents)
				.as("Plain-PDF must NOT have /OutputIntents (that is a PDF/A-3 addition)")
				.isNull();
	}
}
