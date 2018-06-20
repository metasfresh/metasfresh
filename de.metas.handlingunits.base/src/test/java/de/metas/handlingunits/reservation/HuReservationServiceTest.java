package de.metas.handlingunits.reservation;

import static java.math.BigDecimal.ONE;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.Before;
import org.junit.Test;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUPackingMaterialsCollector;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.spi.IHUPackingMaterialCollectorSource;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import mockit.Mocked;

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

public class HuReservationServiceTest
{
	@Mocked
	private IHUPackingMaterialsCollector<IHUPackingMaterialCollectorSource> noopPackingMaterialsCollector;

	private LUTUProducerDestinationTestSupport data;

	private HuReservationService huReservationService;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		data = new LUTUProducerDestinationTestSupport();
		huReservationService = new HuReservationService();
		huReservationService.setHuTransformServiceSupplier(() -> HUTransformService.newInstance(data.helper.getHUContext()));
	}

	@Test
	public void makeReservation_from_aggregate_TU()
	{
		final I_M_HU hu = data.mkAggregateHUWithTotalQtyCU("200");

		final I_M_HU tu = Services.get(IHandlingUnitsBL.class).getTopLevelParent(hu);

		final I_M_Product cuProduct = data.helper.pTomato;
		final I_C_UOM cuUOM = data.helper.uomKg;

		final HuReservationRequest request = HuReservationRequest.builder()
				.salesOrderLineId(OrderLineId.ofRepoId(20))
				.huId(HuId.ofRepoId(tu.getM_HU_ID()))
				.qtyToReserve(Quantity.of(ONE, cuUOM))
				.productId(ProductId.ofRepoId(cuProduct.getM_Product_ID()))
				.build();
		// data.helper.commitAndDumpHU(hu);
		final HuReservation result = huReservationService.makeReservation(request);
		assertThat(result).isNotNull();

		assertThat(result.getReservedQtySum().getQty()).isEqualByComparingTo("1");
		assertThat(result.getReservedQtySum().getUOMId()).isEqualTo(cuUOM.getC_UOM_ID());
	}
}
