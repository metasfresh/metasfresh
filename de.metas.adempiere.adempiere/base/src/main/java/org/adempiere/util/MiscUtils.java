package org.adempiere.util;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.GridTab;
import org.compiere.model.GridWindow;
import org.compiere.model.GridWindowVO;
import org.compiere.model.MQuery;
import org.compiere.model.MTable;
import org.compiere.model.MWindow;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.compiere.util.ValueNamePair;

import de.metas.logging.MetasfreshLastError;

public final class MiscUtils
{

	private MiscUtils()
	{
	}

	public static String loggerMsgs()
	{

		final ValueNamePair lastError = MetasfreshLastError.retrieveError();

		final StringBuffer msg = new StringBuffer(" Infos from CLogger: ");
		if (lastError != null)
		{
			msg.append("; Last error: [value='").append(lastError.getValue())
					.append("', name='").append(lastError.getName() + "']");
		}

		final ValueNamePair lastWarning = MetasfreshLastError.retrieveWarning();
		if (lastWarning != null)
		{
			msg.append(" Last warning: [value='")
					.append(lastWarning.getValue()).append("', name='").append(
							lastWarning.getName()).append("']");
		}
		return msg.toString();
	}

	public static String loggerMsgsUser()
	{

		final ValueNamePair lastError = MetasfreshLastError.retrieveError();
		if (lastError != null)
		{
			return lastError.getName();
		}

		final ValueNamePair lastWarning = MetasfreshLastError.retrieveWarning();
		if (lastWarning != null)
		{

			return lastWarning.getName();
		}
		return "Unknown Error";
	}

	public static IllegalArgumentException illegalArgumentEx(
			final Object value, final String paramName)
			throws IllegalArgumentException
	{

		final StringBuffer sb = new StringBuffer();
		sb.append("Illegal value '");
		sb.append(value);
		sb.append("' for param ");
		sb.append(paramName);

		return new IllegalArgumentException(sb.toString());

	}

	public static void throwIllegalArgumentEx(final Object value,
			final String paramName) throws IllegalArgumentException
	{

		throw illegalArgumentEx(value, paramName);
	}

	/**
	 * Checks if the given object is a {@link PO} instance and returns it cast to PO.
	 * 
	 * @param po
	 *            the object that should be <code>instanceof</code> po
	 * @return
	 * @throws IllegalArgumentException
	 *             if the given 'po' is <code>null</code> or not instance of PO.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends PO> T asPO(final Object po)
	{

		if (po == null)
		{
			throw new IllegalArgumentException("Param 'po' may not be null");
		}

		if (po instanceof PO)
		{
			return (T)po;
		}

		PO po2 = InterfaceWrapperHelper.getPO(po);
		if (po2 != null)
			return (T)po2;

		throw new IllegalArgumentException("Param 'po' must be a PO. Is: "
				+ po.getClass().getName());
	}

	public static <T extends PO> T asPO(final Object po, Class<T> clazz)
	{

		if (po == null)
		{
			throw new IllegalArgumentException("Param 'po' may not be null");
		}

		if (!clazz.isAssignableFrom(po.getClass()))
		{

			throw new IllegalArgumentException("Param 'po' must be a "
					+ clazz.getName() + ". Is: " + po.getClass().getName());

		}
		return clazz.cast(po);
	}

	public static boolean isToday(final Timestamp timestamp)
	{

		if (timestamp == null)
		{
			throw new IllegalArgumentException(
					"Param 'timestamp' may not be null");
		}

		final Calendar calNow = SystemTime.asGregorianCalendar();

		final Calendar calTs = new GregorianCalendar();
		calTs.setTime(timestamp);

		return calNow.get(Calendar.DAY_OF_MONTH) == calTs
				.get(Calendar.DAY_OF_MONTH)
				&& calNow.get(Calendar.MONTH) == calTs.get(Calendar.MONTH)
				&& calNow.get(Calendar.YEAR) == calTs.get(Calendar.YEAR);
	}

	public static Timestamp toTimeStamp(final String string)
	{
		try
		{
			return new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(
					string).getTime());
		}
		catch (ParseException e)
		{
			throwIllegalArgumentEx(string, "string");
			return null;
		}
	}

	public static int getCalloutId(final GridTab mTab, final String colName)
	{

		final Integer id = (Integer)mTab.getValue(colName);

		if (id == null || id <= 0)
		{
			return 0;
		}
		return id;
	}

	public static Boolean getBoolean(final GridTab mTab, final String colName)
	{

		final Object isSOTrx = mTab.getValue(colName);
		if (isSOTrx == null)
		{
			return false;
		}
		if (isSOTrx instanceof Boolean)
		{
			return (Boolean)isSOTrx;

		}
		else if (isSOTrx instanceof String)
		{
			return "Y".equals(isSOTrx);
		}

		throw new AdempiereException("Unexpected class " + isSOTrx.getClass()
				+ " of field " + colName + "; Value: " + isSOTrx);
	}

	/**
	 * Returns an instance of {@link ArrayList} that can be used as a key in HashSets and HashMaps.
	 * 
	 * @param input
	 * @return
	 * 
	 * @deprecated use {@link Util#mkKey(Object...)} instead
	 */
	@Deprecated
	public static ArrayKey mkKey(final Object... input)
	{
		return new ArrayKey(input);
	}

	/**
	 * Immutable wrapper for arrays that uses {@link Arrays#hashCode(Object[]))} and {@link Arrays#equals(Object)}.
	 * Instances of this class are obtained by {@link MiscUtils#mkKey(Object...)} and can be used as keys in hashmaps
	 * and hash sets.
	 * 
	 * Thanks to http://stackoverflow.com/questions/1595588/java-how-to-be-sure-to-store-unique-arrays-based-on
	 * -its-values-on-a-list
	 * 
	 * @author ts
	 * @deprecated use org.compiere.util.Util.ArrayKey
	 */
	@Deprecated
	public static class ArrayKey extends Util.ArrayKey
	{
		public ArrayKey(final Object... input)
		{
			super(input);
		}
	}

	/**
	 * For a {@link Timestamp} representing e.g. "17.12.2009 15:14:34" this method returns a timestamp representing
	 * "17.12.2009 00:00:00".
	 * 
	 * @param ctx
	 * @param ts
	 * @return
	 */
	public static Timestamp removeTime(final Timestamp ts)
	{

		final DateFormat fmt = DateFormat.getDateInstance(DateFormat.LONG);
		final String strDate = fmt.format(ts);

		java.util.Date date;
		try
		{
			date = fmt.parse(strDate);
		}
		catch (ParseException e)
		{
			throw new AdempiereException(e);
		}
		final Timestamp currentDate = new Timestamp(date.getTime());
		return currentDate;
	}

	/**
	 * @deprecated Please use {@link Util#getInstanceOrNull(Class, String)}
	 */
	@Deprecated
	public static <T> T getInstanceOrNull(final Class<T> interfaceClazz, final String className)
	{
		return Util.getInstanceOrNull(interfaceClazz, className);
	}

	public static String mkStackTraceStr()
	{
		final StringBuilder sb = new StringBuilder();
		for (final StackTraceElement ste : Thread.currentThread().getStackTrace())
		{
			sb.append("\tat " + ste);
		}
		return sb.toString();
	}

	public static GridTab getGridTabForTable(
			final Properties ctx,
			final int windowNo,
			final int AD_Table_ID,
			final boolean startWithEmptyQuery)
	{
		final int AD_Window_ID = MTable.get(ctx, AD_Table_ID).getAD_Window_ID();
		//
		return getGridTabForTableAndWindow(ctx, windowNo, AD_Window_ID, AD_Table_ID, startWithEmptyQuery);

	}

	public static GridTab getGridTabForTableAndWindow(
			final Properties ctx,
			final int windowNo,
			final int AD_Window_ID,
			final int AD_Table_ID,
			final boolean startWithEmptyQuery)
	{
		final GridWindowVO wVO = GridWindowVO.create(ctx, windowNo, AD_Window_ID);
		if (wVO == null)
		{
			MWindow w = new MWindow(Env.getCtx(), AD_Window_ID, null);
			throw new AdempiereException("No access to window - " + w.getName() + " (AD_Window_ID=" + AD_Window_ID + ")");
		}
		final GridWindow gridWindow = new GridWindow(wVO);
		//
		GridTab tab = null;
		int tabIndex = -1;
		for (int i = 0; i < gridWindow.getTabCount(); i++)
		{
			GridTab t = gridWindow.getTab(i);
			if (t.getAD_Table_ID() == AD_Table_ID)
			{
				tab = t;
				tabIndex = i;
				break;
			}
		}
		if (tab == null)
		{
			throw new AdempiereException("No Tab found for table " + MTable.getTableName(ctx, AD_Table_ID)
					+ ", Window:" + gridWindow.getName());
		}
		gridWindow.initTab(tabIndex);
		//
		if (startWithEmptyQuery)
		{
			tab.setQuery(MQuery.getEqualQuery("1", "2"));
			tab.query(false);
		}
		return tab;
	}
	
	/**
	 * Returns a string created from the given input, but without any non-ascii characters. If the given string is
	 * empty, then an empty string is returned.
	 * 
	 * It is assumed that
	 * <ul>
	 * <li>the given input is not null</li>
	 * <li>after stripping non-ascii chars from the given (a non-empty!) input there is at least one ascii character
	 * left to output</li>
	 * </ul>
	 * 
	 */
	public static String mkAsciiOnly(final String input)
	{
		Util.assume(input != null, "Input string is not null");
		
		if("".equals(input))
		{
			return "";
		}
		
		final String output = input.replaceAll("[^\\p{ASCII}]", "");
				
		Util.assume(!"".equals(output), "Input string '" + input + "' has at least one ascii-character");
        return output;
	}
}
