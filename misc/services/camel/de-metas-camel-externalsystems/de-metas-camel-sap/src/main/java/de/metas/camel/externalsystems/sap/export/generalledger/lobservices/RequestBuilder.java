/*
 * #%L
 * de-metas-camel-sap
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.camel.externalsystems.sap.export.generalledger.lobservices;

import com.fasterxml.jackson.databind.JsonNode;
import de.metas.common.util.Check;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.annotation.Nullable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@UtilityClass
public class RequestBuilder
{
	@NonNull
	public String getXMLRequest(@NonNull final List<JsonNode> documentTypeGLItems) throws ParserConfigurationException, TransformerException
	{
		if (documentTypeGLItems.isEmpty())
		{
			throw new RuntimeException("documentTypeGLItems is empty!");
		}

		final Document doc = initDocument();
		final Element root = getRoot(doc);

		final Element header = getHeader(doc, documentTypeGLItems.get(0));
		root.appendChild(header);

		final Element lines = getLines(doc, documentTypeGLItems);
		root.appendChild(lines);

		return writeToString(doc);
	}

	@NonNull
	private static Document initDocument() throws ParserConfigurationException
	{
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);

		final DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.newDocument();
	}

	@NonNull
	private static Element getRoot(@NonNull final Document doc)
	{
		final Element root = doc.createElementNS(Format.NAMESPACE_URI, Format.ROOT_TAG_NAME);
		root.setPrefix(Format.NAMESPACE_PREFIX);
		doc.appendChild(root);

		return root;
	}

	@NonNull
	private static Element getHeader(@NonNull final Document doc, @NonNull final JsonNode node)
	{
		final Element header = doc.createElement(Format.NAMESPACE_PREFIX + ":" + Format.HEAD_TAG_NAME);
		header.setAttribute("xmlns", Format.HEAD_NAMESPACE_URI);

		Format.HEADER_FIELDS.forEach(fieldName -> {

			final String value = getAsStringOrNull(node, fieldName);

			final Element element = doc.createElement(fieldName);

			if (value != null)
			{
				element.appendChild(doc.createTextNode(value));
			}

			header.appendChild(element);
		});

		return header;
	}

	@NonNull
	private static Element getLine(@NonNull final Document doc, @NonNull final JsonNode node, @NonNull final AtomicInteger seqNoCounter)
	{
		final Element line = doc.createElement(Format.ITEM_CONTENT_TAG);
		line.setAttribute("xmlns", Format.ITEM_CONTENT_NAMESPACE_URI);

		final Element seqNo = doc.createElement(Format.ITEM_SEQ_NO_TAG);
		seqNo.appendChild(doc.createTextNode(String.valueOf(seqNoCounter.incrementAndGet())));
		line.appendChild(seqNo);

		Format.LINE_FIELDS.forEach(fieldName -> {

			final String value = getAsStringOrNull(node, fieldName);

			final Element element = doc.createElement(fieldName);

			if (value != null)
			{
				element.appendChild(doc.createTextNode(value));
			}

			line.appendChild(element);
		});

		return line;
	}

	@NonNull
	private static Element getLines(@NonNull final Document doc, @NonNull final List<JsonNode> nodes)
	{
		final Element lines = doc.createElement(Format.NAMESPACE_PREFIX + ":" + Format.ITEM_TAG_NAME);
		lines.setAttribute("xmlns", Format.ITEM_NAMESPACE_URI);

		final AtomicInteger seqNoCounter = new AtomicInteger(0);

		nodes.stream()
				.map(node -> getLine(doc, node, seqNoCounter))
				.forEach(lines::appendChild);

		return lines;
	}

	@NonNull
	private static String writeToString(@NonNull final Document document) throws TransformerException
	{
		final DOMSource domSource = new DOMSource(document);
		final StringWriter writer = new StringWriter();
		final StreamResult result = new StreamResult(writer);
		final TransformerFactory tf = TransformerFactory.newInstance();

		final Transformer transformer = tf.newTransformer();
		transformer.transform(domSource, result);

		return writer.toString();
	}

	@Nullable
	private String getAsStringOrNull(@NonNull final JsonNode node, @NonNull final String elemName)
	{
		return Optional.ofNullable(node.get(elemName))
				.filter(elem -> !elem.isNull())
				.map(JsonNode::asText)
				.filter(Check::isNotBlank)
				.orElse(null);
	}
}
