package de.metas.deliveryplanning.interceptor;

import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.deliveryplanning.async.M_ReceiptSchedule_Create_M_Delivery_Planning;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderLineId;
import de.metas.organization.ClientAndOrgId;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_M_ReceiptSchedule.class)
@Component
public class M_ReceiptSchedule
{
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

	private final DeliveryPlanningService deliveryPlanningService;

	public M_ReceiptSchedule(final DeliveryPlanningService deliveryPlanningService)
	{
		this.deliveryPlanningService = deliveryPlanningService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW })
	public void createDeliveryPlanning(@NonNull final I_M_ReceiptSchedule sched)
	{
		// Receipt schedules do not carry M_Shipper_ID directly.
		// Resolve the shipper via C_OrderLine_ID → C_OrderLine.M_Shipper_ID.
		final ShipperId shipperId = resolveShipperId(sched);
		final boolean autoCreateEnabled = deliveryPlanningService.isAutoCreateEnabled(
				ClientAndOrgId.ofClientAndOrg(sched.getAD_Client_ID(), sched.getAD_Org_ID()),
				shipperId);
		if (!autoCreateEnabled)
		{
			// nothing to do
			return;
		}
		M_ReceiptSchedule_Create_M_Delivery_Planning.scheduleOnTrxCommit(sched);
	}

	private ShipperId resolveShipperId(@NonNull final I_M_ReceiptSchedule sched)
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(sched.getC_OrderLine_ID());
		if (orderLineId == null)
		{
			return null;
		}
		final I_C_OrderLine orderLine = orderLineBL.getOrderLineById(orderLineId);
		return ShipperId.ofRepoIdOrNull(orderLine.getM_Shipper_ID());
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void deleteDeliveryPlannings(@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		deliveryPlanningService.deleteForReceiptSchedule(ReceiptScheduleId.ofRepoId(receiptSchedule.getM_ReceiptSchedule_ID()));
	}
}
