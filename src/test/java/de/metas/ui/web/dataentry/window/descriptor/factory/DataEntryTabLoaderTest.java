package de.metas.ui.web.dataentry.window.descriptor.factory;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableList;

import de.metas.dataentry.DataEntryField;
import de.metas.dataentry.DataEntryField.Type;
import de.metas.dataentry.DataEntryFieldId;
import de.metas.dataentry.DataEntryGroup;
import de.metas.dataentry.DataEntryGroup.DocumentLinkColumnName;
import de.metas.dataentry.DataEntryGroupId;
import de.metas.dataentry.DataEntryListValue;
import de.metas.dataentry.DataEntryListValueId;
import de.metas.dataentry.DataEntrySubGroup;
import de.metas.dataentry.DataEntrySubGroupId;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutTab;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutDetailDescriptor;
import io.github.jsonSnapshot.SnapshotConfig;

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

	@Before
	public void init()
	{
		jsonOptions = JSONOptions.builder(null).setAD_LanguageIfNotEmpty("en_US").build();
	}

	@BeforeClass
	public static void beforeAll()
	{
		start(new SnapshotConfig()
		{
			@Override
			public String getFilePath()
			{
				return "src/test/resource/";
			}
		});
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
		final List<DocumentLayoutDetailDescriptor> descriptors = new DataEntryTabLoader(5, WindowId.of(5))
				.createLayoutDescriptors(ImmutableList.of(dataEntryGroup));

	expect(descriptors).toMatchSnapshot();
	}

	@Test
	public void createLayoutDescriptors_verify_JSONDocumentLayoutTab() throws JsonProcessingException
	{
		final DataEntryGroup dataEntryGroup = createSimpleDataEntryGroup();

		// invoke the method under test
		final List<DocumentLayoutDetailDescriptor> descriptors = new DataEntryTabLoader(5, WindowId.of(5))
				.createLayoutDescriptors(ImmutableList.of(dataEntryGroup));

		final List<JSONDocumentLayoutTab> jsonTabs = JSONDocumentLayoutTab.ofList(descriptors, jsonOptions);
		expect(jsonTabs).toMatchSnapshot();
	}

	@Test
	public void createGroupEntityDescriptors_verify_DocumentEntityDescriptor()
	{
		final DataEntryGroup dataEntryGroup = createSimpleDataEntryGroup();

		// invoke the method under test
		final List<DocumentEntityDescriptor> descriptors = new DataEntryTabLoader(5, WindowId.of(5))
				.createGroupEntityDescriptors(ImmutableList.of(dataEntryGroup));

		expect(descriptors).toMatchSnapshot();
	}

	private DataEntryGroup createSimpleDataEntryGroup()
	{
		final DataEntryFieldId dataEntryListFieldId = DataEntryFieldId.ofRepoId(35);

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
						.dataEntryField(DataEntryField.builder()
								.id(DataEntryFieldId.ofRepoId(31))
								.caption(ImmutableTranslatableString.constant("stringField1_caption"))
								.description(ImmutableTranslatableString.constant("stringField1_description"))
								.type(Type.STRING)
								.build())
						.dataEntryField(DataEntryField.builder()
								.id(DataEntryFieldId.ofRepoId(32))
								.caption(ImmutableTranslatableString.constant("numberField1_caption"))
								.description(ImmutableTranslatableString.constant("numberField1_description"))
								.type(Type.NUMBER)
								.build())
						.dataEntryField(DataEntryField.builder()
								.id(DataEntryFieldId.ofRepoId(33))
								.caption(ImmutableTranslatableString.constant("dateField1_caption"))
								.description(ImmutableTranslatableString.constant("dateField1_description"))
								.type(Type.DATE)
								.build())
						.dataEntryField(DataEntryField.builder()
								.id(DataEntryFieldId.ofRepoId(34))
								.caption(ImmutableTranslatableString.constant("yesNoField1_caption"))
								.description(ImmutableTranslatableString.constant("yesNoField1_description"))
								.type(Type.YESNO)
								.build())

						.dataEntryField(DataEntryField.builder()
								.id(dataEntryListFieldId)
								.caption(ImmutableTranslatableString.constant("listField1_caption"))
								.description(ImmutableTranslatableString.constant("listField1_description"))
								.type(Type.LIST)
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
								.build())
						.build())
				.build();
		return dataEntryGroup;
	}

}
