package de.metas.product;

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

import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;

public interface IProductDAO extends ISingletonService
{
	I_M_Product retrieveProductByUPC(Properties ctx, String upc);

	/**
	 *
	 * @param ctx
	 * @return default product category; never returns null
	 */
	I_M_Product_Category retrieveDefaultProductCategory(Properties ctx);

	/**
	 *
	 *
	 * @param product
	 * @param org
	 * @return the product of the given <code>org</code> that is mapped to the given <code>product</code> or <code>null</code> if the given product references no mapping, or the mapping is not active
	 *         or if there is no pendant in the given <code>org</code>.
	 * @task http://dewiki908/mediawiki/index.php/09700_Counter_Documents_%28100691234288%29
	 */
	I_M_Product retrieveMappedProductOrNull(I_M_Product product, I_AD_Org org);

	/**
	 * Retrieve all the products from all the organizations that have the same mapping as the given product
	 *
	 * @param product
	 * @return list of the products if found, empty list otherwise
	 */
	List<de.metas.product.model.I_M_Product> retrieveAllMappedProducts(I_M_Product product);

	I_M_Product retrieveProductByValue(Properties ctx, String value);

	int retrieveProductIdByValue(String value);
}
