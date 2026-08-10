package de.metas.deliveryplanning.interceptor;

import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.deliveryplanning.async.M_ReceiptSchedule_Create_M_Delivery_Planning;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.organization.ClientAndOrgId;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_M_ReceiptSchedule.class)
@Component
public class M_ReceiptSchedule
{
	private final DeliveryPlanningService deliveryPlanningService;

	public M_ReceiptSchedule(final DeliveryPlanningService deliveryPlanningService)
	{
		this.deliveryPlanningService = deliveryPlanningService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW })
	public void createDeliveryPlanning(@NonNull final I_M_ReceiptSchedule sched)
	{
		final boolean autoCreateEnabled = deliveryPlanningService.isAutoCreateEnabled(ClientAndOrgId.ofClientAndOrg(sched.getAD_Client_ID(), sched.getAD_Org_ID()));
		if (!autoCreateEnabled)
		{
			// nothing to do

			return;
		}
		M_ReceiptSchedule_Create_M_Delivery_Planning.scheduleOnTrxCommit(sched);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void deleteDeliveryPlannings(@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		deliveryPlanningService.deleteForReceiptSchedule(ReceiptScheduleId.ofRepoId(receiptSchedule.getM_ReceiptSchedule_ID()));
	}
}
