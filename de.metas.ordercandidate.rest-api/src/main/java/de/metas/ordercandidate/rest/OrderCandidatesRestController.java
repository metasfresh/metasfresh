package de.metas.ordercandidate.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * #%L
 * de.metas.ordercandidate.rest-api
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

@RestController
@RequestMapping(value = OrderCandidatesRestController.ENDPOINT)
public class OrderCandidatesRestController
{
	public static final String ENDPOINT = "/api/sales/order/candidates";

	// @Autowired
	// private OLCandRepository olCandRepo;

	@PostMapping
	public void createOrder(@RequestBody final JsonOLCandCreateRequest request)
	{
		// TODO: impl
		System.out.println(request);
		throw new UnsupportedOperationException();
	}
}
