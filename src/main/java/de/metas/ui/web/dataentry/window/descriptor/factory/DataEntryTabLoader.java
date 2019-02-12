package de.metas.ui.web.dataentry.window.descriptor.factory;

import static org.assertj.core.api.Assertions.fail;

import java.util.List;

import org.adempiere.ad.expression.api.ConstantLogicExpression;
import org.compiere.Adempiere;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

import de.metas.dataentry.DataEntryField;
import de.metas.dataentry.DataEntryField.Type;
import de.metas.dataentry.DataEntryGroup;
import de.metas.dataentry.DataEntryGroup.DocumentLinkColumnName;
import de.metas.dataentry.DataEntryRepository;
import de.metas.dataentry.DataEntrySubGroup;
import de.metas.dataentry.model.I_DataEntry_Record_Assignment;
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
@Builder
public class DataEntryTabLoader
{
	int adWindowId;

	@NonNull
	final WindowId windowId;

	public List<DocumentLayoutDetailDescriptor> loadDocumentLayout()
	{
		final DataEntryRepository dataEntryRepository = Adempiere.getBean(DataEntryRepository.class);

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

	private static DocumentFieldWidgetType ofFieldType(@NonNull final DataEntryField.Type fieldType)
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
		final DataEntryRepository dataEntryRepository = Adempiere.getBean(DataEntryRepository.class);

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
				.setDocumentType(DocumentType.Window, getAdWindowId())
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
		final DataEntrySubGroupBindingDescriptorBuilder dataEntryDocumentBinding = new DataEntrySubGroupBindingDescriptorBuilder();

		final DocumentEntityDescriptor.Builder documentEntityDescriptor = DocumentEntityDescriptor
				.builder()
				.setDocumentType(DocumentType.Window, getAdWindowId())
				.setDetailId(createDetailIdFor(dataEntrySubGroup))
				.setInternalName(dataEntrySubGroup.getInternalName())
				.setCaption(dataEntrySubGroup.getCaption())
				.setDescription(dataEntrySubGroup.getDescription())

				.disableCallouts()
				.setReadonlyLogic(ConstantLogicExpression.FALSE)
				.setAllowCreateNewLogic(ConstantLogicExpression.TRUE)
				.setAllowDeleteLogic(ConstantLogicExpression.TRUE)
				.setDisplayLogic(ConstantLogicExpression.TRUE)

				.setDataBinding(dataEntryDocumentBinding);

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
				I_DataEntry_Record_Assignment.COLUMNNAME_DataEntry_Record_ID,
				true/* mandatory */);

		return DocumentFieldDescriptor.builder(I_DataEntry_Record_Assignment.COLUMNNAME_DataEntry_Record_ID)
				.setCaption(I_DataEntry_Record_Assignment.COLUMNNAME_DataEntry_Record_ID)
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
				true/* mandatory */);

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
				dataEntryField.isMandatory());

		final LookupDescriptorProvider fieldLookupDescriptorProvider;
		if (Type.LIST.equals(dataEntryField.getType()))
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
