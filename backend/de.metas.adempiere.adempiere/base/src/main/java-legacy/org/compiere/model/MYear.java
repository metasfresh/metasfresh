/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.process.DocumentTypeVerify;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.i18n.Language;
import de.metas.logging.LogManager;
import de.metas.process.JavaProcess;

/**
 * Year Model
 * 
 * @author Jorg Janke
 * @version $Id: MYear.java,v 1.5 2006/10/11 04:12:39 jjanke Exp $
 *
 * @author Teo Sarca, www.arhipac.ro
 *         <li>BF [ 1761918 ] Error creating periods for a year with per. created partial
 *         <li>BF [ 2430755 ] Year Create Periods display proper error message
 */
@SuppressWarnings("serial")
public class MYear extends X_C_Year
{
	private static final Logger logger = LogManager.getLogger(MYear.class);

	public MYear(final Properties ctx, final int C_Year_ID, final String trxName)
	{
		super(ctx, C_Year_ID, trxName);
		if (is_new())
		{
			// setC_Calendar_ID (0);
			// setYear (null);
			setProcessing(false);	// N
		}
	}

	public MYear(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	private int getYearAsInt()
	{
		return getYearAsInt(getFiscalYear());
	}

	/**
	 * @return year as int or 0
	 */
	private static int getYearAsInt(final String year)
	{
		try
		{
			return Integer.parseInt(year);
		}
		catch (final Exception e)
		{
			final StringTokenizer st = new StringTokenizer(year, "/-, \t\n\r\f");
			if (st.hasMoreTokens())
			{
				final String year2 = st.nextToken();
				try
				{
					return Integer.parseInt(year2);
				}
				catch (final Exception e2)
				{
					logger.warn(year + "->" + year2 + " - " + e2.toString());
				}
			}
			else
			{
				logger.warn(year + " - " + e.toString());
			}
		}
		return 0;
	}	// getYearAsInt

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("MYear[");
		sb.append(get_ID()).append("-")
				.append(getFiscalYear())
				.append("]");
		return sb.toString();
	}	// toString

	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		final int yy = getYearAsInt();
		if (yy <= 0)
		{
			throw new FillMandatoryException(COLUMNNAME_FiscalYear);
		}
		return true;
	}	// beforeSave

	/**
	 * Create 12 Standard Periods from the specified start date.
	 * Creates also Period Control from DocType.
	 * 
	 * @see DocumentTypeVerify#createPeriodControls(Properties, int, JavaProcess, String)
	 * @param locale locale
	 * @param startDate first day of the calendar year
	 * @param dateFormat SimpleDateFormat pattern for generating the period names.
	 */
	public static void createStdPeriods(final I_C_Year yearRecord, Locale locale, final Timestamp startDate, String dateFormat)
	{
		final Properties ctx = Env.getCtx();
		final int calendarId = yearRecord.getC_Calendar_ID();
		final int yearId = yearRecord.getC_Year_ID();

		if (locale == null)
		{
			final MClient client = MClient.get(ctx);
			locale = client.getLocale();
		}

		if (locale == null && Language.getLoginLanguage() != null)
		{
			locale = Language.getLoginLanguage().getLocale();
		}
		if (locale == null)
		{
			locale = Env.getLanguage(ctx).getLocale();
		}
		//
		SimpleDateFormat formatter;
		if (dateFormat == null || dateFormat.equals(""))
		{
			dateFormat = "MMM-yy";
		}
		formatter = new SimpleDateFormat(dateFormat, locale);

		//
		int year = getYearAsInt(yearRecord.getFiscalYear());
		final GregorianCalendar cal = new GregorianCalendar(locale);
		if (startDate != null)
		{
			cal.setTime(startDate);
			if (cal.get(Calendar.YEAR) != year)
			{
				year = cal.get(Calendar.YEAR);
			}

		}
		else
		{
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DAY_OF_MONTH, 1);
		}
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		//
		for (int month = 0; month < 12; month++)
		{

			final Timestamp start = new Timestamp(cal.getTimeInMillis());
			final String name = formatter.format(start);
			// get last day of same month
			cal.add(Calendar.MONTH, 1);
			cal.add(Calendar.DAY_OF_YEAR, -1);
			final Timestamp end = new Timestamp(cal.getTimeInMillis());
			//
			MPeriod period = MPeriod.findByCalendar(ctx, start, calendarId, ITrx.TRXNAME_ThreadInherited);
			if (period == null)
			{
				period = new MPeriod(yearRecord, month + 1, name, start, end);
			}
			else
			{
				period.setC_Year_ID(yearId);
				period.setPeriodNo(month + 1);
				period.setName(name);
				period.setStartDate(start);
				period.setEndDate(end);
			}
			period.saveEx(ITrx.TRXNAME_ThreadInherited);	// Creates Period Control
			// get first day of next month
			cal.add(Calendar.DAY_OF_YEAR, 1);
		}
	}
}
