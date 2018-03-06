package de.metas.dist.ait.sales.order;

import static com.jayway.restassured.RestAssured.given;

import com.jayway.restassured.specification.RequestSpecification;

import lombok.NonNull;

/*
 * #%L
 * metasfresh-dist-ait
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class SalesOrderClient
{
	private final RequestSpecification spec;

	private static final int salesOrderWindoId = 143;

	public SalesOrderClient(@NonNull RequestSpecification spec)
	{
		this.spec = spec;
	}

	public SalesOrder newSalesOrder()
	{
//		given()
//				.spec(spec)
//				.when()
//				.options("window/{windowId}/NEW", salesOrderWindoId)
//				.then()
//				.statusCode(200);

		final SalesOrder[] newSalesOrder = given()
				.spec(spec)
				.when()
				.body("[]")
				.patch("window/{windowId}/NEW", salesOrderWindoId)
				.then()
				.statusCode(200)
				.extract()
				.as(SalesOrder[].class);

		return newSalesOrder[0];

	}

}
