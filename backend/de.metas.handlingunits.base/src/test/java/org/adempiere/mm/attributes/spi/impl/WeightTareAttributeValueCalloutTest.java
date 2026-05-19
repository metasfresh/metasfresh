package org.adempiere.mm.attributes.spi.impl;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2026 metas GmbH
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
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU;
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
 * Unit tests for {@link WeightTareAttributeValueCallout#calculateWeightTare(I_M_HU)}.
 * The recompute formula must include the per-CU packaging contribution
 * (M_Product.GrossWeight − M_Product.Weight) × storage qty in addition to the
 * existing packing-material PI sum, so the value matches what
 * {@link WeightGenerateHUTrxListener} accumulates incrementally for non-aggregate VHUs
 * and stays consistent across the recompute call sites
 * (AggregateHUTrxListener, AbstractProducerDestination#loadFinished, attribute seed).
 */
public class WeightTareAttributeValueCalloutTest extends AbstractHUTestWithSampling
{
	private I_M_Product product1;
	private ProductId product1Id;

	@Override
	protected void afterInitialize()
	{
		product1 = BusinessTestHelper.createProduct("product1", uomKg);
		product1.setWeight(new BigDecimal("0.200"));           // net weight per unit = 0.200 kg
		product1.setGrossWeight(new BigDecimal("0.205"));      // gross weight per unit = 0.205 kg
		product1.setGrossWeight_UOM_ID(uomKg.getC_UOM_ID());
		save(product1);
		product1Id = ProductId.ofRepoId(product1.getM_Product_ID());
	}

	/**
	 * Virtual-PI VHU with no packing material — the packing-material PI sum is 0,
	 * so the only contributor to calculateWeightTare(hu) is the per-CU storage delta:
	 * 10 × (0.205 − 0.200) = 0.050 kg.
	 */
	@Test
	public void calculateWeightTare_includesPerCUDeltaFromStorage()
	{
		// --- arrange: load qty=10 kg of product1 into a fresh virtual VHU ---
		final HUProducerDestination producer = HUProducerDestination.ofVirtualPI();
		producer.setLocatorId(defaultLocatorId);

		helper.load(producer, product1Id, BigDecimal.TEN, uomKg);

		final List<I_M_HU> createdVHUs = producer.getCreatedHUs();
		assertThat(createdVHUs).as("expected exactly one VHU").hasSize(1);

		final I_M_HU vhu = createdVHUs.get(0);
		Services.get(IHUStatusBL.class).setHUStatus(helper.getHUContext(), vhu, X_M_HU.HUSTATUS_Active);
		save(vhu);

		// --- act + assert ---
		final BigDecimal tare = WeightTareAttributeValueCallout.calculateWeightTare(vhu);
		assertThat(tare)
				.as("calculateWeightTare(hu) must include the per-CU storage delta")
				.isEqualByComparingTo("0.050");
	}

	/**
	 * Same as above, but the product's gross-weight UOM (gram) differs from the
	 * net-weight UOM (kg, hardcoded by IProductBL). The recompute must convert
	 * 205 g → 0.205 kg before computing the per-CU delta.
	 */
	@Test
	public void calculateWeightTare_includesPerCUDeltaFromStorage_whenProductGrossUOMDiffersFromNetUOM()
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

		// reconfigure product1: net stays in kg, gross moves to gram
		product1.setGrossWeight(new BigDecimal("205"));        // 205 g = 0.205 kg
		product1.setGrossWeight_UOM_ID(uomGram.getC_UOM_ID());
		save(product1);

		// --- arrange: load qty=10 kg of product1 into a fresh virtual VHU ---
		final HUProducerDestination producer = HUProducerDestination.ofVirtualPI();
		producer.setLocatorId(defaultLocatorId);

		helper.load(producer, product1Id, BigDecimal.TEN, uomKg);

		final List<I_M_HU> createdVHUs = producer.getCreatedHUs();
		assertThat(createdVHUs).as("expected exactly one VHU").hasSize(1);

		final I_M_HU vhu = createdVHUs.get(0);
		Services.get(IHUStatusBL.class).setHUStatus(helper.getHUContext(), vhu, X_M_HU.HUSTATUS_Active);
		save(vhu);

		// --- act + assert: same numerical expectation as the same-UOM case ---
		final BigDecimal tare = WeightTareAttributeValueCallout.calculateWeightTare(vhu);
		assertThat(tare)
				.as("calculateWeightTare(hu) must convert mismatched gross/net UOMs before computing the delta")
				.isEqualByComparingTo("0.050");
	}

	/**
	 * Non-aggregate TU with a real packing-material PI (IFCO crate). The recompute formula
	 * must be additive: PI packing-material sum (the IFCO's own weight) + per-CU storage delta
	 * (10 × 0.005 = 0.050 kg). Uses pTomato (which the test base wires into the IFCO PI),
	 * configured with the same weight props as product1 in TC3.
	 */
	@Test
	public void calculateWeightTare_addsStorageDeltaOnTopOfPackingMaterialPITare()
	{
		// Configure pTomato — it is the product the IFCO PI accepts in the test-base setup.
		pTomato.setC_UOM_ID(uomKg.getC_UOM_ID());
		pTomato.setWeight(new BigDecimal("0.200"));
		pTomato.setGrossWeight(new BigDecimal("0.205"));
		pTomato.setGrossWeight_UOM_ID(uomKg.getC_UOM_ID());
		save(pTomato);

		// Load 10 kg of pTomato into a fresh IFCO TU (huDefIFCO_10 holds 10 kg per IFCO).
		final HUProducerDestination producer = HUProducerDestination.of(huDefIFCO_10);
		producer.setLocatorId(defaultLocatorId);
		helper.load(producer, helper.pTomatoProductId, BigDecimal.TEN, uomKg);

		final List<I_M_HU> created = producer.getCreatedHUs();
		assertThat(created).as("expected exactly one IFCO TU").hasSize(1);

		final I_M_HU ifcoTU = created.get(0);
		Services.get(IHUStatusBL.class).setHUStatus(helper.getHUContext(), ifcoTU, X_M_HU.HUSTATUS_Active);
		save(ifcoTU);

		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		assertThat(handlingUnitsBL.isAggregateHU(ifcoTU)).as("IFCO TU is non-aggregate").isFalse();

		// PI packing-material tare alone (the IFCO's own weight, configured by the test base).
		final I_M_HU_PI_Version piVersion = handlingUnitsBL.getPIVersion(ifcoTU);
		final BigDecimal piTareOnly = WeightTareAttributeValueCallout.getWeightTare(piVersion);

		// Total = PI packing-material tare + per-CU storage delta (10 × 0.005 = 0.050).
		final BigDecimal totalTare = WeightTareAttributeValueCallout.calculateWeightTare(ifcoTU);
		assertThat(totalTare)
				.as("calculateWeightTare(non-aggregate TU) = PI packing-material sum + per-CU storage delta")
				.isEqualByComparingTo(piTareOnly.add(new BigDecimal("0.050")));
	}

	/**
	 * Aggregate-HU branch: an LU built with 5 aggregated IFCO TUs of pTomato (10 kg each = 50 kg total)
	 * yields a single aggregate VHU. {@code calculateWeightTare} on the aggregate VHU follows the
	 * aggregate branch of the callout (packing material × tuCount) and must add the per-CU storage
	 * delta over the aggregate's consolidated storage (50 kg × 0.005 = 0.250 kg).
	 * <p>
	 * The test takes a baseline reading with {@code Gross == Net} (storage delta = 0), then mutates
	 * the master to introduce a 0.005 kg/kg delta and asserts the resulting tare grew by exactly
	 * 50 × 0.005 = 0.250 kg. Differential measurement avoids hard-coding the test base's IFCO
	 * packing-material weight or going through DAOs from the test.
	 */
	@Test
	public void calculateWeightTare_aggregateHU_includesPerCUDeltaAcrossAggregatedStorage()
	{
		// Baseline: configure pTomato with Gross == Net so the per-CU storage delta is 0.
		pTomato.setC_UOM_ID(uomKg.getC_UOM_ID());
		pTomato.setWeight(new BigDecimal("0.200"));
		pTomato.setGrossWeight(new BigDecimal("0.200"));
		pTomato.setGrossWeight_UOM_ID(uomKg.getC_UOM_ID());
		save(pTomato);

		// Build an LU with 5 IFCO TUs × 10 kg each = 50 kg, structured as an aggregate.
		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setLocatorId(defaultLocatorId);
		lutuProducer.setLUPI(huDefPalet);
		lutuProducer.setMaxLUs(1);
		lutuProducer.setMaxTUsPerLU(5);
		lutuProducer.setLUItemPI(huItemIFCO_10);
		lutuProducer.setTUPI(huDefIFCO_10);
		lutuProducer.addCUPerTU(helper.pTomatoProductId, BigDecimal.TEN, uomKg);

		helper.load(lutuProducer, helper.pTomatoProductId, BigDecimal.valueOf(50), uomKg);

		final List<I_M_HU> lus = lutuProducer.getCreatedHUs();
		assertThat(lus).as("expected exactly one LU").hasSize(1);
		final I_M_HU lu = lus.get(0);

		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		// Locate the aggregate VHU under the LU.
		final I_M_HU aggregateVHU = handlingUnitsBL.retrieveIncludedHUs(lu).stream()
				.filter(handlingUnitsBL::isAggregateHU)
				.findFirst()
				.orElseThrow(() -> new AssertionError("expected an aggregate VHU under the LU"));

		assertThat(handlingUnitsBL.isAggregateHU(aggregateVHU))
				.as("located HU is the aggregate VHU")
				.isTrue();

		// Baseline tare (no per-CU delta): the aggregate branch returns packing material × tuCount only.
		final BigDecimal tareBaseline = WeightTareAttributeValueCallout.calculateWeightTare(aggregateVHU);

		// Introduce a 0.005 kg/kg per-CU packaging delta on the master.
		pTomato.setGrossWeight(new BigDecimal("0.205"));
		save(pTomato);

		// Tare with delta: expected to grow by 50 × 0.005 = 0.250 kg.
		final BigDecimal tareWithDelta = WeightTareAttributeValueCallout.calculateWeightTare(aggregateVHU);

		assertThat(tareWithDelta.subtract(tareBaseline))
				.as("aggregate VHU's calculateWeightTare adds per-CU delta over the aggregated storage qty (50 × 0.005)")
				.isEqualByComparingTo("0.250");
	}
}
