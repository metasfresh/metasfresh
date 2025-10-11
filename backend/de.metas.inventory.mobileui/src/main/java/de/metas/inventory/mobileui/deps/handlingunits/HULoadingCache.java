package de.metas.inventory.mobileui.deps.handlingunits;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.warehouse.LocatorId;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Set;

@Builder(access = AccessLevel.PACKAGE)
public class HULoadingCache
{
	@NonNull private final HandlingUnitsService huService;

	private final HashMap<HuId, LocatorId> huLocatorsByHUId = new HashMap<>();
	private final HashMap<HuId, I_M_HU> husById = new HashMap<>();
	private final HashMap<HuId, HUProductStorages> productStoragesByHUId = new HashMap<>();
	private final HashMap<HuId, ImmutableAttributeSet> attributesByHUId = new HashMap<>();

	public I_M_HU getHUById(final @NotNull HuId huId)
	{
		return husById.computeIfAbsent(huId, huService::getById);
	}

	@NonNull
	public Set<ProductId> getProductIds(final I_M_HU hu)
	{
		return getProductStorages(hu).getProductIds();
	}

	@NonNull
	public Quantity getQty(final I_M_HU hu, final ProductId productId)
	{
		return getProductStorages(hu).getQty(productId);
	}

	private HUProductStorages getProductStorages(final I_M_HU hu)
	{
		return productStoragesByHUId.computeIfAbsent(HuId.ofRepoId(hu.getM_HU_ID()), huId -> huService.getProductStorages(hu));
	}

	public ImmutableAttributeSet getAttributes(final I_M_HU hu)
	{
		return attributesByHUId.computeIfAbsent(HuId.ofRepoId(hu.getM_HU_ID()), huId -> huService.getImmutableAttributeSet(hu));
	}

	public LocatorId getLocatorId(@NonNull final I_M_HU hu)
	{
		return huLocatorsByHUId.computeIfAbsent(HuId.ofRepoId(hu.getM_HU_ID()), huId -> IHandlingUnitsBL.extractLocatorId(hu));
	}
}
