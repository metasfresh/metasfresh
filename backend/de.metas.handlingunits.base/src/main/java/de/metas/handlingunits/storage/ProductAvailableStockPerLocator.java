package de.metas.handlingunits.storage;

import com.google.common.collect.Lists;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.NonNull;
import org.adempiere.warehouse.LocatorId;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

/**
 * Returns the Active on-hand quantity of a product, grouped per locator.
 * <p>
 * This is the per-locator counterpart of
 * {@link de.metas.handlingunits.picking.job.service.external.hu.ProductAvailableStocks},
 * which sums the on-hand qty per product (not per locator).
 */
public class ProductAvailableStockPerLocator
{
	@NonNull private final IHandlingUnitsBL handlingUnitsBL;

	private ProductAvailableStockPerLocator(@NonNull final IHandlingUnitsBL handlingUnitsBL)
	{
		this.handlingUnitsBL = handlingUnitsBL;
	}

	public static ProductAvailableStockPerLocator newInstance(@NonNull final IHandlingUnitsBL handlingUnitsBL)
	{
		return new ProductAvailableStockPerLocator(handlingUnitsBL);
	}

	public ProductQtyOnHandByLocator getQtyOnHandByLocator(
			@NonNull final ProductId productId,
			@NonNull final Collection<LocatorId> locatorIds)
	{
		if (locatorIds.isEmpty())
		{
			return ProductQtyOnHandByLocator.EMPTY;
		}

		final List<I_M_HU> hus = handlingUnitsBL.createHUQueryBuilder()
				.onlyContextClient(false) // fails when running from non-context threads like websockets value producers
				.addOnlyWithProductId(productId)
				.addOnlyInLocatorIds(locatorIds)
				.setOnlyActiveHUs(true)
				.setExcludeAfterPickingLocator(true)
				.list();

		final IHUStorageFactory storageFactory = handlingUnitsBL.getStorageFactory();
		final HashMap<LocatorId, Quantity> result = new HashMap<>();
		storageFactory.streamHUProductStorages(hus)
				.filter(huStorageProduct -> productId.equals(huStorageProduct.getProductId()))
				.forEach(huStorageProduct -> {
					final LocatorId locatorId = IHandlingUnitsBL.extractLocatorId(huStorageProduct.getM_HU());
					final Quantity qty = huStorageProduct.getQtyInStockingUOM();
					result.merge(locatorId, qty, Quantity::add);
				});

		return ProductQtyOnHandByLocator.ofMap(result);
	}

	/**
	 * Lazily streams the locators that have positive on-hand stock for the given product, paired with
	 * their qty (product stocking UOM), preserving the iteration order of {@code orderedLocatorIds}.
	 * <p>
	 * Each call to the underlying HU query covers at most {@code chunkSize} locators; subsequent chunks
	 * are loaded only when the consumer keeps pulling from the stream. Pair with {@link Stream#findFirst()}
	 * or with {@code stream().iterator()} + an early-exit loop to short-circuit once the caller has enough.
	 * Callers tune {@code chunkSize} to their access pattern — small for find-first lookups (a single hit
	 * usually fits in one chunk), larger for cumulative consumers (fewer round-trips when many locators
	 * contribute).
	 */
	public Stream<LocatorIdAndQty> streamLocatorQtyOnHandOrdered(
			@NonNull final ProductId productId,
			final int chunkSize,
			@NonNull final List<LocatorId> orderedLocatorIds)
	{
		if (orderedLocatorIds.isEmpty())
		{
			return Stream.empty();
		}

		return Lists.partition(orderedLocatorIds, chunkSize)
				.stream()
				.flatMap(chunk -> {
					// Side-effecting load inside flatMap is intentional: it's what makes the chunked
					// stream lazy — chunk N's HU query fires only when the consumer pulls past chunk N-1.
					final ProductQtyOnHandByLocator qtyByLocator = getQtyOnHandByLocator(productId, chunk);
					return chunk.stream()
							.filter(qtyByLocator::hasStock)
							.map(locatorId -> LocatorIdAndQty.of(locatorId, qtyByLocator.getQty(locatorId)));
				});
	}

}
