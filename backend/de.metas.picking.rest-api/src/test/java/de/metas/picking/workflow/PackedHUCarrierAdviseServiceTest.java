package de.metas.picking.workflow;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequestItem;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequestParcel;
import de.metas.currency.CurrencyRepository;
import de.metas.customstariff.CustomsTariffRepository;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.impl.PlainMsgBL;
import de.metas.money.MoneyService;
import de.metas.util.Services;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.job.carrieradvise.HUShipmentScheduleResolver;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.TUPickingTarget;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.shipping.PackedHUShippingInfoService;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.CarrierAdviseStatus;
import de.metas.inoutcandidate.CarrierGoodsTypeId;
import de.metas.inoutcandidate.CarrierServiceId;
import de.metas.inoutcandidate.ShipmentSchedule;
import de.metas.inoutcandidate.ShipmentScheduleService;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleId;
import de.metas.product.ProductId;
import de.metas.organization.OrgId;
import de.metas.product.PackageDimensions;
import de.metas.product.ProductRepository;
import de.metas.shipper.gateway.commons.model.CarrierProduct;
import de.metas.shipper.gateway.commons.model.CarrierProductRepository;
import de.metas.shipping.CarrierProductId;
import de.metas.shipping.ShipperId;
import de.metas.shipping.ShipperRepository;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestWatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Drives {@link PackedHUCarrierAdviseService#buildRequestParcel} for a top-level HU holding TWO
 * products and asserts the resulting parcel carries one item per product, each with the
 * parity-correct per-item nominal gross weight (NShiftDraftDeliveryOrderCreator#computeNominalGrossWeightInKg).
 * <p>
 * Also drives {@link PackedHUCarrierAdviseService#advise(PickingJob, de.metas.handlingunits.picking.job.model.PickingJobLineId)}
 * to assert the advised carrier product + read-only flag are persisted onto the picking job header and its
 * non-Manual lines (Manual lines untouched).
 */
@ExtendWith(AdempiereTestWatcher.class)
public class PackedHUCarrierAdviseServiceTest
{
	private LUTUProducerDestinationTestSupport data;
	private PackedHUShippingInfoService packedHUShippingInfoService;
	private HUShipmentScheduleResolver huShipmentScheduleResolver;
	private ShipmentScheduleService shipmentScheduleService;
	private PickingJobRepository pickingJobRepository;
	private ShipperRepository shipperRepository;
	private CarrierProductRepository carrierProductRepository;
	private PackedHUCarrierAdviseService service;
	private int idSeq = 300;

	private static final ShipmentScheduleId SCHED_TOMATO = ShipmentScheduleId.ofRepoId(201);
	private static final ShipmentScheduleId SCHED_SALAD = ShipmentScheduleId.ofRepoId(202);

	// net weight per stocking UOM (kg) — set on the products so computeGrossWeight falls back to net weight
	private static final BigDecimal TOMATO_WEIGHT_KG = new BigDecimal("2");
	private static final BigDecimal SALAD_WEIGHT_KG = new BigDecimal("0.5");

	@BeforeEach
	public void init()
	{
		data = new LUTUProducerDestinationTestSupport();
		Services.registerService(IMsgBL.class, new PlainMsgBL());

		packedHUShippingInfoService = PackedHUShippingInfoService.newInstanceForUnitTesting();
		huShipmentScheduleResolver = mock(HUShipmentScheduleResolver.class);
		shipmentScheduleService = mock(ShipmentScheduleService.class);
		pickingJobRepository = mock(PickingJobRepository.class);
		shipperRepository = mock(ShipperRepository.class);
		carrierProductRepository = mock(CarrierProductRepository.class);

		// spy: the advise(...) flow is exercised end-to-end except the static CarrierAdviseCommand call
		// (real DB + shipper-gateway work), which is stubbed at the #adviseSchedule seam.
		service = spy(new PackedHUCarrierAdviseService(
				packedHUShippingInfoService,
				huShipmentScheduleResolver,
				ProductRepository.newInstanceForUnitTesting(),
				carrierProductRepository,
				mock(CustomsTariffRepository.class),
				shipperRepository,
				shipmentScheduleService,
				pickingJobRepository,
				new MoneyService(new CurrencyRepository())));

		// give both products a net weight (kg/stocking-UOM); computeGrossWeight falls back to net weight
		data.helper.pTomato.setWeight(TOMATO_WEIGHT_KG);
		save(data.helper.pTomato);
		data.helper.pSalad.setWeight(SALAD_WEIGHT_KG);
		save(data.helper.pSalad);
	}

	@Test
	public void buildRequestParcel_multiProduct_oneItemPerProduct_withNominalWeights()
	{
		// A virtual HU has unlimited capacity (ofVirtualPI → maxHUsToCreate=1), so loading two
		// products into the same producer puts both into ONE top-level HU's storage.
		final I_M_HU hu = createTwoProductHU();

		// schedules: one per product, no order line (price/qty fields stay null) — focuses the test on
		// the multi-product item enumeration + per-item nominal weight.
		final ShipmentSchedule tomatoSched = mockSchedule(SCHED_TOMATO, data.helper.pTomatoProductId);
		final ShipmentSchedule saladSched = mockSchedule(SCHED_SALAD, data.helper.pSaladProductId);
		final ImmutableMap<ShipmentScheduleId, ShipmentSchedule> schedulesById = ImmutableMap.of(
				SCHED_TOMATO, tomatoSched,
				SCHED_SALAD, saladSched);

		final JsonDeliveryAdvisorRequestParcel parcel = service.buildRequestParcel(hu, schedulesById);

		assertThat(parcel.getItems()).hasSize(2);

		final JsonDeliveryAdvisorRequestItem tomato = findItem(parcel, data.helper.pTomato.getName());
		assertThat(tomato.getNumberOfItems()).isEqualTo(20);
		// 20 stocking-UOM × 2 kg = 40 kg
		assertThat(tomato.getTotalWeightInKg()).isEqualByComparingTo("40");

		final JsonDeliveryAdvisorRequestItem salad = findItem(parcel, data.helper.pSalad.getName());
		assertThat(salad.getNumberOfItems()).isEqualTo(3);
		// 3 stocking-UOM × 0.5 kg = 1.5 kg
		assertThat(salad.getTotalWeightInKg()).isEqualByComparingTo("1.5");

		// parcel-level fields come from PackedHUShippingInfoService#of(hu)
		assertThat(parcel.getTopLevelType()).isEqualTo("CU");
		assertThat(parcel.getPackageDimensions()).isNotNull();
	}

	/**
	 * One packed HU carries a Tomato schedule (non-Manual, re-advised) and a Salad schedule (Manual, skipped).
	 * After {@link PackedHUCarrierAdviseService#advise}:
	 * <ul>
	 *     <li>the non-Manual (Tomato) line gets the advised carrier product + goods-type + services and {@code carrierAdviseReadOnly=true};</li>
	 *     <li>the Manual (Salad) line is left untouched;</li>
	 *     <li>the header gets the single advised product + {@code carrierAdviseReadOnly=true} (anyManual);</li>
	 *     <li>the picking job is saved.</li>
	 * </ul>
	 */
	@Test
	public void advise_persistsAdvisedProductOnHeaderAndNonManualLine_manualLineUntouched()
	{
		final I_M_HU hu = createTwoProductHU();
		final HuId huId = HuId.ofRepoId(hu.getM_HU_ID());

		final CarrierProductId advisedProductId = CarrierProductId.ofRepoId(777);
		final CarrierGoodsTypeId advisedGoodsTypeId = CarrierGoodsTypeId.ofRepoId(888);
		final ImmutableSet<CarrierServiceId> advisedServices = ImmutableSet.of(
				CarrierServiceId.ofRepoId(901),
				CarrierServiceId.ofRepoId(902));

		// HU resolves to a non-Manual Tomato schedule (re-advised) + a Manual Salad schedule (skipped)
		final ShipmentSchedule tomatoSched = mockSchedule(SCHED_TOMATO, data.helper.pTomatoProductId);
		when(tomatoSched.getCarrierAdvisingStatus()).thenReturn(CarrierAdviseStatus.Completed);
		final ShipmentSchedule saladSched = mockSchedule(SCHED_SALAD, data.helper.pSaladProductId);
		when(saladSched.getCarrierAdvisingStatus()).thenReturn(CarrierAdviseStatus.Manual);
		final ImmutableMap<ShipmentScheduleId, ShipmentSchedule> schedulesById = ImmutableMap.of(
				SCHED_TOMATO, tomatoSched,
				SCHED_SALAD, saladSched);
		when(huShipmentScheduleResolver.resolveSchedulesByIdForHU(any())).thenReturn(schedulesById);

		// stub the static-command seam (no real DB / shipper-gateway call in a unit test): the mobile advise resolves
		// the carrier WITHOUT persisting to the schedule, returning it for the job to persist.
		doReturn(PackedHUCarrierAdviseService.AdvisedCarrier.builder()
				.carrierProductId(advisedProductId)
				.carrierGoodsTypeId(advisedGoodsTypeId)
				.carrierServices(advisedServices)
				.build())
				.when(service).adviseSchedule(eq(SCHED_TOMATO), any());

		// --- picking job: one non-Manual line (Tomato) + one Manual line (Salad) ---
		final PickingJobLine tomatoLine = mock(PickingJobLine.class);
		when(tomatoLine.getScheduleId()).thenReturn(ShipmentScheduleAndJobScheduleId.ofShipmentScheduleId(SCHED_TOMATO));
		final PickingJobLine tomatoLineChanged = mock(PickingJobLine.class);
		when(tomatoLine.withCarrierAdvise(any(), any(), any(), anyBooleanEq())).thenReturn(tomatoLineChanged);

		final PickingJobLine saladLine = mock(PickingJobLine.class);
		when(saladLine.getScheduleId()).thenReturn(ShipmentScheduleAndJobScheduleId.ofShipmentScheduleId(SCHED_SALAD));

		final TUPickingTarget tuTarget = existingTuTarget(huId);
		final PickingJob pickingJob = mock(PickingJob.class);
		// current pick target = the packed HU (the parcel being packed); advise scopes to it
		when(pickingJob.getLuPickingTargetEffective(null)).thenReturn(Optional.empty());
		when(pickingJob.getTuPickingTargetEffective(null)).thenReturn(Optional.of(tuTarget));

		// withChangedLines(mapper): apply the mapper to the two lines so we can assert per-line behaviour
		final PickingJob jobAfterLines = mock(PickingJob.class);
		final ArgumentCaptor<UnaryOperator<PickingJobLine>> mapperCaptor = lineMapperCaptor();
		when(pickingJob.withChangedLines(mapperCaptor.capture())).thenReturn(jobAfterLines);

		final PickingJob jobAfterProduct = mock(PickingJob.class);
		when(jobAfterLines.withCarrierProductId(advisedProductId)).thenReturn(jobAfterProduct);
		final PickingJob jobFinal = mock(PickingJob.class);
		when(jobAfterProduct.withCarrierAdviseReadOnly(true)).thenReturn(jobFinal);

		// --- act ---
		final PickingJob result = service.advise(pickingJob, null);

		// --- assert ---
		// the line mapper sets the advised product + goods-type + services + readOnly on the non-Manual line, leaves the Manual line as-is
		final UnaryOperator<PickingJobLine> mapper = mapperCaptor.getValue();
		assertThat(mapper.apply(tomatoLine)).isSameAs(tomatoLineChanged);
		verify(tomatoLine).withCarrierAdvise(advisedProductId, advisedGoodsTypeId, advisedServices, true);
		assertThat(mapper.apply(saladLine)).isSameAs(saladLine);
		verify(saladLine, never()).withCarrierAdvise(any(), any(), any(), anyBooleanEq());

		// header product + readOnly + save
		verify(jobAfterLines).withCarrierProductId(advisedProductId);
		verify(jobAfterProduct).withCarrierAdviseReadOnly(true);
		verify(pickingJobRepository).save(jobFinal);
		assertThat(result).isSameAs(jobFinal);
	}

	/**
	 * Every advise schedule of the picked HU is Manual: nothing is re-advised, but the carrier product is
	 * manually controlled, so the header must still be flagged read-only ({@code carrierAdviseReadOnly=true}).
	 * No line is touched (a Manual line's carrier product must never be overwritten).
	 */
	@Test
	public void advise_allManual_flagsHeaderReadOnly_noProduct_noLineTouched()
	{
		final I_M_HU hu = createTwoProductHU();
		final HuId huId = HuId.ofRepoId(hu.getM_HU_ID());

		final ShipmentSchedule tomatoSched = mockSchedule(SCHED_TOMATO, data.helper.pTomatoProductId);
		when(tomatoSched.getCarrierAdvisingStatus()).thenReturn(CarrierAdviseStatus.Manual);
		final ShipmentSchedule saladSched = mockSchedule(SCHED_SALAD, data.helper.pSaladProductId);
		when(saladSched.getCarrierAdvisingStatus()).thenReturn(CarrierAdviseStatus.Manual);
		when(huShipmentScheduleResolver.resolveSchedulesByIdForHU(any())).thenReturn(ImmutableMap.of(
				SCHED_TOMATO, tomatoSched,
				SCHED_SALAD, saladSched));

		final TUPickingTarget tuTarget = existingTuTarget(huId);
		final PickingJob pickingJob = mock(PickingJob.class);
		// current pick target = the packed HU (the parcel being packed); advise scopes to it
		when(pickingJob.getLuPickingTargetEffective(null)).thenReturn(Optional.empty());
		when(pickingJob.getTuPickingTargetEffective(null)).thenReturn(Optional.of(tuTarget));
		final PickingJob jobReadOnly = mock(PickingJob.class);
		when(pickingJob.withCarrierAdviseReadOnly(true)).thenReturn(jobReadOnly);

		final PickingJob result = service.advise(pickingJob, null);

		// no schedule advised, no batch re-read, no line change, no header product
		verify(service, never()).adviseSchedule(any(), any());
		verify(pickingJob, never()).withChangedLines(any());
		verify(pickingJob, never()).withCarrierProductId(any());
		// header flagged read-only + saved
		verify(pickingJob).withCarrierAdviseReadOnly(true);
		verify(pickingJobRepository).save(jobReadOnly);
		assertThat(result).isSameAs(jobReadOnly);
	}

	/**
	 * A single-product LOOSE CU (topLevelType CU, no carton = no packing item) is 1 parcel per unit, so
	 * the advise must be sent for 1 CU: item {@code numberOfItems=1} + single-unit weight, and the PARCEL
	 * envelope (gross weight + dimensions) = the product's single unit — NOT the packed qty-N HU aggregate.
	 */
	@Test
	public void buildRequestParcel_singleProductLooseCU_advisesForOneCU()
	{
		// self-packed product with per-unit package dimensions (L=10, W=20, H=30 cm); 0.5 kg per unit
		data.helper.pSalad.setIsSelfPacked(true);
		data.helper.pSalad.setLengthInCm(10);
		data.helper.pSalad.setWidthInCm(20);
		data.helper.pSalad.setHeightInCm(30);
		save(data.helper.pSalad);

		// one loose CU HU holding qty 2 of the single product (no carton → top-level VHU / topLevelType CU)
		final HUProducerDestination producer = HUProducerDestination.ofVirtualPI();
		producer.setLocatorId(data.defaultLocatorId);
		data.helper.load(producer, data.helper.pSaladProductId, new BigDecimal("2"), data.helper.uomEach);
		final I_M_HU hu = producer.getCreatedHUs().get(0);

		final ShipmentSchedule saladSched = mockSchedule(SCHED_SALAD, data.helper.pSaladProductId);
		final JsonDeliveryAdvisorRequestParcel parcel = service.buildRequestParcel(
				hu, ImmutableMap.of(SCHED_SALAD, saladSched));

		assertThat(parcel.getTopLevelType()).isEqualTo("CU");
		assertThat(parcel.getItems()).hasSize(1);
		final JsonDeliveryAdvisorRequestItem item = parcel.getItems().get(0);
		// advise for 1 CU — regardless of the packed qty (2)
		assertThat(item.getNumberOfItems()).isEqualTo(1);
		assertThat(item.getTotalWeightInKg()).isEqualByComparingTo("0.5"); // 1 unit × 0.5 kg
		// parcel envelope = product single-unit, NOT the qty-2 HU aggregate (which would be 1.0 kg)
		assertThat(parcel.getGrossWeightKg()).isEqualByComparingTo("0.5");
		// a self-packed CU carries the product's NAMED dimensions verbatim (no sort/scale): L=10, W=20, H=30
		assertThat(parcel.getPackageDimensions().getLengthInCM()).isEqualTo(10);
		assertThat(parcel.getPackageDimensions().getWidthInCM()).isEqualTo(20);
		assertThat(parcel.getPackageDimensions().getHeightInCM()).isEqualTo(30);
	}

	/**
	 * When there is a current LU/TU pick target (the parcel being packed), advise must scope to THAT
	 * target parcel only — it must NOT fan out over all picked HUs (which would re-advise already-finished
	 * parcels and can collapse divergent per-parcel carriers). Asserted by: advise never consults
	 * {@code getPickedHuIds} when a current target exists, and advises only the target HU's schedule.
	 */
	@Test
	public void advise_withCurrentTuTarget_scopesToTargetParcel_notAllPickedHUs()
	{
		final I_M_HU targetTU = createTwoProductHU();
		final HuId targetHuId = HuId.ofRepoId(targetTU.getM_HU_ID());

		// current pick target = an existing (materialized) TU — the parcel being packed now
		final TUPickingTarget tuTarget = mock(TUPickingTarget.class);
		when(tuTarget.isExistingTU()).thenReturn(true);
		when(tuTarget.getTuId()).thenReturn(targetHuId);

		final PickingJob pickingJob = mock(PickingJob.class);
		when(pickingJob.getLuPickingTargetEffective(null)).thenReturn(Optional.empty());
		when(pickingJob.getTuPickingTargetEffective(null)).thenReturn(Optional.of(tuTarget));
		// stubbed so the OLD (all-picked) code path can still run to completion — the assertion is that
		// the NEW code never calls it (scoped to the target instead).
		when(pickingJob.getPickedHuIds(null)).thenReturn(ImmutableSet.of(targetHuId));

		// the target HU resolves to a single non-Manual schedule
		final ShipmentSchedule tomatoSched = mockSchedule(SCHED_TOMATO, data.helper.pTomatoProductId);
		when(tomatoSched.getCarrierAdvisingStatus()).thenReturn(CarrierAdviseStatus.Completed);
		when(huShipmentScheduleResolver.resolveSchedulesByIdForHU(any()))
				.thenReturn(ImmutableMap.of(SCHED_TOMATO, tomatoSched));
		doReturn(PackedHUCarrierAdviseService.AdvisedCarrier.builder()
				.carrierProductId(CarrierProductId.ofRepoId(777))
				.carrierGoodsTypeId(CarrierGoodsTypeId.ofRepoId(888))
				.carrierServices(ImmutableSet.of())
				.build())
				.when(service).adviseSchedule(any(), any());

		// line plumbing so persistAdvisedProductOnJob completes without NPE
		final PickingJobLine line = mock(PickingJobLine.class);
		when(line.getScheduleId()).thenReturn(ShipmentScheduleAndJobScheduleId.ofShipmentScheduleId(SCHED_TOMATO));
		when(line.withCarrierAdvise(any(), any(), any(), anyBooleanEq())).thenReturn(line);
		final PickingJob jobAfterLines = mock(PickingJob.class);
		when(pickingJob.withChangedLines(any())).thenReturn(jobAfterLines);
		final PickingJob jobAfterProduct = mock(PickingJob.class);
		when(jobAfterLines.withCarrierProductId(any())).thenReturn(jobAfterProduct);
		when(jobAfterProduct.withCarrierAdviseReadOnly(anyBooleanEq())).thenReturn(jobAfterProduct);

		// --- act ---
		service.advise(pickingJob, null);

		// --- assert: scoped to the current target parcel, never fanned out over all picked HUs ---
		verify(pickingJob, never()).getPickedHuIds(any());
		verify(service).adviseSchedule(eq(SCHED_TOMATO), any());
	}

	/**
	 * Job-level (no line context), multiple lines of DIFFERENT products, no pick target: the carriers diverge and
	 * there is nothing to advise onto, so the button is available (an API-advise carrier exists) but read-only.
	 */
	@Test
	public void resolveInfo_jobLevel_multipleLinesDifferentProducts_noTarget_availableReadOnly()
	{
		final PickingJobLine line1 = mockLine(data.helper.pTomatoProductId, CarrierProductId.ofRepoId(701));
		final PickingJobLine line2 = mockLine(data.helper.pSaladProductId, CarrierProductId.ofRepoId(702));

		final CarrierAdviseTargetInfo info = service.resolveInfo(mockJobLevelJob(ImmutableList.of(line1, line2)), null, "en_US");
		assertThat(info.isAvailable()).isTrue();
		assertThat(info.isReadOnly()).isTrue();
	}

	// Job-level WITH a pick target: the read-only flag now derives from the target parcel's own shipment
	// schedules, not the job lines — not faithfully reproducible on a mocked PickingJob (in-memory JUnit does
	// not prove HU behaviour, de.metas.handlingunits.base/CLAUDE.md). Covered with real HU data by
	// nShiftShipment.feature display _300/_310/_330 and guard _201/_202.

	/**
	 * Job-level advise with the SAME product (CU unambiguous) but a MIXED shipper set: one line's carrier is an
	 * API-advise shipper, the other's is a non-API fallback carrier. The non-API carrier is filtered out, leaving
	 * the single API carrier, so the button IS offered — for that API carrier.
	 */
	@Test
	public void resolveInfo_jobLevel_sameProductMixedApiAndNonApiCarriers_availableForApiCarrier()
	{
		final CarrierProductId apiCarrierProductId = CarrierProductId.ofRepoId(701);
		final PickingJobLine apiLine = mockLine(data.helper.pTomatoProductId, apiCarrierProductId, true);
		final PickingJobLine nonApiLine = mockLine(data.helper.pTomatoProductId, CarrierProductId.ofRepoId(702), false);

		// the header carries the (single, API-advise) current carrier → its name is the caption
		final PickingJob job = mockJobLevelJob(ImmutableList.of(apiLine, nonApiLine));
		when(job.getCarrierProductId()).thenReturn(apiCarrierProductId);

		final CarrierAdviseTargetInfo info = service.resolveInfo(job, null, "en_US");
		assertThat(info.isAvailable()).isTrue();
		assertThat(info.getProductCaption()).isEqualTo("carrier");
	}

	/**
	 * Single line with no carrier product yet (null carrierProductId): excluded by the Objects::nonNull filter →
	 * empty carrier-product set → button not offered.
	 */
	@Test
	public void resolveInfo_jobLevel_singleProductNoCarrierProduct_notAvailable()
	{
		final PickingJobLine line = mockLine(data.helper.pTomatoProductId, null);

		assertThat(service.resolveInfo(mockJobLevelJob(ImmutableList.of(line)), null, "en_US").isAvailable())
				.isFalse();
	}

	/**
	 * A non-API-advise shipper (IsApiCarrierAdvise=N) still carries a shipper-name fallback carrier product, yet
	 * the re-advise button must not be offered — the line is not advise-eligible.
	 */
	@Test
	public void resolveInfo_jobLevel_nonApiAdviseShipper_notAvailable()
	{
		final PickingJobLine line = mockLine(data.helper.pTomatoProductId, CarrierProductId.ofRepoId(701), false);

		assertThat(service.resolveInfo(mockJobLevelJob(ImmutableList.of(line)), null, "en_US").isAvailable())
				.isFalse();
	}

	private PickingJobLine mockLine(final ProductId productId, final CarrierProductId carrierProductId)
	{
		return mockLine(productId, carrierProductId, true);
	}

	private PickingJobLine mockLine(final ProductId productId, final CarrierProductId carrierProductId, final boolean apiCarrierAdvise)
	{
		final PickingJobLine line = mock(PickingJobLine.class);
		when(line.getProductId()).thenReturn(productId);
		when(line.getCarrierProductId()).thenReturn(carrierProductId);
		if (carrierProductId != null)
		{
			final ShipperId shipperId = ShipperId.ofRepoId(idSeq++);
			when(carrierProductRepository.getCachedShipperProductById(carrierProductId)).thenReturn(
					CarrierProduct.builder().id(carrierProductId).shipperId(shipperId).code("code").name("carrier").build());
			when(shipperRepository.isApiCarrierAdvise(shipperId)).thenReturn(apiCarrierAdvise);
		}
		return line;
	}

	private static PickingJob mockJobLevelJob(final ImmutableList<PickingJobLine> lines)
	{
		final PickingJob job = mock(PickingJob.class);
		when(job.isLineLevelPickTarget()).thenReturn(false);
		// no pick target → hasExistingTarget=false (read the header, read-only DISPLAY)
		when(job.getLuPickingTargetEffective(null)).thenReturn(Optional.empty());
		when(job.getTuPickingTargetEffective(null)).thenReturn(Optional.empty());
		when(job.getLines()).thenReturn(lines);
		return job;
	}

	private I_M_HU createTwoProductHU()
	{
		final HUProducerDestination producer = HUProducerDestination.ofVirtualPI();
		producer.setLocatorId(data.defaultLocatorId);

		data.helper.load(producer, data.helper.pTomatoProductId, new BigDecimal("20"), data.helper.uomKg);
		data.helper.load(producer, data.helper.pSaladProductId, new BigDecimal("3"), data.helper.uomEach);

		final List<I_M_HU> createdHUs = producer.getCreatedHUs();
		assertThat(createdHUs).hasSize(1);
		return createdHUs.get(0);
	}

	private static TUPickingTarget existingTuTarget(final HuId tuId)
	{
		final TUPickingTarget target = mock(TUPickingTarget.class);
		when(target.isExistingTU()).thenReturn(true);
		when(target.getTuId()).thenReturn(tuId);
		return target;
	}

	private ShipmentSchedule mockSchedule(final ShipmentScheduleId id, final ProductId productId)
	{
		final ShipmentSchedule s = mock(ShipmentSchedule.class);
		when(s.getId()).thenReturn(id);
		when(s.getProductId()).thenReturn(productId);
		when(s.getOrderAndLineId()).thenReturn(null);
		return s;
	}

	private static JsonDeliveryAdvisorRequestItem findItem(
			final JsonDeliveryAdvisorRequestParcel parcel,
			final String productName)
	{
		return parcel.getItems().stream()
				.filter(item -> productName.equals(item.getProductName()))
				.findFirst()
				.orElseThrow(() -> new AssertionError("No item for product " + productName));
	}

	// primitive-boolean matcher helper (matches any boolean value)
	private static boolean anyBooleanEq()
	{
		return anyBoolean();
	}

	@SuppressWarnings("unchecked")
	private static ArgumentCaptor<UnaryOperator<PickingJobLine>> lineMapperCaptor()
	{
		return ArgumentCaptor.forClass((Class<UnaryOperator<PickingJobLine>>) (Class<?>) UnaryOperator.class);
	}

	/**
	 * SysConfig gate for the single-CU baseline branch in {@link PackedHUCarrierAdviseService#buildRequestParcel}.
	 * When {@code de.metas.handlingunits.PackageDimensions.CheckIsSelfPacked=Y},
	 * a non-self-packed single-CU HU with dims → {@link PackageDimensions#UNSPECIFIED}.
	 * When absent / 'N' (default), dims are used.
	 */
	@Nested
	class IsSelfPackedGate
	{
		private static final String SYSCONFIG_CHECK_IS_SELF_PACKED
				= "de.metas.handlingunits.PackageDimensions.CheckIsSelfPacked";

		@Test
		public void whenSysConfigY_nonSelfPacked_singleCU_dimensionsUnspecified()
		{
			// Arrange: SysConfig Y → legacy gate active
			Services.get(ISysConfigBL.class).setValue(SYSCONFIG_CHECK_IS_SELF_PACKED, true, ClientId.SYSTEM, OrgId.ANY);

			// pSalad is NOT self-packed; give it dims
			data.helper.pSalad.setIsSelfPacked(false);
			data.helper.pSalad.setLengthInCm(10);
			data.helper.pSalad.setWidthInCm(20);
			data.helper.pSalad.setHeightInCm(30);
			save(data.helper.pSalad);

			final HUProducerDestination producer = HUProducerDestination.ofVirtualPI();
			producer.setLocatorId(data.defaultLocatorId);
			data.helper.load(producer, data.helper.pSaladProductId, new BigDecimal("1"), data.helper.uomEach);
			final I_M_HU cu = producer.getCreatedHUs().get(0);

			final ShipmentSchedule saladSched = mockSchedule(SCHED_SALAD, data.helper.pSaladProductId);
			final JsonDeliveryAdvisorRequestParcel parcel = service.buildRequestParcel(
					cu, ImmutableMap.of(SCHED_SALAD, saladSched));

			// Gate active: non-self-packed → PackageDimensions.UNSPECIFIED → all three dims = -1
			assertThat(parcel.getPackageDimensions()).isNotNull();
			assertThat(parcel.getPackageDimensions().getLengthInCM()).isEqualTo(-1);
			assertThat(parcel.getPackageDimensions().getWidthInCM()).isEqualTo(-1);
			assertThat(parcel.getPackageDimensions().getHeightInCM()).isEqualTo(-1);
		}

		@Test
		public void whenSysConfigN_nonSelfPacked_singleCU_dimensionsReturned()
		{
			// Arrange: SysConfig N (default) → gate off
			Services.get(ISysConfigBL.class).setValue(SYSCONFIG_CHECK_IS_SELF_PACKED, false, ClientId.SYSTEM, OrgId.ANY);

			data.helper.pSalad.setIsSelfPacked(false);
			data.helper.pSalad.setLengthInCm(10);
			data.helper.pSalad.setWidthInCm(20);
			data.helper.pSalad.setHeightInCm(30);
			save(data.helper.pSalad);

			final HUProducerDestination producer = HUProducerDestination.ofVirtualPI();
			producer.setLocatorId(data.defaultLocatorId);
			data.helper.load(producer, data.helper.pSaladProductId, new BigDecimal("1"), data.helper.uomEach);
			final I_M_HU cu = producer.getCreatedHUs().get(0);

			final ShipmentSchedule saladSched = mockSchedule(SCHED_SALAD, data.helper.pSaladProductId);
			final JsonDeliveryAdvisorRequestParcel parcel = service.buildRequestParcel(
					cu, ImmutableMap.of(SCHED_SALAD, saladSched));

			// Gate off: dims returned
			assertThat(parcel.getPackageDimensions()).isNotNull();
			assertThat(parcel.getPackageDimensions().getLengthInCM()).isEqualTo(10);
			assertThat(parcel.getPackageDimensions().getWidthInCM()).isEqualTo(20);
			assertThat(parcel.getPackageDimensions().getHeightInCM()).isEqualTo(30);
		}
	}
}
