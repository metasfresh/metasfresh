package de.metas.security.permissions.record_access.handlers;

import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.junit.Before;
import org.junit.Test;

import de.metas.security.Principal;
import de.metas.security.permissions.Access;
import de.metas.security.permissions.record_access.PermissionIssuer;
import de.metas.security.permissions.record_access.RecordAccess;
import de.metas.user.UserId;
import de.metas.util.JSONObjectMapper;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class RecordAccessChangeEventTest
{
	private JSONObjectMapper<RecordAccessChangeEvent> jsonObjectMapper;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		this.jsonObjectMapper = JSONObjectMapper.forClass(RecordAccessChangeEvent.class);
	}

	@Test
	public void testSerializeDeserialize()
	{
		testSerializeDeserialize(RecordAccessChangeEvent.accessGrant(recordAccess(1)));

		testSerializeDeserialize(RecordAccessChangeEvent.accessRevoke(recordAccess(2)));

		testSerializeDeserialize(RecordAccessChangeEvent.builder()
				.accessGrant(recordAccess(1))
				.accessGrant(recordAccess(2))
				.accessGrant(recordAccess(3))
				.accessRevoke(recordAccess(4))
				.accessRevoke(recordAccess(5))
				.accessRevoke(recordAccess(6))
				.build());
	}

	private RecordAccess recordAccess(final int recordId)
	{
		return RecordAccess.builder()
				.recordRef(TableRecordReference.of("DummyTable", recordId))
				.principal(Principal.userId(UserId.METASFRESH))
				.permission(Access.READ)
				.issuer(PermissionIssuer.MANUAL)
				.build();
	}

	public void testSerializeDeserialize(final RecordAccessChangeEvent event)
	{
		final String json = jsonObjectMapper.writeValueAsString(event);
		final RecordAccessChangeEvent eventDeserialized = jsonObjectMapper.readValue(json);
		assertThat(eventDeserialized).isEqualTo(event);
	}
}
