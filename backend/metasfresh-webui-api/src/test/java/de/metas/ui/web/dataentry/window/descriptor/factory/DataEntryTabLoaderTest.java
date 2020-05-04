package de.metas.ui.web.dataentry.window.descriptor.factory;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static java.lang.Integer.parseInt;

import java.util.List;

import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableList;

import de.metas.dataentry.DataEntryFieldId;
import de.metas.dataentry.DataEntryListValueId;
import de.metas.dataentry.DataEntrySectionId;
import de.metas.dataentry.DataEntrySubTabId;
import de.metas.dataentry.DataEntryTabId;
import de.metas.dataentry.FieldType;
import de.metas.dataentry.data.DataEntryRecordRepository;
import de.metas.dataentry.data.json.JSONDataEntryRecordMapper;
import de.metas.dataentry.layout.DataEntryField;
import de.metas.dataentry.layout.DataEntryLayout;
import de.metas.dataentry.layout.DataEntryLine;
import de.metas.dataentry.layout.DataEntryListValue;
import de.metas.dataentry.layout.DataEntrySection;
import de.metas.dataentry.layout.DataEntrySubTab;
import de.metas.dataentry.layout.DataEntryTab;
import de.metas.dataentry.layout.DataEntryTab.DocumentLinkColumnName;
import de.metas.i18n.TranslatableStrings;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvidersService;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutOptions;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutTab;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutDetailDescriptor;
import de.metas.user.UserRepository;

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

	private JSONDocumentLayoutOptions jsonLayoutOptions;
	private DataEntryTabLoader dataEntryTabLoader;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init(); // ..because at one point in the code under test, we use IMsgBL

		jsonLayoutOptions = JSONDocumentLayoutOptions.ofAdLanguage("en_US");

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

		return DataEntryTabLoader.builder()
				.filterDescriptorsProvidersService(new DocumentFilterDescriptorsProvidersService(ImmutableList.of()))
				.windowId(windowId)
				.adWindowId(windowId.toAdWindowIdOrNull())
				.dataEntrySubTabBindingDescriptorBuilder(dataEntrySubTabBindingDescriptorBuilder)
				.build();
	}

	@BeforeAll
	public static void beforeAll()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG);
	}

	@AfterAll
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
				.createLayoutDescriptors(DataEntryLayout.builder()
						.windowId(AdWindowId.ofRepoId(1))
						.mainTableId(AdTableId.ofRepoId(1))
						.tab(dataEntryTab)
						.build());

		expect(descriptors).toMatchSnapshot();
	}

	@Test
	public void createLayoutDescriptors_verify_JSONDocumentLayoutTab() throws JsonProcessingException
	{
		final DataEntryTab dataEntryTab = createSimpleDataEntryTab();

		// invoke the method under test
		final List<DocumentLayoutDetailDescriptor> descriptors = dataEntryTabLoader
				.createLayoutDescriptors(DataEntryLayout.builder()
						.windowId(AdWindowId.ofRepoId(1))
						.mainTableId(AdTableId.ofRepoId(1))
						.tab(dataEntryTab)
						.build());

		final List<JSONDocumentLayoutTab> jsonTabs = JSONDocumentLayoutTab.ofList(descriptors, jsonLayoutOptions);
		expect(jsonTabs).toMatchSnapshot();
	}

	@Test
	public void createTabEntityDescriptors_verify_DocumentEntityDescriptor()
	{
		final DataEntryTab dataEntryTab = createSimpleDataEntryTab();

		// invoke the method under test
		final List<DocumentEntityDescriptor> descriptors = dataEntryTabLoader
				.createTabEntityDescriptors(DataEntryLayout.builder()
						.windowId(AdWindowId.ofRepoId(1))
						.mainTableId(AdTableId.ofRepoId(1))
						.tab(dataEntryTab)
						.build());

		expect(descriptors).toMatchSnapshot();
	}

	public static DataEntryTab createSimpleDataEntryTab()
	{
		return DataEntryTab.builder()
				.id(DataEntryTabId.ofRepoId(1))
				.documentLinkColumnName(DocumentLinkColumnName.of("documentLinkColumnName"))
				.internalName("dataEntryTab_internalName")
				.caption(TranslatableStrings.constant("dataEntryTab_caption"))
				.description(TranslatableStrings.constant("dataEntryTab_description"))

				.subTab(createSubTab(1/* subTabNo */))
				.subTab(createSubTab(2/* subTabNo */))

				.build();
	}

	private static DataEntrySubTab createSubTab(final int subTabNo)
	{

		final String idPrefix = "1" + subTabNo;

		final DataEntryFieldId dataEntryListFieldId = DataEntryFieldId.ofRepoId(parseInt(idPrefix + "23"));

		return DataEntrySubTab.builder()
				.id(DataEntrySubTabId.ofRepoId(parseInt(idPrefix)))
				.internalName("dataEntrySubTab_internalName")
				.caption(TranslatableStrings.constant("dataEntrySubTab" + subTabNo + "_caption"))
				.description(TranslatableStrings.constant("dataEntrySubTab" + subTabNo + "_description"))

				.section(DataEntrySection.builder()
						.id(DataEntrySectionId.ofRepoId(parseInt(idPrefix + "1")))
						.caption(TranslatableStrings.constant("dataEntrySection" + subTabNo + "1 - section with 2 lines. "
								+ "The 1st line has 1 field, the 2nd line has 2 fields. "
								+ "Expecation: 1 elementTab with columnCount=2; 4 elementLines, the 2nd one being empty"))
						.description(TranslatableStrings.constant("dataEntrySection1_description"))
						.internalName("dataEntrySection1_internalName")
						.initiallyClosed(false)
						.line(DataEntryLine.builder()
								.field(DataEntryField.builder()
										.id(DataEntryFieldId.ofRepoId(parseInt(idPrefix + "11")))
										.caption(TranslatableStrings.constant("textField" + subTabNo + "1_caption"))
										.description(TranslatableStrings.constant("textField" + subTabNo + "1_description"))
										.type(FieldType.TEXT)
										.build())
								.build())
						.line(DataEntryLine.builder()
								.field(DataEntryField.builder()
										.id(DataEntryFieldId.ofRepoId(parseInt(idPrefix + "12")))
										.caption(TranslatableStrings.constant("longTextField" + subTabNo + "1_caption"))
										.description(TranslatableStrings.constant("longTextField1_description"))
										.type(FieldType.LONG_TEXT)
										.build())

								.field(DataEntryField.builder()
										.id(DataEntryFieldId.ofRepoId(parseInt(idPrefix + "13")))
										.caption(TranslatableStrings.constant("numberField" + subTabNo + "1_caption"))
										.description(TranslatableStrings.constant("numberField" + subTabNo + "1_description"))
										.type(FieldType.NUMBER)
										.build())
								.build())
						.build())

				.section(DataEntrySection.builder()
						.id(DataEntrySectionId.ofRepoId(parseInt(idPrefix + "2")))
						.caption(TranslatableStrings.constant("dataEntrySection" + subTabNo + "2_caption"))
						.description(TranslatableStrings.constant("dataEntrySection" + subTabNo + "2 - section with one line and 3 fields"))
						.internalName("dataEntrySection2_internalName")
						.initiallyClosed(true)
						.line(DataEntryLine.builder()
								.field(DataEntryField.builder()
										.id(DataEntryFieldId.ofRepoId(parseInt(idPrefix + "21")))
										.caption(TranslatableStrings.constant("dateField" + subTabNo + "1_caption"))
										.description(TranslatableStrings.constant("dateField" + subTabNo + "1_description"))
										.type(FieldType.DATE)
										.build())
								.field(DataEntryField.builder()
										.id(DataEntryFieldId.ofRepoId(parseInt(idPrefix + "22")))
										.caption(TranslatableStrings.constant("yesNoField" + subTabNo + "1_caption"))
										.description(TranslatableStrings.constant("yesNoField" + subTabNo + "1_description"))
										.type(FieldType.YESNO)
										.build())
								.field(DataEntryField.builder()
										.id(dataEntryListFieldId)
										.caption(TranslatableStrings.constant("listField" + subTabNo + "1_caption"))
										.description(TranslatableStrings.constant("listField" + subTabNo + "1_description"))
										.type(FieldType.LIST)
										.listValue(DataEntryListValue.builder()
												.id(DataEntryListValueId.ofRepoId(parseInt(idPrefix + "231")))
												.name(TranslatableStrings.constant("listValue" + subTabNo + "1_name"))
												.description(TranslatableStrings.constant("listValue" + subTabNo + "1_description"))
												.build())

										.listValue(DataEntryListValue.builder()
												.id(DataEntryListValueId.ofRepoId(parseInt(idPrefix + "232")))
												.name(TranslatableStrings.constant("listValue" + subTabNo + "2_name"))
												.description(TranslatableStrings.constant("listValue" + subTabNo + "2_description"))
												.build())
										.build())
								.build())
						.build())
				.build();
	}

}
