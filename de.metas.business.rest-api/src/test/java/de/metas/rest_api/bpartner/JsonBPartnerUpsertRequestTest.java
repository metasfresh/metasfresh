package de.metas.rest_api.bpartner;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.adempiere.test.AdempiereTestHelper;
import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import de.metas.rest_api.bpartner.request.JsonBPartnerUpsertRequest;
import de.metas.util.JSONObjectMapper;

/*
 * #%L
 * de.metas.business.rest-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class JsonBPartnerUpsertRequestTest
{
	@BeforeClass
	public static void beforeAll()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG);
	}

	@Test
	public void deserialize_1() throws IOException
	{
		final JsonBPartnerUpsertRequest result = deserialize("/de/metas/rest_api/bpartner/JsonBPartnerUpsertRequest_1.json");
		expect(result).toMatchSnapshot();
	}

	@Test
	public void deserialize_2() throws IOException
	{
		final JsonBPartnerUpsertRequest result = deserialize("/de/metas/rest_api/bpartner/JsonBPartnerUpsertRequest_2.json");
		expect(result).toMatchSnapshot();
	}

	private JsonBPartnerUpsertRequest deserialize(final String jsonResourceName) throws IOException
	{
		final JsonBPartnerUpsertRequest request = readInstanceFromFile(jsonResourceName);

		final JSONObjectMapper<JsonBPartnerUpsertRequest> mapper = JSONObjectMapper.forClass(JsonBPartnerUpsertRequest.class);
		final String string = mapper.writeValueAsString(request);
		final JsonBPartnerUpsertRequest result = mapper.readValue(string);

		assertThat(result).isEqualTo(request);
		return result;
	}

	private JsonBPartnerUpsertRequest readInstanceFromFile(final String jsonResourceName) throws IOException
	{

		final InputStream stream = JsonBPartnerUpsertRequestTest.class.getResourceAsStream(jsonResourceName);
		final String string = IOUtils.toString(stream, "UTF-8");
		assertThat(string).isNotNull(); // guard
		final JSONObjectMapper<JsonBPartnerUpsertRequest> mapper = JSONObjectMapper.forClass(JsonBPartnerUpsertRequest.class);
		final JsonBPartnerUpsertRequest request = mapper.readValue(string);

		return request;
	}
}
