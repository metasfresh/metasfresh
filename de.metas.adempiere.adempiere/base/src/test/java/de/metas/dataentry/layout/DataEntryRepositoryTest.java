package de.metas.dataentry.layout;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_Table;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.metas.dataentry.DataEntryFieldId;
import de.metas.dataentry.DataEntryGroupId;
import de.metas.dataentry.DataEntryListValueId;
import de.metas.dataentry.DataEntrySubGroupId;
import de.metas.dataentry.FieldType;
import de.metas.dataentry.layout.DataEntryGroup.DocumentLinkColumnName;
import de.metas.dataentry.model.I_DataEntry_Field;
import de.metas.dataentry.model.I_DataEntry_Group;
import de.metas.dataentry.model.I_DataEntry_ListValue;
import de.metas.dataentry.model.I_DataEntry_Record;
import de.metas.dataentry.model.I_DataEntry_SubGroup;
import de.metas.dataentry.model.X_DataEntry_Field;
import de.metas.i18n.ImmutableTranslatableString;

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

public class DataEntryRepositoryTest
{
	private static final String STRING_FIELD1_DESCRIPTION = "stringField1_description";
	private static final String STRING_FIELD1_CAPTION = "stringField1_caption";
	private DataEntryGroupRepository dataEntryRepository;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		dataEntryRepository = new DataEntryGroupRepository();
	}

	@BeforeClass
	public static void beforeAll()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG);
	}

	@AfterClass
	public static void afterAll()
	{
		validateSnapshots();
	}

	@Test
	public void getByWindowId_no_record()
	{
		final I_DataEntry_Group groupRecord1 = createLayoutOnlyRecords();
		final AdWindowId windowId_1 = AdWindowId.ofRepoId(groupRecord1.getDataEntry_TargetWindow_ID());

		// invoke the method under test
		final List<DataEntryGroup> result = dataEntryRepository.getByWindowId(windowId_1);

		expect(result).toMatchSnapshot();
	}

	@Test
	public void getByWindowId_incl_record()
	{
		final I_DataEntry_Group groupRecord1 = createLayoutOnlyRecords();
		final AdWindowId windowId_1 = AdWindowId.ofRepoId(groupRecord1.getDataEntry_TargetWindow_ID());

		final I_DataEntry_Record dataRecord = newInstance(I_DataEntry_Record.class);
		dataRecord.setAD_Table_ID(11);
		dataRecord.setRecord_ID(21);
		dataRecord.setDataEntry_RecordData("DataEntry_RecordData");
		saveRecord(dataRecord);

		// invoke the method under test
		final List<DataEntryGroup> result = dataEntryRepository.getByWindowId(windowId_1);
		assertThat(result).hasSize(1);

		expect(result).toMatchSnapshot();
	}

	private I_DataEntry_Group createLayoutOnlyRecords()
	{
		final AdWindowId windowId_1 = AdWindowId.ofRepoId(10);

		final I_AD_Table tableRecord_1 = newInstance(I_AD_Table.class);
		tableRecord_1.setTableName("tableRecord_1_tableName");
		saveRecord(tableRecord_1);

		final I_AD_Tab tabRecord_1 = newInstance(I_AD_Tab.class);
		tabRecord_1.setAD_Window_ID(windowId_1.getRepoId());
		tabRecord_1.setAD_Table(tableRecord_1);
		saveRecord(tabRecord_1);

		final I_DataEntry_Group groupRecord1 = newInstance(I_DataEntry_Group.class);
		groupRecord1.setDataEntry_TargetWindow_ID(windowId_1.getRepoId());
		groupRecord1.setName("groupRecord1_name");
		groupRecord1.setDescription("groupRecord1_description");
		groupRecord1.setTabName("groupRecord1_tabName");
		saveRecord(groupRecord1);

		final I_DataEntry_SubGroup subgroupRecord1_1 = newInstance(I_DataEntry_SubGroup.class);
		subgroupRecord1_1.setDataEntry_Group(groupRecord1);
		subgroupRecord1_1.setName("subgroupRecord1_1_name");
		subgroupRecord1_1.setDescription("subgroupRecord1_1_description");
		subgroupRecord1_1.setTabName("subgroupRecord1_1_tabName");
		saveRecord(subgroupRecord1_1);

		final I_DataEntry_Field fieldRecord1_1_1 = newInstance(I_DataEntry_Field.class);
		fieldRecord1_1_1.setDataEntry_SubGroup(subgroupRecord1_1);
		fieldRecord1_1_1.setDataEntry_RecordType(X_DataEntry_Field.DATAENTRY_RECORDTYPE_Text);
		fieldRecord1_1_1.setName("fieldRecord1_1_1_name");
		fieldRecord1_1_1.setDescription("fieldRecord1_1_1_description");
		fieldRecord1_1_1.setIsMandatory(true);
		fieldRecord1_1_1.setSeqNo(10);
		fieldRecord1_1_1.setPersonalDataCategory(X_DataEntry_Field.PERSONALDATACATEGORY_Personal);
		saveRecord(fieldRecord1_1_1);

		final I_DataEntry_Field fieldRecord1_1_2 = newInstance(I_DataEntry_Field.class);
		fieldRecord1_1_2.setDataEntry_SubGroup(subgroupRecord1_1);
		fieldRecord1_1_2.setDataEntry_RecordType(X_DataEntry_Field.DATAENTRY_RECORDTYPE_Number);
		fieldRecord1_1_2.setName("fieldRecord1_1_2_name");
		fieldRecord1_1_2.setDescription("fieldRecord1_1_2_description");
		fieldRecord1_1_2.setIsMandatory(false);
		fieldRecord1_1_2.setSeqNo(20);
		fieldRecord1_1_2.setPersonalDataCategory(X_DataEntry_Field.PERSONALDATACATEGORY_NotPersonal);
		saveRecord(fieldRecord1_1_2);

		final I_DataEntry_SubGroup subgroupRecord1_2 = newInstance(I_DataEntry_SubGroup.class);
		subgroupRecord1_2.setDataEntry_Group(groupRecord1);
		subgroupRecord1_2.setName("subgroupRecord1_2_name");
		subgroupRecord1_2.setDescription("subgroupRecord1_2_description");
		subgroupRecord1_2.setTabName("subgroupRecord1_2_tabName");
		saveRecord(subgroupRecord1_2);

		final I_DataEntry_Field fieldRecord1_2_1 = newInstance(I_DataEntry_Field.class);
		fieldRecord1_2_1.setDataEntry_SubGroup(subgroupRecord1_2);
		fieldRecord1_2_1.setDataEntry_RecordType(X_DataEntry_Field.DATAENTRY_RECORDTYPE_Date);
		fieldRecord1_2_1.setName("fieldRecord1_1_1_name");
		fieldRecord1_2_1.setDescription("fieldRecord1_1_1_description");
		fieldRecord1_2_1.setIsMandatory(false);
		fieldRecord1_2_1.setSeqNo(10);
		fieldRecord1_2_1.setPersonalDataCategory(X_DataEntry_Field.PERSONALDATACATEGORY_Personal);
		saveRecord(fieldRecord1_2_1);

		final I_DataEntry_Field fieldRecord1_2_2 = newInstance(I_DataEntry_Field.class);
		fieldRecord1_2_2.setDataEntry_SubGroup(subgroupRecord1_2);
		fieldRecord1_2_2.setDataEntry_RecordType(X_DataEntry_Field.DATAENTRY_RECORDTYPE_List);
		fieldRecord1_2_2.setName("fieldRecord1_1_2_name");
		fieldRecord1_2_2.setDescription("fieldRecord1_1_2_description");
		fieldRecord1_2_2.setIsMandatory(false);
		fieldRecord1_2_2.setSeqNo(20);
		fieldRecord1_2_2.setPersonalDataCategory(X_DataEntry_Field.PERSONALDATACATEGORY_NotPersonal);
		saveRecord(fieldRecord1_2_2);

		final I_DataEntry_ListValue listValueRecord_1_2_2_1 = newInstance(I_DataEntry_ListValue.class);
		listValueRecord_1_2_2_1.setDataEntry_Field(fieldRecord1_2_2);
		listValueRecord_1_2_2_1.setName("listValueRecord_1_2_2_1_name");
		listValueRecord_1_2_2_1.setDescription("listValueRecord_1_2_2_1_description");
		listValueRecord_1_2_2_1.setSeqNo(10);
		saveRecord(listValueRecord_1_2_2_1);

		final I_DataEntry_ListValue listValueRecord_1_2_2_2 = newInstance(I_DataEntry_ListValue.class);
		listValueRecord_1_2_2_2.setDataEntry_Field(fieldRecord1_2_2);
		listValueRecord_1_2_2_2.setName("listValueRecord_1_2_2_2_name");
		listValueRecord_1_2_2_2.setDescription("listValueRecord_1_2_2_2_description");
		listValueRecord_1_2_2_2.setSeqNo(20);
		saveRecord(listValueRecord_1_2_2_2);

		final I_DataEntry_Field fieldRecord1_2_3 = newInstance(I_DataEntry_Field.class);
		fieldRecord1_2_3.setDataEntry_SubGroup(subgroupRecord1_2);
		fieldRecord1_2_3.setDataEntry_RecordType(X_DataEntry_Field.DATAENTRY_RECORDTYPE_YesNo);
		fieldRecord1_2_3.setName("fieldRecord1_1_3_name");
		fieldRecord1_2_3.setDescription("fieldRecord1_1_3_description");
		fieldRecord1_2_3.setIsMandatory(false);
		fieldRecord1_2_3.setSeqNo(30);
		fieldRecord1_2_3.setPersonalDataCategory(X_DataEntry_Field.PERSONALDATACATEGORY_NotPersonal);
		saveRecord(fieldRecord1_2_3);

		return groupRecord1;
	}

	private DataEntryGroup createSimpleDataEntryGroup()
	{
		final DataEntryFieldId dataEntryListFieldId = DataEntryFieldId.ofRepoId(35);

		final DataEntryField dataEntryStringField = DataEntryField.builder()
				.id(DataEntryFieldId.ofRepoId(31))
				.caption(ImmutableTranslatableString.constant(STRING_FIELD1_CAPTION))
				.description(ImmutableTranslatableString.constant(STRING_FIELD1_DESCRIPTION))
				.type(FieldType.STRING)
				.build();

		final DataEntryField dataEntryNumberField = DataEntryField.builder()
				.id(DataEntryFieldId.ofRepoId(32))
				.caption(ImmutableTranslatableString.constant("numberField1_caption"))
				.description(ImmutableTranslatableString.constant("numberField1_description"))
				.type(FieldType.NUMBER)
				.build();

		final DataEntryField dataEntryDateField = DataEntryField.builder()
				.id(DataEntryFieldId.ofRepoId(33))
				.caption(ImmutableTranslatableString.constant("dateField1_caption"))
				.description(ImmutableTranslatableString.constant("dateField1_description"))
				.type(FieldType.DATE)
				.build();

		final DataEntryField dataEntryYesNoField = DataEntryField.builder()
				.id(DataEntryFieldId.ofRepoId(34))
				.caption(ImmutableTranslatableString.constant("yesNoField1_caption"))
				.description(ImmutableTranslatableString.constant("yesNoField1_description"))
				.type(FieldType.YESNO)
				.build();

		final DataEntryField dataEntryListField = DataEntryField.builder()
				.id(dataEntryListFieldId)
				.caption(ImmutableTranslatableString.constant("listField1_caption"))
				.description(ImmutableTranslatableString.constant("listField1_description"))
				.type(FieldType.LIST)
				.listValue(new DataEntryListValue(
						DataEntryListValueId.ofRepoId(41),
						dataEntryListFieldId,
						ImmutableTranslatableString.constant("listValue1_name"),
						ImmutableTranslatableString.constant("listValue1_description")))
				.listValue(new DataEntryListValue(
						DataEntryListValueId.ofRepoId(42),
						dataEntryListFieldId,
						ImmutableTranslatableString.constant("listValue2_name"),
						ImmutableTranslatableString.constant("listValue2_description")))
				.build();


		final DataEntryGroup dataEntryGroup = DataEntryGroup
				.builder()
				.id(DataEntryGroupId.ofRepoId(10))
				.documentLinkColumnName(DocumentLinkColumnName.of("documentLinkColumnName"))
				.internalName("dataEntryGroup_internalName")
				.caption(ImmutableTranslatableString.constant("dataEntryGroup_caption"))
				.description(ImmutableTranslatableString.constant("dataEntryGroup_description"))
				.dataEntrySubGroup(DataEntrySubGroup.builder()
						.id(DataEntrySubGroupId.ofRepoId(20))
						.internalName("dataEntrySubGroup_internalName")
						.caption(ImmutableTranslatableString.constant("dataEntrySubGroup_caption"))
						.description(ImmutableTranslatableString.constant("dataEntrySubGroup_description"))
						.dataEntryField(dataEntryStringField)
						.dataEntryField(dataEntryNumberField)
						.dataEntryField(dataEntryDateField)
						.dataEntryField(dataEntryYesNoField)
						.dataEntryField(dataEntryListField)
						.build())
				.build();

		return dataEntryGroup;
	}

}
