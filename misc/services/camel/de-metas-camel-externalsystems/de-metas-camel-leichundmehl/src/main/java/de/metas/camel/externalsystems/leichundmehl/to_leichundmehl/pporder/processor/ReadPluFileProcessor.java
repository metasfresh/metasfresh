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

import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.api.model.XMLPluElement;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.api.model.XMLPluRootElement;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.pporder.ExportPPOrderRouteContext;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.pporder.processor.file.FileUpdater;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.networking.DispatchMessageRequest;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.util.XMLUtil;
import de.metas.common.externalsystem.leichundmehl.JsonExternalSystemLeichMehlConfigProductMapping;
import de.metas.common.externalsystem.leichundmehl.JsonPluFileAudit;
import de.metas.common.util.FileUtil;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.RuntimeCamelException;
import org.w3c.dom.Document;

import javax.xml.transform.TransformerException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT;

public class ReadPluFileProcessor implements Processor
{
	private final ProcessLogger processLogger;

	public ReadPluFileProcessor(@NonNull final ProcessLogger processLogger)
	{
		this.processLogger = processLogger;
	}

	@Override
	public void process(final Exchange exchange)
	{
		final ExportPPOrderRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT, ExportPPOrderRouteContext.class);

		final String content = updatePluFile(context);

		final DispatchMessageRequest request = DispatchMessageRequest.builder()
				.connectionDetails(context.getConnectionDetails())
				.payload(content)
				.build();

		exchange.getIn().setBody(request, DispatchMessageRequest.class);
	}

	@NonNull
	private String updatePluFile(@NonNull final ExportPPOrderRouteContext context)
	{
		final String productBaseFolderName = context.getProductBaseFolderName();
		final JsonExternalSystemLeichMehlConfigProductMapping mapping = context.getProductMapping();

		try
		{
			final Path filePath = getPluFilePath(productBaseFolderName, mapping.getPluFile());

			final Document pluDocument = XMLUtil.readFromPath(filePath);

			final JsonPluFileAudit jsonPluFileAudit = updateDocument(pluDocument, filePath, context);

			final String fileContent = XMLUtil.toString(pluDocument);

			final XMLPluRootElement xmlPluRootElement = XMLPluRootElement.builder()
					.xmlPluElement(XMLPluElement.of(fileContent))
					.build();

			final String xmlRootFile = XMLUtil.convertToXML(xmlPluRootElement, XMLPluRootElement.class);

			context.setJsonPluFileAudit(jsonPluFileAudit);
			context.setPluFileXmlContent(xmlRootFile);
			context.setFilename(filePath.getFileName().toString());

			return xmlRootFile;
		}
		catch (final Exception e)
		{
			throw new RuntimeCamelException(e);
		}
	}

	@NonNull
	private JsonPluFileAudit updateDocument(
			@NonNull final Document pluDocument,
			@NonNull final Path filepath,
			@NonNull final ExportPPOrderRouteContext context) throws TransformerException
	{
		final FileUpdater fileUpdater = FileUpdater.builder()
				.fileName(filepath.getFileName().toString())
				.document(pluDocument)
				.context(context)
				.processLogger(processLogger)
				.build();

		return fileUpdater.updateDocument();
	}

	@NonNull
	private static Path getPluFilePath(@NonNull final String productBaseFolderName, @NonNull final String pluFilepath)
	{
		return Paths.get(productBaseFolderName,
						 FileUtil.normalizeAndValidateFilePath(pluFilepath));
	}
}
