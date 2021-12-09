package de.metas.ui.web.pporder.util;

import java.util.List;

import com.google.common.collect.ImmutableSet;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.adempiere.warehouse.groups.WarehouseGroupAssignmentType;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.model.I_PP_Order;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.model.I_M_Source_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.sourcehu.SourceHUsService;
import de.metas.handlingunits.sourcehu.SourceHUsService.MatchingSourceHusQuery;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.pporder.PPOrderLineRow;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class WEBUI_PP_Order_ProcessHelper
{

	public ProcessPreconditionsResolution checkIssueSourceDefaultPreconditionsApplicable(final PPOrderLineRow ppOrderLineRow)
	{
		if (!ppOrderLineRow.isIssue())
		{
			final String internalReason = StringUtils.formatMessage("The selected row is not an issuerow; row={}", ppOrderLineRow);
			return ProcessPreconditionsResolution.rejectWithInternalReason(internalReason);
		}

		final List<I_M_Source_HU> sourceHus = retrieveActiveSourceHus(ppOrderLineRow);
		if (sourceHus.isEmpty())
		{
			final String internalReason = StringUtils.formatMessage("There are no sourceHU records for the selected row; row={}", ppOrderLineRow);
			return ProcessPreconditionsResolution.rejectWithInternalReason(internalReason);
		}
		return ProcessPreconditionsResolution.accept();
	}

	public static List<I_M_Source_HU> retrieveActiveSourceHus(@NonNull final PPOrderLineRow row)
	{
		final I_PP_Order ppOrder = Services.get(IPPOrderDAO.class).getById(row.getOrderId());

		final MatchingSourceHusQuery query = MatchingSourceHusQuery.builder()
				.productId(row.getProductId())
				.warehouseIds(getIssueFromWarehouseIds(ppOrder))
				.build();
		return SourceHUsService.get().retrieveMatchingSourceHuMarkers(query)
				.stream()
				.filter(huSource -> X_M_HU.HUSTATUS_Active.equals(huSource.getM_HU().getHUStatus()))
				.collect(ImmutableList.toImmutableList());
	}

	private ImmutableSet<WarehouseId> getIssueFromWarehouseIds(final I_PP_Order ppOrder)
	{
		final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);

		final WarehouseId warehouseId = WarehouseId.ofRepoId(ppOrder.getM_Warehouse_ID());
		return warehouseDAO.getWarehouseIdsOfSameGroup(warehouseId, WarehouseGroupAssignmentType.MANUFACTURING);
	}

}
