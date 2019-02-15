package de.metas.dataentry.data;

import static de.metas.util.Check.assumeNotNull;
import static org.adempiere.model.InterfaceWrapperHelper.deleteRecord;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadOrNew;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Repository;

import de.metas.dataentry.DataEntrySubGroupId;
import de.metas.dataentry.data.DataEntryRecord.DataEntryRecordFieldList;
import de.metas.dataentry.model.I_DataEntry_Record;
import de.metas.util.JsonSerializer;
import de.metas.util.Services;
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

@Repository
public class DataEntryRecordRepository
{
	private final JsonSerializer<DataEntryRecordFieldList> jsonSerializer = JsonSerializer.forClass(DataEntryRecordFieldList.class);

	public DataEntryRecord getBy(
			@NonNull final DataEntrySubGroupId dataEntrySubGroupId,
			@NonNull final ITableRecordReference tableRecordReference)
	{
		final int adTableId = tableRecordReference.getAD_Table_ID();
		final int recordId = tableRecordReference.getRecord_ID();

		final I_DataEntry_Record record = Services.get(IQueryBL.class)
				.createQueryBuilder(I_DataEntry_Record.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DataEntry_Record.COLUMN_DataEntry_SubGroup_ID, dataEntrySubGroupId)
				.addEqualsFilter(I_DataEntry_Record.COLUMN_AD_Table_ID, adTableId)
				.addEqualsFilter(I_DataEntry_Record.COLUMN_Record_ID, recordId)
				.create()
				.firstOnly(I_DataEntry_Record.class);

		assumeNotNull(record,
				"There needs to be a DataEntry_Record record for DataEntry_SubGroup_ID={}, AD_Table_ID={} and Record_ID={}",
				dataEntrySubGroupId.getRepoId(), adTableId, recordId);

		return ofRecord(record);
	}

	public DataEntryRecord getBy(@NonNull final DataEntryRecordId dataEntryRecordId)
	{
		final I_DataEntry_Record record = load(dataEntryRecordId, I_DataEntry_Record.class);
		return ofRecord(record);
	}

	private DataEntryRecord ofRecord(@NonNull final I_DataEntry_Record record)
	{
		final String jsonString = record.getDataEntry_RecordData();

		 final DataEntryRecordFieldList fromString = jsonSerializer.fromString(jsonString);

		return DataEntryRecord
				.builder()
				.fields(fromString)
				.dataEntrySubGroupId(DataEntrySubGroupId.ofRepoId(record.getDataEntry_SubGroup_ID()))
				.id(DataEntryRecordId.ofRepoId(record.getDataEntry_Record_ID()))
				.mainRecord(TableRecordReference.of(record.getAD_Table_ID(), record.getRecord_ID()))
				.build();
	}

	public DataEntryRecordId save(@NonNull final DataEntryRecord dataEntryRecord)
	{
		final I_DataEntry_Record dataRecord = loadOrNew(dataEntryRecord.getId(), I_DataEntry_Record.class);

		dataRecord.setAD_Table_ID(dataEntryRecord.getMainRecord().getAD_Table_ID());
		dataRecord.setRecord_ID(dataEntryRecord.getMainRecord().getRecord_ID());
		dataRecord.setDataEntry_SubGroup_ID(dataEntryRecord.getDataEntrySubGroupId().getRepoId());
		dataRecord.setIsActive(true);

		final String jsonString = jsonSerializer.toString(dataEntryRecord.getFields());
		dataRecord.setDataEntry_RecordData(jsonString);

		saveRecord(dataRecord);

		return DataEntryRecordId.ofRepoId(dataRecord.getDataEntry_Record_ID());
	}

	public void delete(@NonNull final DataEntryRecordId dataEntryRecordId)
	{
		final I_DataEntry_Record dataRecord = load(dataEntryRecordId, I_DataEntry_Record.class);
		if (dataRecord != null)
		{
			deleteRecord(dataRecord);
		}
	}
}
