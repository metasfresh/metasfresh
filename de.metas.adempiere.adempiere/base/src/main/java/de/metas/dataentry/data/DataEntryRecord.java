package de.metas.dataentry.data;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.user.CreatedUpdatedInfo;
import org.adempiere.user.UserId;
import org.adempiere.util.lang.ITableRecordReference;

import com.google.common.collect.ImmutableList;

import de.metas.dataentry.DataEntryFieldId;
import de.metas.dataentry.DataEntrySubGroupId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

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
	/** May be empty if not yet persisted */
	Optional<DataEntryRecordId> id;

	/** If {@code true}, then the repository shall create a new DB record. */
	boolean isNew;

	DataEntrySubGroupId dataEntrySubGroupId;

	ITableRecordReference mainRecord;

	@Getter(AccessLevel.NONE)
	final Map<DataEntryFieldId, DataEntryRecordField<?>> fields;

	@Builder
	private DataEntryRecord(
			@Nullable final DataEntryRecordId id,
			final boolean isNew,
			@NonNull final ITableRecordReference mainRecord,
			@NonNull final DataEntrySubGroupId dataEntrySubGroupId,
			@NonNull final List<DataEntryRecordField<?>> fields)
	{
		this.id = Optional.ofNullable(id);
		this.isNew = isNew;
		this.mainRecord = mainRecord;
		this.dataEntrySubGroupId = dataEntrySubGroupId;

		this.fields = new HashMap<>();
		for (final DataEntryRecordField<?> i : fields)
		{
			this.fields.put(i.getDataEntryFieldId(), i);
		}
	}

	public void clearRecordFields()
	{
		fields.clear();
	}

	public void setRecordField(
			@NonNull final DataEntryFieldId dataEntryFieldId,
			@NonNull final UserId updatedBy,
			@Nullable final Object value)
	{
		final DataEntryRecordField<?> previousFieldVersion = fields.remove(dataEntryFieldId);

		if (value == null)
		{
			return;
		}

		final ZonedDateTime updated = ZonedDateTime.now();
		CreatedUpdatedInfo createdUpdatedInfo;
		if (previousFieldVersion == null)
		{
			createdUpdatedInfo = CreatedUpdatedInfo.createNew(updatedBy, updated);
		}
		else
		{
			createdUpdatedInfo = previousFieldVersion.getCreatedUpdatedInfo().updated(updatedBy, updated);
		}

		final DataEntryRecordField<?> //
		dataEntryRecordField = DataEntryRecordField.createDataEntryRecordField(dataEntryFieldId, createdUpdatedInfo, value);

		fields.put(dataEntryFieldId, dataEntryRecordField);
	}

	public boolean isEmpty()
	{
		return fields.isEmpty();
	}

	public ImmutableList<DataEntryRecordField<?>> getFields()
	{
		return ImmutableList.copyOf(fields.values());
	}

	public Optional<ZonedDateTime> getCreatedValue(@NonNull final DataEntryFieldId dataEntryFieldId)
	{
		return getOptional(dataEntryFieldId)
				.map(DataEntryRecordField::getCreatedUpdatedInfo)
				.map(CreatedUpdatedInfo::getCreated);
	}

	public Optional<UserId> getCreatedByValue(@NonNull final DataEntryFieldId dataEntryFieldId)
	{
		return getOptional(dataEntryFieldId)
				.map(DataEntryRecordField::getCreatedUpdatedInfo)
				.map(CreatedUpdatedInfo::getCreatedBy);
	}

	public Optional<ZonedDateTime> getUpdatedValue(@NonNull final DataEntryFieldId dataEntryFieldId)
	{
		return getOptional(dataEntryFieldId)
				.map(DataEntryRecordField::getCreatedUpdatedInfo)
				.map(CreatedUpdatedInfo::getUpdated);
	}

	public Optional<UserId> getUpdatedByValue(@NonNull final DataEntryFieldId dataEntryFieldId)
	{
		return getOptional(dataEntryFieldId)
				.map(DataEntryRecordField::getCreatedUpdatedInfo)
				.map(CreatedUpdatedInfo::getUpdatedBy);
	}

	public Optional<Object> getFieldValue(@NonNull final DataEntryFieldId dataEntryFieldId)
	{
		return getOptional(dataEntryFieldId)
				.map(DataEntryRecordField::getValue);
	}

	private Optional<DataEntryRecordField<?>> getOptional(@NonNull final DataEntryFieldId dataEntryFieldId)
	{
		return Optional.ofNullable(fields.get(dataEntryFieldId));
	}

}
