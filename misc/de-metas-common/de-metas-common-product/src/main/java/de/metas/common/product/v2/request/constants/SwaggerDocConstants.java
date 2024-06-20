/*
 * #%L
 * de-metas-common-product
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

package de.metas.common.product.v2.request.constants;

public class SwaggerDocConstants
{
	public static final String PRODUCT_IDENTIFIER_DOC = "Identifier of the product in question. Can be\\n"
			+ "* a plain `<M_Product_ID>`\n"
			+ "* or something like `ext-<ExternalSystemName>-<M_Product_ID.ExternalId>`";
	public static final String BPARTNER_IDENTIFIER_DOC = "Identifier of the bPartner in question. Can be\n"
			+ "* a plain `<C_BPartner_ID>`\n"
			+ "* or something like `ext-<ExternalSystemName>-<C_BPartner.ExternalId>`\n";
	public static final String PRODUCT_CATEGORY_IDENTIFIER_DOC = "Identifier of the product category in question. Can be\n"
			+ "* a plain `<M_Product_Category_IDD>`\n"
			+ "* or something like `ext-<ExternalSystemName>-<M_Product_Category_ID.ExternalId>`";

	public static final String ORG_CODE_PARAMETER_DOC = "`AD_Org.Value` of the Product(s) identified by the productIdentifier";

}
