package de.metas.vertical.creditscore.creditpass;

/*
 * #%L
 * de.metas.vertical.creditscore.creditpass
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.metas.bpartner.BPartnerId;
import de.metas.util.Check;
import de.metas.vertical.creditscore.base.spi.CreditScoreClient;
import de.metas.vertical.creditscore.base.spi.CreditScoreClientFactory;
import de.metas.vertical.creditscore.creditpass.model.CreditPassConfig;
import de.metas.vertical.creditscore.creditpass.repository.CreditPassConfigRepository;
import lombok.NonNull;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class CreditPassClientFactory implements CreditScoreClientFactory
{
	private final CreditPassConfigRepository configRepository;

	public CreditPassClientFactory(@NonNull final CreditPassConfigRepository configRepository)
	{
		this.configRepository = configRepository;
	}

	@Override
	public CreditScoreClient newClientForBusinessPartner(@NonNull final BPartnerId businessPartnerId)
	{
		Check.errorIf(businessPartnerId.getRepoId() <= 0, "Given parameter businessPartnerId needs to be > 0; businessPartnerId={}", businessPartnerId.getRepoId());

		final CreditPassConfig config = configRepository.getConfigByBPartnerId(businessPartnerId);

		return createCreditPassClient(config);
	}

	private CreditPassClient createCreditPassClient(@NonNull final CreditPassConfig config)
	{
		final RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
				.rootUri(config.getRestApiBaseUrl());
		final RestTemplate restTemplate = restTemplateBuilder.build();
		extractAndConfigureObjectMapperOfRestTemplate(restTemplate);

		return new CreditPassClient(restTemplate, config);
	}

	private void extractAndConfigureObjectMapperOfRestTemplate(final RestTemplate restTemplate)
	{
		final MappingJackson2XmlHttpMessageConverter messageConverter = restTemplate
				.getMessageConverters()
				.stream()
				.filter(MappingJackson2XmlHttpMessageConverter.class::isInstance)
				.map(MappingJackson2XmlHttpMessageConverter.class::cast)
				.findFirst().orElseThrow(() -> new RuntimeException("MappingJackson2XmlHttpMessageConverter not found"));

		messageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
		final ObjectMapper objectMapper = messageConverter.getObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

	}
}
