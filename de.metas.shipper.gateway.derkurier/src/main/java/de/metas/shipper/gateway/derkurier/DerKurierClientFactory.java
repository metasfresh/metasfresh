package de.metas.shipper.gateway.derkurier;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.annotations.VisibleForTesting;

import de.metas.shipper.gateway.derkurier.misc.Converters;
import de.metas.shipper.gateway.derkurier.misc.DerKurierShipperConfig;
import de.metas.shipper.gateway.derkurier.misc.ParcelNumberGenerator;
import de.metas.shipper.gateway.derkurier.model.I_DerKurier_Shipper_Config;
import de.metas.shipper.gateway.spi.ShipperGatewayClient;
import de.metas.shipper.gateway.spi.ShipperGatewayClientFactory;
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
	@Override
	public String getShipperGatewayId()
	{
		return DerKurierConstants.SHIPPER_GATEWAY_ID;
	}

	@Override
	public ShipperGatewayClient newClientForShipperId(final int shipperId)
	{
		final I_DerKurier_Shipper_Config shipperConfigRecord = Services.get(IQueryBL.class).createQueryBuilder(I_DerKurier_Shipper_Config.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DerKurier_Shipper_Config.COLUMN_M_Shipper_ID, shipperId)
				.create()
				.firstOnly(I_DerKurier_Shipper_Config.class);

		final DerKurierShipperConfig shipperConfig = DerKurierShipperConfig.builder()
				.restApiBaseUrl(shipperConfigRecord.getAPIServerBaseURL())
				.customerNumber(shipperConfigRecord.getCustomerNo()).build();
		return createClient(shipperConfig);
	}

	@VisibleForTesting
	DerKurierClient createClient(@NonNull final DerKurierShipperConfig shipperConfig)
	{
		final RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
				.rootUri(shipperConfig.getRestApiBaseUrl());

		final RestTemplate restTemplate = restTemplateBuilder.build();
		extractAndConfigureObjectMapperOfRestTemplate(restTemplate);

		return new DerKurierClient(restTemplate, new Converters(), new ParcelNumberGenerator("blah-blah"));
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
	public static ObjectMapper extractAndConfigureObjectMapperOfRestTemplate(final RestTemplate restTemplate)
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
