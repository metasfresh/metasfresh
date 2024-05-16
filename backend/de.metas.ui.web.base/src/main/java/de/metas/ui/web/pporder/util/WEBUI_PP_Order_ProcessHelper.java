package de.metas.ui.web.pporder.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Source_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.picking.OnOverDelivery;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.requests.PickRequest;
import de.metas.handlingunits.sourcehu.SourceHUsService;
import de.metas.handlingunits.sourcehu.SourceHUsService.MatchingSourceHusQuery;
import de.metas.logging.LogManager;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.pporder.PPOrderLineRow;
import de.metas.ui.web.pporder.PPOrderLinesView;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.adempiere.warehouse.groups.WarehouseGroupAssignmentType;
import org.compiere.SpringContextHolder;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.model.I_PP_Order;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

@UtilityClass
public class WEBUI_PP_Order_ProcessHelper
{
	private static final Logger logger = LogManager.getLogger(WEBUI_PP_Order_ProcessHelper.class);

	private final static PickingCandidateService pickingCandidateService = SpringContextHolder.instance.getBean(PickingCandidateService.class);

	//
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

	public static boolean isEligibleHU(final HURow row)
	{
		final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		final I_M_HU hu = handlingUnitsBL.getById(row.getHuId());
		// Multi product HUs are not allowed - see https://github.com/metasfresh/metasfresh/issues/6709
		return huContextFactory
				.createMutableHUContext()
				.getHUStorageFactory()
				.getStorage(hu)
				.isSingleProductStorage();
	}

	@Nullable
	public static HURow toHURowOrNull(final IViewRow viewRow)
	{
		if (viewRow instanceof HUEditorRow)
		{
			final HUEditorRow huRow = HUEditorRow.cast(viewRow);
			return HURow.builder()
					.huId(huRow.getHuId())
					.topLevelHU(huRow.isTopLevel())
					.huStatusActive(huRow.isHUStatusActive())
					.build();
		}
		else if (viewRow instanceof PPOrderLineRow)
		{
			final PPOrderLineRow ppOrderLineRow = PPOrderLineRow.cast(viewRow);

			// this process does not apply to source HUs
			if (ppOrderLineRow.isSourceHU())
			{
				return null;
			}

			if (!ppOrderLineRow.getType().isHUOrHUStorage())
			{
				return null;
			}
			return HURow.builder()
					.huId(ppOrderLineRow.getHuId())
					.topLevelHU(ppOrderLineRow.isTopLevelHU())
					.huStatusActive(ppOrderLineRow.isHUStatusActive())
					.qty(ppOrderLineRow.getQty())
					.build();
		}
		else
		{
			//noinspection ThrowableNotThrown
			new AdempiereException("Row type not supported: " + viewRow).throwIfDeveloperModeOrLogWarningElse(logger);
			return null;
		}
	}

	public static ImmutableList<HURow> getHURowsFromIncludedRows(final PPOrderLinesView view)
	{
		return view.streamByIds(DocumentIdsSelection.ALL)
				.filter(row -> row.getType().isMainProduct() || row.isReceipt())
				.flatMap(row -> row.getIncludedRows().stream())
				.map(row -> toHURowOrNull(row))
				.filter(Objects::nonNull)
				.filter(HURow::isTopLevelHU)
				.filter(HURow::isHuStatusActive)
				.filter(row -> isEligibleHU(row))
				.collect(ImmutableList.toImmutableList());
	}

	public static void pickAndProcessSingleHU(@NonNull final PickRequest pickRequest, @NonNull final ProcessPickingRequest processRequest)
	{
		pickHU(pickRequest);

		processHUs(processRequest);
	}

	public static void pickHU(@NonNull final PickRequest request)
	{
		pickingCandidateService.pickHU(request);

	}

	public static void processHUs(@NonNull final ProcessPickingRequest request)
	{
		pickingCandidateService.processForHUIds(request.getHuIds(),
												request.getShipmentScheduleId(),
												OnOverDelivery.ofTakeWholeHUFlag(request.isTakeWholeHU()),
												request.getPpOrderId());
	}

}
