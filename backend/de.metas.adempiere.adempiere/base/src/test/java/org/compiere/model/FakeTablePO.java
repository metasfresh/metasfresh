/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package org.compiere.model;

import com.google.common.collect.ImmutableList;
import de.metas.cache.CCache;
import de.metas.security.TableAccessLevel;
import org.adempiere.ad.column.AdColumnId;
import org.adempiere.ad.table.api.AdTableId;
import org.compiere.model.copy.ColumnCloningStrategy;
import org.compiere.model.copy.TableCloningEnabled;
import org.compiere.model.copy.TableDownlineCloningStrategy;
import org.compiere.model.copy.TableWhenChildCloningStrategy;
import org.compiere.util.DisplayType;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;

/**
 * A real {@link PO} for unit tests, backed by a fake table ({@value #TABLE_NAME}) whose {@link POInfo} is registered without a database.
 * <p>
 * A {@link POInfo} is normally loaded from AD_Table/AD_Column (see {@code POInfo.retrievePOInfoMap()}), and its constructor is private.
 * Hence {@link #registerPOInfo()} builds it via reflection and puts it into {@code POInfo}'s cache.
 * Call it after {@code AdempiereTestHelper.get().init()}, which resets that cache.
 */
public class FakeTablePO extends PO
{
	public static final String TABLE_NAME = "Test_FakeTable";
	public static final String KEY_COLUMN_NAME = TABLE_NAME + "_ID";
	/**
	 * A reference to another record of the same table.
	 */
	public static final String REF_COLUMN_NAME = "Ref_ID";

	public FakeTablePO(final Properties ctx)
	{
		super(ctx, 0, null);
	}

	@Override
	protected POInfo initPO(final Properties ctx)
	{
		return POInfo.getPOInfo(TABLE_NAME);
	}

	public static void registerPOInfo()
	{
		try
		{
			final POInfo poInfo = newPOInfo(ImmutableList.of(
					newColumn(1, KEY_COLUMN_NAME, DisplayType.ID, true),
					newColumn(2, REF_COLUMN_NAME, DisplayType.TableDir, false)));

			final Field cacheField = POInfo.class.getDeclaredField("poInfoMapCache");
			cacheField.setAccessible(true);
			@SuppressWarnings("unchecked") final CCache<Integer, POInfo.POInfoMap> cache = (CCache<Integer, POInfo.POInfoMap>)cacheField.get(null);
			cache.put(0, new POInfo.POInfoMap(ImmutableList.of(poInfo)));
		}
		catch (final ReflectiveOperationException ex)
		{
			throw new IllegalStateException("Failed registering the POInfo of " + TABLE_NAME, ex);
		}
	}

	private static POInfo newPOInfo(final List<POInfoColumn> columns) throws ReflectiveOperationException
	{
		final Class<?> headerClass = Class.forName(POInfo.class.getName() + "$POInfoHeader");
		final Object builder = invoke(headerClass, null, "builder");
		final Class<?> builderClass = builder.getClass();
		invoke(builderClass, builder, "tableName", String.class, TABLE_NAME);
		invoke(builderClass, builder, "adTableId", AdTableId.class, AdTableId.ofRepoId(540001));
		invoke(builderClass, builder, "accessLevel", TableAccessLevel.class, TableAccessLevel.All);
		invoke(builderClass, builder, "cloningEnabled", TableCloningEnabled.class, TableCloningEnabled.Disabled);
		invoke(builderClass, builder, "whenChildCloningStrategy", TableWhenChildCloningStrategy.class, TableWhenChildCloningStrategy.Skip);
		invoke(builderClass, builder, "downlineCloningStrategy", TableDownlineCloningStrategy.class, TableDownlineCloningStrategy.Skip);
		final Object header = invoke(builderClass, builder, "build");

		final Constructor<POInfo> constructor = POInfo.class.getDeclaredConstructor(headerClass, List.class);
		constructor.setAccessible(true);
		return constructor.newInstance(header, columns);
	}

	private static Object invoke(final Class<?> clazz, final Object target, final String methodName) throws ReflectiveOperationException
	{
		final Method method = clazz.getDeclaredMethod(methodName);
		method.setAccessible(true);
		return method.invoke(target);
	}

	private static void invoke(final Class<?> clazz, final Object target, final String methodName, final Class<?> paramType, final Object value) throws ReflectiveOperationException
	{
		final Method method = clazz.getDeclaredMethod(methodName, paramType);
		method.setAccessible(true);
		method.invoke(target, value);
	}

	private static POInfoColumn newColumn(final int adColumnId, final String columnName, final int displayType, final boolean isKey)
	{
		return new POInfoColumn(
				AdColumnId.ofRepoId(adColumnId),
				TABLE_NAME,
				columnName,
				null, // columnSQL
				displayType,
				false, // isMandatory
				true, // isUpdateable
				null, // defaultLogic
				columnName, // columnLabel
				null, // columnDescription
				isKey,
				false, // isParent
				null, // AD_Reference_Value_ID
				null, // AD_Reference_Value_TableName
				-1, // AD_Reference_Value_KeyColumn_DisplayType
				null, // AD_Val_Rule_ID
				10, // fieldLength
				null, // valueMin
				null, // valueMax
				false, // isTranslated
				false, // isEncrypted
				true, // isAllowLogging
				-1, // AD_Sequence_ID
				ColumnCloningStrategy.Auto,
				false); // isIdentifier
	}
}
