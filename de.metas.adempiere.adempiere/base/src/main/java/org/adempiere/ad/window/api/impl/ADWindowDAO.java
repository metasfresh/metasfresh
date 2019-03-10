package org.adempiere.ad.window.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.copy;
import static org.adempiere.model.InterfaceWrapperHelper.delete;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.element.api.AdFieldId;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.IQuery.Aggregate;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Element_Link;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_UI_Column;
import org.compiere.model.I_AD_UI_Element;
import org.compiere.model.I_AD_UI_ElementField;
import org.compiere.model.I_AD_UI_ElementGroup;
import org.compiere.model.I_AD_UI_Section;
import org.compiere.model.I_AD_Window;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableSet;

import de.metas.cache.annotation.CacheCtx;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

public class ADWindowDAO implements IADWindowDAO
{
	private static final transient Logger logger = LogManager.getLogger(ADWindowDAO.class);

	@Override
	public ITranslatableString retrieveWindowName(final AdWindowId adWindowId)
	{
		return retrieveWindowNameCached(adWindowId);
	}

	@Cached(cacheName = I_AD_Window.Table_Name + "#By#" + I_AD_Window.COLUMNNAME_AD_Window_ID)
	public ITranslatableString retrieveWindowNameCached(final AdWindowId adWindowId)
	{
		// using a simple DB call would be faster, but this way it's less coupled and after all we have caching
		final I_AD_Window window = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_AD_Window.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Window.COLUMNNAME_AD_Window_ID, adWindowId)
				.create()
				.firstOnly(I_AD_Window.class);

		if (window == null)
		{
			return ImmutableTranslatableString.empty();
		}
		return InterfaceWrapperHelper.getModelTranslationMap(window).getColumnTrl(I_AD_Window.COLUMNNAME_Name, window.getName());
	}

	@Cached(cacheName = I_AD_Window.Table_Name + "#By#" + I_AD_Window.COLUMNNAME_AD_Window_ID)
	@Override
	public String retrieveInternalWindowName(final int adWindowId)
	{
		final I_AD_Window window = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Window.class, Env.getCtx(), ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Window.COLUMNNAME_AD_Window_ID, adWindowId)
				.create()
				.firstOnly(I_AD_Window.class);
		return window.getInternalName();
	}

	@Override
	public List<I_AD_Tab> retrieveTabs(final I_AD_Window adWindow)
	{
		final AdWindowId adWindowId = AdWindowId.ofRepoId(adWindow.getAD_Window_ID());
		return retrieveTabsQuery(adWindowId)
				.create()
				.list(I_AD_Tab.class);
	}

	@Override
	public void deleteTabsByWindowId(@NonNull final AdWindowId adWindowId)
	{
		retrieveTabsQuery(adWindowId)
				.create()
				.delete();
	}

	private IQueryBuilder<I_AD_Tab> retrieveTabsQuery(final AdWindowId adWindowId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL
				.createQueryBuilder(I_AD_Tab.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Tab.COLUMN_AD_Window_ID, adWindowId)
				.orderBy()
				.addColumn(I_AD_Tab.COLUMN_SeqNo)
				.addColumn(I_AD_Tab.COLUMN_AD_Tab_ID)
				.endOrderBy();
	}

	@Override
	public List<I_AD_UI_Section> retrieveUISections(final I_AD_Tab adTab)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(adTab);
		final int AD_Tab_ID = adTab.getAD_Tab_ID();
		return retrieveUISectionsQuery(ctx, AD_Tab_ID)
				.create()
				.list(I_AD_UI_Section.class);
	}

	@Override
	public List<I_AD_UI_Section> retrieveUISections(final Properties ctx, final int AD_Tab_ID)
	{
		return retrieveUISectionsQuery(ctx, AD_Tab_ID)
				.create()
				.list(I_AD_UI_Section.class);
	}

	@Override
	public boolean hasUISections(final I_AD_Tab adTab)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(adTab);
		final int AD_Tab_ID = adTab.getAD_Tab_ID();
		return retrieveUISectionsQuery(ctx, AD_Tab_ID)
				.create()
				.match();
	}

	private IQueryBuilder<I_AD_UI_Section> retrieveUISectionsQuery(final Properties ctx, final int AD_Tab_ID)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL
				.createQueryBuilder(I_AD_UI_Section.class, ctx, ITrx.TRXNAME_ThreadInherited)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_UI_Section.COLUMN_AD_Tab_ID, AD_Tab_ID)
				.orderBy()
				.addColumn(I_AD_UI_Section.COLUMN_SeqNo)
				.addColumn(I_AD_UI_Section.COLUMN_AD_UI_Section_ID)
				.endOrderBy();
	}

	@Override
	public List<I_AD_UI_Column> retrieveUIColumns(final I_AD_UI_Section uiSection)
	{
		return retrieveUIColumnsQuery(uiSection)
				.create()
				.list(I_AD_UI_Column.class);
	}

	private IQueryBuilder<I_AD_UI_Column> retrieveUIColumnsQuery(final I_AD_UI_Section uiSection)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL
				.createQueryBuilder(I_AD_UI_Column.class, uiSection)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_UI_Column.COLUMN_AD_UI_Section_ID, uiSection.getAD_UI_Section_ID())
				.orderBy()
				.addColumn(I_AD_UI_Column.COLUMN_SeqNo)
				.addColumn(I_AD_UI_Column.COLUMN_AD_UI_Column_ID)
				.endOrderBy();
	}

	@Override
	public List<I_AD_UI_ElementGroup> retrieveUIElementGroups(final I_AD_UI_Column uiColumn)
	{
		return retrieveUIElementGroupsQuery(uiColumn)
				.create()
				.list(I_AD_UI_ElementGroup.class);
	}

	private IQueryBuilder<I_AD_UI_ElementGroup> retrieveUIElementGroupsQuery(final I_AD_UI_Column uiColumn)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL
				.createQueryBuilder(I_AD_UI_ElementGroup.class, uiColumn)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_UI_ElementGroup.COLUMN_AD_UI_Column_ID, uiColumn.getAD_UI_Column_ID())
				.orderBy()
				.addColumn(I_AD_UI_ElementGroup.COLUMN_SeqNo)
				.endOrderBy();
	}

	private int retrieveUIElementGroupsNextSeqNo(final I_AD_UI_Column uiColumn)
	{
		final Integer lastSeqNo = retrieveUIElementGroupsQuery(uiColumn)
				.create()
				.aggregate(I_AD_UI_ElementGroup.COLUMN_SeqNo, Aggregate.MAX, Integer.class);
		return nextSeqNo(lastSeqNo);
	}

	private static final int nextSeqNo(final Integer lastSeqNo)
	{
		if (lastSeqNo == null)
		{
			return 10;
		}

		if (lastSeqNo % 10 == 0)
		{
			return lastSeqNo + 10;
		}

		return lastSeqNo / 10 * 10 + 10;
	}

	@Override
	public List<I_AD_UI_Element> retrieveUIElements(final I_AD_UI_ElementGroup uiElementGroup)
	{
		return retrieveUIElementsQuery(uiElementGroup)
				.create()
				.list(I_AD_UI_Element.class);
	}

	private int retrieveUIElementNextSeqNo(final I_AD_UI_ElementGroup uiElementGroup)
	{
		final Integer lastSeqNo = retrieveUIElementsQuery(uiElementGroup)
				.create()
				.aggregate(I_AD_UI_Element.COLUMN_SeqNo, Aggregate.MAX, Integer.class);
		return nextSeqNo(lastSeqNo);
	}

	private IQueryBuilder<I_AD_UI_Element> retrieveUIElementsQuery(final I_AD_UI_ElementGroup uiElementGroup)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL
				.createQueryBuilder(I_AD_UI_Element.class, uiElementGroup)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_UI_Element.COLUMN_AD_UI_ElementGroup_ID, uiElementGroup.getAD_UI_ElementGroup_ID())
				.orderBy()
				.addColumn(I_AD_UI_Element.COLUMN_SeqNo)
				.endOrderBy();
	}

	@Override
	public IQueryBuilder<I_AD_UI_Element> retrieveUIElementsQueryByTabId(final int adTabId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL
				.createQueryBuilder(I_AD_UI_Element.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_UI_Element.COLUMN_AD_Tab_ID, adTabId)
				.orderBy()
				.addColumn(I_AD_UI_Element.COLUMN_SeqNo)
				.endOrderBy();
	}

	@Override
	public List<I_AD_UI_ElementField> retrieveUIElementFields(final I_AD_UI_Element uiElement)
	{
		return retrieveUIElementFieldsQuery(uiElement)
				.create()
				.list(I_AD_UI_ElementField.class);
	}

	private IQueryBuilder<I_AD_UI_ElementField> retrieveUIElementFieldsQuery(final I_AD_UI_Element uiElement)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL
				.createQueryBuilder(I_AD_UI_ElementField.class, uiElement)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_UI_ElementField.COLUMN_AD_UI_Element_ID, uiElement.getAD_UI_Element_ID())
				.orderBy()
				.addColumn(I_AD_UI_ElementField.COLUMN_SeqNo)
				.addColumn(I_AD_UI_ElementField.COLUMN_AD_UI_ElementField_ID)
				.endOrderBy();
	}

	@Override
	public void moveElementGroup(final I_AD_UI_ElementGroup uiElementGroup, final I_AD_UI_Column toUIColumn)
	{
		Check.assumeNotNull(uiElementGroup, "Parameter uiElementGroup is not null");
		Check.assumeNotNull(toUIColumn, "Parameter toUIColumn is not null");

		if (uiElementGroup.getAD_UI_Column_ID() == toUIColumn.getAD_UI_Column_ID())
		{
			// nothing to move
			return;
		}

		InterfaceWrapperHelper.ATTR_ReadOnlyColumnCheckDisabled.setValue(uiElementGroup, Boolean.TRUE);
		uiElementGroup.setAD_UI_Column(toUIColumn);
		uiElementGroup.setSeqNo(retrieveUIElementGroupsNextSeqNo(toUIColumn));
		save(uiElementGroup);
	}

	@Override
	public void moveElement(final I_AD_UI_Element uiElement, final I_AD_UI_ElementGroup toUIElementGroup)
	{
		Check.assumeNotNull(uiElement, "Parameter uiElement is not null");
		Check.assumeNotNull(toUIElementGroup, "Parameter toUIElementGroup is not null");

		if (uiElement.getAD_UI_ElementGroup_ID() == toUIElementGroup.getAD_UI_ElementGroup_ID())
		{
			// nothing to move
			return;
		}

		InterfaceWrapperHelper.ATTR_ReadOnlyColumnCheckDisabled.setValue(uiElement, Boolean.TRUE);
		uiElement.setAD_UI_ElementGroup(toUIElementGroup);
		uiElement.setSeqNo(retrieveUIElementNextSeqNo(toUIElementGroup));
		save(uiElement);
	}

	@Override
	public I_AD_Tab retrieveFirstTab(final int adWindowId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Tab.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Tab.COLUMNNAME_AD_Window_ID, adWindowId)
				.orderBy(I_AD_Tab.COLUMNNAME_SeqNo) // just take the first one; it might have seqno != 10
				.create()
				.first(I_AD_Tab.class);
	}

	@Override
	public void copyWindow(final int targetWindowId, final int sourceWindowId)
	{
		Check.assumeGreaterThanZero(targetWindowId, "targetProcessId");
		Check.assumeGreaterThanZero(sourceWindowId, "sourceProcessId");

		final I_AD_Window targetWindow = load(targetWindowId, I_AD_Window.class);
		final I_AD_Window sourceWindow = load(sourceWindowId, I_AD_Window.class);

		logger.debug("Copying from: {} to: {}", sourceWindow, targetWindow);

		copy()
				.setSkipCalculatedColumns(true)
				.addTargetColumnNameToSkip(I_AD_Window.COLUMNNAME_Name)
				.addTargetColumnNameToSkip(I_AD_Window.COLUMNNAME_InternalName)
				.setFrom(sourceWindow)
				.setTo(targetWindow)
				.copy();

		save(targetWindow);

		copyWindowTrl(targetWindowId, sourceWindowId);

		copyTabs(targetWindow, sourceWindow);
	}

	private void copyWindowTrl(final int targetWindowId, final int sourceWindowId)
	{
		Check.assumeGreaterThanZero(targetWindowId, "targetWindowId");
		Check.assumeGreaterThanZero(sourceWindowId, "sourceWindowId");

		final String sqlDelete = "DELETE FROM AD_Window_Trl WHERE AD_Window_ID = " + targetWindowId;
		final int countDelete = DB.executeUpdateEx(sqlDelete, ITrx.TRXNAME_ThreadInherited);
		logger.debug("AD_Window_Trl deleted: {}", countDelete);

		final String sqlInsert = "INSERT INTO AD_Window_Trl (AD_Window_ID, AD_Language, " +
				" AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, " +
				" Name, Description, Help, IsTranslated) " +
				" SELECT " + targetWindowId + ", AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, " +
				" Updated, UpdatedBy, Name, Description, Help, IsTranslated " +
				" FROM AD_Window_Trl WHERE AD_Window_ID = " + sourceWindowId;

		final int countInsert = DB.executeUpdateEx(sqlInsert, ITrx.TRXNAME_ThreadInherited);
		logger.debug("AD_Window_Trl inserted: {}", countInsert);
	}

	private void copyUISections(final I_AD_Tab targetTab, final I_AD_Tab sourceTab)
	{
		final Map<String, I_AD_UI_Section> existingUISections = retrieveUISectionsQuery(Env.getCtx(), targetTab.getAD_Tab_ID()).create().map(I_AD_UI_Section.class, I_AD_UI_Section::getValue);
		final Collection<I_AD_UI_Section> sourceUISections = retrieveUISections(sourceTab);

		for (final I_AD_UI_Section sourceUISection : sourceUISections)
		{
			final I_AD_UI_Section existingUISection = existingUISections.get(sourceUISection.getValue());
			copyUISection(targetTab, existingUISection, sourceUISection);
		}
	}

	private void copyUISection(final I_AD_Tab targetTab, final I_AD_UI_Section existingUISection, final I_AD_UI_Section sourceUISection)
	{
		logger.debug("Copying UISection {} to {}", sourceUISection, targetTab);

		final I_AD_UI_Section targetUISection = createUpdateUISection(targetTab, existingUISection, sourceUISection);

		copyUISectionTrl(targetUISection.getAD_UI_Section_ID(), sourceUISection.getAD_UI_Section_ID());

		copyUIColumns(targetUISection, sourceUISection);
	}

	private I_AD_UI_Section createUpdateUISection(final I_AD_Tab targetTab, final I_AD_UI_Section existingUISection, final I_AD_UI_Section sourceUISection)
	{
		final I_AD_UI_Section targetUISection = existingUISection != null ? existingUISection : newInstance(I_AD_UI_Section.class);

		copy()
				.setFrom(sourceUISection)
				.setTo(targetUISection)
				.copy();

		targetUISection.setAD_Org_ID(sourceUISection.getAD_Org_ID());

		final int targetTabId = targetTab.getAD_Tab_ID();
		targetUISection.setAD_Tab_ID(targetTabId);

		if (targetUISection.getSeqNo() <= 0)
		{
			final int lastSeqNo = retrieveUISectionLastSeqNo(targetTabId);
			targetUISection.setSeqNo(lastSeqNo + 10);
		}

		save(targetUISection);

		return targetUISection;
	}

	private int retrieveUISectionLastSeqNo(int tabId)
	{
		final Integer lastSeqNo = Services.get(IQueryBL.class).createQueryBuilder(I_AD_UI_Section.class)
				.addEqualsFilter(I_AD_UI_Section.COLUMNNAME_AD_Tab_ID, tabId)
				.addOnlyActiveRecordsFilter()
				.create()
				.aggregate(I_AD_UI_Section.COLUMNNAME_SeqNo, Aggregate.MAX, Integer.class);
		return lastSeqNo == null ? 0 : lastSeqNo;
	}

	private void copyUISectionTrl(final int targetUISectionId, final int sourceUISectionId)
	{
		Check.assumeGreaterThanZero(targetUISectionId, "targetUISectionId");
		Check.assumeGreaterThanZero(sourceUISectionId, "sourceUISectionId");

		final String sqlDelete = "DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = " + targetUISectionId;
		final int countDelete = DB.executeUpdateEx(sqlDelete, ITrx.TRXNAME_ThreadInherited);
		logger.debug("AD_UI_Section_Trl deleted: {}", countDelete);

		final String sqlInsert = "INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language, " +
				" AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, " +
				" Name, IsTranslated) " +
				" SELECT " + targetUISectionId + ", AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, " +
				" Updated, UpdatedBy, Name, IsTranslated " +
				" FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = " + sourceUISectionId;
		final int countInsert = DB.executeUpdateEx(sqlInsert, ITrx.TRXNAME_ThreadInherited);
		logger.debug("AD_UI_Section_Trl inserted: {}", countInsert);
	}

	private void copyUIElementGroups(final I_AD_UI_Column targetUIColumn, final I_AD_UI_Column sourceUIColumn)
	{
		final Map<String, I_AD_UI_ElementGroup> existingUIElementGroups = retrieveUIElementGroupsQuery(targetUIColumn).create().map(I_AD_UI_ElementGroup.class, I_AD_UI_ElementGroup::getName);
		final Collection<I_AD_UI_ElementGroup> sourceUIElementGroups = retrieveUIElementGroups(sourceUIColumn);

		for (final I_AD_UI_ElementGroup sourceUIElementGroup : sourceUIElementGroups)
		{
			final I_AD_UI_ElementGroup existingUIElementGroup = existingUIElementGroups.get(sourceUIElementGroup.getName());
			copyUIElementGroup(targetUIColumn, existingUIElementGroup, sourceUIElementGroup);
		}
	}

	private void copyUIElementGroup(final I_AD_UI_Column targetUIColumn, final I_AD_UI_ElementGroup existingUIElementGroup, final I_AD_UI_ElementGroup sourceUIElementGroup)
	{
		logger.debug("Copying UIElementGroup {} to {}", sourceUIElementGroup, targetUIColumn);

		final I_AD_UI_ElementGroup targetUIElementGroup = createUpdateUIElementGroup(targetUIColumn, existingUIElementGroup, sourceUIElementGroup);

		copyUIElements(targetUIElementGroup, sourceUIElementGroup);
	}

	private I_AD_UI_ElementGroup createUpdateUIElementGroup(final I_AD_UI_Column targetUIColumn, final I_AD_UI_ElementGroup existingUIElementGroup, final I_AD_UI_ElementGroup sourceUIElementGroup)
	{
		final I_AD_UI_ElementGroup targetUIElementGroup = existingUIElementGroup != null ? existingUIElementGroup : newInstance(I_AD_UI_ElementGroup.class);

		copy()
				.setFrom(sourceUIElementGroup)
				.setTo(targetUIElementGroup)
				.copy();

		targetUIElementGroup.setAD_Org_ID(targetUIColumn.getAD_Org_ID());
		targetUIElementGroup.setAD_UI_Column_ID(targetUIColumn.getAD_UI_Column_ID());

		if (targetUIElementGroup.getSeqNo() <= 0)
		{
			final int targetUIColumnId = targetUIColumn.getAD_UI_Column_ID();
			final int lastSeqNo = retrieveUIElementGroupLastSeqNo(targetUIColumnId);
			targetUIElementGroup.setSeqNo(lastSeqNo + 10);
		}

		save(targetUIElementGroup);

		return targetUIElementGroup;
	}

	private int retrieveUIElementGroupLastSeqNo(int uiColumnId)
	{
		final Integer lastSeqNo = Services.get(IQueryBL.class).createQueryBuilder(I_AD_UI_ElementGroup.class)
				.addEqualsFilter(I_AD_UI_ElementGroup.COLUMNNAME_AD_UI_Column_ID, uiColumnId)
				.addOnlyActiveRecordsFilter()
				.create()
				.aggregate(I_AD_UI_ElementGroup.COLUMNNAME_SeqNo, Aggregate.MAX, Integer.class);
		return lastSeqNo == null ? 0 : lastSeqNo;
	}

	private void copyUIElementFields(final I_AD_UI_Element targetUIElement, final I_AD_UI_Element sourceUIElement)
	{
		final Map<Integer, I_AD_UI_ElementField> existingTargetUIElementFields = retrieveUIElementFieldsQuery(targetUIElement).create().map(I_AD_UI_ElementField.class, I_AD_UI_ElementField::getSeqNo);
		final Collection<I_AD_UI_ElementField> sourceUIElementFields = retrieveUIElementFields(sourceUIElement);

		for (final I_AD_UI_ElementField sourceUIElementField : sourceUIElementFields)
		{
			final I_AD_UI_ElementField existingTargetElementField = existingTargetUIElementFields.get(sourceUIElementField.getSeqNo());
			copyUIElementField(targetUIElement, existingTargetElementField, sourceUIElementField);
		}
	}

	private void copyUIElementField(final I_AD_UI_Element targetUIElement, final I_AD_UI_ElementField existingTargetElementField, final I_AD_UI_ElementField sourceUIElementField)
	{
		logger.debug("Copying UI Element Field {} to {}", sourceUIElementField, targetUIElement);

		createUpdateUIElementField(targetUIElement, existingTargetElementField, sourceUIElementField);
	}

	private void createUpdateUIElementField(final I_AD_UI_Element targetUIElement, final I_AD_UI_ElementField existingTargetElementField, final I_AD_UI_ElementField sourceUIElementField)
	{
		final I_AD_UI_ElementField targetUIElementField = existingTargetElementField != null ? existingTargetElementField : newInstance(I_AD_UI_ElementField.class);

		copy()
				.setFrom(sourceUIElementField)
				.setTo(targetUIElementField)
				.copy();

		targetUIElementField.setAD_Org_ID(targetUIElement.getAD_Org_ID());
		targetUIElementField.setSeqNo(sourceUIElementField.getSeqNo());

		final int targetUIElementId = targetUIElement.getAD_UI_Element_ID();
		targetUIElementField.setAD_UI_Element_ID(targetUIElementId);

		final int targetFieldId = getTargetFieldId(sourceUIElementField, targetUIElement);
		targetUIElementField.setAD_Field_ID(targetFieldId);

		save(targetUIElementField);
	}

	private int getTargetFieldId(final I_AD_UI_ElementField sourceUIElementField, final I_AD_UI_Element targetElement)
	{
		if (sourceUIElementField.getAD_Field_ID() <= 0)
		{
			return -1;
		}

		final I_AD_Field sourceField = sourceUIElementField.getAD_Field();

		final int columnId = sourceField.getAD_Column_ID();

		final I_AD_UI_ElementGroup uiElementGroup = targetElement.getAD_UI_ElementGroup();

		final I_AD_UI_Column uiColumn = uiElementGroup.getAD_UI_Column();

		final I_AD_UI_Section uiSection = uiColumn.getAD_UI_Section();

		final I_AD_Tab tab = uiSection.getAD_Tab();

		final Optional<I_AD_Field> fieldForColumn = retrieveFields(tab)
				.stream()
				.filter(field -> field.getAD_Column_ID() == columnId)
				.findFirst();

		return fieldForColumn.isPresent() ? fieldForColumn.get().getAD_Field_ID() : -1;
	}

	private void copyUIElements(final I_AD_UI_ElementGroup targetUIElementGroup, final I_AD_UI_ElementGroup sourceUIElementGroup)
	{
		final Map<String, I_AD_UI_Element> existingTargetUIElements = retrieveUIElementsQuery(targetUIElementGroup).create().map(I_AD_UI_Element.class, I_AD_UI_Element::getName);
		final Collection<I_AD_UI_Element> sourceUIElements = retrieveUIElements(sourceUIElementGroup);

		for (final I_AD_UI_Element sourceUIElement : sourceUIElements)
		{
			final I_AD_UI_Element existingTargetElement = existingTargetUIElements.get(sourceUIElement.getName());
			copyUIElement(targetUIElementGroup, existingTargetElement, sourceUIElement);
		}

	}

	private void copyUIElement(final I_AD_UI_ElementGroup targetElementGroup, final I_AD_UI_Element existingTargetElement, final I_AD_UI_Element sourceUIElement)
	{
		logger.debug("Copying UI Element {} to {}", sourceUIElement, targetElementGroup);

		final I_AD_UI_Element targetUIElement = createUpdateUIElement(targetElementGroup, existingTargetElement, sourceUIElement);

		copyUIElementFields(targetUIElement, sourceUIElement);
	}

	private I_AD_UI_Element createUpdateUIElement(final I_AD_UI_ElementGroup targetElementGroup, final I_AD_UI_Element existingTargetElement, final I_AD_UI_Element sourceElement)
	{
		final int targetElementGroupId = targetElementGroup.getAD_UI_ElementGroup_ID();

		final I_AD_UI_Element targetElement = existingTargetElement != null ? existingTargetElement : newInstance(I_AD_UI_Element.class);

		copy()
				.setFrom(sourceElement)
				.setTo(targetElement)
				.copy();

		targetElement.setAD_Org_ID(targetElementGroup.getAD_Org_ID());
		targetElement.setAD_UI_ElementGroup_ID(targetElementGroupId);

		final int tabId = getTabId(targetElementGroup);
		targetElement.setAD_Tab_ID(tabId);

		final int targetFieldId = getTargetFieldId(sourceElement, tabId);
		targetElement.setAD_Field_ID(targetFieldId);

		if (targetElement.getSeqNo() <= 0)
		{
			final int lastSeqNo = retrieveUIElementLastSeqNo(targetElementGroupId);
			targetElement.setSeqNo(lastSeqNo + 10);
		}

		save(targetElement);

		return targetElement;
	}

	private int getTabId(final I_AD_UI_ElementGroup targetElementGroup)
	{
		final I_AD_UI_Column uiColumn = targetElementGroup.getAD_UI_Column();

		final I_AD_UI_Section uiSection = uiColumn.getAD_UI_Section();

		return uiSection.getAD_Tab_ID();
	}

	private int getTargetFieldId(final I_AD_UI_Element sourceElement, final int tabId)
	{
		if (sourceElement.getAD_Field_ID() <= 0)
		{
			return -1;
		}
		final I_AD_Field sourceField = sourceElement.getAD_Field();

		final int columnId = sourceField.getAD_Column_ID();

		final I_AD_Tab tab = load(tabId, I_AD_Tab.class);

		final Optional<I_AD_Field> fieldForColumn = retrieveFields(tab)
				.stream()
				.filter(field -> field.getAD_Column_ID() == columnId)
				.findFirst();

		return fieldForColumn.isPresent() ? fieldForColumn.get().getAD_Field_ID() : -1;
	}

	private int retrieveUIElementLastSeqNo(int uiElementGroupId)
	{
		final Integer lastSeqNo = Services.get(IQueryBL.class).createQueryBuilder(I_AD_UI_Element.class)
				.addEqualsFilter(I_AD_UI_Element.COLUMNNAME_AD_UI_ElementGroup_ID, uiElementGroupId)
				.addOnlyActiveRecordsFilter()
				.create()
				.aggregate(I_AD_UI_Element.COLUMNNAME_SeqNo, Aggregate.MAX, Integer.class);
		return lastSeqNo == null ? 0 : lastSeqNo;
	}

	private void copyUIColumns(final I_AD_UI_Section targetUISection, final I_AD_UI_Section sourceUISection)
	{
		final Map<Integer, I_AD_UI_Column> existingUIColumns = retrieveUIColumnsQuery(targetUISection).create().map(I_AD_UI_Column.class, I_AD_UI_Column::getSeqNo);
		final Collection<I_AD_UI_Column> sourceUIColumns = retrieveUIColumns(sourceUISection);

		for (final I_AD_UI_Column sourceUIColumn : sourceUIColumns)
		{
			final I_AD_UI_Column existingUIColumn = existingUIColumns.get(sourceUIColumn.getSeqNo());
			copyUIColumn(targetUISection, existingUIColumn, sourceUIColumn);
		}
	}

	private void copyUIColumn(final I_AD_UI_Section targetUISection, final I_AD_UI_Column existingUIColumn, final I_AD_UI_Column sourceUIColumn)
	{
		logger.debug("Copying UIColumn {} to {}", sourceUIColumn, targetUISection);

		final I_AD_UI_Column targetUIColumn = createUpdateUIColumn(targetUISection, existingUIColumn, sourceUIColumn);

		copyUIElementGroups(targetUIColumn, sourceUIColumn);
	}

	private I_AD_UI_Column createUpdateUIColumn(final I_AD_UI_Section targetUISection, final I_AD_UI_Column existingUIColumn, final I_AD_UI_Column sourceUIColumn)
	{

		final I_AD_UI_Column targetUIColumn = existingUIColumn != null ? existingUIColumn : newInstance(I_AD_UI_Column.class);

		copy()
				.addTargetColumnNameToSkip(I_AD_UI_Column.COLUMNNAME_SeqNo)
				.setFrom(sourceUIColumn)
				.setTo(targetUIColumn)
				.copy();

		targetUIColumn.setAD_Org_ID(targetUISection.getAD_Org_ID());
		targetUIColumn.setAD_UI_Section_ID(targetUISection.getAD_UI_Section_ID());
		targetUIColumn.setSeqNo(sourceUIColumn.getSeqNo());

		save(targetUIColumn);

		return targetUIColumn;
	}

	private void copyTabs(final I_AD_Window targetWindow, final I_AD_Window sourceWindow)
	{
		final AdWindowId targetWindowId = AdWindowId.ofRepoId(targetWindow.getAD_Window_ID());
		final Map<Integer, I_AD_Tab> existingTargetTabs = retrieveTabsQuery(targetWindowId)
				.create()
				.map(I_AD_Tab.class, I_AD_Tab::getAD_Table_ID);
		
		final Collection<I_AD_Tab> sourceTabs = retrieveTabs(sourceWindow);

		for (final I_AD_Tab sourceTab : sourceTabs)
		{
			final I_AD_Tab existingTargetTab = existingTargetTabs.get(sourceTab.getAD_Table_ID());
			copyTab(targetWindow, existingTargetTab, sourceTab);
		}
	}

	private void copyTab(final I_AD_Window targetWindow, final I_AD_Tab existingTargetTab, final I_AD_Tab sourceTab)
	{
		logger.debug("Copying tab {} to {}", sourceTab, targetWindow);

		final I_AD_Tab targetTab = createUpdateTab(targetWindow, existingTargetTab, sourceTab);

		copyTabTrl(targetTab.getAD_Tab_ID(), sourceTab.getAD_Tab_ID());

		copyFields(targetTab, sourceTab);
		copyUISections(targetTab, sourceTab);
	}

	private I_AD_Tab createUpdateTab(final I_AD_Window targetWindow, final I_AD_Tab existingTargetTab, final I_AD_Tab sourceTab)
	{
		final int targetWindowId = targetWindow.getAD_Window_ID();

		final I_AD_Tab targetTab = existingTargetTab != null ? existingTargetTab : newInstance(I_AD_Tab.class);

		copy()
				.setFrom(sourceTab)
				.setTo(targetTab)
				.copy();

		targetTab.setAD_Org_ID(targetWindow.getAD_Org_ID());
		targetTab.setAD_Window_ID(targetWindowId);

		final String entityType = targetWindow.getEntityType();
		targetTab.setEntityType(entityType);

		if (targetTab.getSeqNo() <= 0)
		{
			final int lastSeqNo = retrieveTabLastSeqNo(targetWindowId);
			targetTab.setSeqNo(lastSeqNo + 10);
		}
		save(targetTab);

		return targetTab;
	}

	private int retrieveTabLastSeqNo(int windowId)
	{
		final Integer lastSeqNo = Services.get(IQueryBL.class).createQueryBuilder(I_AD_Tab.class)
				.addEqualsFilter(I_AD_Tab.COLUMNNAME_AD_Window_ID, windowId)
				.addOnlyActiveRecordsFilter()
				.create()
				.aggregate(I_AD_Tab.COLUMNNAME_SeqNo, Aggregate.MAX, Integer.class);
		return lastSeqNo == null ? 0 : lastSeqNo;
	}

	private void copyTabTrl(final int targetTabId, final int sourceTabId)
	{
		Check.assumeGreaterThanZero(targetTabId, "targetTabId");
		Check.assumeGreaterThanZero(sourceTabId, "sourceTabId");

		final String sqlDelete = "DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = " + targetTabId;
		final int countDelete = DB.executeUpdateEx(sqlDelete, ITrx.TRXNAME_ThreadInherited);
		logger.debug("AD_Tab_Trl deleted: {}", countDelete);

		final String sqlInsert = "INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language, " +
				" AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, " +
				" Name, Description, Help, CommitWarning, IsTranslated) " +
				" SELECT " + targetTabId + ", AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, " +
				" Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated " +
				" FROM AD_Tab_Trl WHERE AD_Tab_ID = " + sourceTabId;
		final int countInsert = DB.executeUpdateEx(sqlInsert, ITrx.TRXNAME_ThreadInherited);
		logger.debug("AD_Tab_Trl inserted: {}", countInsert);
	}

	private void copyFields(final I_AD_Tab targetTab, final I_AD_Tab sourceTab)
	{
		final Map<Integer, I_AD_Field> existingTargetFields = retrieveFieldsQuery(targetTab).create().map(I_AD_Field.class, I_AD_Field::getAD_Column_ID);
		final Collection<I_AD_Field> sourceFields = retrieveFields(sourceTab);

		for (final I_AD_Field sourceField : sourceFields)
		{
			final I_AD_Field existingTargetField = existingTargetFields.get(sourceField.getAD_Column_ID());
			copyField(targetTab, existingTargetField, sourceField);
		}

	}

	private void copyField(final I_AD_Tab targetTab, final I_AD_Field existingTargetField, final I_AD_Field sourceField)
	{
		logger.debug("Copying field {} to {}", sourceField, targetTab);

		final int targetTabId = targetTab.getAD_Tab_ID();
		final String entityType = targetTab.getEntityType();

		final I_AD_Field targetField = existingTargetField != null ? existingTargetField : newInstance(I_AD_Field.class);

		copy()
				.setFrom(sourceField)
				.setTo(targetField)
				.copy();
		targetField.setAD_Org_ID(targetTab.getAD_Org_ID());
		targetField.setAD_Tab_ID(targetTabId);
		targetField.setEntityType(entityType);

		if (targetField.getSeqNo() <= 0)
		{
			final int lastSeqNo = retrieveFieldLastSeqNo(targetTabId);
			targetField.setSeqNo(lastSeqNo + 10);
		}

		save(targetField);

		copyFieldTrl(targetField.getAD_Field_ID(), sourceField.getAD_Field_ID());
	}

	private int retrieveFieldLastSeqNo(int tabId)
	{
		final Integer lastSeqNo = Services.get(IQueryBL.class).createQueryBuilder(I_AD_Field.class)
				.addEqualsFilter(I_AD_Field.COLUMNNAME_AD_Tab_ID, tabId)
				.addOnlyActiveRecordsFilter()
				.create()
				.aggregate(I_AD_Field.COLUMNNAME_SeqNo, Aggregate.MAX, Integer.class);
		return lastSeqNo == null ? 0 : lastSeqNo;
	}

	private void copyFieldTrl(final int targetFieldId, final int sourceFieldId)
	{
		Check.assumeGreaterThanZero(targetFieldId, "targetFieldId");
		Check.assumeGreaterThanZero(sourceFieldId, "sourceFieldId");

		final String sqlDelete = "DELETE FROM AD_Field_Trl WHERE AD_Field_ID = " + targetFieldId;
		final int countDelete = DB.executeUpdateEx(sqlDelete, ITrx.TRXNAME_ThreadInherited);
		logger.debug("AD_Field_Trl deleted: {}", countDelete);

		final String sqlInsert = "INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language, " +
				" AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, " +
				" Name, Description, Help, IsTranslated) " +
				" SELECT " + targetFieldId + ", AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, " +
				" Updated, UpdatedBy, Name, Description, Help, IsTranslated " +
				" FROM AD_Field_Trl WHERE AD_Field_ID = " + sourceFieldId;
		final int countInsert = DB.executeUpdateEx(sqlInsert, ITrx.TRXNAME_ThreadInherited);
		logger.debug("AD_Field_Trl inserted: {}", countInsert);
	}

	@Override
	public List<I_AD_Field> retrieveFields(final I_AD_Tab adTab)
	{
		return retrieveFieldsQuery(adTab)
				.create()
				.list();
	}

	private IQueryBuilder<I_AD_Field> retrieveFieldsQuery(final I_AD_Tab tab)
	{
		Check.assumeNotNull(tab, "adTab not null");

		final IQueryBuilder<I_AD_Field> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Field.class, tab)
				.addEqualsFilter(I_AD_Field.COLUMNNAME_AD_Tab_ID, tab.getAD_Tab_ID());

		return queryBuilder.orderBy()
				.addColumn(I_AD_Field.COLUMNNAME_AD_Field_ID)
				.endOrderBy();
	}

	@Override
	public void deleteFieldsByTabId(@NonNull final AdTabId tabId)
	{
		Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Field.class)
				.addEqualsFilter(I_AD_Field.COLUMNNAME_AD_Tab_ID, tabId)
				.create()
				.delete();
	}

	@Override
	public void deleteFieldsByColumnId(final int adColumnId)
	{
		Check.assumeGreaterThanZero(adColumnId, "adColumnId");

		Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Field.class)
				.addEqualsFilter(I_AD_Field.COLUMNNAME_AD_Column_ID, adColumnId)
				.create()
				.delete();
	}

	@Override
	public Set<AdTabId> retrieveTabIdsWithMissingADElements()
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_Tab.class)
				.addEqualsFilter(I_AD_Tab.COLUMN_AD_Element_ID, null)
				.create()
				.listIds(AdTabId::ofRepoId);
	}

	@Override
	public Set<AdWindowId> retrieveWindowIdsWithMissingADElements()
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_Window.class)
				.addEqualsFilter(I_AD_Window.COLUMN_AD_Element_ID, null)
				.create()
				.listIds(AdWindowId::ofRepoId);
	}

	@Override
	public void createMissingADElementLinks()
	{
		final ImmutableSet<AdFieldId> fieldIdsWithMissingElementLink = retrieveAllFieldIds(Env.getCtx())
				.stream()
				.filter(fieldId -> retrieveFieldADElementLink(fieldId) == null)
				.collect(ImmutableSet.toImmutableSet());

		createElementLinksForFieldIds(fieldIdsWithMissingElementLink);

		final ImmutableSet<AdTabId> tabIdsWithMissingElementLink = retrieveAllTabIds(Env.getCtx())
				.stream()
				.filter(tabId -> retrieveTabADElementLink(tabId) == null)
				.collect(ImmutableSet.toImmutableSet());

		createElementLinksForTabIds(tabIdsWithMissingElementLink);

		final ImmutableSet<AdWindowId> windowIdsWithMissingElementLink = retrieveAllWindowIds(Env.getCtx())
				.stream()
				.filter(windowId -> retrieveWindowADElementLink(windowId) == null)
				.collect(ImmutableSet.toImmutableSet());

		createElementLinksForWindowIds(windowIdsWithMissingElementLink);

	}

	@Cached(cacheName = I_AD_Field.Table_Name + "#All")
	public Set<AdFieldId> retrieveAllFieldIds(@CacheCtx final Properties ctx)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_AD_Field.class)
				.orderBy(I_AD_Field.COLUMN_AD_Field_ID)
				.create()
				.listIds(AdFieldId::ofRepoId);
	}

	private I_AD_Element_Link retrieveFieldADElementLink(final AdFieldId adFieldId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_AD_Element_Link.class)
				.addEqualsFilter(I_AD_Element_Link.COLUMN_AD_Field_ID, adFieldId.getRepoId())
				.create()
				.firstOnly(I_AD_Element_Link.class);
	}

	private void createElementLinksForFieldIds(ImmutableSet<AdFieldId> fieldIdsWithMissingElementLink)
	{
		for (final AdFieldId fieldId : fieldIdsWithMissingElementLink)
		{
			createADElementLinkForFieldId(fieldId);
		}
	}

	@Override
	public void createADElementLinkForFieldId(final AdFieldId adFieldId)
	{
		final I_AD_Field field = getFieldByIdInTrx(adFieldId.getRepoId());

		final int nameElementId = field.getAD_Name_ID();

		final int fieldElementId;

		if (nameElementId > 0)
		{
			fieldElementId = nameElementId;
		}
		else
		{
			final I_AD_Column fieldColumn = field.getAD_Column();

			fieldElementId = fieldColumn.getAD_Element_ID();
		}

		final I_AD_Element_Link elementLink = newInstance(I_AD_Element_Link.class);
		elementLink.setAD_Window_ID(field.getAD_Tab().getAD_Window_ID());
		elementLink.setAD_Field_ID(field.getAD_Field_ID());
		elementLink.setAD_Element_ID(fieldElementId);
		save(elementLink);

	}

	@Cached(cacheName = I_AD_Tab.Table_Name + "#All")
	public Set<AdTabId> retrieveAllTabIds(@CacheCtx final Properties ctx)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_AD_Tab.class)
				.orderBy(I_AD_Tab.COLUMN_AD_Tab_ID)
				.create()
				.listIds(AdTabId::ofRepoId);
	}

	private I_AD_Element_Link retrieveTabADElementLink(final AdTabId adTabId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_AD_Element_Link.class)
				.addEqualsFilter(I_AD_Element_Link.COLUMN_AD_Tab_ID, adTabId.getRepoId())
				.create()
				.firstOnly(I_AD_Element_Link.class);
	}

	private void createElementLinksForTabIds(final ImmutableSet<AdTabId> tabIdsWithMissingElementLink)
	{
		for (final AdTabId tabId : tabIdsWithMissingElementLink)
		{
			createADElementLinkForTabId(tabId);
		}
	}

	@Override
	public void createADElementLinkForTabId(final AdTabId adTabId)
	{
		final I_AD_Tab tab = getTabByIdInTrx(adTabId);

		final I_AD_Element_Link elementLink = newInstance(I_AD_Element_Link.class);
		elementLink.setAD_Window_ID(tab.getAD_Window_ID());
		elementLink.setAD_Tab_ID(tab.getAD_Tab_ID());
		elementLink.setAD_Element_ID(tab.getAD_Element_ID());
		save(elementLink);
	}

	@Cached(cacheName = I_AD_Window.Table_Name + "#All")
	public Set<AdWindowId> retrieveAllWindowIds(@CacheCtx final Properties ctx)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_AD_Window.class)
				.orderBy(I_AD_Window.COLUMN_AD_Window_ID)
				.create()
				.listIds(AdWindowId::ofRepoId);
	}

	private I_AD_Element_Link retrieveWindowADElementLink(final AdWindowId adWindowId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_AD_Element_Link.class)
				.addEqualsFilter(I_AD_Element_Link.COLUMN_AD_Window_ID, adWindowId.getRepoId())
				.addEqualsFilter(I_AD_Element_Link.COLUMN_AD_Tab_ID, null)
				.addEqualsFilter(I_AD_Element_Link.COLUMN_AD_Field_ID, null)
				.create()
				.firstOnly(I_AD_Element_Link.class);
	}

	private void createElementLinksForWindowIds(final ImmutableSet<AdWindowId> windowIdsWithMissingElementLink)
	{
		for (final AdWindowId windowId : windowIdsWithMissingElementLink)
		{
			createADElementLinkForWindowId(windowId);
		}
	}

	@Override
	public void createADElementLinkForWindowId(final AdWindowId adWindowId)
	{
		final I_AD_Window window = getWindowByIdInTrx(adWindowId);

		final I_AD_Element_Link elementLink = newInstance(I_AD_Element_Link.class);
		elementLink.setAD_Window_ID(window.getAD_Window_ID());
		elementLink.setAD_Element_ID(window.getAD_Element_ID());
		save(elementLink);
	}

	@Override
	public I_AD_Window getWindowByIdInTrx(@NonNull final AdWindowId windowId)
	{
		// use the load with ITrx.TRXNAME_ThreadInherited because the window may not yet be saved in DB when it's needed
		return load(windowId, I_AD_Window.class);
	}

	@Override
	public I_AD_Tab getTabByIdInTrx(@NonNull final AdTabId tabId)
	{
		// use the load with ITrx.TRXNAME_ThreadInherited because the tab may not yet be saved in DB when it's needed
		return load(tabId, I_AD_Tab.class);
	}

	private I_AD_Field getFieldByIdInTrx(final int fieldId)
	{
		// use the load with ITrx.TRXNAME_ThreadInherited because the tab may not yet be saved in DB when it's needed
		return load(fieldId, I_AD_Field.class);
	}

	@Override
	public void deleteExistingADElementLinkForWindowId(final AdWindowId adWindowId)
	{
		final I_AD_Element_Link windowADElementLink = retrieveWindowADElementLink(adWindowId);

		if (windowADElementLink != null)
		{
			delete(windowADElementLink);
		}
	}

	@Override
	public void deleteExistingADElementLinkForTabId(final AdTabId adTabId)
	{
		final I_AD_Element_Link tabADElementLink = retrieveTabADElementLink(adTabId);

		if (tabADElementLink != null)
		{
			delete(tabADElementLink);
		}
	}

	@Override
	public void deleteExistingADElementLinkForFieldId(final AdFieldId adFieldId)
	{
		final I_AD_Element_Link fieldADElementLink = retrieveFieldADElementLink(adFieldId);

		if (fieldADElementLink != null)
		{
			delete(fieldADElementLink);
		}
	}

	@Override
	public void deleteUIElementsByFieldId(@NonNull final AdFieldId adFieldId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		queryBL.createQueryBuilder(I_AD_UI_Element.class)
				.addEqualsFilter(I_AD_UI_Element.COLUMN_AD_Field_ID, adFieldId)
				.create()
				.delete();
	}

	@Override
	public void deleteUISectionsByTabId(@NonNull final AdTabId adTabId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		queryBL.createQueryBuilder(I_AD_UI_Section.class)
				.addEqualsFilter(I_AD_UI_Section.COLUMN_AD_Tab_ID, adTabId)
				.create()
				.delete();

	}
}
