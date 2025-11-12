package de.metas.handlingunits.picking.job.service.commands.retrieve;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.job.model.PickingJobCandidate;
import de.metas.handlingunits.picking.job.model.PickingJobCandidateProduct;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.warehouse.LocatorId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

@Builder
class ProductAvailableStocks
{
	// Services
	@NonNull final IHandlingUnitsBL handlingUnitsBL;

	// Params
	@NonNull final LocatorId pickFromLocatorId;

	// State
	@NonNull private final HashMap<ProductId, ProductAvailableStock> map = new HashMap<>();

	public void allocate(PickingJobCandidate job)
	{
		for (final PickingJobCandidateProduct product : job.getProducts())
		{
			final ProductId productId = product.getProductId();
			final Quantity qtyToDeliver = product.getQtyToDeliver();
			final Quantity qtyAvailableToPick = allocateQty(productId, qtyToDeliver).toZeroIfNegative();
			job.setQtyAvailableToPick(productId, qtyAvailableToPick);
		}
	}

	public Quantity allocateQty(final ProductId productId, final Quantity qty)
	{
		return getByProductId(productId).allocateQty(qty);
	}

	private ProductAvailableStock getByProductId(final ProductId productId)
	{
		return CollectionUtils.getOrLoad(map, productId, this::loadByProductIds);
	}

	public void warmUpByProductIds(final Set<ProductId> productIds)
	{
		CollectionUtils.getAllOrLoad(map, productIds, this::loadByProductIds);
	}

	private Map<ProductId, ProductAvailableStock> loadByProductIds(final Set<ProductId> productIds)
	{
		final HashMap<ProductId, ProductAvailableStock> result = new HashMap<>();
		productIds.forEach(productId -> result.put(productId, new ProductAvailableStock()));

		streamHUProductStorages(productIds)
				.forEach(huStorageProduct -> result.get(huStorageProduct.getProductId()).addQtyOnHand(huStorageProduct.getQty()));

		return result;
	}

	private Stream<IHUProductStorage> streamHUProductStorages(final Set<ProductId> productIds)
	{
		final List<I_M_HU> hus = handlingUnitsBL.createHUQueryBuilder()
				.addOnlyWithProductIds(productIds)
				.addOnlyInLocatorId(pickFromLocatorId)
				.setOnlyActiveHUs(true)
				.list();

		final IHUStorageFactory storageFactory = handlingUnitsBL.getStorageFactory();
		return storageFactory.streamHUProductStorages(hus);

	}
}
