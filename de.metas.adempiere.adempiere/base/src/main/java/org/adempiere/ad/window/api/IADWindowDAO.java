package org.adempiere.ad.window.api;

import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.element.api.AdFieldId;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.model.I_AD_Tab_Callout;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_UI_Column;
import org.compiere.model.I_AD_UI_Element;
import org.compiere.model.I_AD_UI_ElementField;
import org.compiere.model.I_AD_UI_ElementGroup;
import org.compiere.model.I_AD_UI_Section;
import org.compiere.model.I_AD_Window;

import de.metas.i18n.ITranslatableString;
import de.metas.lang.SOTrx;
import de.metas.util.ISingletonService;

public interface IADWindowDAO extends ISingletonService
{

	/**
	 * Loads the given window (cached) and returns its name. Uses {@link org.compiere.util.Env#getCtx()} to get the context.
	 *
	 * @param adWindowId
	 * @return the name for the given <code>AD_Window_ID</code> or <code>null</code> if the given ID is less or equal zero.
	 */
	ITranslatableString retrieveWindowName(AdWindowId adWindowId);

	String retrieveInternalWindowName(AdWindowId adWindowId);

	AdWindowId getWindowIdByInternalName(String internalName);

	List<I_AD_UI_ElementField> retrieveUIElementFields(final I_AD_UI_Element uiElement);

	List<I_AD_UI_Element> retrieveUIElements(final I_AD_UI_ElementGroup uiElementGroup);

	IQueryBuilder<I_AD_UI_Element> retrieveUIElementsQueryByTabId(AdTabId adTabId);

	List<I_AD_UI_ElementGroup> retrieveUIElementGroups(final I_AD_UI_Column uiColumn);

	List<I_AD_UI_Column> retrieveUIColumns(final I_AD_UI_Section uiSection);

	List<I_AD_UI_Section> retrieveUISections(AdTabId adTabId);

	List<I_AD_UI_Section> retrieveUISections(final I_AD_Tab adTab);

	boolean hasUISections(I_AD_Tab adTab);

	List<I_AD_Tab> retrieveTabs(final I_AD_Window adWindow);

	void deleteTabsByWindowId(AdWindowId adWindowId);

	void moveElementGroup(I_AD_UI_ElementGroup uiElementGroup, I_AD_UI_Column toUIColumn);

	void moveElement(I_AD_UI_Element uiElement, I_AD_UI_ElementGroup toUIElementGroup);

	/**
	 * Retrieve the first tab of the given window (seqNo = 10)
	 *
	 * @param window
	 * @return
	 */
	I_AD_Tab retrieveFirstTab(final AdWindowId adWindowId);

	void copyWindow(AdWindowId targetWindowId, AdWindowId sourceWindowId);

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

	int getUIElementNextSeqNo(UIElementGroupId uiElementGroupId);


	/**
	 * All parameters are mandatory for now.
	 *
	 * @return never return {@code null}
	 */
	AdWindowId getAdWindowId(String tableName, SOTrx soTrx, AdWindowId defaultValue);

	List<I_AD_Tab_Callout> retrieveTabCallouts(AdTabId tabId);
}
