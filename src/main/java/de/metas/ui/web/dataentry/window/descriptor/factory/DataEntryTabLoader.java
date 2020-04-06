package de.metas.ui.web.dataentry.window.descriptor.factory;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.expression.api.ConstantLogicExpression;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.model.X_AD_UI_ElementField;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

import de.metas.dataentry.FieldType;
import de.metas.dataentry.layout.DataEntryField;
import de.metas.dataentry.layout.DataEntryLayout;
import de.metas.dataentry.layout.DataEntryLayoutRepository;
import de.metas.dataentry.layout.DataEntryLine;
import de.metas.dataentry.layout.DataEntrySection;
import de.metas.dataentry.layout.DataEntrySubTab;
import de.metas.dataentry.layout.DataEntryTab;
import de.metas.dataentry.layout.DataEntryTab.DocumentLinkColumnName;
import de.metas.dataentry.model.I_DataEntry_SubTab;
import de.metas.dataentry.model.I_DataEntry_Tab;
import de.metas.i18n.TranslatableStrings;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvidersService;
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
import de.metas.ui.web.window.descriptor.DocumentLayoutSectionDescriptor.CaptionMode;
import de.metas.ui.web.window.descriptor.DocumentLayoutSectionDescriptor.ClosableMode;
import de.metas.ui.web.window.descriptor.DocumentLayoutSingleRow;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider;
import de.metas.ui.web.window.descriptor.LookupDescriptorProviders;
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
	private final DocumentFilterDescriptorsProvidersService filterDescriptorsProvidersService;

	AdWindowId adWindowId;
	WindowId windowId;
	DataEntrySubTabBindingDescriptorBuilder dataEntrySubTabBindingDescriptorBuilder;
	DataEntryWebuiTools dataEntryWebuiTools;

	@Builder
	private DataEntryTabLoader(
			@Nullable final DocumentFilterDescriptorsProvidersService filterDescriptorsProvidersService,
			//
			@NonNull final AdWindowId adWindowId,
			@NonNull final WindowId windowId,
			@NonNull final DataEntrySubTabBindingDescriptorBuilder dataEntrySubTabBindingDescriptorBuilder)
	{
		this.filterDescriptorsProvidersService = filterDescriptorsProvidersService;

		this.adWindowId = adWindowId;
		this.windowId = windowId;
		this.dataEntrySubTabBindingDescriptorBuilder = dataEntrySubTabBindingDescriptorBuilder;
		this.dataEntryWebuiTools = dataEntrySubTabBindingDescriptorBuilder.getDataEntryWebuiTools();

	}

	public List<DocumentLayoutDetailDescriptor> loadDocumentLayout()
	{
		final DataEntryLayoutRepository dataEntryRepository = Adempiere.getBean(DataEntryLayoutRepository.class);

		final DataEntryLayout layout = dataEntryRepository.getByWindowId(adWindowId);

		return createLayoutDescriptors(layout);
	}

	@VisibleForTesting
	List<DocumentLayoutDetailDescriptor> createLayoutDescriptors(@NonNull final DataEntryLayout layout)
	{
		final ImmutableList.Builder<DocumentLayoutDetailDescriptor> result = ImmutableList.builder();
		for (final DataEntryTab dataEntryTab : layout.getTabs())
		{
			final ImmutableList<DocumentLayoutDetailDescriptor> //
			groupLayoutDescriptors = createTabLayoutDescriptors(windowId, dataEntryTab);

			result.addAll(groupLayoutDescriptors);
		}
		return result.build();
	}

	private ImmutableList<DocumentLayoutDetailDescriptor> createTabLayoutDescriptors(
			@NonNull final WindowId windowId,
			@NonNull final DataEntryTab dataEntryTab)
	{
		final ImmutableList.Builder<DocumentLayoutDetailDescriptor> subGroupLayoutDescriptors = ImmutableList.builder();
		for (final DataEntrySubTab subTab : dataEntryTab.getSubTabs())
		{
			final DocumentLayoutDetailDescriptor //
			subGroupLayoutDescriptor = createSubTabLayoutDescriptor(windowId, subTab);

			subGroupLayoutDescriptors.add(subGroupLayoutDescriptor);
		}

		final DocumentLayoutDetailDescriptor.Builder builder = DocumentLayoutDetailDescriptor
				.builder(windowId, createDetailIdFor(dataEntryTab))
				.caption(dataEntryTab.getCaption())
				.description(dataEntryTab.getDescription())
				.internalName(dataEntryTab.getInternalName())
				.queryOnActivate(true)
				.supportQuickInput(false)
				.addAllSubTabLayouts(subGroupLayoutDescriptors.build());

		return ImmutableList.of(builder.build());
	}

	private DocumentLayoutDetailDescriptor createSubTabLayoutDescriptor(
			@NonNull final WindowId windowId,
			@NonNull final DataEntrySubTab subTab)
	{
		final DetailId subgroupDetailId = createDetailIdFor(subTab);

		final DocumentLayoutDetailDescriptor.Builder subGroupDescriptor = DocumentLayoutDetailDescriptor
				.builder(windowId, subgroupDetailId)
				.caption(subTab.getCaption())
				.description(subTab.getDescription())
				.internalName(subTab.getInternalName())
				.queryOnActivate(true)
				.supportQuickInput(false);

		final DocumentLayoutSingleRow.Builder singleRowLayoutBuilder = DocumentLayoutSingleRow
				.builder()
				.setWindowId(windowId);

		for (final DataEntrySection dataEntrySection : subTab.getSections())
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

		final ClosableMode closableMode = dataEntrySection.isInitiallyClosed()
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

		final ImmutableList<DocumentLayoutElementGroupDescriptor.Builder> //
		elementGroups = createLayoutElemementTab(dataEntrySection);

		column.addElementTabs(elementGroups);

		return layoutSection;
	}

	private ImmutableList<DocumentLayoutElementGroupDescriptor.Builder> createLayoutElemementTab(@NonNull final DataEntrySection dataEntrySection)
	{
		final int columnCount = dataEntrySection.getLines().stream()
				.map(DataEntryLine::getFields)
				.map(Collection::size)
				.max(Comparator.naturalOrder()).orElse(0);

		final ImmutableList.Builder<DocumentLayoutElementGroupDescriptor.Builder> elementGroups = ImmutableList.builder();

		final List<DataEntryLine> dataEntryLines = dataEntrySection.getLines();
		for (final DataEntryLine dataEntryLine : dataEntryLines)
		{
			final DocumentLayoutElementGroupDescriptor.Builder elementGroup = DocumentLayoutElementGroupDescriptor
					.builder()
					.setColumnCount(columnCount);

			final ImmutableList<DocumentLayoutElementLineDescriptor.Builder> elementLines = createLayoutElemementLine(dataEntryLine, columnCount);
			elementGroup.addElementLines(elementLines);

			elementGroups.add(elementGroup);
		}

		return elementGroups.build();
	}

	private ImmutableList<DocumentLayoutElementLineDescriptor.Builder> createLayoutElemementLine(
			@NonNull final DataEntryLine dataEntryLine,
			final int columnCount)
	{
		final ImmutableList.Builder<DocumentLayoutElementLineDescriptor.Builder> result = ImmutableList.builder();

		final List<DataEntryField> fields = dataEntryLine.getFields();
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
				.setEmptyFieldText(TranslatableStrings.empty());
		element.addField(dataField);

		final DocumentLayoutElementFieldDescriptor.Builder infoField = DocumentLayoutElementFieldDescriptor
				.builder(createInfoFieldName(field))
				.setEmptyFieldText(TranslatableStrings.empty())
				.setFieldType(DocumentLayoutElementFieldDescriptor.FieldType.Tooltip)
				.setTooltipIconName(X_AD_UI_ElementField.TOOLTIPICONNAME_Text)
				.setLookupSourceAsText();
		element.addField(infoField);

		return element;
	}

	public List<DocumentEntityDescriptor> loadDocumentEntity()
	{
		final DataEntryLayoutRepository dataEntryRepository = Adempiere.getBean(DataEntryLayoutRepository.class);

		final DataEntryLayout layout = dataEntryRepository.getByWindowId(adWindowId);

		return createTabEntityDescriptors(layout);
	}

	@VisibleForTesting
	List<DocumentEntityDescriptor> createTabEntityDescriptors(@NonNull final DataEntryLayout layout)
	{
		final ImmutableList.Builder<DocumentEntityDescriptor> result = ImmutableList.builder();
		for (final DataEntryTab dataEntryTab : layout.getTabs())
		{
			final ImmutableList<DocumentEntityDescriptor> groupEntityDescriptors = createTabEntityDescriptors(dataEntryTab);
			result.addAll(groupEntityDescriptors);
		}
		return result.build();
	}

	private ImmutableList<DocumentEntityDescriptor> createTabEntityDescriptors(@NonNull final DataEntryTab dataEntryTab)
	{
		final ImmutableList.Builder<DocumentEntityDescriptor> subGroupEntityDescriptors = ImmutableList.builder();
		for (final DataEntrySubTab dataEntrySubTab : dataEntryTab.getSubTabs())
		{
			final DocumentEntityDescriptor subGroupEntityDescriptor = createSubTabEntityDescriptor(dataEntrySubTab, dataEntryTab.getDocumentLinkColumnName());
			subGroupEntityDescriptors.add(subGroupEntityDescriptor);
		}

		final DataEntryTabBindingDescriptorBuilder dataEntryDocumentBinding = new DataEntryTabBindingDescriptorBuilder();

		final DocumentEntityDescriptor documentEntityDescriptor = DocumentEntityDescriptor.builder()
				.setDocumentType(getAdWindowId())
				.setDetailId(createDetailIdFor(dataEntryTab))
				.setInternalName(dataEntryTab.getInternalName())
				.setCaption(dataEntryTab.getCaption())
				.setDescription(dataEntryTab.getDescription())

				.disableCallouts()
				.setReadonlyLogic(ConstantLogicExpression.FALSE)
				.setDisplayLogic(ConstantLogicExpression.TRUE)

				// this is just a "grouping" tab with no data(-records) of its own
				.setAllowCreateNewLogic(ConstantLogicExpression.FALSE)
				.setAllowDeleteLogic(ConstantLogicExpression.FALSE)

				.addAllIncludedEntities(subGroupEntityDescriptors.build())

				.setDataBinding(dataEntryDocumentBinding)

				.setFilterDescriptorsProvidersService(filterDescriptorsProvidersService)
				.build();

		return ImmutableList.of(documentEntityDescriptor);
	}

	private DocumentEntityDescriptor createSubTabEntityDescriptor(
			@NonNull final DataEntrySubTab dataEntrySubTab,
			@NonNull final DocumentLinkColumnName documentLinkColumnName)
	{
		final DocumentEntityDescriptor.Builder documentEntityDescriptor = DocumentEntityDescriptor.builder()
				.setSingleRowDetail(true)
				.setDocumentType(getAdWindowId())
				.setDetailId(createDetailIdFor(dataEntrySubTab))
				.setInternalName(dataEntrySubTab.getInternalName())
				.setCaption(dataEntrySubTab.getCaption())
				.setDescription(dataEntrySubTab.getDescription())

				.disableCallouts()
				.setReadonlyLogic(ConstantLogicExpression.FALSE)
				.setAllowCreateNewLogic(ConstantLogicExpression.TRUE)
				.setAllowDeleteLogic(ConstantLogicExpression.TRUE)
				.setDisplayLogic(ConstantLogicExpression.TRUE)

				.setDataBinding(dataEntrySubTabBindingDescriptorBuilder);

		//
		documentEntityDescriptor.setFilterDescriptorsProvidersService(filterDescriptorsProvidersService);

		//
		documentEntityDescriptor.addField(createIDField());
		documentEntityDescriptor.addField(createParentLinkField(documentLinkColumnName));
		for (final DataEntrySection dataEntrySection : dataEntrySubTab.getSections())
		{
			for (final DataEntryLine dataEntryLine : dataEntrySection.getLines())
			{
				for (final DataEntryField dataEntryField : dataEntryLine.getFields())
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
				.columnName(I_DataEntry_SubTab.COLUMNNAME_DataEntry_SubTab_ID)
				.mandatory(true)
				.fieldType(FieldType.SUB_TAB_ID)
				.build();

		return DocumentFieldDescriptor.builder(I_DataEntry_SubTab.COLUMNNAME_DataEntry_SubTab_ID)
				.setCaption(I_DataEntry_SubTab.COLUMNNAME_DataEntry_SubTab_ID)
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
			fieldLookupDescriptorProvider = LookupDescriptorProviders.singleton(lookupDescriptor);
		}
		else
		{
			fieldLookupDescriptorProvider = LookupDescriptorProviders.NULL;
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
		final String fieldName = createInfoFieldName(dataEntryField);
		final boolean mandatory = false;

		final DocumentFieldDataBindingDescriptor dataBinding = DataEntryFieldBindingDescriptor
				.builder()
				.columnName(fieldName)
				.mandatory(mandatory)
				.dataEntryFieldId(dataEntryField.getId())
				.fieldType(FieldType.CREATED_UPDATED_INFO)
				.build();

		return DocumentFieldDescriptor
				.builder(fieldName)
				.setWidgetType(ofFieldType(FieldType.CREATED_UPDATED_INFO))
				.setLookupDescriptorProvider(LookupDescriptorProviders.NULL)
				.addCharacteristic(Characteristic.PublicField)
				.setMandatoryLogic(ConstantLogicExpression.of(mandatory))
				.setDataBinding(dataBinding);
	}

	private String createInfoFieldName(@NonNull final DataEntryField dataEntryField)
	{
		return dataEntryWebuiTools.computeFieldName(dataEntryField.getId()) + "_Info";
	}

	private static DetailId createDetailIdFor(@NonNull final DataEntryTab dataEntryTab)
	{
		return DetailId.fromPrefixAndId(I_DataEntry_Tab.Table_Name, dataEntryTab.getId().getRepoId());
	}

	private static DetailId createDetailIdFor(@NonNull final DataEntrySubTab subTab)
	{
		return DetailId.fromPrefixAndId(I_DataEntry_SubTab.Table_Name, subTab.getId().getRepoId());
	}

	private static DocumentFieldWidgetType ofFieldType(@NonNull final FieldType fieldType)
	{
		switch (fieldType)
		{
			case DATE:
				return DocumentFieldWidgetType.LocalDate;
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
			case SUB_TAB_ID:
				return DocumentFieldWidgetType.Integer;
			case PARENT_LINK_ID:
				return DocumentFieldWidgetType.Integer;
			default:
				throw new AdempiereException("Unexpected DataEntryField.Type=" + fieldType);
		}
	}
}
