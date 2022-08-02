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

package de.metas.camel.externalsystems.alberta.ordercandidate;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface GetOrdersRouteConstants
{

	String ROUTE_PROPERTY_ORG_CODE = "orgCode";
	String ROUTE_PROPERTY_CURRENT_ORDER = "currentOrder";
	String ROUTE_PROPERTY_UPDATED_AFTER = "updatedAfter";
	String ROUTE_PROPERTY_COMMAND = "command";
	String ROUTE_PROPERTY_EXTERNAL_SYSTEM_CONFIG_ID = "externalSystemConfigId";
	String ROUTE_PROPERTY_DELIVERY_ADDRESS_METASFRESH_ID = "deliveryAddressMetasfreshId";

	String DELIVERY_ADDRESS_PREFIX = "delivery_";

	@AllArgsConstructor
	@Getter
	enum OrderStatus
	{
		CREATED("created"),
		UPDATED("updated");

		private final String value;
	}
}
