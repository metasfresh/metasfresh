package de.metas.migration.cli.workspace_migrate;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;

import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.migration.cli
 * %%
 * Copyright (C) 2019 metas GmbH
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

@UtilityClass
final class XmlUtils
{
	private static XPath xPath = XPathFactory.newInstance().newXPath();

	public static org.w3c.dom.Document loadDocument(final File file)
	{
		try (final InputStream in = new FileInputStream(file))
		{
			final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			return documentBuilder.parse(in);
		}
		catch (final Exception e)
		{
			throw new RuntimeException("Failed loading XML document: " + file, e);
		}
	}

	public static String getString(final String xPathExpression, final Node node) throws XPathExpressionException
	{
		return (String)xPath.evaluate(xPathExpression, node, XPathConstants.STRING);
	}

}
