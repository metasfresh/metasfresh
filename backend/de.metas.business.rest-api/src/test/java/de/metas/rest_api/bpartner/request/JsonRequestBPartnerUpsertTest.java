package de.metas.rest_api.bpartner.request;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.adempiere.test.AdempiereTestHelper;
import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import de.metas.util.JSONObjectMapper;
import lombok.NonNull;

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

public class JsonRequestBPartnerUpsertTest
{
	@BeforeClass
	public static void beforeAll()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG);
	}

	@Test
	public void deserialize_1() throws IOException
	{
		final JsonRequestBPartnerUpsert result = deserialize("/de/metas/rest_api/bpartner/JsonBPartnerUpsertRequest_1.json");
		expect(result).toMatchSnapshot();
	}

	private JsonRequestBPartnerUpsert deserialize(@NonNull final String jsonResourceName) throws IOException
	{
		final JsonRequestBPartnerUpsert request = readInstanceFromFile(jsonResourceName);

		final JSONObjectMapper<JsonRequestBPartnerUpsert> mapper = JSONObjectMapper.forClass(JsonRequestBPartnerUpsert.class);
		final String string = mapper.writeValueAsString(request);
		final JsonRequestBPartnerUpsert result = mapper.readValue(string);

		assertThat(result).isEqualTo(request);
		return result;
	}

	private JsonRequestBPartnerUpsert readInstanceFromFile(@NonNull final String jsonResourceName) throws IOException
	{

		final InputStream stream = JsonRequestBPartnerUpsertTest.class.getResourceAsStream(jsonResourceName);
		final String string = IOUtils.toString(stream, "UTF-8");
		assertThat(string).isNotNull(); // guard
		final JSONObjectMapper<JsonRequestBPartnerUpsert> mapper = JSONObjectMapper.forClass(JsonRequestBPartnerUpsert.class);
		final JsonRequestBPartnerUpsert request = mapper.readValue(string);

		return request;
	}
}
