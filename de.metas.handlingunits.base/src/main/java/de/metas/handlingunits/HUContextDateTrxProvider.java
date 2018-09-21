package de.metas.handlingunits;

/*
 * #%L
 * de.metas.handlingunits.base
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


import java.util.Date;

import de.metas.util.Check;
import de.metas.util.time.SystemTime;

/**
 * Provider used to get the initial DateTrx when an {@link IHUContext} is created.
 * 
 * @author tsa
 *
 */
public class HUContextDateTrxProvider
{
	public static interface ITemporaryDateTrx extends AutoCloseable
	{
		@Override
		public void close();
	}

	/** Thread Local used to hold the DateTrx overrides */
	private final ThreadLocal<Date> dateRef = new ThreadLocal<>();

	/**
	 * Gets DateTrx.
	 * 
	 * By default, it will return {@link SystemTime#asDate()}.
	 * 
	 * @return date trx;
	 */
	public Date getDateTrx()
	{
		final Date date = dateRef.get();
		if (date != null)
		{
			return date;
		}
		return SystemTime.asDate();
	}

	/**
	 * Temporary override current date provider by given date.
	 * 
	 * @param date
	 * @return
	 */
	public ITemporaryDateTrx temporarySet(final Date date)
	{
		Check.assumeNotNull(date, "date not null");

		final Date dateOld = dateRef.get() == null ? null : (Date)dateRef.get().clone();
		dateRef.set((Date)date.clone());

		return new ITemporaryDateTrx()
		{

			@Override
			public void close()
			{
				dateRef.set(dateOld);
			}
		};

	}
}
