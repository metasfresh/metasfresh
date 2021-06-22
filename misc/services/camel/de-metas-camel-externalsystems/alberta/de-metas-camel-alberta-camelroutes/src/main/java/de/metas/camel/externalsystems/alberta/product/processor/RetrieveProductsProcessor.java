/*
 * #%L
 * de-metas-camel-alberta-camelroutes
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.camel.externalsystems.alberta.product.processor;

import de.metas.camel.externalsystems.alberta.common.AlbertaConnectionDetails;
import de.metas.camel.externalsystems.alberta.patient.GetPatientsRouteConstants;
import de.metas.camel.externalsystems.common.GetProductsCamelRequest;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.util.CoalesceUtil;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.time.Instant;

import static de.metas.camel.externalsystems.alberta.product.PushProductsRouteConstants.ALBERTA_EXTERNAL_SYSTEM_CONFIG_TYPE;
import static de.metas.camel.externalsystems.alberta.product.PushProductsRouteConstants.ROUTE_PROPERTY_ALBERTA_PRODUCT_API;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PINSTANCE_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_UPDATED_AFTER;

/**
 * Prepares the exchange so that we can retrieve products from metasfresh and create them in Alberta.
 */
public class RetrieveProductsProcessor implements Processor
{
	@Override
	public void process(@NonNull final Exchange exchange) throws Exception
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		exchange.getIn().setHeader(GetPatientsRouteConstants.HEADER_ORG_CODE, request.getOrgCode());
		if (request.getAdPInstanceId() != null)
		{
			exchange.getIn().setHeader(HEADER_PINSTANCE_ID, request.getAdPInstanceId().getValue());
		}

		final String apiKey = request.getParameters().get(ExternalSystemConstants.PARAM_API_KEY);
		final String tenant = request.getParameters().get(ExternalSystemConstants.PARAM_TENANT);
		final String basePath = request.getParameters().get(ExternalSystemConstants.PARAM_BASE_PATH);

		final AlbertaConnectionDetails albertaConnectionDetails = AlbertaConnectionDetails.builder()
				.apiKey(apiKey)
				.tenant(tenant)
				.basePath(basePath)
				.build();

		final AlbertaProductApi albertaProductApi = AlbertaProductApi.of(albertaConnectionDetails);
		exchange.setProperty(ROUTE_PROPERTY_ALBERTA_PRODUCT_API, albertaProductApi);

		exchange.getIn().setBody(buildGetProductsCamelRequest(request));
	}

	private GetProductsCamelRequest buildGetProductsCamelRequest(@NonNull final JsonExternalSystemRequest request)
	{
		final String updatedAfter = CoalesceUtil.coalesce(
				request.getParameters().get(PARAM_UPDATED_AFTER),
				Instant.ofEpochSecond(0).toString());

		return GetProductsCamelRequest.builder()
				.pInstanceId(request.getAdPInstanceId())
				.externalSystemType(ALBERTA_EXTERNAL_SYSTEM_CONFIG_TYPE)
				.externalSystemChildConfigValue(request.getParameters().get(ExternalSystemConstants.PARAM_CHILD_CONFIG_VALUE))
				.since(updatedAfter)
				.build();
	}
}
