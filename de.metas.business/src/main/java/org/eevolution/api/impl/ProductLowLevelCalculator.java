package org.eevolution.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.exceptions.BOMCycleException;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.eevolution.model.X_PP_Product_BOMLine;

/**
 * Calculates product's low level code (LLC) and also checks for BOM cycles (it is throwing {@link BOMCycleException} in that case).
 * 
 * @author metas-dev <dev@metasfresh.com>
 */
/* package */class ProductLowLevelCalculator
{
	public static final ProductLowLevelCalculator newInstance()
	{
		return new ProductLowLevelCalculator();
	}

	private final Set<Integer> seenProductIds = new LinkedHashSet<>();

	private ProductLowLevelCalculator()
	{
	}

	/**
	 * Calculate the low level of given product
	 */
	public int getLowLevel(final int productId)
	{
		clearSeenProducts();
		markProductAsSeen(productId);

		final DefaultMutableTreeNode ibom = createParentProductNode(productId); // start traversing tree
		return ibom.getDepth();
	}

	/**
	 * get an implotion the product
	 * 
	 * @param ID Product
	 * @param ID BOM
	 * @return DefaultMutableTreeNode Tree with all parent product
	 */
	private DefaultMutableTreeNode createParentProductNode(final int productId)
	{
		final DefaultMutableTreeNode productNode = new DefaultMutableTreeNode(productId);

		final List<I_PP_Product_BOMLine> productBOMLines = Services.get(IProductBOMDAO.class)
				.retrieveBOMLinesForProductQuery(Env.getCtx(), productId, ITrx.TRXNAME_ThreadInherited)
				.list();

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

	private static final boolean isByOrCoProduct(final I_PP_Product_BOMLine bomLine)
	{
		final String componentType = bomLine.getComponentType();
		return X_PP_Product_BOMLine.COMPONENTTYPE_By_Product.equals(componentType)
				|| X_PP_Product_BOMLine.COMPONENTTYPE_Co_Product.equals(componentType);
	}

	private DefaultMutableTreeNode createParentProductNodeForBOMLine(final I_PP_Product_BOMLine bomLine)
	{
		final I_PP_Product_BOM bom = bomLine.getPP_Product_BOM();
		if (!bom.isActive())
		{
			return null;
		}

		// Check Child = Parent error
		final int productId = bomLine.getM_Product_ID();
		final int parentProductId = bom.getM_Product_ID();
		if (productId == parentProductId)
		{
			throw new BOMCycleException(bom, load(productId, I_M_Product.class));
		}

		// Check BOM Loop Error
		if (!markProductAsSeen(parentProductId))
		{
			throw new BOMCycleException(bom, load(parentProductId, I_M_Product.class));
		}

		return createParentProductNode(parentProductId);
	}

	private void clearSeenProducts()
	{
		seenProductIds.clear();
	}

	/** @return true if not already seen */
	private boolean markProductAsSeen(final int productId)
	{
		return seenProductIds.add(productId);
	}
}
