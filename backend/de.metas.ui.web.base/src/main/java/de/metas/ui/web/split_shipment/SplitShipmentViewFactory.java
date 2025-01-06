package de.metas.ui.web.split_shipment;

import de.metas.i18n.TranslatableStrings;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_Split;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.ViewCloseAction;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;

@ViewFactory(windowId = SplitShipmentViewFactory.WINDOWID_String)
@RequiredArgsConstructor
public class SplitShipmentViewFactory implements IViewFactory
{
	static final String WINDOWID_String = "splitShipments";
	private static final WindowId WINDOWID = WindowId.fromJson(WINDOWID_String);

	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	@NonNull private final SplitShipmentRowsService rowsService;

	@Override
	public ViewLayout getViewLayout(final WindowId windowId, JSONViewDataType viewDataType, ViewProfileId profileId)
	{
		Check.assumeEquals(windowId, WINDOWID, "windowId");

		return ViewLayout.builder()
				.setWindowId(WINDOWID)
				.setCaption(TranslatableStrings.adElementOrMessage(I_M_ShipmentSchedule_Split.COLUMNNAME_M_ShipmentSchedule_Split_ID))
				.setAllowOpeningRowDetails(false)
				.allowViewCloseAction(ViewCloseAction.CANCEL)
				.allowViewCloseAction(ViewCloseAction.DONE)
				.addElementsFromViewRowClass(SplitShipmentRow.class, viewDataType)
				.build();
	}

	public static CreateViewRequest createViewRequest(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		return CreateViewRequest.builder(WINDOWID)
				.addFilterOnlyId(shipmentScheduleId.getRepoId())
				.build();
	}

	@Override
	public SplitShipmentView createView(@NonNull final CreateViewRequest request)
	{
		final ViewId viewId = request.getViewId();
		viewId.assertWindowId(WINDOWID);

		final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(request.getSingleFilterOnlyId());
		final SplitShipmentRows rows = rowsService.getByShipmentScheduleId(shipmentScheduleId);

		return SplitShipmentView.builder()
				.viewId(viewId)
				.rows(rows)
				.relatedProcess(createProcessDescriptor(10, SplitShipmentView_ProcessAllRows.class))
				.relatedProcess(createProcessDescriptor(20, SplitShipmentView_RemoveRows.class))
				.build();
	}

	@SuppressWarnings("SameParameterValue")
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
