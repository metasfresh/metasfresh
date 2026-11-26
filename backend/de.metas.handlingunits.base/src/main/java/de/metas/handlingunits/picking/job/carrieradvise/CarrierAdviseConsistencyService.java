package de.metas.handlingunits.picking.job.carrieradvise;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.shipmentschedule.api.DeliveryOrderCarrierResolver;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.ShipmentSchedule;
import de.metas.shipper.gateway.commons.model.ShipperConfigRepository;
import de.metas.shipper.gateway.spi.model.ResolvedCarrier;
import de.metas.shipping.Shipper;
import de.metas.shipping.ShipperId;
import de.metas.shipping.ShipperRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

/**
 * Validates that the carrier-advise is consistent across all shipment schedules linked to each packed top-level
 * HU (parcel) in a picking job, at job completion.
 *
 * <p>SCHEDULE-SOURCED: the carrier (product + goods-type + services) and the manual flag are read from the
 * shipment schedules — resolved with the same {@link DeliveryOrderCarrierResolver} the delivery order uses — and
 * reduced via the central {@link ResolvedCarrier#distinctManualCarriers} helper, so this check and the
 * delivery-order carrier resolution apply one shared rule set. Per package/HU:
 * <ul>
 *   <li><b>&ge;2 distinct manual carriers</b> → ambiguous human override → reject;</li>
 *   <li><b>exactly one manual carrier</b> → manual wins, consistent (a co-packed non-manual is overridden);</li>
 *   <li><b>no manual, divergent non-manual carriers</b> → reject <b>only</b> when the shipper has selection rules
 *       OFF (rules ON ⇒ nShift resolves at ship + a re-advise harmonises ⇒ not a completion blocker).</li>
 * </ul>
 */
@Service
@RequiredArgsConstructor
public class CarrierAdviseConsistencyService
{
	private static final AdMessageKey MSG_ManualInconsistentOnHU =
			AdMessageKey.of("de.metas.picking.CarrierAdvise_ManualInconsistentOnHU");
	private static final AdMessageKey MSG_NonManualDivergentOnHU =
			AdMessageKey.of("de.metas.picking.CarrierAdvise_NonManualDivergentOnHU");

	@NonNull private final HUShipmentScheduleResolver huShipmentScheduleResolver;
	@NonNull private final ShipperRepository shipperRepository;
	@NonNull private final ShipperConfigRepository shipperConfigRepository;
	@NonNull private final DeliveryOrderCarrierResolver deliveryOrderCarrierResolver;

	@VisibleForTesting
	public static CarrierAdviseConsistencyService newInstanceForUnitTesting(
			@NonNull final HUShipmentScheduleResolver huShipmentScheduleResolver,
			@NonNull final ShipperRepository shipperRepository,
			@NonNull final ShipperConfigRepository shipperConfigRepository,
			@NonNull final DeliveryOrderCarrierResolver deliveryOrderCarrierResolver)
	{
		Adempiere.assertUnitTestMode();
		return new CarrierAdviseConsistencyService(huShipmentScheduleResolver, shipperRepository, shipperConfigRepository, deliveryOrderCarrierResolver);
	}

	/**
	 * Asserts carrier-advise consistency for one closed top-level HU (parcel). Blocks when there are
	 * &ge;2 distinct manual carriers, or when non-manual carriers diverge while the shipper has selection
	 * rules OFF, per the class contract.
	 */
	public void assertConsistentForClosedHU(@NonNull final I_M_HU topLevelHU)
	{
		// Early gate: with NO API-carrier-advise shipper configured on the instance, no schedule can be advise-enabled,
		// so nothing can ever be inconsistent — skip the (heavy) per-HU schedule resolution entirely. Cache-backed.
		if (!shipperRepository.isAnyApiCarrierAdvise())
		{
			return;
		}

		final ImmutableMap<ShipmentScheduleId, ShipmentSchedule> schedulesById =
				huShipmentScheduleResolver.resolveSchedulesByIdForHU(topLevelHU);
		if (schedulesById.isEmpty())
		{
			return;
		}

		// The advise-enabled gate stays schedule/shipper-based: the shipper is legitimately header-level.
		final ImmutableSet<ShipperId> allShipperIds = schedulesById.values().stream()
				.map(ShipmentSchedule::getShipperId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
		if (allShipperIds.isEmpty())
		{
			return;
		}
		final Map<ShipperId, Shipper> shippersById = shipperRepository.getByIds(allShipperIds);

		final ImmutableList<ShipmentSchedule> adviseEnabledSchedules = schedulesById.values().stream()
				.filter(schedule -> isAdviseEnabled(schedule, shippersById))
				.collect(ImmutableList.toImmutableList());
		if (adviseEnabledSchedules.isEmpty())
		{
			return;
		}

		final HuId huId = HuId.ofRepoId(topLevelHU.getM_HU_ID());

		// Carrier + manual flag come from the shipment SCHEDULES, resolved with the same SCHEDULE-SOURCED logic used
		// for the delivery order, then reduced via the central ResolvedCarrier helper (shared with ShipperGatewayFacade).
		final ImmutableList<ResolvedCarrier> resolvedCarriers =
				ImmutableList.copyOf(deliveryOrderCarrierResolver.resolveBySchedules(adviseEnabledSchedules).values());

		final ImmutableSet<ResolvedCarrier> distinctManualCarriers = ResolvedCarrier.distinctManualCarriers(resolvedCarriers);

		// ≥2 distinct manual carriers on one HU → ambiguous human override → reject.
		// (AdempiereException(AdMessageKey, ...) is already a user-validation error.)
		if (distinctManualCarriers.size() > 1)
		{
			throw new AdempiereException(MSG_ManualInconsistentOnHU, huId.getRepoId());
		}

		// exactly one manual → manual wins (a co-packed non-manual is overridden), consistent → done.
		if (!distinctManualCarriers.isEmpty())
		{
			return;
		}

		// no manual: divergence check applies ONLY when the HU's shipper has selection rules OFF — then the explicit
		// carrier is authoritative and divergence is a real problem the picker must fix. With selection rules ON,
		// nShift resolves the carrier at ship and a re-advise harmonises it, so divergence is not a completion blocker.
		if (anyShipperHasSelectionRulesOn(adviseEnabledSchedules))
		{
			return;
		}

		// Counting distinct values including null routes a failed-advise carrier (null vs a set product on the same
		// HU) to a reject, which the picker resolves with re-advise. Stream.distinct() tolerates null.
		final long distinctProductIds = resolvedCarriers.stream()
				.map(ResolvedCarrier::getCarrierProductId)
				.distinct()
				.count();
		final long distinctGoodsTypeIds = resolvedCarriers.stream()
				.map(ResolvedCarrier::getCarrierGoodsTypeId)
				.distinct()
				.count();
		if (distinctProductIds > 1 || distinctGoodsTypeIds > 1)
		{
			throw new AdempiereException(MSG_NonManualDivergentOnHU, huId.getRepoId());
		}
	}

	private static boolean isAdviseEnabled(
			@NonNull final ShipmentSchedule schedule,
			@NonNull final Map<ShipperId, Shipper> shippersById)
	{
		final ShipperId shipperId = schedule.getShipperId();
		if (shipperId == null)
		{
			return false;
		}
		final Shipper shipper = shippersById.get(shipperId);
		return shipper != null && shipper.isApiCarrierAdvise();
	}

	/**
	 * True if at least one of the given advise-enabled schedules has a shipper with selection rules ON
	 * ({@code Carrier_Config.IsSelectionRules='Y'} / no config row → default 'Y'). With selection rules ON nShift
	 * auto-resolves the carrier, so the non-manual divergence check is skipped.
	 * <p>
	 * The {@code anyMatch} (rather than {@code allMatch}) semantics are deliberate: a HU carrying schedules of two
	 * shippers — one rules-OFF, one rules-ON — is intentionally allowed to complete (the multi-shipper-on-HU block
	 * "E3" was removed by explicit product decision, on the rationale that nShift's selection rules plus the packing
	 * re-advise resolve the carrier). Do not tighten to {@code allMatch} without revisiting it. Schedules carry a
	 * non-null shipper id here (they passed {@link #isAdviseEnabled}).
	 */
	private boolean anyShipperHasSelectionRulesOn(@NonNull final ImmutableList<ShipmentSchedule> adviseEnabledSchedules)
	{
		return adviseEnabledSchedules.stream()
				.map(ShipmentSchedule::getShipperId)
				.filter(Objects::nonNull)
				.anyMatch(shipperConfigRepository::isSelectionRules);
	}
}
