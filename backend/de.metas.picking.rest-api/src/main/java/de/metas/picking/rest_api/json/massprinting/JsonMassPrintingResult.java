package de.metas.picking.rest_api.json.massprinting;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.picking.job.massprinting.MassPrintingResult;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

/**
 * Response body from the mass-printing scan endpoint.
 * Contains per-product results for every self-packed product that was processed,
 * and the list of non-self-packed products that were skipped.
 */
@Value
@Builder
@Jacksonized
public class JsonMassPrintingResult
{
	/**
	 * Per-product results. One entry per self-packed product on the scanned LU
	 * that had open demand. Empty when no self-packed product with open demand was found.
	 */
	@NonNull @Singular ImmutableList<JsonMassPrintingProductResult> productResults;

	/**
	 * Product IDs of non-{@code IsSelfPacked} products on the scanned LU that were skipped.
	 * Products in this list were not picked.
	 */
	@NonNull @Singular ImmutableList<Integer> skippedNonSelfPackedProductIds;

	/**
	 * Converts a domain {@link MassPrintingResult} to its JSON representation.
	 *
	 * @param result domain result (never null)
	 * @return JSON result ready to return from the REST endpoint
	 */
	@NonNull
	public static JsonMassPrintingResult of(@NonNull final MassPrintingResult result)
	{
		final List<JsonMassPrintingProductResult> productResults = result.getProductResults().stream()
				.map(pr -> JsonMassPrintingProductResult.builder()
						.productId(pr.getProductId().getRepoId())
						.boxesPacked(pr.getBoxesPacked())
						.labelsPrinted(pr.getLabelsPrinted())
						.labelPrintFailures(pr.getLabelPrintFailures())
						.unitsLeftOnLU(pr.getUnitsLeftOnLU())
						.unitsOfOpenDemandRemaining(pr.getUnitsOfOpenDemandRemaining())
						.build())
				.collect(ImmutableList.toImmutableList());

		final List<Integer> skippedIds = result.getSkippedNonSelfPackedProductIds().stream()
				.map(ProductId::getRepoId)
				.collect(ImmutableList.toImmutableList());

		return JsonMassPrintingResult.builder()
				.productResults(productResults)
				.skippedNonSelfPackedProductIds(skippedIds)
				.build();
	}
}
