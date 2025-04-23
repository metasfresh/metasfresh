package de.metas.handlingunits.generichumodel;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.assertThat;

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
	void retainReference_1()
	{
		final TableRecordReference ref1 = TableRecordReference.of(1, 2);

		final HU cu1_1 = HU.builder()
				.id(HuId.ofRepoId(1))
				.orgId(OrgId.ofRepoId(20))
				.type(HUType.VirtualPI)
				.packagingCode(null)
				.attributes(ImmutableAttributeSet.EMPTY)
				.productQtyInStockUOM(ProductId.ofRepoId(31), Quantity.of(ONE, stockUOMRecord))
				.weightNet(Quantity.of(new BigDecimal("4"), weightUOMRecord))
				.referencingModel(ref1)
				.build();

		final HU cu1_2 = cu1_1.toBuilder()
				.clearProductQtysInStockUOM()
				.clearReferencingModels()
				.id(HuId.ofRepoId(2))
				.productQtyInStockUOM(ProductId.ofRepoId(32), Quantity.of(TEN, stockUOMRecord))
				.weightNet(Quantity.of(new BigDecimal("6"), weightUOMRecord))
				.build();

		// contains cu1_1 and cu1_2
		final HU tu1 = cu1_1.toBuilder()
				.clearChildHUs()
				.clearProductQtysInStockUOM()
				.clearReferencingModels()
				.id(HuId.ofRepoId(10))
				.type(HUType.TransportUnit)
				.childHU(cu1_1)
				.childHU(cu1_2)
				.productQtyInStockUOM(ProductId.ofRepoId(31), Quantity.of(ONE, stockUOMRecord))
				.productQtyInStockUOM(ProductId.ofRepoId(32), Quantity.of(TEN, stockUOMRecord))
				.weightNet(Quantity.of(new BigDecimal("10"), weightUOMRecord))
				.build();

		// contains no CUs but references ref1
		final HU tu2 = tu1.toBuilder()
				.clearChildHUs()
				.clearProductQtysInStockUOM()
				.id(HuId.ofRepoId(20))
				.productQtyInStockUOM(ProductId.ofRepoId(31), Quantity.of(new BigDecimal("20"), stockUOMRecord))
				.weightNet(Quantity.of(new BigDecimal("20"), weightUOMRecord))
				.referencingModel(ref1)
				.build();

		// contains no CUs
		final HU tu3 = tu1.toBuilder()
				.clearChildHUs()
				.clearProductQtysInStockUOM()
				.clearPackagingGTINs()
				.id(HuId.ofRepoId(30))
				.productQtyInStockUOM(ProductId.ofRepoId(32), Quantity.of(new BigDecimal("30"), stockUOMRecord))
				.weightNet(Quantity.of(new BigDecimal("30"), weightUOMRecord))
				.build();

		final HU lu = tu1.toBuilder()
				.clearChildHUs()
				.clearProductQtysInStockUOM()
				.clearReferencingModels()
				.id(HuId.ofRepoId(100))
				.type(HUType.LoadLogistiqueUnit)
				.childHU(tu1)
				.childHU(tu2)
				.childHU(tu3)
				.weightNet(Quantity.of(new BigDecimal("60"), weightUOMRecord))
				.productQtyInStockUOM(ProductId.ofRepoId(31), Quantity.of(new BigDecimal("21"), stockUOMRecord))
				.productQtyInStockUOM(ProductId.ofRepoId(32), Quantity.of(new BigDecimal("40"), stockUOMRecord))
				.build();

		// invoke the method under test
		final HU luResult = lu.retainReference(ref1);

		assertThat(luResult).isNotNull();
		assertThat(luResult.getChildHUs()).as("tu3 should not have been retained").hasSize(2);
		assertThat(luResult.getWeightNet()).isNotNull();
		assertThat(luResult.getWeightNet())
				.as("lu shall have a weight of 4 + 20 (from cu1_1 and tu2)").isEqualTo(Quantity.of(new BigDecimal("24"), weightUOMRecord));

		assertThat(luResult.getProductQtysInStockUOM())
				.as("lu shall only have productId 31").containsOnlyKeys(ProductId.ofRepoId(31))
				.as("lu shall have a qty of 20 + 1 (from tu1 and tu2)").containsValue(Quantity.of(new BigDecimal("21"), stockUOMRecord));

		final ImmutableMap<HuId, HU> tuId2tu = Maps.uniqueIndex(luResult.getChildHUs(), HU::getId);
		final HU tu1Result = tuId2tu.get(HuId.ofRepoId(10));
		final HU tu2Result = tuId2tu.get(HuId.ofRepoId(20));

		assertThat(tu1Result.getProductQtysInStockUOM())
				.containsOnlyKeys(ProductId.ofRepoId(31))
				.containsValue(Quantity.of(ONE, stockUOMRecord));
		assertThat(tu1Result.getChildHUs()).as("cu1_2 should not have been retained").hasSize(1);
		assertThat(tu1Result.getWeightNet()).isNotNull();
		assertThat(tu1Result.getWeightNet())
				.as("lu shall have a weight of 4 (from cu1_1)").isEqualTo(Quantity.of(new BigDecimal("4"), weightUOMRecord));

		assertThat(tu2Result.getProductQtysInStockUOM())
				.containsOnlyKeys(ProductId.ofRepoId(31))
				.containsValue(Quantity.of(new BigDecimal("20"), stockUOMRecord));
	}

	@Test
	void retainReference_2()
	{
		// given
		final TableRecordReference ref1 = TableRecordReference.of(1, 2);
		final TableRecordReference ref2 = TableRecordReference.of(3, 4);

		final HU cu1_1 = HU.builder()
				.id(HuId.ofRepoId(1))
				.orgId(OrgId.ofRepoId(20))
				.type(HUType.VirtualPI)
				.packagingCode(null)
				.attributes(ImmutableAttributeSet.EMPTY)
				.productQtyInStockUOM(ProductId.ofRepoId(31), Quantity.of(ONE, stockUOMRecord))
				.weightNet(Quantity.of(new BigDecimal("3"), weightUOMRecord))
				.referencingModel(ref1)
				.build();

		final HU cu1_2 = HU.builder()
				.id(HuId.ofRepoId(2))
				.orgId(OrgId.ofRepoId(20))
				.type(HUType.VirtualPI)
				.packagingCode(null)
				.attributes(ImmutableAttributeSet.EMPTY)
				.productQtyInStockUOM(ProductId.ofRepoId(31), Quantity.of(TEN, stockUOMRecord))
				.weightNet(Quantity.of(new BigDecimal("4"), weightUOMRecord))
				.referencingModel(ref2)
				.build();

		final HU tu1 = cu1_1.toBuilder().clearChildHUs().clearProductQtysInStockUOM()
				.id(HuId.ofRepoId(10))
				.type(HUType.TransportUnit)
				.childHU(cu1_1)
				.childHU(cu1_2)
				.productQtyInStockUOM(ProductId.ofRepoId(31), Quantity.of("11", stockUOMRecord))
				.weightNet(Quantity.of(new BigDecimal("7"), weightUOMRecord))
				.referencingModel(ref1)
				.referencingModel(ref2)
				.build();

		// when
		final HU result = tu1.retainReference(ref1);

		// then
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(HuId.ofRepoId(10));
		assertThat(result.getChildHUs()).hasSize(1);
		assertThat(result.getChildHUs().get(0).getId()).isEqualTo(HuId.ofRepoId(1));
		assertThat(result.getChildHUs().get(0).getProductQtysInStockUOM().get(ProductId.ofRepoId(31))).isEqualByComparingTo(Quantity.of(ONE, stockUOMRecord));
		assertThat(result.getChildHUs().get(0).getWeightNet()).isNotNull();
		assertThat(result.getChildHUs().get(0).getWeightNet()).isEqualByComparingTo(Quantity.of("3", weightUOMRecord));
		
		assertThat(result.getProductQtysInStockUOM()).hasSize(1);
		assertThat(result.getProductQtysInStockUOM().get(ProductId.ofRepoId(31))).isEqualByComparingTo(Quantity.of(ONE, stockUOMRecord));
		assertThat(result.getWeightNet()).isNotNull();
		assertThat(result.getWeightNet()).isEqualByComparingTo(Quantity.of("3", weightUOMRecord));
	}

}

