package org.compiere.util;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.adempiere.process.rpl.XMLHelper;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * 
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/03749_Replication_interface_needs_to_support_binary_data_%282013010310000075%29
 */
public class TestEncodeDecodeBase64
{
	final String RESOURSE_TestPdf = "/org/compiere/util/03749_test.pdf";

	@Test
	public void testEncodeDecodeStringByte() throws Exception
	{
		final String dataStr = "1237890";
		final byte[] data = dataStr.getBytes();

		final byte[] dataEncoded = Util.encodeBase64(data).getBytes();
		// final String dataEncodedStr = new String(dataEncoded);

		final byte[] dataDecoded = Util.decodeBase64(new String(dataEncoded));
		final String dataDecodedStr = new String(dataDecoded);

		Assert.assertEquals(asList(data), asList(dataDecoded));
		Assert.assertEquals(dataStr, dataDecodedStr);
	}

	@Test
	public void testEncodeDecodePDFByte() throws Exception
	{
		final InputStream in = getClass().getResourceAsStream(RESOURSE_TestPdf);
		Assert.assertNotNull("Resource " + RESOURSE_TestPdf + " not found", in);

		final byte[] data = Util.readBytes(in);
		Assert.assertNotNull(data);

		final byte[] dataEncoded = Util.encodeBase64(data).getBytes();
		Assert.assertNotNull(dataEncoded);

		final byte[] dataDecoded = Util.decodeBase64(new String(dataEncoded));
		Assert.assertNotNull(dataDecoded);

		Assert.assertEquals("Expect same size", data.length, dataDecoded.length);
		Assert.assertEquals(asList(data), asList(dataDecoded));
	}

	@Test
	public void testEncodeDecodeXMLDocument() throws Exception
	{
		final InputStream in = getClass().getResourceAsStream(RESOURSE_TestPdf);
		Assert.assertNotNull("Resource " + RESOURSE_TestPdf + " not found", in);
		final byte[] data = Util.readBytes(in);
		Assert.assertNotNull(data);

		final String documentStr = createBase64XMLDocument(data);

		final Document documentReceived = XMLHelper.createDocumentFromString(documentStr);
		Element base64Element = XMLHelper.getElement("/Base64", documentReceived);

		final String dataStrReceived = getText(base64Element);
		final byte[] dataReceived = Util.decodeBase64(dataStrReceived);

		Assert.assertEquals(asList(data), asList(dataReceived));
	}

	// thx to http://www.java2s.com/Code/Java/XML/DOMUtilgetElementText.htm
	public static String getText(Element element)
	{
		StringBuffer buf = new StringBuffer();
		NodeList list = element.getChildNodes();
		boolean found = false;
		for (int i = 0; i < list.getLength(); i++)
		{
			Node node = list.item(i);
			if (node.getNodeType() == Node.TEXT_NODE)
			{
				buf.append(node.getNodeValue());
				found = true;
			}
		}
		return found ? buf.toString() : null;
	}

	private static final String createBase64XMLDocument(byte[] data) throws ParserConfigurationException
	{
		final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		final Document document = documentBuilder.newDocument();

		final Element rootElement = document.createElement("Base64");
		document.appendChild(rootElement);

		final String dataStr = DatatypeConverter.printBase64Binary(data);
		final Text dataText = document.createTextNode(dataStr);
		rootElement.appendChild(dataText);

		return XMLHelper.createStringFromDOMNode(rootElement);
	}

	private static List<Byte> asList(byte[] data)
	{
		if (data == null)
		{
			return null;
		}

		final List<Byte> list = new ArrayList<>(data.length);
		for (byte b : data)
		{
			list.add(b);
		}

		return list;
	}
}
