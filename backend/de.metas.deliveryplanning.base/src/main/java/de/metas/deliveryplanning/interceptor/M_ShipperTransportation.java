package de.metas.deliveryplanning.interceptor;

import de.metas.bpartner.service.IBPartnerStatisticsUpdater;
import de.metas.deliveryplanning.DeliveryInstructionUserNotificationsProducer;
import de.metas.deliveryplanning.DeliveryInstructionsViewInvalidator;
import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.event.IEventBusFactory;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_M_ShipperTransportation.class)
@Component
public class M_ShipperTransportation
{
	private final DeliveryPlanningService deliveryPlanningService;
	private final DeliveryInstructionsViewInvalidator deliveryInstructionsViewInvalidator;

	private final IBPartnerStatisticsUpdater bpartnerStatisticsUpdater = Services.get(IBPartnerStatisticsUpdater.class);

	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	public M_ShipperTransportation(
			@NonNull final DeliveryPlanningService deliveryPlanningService,
			@NonNull final DeliveryInstructionsViewInvalidator deliveryInstructionsViewInvalidator)
	{
		this.deliveryPlanningService = deliveryPlanningService;
		this.deliveryInstructionsViewInvalidator = deliveryInstructionsViewInvalidator;
	}

	@Init
	public void onInit()
	{
		// Setup event bus topics on which client notification listener shall subscribe
		Services.get(IEventBusFactory.class).addAvailableUserNotificationsTopic(DeliveryInstructionUserNotificationsProducer.EVENTBUS_TOPIC);
	}

	@ModelChange(timings = {ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE})
	public void onAfterNewOrChange(@NonNull final I_M_ShipperTransportation shipperTransportation, @NonNull final ModelChangeType changeType)
	{
		deliveryInstructionsViewInvalidator.invalidateByShipperTransportation(shipperTransportation, changeType);
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_VOID)
	public void unlinkDeliveryPlannings(@NonNull final I_M_ShipperTransportation shipperTransportation)
	{
		deliveryPlanningService.unlinkDeliveryPlannings(ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID()));
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE, ModelValidator.TIMING_AFTER_VOID })
	public void updateBPartnerStatistics(@NonNull final I_M_ShipperTransportation shipperTransportation)
	{
		bpartnerStatisticsUpdater.updateBPartnerStatistics(IBPartnerStatisticsUpdater.BPartnerStatisticsUpdateRequest.builder()
				.bpartnerId(shipperTransportation.getShipper_BPartner_ID())
				.build());

		updateDeliveryPlanning(shipperTransportation);
	}

	private void updateDeliveryPlanning(@NonNull final I_M_ShipperTransportation shipperTransportation)
	{
		final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoIdOrNull(shipperTransportation.getM_Delivery_Planning_ID());
		if (deliveryPlanningId != null)
		{
			trxManager.runAfterCommit(() ->
					deliveryPlanningService.updateICFromDeliveryPlanningId(deliveryPlanningId));
		}
	}


}
