package de.metas.frontend_testing.masterdata.inlinefilter;

import com.google.common.collect.ImmutableList;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.column.AdColumnId;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.element.api.AdFieldId;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.element.api.AdUIElementId;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_AD_Ref_List;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_UI_Element;
import org.compiere.model.I_AD_Window;
import org.compiere.model.X_AD_UI_Element;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

/**
 * Record access for the inline filter setup: {@code AD_Column.IsShowFilterInline} and {@code AD_UI_Element.WidgetSize}.
 * Records are always loaded fresh from the database, because they are modified and saved.
 */
@Component
public class InlineFilterRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
	private final IADWindowDAO adWindowDAO = Services.get(IADWindowDAO.class);

	public Optional<AdColumnId> getColumnId(@NonNull final String tableName, @NonNull final String columnName)
	{
		final I_AD_Column column = adTableDAO.retrieveColumnOrNull(tableName, columnName);
		return column != null ? Optional.of(AdColumnId.ofRepoId(column.getAD_Column_ID())) : Optional.empty();
	}

	public boolean isShowFilterInline(@NonNull final AdColumnId columnId)
	{
		return getColumn(columnId).isShowFilterInline();
	}

	public void setShowFilterInline(@NonNull final AdColumnId columnId, final boolean showFilterInline)
	{
		final I_AD_Column column = getColumn(columnId);
		column.setIsShowFilterInline(showFilterInline);
		InterfaceWrapperHelper.saveRecord(column);
	}

	/**
	 * The main tab is the one with {@code TabLevel=0} and the lowest {@code SeqNo}.
	 *
	 * @return empty if the window does not exist or has no active main tab
	 */
	public Optional<AdTabId> getMainTabId(@NonNull final AdWindowId windowId)
	{
		final boolean windowExists = queryBL.createQueryBuilder(I_AD_Window.class)
				.addEqualsFilter(I_AD_Window.COLUMNNAME_AD_Window_ID, windowId)
				.addOnlyActiveRecordsFilter()
				.create()
				.anyMatch();
		if (!windowExists)
		{
			return Optional.empty();
		}

		return adWindowDAO.retrieveTabs(adWindowDAO.getById(windowId)).stream()
				.filter(tab -> tab.getTabLevel() == 0)
				.findFirst() // retrieveTabs is ordered by SeqNo
				.map(tab -> AdTabId.ofRepoId(tab.getAD_Tab_ID()));
	}

	/**
	 * @return the {@code AD_Field_ID} of the column in the tab, if there is one
	 */
	public Optional<AdFieldId> getFieldId(@NonNull final AdTabId tabId, @NonNull final AdColumnId columnId)
	{
		final I_AD_Tab tab = InterfaceWrapperHelper.load(tabId, I_AD_Tab.class);
		return adWindowDAO.retrieveFields(tab).stream()
				.filter(field -> field.getAD_Column_ID() == columnId.getRepoId())
				.map(I_AD_Field::getAD_Field_ID)
				.map(AdFieldId::ofRepoId)
				.findFirst();
	}

	public List<AdUIElementId> getUIElementIds(@NonNull final AdTabId tabId, @NonNull final AdFieldId fieldId)
	{
		return adWindowDAO.retrieveUIElementsQueryByTabId(tabId)
				.addEqualsFilter(I_AD_UI_Element.COLUMNNAME_AD_Field_ID, fieldId)
				.create()
				.stream(I_AD_UI_Element.class)
				.map(element -> AdUIElementId.ofRepoId(element.getAD_UI_Element_ID()))
				.collect(ImmutableList.toImmutableList());
	}

	@Nullable
	public String getWidgetSize(@NonNull final AdUIElementId elementId)
	{
		return getUIElement(elementId).getWidgetSize();
	}

	public void setWidgetSize(@NonNull final AdUIElementId elementId, @NonNull final String widgetSize)
	{
		final I_AD_UI_Element element = getUIElement(elementId);
		element.setWidgetSize(widgetSize);
		InterfaceWrapperHelper.saveRecord(element);
	}

	public boolean isValidWidgetSize(@NonNull final String widgetSize)
	{
		return queryBL.createQueryBuilder(I_AD_Ref_List.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Ref_List.COLUMNNAME_AD_Reference_ID, X_AD_UI_Element.WIDGETSIZE_AD_Reference_ID)
				.addEqualsFilter(I_AD_Ref_List.COLUMNNAME_Value, widgetSize)
				.create()
				.anyMatch();
	}

	private I_AD_Column getColumn(final AdColumnId columnId)
	{
		return queryBL.createQueryBuilder(I_AD_Column.class)
				.addEqualsFilter(I_AD_Column.COLUMNNAME_AD_Column_ID, columnId)
				.create()
				.firstOnlyNotNull(I_AD_Column.class);
	}

	private I_AD_UI_Element getUIElement(final AdUIElementId elementId)
	{
		return queryBL.createQueryBuilder(I_AD_UI_Element.class)
				.addEqualsFilter(I_AD_UI_Element.COLUMNNAME_AD_UI_Element_ID, elementId)
				.create()
				.firstOnlyNotNull(I_AD_UI_Element.class);
	}
}
