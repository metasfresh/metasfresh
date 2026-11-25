package de.metas.handlingunits.allocation.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Loads TUs onto new LUs, choosing each TU's LU item by the TU's product.
 * <p>
 * Grouping rule:
 * <ul>
 * <li>A TU whose LU item resolves to a <b>product-specific</b> item (e.g. 40 TUs of product XY per pallet) goes onto new LUs of that item;
 * each such item gets its own LUs, filled up to its capacity.</li>
 * <li>All other TUs (product without its own item, or a TU holding several products) share the LUs of the generic item, mixed as {@link LULoader} does.</li>
 * </ul>
 * Why: a product-specific LU item states how many TUs of <i>that</i> product fit on the pallet, so its TUs must not be mixed with other products' TUs
 * on it; the generic item is shared by all products, as before.
 * The TUs keep their order within each group, so the product-specific LUs and the number of LUs do not depend on how the products are interleaved;
 * which product lands on which generic LU does.
 */
public class TUsToNewLUsLoader
{
	@NonNull private final IHUContext huContext;

	private LULoader sharedLoader;
	/**
	 * One loader per product-specific LU item (not per product): a product can have items for several TU packings, and an LU built for one
	 * of them must not take TUs of the other one (it would hang them on that TU packing's generic item).
	 */
	private final Map<Integer, LULoader> productSpecificLoaders = new LinkedHashMap<>();

	@Builder
	private TUsToNewLUsLoader(@NonNull final IHUContext huContext)
	{
		this.huContext = huContext;
	}

	/**
	 * @return true if the TU is a TU whose packing instruction has an LU item for the TU's product (or a generic one)
	 */
	public static boolean isPackToLUAllowed(@NonNull final I_M_HU tu)
	{
		final I_M_HU_PI_Version piVersion = tu.getM_HU_PI_Version();
		if (piVersion == null
				|| piVersion.getHU_UnitType() != null && !X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit.equals(piVersion.getHU_UnitType()))
		{
			return false;
		}

		return retrieveLUItem(tu, getSingleProductIdOrNull(tu)) != null;
	}

	/**
	 * @param tus top-level TUs for which {@link #isPackToLUAllowed(I_M_HU)} is true; callers filter with it first
	 *            (a TU without packing instruction version or without an LU item cannot be loaded)
	 */
	public void addTUs(@NonNull final Collection<I_M_HU> tus)
	{
		tus.forEach(this::addTU);
	}

	private void addTU(@NonNull final I_M_HU tu)
	{
		final ProductId productId = getSingleProductIdOrNull(tu);
		final I_M_HU_PI_Item luItem = productId != null ? retrieveLUItem(tu, productId) : null;
		if (luItem != null && luItem.getM_Product_ID() > 0)
		{
			productSpecificLoaders.computeIfAbsent(luItem.getM_HU_PI_Item_ID(), ignored -> new LULoader(huContext))
					.addTU(tu, productId);
		}
		else
		{
			getSharedLoader().addTU(tu);
		}
	}

	private LULoader getSharedLoader()
	{
		if (sharedLoader == null)
		{
			sharedLoader = new LULoader(huContext);
		}
		return sharedLoader;
	}

	public void close()
	{
		getAllLoaders().forEach(LULoader::close);
	}

	public List<I_M_HU> getLU_HUs()
	{
		final List<I_M_HU> lus = new ArrayList<>();
		getAllLoaders().forEach(loader -> lus.addAll(loader.getLU_HUs()));
		return lus;
	}

	private List<LULoader> getAllLoaders()
	{
		final List<LULoader> loaders = new ArrayList<>();
		if (sharedLoader != null)
		{
			loaders.add(sharedLoader);
		}
		loaders.addAll(productSpecificLoaders.values());
		return loaders;
	}

	@Nullable
	private static ProductId getSingleProductIdOrNull(@NonNull final I_M_HU tu)
	{
		return Services.get(IHandlingUnitsBL.class).getStorageFactory().getStorage(tu).getSingleProductIdOrNull();
	}

	@Nullable
	private static I_M_HU_PI_Item retrieveLUItem(@NonNull final I_M_HU tu, @Nullable final ProductId productId)
	{
		final BPartnerId bpartnerId = IHandlingUnitsBL.extractBPartnerIdOrNull(tu);
		return Services.get(IHandlingUnitsDAO.class).retrieveDefaultParentPIItem(
				tu.getM_HU_PI_Version().getM_HU_PI(),
				X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit,
				bpartnerId,
				productId);
	}
}
