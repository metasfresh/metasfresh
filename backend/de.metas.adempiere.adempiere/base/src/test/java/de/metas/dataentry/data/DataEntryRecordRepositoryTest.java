package de.metas.dataentry.data;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Optional;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_Product;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import de.metas.dataentry.DataEntrySubTabId;
import de.metas.dataentry.data.json.JSONDataEntryRecordMapper;
import de.metas.dataentry.model.I_DataEntry_Record;

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

public class DataEntryRecordRepositoryTest
{
	@Rule
	public final TestWatcher adempiereTestWatcher = new AdempiereTestWatcher();

	private DataEntryRecordRepository dataEntryRecordRepository;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		dataEntryRecordRepository = new DataEntryRecordRepository(new JSONDataEntryRecordMapper());
	}

	@Test
	public void saveData_empty() throws JSONException
	{
		final String expectedEmptyJSON = "{\r\n" +
				"  \"createdUpdatedInfos\" : { },\r\n" +
				"  \"dates\" : { },\r\n" +
				"  \"listValues\" : { },\r\n" +
				"  \"numbers\" : { },\r\n" +
				"  \"strings\" : { },\r\n" +
				"  \"yesNos\" : { }\r\n" +
				"}\r\n" +
				"";

		final DataEntrySubTabId dataEntrySubTabId = DataEntrySubTabId.ofRepoId(10);
		final DataEntryRecord dataEntryRecord = DataEntryRecord
				.builder()
				.dataEntrySubTabId(dataEntrySubTabId)
				.mainRecord(TableRecordReference.of(I_M_Product.Table_Name, 41))
				.build();

		// invoke the method under test
		final DataEntryRecordId resultId = dataEntryRecordRepository.save(dataEntryRecord);

		final I_DataEntry_Record resultRecord = load(resultId, I_DataEntry_Record.class);
		assertThat(resultRecord.getDataEntry_SubTab_ID()).isEqualTo(dataEntrySubTabId.getRepoId());
		final TableRecordReference resultReference = TableRecordReference.of(resultRecord.getAD_Table_ID(), resultRecord.getRecord_ID());
		assertThat(resultReference.getTableName()).isEqualTo(I_M_Product.Table_Name);
		assertThat(resultReference.getRecord_ID()).isEqualTo(41);

		JSONAssert.assertEquals(expectedEmptyJSON, resultRecord.getDataEntry_RecordData(), JSONCompareMode.STRICT);
	}

	@Test
	public void saveData_nonEmpty() throws IOException, JSONException
	{
		final DataEntrySubTabId dataEntrySubTabId = DataEntrySubTabId.ofRepoId(10);

		final DataEntryRecord dataEntryRecord = DataEntryRecord
				.builder()
				.dataEntrySubTabId(dataEntrySubTabId)
				.mainRecord(TableRecordReference.of(I_M_Product.Table_Name, 41))
				.fields(DataEntryRecordTestConstants.SIMPLE_DATA_ENTRY_FIELD_DATA)
				.build();

		// invoke the method under test
		final DataEntryRecordId resultId = dataEntryRecordRepository.save(dataEntryRecord);

		// get the data we just stored and compare it with our snapshot file
		final I_DataEntry_Record resultRecord = load(resultId, I_DataEntry_Record.class);
		assertThat(resultRecord.getDataEntry_SubTab_ID()).isEqualTo(dataEntrySubTabId.getRepoId());
		final TableRecordReference resultReference = TableRecordReference.of(resultRecord.getAD_Table_ID(), resultRecord.getRecord_ID());
		assertThat(resultReference.getTableName()).isEqualTo(I_M_Product.Table_Name);
		assertThat(resultReference.getRecord_ID()).isEqualTo(41);

		final String serializedRecordData = resultRecord.getDataEntry_RecordData();

		final String expectedString = DataEntryRecordTestConstants.SIMPLE_DATA_ENTRY_FIELD_DATA_JSON;

		JSONAssert.assertEquals(expectedString, serializedRecordData, JSONCompareMode.STRICT);
	}

	@Test
	public void getBy() throws IOException
	{
		DataEntrySubTabId dataEntrySubTabId = DataEntrySubTabId.ofRepoId(10);
		final TableRecordReference tableRecordReference = TableRecordReference.of(I_M_Product.Table_Name, 41);

		final I_DataEntry_Record record = newInstance(I_DataEntry_Record.class);
		record.setDataEntry_SubTab_ID(dataEntrySubTabId.getRepoId());
		record.setAD_Table_ID(tableRecordReference.getAD_Table_ID());
		record.setRecord_ID(tableRecordReference.getRecord_ID());
		record.setDataEntry_RecordData(DataEntryRecordTestConstants.SIMPLE_DATA_ENTRY_FIELD_DATA_JSON);
		saveRecord(record);

		final DataEntryRecordQuery query = DataEntryRecordQuery.builder()
				.dataEntrySubTabId(dataEntrySubTabId)
				.recordId(tableRecordReference.getRecord_ID())
				.build();

		// invoke the method under test
		final Optional<DataEntryRecord> result = dataEntryRecordRepository.getBy(query);

		assertThat(result).isPresent();
		assertThat(result.get().getId().get().getRepoId()).isEqualTo(record.getDataEntry_Record_ID());
		assertThat(result.get().getMainRecord().getAD_Table_ID()).isEqualTo(record.getAD_Table_ID());
		assertThat(result.get().getMainRecord().getRecord_ID()).isEqualTo(record.getRecord_ID());

		assertThat(result.get().getFields())
				.containsExactlyElementsOf(DataEntryRecordTestConstants.SIMPLE_DATA_ENTRY_FIELD_DATA);
	}

}
