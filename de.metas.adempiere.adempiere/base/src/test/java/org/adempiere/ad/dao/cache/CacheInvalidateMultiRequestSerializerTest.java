package org.adempiere.ad.dao.cache;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.cache.model.CacheInvalidateRequest;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class CacheInvalidateMultiRequestSerializerTest
{
	private CacheInvalidateMultiRequestSerializer jsonSerializer = new CacheInvalidateMultiRequestSerializer();

	private void testSerializeDeserialize(final CacheInvalidateRequest request)
	{
		final CacheInvalidateMultiRequest multiRequest = CacheInvalidateMultiRequest.of(request);
		final String json = jsonSerializer.toJson(multiRequest);
		final CacheInvalidateMultiRequest multiRequest2 = jsonSerializer.fromJson(json);
		assertThat(multiRequest2).isEqualTo(multiRequest);
	}

	@Test
	public void request_all()
	{
		testSerializeDeserialize(CacheInvalidateRequest.all());
	}

	@Test
	public void request_allRecordsForTable()
	{
		testSerializeDeserialize(CacheInvalidateRequest.allRecordsForTable("SomeTable"));
	}

	@Test
	public void request_rootRecord()
	{
		testSerializeDeserialize(CacheInvalidateRequest.rootRecord("SomeTable", 123));
	}

	@Test
	public void request_allChildRecords()
	{
		testSerializeDeserialize(CacheInvalidateRequest.allChildRecords("SomeRootTable", 456, "SomeChildTable"));
	}

	@Test
	public void request_childRecord()
	{
		testSerializeDeserialize(CacheInvalidateRequest.builder()
				.rootRecord("SomeRootTable", 123)
				.childRecord("SomeChildTable", 456)
				.build());
	}

}
