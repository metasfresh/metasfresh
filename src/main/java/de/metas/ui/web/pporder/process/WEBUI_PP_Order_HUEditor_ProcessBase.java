package de.metas.ui.web.pporder.process;

import java.util.Optional;
import java.util.stream.Stream;

import org.adempiere.util.Services;
import org.compiere.Adempiere;

import de.metas.handlingunits.pporder.api.IHUPPOrderQtyDAO;
import de.metas.handlingunits.sourcehu.SourceHUsService;
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
		final Stream<HUEditorRow> huEditorRows = huEditorView.streamByIds(getSelectedRowIds());

		return retrieveEligibleHUEditorRows(huEditorRows);
	}

	protected final static Stream<HUEditorRow> retrieveEligibleHUEditorRows(@NonNull final Stream<HUEditorRow> inputStream)
	{
		final SourceHUsService sourceHuService = SourceHUsService.get();
		final IHUPPOrderQtyDAO huPpOrderQtyDAO = Services.get(IHUPPOrderQtyDAO.class);

		return inputStream
				.filter(huRow -> huRow.isHUStatusActive())
				.filter(huRow -> !sourceHuService.isHuOrAnyParentSourceHu(huRow.getM_HU_ID()))
				.filter(huRow -> !huPpOrderQtyDAO.isHuIdIssued(huRow.getM_HU_ID()));
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
}
