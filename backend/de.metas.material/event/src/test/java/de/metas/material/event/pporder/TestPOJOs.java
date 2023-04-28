/*
 * #%L
 * metasfresh-material-event
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

package de.metas.material.event.pporder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.metas.bpartner.BPartnerId;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ResourceId;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.*;

public class TestPOJOs
{
	private final ObjectMapper objectMapper = new ObjectMapper()
			.registerModule(new JavaTimeModule())
			.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

	@Test
	public void givenPPOrderCandidate_whenSerializeDeserialize_thenSuccess() throws IOException
	{
		testSerializeDeserializeObject(getMockPPOrderCandidate());
	}

	private void testSerializeDeserializeObject(final Object value) throws IOException
	{
		final Class<?> valueClass = value.getClass();
		final String json = objectMapper.writeValueAsString(value);
		final Object value2 = objectMapper.readValue(json, valueClass);
		assertThat(value2).isEqualTo(value);
	}

	private PPOrderCandidate getMockPPOrderCandidate()
	{
		return PPOrderCandidate.builder()
				.ppOrderCandidateId(1)
				.ppOrderData(PPOrderData.builder()
									 .clientAndOrgId(ClientAndOrgId.ofClientAndOrg(2, 3))
									 .plantId(ResourceId.ofRepoId(4))
									 .warehouseId(WarehouseId.ofRepoId(5))
									 .bpartnerId(BPartnerId.ofRepoId(6))
									 .productPlanningId(7)
									 .orderLineId(8)
									 .datePromised(Instant.now())
									 .materialDispoGroupId(MaterialDispoGroupId.ofInt(9))
									 .dateStartSchedule(Instant.now())
									 .productDescriptor(ProductDescriptor.completeForProductIdAndEmptyAttribute(1))
									 .qtyRequired(BigDecimal.ONE)
									 .qtyDelivered(BigDecimal.ZERO)
									 .build())
				.build();
	}
}
