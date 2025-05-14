package org.adempiere.ad.window.api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.i18n.ITranslatableString;
import de.metas.lang.SOTrx;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdFieldId;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.element.api.AdUIColumnId;
import org.adempiere.ad.element.api.AdUIElementGroupId;
import org.adempiere.ad.element.api.AdUIElementId;
import org.adempiere.ad.element.api.AdUISectionId;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.model.I_AD_Tab_Callout;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_UI_Column;
import org.compiere.model.I_AD_UI_Element;
import org.compiere.model.I_AD_UI_ElementField;
import org.compiere.model.I_AD_UI_ElementGroup;
import org.compiere.model.I_AD_UI_Section;
import org.compiere.model.I_AD_Window;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

public interface IADWindowDAO extends ISingletonService
{

	/**
	 * Loads the given window (cached) and returns its name. Uses {@link org.compiere.util.Env#getCtx()} to get the context.
	 *
	 * @return the name for the given <code>AD_Window_ID</code> or <code>null</code> if the given ID is less or equal zero.
	 */
	ITranslatableString retrieveWindowName(AdWindowId adWindowId);

	AdWindowId getWindowIdByInternalName(String internalName);

	ImmutableList<I_AD_UI_ElementField> retrieveUIElementFields(Set<AdUIElementId> uiElementIds);

	ImmutableList<I_AD_UI_ElementField> retrieveUIElementFields(final I_AD_UI_Element uiElement);

	ImmutableList<I_AD_UI_Element> retrieveUIElements(Set<AdUIElementGroupId> uiElementGroupIds);

	ImmutableList<I_AD_UI_Element> retrieveUIElements(final I_AD_UI_ElementGroup uiElementGroup);

	ImmutableList<I_AD_UI_ElementGroup> retrieveUIElementGroups(Set<AdUIColumnId> uiColumnIds);

	ImmutableList<I_AD_UI_ElementGroup> retrieveUIElementGroups(final I_AD_UI_Column uiColumn);

	ImmutableList<I_AD_UI_Column> retrieveUIColumns(Set<AdUISectionId> uiSectionIds);

	ImmutableList<I_AD_UI_Column> retrieveUIColumns(final I_AD_UI_Section uiSection);

	ImmutableList<I_AD_UI_Section> retrieveUISections(Set<AdTabId> adTabIds);

	List<I_AD_UI_Section> retrieveUISections(final I_AD_Tab adTab);

	boolean hasUISections(I_AD_Tab adTab);

	List<I_AD_Tab> retrieveTabs(final I_AD_Window adWindow);

	void moveElementGroup(I_AD_UI_ElementGroup uiElementGroup, I_AD_UI_Column toUIColumn);

	void moveElement(I_AD_UI_Element uiElement, I_AD_UI_ElementGroup toUIElementGroup);

	/**
	 * Retrieve the first tab of the given window (seqNo = 10)
	 */
	@Nullable
	I_AD_Tab retrieveFirstTab(final AdWindowId adWindowId);

	@Nullable
	String getFirstTabWhereClause(@NonNull AdWindowId adWindowId);

	void copyWindow(@NonNull WindowCopyRequest request);

	List<I_AD_Field> retrieveFields(I_AD_Tab adTab);

	void deleteFieldsByTabId(AdTabId tabId);

	void deleteFieldsByColumnId(int adColumnId);

	Set<AdTabId> retrieveTabIdsWithMissingADElements();

	Set<AdWindowId> retrieveWindowIdsWithMissingADElements();

	I_AD_Window getById(AdWindowId adWindowId);

	I_AD_Window getWindowByIdInTrx(AdWindowId windowId);

	I_AD_Tab getTabByIdInTrx(AdTabId tabId);

	void deleteUIElementsByFieldId(AdFieldId adFieldId);

	void deleteUISectionsByTabId(AdTabId adTabId);

	int getUIElementNextSeqNo(AdUIElementGroupId uiElementGroupId);

	/**
	 * All parameters are mandatory for now.
	 *
	 * @return never return {@code null}
	 */
	@Deprecated
	AdWindowId getAdWindowId(String tableName, SOTrx soTrx, AdWindowId defaultValue);

	List<I_AD_Tab_Callout> retrieveTabCallouts(AdTabId tabId);

	AdTabId copyTabToWindow(I_AD_Tab sourceTab, AdWindowId targetWindowId);

	ImmutableSet<AdWindowId> retrieveAllAdWindowIdsByTableId(AdTableId adTableId);

	ImmutableSet<AdWindowId> retrieveAllActiveAdWindowIds();
}
