package de.metas.vertical.pharma.msv3.server.metasfreshGateway;

import java.time.LocalDate;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import de.metas.ordercandidate.rest.JsonBPartnerInfo;
import de.metas.ordercandidate.rest.JsonOLCandCreateBulkRequest;
import de.metas.ordercandidate.rest.JsonOLCandCreateBulkRequest.JsonOLCandCreateBulkRequestBuilder;
import de.metas.ordercandidate.rest.JsonOLCandCreateBulkResponse;
import de.metas.ordercandidate.rest.JsonOLCandCreateRequest;
import de.metas.ordercandidate.rest.OrderCandidatesRestEndpoint;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequest;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequestPackage;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequestPackageItem;
import de.metas.vertical.pharma.msv3.protocol.types.BPartnerId;
import de.metas.vertical.pharma.msv3.protocol.types.Id;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.msv3.server
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Service
@Deprecated
public class MetasfreshServerGateway implements InitializingBean
{
	private final RestTemplate restTemplate;
	@Value("${metasfresh.server.baseUrl}")
	private String metasfreshServerBaseUrl;
	@Value("${metasfresh.server.authKey}")
	private String metasfreshServerAuthKey;

	public MetasfreshServerGateway(final RestTemplateBuilder restTemplateBuilder)
	{
		restTemplate = restTemplateBuilder.build();
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		if (metasfreshServerBaseUrl == null || metasfreshServerBaseUrl.isEmpty())
		{
			throw new IllegalStateException("metasfresh.server.baseUrl is not set");
		}
		if (metasfreshServerAuthKey == null || metasfreshServerAuthKey.isEmpty())
		{
			throw new IllegalStateException("metasfresh.server.authKey is not set");
		}
	}

	private String url(@NonNull final String path)
	{
		return metasfreshServerBaseUrl + path;
	}

	private HttpHeaders createHeaders()
	{
		final HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", metasfreshServerAuthKey);
		return headers;
	}

	private <T> T postRequest(final String path, final Object request, final Class<T> responseType)
	{
		final HttpEntity<Object> httpEntity = new HttpEntity<>(request, createHeaders());
		final ResponseEntity<T> httpResponse = restTemplate.postForEntity(url(path), httpEntity, responseType);
		return httpResponse.getBody();
	}

	public JsonOLCandCreateBulkResponse postOrder(final OrderCreateRequest request)
	{
		return postRequest(
				OrderCandidatesRestEndpoint.ENDPOINT + OrderCandidatesRestEndpoint.PATH_BULK,
				toJsonOLCandCreateBulkRequest(request),
				JsonOLCandCreateBulkResponse.class);
	}

	private JsonOLCandCreateBulkRequest toJsonOLCandCreateBulkRequest(final OrderCreateRequest request)
	{
		final JsonOLCandCreateBulkRequestBuilder bulkRequestBuilder = JsonOLCandCreateBulkRequest.builder();

		final BPartnerId bpartnerId = request.getBpartnerId();
		final Id orderId = request.getOrderId();

		for (final OrderCreateRequestPackage orderPackage : request.getOrderPackages())
		{
			for (final OrderCreateRequestPackageItem item : orderPackage.getItems())
			{
				bulkRequestBuilder.request(JsonOLCandCreateRequest.builder()
						.externalId(item.getId().getValueAsString())
						.bpartner(JsonBPartnerInfo.builder()
								.bpartnerId(bpartnerId.getBpartnerId())
								.bpartnerLocationId(bpartnerId.getBpartnerLocationId())
								.build())
						.poReference(orderId.getValueAsString())
						.dateRequired(LocalDate.now()) // FIXME: find out which is the next slot
						.productCode(item.getPzn().getValueAsString())
						.qty(item.getQty().getValueAsBigDecimal())
						.build());
			}
		}

		return bulkRequestBuilder.build();
	}
}
