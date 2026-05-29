package de.metas.handlingunits.picking.job.model;

import de.metas.product.ProductId;
import de.metas.product.ProductValueAndName;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.function.Supplier;

public class PickingJobCandidateProductsCollector
{
	private final LinkedHashMap<ProductId, ProductCollector> productsById = new LinkedHashMap<>();

	public void collect(@NonNull final ScheduledPackageable item)
	{
		collect(item.getProductId(),
				() -> item.getProductValueAndName(),
				item.getQtyToDeliver());
	}

	public void collect(
			@NonNull final ProductId productId,
			@NonNull final Supplier<ProductValueAndName> productValueAndName,
			@NonNull final Quantity qtyToDeliver)
	{
		productsById.computeIfAbsent(productId, k -> ProductCollector.builder()
						.productId(productId)
						.productValueAndName(productValueAndName.get())
						.build())
				.addQtyToDeliver(qtyToDeliver);
	}

	public PickingJobCandidateProducts toProducts()
	{
		return productsById.values()
				.stream()
				.map(ProductCollector::toProduct)
				.collect(PickingJobCandidateProducts.collect());
	}

	//
	//
	//
	//
	//

	@Data
	@Builder
	private static class ProductCollector
	{
		@NonNull private final ProductId productId;
		@NonNull private final ProductValueAndName productValueAndName;
		@Nullable private Quantity qtyToDeliver;

		public void addQtyToDeliver(@NonNull final Quantity qtyToAdd)
		{
			this.qtyToDeliver = this.qtyToDeliver == null
					? qtyToAdd
					: this.qtyToDeliver.add(qtyToAdd);
		}

		public PickingJobCandidateProduct toProduct()
		{
			return PickingJobCandidateProduct.builder()
					.productId(productId)
					.productValueAndName(productValueAndName)
					.qtyToDeliver(qtyToDeliver)
					.build();
		}
	}
}
