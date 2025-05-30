package de.metas.rest_api.bpartner_product.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner_product.IBPartnerProductDAO;
import de.metas.i18n.IMsgBL;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.rest_api.utils.RestApiUtilsV1;
import de.metas.util.Services;
import de.metas.util.web.MetasfreshRestAPIConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

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
@RequestMapping(value = {
		MetasfreshRestAPIConstants.ENDPOINT_API_DEPRECATED + "/bpartnerProducts",
		MetasfreshRestAPIConstants.ENDPOINT_API_V1 + "/bpartnerProducts",
		MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/bpartnerProducts" })
//the spelling "Bpartner.." is to avoid swagger spelling it "b-partner-prod.."
public class BpartnerProductRestController
{
	private final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
	private final IBPartnerProductDAO bpartnerProductsRepo = Services.get(IBPartnerProductDAO.class);
	private final IProductBL productsService = Services.get(IProductBL.class);

	@Operation(summary = "For a BPartner's name and that partner's specific product-number or product-name, this operation returns the product's metasfresh search-key (i.e. `M_Product.Value`).<br>"
			+ "Note that the respective `C_BPartner_Product` record needs to be flagged with `UsedForCustomer='Y'` to be found by this operation.")
	@GetMapping("/query")
	public ResponseEntity<JsonBPartnerProductResult> getByCustomerProductNo(
			@Parameter(description = "`C_BPartner.Name` of the bpartner in question.")
			@RequestParam("customerName") final String customerName,

			@Parameter(description = "`C_BPartner_Product.ProductNo` or `.ProductName`.")
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
		return msgBL.parseTranslation(RestApiUtilsV1.getAdLanguage(), message);
	}
}
