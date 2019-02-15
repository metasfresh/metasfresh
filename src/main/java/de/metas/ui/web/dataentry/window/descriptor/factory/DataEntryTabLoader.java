package de.metas.ui.web.dataentry.window.descriptor.factory;

import static org.assertj.core.api.Assertions.fail;

import java.util.List;

import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.expression.api.ConstantLogicExpression;
import org.compiere.Adempiere;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

import de.metas.dataentry.FieldType;
import de.metas.dataentry.layout.DataEntryField;
import de.metas.dataentry.layout.DataEntryGroup;
import de.metas.dataentry.layout.DataEntryGroup.DocumentLinkColumnName;
import de.metas.dataentry.layout.DataEntryGroupRepository;
import de.metas.dataentry.layout.DataEntrySubGroup;
import de.metas.dataentry.model.I_DataEntry_SubGroup;
import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.DocumentLayoutColumnDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutDetailDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementGroupDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementLineDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutSectionDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutSingleRow;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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

@Value
public class DataEntryTabLoader
{
	AdWindowId adWindowId;

	WindowId windowId;

	DataEntrySubGroupBindingDescriptorBuilder dataEntrySubGroupBindingDescriptorBuilder;

	@Builder
	private DataEntryTabLoader(
			@NonNull final AdWindowId adWindowId,
			@NonNull final WindowId windowId,
			@NonNull final DataEntrySubGroupBindingDescriptorBuilder dataEntrySubGroupBindingDescriptorBuilder)
	{
		this.adWindowId = adWindowId;
		this.windowId = windowId;
		this.dataEntrySubGroupBindingDescriptorBuilder = dataEntrySubGroupBindingDescriptorBuilder;
	}

	public List<DocumentLayoutDetailDescriptor> loadDocumentLayout()
	{
		final DataEntryGroupRepository dataEntryRepository = Adempiere.getBean(DataEntryGroupRepository.class);

		final List<DataEntryGroup> dataEntryGroups = dataEntryRepository.getByWindowId(adWindowId);

		return createLayoutDescriptors(dataEntryGroups);
	}

	@VisibleForTesting
	List<DocumentLayoutDetailDescriptor> createLayoutDescriptors(@NonNull final List<DataEntryGroup> dataEntryGroups)
	{
		final ImmutableList.Builder<DocumentLayoutDetailDescriptor> result = ImmutableList.builder();
		for (final DataEntryGroup dataEntryGroup : dataEntryGroups)
		{
			result.addAll(createGroupLayoutDescriptors(windowId, dataEntryGroup));
		}
		return result.build();
	}

	private static ImmutableList<DocumentLayoutDetailDescriptor> createGroupLayoutDescriptors(
			@NonNull final WindowId windowId,
			@NonNull final DataEntryGroup dataEntryGroup)
	{
		final ImmutableList.Builder<DocumentLayoutDetailDescriptor> subGroupLayoutDescriptors = ImmutableList.builder();
		for (final DataEntrySubGroup dataEntrySubGroup : dataEntryGroup.getDataEntrySubGroups())
		{
			subGroupLayoutDescriptors.add(createSubGroupLayoutDescriptor(windowId, dataEntrySubGroup));
		}

		if (dataEntryGroup.isAnonymous())
		{
			return subGroupLayoutDescriptors.build();
		}

		final DocumentLayoutDetailDescriptor.Builder builder = DocumentLayoutDetailDescriptor
				.builder(windowId, createDetailIdFor(dataEntryGroup))
				.caption(dataEntryGroup.getCaption())
				.description(dataEntryGroup.getDescription())
				.internalName(dataEntryGroup.getInternalName())
				.queryOnActivate(true)
				.supportQuickInput(false)
				.addAllSubTabLayouts(subGroupLayoutDescriptors.build());

		return ImmutableList.of(builder.build());
	}

	private static DocumentLayoutDetailDescriptor createSubGroupLayoutDescriptor(
			@NonNull final WindowId windowId,
			@NonNull final DataEntrySubGroup dataEntrySubGroup)
	{
		final DetailId subgroupDetailId = createDetailIdFor(dataEntrySubGroup);

		final DocumentLayoutDetailDescriptor.Builder subGroupDescriptor = DocumentLayoutDetailDescriptor
				.builder(windowId, subgroupDetailId)
				.caption(dataEntrySubGroup.getCaption())
				.description(dataEntrySubGroup.getDescription())
				.internalName(dataEntrySubGroup.getInternalName())
				.queryOnActivate(true)
				.supportQuickInput(false);

		final DocumentLayoutColumnDescriptor.Builder column = DocumentLayoutColumnDescriptor
				.builder();
		final DocumentLayoutSectionDescriptor.Builder section = DocumentLayoutSectionDescriptor
				.builder()
				.addColumn(column);
		final DocumentLayoutSingleRow.Builder singleRowLayout = DocumentLayoutSingleRow
				.builder()
				.setWindowId(windowId)
				.addSections(ImmutableList.of(section));

		final List<DataEntryField> fields = dataEntrySubGroup.getDataEntryFields();
		for (final DataEntryField field : fields)
		{
			// note that each element builder can be used/"consumed" only ones
			final DocumentLayoutElementDescriptor.Builder elementForSingleRowLayout = createFieldElementDescriptor(field);
			final DocumentLayoutElementLineDescriptor.Builder elementLine = DocumentLayoutElementLineDescriptor
					.builder()
					.addElement(elementForSingleRowLayout);
			final DocumentLayoutElementGroupDescriptor.Builder elementGroup = DocumentLayoutElementGroupDescriptor
					.builder()
					.addElementLine(elementLine);
			column.addElementGroup(elementGroup);
		}
		subGroupDescriptor.singleRowDetailLayout(true);
		subGroupDescriptor.singleRowLayout(singleRowLayout);

		return subGroupDescriptor.build();
	}

	private static DocumentLayoutElementDescriptor.Builder createFieldElementDescriptor(final DataEntryField field)
	{
		DocumentLayoutElementFieldDescriptor.Builder fieldBuilder = DocumentLayoutElementFieldDescriptor
				.builder(createFieldNameFor(field))
				.setEmptyText(ITranslatableString.empty());

		final DocumentLayoutElementDescriptor.Builder element = DocumentLayoutElementDescriptor
				.builder()
				.setCaption(field.getCaption())
				.setDescription(field.getDescription())
				.setViewEditorRenderMode(ViewEditorRenderMode.ALWAYS)
				.setWidgetType(ofFieldType(field.getType()))
				.addField(fieldBuilder);
		return element;
	}

	private static String createFieldNameFor(@NonNull final DataEntryField field)
	{
		return Integer.toString(field.getId().getRepoId());
	}

	private static DocumentFieldWidgetType ofFieldType(@NonNull final FieldType fieldType)
	{
		switch (fieldType)
		{
			case DATE:
				return DocumentFieldWidgetType.Date;
			case LIST:
				return DocumentFieldWidgetType.List;
			case NUMBER:
				return DocumentFieldWidgetType.Number;
			case STRING:
				return DocumentFieldWidgetType.Text;
			case YESNO:
				return DocumentFieldWidgetType.YesNo;
			default:
				fail("Unexpected DataEntryField.Type={}", fieldType);
				return null;
		}
	}

	public List<DocumentEntityDescriptor> loadDocumentEntity()
	{
		final DataEntryGroupRepository dataEntryRepository = Adempiere.getBean(DataEntryGroupRepository.class);

		final List<DataEntryGroup> dataEntryGroups = dataEntryRepository.getByWindowId(adWindowId);

		return createGroupEntityDescriptors(dataEntryGroups);
	}

	@VisibleForTesting
	List<DocumentEntityDescriptor> createGroupEntityDescriptors(@NonNull final List<DataEntryGroup> dataEntryGroups)
	{
		final ImmutableList.Builder<DocumentEntityDescriptor> result = ImmutableList.builder();
		for (final DataEntryGroup dataEntryGroup : dataEntryGroups)
		{
			result.addAll(createGroupEntityDescriptors(dataEntryGroup));
		}
		return result.build();
	}

	private ImmutableList<DocumentEntityDescriptor> createGroupEntityDescriptors(@NonNull final DataEntryGroup dataEntryGroup)
	{
		final ImmutableList.Builder<DocumentEntityDescriptor> subGroupEntityDescriptors = ImmutableList.builder();
		for (final DataEntrySubGroup dataEntrySubGroup : dataEntryGroup.getDataEntrySubGroups())
		{
			subGroupEntityDescriptors.add(createSubGroupEntityDescriptor(dataEntrySubGroup, dataEntryGroup.getDocumentLinkColumnName()));
		}

		if (dataEntryGroup.isAnonymous())
		{
			return subGroupEntityDescriptors.build();
		}

		final DataEntryGroupBindingDescriptorBuilder dataEntryDocumentBinding = new DataEntryGroupBindingDescriptorBuilder();

		final DocumentEntityDescriptor documentEntityDescriptor = DocumentEntityDescriptor
				.builder()
				.setDocumentType(DocumentType.Window, getAdWindowId().getRepoId())
				.setDetailId(createDetailIdFor(dataEntryGroup))
				.setInternalName(dataEntryGroup.getInternalName())
				.setCaption(dataEntryGroup.getCaption())
				.setDescription(dataEntryGroup.getDescription())

				.disableCallouts()
				.setReadonlyLogic(ConstantLogicExpression.FALSE)
				.setDisplayLogic(ConstantLogicExpression.TRUE)

				// this is just a "grouping" tab with no data(-records) of its own
				.setAllowCreateNewLogic(ConstantLogicExpression.FALSE)
				.setAllowDeleteLogic(ConstantLogicExpression.FALSE)

				.addAllIncludedEntities(subGroupEntityDescriptors.build())

				.setDataBinding(dataEntryDocumentBinding)
				.build();

		return ImmutableList.of(documentEntityDescriptor);
	}

	private DocumentEntityDescriptor createSubGroupEntityDescriptor(
			@NonNull final DataEntrySubGroup dataEntrySubGroup,
			@NonNull final DocumentLinkColumnName documentLinkColumnName)
	{
		final DocumentEntityDescriptor.Builder documentEntityDescriptor = DocumentEntityDescriptor
				.builder()
				.setDocumentType(DocumentType.Window, getAdWindowId().getRepoId())
				.setDetailId(createDetailIdFor(dataEntrySubGroup))
				.setInternalName(dataEntrySubGroup.getInternalName())
				.setCaption(dataEntrySubGroup.getCaption())
				.setDescription(dataEntrySubGroup.getDescription())

				.disableCallouts()
				.setReadonlyLogic(ConstantLogicExpression.FALSE)
				.setAllowCreateNewLogic(ConstantLogicExpression.TRUE)
				.setAllowDeleteLogic(ConstantLogicExpression.TRUE)
				.setDisplayLogic(ConstantLogicExpression.TRUE)

				.setDataBinding(dataEntrySubGroupBindingDescriptorBuilder);

		documentEntityDescriptor.addField(createIDField());

		documentEntityDescriptor.addField(createParentLinkField(documentLinkColumnName));

		for (final DataEntryField dataEntryField : dataEntrySubGroup.getDataEntryFields())
		{
			documentEntityDescriptor.addField(createFieldDescriptor(dataEntryField));
		}

		return documentEntityDescriptor.build();
	}

	private DocumentFieldDescriptor.Builder createIDField()
	{
		final DocumentFieldDataBindingDescriptor dataBinding = new DataEntryFieldBindingDescriptor(
				I_DataEntry_SubGroup.COLUMNNAME_DataEntry_SubGroup_ID,
				true/* mandatory */,
				FieldType.RECORD_ID);

		return DocumentFieldDescriptor.builder(I_DataEntry_SubGroup.COLUMNNAME_DataEntry_SubGroup_ID)
				.setCaption(I_DataEntry_SubGroup.COLUMNNAME_DataEntry_SubGroup_ID)
				.setWidgetType(DocumentFieldWidgetType.Integer)
				.setDisplayLogic(ConstantLogicExpression.FALSE)
				.setKey(true)
				.addCharacteristic(Characteristic.PublicField)
				.setDataBinding(dataBinding);
	}

	private DocumentFieldDescriptor.Builder createParentLinkField(@NonNull final DocumentLinkColumnName documentLinkColumnName)
	{
		final String columnNameAsString = documentLinkColumnName.getAsString();

		final DocumentFieldDataBindingDescriptor dataBinding = new DataEntryFieldBindingDescriptor(
				columnNameAsString,
				true/* mandatory */,
				FieldType.PARENT_LINK_ID);

		return DocumentFieldDescriptor.builder(columnNameAsString)
				.setCaption(columnNameAsString)
				.setWidgetType(DocumentFieldWidgetType.Integer)
				.setParentLink(true, columnNameAsString)
				.setDisplayLogic(ConstantLogicExpression.FALSE)
				.addCharacteristic(Characteristic.PublicField)
				.setDataBinding(dataBinding);
	}

	private DocumentFieldDescriptor.Builder createFieldDescriptor(@NonNull final DataEntryField dataEntryField)
	{
		final DocumentFieldDataBindingDescriptor dataBinding = new DataEntryFieldBindingDescriptor(
				createFieldNameFor(dataEntryField),
				dataEntryField.isMandatory(),
				dataEntryField.getType());

		final LookupDescriptorProvider fieldLookupDescriptorProvider;
		if (FieldType.LIST.equals(dataEntryField.getType()))
		{
			final DataEntryListValueLookupDescriptor lookupDescriptor = DataEntryListValueLookupDescriptor.of(dataEntryField.getListValues());
			fieldLookupDescriptorProvider = LookupDescriptorProvider.singleton(lookupDescriptor);
		}
		else
		{
			fieldLookupDescriptorProvider = LookupDescriptorProvider.NULL;
		}

		return DocumentFieldDescriptor.builder(createFieldNameFor(dataEntryField))
				.setCaption(dataEntryField.getCaption())
				.setDescription(dataEntryField.getDescription())
				.setWidgetType(ofFieldType(dataEntryField.getType()))
				.setLookupDescriptorProvider(fieldLookupDescriptorProvider)
				.addCharacteristic(Characteristic.PublicField)
				.setDataBinding(dataBinding);
	}

	private static DetailId createDetailIdFor(@NonNull final DataEntryGroup dataEntryGroup)
	{
		return DetailId.fromAD_Tab_ID(dataEntryGroup.getId().getRepoId());
	}

	private static DetailId createDetailIdFor(@NonNull final DataEntrySubGroup dataEntrySubGroup)
	{
		return DetailId.fromAD_Tab_ID(dataEntrySubGroup.getId().getRepoId() * 2); // TODO allow detail-ID with letter-prefix?
	}
}
