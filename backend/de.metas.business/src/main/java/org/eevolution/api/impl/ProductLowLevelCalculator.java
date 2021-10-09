package org.eevolution.api.impl;

import de.metas.product.ProductId;
import de.metas.util.Services;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.exceptions.BOMCycleException;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;

import javax.annotation.Nullable;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Calculates product's low level code (LLC) and also checks for BOM cycles (it is throwing {@link BOMCycleException} in that case).
 * 
 * @author metas-dev <dev@metasfresh.com>
 */
/* package */class ProductLowLevelCalculator
{
	public static ProductLowLevelCalculator newInstance()
	{
		return new ProductLowLevelCalculator();
	}

	private final Set<ProductId> seenProductIds = new LinkedHashSet<>();

	private ProductLowLevelCalculator()
	{
	}

	/**
	 * Calculate the low level of given product
	 */
	public int getLowLevel(final ProductId productId)
	{
		clearSeenProducts();
		markProductAsSeen(productId);

		final DefaultMutableTreeNode ibom = createParentProductNode(productId); // start traversing tree
		return ibom.getDepth();
	}

	/**
	 * get an implotion the product
	 * 
	 * @return DefaultMutableTreeNode Tree with all parent product
	 */
	private DefaultMutableTreeNode createParentProductNode(final ProductId productId)
	{
		final DefaultMutableTreeNode productNode = new DefaultMutableTreeNode(productId);

		final IProductBOMDAO productBOMDAO = Services.get(IProductBOMDAO.class);
		final List<I_PP_Product_BOMLine> productBOMLines = productBOMDAO.retrieveBOMLinesByComponentIdInTrx(productId);

		boolean first = true;
		for (final I_PP_Product_BOMLine productBOMLine : productBOMLines)
		{
			// Don't navigate the Co/ByProduct lines (gh480)
			if(isByOrCoProduct(productBOMLine))
			{
				continue;
			}

			// If not the first bom line at this level
			if (!first)
			{
				clearSeenProducts();
				markProductAsSeen(productId);
			}
			first = false;

			final DefaultMutableTreeNode bomNode = createParentProductNodeForBOMLine(productBOMLine);
			if (bomNode != null)
			{
				productNode.add(bomNode);
			}
		}

		return productNode;
	}

	private static boolean isByOrCoProduct(final I_PP_Product_BOMLine bomLine)
	{
		final BOMComponentType componentType = BOMComponentType.ofCode(bomLine.getComponentType());
		return componentType.isByOrCoProduct();
	}

	@Nullable
	private DefaultMutableTreeNode createParentProductNodeForBOMLine(final I_PP_Product_BOMLine bomLine)
	{
		final I_PP_Product_BOM bom = bomLine.getPP_Product_BOM();
		if (!bom.isActive())
		{
			return null;
		}

		// Check Child = Parent error
		final ProductId productId = ProductId.ofRepoId(bomLine.getM_Product_ID());
		final ProductId parentProductId = ProductId.ofRepoId(bom.getM_Product_ID());
		if (productId.equals(parentProductId))
		{
			throw new BOMCycleException(bom, productId);
		}

		// Check BOM Loop Error
		if (!markProductAsSeen(parentProductId))
		{
			throw new BOMCycleException(bom, parentProductId);
		}

		return createParentProductNode(parentProductId);
	}

	private void clearSeenProducts()
	{
		seenProductIds.clear();
	}

	/** @return true if not already seen */
	private boolean markProductAsSeen(final ProductId productId)
	{
		return seenProductIds.add(productId);
	}
}
