package de.metas.ui.web.pporder.process;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.ClearanceStatus;
import de.metas.handlingunits.ClearanceStatusInfo;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyBL;
import de.metas.organization.InstantAndOrgId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.IViewRow;
import de.metas.util.Services;
import lombok.NonNull;
import org.eevolution.api.ManufacturingOrderQuery;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.PPOrderPlanningStatus;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Similar to de.metas.ui.web.handlingunits.process.WEBUI_M_HU_Clearance
 */
public class WEBUI_PP_Order_Clearance extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	private final IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IHUPPOrderQtyBL huPPOrderQtyBL = Services.get(IHUPPOrderQtyBL.class);

	@Param(parameterName = I_M_HU.COLUMNNAME_ClearanceStatus, mandatory = true)
	private ClearanceStatus clearanceStatus;

	@Param(parameterName = I_M_HU.COLUMNNAME_ClearanceNote)
	private String clearanceNote;

	@Param(parameterName = I_M_HU.COLUMNNAME_ClearanceDate)
	private Instant clearanceDate;

	private IHUContext huContext;
	private final HashMap<ProductId, Optional<ClearanceStatus>> initialClearanceStatusCache = new HashMap<>();

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!hasEligibleSelectedPPOrders())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No eligible orders selected");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		huContext = handlingUnitsBL.createMutableHUContext();

		//
		// Process manufacturing orders
		final Set<PPOrderId> ppOrderIds = getEligibleSelectedPPOrderIds();
		huPPOrderBL.processPlanning(ppOrderIds, PPOrderPlanningStatus.COMPLETE);

		//
		// Change clearing status of all received HUs that have the initial clearing status
		final ClearanceStatusInfo clearanceStatusInfoToSet = getClearanceStatusInfoToSet();
		final Set<HuId> receivedHUIds = huPPOrderQtyBL.getFinishedGoodsReceivedHUIds(ppOrderIds);
		final List<I_M_HU> receivedHUs = handlingUnitsBL.getByIds(receivedHUIds);
		handlingUnitsBL.setClearanceStatusRecursively(receivedHUs, clearanceStatusInfoToSet, this::isInitialClearanceStatus);

		return MSG_OK;
	}

	private boolean hasEligibleSelectedPPOrders()
	{
		return getEligibleSelectedPPOrdersQuery()
				.map(huPPOrderBL::anyMatch)
				.orElse(false);
	}

	private Set<PPOrderId> getEligibleSelectedPPOrderIds()
	{
		return getEligibleSelectedPPOrdersQuery()
				.map(huPPOrderBL::getManufacturingOrderIds)
				.orElse(ImmutableSet.of());
	}

	private Optional<ManufacturingOrderQuery> getEligibleSelectedPPOrdersQuery()
	{
		final ImmutableSet<PPOrderId> selectedOrderIds = streamSelectedRows().map(WEBUI_PP_Order_Clearance::extractPPOrderId).collect(ImmutableSet.toImmutableSet());
		if (selectedOrderIds.isEmpty())
		{
			return Optional.empty();
		}

		return Optional.of(
				ManufacturingOrderQuery.builder()
						.onlyIds(selectedOrderIds)
						.onlyCompleted(true)
						.onlyPlanningStatus(PPOrderPlanningStatus.REVIEW)
						.build());
	}

	private static PPOrderId extractPPOrderId(final IViewRow row) {return row.getId().toId(PPOrderId::ofRepoId);}

	private ClearanceStatusInfo getClearanceStatusInfoToSet()
	{
		return ClearanceStatusInfo.builder()
				.clearanceStatus(clearanceStatus)
				.clearanceNote(clearanceNote)
				.clearanceDate(InstantAndOrgId.ofInstant(clearanceDate, getOrgId()))
				.build();
	}

	private boolean isInitialClearanceStatus(final I_M_HU hu)
	{
		final ProductId storageProductId = huContext.getHUStorageFactory().getStorage(hu).getSingleProductIdOrNull();
		if (storageProductId == null)
		{
			// cannot determine a single product in order to fetch the initial clearance status
			// => consider not initial clearance status
			return false;
		}

		// Don't update the clearance status of HUs which does not have the initial clearance status
		final ClearanceStatus initialClearanceStatus = getInitialClearanceStatus(storageProductId).orElse(null);
		final ClearanceStatus huClearanceStatus = ClearanceStatus.ofNullableCode(hu.getClearanceStatus());
		return ClearanceStatus.equals(huClearanceStatus, initialClearanceStatus);
	}

	private Optional<ClearanceStatus> getInitialClearanceStatus(@NonNull final ProductId productId)
	{
		return initialClearanceStatusCache.computeIfAbsent(productId, productBL::getInitialClearanceStatus);
	}
}
