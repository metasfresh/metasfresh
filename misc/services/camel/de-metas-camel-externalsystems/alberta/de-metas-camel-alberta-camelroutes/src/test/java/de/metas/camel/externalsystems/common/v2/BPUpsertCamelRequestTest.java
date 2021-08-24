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

package de.metas.camel.externalsystems.common.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.metas.camel.externalsystems.alberta.patient.GetPatientsRouteTests;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsertItem;
import de.metas.common.bpartner.v2.request.JsonRequestComposite;
import de.metas.common.bpartner.v2.request.alberta.JsonAlbertaBPartner;
import de.metas.common.bpartner.v2.request.alberta.JsonCompositeAlbertaBPartner;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static de.metas.camel.externalsystems.alberta.patient.GetPatientsRouteTests.JSON_UPSERT_BPARTNER_REQUEST;
import static org.junit.jupiter.api.Assertions.*;

class BPUpsertCamelRequestTest
{

	@Test
	void testToString() throws IOException
	{
		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		
		final InputStream bparnerUpsertRequestExpected = this.getClass().getResourceAsStream(JSON_UPSERT_BPARTNER_REQUEST);
		final JsonRequestBPartnerUpsert jsonRequestBPartnerUpsert = objectMapper.readValue(bparnerUpsertRequestExpected, JsonRequestBPartnerUpsert.class);

		final BPUpsertCamelRequest request = BPUpsertCamelRequest.builder().orgCode("orgCode").jsonRequestBPartnerUpsert(jsonRequestBPartnerUpsert).build();
		request.toString();
	}
}