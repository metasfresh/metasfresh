package org.eevolution.event;

import static de.metas.document.engine.IDocument.ACTION_Complete;
import static de.metas.document.engine.IDocument.STATUS_Completed;

import java.util.Date;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.Services;
import org.adempiere.util.lang.Mutable;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_PP_Order;
import org.eevolution.mrp.spi.impl.ddorder.DDOrderProducer;
import org.eevolution.mrp.spi.impl.pporder.PPOrderProducer;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;

import de.metas.document.engine.IDocumentBL;
import de.metas.logging.LogManager;
import de.metas.material.event.DDOrderRequestedEvent;
import de.metas.material.event.MaterialDemandEvent;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.MaterialEventListener;
import de.metas.material.event.PPOrderRequestedEvent;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.pporder.PPOrder;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-mrp
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
/**
 * This listener is dedicated to handle {@link MaterialDemandEvent}s. It ignores and other {@link MaterialEvent}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Service
public class MaterialDocumentListener implements MaterialEventListener
{
	private static final transient Logger logger = LogManager.getLogger(MaterialDocumentListener.class);

	private final PPOrderProducer ppOrderProducer;

	private final DDOrderProducer ddOrderProducer;

	public MaterialDocumentListener(
			@NonNull final PPOrderProducer ppOrderProducer,
			@NonNull final DDOrderProducer ddOrderProducer)
	{
		this.ppOrderProducer = ppOrderProducer;
		this.ddOrderProducer = ddOrderProducer;
	}

	@Override
	public void onEvent(final MaterialEvent event)
	{
		if (event instanceof PPOrderRequestedEvent)
		{
			logger.info("Received event {}", event);

			final PPOrderRequestedEvent productionOrderEvent = (PPOrderRequestedEvent)event;
			createProductionOrderInTrx(
					productionOrderEvent.getPpOrder(),
					Date.from(productionOrderEvent.getEventDescr().getWhen()));
		}
		else if (event instanceof DDOrderRequestedEvent)
		{
			logger.info("Received event {}", event);

			final DDOrderRequestedEvent distributionOrderEvent = (DDOrderRequestedEvent)event;
			createDistributionOrderInTrx(
					distributionOrderEvent.getDdOrder(),
					Date.from(distributionOrderEvent.getEventDescr().getWhen()));
		}

	}

	private I_PP_Order createProductionOrderInTrx(
			@NonNull final PPOrder ppOrder,
			@NonNull final Date dateOrdered)
	{
		Mutable<I_PP_Order> result = new Mutable<>();

		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager
				.run(trxName -> {
					result.setValue(createProductionOrder(ppOrder, dateOrdered));
				});
		return result.getValue();
	}

	/**
	 * Creates a production order. Note that is does not fire an event, because production orders can be created and changed for mand reson, and therefore we leave the event-firing to a model interceptor.
	 *
	 * @param ppOrder
	 * @param dateOrdered
	 * @return
	 */
	@VisibleForTesting
	I_PP_Order createProductionOrder(
			@NonNull final PPOrder ppOrder,
			@NonNull final Date dateOrdered)
	{
		final I_PP_Order ppOrderRecord = ppOrderProducer.createPPOrder(ppOrder, dateOrdered);
		if (ppOrderRecord.getPP_Product_Planning().isDocComplete())
		{
			Services.get(IDocumentBL.class).processEx(ppOrderRecord, ACTION_Complete, STATUS_Completed);
		}
		return ppOrderRecord;
	}

	private I_DD_Order createDistributionOrderInTrx(
			@NonNull final DDOrder ddOrder,
			@NonNull final Date dateOrdered)
	{
		final Mutable<I_DD_Order> result = new Mutable<>();

		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager
				.run(trxName -> {
					result.setValue(createDistributionOrder(ddOrder, dateOrdered));
				});
		return result.getValue();
	}

	@VisibleForTesting
	I_DD_Order createDistributionOrder(
			@NonNull final DDOrder ddOrder,
			@NonNull final Date dateOrdered)
	{
		final I_DD_Order ddOrderRecord = ddOrderProducer.createDDOrder(ddOrder, dateOrdered);
		if (ddOrderRecord.getPP_Product_Planning().isDocComplete())
		{
			Services.get(IDocumentBL.class).processEx(ddOrderRecord, ACTION_Complete, STATUS_Completed);
		}
		return ddOrderRecord;
	}
}
