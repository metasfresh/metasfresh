package de.metas.ui.web.dataentry.window.descriptor.factory;

import static org.adempiere.model.InterfaceWrapperHelper.COLUMNNAME_Created;
import static org.adempiere.model.InterfaceWrapperHelper.COLUMNNAME_CreatedBy;
import static org.adempiere.model.InterfaceWrapperHelper.COLUMNNAME_Updated;
import static org.adempiere.model.InterfaceWrapperHelper.COLUMNNAME_UpdatedBy;

import java.util.List;

import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.expression.api.ConstantLogicExpression;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_User;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

import de.metas.dataentry.DataEntryFieldId;
import de.metas.dataentry.FieldType;
import de.metas.dataentry.data.DataEntryRecordRepository;
import de.metas.dataentry.data.json.JSONDataEntryRecordMapper;
import de.metas.dataentry.layout.DataEntryField;
import de.metas.dataentry.layout.DataEntryGroup;
import de.metas.dataentry.layout.DataEntryGroup.DocumentLinkColumnName;
import de.metas.dataentry.layout.DataEntryLayoutRepository;
import de.metas.dataentry.layout.DataEntrySection;
import de.metas.dataentry.layout.DataEntrySubGroup;
import de.metas.dataentry.model.I_DataEntry_Group;
import de.metas.dataentry.model.I_DataEntry_SubGroup;
import de.metas.i18n.IMsgBL;
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
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementGroupDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementLineDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutSectionDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutSectionDescriptor.CaptionMode;
import de.metas.ui.web.window.descriptor.DocumentLayoutSectionDescriptor.ClosableMode;
import de.metas.ui.web.window.descriptor.DocumentLayoutSingleRow;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
import de.metas.ui.web.window.descriptor.WidgetSize;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptor;
import de.metas.util.Services;
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

	@VisibleForTesting
	public static DataEntryTabLoader createInstanceForUnitTesting()
	{
		final int windowIdInt = 5;

		final JSONDataEntryRecordMapper jsonDataEntryRecordMapper = new JSONDataEntryRecordMapper();
		final DataEntryRecordRepository dataEntryRecordRepository = new DataEntryRecordRepository(jsonDataEntryRecordMapper);
		final DataEntrySubGroupBindingDescriptorBuilder //
		dataEntrySubGroupBindingDescriptorBuilder = new DataEntrySubGroupBindingDescriptorBuilder(dataEntryRecordRepository);

		final DataEntryTabLoader dataEntryTabLoader = DataEntryTabLoader
				.builder()
				.adWindowId(AdWindowId.ofRepoId(windowIdInt))
				.windowId(WindowId.of(windowIdInt))
				.dataEntrySubGroupBindingDescriptorBuilder(dataEntrySubGroupBindingDescriptorBuilder)
				.build();
		return dataEntryTabLoader;
	}

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
		final DataEntryLayoutRepository dataEntryRepository = Adempiere.getBean(DataEntryLayoutRepository.class);

		final List<DataEntryGroup> dataEntryGroups = dataEntryRepository.getByWindowId(adWindowId);

		return createLayoutDescriptors(dataEntryGroups);
	}

	@VisibleForTesting
	List<DocumentLayoutDetailDescriptor> createLayoutDescriptors(@NonNull final List<DataEntryGroup> dataEntryGroups)
	{
		final ImmutableList.Builder<DocumentLayoutDetailDescriptor> result = ImmutableList.builder();
		for (final DataEntryGroup dataEntryGroup : dataEntryGroups)
		{
			final ImmutableList<DocumentLayoutDetailDescriptor> //
			groupLayoutDescriptors = createGroupLayoutDescriptors(windowId, dataEntryGroup);

			result.addAll(groupLayoutDescriptors);
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
			final DocumentLayoutDetailDescriptor //
			subGroupLayoutDescriptor = createSubGroupLayoutDescriptor(windowId, dataEntrySubGroup);

			subGroupLayoutDescriptors.add(subGroupLayoutDescriptor);
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

		final DocumentLayoutSingleRow.Builder singleRowLayoutBuilder = DocumentLayoutSingleRow
				.builder()
				.setWindowId(windowId);

		for (final DataEntrySection dataEntrySection : dataEntrySubGroup.getDataEntrySections())
		{
			final DocumentLayoutSectionDescriptor.Builder section = createLayoutSection(dataEntrySection);

			singleRowLayoutBuilder.addSection(section);
		}

		subGroupDescriptor.singleRowDetailLayout(true);
		subGroupDescriptor.singleRowLayout(singleRowLayoutBuilder);

		return subGroupDescriptor.build();
	}

	private static DocumentLayoutSectionDescriptor.Builder createLayoutSection(
			@NonNull final DataEntrySection dataEntrySection)
	{
		final DocumentLayoutColumnDescriptor.Builder column = DocumentLayoutColumnDescriptor
				.builder();

		final ClosableMode closableMode = dataEntrySection.isInitallyClosed()
				? ClosableMode.INITIALLY_CLOSED
				: ClosableMode.INITIALLY_OPEN;

		final DocumentLayoutSectionDescriptor.Builder section = DocumentLayoutSectionDescriptor
				.builder()
				.setCaption(dataEntrySection.getCaption())
				.setDescription(dataEntrySection.getDescription())
				.setInternalName(dataEntrySection.getInternalName())
				.addColumn(column)
				.setClosableMode(closableMode)
				.setCaptionMode(CaptionMode.DISPLAY);

		final List<DataEntryField> fields = dataEntrySection.getDataEntryFields();
		for (final DataEntryField field : fields)
		{
			final DocumentLayoutElementGroupDescriptor.Builder elementGroup = createLayoutElemementGroup(field);
			column.addElementGroup(elementGroup);
		}
		return section;
	}

	private static DocumentLayoutElementGroupDescriptor.Builder createLayoutElemementGroup(final DataEntryField field)
	{
		// note that each element builder can be used/"consumed" only ones
		final DocumentLayoutElementDescriptor.Builder dataElement = createFieldElementDescriptor(field);

		final DocumentLayoutElementDescriptor.Builder createdElement = createFieldElementDescriptor(COLUMNNAME_Created, field.getId(), DocumentFieldWidgetType.DateTime);
		final DocumentLayoutElementDescriptor.Builder createdByElement = createFieldElementDescriptor(COLUMNNAME_CreatedBy, field.getId(), DocumentFieldWidgetType.Lookup);
		final DocumentLayoutElementDescriptor.Builder updatedElement = createFieldElementDescriptor(COLUMNNAME_Updated, field.getId(), DocumentFieldWidgetType.DateTime);
		final DocumentLayoutElementDescriptor.Builder updatedByElement = createFieldElementDescriptor(COLUMNNAME_UpdatedBy, field.getId(), DocumentFieldWidgetType.Lookup);

		final DocumentLayoutElementLineDescriptor.Builder elementLine = DocumentLayoutElementLineDescriptor
				.builder()
				.addElement(dataElement)
				.addElement(createdElement)
				.addElement(createdByElement)
				.addElement(updatedElement)
				.addElement(updatedByElement);

		final DocumentLayoutElementGroupDescriptor.Builder elementGroup = DocumentLayoutElementGroupDescriptor
				.builder()
				.addElementLine(elementLine);
		return elementGroup;
	}

	private static DocumentLayoutElementDescriptor.Builder createFieldElementDescriptor(final DataEntryField field)
	{
		final DocumentLayoutElementFieldDescriptor.Builder fieldBuilder = DocumentLayoutElementFieldDescriptor
				.builder(createFieldNameFor(field))
				.setEmptyText(ITranslatableString.empty());

		final DocumentLayoutElementDescriptor.Builder element = DocumentLayoutElementDescriptor
				.builder()
				.setCaption(field.getCaption())
				.setDescription(field.getDescription())
				.setViewEditorRenderMode(ViewEditorRenderMode.ALWAYS)
				.setWidgetType(ofFieldType(field.getType()))
				.setWidgetSize(WidgetSize.Large)
				.addField(fieldBuilder);
		return element;
	}

	private static DocumentLayoutElementDescriptor.Builder createFieldElementDescriptor(
			@NonNull final String columnName,
			@NonNull final DataEntryFieldId dataEntryFieldId,
			@NonNull final DocumentFieldWidgetType type)
	{
		final String columnNametoUse = constructCreatedUpdatedFieldColumnName(columnName, dataEntryFieldId);

		final DocumentLayoutElementFieldDescriptor.Builder fieldBuilder = DocumentLayoutElementFieldDescriptor
				.builder(columnNametoUse)
				.setEmptyText(ITranslatableString.empty());

		final IMsgBL msgBL = Services.get(IMsgBL.class);

		final DocumentLayoutElementDescriptor.Builder element = DocumentLayoutElementDescriptor
				.builder()
				.setCaption(msgBL.translatable(columnName))
				.setDescription(ITranslatableString.empty())
				.setViewEditorRenderMode(ViewEditorRenderMode.NEVER)
				.setWidgetType(type)
				.setWidgetSize(WidgetSize.Small)
				.addField(fieldBuilder);
		return element;
	}

	/** doesn't work*/
	private static DocumentLayoutElementDescriptor.Builder createCreatedElementDescriptor(@NonNull final DataEntryFieldId dataEntryFieldId)
	{
		final String createdByColName = constructCreatedUpdatedFieldColumnName(COLUMNNAME_CreatedBy, dataEntryFieldId);
		final String createdColName = constructCreatedUpdatedFieldColumnName(COLUMNNAME_Created, dataEntryFieldId);

		final DocumentLayoutElementFieldDescriptor.Builder createdByFieldBuilder = DocumentLayoutElementFieldDescriptor
				.builder(createdByColName)
				.setLookupSource(LookupSource.lookup)
				.setEmptyText(ITranslatableString.empty());

		final DocumentLayoutElementFieldDescriptor.Builder createdFieldBuilder = DocumentLayoutElementFieldDescriptor
				.builder(createdColName)
				.setLookupSource(LookupSource.text)
				.setEmptyText(ITranslatableString.empty());

		final IMsgBL msgBL = Services.get(IMsgBL.class);

		final DocumentLayoutElementDescriptor.Builder element = DocumentLayoutElementDescriptor
				.builder()
				.setCaption(msgBL.translatable(createdByColName))
				.setDescription(ITranslatableString.empty())
				.setViewEditorRenderMode(ViewEditorRenderMode.NEVER)
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.setWidgetSize(WidgetSize.Small)
				.addField(createdByFieldBuilder)
				.addField(createdFieldBuilder);
		return element;
	}

	private static String createFieldNameFor(@NonNull final DataEntryField field)
	{
		return Integer.toString(field.getId().getRepoId());
	}

	public List<DocumentEntityDescriptor> loadDocumentEntity()
	{
		final DataEntryLayoutRepository dataEntryRepository = Adempiere.getBean(DataEntryLayoutRepository.class);

		final List<DataEntryGroup> dataEntryGroups = dataEntryRepository.getByWindowId(adWindowId);

		return createGroupEntityDescriptors(dataEntryGroups);
	}

	@VisibleForTesting
	List<DocumentEntityDescriptor> createGroupEntityDescriptors(@NonNull final List<DataEntryGroup> dataEntryGroups)
	{
		final ImmutableList.Builder<DocumentEntityDescriptor> result = ImmutableList.builder();
		for (final DataEntryGroup dataEntryGroup : dataEntryGroups)
		{
			final ImmutableList<DocumentEntityDescriptor> groupEntityDescriptors = createGroupEntityDescriptors(dataEntryGroup);
			result.addAll(groupEntityDescriptors);
		}
		return result.build();
	}

	private ImmutableList<DocumentEntityDescriptor> createGroupEntityDescriptors(@NonNull final DataEntryGroup dataEntryGroup)
	{
		final ImmutableList.Builder<DocumentEntityDescriptor> subGroupEntityDescriptors = ImmutableList.builder();
		for (final DataEntrySubGroup dataEntrySubGroup : dataEntryGroup.getDataEntrySubGroups())
		{
			final DocumentEntityDescriptor subGroupEntityDescriptor = createSubGroupEntityDescriptor(dataEntrySubGroup, dataEntryGroup.getDocumentLinkColumnName());
			subGroupEntityDescriptors.add(subGroupEntityDescriptor);
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
				.setSingleRowDetail(true)
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

		for (final DataEntrySection dataEntrySection : dataEntrySubGroup.getDataEntrySections())
		{
			for (final DataEntryField dataEntryField : dataEntrySection.getDataEntryFields())
			{
				final DocumentFieldDescriptor.Builder dataField = createFieldDescriptor(dataEntryField);

				final DocumentFieldDescriptor.Builder createdField = createFieldDescriptor(dataEntryField.getId(), FieldType.CREATED);
				final DocumentFieldDescriptor.Builder createdByField = createFieldDescriptor(dataEntryField.getId(), FieldType.CREATED_BY);
				final DocumentFieldDescriptor.Builder updatedField = createFieldDescriptor(dataEntryField.getId(), FieldType.UPDATED);
				final DocumentFieldDescriptor.Builder updatedByField = createFieldDescriptor(dataEntryField.getId(), FieldType.UPDATED_BY);

				documentEntityDescriptor.addField(dataField);
				documentEntityDescriptor.addField(createdField);
				documentEntityDescriptor.addField(createdByField);
				documentEntityDescriptor.addField(updatedField);
				documentEntityDescriptor.addField(updatedByField);
			}
		}

		return documentEntityDescriptor.build();
	}

	private DocumentFieldDescriptor.Builder createIDField()
	{
		final DocumentFieldDataBindingDescriptor dataBinding = DataEntryFieldBindingDescriptor
				.builder()
				.columnName(I_DataEntry_SubGroup.COLUMNNAME_DataEntry_SubGroup_ID)
				.mandatory(true)
				.fieldType(FieldType.SUB_GROUP_ID)
				.build();

		return DocumentFieldDescriptor.builder(I_DataEntry_SubGroup.COLUMNNAME_DataEntry_SubGroup_ID)
				.setCaption(I_DataEntry_SubGroup.COLUMNNAME_DataEntry_SubGroup_ID)
				.setWidgetType(DocumentFieldWidgetType.Text) // not an int; we construct the DocumentId as string
				.setDisplayLogic(ConstantLogicExpression.FALSE)
				.setKey(true)
				.addCharacteristic(Characteristic.PublicField)
				.setDataBinding(dataBinding);
	}

	private DocumentFieldDescriptor.Builder createParentLinkField(@NonNull final DocumentLinkColumnName documentLinkColumnName)
	{
		final String columnNameAsString = documentLinkColumnName.getAsString();

		final DocumentFieldDataBindingDescriptor dataBinding = DataEntryFieldBindingDescriptor
				.builder()
				.columnName(columnNameAsString)
				.mandatory(true)
				.fieldType(FieldType.PARENT_LINK_ID)
				.build();

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
		final DocumentFieldDataBindingDescriptor dataBinding = DataEntryFieldBindingDescriptor
				.builder()
				.columnName(createFieldNameFor(dataEntryField))
				.mandatory(dataEntryField.isMandatory())
				.dataEntryFieldId(dataEntryField.getId())
				.fieldType(dataEntryField.getType())
				.build();

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

	private DocumentFieldDescriptor.Builder createFieldDescriptor(
			@NonNull final DataEntryFieldId dataEntryFieldId,
			@NonNull final FieldType fieldType)
	{

		final String columnName;
		final LookupDescriptorProvider fieldLookupDescriptorProvider;
		switch (fieldType)
		{
			case CREATED:
				columnName = COLUMNNAME_Created;
				fieldLookupDescriptorProvider = LookupDescriptorProvider.NULL;
				break;
			case CREATED_BY:
				columnName = COLUMNNAME_CreatedBy;
				fieldLookupDescriptorProvider = createAdUserLookupDescriptor();
				break;
			case UPDATED:
				columnName = COLUMNNAME_Updated;
				fieldLookupDescriptorProvider = LookupDescriptorProvider.NULL;
				break;
			case UPDATED_BY:
				columnName = COLUMNNAME_UpdatedBy;
				fieldLookupDescriptorProvider = createAdUserLookupDescriptor();
				break;
			default:
				throw new AdempiereException("Unexpected fieldType=" + fieldType);
		}

		final String columnNameToUse = constructCreatedUpdatedFieldColumnName(columnName, dataEntryFieldId);

		final DocumentFieldDataBindingDescriptor dataBinding = DataEntryFieldBindingDescriptor
				.builder()
				.columnName(columnNameToUse)
				.mandatory(false)
				.dataEntryFieldId(dataEntryFieldId)
				.fieldType(fieldType)
				.build();

		final IMsgBL msgBL = Services.get(IMsgBL.class);
		final ITranslatableString caption = msgBL.translatable(columnName);

		return DocumentFieldDescriptor.builder(columnNameToUse)
				.setCaption(caption)
				.setDescription(ITranslatableString.empty())
				.setWidgetType(ofFieldType(fieldType))
				.setLookupDescriptorProvider(fieldLookupDescriptorProvider)
				.addCharacteristic(Characteristic.PublicField)
				.setReadonlyLogic(ConstantLogicExpression.TRUE)
				.setDataBinding(dataBinding);
	}

	private static String constructCreatedUpdatedFieldColumnName(
			@NonNull final String columnName,
			@NonNull final DataEntryFieldId dataEntryFieldId)
	{
		return columnName + "_" + dataEntryFieldId.getRepoId();
	}

	private LookupDescriptorProvider createAdUserLookupDescriptor()
	{
		if (Adempiere.isUnitTestMode())
		{
			return LookupDescriptorProvider.NULL;
		}

		// SqlLookupDescriptor uses MLookupFactory somewhere under the hood, which needs a DB connection
		return SqlLookupDescriptor.searchInTable(I_AD_User.Table_Name);
	}

	private static DetailId createDetailIdFor(@NonNull final DataEntryGroup dataEntryGroup)
	{
		return DetailId.fromPrefixAndId(I_DataEntry_Group.Table_Name, dataEntryGroup.getId().getRepoId());
	}

	private static DetailId createDetailIdFor(@NonNull final DataEntrySubGroup dataEntrySubGroup)
	{
		return DetailId.fromPrefixAndId(I_DataEntry_SubGroup.Table_Name, dataEntrySubGroup.getId().getRepoId());
	}

	private static DocumentFieldWidgetType ofFieldType(@NonNull final FieldType fieldType)
	{
		switch (fieldType)
		{
			case DATE:
				return DocumentFieldWidgetType.ZonedDateTime;
			case LIST:
				return DocumentFieldWidgetType.List;
			case NUMBER:
				return DocumentFieldWidgetType.Number;
			case TEXT:
				return DocumentFieldWidgetType.Text;
			case LONG_TEXT:
				return DocumentFieldWidgetType.LongText;
			case YESNO:
				return DocumentFieldWidgetType.YesNo;
			case CREATED:
				return DocumentFieldWidgetType.ZonedDateTime;
			case CREATED_BY:
				return DocumentFieldWidgetType.Lookup;
			case UPDATED:
				return DocumentFieldWidgetType.ZonedDateTime;
			case UPDATED_BY:
				return DocumentFieldWidgetType.Lookup;
			case SUB_GROUP_ID:
				return DocumentFieldWidgetType.Integer;
			case PARENT_LINK_ID:
				return DocumentFieldWidgetType.Integer;
			default:
				throw new AdempiereException("Unexpected DataEntryField.Type=" + fieldType);
		}
	}
}
