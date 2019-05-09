package de.metas.ui.web.dataentry.window.descriptor.factory;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static java.lang.Integer.parseInt;

import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.user.UserRepository;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableList;

import de.metas.dataentry.DataEntryFieldId;
import de.metas.dataentry.DataEntryTabId;
import de.metas.dataentry.DataEntryListValueId;
import de.metas.dataentry.DataEntrySectionId;
import de.metas.dataentry.DataEntrySubTabId;
import de.metas.dataentry.FieldType;
import de.metas.dataentry.data.DataEntryRecordRepository;
import de.metas.dataentry.data.json.JSONDataEntryRecordMapper;
import de.metas.dataentry.layout.DataEntryField;
import de.metas.dataentry.layout.DataEntryTab;
import de.metas.dataentry.layout.DataEntryTab.DocumentLinkColumnName;
import de.metas.dataentry.layout.DataEntryLine;
import de.metas.dataentry.layout.DataEntryListValue;
import de.metas.dataentry.layout.DataEntrySection;
import de.metas.dataentry.layout.DataEntrySubTab;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutTab;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutDetailDescriptor;

/*
 * #%L
 * metasfresh-webui-api
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

public class DataEntryTabLoaderTest
{

	private JSONOptions jsonOptions;
	private DataEntryTabLoader dataEntryTabLoader;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init(); // ..because at one point in the code under test, we use IMsgBL

		jsonOptions = JSONOptions.builder(null/* userSession */).setAD_LanguageIfNotEmpty("en_US").build();

		dataEntryTabLoader = createDataEntryTabLoader();
	}

	private static DataEntryTabLoader createDataEntryTabLoader()
	{
		final WindowId windowId = WindowId.of(5);

		final DataEntryWebuiTools dataEntryWebuiTools = new DataEntryWebuiTools(new UserRepository());
		final JSONDataEntryRecordMapper jsonDataEntryRecordMapper = new JSONDataEntryRecordMapper();
		final DataEntryRecordRepository dataEntryRecordRepository = new DataEntryRecordRepository(jsonDataEntryRecordMapper);

		final DataEntrySubTabBindingDescriptorBuilder //
		dataEntrySubTabBindingDescriptorBuilder = new DataEntrySubTabBindingDescriptorBuilder(
				dataEntryRecordRepository,
				dataEntryWebuiTools);

		return DataEntryTabLoader
				.builder()
				.windowId(windowId)
				.adWindowId(windowId.toAdWindowIdOrNull())
				.dataEntrySubTabBindingDescriptorBuilder(dataEntrySubTabBindingDescriptorBuilder)
				.build();
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
	public void createLayoutDescriptors_verify_DocumentLayoutDetailDescriptor() throws JsonProcessingException
	{
		final DataEntryTab dataEntryTab = createSimpleDataEntryTab();

		// invoke the method under test
		final List<DocumentLayoutDetailDescriptor> descriptors = dataEntryTabLoader
				.createLayoutDescriptors(ImmutableList.of(dataEntryTab));

		expect(descriptors).toMatchSnapshot();
	}

	@Test
	public void createLayoutDescriptors_verify_JSONDocumentLayoutTab() throws JsonProcessingException
	{
		final DataEntryTab dataEntryTab = createSimpleDataEntryTab();

		// invoke the method under test
		final List<DocumentLayoutDetailDescriptor> descriptors = dataEntryTabLoader
				.createLayoutDescriptors(ImmutableList.of(dataEntryTab));

		final List<JSONDocumentLayoutTab> jsonTabs = JSONDocumentLayoutTab.ofList(descriptors, jsonOptions);
		expect(jsonTabs).toMatchSnapshot();
	}

	@Test
	public void createTabEntityDescriptors_verify_DocumentEntityDescriptor()
	{
		final DataEntryTab dataEntryTab = createSimpleDataEntryTab();

		// invoke the method under test
		final List<DocumentEntityDescriptor> descriptors = dataEntryTabLoader
				.createTabEntityDescriptors(ImmutableList.of(dataEntryTab));

		expect(descriptors).toMatchSnapshot();
	}

	public static DataEntryTab createSimpleDataEntryTab()
	{
		return DataEntryTab.builder()
				.id(DataEntryTabId.ofRepoId(1))
				.documentLinkColumnName(DocumentLinkColumnName.of("documentLinkColumnName"))
				.internalName("dataEntryTab_internalName")
				.caption(ImmutableTranslatableString.constant("dataEntryTab_caption"))
				.description(ImmutableTranslatableString.constant("dataEntryTab_description"))

				.dataEntrySubTab(createSubTab(1/* subTabNo */))
				.dataEntrySubTab(createSubTab(2/* subTabNo */))

				.build();
	}

	private static DataEntrySubTab createSubTab(final int subTabNo)
	{

		final String idPrefix = "1" + subTabNo;

		final DataEntryFieldId dataEntryListFieldId = DataEntryFieldId.ofRepoId(parseInt(idPrefix + "23"));

		return DataEntrySubTab.builder()
				.id(DataEntrySubTabId.ofRepoId(parseInt(idPrefix)))
				.internalName("dataEntrySubTab_internalName")
				.caption(ImmutableTranslatableString.constant("dataEntrySubTab" + subTabNo + "_caption"))
				.description(ImmutableTranslatableString.constant("dataEntrySubTab" + subTabNo + "_description"))

				.dataEntrySection(DataEntrySection.builder()
						.id(DataEntrySectionId.ofRepoId(parseInt(idPrefix + "1")))
						.caption(ImmutableTranslatableString.constant("dataEntrySection" + subTabNo + "1 - section with 2 lines. "
								+ "The 1st line has 1 field, the 2nd line has 2 fields. "
								+ "Expecation: 1 elementTab with columnCount=2; 4 elementLines, the 2nd one being empty"))
						.description(ImmutableTranslatableString.constant("dataEntrySection1_description"))
						.internalName("dataEntrySection1_internalName")
						.initiallyClosed(false)
						.dataEntryLine(DataEntryLine.builder()
								.dataEntryField(DataEntryField.builder()
										.id(DataEntryFieldId.ofRepoId(parseInt(idPrefix + "11")))
										.caption(ImmutableTranslatableString.constant("textField" + subTabNo + "1_caption"))
										.description(ImmutableTranslatableString.constant("textField" + subTabNo + "1_description"))
										.type(FieldType.TEXT)
										.build())
								.build())
						.dataEntryLine(DataEntryLine.builder()
								.dataEntryField(DataEntryField.builder()
										.id(DataEntryFieldId.ofRepoId(parseInt(idPrefix + "12")))
										.caption(ImmutableTranslatableString.constant("longTextField" + subTabNo + "1_caption"))
										.description(ImmutableTranslatableString.constant("longTextField1_description"))
										.type(FieldType.LONG_TEXT)
										.build())

								.dataEntryField(DataEntryField.builder()
										.id(DataEntryFieldId.ofRepoId(parseInt(idPrefix + "13")))
										.caption(ImmutableTranslatableString.constant("numberField" + subTabNo + "1_caption"))
										.description(ImmutableTranslatableString.constant("numberField" + subTabNo + "1_description"))
										.type(FieldType.NUMBER)
										.build())
								.build())
						.build())

				.dataEntrySection(DataEntrySection.builder()
						.id(DataEntrySectionId.ofRepoId(parseInt(idPrefix + "2")))
						.caption(ImmutableTranslatableString.constant("dataEntrySection" + subTabNo + "2_caption"))
						.description(ImmutableTranslatableString.constant("dataEntrySection" + subTabNo + "2 - section with one line and 3 fields"))
						.internalName("dataEntrySection2_internalName")
						.initiallyClosed(true)
						.dataEntryLine(DataEntryLine.builder()
								.dataEntryField(DataEntryField.builder()
										.id(DataEntryFieldId.ofRepoId(parseInt(idPrefix + "21")))
										.caption(ImmutableTranslatableString.constant("dateField" + subTabNo + "1_caption"))
										.description(ImmutableTranslatableString.constant("dateField" + subTabNo + "1_description"))
										.type(FieldType.DATE)
										.build())
								.dataEntryField(DataEntryField.builder()
										.id(DataEntryFieldId.ofRepoId(parseInt(idPrefix + "22")))
										.caption(ImmutableTranslatableString.constant("yesNoField" + subTabNo + "1_caption"))
										.description(ImmutableTranslatableString.constant("yesNoField" + subTabNo + "1_description"))
										.type(FieldType.YESNO)
										.build())
								.dataEntryField(DataEntryField.builder()
										.id(dataEntryListFieldId)
										.caption(ImmutableTranslatableString.constant("listField" + subTabNo + "1_caption"))
										.description(ImmutableTranslatableString.constant("listField" + subTabNo + "1_description"))
										.type(FieldType.LIST)
										.listValue(new DataEntryListValue(
												DataEntryListValueId.ofRepoId(parseInt(idPrefix + "231")),
												dataEntryListFieldId,
												ImmutableTranslatableString.constant("listValue" + subTabNo + "1_name"),
												ImmutableTranslatableString.constant("listValue" + subTabNo + "1_description")))
										.listValue(new DataEntryListValue(
												DataEntryListValueId.ofRepoId(parseInt(idPrefix + "232")),
												dataEntryListFieldId,
												ImmutableTranslatableString.constant("listValue" + subTabNo + "2_name"),
												ImmutableTranslatableString.constant("listValue" + subTabNo + "2_description")))
										.build())
								.build())
						.build())
				.build();
	}

}
