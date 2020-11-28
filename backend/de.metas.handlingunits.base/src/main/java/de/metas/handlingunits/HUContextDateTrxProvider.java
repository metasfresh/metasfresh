package de.metas.handlingunits;

import java.time.ZonedDateTime;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Date;

import org.compiere.util.TimeUtil;

import de.metas.common.util.time.SystemTime;
import lombok.NonNull;

/**
 * Provider used to get the initial DateTrx when an {@link IHUContext} is created.
 * 
 * @author tsa
 *
 */
public class HUContextDateTrxProvider
{
	public interface ITemporaryDateTrx extends AutoCloseable
	{
		@Override void close();
	}

	/** Thread Local used to hold the DateTrx overrides */
	private final ThreadLocal<ZonedDateTime> dateRef = new ThreadLocal<>();

	/**
	 * Gets DateTrx.
	 * 
	 * By default, it will return {@link SystemTime#asDate()}.
	 * 
	 * @return date trx;
	 */
	public ZonedDateTime getDateTrx()
	{
		final ZonedDateTime date = dateRef.get();
		if (date != null)
		{
			return date;
		}
		return de.metas.common.util.time.SystemTime.asZonedDateTime();
	}

	/**
	 * Temporary override current date provider by given date.
	 * 
	 * @param date
	 * @return
	 */
	public ITemporaryDateTrx temporarySet(@NonNull final Date date)
	{
		return temporarySet(TimeUtil.asZonedDateTime(date));
	}

	public ITemporaryDateTrx temporarySet(@NonNull final ZonedDateTime date)
	{
		final ZonedDateTime dateOld = dateRef.get();

		dateRef.set(date);

		return () -> dateRef.set(dateOld);

	}
}
