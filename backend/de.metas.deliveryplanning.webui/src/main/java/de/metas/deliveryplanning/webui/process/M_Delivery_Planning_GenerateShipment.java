package de.metas.deliveryplanning.webui.process;

import com.google.common.collect.ImmutableMap;
import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.deliveryplanning.DeliveryPlanningShipmentInfo;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer;
import de.metas.inout.ShipmentScheduleId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.SpringContextHolder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public class M_Delivery_Planning_GenerateShipment extends JavaProcess implements IProcessPrecondition
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final DeliveryPlanningService deliveryPlanningService = SpringContextHolder.instance.getBean(DeliveryPlanningService.class);

	private static final String PARAM_DeliveryDate = "DeliveryDate";
	@Param(parameterName = PARAM_DeliveryDate, mandatory = true)
	private LocalDate p_DeliveryDate;

	private static final String PARAM_Qty = "Qty";
	@Param(parameterName = PARAM_Qty, mandatory = true)
	private BigDecimal p_QtyBD;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoId(context.getSingleSelectedRecordId());
		final Optional<DeliveryPlanningShipmentInfo> optionalShipmentInfo = deliveryPlanningService.getShipmentInfoIfOutgoingType(deliveryPlanningId);
		if (!optionalShipmentInfo.isPresent())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Not an outgoing delivery planning");
		}

		final DeliveryPlanningShipmentInfo shipmentInfo = optionalShipmentInfo.get();
		if (shipmentInfo.isShipped())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Already shipped");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		if (p_DeliveryDate == null)
		{
			throw new FillMandatoryException(PARAM_DeliveryDate);
		}
		if (p_QtyBD == null || p_QtyBD.signum() <= 0)
		{
			throw new FillMandatoryException(PARAM_Qty);
		}

		final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoId(getRecord_ID());
		final DeliveryPlanningShipmentInfo shipmentInfo = deliveryPlanningService.getShipmentInfo(deliveryPlanningId);
		if (shipmentInfo.isShipped())
		{
			throw new AdempiereException("Already shipped");
		}

		final ShipmentScheduleId shipmentScheduleId = shipmentInfo.getShipmentScheduleId();

		final ShipmentScheduleEnqueuer.Result result = new ShipmentScheduleEnqueuer()
				.setContext(getCtx(), getTrxName())
				.createWorkpackages(
						ShipmentScheduleEnqueuer.ShipmentScheduleWorkPackageParameters.builder()
								.adPInstanceId(getPinstanceId())
								.queryFilters(queryBL.createCompositeQueryFilter(I_M_ShipmentSchedule.class)
										.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleId))
								.quantityType(M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER)
								.completeShipments(true)
								.fixedShipmentDate(p_DeliveryDate)
								.qtysToDeliverOverride(ImmutableMap.<ShipmentScheduleId, BigDecimal>builder()
										.put(shipmentScheduleId, p_QtyBD)
										.build())
								//.forexContractId(isForexContract ? forexContractId : null) // TODO
								.deliveryPlanningId(deliveryPlanningId)
								.build());

		if (result.getWorkpackageEnqueuedCount() <= 0)
		{
			throw new AdempiereException("Could not ship")
					.setParameter("result", result);
		}

		return MSG_OK;
	}
}
