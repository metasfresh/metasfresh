package de.metas.fresh.callout;

/*
 * #%L
 * de.metas.fresh.base
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

import java.sql.Timestamp;
import java.util.Calendar;

import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.ui.spi.TabCalloutAdapter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * This callout is supposed to be usable for <b>any</b> tab with a DatePromised field.
 * 
 */
// task 06706
public class DatePromised extends TabCalloutAdapter
{
	private static final Logger logger = LogManager.getLogger(DatePromised.class);

	@Override
	public void onNew(final ICalloutRecord calloutRecord)
	{
		Check.assumeNotNull(calloutRecord, "Param 'calloutRecord' is not null");

		final DatePromisedAware datePromisedAware = calloutRecord.getModel(DatePromisedAware.class);

		final Timestamp datePromised = datePromisedAware.getDatePromised();
		if (datePromised == null)
		{
			// issuing a warning
			final String msg = "Callout record " + calloutRecord + " has no value for column name " + DatePromisedAware.COLUMNNAME_DatePromised + ". "
					+ "Please make sure the field exists and the column has a default value, or remove the tab callout";
			logger.warn(msg, new AdempiereException(msg));

			return; // nothing we can do
		}

		final Calendar datePromisedCal = TimeUtil.asCalendar(datePromised);

		//
		// If time is already set return
		if (datePromisedCal.get(Calendar.HOUR_OF_DAY) > 0
				|| datePromisedCal.get(Calendar.MINUTE) > 0)
		{
			return;
		}

		datePromisedCal.set(Calendar.HOUR_OF_DAY, 23);
		datePromisedCal.set(Calendar.MINUTE, 59);
		datePromisedCal.set(Calendar.SECOND, 59);
		datePromisedCal.set(Calendar.MILLISECOND, 999);

		final Timestamp datePromisedNew = new Timestamp(datePromisedCal.getTimeInMillis());
		datePromisedAware.setDatePromised(datePromisedNew);
	}

	private static interface DatePromisedAware
	{
		//@formatter:off
		String COLUMNNAME_DatePromised = "DatePromised";
		void setDatePromised(java.sql.Timestamp DatePromised);
		java.sql.Timestamp getDatePromised();
		//@formatter:on
	}
}
