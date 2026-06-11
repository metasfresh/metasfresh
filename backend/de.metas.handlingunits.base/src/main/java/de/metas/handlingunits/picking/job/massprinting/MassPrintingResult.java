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
	public static final MassPrintingResult EMPTY = MassPrintingResult.builder().build();

	@NonNull @Singular ImmutableList<ProductResult> productResults;

	/** Products on the LU that were skipped because they are not IsSelfPacked. */
	@NonNull @Singular ImmutableList<ProductId> skippedNonSelfPackedProductIds;

	@Value
	@Builder
	public static class ProductResult
	{
		@NonNull ProductId productId;

		/** Number of product units packed (in product UOM). */
		int unitsPacked;

		/**
		 * The shippable HUs produced — one per picked unit, regardless of packing-instruction type
		 * (one VHU per unit for the Virtual/null-PI path; one TU box per unit for finite-PI).
		 * Size equals {@link #unitsPacked}.
		 */
		@NonNull @Singular ImmutableSet<HuId> packedHUIds;

		/** Units remaining on the LU after packing (leftover). */
		int unitsLeftOnLU;

		/** Units of open demand remaining after packing (demand that could not be fulfilled). */
		int unitsOfOpenDemandRemaining;
	}
}
