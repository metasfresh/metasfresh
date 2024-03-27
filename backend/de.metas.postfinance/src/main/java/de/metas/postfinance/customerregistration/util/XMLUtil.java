/*
 * #%L
 * de.metas.postfinance
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.postfinance.customerregistration.util;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import de.metas.postfinance.ConfiguredXmlMapper;
import de.metas.postfinance.customerregistration.model.XmlCustomerRegistrationMessage;
import de.metas.postfinance.jaxb.DownloadFile;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;

import static de.metas.postfinance.PostFinanceConstants.XML_PROPERTY_VALUE_YES;

@UtilityClass
public class XMLUtil
{
	@NonNull
	public XmlCustomerRegistrationMessage getXmlCustomerRegistrationMessage(@NonNull final DownloadFile downloadFile)
	{
		try
		{
			final Document document = readFromDownloadFile(downloadFile);
			final String documentAsString = toString(document);

			final XmlMapper xmlMapper = ConfiguredXmlMapper.get();
			return xmlMapper.readValue(documentAsString, XmlCustomerRegistrationMessage.class);
		}
		catch (final Exception e)
		{
			throw new AdempiereException(e);
		}
	}


	public boolean isXML(@NonNull final String filename)
	{
		final String name = filename.trim().toLowerCase();
		return name.endsWith(".xml");
	}
	
	@NonNull
	private Document readFromDownloadFile(@NonNull final DownloadFile downloadFile) throws ParserConfigurationException, IOException, SAXException
	{
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder builder = factory.newDocumentBuilder();
		final Document document = builder.parse(new ByteArrayInputStream(downloadFile.getData().getValue()));

		//dev-note: ensure that the document hierarchy isn't affected by any extra white spaces or new lines within nodes.
		document.getDocumentElement().normalize();

		return document;
	}

	@NonNull
	private String toString(@NonNull final Document document) throws TransformerException
	{
		final TransformerFactory transformerFactory = TransformerFactory.newInstance();
		final Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, XML_PROPERTY_VALUE_YES);

		final StringWriter buffer = new StringWriter();

		transformer.transform(new DOMSource(document.getDocumentElement()),
							  new StreamResult(buffer));

		return buffer.toString();
	}
}
