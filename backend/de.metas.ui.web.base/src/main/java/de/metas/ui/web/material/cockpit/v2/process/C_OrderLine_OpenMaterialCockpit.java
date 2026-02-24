package de.metas.ui.web.material.cockpit.v2.process;

import de.metas.interfaces.I_C_OrderLine;
import de.metas.material.cockpit.model.I_QtyDemand_QtySupply_V;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderLineId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult.ViewOpenTarget;
import de.metas.process.ProcessExecutionResult.WebuiViewToOpen;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.product.ProductId;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.material.cockpit.v2.MaterialCockpitV2Util;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;

public class C_OrderLine_OpenMaterialCockpit extends JavaProcess implements IProcessPrecondition
{
	@NonNull private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	@NonNull private final IViewsRepository viewsRepo = SpringContextHolder.instance.getBean(IViewsRepository.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (context.getSelectedIncludedRecords().size() != 1)
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final OrderLineId orderLineId = CollectionUtils.singleElement(getSelectedIncludedRecordIds(I_C_OrderLine.class, OrderLineId::ofRepoId));
		final I_C_OrderLine orderLine = orderLineBL.getOrderLineById(orderLineId);

		final CreateViewRequest viewRequest = CreateViewRequest
				.builder(MaterialCockpitV2Util.WINDOWID_MaterialCockpitV2)
				.addStickyFilters(extractCockpitFilter(orderLine))
				.build();

		final ViewId viewId = viewsRepo.createView(viewRequest).getViewId();

		getResult().setWebuiViewToOpen(WebuiViewToOpen.builder()
				.viewId(viewId.getViewId())
				.target(ViewOpenTarget.ModalOverlay)
				.build());

		return MSG_OK;
	}

	private static DocumentFilter extractCockpitFilter(final I_C_OrderLine orderLine)
	{
		final DocumentFilter.DocumentFilterBuilder filterBuilder = DocumentFilter.builder()
				.setFilterId("OrderLineContext");

		final ProductId productId = ProductId.ofRepoId(orderLine.getM_Product_ID());
		filterBuilder.addParameterEquals(I_QtyDemand_QtySupply_V.COLUMNNAME_M_Product_ID, productId);

		final WarehouseId warehouseId = WarehouseId.ofRepoId(orderLine.getM_Warehouse_ID());
		filterBuilder.addParameterEquals(I_QtyDemand_QtySupply_V.COLUMNNAME_M_Warehouse_ID, warehouseId);

		return filterBuilder.build();
	}
}
