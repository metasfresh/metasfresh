package de.metas.inoutcandidate.modelvalidator;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.IInOutBL;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateRepository;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Interceptor(I_C_Order.class)
@Component
public class C_Order_ShipmentSchedule
{
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private final IShipmentScheduleInvalidateRepository scheduleInvalidateRepository = Services.get(IShipmentScheduleInvalidateRepository.class);
	private final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
	private final IInOutBL inOutBL = Services.get(IInOutBL.class);
	private final @NonNull AdMessageKey ERR_NoReactivationIfNotVoidedNotReversedShipments= AdMessageKey.of("ERR_NoReactivationIfNotVoidedNotReversedShipments");



	@DocValidate(timings = ModelValidator.TIMING_AFTER_REACTIVATE)
	public void closeExistingScheds(@NonNull final I_C_Order orderRecord)
	{
		final ImmutableList<TableRecordReference> orderLineRecordRefs = orderDAO
				.retrieveAllOrderLineIds(OrderId.ofRepoId(orderRecord.getC_Order_ID()))
				.stream()
				.map(OrderAndLineId::getOrderLineId)
				.map(orderLineId -> TableRecordReference.of(I_C_OrderLine.Table_Name, orderLineId))
				.collect(ImmutableList.toImmutableList());

		shipmentScheduleBL.closeShipmentSchedulesFor(orderLineRecordRefs);
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void openExistingScheds(@NonNull final I_C_Order orderRecord)
	{
		final ImmutableList<TableRecordReference> orderLineRecordRefs = orderDAO
				.retrieveAllOrderLineIds(OrderId.ofRepoId(orderRecord.getC_Order_ID()))
				.stream()
				.map(OrderAndLineId::getOrderLineId)
				.map(orderLineId -> TableRecordReference.of(I_C_OrderLine.Table_Name, orderLineId))
				.collect(ImmutableList.toImmutableList());

		shipmentScheduleBL.openShipmentSchedulesFor(orderLineRecordRefs);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_NEW }, ifColumnsChanged = I_C_Order.COLUMNNAME_POReference)
	public void propagatePOReferenceChangeToShipmentSchedule(@NonNull final I_C_Order order)
	{
		//retrieve and invalidate all related shipment schedules
		final Set<ShipmentScheduleId> shipmentScheduleIds = shipmentSchedulePA.retrieveScheduleIdsByOrderId(OrderId.ofRepoId(order.getC_Order_ID()));

		//invalidate all related shipment schedules
		scheduleInvalidateRepository.invalidateShipmentSchedules(shipmentScheduleIds);
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_REACTIVATE })
	public void reactivateIfNoActiveShipment(final I_C_Order order)
	{
		final ImmutableSet<I_M_InOut> activeShipments = inOutBL.getNotVoidedNotReversedForOrderId(OrderId.ofRepoId(order.getC_Order_ID()));

		if (!activeShipments.isEmpty())
		{
			throw new AdempiereException(
					ERR_NoReactivationIfNotVoidedNotReversedShipments,
					activeShipments.stream().map(I_M_InOut::getDocumentNo).collect(Collectors.joining(", ")));
		}
	}
}
