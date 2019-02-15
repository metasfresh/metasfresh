package de.metas.dataentry.data;

import static de.metas.util.Check.fail;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import org.adempiere.user.CreatedUpdatedInfo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import de.metas.dataentry.DataEntryFieldId;
import de.metas.dataentry.DataEntryListValueId;
import de.metas.dataentry.FieldType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

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

@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS)
@EqualsAndHashCode
public abstract class DataEntryRecordField<T>
{
	@Getter
	@JsonIgnore
	private final DataEntryFieldId dataEntryFieldId;

	@Getter
	@JsonIgnore
	private CreatedUpdatedInfo createdUpdatedInfo;

	@JsonIgnore
	public abstract FieldType getFieldType();

	protected DataEntryRecordField(
			@NonNull final DataEntryFieldId dataEntryFieldId,
			@NonNull final CreatedUpdatedInfo createdUpdatedInfo)
	{
		this.dataEntryFieldId = dataEntryFieldId;
		this.createdUpdatedInfo = createdUpdatedInfo;
	}

	@JsonIgnore
	public abstract T getValue();

	@JsonProperty("dataEntryFieldId")
	private int getDataEntryFieldRepoIdId()
	{
		return dataEntryFieldId.getRepoId();
	}

	@JsonProperty("createdBy")
	private int getCreatedByRepoId()
	{
		return createdUpdatedInfo.getCreatedBy().getRepoId();
	}

	@JsonProperty("created")
	private ZonedDateTime getCreated()
	{
		return createdUpdatedInfo.getCreated();
	}

	@JsonProperty("updatedBy")
	private int getUpdatedByRepoId()
	{
		return createdUpdatedInfo.getUpdatedBy().getRepoId();
	}

	@JsonProperty("updated")
	private ZonedDateTime getUpdated()
	{
		return createdUpdatedInfo.getUpdated();
	}

	@SuppressWarnings("unchecked")
	public static <T> DataEntryRecordField<T> createDataEntryRecordField(
			@NonNull final DataEntryFieldId dataEntryFieldId,
			@NonNull final CreatedUpdatedInfo createdUpdatedInfo,
			@NonNull final T value)
	{
		final DataEntryRecordField<T> result;

		if (value instanceof String)
		{
			result = (DataEntryRecordField<T>)DataEntryRecordFieldString.of(dataEntryFieldId, createdUpdatedInfo, (String)value);
		}
		else if (value instanceof BigDecimal)
		{
			result = (DataEntryRecordField<T>)DataEntryRecordFieldNumber.of(dataEntryFieldId, createdUpdatedInfo, (BigDecimal)value);

		}
		else if (value instanceof DataEntryListValueId)
		{
			result = (DataEntryRecordField<T>)DataEntryRecordFieldListValue.of(dataEntryFieldId, createdUpdatedInfo, (DataEntryListValueId)value);

		}
		else if (value instanceof ZonedDateTime)
		{
			result = (DataEntryRecordField<T>)DataEntryRecordFieldDate.of(dataEntryFieldId, createdUpdatedInfo, (ZonedDateTime)value);

		}
		else if (value instanceof Boolean)
		{
			result = (DataEntryRecordField<T>)DataEntryRecordFieldYesNo.of(dataEntryFieldId, createdUpdatedInfo, (Boolean)value);

		}
		else
		{
			fail("Unexpected value type={}; dataEntryFieldId={}", value.getClass(), dataEntryFieldId);
			result = null;
		}
		return result;
	}
}
