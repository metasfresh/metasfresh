/*
 * #%L
 * de-metas-common-bpartner
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

package de.metas.common.bpartner.request;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import lombok.NonNull;

public class JsonRequestBPartnerUpsertTest
{
	private ObjectMapper mapper = new ObjectMapper();
	
	@BeforeClass
	public static void beforeAll()
	{
		start();
	}

	@Test
	public void deserialize_1() throws IOException
	{
		final JsonRequestBPartnerUpsert result = deserialize("/de/metas/common/bpartner/request/JsonBPartnerUpsertRequest_1.json");

		final JsonRequestContact contact = result.getRequestItems().get(0).getBpartnerComposite().getContactsNotNull().getRequestItems().get(0).getContact();

		// test one particular case were we explicitly have "fax" : null in the JSON
		assertThat(contact.getFax()).isNull();
		assertThat(contact.isFaxSet()).isTrue();

		expect(result).toMatchSnapshot();
	}

	private JsonRequestBPartnerUpsert deserialize(@NonNull final String jsonResourceName) throws IOException
	{
		final JsonRequestBPartnerUpsert request = readInstanceFromFile(jsonResourceName);

		final String string = mapper.writeValueAsString(request);
		final JsonRequestBPartnerUpsert result = mapper.readValue(string, JsonRequestBPartnerUpsert.class);

		assertThat(result).isEqualTo(request);
		return result;
	}

	private JsonRequestBPartnerUpsert readInstanceFromFile(@NonNull final String jsonResourceName) throws IOException
	{

		final InputStream stream = JsonRequestBPartnerUpsertTest.class.getResourceAsStream(jsonResourceName);
		
		final String string = IOUtils.toString(stream, "UTF-8");
		assertThat(string).isNotNull(); // guard
		
		final JsonRequestBPartnerUpsert request = mapper.readValue(string,JsonRequestBPartnerUpsert.class);

		return request;
	}
}
