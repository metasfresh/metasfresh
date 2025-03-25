/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.ui.web.window.descriptor.factory.standard;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.CoalesceUtil;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.ui.web.attributes_included_tab.AttributesUIElementTypeFactory;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.window.datatypes.MediaType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.DocumentLayoutColumnDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutDetailDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.FieldType;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementGroupDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementLineDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutSectionDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutSingleRow;
import de.metas.ui.web.window.descriptor.DocumentLayoutSingleRow.Builder;
import de.metas.ui.web.window.descriptor.LayoutElementType;
import de.metas.ui.web.window.descriptor.LayoutType;
import de.metas.ui.web.window.descriptor.QuickInputSupportDescriptor;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
import de.metas.ui.web.window.descriptor.WidgetSize;
import de.metas.util.Check;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdFieldId;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.element.api.AdUIElementId;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.GridTabVO;
import org.compiere.model.GridWindowVO;
import org.compiere.model.I_AD_UI_Column;
import org.compiere.model.I_AD_UI_Element;
import org.compiere.model.I_AD_UI_ElementField;
import org.compiere.model.I_AD_UI_ElementGroup;
import org.compiere.model.I_AD_UI_Section;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static de.metas.ui.web.window.WindowConstants.FIELDNAME_AD_Client_ID;
import static de.metas.ui.web.window.WindowConstants.FIELDNAME_AD_Org_ID;
import static de.metas.ui.web.window.WindowConstants.SYS_CONFIG_AD_CLIENT_ID_IS_DISPLAYED;
import static de.metas.ui.web.window.WindowConstants.SYS_CONFIG_AD_ORG_ID_IS_DISPLAYED;

public class LayoutFactory
{
	// services
	private static final Logger logger = LogManager.getLogger(LayoutFactory.class);
	@NonNull private final LayoutFactorySupportingServices services;

	public static final AdMessageKey TAB_EMPTY_RESULT_HINT = AdMessageKey.of("de.metas.ui.web.TAB_EMPTY_RESULT_HINT");
	public static final AdMessageKey TAB_EMPTY_RESULT_TEXT = AdMessageKey.of("de.metas.ui.web.TAB_EMPTY_RESULT_TEXT");

	private static final int DEFAULT_MultiLine_LinesCount = 3;

	//
	// Parameters
	@NonNull private final IWindowUIElementsProvider windowUIElementsProvider;
	@NonNull private final GridWindowVO gridWindowVO;
	@NonNull private final GridTabVO gridTabVO;
	private final boolean isRootTab;
	@NonNull @Getter private final AdWindowId adWindowId;

	//
	// Build parameters
	@NonNull private final IWindowUIElementsProvider _windowUIElementsProviderEffective;
	private final List<I_AD_UI_Section> _uiSections;
	private final GridTabVOBasedDocumentEntityDescriptorFactory descriptorsFactory;
	private final AttributesUIElementTypeFactory attributesUIElementTypeFactory;
	private final ImmutableSet<AdTabId> childAdTabIdsToSkip;

	@lombok.Builder
	LayoutFactory(
			@NonNull final IWindowUIElementsProvider windowUIElementsProvider,
			@NonNull final LayoutFactorySupportingServices services,
			//
			@NonNull final GridWindowVO gridWindowVO,
			@NonNull final GridTabVO gridTabVO,
			@Nullable final GridTabVO parentTab)
	{
		this.windowUIElementsProvider = windowUIElementsProvider;
		this.services = services;
		//
		this.gridWindowVO = gridWindowVO;
		this.gridTabVO = gridTabVO;
		this.isRootTab = parentTab == null;
		this.adWindowId = gridTabVO.getAdWindowId();

		final AdTabId templateTabId = CoalesceUtil.coalesce(gridTabVO.getTemplateTabId(), gridTabVO.getAdTabId());
		if (templateTabId == null)
		{
			throw new AdempiereException("No AD_Tab_ID found for " + gridTabVO);
		}

		//
		// Pick the right UI elements provider (DAO, fallback to InMemory),
		// and fetch the UI sections
		{
			IWindowUIElementsProvider windowUIElementsProviderEffective = windowUIElementsProvider;
			List<I_AD_UI_Section> uiSections = windowUIElementsProvider.getUISections(templateTabId);
			if (uiSections.isEmpty())
			{
				windowUIElementsProviderEffective = new InMemoryUIElementsProvider();
				logger.debug("No UI Sections found for {}. Switching to {}", gridTabVO, windowUIElementsProviderEffective);

				uiSections = windowUIElementsProviderEffective.getUISections(templateTabId);
			}

			_uiSections = ImmutableList.copyOf(uiSections);
			logger.trace("UI sections: {}", _uiSections);

			this._windowUIElementsProviderEffective = windowUIElementsProviderEffective;
			logger.trace("Using UI provider: {}", this._windowUIElementsProviderEffective);
		}

		final List<I_AD_UI_Element> labelsUIElements = this._windowUIElementsProviderEffective.getUIElementsOfType(templateTabId, LayoutElementType.Labels);
		this.attributesUIElementTypeFactory = AttributesUIElementTypeFactory.builder()
				.attributesIncludedTabDescriptorService(services.getAttributesIncludedTabDescriptorService())
				.attributesUIElements(this._windowUIElementsProviderEffective.getUIElementsOfType(templateTabId, LayoutElementType.Attributes))
				.build();
		descriptorsFactory = GridTabVOBasedDocumentEntityDescriptorFactory.builder()
				.lookupDataSourceFactory(services.getLookupDataSourceFactory())
				.documentDecorators(services.getDocumentDecorators())
				.gridTabVO(gridTabVO)
				.parentTabVO(parentTab)
				.isSOTrx(gridWindowVO.isSOTrx())
				.labelsUIElements(labelsUIElements)
				.attributesUIElementTypeFactory(attributesUIElementTypeFactory)
				.build();

		this.childAdTabIdsToSkip = extractLabelsTabIdsOfVisibleElements(labelsUIElements);
	}

	private static ImmutableSet<AdTabId> extractLabelsTabIdsOfVisibleElements(final List<I_AD_UI_Element> labelsUIElements)
	{
		return labelsUIElements
				.stream()
				.filter(I_AD_UI_Element::isDisplayed) // hide the Tab only if the field is displayed. If the field is used only for filtering, we shall also have the tab.
				.map(labelsUIElement -> AdTabId.ofRepoId(labelsUIElement.getLabels_Tab_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("AD_Window_ID", adWindowId)
				.add("AD_UI_Sections.count", getUISections().size())
				.add("UIProvider", getUIProvider())
				.toString();
	}

	public String getTableName() {return descriptorsFactory.getTableName();}

	public boolean isSkipAD_Tab_ID(final AdTabId adTabId)
	{
		return childAdTabIdsToSkip.contains(adTabId);
	}

	public List<LayoutFactory> getIncludedTabLayouts()
	{
		if (!isRootTab)
		{
			return ImmutableList.of();
		}

		final ImmutableList.Builder<LayoutFactory> result = ImmutableList.builder();
		for (final GridTabVO includedTabVO : gridWindowVO.getChildTabs(gridTabVO.getTabNo()))
		{
			// Skip sort tabs because they are not supported
			if (includedTabVO.IsSortTab)
			{
				continue;
			}

			// Skip tabs which were already used/embedded in root layout
			if (isSkipAD_Tab_ID(includedTabVO.getAdTabId()))
			{
				continue;
			}

			result.add(
					LayoutFactory.builder()
							.services(services)
							.windowUIElementsProvider(windowUIElementsProvider)
							.gridWindowVO(gridWindowVO)
							.gridTabVO(includedTabVO)
							.parentTab(gridTabVO)
							.build()
			);
		}

		return result.build();
	}

	private IWindowUIElementsProvider getUIProvider()
	{
		return this._windowUIElementsProviderEffective;
	}

	private List<I_AD_UI_Section> getUISections()
	{
		return _uiSections;
	}

	private Stream<I_AD_UI_Element> streamAD_UI_Elements()
	{
		return getUISections()
				.stream()
				.flatMap(uiSection -> getUIProvider().getUIColumns(uiSection).stream())
				.flatMap(uiColumn -> getUIProvider().getUIElementGroups(uiColumn).stream())
				.flatMap(uiElementGroup -> getUIProvider().getUIElements(uiElementGroup).stream());
	}

	/**
	 * Single row layout: sections list
	 */
	@Nullable
	public DocumentLayoutSingleRow.Builder layoutSingleRow()
	{
		// NOTE, according to (FRESH-686 #26), we are putting all elements in one list, one after another, no sections, no columns etc
		final DocumentEntityDescriptor.Builder entityDescriptor = documentEntity();
		logger.trace("Generating single row layout for {}", entityDescriptor);

		// If the tab is never displayed then don't create the layout
		final ILogicExpression tabDisplayLogic = descriptorsFactory.getTabDisplayLogic();
		if (tabDisplayLogic.isConstantFalse())
		{
			logger.warn("Skip creating single row layout because it's never displayed: {}, tabDisplayLogic={}", entityDescriptor, tabDisplayLogic);
			return null;
		}

		//
		// Layout sections
		List<DocumentLayoutSectionDescriptor.Builder> layoutSectionsList = layoutSingleRow_SectionsList();
		// In case there were no layout sections defined then use the grid view elements and put them in one column.
		// Usually this happens when generating the single row layout for included tabs.
		if (layoutSectionsList.stream().noneMatch(DocumentLayoutSectionDescriptor.Builder::isNotEmpty))
		{
			final DocumentLayoutSectionDescriptor.Builder oneLayoutSection = DocumentLayoutSectionDescriptor.builder()
					.addColumn(layoutGridView().getElements());
			layoutSectionsList = ImmutableList.of(oneLayoutSection);
		}

		return DocumentLayoutSingleRow.builder()
				.setCaption(entityDescriptor.getCaption())
				.setDescription(entityDescriptor.getDescription())
				.notFoundMessages(entityDescriptor.getNotFoundMessages())
				.addSections(layoutSectionsList);
	}

	private List<DocumentLayoutSectionDescriptor.Builder> layoutSingleRow_SectionsList()
	{
		final List<I_AD_UI_Section> uiSections = getUISections();
		logger.trace("Generating layout sections list for {}", uiSections);

		//
		// UI Sections
		final List<DocumentLayoutSectionDescriptor.Builder> layoutSectionBuilders = new ArrayList<>();
		for (final I_AD_UI_Section uiSection : uiSections)
		{
			layoutSectionBuilders.add(layoutSingleRow_Section(uiSection)
					.setExcludeSpecialFields());
		}

		return layoutSectionBuilders;
	}

	private DocumentLayoutSectionDescriptor.Builder layoutSingleRow_Section(final I_AD_UI_Section uiSection)
	{
		final IModelTranslationMap uiSectionTrls = InterfaceWrapperHelper.getModelTranslationMap(uiSection);
		final DocumentLayoutSectionDescriptor.Builder layoutSectionBuilder = DocumentLayoutSectionDescriptor.builder()
				.setInternalName(uiSection.toString())
				.setCaption(uiSectionTrls.getColumnTrl(I_AD_UI_Section.COLUMNNAME_Name, uiSection.getName()))
				.setDescription(uiSectionTrls.getColumnTrl(I_AD_UI_Section.COLUMNNAME_Description, uiSection.getDescription()))
				.setUIStyle(uiSection.getUIStyle());

		if (!uiSection.isActive())
		{
			return layoutSectionBuilder.setInvalid("UI section not active: " + uiSection);
		}

		//
		// UI Columns
		for (final I_AD_UI_Column uiColumn : getUIProvider().getUIColumns(uiSection))
		{
			final DocumentLayoutColumnDescriptor.Builder layoutColumnBuilder = layoutSingleRow_Column(uiColumn);
			if (layoutColumnBuilder == null)
			{
				continue;
			}
			layoutSectionBuilder.addColumn(layoutColumnBuilder);
		}

		return layoutSectionBuilder;
	}

	@Nullable
	private DocumentLayoutColumnDescriptor.Builder layoutSingleRow_Column(final I_AD_UI_Column uiColumn)
	{
		if (!uiColumn.isActive())
		{
			logger.trace("Skip adding {} because it's not active", uiColumn);
			return null;
		}

		final List<I_AD_UI_ElementGroup> uiElementGroups = getUIProvider().getUIElementGroups(uiColumn);
		logger.trace("Generating layout column for {}: {}", uiColumn, uiElementGroups);

		final DocumentLayoutColumnDescriptor.Builder layoutColumnBuilder = DocumentLayoutColumnDescriptor.builder()
				.setInternalName(uiColumn.toString());

		//
		// UI Element Groups
		for (final I_AD_UI_ElementGroup uiElementGroup : uiElementGroups)
		{
			final DocumentLayoutElementGroupDescriptor.Builder layoutElementGroupBuilder = layoutSingleRow_ElementGroup(uiElementGroup);
			if (layoutElementGroupBuilder == null)
			{
				continue;
			}
			layoutColumnBuilder.addElementGroup(layoutElementGroupBuilder);
		}

		return layoutColumnBuilder;
	}

	@Nullable
	private DocumentLayoutElementGroupDescriptor.Builder layoutSingleRow_ElementGroup(final I_AD_UI_ElementGroup uiElementGroup)
	{
		if (!uiElementGroup.isActive())
		{
			logger.trace("Skip building layout element group for {} because it's not active", uiElementGroup);
			return null;
		}

		final List<I_AD_UI_Element> uiElements = getUIProvider().getUIElements(uiElementGroup);
		logger.trace("Building layout element group for {}: {}", uiElementGroup, uiElements);

		final DocumentLayoutElementGroupDescriptor.Builder layoutElementGroupBuilder = DocumentLayoutElementGroupDescriptor.builder()
				.setInternalName(uiElementGroup.toString())
				.setLayoutType(uiElementGroup.getUIStyle());

		//
		// UI Elements
		for (final I_AD_UI_Element uiElement : uiElements)
		{
			if (!uiElement.isDisplayed())
			{
				logger.trace("Skip {} because it's not displayed", uiElement);
				continue;
			}

			for (final DocumentLayoutElementLineDescriptor.Builder layoutElementLineBuilder : layoutSingleRow_ElementLines(uiElement))
			{
				if (!layoutElementLineBuilder.hasElements())
				{
					logger.trace("Skip {} because it's empty", layoutElementLineBuilder);
					continue;
				}

				layoutElementGroupBuilder.addElementLine(layoutElementLineBuilder);
			}
		}

		logger.trace("Built layout element group for {}: {}", uiElementGroup, layoutElementGroupBuilder);
		return layoutElementGroupBuilder;
	}

	private List<DocumentLayoutElementLineDescriptor.Builder> layoutSingleRow_ElementLines(final I_AD_UI_Element uiElement)
	{
		try
		{
			logger.trace("Building layout element line for {}", uiElement);

			final List<DocumentLayoutElementDescriptor.Builder> layoutElementBuilders = layoutElements(uiElement);
			if (layoutElementBuilders.isEmpty())
			{
				logger.trace("Skip building layout element line because got null layout element: {}", uiElement);
				return ImmutableList.of();
			}

			final ImmutableList<DocumentLayoutElementLineDescriptor.Builder> result = layoutElementBuilders.stream()
					.map(layoutElementBuilder -> DocumentLayoutElementLineDescriptor.builder()
							.setInternalName(uiElement.toString())
							.addElement(layoutElementBuilder))
					.collect(ImmutableList.toImmutableList());

			logger.trace("Built layout element lines for {}: {}", uiElement, result);
			return result;
		}
		catch (Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex)
					.setParameter("AD_UI_Element", uiElement)
					.setParameter("AD_Tab_ID", uiElement.getAD_Tab_ID());
		}
	}

	private List<DocumentLayoutElementDescriptor.Builder> layoutElements(@NonNull final I_AD_UI_Element uiElement)
	{
		logger.trace("Building layout element for {}", uiElement);

		if (!uiElement.isActive())
		{
			logger.trace("Skip building layout element for {} because it's not active", uiElement);
			return ImmutableList.of();
		}

		final List<DocumentLayoutElementDescriptor.Builder> result;

		final LayoutElementType layoutElementType = LayoutElementType.ofCode(uiElement.getAD_UI_ElementType());
		if (LayoutElementType.InlineTab.equals(layoutElementType))
		{
			result = layoutElements_InlineTab(uiElement);
		}
		else
		{
			result = layoutElements_Default(uiElement);
		}

		if (result.isEmpty())
		{
			return ImmutableList.of();
		}

		//
		// Collect advanced fields
		result.stream()
				.filter(DocumentLayoutElementDescriptor.Builder::isAdvancedField)
				.forEach(layoutElementBuilder -> descriptorsFactory.addFieldsCharacteristic(layoutElementBuilder.getFieldNames(), Characteristic.AdvancedField));

		logger.trace("Built layout elements for {}: {}", uiElement, result);
		return result;
	}

	private List<DocumentLayoutElementDescriptor.Builder> layoutElements_Default(@NonNull final I_AD_UI_Element uiElement)
	{
		final ArrayList<DocumentLayoutElementDescriptor.Builder> result = new ArrayList<>();

		final List<List<DocumentFieldDescriptor.Builder>> fieldsGroupedByElement = extractDocumentFields(uiElement);
		for (final List<DocumentFieldDescriptor.Builder> fields : fieldsGroupedByElement)
		{
			// UI main field
			final DocumentLayoutElementDescriptor.Builder layoutElementBuilder = DocumentLayoutElementDescriptor.builder()
					.setInternalName(uiElement.toString())
					.setLayoutType(LayoutType.fromNullable(uiElement.getUIStyle()))
					.setWidgetSize(WidgetSize.fromNullableADRefListValue(uiElement.getWidgetSize()))
					.setMultilineText(uiElement.isMultiLine())
					.setMultilineTextLines(extractMultiLineLinesCount(uiElement))
					.setAdvancedField(uiElement.isAdvancedField())
					.restrictToMediaTypes(MediaType.fromNullableCommaSeparatedString(uiElement.getMediaTypes()));

			for (final DocumentFieldDescriptor.Builder field : fields)
			{
				final DocumentLayoutElementFieldDescriptor.Builder layoutElementFieldBuilder = layoutElementField(field);

				if (layoutElementBuilder.getFieldsCount() <= 0)
				{
					layoutElementBuilder.setCaption(field.getCaption());
					layoutElementBuilder.setDescription(field.getDescription());
				}

				//
				// Element Widget type
				if (!layoutElementBuilder.isWidgetTypeSet())
				{
					layoutElementBuilder.setWidgetType(field.getWidgetType());
					layoutElementBuilder.setMaxLength(field.getFieldMaxLength());
				}

				if (!layoutElementBuilder.isWidgetSizeSet())
				{
					layoutElementBuilder.setWidgetSize(field.getWidgetSize());
				}

				layoutElementBuilder.setButtonActionDescriptor(field.getButtonActionDescriptor());

				layoutElementBuilder.addField(layoutElementFieldBuilder);
			}

			if (layoutElementBuilder.getFieldsCount() <= 0)
			{
				logger.trace("Skip layout element for {} because it has no fields: {}", uiElement, layoutElementBuilder);
				continue;
			}

			final ViewEditorRenderMode viewEditorRenderMode = computeViewEditorRenderMode(uiElement, layoutElementBuilder.getWidgetType());
			layoutElementBuilder.setViewEditorRenderMode(viewEditorRenderMode);

			//
			result.add(layoutElementBuilder);
		}

		return result;
	}

	@NonNull
	private List<DocumentLayoutElementDescriptor.Builder> layoutElements_InlineTab(@NonNull final I_AD_UI_Element uiElement)
	{
		final AdTabId inlineTabId = AdTabId.ofRepoId(uiElement.getInline_Tab_ID());

		return ImmutableList.of(
				DocumentLayoutElementDescriptor.builder()
						.setInternalName(uiElement.toString())
						.setLayoutType(LayoutType.fromNullable(uiElement.getUIStyle()))
						.setWidgetSize(WidgetSize.fromNullableADRefListValue(uiElement.getWidgetSize()))
						.setAdvancedField(uiElement.isAdvancedField())
						.restrictToMediaTypes(MediaType.fromNullableCommaSeparatedString(uiElement.getMediaTypes()))
						.setWidgetType(DocumentFieldWidgetType.InlineTab)
						.setInlineTabId(DetailId.fromAD_Tab_ID(inlineTabId))
						.setCaption(TranslatableStrings.empty())
						.setDescription(TranslatableStrings.empty())
		);
	}

	private static int extractMultiLineLinesCount(final I_AD_UI_Element uiElement)
	{
		if (!uiElement.isMultiLine())
		{
			return 0;
		}

		final int linesCount = uiElement.getMultiLine_LinesCount();
		return linesCount > 0 ? linesCount : DEFAULT_MultiLine_LinesCount;
	}

	/**
	 * @implSpec task <a href="https://github.com/metasfresh/metasfresh-webui-api/issues/778">778</a>
	 */
	private ViewEditorRenderMode computeViewEditorRenderMode(
			@NonNull final I_AD_UI_Element uiElement,
			final DocumentFieldWidgetType widgetType)
	{
		final AdFieldId adFieldId = AdFieldId.ofRepoIdOrNull(uiElement.getAD_Field_ID());
		if (adFieldId == null)
		{
			return ViewEditorRenderMode.NEVER;
		}

		final DocumentFieldDescriptor.Builder field = descriptorsFactory.documentFieldByAD_Field_ID(adFieldId);
		final boolean readOnly = field != null && field.getReadonlyLogicEffective().isConstantTrue();
		if (readOnly)
		{
			return ViewEditorRenderMode.NEVER;
		}

		final ViewEditorRenderMode viewEditMode = ViewEditorRenderMode.ofNullableCode(uiElement.getViewEditMode());
		if (viewEditMode != null)
		{
			return viewEditMode;
		}

		// if we can't tell the mode from field, then use our "old" logic
		if (widgetType == DocumentFieldWidgetType.Amount
				|| widgetType == DocumentFieldWidgetType.CostPrice
				|| widgetType == DocumentFieldWidgetType.Quantity)
		{
			return ViewEditorRenderMode.ON_DEMAND;
		}
		else
		{
			return ViewEditorRenderMode.NEVER;
		}
	}

	private List<List<DocumentFieldDescriptor.Builder>> extractDocumentFields(final I_AD_UI_Element uiElement)
	{
		final LayoutElementType uiElementType = LayoutElementType.ofCode(uiElement.getAD_UI_ElementType());
		if (LayoutElementType.Field.equals(uiElementType))
		{
			return extractDocumentFields_Field(uiElement);
		}
		else if (LayoutElementType.Labels.equals(uiElementType))
		{
			return extractDocumentFields_Labels(uiElement);
		}
		else if (LayoutElementType.InlineTab.equals(uiElementType))
		{
			throw new AdempiereException("InlineTab element has no fields: " + uiElement);
		}
		else if (LayoutElementType.Attributes.equals(uiElementType))
		{
			return extractDocumentFields_Attributes(uiElement);
		}
		else
		{
			throw new AdempiereException("Unknown AD_UI_ElementType: " + uiElementType + "  for " + uiElement);
		}
	}

	private List<List<DocumentFieldDescriptor.Builder>> extractDocumentFields_Field(final I_AD_UI_Element uiElement)
	{
		final ArrayList<DocumentFieldDescriptor.Builder> fields = new ArrayList<>();

		// add the "primary" field
		{
			final DocumentFieldDescriptor.Builder field = descriptorsFactory.documentFieldByAD_Field_ID(AdFieldId.ofRepoId(uiElement.getAD_Field_ID()));
			if (field != null && isSkipField(field))
			{
				logger.trace("Skip field {} because it's not displayed", field.getFieldName());
			}
			else if (field != null)
			{
				fields.add(field);
			}
			else
			{
				logger.warn("No field found for AD_Field_ID={}; AD_UI_Element={}", uiElement.getAD_Field_ID(), uiElement);
			}
		}

		// add additional fields / tooltips (if any)
		for (final I_AD_UI_ElementField uiElementField : getUIProvider().getUIElementFields(uiElement))
		{
			if (!uiElementField.isActive())
			{
				logger.trace("Skip {} because it's not active", uiElementField);
				continue;
			}

			final DocumentFieldDescriptor.Builder field = descriptorsFactory.documentFieldByAD_UI_ElementField(uiElementField);
			if (field == null)
			{
				logger.warn("No field found for AD_UI_ElementField_ID={}; AD_UI_ElementField={}", uiElementField.getAD_Field_ID(), uiElementField);
				continue;
			}

			fields.add(field);
		}

		return ImmutableList.of(fields);
	}

	private List<List<DocumentFieldDescriptor.Builder>> extractDocumentFields_Labels(final I_AD_UI_Element uiElement)
	{
		final String labelsFieldName = descriptorsFactory.getLabelsFieldName(AdUIElementId.ofRepoId(uiElement.getAD_UI_Element_ID()));
		final DocumentFieldDescriptor.Builder field = descriptorsFactory.documentField(labelsFieldName);
		if (field == null)
		{
			logger.warn("No label field found for labelsFieldName={}", labelsFieldName);
			return ImmutableList.of();
		}
		else
		{
			return ImmutableList.of(ImmutableList.of(field));
		}
	}

	private List<List<DocumentFieldDescriptor.Builder>> extractDocumentFields_Attributes(final I_AD_UI_Element uiElement)
	{
		final ArrayList<List<DocumentFieldDescriptor.Builder>> result = new ArrayList<>();

		final AdUIElementId uiElementId = AdUIElementId.ofRepoId(uiElement.getAD_UI_Element_ID());
		attributesUIElementTypeFactory.getGeneratedFieldNames(uiElementId)
				.stream()
				.distinct()
				.forEach(fieldName -> {
					final DocumentFieldDescriptor.Builder field = descriptorsFactory.documentField(fieldName);
					if (field == null)
					{
						logger.warn("No generated field found for name={}", fieldName);
					}
					else
					{
						result.add(ImmutableList.of(field));
					}
				});

		return result;
	}

	public ViewLayout.Builder layoutGridView()
	{
		final DocumentEntityDescriptor.Builder entityDescriptor = documentEntity();
		logger.trace("Generating grid view layout for {}", entityDescriptor);

		final ViewLayout.Builder layout = ViewLayout.builder()
				.setDetailId(entityDescriptor.getDetailId())
				.setCaption(entityDescriptor.getCaption())
				.setDescription(entityDescriptor.getDescription())
				.setEmptyResultText(TranslatableStrings.adMessage(TAB_EMPTY_RESULT_TEXT))
				.setEmptyResultHint(TranslatableStrings.adMessage(TAB_EMPTY_RESULT_HINT))
				.setPageLength(entityDescriptor.getViewPageLength())
				.setIdFieldName(entityDescriptor.getSingleIdFieldNameOrNull())
				.setDefaultOrderBys(entityDescriptor.getDefaultOrderBys());

		//
		// Create UI elements from AD_UI_Elements which were marked as DisplayedGrid
		{
			streamAD_UI_Elements()
					.filter(I_AD_UI_Element::isDisplayedGrid)
					.sorted(Comparator.comparing(I_AD_UI_Element::getSeqNoGrid))
					.flatMap(adUIElement -> layoutElements(adUIElement).stream())
					.filter(Objects::nonNull)
					.peek(DocumentLayoutElementDescriptor.Builder::setGridElement)
					.forEach(layout::addElement);
		}

		//
		// Fallback: when no elements were found: creating the view using the single row layout
		if (!layout.hasElements())
		{
			logger.debug("No grid layout was found for {}. Trying to create one based on single row layout elements", entityDescriptor);
			streamAD_UI_Elements()
					.filter(adUIElement -> adUIElement.isDisplayed() && !adUIElement.isAdvancedField())
					.flatMap(adUIElement -> layoutElements(adUIElement).stream())
					.filter(Objects::nonNull)
					.peek(DocumentLayoutElementDescriptor.Builder::setGridElement)
					.forEach(layout::addElement);
		}

		//
		// Fallback:
		if (!layout.hasElements())
		{
			logger.warn("No grid layout found for {}. Continuing", entityDescriptor);
		}

		//
		// Make sure all added elements have the GridViewField characteristic
		descriptorsFactory.addFieldsCharacteristic(layout.getFieldNames(), Characteristic.GridViewField);

		return layout;
	}

	/**
	 * @return included entity grid layout
	 */
	public Optional<DocumentLayoutDetailDescriptor.Builder> layoutDetail()
	{
		final DocumentEntityDescriptor.Builder entityDescriptor = documentEntity();
		logger.trace("Generating layout detail for {}", entityDescriptor);

		// If the detail is never displayed then don't add it to layout
		final ILogicExpression tabDisplayLogic = descriptorsFactory.getTabDisplayLogic();
		if (tabDisplayLogic.isConstantFalse())
		{
			logger.trace("Skip adding detail tab to layout because it's never displayed: {}, tabDisplayLogic={}", entityDescriptor, tabDisplayLogic);
			return Optional.empty();
		}

		@NonNull final Builder layoutSingleRow = Objects.requireNonNull(layoutSingleRow());

		final DocumentLayoutDetailDescriptor.Builder builder = DocumentLayoutDetailDescriptor
				.builder(entityDescriptor.getWindowId(), entityDescriptor.getDetailId())
				.caption(entityDescriptor.getCaption())
				.description(entityDescriptor.getDescription())
				.internalName(entityDescriptor.getInternalName())
				.gridLayout(layoutGridView())
				.singleRowLayout(layoutSingleRow)
				.queryOnActivate(entityDescriptor.isQueryIncludedTabOnActivate())
				.quickInputSupport(extractQuickInputSupport(entityDescriptor))
				.newRecordInputMode(entityDescriptor.getIncludedTabNewRecordInputMode());
		return Optional.of(builder);
	}

	@Nullable
	private QuickInputSupportDescriptor extractQuickInputSupport(final DocumentEntityDescriptor.Builder entityDescriptor)
	{
		final QuickInputSupportDescriptor quickInputSupport = entityDescriptor.getQuickInputSupport();
		if (quickInputSupport == null)
		{
			return null;
		}

		if (!services.hasQuickInputEntityDescriptor(entityDescriptor))
		{
			return null;
		}

		return quickInputSupport;
	}

	private DocumentLayoutElementFieldDescriptor.Builder layoutElementField(final DocumentFieldDescriptor.Builder field)
	{
		logger.trace("Building layout element field for {}", field);

		final String fieldName = field.getFieldName();
		if (!field.hasCharacteristic(Characteristic.PublicField) && field.isPossiblePublicField())
		{
			field.addCharacteristic(Characteristic.PublicField);
		}

		final DocumentLayoutElementFieldDescriptor.Builder layoutElementFieldBuilder = DocumentLayoutElementFieldDescriptor.builder(fieldName)
				.setCaption(field.getCaption())
				.setLookupInfos(field.getLookupDescriptor().orElse(null))
				.setPublicField(field.hasCharacteristic(Characteristic.PublicField))
				.setSupportZoomInto(field.isSupportZoomInto())
				.trackField(field)
				.setForbidNewRecordCreation(field.isForbidNewRecordCreation());

		if (!Check.isEmpty(field.getTooltipIconName()))
		{
			layoutElementFieldBuilder.setFieldType(FieldType.Tooltip);
			layoutElementFieldBuilder.setTooltipIconName(field.getTooltipIconName());
		}

		logger.trace("Built layout element field for {}: {}", field, layoutElementFieldBuilder);
		return layoutElementFieldBuilder;
	}

	public final ViewLayout layoutSideListView()
	{
		final ViewLayout.Builder layoutBuilder = ViewLayout.builder()
				.setWindowId(WindowId.of(adWindowId))
				.setEmptyResultText(TranslatableStrings.adMessage(TAB_EMPTY_RESULT_TEXT))
				.setEmptyResultHint(TranslatableStrings.adMessage(TAB_EMPTY_RESULT_HINT));

		//
		// Create UI elements from AD_UI_Elements which were marked as DisplayedGrid
		streamAD_UI_Elements()
				// .peek((uiElement)->System.out.println("UI ELEMENT: "+uiElement + ", SIDE="+uiElement.isDisplayed_SideList()))
				.filter(I_AD_UI_Element::isDisplayed_SideList)
				.sorted(Comparator.comparing(I_AD_UI_Element::getSeqNo_SideList))
				.flatMap(adUIElement -> layoutElements(adUIElement).stream())
				.filter(Objects::nonNull) // avoid NPE
				.map(DocumentLayoutElementDescriptor.Builder::setGridElement)
				.forEach(layoutBuilder::addElement);

		//
		// Fallback: when no elements were found: creating the view using the single row layout
		if (!layoutBuilder.hasElements())
		{
			logger.debug("No side list layout was found for {}. Trying to create one based on single row layout elements", this);
			streamAD_UI_Elements()
					.filter(adUIElement -> adUIElement.isDisplayed() && !adUIElement.isAdvancedField())
					.flatMap(adUIElement -> layoutElements(adUIElement).stream())
					.filter(Objects::nonNull)
					.peek(DocumentLayoutElementDescriptor.Builder::setGridElement)
					.forEach(layoutBuilder::addElement);
		}

		//
		// Fallback:
		if (!layoutBuilder.hasElements())
		{
			logger.warn("No side list layout found for {}. Continuing", this);
		}

		//
		// Make sure all added elements have the SideListField characteristic
		descriptorsFactory.addFieldsCharacteristic(layoutBuilder.getFieldNames(), Characteristic.SideListField);

		return layoutBuilder.build();
	}

	public DocumentEntityDescriptor.Builder documentEntity()
	{
		return descriptorsFactory.documentEntity();
	}

	@Nullable
	public DocumentLayoutElementDescriptor createSpecialElement_DocumentSummary()
	{
		final DocumentFieldDescriptor.Builder field = descriptorsFactory.getSpecialField_DocumentSummary();
		if (field == null)
		{
			return null;
		}

		return DocumentLayoutElementDescriptor.builder()
				.setCaptionNone() // not relevant
				.setDescription(null) // not relevant
				.setLayoutTypeNone() // not relevant
				.setWidgetType(field.getWidgetType())
				.setMaxLength(field.getFieldMaxLength())
				.addField(layoutElementField(field))
				.build();
	}

	@Nullable
	public DocumentLayoutElementDescriptor createSpecialElement_DocStatusAndDocAction()
	{
		final Map<Characteristic, DocumentFieldDescriptor.Builder> fields = descriptorsFactory.getSpecialField_DocSatusAndDocAction();
		if (fields == null || fields.isEmpty())
		{
			return null;
		}

		final DocumentFieldDescriptor.Builder docStatusField = fields.get(Characteristic.SpecialField_DocStatus);
		final DocumentFieldDescriptor.Builder docActionField = fields.get(Characteristic.SpecialField_DocAction);

		return DocumentLayoutElementDescriptor.builder()
				.setCaptionNone() // not relevant
				.setDescription(null) // not relevant
				.setLayoutTypeNone() // not relevant
				.setWidgetType(DocumentFieldWidgetType.ActionButton)
				.addField(layoutElementField(docStatusField).setFieldType(FieldType.ActionButtonStatus))
				.addField(layoutElementField(docActionField).setFieldType(FieldType.ActionButton))
				.build();
	}

	private boolean isSkipField(@NonNull final DocumentFieldDescriptor.Builder field)
	{
		switch (field.getFieldName())
		{
			case FIELDNAME_AD_Org_ID:
				return !services.getSysConfigBooleanValue(SYS_CONFIG_AD_ORG_ID_IS_DISPLAYED, true);
			case FIELDNAME_AD_Client_ID:
				return !services.getSysConfigBooleanValue(SYS_CONFIG_AD_CLIENT_ID_IS_DISPLAYED, true);
			default:
				return false;
		}
	}
}
