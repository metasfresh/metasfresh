package de.metas.adempiere.form;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import org.adempiere.util.collections.Predicate;
import org.compiere.util.TimeUtil;

/**
 * Collect DeliveryDates for {@link TableRow}s
 *
 * @author tsa
 *
 */
/* package */class TableRowDeliveryDatesCollector implements Predicate<TableRow>
{
	private Set<Date> deliveryDates = new TreeSet<Date>();

	/**
	 * Returns always <code>false</code>, but if the given <code>tableRow</code> has a <code>DeliveryDate</code>,
	 * then it adds that date to the result that is returned by {@link #getDeliveryDates()}.
	 *
	 * @return always <code>false</code>.
	 */
	@Override
	public boolean evaluate(final TableRow tableRow)
	{
		Date deliveryDate = tableRow.getDeliveryDate();
		if (deliveryDate == null)
		{
			// shall not happen, but skip it
			return false;
		}

		// make sure is truncated to date
		deliveryDate = TimeUtil.getDay(deliveryDate);

		deliveryDates.add(deliveryDate);

		return false;
	}

	/**
	 *
	 * @return collected DeliveryDates
	 */
	public Set<Date> getDeliveryDates()
	{
		return deliveryDates;
	}
}
