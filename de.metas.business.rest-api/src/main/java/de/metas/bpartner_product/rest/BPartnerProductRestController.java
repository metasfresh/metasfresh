package de.metas.bpartner_product.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner_product.IBPartnerProductDAO;
import de.metas.i18n.ILanguageDAO;
import de.metas.i18n.IMsgBL;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Check;
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

	@GetMapping("/query")
	public ResponseEntity<JsonBPartnerProductResult> getByCustomerProductNo(
			final String customerName,
			final String customerProductNo)
	{
		final BPartnerId customerId = bpartnersRepo.retrieveBPartnerIdBy(BPartnerQuery.builder()
				.bpartnerName(customerName)
				.build())
				.orElse(null);
		if (customerId == null)
		{
			return JsonBPartnerProductResult.notFound(trl("@NotFound@ @C_BPartner_ID@"));
		}

		final ProductId productId = bpartnerProductsRepo.getProductIdByCustomerProductNo(customerId, customerProductNo)
				.orElse(null);
		if (productId == null)
		{
			return JsonBPartnerProductResult.notFound(trl("@NotFound@ @M_Product_ID@"));
		}

		return JsonBPartnerProductResult.ok(JsonBPartnerProduct.builder()
				.productNo(productsService.getProductValue(productId))
				.build());
	}

	private String trl(final String message)
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);
		return msgBL.parseTranslation(getAdLanguage(), message);
	}

	private String getAdLanguage()
	{
		final ILanguageDAO languagesRepo = Services.get(ILanguageDAO.class);

		final HttpServletRequest httpServletRequest = getHttpServletRequest();
		if (httpServletRequest != null)
		{
			final String httpAcceptLanguage = httpServletRequest.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
			if (!Check.isEmpty(httpAcceptLanguage, true))
			{
				final String requestLanguage = languagesRepo.retrieveAvailableLanguages()
						.getAD_LanguageFromHttpAcceptLanguage(httpAcceptLanguage, null);
				if (requestLanguage != null)
				{
					return requestLanguage;
				}
			}
		}

		// Fallback to base language
		return languagesRepo.retrieveBaseLanguage();
	}

	private static final HttpServletRequest getHttpServletRequest()
	{
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes == null)
		{
			return null;
		}
		if (!(requestAttributes instanceof ServletRequestAttributes))
		{
			return null;
		}

		final HttpServletRequest servletRequest = ((ServletRequestAttributes)requestAttributes).getRequest();
		return servletRequest;
	}
}
