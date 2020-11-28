package de.metas.procurement.base.balance.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.procurement.base.balance.IPMMBalanceChangeEventProcessor;
import de.metas.procurement.base.balance.IPMMBalanceDAO;
import de.metas.procurement.base.balance.PMMBalanceChangeEvent;
import de.metas.procurement.base.balance.PMMBalanceSegment;
import de.metas.procurement.base.model.I_PMM_Balance;
import de.metas.util.Services;

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

public class PMMBalanceChangeEventProcessor implements IPMMBalanceChangeEventProcessor
{
	private static final Logger logger = LogManager.getLogger(PMMBalanceChangeEventProcessor.class);

	public PMMBalanceChangeEventProcessor()
	{
		super();
	}

	@Override
	public void addEvent(final PMMBalanceChangeEvent event)
	{
		logger.debug("Got event: {}", event);
		final IPMMBalanceDAO balanceDAO = Services.get(IPMMBalanceDAO.class);

		final PMMBalanceSegment segment = PMMBalanceSegment.of(event);
		final Date date = event.getDate();

		logger.trace("Searching for balance records using segment={}, date={}", segment, date);
		final List<I_PMM_Balance> balanceRecords = balanceDAO.retriveForAllDateSegments(segment, date);

		for (final I_PMM_Balance balanceRecord : balanceRecords)
		{
			updateBalanceRecordFromEvent(balanceRecord, event);
			InterfaceWrapperHelper.save(balanceRecord);
			logger.trace("Updated {}", balanceRecord);
		}
	}

	private void updateBalanceRecordFromEvent(final I_PMM_Balance balanceRecord, final PMMBalanceChangeEvent event)
	{
		logger.trace("Updating {} from {}", balanceRecord, event);

		boolean updated = false;

		//
		// Qty Promised
		{
			final BigDecimal event_QtyPromised = event.getQtyPromised();
			if (event_QtyPromised.signum() != 0)
			{
				final BigDecimal balance_QtyPromised_Old = balanceRecord.getQtyPromised();
				final BigDecimal balance_QtyPromised_New = balance_QtyPromised_Old.add(event_QtyPromised);
				balanceRecord.setQtyPromised(balance_QtyPromised_New);
				updated = true;

				logger.trace("Updated QtyPromised={} -> {} (+ {})", balance_QtyPromised_Old, balance_QtyPromised_New, event_QtyPromised);
			}
		}

		//
		// Qty Promised TU
		{
			final BigDecimal event_QtyPromisedTU = event.getQtyPromised_TU();
			if (event_QtyPromisedTU.signum() != 0)
			{
				final BigDecimal balance_QtyPromisedTU_Old = balanceRecord.getQtyPromised_TU();
				final BigDecimal balance_QtyPromisedTU_New = balance_QtyPromisedTU_Old.add(event_QtyPromisedTU);
				balanceRecord.setQtyPromised_TU(balance_QtyPromisedTU_New);
				updated = true;

				logger.trace("Updated QtyPromised_TU={} -> {} (+ {})", balance_QtyPromisedTU_Old, balance_QtyPromisedTU_New, event_QtyPromisedTU);
			}
		}

		//
		// Qty Ordered
		{
			final BigDecimal event_QtyOrdered = event.getQtyOrdered();
			if (event_QtyOrdered.signum() != 0)
			{
				final BigDecimal balance_QtyOrdered_Old = balanceRecord.getQtyOrdered();
				final BigDecimal balance_QtyOrdered_New = balance_QtyOrdered_Old.add(event_QtyOrdered);
				balanceRecord.setQtyOrdered(balance_QtyOrdered_New);
				updated = true;

				logger.trace("Updated QtyOrdered={} -> {} (+ {})", balance_QtyOrdered_Old, balance_QtyOrdered_New, event_QtyOrdered);
			}
		}

		//
		// Qty Ordered TU
		{
			final BigDecimal event_QtyOrderedTU = event.getQtyOrdered_TU();
			if (event_QtyOrderedTU.signum() != 0)
			{
				final BigDecimal balance_QtyOrderedTU_Old = balanceRecord.getQtyOrdered_TU();
				final BigDecimal balance_QtyOrderedTU_New = balance_QtyOrderedTU_Old.add(event_QtyOrderedTU);
				balanceRecord.setQtyOrdered_TU(balance_QtyOrderedTU_New);
				updated = true;

				logger.trace("Updated QtyOrdered_TU={} -> {} (+ {})", balance_QtyOrderedTU_Old, balance_QtyOrderedTU_New, event_QtyOrderedTU);
			}
		}

		//
		// Qty Delivered (i.e. receipt)
		{
			final BigDecimal event_QtyDelivered = event.getQtyDelivered();
			{
				final BigDecimal balance_QtyDelivered_Old = balanceRecord.getQtyDelivered();
				final BigDecimal balance_QtyDelivered_New = balance_QtyDelivered_Old.add(event_QtyDelivered);
				balanceRecord.setQtyDelivered(balance_QtyDelivered_New);
				updated = true;

				logger.trace("Updated QtyDelivered={} -> {} (+ {})", balance_QtyDelivered_Old, balance_QtyDelivered_New, event_QtyDelivered);
			}
		}

		// Warn if nothing was updated because we assume at this point the event was triggered only for a real change
		if (!updated)
		{
			logger.warn("Record {} was not updated from {}. This might be a possible development issue.", balanceRecord, event);
		}
	}
}
