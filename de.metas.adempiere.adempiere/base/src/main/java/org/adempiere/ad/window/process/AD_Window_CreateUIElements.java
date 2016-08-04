package org.adempiere.ad.window.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.window.api.IADFieldDAO;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_UI_Column;
import org.compiere.model.I_AD_UI_Element;
import org.compiere.model.I_AD_UI_ElementField;
import org.compiere.model.I_AD_UI_ElementGroup;
import org.compiere.model.I_AD_UI_Section;
import org.compiere.model.I_AD_Window;
import org.compiere.model.X_AD_UI_Column;
import org.compiere.process.SvrProcess;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Ordering;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class AD_Window_CreateUIElements extends SvrProcess
{
	private final transient IADWindowDAO windowDAO = Services.get(IADWindowDAO.class);

	private static final Ordering<I_AD_Field> ORDERING_AD_Field_BySeqNo = Ordering.natural()
			.onResultOf((adField) -> adField.getSeqNo());

	private final Map<String, String> HARDCODED_columnName2elementId = ImmutableMap.<String, String> builder()
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

	@Override
	protected String doIt() throws Exception
	{
		final I_AD_Window adWindow = getRecord(I_AD_Window.class);

		final List<I_AD_Tab> adTabs = windowDAO.retrieveTabs(adWindow);
		if (adTabs.isEmpty())
		{
			throw new AdempiereException("@NotFound@ @AD_Tab_ID@");
		}

		final I_AD_Tab adTab = adTabs.get(0);
		migratePrimaryTab(adTab);

		return MSG_OK;
	}

	private void migratePrimaryTab(final I_AD_Tab adTab)
	{
		if (!windowDAO.retrieveUISections(adTab).isEmpty())
		{
			throw new AdempiereException("Tab already has UI sections: " + adTab);
		}

		final I_AD_UI_Section uiSection = createUISection(adTab);

		final I_AD_UI_Column uiColumnLeft = createUIColumn(uiSection, X_AD_UI_Column.UISTYLE_Left);
		@SuppressWarnings("unused")
		final I_AD_UI_Column uiColumnRight = createUIColumn(uiSection, X_AD_UI_Column.UISTYLE_Right);

		final I_AD_UI_ElementGroup uiElementGroup_Left_Default = createUIElementGroup(uiColumnLeft);

		final List<I_AD_Field> adFields = new ArrayList<>(Services.get(IADFieldDAO.class).retrieveFields(adTab));
		adFields.sort(ORDERING_AD_Field_BySeqNo);

		final Map<String, I_AD_UI_Element> uiElementsById = new HashMap<>();

		int uiElement_nextSeqNo = 10;
		for (final I_AD_Field adField : adFields)
		{
			if (!adField.isActive())
			{
				continue;
			}
			if (!adField.isDisplayed())
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
				uiElement = createUIElement(uiElementGroup_Left_Default, adField, uiElement_nextSeqNo);
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

	private String extractElementId(final I_AD_Field adField)
	{
		final String columnName = adField.getAD_Column().getColumnName();

		String elementId = HARDCODED_columnName2elementId.get(columnName);
		if (elementId == null)
		{
			elementId = columnName;
		}

		return elementId;
	}

	private I_AD_UI_Section createUISection(final I_AD_Tab adTab)
	{
		final I_AD_UI_Section uiSection = InterfaceWrapperHelper.newInstance(I_AD_UI_Section.class, adTab);
		uiSection.setAD_Tab(adTab);
		uiSection.setName("main"); // FIXME hardcoded
		uiSection.setSeqNo(10); // FIXME: hardcoded
		InterfaceWrapperHelper.save(uiSection);

		addLog("Created {} for {}", uiSection, adTab);
		return uiSection;
	}

	private I_AD_UI_Column createUIColumn(final I_AD_UI_Section uiSection, final String uiStyle)
	{
		final I_AD_UI_Column uiColumn = InterfaceWrapperHelper.newInstance(I_AD_UI_Column.class, uiSection);
		uiColumn.setAD_UI_Section(uiSection);
		uiColumn.setUIStyle(uiStyle);
		InterfaceWrapperHelper.save(uiColumn);

		addLog("Created {} (UIStyle={}) for {}", uiColumn, uiStyle, uiSection);
		return uiColumn;
	}

	private I_AD_UI_ElementGroup createUIElementGroup(final I_AD_UI_Column uiColumn)
	{
		final I_AD_UI_ElementGroup uiElementGroup = InterfaceWrapperHelper.newInstance(I_AD_UI_ElementGroup.class, uiColumn);
		uiElementGroup.setAD_UI_Column(uiColumn);
		uiElementGroup.setName("default");
		uiElementGroup.setSeqNo(10); // FIXME: hardcoded
		uiElementGroup.setUIStyle("primary");
		InterfaceWrapperHelper.save(uiElementGroup);

		addLog("Created {} for {}", uiElementGroup, uiColumn);
		return uiElementGroup;
	}

	private I_AD_UI_Element createUIElement(final I_AD_UI_ElementGroup uiElementGroup, final I_AD_Field adField, final int seqNo)
	{
		final I_AD_UI_Element uiElement = InterfaceWrapperHelper.newInstance(I_AD_UI_Element.class, uiElementGroup);
		uiElement.setAD_UI_ElementGroup(uiElementGroup);
		uiElement.setAD_Field(adField);
		uiElement.setName(adField.getName());
		uiElement.setDescription(adField.getDescription());
		uiElement.setHelp(adField.getHelp());
		uiElement.setIsBasicField(true);
		uiElement.setIsAdvancedField(false);
		uiElement.setSeqNo(seqNo);
		InterfaceWrapperHelper.save(uiElement);

		addLog("Created {} (AD_Field={}, seqNo={}) for {}", uiElement, adField, seqNo, uiElementGroup);
		return uiElement;
	}

	private I_AD_UI_ElementField createUIElementField(final I_AD_UI_Element uiElement, final I_AD_Field adField)
	{
		final I_AD_UI_ElementField uiElementField = InterfaceWrapperHelper.newInstance(I_AD_UI_ElementField.class, uiElement);
		uiElementField.setAD_UI_Element(uiElement);
		uiElementField.setAD_Field(adField);
		InterfaceWrapperHelper.save(uiElementField);

		addLog("Created {} (AD_Field={}) for {}", uiElementField, adField, uiElement);
		return uiElementField;
	}
}
