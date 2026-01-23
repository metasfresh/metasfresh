package de.metas.handlingunits.picking.job.service.external.hu;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.job.model.PickingJobCandidate;
import de.metas.handlingunits.picking.job.model.PickingJobCandidateProduct;
import de.metas.handlingunits.picking.job.model.PickingJobCandidateProducts;
import de.metas.handlingunits.picking.job.model.PickingJobReference;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.collections.CollectionUtils;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.warehouse.LocatorId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class ProductAvailableStocks
{
	// Services
	@NonNull final IHandlingUnitsBL handlingUnitsBL;

	// Params
	@NonNull final ImmutableSet<LocatorId> pickFromLocatorIds;

	// State
	@NonNull private final HashMap<ProductId, ProductAvailableStock> map = new HashMap<>();

	@Builder(access = AccessLevel.PACKAGE)
	private ProductAvailableStocks(
			@NonNull final IHandlingUnitsBL handlingUnitsBL,
			@NonNull final Set<LocatorId> pickFromLocatorIds)
	{
		Check.assumeNotEmpty(pickFromLocatorIds, "pickFromLocatorIds shall not be empty");

		this.handlingUnitsBL = handlingUnitsBL;
		this.pickFromLocatorIds = ImmutableSet.copyOf(pickFromLocatorIds);
	}

	public PickingJobCandidate allocate(PickingJobCandidate job)
	{
		final PickingJobCandidateProducts products = job.getProducts()
				.updatingEachProduct(this::allocate);

		return job.withProducts(products);
	}

	public PickingJobReference allocate(PickingJobReference job)
	{
		final PickingJobCandidateProducts products = job.getProducts()
				.updatingEachProduct(this::allocate);

		return job.withProducts(products);
	}

	private PickingJobCandidateProduct allocate(final PickingJobCandidateProduct product)
	{
		final Quantity qtyToDeliver = product.getQtyToDeliver();
		if (qtyToDeliver == null)
		{
			return product;
		}

		final ProductId productId = product.getProductId();
		final Quantity qtyAvailableToPick = allocateQty(productId, qtyToDeliver);

		return product.withQtyAvailableToPick(qtyAvailableToPick);
	}

	public Quantity allocateQty(@NonNull final ProductId productId, @NonNull final Quantity qtyToDeliver)
	{
		return getByProductId(productId)
				.allocateQty(qtyToDeliver)
				.toZeroIfNegative();
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
				.onlyContextClient(false) // fails when running from non-context threads like websockets value producers
				.addOnlyWithProductIds(productIds)
				.addOnlyInLocatorIds(pickFromLocatorIds)
				.setOnlyActiveHUs(true)
				.list();

		final IHUStorageFactory storageFactory = handlingUnitsBL.getStorageFactory();
		return storageFactory.streamHUProductStorages(hus);

	}
}
