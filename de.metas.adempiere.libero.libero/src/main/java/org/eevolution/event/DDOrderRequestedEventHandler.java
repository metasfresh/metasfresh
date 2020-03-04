package org.eevolution.event;

import static de.metas.document.engine.IDocument.ACTION_Complete;
import static de.metas.document.engine.IDocument.STATUS_Completed;

import java.util.Collection;
import java.util.Date;

import org.compiere.util.TimeUtil;
import org.eevolution.model.I_DD_Order;
import org.eevolution.mrp.spi.impl.ddorder.DDOrderProducer;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

import ch.qos.logback.classic.Level;
import de.metas.Profiles;
import de.metas.document.engine.IDocumentBL;
import de.metas.logging.LogManager;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrderRequestedEvent;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

@Service
@Profile(Profiles.PROFILE_App) // only one handler should bother itself with these events
public class DDOrderRequestedEventHandler implements MaterialEventHandler<DDOrderRequestedEvent>
{
	private static final Logger logger = LogManager.getLogger(DDOrderRequestedEventHandler.class);

	private final DDOrderProducer ddOrderProducer;

	public DDOrderRequestedEventHandler(@NonNull final DDOrderProducer ddOrderProducer)
	{
		this.ddOrderProducer = ddOrderProducer;
	}

	@Override
	public Collection<Class<? extends DDOrderRequestedEvent>> getHandeledEventType()
	{
		return ImmutableList.of(DDOrderRequestedEvent.class);
	}

	@Override
	public void validateEvent(@NonNull final DDOrderRequestedEvent event)
	{
		// nothing to do: the event was already validated on construction
	}

	@Override
	public void handleEvent(@NonNull final DDOrderRequestedEvent distributionOrderEvent)
	{
		createDDOrder(distributionOrderEvent);
	}

	@VisibleForTesting
	I_DD_Order createDDOrder(@NonNull final DDOrderRequestedEvent ddOrderRequestedEvent)
	{
		final DDOrder ddOrder = ddOrderRequestedEvent.getDdOrder();
		final Date dateOrdered = TimeUtil.asDate(ddOrderRequestedEvent.getDateOrdered());

		final I_DD_Order ddOrderRecord = ddOrderProducer.createDDOrder(ddOrder, dateOrdered);

		Loggables.withLogger(logger, Level.DEBUG).addLog(
				"Created ddOrder; DD_Order_ID={}; DocumentNo={}",
				ddOrderRecord.getDD_Order_ID(), ddOrderRecord.getDocumentNo());

		if (ddOrderRecord.getPP_Product_Planning().isDocComplete())
		{
			Services.get(IDocumentBL.class).processEx(ddOrderRecord, ACTION_Complete, STATUS_Completed);
			Loggables.withLogger(logger, Level.DEBUG).addLog(
					"Completed ddOrder; DD_Order_ID={}; DocumentNo={}",
					ddOrderRecord.getDD_Order_ID(), ddOrderRecord.getDocumentNo());
		}
		return ddOrderRecord;
	}
}
