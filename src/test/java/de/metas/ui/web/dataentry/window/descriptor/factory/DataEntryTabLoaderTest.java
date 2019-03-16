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
import de.metas.dataentry.DataEntryGroupId;
import de.metas.dataentry.DataEntryListValueId;
import de.metas.dataentry.DataEntrySectionId;
import de.metas.dataentry.DataEntrySubGroupId;
import de.metas.dataentry.FieldType;
import de.metas.dataentry.data.DataEntryRecordRepository;
import de.metas.dataentry.data.json.JSONDataEntryRecordMapper;
import de.metas.dataentry.layout.DataEntryField;
import de.metas.dataentry.layout.DataEntryGroup;
import de.metas.dataentry.layout.DataEntryGroup.DocumentLinkColumnName;
import de.metas.dataentry.layout.DataEntryLine;
import de.metas.dataentry.layout.DataEntryListValue;
import de.metas.dataentry.layout.DataEntrySection;
import de.metas.dataentry.layout.DataEntrySubGroup;
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

		final DataEntrySubGroupBindingDescriptorBuilder //
		dataEntrySubGroupBindingDescriptorBuilder = new DataEntrySubGroupBindingDescriptorBuilder(
				dataEntryRecordRepository,
				dataEntryWebuiTools);

		return DataEntryTabLoader
				.builder()
				.windowId(windowId)
				.adWindowId(windowId.toAdWindowIdOrNull())
				.dataEntrySubGroupBindingDescriptorBuilder(dataEntrySubGroupBindingDescriptorBuilder)
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
		final DataEntryGroup dataEntryGroup = createSimpleDataEntryGroup();

		// invoke the method under test
		final List<DocumentLayoutDetailDescriptor> descriptors = dataEntryTabLoader
				.createLayoutDescriptors(ImmutableList.of(dataEntryGroup));

		expect(descriptors).toMatchSnapshot();
	}

	@Test
	public void createLayoutDescriptors_verify_JSONDocumentLayoutTab() throws JsonProcessingException
	{
		final DataEntryGroup dataEntryGroup = createSimpleDataEntryGroup();

		// invoke the method under test
		final List<DocumentLayoutDetailDescriptor> descriptors = dataEntryTabLoader
				.createLayoutDescriptors(ImmutableList.of(dataEntryGroup));

		final List<JSONDocumentLayoutTab> jsonTabs = JSONDocumentLayoutTab.ofList(descriptors, jsonOptions);
		expect(jsonTabs).toMatchSnapshot();
	}

	@Test
	public void createGroupEntityDescriptors_verify_DocumentEntityDescriptor()
	{
		final DataEntryGroup dataEntryGroup = createSimpleDataEntryGroup();

		// invoke the method under test
		final List<DocumentEntityDescriptor> descriptors = dataEntryTabLoader
				.createGroupEntityDescriptors(ImmutableList.of(dataEntryGroup));

		expect(descriptors).toMatchSnapshot();
	}

	public static DataEntryGroup createSimpleDataEntryGroup()
	{
		return DataEntryGroup.builder()
				.id(DataEntryGroupId.ofRepoId(1))
				.documentLinkColumnName(DocumentLinkColumnName.of("documentLinkColumnName"))
				.internalName("dataEntryGroup_internalName")
				.caption(ImmutableTranslatableString.constant("dataEntryGroup_caption"))
				.description(ImmutableTranslatableString.constant("dataEntryGroup_description"))

				.dataEntrySubGroup(createSubGroup(1/* subgroupNo */))
				.dataEntrySubGroup(createSubGroup(2/* subgroupNo */))

				.build();
	}

	private static DataEntrySubGroup createSubGroup(final int subgroupNo)
	{

		final String idPrefix = "1" + subgroupNo;

		final DataEntryFieldId dataEntryListFieldId = DataEntryFieldId.ofRepoId(parseInt(idPrefix + "23"));

		return DataEntrySubGroup.builder()
				.id(DataEntrySubGroupId.ofRepoId(parseInt(idPrefix)))
				.internalName("dataEntrySubGroup_internalName")
				.caption(ImmutableTranslatableString.constant("dataEntrySubGroup" + subgroupNo + "_caption"))
				.description(ImmutableTranslatableString.constant("dataEntrySubGroup" + subgroupNo + "_description"))

				.dataEntrySection(DataEntrySection.builder()
						.id(DataEntrySectionId.ofRepoId(parseInt(idPrefix + "1")))
						.caption(ImmutableTranslatableString.constant("dataEntrySection" + subgroupNo + "1 - section with 2 lines. "
								+ "The 1st line has 1 field, the 2nd line has 2 fields. "
								+ "Expecation: 1 elementGroup with columnCount=2; 4 elementLines, the 2nd one being empty"))
						.description(ImmutableTranslatableString.constant("dataEntrySection1_description"))
						.internalName("dataEntrySection1_internalName")
						.initallyClosed(false)
						.dataEntryLine(DataEntryLine.builder()
								.dataEntryField(DataEntryField.builder()
										.id(DataEntryFieldId.ofRepoId(parseInt(idPrefix + "11")))
										.caption(ImmutableTranslatableString.constant("textField" + subgroupNo + "1_caption"))
										.description(ImmutableTranslatableString.constant("textField" + subgroupNo + "1_description"))
										.type(FieldType.TEXT)
										.build())
								.build())
						.dataEntryLine(DataEntryLine.builder()
								.dataEntryField(DataEntryField.builder()
										.id(DataEntryFieldId.ofRepoId(parseInt(idPrefix + "12")))
										.caption(ImmutableTranslatableString.constant("longTextField" + subgroupNo + "1_caption"))
										.description(ImmutableTranslatableString.constant("longTextField1_description"))
										.type(FieldType.LONG_TEXT)
										.build())

								.dataEntryField(DataEntryField.builder()
										.id(DataEntryFieldId.ofRepoId(parseInt(idPrefix + "13")))
										.caption(ImmutableTranslatableString.constant("numberField" + subgroupNo + "1_caption"))
										.description(ImmutableTranslatableString.constant("numberField" + subgroupNo + "1_description"))
										.type(FieldType.NUMBER)
										.build())
								.build())
						.build())

				.dataEntrySection(DataEntrySection.builder()
						.id(DataEntrySectionId.ofRepoId(parseInt(idPrefix + "2")))
						.caption(ImmutableTranslatableString.constant("dataEntrySection" + subgroupNo + "2_caption"))
						.description(ImmutableTranslatableString.constant("dataEntrySection" + subgroupNo + "2 - section with one line and 3 fields"))
						.internalName("dataEntrySection2_internalName")
						.initallyClosed(true)
						.dataEntryLine(DataEntryLine.builder()
								.dataEntryField(DataEntryField.builder()
										.id(DataEntryFieldId.ofRepoId(parseInt(idPrefix + "21")))
										.caption(ImmutableTranslatableString.constant("dateField" + subgroupNo + "1_caption"))
										.description(ImmutableTranslatableString.constant("dateField" + subgroupNo + "1_description"))
										.type(FieldType.DATE)
										.build())
								.dataEntryField(DataEntryField.builder()
										.id(DataEntryFieldId.ofRepoId(parseInt(idPrefix + "22")))
										.caption(ImmutableTranslatableString.constant("yesNoField" + subgroupNo + "1_caption"))
										.description(ImmutableTranslatableString.constant("yesNoField" + subgroupNo + "1_description"))
										.type(FieldType.YESNO)
										.build())
								.dataEntryField(DataEntryField.builder()
										.id(dataEntryListFieldId)
										.caption(ImmutableTranslatableString.constant("listField" + subgroupNo + "1_caption"))
										.description(ImmutableTranslatableString.constant("listField" + subgroupNo + "1_description"))
										.type(FieldType.LIST)
										.listValue(new DataEntryListValue(
												DataEntryListValueId.ofRepoId(parseInt(idPrefix + "231")),
												dataEntryListFieldId,
												ImmutableTranslatableString.constant("listValue" + subgroupNo + "1_name"),
												ImmutableTranslatableString.constant("listValue" + subgroupNo + "1_description")))
										.listValue(new DataEntryListValue(
												DataEntryListValueId.ofRepoId(parseInt(idPrefix + "232")),
												dataEntryListFieldId,
												ImmutableTranslatableString.constant("listValue" + subgroupNo + "2_name"),
												ImmutableTranslatableString.constant("listValue" + subgroupNo + "2_description")))
										.build())
								.build())
						.build())
				.build();
	}

}
