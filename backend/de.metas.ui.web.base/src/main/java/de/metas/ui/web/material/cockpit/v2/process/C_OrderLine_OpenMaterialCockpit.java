package de.metas.ui.web.material.cockpit.v2.process;

import de.metas.process.IProcessPrecondition;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult.ViewOpenTarget;
import de.metas.process.ProcessExecutionResult.WebuiViewToOpen;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.material.cockpit.v2.MaterialCockpitV2Util;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import org.compiere.model.I_C_OrderLine;
import org.springframework.beans.factory.annotation.Autowired;

public class C_OrderLine_OpenMaterialCockpit extends JavaProcess implements IProcessPrecondition
{
	@Autowired
	private transient IViewsRepository viewsRepo;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (context.getSelectedIncludedRecords().isEmpty()
				&& context.getSingleSelectedRecordIdOrNull() == null)
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final I_C_OrderLine orderLine = getRecord(I_C_OrderLine.class);

		final int productId = orderLine.getM_Product_ID();
		final int warehouseId = orderLine.getM_Warehouse_ID();

		final DocumentFilter.DocumentFilterBuilder filterBuilder = DocumentFilter.builder()
				.setFilterId("SOLineContext");

		if (productId > 0)
		{
			filterBuilder.addParameter(DocumentFilterParam.builder()
					.setFieldName("ProductValue")
					.setValue(orderLine.getM_Product().getValue())
					.build());
		}

		if (warehouseId > 0)
		{
			filterBuilder.addParameter(DocumentFilterParam.builder()
					.setFieldName("M_Warehouse_ID")
					.setValue(warehouseId)
					.build());
		}

		final CreateViewRequest viewRequest = CreateViewRequest
				.builder(MaterialCockpitV2Util.WINDOWID_MaterialCockpitV2)
				.addStickyFilters(filterBuilder.build())
				.build();

		final IView view = viewsRepo.createView(viewRequest);

		getResult().setWebuiViewToOpen(WebuiViewToOpen.builder()
				.viewId(view.getViewId().getViewId())
				.target(ViewOpenTarget.ModalOverlay)
				.build());

		return MSG_OK;
	}
}
