package de.metas.handlingunits.picking.job.service.commands.retrieve;

import de.metas.handlingunits.picking.job.model.PickingJobCandidateProduct;
import de.metas.handlingunits.picking.job.model.PickingJobCandidateProducts;
import de.metas.handlingunits.picking.job.model.ScheduledPackageable;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;

class ProductsCollector
{
	private final LinkedHashMap<ProductId, ProductCollector> productsById = new LinkedHashMap<>();

	public void collect(@NonNull final ScheduledPackageable item)
	{
		productsById.computeIfAbsent(item.getProductId(), k -> newProductCollector(item))
				.addQtyToDeliver(item.getQtyToDeliver());
	}

	private static ProductCollector newProductCollector(final @NotNull ScheduledPackageable item)
	{
		return ProductCollector.builder()
				.productId(item.getProductId())
				.productName(TranslatableStrings.anyLanguage(item.getProductName()))
				.build();
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
		@NonNull private final ITranslatableString productName;
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
					.productName(productName)
					.qtyToDeliver(qtyToDeliver)
					.build();
		}
	}
}
