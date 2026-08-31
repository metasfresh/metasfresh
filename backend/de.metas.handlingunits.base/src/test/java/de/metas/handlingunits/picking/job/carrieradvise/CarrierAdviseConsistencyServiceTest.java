package de.metas.handlingunits.picking.job.carrieradvise;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.shipmentschedule.api.DeliveryOrderCarrierResolver;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.CarrierGoodsTypeId;
import de.metas.inoutcandidate.CarrierServiceId;
import de.metas.inoutcandidate.ShipmentSchedule;
import de.metas.shipper.gateway.commons.model.ShipperConfigRepository;
import de.metas.shipper.gateway.spi.model.ResolvedCarrier;
import de.metas.shipping.CarrierProductId;
import de.metas.shipping.Shipper;
import de.metas.shipping.ShipperId;
import de.metas.shipping.ShipperRepository;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/**
 * The consistency check is SCHEDULE-SOURCED: the carrier (product/goods-type/services) and the manual flag are
 * resolved per shipment schedule via {@link DeliveryOrderCarrierResolver#resolveBySchedules} (mocked here) and
 * reduced via the central {@link ResolvedCarrier#distinctManualCarriers}. Per package/HU:
 * ≥2 distinct manual → reject; exactly 1 manual → manual wins (OK); no manual + divergent product/goods-type →
 * reject ONLY when selection rules are OFF.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CarrierAdviseConsistencyServiceTest
{
	private static final HuId HU_ID_1 = HuId.ofRepoId(101);
	private static final ShipmentScheduleId SCHED_ID_1 = ShipmentScheduleId.ofRepoId(201);
	private static final ShipmentScheduleId SCHED_ID_2 = ShipmentScheduleId.ofRepoId(202);
	private static final ShipperId SHIPPER_1 = ShipperId.ofRepoId(301);
	private static final CarrierProductId CARRIER_PRODUCT_1 = CarrierProductId.ofRepoId(401);
	private static final CarrierProductId CARRIER_PRODUCT_2 = CarrierProductId.ofRepoId(402);
	private static final CarrierGoodsTypeId GOODS_TYPE_1 = CarrierGoodsTypeId.ofRepoId(501);
	private static final CarrierGoodsTypeId GOODS_TYPE_2 = CarrierGoodsTypeId.ofRepoId(502);
	private static final CarrierServiceId SERVICE_1 = CarrierServiceId.ofRepoId(601);
	private static final CarrierServiceId SERVICE_2 = CarrierServiceId.ofRepoId(602);

	private static final AdMessageKey MSG_ManualInconsistentOnHU = AdMessageKey.of("de.metas.picking.CarrierAdvise_ManualInconsistentOnHU");
	private static final AdMessageKey MSG_NonManualDivergentOnHU = AdMessageKey.of("de.metas.picking.CarrierAdvise_NonManualDivergentOnHU");

	@Mock private HUShipmentScheduleResolver resolver;
	@Mock private ShipperRepository shipperRepository;
	@Mock private ShipperConfigRepository shipperConfigRepository;
	@Mock private DeliveryOrderCarrierResolver deliveryOrderCarrierResolver;

	private CarrierAdviseConsistencyService service;

	/** Top-level HU under test; its {@link I_M_HU#getM_HU_ID()} is {@code HU_ID_1.getRepoId()}. */
	private I_M_HU topLevelHU;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		service = CarrierAdviseConsistencyService.newInstanceForUnitTesting(
				resolver, shipperRepository, shipperConfigRepository, deliveryOrderCarrierResolver);

		// Default: selection rules OFF (Carrier_Config.IsSelectionRules='N') → the non-manual divergence check is active.
		// The rules-ON case (skip divergence) overrides this per-test via stubSelectionRules(...).
		when(shipperConfigRepository.isSelectionRules(any())).thenReturn(false);

		// Default: the instance HAS an API-advise shipper, so the early gate passes and the guard runs. The
		// no-api-advise-instance gate (skip everything) is covered by its own test.
		when(shipperRepository.isAnyApiCarrierAdvise()).thenReturn(true);

		topLevelHU = mock(I_M_HU.class);
		when(topLevelHU.getM_HU_ID()).thenReturn(HU_ID_1.getRepoId());
	}

	@Test
	void noApiCarrierAdviseShipperOnInstance_skipsGuardEntirely()
	{
		// Even a would-be-rejecting HU (two distinct manual carriers) passes: with no API-advise shipper on the
		// instance the early gate returns before any per-HU schedule resolution.
		when(shipperRepository.isAnyApiCarrierAdvise()).thenReturn(false);

		assertThatCode(() -> service.assertConsistentForClosedHU(topLevelHU)).doesNotThrowAnyException();
		verifyNoInteractions(resolver);
	}

	// --------------------------------------------------
	// helpers
	// --------------------------------------------------

	/** Stub a shipper with isApiCarrierAdvise()==true for the given ShipperId. */
	private void stubShipper(final ShipperId shipperId)
	{
		final Shipper shipper = Shipper.builder()
				.id(shipperId)
				.name("Shipper-" + shipperId.getRepoId())
				.apiCarrierAdvise(true)
				.build();
		when(shipperRepository.getByIds(any())).thenAnswer(inv -> {
			final java.util.Set<ShipperId> ids = inv.getArgument(0);
			final Map<ShipperId, Shipper> result = new HashMap<>();
			if (ids.contains(shipperId))
			{
				result.put(shipperId, shipper);
			}
			return result;
		});
	}

	/** Stub the shipper's Carrier_Config.IsSelectionRules value (true = rules ON → divergence check skipped). */
	private void stubSelectionRules(final ShipperId shipperId, final boolean selectionRules)
	{
		when(shipperConfigRepository.isSelectionRules(shipperId)).thenReturn(selectionRules);
	}

	/** Mocks a shipment schedule — the advise-enabled gate (shipperId) + its id. Carrier values come from the resolver. */
	private ShipmentSchedule mockSchedule(final ShipmentScheduleId id, final ShipperId shipperId)
	{
		final ShipmentSchedule schedule = mock(ShipmentSchedule.class);
		when(schedule.getId()).thenReturn(id);
		when(schedule.getShipperId()).thenReturn(shipperId);
		return schedule;
	}

	private void stubResolver(final ShipmentSchedule... schedules)
	{
		final ImmutableMap.Builder<ShipmentScheduleId, ShipmentSchedule> map = ImmutableMap.builder();
		for (final ShipmentSchedule schedule : schedules)
		{
			map.put(schedule.getId(), schedule);
		}
		when(resolver.resolveSchedulesByIdForHU(topLevelHU)).thenReturn(map.build());
	}

	/** Mocks {@link DeliveryOrderCarrierResolver#resolveBySchedules} to return the given per-schedule carriers. */
	private void stubCarriers(final Map<ShipmentScheduleId, ResolvedCarrier> carriersByScheduleId)
	{
		when(deliveryOrderCarrierResolver.resolveBySchedules(any())).thenReturn(ImmutableMap.copyOf(carriersByScheduleId));
	}

	private static ResolvedCarrier carrier(
			final boolean manual,
			@Nullable final CarrierProductId carrierProductId,
			@Nullable final CarrierGoodsTypeId carrierGoodsTypeId,
			final ImmutableSet<CarrierServiceId> carrierServices)
	{
		return ResolvedCarrier.builder()
				.manual(manual)
				.carrierProductId(carrierProductId)
				.carrierGoodsTypeId(carrierGoodsTypeId)
				.carrierServices(carrierServices)
				.build();
	}

	private static void assertThrowsWithKey(
			final org.assertj.core.api.ThrowableAssert.ThrowingCallable code,
			final AdMessageKey expectedKey)
	{
		assertThatThrownBy(code)
				.isInstanceOfSatisfying(AdempiereException.class, adEx -> {
					assertThat(adEx.getErrorCode())
							.as("exception must carry expected AD_Message key as error code")
							.isEqualTo(expectedKey.toAD_Message());
					assertThat(adEx.isUserValidationError())
							.as("exception must be a user-validation error")
							.isTrue();
				});
	}

	// --------------------------------------------------
	// Single closed top-level HU (parcel) entry point: assertConsistentForClosedHU(topLevelHU). Per package/HU:
	//   E1 — ≥2 distinct manual carriers (product / goods-type / services each make them distinct) → reject;
	//   exactly 1 manual → manual wins (OK);
	//   E2 — no manual + divergent non-manual product/goods-type → reject ONLY when selection rules are OFF
	//        (rules ON ⇒ nShift resolves + re-advise harmonises ⇒ not a blocker);
	//   plus OK/consistent and no-advise-enabled cases.
	// --------------------------------------------------

	@Nested
	class ClosedHU
	{
		// ----------------------------------------------
		// Manual wins: 1 manual + 1 automatic on one HU → completes (relaxed from the former mixed-manual reject).
		// ----------------------------------------------

		@Test
		void manualPlusNonManual_manualWins_doesNotThrow()
		{
			final ShipmentSchedule manual = mockSchedule(SCHED_ID_1, SHIPPER_1);
			final ShipmentSchedule nonManual = mockSchedule(SCHED_ID_2, SHIPPER_1);
			stubResolver(manual, nonManual);
			stubShipper(SHIPPER_1);
			stubCarriers(ImmutableMap.of(
					SCHED_ID_1, carrier(true, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of(SERVICE_1)),
					SCHED_ID_2, carrier(false, CARRIER_PRODUCT_2, GOODS_TYPE_2, ImmutableSet.of())));

			assertThatCode(() -> service.assertConsistentForClosedHU(topLevelHU))
					.doesNotThrowAnyException();
		}

		// ----------------------------------------------
		// ≥2 distinct manual carriers on one HU → reject (E1). Product / goods-type / services each make them distinct.
		// ----------------------------------------------

		@Test
		void twoDistinctManual_differentProduct_throwsManualInconsistent()
		{
			final ShipmentSchedule s1 = mockSchedule(SCHED_ID_1, SHIPPER_1);
			final ShipmentSchedule s2 = mockSchedule(SCHED_ID_2, SHIPPER_1);
			stubResolver(s1, s2);
			stubShipper(SHIPPER_1);
			stubCarriers(ImmutableMap.of(
					SCHED_ID_1, carrier(true, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of(SERVICE_1)),
					SCHED_ID_2, carrier(true, CARRIER_PRODUCT_2, GOODS_TYPE_1, ImmutableSet.of(SERVICE_1))));

			assertThrowsWithKey(() -> service.assertConsistentForClosedHU(topLevelHU), MSG_ManualInconsistentOnHU);
		}

		@Test
		void twoDistinctManual_differentGoodsType_throwsManualInconsistent()
		{
			final ShipmentSchedule s1 = mockSchedule(SCHED_ID_1, SHIPPER_1);
			final ShipmentSchedule s2 = mockSchedule(SCHED_ID_2, SHIPPER_1);
			stubResolver(s1, s2);
			stubShipper(SHIPPER_1);
			stubCarriers(ImmutableMap.of(
					SCHED_ID_1, carrier(true, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of(SERVICE_1)),
					SCHED_ID_2, carrier(true, CARRIER_PRODUCT_1, GOODS_TYPE_2, ImmutableSet.of(SERVICE_1))));

			assertThrowsWithKey(() -> service.assertConsistentForClosedHU(topLevelHU), MSG_ManualInconsistentOnHU);
		}

		@Test
		void twoDistinctManual_differentServices_throwsManualInconsistent()
		{
			final ShipmentSchedule s1 = mockSchedule(SCHED_ID_1, SHIPPER_1);
			final ShipmentSchedule s2 = mockSchedule(SCHED_ID_2, SHIPPER_1);
			stubResolver(s1, s2);
			stubShipper(SHIPPER_1);
			stubCarriers(ImmutableMap.of(
					SCHED_ID_1, carrier(true, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of(SERVICE_1)),
					SCHED_ID_2, carrier(true, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of(SERVICE_2))));

			assertThrowsWithKey(() -> service.assertConsistentForClosedHU(topLevelHU), MSG_ManualInconsistentOnHU);
		}

		// ----------------------------------------------
		// No manual, divergent non-manual carrier → reject ONLY when selection rules are OFF.
		// ----------------------------------------------

		@Test
		void nonManual_divergentProduct_selectionRulesOff_throwsNonManualDivergent()
		{
			final ShipmentSchedule s1 = mockSchedule(SCHED_ID_1, SHIPPER_1);
			final ShipmentSchedule s2 = mockSchedule(SCHED_ID_2, SHIPPER_1);
			stubResolver(s1, s2);
			stubShipper(SHIPPER_1);
			stubSelectionRules(SHIPPER_1, false);
			stubCarriers(ImmutableMap.of(
					SCHED_ID_1, carrier(false, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of()),
					SCHED_ID_2, carrier(false, CARRIER_PRODUCT_2, GOODS_TYPE_1, ImmutableSet.of())));

			assertThrowsWithKey(() -> service.assertConsistentForClosedHU(topLevelHU), MSG_NonManualDivergentOnHU);
		}

		@Test
		void nonManual_divergentGoodsType_selectionRulesOff_throwsNonManualDivergent()
		{
			final ShipmentSchedule s1 = mockSchedule(SCHED_ID_1, SHIPPER_1);
			final ShipmentSchedule s2 = mockSchedule(SCHED_ID_2, SHIPPER_1);
			stubResolver(s1, s2);
			stubShipper(SHIPPER_1);
			stubSelectionRules(SHIPPER_1, false);
			// same product, divergent goods-type, rules OFF → the goods-type arm of the reject condition
			stubCarriers(ImmutableMap.of(
					SCHED_ID_1, carrier(false, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of()),
					SCHED_ID_2, carrier(false, CARRIER_PRODUCT_1, GOODS_TYPE_2, ImmutableSet.of())));

			assertThrowsWithKey(() -> service.assertConsistentForClosedHU(topLevelHU), MSG_NonManualDivergentOnHU);
		}

		@Test
		void nonManual_divergentProduct_selectionRulesOn_doesNotThrow()
		{
			final ShipmentSchedule s1 = mockSchedule(SCHED_ID_1, SHIPPER_1);
			final ShipmentSchedule s2 = mockSchedule(SCHED_ID_2, SHIPPER_1);
			stubResolver(s1, s2);
			stubShipper(SHIPPER_1);
			stubSelectionRules(SHIPPER_1, true);
			stubCarriers(ImmutableMap.of(
					SCHED_ID_1, carrier(false, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of()),
					SCHED_ID_2, carrier(false, CARRIER_PRODUCT_2, GOODS_TYPE_1, ImmutableSet.of())));

			assertThatCode(() -> service.assertConsistentForClosedHU(topLevelHU))
					.doesNotThrowAnyException();
		}

		@Test
		void nonManual_divergentGoodsType_selectionRulesOn_doesNotThrow()
		{
			final ShipmentSchedule s1 = mockSchedule(SCHED_ID_1, SHIPPER_1);
			final ShipmentSchedule s2 = mockSchedule(SCHED_ID_2, SHIPPER_1);
			stubResolver(s1, s2);
			stubShipper(SHIPPER_1);
			stubSelectionRules(SHIPPER_1, true);
			stubCarriers(ImmutableMap.of(
					SCHED_ID_1, carrier(false, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of()),
					SCHED_ID_2, carrier(false, CARRIER_PRODUCT_1, GOODS_TYPE_2, ImmutableSet.of())));

			assertThatCode(() -> service.assertConsistentForClosedHU(topLevelHU))
					.doesNotThrowAnyException();
		}

		@Test
		void nonManual_nullVsSetProduct_selectionRulesOff_throwsNonManualDivergent()
		{
			final ShipmentSchedule s1 = mockSchedule(SCHED_ID_1, SHIPPER_1);
			final ShipmentSchedule s2 = mockSchedule(SCHED_ID_2, SHIPPER_1);
			stubResolver(s1, s2);
			stubShipper(SHIPPER_1);
			// a failed-advise carrier (null product) vs a set product → distinct count (incl. null) > 1 → reject
			stubCarriers(ImmutableMap.of(
					SCHED_ID_1, carrier(false, null, null, ImmutableSet.of()),
					SCHED_ID_2, carrier(false, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of())));

			assertThrowsWithKey(() -> service.assertConsistentForClosedHU(topLevelHU), MSG_NonManualDivergentOnHU);
		}

		// ----------------------------------------------
		// OK cases
		// ----------------------------------------------

		@Test
		void ok_allNonManual_consistent_doesNotThrow()
		{
			final ShipmentSchedule s1 = mockSchedule(SCHED_ID_1, SHIPPER_1);
			final ShipmentSchedule s2 = mockSchedule(SCHED_ID_2, SHIPPER_1);
			stubResolver(s1, s2);
			stubShipper(SHIPPER_1);
			stubCarriers(ImmutableMap.of(
					SCHED_ID_1, carrier(false, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of()),
					SCHED_ID_2, carrier(false, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of())));

			assertThatCode(() -> service.assertConsistentForClosedHU(topLevelHU))
					.doesNotThrowAnyException();
		}

		@Test
		void ok_allManual_identical_doesNotThrow()
		{
			final ShipmentSchedule s1 = mockSchedule(SCHED_ID_1, SHIPPER_1);
			final ShipmentSchedule s2 = mockSchedule(SCHED_ID_2, SHIPPER_1);
			stubResolver(s1, s2);
			stubShipper(SHIPPER_1);
			stubCarriers(ImmutableMap.of(
					SCHED_ID_1, carrier(true, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of(SERVICE_1)),
					SCHED_ID_2, carrier(true, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of(SERVICE_1))));

			assertThatCode(() -> service.assertConsistentForClosedHU(topLevelHU))
					.doesNotThrowAnyException();
		}

		// ----------------------------------------------
		// OK — no advise-enabled schedule (shipper not API-carrier-advise) → early return, no resolve, no throw.
		// ----------------------------------------------

		@Test
		void noAdviseEnabledSchedule_doesNotThrow()
		{
			final ShipmentSchedule s1 = mockSchedule(SCHED_ID_1, SHIPPER_1);
			stubResolver(s1);
			// shipperRepository returns no api-carrier-advise shipper → isAdviseEnabled == false for all
			when(shipperRepository.getByIds(any())).thenReturn(ImmutableMap.of());

			assertThatCode(() -> service.assertConsistentForClosedHU(topLevelHU))
					.doesNotThrowAnyException();
		}
	}
}
