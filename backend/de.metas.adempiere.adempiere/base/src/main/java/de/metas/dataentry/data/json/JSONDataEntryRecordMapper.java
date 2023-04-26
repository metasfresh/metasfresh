package de.metas.dataentry.data.json;

import com.google.common.collect.ImmutableList;
import de.metas.CreatedUpdatedInfo;
import de.metas.dataentry.DataEntryFieldId;
import de.metas.dataentry.DataEntryListValueId;
import de.metas.dataentry.data.DataEntryRecordField;
import de.metas.dataentry.data.DataEntryRecordFieldDate;
import de.metas.dataentry.data.DataEntryRecordFieldListValue;
import de.metas.dataentry.data.DataEntryRecordFieldNumber;
import de.metas.dataentry.data.DataEntryRecordFieldString;
import de.metas.dataentry.data.DataEntryRecordFieldYesNo;
import de.metas.util.JSONObjectMapper;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

@Service
public class JSONDataEntryRecordMapper
{
	private final JSONObjectMapper<JSONDataEntryRecord> delegate = JSONObjectMapper.forClass(JSONDataEntryRecord.class);

	public String serialize(@NonNull final List<DataEntryRecordField<?>> fields)
	{
		final JSONDataEntryRecord.JSONDataEntryRecordBuilder record = JSONDataEntryRecord.builder();
		for (final DataEntryRecordField<?> field : fields)
		{
			record.createdUpdatedInfo(field.getDataEntryFieldId().getRepoId(), field.getCreatedUpdatedInfo());

			if (field instanceof DataEntryRecordFieldYesNo)
			{
				record.yesNo(
						field.getDataEntryFieldId().getRepoId(),
						((DataEntryRecordFieldYesNo)field).getValue());
			}
			else if (field instanceof DataEntryRecordFieldDate)
			{
				record.date(
						field.getDataEntryFieldId().getRepoId(),
						((DataEntryRecordFieldDate)field).getValue());
			}
			else if (field instanceof DataEntryRecordFieldListValue)
			{
				record.listValue(
						field.getDataEntryFieldId().getRepoId(),
						((DataEntryRecordFieldListValue)field).getValue());
			}
			else if (field instanceof DataEntryRecordFieldString)
			{
				record.string(
						field.getDataEntryFieldId().getRepoId(),
						((DataEntryRecordFieldString)field).getValue());
			}
			else if (field instanceof DataEntryRecordFieldNumber)
			{
				record.number(
						field.getDataEntryFieldId().getRepoId(),
						((DataEntryRecordFieldNumber)field).getValue());
			}
		}

		return delegate.writeValueAsString(record.build());
	}

	public List<DataEntryRecordField<?>> deserialize(@NonNull final String recordString)
	{
		final ImmutableList.Builder<DataEntryRecordField<?>> result = ImmutableList.builder();

		final JSONDataEntryRecord record = delegate.readValue(recordString);
		final Map<Integer, CreatedUpdatedInfo> createdUpdatedInfos = record.getCreatedUpdatedInfos();

		for (final Entry<Integer, LocalDate> data : record.getDates().entrySet())
		{
			final DataEntryRecordFieldDate dataEntryRecordField = DataEntryRecordFieldDate
					.of(
							DataEntryFieldId.ofRepoId(data.getKey()),
							createdUpdatedInfos.get(data.getKey()), data.getValue());

			result.add(dataEntryRecordField);
		}
		for (final Entry<Integer, DataEntryListValueId> data : record.getListValues().entrySet())
		{
			final DataEntryRecordFieldListValue dataEntryRecordField = DataEntryRecordFieldListValue
					.of(
							DataEntryFieldId.ofRepoId(data.getKey()),
							createdUpdatedInfos.get(data.getKey()), data.getValue());
			result.add(dataEntryRecordField);
		}
		for (final Entry<Integer, BigDecimal> data : record.getNumbers().entrySet())
		{
			final DataEntryRecordFieldNumber dataEntryRecordField = DataEntryRecordFieldNumber
					.of(
							DataEntryFieldId.ofRepoId(data.getKey()),
							createdUpdatedInfos.get(data.getKey()), data.getValue());
			result.add(dataEntryRecordField);
		}
		for (final Entry<Integer, String> data : record.getStrings().entrySet())
		{
			final DataEntryRecordFieldString dataEntryRecordField = DataEntryRecordFieldString
					.of(
							DataEntryFieldId.ofRepoId(data.getKey()),
							createdUpdatedInfos.get(data.getKey()), data.getValue());
			result.add(dataEntryRecordField);
		}
		for (final Entry<Integer, Boolean> data : record.getYesNos().entrySet())
		{
			final DataEntryRecordFieldYesNo dataEntryRecordField = DataEntryRecordFieldYesNo
					.of(
							DataEntryFieldId.ofRepoId(data.getKey()),
							createdUpdatedInfos.get(data.getKey()), data.getValue());
			result.add(dataEntryRecordField);
		}

		return result.build();
	}
}
