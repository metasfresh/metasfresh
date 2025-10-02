package de.metas.inventory.mobileui.job.repository;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.i18n.IModelTranslationMap;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Product;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

class Products
{
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	
	private final HashMap<ProductId, ProductInfo> byId = new HashMap<>();

	public <T> void warnUp(final Collection<T> objects, Function<T, ProductId> productIdMapper)
	{
		final ImmutableSet<ProductId> productIds = objects.stream().map(productIdMapper).collect(ImmutableSet.toImmutableSet());
		getByIds(productIds);
	}

	public ProductInfo getById(@NonNull final ProductId productId)
	{
		final Collection<ProductInfo> productInfos = getByIds(ImmutableSet.of(productId));
		return CollectionUtils.singleElement(productInfos);
	}

	private Collection<ProductInfo> getByIds(final Set<ProductId> productIds)
	{
		return CollectionUtils.getAllOrLoad(byId, productIds, this::retrieveProductInfos);
	}

	private Map<ProductId, ProductInfo> retrieveProductInfos(final Set<ProductId> productIds)
	{
		return productDAO.getByIds(productIds)
				.stream()
				.map(Products::fromRecord)
				.collect(ImmutableMap.toImmutableMap(ProductInfo::getProductId, productInfo -> productInfo));
	}

	private static ProductInfo fromRecord(final I_M_Product product)
	{
		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());
		final IModelTranslationMap trls = InterfaceWrapperHelper.getModelTranslationMap(product);

		return ProductInfo.builder()
				.productId(productId)
				.productNo(product.getValue())
				.productName(trls.getColumnTrl(I_M_Product.COLUMNNAME_Name, product.getName()))
				.build();
	}
}
