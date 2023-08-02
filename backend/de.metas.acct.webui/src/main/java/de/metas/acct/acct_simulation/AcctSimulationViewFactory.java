package de.metas.acct.acct_simulation;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.TranslatableStrings;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.RelatedProcessDescriptor;
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
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;

import java.util.function.Supplier;

@ViewFactory(windowId = AcctSimulationViewFactory.WINDOWID_String)
class AcctSimulationViewFactory implements IViewFactory
{
	public static final String WINDOWID_String = "acctSimulation";
	public static final WindowId WINDOW_ID = WindowId.fromJson(WINDOWID_String);

	private static final String VIEW_PARAM_DocInfo = "docInfo";

	private final AcctSimulationViewDataService dataService;
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	private IViewsRepository viewsRepository;

	public AcctSimulationViewFactory(
			@NonNull final AcctSimulationViewDataService dataService)
	{
		this.dataService = dataService;
	}

	@Override
	public void setViewsRepository(final IViewsRepository viewsRepository) {this.viewsRepository = viewsRepository;}

	@Override
	public ViewLayout getViewLayout(final WindowId windowId, final JSONViewDataType viewDataType, final ViewProfileId profileId)
	{
		return ViewLayout.builder()
				.setWindowId(WINDOW_ID)
				.setCaption(TranslatableStrings.anyLanguage("Accounting Simulation"))
				.setAllowOpeningRowDetails(false)
				.allowViewCloseAction(ViewCloseAction.CLOSE)
				.addElementsFromViewRowClass(AcctRow.class, viewDataType)
				.setFilters(ImmutableList.of())
				.build();
	}

	public AcctSimulationView createView(@NonNull final TableRecordReference docRecordRef)
	{
		final AcctSimulationDocInfo docInfo = dataService.getDocInfo(docRecordRef, ClientId.METASFRESH);
		return AcctSimulationView.cast(
				viewsRepository.createView(CreateViewRequest.builder(WINDOW_ID)
						.setParameter(VIEW_PARAM_DocInfo, docInfo)
						.build())
		);
	}

	@Override
	public IView filterView(final @NonNull IView view, final @NonNull JSONFilterViewRequest filterViewRequest, final @NonNull Supplier<IViewsRepository> viewsRepo)
	{
		final AcctSimulationView acctSimulationView = AcctSimulationView.cast(view);
		final CreateViewRequest createViewRequest = CreateViewRequest.filterViewBuilder(view, filterViewRequest)
				.setParameter(VIEW_PARAM_DocInfo, acctSimulationView.getDocInfo())
				.build();
		return createView(createViewRequest);
	}

	/**
	 * @deprecated Don't call it directly. Shall be called only by API!
	 */
	@Override
	@Deprecated
	@SuppressWarnings("DeprecatedIsStillUsed")
	public IView createView(@NonNull final CreateViewRequest request)
	{
		final ViewId viewId = request.getViewId();
		viewId.assertWindowId(WINDOW_ID);

		return AcctSimulationView.builder()
				.rowsData(getViewData(request))
				.relatedProcess(createProcessDescriptor(10, AcctSimulationView_AddRow.class))
				.relatedProcess(createProcessDescriptor(20, AcctSimulationView_RemoveRows.class))
				.relatedProcess(createProcessDescriptor(30, AcctSimulationView_UpdateSimulation.class))
				.relatedProcess(createProcessDescriptor(40, AcctSimulationView_Save.class))
				.build();
	}

	private AcctSimulationViewData getViewData(final @NonNull CreateViewRequest request)
	{
		final AcctSimulationDocInfo docInfo = request.getParameterAs(VIEW_PARAM_DocInfo, AcctSimulationDocInfo.class);
		if (docInfo == null)
		{
			throw new AdempiereException("Parameter " + VIEW_PARAM_DocInfo + " is missing from " + request);
		}

		return dataService.getViewData(docInfo, request.getViewId());
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

}
