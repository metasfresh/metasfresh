package de.metas.security.permissions.bpartner_hierarchy;

import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.metas.bpartner.BPartnerId;
import de.metas.security.permissions.bpartner_hierarchy.handlers.BPartnerDependentDocument;
import de.metas.user.UserId;
import de.metas.util.JSONObjectMapper;

/*
 * #%L
 * de.metas.business
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

public class BPartnerDependentDocumentEventTest
{
	private JSONObjectMapper<BPartnerDependentDocumentEvent> jsonObjectMapper;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		this.jsonObjectMapper = JSONObjectMapper.forClass(BPartnerDependentDocumentEvent.class);
	}

	@Nested
	public class serializeDeserialize
	{
		private void testSerializeDeserialize(final BPartnerDependentDocumentEvent event)
		{
			final String json = jsonObjectMapper.writeValueAsString(event);
			final BPartnerDependentDocumentEvent eventDeserialized = jsonObjectMapper.readValue(json);
			assertThat(eventDeserialized).isEqualTo(event);
		}

		@Test
		public void newRecord()
		{
			testSerializeDeserialize(BPartnerDependentDocumentEvent.newRecord(BPartnerDependentDocument.builder()
					.documentRef(TableRecordReference.of("DummyTable", 1))
					.newBPartnerId(BPartnerId.ofRepoId(1))
					.oldBPartnerId(null)
					.updatedBy(UserId.ofRepoId(666))
					.build()));
		}

		@Test
		public void bpartnerChanged()
		{
			testSerializeDeserialize(BPartnerDependentDocumentEvent.bpartnerChanged(BPartnerDependentDocument.builder()
					.documentRef(TableRecordReference.of("DummyTable", 1))
					.newBPartnerId(BPartnerId.ofRepoId(2))
					.oldBPartnerId(BPartnerId.ofRepoId(1))
					.updatedBy(UserId.ofRepoId(666))
					.build()));
		}
	}
}
