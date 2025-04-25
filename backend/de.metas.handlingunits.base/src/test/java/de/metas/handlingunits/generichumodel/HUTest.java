package de.metas.handlingunits.generichumodel;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.NonNull;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Stack;
import java.util.function.Supplier;

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

	/**
	 * Expect that the 1st and 3rd CU are both retained.
	 * The first one because it explicitly has ref1 and the third one because it has no reference, but its parent has ref1.
	 */
	@Test
	void retainReference_2()
	{
		// given
		final ProductId productId = BusinessTestHelper.createProductId("product", stockUOMRecord);
		final TableRecordReference ref1 = TableRecordReference.of(1, 2);
		final TableRecordReference ref2 = TableRecordReference.of(3, 4);

		final HU tu1 = prepareTU(productId, ref1, ref2, false);

		// when
		final HU result = tu1.retainReference(ref1);

		// then
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(HuId.ofRepoId(10));
		assertThat(result.getProductQtysInStockUOM().get(productId)).isEqualByComparingTo(Quantity.of("101", stockUOMRecord));
		assertThat(result.getChildHUs()).hasSize(2); // we expect c1_1 and c1_3 to be retained
		assertThat(result.getProductQtysInStockUOM()).hasSize(1);
		assertThat(result.getWeightNet()).isNotNull();
		assertThat(result.getWeightNet()).isEqualByComparingTo(Quantity.of("8", weightUOMRecord));
		assertThat(result.getReferencingModels()).containsExactly(ref1);

		assertThat(result.getChildHUs().get(0).getId()).isEqualTo(HuId.ofRepoId(1));
		assertThat(result.getChildHUs().get(0).getProductQtysInStockUOM().get(productId)).isEqualByComparingTo(Quantity.of("1", stockUOMRecord));
		assertThat(result.getChildHUs().get(0).getWeightNet()).isNotNull();
		assertThat(result.getChildHUs().get(0).getWeightNet()).isEqualByComparingTo(Quantity.of("3", weightUOMRecord));
		assertThat(result.getChildHUs().get(0).getReferencingModels()).containsExactly(ref1);

		assertThat(result.getChildHUs().get(1).getId()).isEqualTo(HuId.ofRepoId(3));
		assertThat(result.getChildHUs().get(1).getProductQtysInStockUOM().get(productId)).isEqualByComparingTo(Quantity.of("100", stockUOMRecord));
		assertThat(result.getChildHUs().get(1).getWeightNet()).isNotNull();
		assertThat(result.getChildHUs().get(1).getWeightNet()).isEqualByComparingTo(Quantity.of("5", weightUOMRecord));
		assertThat(result.getChildHUs().get(1).getReferencingModels()).isEmpty(); // it doesn't contain any ref. it's just here because its parent does

	}

	/**
	 * Expect that only the 2nd CU is retained.
	 */
	@Test
	void retainReference_3()
	{
		// given
		final ProductId productId = BusinessTestHelper.createProductId("product", stockUOMRecord);
		final TableRecordReference ref1 = TableRecordReference.of(1, 2);
		final TableRecordReference ref2 = TableRecordReference.of(3, 4);

		final HU tu1 = prepareTU(productId, ref1, ref2, true);

		// when
		final HU result = tu1.retainReference(ref2);

		// then
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(HuId.ofRepoId(10));
		assertThat(result.getProductQtysInStockUOM().get(productId)).isEqualByComparingTo(Quantity.of("110", stockUOMRecord));
		assertThat(result.getChildHUs()).hasSize(2); // we expect c1_2 and c1_3 to be retained
		assertThat(result.getWeightNet()).isNotNull();
		assertThat(result.getWeightNet()).isEqualByComparingTo(Quantity.of("9", weightUOMRecord));
		assertThat(result.getReferencingModels()).containsExactly(ref2);

		assertThat(result.getChildHUs().get(0).getId()).isEqualTo(HuId.ofRepoId(2));
		assertThat(result.getChildHUs().get(0).getProductQtysInStockUOM().get(productId)).isEqualByComparingTo(Quantity.of("10", stockUOMRecord));
		assertThat(result.getChildHUs().get(0).getWeightNet()).isNotNull();
		assertThat(result.getChildHUs().get(0).getWeightNet()).isEqualByComparingTo(Quantity.of("4", weightUOMRecord));
		assertThat(result.getChildHUs().get(0).getReferencingModels()).containsExactly(ref2);

		assertThat(result.getChildHUs().get(1).getId()).isEqualTo(HuId.ofRepoId(3));
		assertThat(result.getChildHUs().get(1).getProductQtysInStockUOM().get(productId)).isEqualByComparingTo(Quantity.of("100", stockUOMRecord));
		assertThat(result.getChildHUs().get(1).getWeightNet()).isNotNull();
		assertThat(result.getChildHUs().get(1).getWeightNet()).isEqualByComparingTo(Quantity.of("5", weightUOMRecord));
		assertThat(result.getChildHUs().get(1).getReferencingModels()).containsExactly(ref2);
	}

	@Test
	public void extractMedianCUQtyPerChildHU()
	{
		final ProductId productId = BusinessTestHelper.createProductId("product", stockUOMRecord);
		final TableRecordReference ref1 = TableRecordReference.of(1, 2);
		final TableRecordReference ref2 = TableRecordReference.of(3, 4);

		final HU tu1 = prepareTU(productId, ref1, ref2, true/*true or false, doesn't matter for this test*/);

		assertThat(tu1.extractMedianCUQtyPerChildHU(productId)).isEqualByComparingTo(Quantity.of(TEN, stockUOMRecord));
	}

	@Test
	void extractLotNumber()
	{
		final ProductId productId = BusinessTestHelper.createProductId("product", stockUOMRecord);
		final TableRecordReference ref1 = TableRecordReference.of(1, 2);
		final TableRecordReference ref2 = TableRecordReference.of(3, 4);

		final HU tu1 = prepareTU(productId, ref1, ref2, true/*true or false, doesn't matter for this test*/);

		{
			// tu1 has no lotno and its children have different lotNos => expect null
			final Stack<String> lotNumbers = new Stack<>();
			lotNumbers.push("4");
			lotNumbers.push("3");
			lotNumbers.push("3");
			lotNumbers.push(""); // this will be the lotnumber for tu1
			final @NonNull Supplier<String> lotNumberSupplier = lotNumbers::pop;

			final String result = tu1.extractLotNumber(lotNumberSupplier);

			assertThat(result).isNull();
		}

		{
			// tu1 has no lotno and its children the same lotno 4. expect 4
			final Stack<String> lotNumbers = new Stack<>();
			lotNumbers.push("4");
			lotNumbers.push("4");
			lotNumbers.push("4");
			lotNumbers.push(""); // this will be the lotnumber for tu1
			final @NonNull Supplier<String> lotNumberSupplier = lotNumbers::pop;

			final String result = tu1.extractLotNumber(lotNumberSupplier);

			assertThat(result).isEqualTo("4");
		}

		{
			// tu1 has a lotno 
			final Stack<String> lotNumbers = new Stack<>();
			lotNumbers.push("1");
			final @NonNull Supplier<String> lotNumberSupplier = lotNumbers::pop;

			final String result = tu1.extractLotNumber(lotNumberSupplier);

			assertThat(result).isEqualTo("1");
		}
	}

	/**
	 * The HUs all have the same product
	 */
	private HU prepareTU(
			@NonNull final ProductId productId,
			@NonNull final TableRecordReference ref1,
			@NonNull final TableRecordReference ref2,
			final boolean putRef2IntoCu1_3)
	{
		final HU cu1_1 = HU.builder()
				.id(HuId.ofRepoId(1))
				.orgId(OrgId.ofRepoId(20))
				.type(HUType.VirtualPI)
				.packagingCode(null)
				.attributes(ImmutableAttributeSet.EMPTY)
				.productQtyInStockUOM(productId, Quantity.of(ONE, stockUOMRecord))
				.weightNet(Quantity.of(new BigDecimal("3"), weightUOMRecord))
				.referencingModel(ref1)
				.build();

		// explicitly references ref2 tableRecordReference, so it shall not be retained if ref1 is asked for
		final HU cu1_2 = HU.builder()
				.id(HuId.ofRepoId(2))
				.orgId(OrgId.ofRepoId(20))
				.type(HUType.VirtualPI)
				.packagingCode(null)
				.attributes(ImmutableAttributeSet.EMPTY)
				.productQtyInStockUOM(productId, Quantity.of(TEN, stockUOMRecord))
				.weightNet(Quantity.of(new BigDecimal("4"), weightUOMRecord))
				.referencingModel(ref2)
				.build();

		final HU.HUBuilder cu1_3Builder = HU.builder()
				.id(HuId.ofRepoId(3))
				.orgId(OrgId.ofRepoId(20))
				.type(HUType.VirtualPI)
				.packagingCode(null)
				.attributes(ImmutableAttributeSet.EMPTY)
				.productQtyInStockUOM(productId, Quantity.of("100", stockUOMRecord))
				.weightNet(Quantity.of(new BigDecimal("5"), weightUOMRecord));
		if (putRef2IntoCu1_3)
		{
			// explicitly references ref2 tableRecordReference, so it shall not be retained if ref1 is asked for
			cu1_3Builder.referencingModel(ref2);
		}
		final HU cu1_3 = cu1_3Builder.build();

		return HU.builder()
				.id(HuId.ofRepoId(10))
				.orgId(OrgId.ofRepoId(20))
				.type(HUType.TransportUnit)
				.attributes(ImmutableAttributeSet.EMPTY)
				.childHU(cu1_1)
				.childHU(cu1_2)
				.childHU(cu1_3)
				.productQtyInStockUOM(productId, Quantity.of("111", stockUOMRecord))
				.weightNet(Quantity.of(new BigDecimal("12"), weightUOMRecord))
				.referencingModel(ref1)
				.build();
	}
}

