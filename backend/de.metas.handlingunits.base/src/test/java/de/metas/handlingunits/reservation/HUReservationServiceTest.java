package de.metas.handlingunits.reservation;

import de.metas.global_qrcodes.service.GlobalQRCodeService;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.qrcodes.service.HUQRCodesRepository;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.qrcodes.service.QRCodeConfigurationRepository;
import de.metas.handlingunits.qrcodes.service.QRCodeConfigurationService;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.order.OrderLineId;
import de.metas.printing.DoNothingMassPrintingService;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.assertj.core.api.Condition;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static de.metas.handlingunits.HUAssertions.assertThat;
import static de.metas.handlingunits.HUConditions.isAggregate;
import static de.metas.handlingunits.HUConditions.isNotAggregate;
import static java.math.BigDecimal.ONE;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@ExtendWith(AdempiereTestWatcher.class)
public class HUReservationServiceTest
{
	private LUTUProducerDestinationTestSupport data;

	private HUReservationService huReservationService;

	private IHandlingUnitsBL handlingUnitsBL;

	private I_C_UOM cuUOM;

	private IHandlingUnitsDAO handlingUnitsDAO;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		data = new LUTUProducerDestinationTestSupport();

		final HUReservationRepository huReservationRepository = new HUReservationRepository();

		huReservationService = new HUReservationService(huReservationRepository);
		huReservationService.setHuTransformServiceSupplier(() -> HUTransformService.newInstance(data.helper.getHUContext()));

		cuUOM = data.helper.uomKg;

		handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		final QRCodeConfigurationService qrCodeConfigurationService = new QRCodeConfigurationService(new QRCodeConfigurationRepository());
		SpringContextHolder.registerJUnitBean(qrCodeConfigurationService);
		SpringContextHolder.registerJUnitBean(new HUQRCodesService(new HUQRCodesRepository(), new GlobalQRCodeService(DoNothingMassPrintingService.instance), qrCodeConfigurationService));
	}

	@Test
	public void makeReservation_from_aggregate_TU()
	{
		final I_M_HU lu = handlingUnitsBL.getTopLevelParent(data.mkAggregateHUWithTotalQtyCU("200"));

		final ReserveHUsRequest request = ReserveHUsRequest.builder()
				.documentRef(HUReservationDocRef.ofSalesOrderLineId(OrderLineId.ofRepoId(20)))
				.huId(HuId.ofRepoId(lu.getM_HU_ID()))
				.qtyToReserve(Quantity.of(ONE, cuUOM))
				.productId(data.helper.pTomatoProductId)
				.build();

		// invoke the method under test
		final HUReservation result = huReservationService.makeReservation(request).get();

		assertThat(result).isNotNull();
		assertThat(result.getReservedQtySum().toBigDecimal()).isEqualByComparingTo("1");
		assertThat(result.getReservedQtySum().getUomId().getRepoId()).isEqualTo(cuUOM.getC_UOM_ID());

		// final Map<HuId, Quantity> vhuId2reservedQtys = result.getVhuId2reservedQtys();
		assertThat(result.getVhuIds()).hasSize(1);
		final HuId vhuId = result.getVhuIds().iterator().next();
		assertThat(result.getReservedQtyByVhuId(vhuId).toBigDecimal()).isEqualByComparingTo(ONE);

		assertThatHuHasQty(lu, "200");

		final List<I_M_HU> includedHUs = handlingUnitsDAO.retrieveIncludedHUs(lu);
		// data.helper.commitAndDumpHU(lu);
		assertThat(includedHUs).hasSize(2); // one for the remaining "aggregated" TUs, one for the "real" TU that contains the reserved CU

		assertThat(includedHUs)
				.filteredOn(isAggregate())
				.hasSize(1)
				.allSatisfy(hu -> assertThatHuHasQty(hu, "160"));

		assertThat(includedHUs)
				.filteredOn(isNotAggregate())
				.hasSize(1)
				.allSatisfy(includedHU -> assertThatHuHasQty(includedHU, "40"))
				.allSatisfy(includedHU -> {
					final List<I_M_HU> includedCUs = handlingUnitsDAO.retrieveIncludedHUs(includedHU);
					assertThat(includedCUs).hasSize(2).filteredOn(hasQty("1"))
							.hasSize(1)
							.allMatch(I_M_HU::isReserved)
							.extracting(I_M_HU::getM_HU_ID)
							.allMatch(huId -> huId == vhuId.getRepoId());
					assertThat(includedCUs).hasSize(2).filteredOn(hasQty("39")).hasSize(1);
				});
	}

	@Test
	public void makeReservation_from_aggregate_TU_reserve_all()
	{
		// create one LU with 5 TUs with 40kg each
		final I_M_HU lu = handlingUnitsBL.getTopLevelParent(data.mkAggregateHUWithTotalQtyCU("200"));

		final ReserveHUsRequest firstRequest = ReserveHUsRequest.builder()
				.documentRef(HUReservationDocRef.ofSalesOrderLineId(OrderLineId.ofRepoId(20)))
				.huId(HuId.ofRepoId(lu.getM_HU_ID()))
				.qtyToReserve(Quantity.of("200", cuUOM))
				.productId(data.helper.pTomatoProductId)
				.build();

		// invoke the method under test
		final HUReservation result = huReservationService.makeReservation(firstRequest).get();

		assertThat(result.getReservedQtySum().toBigDecimal()).isEqualByComparingTo("200");

		// final Map<HuId, Quantity> vhuId2reservedQtys = result.getVhuId2reservedQtys();
		assertThat(result.getVhuIds()).hasSize(5);
		final HuId vhuId = result.getVhuIds().iterator().next();
		assertThat(result.getReservedQtyByVhuId(vhuId).toBigDecimal()).isEqualByComparingTo("40");

		assertThatHuHasQty(lu, "200");

		final List<I_M_HU> includedHUs = handlingUnitsDAO.retrieveIncludedHUs(lu);
		assertThat(includedHUs).hasSize(5)
				.filteredOn(isNotAggregate()).hasSize(5)
				.allSatisfy(tu -> assertThatHuHasQty(tu, "40"))
				.allSatisfy(tu -> {
					final List<I_M_HU> includedCUs = handlingUnitsDAO.retrieveIncludedHUs(tu);
					assertThat(includedCUs).hasSize(1)
							.filteredOn(hasQty("40")).hasSize(1)
							.allMatch(I_M_HU::isReserved)
							.extracting(I_M_HU::getM_HU_ID)
							.allMatch(cuId -> result.getVhuIds().contains(HuId.ofRepoId(cuId)));
				});
	}

	@Test
	public void makeReservation_from_aggregate_TU_was_already_reserved()
	{
		final I_M_HU lu = handlingUnitsBL.getTopLevelParent(data.mkAggregateHUWithTotalQtyCU("200"));

		//
		// First reservation request
		{
			final ReserveHUsRequest firstRequest = ReserveHUsRequest.builder()
					.documentRef(HUReservationDocRef.ofSalesOrderLineId(OrderLineId.ofRepoId(20)))
					.huId(HuId.ofRepoId(lu.getM_HU_ID()))
					.qtyToReserve(Quantity.of("200", cuUOM))
					.productId(data.helper.pTomatoProductId)
					.build();

			// invoke the method under test
			final HUReservation firstResult = huReservationService.makeReservation(firstRequest).get();
			assertThat(firstResult.getReservedQtySum().toBigDecimal()).isEqualByComparingTo("200"); // guard
		}

		//
		// Second reservation request
		{
			final ReserveHUsRequest secondRequest = ReserveHUsRequest.builder()
					.documentRef(HUReservationDocRef.ofSalesOrderLineId(OrderLineId.ofRepoId(20)))
					.huId(HuId.ofRepoId(lu.getM_HU_ID()))
					.qtyToReserve(Quantity.of("200", cuUOM))
					.productId(ProductId.ofRepoId(data.helper.pTomato.getM_Product_ID()))
					.build();

			// invoke the method under test
			final Optional<HUReservation> secondResult = huReservationService.makeReservation(secondRequest);
			assertThat(secondResult).isNotPresent();
		}
	}

	private void assertThatHuHasQty(final I_M_HU hu, final String expectedQty)
	{
		final Quantity expectedQuantity = Quantity.of(new BigDecimal(expectedQty), cuUOM);
		assertThat(hu).hasStorage(data.helper.pTomatoProductId, expectedQuantity);
	}

	private Condition<I_M_HU> hasQty(final String qty)
	{
		return new Condition<>(hu -> extractQty(hu).compareTo(new BigDecimal(qty)) == 0, "hu has qty=%s", qty);
	}

	private BigDecimal extractQty(final I_M_HU hu)
	{
		final IHUStorage storage = handlingUnitsBL.getStorageFactory().getStorage(hu);
		final List<IHUProductStorage> productStorages = storage.getProductStorages();
		assertThat(productStorages).hasSize(1);

		final Quantity luQuantity = productStorages.get(0).getQty(cuUOM);
		return luQuantity.toBigDecimal();
	}
}
