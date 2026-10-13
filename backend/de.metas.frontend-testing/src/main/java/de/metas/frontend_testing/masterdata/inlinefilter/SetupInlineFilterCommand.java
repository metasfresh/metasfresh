package de.metas.frontend_testing.masterdata.inlinefilter;

import de.metas.ad_reference.ADReferenceService;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_UI_Element;
import org.compiere.model.I_AD_Window;
import org.compiere.model.X_AD_UI_Element;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Sets {@code AD_Column.IsShowFilterInline} and the {@code AD_UI_Element.WidgetSize} of the column's field in the window's main tab.
 * <p>
 * The element is resolved strictly and always, also for a request that only sets {@code showInline}:
 * window, then main tab (active, {@code TabLevel=0}, lowest {@code SeqNo}), then the active field of the column in that tab, then the active UI element of that field.
 * Nothing is created: a missing window, column, field or element, or more than one element, is an error.
 * <p>
 * Two steps: {@link #validate()} resolves and checks everything without writing; {@link #execute()} writes.
 * Callers with several requests validate all of them first, so a rejected request writes nothing.
 * <p>
 * This only writes AD data: the caller must reset the webapi caches ({@code GET /cache/reset}) afterwards.
 */
public class SetupInlineFilterCommand
{
	@NonNull private final IADTableDAO adTableDAO;
	@NonNull private final IADWindowDAO adWindowDAO;
	@NonNull private final ADReferenceService adReferenceService;
	@NonNull private final JsonSetupInlineFilterRequest request;

	@Nullable private I_AD_Column column;
	@Nullable private I_AD_UI_Element element;

	@Builder
	private SetupInlineFilterCommand(
			@NonNull final IADTableDAO adTableDAO,
			@NonNull final IADWindowDAO adWindowDAO,
			@NonNull final ADReferenceService adReferenceService,
			@NonNull final JsonSetupInlineFilterRequest request)
	{
		this.adTableDAO = adTableDAO;
		this.adWindowDAO = adWindowDAO;
		this.adReferenceService = adReferenceService;
		this.request = request;
	}

	/** Resolves the column and the UI element and checks the request. Does not write. */
	public SetupInlineFilterCommand validate()
	{
		final String widgetSize = request.getWidgetSize();
		if (widgetSize != null
				&& adReferenceService.retrieveListItemOrNull(X_AD_UI_Element.WIDGETSIZE_AD_Reference_ID, widgetSize) == null)
		{
			throw new AdempiereException("Invalid widgetSize `" + widgetSize + "`; it is not an active value of the WidgetSize reference list");
		}

		column = adTableDAO.retrieveColumn(request.getTableName(), request.getColumnName());
		element = resolveUIElement(column.getAD_Column_ID());
		return this;
	}

	public JsonSetupInlineFilterResponse execute()
	{
		if (column == null || element == null)
		{
			validate();
		}
		final I_AD_Column column = this.column;
		final I_AD_UI_Element element = this.element;
		assert column != null && element != null;

		final JsonSetupInlineFilterResponse previousValues = JsonSetupInlineFilterResponse.builder()
				.previousShowInline(column.isShowFilterInline())
				.previousWidgetSize(element.getWidgetSize())
				.build();

		if (request.getShowInline() != null)
		{
			column.setIsShowFilterInline(request.getShowInline());
			adTableDAO.saveColumn(column);
		}
		if (request.getWidgetSize() != null)
		{
			element.setWidgetSize(request.getWidgetSize());
			adWindowDAO.saveUIElement(element);
		}

		return previousValues;
	}

	private String columnDisplayName()
	{
		return request.getTableName() + "." + request.getColumnName();
	}

	private I_AD_UI_Element resolveUIElement(final int columnId)
	{
		final String where = "column " + columnDisplayName() + " in the main tab of window " + request.getWindowId();

		if (request.getWindowId() <= 0)
		{
			throw new AdempiereException("windowId is required (got " + request.getWindowId() + ")");
		}
		final AdWindowId windowId = AdWindowId.ofRepoId(request.getWindowId());
		if (!adWindowDAO.retrieveAllActiveAdWindowIds().contains(windowId))
		{
			throw new AdempiereException("Unknown or inactive window " + windowId.getRepoId());
		}

		final I_AD_Window window = adWindowDAO.getById(windowId);
		final I_AD_Tab mainTab = adWindowDAO.retrieveTabs(window).stream() // active tabs, ordered by SeqNo
				.filter(tab -> tab.getTabLevel() == 0)
				.findFirst()
				.orElseThrow(() -> new AdempiereException("Window " + windowId.getRepoId() + " has no active main tab"));

		final I_AD_Field field = adWindowDAO.retrieveFields(mainTab).stream()
				.filter(I_AD_Field::isActive)
				.filter(candidate -> candidate.getAD_Column_ID() == columnId)
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No active field for " + where));

		final List<I_AD_UI_Element> elements = adWindowDAO.retrieveUIElementsQueryByTabId(AdTabId.ofRepoId(mainTab.getAD_Tab_ID()))
				.addEqualsFilter(I_AD_UI_Element.COLUMNNAME_AD_Field_ID, field.getAD_Field_ID())
				.create()
				.list();
		if (elements.isEmpty())
		{
			throw new AdempiereException("No active UI element for " + where + "; not creating one");
		}
		if (elements.size() > 1)
		{
			throw new AdempiereException(elements.size() + " UI elements for " + where + "; refusing to guess");
		}
		return elements.get(0);
	}
}
