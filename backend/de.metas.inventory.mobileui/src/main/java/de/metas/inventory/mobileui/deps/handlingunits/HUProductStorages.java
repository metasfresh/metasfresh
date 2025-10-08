package de.metas.inventory.mobileui.deps.handlingunits;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;
import java.util.Set;

public class HUProductStorages
{
	private final ImmutableMap<ProductId, IHUProductStorage> byProductId;

	HUProductStorages(final List<IHUProductStorage> productStorages)
	{
		this.byProductId = Maps.uniqueIndex(productStorages, IProductStorage::getProductId);
	}

	@NonNull
	public Quantity getQty(@NonNull final ProductId productId)
	{
		final IHUProductStorage huProductStorage = byProductId.get(productId);
		if (huProductStorage == null)
		{
			throw new AdempiereException("HU does not contain product")
					.setParameter("huProductStorages", byProductId.values())
					.setParameter("productId", productId);
		}
		return huProductStorage.getQty();
	}

	public Set<ProductId> getProductIds() {return byProductId.keySet();}
}
