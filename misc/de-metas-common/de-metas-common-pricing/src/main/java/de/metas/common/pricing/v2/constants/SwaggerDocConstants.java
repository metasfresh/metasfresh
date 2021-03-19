/*
 * #%L
 * de-metas-common-pricing
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.common.pricing.v2.constants;

public class SwaggerDocConstants
{
	public static final String PRICE_LIST_VERSION_IDENTIFIER = "Identifier of the price list version in question. Can be\n"
			+ "* a plain `<M_PriceList_Version_ID>`\n"
			+ "* or something like `ext-<ExternalSystemName>-<ExternalReference>";

	public static final String PRICE_LIST_IDENTIFIER = "Identifier of the price list in question. Can be\n"
			+ "* a plain `<M_PriceList_ID>`\n"
			+ "* or something like `ext-<ExternalSystemName>-<ExternalReference>";

	public static final String PRODUCT_PRICE_IDENTIFIER = "Identifier of the product price in question. Can be\n"
			+ "* a plain `<M_ProductPrice_ID>`\n"
			+ "* or something like `ext-<ExternalSystemName>-<ExternalReference>";

	public static final String PRODUCT_IDENTIFIER = "Identifier of the product for the price in question. Can be\n"
			+ "* a plain `<M_Product_ID>`\n"
			+ "* or something like `ext-<ExternalSystemName>-<ExternalReference>";
}
