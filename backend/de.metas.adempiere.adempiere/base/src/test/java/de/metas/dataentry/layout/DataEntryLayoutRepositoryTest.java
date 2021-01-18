package de.metas.dataentry.layout;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_Table;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.metas.dataentry.model.I_DataEntry_Field;
import de.metas.dataentry.model.I_DataEntry_Line;
import de.metas.dataentry.model.I_DataEntry_ListValue;
import de.metas.dataentry.model.I_DataEntry_Section;
import de.metas.dataentry.model.I_DataEntry_SubTab;
import de.metas.dataentry.model.I_DataEntry_Tab;
import de.metas.dataentry.model.X_DataEntry_Field;
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

public class DataEntryLayoutRepositoryTest
{
	private DataEntryLayoutRepository dataEntryLayoutRepository;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		dataEntryLayoutRepository = new DataEntryLayoutRepository();
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
	public void getByWindowId()
	{
		final I_DataEntry_Tab tabRecord1 = createRecords();
		final AdWindowId windowId_1 = AdWindowId.ofRepoId(tabRecord1.getDataEntry_TargetWindow_ID());

		// invoke the method under test
		final DataEntryLayout layout = dataEntryLayoutRepository.getByWindowId(windowId_1);

		expect(layout.getTabs()).toMatchSnapshot();
	}

	private I_DataEntry_Tab createRecords()
	{
		final AdWindowId windowId_1 = AdWindowId.ofRepoId(10);

		final I_AD_Table tableRecord_1 = newInstance(I_AD_Table.class);
		tableRecord_1.setTableName("tableRecord_1_tableName");
		saveRecord(tableRecord_1);

		final I_AD_Tab tabRecord_1 = newInstance(I_AD_Tab.class);
		tabRecord_1.setAD_Window_ID(windowId_1.getRepoId());
		tabRecord_1.setAD_Table(tableRecord_1);
		saveRecord(tabRecord_1);

		// tab
		final I_DataEntry_Tab tabRecord1 = newInstance(I_DataEntry_Tab.class);
		tabRecord1.setDataEntry_TargetWindow_ID(windowId_1.getRepoId());
		tabRecord1.setName("tabRecord1_name");
		tabRecord1.setDescription("tabRecord1_description");
		tabRecord1.setTabName("tabRecord1_tabName");
		tabRecord1.setAvailableInAPI(true);
		saveRecord(tabRecord1);

		createSubtab1Records(tabRecord1);

		createSubtab2Records(tabRecord1);

		return tabRecord1;
	}

	private void createSubtab1Records(@NonNull final I_DataEntry_Tab tabRecord1)
	{
		final I_DataEntry_SubTab subtabRecord1_1 = newInstance(I_DataEntry_SubTab.class);
		subtabRecord1_1.setDataEntry_Tab(tabRecord1);
		subtabRecord1_1.setName("subtabRecord1_1_name");
		subtabRecord1_1.setDescription("subtabRecord1_1_description - seqNo20");
		subtabRecord1_1.setTabName("subtabRecord1_1_tabName");
		subtabRecord1_1.setSeqNo(20);
		saveRecord(subtabRecord1_1);

		final I_DataEntry_Section sectionRecord1_1_2 = newInstance(I_DataEntry_Section.class);
		sectionRecord1_1_2.setDataEntry_SubTab(subtabRecord1_1);
		sectionRecord1_1_2.setSeqNo(10);
		sectionRecord1_1_2.setIsInitiallyClosed(false);
		sectionRecord1_1_2.setName("sectionRecord1_1_2_name");
		sectionRecord1_1_2.setSectionName("sectionRecord1_1_2_sectionName");
		sectionRecord1_1_2.setDescription("sectionRecord1_1_2_description - seqNo10");
		sectionRecord1_1_2.setAvailableInAPI(true);
		saveRecord(sectionRecord1_1_2);

		final I_DataEntry_Line lineRecord1_1_2_1 = newInstance(I_DataEntry_Line.class);
		lineRecord1_1_2_1.setDataEntry_Section(sectionRecord1_1_2);
		lineRecord1_1_2_1.setSeqNo(10);
		saveRecord(lineRecord1_1_2_1);

		// note: the fields of tab 1 are created in reverse order (SeqNo)
		final I_DataEntry_Field fieldRecord1_1_2_1_1 = newInstance(I_DataEntry_Field.class);
		fieldRecord1_1_2_1_1.setDataEntry_Line(lineRecord1_1_2_1);
		fieldRecord1_1_2_1_1.setDataEntry_RecordType(X_DataEntry_Field.DATAENTRY_RECORDTYPE_Text);
		fieldRecord1_1_2_1_1.setName("fieldRecord1_1_2_1_1_name");
		fieldRecord1_1_2_1_1.setDescription("fieldRecord1_1_2_1_1_description - seqNo20");
		fieldRecord1_1_2_1_1.setIsMandatory(true);
		fieldRecord1_1_2_1_1.setSeqNo(20);
		fieldRecord1_1_2_1_1.setPersonalDataCategory(X_DataEntry_Field.PERSONALDATACATEGORY_Personal);
		saveRecord(fieldRecord1_1_2_1_1);

		final I_DataEntry_Field fieldRecord1_1_2_1_2 = newInstance(I_DataEntry_Field.class);
		fieldRecord1_1_2_1_2.setDataEntry_Line(lineRecord1_1_2_1);
		fieldRecord1_1_2_1_2.setDataEntry_RecordType(X_DataEntry_Field.DATAENTRY_RECORDTYPE_Number);
		fieldRecord1_1_2_1_2.setName("fieldRecord1_1_2_1_2_name");
		fieldRecord1_1_2_1_2.setDescription("fieldRecord1_1_2_1_2_description - seqNo10");
		fieldRecord1_1_2_1_2.setIsMandatory(false);
		fieldRecord1_1_2_1_2.setSeqNo(10);
		fieldRecord1_1_2_1_2.setPersonalDataCategory(X_DataEntry_Field.PERSONALDATACATEGORY_NotPersonal);
		saveRecord(fieldRecord1_1_2_1_2);
	}

	private void createSubtab2Records(@NonNull final I_DataEntry_Tab tabRecord1)
	{
		final I_DataEntry_SubTab subtabRecord1_2 = newInstance(I_DataEntry_SubTab.class);
		subtabRecord1_2.setDataEntry_Tab(tabRecord1);
		subtabRecord1_2.setName("subtabRecord1_2_name");
		subtabRecord1_2.setDescription("subtabRecord1_2_description - seqNo10");
		subtabRecord1_2.setTabName("subtabRecord1_2_tabName");
		subtabRecord1_2.setSeqNo(10);
		saveRecord(subtabRecord1_2);

		// note: the section records are created in reverse order (SeqNo)
		final I_DataEntry_Section sectionRecord1_2_1 = newInstance(I_DataEntry_Section.class);
		sectionRecord1_2_1.setDataEntry_SubTab(subtabRecord1_2);
		sectionRecord1_2_1.setSeqNo(20);
		sectionRecord1_2_1.setIsInitiallyClosed(true);
		sectionRecord1_2_1.setName("sectionRecord1_2_1_name");
		sectionRecord1_2_1.setSectionName("sectionRecord1_2_1_sectionName");
		sectionRecord1_2_1.setDescription("sectionRecord1_2_1_description - seqNo20");
		sectionRecord1_2_1.setAvailableInAPI(true);
		saveRecord(sectionRecord1_2_1);

		final I_DataEntry_Line lineRecord1_2_1_1 = newInstance(I_DataEntry_Line.class);
		lineRecord1_2_1_1.setDataEntry_Section(sectionRecord1_2_1);
		lineRecord1_2_1_1.setSeqNo(10);
		saveRecord(lineRecord1_2_1_1);

		final I_DataEntry_Field fieldRecord1_2_1_1_1 = newInstance(I_DataEntry_Field.class);
		fieldRecord1_2_1_1_1.setDataEntry_Line(lineRecord1_2_1_1);
		fieldRecord1_2_1_1_1.setDataEntry_RecordType(X_DataEntry_Field.DATAENTRY_RECORDTYPE_Date);
		fieldRecord1_2_1_1_1.setName("fieldRecord1_2_1_1_1_name");
		fieldRecord1_2_1_1_1.setDescription("fieldRecord1_2_1_1_1_description - seqNo10");
		fieldRecord1_2_1_1_1.setIsMandatory(false);
		fieldRecord1_2_1_1_1.setSeqNo(10);
		fieldRecord1_2_1_1_1.setPersonalDataCategory(X_DataEntry_Field.PERSONALDATACATEGORY_Personal);
		saveRecord(fieldRecord1_2_1_1_1);

		final I_DataEntry_Field fieldRecord1_2_1_1_2 = newInstance(I_DataEntry_Field.class);
		fieldRecord1_2_1_1_2.setDataEntry_Line(lineRecord1_2_1_1);
		fieldRecord1_2_1_1_2.setDataEntry_RecordType(X_DataEntry_Field.DATAENTRY_RECORDTYPE_List);
		fieldRecord1_2_1_1_2.setName("fieldRecord1_2_1_1_2_name");
		fieldRecord1_2_1_1_2.setDescription("fieldRecord1_2_1_1_2_description");
		fieldRecord1_2_1_1_2.setIsMandatory(false);
		fieldRecord1_2_1_1_2.setSeqNo(20);
		fieldRecord1_2_1_1_2.setPersonalDataCategory(X_DataEntry_Field.PERSONALDATACATEGORY_NotPersonal);
		saveRecord(fieldRecord1_2_1_1_2);

		final I_DataEntry_ListValue listValueRecord1_2_1_1_2_1 = newInstance(I_DataEntry_ListValue.class);
		listValueRecord1_2_1_1_2_1.setDataEntry_Field(fieldRecord1_2_1_1_2);
		listValueRecord1_2_1_1_2_1.setName("listValueRecord1_2_1_1_2_1_name");
		listValueRecord1_2_1_1_2_1.setDescription("listValueRecord1_2_1_1_2_1_description - seqNo20");
		listValueRecord1_2_1_1_2_1.setSeqNo(20);
		saveRecord(listValueRecord1_2_1_1_2_1);

		final I_DataEntry_ListValue listValueRecord1_2_1_1_2_2 = newInstance(I_DataEntry_ListValue.class);
		listValueRecord1_2_1_1_2_2.setDataEntry_Field(fieldRecord1_2_1_1_2);
		listValueRecord1_2_1_1_2_2.setName("listValueRecord1_2_1_1_2_2_name");
		listValueRecord1_2_1_1_2_2.setDescription("listValueRecord1_2_1_1_2_2_description - seqNo10");
		listValueRecord1_2_1_1_2_2.setSeqNo(10);
		saveRecord(listValueRecord1_2_1_1_2_2);

		final I_DataEntry_Field fieldRecord1_2_1_1_3 = newInstance(I_DataEntry_Field.class);
		fieldRecord1_2_1_1_3.setDataEntry_Line(lineRecord1_2_1_1);
		fieldRecord1_2_1_1_3.setDataEntry_RecordType(X_DataEntry_Field.DATAENTRY_RECORDTYPE_YesNo);
		fieldRecord1_2_1_1_3.setName("fieldRecord1_2_1_1_3_name");
		fieldRecord1_2_1_1_3.setDescription("fieldRecord1_2_1_1_3_description");
		fieldRecord1_2_1_1_3.setIsMandatory(false);
		fieldRecord1_2_1_1_3.setSeqNo(30);
		fieldRecord1_2_1_1_3.setPersonalDataCategory(X_DataEntry_Field.PERSONALDATACATEGORY_NotPersonal);
		saveRecord(fieldRecord1_2_1_1_3);

		final I_DataEntry_Field fieldRecord1_2_1_1_4 = newInstance(I_DataEntry_Field.class);
		fieldRecord1_2_1_1_4.setDataEntry_Line(lineRecord1_2_1_1);
		fieldRecord1_2_1_1_4.setDataEntry_RecordType(X_DataEntry_Field.DATAENTRY_RECORDTYPE_Text);
		fieldRecord1_2_1_1_4.setName("fieldRecord1_2_1_1_4_name");
		fieldRecord1_2_1_1_4.setDescription("fieldRecord1_2_1_1_4_description");
		fieldRecord1_2_1_1_4.setIsMandatory(true);
		fieldRecord1_2_1_1_4.setSeqNo(40);
		fieldRecord1_2_1_1_4.setPersonalDataCategory(X_DataEntry_Field.PERSONALDATACATEGORY_Personal);
		saveRecord(fieldRecord1_2_1_1_4);

		// the last field has no section so it shall be added to the "default" section
		final I_DataEntry_Field fieldRecord1_2_1_1_5 = newInstance(I_DataEntry_Field.class);
		fieldRecord1_2_1_1_5.setDataEntry_Line(lineRecord1_2_1_1);
		fieldRecord1_2_1_1_5.setDataEntry_RecordType(X_DataEntry_Field.DATAENTRY_RECORDTYPE_LongText);
		fieldRecord1_2_1_1_5.setName("fieldRecord1_2_1_1_5_name");
		fieldRecord1_2_1_1_5.setDescription("fieldRecord1_2_1_1_5_description");
		fieldRecord1_2_1_1_5.setIsMandatory(true);
		fieldRecord1_2_1_1_5.setSeqNo(50);
		fieldRecord1_2_1_1_5.setPersonalDataCategory(X_DataEntry_Field.PERSONALDATACATEGORY_SensitivePersonal);
		saveRecord(fieldRecord1_2_1_1_5);
	}
}
