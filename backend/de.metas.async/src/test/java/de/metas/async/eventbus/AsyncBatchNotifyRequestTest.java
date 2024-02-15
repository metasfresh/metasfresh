/*
 * #%L
 * de.metas.async
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.async.eventbus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.async.AsyncBatchId;
import org.adempiere.service.ClientId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AsyncBatchNotifyRequestTest
{
	@Test
	void testSerializable() throws JsonProcessingException
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();

		final AsyncBatchNotifyRequest obj = AsyncBatchNotifyRequest.builder()
				.clientId(ClientId.METASFRESH)
				.asyncBatchId(AsyncBatchId.ofRepoId(1234))
				.noOfEnqueuedWPs(10)
				.noOfProcessedWPs(5)
				.noOfErrorWPs(3)
				.build();

		final String json = objectMapper.writeValueAsString(obj);
		final AsyncBatchNotifyRequest obj2 = objectMapper.readValue(json, AsyncBatchNotifyRequest.class);
		Assertions.assertThat(obj2).usingRecursiveComparison().isEqualTo(obj);
		Assertions.assertThat(obj2).isEqualTo(obj);
	}
}