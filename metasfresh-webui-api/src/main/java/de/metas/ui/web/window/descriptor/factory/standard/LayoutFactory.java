package de.metas.ui.web.window.descriptor.factory.standard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.adempiere.ad.expression.api.ILogicExpression;
import org.compiere.model.GridTabVO;
import org.compiere.model.GridWindowVO;
import org.compiere.model.I_AD_UI_Column;
import org.compiere.model.I_AD_UI_Element;
import org.compiere.model.I_AD_UI_ElementField;
import org.compiere.model.I_AD_UI_ElementGroup;
import org.compiere.model.I_AD_UI_Section;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.logging.LogManager;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.DocumentLayoutColumnDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutDetailDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutDetailQuickInputDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.FieldType;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementGroupDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementLineDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutSectionDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutSideListDescriptor;
import de.metas.ui.web.window.descriptor.LayoutType;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/*package */class LayoutFactory
{
	// services
	private static final transient Logger logger = LogManager.getLogger(LayoutFactory.class);

	// FIXME TRL HARDCODED_TAB_EMPTY_RESULT_TEXT
	private static final ITranslatableString HARDCODED_TAB_EMPTY_RESULT_TEXT = ImmutableTranslatableString.builder()
			.setDefaultValue("There are no detail rows")
			.put("de_DE", "Es sind noch keine Detailzeilen vorhanden.")
			.put("de_CH", "Es sind noch keine Detailzeilen vorhanden.")
			.build();

	// FIXME TRL HARDCODED_TAB_EMPTY_RESULT_TEXT
	private static final ITranslatableString HARDCODED_TAB_EMPTY_RESULT_HINT = ImmutableTranslatableString.builder()
			.setDefaultValue("You can create them in this window.")
			.put("de_DE", "Du kannst sie im jeweiligen Fenster erfassen.")
			.put("de_CH", "Du kannst sie im jeweiligen Fenster erfassen.")
			.build();

	//
	// Parameters
	private final GridTabVOBasedDocumentEntityDescriptorFactory descriptorsFactory;
	private final int _adWindowId;

	//
	// Build parameters
	private IWindowUIElementsProvider _uiProvider;
	private final List<I_AD_UI_Section> _uiSections;

	public LayoutFactory(final GridWindowVO gridWindowVO, final GridTabVO gridTabVO, final GridTabVO parentTab)
	{
		super();
		descriptorsFactory = new GridTabVOBasedDocumentEntityDescriptorFactory(gridTabVO, parentTab, gridWindowVO.isSOTrx());
		_adWindowId = gridTabVO.getAD_Window_ID();

		//
		// Pick the right UI elements provider (DAO, fallback to InMemory),
		// and fetch the UI sections
		{
			IWindowUIElementsProvider uiProvider = new DAOWindowUIElementsProvider();
			final int AD_Tab_ID = gridTabVO.getAD_Tab_ID();
			List<I_AD_UI_Section> uiSections = uiProvider.getUISections(AD_Tab_ID);
			if (uiSections.isEmpty())
			{
				uiProvider = new InMemoryUIElementsProvider();
				logger.warn("No UI Sections found for {}. Switching to {}", gridTabVO, uiProvider);

				uiSections = uiProvider.getUISections(AD_Tab_ID);
			}

			_uiSections = ImmutableList.copyOf(uiSections);
			logger.trace("UI sections: {}", _uiSections);

			_uiProvider = uiProvider;
			logger.trace("Using UI provider: {}", _uiProvider);
		}
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("AD_Window_ID", _adWindowId)
				.add("AD_UI_Sections.count", getUISections().size())
				.add("UIProvider", getUIProvider())
				.toString();
	}

	private IWindowUIElementsProvider getUIProvider()
	{
		return _uiProvider;
	}

	private final List<I_AD_UI_Section> getUISections()
	{
		return _uiSections;
	}

	private final Stream<I_AD_UI_Element> streamAD_UI_Elements()
	{
		return getUISections()
				.stream()
				.flatMap(uiSection -> getUIProvider().getUIColumns(uiSection).stream())
				.flatMap(uiColumn -> getUIProvider().getUIElementGroups(uiColumn).stream())
				.flatMap(uiElementGroup -> getUIProvider().getUIElements(uiElementGroup).stream());
	}

	private int getAD_Window_ID()
	{
		return _adWindowId;
	}

	/**
	 * Single row layout: sections list
	 */
	public List<DocumentLayoutSectionDescriptor.Builder> layoutSectionsList()
	{
		final List<I_AD_UI_Section> uiSections = getUISections();
		logger.trace("Generating layout sections list for {}", uiSections);

		//
		// UI Sections
		final List<DocumentLayoutSectionDescriptor.Builder> layoutSectionBuilders = new ArrayList<>();
		for (final I_AD_UI_Section uiSection : uiSections)
		{
			layoutSectionBuilders.add(layoutSection(uiSection)
					.setExcludeSpecialFields());
		}

		return layoutSectionBuilders;
	}

	/**
	 * Single row layout: section
	 */
	private DocumentLayoutSectionDescriptor.Builder layoutSection(final I_AD_UI_Section uiSection)
	{
		final DocumentLayoutSectionDescriptor.Builder layoutSectionBuilder = DocumentLayoutSectionDescriptor.builder()
				.setInternalName(uiSection.toString());

		if (!uiSection.isActive())
		{
			return layoutSectionBuilder.setInvalid("UI section not active: " + uiSection);
		}

		//
		// UI Columns
		for (final I_AD_UI_Column uiColumn : getUIProvider().getUIColumns(uiSection))
		{
			final DocumentLayoutColumnDescriptor.Builder layoutColumnBuilder = layoutColumn(uiColumn);
			if (layoutColumnBuilder == null)
			{
				continue;
			}
			layoutSectionBuilder.addColumn(layoutColumnBuilder);
		}

		return layoutSectionBuilder;
	}

	private DocumentLayoutColumnDescriptor.Builder layoutColumn(final I_AD_UI_Column uiColumn)
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
			final DocumentLayoutElementGroupDescriptor.Builder layoutElementGroupBuilder = layoutElementGroup(uiElementGroup);
			if (layoutElementGroupBuilder == null)
			{
				continue;
			}
			layoutColumnBuilder.addElementGroup(layoutElementGroupBuilder);
		}

		return layoutColumnBuilder;
	}

	private DocumentLayoutElementGroupDescriptor.Builder layoutElementGroup(final I_AD_UI_ElementGroup uiElementGroup)
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

			final DocumentLayoutElementLineDescriptor.Builder layoutElementLineBuilder = layoutElementLine(uiElement, layoutElementGroupBuilder);
			if (layoutElementLineBuilder == null)
			{
				continue;
			}
			if (!layoutElementLineBuilder.hasElements())
			{
				logger.trace("Skip {} because it's empty", layoutElementLineBuilder);
				continue;
			}

			layoutElementGroupBuilder.addElementLine(layoutElementLineBuilder);
		}

		logger.trace("Built layout element group for {}: {}", uiElementGroup, layoutElementGroupBuilder);
		return layoutElementGroupBuilder;
	}

	private DocumentLayoutElementLineDescriptor.Builder layoutElementLine(final I_AD_UI_Element uiElement, final DocumentLayoutElementGroupDescriptor.Builder layoutElementGroupBuilder)
	{
		logger.trace("Building layout element line for {}", uiElement);

		final DocumentLayoutElementDescriptor.Builder layoutElementBuilder = layoutElement(uiElement);
		if (layoutElementBuilder == null)
		{
			logger.trace("Skip building layout element line because got null layout element", uiElement);
			return null;
		}

		final DocumentLayoutElementLineDescriptor.Builder layoutElementLineBuilder = DocumentLayoutElementLineDescriptor.builder()
				.setInternalName(uiElement.toString())
				.addElement(layoutElementBuilder);

		logger.trace("Built layout element line for {}: {}", uiElement, layoutElementLineBuilder);
		return layoutElementLineBuilder;
	}

	private DocumentLayoutElementDescriptor.Builder layoutElement(final I_AD_UI_Element uiElement)
	{
		logger.trace("Building layout element for {}", uiElement);

		if (!uiElement.isActive())
		{
			logger.trace("Skip building layout element for {} because it's not active", uiElement);
			return null;
		}

		final LayoutType layoutType = LayoutType.fromNullable(uiElement.getUIStyle());

		//
		// UI main field
		final DocumentLayoutElementDescriptor.Builder layoutElementBuilder = DocumentLayoutElementDescriptor.builder()
				.setInternalName(uiElement.toString())
				.setLayoutType(layoutType)
				.setAdvancedField(uiElement.isAdvancedField());

		//
		// Fields
		for (final DocumentFieldDescriptor.Builder field : extractDocumentFields(uiElement))
		{
			final DocumentLayoutElementFieldDescriptor.Builder layoutElementFieldBuilder = layoutElementField(field);

			if (layoutElementBuilder.getFieldsCount() <= 0)
			{
				layoutElementBuilder.setCaption(field.getCaption());
				layoutElementBuilder.setDescription(field.getDescription());
			}

			//
			// Element Widget type
			if (layoutElementBuilder.getWidgetType() == null)
			{
				layoutElementBuilder.setWidgetType(field.getWidgetType());
			}

			layoutElementBuilder.addField(layoutElementFieldBuilder);
		}

		// NOTE: per jassy request, when dealing with composed lookup fields, first field shall be Lookup and not List.
		if (layoutElementBuilder.getFieldsCount() > 1)
		{
			final DocumentLayoutElementFieldDescriptor.Builder layoutElementFieldBuilder = layoutElementBuilder.getFirstField();
			if (layoutElementFieldBuilder.isLookup())
			{
				layoutElementBuilder.setWidgetType(DocumentFieldWidgetType.Lookup);
				layoutElementFieldBuilder.setLookupSource(LookupSource.lookup);
			}
		}

		//
		// Collect advanced fields
		if (layoutElementBuilder.isAdvancedField())
		{
			descriptorsFactory.addFieldsCharacteristic(layoutElementBuilder.getFieldNames(), Characteristic.AdvancedField);
		}

		logger.trace("Built layout element for {}: {}", uiElement, layoutElementBuilder);
		return layoutElementBuilder;
	}

	private List<DocumentFieldDescriptor.Builder> extractDocumentFields(final I_AD_UI_Element uiElement)
	{
		final List<DocumentFieldDescriptor.Builder> fields = new ArrayList<>();

		{
			final DocumentFieldDescriptor.Builder field = descriptorsFactory.documentFieldByAD_Field_ID(uiElement.getAD_Field_ID());
			if (field != null)
			{
				fields.add(field);
			}
			else
			{
				logger.warn("No field found for {} (AD_Field_ID={})", uiElement, uiElement.getAD_Field_ID());
			}
		}

		for (final I_AD_UI_ElementField uiElementField : getUIProvider().getUIElementFields(uiElement))
		{
			if (!uiElementField.isActive())
			{
				logger.trace("Skip {} because it's not active", uiElementField);
				continue;
			}

			final DocumentFieldDescriptor.Builder field = descriptorsFactory.documentFieldByAD_Field_ID(uiElementField.getAD_Field_ID());
			if (field == null)
			{
				logger.warn("No field found for {} (AD_Field_ID={})", uiElementField, uiElementField.getAD_Field_ID());
				continue;
			}

			fields.add(field);
		}

		return fields;
	}

	public DocumentLayoutDetailDescriptor.Builder layoutDetail()
	{
		final DocumentEntityDescriptor.Builder entityDescriptor = descriptorsFactory.documentEntity();
		logger.trace("Generating layout detail for {}", entityDescriptor);

		// If the detail is never displayed then don't add it to layout
		final ILogicExpression tabDisplayLogic = descriptorsFactory.getTabDisplayLogic();
		if (tabDisplayLogic.isConstantFalse())
		{
			logger.trace("Skip adding detail tab to layout because it's never displayed: {}, tabDisplayLogic={}", entityDescriptor, tabDisplayLogic);
			return null;
		}

		final DocumentLayoutDetailDescriptor.Builder layoutDetail = DocumentLayoutDetailDescriptor.builder()
				.setDetailId(entityDescriptor.getDetailId())
				.setCaption(entityDescriptor.getCaption())
				.setDescription(entityDescriptor.getDescription())
				.setEmptyResultText(HARDCODED_TAB_EMPTY_RESULT_TEXT)
				.setEmptyResultHint(HARDCODED_TAB_EMPTY_RESULT_HINT);

		//
		// Create UI elements from AD_UI_Elements which were marked as DisplayedGrid
		{
			streamAD_UI_Elements()
					.filter(adUIElement -> adUIElement.isDisplayedGrid())
					.sorted(Comparator.comparing(I_AD_UI_Element::getSeqNoGrid))
					.map(adUIElement -> layoutElement(adUIElement))
					.filter(uiElement -> uiElement != null)
					.peek(uiElement -> uiElement.setGridElement())
					.forEach(layoutDetail::addElement);
		}

		//
		// Fallback: when no elements were found: creating the view using the single row layout
		if (!layoutDetail.hasElements())
		{
			logger.warn("No grid layout was found for {}. Trying to create one based on single row layout elements", entityDescriptor);
			streamAD_UI_Elements()
					.filter(adUIElement -> adUIElement.isDisplayed() && !adUIElement.isAdvancedField())
					.map(adUIElement -> layoutElement(adUIElement))
					.filter(uiElement -> uiElement != null)
					.peek(uiElement -> uiElement.setGridElement())
					.forEach(layoutDetail::addElement);
		}

		//
		// Fallback:
		if (!layoutDetail.hasElements())
		{
			logger.warn("No grid layout found for {}. Continuing", entityDescriptor);
		}

		//
		// Make sure all added elements have the GridViewField characteristic
		descriptorsFactory.addFieldsCharacteristic(layoutDetail.getFieldNames(), Characteristic.GridViewField);

		//
		// Quick input
		layoutDetail.setQuickInput(layoutDetail_QuickInput());

		return layoutDetail;
	}

	public DocumentLayoutDetailQuickInputDescriptor.Builder layoutDetail_QuickInput()
	{
		final DocumentEntityDescriptor.Builder documentEntityDescriptor = documentEntity();
		final DocumentEntityDescriptor.Builder quickInputDescriptor = documentEntityDescriptor.getQuickInputDescriptor();
		if (quickInputDescriptor == null)
		{
			return null;
		}

		// FIXME: hardcoded quickInput for Order lines

		if (!org.compiere.model.I_C_OrderLine.Table_Name.equals(documentEntityDescriptor.getTableName()))
		{
			return null;
		}

		final DocumentFieldDescriptor.Builder field_M_Product_ID = quickInputDescriptor.getFieldBuilder("M_Product_ID");
		final DocumentFieldDescriptor.Builder field_M_HU_PI_Item_Product_ID = quickInputDescriptor.getFieldBuilder("M_HU_PI_Item_Product_ID");
		final DocumentFieldDescriptor.Builder field_Qty = quickInputDescriptor.getFieldBuilder("Qty");

		final DocumentLayoutDetailQuickInputDescriptor.Builder quickInputLayout = DocumentLayoutDetailQuickInputDescriptor.builder()
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setWidgetType(field_M_Product_ID.getWidgetType())
						.addField(DocumentLayoutElementFieldDescriptor.builder(field_M_Product_ID.getFieldName())
								.setPublicField(true)
								.setLookupSource(field_M_Product_ID.getLookupSourceType()))
						.addField(DocumentLayoutElementFieldDescriptor.builder(field_M_HU_PI_Item_Product_ID.getFieldName())
								.setPublicField(true)
								.setLookupSource(field_M_HU_PI_Item_Product_ID.getLookupSourceType())))
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setWidgetType(field_Qty.getWidgetType())
						.addField(DocumentLayoutElementFieldDescriptor.builder(field_Qty.getFieldName())
								.setPublicField(true)
								.setLookupSource(field_Qty.getLookupSourceType())));

		return quickInputLayout;
	}

	/**
	 * Build the layout for advanced view (for header documents).
	 *
	 * @task https://github.com/metasfresh/metasfresh-webui/issues/26
	 */
	public DocumentLayoutDetailDescriptor.Builder layoutAdvancedView()
	{
		// NOTE, according to (FRESH-686 #26), we are putting all elements in one list, one after another, no sections, no columns etc
		final DocumentEntityDescriptor.Builder entityDescriptor = descriptorsFactory.documentEntity();
		logger.trace("Generating advanced view layout for {}", entityDescriptor);

		// If the detail is never displayed then don't add it to layout
		final ILogicExpression tabDisplayLogic = descriptorsFactory.getTabDisplayLogic();
		if (tabDisplayLogic.isConstantFalse())
		{
			logger.trace("Skip adding advanced view layout because it's never displayed: {}, tabDisplayLogic={}", entityDescriptor, tabDisplayLogic);
			return null;
		}

		final Stream<DocumentLayoutElementDescriptor.Builder> layoutElements = streamAD_UI_Elements()
				.filter(uiElement -> uiElement.isDisplayed())
				.sorted(Comparator.comparing(I_AD_UI_Element::getSeqNo))
				.map(uiElement -> layoutElement(uiElement).setNotGridElement())
				.filter(uiElement -> uiElement != null);

		final DocumentLayoutDetailDescriptor.Builder advancedViewLayout = DocumentLayoutDetailDescriptor.builder()
				.setDetailId(entityDescriptor.getDetailId())
				.setCaption(entityDescriptor.getCaption())
				.setDescription(entityDescriptor.getDescription())
				.setEmptyResultText(HARDCODED_TAB_EMPTY_RESULT_TEXT)
				.setEmptyResultHint(HARDCODED_TAB_EMPTY_RESULT_HINT)
				.addElements(layoutElements);

		return advancedViewLayout;
	}

	private final DocumentLayoutElementFieldDescriptor.Builder layoutElementField(final DocumentFieldDescriptor.Builder field)
	{
		logger.trace("Building layout element field for {}", field);

		final String fieldName = field.getFieldName();
		if (!field.hasCharacteristic(Characteristic.PublicField) && field.isPossiblePublicField())
		{
			field.addCharacteristic(Characteristic.PublicField);
		}

		final DocumentLayoutElementFieldDescriptor.Builder layoutElementFieldBuilder = DocumentLayoutElementFieldDescriptor.builder(fieldName)
				.setLookupSource(field.getLookupSourceType())
				.setPublicField(field.hasCharacteristic(Characteristic.PublicField))
				.trackField(field);

		logger.trace("Built layout element field for {}: {}", field, layoutElementFieldBuilder);
		return layoutElementFieldBuilder;
	}

	public final DocumentLayoutSideListDescriptor layoutSideList()
	{
		final DocumentLayoutSideListDescriptor.Builder layoutSideListBuilder = DocumentLayoutSideListDescriptor.builder()
				.setAD_Window_ID(getAD_Window_ID())
				.setEmptyResultText(HARDCODED_TAB_EMPTY_RESULT_TEXT)
				.setEmptyResultHint(HARDCODED_TAB_EMPTY_RESULT_HINT);

		//
		// Create UI elements from AD_UI_Elements which were marked as DisplayedGrid
		streamAD_UI_Elements()
				// .peek((uiElement)->System.out.println("UI ELEMENT: "+uiElement + ", SIDE="+uiElement.isDisplayed_SideList()))
				.filter(uiElement -> uiElement.isDisplayed_SideList())
				.sorted(Comparator.comparing(I_AD_UI_Element::getSeqNo_SideList))
				.map(uiElement -> layoutElement(uiElement).setGridElement())
				.filter(uiElement -> uiElement != null)
				.forEach(layoutSideListBuilder::addElement);

		//
		// Fallback: when no elements were found: creating the view using the single row layout
		if (!layoutSideListBuilder.hasElements())
		{
			logger.warn("No side list layout was found for {}. Trying to create one based on single row layout elements", this);
			streamAD_UI_Elements()
					.filter(adUIElement -> adUIElement.isDisplayed() && !adUIElement.isAdvancedField())
					.map(adUIElement -> layoutElement(adUIElement))
					.filter(uiElement -> uiElement != null)
					.peek(uiElement -> uiElement.setGridElement())
					.forEach(layoutSideListBuilder::addElement);
		}

		//
		// Fallback:
		if (!layoutSideListBuilder.hasElements())
		{
			logger.warn("No side list layout found for {}. Continuing", this);
		}

		//
		// Make sure all added elements have the SideListField characteristic
		descriptorsFactory.addFieldsCharacteristic(layoutSideListBuilder.getFieldNames(), Characteristic.SideListField);

		return layoutSideListBuilder.build();
	}

	public DocumentEntityDescriptor.Builder documentEntity()
	{
		return descriptorsFactory.documentEntity();
	}

	public DocumentLayoutElementDescriptor createSpecialElement_DocumentNo()
	{
		final DocumentFieldDescriptor.Builder field = descriptorsFactory.getSpecialField_DocumentNo();
		if (field == null)
		{
			return null;
		}

		return DocumentLayoutElementDescriptor.builder()
				.setCaption(null) // not relevant
				.setDescription(null) // not relevant
				.setLayoutTypeNone() // not relevant
				.setWidgetType(field.getWidgetType())
				.addField(layoutElementField(field))
				.build();
	}

	public DocumentLayoutElementDescriptor createSpecialElement_DocumentSummary()
	{
		final DocumentFieldDescriptor.Builder field = descriptorsFactory.getSpecialField_DocumentSummary();
		if (field == null)
		{
			return null;
		}

		return DocumentLayoutElementDescriptor.builder()
				.setCaption(null) // not relevant
				.setDescription(null) // not relevant
				.setLayoutTypeNone() // not relevant
				.setWidgetType(field.getWidgetType())
				.addField(layoutElementField(field))
				.build();
	}

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
				.setCaption(null) // not relevant
				.setDescription(null) // not relevant
				.setLayoutTypeNone() // not relevant
				.setWidgetType(DocumentFieldWidgetType.ActionButton)
				.addField(layoutElementField(docStatusField).setFieldType(FieldType.ActionButtonStatus))
				.addField(layoutElementField(docActionField).setFieldType(FieldType.ActionButton))
				.build();
	}

}
