package de.metas.ui.web.material.cockpit;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.product.Product;
import de.metas.product.ProductId;
import de.metas.product.ProductRepository;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.warehouse.Warehouse;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.WarehouseRepository;
import org.compiere.model.I_C_UOM;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Builder
public class MaterialCockpitRowCache
{
	@NonNull private final ProductRepository productRepository;
	@NonNull private final WarehouseRepository warehouseRepository;
	@NonNull private final IUOMDAO uomDAO;

	private final HashMap<ProductId, Product> productsCache = new HashMap<>();
	private final HashMap<UomId, I_C_UOM> uomsCache = new HashMap<>();

	public void warmUpProducts(final Set<ProductId> productIds)
	{
		final Collection<Product> products = CollectionUtils.getAllOrLoad(productsCache, productIds, this::retrieveProductsByIds);

		final ImmutableSet<UomId> uomIds = products.stream().map(Product::getUomId).collect(ImmutableSet.toImmutableSet());
		warmUpUOMs(uomIds);
	}

	public Product getProductById(final ProductId productId)
	{
		return productsCache.computeIfAbsent(productId, this::retrieveProductById);
	}

	public Product retrieveProductById(@NonNull final ProductId productId)
	{
		return productRepository.getById(productId);
	}

	public Map<ProductId, Product> retrieveProductsByIds(final Set<ProductId> productIds)
	{
		final ImmutableList<Product> products = productRepository.getByIds(productIds);
		return Maps.uniqueIndex(products, Product::getId);
	}

	public I_C_UOM getUomById(final UomId uomId)
	{
		return uomsCache.computeIfAbsent(uomId, uomDAO::getById);
	}

	public I_C_UOM getUomByProductId(final ProductId productId)
	{
		final UomId uomId = getProductById(productId).getUomId();
		return getUomById(uomId);
	}


	private void warmUpUOMs(final Collection<UomId> uomIds)
	{
		if (uomIds.isEmpty())
		{
			return;
		}

		CollectionUtils.getAllOrLoad(uomsCache, uomIds, this::retrieveUOMsByIds);
	}

	private Map<UomId, I_C_UOM> retrieveUOMsByIds(final Collection<UomId> uomIds)
	{
		final List<I_C_UOM> uoms = uomDAO.getByIds(uomIds);
		return Maps.uniqueIndex(uoms, uom -> UomId.ofRepoId(uom.getC_UOM_ID()));
	}

	public ImmutableSet<WarehouseId> getAllActiveWarehouseIds()
	{
		return warehouseRepository.getAllActiveIds();
	}

	public Warehouse getWarehouseById(@NonNull final WarehouseId warehouseId)
	{
		return warehouseRepository.getById(warehouseId);
	}

}
