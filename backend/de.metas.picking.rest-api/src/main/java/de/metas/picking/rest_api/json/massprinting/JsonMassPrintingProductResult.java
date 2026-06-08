package de.metas.picking.rest_api.json.massprinting;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * Per-product result within a {@link JsonMassPrintingResult}.
 * One entry per self-packed product found on the scanned LU that had open demand.
 */
@Value
@Builder
@Jacksonized
public class JsonMassPrintingProductResult
{
	/** Numeric product id (M_Product_ID). */
	int productId;

	/** Number of boxes packed (one box = one picked unit). */
	int boxesPacked;

	/**
	 * Shippable HU ids produced by this scan — one per picked unit.
	 * VHU/null-PI path: one VHU per unit (M_HU_PI_ID=101, HU_UnitType='V').
	 * TU/finite-PI path: one TU box per unit.
	 * Size equals {@link #boxesPacked}.
	 * Exposed so the test harness can assert the HU type (VirtualPI vs TransportUnit)
	 * and verify the VHU path for null-PI self-packed schedules.
	 */
	@NonNull @lombok.Builder.Default ImmutableList<Integer> packedHUIds = ImmutableList.of();

	/** Number of HU labels printed successfully. */
	int labelsPrinted;

	/** Number of label print failures (labels not printed due to an error). */
	int labelPrintFailures;

	/** Units remaining on the LU after packing (demand was fully satisfied before LU was exhausted). */
	int unitsLeftOnLU;

	/**
	 * Units of open demand remaining after packing (demand that could not be fulfilled because
	 * the LU did not have enough units).
	 */
	int unitsOfOpenDemandRemaining;
}
