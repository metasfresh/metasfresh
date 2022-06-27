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

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.common.XmlMapperHolder;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.api.model.XMLPluElement;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.api.model.XMLPluRootElement;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.pporder.ExportPPOrderRouteContext;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.pporder.PluFileDetails;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.tcp.DispatchMessageRequest;
import de.metas.common.externalsystem.JsonExternalSystemLeichMehlConfigProductMapping;
import de.metas.common.util.FileUtil;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.RuntimeCamelException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.Function;

import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT;

public class ReadPluFileProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final ExportPPOrderRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT, ExportPPOrderRouteContext.class);

		final Optional<PluFileDetails> pluFileDetailsOpt = getPluFileDetails(context.getProductMapping());

		if (pluFileDetailsOpt.isEmpty())
		{
			exchange.getIn().setBody(null); //dev-note: nothing to route
			return;
		}

		final PluFileDetails pluFileDetails = pluFileDetailsOpt.get();

		final Optional<String> filenameOpt = concatenateFilename(context.getProductBaseFolderName(), pluFileDetails.getPath());

		if (filenameOpt.isEmpty())
		{
			exchange.getIn().setBody(null); //dev-note: nothing to route
			return;
		}

		final DispatchMessageRequest request = DispatchMessageRequest.builder()
				.tcpConnection(context.getTcpDetails())
				.tcpPayload(pluFileDetails.getContent())
				.tcpFilename(filenameOpt.get())
				.build();

		exchange.getIn().setBody(request, DispatchMessageRequest.class);
	}

	@NonNull
	private Optional<PluFileDetails> getPluFileDetails(@NonNull final JsonExternalSystemLeichMehlConfigProductMapping mapping)
	{
		final XmlMapper xmlMapper = XmlMapperHolder.sharedXmlMapper();

		try
		{
			final Optional<Path> filePathOpt = FileUtil.normalizeFilePath(mapping.getPluFile())
					.map(Paths::get);

			if (filePathOpt.isEmpty())
			{
				return Optional.empty();
			}

			final Path filePath = filePathOpt.get();

			final Object pluFileContent = xmlMapper.readValue(Files.readAllBytes(filePath), Object.class);

			final Optional<XMLPluRootElement> xmlPluRootElement = Optional.ofNullable(pluFileContent)
					.map(content -> XMLPluRootElement.builder()
							.xmlPluElement(XMLPluElement.builder()
												   .content(content)
												   .build())
							.build());

			if (xmlPluRootElement.isEmpty())
			{
				return Optional.empty();
			}

			final String fileContent = xmlMapper.writeValueAsString(xmlPluRootElement.get());

			return Optional.of(PluFileDetails.builder()
									   .path(filePath)
									   .content(fileContent)
									   .build());
		}
		catch (final Exception e)
		{
			throw new RuntimeCamelException(e);
		}
	}

	@NonNull
	private Optional<String> concatenateFilename(@NonNull final String productBaseFolderName, @NonNull final Path filePath)
	{
		final Function<StringBuilder, StringBuilder> appendFileSeparatorIfNeeded = pathBuilder -> {
			if (!filePath.startsWith(File.separator))
			{
				pathBuilder.append(File.separator);
			}

			return pathBuilder.append(filePath);
		};

		return FileUtil.normalizeFilePath(productBaseFolderName)
				.map(basePath -> new StringBuilder().append(basePath))
				.map(appendFileSeparatorIfNeeded)
				.map(StringBuilder::toString);
	}
}
