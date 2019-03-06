package de.metas.dataentry.data;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.List;
import java.util.Optional;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.IQuery;
import org.springframework.stereotype.Repository;

import de.metas.dataentry.DataEntrySubGroupId;
import de.metas.dataentry.data.json.JSONDataEntryRecordMapper;
import de.metas.dataentry.model.I_DataEntry_Record;
import de.metas.util.Check;
import de.metas.util.Services;
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

@Repository
public class DataEntryRecordRepository
{
	private final JSONDataEntryRecordMapper jsonDataEntryRecordMapper;

	public DataEntryRecordRepository(@NonNull final JSONDataEntryRecordMapper jsonDataEntryRecordMapper)
	{
		this.jsonDataEntryRecordMapper = jsonDataEntryRecordMapper;
	}

	@Value
	public static final class DataEntryRecordQuery
	{
		@NonNull
		final DataEntrySubGroupId dataEntrySubGroupId;

		@NonNull
		final ITableRecordReference tableRecordReference;
	}

	public Optional<DataEntryRecord> getBy(@NonNull final DataEntryRecordQuery dataEntryRecordQuery)
	{
		final IQuery<I_DataEntry_Record> query = createQuery(dataEntryRecordQuery);

		final I_DataEntry_Record record = query.firstOnly(I_DataEntry_Record.class);

		if (record == null)
		{
			return Optional.empty();
		}

		return Optional.of(ofRecord(record));
	}

	public DataEntryRecord getBy(@NonNull final DataEntryRecordId dataEntryRecordId)
	{
		final I_DataEntry_Record record = load(dataEntryRecordId, I_DataEntry_Record.class);
		return ofRecord(record);
	}

	private DataEntryRecord ofRecord(@NonNull final I_DataEntry_Record record)
	{
		final String jsonString = record.getDataEntry_RecordData();

		final List<DataEntryRecordField<?>> fields = jsonDataEntryRecordMapper.deserialize(jsonString);

		return DataEntryRecord
				.builder()
				.id(DataEntryRecordId.ofRepoId(record.getDataEntry_Record_ID()))
				.isNew(false)
				.dataEntrySubGroupId(DataEntrySubGroupId.ofRepoId(record.getDataEntry_SubGroup_ID()))
				.mainRecord(TableRecordReference.of(record.getAD_Table_ID(), record.getRecord_ID()))
				.fields(fields)
				.build();
	}

	public DataEntryRecordId save(@NonNull final DataEntryRecord dataEntryRecord)
	{
		final I_DataEntry_Record dataRecord;
		if (dataEntryRecord.isNew())
		{
			dataRecord = newInstance(I_DataEntry_Record.class);
			dataEntryRecord.getId().ifPresent(id -> dataRecord.setDataEntry_SubGroup_ID(id.getRepoId()));
		}
		else
		{
			Check.assume(dataEntryRecord.getId().isPresent(), "If isNew=false, then the given dataEntryRecord needs to have an Id dataEntryRecord= {}", dataEntryRecord);
			dataRecord = load(dataEntryRecord.getId().get(), I_DataEntry_Record.class);
		}

		dataRecord.setAD_Table_ID(dataEntryRecord.getMainRecord().getAD_Table_ID());
		dataRecord.setRecord_ID(dataEntryRecord.getMainRecord().getRecord_ID());
		dataRecord.setDataEntry_SubGroup_ID(dataEntryRecord.getDataEntrySubGroupId().getRepoId());
		dataRecord.setIsActive(true);

		final String jsonString = jsonDataEntryRecordMapper.serialize(dataEntryRecord.getFields());
		dataRecord.setDataEntry_RecordData(jsonString);

		saveRecord(dataRecord);

		return DataEntryRecordId.ofRepoId(dataRecord.getDataEntry_Record_ID());
	}

	public void deleteBy(@NonNull final DataEntryRecordQuery dataEntryRecordQuery)
	{
		final IQuery<I_DataEntry_Record> query = createQuery(dataEntryRecordQuery);

		query.delete();
	}


	private IQuery<I_DataEntry_Record> createQuery(@NonNull final DataEntryRecordQuery dataEntryRecordQuery)
	{
		final DataEntrySubGroupId dataEntrySubGroupId = dataEntryRecordQuery.getDataEntrySubGroupId();
		final ITableRecordReference tableRecordReference = dataEntryRecordQuery.getTableRecordReference();

		final int adTableId = tableRecordReference.getAD_Table_ID();
		final int recordId = tableRecordReference.getRecord_ID();

		final IQuery<I_DataEntry_Record> query = Services.get(IQueryBL.class)
				.createQueryBuilder(I_DataEntry_Record.class)
				.addOnlyActiveRecordsFilter() // we have a UC on those three columns
				.addEqualsFilter(I_DataEntry_Record.COLUMN_DataEntry_SubGroup_ID, dataEntrySubGroupId)
				.addEqualsFilter(I_DataEntry_Record.COLUMN_AD_Table_ID, adTableId)
				.addEqualsFilter(I_DataEntry_Record.COLUMN_Record_ID, recordId)
				.create();
		return query;
	}
}
