package de.metas.manufacturing.issue.plan;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.plan.generator.pickFromHUs.HUsLoadingCache;
import de.metas.handlingunits.picking.plan.generator.pickFromHUs.PickFromHU;
import de.metas.handlingunits.picking.plan.generator.pickFromHUs.PickFromHUsGetRequest;
import de.metas.handlingunits.picking.plan.generator.pickFromHUs.PickFromHUsSupplier;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.product.ProductId;
import de.metas.quantity.QuantityUOMConverter;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

class AllocableHUsMap
{
	private final IHUStorageFactory storageFactory;
	private final QuantityUOMConverter uomConverter;
	private final PickFromHUsSupplier pickFromHUsSupplier;

	private final HashMultimap<ProductId, HuId> sourceHUs = HashMultimap.create();
	private final HashMap<AllocableHUsGroupingKey, AllocableHUsList> groups = new HashMap<>();
	private final HashMap<AllocableHUKey, AllocableHU> allocableHUs = new HashMap<>();

	@Builder
	private AllocableHUsMap(
			@NonNull final IHUStorageFactory storageFactory,
			@NonNull final QuantityUOMConverter uomConverter,
			@NonNull final PickFromHUsSupplier pickFromHUsSupplier)
	{
		this.storageFactory = storageFactory;
		this.uomConverter = uomConverter;
		this.pickFromHUsSupplier = pickFromHUsSupplier;
	}

	public Collection<AllocableHU> getAllAllocableHUsInvolved() {return allocableHUs.values();}

	public void addSourceHUs(@NonNull final Set<HuId> huIds)
	{
		if (huIds.isEmpty())
		{
			return;
		}

		for (final I_M_HU hu : pickFromHUsSupplier.getHusCache().getHUsByIds(huIds))
		{
			final HuId huId = HuId.ofRepoId(hu.getM_HU_ID());

			final ImmutableSet<ProductId> productIds = storageFactory
					.getStorage(hu)
					.getProductStorages()
					.stream()
					.map(IHUProductStorage::getProductId)
					.collect(ImmutableSet.toImmutableSet());

			for (final ProductId productId : productIds)
			{
				sourceHUs.put(productId, huId);
			}
		}
	}

	public AllocableHUsList getAllocableHUs(@NonNull final AllocableHUsGroupingKey key)
	{
		return groups.computeIfAbsent(key, this::retrieveAvailableHUsToPick);
	}

	private AllocableHUsList retrieveAvailableHUsToPick(@NonNull final AllocableHUsGroupingKey key)
	{
		final ShipmentAllocationBestBeforePolicy bestBeforePolicy = ShipmentAllocationBestBeforePolicy.Expiring_First;
		final ProductId productId = key.getProductId();

		final ImmutableList<PickFromHU> husEligibleToPick;

		final Set<HuId> productSourceHUs = sourceHUs.get(productId);
		if (!productSourceHUs.isEmpty())
		{
			husEligibleToPick = productSourceHUs.stream()
					.map(pickFromHUsSupplier::createPickFromHUByTopLevelHUId)
					.sorted(PickFromHUsSupplier.getAllocationOrder(bestBeforePolicy))
					.collect(ImmutableList.toImmutableList());
		}
		else
		{
			husEligibleToPick = pickFromHUsSupplier.getEligiblePickFromHUs(
					PickFromHUsGetRequest.builder()
							.pickFromLocatorId(key.getPickFromLocatorId())
							.productId(productId)
							.asiId(AttributeSetInstanceId.NONE) // TODO match attributes
							.bestBeforePolicy(bestBeforePolicy)
							.reservationRef(Optional.empty()) // TODO introduce some PP Order reservation
							.enforceMandatoryAttributesOnPicking(false)
							.build());
		}

		final ImmutableList<AllocableHU> hus = CollectionUtils.map(husEligibleToPick, hu -> toAllocableHU(hu.getTopLevelHUId(), productId));
		return new AllocableHUsList(hus);
	}

	private AllocableHU toAllocableHU(@NonNull final HuId huId, @NonNull final ProductId productId)
	{
		final AllocableHUKey key = AllocableHUKey.of(huId, productId);
		return allocableHUs.computeIfAbsent(key, this::createAllocableHU);
	}

	private AllocableHU createAllocableHU(@NonNull final AllocableHUKey key)
	{
		final HUsLoadingCache husCache = pickFromHUsSupplier.getHusCache();
		final I_M_HU topLevelHU = husCache.getHUById(key.getTopLevelHUId());
		return new AllocableHU(storageFactory, uomConverter, topLevelHU, key.getProductId());
	}

	@Value(staticConstructor = "of")
	private static class AllocableHUKey
	{
		@NonNull HuId topLevelHUId;
		@NonNull ProductId productId;
	}
}
