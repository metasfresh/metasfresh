package de.metas.handlingunits.picking.job.model;

import com.google.common.collect.ImmutableSet;
import de.metas.order.OrderAndLineId;
import de.metas.product.ProductId;
import de.metas.product.ProductValueAndName;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class PickingJobCandidateProductsCollector
{
	private final LinkedHashMap<ProductId, ProductCollector> productsById = new LinkedHashMap<>();

	public void collect(@NonNull final ScheduledPackageable item)
	{
		collectAndGet(item.getProductId(),
				() -> item.getProductValueAndName(),
				item.getQtyToDeliver())
				.addSalesOrderAndLineId(item.getSalesOrderAndLineIdOrNull());
	}

	public void collect(
			@NonNull final ProductId productId,
			@NonNull final Supplier<ProductValueAndName> productValueAndName,
			@NonNull final Quantity qtyToDeliver)
	{
		collectAndGet(productId, productValueAndName, qtyToDeliver);
	}

	private ProductCollector collectAndGet(
			@NonNull final ProductId productId,
			@NonNull final Supplier<ProductValueAndName> productValueAndName,
			@NonNull final Quantity qtyToDeliver)
	{
		final ProductCollector productCollector = productsById.computeIfAbsent(productId, k -> ProductCollector.builder()
				.productId(productId)
				.productValueAndName(productValueAndName.get())
				.build());
		productCollector.addQtyToDeliver(qtyToDeliver);
		return productCollector;
	}

	/**
	 * @return the products in the order they were collected
	 */
	public PickingJobCandidateProducts toProducts()
	{
		return productsById.values()
				.stream()
				.map(ProductCollector::toProduct)
				.collect(PickingJobCandidateProducts.collect());
	}

	/**
	 * @return the sales order lines of all collected items, so the caller can batch-load their SeqNos before calling {@link #toProductsOrderedBySalesOrderLine(ToIntFunction)}
	 */
	public ImmutableSet<OrderAndLineId> getSalesOrderAndLineIds()
	{
		return productsById.values()
				.stream()
				.flatMap(productCollector -> productCollector.getSalesOrderAndLineIds().stream())
				.collect(ImmutableSet.toImmutableSet());
	}

	/**
	 * @return the products ordered by their first sales order line (C_OrderLine.Line, tie-broken by the order line ID),
	 * i.e. the same order an already started picking job uses for its product names (see PickingJobLoaderAndSaver.extractProducts).
	 * Products without a sales order line come last, in the order they were collected.
	 */
	public PickingJobCandidateProducts toProductsOrderedBySalesOrderLine(@NonNull final ToIntFunction<OrderAndLineId> salesOrderLineSeqNoProvider)
	{
		final Comparator<OrderAndLineId> salesOrderLineComparator = Comparator.<OrderAndLineId>comparingInt(salesOrderLineSeqNoProvider)
				.thenComparingInt(orderAndLineId -> orderAndLineId.getOrderLineId().getRepoId());

		return productsById.values()
				.stream()
				.sorted(Comparator.comparing(
						(ProductCollector productCollector) -> productCollector.getFirstSalesOrderAndLineId(salesOrderLineComparator).orElse(null),
						Comparator.nullsLast(salesOrderLineComparator)))
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
		@NonNull private final HashSet<OrderAndLineId> salesOrderAndLineIds = new HashSet<>();

		public void addQtyToDeliver(@NonNull final Quantity qtyToAdd)
		{
			this.qtyToDeliver = this.qtyToDeliver == null
					? qtyToAdd
					: this.qtyToDeliver.add(qtyToAdd);
		}

		public void addSalesOrderAndLineId(@Nullable final OrderAndLineId salesOrderAndLineId)
		{
			if (salesOrderAndLineId != null)
			{
				salesOrderAndLineIds.add(salesOrderAndLineId);
			}
		}

		public Optional<OrderAndLineId> getFirstSalesOrderAndLineId(@NonNull final Comparator<OrderAndLineId> salesOrderLineComparator)
		{
			return salesOrderAndLineIds.stream().min(salesOrderLineComparator);
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
