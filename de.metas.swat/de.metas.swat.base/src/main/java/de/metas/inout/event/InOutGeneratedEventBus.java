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

import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.QueueableForwardingEventBus;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.inout.model.I_M_InOut;

/**
 * {@link IEventBus} wrapper implementation tailored for sending events about generated shipments/receipts.
 *
 * @author tsa
 *
 */
public final class InOutGeneratedEventBus extends QueueableForwardingEventBus
{
	public static final InOutGeneratedEventBus newInstance()
	{
		final IEventBus eventBus = Services.get(IEventBusFactory.class).getEventBus(EVENTBUS_TOPIC);
		return new InOutGeneratedEventBus(eventBus);
	}

	/** Topic used to send notifications about shipments/receipts that were generated asynchronously */
	public static final Topic EVENTBUS_TOPIC = Topic.builder()
			.setName("de.metas.inout.InOutGenerated")
			.setType(Type.REMOTE)
			.build();

	private static final String MSG_Event_ShipmentGenerated = "Event_ShipmentGenerated";
	private static final String MSG_Event_ReceiptGenerated = "Event_ReceiptGenerated";

	private InOutGeneratedEventBus(final IEventBus delegate)
	{
		super(delegate);
	}

	@Override
	public InOutGeneratedEventBus queueEvents()
	{
		super.queueEvents();
		return this;
	}

	@Override
	public InOutGeneratedEventBus queueEventsUntilTrxCommit(final String trxName)
	{
		super.queueEventsUntilTrxCommit(trxName);
		return this;
	}

	/**
	 * Post events about given inouts that were generated.
	 *
	 * @param inouts
	 */
	public InOutGeneratedEventBus notify(final Collection<? extends I_M_InOut> inouts)
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

	public final InOutGeneratedEventBus notify(final I_M_InOut inout)
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

		final int recipientUserId = inout.getCreatedBy();

		final String adMessage = inout.isSOTrx() ? MSG_Event_ShipmentGenerated : MSG_Event_ReceiptGenerated;

		final Event event = Event.builder()
				.setDetailADMessage(adMessage, TableRecordReference.of(inout), bpValue, bpName)
				.addRecipient_User_ID(recipientUserId)
				.setRecord(TableRecordReference.of(inout))
				.build();
		return event;
	}
}
