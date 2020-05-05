package de.metas.dist.ait.menu;

import static com.jayway.restassured.RestAssured.given;

import com.jayway.restassured.specification.RequestSpecification;

import de.metas.dist.ait.login.LoginClient;
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

public class Menu
{
	private final RequestSpecification spec;
	
	public Menu(@NonNull final LoginClient login)
	{
		spec = login.getSpec();
	}
	
	public void searchInMenu(String search)
	{
		given() // will do http://localhost:8080 by default
		.spec(spec)
		.when()
		.param("nameQuery", search)
		.get("/rest/api/menu/queryPaths")
		.then()
		.statusCode(200).extract().as(Boolean.class);
	}
}
