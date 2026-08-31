package de.metas.deliveryplanning.interceptor;

import de.metas.bpartner.service.IBPartnerStatisticsUpdater;
import de.metas.deliveryplanning.DeliveryInstructionUserNotificationsProducer;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.event.IEventBusFactory;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_M_ShipperTransportation.class)
@Component
@RequiredArgsConstructor
public class M_ShipperTransportation
{
	@NonNull private final DeliveryPlanningService deliveryPlanningService;
	@NonNull private final IEventBusFactory eventBusFactory;

	@NonNull private final IBPartnerStatisticsUpdater bpartnerStatisticsUpdater = Services.get(IBPartnerStatisticsUpdater.class);

	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);

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
	}

	/**
	 * COMPLETE only, and adding VOID here would be a silent no-op: this method re-derives the plannings from
	 * {@code getAllocatedPlanningIds} inside a run-after-commit closure, so on VOID it would run AFTER the sibling
	 * {@link #unlinkDeliveryPlannings(I_M_ShipperTransportation)} had already deactivated those allocations in the
	 * same transaction, always find an empty set, and invalidate nothing. Void's invalidation is therefore owned by
	 * that handler, which captures the ids BEFORE deactivating.
	 */
	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void invalidateInvoiceCandidatesAfterComplete(@NonNull final I_M_ShipperTransportation shipperTransportation)
	{
		final ShipperTransportationId deliveryInstructionId = ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID());
		trxManager.runAfterCommit(() -> deliveryPlanningService.invalidateInvoiceCandidatesFor(deliveryInstructionId));
	}

	/**
	 * Refuses to complete a delivery instruction while any of its allocated plannings is closed, or while it has no
	 * active allocation at all. A plain transport order is never rejected here.
	 */
	@DocValidate(timings = ModelValidator.TIMING_BEFORE_COMPLETE)
	public void rejectCompleteWithClosedAllocatedPlannings(@NonNull final I_M_ShipperTransportation shipperTransportation)
	{
		deliveryPlanningService.getCompleteRejectionReason(ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID()))
				.ifPresent(reason -> {throw new AdempiereException(reason);});
	}

	/**
	 * The instruction's dates are read-only on an allocated planning; this one-way sync is what keeps them in step.
	 */
	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = {
			I_M_ShipperTransportation.COLUMNNAME_ETD,
			I_M_ShipperTransportation.COLUMNNAME_ETA,
			I_M_ShipperTransportation.COLUMNNAME_ATD,
			I_M_ShipperTransportation.COLUMNNAME_ATA,
			I_M_ShipperTransportation.COLUMNNAME_LoadingTime,
			I_M_ShipperTransportation.COLUMNNAME_DeliveryTime })
	public void syncDatesToAllocatedPlannings(@NonNull final I_M_ShipperTransportation shipperTransportation)
	{
		deliveryPlanningService.syncDatesToAllocatedPlannings(shipperTransportation);
	}
}
