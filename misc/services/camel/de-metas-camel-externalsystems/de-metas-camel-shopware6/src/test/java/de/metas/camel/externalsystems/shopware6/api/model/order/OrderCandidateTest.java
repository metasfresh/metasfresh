/*
 * #%L
 * de-metas-camel-shopware6
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

package de.metas.camel.externalsystems.shopware6.api.model.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

<<<<<<< HEAD
=======
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.JSON_NODE_ORDER_CUSTOMER;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import static org.assertj.core.api.Assertions.*;

public class OrderCandidateTest
{
	private static final String ORDER_CANDIDATE_METASFRESH_ID = "1_OrderCandidate_Metasfresh_Id.json";

	private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	@Test
	public void givenExistingPath_whenGetCustomField_thenReturnValue() throws IOException
	{
		//given
<<<<<<< HEAD
		final String bPartnerCustomJsonPath = "/orderCustomer/customFields/metasfreshId";
=======
		final String bPartnerCustomJsonPath = "/customFields/metasfreshId";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		final String customFieldValue = "metasfreshId";

		final InputStream orderCand = this.getClass().getResourceAsStream(ORDER_CANDIDATE_METASFRESH_ID);
		final OrderCandidate orderCandidate = objectMapper.readValue(orderCand, OrderCandidate.class);

		//when
<<<<<<< HEAD
		final String customField = orderCandidate.getCustomField(bPartnerCustomJsonPath);
=======
		final Customer customer = Customer.of(orderCandidate.getCustomNode(JSON_NODE_ORDER_CUSTOMER), orderCandidate.getJsonOrder().getOrderCustomer());
		final String customField = customer.getCustomField(bPartnerCustomJsonPath);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		//then
		assertThat(customField).isNotNull();
		assertThat(customField).isEqualTo(customFieldValue);
	}

	@Test
	public void givenMissingPath_whenGetCustomField_thenReturnValue() throws IOException
	{
		//given
<<<<<<< HEAD
		final String bPartnerCustomJsonPath = "/orderCustomer/customFields/metasfreshId_missing";
=======
		final String bPartnerCustomJsonPath = "/customFields/metasfreshId_missing";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		final InputStream orderCand = this.getClass().getResourceAsStream(ORDER_CANDIDATE_METASFRESH_ID);
		final OrderCandidate orderCandidate = objectMapper.readValue(orderCand, OrderCandidate.class);

		//when
<<<<<<< HEAD
		final String customField = orderCandidate.getCustomField(bPartnerCustomJsonPath);
=======
		final Customer customer = Customer.of(orderCandidate.getCustomNode(JSON_NODE_ORDER_CUSTOMER), orderCandidate.getJsonOrder().getOrderCustomer());
		final String customField = customer.getCustomField(bPartnerCustomJsonPath);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		//then
		assertThat(customField).isNull();
	}
}
