package de.metas.dataentry.data;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.dataentry.DataEntryFieldId;
import de.metas.dataentry.DataEntrySubTabId;
import de.metas.user.UserId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.Value;
import lombok.experimental.NonFinal;

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

@Value
public class DataEntryRecord
{
	final DataEntrySubTabId dataEntrySubTabId;
	final TableRecordReference mainRecord;

	@Getter(AccessLevel.NONE)
	final Map<DataEntryFieldId, DataEntryRecordField<?>> fields;
	final boolean readOnly;

	/** May be empty if not yet persisted */
	@Setter(AccessLevel.NONE)
	@NonFinal
	Optional<DataEntryRecordId> id;

	@Builder
	private DataEntryRecord(
			@Nullable final DataEntryRecordId id,
			@NonNull final TableRecordReference mainRecord,
			@NonNull final DataEntrySubTabId dataEntrySubTabId,
			@Nullable final List<DataEntryRecordField<?>> fields,
			final boolean readOnly)
	{
		this.id = Optional.ofNullable(id);
		this.mainRecord = mainRecord;
		this.dataEntrySubTabId = dataEntrySubTabId;

		this.readOnly = readOnly;
		this.fields = toMap(fields, readOnly);
	}

	/** Copy constructor */
	private DataEntryRecord(final DataEntryRecord from, final boolean readOnly)
	{
		this.id = from.id;
		this.mainRecord = from.mainRecord;
		this.dataEntrySubTabId = from.dataEntrySubTabId;

		this.readOnly = readOnly;
		this.fields = readOnly ? ImmutableMap.copyOf(from.fields) : new HashMap<>(from.fields);
	}

	private static Map<DataEntryFieldId, DataEntryRecordField<?>> toMap(
			@Nullable final Collection<DataEntryRecordField<?>> fields,
			final boolean readOnly)
	{
		if (readOnly)
		{
			if (fields == null || fields.isEmpty())
			{
				return ImmutableMap.of();
			}
			else
			{
				return Maps.uniqueIndex(fields, DataEntryRecordField::getDataEntryFieldId);
			}
		}
		else
		{
			if (fields == null || fields.isEmpty())
			{
				return new HashMap<>();
			}
			else
			{
				final HashMap<DataEntryFieldId, DataEntryRecordField<?>> map = new HashMap<>();
				for (final DataEntryRecordField<?> field : fields)
				{
					map.put(field.getDataEntryFieldId(), field);
				}
				return map;
			}
		}
	}

	public DataEntryRecord copyAsImmutable()
	{
		if (readOnly)
		{
			return this;
		}
		else
		{
			return new DataEntryRecord(this, true);
		}
	}

	private void assertReadWrite()
	{
		if (readOnly)
		{
			throw new AdempiereException("Changing readonly instance is not allowed: " + this);
		}
	}

	void setId(@NonNull final DataEntryRecordId id)
	{
		this.id = Optional.of(id);
	}

	/**
	 * @return {@code true} if the given value is different from the previous one.
	 */
	public boolean setRecordField(
			@NonNull final DataEntryFieldId dataEntryFieldId,
			@NonNull final UserId updatedBy,
			@Nullable final Object value)
	{
		assertReadWrite();

		final DataEntryRecordField<?> previousFieldVersion = fields.get(dataEntryFieldId);

		final Object previousValue = previousFieldVersion == null ? null : previousFieldVersion.getValue();
		final boolean valueChanged = !Objects.equals(previousValue, value);

		if (!valueChanged)
		{
			return false;
		}

		final ZonedDateTime updated = ZonedDateTime.now();
		final DataEntryCreatedUpdatedInfo createdUpdatedInfo;
		if (previousFieldVersion == null)
		{
			createdUpdatedInfo = DataEntryCreatedUpdatedInfo.createNew(updatedBy, updated);
		}
		else
		{
			createdUpdatedInfo = previousFieldVersion.getCreatedUpdatedInfo().updated(updatedBy, updated);
		}

		final DataEntryRecordField<?> dataEntryRecordField = value != null
				? DataEntryRecordField.createDataEntryRecordField(dataEntryFieldId, createdUpdatedInfo, value)
				: DataEntryRecordFieldString.of(dataEntryFieldId, createdUpdatedInfo, null);

		fields.put(dataEntryFieldId, dataEntryRecordField);
		return true;
	}

	public boolean isEmpty()
	{
		return fields.isEmpty();
	}

	public ImmutableList<DataEntryRecordField<?>> getFields()
	{
		return ImmutableList.copyOf(fields.values());
	}

	public Optional<ZonedDateTime> getCreatedValue(@NonNull final DataEntryFieldId fieldId)
	{
		return getCreatedUpdatedInfo(fieldId)
				.map(DataEntryCreatedUpdatedInfo::getCreated);
	}

	public Optional<UserId> getCreatedByValue(@NonNull final DataEntryFieldId fieldId)
	{
		return getCreatedUpdatedInfo(fieldId)
				.map(DataEntryCreatedUpdatedInfo::getCreatedBy);
	}

	public Optional<ZonedDateTime> getUpdatedValue(@NonNull final DataEntryFieldId fieldId)
	{
		return getCreatedUpdatedInfo(fieldId)
				.map(DataEntryCreatedUpdatedInfo::getUpdated);
	}

	public Optional<UserId> getUpdatedByValue(@NonNull final DataEntryFieldId fieldId)
	{
		return getCreatedUpdatedInfo(fieldId)
				.map(DataEntryCreatedUpdatedInfo::getUpdatedBy);
	}

	public Optional<DataEntryCreatedUpdatedInfo> getCreatedUpdatedInfo(@NonNull final DataEntryFieldId fieldId)
	{
		return getOptional(fieldId)
				.map(DataEntryRecordField::getCreatedUpdatedInfo);
	}

	public Optional<Object> getFieldValue(@NonNull final DataEntryFieldId fieldId)
	{
		return getOptional(fieldId)
				.map(DataEntryRecordField::getValue);
	}

	private Optional<DataEntryRecordField<?>> getOptional(@NonNull final DataEntryFieldId fieldId)
	{
		return Optional.ofNullable(fields.get(fieldId));
	}

}
