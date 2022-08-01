/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.invoice.process;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

public class XmlToPdfConverter
{

	public static byte[] getPDF(final String documentSourcePath) throws ParserConfigurationException, IOException, SAXException, TransformerException, DocumentException
	{

		final org.w3c.dom.Document xmlDocumentSource = xmlDocumentBuilder(documentSourcePath);

		final String stringFromXml = xmlToStringTransformer(xmlDocumentSource);
		System.out.println(stringFromXml);

		return stringToPdfTransformer(stringFromXml);
	}

	private static byte[] stringToPdfTransformer(final String string) throws ParserConfigurationException, IOException, DocumentException
	{
		final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		documentBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);

		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		final com.lowagie.text.Document pdfDocument = new com.lowagie.text.Document();
		PdfWriter.getInstance(pdfDocument, byteArrayOutputStream);

		pdfDocument.open();
		pdfDocument.add(new Paragraph(string));
		pdfDocument.close();

		return byteArrayOutputStream.toByteArray();
	}

	private static String xmlToStringTransformer(final org.w3c.dom.Document xmlDocumentSource) throws TransformerException
	{
		final TransformerFactory transformerFactory = TransformerFactory.newInstance();
		final Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		final StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(xmlDocumentSource), new StreamResult(writer));
		return writer.getBuffer().toString();
	}

	private static org.w3c.dom.Document xmlDocumentBuilder(final String documentPath) throws ParserConfigurationException, IOException, SAXException
	{
		final File file = new File(documentPath);
		final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		return documentBuilder.parse(file);
	}

}