package de.metas.acct.gljournal_sap.select_open_items;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IAcctSchemaBL;
import de.metas.acct.gljournal_sap.SAPGLJournalId;
import de.metas.acct.gljournal_sap.service.SAPGLJournalService;
import de.metas.elementvalue.ElementValueService;
import de.metas.i18n.TranslatableStrings;
import de.metas.money.MoneyService;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.ViewCloseAction;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.LookupDescriptorProviders;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.acct.api.IFactAcctBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;

@ViewFactory(windowId = OIViewFactory.WINDOWID_String)
public class OIViewFactory implements IViewFactory
{
	public static final String WINDOWID_String = "SAPGLJournalSelectOpenItems";
	public static final WindowId WINDOW_ID = WindowId.fromJson(WINDOWID_String);
	private static final String VIEW_PARAM_SAP_GLJournal_ID = "SAP_GLJournal_ID";

	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	private final IAcctSchemaBL acctSchemaBL = Services.get(IAcctSchemaBL.class);
	private final LookupDataSourceFactory lookupDataSourceFactory;
	private final OIViewDataService viewDataService;

	private DocumentFilterDescriptor _filterDescriptor; // lazy

	public OIViewFactory(
			@NonNull final LookupDataSourceFactory lookupDataSourceFactory,
			@NonNull final SAPGLJournalService glJournalService,
			@NonNull final MoneyService moneyService,
			@NonNull final ElementValueService elementValueService)
	{
		this.lookupDataSourceFactory = lookupDataSourceFactory;
		this.viewDataService = OIViewDataService.builder()
				.lookupDataSourceFactory(lookupDataSourceFactory)
				.factAcctBL(Services.get(IFactAcctBL.class))
				.acctSchemaBL(Services.get(IAcctSchemaBL.class))
				.moneyService(moneyService)
				.glJournalService(glJournalService)
				.elementValueService(elementValueService)
				.build();
	}

	public static CreateViewRequest createViewRequest(final SAPGLJournalId sapglJournalId)
	{
		return CreateViewRequest.builder(OIViewFactory.WINDOW_ID)
				.setParameter(VIEW_PARAM_SAP_GLJournal_ID, sapglJournalId)
				.build();
	}

	@Override
	public ViewLayout getViewLayout(final WindowId windowId, final JSONViewDataType viewDataType, final ViewProfileId profileId)
	{
		return ViewLayout.builder()
				.setWindowId(WINDOW_ID)
				.setCaption(TranslatableStrings.anyLanguage("Select Open Items")) // TODO trl
				.setAllowOpeningRowDetails(false)
				.allowViewCloseAction(ViewCloseAction.DONE)
				.addElementsFromViewRowClass(OIRow.class, viewDataType)
				.setFilters(ImmutableList.of(getFilterDescriptor()))
				.build();
	}

	@Override
	public IView createView(@NonNull final CreateViewRequest request)
	{
		final ViewId viewId = request.getViewId();
		viewId.assertWindowId(WINDOW_ID);

		return OIView.builder()
				.viewId(viewId)
				.rowsData(getViewData(request))
				.filterDescriptor(getFilterDescriptor())
				.sapglJournalId(extractSAPGLJournalId(request))
				.relatedProcess(createProcessDescriptor(10, OIView_Select.class))
				.relatedProcess(createProcessDescriptor(20, OIView_AddToJournal.class))
				.build();
	}

	private RelatedProcessDescriptor createProcessDescriptor(final int sortNo, @NonNull final Class<?> processClass)
	{
		final AdProcessId processId = adProcessDAO.retrieveProcessIdByClass(processClass);
		if (processId == null)
		{
			throw new AdempiereException("No processId found for " + processClass);
		}

		return RelatedProcessDescriptor.builder()
				.processId(processId)
				.anyTable().anyWindow()
				.displayPlace(RelatedProcessDescriptor.DisplayPlace.ViewQuickActions)
				.sortNo(sortNo)
				.build();
	}

	private OIViewData getViewData(final @NonNull CreateViewRequest request)
	{
		final SAPGLJournalId sapglJournalId = extractSAPGLJournalId(request);
		final DocumentFilter effectiveFilter = getEffectiveFilter(request);
		return viewDataService.getData(sapglJournalId, effectiveFilter);
	}

	@NonNull
	private static SAPGLJournalId extractSAPGLJournalId(final @NonNull CreateViewRequest request)
	{
		return Check.assumeNotNull(request.getParameterAs(VIEW_PARAM_SAP_GLJournal_ID, SAPGLJournalId.class), "No {} parameter provided", VIEW_PARAM_SAP_GLJournal_ID);
	}

	@Nullable
	private DocumentFilter getEffectiveFilter(final @NonNull CreateViewRequest request)
	{
		return request.getFiltersUnwrapped(getFilterDescriptor())
				.getFilterById(OIViewFilterHelper.FILTER_ID)
				.orElse(null);
	}

	private DocumentFilterDescriptor getFilterDescriptor()
	{
		DocumentFilterDescriptor filterDescriptor = this._filterDescriptor;
		if (filterDescriptor == null)
		{
			final AcctSchema primaryAcctSchema = acctSchemaBL.getPrimaryAcctSchema(ClientId.METASFRESH);
			final LookupDescriptorProviders lookupDescriptorProviders = lookupDataSourceFactory.getLookupDescriptorProviders();
			filterDescriptor = this._filterDescriptor = OIViewFilterHelper.createFilterDescriptor(lookupDescriptorProviders, primaryAcctSchema);
		}

		return filterDescriptor;
	}

}
