/*
 * #%L
 * de-metas-camel-shopware6
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

package de.metas.camel.externalsystems.shopware6;

public interface Shopware6Constants
{
	String SHOPWARE6_SYSTEM_NAME = "Shopware6";
	String ESR_TYPE_BPARTNER_LOCATION = "BPartnerLocation";
	String ESR_TYPE_BPARTNER = "BPartner";

	String FIELD_UPDATED_AT = "updatedAt";
	String FIELD_CREATED_AT = "createdAt";

	String PARAMETERS_DATE_GTE =  "gte";

	//camel route properties
	String ROUTE_PROPERTY_SHOPWARE_CLIENT = "shopware-client";
	String ROUTE_PROPERTY_CURRENT_ORDER = "slack";

	//nodes
	String JSON_NODE_DATA = "data";

	//shopware client
	int CONNECTION_TIMEOUT_SECONDS = 600;
	int READ_TIMEOUT_SECONDS = 600;

}
