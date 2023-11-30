package de.metas.ui.web.material.cockpit.filters;

import org.compiere.model.I_M_Product;

import de.metas.material.cockpit.model.I_MD_Cockpit;
import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Value
@Builder
public class ProductFilterVO
{
	public static final ProductFilterVO EMPTY = builder().build();

	public static final String FILTER_ID = "materialCockpitAllParamsFilter";

	public static final String PARAM_ProductValue = I_MD_Cockpit.COLUMNNAME_ProductValue;
	String productValue;

	public static final String PARAM_ProductName = I_MD_Cockpit.COLUMNNAME_ProductName;
	String productName;

	public static final String PARAM_M_Product_ID = I_M_Product.COLUMNNAME_M_Product_ID;
	int productId;

	public static final String PARAM_M_Product_Category_ID = I_M_Product.COLUMNNAME_M_Product_Category_ID;
	int productCategoryId;

	public static final String PARAM_IsPurchased = I_M_Product.COLUMNNAME_IsPurchased;
	Boolean isPurchased;

	public static final String PARAM_IsSold = I_M_Product.COLUMNNAME_IsSold;
	Boolean isSold;
}
