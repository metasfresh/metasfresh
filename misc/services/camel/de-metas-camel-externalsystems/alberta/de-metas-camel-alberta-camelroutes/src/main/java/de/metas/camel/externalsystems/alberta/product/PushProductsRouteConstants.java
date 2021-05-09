/*
 * #%L
 * de-metas-camel-alberta-camelroutes
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

package de.metas.camel.externalsystems.alberta.product;

public interface PushProductsRouteConstants
{
	String ROUTE_PROPERTY_ALBERTA_PRODUCT_API = "AlbertaProductApi";

	// FIXME: might be a good idea to expose somehow all those ExternalSystem enums in a common module
	//keep in sync with de.metas.externalsystem.ExternalSystemType.Alberta
	String ALBERTA_EXTERNAL_SYSTEM_CONFIG_TYPE = "Alberta";
	// keep in sync with de.metas.externalreference.AlbertaExternalSystem.ALBERTA
	String ALBERTA_EXTERNAL_REFERENCE_SYSTEM = "ALBERTA";
	//keep in sync with de.metas.externalreference.product.ProductExternalReferenceType.PRODUCT
	String PRODUCT_EXTERNAL_REFERENCE_TYPE = "Product";
}
