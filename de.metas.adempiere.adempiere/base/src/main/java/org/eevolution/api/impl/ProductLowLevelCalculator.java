package org.eevolution.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.swing.tree.DefaultMutableTreeNode;

import org.adempiere.util.Services;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.exceptions.BOMCycleException;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;

/* package */class ProductLowLevelCalculator
{
	/**
	 * Map of M_Product_ID to PP_Product_BOM_ID.
	 */
	private Map<Integer, Integer> tableproduct = new HashMap<Integer, Integer>();
	private Properties m_ctx = null;
	private String m_trxName = null;

	public ProductLowLevelCalculator(final Properties ctx, final String trxName)
	{
		super();

		m_ctx = ctx;
		m_trxName = trxName;
	}

	/**
	 * get low level of a Product
	 * 
	 * @param ID Product
	 * @return int low level
	 */
	public int getLowLevel(int M_Product_ID)
	{
		// int AD_Client_ID = Env.getAD_Client_ID(m_ctx);
		tableproduct.clear(); // reset tableproduct cache
		DefaultMutableTreeNode ibom = null;

		final int productBOMId = 0;
		tableproduct.put(M_Product_ID, productBOMId); // insert parent into cache
		ibom = iparent(M_Product_ID, productBOMId); // start traversing tree

		return ibom.getDepth();
	}

	/**
	 * get an implotion the product
	 * 
	 * @param ID Product
	 * @param ID BOM
	 * @return DefaultMutableTreeNode Tree with all parent product
	 */
	private DefaultMutableTreeNode iparent(final int M_Product_ID, final int PP_Product_BOM_ID)
	{

		final DefaultMutableTreeNode parentNode = new DefaultMutableTreeNode(Integer.toString(M_Product_ID) + "|" + Integer.toString(PP_Product_BOM_ID));

		final Iterator<I_PP_Product_BOMLine> productBOMLines = Services.get(IProductBOMDAO.class)
				.retrieveBOMLinesForProductQuery(m_ctx, M_Product_ID, m_trxName)
				.iterate(I_PP_Product_BOMLine.class);

		boolean first = true;
		while (productBOMLines.hasNext())
		{
			final I_PP_Product_BOMLine bomLine = productBOMLines.next();

			// If not the first bom line at this level
			if (!first)
			{
				// need to reset tableproduct cache
				tableproduct.clear();
				tableproduct.put(M_Product_ID, PP_Product_BOM_ID); // insert parent into cache
			}
			first = false;

			final DefaultMutableTreeNode bomNode = icomponent(bomLine, M_Product_ID, parentNode);
			if (bomNode != null)
			{
				parentNode.add(bomNode);
			}
		}

		return parentNode;
	}

	/**
	 * get an implotion the product
	 * 
	 * @param ID Product
	 * @param ID BOM
	 * @return DefaultMutableTreeNode Tree with all parent product
	 */
	private DefaultMutableTreeNode icomponent(final I_PP_Product_BOMLine bomLine,
			final int M_Product_ID,
			final DefaultMutableTreeNode bomNode)
	{
		final I_PP_Product_BOM bom = bomLine.getPP_Product_BOM();
		if (!bom.isActive())
		{
			return null;
		}

		final int bomProductId = bom.getM_Product_ID();
		if (M_Product_ID != bomProductId)
		{
			// BOM Loop Error
			if (!tableproduct(bomProductId, bom.getPP_Product_BOM_ID()))
			{
				bomNode.add(iparent(bomProductId, bom.getPP_Product_BOM_ID()));
			}
			else
			{
				throw new BOMCycleException(bom, bom.getM_Product());
			}
		}
		else
		{
			// Child = Parent error
			throw new BOMCycleException(bom, bomLine.getM_Product());
		}

		return null;
	}

	/**
	 * find a product in cache
	 * 
	 * @param ID Product
	 * @param ID BOM
	 * @return true if product is found
	 */
	private boolean tableproduct(int M_Product_ID, int PP_Product_BOM_ID)
	{
		if (tableproduct.containsKey(M_Product_ID))
		{
			return true;
		}
		tableproduct.put(M_Product_ID, PP_Product_BOM_ID);
		return false;
	}
}
