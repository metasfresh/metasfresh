/*
 * #%L
 * de-metas-camel-leichundmehl
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

package de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.util;

import com.sun.xml.bind.marshaller.CharacterEscapeHandler;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.annotation.Nullable;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.file.Path;

import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.XML_PROPERTY_FILE_ENCODING_VALUE;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.XML_PROPERTY_VALUE_YES;

@UtilityClass
public class XMLUtil
{
	@NonNull
	public static Document readFromPath(@NonNull final Path path) throws ParserConfigurationException, IOException, SAXException
	{
		final InputStream inputStream = new FileInputStream(path.toFile());
		final InputStreamReader inputStreamReader = new InputStreamReader(inputStream, XML_PROPERTY_FILE_ENCODING_VALUE);

		final BufferedReader reader = new BufferedReader(inputStreamReader);
		final InputSource input = new InputSource(reader);

		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder builder = factory.newDocumentBuilder();
		final Document document = builder.parse(input);
		//dev-note: ensure that the document hierarchy isn't affected by any extra white spaces or new lines within nodes.
		document.getDocumentElement().normalize();

		return document;
	}

	@NonNull
	public static String toString(@NonNull final Document document) throws TransformerException
	{
		final TransformerFactory transformerFactory = TransformerFactory.newInstance();
		final Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, XML_PROPERTY_VALUE_YES);

		final StringWriter buffer = new StringWriter();

		transformer.transform(new DOMSource(document.getDocumentElement()),
							  new StreamResult(buffer));

		return buffer.toString();
	}

	@NonNull
	public static String convertToXML(@NonNull final Object object, @NonNull final Class<?> clazz) throws Exception
	{
		final JAXBContext jaxbContext = JAXBContext.newInstance(clazz);

		final Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, XML_PROPERTY_FILE_ENCODING_VALUE);

		final CharacterEscapeHandler escapeHandler = NoEscapeHandler.INSTANCE;
		marshaller.setProperty("com.sun.xml.bind.characterEscapeHandler", escapeHandler);

		final StringWriter sw = new StringWriter();

		marshaller.marshal(object, sw);

		return sw.toString();
	}

	public static boolean hasAttribute(
			@NonNull final Node node,
			@NonNull final String attributeName,
			@NonNull final String matchingValue)
	{
		final NamedNodeMap namedNodeMap = node.getAttributes();

		final Node attributeNode = namedNodeMap.getNamedItem(attributeName);
		if (attributeNode == null || attributeNode.getNodeValue() == null)
		{
			return false;
		}

		return matchingValue.equals(attributeNode.getNodeValue());
	}

	@Nullable
	public static Element getElementByTag(@NonNull final Node node, @NonNull final String tagName)
	{
		final Element element = node instanceof Document
				? ((Document)node).getDocumentElement()
				: (Element)node;

		final NodeList nodeList = element.getElementsByTagName(tagName);

		if (nodeList.getLength() == 0)
		{
			return null;
		}

		final Node childNode = nodeList.item(0);

		return (Element)childNode;
	}
}
