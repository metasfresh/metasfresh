package de.metas.bpartner_product.rest;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner_product.IBPartnerProductDAO;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.web.MetasfreshRestAPIConstants;

/*
 * #%L
 * de.metas.business.rest-api
 * %%
 * Copyright (C) 2019 metas GmbH
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
@RequestMapping(BPartnerProductRestController.ENDPOINT)
public class BPartnerProductRestController
{
	static final String ENDPOINT = MetasfreshRestAPIConstants.ENDPOINT_API + "/bpartnerProducts";

	private final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
	private final IBPartnerProductDAO bpartnerProductsRepo = Services.get(IBPartnerProductDAO.class);
	private final IProductBL productsService = Services.get(IProductBL.class);

	@GetMapping
	public JsonBPartnerProduct getByCustomerProductNo(
			final String customerName,
			final String customerProductNo)
	{
		final BPartnerId customerId = bpartnersRepo.retrieveBPartnerIdBy(BPartnerQuery.builder()
				.bpartnerName(customerName)
				.failIfNotExists(true)
				.build())
				.get();

		final ProductId productId = bpartnerProductsRepo.getProductIdByCustomerProductNo(customerId, customerProductNo)
				.orElseThrow(() -> new AdempiereException("@NotFound@"));

		final String productNo = productsService.getProductValue(productId);

		return JsonBPartnerProduct.builder()
				.productNo(productNo)
				.build();
	}
}
