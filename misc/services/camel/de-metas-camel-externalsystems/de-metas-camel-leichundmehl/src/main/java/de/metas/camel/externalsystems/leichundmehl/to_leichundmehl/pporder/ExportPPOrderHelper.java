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

package de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.pporder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.DestinationDetails;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.networking.tcp.TCPConnectionDetails;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.leichundmehl.JsonExternalSystemLeichMehlConfigProductMapping;
import de.metas.common.externalsystem.leichundmehl.JsonExternalSystemLeichMehlPluFileConfigs;
import de.metas.common.util.Check;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.camel.Predicate;

import java.util.Map;

import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT;

@UtilityClass
public class ExportPPOrderHelper
{
	public static DestinationDetails getDestinationDetails(@NonNull final Map<String, String> parameters)
	{
		final String pluFileDestination = parameters.get(ExternalSystemConstants.PARAM_PLU_FILE_DESTINATION);

		final DestinationDetails.DestinationDetailsBuilder result = DestinationDetails.builder();

		if ("2DSK".equals(pluFileDestination))
		{
			result.pluFileServerFolder(parameters.get(ExternalSystemConstants.PARAM_PLU_FILE_SERVER_FOLDER));
		}
		else
		{
			result.tcpConnectionDetails(ExportPPOrderHelper.getTcpConnectionDetails(parameters));
		}
		return result.build();
	}

	@NonNull
	private TCPConnectionDetails getTcpConnectionDetails(@NonNull final Map<String, String> params)
	{
		final String tcpPortNumber = params.get(ExternalSystemConstants.PARAM_TCP_PORT_NUMBER);
		if (Check.isBlank(tcpPortNumber))
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_TCP_PORT_NUMBER);
		}

		final String tcpHost = params.get(ExternalSystemConstants.PARAM_TCP_HOST);
		if (Check.isBlank(tcpHost))
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_TCP_HOST);
		}

		return TCPConnectionDetails.builder()
				.tcpPort(Integer.parseInt(tcpPortNumber))
				.tcpHost(tcpHost)
				.build();
	}

	@NonNull
	public JsonExternalSystemLeichMehlConfigProductMapping getProductMapping(@NonNull final Map<String, String> params)
	{
		final String productMapping = params.get(ExternalSystemConstants.PARAM_CONFIG_MAPPINGS);
		if (Check.isBlank(productMapping))
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_CONFIG_MAPPINGS);
		}

		final ObjectMapper mapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

		try
		{
			return mapper.readValue(productMapping, JsonExternalSystemLeichMehlConfigProductMapping.class);
		}
		catch (final JsonProcessingException e)
		{
			throw new RuntimeException(e);
		}
	}

	@NonNull
	public JsonExternalSystemLeichMehlPluFileConfigs getPluFileConfigs(@NonNull final Map<String, String> params)
	{
		final String pluFileConfigs = params.get(ExternalSystemConstants.PARAM_PLU_FILE_CONFIG);
		if (Check.isBlank(pluFileConfigs))
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_PLU_FILE_CONFIG);
		}

		final ObjectMapper mapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

		try
		{
			return mapper.readValue(pluFileConfigs, JsonExternalSystemLeichMehlPluFileConfigs.class);
		}
		catch (final JsonProcessingException e)
		{
			throw new RuntimeException(e);
		}
	}

	@NonNull
	public Predicate isStoreFileOnDisk()
	{
		return exchange -> {
			final ExportPPOrderRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT, ExportPPOrderRouteContext.class);
			return context.getDestinationDetails().isStoreFileOnDisk();
		};
	}

	@NonNull
	public Predicate isPluFileExportAuditEnabled()
	{
		return exchange -> {
			final ExportPPOrderRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT, ExportPPOrderRouteContext.class);

			return context.isPluFileExportAuditEnabled();
		};
	}

	public static Integer getPPOrderMetasfreshId(@NonNull final Map<String, String> parameters)
	{
		final String ppOrderStr = parameters.get(ExternalSystemConstants.PARAM_PP_ORDER_ID);
		if (Check.isBlank(ppOrderStr))
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_PP_ORDER_ID);
		}
		try
		{
			return Integer.parseInt(ppOrderStr);
		}
		catch (final NumberFormatException e)
		{
			throw new RuntimeException("Unable to parse PP_Order_ID from string=" + ppOrderStr);
		}
	}
}
