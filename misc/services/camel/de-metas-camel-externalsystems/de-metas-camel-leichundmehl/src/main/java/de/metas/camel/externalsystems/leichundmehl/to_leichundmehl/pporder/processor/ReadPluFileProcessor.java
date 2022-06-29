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

package de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.pporder.processor;

import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.api.model.XMLPluElement;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.api.model.XMLPluRootElement;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.pporder.ExportPPOrderRouteContext;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.tcp.DispatchMessageRequest;
import de.metas.common.externalsystem.JsonExternalSystemLeichMehlConfigProductMapping;
import de.metas.common.util.FileUtil;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.RuntimeCamelException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT;

public class ReadPluFileProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final ExportPPOrderRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT, ExportPPOrderRouteContext.class);

		final Optional<Object> pluFileContentOpt = getPluFileContent(context.getProductMapping(), context.getProductBaseFolderName());

		if (pluFileContentOpt.isEmpty())
		{
			exchange.getIn().setBody(null); //dev-note: nothing to route
			return;
		}

		final DispatchMessageRequest request = DispatchMessageRequest.builder()
				.connectionDetails(context.getConnectionDetails())
				.payload(pluFileContentOpt.get())
				.build();

		exchange.getIn().setBody(request, DispatchMessageRequest.class);
	}

	@NonNull
	private Optional<Object> getPluFileContent(
			@NonNull final JsonExternalSystemLeichMehlConfigProductMapping mapping,
			@NonNull final String productBaseFolderName)
	{
		try
		{
			final Path filePath = getPluFilePath(productBaseFolderName, mapping.getPluFile());

			final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			final DocumentBuilder builder = factory.newDocumentBuilder();

			final InputStream inputStream = new FileInputStream(filePath.toFile());
			final InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "Windows-1252");

			final BufferedReader reader = new BufferedReader(inputStreamReader);
			final InputSource input = new InputSource(reader);
			final Document doc = builder.parse(input);

			final Element root = doc.getDocumentElement();
			root.normalize();

			final TransformerFactory transFactory = TransformerFactory.newInstance();
			final Transformer transformer = transFactory.newTransformer();
			final StringWriter buffer = new StringWriter();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.transform(new DOMSource(doc),
								  new StreamResult(buffer));
			final String strContent = buffer.toString();

			final XMLPluRootElement xmlPluRootElement = XMLPluRootElement.builder()
					.xmlPluElement(XMLPluElement.builder()
										   .content(strContent)
										   .build())
					.build();

			return Optional.of(xmlPluRootElement);
		}
		catch (final Exception e)
		{
			throw new RuntimeCamelException(e);
		}
	}

	@NonNull
	private Path getPluFilePath(@NonNull final String productBaseFolderName, @NonNull final String pluFilepath)
	{
		return Paths.get(FileUtil.normalizeAndValidateFilePath(productBaseFolderName),
						 FileUtil.normalizeAndValidateFilePath(pluFilepath));
	}
}
