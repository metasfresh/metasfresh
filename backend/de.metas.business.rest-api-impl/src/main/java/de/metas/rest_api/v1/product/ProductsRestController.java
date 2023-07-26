/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.v1.product;

import de.metas.Profiles;
import de.metas.logging.LogManager;
import de.metas.rest_api.v1.product.command.GetProductsCommand;
import de.metas.rest_api.product.response.JsonGetProductsResponse;
import de.metas.util.web.MetasfreshRestAPIConstants;
import lombok.NonNull;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = {
		MetasfreshRestAPIConstants.ENDPOINT_API_DEPRECATED + "/products",
		MetasfreshRestAPIConstants.ENDPOINT_API_V1 + "/products"})
@RestController
@Profile(Profiles.PROFILE_App)
public class ProductsRestController
{
	private static final Logger logger = LogManager.getLogger(ProductsRestController.class);
	private final ProductsServicesFacade productsServicesFacade;

	public ProductsRestController(@NonNull final ProductsServicesFacade productsServicesFacade)
	{
		this.productsServicesFacade = productsServicesFacade;
	}

	@GetMapping
	public ResponseEntity<JsonGetProductsResponse> getProducts()
	{
		final String adLanguage = Env.getADLanguageOrBaseLanguage();

		try
		{
			final JsonGetProductsResponse response = GetProductsCommand.builder()
					.servicesFacade(productsServicesFacade)
					.adLanguage(adLanguage)
					.execute();

			return ResponseEntity.ok(response);
		}
		catch (final Exception ex)
		{
			logger.debug("Got exception", ex);

			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(JsonGetProductsResponse.error(ex, adLanguage));
		}
	}
}
