package de.metas.einvoice.zugferd;

/*
 * #%L
 * de.metas.einvoice.base
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
import org.mustangproject.ZUGFeRD.ZUGFeRDExporterFromA3;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Assembles a ZUGFeRD/Factur-X PDF by embedding a pre-validated EN16931 CII XML into an
 * existing PDF/A-3 document.
 *
 * <p>The assembler is a pure byte→byte function. It does not generate the CII XML (that is the
 * responsibility of {@code EInvoiceCiiService}) and does not write the result to any archive
 * (that is Task 6). It simply takes the two raw inputs and returns the assembled PDF bytes.
 *
 * <p>Profile: EN16931 (COMFORT) — the {@code factur-x.xml} attachment is written with
 * {@code AFRelationship = Alternative} as required by the Factur-X 2.1.1 specification
 * (§ 2.2.2 "Data Relationship").
 *
 * <p>Implementation uses Mustangproject {@code ZUGFeRDExporterFromA3} 2.11.0 (Apache-2.0),
 * the last release targeting Java 1.8.
 */
@UtilityClass
public class ZugferdAssembler
{
	/**
	 * Embeds an EN16931 CII XML string into a PDF/A-3 byte array and returns the assembled
	 * ZUGFeRD/Factur-X PDF bytes.
	 *
	 * <p>The PDF/A-3 input is loaded as-is (Mustangproject 2.11.0 does not re-validate its
	 * PDF/A conformance). The assembled output contains:
	 * <ul>
	 *   <li>{@code factur-x.xml} embedded with {@code AFRelationship = Alternative}</li>
	 *   <li>Factur-X XMP metadata (ZUGFeRD version 2, EN16931 profile)</li>
	 *   <li>sRGB output intent and structure tree added / updated</li>
	 * </ul>
	 *
	 * @param pdfA3  bytes of the base PDF/A-3 document (must be parseable by PDFBox 2.x)
	 * @param ciiXml EN16931 CII XML string (UTF-8 encoded); must contain the
	 *               {@code rsm:CrossIndustryInvoice} root element
	 * @return assembled ZUGFeRD/Factur-X PDF bytes
	 * @throws IOException if the PDF cannot be parsed or the export fails
	 */
	public byte[] embed(final byte[] pdfA3, @NonNull final String ciiXml) throws IOException
	{
		final byte[] ciiXmlBytes = ciiXml.getBytes(StandardCharsets.UTF_8);

		final ZUGFeRDExporterFromA3 exporter = new ZUGFeRDExporterFromA3();
		try
		{
			// load() parses the PDF with PDFBox; ensurePDFIsValid() returns true unconditionally in 2.11.0
			exporter.load(pdfA3);

			// setXML() wraps the raw bytes in a CustomXMLProvider (default profile: EN16931),
			// then calls prepare() → prepareDocument() + PDFAttachGenericFile("factur-x.xml", "Alternative", ...)
			exporter.setXML(ciiXmlBytes);

			final ByteArrayOutputStream out = new ByteArrayOutputStream();
			exporter.export(out);
			return out.toByteArray();
		}
		finally
		{
			// close() releases the PDDocument handle; ignore errors since the export already completed
			try
			{
				exporter.close();
			}
			catch (final IOException ignored)
			{
				// ignore
			}
		}
	}
}
