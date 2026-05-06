package org.adempiere.mm.attributes.spi.impl;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.AbstractHUTestWithSampling;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.weightable.IWeightable;
import de.metas.handlingunits.attribute.weightable.Weightables;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.product.ProductId;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_UOM;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link WeightGenerateHUTrxListener}.
 */
public class WeightGenerateHUTrxListenerTest extends AbstractHUTestWithSampling
{
	private I_M_Product product1;
	private ProductId product1Id;

	@Override
	protected void afterInitialize()
	{
		// Generic test product with both a NetWeight (M_Product.Weight) and a GrossWeight set.
		// Stocking UOM is kg, matching the trx UOM used in the load() calls so the conversion
		// inside IProductBL.getNetWeight / getGrossWeight is 1:1.
		product1 = BusinessTestHelper.createProduct("product1", uomKg);
		product1.setWeight(new BigDecimal("0.200"));           // net weight per unit = 0.200 kg
		product1.setGrossWeight(new BigDecimal("0.205"));      // gross weight per unit = 0.205 kg
		product1.setGrossWeight_UOM_ID(uomKg.getC_UOM_ID());   // gross weight expressed in kg
		save(product1);
		product1Id = ProductId.ofRepoId(product1.getM_Product_ID());
	}

	/**
	 * After loading qty=10 of a product with NetWeight=0.200 kg and GrossWeight=0.205 kg
	 * into a fresh VHU, the listener must write:
	 * <ul>
	 *   <li>WeightNet  = 10 × 0.200 = 2.000 kg</li>
	 *   <li>WeightTare = 10 × (0.205 − 0.200) = 0.050 kg</li>
	 *   <li>WeightGross = WeightNet + WeightTare = 2.050 kg (unchanged total)</li>
	 * </ul>
	 */
	@Test
	public void trxLineProcessed_writesNetAndTareDelta_whenProductHasBothNetAndGross()
	{
		// --- arrange: create a fresh VHU loaded with qty=10 kg of pTomato ---
		final HUProducerDestination producer = HUProducerDestination.ofVirtualPI();
		producer.setLocatorId(defaultLocatorId);

		helper.load(producer, product1Id, BigDecimal.TEN, helper.uomKg);

		final List<I_M_HU> createdVHUs = producer.getCreatedHUs();
		assertThat(createdVHUs).as("expected exactly one VHU").hasSize(1);

		final I_M_HU vhu = createdVHUs.get(0);
		Services.get(IHUStatusBL.class).setHUStatus(helper.getHUContext(), vhu, X_M_HU.HUSTATUS_Active);
		save(vhu);

		// --- act: retrieve the weightable from the VHU ---
		final IAttributeStorageFactory attrStorageFactory = helper.getHUContext().getHUAttributeStorageFactory();
		final IAttributeStorage attrStorage = attrStorageFactory.getAttributeStorage(vhu);
		final IWeightable weightable = Weightables.wrap(attrStorage);

		// --- assert ---
		assertThat(weightable.getWeightNet())
				.as("WeightNet after fill")
				.isEqualByComparingTo("2.000");

		assertThat(weightable.getWeightTare())
				.as("WeightTare after fill")
				.isEqualByComparingTo("0.050");

		assertThat(weightable.getWeightGross())
				.as("WeightGross after fill (Net + Tare)")
				.isEqualByComparingTo("2.050");
	}

	/**
	 * Same fill as above, but the product's gross-weight UOM (gram) differs from the net-weight UOM (kg).
	 * The listener must convert 205 g → 0.205 kg before computing the tare delta.
	 * Without the conversion step the listener would throw a UOM mismatch exception.
	 */
	@Test
	public void trxLineProcessed_writesNetAndTareDelta_whenProductGrossUOMDiffersFromNetUOM()
	{
		// --- arrange: gram UOM + product-scoped gram→kg conversion ---
		final I_C_UOM uomGram = BusinessTestHelper.createUOM("g", X_C_UOM.UOMTYPE_Weigth, 3);
		uomGram.setX12DE355(X12DE355.ofCode("GRM").getCode());
		save(uomGram);

		BusinessTestHelper.createUOMConversion(CreateUOMConversionRequest.builder()
				.productId(product1Id)
				.fromUomId(UomId.ofRepoId(uomGram.getC_UOM_ID()))
				.toUomId(UomId.ofRepoId(uomKg.getC_UOM_ID()))
				.fromToMultiplier(new BigDecimal("0.001"))
				.build());

		// reconfigure product1: net weight in kg (unchanged), gross weight in gram
		product1.setGrossWeight(new BigDecimal("205"));        // gross weight per unit = 205 g = 0.205 kg
		product1.setGrossWeight_UOM_ID(uomGram.getC_UOM_ID());
		save(product1);

		// --- act: load qty=10 kg of product1 into a fresh VHU ---
		final HUProducerDestination producer = HUProducerDestination.ofVirtualPI();
		producer.setLocatorId(defaultLocatorId);

		helper.load(producer, product1Id, BigDecimal.TEN, helper.uomKg);

		final List<I_M_HU> createdVHUs = producer.getCreatedHUs();
		assertThat(createdVHUs).as("expected exactly one VHU").hasSize(1);

		final I_M_HU vhu = createdVHUs.get(0);
		Services.get(IHUStatusBL.class).setHUStatus(helper.getHUContext(), vhu, X_M_HU.HUSTATUS_Active);
		save(vhu);

		// --- assert: same numerical expectations as the same-UOM case ---
		final IAttributeStorageFactory attrStorageFactory = helper.getHUContext().getHUAttributeStorageFactory();
		final IAttributeStorage attrStorage = attrStorageFactory.getAttributeStorage(vhu);
		final IWeightable weightable = Weightables.wrap(attrStorage);

		assertThat(weightable.getWeightNet())
				.as("WeightNet after fill with mismatched gross/net UOMs")
				.isEqualByComparingTo("2.000");

		assertThat(weightable.getWeightTare())
				.as("WeightTare after fill with mismatched gross/net UOMs")
				.isEqualByComparingTo("0.050");

		assertThat(weightable.getWeightGross())
				.as("WeightGross after fill with mismatched gross/net UOMs")
				.isEqualByComparingTo("2.050");
	}

	/**
	 * When the product master has no net weight (M_Product.Weight is the default 0),
	 * the listener does not derive WeightNet/WeightTare. The VHU keeps its seed weight
	 * attribute values (0 for a fresh VHU with no packing material).
	 */
	@Test
	public void trxLineProcessed_writesNothing_whenProductHasNoNetWeight()
	{
		// --- arrange: product with only a gross weight set (net stays at default 0) ---
		final I_M_Product productGrossOnly = BusinessTestHelper.createProduct("productGrossOnly", uomKg);
		productGrossOnly.setGrossWeight(new BigDecimal("0.205"));
		productGrossOnly.setGrossWeight_UOM_ID(uomKg.getC_UOM_ID());
		save(productGrossOnly);
		final ProductId productGrossOnlyId = ProductId.ofRepoId(productGrossOnly.getM_Product_ID());

		// --- act: load qty=10 kg into a fresh VHU ---
		final HUProducerDestination producer = HUProducerDestination.ofVirtualPI();
		producer.setLocatorId(defaultLocatorId);

		helper.load(producer, productGrossOnlyId, BigDecimal.TEN, uomKg);

		final List<I_M_HU> createdVHUs = producer.getCreatedHUs();
		assertThat(createdVHUs).as("expected exactly one VHU").hasSize(1);

		final I_M_HU vhu = createdVHUs.get(0);
		Services.get(IHUStatusBL.class).setHUStatus(helper.getHUContext(), vhu, X_M_HU.HUSTATUS_Active);
		save(vhu);

		// --- assert: VHU's weight attributes remain at their seed values (0) ---
		final IAttributeStorageFactory attrStorageFactory = helper.getHUContext().getHUAttributeStorageFactory();
		final IAttributeStorage attrStorage = attrStorageFactory.getAttributeStorage(vhu);
		final IWeightable weightable = Weightables.wrap(attrStorage);

		assertThat(weightable.getWeightNet())
				.as("WeightNet stays at seed when product has no net weight")
				.isEqualByComparingTo("0");

		assertThat(weightable.getWeightTare())
				.as("WeightTare stays at seed when product has no net weight")
				.isEqualByComparingTo("0");

		assertThat(weightable.getWeightGross())
				.as("WeightGross stays at seed when product has no net weight")
				.isEqualByComparingTo("0");
	}
}
