/**********************************************************************
 * This file is part of Adempiere ERP Bazaar                           *
 * http://www.adempiere.org                                            *
 *                                                                     *
 * Copyright (C) Trifon Trifonov.                                      *
 * Copyright (C) Contributors                                          *
 *                                                                     *
 * This program is free software; you can redistribute it and/or       *
 * modify it under the terms of the GNU General Public License         *
 * as published by the Free Software Foundation; either version 2      *
 * of the License, or (at your option) any later version.              *
 *                                                                     *
 * This program is distributed in the hope that it will be useful,     *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of      *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the        *
 * GNU General Public License for more details.                        *
 *                                                                     *
 * You should have received a copy of the GNU General Public License   *
 * along with this program; if not, write to the Free Software         *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,          *
 * MA 02110-1301, USA.                                                 *
 *                                                                     *
 * Contributors:                                                       *
 * - Trifon Trifonov (trifonnt@users.sourceforge.net)                  *
 *                                                                     *
 * Sponsors:                                                           *
 * - E-evolution (http://www.e-evolution.com)                          *
 **********************************************************************/
package org.adempiere.process.rpl;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.adempiere.exceptions.AdempiereException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Utility class which helps with XML processing.
 *
 * @author Trifon Trifonov
 * @version $Id$
 */
public class XMLHelper
{

	private static XPath xPath = XPathFactory.newInstance().newXPath();

	public static Element getElement(final String xPathExpression, final Node node)
			throws XPathExpressionException
	{
		return (Element)xPath.evaluate(xPathExpression, node,
				XPathConstants.NODE);
	}

	public static Node getNode(final String xPathExpression, final Node node)
			throws XPathExpressionException
	{
		return (Node)xPath
				.evaluate(xPathExpression, node, XPathConstants.NODE);
	}

	public static NodeList getNodeList(final String xPathExpression, final Node node)
			throws XPathExpressionException
	{
		return (NodeList)xPath.evaluate(xPathExpression, node,
				XPathConstants.NODESET);
	}

	public static Double getNumber(final String xPathExpression, final Node node)
			throws XPathExpressionException
	{
		return (Double)xPath.evaluate(xPathExpression, node,
				XPathConstants.NUMBER);
	}

	public static String getString(final String xPathExpression, final Node node)
			throws XPathExpressionException
	{
		return (String)xPath.evaluate(xPathExpression, node,
				XPathConstants.STRING);
	}

	public static Boolean getBoolean(final String xPathExpression, final Node node)
			throws XPathExpressionException
	{
		return (Boolean)xPath.evaluate(xPathExpression, node,
				XPathConstants.BOOLEAN);
	}

	public static Document createDocumentFromFile(final String pathToXmlFile)
			throws ParserConfigurationException, SAXException, IOException
	{
		// path to file is global
		final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
		final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
		// String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

		final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		// validate against XML Schema in dbsql2xml.xsd
		// documentBuilderFactory.setNamespaceAware(true);

		// INFO change validation to true. Someday when xsd file is complete...
		documentBuilderFactory.setValidating(false);
		documentBuilderFactory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
		// documentBuilderFactory.setAttribute(JAXP_SCHEMA_SOURCE, new File(pathToXsdFile));
		final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		final Document document = documentBuilder.parse(new File(pathToXmlFile));

		return document;
	}

	public static Document createDocumentFromString(final String str)
			throws ParserConfigurationException, SAXException, IOException
	{
		// path to file is global
		// String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
		// String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
		// String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

		final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		// validate against XML Schema in dbsql2xml.xsd
		// documentBuilderFactory.setNamespaceAware(true);

		// INFO change validation to true. Someday when xsd file is complete...
		documentBuilderFactory.setValidating(false);
		// documentBuilderFactory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
		// documentBuilderFactory.setAttribute(JAXP_SCHEMA_SOURCE, new File(pathToXsdFile));
		final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		final Document document = documentBuilder.parse(new InputSource(new StringReader(str)));

		return document;
	}

	// t.schoneberg@metas.de, 03132: code extraced from class 'TopicRplExportProcessor'
	public static String createStringFromDOMNode(final Node node)
	{
		final Writer writer = new StringWriter();
		writeDocument(writer, node);

		return writer.toString();
	}

	// t.schoneberg@metas.de, 03132: code extraced from class 'TopicRplExportProcessor'
	public static void writeDocument(final Writer writer, final Node node)
	{
		// Construct Transformer Factory and Transformer
		final TransformerFactory tranFactory = TransformerFactory.newInstance();
		final String jVersion = System.getProperty("java.version");
		if (jVersion.startsWith("1.5.0"))
		{
			tranFactory.setAttribute("indent-number", Integer.valueOf(1));
		}

		Transformer aTransformer;
		try
		{
			aTransformer = tranFactory.newTransformer();
		}
		catch (final TransformerConfigurationException e)
		{
			throw new AdempiereException(e);
		}
		aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
		final Source src = new DOMSource(node);

		// =================================== Write to String
		// Writer writer = new StringWriter();
		final Result dest2 = new StreamResult(writer);
		try
		{
			aTransformer.transform(src, dest2);
		}
		catch (final TransformerException e)
		{
			throw new AdempiereException(e);
		}
	}
}
