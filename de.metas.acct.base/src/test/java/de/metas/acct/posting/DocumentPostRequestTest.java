package de.metas.acct.posting;

import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.JsonObjectMapperHolder;
import de.metas.user.UserId;

/*
 * #%L
 * de.metas.acct.base
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

public class DocumentPostRequestTest
{
	private ObjectMapper jsonObjectMapper;

	@Before
	public void init()
	{
		jsonObjectMapper = 	JsonObjectMapperHolder.newJsonObjectMapper();
	}

	@Test
	public void testSerializeDeserialize() throws Exception
	{
		testSerializeDeserialize(DocumentPostRequest.builder()
				.record(TableRecordReference.of("MyDocumentTable", 1234))
				.clientId(ClientId.ofRepoId(1000))
				.force(true)
				.responseRequired(true)
				.onErrorNotifyUserId(UserId.METASFRESH)
				.build());
	}

	private void testSerializeDeserialize(final DocumentPostRequest request) throws Exception
	{
		final String json = jsonObjectMapper.writeValueAsString(request);
		final DocumentPostRequest requestDeserialized = jsonObjectMapper.readValue(json, DocumentPostRequest.class);
		Assert.assertEquals(request, requestDeserialized);
	}

}
