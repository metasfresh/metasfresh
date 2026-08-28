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

	private final IBPartnerStatisticsUpdater bpartnerStatisticsUpdater = Services.get(IBPartnerStatisticsUpdater.class);

	private final ITrxManager trxManager = Services.get(ITrxManager.class);

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
	 * COMPLETE only, deliberately not VOID: on void, invoice-candidate invalidation for the instruction's
	 * allocations is owned exclusively by {@link #unlinkDeliveryPlannings(I_M_ShipperTransportation)}, which
	 * captures the affected planning ids BEFORE deactivating the allocations. This method's
	 * {@code getAllocatedPlanningIds}-based re-derivation would, on VOID, run inside a
	 * {@code trxManager.runAfterCommit} closure AFTER {@code unlinkDeliveryPlannings} (same class, same
	 * timing) had already deactivated those very allocations in the same transaction - always finding an
	 * empty set and silently invalidating nothing. Keeping this handler COMPLETE-only removes that hazard by
	 * construction instead of leaving a redundant, always-empty call in place.
	 */
	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void invalidateInvoiceCandidatesAfterComplete(@NonNull final I_M_ShipperTransportation shipperTransportation)
	{
		final ShipperTransportationId deliveryInstructionId = ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID());
		trxManager.runAfterCommit(() -> deliveryPlanningService.invalidateInvoiceCandidatesFor(deliveryInstructionId));
	}

	/**
	 * Refuses to complete the instruction while any of its currently allocated plannings is closed, naming it -
	 * and refuses to complete a delivery instruction that has zero active allocations at all.
	 * <p>
	 * A transport order is a no-op either way:
	 * {@link DeliveryPlanningService#getCompleteRejectionReason(ShipperTransportationId)} comes back empty and
	 * nothing is read beyond the one allocation lookup plus the document-type check.
	 */
	@DocValidate(timings = ModelValidator.TIMING_BEFORE_COMPLETE)
	public void rejectCompleteWithClosedAllocatedPlannings(@NonNull final I_M_ShipperTransportation shipperTransportation)
	{
		deliveryPlanningService.getCompleteRejectionReason(ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID()))
				.ifPresent(reason -> {throw new AdempiereException(reason);});
	}

	/**
	 * The instruction's dates are read-only on the plannings while they are allocated - this is what keeps them
	 * in step: a planner's edit of any of these fields on the instruction reaches every currently allocated
	 * planning, one-way, instruction to planning.
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
