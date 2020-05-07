package de.metas.handlingunits.generichumodel;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;

import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

class HUTest
{

	private I_C_UOM stockUOMRecord;
	private I_C_UOM weightUOMRecord;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		stockUOMRecord = BusinessTestHelper.createUOM("stockUom");
		weightUOMRecord = BusinessTestHelper.createUOM("weightUOMRecord");
	}

	@Test
	void retainProduct()
	{
		final HU cu1_1 = HU.builder()
				.id(HuId.ofRepoId(1))
				.orgId(OrgId.ofRepoId(20))
				.type(HUType.VirtualPI)
				.packagingCode(Optional.empty())
				.attributes(ImmutableAttributeSet.EMPTY)
				.productQtyInStockUOM(ProductId.ofRepoId(31), Quantity.of(ONE, stockUOMRecord))
				.weightNet(Optional.of(Quantity.of(new BigDecimal("4"), weightUOMRecord)))
				.build();

		final HU cu1_2 = cu1_1.toBuilder()
				.clearProductQtysInStockUOM()
				.id(HuId.ofRepoId(2))
				.productQtyInStockUOM(ProductId.ofRepoId(32), Quantity.of(TEN, stockUOMRecord))
				.weightNet(Optional.of(Quantity.of(new BigDecimal("6"), weightUOMRecord)))
				.build();

		final HU tu1 = cu1_1.toBuilder().clearChildHUs().clearProductQtysInStockUOM()
				.id(HuId.ofRepoId(10))
				.type(HUType.TransportUnit)
				.childHU(cu1_1)
				.childHU(cu1_2)
				.productQtyInStockUOM(ProductId.ofRepoId(31), Quantity.of(ONE, stockUOMRecord))
				.productQtyInStockUOM(ProductId.ofRepoId(32), Quantity.of(TEN, stockUOMRecord))
				.weightNet(Optional.of(Quantity.of(new BigDecimal("10"), weightUOMRecord)))
				.build();

		final HU tu2 = tu1.toBuilder().clearChildHUs().clearProductQtysInStockUOM()
				.clearProductQtysInStockUOM()
				.id(HuId.ofRepoId(20))
				.productQtyInStockUOM(ProductId.ofRepoId(31), Quantity.of(new BigDecimal("20"), stockUOMRecord))
				.weightNet(Optional.of(Quantity.of(new BigDecimal("20"), weightUOMRecord)))
				.build();

		final HU tu3 = tu1.toBuilder().clearChildHUs().clearProductQtysInStockUOM()
				.clearProductQtysInStockUOM()
				.id(HuId.ofRepoId(30))
				.productQtyInStockUOM(ProductId.ofRepoId(32), Quantity.of(new BigDecimal("30"), stockUOMRecord))
				.weightNet(Optional.of(Quantity.of(new BigDecimal("30"), weightUOMRecord)))
				.build();

		final HU lu = tu1.toBuilder().clearChildHUs().clearProductQtysInStockUOM()
				.id(HuId.ofRepoId(100))
				.type(HUType.LoadLogistiqueUnit)
				.childHU(tu1)
				.childHU(tu2)
				.childHU(tu3)
				.weightNet(Optional.of(Quantity.of(new BigDecimal("60"), weightUOMRecord)))
				.productQtyInStockUOM(ProductId.ofRepoId(31), Quantity.of(new BigDecimal("21"), stockUOMRecord))
				.productQtyInStockUOM(ProductId.ofRepoId(32), Quantity.of(new BigDecimal("40"), stockUOMRecord))
				.build();

		// invoke the method under test
		final Optional<HU> luResult = lu.retainProduct(ProductId.ofRepoId(31));

		assertThat(luResult).isPresent();
		assertThat(luResult.get().getChildHUs()).as("tu3 should not have been retained").hasSize(2);
		assertThat(luResult.get().getWeightNet()).isPresent();
		assertThat(luResult.get().getWeightNet().get())
				.as("lu shall have a weight of 4 + 20 (from cu1_1 and tu2)").isEqualTo(Quantity.of(new BigDecimal("24"), weightUOMRecord));

		assertThat(luResult.get().getProductQtysInStockUOM())
				.as("lu shall only have productId 31").containsOnlyKeys(ProductId.ofRepoId(31))
				.as("lu shall have a qty of 20 + 1 (from tu1 and tu2)").containsValue(Quantity.of(new BigDecimal("21"), stockUOMRecord));

		final ImmutableMap<HuId, HU> tuId2tu = Maps.uniqueIndex(luResult.get().getChildHUs(), HU::getId);
		final HU tu1Result = tuId2tu.get(HuId.ofRepoId(10));
		final HU tu2Result = tuId2tu.get(HuId.ofRepoId(20));

		assertThat(tu1Result.getProductQtysInStockUOM())
				.containsOnlyKeys(ProductId.ofRepoId(31))
				.containsValue(Quantity.of(ONE, stockUOMRecord));
		assertThat(tu1Result.getChildHUs()).as("cu1_2 should not have been retained").hasSize(1);
		assertThat(tu1Result.getWeightNet()).isPresent();
		assertThat(tu1Result.getWeightNet().get())
				.as("lu shall have a weight of 4 (from cu1_1)").isEqualTo(Quantity.of(new BigDecimal("4"), weightUOMRecord));

		assertThat(tu2Result.getProductQtysInStockUOM())
				.containsOnlyKeys(ProductId.ofRepoId(31))
				.containsValue(Quantity.of(new BigDecimal("20"), stockUOMRecord));
	}

}
