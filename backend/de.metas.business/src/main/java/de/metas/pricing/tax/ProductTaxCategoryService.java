/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.pricing.tax;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.location.CountryId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ProductTaxCategoryService
{
	private static final AdMessageKey MSG_NO_C_TAX_CATEGORY_FOR_PRODUCT_PRICE = AdMessageKey.of("MissingTaxCategoryForProductPrice");

	private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final ProductTaxCategoryRepository productTaxCategoryRepository;

	public ProductTaxCategoryService(@NonNull final ProductTaxCategoryRepository productTaxCategoryRepository)
	{
		this.productTaxCategoryRepository = productTaxCategoryRepository;
	}

	@NonNull
	public TaxCategoryId getTaxCategoryId(@NonNull final I_M_ProductPrice productPrice)
	{
		return getTaxCategoryIdOptional(productPrice)
				.orElseThrow(() -> new AdempiereException(msgBL.getTranslatableMsgText(MSG_NO_C_TAX_CATEGORY_FOR_PRODUCT_PRICE))
						.appendParametersToMessage()
						.setParameter("productPriceId", productPrice.getM_ProductPrice_ID())
						.setParameter("productId", productPrice.getM_Product_ID())
						.setParameter("priceListVersionId", productPrice.getM_PriceList_Version_ID()));
	}

	@NonNull
	public Optional<TaxCategoryId> getTaxCategoryIdOptional(@NonNull final I_M_ProductPrice productPrice)
	{
		final TaxCategoryId productPriceTaxCategoryId = TaxCategoryId.ofRepoIdOrNull(productPrice.getC_TaxCategory_ID());

		if (productPriceTaxCategoryId != null)
		{
			return Optional.of(productPriceTaxCategoryId);
		}

		return findTaxCategoryId(productPrice);
	}

	@NonNull
	public Optional<TaxCategoryId> findTaxCategoryId(@NonNull final LookupTaxCategoryRequest lookupTaxCategoryRequest)
	{
		return productTaxCategoryRepository.getTaxCategoryId(lookupTaxCategoryRequest);
	}

	@NonNull
	public Stream<ProductTaxCategory> findAllFor(@NonNull final I_M_ProductPrice productPrice)
	{
		return productTaxCategoryRepository.findAllFor(createLookupTaxCategoryRequest(productPrice));
	}

	@NonNull
	private Optional<TaxCategoryId> findTaxCategoryId(@NonNull final I_M_ProductPrice productPrice)
	{
		return productTaxCategoryRepository.getTaxCategoryId(createLookupTaxCategoryRequest(productPrice));
	}

	@NonNull
	private LookupTaxCategoryRequest createLookupTaxCategoryRequest(@NonNull final I_M_ProductPrice productPrice)
	{
		final ProductId productId = ProductId.ofRepoId(productPrice.getM_Product_ID());
		final PriceListVersionId priceListVersionId = PriceListVersionId.ofRepoId(productPrice.getM_PriceList_Version_ID());

		final I_M_PriceList_Version priceListVersionRecord = priceListDAO.getPriceListVersionById(priceListVersionId);
		final I_M_PriceList priceListRecord = priceListDAO.getById(priceListVersionRecord.getM_PriceList_ID());

		return LookupTaxCategoryRequest.builder()
				.productId(productId)
				.targetDate(TimeUtil.asInstant(priceListVersionRecord.getValidFrom()))
				.countryId(CountryId.ofRepoIdOrNull(priceListRecord.getC_Country_ID()))
				.build();
	}

	@NonNull
	public Optional<ProductTaxCategory> getProductTaxCategoryByUniqueKey(
			@NonNull final ProductId productId,
			@NonNull final CountryId countryId)
	{
		return productTaxCategoryRepository.getProductTaxCategoryByUniqueKey(productId, countryId);
	}

	public void save(@NonNull final ProductTaxCategory request)
	{
		productTaxCategoryRepository.save(request);
	}

	@NonNull
	public ProductTaxCategory createProductTaxCategory(@NonNull final CreateProductTaxCategoryRequest createProductTaxCategoryRequest)
	{
		return productTaxCategoryRepository.createProductTaxCategory(createProductTaxCategoryRequest);
	}
}