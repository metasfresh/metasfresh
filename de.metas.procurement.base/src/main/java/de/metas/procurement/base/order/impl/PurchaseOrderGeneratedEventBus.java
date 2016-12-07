package de.metas.procurement.base.order.impl;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.slf4j.Logger;

import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.QueueableForwardingEventBus;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.procurement.base.ProcurementConstants;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class PurchaseOrderGeneratedEventBus extends QueueableForwardingEventBus
{
	public static final PurchaseOrderGeneratedEventBus newInstance()
	{
		final IEventBus eventBus = Services.get(IEventBusFactory.class).getEventBus(EVENTBUS_TOPIC);
		return new PurchaseOrderGeneratedEventBus(eventBus);
	}

	public static final Topic EVENTBUS_TOPIC = Topic.builder()
			.setName("de.metas.procurement.base.order.async.PurchaseOrderGeneratedEventBus")
			.setType(Type.REMOTE)
			.build();

	private static final transient Logger logger = ProcurementConstants.getLogger(PurchaseOrderGeneratedEventBus.class);

	private static final String MSG_Event_Generated = "Event_ProcurementPurchaseOrderGenerated";

	private PurchaseOrderGeneratedEventBus(IEventBus delegate)
	{
		super(delegate);
	}

	@Override
	public PurchaseOrderGeneratedEventBus queueEvents()
	{
		super.queueEvents();
		return this;
	}

	@Override
	public PurchaseOrderGeneratedEventBus queueEventsUntilTrxCommit(final String trxName)
	{
		super.queueEventsUntilTrxCommit(trxName);
		return this;
	}

	/**
	 * Post events about given document.
	 *
	 * @param document
	 * @param recipientUserId
	 */
	public PurchaseOrderGeneratedEventBus notify(final I_C_Order document, final int recipientUserId)
	{
		if (document == null)
		{
			return this;
		}

		try
		{
			final Event event = createEvent(document, recipientUserId);
			postEvent(event);
		}
		catch (Exception e)
		{
			logger.warn("Failed creating event for " + document + ". Ignored.", e);
		}

		return this;
	}

	private final Event createEvent(final I_C_Order document, final int recipientUserId)
	{
		Check.assumeNotNull(document, "document not null");

		//
		// Get the recipient
		final int recipientUserIdToUse;
		if (recipientUserId > 0)
		{
			recipientUserIdToUse = recipientUserId;
		}
		else
		{
			recipientUserIdToUse = extractRecipientUser(document);
			if (recipientUserIdToUse < 0)
			{
				throw new AdempiereException("No recipient found for " + document);
			}
		}

		//
		// Extract event message parameters
		final Object[] adMessageParams = extractEventMessageParams(document);

		//
		// Create an return the event
		final Event event = Event.builder()
				.setDetailADMessage(MSG_Event_Generated, adMessageParams)
				.addRecipient_User_ID(recipientUserIdToUse)
				.setRecord(TableRecordReference.of(document))
				.build();
		return event;
	}

	private Object[] extractEventMessageParams(final I_C_Order document)
	{
		final I_C_BPartner bpartner = document.getC_BPartner();
		final String bpValue = bpartner.getValue();
		final String bpName = bpartner.getName();
		return new Object[] { TableRecordReference.of(document), bpValue, bpName };
	}

	private int extractRecipientUser(final I_C_Order document)
	{
		final Integer createdBy = InterfaceWrapperHelper.getValueOrNull(document, "CreatedBy");
		return createdBy == null ? -1 : createdBy;
	}

}
