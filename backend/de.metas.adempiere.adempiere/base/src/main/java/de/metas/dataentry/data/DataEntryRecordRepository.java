package de.metas.dataentry.data;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.IQuery;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.dataentry.DataEntrySubTabId;
import de.metas.dataentry.data.json.JSONDataEntryRecordMapper;
import de.metas.dataentry.model.I_DataEntry_Record;
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
	private final JSONDataEntryRecordMapper jsonDataEntryRecordMapper;

	public DataEntryRecordRepository(@NonNull final JSONDataEntryRecordMapper jsonDataEntryRecordMapper)
	{
		this.jsonDataEntryRecordMapper = jsonDataEntryRecordMapper;
	}

	public Optional<DataEntryRecord> getBy(@NonNull final DataEntryRecordQuery query)
	{
		final I_DataEntry_Record record = query(query)
				.firstOnly(I_DataEntry_Record.class);

		return record != null
				? Optional.of(toDataEntryRecord(record))
				: Optional.empty();
	}

	public List<DataEntryRecord> list(@NonNull final DataEntryRecordQuery query)
	{
		return stream(query).collect(ImmutableList.toImmutableList());
	}

	public Stream<DataEntryRecord> stream(@NonNull final DataEntryRecordQuery query)
	{
		return query(query)
				.stream()
				.map(this::toDataEntryRecord);
	}

	public DataEntryRecord getById(@NonNull final DataEntryRecordId dataEntryRecordId)
	{
		final I_DataEntry_Record record = getRecordById(dataEntryRecordId);
		return toDataEntryRecord(record);
	}

	private I_DataEntry_Record getRecordById(final DataEntryRecordId dataEntryRecordId)
	{
		return load(dataEntryRecordId, I_DataEntry_Record.class);
	}

	private DataEntryRecord toDataEntryRecord(@NonNull final I_DataEntry_Record record)
	{
		final String jsonString = record.getDataEntry_RecordData();

		final List<DataEntryRecordField<?>> fields = jsonDataEntryRecordMapper.deserialize(jsonString);

		return DataEntryRecord.builder()
				.id(DataEntryRecordId.ofRepoId(record.getDataEntry_Record_ID()))
				.dataEntrySubTabId(DataEntrySubTabId.ofRepoId(record.getDataEntry_SubTab_ID()))
				.mainRecord(TableRecordReference.of(record.getAD_Table_ID(), record.getRecord_ID()))
				.fields(fields)
				.build();
	}

	public DataEntryRecordId save(@NonNull final DataEntryRecord dataEntryRecord)
	{
		final I_DataEntry_Record dataRecord;
		final DataEntryRecordId existingId = dataEntryRecord.getId().orElse(null);
		if (existingId == null)
		{
			dataRecord = newInstance(I_DataEntry_Record.class);
		}
		else
		{
			dataRecord = getRecordById(existingId);
		}

		dataRecord.setAD_Table_ID(dataEntryRecord.getMainRecord().getAD_Table_ID());
		dataRecord.setRecord_ID(dataEntryRecord.getMainRecord().getRecord_ID());
		dataRecord.setDataEntry_SubTab_ID(dataEntryRecord.getDataEntrySubTabId().getRepoId());
		dataRecord.setIsActive(true);

		final String jsonString = jsonDataEntryRecordMapper.serialize(dataEntryRecord.getFields());
		dataRecord.setDataEntry_RecordData(jsonString);

		saveRecord(dataRecord);

		final DataEntryRecordId dataEntryRecordId = DataEntryRecordId.ofRepoId(dataRecord.getDataEntry_Record_ID());
		dataEntryRecord.setId(dataEntryRecordId);

		return dataEntryRecordId;
	}

	public void deleteBy(@NonNull final DataEntryRecordQuery query)
	{
		query(query).delete();
	}

	private IQuery<I_DataEntry_Record> query(@NonNull final DataEntryRecordQuery query)
	{
		final ImmutableSet<DataEntrySubTabId> dataEntrySubTabIds = query.getDataEntrySubTabIds();
		final int recordId = query.getRecordId();

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_DataEntry_Record.class)
				.addOnlyActiveRecordsFilter() // we have a UC on those columns
				.addInArrayFilter(I_DataEntry_Record.COLUMN_DataEntry_SubTab_ID, dataEntrySubTabIds)
				.addEqualsFilter(I_DataEntry_Record.COLUMN_Record_ID, recordId)
				.orderBy(I_DataEntry_Record.COLUMNNAME_DataEntry_SubTab_ID)
				.create();
	}
}
