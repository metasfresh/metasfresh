/*
 * #%L
 * procurement-webui-backend
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

package de.metas.procurement.webui.rest.products;

import de.metas.procurement.webui.Application;
import de.metas.procurement.webui.model.Product;
import de.metas.procurement.webui.model.User;
import de.metas.procurement.webui.service.IContractsService;
import de.metas.procurement.webui.service.ILoginService;
import de.metas.procurement.webui.service.IProductSuppliesService;
import lombok.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ProductsRestController.ENDPOINT)
public class ProductsRestController
{
	static final String ENDPOINT = Application.ENDPOINT_ROOT + "/products";

	private final ILoginService loginService;
	private final IProductSuppliesService productSuppliesService;
	private final IContractsService contractsService;

	public ProductsRestController(
			@NonNull final ILoginService loginService,
			@NonNull final IProductSuppliesService productSuppliesService,
			@NonNull final IContractsService contractsService)
	{
		this.loginService = loginService;
		this.productSuppliesService = productSuppliesService;
		this.contractsService = contractsService;
	}

	@PostMapping("/favorite/add")
	public void addToFavorite(@RequestBody @NonNull final JsonAddProductsToFavoriteRequest request)
	{
		final User user = loginService.getLoggedInUser();
		for (final String productIdStr : request.getProductIds())
		{
			final Product product = productSuppliesService.getProductById(Long.parseLong(productIdStr));
			productSuppliesService.addUserFavoriteProduct(user, product);
		}
	}

	@PostMapping("/favorite/remove")
	public void removeFromFavorite(@RequestBody @NonNull final JsonRemoveProductsFromFavoriteRequest request)
	{
		final User user = loginService.getLoggedInUser();
		for (final String productIdStr : request.getProductIds())
		{
			final Product product = productSuppliesService.getProductById(Long.parseLong(productIdStr));
			productSuppliesService.removeUserFavoriteProduct(user, product);
		}
	}

	@GetMapping("/notFavorite")
	public JsonProductToAddResponse getProductsNotFavorite()
	{
		final User user = loginService.getLoggedInUser();
		final List<Product> productsContracted = contractsService.getContracts(user.getBpartner()).getProducts();
		final List<Product> productsFavorite = productSuppliesService.getUserFavoriteProducts(user);
		final List<Product> productsShared = productSuppliesService.getAllSharedProducts();

		final ArrayList<Product> productsNotSelected = new ArrayList<>(productsContracted);
		productsNotSelected.removeAll(productsFavorite);

		final ArrayList<Product> productsNotContracted = new ArrayList<>(productsShared);
		productsNotContracted.removeAll(productsFavorite);
		productsNotContracted.removeAll(productsContracted);

		final Locale locale = loginService.getLocale();
		return JsonProductToAddResponse.builder()
				.products(toJsonProductOrderedList(productsNotSelected, locale))
				.moreProducts(toJsonProductOrderedList(productsNotContracted, locale))
				.build();
	}

	private static ArrayList<JsonProduct> toJsonProductOrderedList(@NonNull final List<Product> products, @NonNull final Locale locale)
	{
		return products.stream()
				.map(product -> toJsonProduct(product, locale))
				.sorted(Comparator.comparing(JsonProduct::getProductName))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	private static JsonProduct toJsonProduct(@NonNull final Product product, @NonNull final Locale locale)
	{
		return JsonProduct.builder()
				.productId(product.getIdAsString())
				.productName(product.getName(locale))
				.packingInfo(product.getPackingInfo(locale))
				.build();
	}
}
