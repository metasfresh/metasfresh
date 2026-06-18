package de.metas.picking.workflow;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequestItem;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequestParcel;
import de.metas.customstariff.CustomsTariffRepository;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.shipping.PackedHUShippingInfoService;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.CarrierAdviseStatus;
import de.metas.inoutcandidate.ShipmentSchedule;
import de.metas.inoutcandidate.ShipmentScheduleService;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleId;
import de.metas.product.ProductId;
import de.metas.product.ProductRepository;
import de.metas.shipper.gateway.commons.model.CarrierProductRepository;
import de.metas.shipping.CarrierProductId;
import de.metas.shipping.ShipperRepository;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.UnaryOperator;

import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
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
	private PackedHUCarrierAdviseService service;

	private static final ShipmentScheduleId SCHED_TOMATO = ShipmentScheduleId.ofRepoId(201);
	private static final ShipmentScheduleId SCHED_SALAD = ShipmentScheduleId.ofRepoId(202);

	// net weight per stocking UOM (kg) — set on the products so computeGrossWeight falls back to net weight
	private static final BigDecimal TOMATO_WEIGHT_KG = new BigDecimal("2");
	private static final BigDecimal SALAD_WEIGHT_KG = new BigDecimal("0.5");

	@BeforeEach
	public void init()
	{
		data = new LUTUProducerDestinationTestSupport();
		SpringContextHolder.registerJUnitBean(ProductRepository.newInstanceForUnitTesting());

		packedHUShippingInfoService = PackedHUShippingInfoService.newInstanceForUnitTesting();
		huShipmentScheduleResolver = mock(HUShipmentScheduleResolver.class);
		shipmentScheduleService = mock(ShipmentScheduleService.class);
		pickingJobRepository = mock(PickingJobRepository.class);

		// spy: the advise(...) flow is exercised end-to-end except the static CarrierAdviseCommand call
		// (real DB + shipper-gateway work), which is stubbed at the #adviseSchedule seam.
		service = spy(new PackedHUCarrierAdviseService(
				packedHUShippingInfoService,
				huShipmentScheduleResolver,
				ProductRepository.newInstanceForUnitTesting(),
				mock(CarrierProductRepository.class),
				mock(CustomsTariffRepository.class),
				mock(ShipperRepository.class),
				shipmentScheduleService,
				pickingJobRepository));

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
	 *     <li>the non-Manual (Tomato) line gets the advised carrier product and {@code carrierAdviseReadOnly=true};</li>
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

		// HU resolves to a non-Manual Tomato schedule (re-advised) + a Manual Salad schedule (skipped)
		final ShipmentSchedule tomatoSched = mockSchedule(SCHED_TOMATO, data.helper.pTomatoProductId);
		when(tomatoSched.getCarrierAdvisingStatus()).thenReturn(CarrierAdviseStatus.Completed);
		final ShipmentSchedule saladSched = mockSchedule(SCHED_SALAD, data.helper.pSaladProductId);
		when(saladSched.getCarrierAdvisingStatus()).thenReturn(CarrierAdviseStatus.Manual);
		final ImmutableMap<ShipmentScheduleId, ShipmentSchedule> schedulesById = ImmutableMap.of(
				SCHED_TOMATO, tomatoSched,
				SCHED_SALAD, saladSched);
		when(huShipmentScheduleResolver.resolveSchedulesByIdForHU(any())).thenReturn(schedulesById);

		// stub the static-command seam (no real DB / shipper-gateway call in a unit test)
		doNothing().when(service).adviseSchedule(any(), any());

		// post-executeSync re-read: the Tomato schedule now carries the advised product
		final ShipmentSchedule tomatoSchedAdvised = mock(ShipmentSchedule.class);
		when(tomatoSchedAdvised.getCarrierProductId()).thenReturn(advisedProductId);
		when(shipmentScheduleService.getById(SCHED_TOMATO)).thenReturn(tomatoSchedAdvised);

		// --- picking job: one non-Manual line (Tomato) + one Manual line (Salad) ---
		final PickingJobLine tomatoLine = mock(PickingJobLine.class);
		when(tomatoLine.getScheduleId()).thenReturn(ShipmentScheduleAndJobScheduleId.ofShipmentScheduleId(SCHED_TOMATO));
		final PickingJobLine tomatoLineChanged = mock(PickingJobLine.class);
		when(tomatoLine.withCarrierProductIdAndReadOnly(any(), anyBooleanEq())).thenReturn(tomatoLineChanged);

		final PickingJobLine saladLine = mock(PickingJobLine.class);
		when(saladLine.getScheduleId()).thenReturn(ShipmentScheduleAndJobScheduleId.ofShipmentScheduleId(SCHED_SALAD));

		final PickingJob pickingJob = mock(PickingJob.class);
		when(pickingJob.getPickedHuIds(null)).thenReturn(com.google.common.collect.ImmutableSet.of(huId));

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
		// the line mapper sets the advised product + readOnly on the non-Manual line, leaves the Manual line as-is
		final UnaryOperator<PickingJobLine> mapper = mapperCaptor.getValue();
		assertThat(mapper.apply(tomatoLine)).isSameAs(tomatoLineChanged);
		verify(tomatoLine).withCarrierProductIdAndReadOnly(advisedProductId, true);
		assertThat(mapper.apply(saladLine)).isSameAs(saladLine);
		verify(saladLine, never()).withCarrierProductIdAndReadOnly(any(), anyBooleanEq());

		// header product + readOnly + save
		verify(jobAfterLines).withCarrierProductId(advisedProductId);
		verify(jobAfterProduct).withCarrierAdviseReadOnly(true);
		verify(pickingJobRepository).save(jobFinal);
		assertThat(result).isSameAs(jobFinal);
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
		return org.mockito.ArgumentMatchers.anyBoolean();
	}

	@SuppressWarnings("unchecked")
	private static ArgumentCaptor<UnaryOperator<PickingJobLine>> lineMapperCaptor()
	{
		return ArgumentCaptor.forClass((Class<UnaryOperator<PickingJobLine>>) (Class<?>) UnaryOperator.class);
	}
}
