/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package org.adempiere.ad.persistence.modelgen;

import de.metas.adempiere.service.IColumnBL;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.compiere.util.DisplayType;

import java.math.BigDecimal;
import java.util.Optional;

@Value
@Builder
public class DataTypeInfo
{
	@NonNull
	String javaCode;
	boolean javaCodeFullyQualified;

	@NonNull
	Class<?> typeClass;
	boolean primitiveType;
	int displayType;

	@NonNull
	NullableType nullableValueSetter;

	@NonNull
	NullableType nullableValueGetter;

	@NonNull
	NullableType nullableValueObject;

	@NonNull
	public static DataTypeInfo ofColumnInfo(@NonNull final ColumnInfo columnInfo)
	{
		Class<?> typeClass = getClass(columnInfo);
		String typeClassName = typeClass.getName();
		typeClassName = typeClassName.substring(typeClassName.lastIndexOf('.') + 1);

		final int displayType = columnInfo.getDisplayType();
		final String javaCode;
		final boolean isJavaCodeFullyQualified;
		final boolean primitive;
		NullableType nullableValueSetter = columnInfo.isMandatory() ? NullableType.NON_NULL : NullableType.NULLABLE;
		NullableType nullableValueGetter = columnInfo.isMandatory() ? NullableType.NON_NULL : NullableType.NULLABLE;
		NullableType nullableValueObject = columnInfo.isMandatory() ? NullableType.NON_NULL : NullableType.NULLABLE;
		if (typeClassName.equals("Boolean"))
		{
			typeClass = boolean.class;
			javaCode = "boolean";
			isJavaCodeFullyQualified = false;
			primitive = true;
			nullableValueSetter = NullableType.DOES_NOT_APPLY;
			nullableValueGetter = NullableType.DOES_NOT_APPLY;
			nullableValueObject = NullableType.DOES_NOT_APPLY;
		}
		else if (typeClassName.equals("Integer"))
		{
			typeClass = int.class;
			javaCode = "int";
			isJavaCodeFullyQualified = false;
			primitive = true;
			nullableValueSetter = NullableType.DOES_NOT_APPLY;
			nullableValueGetter = NullableType.DOES_NOT_APPLY;
			//nullableValueObject = NullableType.DOES_NOT_APPLY;
		}
		else if (typeClassName.equals("BigDecimal"))
		{
			typeClass = BigDecimal.class;
			javaCode = typeClass.getSimpleName();
			isJavaCodeFullyQualified = false;
			primitive = false;
			nullableValueGetter = NullableType.NON_NULL; // getter never returns null
			nullableValueObject = NullableType.DOES_NOT_APPLY;
		}
		else if (displayType == DisplayType.Binary
				|| displayType == DisplayType.Image)
		{
			typeClass = byte[].class;
			javaCode = "byte[]";
			isJavaCodeFullyQualified = false;
			primitive = true;
			nullableValueObject = NullableType.DOES_NOT_APPLY;
		}
		else
		{
			javaCode = typeClass.getName(); // metas: always use FQ names
			isJavaCodeFullyQualified = true;
			primitive = false;
		}

		return DataTypeInfo.builder()
				.javaCode(javaCode)
				.javaCodeFullyQualified(isJavaCodeFullyQualified)
				.typeClass(typeClass)
				.primitiveType(primitive)
				.displayType(displayType)
				.nullableValueGetter(nullableValueGetter)
				.nullableValueSetter(nullableValueSetter)
				.nullableValueObject(nullableValueObject)
				.build();
	}

	/**
	 * Get class for given display type and reference
	 *
	 * @return class
	 */
	private static Class<?> getClass(final ColumnInfo columnInfo)
	{
		final int displayType = columnInfo.getDisplayType();
		final int AD_Reference_ID = columnInfo.getAdReferenceId();
		return getClass(columnInfo, displayType, AD_Reference_ID);
	}

	public static Class<?> getClass(
			final ColumnInfo columnInfo,
			int displayType,
			int AD_Reference_ID)
	{
		final String columnName = columnInfo.getColumnName();

		// Handle Posted
		// TODO: hardcoded
		if (columnName.equalsIgnoreCase("Posted")
				|| columnName.equalsIgnoreCase("Processed")
				|| columnName.equalsIgnoreCase("Processing"))
		{
			return Boolean.class;
		}
		// Record_ID
		// TODO: hardcoded
		else if (Services.get(IColumnBL.class).isRecordIdColumnName(columnName))
		{
			return Integer.class;
		}
		// Reference Table
		else if ((DisplayType.Table == displayType || DisplayType.Search == displayType)
				&& AD_Reference_ID > 0)
		{
			final Optional<TableReferenceInfo> tableReferenceInfoOrNull = columnInfo.getTableReferenceInfo();
			if (tableReferenceInfoOrNull.isPresent())
			{
				final TableReferenceInfo tableReferenceInfo = tableReferenceInfoOrNull.get();
				displayType = tableReferenceInfo.getRefDisplayType();
				AD_Reference_ID = tableReferenceInfo.getKeyReferenceValueId();
			}
			else
			{
				throw new IllegalStateException("Not found AD_Ref_Table/AD_Column for " + columnInfo);
			}
			//
			return getClass(columnInfo, displayType, AD_Reference_ID); // recursive call with new parameters
		}
		else
		{
			return DisplayType.getClass(displayType, true);
		}
	}

	public boolean isObject()
	{
		return "Object".equals(javaCode);
	}

	public boolean isString()
	{
		return String.class.equals(typeClass);
	}

	public boolean isBoolean()
	{
		return boolean.class.equals(typeClass)
				|| Boolean.class.equals(typeClass);
	}

	public boolean isInteger()
	{
		return int.class.equals(typeClass)
				|| Integer.class.equals(typeClass);
	}

	public boolean isBigDecimal()
	{
		return java.math.BigDecimal.class.equals(typeClass);
	}

	public boolean isTimestamp()
	{
		return java.sql.Timestamp.class.equals(typeClass);
	}

}
