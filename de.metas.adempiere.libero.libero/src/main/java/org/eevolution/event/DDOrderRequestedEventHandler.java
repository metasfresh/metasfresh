package org.eevolution.event;

import static de.metas.document.engine.IDocument.ACTION_Complete;
import static de.metas.document.engine.IDocument.STATUS_Completed;

import java.util.Date;

import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.Services;
import org.adempiere.util.lang.Mutable;
import org.eevolution.model.I_DD_Order;
import org.eevolution.mrp.spi.impl.ddorder.DDOrderProducer;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;

import de.metas.document.engine.IDocumentBL;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrderRequestedEvent;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Service
public class DDOrderRequestedEventHandler
{
	public static final ModelDynAttributeAccessor<I_DD_Order, Integer> ATTR_DDORDER_REQUESTED_EVENT_GROUP_ID = //
			new ModelDynAttributeAccessor<>(I_DD_Order.class.getName(), "DDOrderRequestedEvent_GroupId", Integer.class);

	private final DDOrderProducer ddOrderProducer;

	public DDOrderRequestedEventHandler(@NonNull final DDOrderProducer ddOrderProducer)
	{
		this.ddOrderProducer = ddOrderProducer;
	}

	public  I_DD_Order createDDOrderInTrx(@NonNull final DDOrderRequestedEvent distributionOrderEvent)
	{
		final Mutable<I_DD_Order> result = new Mutable<>();

		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager.run(trxName -> result.setValue(createDDOrder(distributionOrderEvent)));

		return result.getValue();
	}

	@VisibleForTesting
	I_DD_Order createDDOrder(@NonNull final DDOrderRequestedEvent ddOrderRequestedEvent)
	{
		final DDOrder ddOrder= ddOrderRequestedEvent.getDdOrder();
		final Date dateOrdered= Date.from(ddOrderRequestedEvent.getEventDescriptor().getWhen());

		final I_DD_Order ddOrderRecord = ddOrderProducer.createDDOrder(ddOrder, dateOrdered);
		ATTR_DDORDER_REQUESTED_EVENT_GROUP_ID.setValue(ddOrderRecord, ddOrderRequestedEvent.getGroupId());

		if (ddOrderRecord.getPP_Product_Planning().isDocComplete())
		{
			Services.get(IDocumentBL.class).processEx(ddOrderRecord, ACTION_Complete, STATUS_Completed);
		}
		return ddOrderRecord;
	}
}
