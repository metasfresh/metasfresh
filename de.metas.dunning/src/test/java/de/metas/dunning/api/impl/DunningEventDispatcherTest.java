package de.metas.dunning.api.impl;

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

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import de.metas.dunning.DunningTestBase;
import de.metas.dunning.api.IDunningBL;
import de.metas.dunning.invoice.api.IInvoiceSourceBL;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import de.metas.dunning.spi.impl.MockedDunningCandidateListener;
import de.metas.dunning.spi.impl.MockedDunningCandidateListener.Event;

public class DunningEventDispatcherTest extends DunningTestBase
{
	/**
	 * Used only for unit tests.
	 */
	private final static String EVENT_TestEvent = IDunningBL.class.getName() + "#EVENT_TestEvent";

	private DunningEventDispatcher dispatcher;

	@Override
	protected void createMasterData()
	{
		dispatcher = new DunningEventDispatcher();
	}

	@Test
	public void registerDunningCandidateListener()
	{
		final MockedDunningCandidateListener listener1 = new MockedDunningCandidateListener();
		final MockedDunningCandidateListener listener2 = new MockedDunningCandidateListener();

		Assert.assertEquals("Invalid return type when registering listener1 to event1", true, dispatcher.registerDunningCandidateListener(IInvoiceSourceBL.EVENT_AfterInvoiceWriteOff, listener1));
		Assert.assertEquals("Invalid return type when registering listener1 to event2", true, dispatcher.registerDunningCandidateListener(EVENT_TestEvent, listener1));
		Assert.assertEquals("Invalid return type when registering listener1 to event1 again", false,
				dispatcher.registerDunningCandidateListener(IInvoiceSourceBL.EVENT_AfterInvoiceWriteOff, listener1));

		Assert.assertEquals("Invalid return type when registering listener2 to event1", true, dispatcher.registerDunningCandidateListener(IInvoiceSourceBL.EVENT_AfterInvoiceWriteOff, listener2));
		Assert.assertEquals("Invalid return type when registering listener2 to event2", true, dispatcher.registerDunningCandidateListener(EVENT_TestEvent, listener2));
		Assert.assertEquals("Invalid return type when registering listener2 to event1 again", false,
				dispatcher.registerDunningCandidateListener(IInvoiceSourceBL.EVENT_AfterInvoiceWriteOff, listener2));
	}

	@Test
	public void fireDunningCandidateEvent() // NOPMD by tsa ` 1 |on 3/24/13 5:23 PM, missing assert calls but we are calling them indirectly
	{
		final I_C_Dunning_Candidate candidate = createDunningCandidate();

		final MockedDunningCandidateListener listener1 = new MockedDunningCandidateListener();
		final MockedDunningCandidateListener listener2 = new MockedDunningCandidateListener();
		dispatcher.registerDunningCandidateListener(IInvoiceSourceBL.EVENT_AfterInvoiceWriteOff, listener1);
		dispatcher.registerDunningCandidateListener(IInvoiceSourceBL.EVENT_AfterInvoiceWriteOff, listener2);

		dispatcher.fireDunningCandidateEvent(IInvoiceSourceBL.EVENT_AfterInvoiceWriteOff, candidate);
		listener1.assertTriggered(IInvoiceSourceBL.EVENT_AfterInvoiceWriteOff, candidate);
		listener2.assertTriggered(IInvoiceSourceBL.EVENT_AfterInvoiceWriteOff, candidate);

		dispatcher.fireDunningCandidateEvent(EVENT_TestEvent, candidate);
		listener1.assertNotTriggered(EVENT_TestEvent, candidate);
		listener2.assertNotTriggered(EVENT_TestEvent, candidate);
	}

	@Test
	public void fireDunningCandidateEvent_MultiThread() throws InterruptedException
	{
		final I_C_Dunning_Candidate candidate = createDunningCandidate();

		final MockedDunningCandidateListener listener1 = new MockedDunningCandidateListener();
		listener1.setDelayOnEventMillis(1);
		dispatcher.registerDunningCandidateListener(IInvoiceSourceBL.EVENT_AfterInvoiceWriteOff, listener1);

		final int threadsCount = 10;
		final int runsPerThreadCount = 50;

		final List<Thread> threads = new ArrayList<>();
		for (int threadNo = 1; threadNo <= threadsCount; threadNo++)
		{
			final Runnable runnable = () -> {
				for (int runs = 1; runs <= runsPerThreadCount; runs++)
				{
					dispatcher.fireDunningCandidateEvent(IInvoiceSourceBL.EVENT_AfterInvoiceWriteOff, candidate);
				}
			};
			final Thread thread = new Thread(runnable, "test-fireDunningCandidateEvent_MultiThread-" + threadNo);
			thread.setDaemon(true);
			thread.start();
			threads.add(thread);
		}

		for (Thread thread : threads)
		{
			thread.join();
		}

		final Event event = listener1.getEvent(IInvoiceSourceBL.EVENT_AfterInvoiceWriteOff, candidate);
		Assert.assertNotNull("Event shall be triggered", event);
		Assert.assertEquals("Invalid trigger count for " + event, threadsCount * runsPerThreadCount, event.getTriggeredCount());
	}

	private I_C_Dunning_Candidate createDunningCandidate()
	{
		final I_C_Dunning_Candidate candidate = InterfaceWrapperHelper.create(Env.getCtx(), I_C_Dunning_Candidate.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(candidate);
		return candidate;
	}
}
