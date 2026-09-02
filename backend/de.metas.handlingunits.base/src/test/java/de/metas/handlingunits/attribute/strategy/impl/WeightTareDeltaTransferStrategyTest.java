package de.metas.handlingunits.attribute.strategy.impl;

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
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferRequest;
import de.metas.handlingunits.model.I_M_HU;
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

import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link WeightTareDeltaTransferStrategy}. The strategy moves only the
 * per-CU packaging delta {@code (Product.GrossWeight − Product.NetWeight) × qty} between
 * source and destination on a VHU-to-VHU transfer; the packing-material portion of
 * {@code WeightTare} is intentionally untouched (BOTU sum on the source's children
 * re-aggregates it).
 */
public class WeightTareDeltaTransferStrategyTest extends AbstractHUTestWithSampling
{
	private final WeightTareDeltaTransferStrategy strategy = WeightTareDeltaTransferStrategy.newInstance();

	private I_M_Product product1;
	private ProductId product1Id;

	@Override
	protected void afterInitialize()
	{
		product1 = BusinessTestHelper.createProduct("product1", uomKg);
		product1.setWeight(new BigDecimal("0.200"));        // net weight per unit = 0.200 kg
		product1.setGrossWeight(new BigDecimal("0.205"));   // gross weight per unit = 0.205 kg
		product1.setGrossWeight_UOM_ID(uomKg.getC_UOM_ID());
		save(product1);
		product1Id = ProductId.ofRepoId(product1.getM_Product_ID());
	}

	/**
	 * Picking-shape transfer: source VHU holds 100 PCE with the listener-written tare
	 * (= 0.005 × 100 = 0.500); destination VHU already holds 50 PCE with its own existing
	 * per-CU tare (= 0.005 × 50 = 0.250) from a previous fill. Transferring 20 PCE moves
	 * the per-CU delta × 20 = 0.100 kg between them.
	 * <p>
	 * Asserts both sides arithmetically:
	 * <ul>
	 *   <li>source loses 0.100 → 0.500 − 0.100 = 0.400</li>
	 *   <li>destination gains 0.100 → 0.250 + 0.100 = 0.350 (additive — does NOT reset)</li>
	 * </ul>
	 * The destination starting at a non-zero tare is the realistic case: receiving HUs
	 * usually have their own existing per-CU contribution, and the strategy must add to it,
	 * not overwrite.
	 */
	@Test
	public void transferAttribute_movesPerCUDeltaForMovedQty()
	{
		// Source VHU with 100 PCE — represents inventory or source-of-pick
		final HUProducerDestination sourceProducer = HUProducerDestination.ofVirtualPI();
		sourceProducer.setLocatorId(defaultLocatorId);
		helper.load(sourceProducer, product1Id, BigDecimal.valueOf(100), uomKg);
		final I_M_HU sourceVHU = sourceProducer.getCreatedHUs().get(0);

		// Destination VHU with 50 PCE — represents an already-partially-filled receiving HU
		final HUProducerDestination destProducer = HUProducerDestination.ofVirtualPI();
		destProducer.setLocatorId(defaultLocatorId);
		helper.load(destProducer, product1Id, BigDecimal.valueOf(50), uomKg);
		final I_M_HU destVHU = destProducer.getCreatedHUs().get(0);

		final IHUContext huContext = helper.getHUContext();
		final IAttributeStorage sourceAttrs = huContext.getHUAttributeStorageFactory().getAttributeStorage(sourceVHU);
		final IAttributeStorage destAttrs = huContext.getHUAttributeStorageFactory().getAttributeStorage(destVHU);

		// Pre-set both sides as the listener would have at fill time:
		//   source.tare  = 0.005 × 100 = 0.500
		//   dest.tare    = 0.005 ×  50 = 0.250 (already-partially-filled receiver)
		sourceAttrs.setValue(attr_WeightTare, new BigDecimal("0.500"));
		destAttrs.setValue(attr_WeightTare, new BigDecimal("0.250"));

		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IHUAttributeTransferRequest request = new HUAttributeTransferRequestBuilder(huContext)
				.setProductId(product1Id)
				.setQty(BigDecimal.valueOf(20))
				.setUOM(uomKg)
				.setAttributeStorageFrom(sourceAttrs)
				.setAttributeStorageTo(destAttrs)
				.setHUStorageFrom(handlingUnitsBL.getStorageFactory().getStorage(sourceVHU))
				.setHUStorageTo(handlingUnitsBL.getStorageFactory().getStorage(destVHU))
				.setVHUTransfer(true)
				.create();

		strategy.transferAttribute(request, attr_WeightTare);

		assertThat(sourceAttrs.getValueAsBigDecimal(attr_WeightTare))
				.as("source loses (0.205 − 0.200) × 20 = 0.100: 0.500 − 0.100 = 0.400")
				.isEqualByComparingTo("0.400");
		assertThat(destAttrs.getValueAsBigDecimal(attr_WeightTare))
				.as("dest ADDS 0.100 to its existing 0.250 = 0.350 (additive, not reset)")
				.isEqualByComparingTo("0.350");
	}

	/**
	 * Same picking-shape scenario as above, but the product's gross-weight UOM (gram)
	 * differs from the net-weight UOM (kg, hardcoded by IProductBL). The strategy must
	 * convert 205 g → 0.205 kg before computing the per-CU delta. Numerical expectation
	 * is identical: (0.205 − 0.200) × 20 = 0.100 kg moved from source to destination.
	 */
	@Test
	public void transferAttribute_movesPerCUDeltaForMovedQty_whenProductGrossUOMDiffersFromNetUOM()
	{
		// gram UOM + product-scoped gram→kg conversion
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
		product1.setGrossWeight(BigDecimal.valueOf(205));      // 205 g = 0.205 kg
		product1.setGrossWeight_UOM_ID(uomGram.getC_UOM_ID());
		save(product1);

		final HUProducerDestination sourceProducer = HUProducerDestination.ofVirtualPI();
		sourceProducer.setLocatorId(defaultLocatorId);
		helper.load(sourceProducer, product1Id, BigDecimal.valueOf(100), uomKg);
		final I_M_HU sourceVHU = sourceProducer.getCreatedHUs().get(0);

		final HUProducerDestination destProducer = HUProducerDestination.ofVirtualPI();
		destProducer.setLocatorId(defaultLocatorId);
		helper.load(destProducer, product1Id, BigDecimal.valueOf(50), uomKg);
		final I_M_HU destVHU = destProducer.getCreatedHUs().get(0);

		final IHUContext huContext = helper.getHUContext();
		final IAttributeStorage sourceAttrs = huContext.getHUAttributeStorageFactory().getAttributeStorage(sourceVHU);
		final IAttributeStorage destAttrs = huContext.getHUAttributeStorageFactory().getAttributeStorage(destVHU);

		sourceAttrs.setValue(attr_WeightTare, new BigDecimal("0.500"));
		destAttrs.setValue(attr_WeightTare, new BigDecimal("0.250"));

		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IHUAttributeTransferRequest request = new HUAttributeTransferRequestBuilder(huContext)
				.setProductId(product1Id)
				.setQty(BigDecimal.valueOf(20))
				.setUOM(uomKg)
				.setAttributeStorageFrom(sourceAttrs)
				.setAttributeStorageTo(destAttrs)
				.setHUStorageFrom(handlingUnitsBL.getStorageFactory().getStorage(sourceVHU))
				.setHUStorageTo(handlingUnitsBL.getStorageFactory().getStorage(destVHU))
				.setVHUTransfer(true)
				.create();

		strategy.transferAttribute(request, attr_WeightTare);

		assertThat(sourceAttrs.getValueAsBigDecimal(attr_WeightTare))
				.as("source loses (205g→0.205kg − 0.200kg) × 20 = 0.100: 0.500 − 0.100 = 0.400")
				.isEqualByComparingTo("0.400");
		assertThat(destAttrs.getValueAsBigDecimal(attr_WeightTare))
				.as("dest gains 0.100 (UOM conversion applied before subtraction): 0.250 + 0.100 = 0.350")
				.isEqualByComparingTo("0.350");
	}

	/**
	 * Product with Net == Gross has no per-CU delta. The strategy short-circuits without
	 * calling {@code setValue} — both sides stay at their pre-set sentinel values
	 * regardless of qty (here: a realistic picking-shape transfer of 20 PCE that would
	 * otherwise move 0.100 kg).
	 */
	@Test
	public void transferAttribute_isNoOp_whenNetEqualsGross()
	{
		product1.setGrossWeight(new BigDecimal("0.200")); // net == gross → delta = 0
		save(product1);

		final HUProducerDestination sourceProducer = HUProducerDestination.ofVirtualPI();
		sourceProducer.setLocatorId(defaultLocatorId);
		helper.load(sourceProducer, product1Id, BigDecimal.valueOf(100), uomKg);
		final I_M_HU sourceVHU = sourceProducer.getCreatedHUs().get(0);

		final HUProducerDestination destProducer = HUProducerDestination.ofVirtualPI();
		destProducer.setLocatorId(defaultLocatorId);
		helper.load(destProducer, product1Id, BigDecimal.valueOf(50), uomKg);
		final I_M_HU destVHU = destProducer.getCreatedHUs().get(0);

		final IHUContext huContext = helper.getHUContext();
		final IAttributeStorage sourceAttrs = huContext.getHUAttributeStorageFactory().getAttributeStorage(sourceVHU);
		final IAttributeStorage destAttrs = huContext.getHUAttributeStorageFactory().getAttributeStorage(destVHU);

		// Sentinels — if the strategy fires it would alter these.
		sourceAttrs.setValue(attr_WeightTare, BigDecimal.valueOf(777));
		destAttrs.setValue(attr_WeightTare, BigDecimal.valueOf(888));

		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IHUAttributeTransferRequest request = new HUAttributeTransferRequestBuilder(huContext)
				.setProductId(product1Id)
				.setQty(BigDecimal.valueOf(20))
				.setUOM(uomKg)
				.setAttributeStorageFrom(sourceAttrs)
				.setAttributeStorageTo(destAttrs)
				.setHUStorageFrom(handlingUnitsBL.getStorageFactory().getStorage(sourceVHU))
				.setHUStorageTo(handlingUnitsBL.getStorageFactory().getStorage(destVHU))
				.setVHUTransfer(true)
				.create();

		strategy.transferAttribute(request, attr_WeightTare);

		assertThat(sourceAttrs.getValueAsBigDecimal(attr_WeightTare))
				.as("Net == Gross: no delta to move; source sentinel untouched")
				.isEqualByComparingTo("777");
		assertThat(destAttrs.getValueAsBigDecimal(attr_WeightTare))
				.as("Net == Gross: no delta to move; dest sentinel untouched")
				.isEqualByComparingTo("888");
	}

	/**
	 * {@code isTransferable} mirrors {@code request.isVHUTransfer()} — the strategy is
	 * meaningful only at the VHU leaf. At TU/LU level the strategy must return false, so
	 * BOTU propagation handles parent tare from children.
	 */
	@Test
	public void isTransferable_mirrorsIsVHUTransfer()
	{
		// Same picking-shape source/dest as the value-asserting tests, so the gate is
		// exercised against a request the math-asserting tests would also produce.
		final HUProducerDestination sourceProducer = HUProducerDestination.ofVirtualPI();
		sourceProducer.setLocatorId(defaultLocatorId);
		helper.load(sourceProducer, product1Id, BigDecimal.valueOf(100), uomKg);
		final I_M_HU sourceVHU = sourceProducer.getCreatedHUs().get(0);

		final HUProducerDestination destProducer = HUProducerDestination.ofVirtualPI();
		destProducer.setLocatorId(defaultLocatorId);
		helper.load(destProducer, product1Id, BigDecimal.valueOf(50), uomKg);
		final I_M_HU destVHU = destProducer.getCreatedHUs().get(0);

		final IHUContext huContext = helper.getHUContext();
		final IAttributeStorage sourceAttrs = huContext.getHUAttributeStorageFactory().getAttributeStorage(sourceVHU);
		final IAttributeStorage destAttrs = huContext.getHUAttributeStorageFactory().getAttributeStorage(destVHU);

		final IHUAttributeTransferRequest vhuRequest = new HUAttributeTransferRequestBuilder(huContext)
				.setProductId(product1Id)
				.setQty(BigDecimal.valueOf(20))
				.setUOM(uomKg)
				.setAttributeStorageFrom(sourceAttrs)
				.setAttributeStorageTo(destAttrs)
				.setVHUTransfer(true)
				.create();
		assertThat(strategy.isTransferable(vhuRequest, attr_WeightTare))
				.as("VHU-to-VHU transfer is in scope").isTrue();

		final IHUAttributeTransferRequest nonVhuRequest = new HUAttributeTransferRequestBuilder(huContext)
				.setProductId(product1Id)
				.setQty(BigDecimal.valueOf(20))
				.setUOM(uomKg)
				.setAttributeStorageFrom(sourceAttrs)
				.setAttributeStorageTo(destAttrs)
				.setVHUTransfer(false)
				.create();
		assertThat(strategy.isTransferable(nonVhuRequest, attr_WeightTare))
				.as("TU/LU-level transfer is out of scope; parent tare comes from BOTU sum").isFalse();
	}
}
