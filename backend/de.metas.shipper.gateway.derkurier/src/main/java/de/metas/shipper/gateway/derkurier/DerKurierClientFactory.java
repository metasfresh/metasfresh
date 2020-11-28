package de.metas.shipper.gateway.derkurier;

import de.metas.shipping.ShipperId;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.annotations.VisibleForTesting;

import de.metas.shipper.gateway.derkurier.misc.Converters;
import de.metas.shipper.gateway.derkurier.misc.DerKurierDeliveryOrderService;
import de.metas.shipper.gateway.derkurier.misc.DerKurierShipperConfig;
import de.metas.shipper.gateway.derkurier.misc.DerKurierShipperConfigRepository;
import de.metas.shipper.gateway.spi.ShipperGatewayClient;
import de.metas.shipper.gateway.spi.ShipperGatewayClientFactory;
import de.metas.util.Check;
import lombok.NonNull;

/*
 * #%L
 * de.metas.shipper.gateway.derkurier
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
public class DerKurierClientFactory implements ShipperGatewayClientFactory
{
	private final DerKurierShipperConfigRepository derKurierShipperConfigRepository;
	private final DerKurierDeliveryOrderService derKurierDeliveryOrderService;
	private final DerKurierDeliveryOrderRepository derKurierDeliveryOrderRepository;
	private final Converters converters;

	public DerKurierClientFactory(
			@NonNull final DerKurierShipperConfigRepository derKurierShipperConfigRepository,
			@NonNull final DerKurierDeliveryOrderService derKurierDeliveryOrderService,
			@NonNull final DerKurierDeliveryOrderRepository derKurierDeliveryOrderRepository,
			@NonNull final Converters converters)
	{
		this.derKurierShipperConfigRepository = derKurierShipperConfigRepository;
		this.derKurierDeliveryOrderRepository = derKurierDeliveryOrderRepository;
		this.derKurierDeliveryOrderService = derKurierDeliveryOrderService;
		this.converters = converters;
	}

	@Override
	public String getShipperGatewayId()
	{
		return DerKurierConstants.SHIPPER_GATEWAY_ID;
	}

	@Override
	public ShipperGatewayClient newClientForShipperId(@NonNull final ShipperId shipperId)
	{
		final DerKurierShipperConfig shipperConfig = derKurierShipperConfigRepository.retrieveConfigForShipperId(shipperId.getRepoId());
		return createClient(shipperConfig);
	}

	@VisibleForTesting
	DerKurierClient createClient(@NonNull final DerKurierShipperConfig shipperConfig)
	{
		final RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
				.rootUri(shipperConfig.getRestApiBaseUrl());

		final RestTemplate restTemplate = restTemplateBuilder.build();
		extractAndConfigureObjectMapperOfRestTemplate(restTemplate);

		return new DerKurierClient(
				restTemplate,
				converters,
				derKurierDeliveryOrderService,
				derKurierDeliveryOrderRepository);
	}

	/**
	 * Put JavaTimeModule into the rest template's jackson object mapper.
	 * <b>
	 * Note 1: there have to be better ways to achieve this, but i don't know them.
	 * thx to https://stackoverflow.com/a/47176770/1012103
	 * <b>
	 * Note 2: visible because this is the object mapper we run with; we want our unit tests to use it as well.
	 */
	@VisibleForTesting
	public static ObjectMapper extractAndConfigureObjectMapperOfRestTemplate(@NonNull final RestTemplate restTemplate)
	{
		final MappingJackson2HttpMessageConverter messageConverter = restTemplate
				.getMessageConverters()
				.stream()
				.filter(MappingJackson2HttpMessageConverter.class::isInstance)
				.map(MappingJackson2HttpMessageConverter.class::cast)
				.findFirst().orElseThrow(() -> new RuntimeException("MappingJackson2HttpMessageConverter not found"));

		final ObjectMapper objectMapper = messageConverter.getObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		return objectMapper;
	}
}
