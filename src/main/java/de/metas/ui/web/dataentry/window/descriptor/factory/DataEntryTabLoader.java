package de.metas.ui.web.dataentry.window.descriptor.factory;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.expression.api.ConstantLogicExpression;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.user.UserRepository;
import org.compiere.Adempiere;
import org.compiere.model.X_AD_UI_ElementField;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

import de.metas.dataentry.FieldType;
import de.metas.dataentry.data.DataEntryRecordRepository;
import de.metas.dataentry.data.json.JSONDataEntryRecordMapper;
import de.metas.dataentry.layout.DataEntryField;
import de.metas.dataentry.layout.DataEntryGroup;
import de.metas.dataentry.layout.DataEntryGroup.DocumentLinkColumnName;
import de.metas.dataentry.layout.DataEntryLayoutRepository;
import de.metas.dataentry.layout.DataEntryLine;
import de.metas.dataentry.layout.DataEntrySection;
import de.metas.dataentry.layout.DataEntrySubGroup;
import de.metas.dataentry.model.I_DataEntry_Group;
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

	DataEntryWebuiTools dataEntryWebuiTools;

	@VisibleForTesting
	public static DataEntryTabLoader createInstanceForUnitTesting()
	{
		final int windowIdInt = 5;

		final DataEntryWebuiTools dataEntryWebuiTools = new DataEntryWebuiTools(new UserRepository());
		final JSONDataEntryRecordMapper jsonDataEntryRecordMapper = new JSONDataEntryRecordMapper();
		final DataEntryRecordRepository dataEntryRecordRepository = new DataEntryRecordRepository(jsonDataEntryRecordMapper);

		final DataEntrySubGroupBindingDescriptorBuilder //
		dataEntrySubGroupBindingDescriptorBuilder = new DataEntrySubGroupBindingDescriptorBuilder(
				dataEntryRecordRepository,
				dataEntryWebuiTools);

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
		this.dataEntryWebuiTools = dataEntrySubGroupBindingDescriptorBuilder.getDataEntryWebuiTools();

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

	private ImmutableList<DocumentLayoutDetailDescriptor> createGroupLayoutDescriptors(
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

	private DocumentLayoutDetailDescriptor createSubGroupLayoutDescriptor(
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

	private DocumentLayoutSectionDescriptor.Builder createLayoutSection(
			@NonNull final DataEntrySection dataEntrySection)
	{
		final DocumentLayoutColumnDescriptor.Builder column = DocumentLayoutColumnDescriptor
				.builder();

		final ClosableMode closableMode = dataEntrySection.isInitallyClosed()
				? ClosableMode.INITIALLY_CLOSED
				: ClosableMode.INITIALLY_OPEN;

		final DocumentLayoutSectionDescriptor.Builder layoutSection = DocumentLayoutSectionDescriptor
				.builder()
				.setCaption(dataEntrySection.getCaption())
				.setDescription(dataEntrySection.getDescription())
				.setInternalName(dataEntrySection.getInternalName())
				.addColumn(column)
				.setClosableMode(closableMode)
				.setCaptionMode(CaptionMode.DISPLAY);

		final DocumentLayoutElementGroupDescriptor.Builder elementGroup = createLayoutElemementGroup(dataEntrySection);
		column.addElementGroup(elementGroup);

		return layoutSection;
	}

	private DocumentLayoutElementGroupDescriptor.Builder createLayoutElemementGroup(@NonNull final DataEntrySection dataEntrySection)
	{
		final Integer columnCount = dataEntrySection.getDataEntryLines().stream()
				.map(DataEntryLine::getDataEntryFields)
				.map(Collection::size)
				.max(Comparator.naturalOrder()).orElse(0);

		final DocumentLayoutElementGroupDescriptor.Builder elementGroup = DocumentLayoutElementGroupDescriptor
				.builder()
				.setColumnCount(columnCount);

		final List<DataEntryLine> dataEntryLines = dataEntrySection.getDataEntryLines();
		for (final DataEntryLine dataEntryLine : dataEntryLines)
		{
			final ImmutableList<DocumentLayoutElementLineDescriptor.Builder> elementLines = createLayoutElemementLine(dataEntryLine, columnCount);
			elementGroup.addElementLines(elementLines);
		}

		return elementGroup;
	}

	private ImmutableList<DocumentLayoutElementLineDescriptor.Builder> createLayoutElemementLine(
			@NonNull final DataEntryLine dataEntryLine,
			final int columnCount)
	{
		final ImmutableList.Builder<DocumentLayoutElementLineDescriptor.Builder> result = ImmutableList.builder();

		final List<DataEntryField> fields = dataEntryLine.getDataEntryFields();
		for (final DataEntryField field : fields)
		{
			final DocumentLayoutElementLineDescriptor.Builder elementLine = createLayoutElemementLine(field);
			result.add(elementLine);
		}

		// fill the gap with "empty cells" if this line has less fields than columnCount specifies
		for (int i = fields.size(); i < columnCount; i++)
		{
			final DocumentLayoutElementLineDescriptor.Builder emptyElementLine = DocumentLayoutElementLineDescriptor.builder();
			result.add(emptyElementLine);
		}

		return result.build();
	}

//	private DocumentLayoutElementGroupDescriptor.Builder createLayoutElemementGroup(@NonNull final DataEntryLine line)
//	{
//		final DocumentLayoutElementGroupDescriptor.Builder elementGroup = DocumentLayoutElementGroupDescriptor
//				.builder()
//				.setColumnCount(line.getDataEntryFields().size());
//
//		final List<DataEntryField> fields = line.getDataEntryFields();
//		for (final DataEntryField field : fields)
//		{
//			final DocumentLayoutElementLineDescriptor.Builder elementLine = createLayoutElemementLine(field);
//			elementGroup.addElementLine(elementLine);
//		}
//
//		return elementGroup;
//	}

	private DocumentLayoutElementLineDescriptor.Builder createLayoutElemementLine(@NonNull final DataEntryField field)
	{
		// note that each element builder can be used/"consumed" only ones
		final DocumentLayoutElementDescriptor.Builder dataElement = createFieldElementDescriptor(field);

		final DocumentLayoutElementLineDescriptor.Builder elementLine = DocumentLayoutElementLineDescriptor
				.builder()
				.addElement(dataElement);
		return elementLine;
	}

	private DocumentLayoutElementDescriptor.Builder createFieldElementDescriptor(@NonNull final DataEntryField field)
	{
		final DocumentLayoutElementDescriptor.Builder element = DocumentLayoutElementDescriptor
				.builder()
				.setCaption(field.getCaption())
				.setDescription(field.getDescription())
				.setViewEditorRenderMode(ViewEditorRenderMode.ALWAYS)
				.setWidgetType(ofFieldType(field.getType()))
				.setWidgetSize(WidgetSize.Default);
		if (FieldType.LONG_TEXT.equals(field.getType()))
		{
			element.setMultilineText(true);
			element.setMultilineTextLines(10);
		}

		final String fieldName = dataEntryWebuiTools.computeFieldName(field.getId());

		final DocumentLayoutElementFieldDescriptor.Builder dataField = DocumentLayoutElementFieldDescriptor
				.builder(fieldName)
				.setEmptyText(ITranslatableString.empty());
		element.addField(dataField);

		final DocumentLayoutElementFieldDescriptor.Builder infoField = DocumentLayoutElementFieldDescriptor
				.builder(fieldName + "_CreateUpdatedInfo")
				.setEmptyText(ITranslatableString.empty())
				.setFieldType(DocumentLayoutElementFieldDescriptor.FieldType.Tooltip)
				.setTooltipIconName(X_AD_UI_ElementField.TOOLTIPICONNAME_Text)
				.setLookupSource(LookupSource.text);
		element.addField(infoField);

		return element;
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
			for (final DataEntryLine dataEntryLine : dataEntrySection.getDataEntryLines())
			{
				for (final DataEntryField dataEntryField : dataEntryLine.getDataEntryFields())
				{
					final DocumentFieldDescriptor.Builder dataField = createDataFieldDescriptor(dataEntryField);

					// final DocumentFieldDescriptor.Builder createdField = createFieldDescriptor(dataEntryField.getId(), FieldType.CREATED);
					// final DocumentFieldDescriptor.Builder createdByField = createFieldDescriptor(dataEntryField.getId(), FieldType.CREATED_BY);
					// final DocumentFieldDescriptor.Builder updatedField = createFieldDescriptor(dataEntryField.getId(), FieldType.UPDATED);
					// final DocumentFieldDescriptor.Builder updatedByField = createFieldDescriptor(dataEntryField.getId(), FieldType.UPDATED_BY);

					documentEntityDescriptor.addField(dataField);
					// documentEntityDescriptor.addField(createdField);
					// documentEntityDescriptor.addField(createdByField);
					// documentEntityDescriptor.addField(updatedField);
					// documentEntityDescriptor.addField(updatedByField);

					final DocumentFieldDescriptor.Builder createdUpdatedInfoField = createCreatedUpdatedInfoFieldDescriptor(dataEntryField);
					documentEntityDescriptor.addField(createdUpdatedInfoField);
				}
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

	private DocumentFieldDescriptor.Builder createDataFieldDescriptor(@NonNull final DataEntryField dataEntryField)
	{
		final String fieldName = dataEntryWebuiTools.computeFieldName(dataEntryField.getId());

		final DocumentFieldDataBindingDescriptor dataBinding = DataEntryFieldBindingDescriptor
				.builder()
				.columnName(fieldName)
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

		return DocumentFieldDescriptor.builder(fieldName)
				.setCaption(dataEntryField.getCaption())
				.setDescription(dataEntryField.getDescription())
				.setWidgetType(ofFieldType(dataEntryField.getType()))
				.setLookupDescriptorProvider(fieldLookupDescriptorProvider)
				.addCharacteristic(Characteristic.PublicField)
				.setMandatoryLogic(ConstantLogicExpression.of(dataEntryField.isMandatory()))
				.setDataBinding(dataBinding);
	}

	private DocumentFieldDescriptor.Builder createCreatedUpdatedInfoFieldDescriptor(@NonNull final DataEntryField dataEntryField)
	{

		final String fieldName = dataEntryWebuiTools.computeFieldName(dataEntryField.getId()) + "_Info";
		final boolean mandatory = false;

		final DocumentFieldDataBindingDescriptor dataBinding = DataEntryFieldBindingDescriptor
				.builder()
				.columnName(fieldName)
				.mandatory(mandatory)
				.dataEntryFieldId(dataEntryField.getId())
				.fieldType(FieldType.CREATED_UPDATED_INFO)
				.build();

		return DocumentFieldDescriptor.builder(fieldName)
				// .setCaption(dataEntryField.getCaption())
				// .setDescription(dataEntryField.getDescription())
				.setWidgetType(ofFieldType(FieldType.CREATED_UPDATED_INFO))
				.setLookupDescriptorProvider(LookupDescriptorProvider.NULL)
				.addCharacteristic(Characteristic.PublicField)
				.setMandatoryLogic(ConstantLogicExpression.of(mandatory))
				.setDataBinding(dataBinding);
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
			case CREATED_UPDATED_INFO:
				return DocumentFieldWidgetType.Text;
			case SUB_GROUP_ID:
				return DocumentFieldWidgetType.Integer;
			case PARENT_LINK_ID:
				return DocumentFieldWidgetType.Integer;
			default:
				throw new AdempiereException("Unexpected DataEntryField.Type=" + fieldType);
		}
	}
}
