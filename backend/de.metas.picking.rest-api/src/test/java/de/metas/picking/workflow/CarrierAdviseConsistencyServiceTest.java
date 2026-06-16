package de.metas.picking.workflow;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.util.Services;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.LUTUCUPair;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.CarrierAdviseStatus;
import de.metas.inoutcandidate.CarrierGoodsTypeId;
import de.metas.inoutcandidate.CarrierServiceId;
import de.metas.inoutcandidate.ShipmentSchedule;
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
	private static final ShipperId SHIPPER_2 = ShipperId.ofRepoId(302);
	private static final CarrierProductId CARRIER_PRODUCT_1 = CarrierProductId.ofRepoId(401);
	private static final CarrierProductId CARRIER_PRODUCT_2 = CarrierProductId.ofRepoId(402);
	private static final CarrierGoodsTypeId GOODS_TYPE_1 = CarrierGoodsTypeId.ofRepoId(501);
	private static final CarrierGoodsTypeId GOODS_TYPE_2 = CarrierGoodsTypeId.ofRepoId(502);
	private static final CarrierServiceId SERVICE_1 = CarrierServiceId.ofRepoId(601);
	private static final CarrierServiceId SERVICE_2 = CarrierServiceId.ofRepoId(602);

	@Mock private HUShipmentScheduleResolver resolver;
	@Mock private IHandlingUnitsBL handlingUnitsBL;
	@Mock private IHandlingUnitsDAO handlingUnitsDAO;
	@Mock private ShipperRepository shipperRepository;

	private CarrierAdviseConsistencyService service;

	/** The single top-level HU returned for HU_ID_1 in every test. */
	private I_M_HU topLevelHU;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		Services.registerService(IHandlingUnitsBL.class, handlingUnitsBL);
		Services.registerService(IHandlingUnitsDAO.class, handlingUnitsDAO);

		service = CarrierAdviseConsistencyService.newInstanceForUnitTesting(resolver, shipperRepository);

		topLevelHU = mock(I_M_HU.class);
		when(topLevelHU.getM_HU_ID()).thenReturn(HU_ID_1.getRepoId());

		// by default: getById(HU_ID_1) → topLevelHU, top-level parent = itself
		when(handlingUnitsDAO.getById(HU_ID_1)).thenReturn(topLevelHU);
		when(handlingUnitsBL.getTopLevelParentAsLUTUCUPair(topLevelHU))
				.thenReturn(LUTUCUPair.ofLU(topLevelHU));
	}

	// --------------------------------------------------
	// helpers
	// --------------------------------------------------

	private PickingJob jobWithPickedHU(final HuId huId)
	{
		final PickingJob job = mock(PickingJob.class);
		when(job.getAllPickedHuIds()).thenReturn(ImmutableSet.of(huId));
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

	/** Stub two advise-enabled shippers. */
	private void stubTwoShippers()
	{
		final Shipper s1 = Shipper.builder()
				.id(SHIPPER_1)
				.name("Shipper-" + SHIPPER_1.getRepoId())
				.apiCarrierAdvise(true)
				.build();
		final Shipper s2 = Shipper.builder()
				.id(SHIPPER_2)
				.name("Shipper-" + SHIPPER_2.getRepoId())
				.apiCarrierAdvise(true)
				.build();
		final Map<ShipperId, Shipper> twoShippers = new HashMap<>();
		twoShippers.put(SHIPPER_1, s1);
		twoShippers.put(SHIPPER_2, s2);
		when(shipperRepository.getByIds(any())).thenReturn(twoShippers);
	}

	private ShipmentSchedule mockSchedule(
			final ShipmentScheduleId id,
			final ShipperId shipperId,
			final CarrierAdviseStatus status,
			final CarrierProductId productId,
			final CarrierGoodsTypeId goodsTypeId,
			final ImmutableSet<CarrierServiceId> serviceIds)
	{
		final ShipmentSchedule s = mock(ShipmentSchedule.class);
		when(s.getId()).thenReturn(id);
		when(s.getShipperId()).thenReturn(shipperId);
		when(s.getCarrierAdvisingStatus()).thenReturn(status);
		when(s.getCarrierProductId()).thenReturn(productId);
		when(s.getCarrierGoodsTypeId()).thenReturn(goodsTypeId);
		when(s.getCarrierServicesIfLoaded()).thenReturn(serviceIds);
		return s;
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
	// (E3) more than one shipper
	// --------------------------------------------------

	@Test
	void e3_twoShipperIds_throwsMultipleShippers()
	{
		final ShipmentSchedule s1 = mockSchedule(SCHED_ID_1, SHIPPER_1,
				CarrierAdviseStatus.NotRequested, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of());
		final ShipmentSchedule s2 = mockSchedule(SCHED_ID_2, SHIPPER_2,
				CarrierAdviseStatus.NotRequested, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of());
		stubResolver(s1, s2);
		stubTwoShippers();

		assertThrowsWithKey(
				() -> service.assertConsistentForJob(jobWithPickedHU(HU_ID_1)),
				AdMessageKey.of("de.metas.picking.CarrierAdvise_MultipleShippersOnHU"));
	}

	// --------------------------------------------------
	// (E1) manual + non-manual mix
	// --------------------------------------------------

	@Test
	void e1_mixedManualNonManual_throwsManualInconsistent()
	{
		final ShipmentSchedule manual = mockSchedule(SCHED_ID_1, SHIPPER_1,
				CarrierAdviseStatus.Manual, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of(SERVICE_1));
		final ShipmentSchedule nonManual = mockSchedule(SCHED_ID_2, SHIPPER_1,
				CarrierAdviseStatus.NotRequested, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of());
		stubResolver(manual, nonManual);
		stubShipper(SHIPPER_1);

		assertThrowsWithKey(
				() -> service.assertConsistentForJob(jobWithPickedHU(HU_ID_1)),
				AdMessageKey.of("de.metas.picking.CarrierAdvise_ManualInconsistentOnHU"));
	}

	// --------------------------------------------------
	// (E1) all manual — divergent CarrierProductId
	// --------------------------------------------------

	@Test
	void e1_allManual_differentProduct_throwsManualInconsistent()
	{
		final ShipmentSchedule s1 = mockSchedule(SCHED_ID_1, SHIPPER_1,
				CarrierAdviseStatus.Manual, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of(SERVICE_1));
		final ShipmentSchedule s2 = mockSchedule(SCHED_ID_2, SHIPPER_1,
				CarrierAdviseStatus.Manual, CARRIER_PRODUCT_2, GOODS_TYPE_1, ImmutableSet.of(SERVICE_1));
		stubResolver(s1, s2);
		stubShipper(SHIPPER_1);

		assertThrowsWithKey(
				() -> service.assertConsistentForJob(jobWithPickedHU(HU_ID_1)),
				AdMessageKey.of("de.metas.picking.CarrierAdvise_ManualInconsistentOnHU"));
	}

	// --------------------------------------------------
	// (E1) all manual — divergent CarrierGoodsTypeId
	// --------------------------------------------------

	@Test
	void e1_allManual_differentGoodsType_throwsManualInconsistent()
	{
		final ShipmentSchedule s1 = mockSchedule(SCHED_ID_1, SHIPPER_1,
				CarrierAdviseStatus.Manual, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of(SERVICE_1));
		final ShipmentSchedule s2 = mockSchedule(SCHED_ID_2, SHIPPER_1,
				CarrierAdviseStatus.Manual, CARRIER_PRODUCT_1, GOODS_TYPE_2, ImmutableSet.of(SERVICE_1));
		stubResolver(s1, s2);
		stubShipper(SHIPPER_1);

		assertThrowsWithKey(
				() -> service.assertConsistentForJob(jobWithPickedHU(HU_ID_1)),
				AdMessageKey.of("de.metas.picking.CarrierAdvise_ManualInconsistentOnHU"));
	}

	// --------------------------------------------------
	// (E1) all manual — divergent CarrierServiceId set
	// --------------------------------------------------

	@Test
	void e1_allManual_differentServiceIds_throwsManualInconsistent()
	{
		final ShipmentSchedule s1 = mockSchedule(SCHED_ID_1, SHIPPER_1,
				CarrierAdviseStatus.Manual, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of(SERVICE_1));
		final ShipmentSchedule s2 = mockSchedule(SCHED_ID_2, SHIPPER_1,
				CarrierAdviseStatus.Manual, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of(SERVICE_2));
		stubResolver(s1, s2);
		stubShipper(SHIPPER_1);

		assertThrowsWithKey(
				() -> service.assertConsistentForJob(jobWithPickedHU(HU_ID_1)),
				AdMessageKey.of("de.metas.picking.CarrierAdvise_ManualInconsistentOnHU"));
	}

	// --------------------------------------------------
	// (E2) all non-manual — divergent CarrierProductId
	// --------------------------------------------------

	@Test
	void e2_nonManual_divergentProduct_throwsNonManualDivergent()
	{
		final ShipmentSchedule s1 = mockSchedule(SCHED_ID_1, SHIPPER_1,
				CarrierAdviseStatus.NotRequested, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of());
		final ShipmentSchedule s2 = mockSchedule(SCHED_ID_2, SHIPPER_1,
				CarrierAdviseStatus.NotRequested, CARRIER_PRODUCT_2, GOODS_TYPE_1, ImmutableSet.of());
		stubResolver(s1, s2);
		stubShipper(SHIPPER_1);

		assertThrowsWithKey(
				() -> service.assertConsistentForJob(jobWithPickedHU(HU_ID_1)),
				AdMessageKey.of("de.metas.picking.CarrierAdvise_NonManualDivergentOnHU"));
	}

	// --------------------------------------------------
	// (E2) all non-manual — failed-advise (null product) vs successfully-advised product
	// --------------------------------------------------

	@Test
	void e2_nonManual_nullVsSetProduct_throwsNonManualDivergent()
	{
		final ShipmentSchedule adviseFailed = mockSchedule(SCHED_ID_1, SHIPPER_1,
				CarrierAdviseStatus.Failed, null, null, ImmutableSet.of());
		final ShipmentSchedule advised = mockSchedule(SCHED_ID_2, SHIPPER_1,
				CarrierAdviseStatus.Completed, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of());
		stubResolver(adviseFailed, advised);
		stubShipper(SHIPPER_1);

		assertThrowsWithKey(
				() -> service.assertConsistentForJob(jobWithPickedHU(HU_ID_1)),
				AdMessageKey.of("de.metas.picking.CarrierAdvise_NonManualDivergentOnHU"));
	}

	// --------------------------------------------------
	// OK — all non-manual, consistent product+goodsType
	// --------------------------------------------------

	@Test
	void ok_allNonManual_consistent()
	{
		final ShipmentSchedule s1 = mockSchedule(SCHED_ID_1, SHIPPER_1,
				CarrierAdviseStatus.NotRequested, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of());
		final ShipmentSchedule s2 = mockSchedule(SCHED_ID_2, SHIPPER_1,
				CarrierAdviseStatus.Completed, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of());
		stubResolver(s1, s2);
		stubShipper(SHIPPER_1);

		assertThatCode(() -> service.assertConsistentForJob(jobWithPickedHU(HU_ID_1)))
				.doesNotThrowAnyException();
	}

	// --------------------------------------------------
	// OK — all manual, identical tuple
	// --------------------------------------------------

	@Test
	void ok_allManual_identical()
	{
		final ShipmentSchedule s1 = mockSchedule(SCHED_ID_1, SHIPPER_1,
				CarrierAdviseStatus.Manual, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of(SERVICE_1));
		final ShipmentSchedule s2 = mockSchedule(SCHED_ID_2, SHIPPER_1,
				CarrierAdviseStatus.Manual, CARRIER_PRODUCT_1, GOODS_TYPE_1, ImmutableSet.of(SERVICE_1));
		stubResolver(s1, s2);
		stubShipper(SHIPPER_1);

		assertThatCode(() -> service.assertConsistentForJob(jobWithPickedHU(HU_ID_1)))
				.doesNotThrowAnyException();
	}
}
