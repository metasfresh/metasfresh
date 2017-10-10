package de.metas.ui.web.pporder.process;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.adempiere.util.Services;
import org.compiere.Adempiere;

import de.metas.handlingunits.pporder.api.IHUPPOrderQtyDAO;
import de.metas.handlingunits.sourcehu.ISourceHuService;
import de.metas.ui.web.handlingunits.HUEditorProcessTemplate;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.pporder.PPOrderLinesView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import lombok.NonNull;

public abstract class WEBUI_PP_Order_HUEditor_ProcessBase extends HUEditorProcessTemplate
{
	protected final Stream<HUEditorRow> retrieveSelectedAndEligibleHUEditorRows()
	{
		final HUEditorView huEditorView = HUEditorView.cast(super.getView());
		final List<HUEditorRow> huEditorRows = huEditorView.getByIds(getSelectedDocumentIds());

		return retrieveEligibleHUEditorRows(huEditorRows.stream());
	}

	protected final static Stream<HUEditorRow> retrieveEligibleHUEditorRows(@NonNull final Stream<HUEditorRow> inputStream)
	{
		final ISourceHuService sourceHuService = Services.get(ISourceHuService.class);
		final IHUPPOrderQtyDAO huPpOrderQtyDAO = Services.get(IHUPPOrderQtyDAO.class);

		final Stream<HUEditorRow> resultStream = inputStream
				.filter(huRow -> huRow.isHUStatusActive())
				.filter(huRow -> !sourceHuService.isSourceHUOrChildOfSourceHU(huRow.getM_HU_ID()))
				.filter(huRow -> !huPpOrderQtyDAO.isHuIdIssued(huRow.getM_HU_ID()));

		return resultStream;
	}

	protected Optional<PPOrderLinesView> getPPOrderView()
	{
		final ViewId parentViewId = getView().getParentViewId();
		if (parentViewId == null)
		{
			return Optional.empty();
		}

		final IViewsRepository viewsRepo = Adempiere.getSpringApplicationContext().getBean(IViewsRepository.class);
		final PPOrderLinesView ppOrderView = viewsRepo.getView(parentViewId, PPOrderLinesView.class);
		return Optional.of(ppOrderView);
	}

	protected final void invalidateViewsAndPrepareReturn()
	{
		invalidateView(); // picking slots view
		invalidateParentView();  // picking view

		// After this process finished successfully go back to picking slots view
		// TODO verify that this is OK
		getResult().setWebuiIncludedViewIdToOpen(getPPOrderView().get().getViewId().getViewId());
	}
}
