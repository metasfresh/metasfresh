package org.adempiere.ad.window.process;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.window.api.IADFieldDAO;
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
	private static final Ordering<I_AD_Field> ORDERING_AD_Field_BySeqNo = Ordering.natural()
			.onResultOf((adField) -> adField.getSeqNo());

	@Override
	protected String doIt() throws Exception
	{
		final I_AD_Window adWindow = getRecord(I_AD_Window.class);

		final List<I_AD_Tab> adTabs = retrieveTabs(adWindow);
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
		if (!retrieveUISections(adTab).isEmpty())
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

			final I_AD_UI_Element uiElement = createUIElement(uiElementGroup_Left_Default, adField, uiElement_nextSeqNo);
			if (uiElement == null)
			{
				continue;
			}

			uiElement_nextSeqNo += 10;
		}
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
		uiElementGroup.setUIStyle("bordered");
		InterfaceWrapperHelper.save(uiElementGroup);

		addLog("Created {} for {}", uiElementGroup, uiColumn);
		return uiElementGroup;
	}

	private I_AD_UI_Element createUIElement(final I_AD_UI_ElementGroup uiElementGroup, final I_AD_Field adField, final int seqNo)
	{
		final I_AD_UI_Element uiElement = InterfaceWrapperHelper.newInstance(I_AD_UI_Element.class, uiElementGroup);
		uiElement.setAD_UI_ElementGroup(uiElementGroup);
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

	private static List<I_AD_Tab> retrieveTabs(final I_AD_Window adWindow)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL
				.createQueryBuilder(I_AD_Tab.class, adWindow)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Tab.COLUMN_AD_Window_ID, adWindow.getAD_Window_ID())
				.orderBy()
				.addColumn(I_AD_Tab.COLUMN_SeqNo)
				.endOrderBy()
				.create()
				.list(I_AD_Tab.class);
	}

	private static List<I_AD_UI_Section> retrieveUISections(final I_AD_Tab adTab)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL
				.createQueryBuilder(I_AD_UI_Section.class, adTab)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_UI_Section.COLUMN_AD_Tab_ID, adTab.getAD_Tab_ID())
				.orderBy()
				.addColumn(I_AD_UI_Section.COLUMN_SeqNo)
				.endOrderBy()
				.create()
				.list(I_AD_UI_Section.class);
	}

	private static List<I_AD_UI_Column> retrieveUIColumns(final I_AD_UI_Section uiSection)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL
				.createQueryBuilder(I_AD_UI_Column.class, uiSection)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_UI_Column.COLUMN_AD_UI_Section_ID, uiSection.getAD_UI_Section_ID())
				.orderBy()
				.addColumn(I_AD_UI_Column.COLUMN_AD_UI_Column_ID)
				.endOrderBy()
				.create()
				.list(I_AD_UI_Column.class);
	}

	private static List<I_AD_UI_ElementGroup> retrieveUIElementGroups(final I_AD_UI_Column uiColumn)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL
				.createQueryBuilder(I_AD_UI_ElementGroup.class, uiColumn)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_UI_ElementGroup.COLUMN_AD_UI_Column_ID, uiColumn.getAD_UI_Column_ID())
				.orderBy()
				.addColumn(I_AD_UI_ElementGroup.COLUMN_SeqNo)
				.endOrderBy()
				.create()
				.list(I_AD_UI_ElementGroup.class);
	}

	private static List<I_AD_UI_Element> retrieveUIElements(final I_AD_UI_ElementGroup uiElementGroup)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL
				.createQueryBuilder(I_AD_UI_Element.class, uiElementGroup)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_UI_Element.COLUMN_AD_UI_ElementGroup_ID, uiElementGroup.getAD_UI_ElementGroup_ID())
				.orderBy()
				.addColumn(I_AD_UI_Element.COLUMN_SeqNo)
				.endOrderBy()
				.create()
				.list(I_AD_UI_Element.class);
	}

	private static List<I_AD_UI_ElementField> retrieveUIElementFields(final I_AD_UI_Element uiElement)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL
				.createQueryBuilder(I_AD_UI_ElementField.class, uiElement)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_UI_ElementField.COLUMN_AD_UI_Element_ID, uiElement.getAD_UI_Element_ID())
				.orderBy()
				.addColumn(I_AD_UI_ElementField.COLUMN_AD_UI_ElementField_ID)
				.endOrderBy()
				.create()
				.list(I_AD_UI_ElementField.class);
	}

}
