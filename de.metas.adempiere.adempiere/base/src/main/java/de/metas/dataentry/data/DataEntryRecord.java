package de.metas.dataentry.data;

import javax.annotation.Nullable;

import org.adempiere.util.lang.ITableRecordReference;

import de.metas.dataentry.DataEntryGroupId;
import lombok.Builder;
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
	DataEntryRecordId id;

	DataEntryGroupId dataEntryGroupId;

	ITableRecordReference mainRecord;

	DataEntryFieldData dataEntryFieldData;

	@Builder
	private DataEntryRecord(
			@Nullable final DataEntryRecordId id,
			@NonNull final DataEntryGroupId dataEntryGroupId,
			@NonNull final DataEntryFieldData dataEntryFieldData,
			@NonNull final ITableRecordReference mainRecord)
	{
		this.id = id;
		this.dataEntryFieldData = dataEntryFieldData;
		this.mainRecord = mainRecord;
		this.dataEntryGroupId = dataEntryGroupId;
	}
}
