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

package de.metas.camel.externalsystems.shopware6.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PathSegmentsEnum
{
	API("api"),
	SEARCH("search"),
	ORDER("order"),
	PRODUCT("product"),
	UNIT("unit"),
	DELIVERIES("deliveries"),
	ORDER_ADDRESS("order-address"),
	OATH("oauth"),
	TOKEN("token"),
	COUNTRY("country"),
	LINE_ITEMS("line-items"),
	CURRENCY("currency"),
	SALUTATION("salutation"),
	CUSTOMER("customer"),
	GROUP("group"),
	TRANSACTIONS("transactions"),
	PAYMENT_METHOD("payment-method"),

	CUSTOMER_GROUP("customer-group"),
	CUSTOMER_ADDRESS("customer-address");

	private final String value;
}
