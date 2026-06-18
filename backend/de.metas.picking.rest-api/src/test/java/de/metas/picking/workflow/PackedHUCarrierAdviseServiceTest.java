package de.metas.picking.workflow;

import com.google.common.collect.ImmutableMap;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequestItem;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequestParcel;
import de.metas.customstariff.CustomsTariffRepository;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.shipping.PackedHUShippingInfoService;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.ShipmentSchedule;
import de.metas.product.ProductId;
import de.metas.product.ProductRepository;
import de.metas.shipper.gateway.commons.model.CarrierProductRepository;
import de.metas.shipping.ShipperRepository;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Drives {@link PackedHUCarrierAdviseService#buildRequestParcel} for a top-level HU holding TWO
 * products and asserts the resulting parcel carries one item per product, each with the
 * parity-correct per-item nominal gross weight (NShiftDraftDeliveryOrderCreator#computeNominalGrossWeightInKg).
 */
@ExtendWith(AdempiereTestWatcher.class)
public class PackedHUCarrierAdviseServiceTest
{
	private LUTUProducerDestinationTestSupport data;
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

		final PackedHUShippingInfoService packedHUShippingInfoService = PackedHUShippingInfoService.newInstanceForUnitTesting();

		service = new PackedHUCarrierAdviseService(
				packedHUShippingInfoService,
				mock(HUShipmentScheduleResolver.class),
				ProductRepository.newInstanceForUnitTesting(),
				mock(CarrierProductRepository.class),
				mock(CustomsTariffRepository.class),
				mock(ShipperRepository.class));

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
		final HUProducerDestination producer = HUProducerDestination.ofVirtualPI();
		producer.setLocatorId(data.defaultLocatorId);

		data.helper.load(producer, data.helper.pTomatoProductId, new BigDecimal("20"), data.helper.uomKg);
		data.helper.load(producer, data.helper.pSaladProductId, new BigDecimal("3"), data.helper.uomEach);

		final List<I_M_HU> createdHUs = producer.getCreatedHUs();
		assertThat(createdHUs).hasSize(1);
		final I_M_HU hu = createdHUs.get(0);

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

	private ShipmentSchedule mockSchedule(final ShipmentScheduleId id, final ProductId productId)
	{
		final ShipmentSchedule s = mock(ShipmentSchedule.class);
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
}
