package de.metas.dataentry.data;

import javax.annotation.Nullable;

import org.adempiere.user.CreatedUpdatedInfo;

import de.metas.dataentry.DataEntryFieldId;
import de.metas.dataentry.DataEntryListValueId;
import de.metas.dataentry.FieldType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

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

@EqualsAndHashCode(callSuper = true)
@ToString
public class DataEntryRecordFieldListValue extends DataEntryRecordField<DataEntryListValueId>
{
	@Getter
	DataEntryListValueId value;

	public static DataEntryRecordFieldListValue of(
			@NonNull final DataEntryFieldId dataEntryFieldId,
			@NonNull final CreatedUpdatedInfo createdUpdatedInfo,
			@Nullable final DataEntryListValueId value)
	{
		return new DataEntryRecordFieldListValue(dataEntryFieldId, createdUpdatedInfo, value);
	}

	private DataEntryRecordFieldListValue(
			@NonNull final DataEntryFieldId dataEntryFieldRepoId,
			@NonNull final CreatedUpdatedInfo createdUpdatedInfo,
			@Nullable final DataEntryListValueId listValueId)
	{
		super(dataEntryFieldRepoId, createdUpdatedInfo);
		this.value = listValueId;
	}

	@Override
	public FieldType getFieldType()
	{
		return FieldType.LIST;
	}
}
