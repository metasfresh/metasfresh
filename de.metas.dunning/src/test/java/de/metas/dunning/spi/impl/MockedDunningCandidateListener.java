package de.metas.dunning.spi.impl;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.dunning
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.ArrayList;
import java.util.List;

import de.metas.dunning.model.I_C_Dunning_Candidate;
import de.metas.dunning.spi.IDunningCandidateListener;

public class MockedDunningCandidateListener implements IDunningCandidateListener
{
	public static class Event
	{
		private final String eventName;
		private final I_C_Dunning_Candidate candidate;
		protected int triggeredCount = 0;

		Event(String eventName, I_C_Dunning_Candidate candidate)
		{
			super();
			this.eventName = eventName;
			this.candidate = candidate;
			this.triggeredCount = 0;
		}

		public int getTriggeredCount()
		{
			return triggeredCount;
		}

		public String getEventName()
		{
			return eventName;
		}

		public I_C_Dunning_Candidate getCandidate()
		{
			return candidate;
		}

		@Override
		public String toString()
		{
			return "Event [eventName=" + eventName + ", candidate=" + candidate + ", triggeredCount=" + triggeredCount + "]";
		}
	}

	private final List<Event> events = new ArrayList<>();
	private int delayOnEventMillis = 0;

	@Override
	public void onEvent(String eventName, I_C_Dunning_Candidate candidate)
	{
		synchronized (events)
		{
			if (delayOnEventMillis > 0)
			{
				try
				{
					Thread.sleep(delayOnEventMillis);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace(); // NOPMD by tsa on 3/24/13 5:43 PM
				}
			}
			Event event = getEvent(eventName, candidate);
			if (event == null)
			{
				event = new Event(eventName, candidate);
				events.add(event);
			}

			event.triggeredCount++;
		}
	}

	public int getDelayOnEventMillis()
	{
		return delayOnEventMillis;
	}

	public void setDelayOnEventMillis(int delayOnEventMillis)
	{
		this.delayOnEventMillis = delayOnEventMillis;
	}

	public Event getEvent(String eventName, I_C_Dunning_Candidate candidate)
	{
		for (Event event : events)
		{
			if (event.getEventName().equals(eventName)
					&& event.getCandidate().getC_Dunning_Candidate_ID() == candidate.getC_Dunning_Candidate_ID())
			{
				return event;
			}
		}

		return null;
	}

	public void assertTriggered(final String eventName, final I_C_Dunning_Candidate candidate)
	{
		final Event event = getEvent(eventName, candidate);

		assertThat(event)
				.as("Event " + eventName + " was triggered for " + candidate)
				.isNotNull();

		assertThat(event.getTriggeredCount())
				.as("Event " + eventName + " shall be triggered at least once for " + candidate)
				.isGreaterThanOrEqualTo(1);
	}

	public void assertNotTriggered(final String eventName, final I_C_Dunning_Candidate candidate)
	{
		final Event event = getEvent(eventName, candidate);
		assertThat(event)
				.as("Event " + eventName + " was never triggered for " + candidate)
				.isNull();
	}
}
