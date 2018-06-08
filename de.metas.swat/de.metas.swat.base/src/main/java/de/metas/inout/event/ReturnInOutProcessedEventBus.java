package de.metas.inout.event;

import java.util.Collection;
import java.util.Collections;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_InOut;
import org.compiere.util.Env;

import de.metas.document.engine.IDocumentBL;
import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.QueueableForwardingEventBus;
import de.metas.event.Topic;
import de.metas.event.Type;

/*
 * #%L
 * de.metas.swat.base
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

public class ReturnInOutProcessedEventBus extends QueueableForwardingEventBus
{

	/**
	 * M_InOut PO
	 */
	private static final int WINDOW_RETURN_TO_VENDOR = 53098; // FIXME: HARDCODED

	private static final int WINDOW_RETURN_FROM_CUSTOMER = 53097; // FIXME: HARDCODED

	public static final ReturnInOutProcessedEventBus newInstance()
	{
		final IEventBus eventBus = Services.get(IEventBusFactory.class).getEventBus(EVENTBUS_TOPIC);
		return new ReturnInOutProcessedEventBus(eventBus);
	}

	/** Topic used to send notifications about shipments/receipts that were generated/reversed asynchronously */
	public static final Topic EVENTBUS_TOPIC = Topic.builder()
			.name("de.metas.inout.ReturnInOut.ProcessedEvents")
			.type(Type.REMOTE)
			.build();

	// services
	private final transient IDocumentBL docActionBL = Services.get(IDocumentBL.class);

	private static final String MSG_Event_RETURN_FROM_CUSTOMER_Generated = "Event_CustomerReturn_Generated";
	private static final String MSG_Event_RETURN_TO_VENDOR_Generated = "Event_ReturnToVendor_Generated";

	private ReturnInOutProcessedEventBus(final IEventBus delegate)
	{
		super(delegate);
	}

	@Override
	public ReturnInOutProcessedEventBus queueEvents()
	{
		super.queueEvents();
		return this;
	}

	@Override
	public ReturnInOutProcessedEventBus queueEventsUntilTrxCommit(final String trxName)
	{
		super.queueEventsUntilTrxCommit(trxName);
		return this;
	}

	@Override
	public ReturnInOutProcessedEventBus queueEventsUntilCurrentTrxCommit()
	{
		super.queueEventsUntilCurrentTrxCommit();
		return this;
	}

	/**
	 * Post events about given shipment/receipts that were processed.
	 *
	 * @param inouts
	 * @see #notify(I_M_InOut)
	 */
	public ReturnInOutProcessedEventBus notify(final Collection<? extends I_M_InOut> inouts)
	{
		if (inouts == null || inouts.isEmpty())
		{
			return this;
		}

		for (final I_M_InOut inout : inouts)
		{
			final Event event = createInOutGeneratedEvent(inout);
			postEvent(event);
		}

		return this;
	}

	/**
	 * Post events about given shipment/receipts that were processed, i.e.
	 * <ul>
	 * <li>if inout's DocStatus is Completed, a "generated" notification will be sent
	 * <li>if inout's DocStatus is Voided or Reversed, a "reversed" notification will be sent
	 * </ul>
	 *
	 * @param inout
	 * @return
	 */
	public final ReturnInOutProcessedEventBus notify(final I_M_InOut inout)
	{
		Check.assumeNotNull(inout, "inout not null");
		notify(Collections.singleton(inout));
		return this;
	}

	private final Event createInOutGeneratedEvent(final I_M_InOut inout)
	{
		Check.assumeNotNull(inout, "inout not null");

		final I_C_BPartner bpartner = inout.getC_BPartner();
		final String bpValue = bpartner.getValue();
		final String bpName = bpartner.getName();

		final String adMessage = getNotificationAD_Message(inout);
		final int recipientUserId = getNotificationRecipientUserId(inout);

		final Event event = Event.builder()
				.setDetailADMessage(adMessage, TableRecordReference.of(inout), bpValue, bpName)
				.addRecipient_User_ID(recipientUserId)
				.setRecord(TableRecordReference.of(inout))
				.setSuggestedWindowId(getWindowID(inout))
				.build();
		return event;
	}

	private int getWindowID(I_M_InOut inout)
	{
		return inout.isSOTrx() ? WINDOW_RETURN_FROM_CUSTOMER : WINDOW_RETURN_TO_VENDOR;
	}

	private final String getNotificationAD_Message(final I_M_InOut inout)
	{

		return inout.isSOTrx() ? MSG_Event_RETURN_FROM_CUSTOMER_Generated : MSG_Event_RETURN_TO_VENDOR_Generated;

	}

	private final int getNotificationRecipientUserId(final I_M_InOut inout)
	{
		//
		// In case of reversal i think we shall notify the current user too
		if (docActionBL.isDocumentReversedOrVoided(inout))
		{
			final int currentUserId = Env.getAD_User_ID(Env.getCtx()); // current/triggering user
			if (currentUserId > 0)
			{
				return currentUserId;
			}

			return inout.getUpdatedBy(); // last updated
		}
		//
		// Fallback: notify only the creator
		else
		{
			return inout.getCreatedBy();
		}
	}
}
