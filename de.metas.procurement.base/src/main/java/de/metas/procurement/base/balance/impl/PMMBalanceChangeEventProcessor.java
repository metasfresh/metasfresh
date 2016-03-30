package de.metas.procurement.base.balance.impl;

import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import de.metas.procurement.base.balance.IPMMBalanceChangeEventProcessor;
import de.metas.procurement.base.balance.IPMMBalanceDAO;
import de.metas.procurement.base.balance.PMMBalanceChangeEvent;
import de.metas.procurement.base.model.I_PMM_Balance;

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
	public PMMBalanceChangeEventProcessor()
	{
		super();
	}

	@Override
	public void addEvent(final PMMBalanceChangeEvent event)
	{
		final IPMMBalanceDAO balanceDAO = Services.get(IPMMBalanceDAO.class);

		for (final I_PMM_Balance balanceRecord : balanceDAO.retriveForAllDateSegments(event))
		{
			updateBalanceRecordFromEvent(balanceRecord, event);
			InterfaceWrapperHelper.save(balanceRecord);
		}
	}

	private void updateBalanceRecordFromEvent(final I_PMM_Balance balanceRecord, final PMMBalanceChangeEvent event)
	{
		//
		// Qty Promised
		{
			final BigDecimal qtyPromised = event.getQtyPromised();
			final BigDecimal qtyPromisedTU = event.getQtyPromised_TU();
			balanceRecord.setQtyPromised(balanceRecord.getQtyPromised().add(qtyPromised));
			balanceRecord.setQtyPromised_TU(balanceRecord.getQtyPromised_TU().add(qtyPromisedTU));
		}
		//
		// Qty Ordered
		{
			final BigDecimal qtyOrdered = event.getQtyOrdered();
			final BigDecimal qtyOrderedTU = event.getQtyOrdered_TU();
			balanceRecord.setQtyOrdered(balanceRecord.getQtyOrdered().add(qtyOrdered));
			balanceRecord.setQtyOrdered_TU(balanceRecord.getQtyOrdered_TU().add(qtyOrderedTU));
		}

		//
		// Qty Delivered (i.e. receipt)
		{
			final BigDecimal qtyDelivered = event.getQtyDelivered();
			balanceRecord.setQtyDelivered(balanceRecord.getQtyDelivered().add(qtyDelivered));
		}
	}
}
