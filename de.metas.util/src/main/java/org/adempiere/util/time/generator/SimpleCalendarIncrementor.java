package org.adempiere.util.time.generator;

/*
 * #%L
 * de.metas.util
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


import java.util.Calendar;

import org.adempiere.util.Check;

import lombok.Value;

/**
 * Increment by given a given number of Day/Month/Year/etc.
 * 
 * NOTE: this class is immutable and can be used to define pre-configured constants.
 * 
 * @author tsa
 *
 */
@Value
public final class SimpleCalendarIncrementor implements ICalendarIncrementor
{
	private final int type;
	private final int amount;

	/**
	 * 
	 * @param type increment type (e.g. {@link Calendar#DAY_OF_MONTH}, {@link Calendar#MONTH} etc).
	 * @param amount increment amount
	 */
	public SimpleCalendarIncrementor(final int type, final int amount)
	{
		super();

		// NOTE: we shall validate the "type" too, but i don't know how....
		this.type = type;

		Check.assume(amount != 0, "amount != 0");
		this.amount = amount;
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + " [type=" + type + ", amount=" + amount + "]";
	}

	@Override
	public void increment(Calendar calendar)
	{
		calendar.add(type, amount);
	}

	@Override
	public void adjustToClosest(Calendar calendar)
	{
	}
}
