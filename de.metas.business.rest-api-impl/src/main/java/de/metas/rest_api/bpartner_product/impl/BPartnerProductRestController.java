package de.metas.rest_api.bpartner_product.impl;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner_product.IBPartnerProductDAO;
import de.metas.i18n.IMsgBL;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.rest_api.utils.RestApiUtils;
import de.metas.util.Services;
import de.metas.util.rest.MetasfreshRestAPIConstants;

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

	@GetMapping("/query")
	public ResponseEntity<JsonBPartnerProductResult> getByCustomerProductNo(
			@RequestParam("customerName") final String customerName,
			@RequestParam("customerProduct") final String customerProductSearchString)
	{
		final BPartnerId customerId = findCustomerId(customerName).orElse(null);
		if (customerId == null)
		{
			return JsonBPartnerProductResult.notFound(trl("@NotFound@ @C_BPartner_ID@"));
		}

		final ProductId productId = findProductId(customerId, customerProductSearchString).orElse(null);
		if (productId == null)
		{
			return JsonBPartnerProductResult.notFound(trl("@NotFound@ @M_Product_ID@"));
		}

		return JsonBPartnerProductResult.ok(JsonBPartnerProduct.builder()
				.productNo(productsService.getProductValue(productId))
				.build());
	}

	private Optional<BPartnerId> findCustomerId(final String customerName)
	{
		return bpartnersRepo.retrieveBPartnerIdBy(BPartnerQuery.builder()
				.bpartnerName(customerName)
				.build());
	}

	private Optional<ProductId> findProductId(final BPartnerId customerId, final String customerProductSearchString)
	{
		final Optional<ProductId> productIdByProductNo = bpartnerProductsRepo.getProductIdByCustomerProductNo(customerId, customerProductSearchString);
		if (productIdByProductNo.isPresent())
		{
			return productIdByProductNo;
		}

		final Optional<ProductId> productIdByProductName = bpartnerProductsRepo.getProductIdByCustomerProductName(customerId, customerProductSearchString);
		if (productIdByProductName.isPresent())
		{
			return productIdByProductName;
		}

		return Optional.empty();
	}

	private String trl(final String message)
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);
		return msgBL.parseTranslation(RestApiUtils.getAdLanguage(), message);
	}
}
