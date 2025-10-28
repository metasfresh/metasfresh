package de.metas.inventory.mobileui.deps.handlingunits;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.inventory.mobileui.deps.products.Attributes;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
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
	private final HashMap<HuId, Attributes> attributesByHUId = new HashMap<>();
	private final HashMap<HuId, String> displayNamesByHUId = new HashMap<>();

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

	public Attributes getAttributes(final I_M_HU hu)
	{
		return attributesByHUId.computeIfAbsent(HuId.ofRepoId(hu.getM_HU_ID()), huId -> huService.getImmutableAttributeSet(hu));
	}

	public LocatorId getLocatorId(@NonNull final I_M_HU hu)
	{
		return huLocatorsByHUId.computeIfAbsent(HuId.ofRepoId(hu.getM_HU_ID()), huId -> IHandlingUnitsBL.extractLocatorId(hu));
	}

	public String getDisplayName(@NonNull final HuId huId)
	{
		return displayNamesByHUId.computeIfAbsent(huId, this::retrieveDisplayName);
	}

	private String retrieveDisplayName(final HuId huId)
	{
		return huService.getDisplayName(getHUById(huId));
	}
}
