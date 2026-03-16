package de.metas.acct.account_info.bpartner_balance;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.IAcctSchemaBL;
import de.metas.elementvalue.ElementValueService;
import de.metas.i18n.TranslatableStrings;
import de.metas.money.MoneyService;
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
import org.adempiere.acct.api.IFactAcctBL;

import javax.annotation.Nullable;
import java.util.function.Supplier;

@ViewFactory(windowId = BPartnerBalanceViewFactory.WINDOWID_String)
public class BPartnerBalanceViewFactory implements IViewFactory
{
	public static final String WINDOWID_String = "BPartnerBalance";
	public static final WindowId WINDOW_ID = WindowId.fromJson(WINDOWID_String);

	private final BPartnerBalanceViewDataService viewDataService;
	private final LookupDataSourceFactory lookupDataSourceFactory;

	private DocumentFilterDescriptor _filterDescriptor; // lazy

	public BPartnerBalanceViewFactory(
			@NonNull final LookupDataSourceFactory lookupDataSourceFactory,
			@NonNull final MoneyService moneyService,
			@NonNull final ElementValueService elementValueService)
	{
		this.lookupDataSourceFactory = lookupDataSourceFactory;
		this.viewDataService = BPartnerBalanceViewDataService.builder()
				.factAcctBL(Services.get(IFactAcctBL.class))
				.acctSchemaBL(Services.get(IAcctSchemaBL.class))
				.moneyService(moneyService)
				.elementValueService(elementValueService)
				.build();
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
				.setCaption(TranslatableStrings.adElementOrMessage("BPartnerBalance"))
				.setAllowOpeningRowDetails(false)
				.allowViewCloseAction(ViewCloseAction.DONE)
				.addElementsFromViewRowClass(BPartnerBalanceRow.class, viewDataType)
				.setFilters(ImmutableList.of(getFilterDescriptor()))
				.build();
	}

	@Override
	public IView createView(@NonNull final CreateViewRequest request)
	{
		final ViewId viewId = request.getViewId();
		viewId.assertWindowId(WINDOW_ID);

		return BPartnerBalanceView.builder()
				.viewId(viewId)
				.rowsData(getViewData(request))
				.filterDescriptor(getFilterDescriptor())
				.build();
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

	private BPartnerBalanceViewData getViewData(@NonNull final CreateViewRequest request)
	{
		final DocumentFilter effectiveFilter = getEffectiveFilter(request);
		return viewDataService.getData(effectiveFilter);
	}

	@Nullable
	private DocumentFilter getEffectiveFilter(@NonNull final CreateViewRequest request)
	{
		return request.getFiltersUnwrapped(getFilterDescriptor())
				.getFilterById(BPartnerBalanceFilterHelper.FILTER_ID)
				.orElse(null);
	}

	private DocumentFilterDescriptor getFilterDescriptor()
	{
		DocumentFilterDescriptor filterDescriptor = this._filterDescriptor;
		if (filterDescriptor == null)
		{
			final LookupDescriptorProviders lookupDescriptorProviders = lookupDataSourceFactory.getLookupDescriptorProviders();
			filterDescriptor = this._filterDescriptor = BPartnerBalanceFilterHelper.createFilterDescriptor(lookupDescriptorProviders);
		}
		return filterDescriptor;
	}
}
