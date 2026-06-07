package de.metas.handlingunits.picking.job.massprinting;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/**
 * Per-scan result summary returned after a mass-printing scan.
 * Contains per-product results for self-packed products and skipped products.
 */
@Value
@Builder
public class MassPrintingResult
{
	@NonNull @Singular ImmutableList<ProductResult> productResults;

	/** Products on the LU that were skipped because they are not IsSelfPacked. */
	@NonNull @Singular ImmutableList<ProductId> skippedNonSelfPackedProductIds;

	@Value
	@Builder
	public static class ProductResult
	{
		@NonNull ProductId productId;

		/** Number of boxes packed (one box = one picked unit). */
		int boxesPacked;

		/** The box HUs produced (one HU per box); size must equal {@link #boxesPacked}. */
		@NonNull @Singular ImmutableSet<HuId> packedHUIds;

		/** Number of labels printed successfully. */
		int labelsPrinted;

		/** Number of label print failures (labels not printed). */
		int labelPrintFailures;

		/** Units remaining on the LU after packing (leftover). */
		int unitsLeftOnLU;

		/** Units of open demand remaining after packing (demand that could not be fulfilled). */
		int unitsOfOpenDemandRemaining;
	}
}
