package de.metas.inout.event;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Collection;
import java.util.Collections;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_InOut;
import org.compiere.util.Env;

import de.metas.document.engine.IDocActionBL;
import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.QueueableForwardingEventBus;
import de.metas.event.Topic;
import de.metas.event.Type;

/**
 * {@link IEventBus} wrapper implementation tailored for sending events about generated or reversed shipments/receipts.
 *
 * @author tsa
 *
 */
public final class InOutProcessedEventBus extends QueueableForwardingEventBus
{
	public static final InOutProcessedEventBus newInstance()
	{
		final IEventBus eventBus = Services.get(IEventBusFactory.class).getEventBus(EVENTBUS_TOPIC);
		return new InOutProcessedEventBus(eventBus);
	}

	/** Topic used to send notifications about shipments/receipts that were generated/reversed asynchronously */
	public static final Topic EVENTBUS_TOPIC = Topic.builder()
			.setName("de.metas.inout.InOut.ProcessedEvents")
			.setType(Type.REMOTE)
			.build();
	
	// services
	private final transient IDocActionBL docActionBL = Services.get(IDocActionBL.class);

	private static final String MSG_Event_ShipmentGenerated = "Event_ShipmentGenerated";
	private static final String MSG_Event_ReceiptGenerated = "Event_ReceiptGenerated";
	//
	private static final String MSG_Event_ShipmentReversed = "Event_ShipmentReversed";
	private static final String MSG_Event_ReceiptReversed = "Event_ReceiptReversed";

	private InOutProcessedEventBus(final IEventBus delegate)
	{
		super(delegate);
	}

	@Override
	public InOutProcessedEventBus queueEvents()
	{
		super.queueEvents();
		return this;
	}

	@Override
	public InOutProcessedEventBus queueEventsUntilTrxCommit(final String trxName)
	{
		super.queueEventsUntilTrxCommit(trxName);
		return this;
	}
	
	@Override
	public InOutProcessedEventBus queueEventsUntilCurrentTrxCommit()
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
	public InOutProcessedEventBus notify(final Collection<? extends I_M_InOut> inouts)
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
	public final InOutProcessedEventBus notify(final I_M_InOut inout)
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
				.build();
		return event;
	}
	
	private final String getNotificationAD_Message(final I_M_InOut inout)
	{
		if(docActionBL.isStatusReversedOrVoided(inout))
		{
			return inout.isSOTrx() ? MSG_Event_ShipmentReversed : MSG_Event_ReceiptReversed;
		}
		else
		{
			return inout.isSOTrx() ? MSG_Event_ShipmentGenerated : MSG_Event_ReceiptGenerated;
		}
	}
	
	private final int getNotificationRecipientUserId(final I_M_InOut inout)
	{
		//
		// In case of reversal i think we shall notify the current user too
		if(docActionBL.isStatusReversedOrVoided(inout))
		{
			final int currentUserId = Env.getAD_User_ID(Env.getCtx()); // current/triggering user
			if(currentUserId > 0)
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
