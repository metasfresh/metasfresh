package de.metas.event;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.UUID;

import org.junit.Test;

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

public class EventTest
{
	@Test
	public void addRecipient_User_ID_when_different_then_events_arent_equal()
	{
		final UUID uuid = UUID.randomUUID();
		final Instant when = Instant.now();

		final Event event1 = Event.builder()
				.setSummary("Summary1")
				.setDetailPlain("Detail1")
				.setUUID(uuid)
				.setWhen(when)
				.addRecipient_User_ID(10)
				.addRecipient_User_ID(20)
				.addRecipient_User_ID(40)
				.addRecipient_User_ID(30)
				.addRecipient_User_ID(15)
				.build();
		final Event event2 = Event.builder()
				.setSummary("Summary1")
				.setDetailPlain("Detail1")
				.setUUID(uuid)
				.setWhen(when)
				.addRecipient_User_ID(10)
				.addRecipient_User_ID(20)
				.addRecipient_User_ID(40)
				.addRecipient_User_ID(30)
				.addRecipient_User_ID(16)
				.build();

		assertThat(event1).isNotEqualTo(event2);
	}

	@Test
	public void toBuilder_adds_add_event_fields()
	{
		final UUID uuid = UUID.randomUUID();
		final Instant when = Instant.now();

		final Event event1 = Event.builder()
				.setSummary("Summary1")
				.setDetailPlain("Detail1")
				.setUUID(uuid)
				.setWhen(when)
				.addRecipient_User_ID(10)
				.addRecipient_User_ID(20)
				.addRecipient_User_ID(40)
				.addRecipient_User_ID(30)
				.addRecipient_User_ID(15)
				.build();

		final Event event2 = event1.toBuilder().build();

		assertThat(event1)
				.as("event1.toBuilder().build() needs to forward all data from event1 to event2 and such that they are equal;\nevent1 = %s\nevent2 = %s ",
						event1, event2)
				.isEqualTo(event2);
	}
}
