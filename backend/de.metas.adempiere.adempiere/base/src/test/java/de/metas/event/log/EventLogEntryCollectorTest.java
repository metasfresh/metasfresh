package de.metas.event.log;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.event.Event;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class EventLogEntryCollectorTest
{
	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(new EventLogService(mock(EventLogsRepository.class)));
	}

	@Test
	public void createThreadLocalForEvent_cascaded()
	{
		final Event event = Event.builder()
				.build();

		try (final EventLogEntryCollector c1 = EventLogEntryCollector.createThreadLocalForEvent(event))
		{
			assertThat(EventLogEntryCollector.getThreadLocal()).isSameAs(c1);
			assertThat(c1.getPreviousEntryCollector()).isNull();

			try (final EventLogEntryCollector c2 = EventLogEntryCollector.createThreadLocalForEvent(event))
			{
				assertThat(EventLogEntryCollector.getThreadLocal()).isSameAs(c2);
				assertThat(c2.getPreviousEntryCollector()).isSameAs(c1);
			}

			assertThat(EventLogEntryCollector.getThreadLocal()).isSameAs(c1);
		}
	}
}
