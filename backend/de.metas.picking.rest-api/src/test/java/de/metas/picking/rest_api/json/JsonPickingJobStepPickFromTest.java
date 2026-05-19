/*
 * #%L
 * de.metas.picking.rest-api
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.picking.rest_api.json;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HUPIItemProduct;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.handlingunits.picking.QtyRejectedWithReason;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.model.LocatorInfo;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobPickFromAlternativeId;
import de.metas.handlingunits.picking.job.model.PickingJobProgress;
import de.metas.handlingunits.picking.job.model.PickingJobStep;
import de.metas.handlingunits.picking.job.model.PickingJobStepId;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFrom;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFromKey;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickedTo;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickedToHU;
import de.metas.handlingunits.picking.job.model.PickingUnit;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodePackingInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUniqueId;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUnitType;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.product.ProductId;
import de.metas.product.ProductValueAndName;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * RED test: verifies that {@link JsonPickingJobStepPickFrom#of(PickingJobStepPickFrom, PickingJobLine, JsonOpts, Function)}
 * converts CU qty to TU qty when the parent line's picking unit is TU.
 *
 * <p>This test calls the post-fix 4-argument signature and therefore FAILS TO COMPILE
 * against the current 3-argument production code — satisfying the RED phase.
 * Task 2 adds the {@code PickingJobLine} parameter; after that the test must go GREEN.
 *
 * <p>Setup: 12 CU at 2 CU/TU → expected 6 TU.
 */
class JsonPickingJobStepPickFromTest
{
	/** A mocked {@link I_C_UOM} record with {@code C_UOM_ID=100}. */
	private I_C_UOM buildUomEach()
	{
		final I_C_UOM uom = Mockito.mock(I_C_UOM.class);
		Mockito.when(uom.getC_UOM_ID()).thenReturn(100);
		Mockito.when(uom.getUOMType()).thenReturn("");
		Mockito.when(uom.getX12DE355()).thenReturn("EA");
		Mockito.when(uom.getStdPrecision()).thenReturn(2);
		Mockito.when(uom.getCostingPrecision()).thenReturn(2);
		return uom;
	}

	// -----------------------------------------------------------------------
	// Test (a): qtyPicked 12 CU → 6 TU
	// -----------------------------------------------------------------------

	@Test
	void qtyPicked_isMappedFromCuToTu()
	{
		// Arrange
		final I_C_UOM uomEach = buildUomEach();
		final ProductId productId = ProductId.ofRepoId(1001);
		final Quantity twelveCUs = Quantity.of(BigDecimal.valueOf(12), uomEach);
		// 2 CU/TU → 12 / 2 = 6 TU
		final Quantity twoCUsPerTU = Quantity.of(BigDecimal.valueOf(2), uomEach);

		final HUPIItemProduct packingInfo = HUPIItemProduct.builder()
				.id(HUPIItemProductId.ofRepoId(999))
				.name(TranslatableStrings.constant("2 per TU"))
				.piItemId(HuPackingInstructionsItemId.ofRepoId(888))
				.productId(productId)
				.qtyCUsPerTU(twoCUsPerTU)
				.build();

		final PickingJobLine line = Mockito.mock(PickingJobLine.class);
		Mockito.when(line.getPickingUnit()).thenReturn(PickingUnit.TU);
		Mockito.when(line.getProductId()).thenReturn(productId);
		Mockito.when(line.getPackingInfo()).thenReturn(packingInfo);

		final PickingJobStepPickedToHU pickedHU = PickingJobStepPickedToHU.builder()
				.pickFromHUId(HuId.ofRepoId(123))
				.actualPickedHU(buildDummyHUInfo(uomEach))
				.qtyPicked(twelveCUs)
				.createdAt(Instant.now())
				.build();

		final PickingJobStepPickedTo pickedTo = PickingJobStepPickedTo.builder()
				.actualPickedHUs(ImmutableList.of(pickedHU))
				.build();

		final PickingJobStepPickFrom pickFrom = buildPickFrom(pickedTo, uomEach);
		final JsonOpts jsonOpts = JsonOpts.ofAdLanguage("en_US");
		final Function<UomId, ITranslatableString> getUOMSymbolById = uomId -> TranslatableStrings.constant("TU");

		// Act — calls the POST-FIX 4-arg signature; compile fails against current 3-arg code (RED)
		final JsonPickingJobStepPickFrom json =
				JsonPickingJobStepPickFrom.of(pickFrom, line, jsonOpts, getUOMSymbolById);

		// Assert
		assertThat(json.getQtyPicked()).isEqualByComparingTo("6");
	}

	// -----------------------------------------------------------------------
	// Test (b): qtyRejected 12 CU → 6 TU
	// -----------------------------------------------------------------------

	@Test
	void qtyRejected_isMappedFromCuToTu()
	{
		// Arrange
		final I_C_UOM uomEach = buildUomEach();
		final ProductId productId = ProductId.ofRepoId(1001);
		final Quantity twelveCUs = Quantity.of(BigDecimal.valueOf(12), uomEach);
		final Quantity twoCUsPerTU = Quantity.of(BigDecimal.valueOf(2), uomEach);

		final HUPIItemProduct packingInfo = HUPIItemProduct.builder()
				.id(HUPIItemProductId.ofRepoId(999))
				.name(TranslatableStrings.constant("2 per TU"))
				.piItemId(HuPackingInstructionsItemId.ofRepoId(888))
				.productId(productId)
				.qtyCUsPerTU(twoCUsPerTU)
				.build();

		final PickingJobLine line = Mockito.mock(PickingJobLine.class);
		Mockito.when(line.getPickingUnit()).thenReturn(PickingUnit.TU);
		Mockito.when(line.getProductId()).thenReturn(productId);
		Mockito.when(line.getPackingInfo()).thenReturn(packingInfo);

		// pickedTo with only qtyRejected; empty actualPickedHUs is allowed when qtyRejected != null
		final PickingJobStepPickedTo pickedTo = PickingJobStepPickedTo.builder()
				.actualPickedHUs(ImmutableList.of())
				.qtyRejected(QtyRejectedWithReason.of(twelveCUs, QtyRejectedReasonCode.ofCode("DAMAGED")))
				.build();

		final PickingJobStepPickFrom pickFrom = buildPickFrom(pickedTo, uomEach);
		final JsonOpts jsonOpts = JsonOpts.ofAdLanguage("en_US");
		final Function<UomId, ITranslatableString> getUOMSymbolById = uomId -> TranslatableStrings.constant("TU");

		// Act — calls the POST-FIX 4-arg signature; compile fails against current 3-arg code (RED)
		final JsonPickingJobStepPickFrom json =
				JsonPickingJobStepPickFrom.of(pickFrom, line, jsonOpts, getUOMSymbolById);

		// Assert
		assertThat(json.getQtyRejected()).isEqualByComparingTo("6");
	}

	// -----------------------------------------------------------------------
	// Test (c): pickingUnit=CU → qtyPicked passes through unchanged
	// -----------------------------------------------------------------------

	@Test
	void qtyPicked_isNotConverted_whenPickingUnit_isCU()
	{
		// Arrange
		final I_C_UOM uomEach = buildUomEach();
		final ProductId productId = ProductId.ofRepoId(1001);
		final Quantity twelveCUs = Quantity.of(BigDecimal.valueOf(12), uomEach);

		// line reports CU — no TU conversion expected
		final PickingJobLine line = Mockito.mock(PickingJobLine.class);
		Mockito.when(line.getPickingUnit()).thenReturn(PickingUnit.CU);

		final PickingJobStepPickedToHU pickedHU = PickingJobStepPickedToHU.builder()
				.pickFromHUId(HuId.ofRepoId(123))
				.actualPickedHU(buildDummyHUInfo(uomEach))
				.qtyPicked(twelveCUs)
				.createdAt(Instant.now())
				.build();

		final PickingJobStepPickedTo pickedTo = PickingJobStepPickedTo.builder()
				.actualPickedHUs(ImmutableList.of(pickedHU))
				.build();

		final PickingJobStepPickFrom pickFrom = buildPickFrom(pickedTo, uomEach);
		final JsonOpts jsonOpts = JsonOpts.ofAdLanguage("en_US");
		final Function<UomId, ITranslatableString> getUOMSymbolById = uomId -> TranslatableStrings.constant("EA");

		// Act
		final JsonPickingJobStepPickFrom json =
				JsonPickingJobStepPickFrom.of(pickFrom, line, jsonOpts, getUOMSymbolById);

		// Assert — raw CU value passes through; packingInfo must not have been consulted
		assertThat(json.getQtyPicked()).isEqualByComparingTo("12");
		Mockito.verify(line, Mockito.never()).getPackingInfo();
	}

	// -----------------------------------------------------------------------
	// Test (d): JsonPickingJobStep.of end-to-end — alternative pickFrom with TU conversion
	// -----------------------------------------------------------------------

	@Test
	void qtyPicked_inAlternativePickFrom_isMappedFromCuToTu()
	{
		// Arrange
		final I_C_UOM uomEach = buildUomEach();
		final ProductId productId = ProductId.ofRepoId(1001);
		// 4 CU @ 2 CU/TU → expect 2 TU
		final Quantity fourCUs = Quantity.of(BigDecimal.valueOf(4), uomEach);
		final Quantity twoCUsPerTU = Quantity.of(BigDecimal.valueOf(2), uomEach);

		final HUPIItemProduct packingInfo = HUPIItemProduct.builder()
				.id(HUPIItemProductId.ofRepoId(999))
				.name(TranslatableStrings.constant("2 per TU"))
				.piItemId(HuPackingInstructionsItemId.ofRepoId(888))
				.productId(productId)
				.qtyCUsPerTU(twoCUsPerTU)
				.build();

		final PickingJobLine line = Mockito.mock(PickingJobLine.class);
		Mockito.when(line.getPickingUnit()).thenReturn(PickingUnit.TU);
		Mockito.when(line.getProductId()).thenReturn(productId);
		Mockito.when(line.getPackingInfo()).thenReturn(packingInfo);

		// Build main pickFrom with no pickedTo
		final PickingJobStepPickFrom mainPickFrom = buildPickFrom(null, uomEach);

		// Build alternative pickFrom with 4 CU picked
		final PickingJobPickFromAlternativeId altId = PickingJobPickFromAlternativeId.ofRepoId(42);
		final PickingJobStepPickFromKey altKey = PickingJobStepPickFromKey.alternative(altId);

		final PickingJobStepPickedToHU altPickedHU = PickingJobStepPickedToHU.builder()
				.pickFromHUId(HuId.ofRepoId(456))
				.actualPickedHU(buildDummyHUInfo(uomEach))
				.qtyPicked(fourCUs)
				.createdAt(Instant.now())
				.build();

		final PickingJobStepPickedTo altPickedTo = PickingJobStepPickedTo.builder()
				.actualPickedHUs(ImmutableList.of(altPickedHU))
				.build();

		final PickingJobStepPickFrom altPickFrom = PickingJobStepPickFrom.builder()
				.pickFromKey(altKey)
				.pickFromLocator(LocatorInfo.builder()
						.id(LocatorId.ofRepoId(1, 100))
						.caption("loc1")
						.build())
				.pickFromHU(buildDummyHUInfo(uomEach))
				.pickedTo(altPickedTo)
				.build();

		// Mock PickingJobStep
		final Quantity twoCUs = Quantity.of(BigDecimal.valueOf(2), uomEach);
		final PickingJobStep step = Mockito.mock(PickingJobStep.class);
		Mockito.when(step.getId()).thenReturn(PickingJobStepId.ofRepoId(1));
		Mockito.when(step.getProgress()).thenReturn(PickingJobProgress.IN_PROGRESS);
		Mockito.when(step.getProductId()).thenReturn(productId);
		Mockito.when(step.getProductValueAndName()).thenReturn(
				ProductValueAndName.of("VAL-001", TranslatableStrings.constant("Test product")));
		Mockito.when(step.getQtyToPick()).thenReturn(twoCUs);
		Mockito.when(step.getPickFromKeys()).thenReturn(
				ImmutableSet.of(PickingJobStepPickFromKey.MAIN, altKey));
		Mockito.when(step.getPickFrom(PickingJobStepPickFromKey.MAIN)).thenReturn(mainPickFrom);
		Mockito.when(step.getPickFrom(altKey)).thenReturn(altPickFrom);

		final JsonOpts jsonOpts = JsonOpts.ofAdLanguage("en_US");
		final Function<UomId, ITranslatableString> getUOMSymbolById = uomId -> TranslatableStrings.constant("TU");

		// Act
		final JsonPickingJobStep json = JsonPickingJobStep.of(step, line, jsonOpts, getUOMSymbolById);

		// Assert — one alternative; 4 CU @ 2 CU/TU = 2 TU
		assertThat(json.getPickFromAlternatives()).hasSize(1);
		assertThat(json.getPickFromAlternatives().get(0).getQtyPicked()).isEqualByComparingTo("2");
	}

	// -----------------------------------------------------------------------
	// Helpers
	// -----------------------------------------------------------------------

	private static PickingJobStepPickFrom buildPickFrom(
			final PickingJobStepPickedTo pickedTo,
			final I_C_UOM uomEach)
	{
		return PickingJobStepPickFrom.builder()
				.pickFromKey(PickingJobStepPickFromKey.MAIN)
				.pickFromLocator(LocatorInfo.builder()
						.id(LocatorId.ofRepoId(1, 100))
						.caption("loc1")
						.build())
				.pickFromHU(buildDummyHUInfo(uomEach))
				.pickedTo(pickedTo)
				.build();
	}

	private static HUInfo buildDummyHUInfo(@SuppressWarnings("unused") final I_C_UOM uomEach)
	{
		final HUQRCode qrCode = HUQRCode.builder()
				.id(HUQRCodeUniqueId.random())
				.packingInfo(HUQRCodePackingInfo.builder()
						.huUnitType(HUQRCodeUnitType.TU)
						.packingInstructionsId(HuPackingInstructionsId.ofRepoId(12340))
						.caption("Test TU")
						.build())
				.attributes(ImmutableList.of())
				.build();
		return HUInfo.ofHuIdAndQRCode(HuId.ofRepoId(123), qrCode);
	}
}
