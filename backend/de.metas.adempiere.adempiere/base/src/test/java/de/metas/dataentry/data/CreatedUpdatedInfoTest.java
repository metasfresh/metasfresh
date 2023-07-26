package de.metas.dataentry.data;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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

class CreatedUpdatedInfoTest
{
	private static final ZonedDateTime CREATED = LocalDate.of(2019, Month.MARCH, 8)
			.atTime(13, 20, 42, 2)
			.atZone(ZoneId.of("+01:00"));

	private static final ZonedDateTime UPDATED = LocalDate.of(2019, Month.MARCH, 8)
			.atTime(14, 20, 42, 2)
			.atZone(ZoneId.of("+01:00"));

	@BeforeAll
	static void init()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG);
	}

	@AfterAll
	static void afterAll()
	{
		validateSnapshots();
	}

	@Test
	void createNew_updated()
	{
		final DataEntryCreatedUpdatedInfo createdUpdatedInfo = DataEntryCreatedUpdatedInfo
				.createNew(UserId.ofRepoId(10), CREATED)
				.updated(UserId.ofRepoId(20), UPDATED);

		expect(createdUpdatedInfo).toMatchSnapshot();
	}

	@Test
	void serialize_deserialize() throws IOException
	{
		final DataEntryCreatedUpdatedInfo createdUpdatedInfo = DataEntryCreatedUpdatedInfo
				.createNew(UserId.ofRepoId(10), CREATED)
				.updated(UserId.ofRepoId(20), UPDATED);

		final JSONObjectMapper<DataEntryCreatedUpdatedInfo> objectMapper = JSONObjectMapper.forClass(DataEntryCreatedUpdatedInfo.class);

		final String jsonString = objectMapper.writeValueAsString(createdUpdatedInfo);
		final DataEntryCreatedUpdatedInfo deserializedObject = objectMapper.readValue(jsonString);

		assertThat(deserializedObject).isEqualTo(createdUpdatedInfo);
	}

}
