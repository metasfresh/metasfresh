package de.metas.picking.rest_api.json.massprinting;

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

	/**
	 * Builds a result entry from the individual per-product values.
	 *
	 * @param productId                  numeric product id (M_Product_ID)
	 * @param boxesPacked                number of boxes packed (one box = one picked unit)
	 * @param labelsPrinted              number of HU labels printed successfully
	 * @param labelPrintFailures         number of label print failures
	 * @param unitsLeftOnLU              units remaining on the LU after packing
	 * @param unitsOfOpenDemandRemaining units of open demand that could not be fulfilled
	 * @return the JSON per-product result entry
	 */
	@NonNull
	public static JsonMassPrintingProductResult of(
			final int productId,
			final int boxesPacked,
			final int labelsPrinted,
			final int labelPrintFailures,
			final int unitsLeftOnLU,
			final int unitsOfOpenDemandRemaining)
	{
		return JsonMassPrintingProductResult.builder()
				.productId(productId)
				.boxesPacked(boxesPacked)
				.labelsPrinted(labelsPrinted)
				.labelPrintFailures(labelPrintFailures)
				.unitsLeftOnLU(unitsLeftOnLU)
				.unitsOfOpenDemandRemaining(unitsOfOpenDemandRemaining)
				.build();
	}
}
