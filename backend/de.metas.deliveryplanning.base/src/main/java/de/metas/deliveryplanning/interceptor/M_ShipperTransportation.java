package de.metas.deliveryplanning.interceptor;

import de.metas.bpartner.service.IBPartnerStatisticsUpdater;
import de.metas.deliveryplanning.DeliveryInstructionUserNotificationsProducer;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.event.IEventBusFactory;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_M_ShipperTransportation.class)
@Component
public class M_ShipperTransportation
{
	private final DeliveryPlanningService deliveryPlanningService;
	private final IEventBusFactory eventBusFactory;

	private final IBPartnerStatisticsUpdater bpartnerStatisticsUpdater = Services.get(IBPartnerStatisticsUpdater.class);

	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	public M_ShipperTransportation(
			@NonNull final DeliveryPlanningService deliveryPlanningService,
			@NonNull final IEventBusFactory eventBusFactory)
	{
		this.deliveryPlanningService = deliveryPlanningService;
		this.eventBusFactory = eventBusFactory;
	}

	@Init
	public void onInit()
	{
		// Setup event bus topics on which client notification listener shall subscribe
		eventBusFactory.addAvailableUserNotificationsTopic(DeliveryInstructionUserNotificationsProducer.EVENTBUS_TOPIC);
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
		final ShipperTransportationId deliveryInstructionId = ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID());
		trxManager.runAfterCommit(() -> deliveryPlanningService.invalidateInvoiceCandidatesFor(deliveryInstructionId));
	}

	/**
	 * Refuses to complete the instruction while any of its currently allocated plannings is closed, naming it -
	 * gh31608 Task C1, AC6. A transport order, or an instruction with no allocations, is a no-op:
	 * {@link DeliveryPlanningService#getCompleteRejectionReason(ShipperTransportationId)} comes back empty and
	 * nothing is read beyond the one allocation lookup.
	 */
	@DocValidate(timings = ModelValidator.TIMING_BEFORE_COMPLETE)
	public void rejectCompleteWithClosedAllocatedPlannings(@NonNull final I_M_ShipperTransportation shipperTransportation)
	{
		deliveryPlanningService.getCompleteRejectionReason(ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID()))
				.ifPresent(reason -> { throw new AdempiereException(reason); });
	}

	/**
	 * Cascades the instruction's {@code DocStatus} and {@code Processed} onto every one of its active allocations,
	 * on complete and on re-activate - gh31608 Task C1, AC6. Void is not one of these timings: it already
	 * deactivates the allocation via {@link #unlinkDeliveryPlannings(I_M_ShipperTransportation)}, which is where
	 * §3d of the aggregation design puts it, and not in {@code MMShipperTransportation.voidIt()} - voiding a
	 * COMPLETED instruction does not run that method's own line-deactivation branch, so the deactivation has to
	 * happen here regardless of which DocStatus the instruction was voided from.
	 */
	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE, ModelValidator.TIMING_AFTER_REACTIVATE })
	public void cascadeDocStatusToAllocations(@NonNull final I_M_ShipperTransportation shipperTransportation)
	{
		deliveryPlanningService.cascadeDocStatusToAllocations(ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID()));
	}
}
