package de.metas.marketing.gateway.cleverreach.restapi.models;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import de.metas.util.JSONObjectMapper;

/*
 * #%L
 * marketing-cleverreach
 * %%
 * Copyright (C) 2020 metas GmbH
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

class ErrorResponseTest
{

	@Test
	void deserializeErrorResponse()
	{
		final String errorMsg = "{ \"error\": { \"code\": 404,\"message\": \"Not Found\" } }";

		final JSONObjectMapper<ErrorResponse> mapper = JSONObjectMapper.forClass(ErrorResponse.class);
		final ErrorResponse errorREsponse = mapper.readValue(errorMsg);

		assertThat(errorREsponse.getError().getCode()).isEqualTo("404");
		assertThat(errorREsponse.getError().getMessage()).isEqualTo("Not Found");
	}
}
