package de.metas.acct.account_info.hierarchy;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IAcctSchemaBL;
import de.metas.i18n.TranslatableStrings;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewCloseAction;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONFilterViewRequest;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.LookupDescriptorProviders;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;
import java.util.function.Supplier;

@ViewFactory(windowId = AccountHierarchyViewFactory.WINDOWID_String)
public class AccountHierarchyViewFactory implements IViewFactory
{
	public static final String WINDOWID_String = "AccountHierarchy";
	public static final WindowId WINDOW_ID = WindowId.fromJson(WINDOWID_String);

	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	private final IAcctSchemaBL acctSchemaBL = Services.get(IAcctSchemaBL.class);
	private final LookupDataSourceFactory lookupDataSourceFactory;

	private DocumentFilterDescriptor _filterDescriptor; // lazy

	public AccountHierarchyViewFactory(@NonNull final LookupDataSourceFactory lookupDataSourceFactory)
	{
		this.lookupDataSourceFactory = lookupDataSourceFactory;
	}

	public static CreateViewRequest createViewRequest()
	{
		return CreateViewRequest.builder(WINDOW_ID).build();
	}

	@Override
	public ViewLayout getViewLayout(final WindowId windowId, final JSONViewDataType viewDataType, final ViewProfileId profileId)
	{
		return ViewLayout.builder()
				.setWindowId(WINDOW_ID)
				.setCaption(TranslatableStrings.adElementOrMessage("AccountHierarchy"))
				.setHasTreeSupport(true)
				.setTreeCollapsible(true)
				.setTreeExpandedDepth(ViewLayout.TreeExpandedDepth_ExpandedFirstLevel)
				.setAllowOpeningRowDetails(false)
				.allowViewCloseAction(ViewCloseAction.DONE)
				.addElementsFromViewRowClass(AccountHierarchyRow.class, viewDataType)
				.setFilters(ImmutableList.of(getFilterDescriptor()))
				.build();
	}

	@Override
	public IView createView(@NonNull final CreateViewRequest request)
	{
		final ViewId viewId = request.getViewId();
		viewId.assertWindowId(WINDOW_ID);

		final AccountHierarchyView.AccountHierarchyViewBuilder viewBuilder = AccountHierarchyView.builder()
				.viewId(viewId)
				.rowsData(getViewData(request))
				.filterDescriptor(getFilterDescriptor());

		// Add quick action to open BPartner Balance
		try
		{
			final AdProcessId openBPartnerBalanceProcessId = adProcessDAO.retrieveProcessIdByClass(AccountHierarchy_OpenBPartnerBalance.class);
			viewBuilder.relatedProcess(RelatedProcessDescriptor.builder()
					.processId(openBPartnerBalanceProcessId)
					.anyTable().anyWindow()
					.displayPlace(RelatedProcessDescriptor.DisplayPlace.ViewQuickActions)
					.sortNo(10)
					.build());
		}
		catch (final Exception ex)
		{
			// Process not yet registered — skip
		}

		return viewBuilder.build();
	}

	@Override
	public IView filterView(
			@NonNull final IView view,
			@NonNull final JSONFilterViewRequest filterViewRequest,
			@NonNull final Supplier<IViewsRepository> viewsRepo)
	{
		final CreateViewRequest createViewRequest = CreateViewRequest.filterViewBuilder(view, filterViewRequest).build();
		return createView(createViewRequest);
	}

	private AccountHierarchyViewData getViewData(@NonNull final CreateViewRequest request)
	{
		final AcctSchema acctSchema = acctSchemaBL.getPrimaryAcctSchema(ClientId.METASFRESH);
		final DocumentFilter effectiveFilter = getEffectiveFilter(request);

		return AccountHierarchyViewData.builder()
				.viewDataService(AccountHierarchyViewDataService.builder()
						.acctSchemaId(acctSchema.getId())
						.build())
				.filter(effectiveFilter)
				.build();
	}

	@Nullable
	private DocumentFilter getEffectiveFilter(@NonNull final CreateViewRequest request)
	{
		return request.getFiltersUnwrapped(getFilterDescriptor())
				.getFilterById(AccountHierarchyFilterHelper.FILTER_ID)
				.orElse(null);
	}

	private DocumentFilterDescriptor getFilterDescriptor()
	{
		DocumentFilterDescriptor filterDescriptor = this._filterDescriptor;
		if (filterDescriptor == null)
		{
			final LookupDescriptorProviders lookupDescriptorProviders = lookupDataSourceFactory.getLookupDescriptorProviders();
			filterDescriptor = this._filterDescriptor = AccountHierarchyFilterHelper.createFilterDescriptor(lookupDescriptorProviders);
		}
		return filterDescriptor;
	}
}
