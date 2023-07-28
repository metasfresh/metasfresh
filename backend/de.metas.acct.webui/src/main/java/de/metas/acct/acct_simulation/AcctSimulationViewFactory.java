package de.metas.acct.acct_simulation;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.TranslatableStrings;
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
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;

import java.util.function.Supplier;

@ViewFactory(windowId = AcctSimulationViewFactory.WINDOWID_String)
public class AcctSimulationViewFactory implements IViewFactory
{
	public static final String WINDOWID_String = "acctSimulation";
	public static final WindowId WINDOW_ID = WindowId.fromJson(WINDOWID_String);

	private static final String VIEW_PARAM_DocRecordRef = "docRecordRef";

	private final AcctSimulationViewDataService acctSimulationViewDataService;

	public AcctSimulationViewFactory(
			@NonNull final AcctSimulationViewDataService acctSimulationViewDataService)
	{
		this.acctSimulationViewDataService = acctSimulationViewDataService;
	}

	@Override
	public ViewLayout getViewLayout(final WindowId windowId, final JSONViewDataType viewDataType, final ViewProfileId profileId)
	{
		return ViewLayout.builder()
				.setWindowId(WINDOW_ID)
				.setCaption(TranslatableStrings.anyLanguage("Accounting Simulation"))
				.setAllowOpeningRowDetails(false)
				.allowViewCloseAction(ViewCloseAction.DONE)
				.addElementsFromViewRowClass(AcctRow.class, viewDataType)
				.setFilters(ImmutableList.of())
				.build();
	}

	public static CreateViewRequest createViewRequest(@NonNull final TableRecordReference docRecordRef)
	{
		return CreateViewRequest.builder(WINDOW_ID)
				.setParameter(VIEW_PARAM_DocRecordRef, docRecordRef)
				.build();
	}

	@Override
	public IView filterView(final @NonNull IView view, final @NonNull JSONFilterViewRequest filterViewRequest, final @NonNull Supplier<IViewsRepository> viewsRepo)
	{
		final AcctSimulationView acctSimulationView = AcctSimulationView.cast(view);
		final CreateViewRequest createViewRequest = CreateViewRequest.filterViewBuilder(view, filterViewRequest)
				.setParameter(VIEW_PARAM_DocRecordRef, acctSimulationView.getDocRecordRef())
				.build();
		return createView(createViewRequest);
	}

	@Override
	public IView createView(@NonNull final CreateViewRequest request)
	{
		final ViewId viewId = request.getViewId();
		viewId.assertWindowId(WINDOW_ID);

		return AcctSimulationView.builder()
				.viewId(viewId)
				.rowsData(getViewData(request))
				.build();
	}

	private AcctSimulationViewData getViewData(final @NonNull CreateViewRequest request)
	{
		final TableRecordReference docRecordRef = request.getParameterAs(VIEW_PARAM_DocRecordRef, TableRecordReference.class);
		if (docRecordRef == null)
		{
			throw new AdempiereException("Parameter " + VIEW_PARAM_DocRecordRef + " is missing from " + request);
		}

		return acctSimulationViewDataService.getViewData(docRecordRef, ClientId.METASFRESH);
	}

}
