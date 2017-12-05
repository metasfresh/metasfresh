package org.eevolution.event;

import static de.metas.document.engine.IDocument.ACTION_Complete;
import static de.metas.document.engine.IDocument.STATUS_Completed;

import java.util.Date;

import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.Services;
import org.adempiere.util.lang.Mutable;
import org.eevolution.model.I_PP_Order;
import org.eevolution.mrp.spi.impl.pporder.PPOrderProducer;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;

import de.metas.document.engine.IDocumentBL;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderRequestedEvent;
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
public class PPOrderRequestedEventHandler
{
	public static final ModelDynAttributeAccessor<I_PP_Order, Integer> ATTR_PPORDER_REQUESTED_EVENT_GROUP_ID = //
			new ModelDynAttributeAccessor<>(I_PP_Order.class.getName(), "PPOrderRequestedEvent_GroupId", Integer.class);

	private final PPOrderProducer ppOrderProducer;

	public PPOrderRequestedEventHandler(@NonNull final PPOrderProducer ppOrderProducer)
	{
		this.ppOrderProducer = ppOrderProducer;
	}

	public I_PP_Order createProductionOrderInTrx(@NonNull final PPOrderRequestedEvent ppOrderEquestedEvent)
	{
		final Mutable<I_PP_Order> result = new Mutable<>();

		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager.run(trxName -> result.setValue(createProductionOrder(ppOrderEquestedEvent)));

		return result.getValue();
	}

	/**
	 * Creates a production order. Note that is does not fire an event, because production orders can be created and changed for many resons,<br>
	 * and therefore we leave the event-firing to a model interceptor.
	 *
	 * @param ppOrder
	 * @param dateOrdered
	 * @return
	 */
	@VisibleForTesting
	I_PP_Order createProductionOrder(@NonNull final PPOrderRequestedEvent ppOrderRequestedEvent)
	{
		final PPOrder ppOrder = ppOrderRequestedEvent.getPpOrder();
		final Date dateOrdered = Date.from(ppOrderRequestedEvent.getEventDescriptor().getWhen());

		final I_PP_Order ppOrderRecord = ppOrderProducer.createPPOrder(ppOrder, dateOrdered);
		ATTR_PPORDER_REQUESTED_EVENT_GROUP_ID.setValue(ppOrderRecord, ppOrderRequestedEvent.getGroupId());

		if (ppOrderRecord.getPP_Product_Planning().isDocComplete())
		{
			Services.get(IDocumentBL.class).processEx(ppOrderRecord, ACTION_Complete, STATUS_Completed);
		}
		return ppOrderRecord;
	}
}
