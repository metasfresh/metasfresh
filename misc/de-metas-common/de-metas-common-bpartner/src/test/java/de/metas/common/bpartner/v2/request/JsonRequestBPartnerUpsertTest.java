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

package de.metas.common.bpartner.v2.request;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.common.bpartner.v1.request.JsonRequestBPartnerUpsert;
import de.metas.common.bpartner.v1.request.JsonRequestContact;
import lombok.NonNull;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SnapshotExtension.class)
public class JsonRequestBPartnerUpsertTest
{
	private final ObjectMapper mapper = new ObjectMapper();
	private Expect expect;

	@Test
	public void deserialize_1() throws IOException
	{
		final JsonRequestBPartnerUpsert result = deserialize("/de/metas/common/bpartner/v1/request/JsonBPartnerUpsertRequest_1.json");

		final JsonRequestContact contact = result.getRequestItems().get(0).getBpartnerComposite().getContactsNotNull().getRequestItems().get(0).getContact();

		// test one particular case were we explicitly have "fax" : null in the JSON
		assertThat(contact.getFax()).isNull();
		assertThat(contact.isFaxSet()).isTrue();

		expect.serializer("orderedJson").toMatchSnapshot(result);
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
