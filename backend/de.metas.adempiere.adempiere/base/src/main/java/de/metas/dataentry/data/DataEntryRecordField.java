package de.metas.dataentry.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.metas.CreatedUpdatedInfo;
import de.metas.dataentry.DataEntryFieldId;
import de.metas.dataentry.DataEntryListValueId;
import de.metas.dataentry.FieldType;
import de.metas.dataentry.layout.DataEntryField;
import de.metas.dataentry.layout.DataEntryListValue;
import de.metas.util.NumberUtils;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

import static de.metas.util.Check.assume;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@EqualsAndHashCode
public abstract class DataEntryRecordField<T>
{
	@Getter
	@JsonIgnore
	private final DataEntryFieldId dataEntryFieldId;

	@Getter
	@JsonIgnore
	private final CreatedUpdatedInfo createdUpdatedInfo;

	protected DataEntryRecordField(
			@NonNull final DataEntryFieldId dataEntryFieldId,
			@NonNull final CreatedUpdatedInfo createdUpdatedInfo)
	{
		this.dataEntryFieldId = dataEntryFieldId;
		this.createdUpdatedInfo = createdUpdatedInfo;
	}

	@Nullable
	public abstract T getValue();

	@SuppressWarnings("unchecked")
	public static <T> DataEntryRecordField<T> createDataEntryRecordField(
			@NonNull final DataEntryFieldId dataEntryFieldId,
			@NonNull final CreatedUpdatedInfo createdUpdatedInfo,
			@NonNull final T value)
	{
		final DataEntryRecordField<T> result;

		if (FieldType.TEXT.getClazz().isInstance(value))
		{
			assume(FieldType.TEXT.getClazz().equals(String.class), "FieldType.TEXT's class needs to be {}", String.class);
			result = (DataEntryRecordField<T>)DataEntryRecordFieldString.of(dataEntryFieldId, createdUpdatedInfo, (String)value);
		}
		else if (FieldType.LONG_TEXT.getClazz().isInstance(value))
		{
			assume(FieldType.LONG_TEXT.getClazz().equals(String.class), "FieldType.LONG_TEXT's class needs to be {}", String.class);
			result = (DataEntryRecordField<T>)DataEntryRecordFieldString.of(dataEntryFieldId, createdUpdatedInfo, (String)value);
		}
		else if (FieldType.NUMBER.getClazz().isInstance(value))
		{
			assume(FieldType.NUMBER.getClazz().equals(BigDecimal.class), "FieldType.NUMBER's class needs to be {}", BigDecimal.class);
			result = (DataEntryRecordField<T>)DataEntryRecordFieldNumber.of(dataEntryFieldId, createdUpdatedInfo, (BigDecimal)value);
		}
		else if (FieldType.LIST.getClazz().isInstance(value))
		{
			assume(FieldType.LIST.getClazz().equals(DataEntryListValueId.class), "FieldType.LIST's class needs to be {}", DataEntryListValueId.class);
			result = (DataEntryRecordField<T>)DataEntryRecordFieldListValue.of(dataEntryFieldId, createdUpdatedInfo, (DataEntryListValueId)value);
		}
		else if (FieldType.DATE.getClazz().isInstance(value))
		{
			assume(FieldType.DATE.getClazz().equals(LocalDate.class), "FieldType.DATE's class needs to be {}", LocalDate.class);
			result = (DataEntryRecordField<T>)DataEntryRecordFieldDate.of(dataEntryFieldId, createdUpdatedInfo, (LocalDate)value);
		}
		else if (FieldType.YESNO.getClazz().isInstance(value))
		{
			assume(FieldType.YESNO.getClazz().equals(Boolean.class), "FieldType.YESNO's class needs to be {}", Boolean.class);
			result = (DataEntryRecordField<T>)DataEntryRecordFieldYesNo.of(dataEntryFieldId, createdUpdatedInfo, (Boolean)value);
		}
		else
		{
			throw new AdempiereException("Unexpected value `" + value + "` (" + value.getClass() + ") for " + dataEntryFieldId);
		}

		return result;
	}

	@Nullable
	public static Object convertValueToFieldType(
			@Nullable final Object value,
			@NonNull final DataEntryField field)
	{
		if (value == null)
		{
			return null;
		}

		final Class<?> typeClass = field.getType().getClazz();
		if (typeClass.isInstance(value))
		{
			return value;
		}
		else if (Integer.class.equals(typeClass))
		{
			final Integer valueConv = NumberUtils.asInteger(value, null);
			if (valueConv == null)

			{
				throw new AdempiereException("Failed converting `" + value + "` " + value.getClass() + " to " + Integer.class);
			}
			return valueConv;
		}
		else if (BigDecimal.class.equals(typeClass))
		{
			final BigDecimal valueConv = NumberUtils.asBigDecimal(value, null);
			if (valueConv == null)
			{
				throw new AdempiereException("Failed converting `" + value + "` " + value.getClass() + " to " + BigDecimal.class);
			}
			return valueConv;
		}
		else if (String.class.equals(typeClass))
		{
			return value.toString();
		}
		else if (Boolean.class.equals(typeClass))
		{
			final Boolean valueConv = StringUtils.toBoolean(value, null);
			if (valueConv == null)
			{
				throw new AdempiereException("Failed converting `" + value + "` " + value.getClass() + " to " + Boolean.class);
			}
			return valueConv;
		}
		else if (LocalDate.class.equals(typeClass))
		{
			return TimeUtil.asLocalDate(value);
		}
		else if (DataEntryListValueId.class.equals(typeClass))
		{
			return convertValueToListValueId(value, field);
		}
		else
		{
			throw new AdempiereException("Cannot convert `" + value + "` from " + value.getClass() + " to " + typeClass);
		}
	}

	@Nullable
	private static DataEntryListValueId convertValueToListValueId(
			@Nullable final Object value,
			@NonNull final DataEntryField field)
	{
		if (value == null)
		{
			return null;
		}

		//
		// Match by ID
		final Integer valueInt = NumberUtils.asIntegerOrNull(value);
		if (valueInt != null)
		{
			final DataEntryListValueId id = DataEntryListValueId.ofRepoIdOrNull(valueInt);
			if (id != null)
			{
				final DataEntryListValue matchedListValue = field.getFirstListValueMatching(listValue -> id.equals(listValue.getId()))
						.orElse(null);
				if (matchedListValue != null)
				{
					return matchedListValue.getId();
				}
			}
		}

		//
		// Match by Name
		{
			final String captionStr = value.toString().trim();
			if (captionStr.isEmpty())
			{
				return null;
			}

			final DataEntryListValue matchedListValue = field.getFirstListValueMatching(listValue -> listValue.isNameMatching(captionStr))
					.orElse(null);
			if (matchedListValue != null)
			{
				return matchedListValue.getId();
			}
		}

		//
		// Fail
		throw new AdempiereException("No list value found for `" + value + "`");
	}
}
