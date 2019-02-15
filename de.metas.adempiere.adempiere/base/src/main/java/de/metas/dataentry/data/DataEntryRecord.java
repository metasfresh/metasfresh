package de.metas.dataentry.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.adempiere.util.lang.ITableRecordReference;

import com.fasterxml.jackson.annotation.JsonCreator;

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
	/** May be null if not yet persisted */
	private final DataEntryRecordId id;

	private final DataEntrySubGroupId dataEntrySubGroupId;

	private final ITableRecordReference mainRecord;

	// @Getter(AccessLevel.NONE)
	// private final DataEntryFieldData dataEntryFieldData;

	@Getter(AccessLevel.NONE)
	final Map<DataEntryFieldId, DataEntryRecordField<?>> fields;

	@Builder
	private DataEntryRecord(
			@Nullable final DataEntryRecordId id,
			@NonNull final ITableRecordReference mainRecord,
			@NonNull final DataEntrySubGroupId dataEntrySubGroupId,
			@NonNull final List<DataEntryRecordField<?>> fields)
	{
		this.id = id;
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
			@Nullable final Object value)
	{
		fields.remove(dataEntryFieldId);

		if (value == null)
		{
			return;
		}

		final DataEntryRecordField<?> //
		dataEntryRecordField = DataEntryRecordField.createDataEntryRecordField(dataEntryFieldId, value);

		fields.put(dataEntryFieldId, dataEntryRecordField);
	}

	public boolean isEmpty()
	{
		return fields.isEmpty();
	}

	public DataEntryRecordFieldList getFields()
	{
		return new DataEntryRecordFieldList(fields.values());
	}

	/**
	 * Thx to https://github.com/FasterXML/jackson-databind/issues/336#issuecomment-27228643
	 */
	public static class DataEntryRecordFieldList extends ArrayList<DataEntryRecordField<?>>
	{
		private static final long serialVersionUID = 6030884469297498240L;

		@JsonCreator
		private DataEntryRecordFieldList(Collection<DataEntryRecordField<?>> fields)
		{
			super(fields);
		}
	};
}
