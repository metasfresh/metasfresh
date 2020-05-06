package de.metas.procurement.base.balance;

import java.math.BigDecimal;
import java.util.Date;

import org.adempiere.test.AdempiereTestWatcher;
import org.junit.Before;
import org.junit.Rule;

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

public class PMMBalanceChangeEventProcessorTest extends AbstractBalanceScenariosTest
{
	@Rule
	public AdempiereTestWatcher testWatcher = new AdempiereTestWatcher();

	@Override
	@Before
	public void init()
	{
		super.init();
	}

	@Override
	protected void addQtyPromised(final PMMBalanceSegment segment, final Date date, final BigDecimal qtyPromised, final BigDecimal qtyPromisedTU)
	{
		processEvent(eventBuilder(segment)
				.setDate(date)
				.setQtyPromised(qtyPromised, qtyPromisedTU)
				.build());
	}

	@Override
	protected void addQtyOrdered(final PMMBalanceSegment segment, final Date date, final BigDecimal qtyOrdered, final BigDecimal qtyOrderedTU)
	{
		processEvent(eventBuilder(segment)
				.setDate(date)
				.setQtyOrdered(qtyOrdered, qtyOrderedTU)
				.build());
	}
}
