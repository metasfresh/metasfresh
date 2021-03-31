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

package de.metas.camel.alberta.common;

public interface CommonAlbertaConstants
{
	// keep in sync with de.metas.externalreference.AlbertaExternalSystem.ALBERTA
	String ALBERTA_EXTERNAL_REFERENCE_SYSTEM = "ALBERTA";
	//keep in sync with de.metas.externalreference.product.ProductExternalReferenceType.PRODUCT
	String PRODUCT_EXTERNAL_REFERENCE_TYPE = "Product";

	//keep in sync with de.metas.externalreference.bpartnerlocation.BPLocationExternalReferenceType.BPARTNER_LOCATION
	String BPARTNER_LOCATION_EXTERNAL_REFERENCE_TYPE = "BPartnerLocation";

	//keep in sync with de.metas.externalreference.bpartner.BPartnerExternalReferenceType
	String BPARTNER_EXTERNAL_REFERENCE_TYPE = "BPartner";

	String EXTERNAL_IDENTIFIER_PREFIX = "ext";

	String ALBERTA_DATA_INPUT_SOURCE = "int-Alberta";
}
