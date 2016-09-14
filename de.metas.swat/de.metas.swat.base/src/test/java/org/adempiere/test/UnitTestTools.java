package org.adempiere.test;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.createNiceMock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import org.adempiere.util.ISingletonService;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.model.POInfoColumn;
import org.compiere.util.DisplayType;
import org.junit.Assert;

public class UnitTestTools
{

	public static <T extends ISingletonService> T serviceMock(final Class<T> clazz,
			final Collection<Object> mocks)
	{

		final T mock = mock(clazz, mocks);

		Services.registerService(clazz, mock);
		return mock;
	}

	public static <T extends ISingletonService> T serviceMock(final Class<T> clazz,
			final Map<String, Object> mocks)
	{

		final String key = clazz.getSimpleName();
		final T mock = mock(clazz, key, mocks);

		Services.registerService(clazz, mock);
		return mock;
	}

	@Deprecated
	public static <T extends ISingletonService> T serviceMock(final Class<T> clazz, final String key,
			final Map<String, Object> mocks)
	{

		final T mock = mock(clazz, key, mocks);

		Services.registerService(clazz, mock);
		return mock;
	}

	public static <T> T mock(final Class<T> clazz,
			final Collection<Object> mocks)
	{

		final T mock = createMock(clazz);
		mocks.add(mock);

		return mock;
	}

	public static <T> T mock(final Class<T> clazz, final String key, final Map<String, Object> mocks)
	{

		final T mock = createMock(clazz);

		final Object oldVal = mocks.put(key, mock);
		Assert.assertNull("Error in test setup: there is already a mock with key '" + key + "'", oldVal);

		return mock;
	}

	/**
	 * Returns a timestamp that is <code>days</code> days before the current
	 * time.
	 *
	 * @param days
	 * @return
	 */
	public static Timestamp daysBefore(final int days)
	{

		if (days < 0)
		{
			throw new IllegalArgumentException(
					"Parameter days must be 0 or positive; Was: " + days + ".");
		}

		Calendar cal = GregorianCalendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, days * -1);
		return new Timestamp(cal.getTimeInMillis());
	}

	/**
	 * Returns a timestamp that is <code>days</code> days after the current
	 * time.
	 *
	 * @param days
	 * @return
	 */
	public static Timestamp daysAfter(final int days)
	{

		if (days < 0)
		{
			throw new IllegalArgumentException(
					"Parameter days must be 0 or positive; Was: " + days + ".");
		}

		Calendar cal = SystemTime.asGregorianCalendar();
		cal.add(Calendar.DAY_OF_MONTH, days);
		return new Timestamp(cal.getTimeInMillis());
	}

	@SuppressWarnings("unchecked")
	public static POInfo mockPOInfo(final String tableName,
			final Class<? extends PO> classToMock, final String name,
			final Map<String, Object> mocks)
	{

		final POInfo poInfo = createNiceMock(POInfo.class);

		int colCount = 0;
		for (final Field field : classToMock.getFields())
		{

			if (!field.getName().startsWith("COLUMNNAME_"))
			{
				// we are only interested in the interface's 'COLUMNNAME_*'
				// constants.
				continue;
			}

			String colName;
			Class colClass;

			try
			{
				// the column name is the constant's value
				colName = (String)field.get(null);
				colClass = findColClass(classToMock, colName);

			}
			catch (IllegalArgumentException e)
			{
				throw new RuntimeException(e);
			}
			catch (IllegalAccessException e)
			{
				throw new RuntimeException(e);
			}

			final boolean isKeyCol = (tableName + "_ID").equals(colName);
			final boolean isUpdatable = !isKeyCol;

			int displayType;
			if (isKeyCol)
			{
				displayType = DisplayType.ID;
			}
			else
			{

				displayType = DisplayType.String;
			}

			setupForColIdx(poInfo, colCount, colName, colClass, isKeyCol,
					isUpdatable, displayType);

			colCount++;
		}

		expect(poInfo.getColumnCount()).andStubReturn(colCount);
		expect(poInfo.getTableName()).andStubReturn(tableName);
		mocks.put(name, poInfo);
		return poInfo;
	}

	public static void assertMethodExists(final Class<?> className,
			final String methodName, final Class<?>[] params,
			final Class<?> returnType)
	{

		try
		{
			final Method method = className.getMethod(methodName, params);
			assertEquals(returnType, method.getReturnType());
		}
		catch (SecurityException e)
		{
			fail();
		}
		catch (NoSuchMethodException e)
		{
			fail("Method doesn't exist");
		}
	}

	@SuppressWarnings("unchecked")
	private static POInfoColumn setupForColIdx(final POInfo poInfo,
			final int colCount, final String colName, final Class colClass,
			final boolean isKeyCol, final boolean isUpdatable,
			final int displayType)
	{

		expect(poInfo.getColumnIndex(colName)).andStubReturn(colCount);
		expect(poInfo.isVirtualColumn(colCount)).andStubReturn(false);
		expect(poInfo.getColumnName(colCount)).andStubReturn(colName);

		expect(poInfo.isKey(colCount)).andStubReturn(isKeyCol);
		expect(poInfo.isColumnUpdateable(colCount)).andStubReturn(isUpdatable);

		expect(poInfo.getColumnClass(colCount)).andStubReturn(colClass);

		expect(poInfo.isColumnParent(colCount)).andStubReturn(false);
		expect(poInfo.getColumnDisplayType(colCount))
				.andStubReturn(displayType);

		final POInfoColumn poInfoColumn = new POInfoColumn(
				colCount,
				colName,
				null, // columnSQL
				displayType,
				false, // isMandatory
				isUpdatable,
				null, // defaultLogic
				null, // columnLabel
				null, // columnDescription
				isKeyCol, // isKey
				false, // isParent
				-1, // ad_Reference_Value_ID
				-1, // AD_Val_Rule_ID
				255, // fieldLength
				null, // valueMin
				null, // valueMax
				false, // IsTranslated
				false, // IsEncrypted
				false); // IsAllowLogging

		expect(poInfo.getColumn(colCount)).andStubReturn(poInfoColumn);
		return poInfoColumn;
	}

	private static Class<?> findColClass(final Class<? extends PO> classToMock,
			String colName)
	{

		Class<?> colClass = String.class;

		//
		// now get the column type by examining the setter's parameter
		// type
		for (final Method method : classToMock.getMethods())
		{

			if (method.getName().equals("set" + colName))
			{

				if (method.getParameterTypes().length == 1)
				{

					colClass = method.getParameterTypes()[0];

					if (colClass == boolean.class)
					{
						colClass = Boolean.class;
					}
				}
			}
		}
		return colClass;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getFromMap(final Map<String, Object> map, Class<T> clazz,
			final String key)
	{

		final Object val = map.get(key);
		assertNotNull("Error in test setup: no value for key '" + key
				+ "' (" + clazz + ")", val);

		return (T)val;
	}

	/**
	 *
	 * @param string
	 *            with format "yyyy-MM-dd".
	 * @return
	 */
	public static Timestamp toTimeStamp(final String string)
	{
		try
		{
			return new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(
					string).getTime());
		}
		catch (ParseException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 *
	 * @param string
	 *            with format "yyyy-MM-dd".
	 * @return
	 */
	public static Date toDate(final String string)
	{
		try
		{
			return new Date(new SimpleDateFormat("yyyy-MM-dd").parse(string)
					.getTime());
		}
		catch (ParseException e)
		{
			throw new RuntimeException(e);
		}
	}
}
