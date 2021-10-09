package org.adempiere.ad.window.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nullable;

import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.ad.window.api.UIElementGroupId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_UI_Column;
import org.compiere.model.I_AD_UI_Element;
import org.compiere.model.I_AD_UI_ElementField;
import org.compiere.model.I_AD_UI_ElementGroup;
import org.compiere.model.I_AD_UI_Section;
import org.compiere.model.I_AD_Window;
import org.compiere.model.X_AD_UI_Element;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Ordering;

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public final class WindowUIElementsGenerator
{
	private static final Logger logger = LogManager.getLogger(WindowUIElementsGenerator.class);

	public static WindowUIElementsGenerator forConsumer(final IWindowUIElementsGeneratorConsumer consumer)
	{
		return new WindowUIElementsGenerator(consumer);
	}

	private final transient IADWindowDAO windowDAO = Services.get(IADWindowDAO.class);

	private static final ModelDynAttributeAccessor<I_AD_UI_Element, Integer> DYNATTR_UIElementField_NextSeqNo = new ModelDynAttributeAccessor<>("UIElementField_NextSeqNo", Integer.class);
	private static final Ordering<I_AD_Field> ORDERING_AD_Field_BySeqNo = Ordering.natural()
			.onResultOf((adField) -> adField.getSeqNo());
	private static final Ordering<I_AD_Field> ORDERING_AD_Field_BySeqNoGrid = Ordering.natural()
			.onResultOf((adField) -> adField.getSeqNoGrid());

	private static final Map<String, String> HARDCODED_columnName2elementId = ImmutableMap.<String, String> builder()
			//
			.put("C_BPartner_ID", "C_BPartner_ID")
			.put("C_BPartner_Location_ID", "C_BPartner_ID")
			.put("AD_User_ID", "C_BPartner_ID")
			//
			.put("Bill_BPartner_ID", "Bill_BPartner_ID")
			.put("Bill_Location_ID", "Bill_BPartner_ID")
			.put("Bill_User_ID", "Bill_BPartner_ID")
			//
			.put("DropShip_BPartner_ID", "DropShip_BPartner_ID")
			.put("DropShip_Location_ID", "DropShip_BPartner_ID")
			.put("DropShip_User_ID", "DropShip_BPartner_ID")
			//
			.put("M_Product_ID", "M_Product_ID")
			.put("M_HU_PI_Item_Product_ID", "M_Product_ID")
			//
			.put("HandOver_Partner_ID", "HandOver_Partner_ID")
			.put("HandOver_Location_ID", "HandOver_Partner_ID")
			//
			.build();

	private final IWindowUIElementsGeneratorConsumer consumer;

	private WindowUIElementsGenerator(final IWindowUIElementsGeneratorConsumer consumer)
	{
		super();
		Check.assumeNotNull(consumer, "Parameter consumer is not null");
		this.consumer = consumer;
	}

	private void log(final String msg, final Object... msgParameters)
	{
		Loggables.withLogger(logger, Level.DEBUG).addLog(msg, msgParameters);
	}

	public void generate(final I_AD_Window adWindow)
	{
		final List<I_AD_Tab> adTabs = windowDAO.retrieveTabs(adWindow);
		if (adTabs.isEmpty())
		{
			throw new AdempiereException("@NotFound@ @AD_Tab_ID@");
		}

		final I_AD_Tab masterTab = adTabs.get(0);
		migratePrimaryTab(masterTab);

		final List<I_AD_Tab> detailTabs = getDirectChildTabs(adTabs, masterTab);
		for (final I_AD_Tab detailTab : detailTabs)
		{
			migrateDetailTab(detailTab);
		}
	}

	private static List<I_AD_Tab> getDirectChildTabs(final List<I_AD_Tab> tabs, final I_AD_Tab parentTab)
	{
		final int parentTabLevel = parentTab.getTabLevel();
		final int childTabLevelExpected = parentTabLevel + 1;

		final int tabsCount = tabs.size();
		final List<I_AD_Tab> childTabs = new ArrayList<>();
		boolean foundParentTab = false;
		for (int childTabNo = 0; childTabNo < tabsCount; childTabNo++)
		{
			final I_AD_Tab childTab = tabs.get(childTabNo);

			if (!foundParentTab)
			{
				if (childTab.getAD_Tab_ID() == parentTab.getAD_Tab_ID())
				{
					foundParentTab = true;
				}

				continue;
			}

			final int childTabLevel = childTab.getTabLevel();

			if (childTabLevel == parentTabLevel)
			{
				// we just moved to another parent tab. Stop here.
				break;
			}
			else if (childTabLevel == childTabLevelExpected)
			{
				// we found a child tab. Collect it.
				childTabs.add(childTab);
			}
			else // childTabLevel > childTabLevelExpected
			{
				// we found a child of a child tab. Ignore it.
				continue;
			}
		}

		return childTabs;
	}

	public void migratePrimaryTab(final I_AD_Tab masterTab)
	{
		if (windowDAO.hasUISections(masterTab))
		{
			log("Skip migrating {} because already has sections", masterTab);
			return;
		}

		final I_AD_UI_Section uiSection = createUISection(masterTab, 10);

		final I_AD_UI_Column uiColumnLeft = createUIColumn(uiSection, 10);
		@SuppressWarnings("unused")
		final I_AD_UI_Column uiColumnRight = createUIColumn(uiSection, 20);

		final I_AD_UI_ElementGroup uiElementGroup_Left_Default = createUIElementGroup(uiColumnLeft, 10);

		final List<I_AD_Field> adFields = new ArrayList<>(Services.get(IADWindowDAO.class).retrieveFields(masterTab));
		adFields.sort(ORDERING_AD_Field_BySeqNo);

		final Map<String, I_AD_UI_Element> uiElementsById = new HashMap<>();

		final AtomicInteger uiElement_nextSeqNo = new AtomicInteger(10);
		final AtomicInteger uiElement_nextSeqNoGrid = new AtomicInteger(10);
		for (final I_AD_Field adField : adFields)
		{
			if (!adField.isActive())
			{
				continue;
			}
			if (!adField.isDisplayed() && !adField.isDisplayedGrid())
			{
				continue;
			}
			if (adField.getIncluded_Tab_ID() > 0)
			{
				// skip included tabs
				continue;
			}

			final String uiElementId = extractElementId(adField);
			I_AD_UI_Element uiElement = uiElementsById.get(uiElementId);

			if (uiElement == null)
			{
				uiElement = createUIElement(uiElementGroup_Left_Default, adField, uiElement_nextSeqNo, uiElement_nextSeqNoGrid);
				if (uiElement != null)
				{
					uiElementsById.put(uiElementId, uiElement);
				}
			}
			else
			{
				createUIElementField(uiElement, adField);
			}
		}
	}

	public void migrateDetailTab(final I_AD_Tab detailTab)
	{
		if (windowDAO.hasUISections(detailTab))
		{
			log("Skip migrating {} because already has sections", detailTab);
			return;
		}

		final I_AD_UI_Section uiSection = createUISection(detailTab, 10);
		final I_AD_UI_Column uiColumn = createUIColumn(uiSection, 10);
		final I_AD_UI_ElementGroup uiElementGroup = createUIElementGroup(uiColumn, 10);

		final List<I_AD_Field> adFields = new ArrayList<>(Services.get(IADWindowDAO.class).retrieveFields(detailTab));
		adFields.sort(ORDERING_AD_Field_BySeqNoGrid);

		final Map<String, I_AD_UI_Element> uiElementsById = new HashMap<>();

		int uiElement_nextSeqNo = 10;
		for (final I_AD_Field adField : adFields)
		{
			if (!adField.isActive())
			{
				continue;
			}
			if (!adField.isDisplayedGrid())
			{
				continue;
			}
			if (adField.getIncluded_Tab_ID() > 0)
			{
				// skip included tabs
				continue;
			}

			final String uiElementId = extractElementId(adField);
			I_AD_UI_Element uiElement = uiElementsById.get(uiElementId);

			if (uiElement == null)
			{
				uiElement = createUIElement_Grid(uiElementGroup, adField, uiElement_nextSeqNo);
				if (uiElement != null)
				{
					uiElement_nextSeqNo += 10;
					uiElementsById.put(uiElementId, uiElement);
				}
			}
			else
			{
				createUIElementField(uiElement, adField);
			}
		}
	}

	private static String extractElementId(final I_AD_Field adField)
	{
		final String columnName = adField.getAD_Column().getColumnName();

		String elementId = HARDCODED_columnName2elementId.get(columnName);
		if (elementId == null)
		{
			elementId = columnName;
		}

		return elementId;
	}

	private I_AD_UI_Section createUISection(final I_AD_Tab adTab, final int seqNo)
	{
		final I_AD_UI_Section uiSection = InterfaceWrapperHelper.newInstance(I_AD_UI_Section.class, adTab);
		uiSection.setAD_Tab(adTab);
		uiSection.setValue("main"); // FIXME hardcoded
		uiSection.setSeqNo(seqNo);

		consumer.consume(uiSection, adTab);

		return uiSection;
	}

	private I_AD_UI_Column createUIColumn(final I_AD_UI_Section uiSection, final int seqNo)
	{
		final I_AD_UI_Column uiColumn = InterfaceWrapperHelper.newInstance(I_AD_UI_Column.class, uiSection);
		uiColumn.setAD_UI_Section(uiSection);
		uiColumn.setSeqNo(seqNo);

		consumer.consume(uiColumn, uiSection);

		return uiColumn;
	}

	private I_AD_UI_ElementGroup createUIElementGroup(final I_AD_UI_Column uiColumn, final int seqNo)
	{
		final I_AD_UI_ElementGroup uiElementGroup = InterfaceWrapperHelper.newInstance(I_AD_UI_ElementGroup.class, uiColumn);
		uiElementGroup.setAD_UI_Column(uiColumn);
		uiElementGroup.setName("default");
		uiElementGroup.setSeqNo(seqNo);
		uiElementGroup.setUIStyle("primary");

		consumer.consume(uiElementGroup, uiColumn);

		return uiElementGroup;
	}

	public static I_AD_UI_Element createUIElementNoSave(
			@Nullable final UIElementGroupId uiElementGroupId,
			@NonNull final I_AD_Field adField)
	{
		final I_AD_UI_Element uiElement = InterfaceWrapperHelper.newInstance(I_AD_UI_Element.class);
		uiElement.setAD_Tab_ID(adField.getAD_Tab_ID());
		uiElement.setAD_UI_ElementGroup_ID(UIElementGroupId.toRepoId(uiElementGroupId));
		uiElement.setAD_UI_ElementType(X_AD_UI_Element.AD_UI_ELEMENTTYPE_Field);
		uiElement.setAD_Field(adField);
		uiElement.setName(adField.getName());
		uiElement.setDescription(adField.getDescription());
		uiElement.setHelp(adField.getHelp());
		uiElement.setIsAdvancedField(false);

		uiElement.setIsDisplayed(false);
		uiElement.setSeqNo(0);

		uiElement.setIsDisplayedGrid(false);
		uiElement.setSeqNoGrid(0);

		uiElement.setIsDisplayed_SideList(false);
		uiElement.setSeqNo_SideList(0);

		// #1019 set the widget size in the UI element if it was set in the original element
		final I_AD_Element element = adField.getAD_Column().getAD_Element();
		uiElement.setWidgetSize(element.getWidgetSize());

		return uiElement;
	}

	private I_AD_UI_Element createUIElement(
			final I_AD_UI_ElementGroup uiElementGroup,
			final I_AD_Field adField,
			final AtomicInteger nextSeqNo,
			final AtomicInteger nextSeqNoGrid)
	{
		final UIElementGroupId uiElementGroupId = UIElementGroupId.ofRepoIdOrNull(uiElementGroup.getAD_UI_ElementGroup_ID()); // might not be saved
		final I_AD_UI_Element uiElement = createUIElementNoSave(uiElementGroupId, adField);

		final boolean displayed = adField.isDisplayed();
		uiElement.setIsDisplayed(displayed);
		if (displayed)
		{
			final int seqNo = nextSeqNo.getAndAdd(10);
			uiElement.setSeqNo(seqNo);
		}

		final boolean displayedGrid = adField.isDisplayedGrid();
		uiElement.setIsDisplayedGrid(displayedGrid);
		if (displayedGrid)
		{
			final int seqNoGrid = nextSeqNoGrid.getAndAdd(10);
			uiElement.setSeqNoGrid(seqNoGrid);
		}

		consumer.consume(uiElement, uiElementGroup);

		return uiElement;
	}

	private I_AD_UI_Element createUIElement_Grid(
			final I_AD_UI_ElementGroup uiElementGroup,
			final I_AD_Field adField, final int seqNo)
	{
		final UIElementGroupId uiElementGroupId = UIElementGroupId.ofRepoIdOrNull(uiElementGroup.getAD_UI_ElementGroup_ID()); // might not be saved
		final I_AD_UI_Element uiElement = createUIElementNoSave(uiElementGroupId, adField);
		uiElement.setIsDisplayedGrid(true);
		uiElement.setSeqNoGrid(seqNo);

		consumer.consume(uiElement, uiElementGroup);

		return uiElement;
	}

	private I_AD_UI_ElementField createUIElementField(final I_AD_UI_Element uiElement, final I_AD_Field adField)
	{
		final int seqNo = DYNATTR_UIElementField_NextSeqNo.getValue(uiElement, 10);
		final I_AD_UI_ElementField uiElementField = InterfaceWrapperHelper.newInstance(I_AD_UI_ElementField.class, uiElement);
		uiElementField.setAD_UI_Element(uiElement);
		uiElementField.setAD_Field(adField);
		uiElementField.setSeqNo(seqNo);

		consumer.consume(uiElementField, uiElement);

		DYNATTR_UIElementField_NextSeqNo.setValue(uiElement, seqNo + 10);

		return uiElementField;
	}
}
