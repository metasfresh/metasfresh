package de.metas.picking.workflow;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.util.Services;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.CarrierGoodsTypeId;
import de.metas.inoutcandidate.CarrierServiceId;
import de.metas.inoutcandidate.ShipmentSchedule;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleId;
import de.metas.shipping.CarrierProductId;
import de.metas.shipping.Shipper;
import de.metas.shipping.ShipperRepository;
import de.metas.shipping.ShipperId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CarrierAdviseConsistencyServiceTest
{
	// IDs used across all tests
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

	@Mock private HUShipmentScheduleResolver resolver;
	@Mock private IHandlingUnitsBL handlingUnitsBL;
	@Mock private ShipperRepository shipperRepository;

	private CarrierAdviseConsistencyService service;

	/** The single top-level HU returned for HU_ID_1 in every test. */
	private I_M_HU topLevelHU;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		Services.registerService(IHandlingUnitsBL.class, handlingUnitsBL);

		service = CarrierAdviseConsistencyService.newInstanceForUnitTesting(resolver, shipperRepository);

		topLevelHU = mock(I_M_HU.class);
		when(topLevelHU.getM_HU_ID()).thenReturn(HU_ID_1.getRepoId());

		// by default: getTopLevelHUsByHuIds([HU_ID_1]) → {HU_ID_1: topLevelHU} (top-level parent = itself)
		when(handlingUnitsBL.getTopLevelHUsByHuIds(ImmutableSet.of(HU_ID_1)))
				.thenReturn(ImmutableMap.of(HU_ID_1, topLevelHU));
	}

	// --------------------------------------------------
	// helpers
	// --------------------------------------------------

	/**
	 * Builds a picking job that has picked {@code HU_ID_1} and whose lines are the given ones.
	 * The consistency service reads the carrier VALUES + manual flag from these lines (matched to a
	 * schedule by {@link ShipmentScheduleId}); the advise-enabled gate stays schedule/shipper-based.
	 */
	private PickingJob jobWithPickedHU(final PickingJobLine... lines)
	{
		final PickingJob job = mock(PickingJob.class);
		when(job.getAllPickedHuIds()).thenReturn(ImmutableSet.of(HU_ID_1));
		when(job.streamLines()).thenAnswer(inv -> ImmutableList.copyOf(lines).stream());
		return job;
	}

	/** Stub a shipper that has isApiCarrierAdvise()==true for the given ShipperId. */
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

	/**
	 * Mocks a shipment schedule. After T4 the schedule is only the advise-enabled GATE: the service reads
	 * its {@code shipperId} (to resolve the shipper's {@code isApiCarrierAdvise()}) and its id (to match the
	 * picking-job line). The carrier VALUES + manual flag are read from the line, NOT from the schedule.
	 */
	private ShipmentSchedule mockSchedule(
			final ShipmentScheduleId id,
			final ShipperId shipperId)
	{
		final ShipmentSchedule s = mock(ShipmentSchedule.class);
		when(s.getId()).thenReturn(id);
		when(s.getShipperId()).thenReturn(shipperId);
		return s;
	}

	/**
	 * Mocks a picking-job line carrying the carrier VALUES + manual flag for the given shipment schedule.
	 * This is now the source of truth the consistency service reads.
	 */
	private PickingJobLine mockLine(
			final ShipmentScheduleId scheduleId,
			final boolean isManual,
			@Nullable final CarrierProductId carrierProductId,
			@Nullable final CarrierGoodsTypeId carrierGoodsTypeId,
			final ImmutableSet<CarrierServiceId> carrierServices)
	{
		final ShipmentScheduleAndJobScheduleId scheduleAndJobScheduleId =
				ShipmentScheduleAndJobScheduleId.ofShipmentScheduleId(scheduleId);

		final PickingJobLine line = mock(PickingJobLine.class);
		when(line.getScheduleId()).thenReturn(scheduleAndJobScheduleId);
		when(line.isManual()).thenReturn(isManual);
		when(line.getCarrierProductId()).thenReturn(carrierProductId);
		when(line.getCarrierGoodsTypeId()).thenReturn(carrierGoodsTypeId);
		when(line.getCarrierServices()).thenReturn(carrierServices);
		return line;
	}

	private void stubResolver(final ShipmentSchedule... schedules)
	{
		final ImmutableMap.Builder<ShipmentScheduleId, ShipmentSchedule> map = ImmutableMap.builder();
		for (final ShipmentSchedule s : schedules)
		{
			map.put(s.getId(), s);
		}
		when(resolver.resolveSchedulesByIdForHU(topLevelHU)).thenReturn(map.build());
	}

	private static void assertThrowsWithKey(
			final org.assertj.core.api.ThrowableAssert.ThrowingCallable code,
			final AdMessageKey expectedKey)
	{
		assertThatThrownBy(code)
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> {
					final AdempiereException adEx = (AdempiereException)ex;
					assertThat(adEx.getErrorCode())
							.as("exception must carry expected AD_Message key as error code")
							.isEqualTo(expectedKey.toAD_Message());
					assertThat(adEx.isUserValidationError())
							.as("exception must be a user-validation error")
							.isTrue();
				});
	}

	// --------------------------------------------------
	// (T4 RED) line is the source of truth: schedules AGREE on product, lines DIVERGE → E2
	//
	// This is the central proof that the consistency check now reads the LINE, not the schedule.
	// The two non-manual schedules carry no carrier values at all (they are only the advise-enabled
	// gate); the divergence lives exclusively on the lines. Schedule-reading code sees no divergence
	// and would NOT throw; line-reading code throws NonManualDivergentOnHU.
	// --------------------------------------------------

	@Test
	void e2_linesDivergeOnProduct_schedulesAgree_throwsNonManualDivergent()
	{
		final ShipmentSchedule s1 = mockSchedule(SCHED_ID_1, SHIPPER_1);
		final ShipmentSchedule s2 = mockSchedule(SCHED_ID_2, SHIPPER_1);
		stubResolver(s1, s2);
		stubShipper(SHIPPER_1);

		// lines diverge on carrier product
		final PickingJobLine line1 = mockLine(SCHED_ID_1, false, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of());
		final PickingJobLine line2 = mockLine(SCHED_ID_2, false, CARRIER_PRODUCT_2, GOODS_TYPE_1, ImmutableSet.of());

		assertThrowsWithKey(
				() -> service.assertConsistentForJob(jobWithPickedHU(line1, line2)),
				AdMessageKey.of("de.metas.picking.CarrierAdvise_NonManualDivergentOnHU"));
	}

	// --------------------------------------------------
	// (E3 dropped) The "multiple advise-enabled shippers on one HU" check (MSG_MultipleShippersOnHU)
	// was removed in T4: the shipper is legitimately header-level and multiple advise-enabled shippers
	// on a single picked HU is not a real case to guard against. There is intentionally NO replacement
	// assertion for it.
	// --------------------------------------------------

	// --------------------------------------------------
	// (E1) manual + non-manual mix
	// --------------------------------------------------

	@Test
	void e1_mixedManualNonManual_throwsManualInconsistent()
	{
		final ShipmentSchedule manual = mockSchedule(SCHED_ID_1, SHIPPER_1);
		final ShipmentSchedule nonManual = mockSchedule(SCHED_ID_2, SHIPPER_1);
		stubResolver(manual, nonManual);
		stubShipper(SHIPPER_1);

		final PickingJobLine lineManual = mockLine(SCHED_ID_1, true, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of(SERVICE_1));
		final PickingJobLine lineNonManual = mockLine(SCHED_ID_2, false, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of());

		assertThrowsWithKey(
				() -> service.assertConsistentForJob(jobWithPickedHU(lineManual, lineNonManual)),
				AdMessageKey.of("de.metas.picking.CarrierAdvise_ManualInconsistentOnHU"));
	}

	// --------------------------------------------------
	// (E1) all manual — divergent CarrierProductId
	// --------------------------------------------------

	@Test
	void e1_allManual_differentProduct_throwsManualInconsistent()
	{
		final ShipmentSchedule s1 = mockSchedule(SCHED_ID_1, SHIPPER_1);
		final ShipmentSchedule s2 = mockSchedule(SCHED_ID_2, SHIPPER_1);
		stubResolver(s1, s2);
		stubShipper(SHIPPER_1);

		final PickingJobLine line1 = mockLine(SCHED_ID_1, true, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of(SERVICE_1));
		final PickingJobLine line2 = mockLine(SCHED_ID_2, true, CARRIER_PRODUCT_2, GOODS_TYPE_1, ImmutableSet.of(SERVICE_1));

		assertThrowsWithKey(
				() -> service.assertConsistentForJob(jobWithPickedHU(line1, line2)),
				AdMessageKey.of("de.metas.picking.CarrierAdvise_ManualInconsistentOnHU"));
	}

	// --------------------------------------------------
	// (E1) all manual — divergent CarrierGoodsTypeId
	// --------------------------------------------------

	@Test
	void e1_allManual_differentGoodsType_throwsManualInconsistent()
	{
		final ShipmentSchedule s1 = mockSchedule(SCHED_ID_1, SHIPPER_1);
		final ShipmentSchedule s2 = mockSchedule(SCHED_ID_2, SHIPPER_1);
		stubResolver(s1, s2);
		stubShipper(SHIPPER_1);

		final PickingJobLine line1 = mockLine(SCHED_ID_1, true, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of(SERVICE_1));
		final PickingJobLine line2 = mockLine(SCHED_ID_2, true, CARRIER_PRODUCT_1, GOODS_TYPE_2, ImmutableSet.of(SERVICE_1));

		assertThrowsWithKey(
				() -> service.assertConsistentForJob(jobWithPickedHU(line1, line2)),
				AdMessageKey.of("de.metas.picking.CarrierAdvise_ManualInconsistentOnHU"));
	}

	// --------------------------------------------------
	// (E1) all manual — divergent CarrierServiceId set
	// --------------------------------------------------

	@Test
	void e1_allManual_differentServiceIds_throwsManualInconsistent()
	{
		final ShipmentSchedule s1 = mockSchedule(SCHED_ID_1, SHIPPER_1);
		final ShipmentSchedule s2 = mockSchedule(SCHED_ID_2, SHIPPER_1);
		stubResolver(s1, s2);
		stubShipper(SHIPPER_1);

		final PickingJobLine line1 = mockLine(SCHED_ID_1, true, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of(SERVICE_1));
		final PickingJobLine line2 = mockLine(SCHED_ID_2, true, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of(SERVICE_2));

		assertThrowsWithKey(
				() -> service.assertConsistentForJob(jobWithPickedHU(line1, line2)),
				AdMessageKey.of("de.metas.picking.CarrierAdvise_ManualInconsistentOnHU"));
	}

	// --------------------------------------------------
	// (E2) all non-manual — divergent CarrierProductId
	// --------------------------------------------------

	@Test
	void e2_nonManual_divergentProduct_throwsNonManualDivergent()
	{
		final ShipmentSchedule s1 = mockSchedule(SCHED_ID_1, SHIPPER_1);
		final ShipmentSchedule s2 = mockSchedule(SCHED_ID_2, SHIPPER_1);
		stubResolver(s1, s2);
		stubShipper(SHIPPER_1);

		final PickingJobLine line1 = mockLine(SCHED_ID_1, false, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of());
		final PickingJobLine line2 = mockLine(SCHED_ID_2, false, CARRIER_PRODUCT_2, GOODS_TYPE_1, ImmutableSet.of());

		assertThrowsWithKey(
				() -> service.assertConsistentForJob(jobWithPickedHU(line1, line2)),
				AdMessageKey.of("de.metas.picking.CarrierAdvise_NonManualDivergentOnHU"));
	}

	// --------------------------------------------------
	// (E2) all non-manual — failed-advise (null product) vs successfully-advised product
	// --------------------------------------------------

	@Test
	void e2_nonManual_nullVsSetProduct_throwsNonManualDivergent()
	{
		final ShipmentSchedule s1 = mockSchedule(SCHED_ID_1, SHIPPER_1);
		final ShipmentSchedule s2 = mockSchedule(SCHED_ID_2, SHIPPER_1);
		stubResolver(s1, s2);
		stubShipper(SHIPPER_1);

		// adviseFailed line: null carrier product; advised line: set product → distinct count (incl null) > 1 → E2
		final PickingJobLine adviseFailed = mockLine(SCHED_ID_1, false, null, null, ImmutableSet.of());
		final PickingJobLine advised = mockLine(SCHED_ID_2, false, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of());

		assertThrowsWithKey(
				() -> service.assertConsistentForJob(jobWithPickedHU(adviseFailed, advised)),
				AdMessageKey.of("de.metas.picking.CarrierAdvise_NonManualDivergentOnHU"));
	}

	// --------------------------------------------------
	// OK — all non-manual, consistent product+goodsType
	// --------------------------------------------------

	@Test
	void ok_allNonManual_consistent()
	{
		final ShipmentSchedule s1 = mockSchedule(SCHED_ID_1, SHIPPER_1);
		final ShipmentSchedule s2 = mockSchedule(SCHED_ID_2, SHIPPER_1);
		stubResolver(s1, s2);
		stubShipper(SHIPPER_1);

		final PickingJobLine line1 = mockLine(SCHED_ID_1, false, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of());
		final PickingJobLine line2 = mockLine(SCHED_ID_2, false, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of());

		assertThatCode(() -> service.assertConsistentForJob(jobWithPickedHU(line1, line2)))
				.doesNotThrowAnyException();
	}

	// --------------------------------------------------
	// OK — all manual, identical tuple
	// --------------------------------------------------

	@Test
	void ok_allManual_identical()
	{
		final ShipmentSchedule s1 = mockSchedule(SCHED_ID_1, SHIPPER_1);
		final ShipmentSchedule s2 = mockSchedule(SCHED_ID_2, SHIPPER_1);
		stubResolver(s1, s2);
		stubShipper(SHIPPER_1);

		final PickingJobLine line1 = mockLine(SCHED_ID_1, true, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of(SERVICE_1));
		final PickingJobLine line2 = mockLine(SCHED_ID_2, true, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of(SERVICE_1));

		assertThatCode(() -> service.assertConsistentForJob(jobWithPickedHU(line1, line2)))
				.doesNotThrowAnyException();
	}

	// --------------------------------------------------
	// OK — advise-enabled schedule with NO line in the job is safely skipped (filter(Objects::nonNull))
	//
	// Two advise-enabled non-manual schedules (same shipper) resolve for the HU, but only the FIRST has a
	// corresponding picking-job line; the second has none. The line-less schedule is not part of this job's
	// picked state, so it is skipped — leaving a single line, which is trivially consistent → no throw.
	// Characterization of the existing guard; no behavior change.
	// --------------------------------------------------

	@Test
	void scheduleWithNoLine_isSkipped_noThrow()
	{
		final ShipmentSchedule s1 = mockSchedule(SCHED_ID_1, SHIPPER_1);
		final ShipmentSchedule s2 = mockSchedule(SCHED_ID_2, SHIPPER_1);
		stubResolver(s1, s2);
		stubShipper(SHIPPER_1);

		// only SCHED_ID_1 has a line in this job; SCHED_ID_2 has none → it is skipped
		final PickingJobLine line1 = mockLine(SCHED_ID_1, false, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of());

		assertThatCode(() -> service.assertConsistentForJob(jobWithPickedHU(line1)))
				.doesNotThrowAnyException();
	}

	// --------------------------------------------------
	// OK — advise-enabled schedules exist but NONE has a line → adviseEnabledLines empty → early return
	//
	// Distinct from the skip-one case above: here NO advise-enabled schedule has a corresponding line, so the
	// set of advised lines is totally empty and the method returns before any inconsistency check.
	// Characterization of the existing guard; no behavior change.
	// --------------------------------------------------

	@Test
	void allAdviseEnabledSchedules_haveNoLine_noThrow()
	{
		final ShipmentSchedule s1 = mockSchedule(SCHED_ID_1, SHIPPER_1);
		final ShipmentSchedule s2 = mockSchedule(SCHED_ID_2, SHIPPER_1);
		stubResolver(s1, s2);
		stubShipper(SHIPPER_1);

		// the job has NO lines at all → no advise-enabled schedule resolves to a line
		assertThatCode(() -> service.assertConsistentForJob(jobWithPickedHU()))
				.doesNotThrowAnyException();
	}
}
