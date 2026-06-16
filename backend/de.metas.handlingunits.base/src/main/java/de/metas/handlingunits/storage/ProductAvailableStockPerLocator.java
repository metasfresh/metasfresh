package de.metas.handlingunits.storage;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.NonNull;
import org.adempiere.warehouse.LocatorId;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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

	/** Convenience overload accepting any {@link Collection} of locator ids (copied to an immutable set). */
	public ProductQtyOnHandByLocator getQtyOnHandByLocator(
			@NonNull final ProductId productId,
			@NonNull final Collection<LocatorId> locatorIds)
	{
		return getQtyOnHandByLocator(productId, ImmutableSet.copyOf(locatorIds));
	}

	public ProductQtyOnHandByLocator getQtyOnHandByLocator(
			@NonNull final ProductId productId,
			@NonNull final Set<LocatorId> locatorIds)
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

		final HashMap<LocatorId, Quantity> qtyByLocator = new HashMap<>();
		storageFactory.streamHUProductStorages(hus)
				.filter(huStorageProduct -> productId.equals(huStorageProduct.getProductId()))
				.forEach(huStorageProduct -> {
					final LocatorId locatorId = IHandlingUnitsBL.extractLocatorId(huStorageProduct.getM_HU());
					final Quantity qty = huStorageProduct.getQtyInStockingUOM();
					qtyByLocator.merge(locatorId, qty, Quantity::add);
				});

		return ProductQtyOnHandByLocator.ofMap(qtyByLocator);
	}
}
