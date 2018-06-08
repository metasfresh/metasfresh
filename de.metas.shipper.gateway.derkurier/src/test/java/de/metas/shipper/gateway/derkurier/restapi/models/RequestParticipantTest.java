package de.metas.shipper.gateway.derkurier.restapi.models;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.shipper.gateway.derkurier.DerKurierClientFactory;

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

public class RequestParticipantTest
{

	@Test
	public void test() throws JsonProcessingException
	{
		final RequestParticipant sender = RequestParticipant.builder()
				.country("DE")
				.zip("50969")
				.timeFrom(LocalTime.of(10, 20, 30, 40))
				.timeTo(LocalTime.of(11, 21, 31, 41))
				.build();

		final ObjectMapper objectMapper = DerKurierClientFactory.extractAndConfigureObjectMapperOfRestTemplate(new RestTemplate());
		final String string = objectMapper.writeValueAsString(sender);
		assertThat(string).contains("10:20");
	}

}
