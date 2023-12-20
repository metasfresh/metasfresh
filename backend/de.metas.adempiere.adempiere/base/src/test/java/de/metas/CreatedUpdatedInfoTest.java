/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas;

import au.com.origin.snapshots.Expect;

import au.com.origin.snapshots.junit5.SnapshotExtension;
import de.metas.user.UserId;
import de.metas.util.JSONObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SnapshotExtension.class)
class CreatedUpdatedInfoTest
{
	private Expect expect;
	private static final ZonedDateTime CREATED = LocalDate.of(2019, Month.MARCH, 8)
			.atTime(13, 20, 42, 2)
			.atZone(ZoneId.of("+01:00"));

	private static final ZonedDateTime UPDATED = LocalDate.of(2019, Month.MARCH, 8)
			.atTime(14, 20, 42, 2)
			.atZone(ZoneId.of("+01:00"));

	@Test
	void createNew_updated()
	{
		final CreatedUpdatedInfo createdUpdatedInfo = CreatedUpdatedInfo
				.createNew(UserId.ofRepoId(10), CREATED)
				.updated(UserId.ofRepoId(20), UPDATED);

		expect.serializer("orderedJson").toMatchSnapshot(createdUpdatedInfo);
	}

	@Test
	void serialize_deserialize()
	{
		final CreatedUpdatedInfo createdUpdatedInfo = CreatedUpdatedInfo
				.createNew(UserId.ofRepoId(10), CREATED)
				.updated(UserId.ofRepoId(20), UPDATED);

		final JSONObjectMapper<CreatedUpdatedInfo> objectMapper = JSONObjectMapper.forClass(CreatedUpdatedInfo.class);

		final String jsonString = objectMapper.writeValueAsString(createdUpdatedInfo);
		final CreatedUpdatedInfo deserializedObject = objectMapper.readValue(jsonString);

		assertThat(deserializedObject).isEqualTo(createdUpdatedInfo);
	}

}
