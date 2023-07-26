package de.metas.dataentry.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import de.metas.common.util.time.SystemTime;
import org.junit.Test;

import de.metas.dataentry.DataEntryFieldId;
import de.metas.user.UserId;

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

public class DataEntryRecordFieldDateTimeTest
{
	/**
	 * Create two different instances that consist of the same primitives; make sure the two instances are equal.
	 */
	@Test
	public void equals()
	{
		final long millis = SystemTime.millis();

		final ZonedDateTime time1 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.of("CET"));
		final ZonedDateTime time2 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.of("CET"));
		assertThat(time1).isEqualTo(time2);// guard

		final DataEntryFieldId id1 = DataEntryFieldId.ofRepoId(10);
		final DataEntryFieldId id2 = DataEntryFieldId.ofRepoId(10);
		assertThat(id1).isEqualTo(id2);// guard

		final ZonedDateTime createdTime1 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.of("CET"));
		final ZonedDateTime createdTime2 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.of("CET"));

		final ZonedDateTime updatedTime1 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(millis + 100), ZoneId.of("CET"));
		final ZonedDateTime updatedTime2 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(millis + 100), ZoneId.of("CET"));

		final DataEntryCreatedUpdatedInfo createdUpdatedInfo1 = DataEntryCreatedUpdatedInfo.of(createdTime1, UserId.ofRepoId(10), updatedTime1, UserId.ofRepoId(20));
		final DataEntryCreatedUpdatedInfo createdUpdatedInfo2 = DataEntryCreatedUpdatedInfo.of(createdTime2, UserId.ofRepoId(10), updatedTime2, UserId.ofRepoId(20));
		assertThat(createdUpdatedInfo1).isEqualTo(createdUpdatedInfo2); // guard

		final DataEntryRecordFieldDateTime value1 = DataEntryRecordFieldDateTime.of(id1, createdUpdatedInfo1, time1);
		final DataEntryRecordFieldDateTime value2 = DataEntryRecordFieldDateTime.of(id2, createdUpdatedInfo2, time2);

		// invoke the method under test
		assertThat(value1).isEqualTo(value2);
	}

}
