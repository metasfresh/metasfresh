/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.pricing.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.ProductPriceId;
import de.metas.pricing.exceptions.PriceListVersionNotFoundException;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.user.UserId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.model.I_M_ProductScalePrice;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public interface IPriceListDAO extends ISingletonService
{
	int M_PricingSystem_ID_None = PricingSystemId.NONE.getRepoId();
	int M_PriceList_ID_None = PriceListId.NONE.getRepoId();

	@Nullable
	I_M_PricingSystem getPricingSystemById(@Nullable PricingSystemId pricingSystemId);

	@NonNull
	PricingSystemId getPricingSystemIdByValue(String value);

	@Nullable
	PricingSystemId getPricingSystemIdByValueOrNull(String value);

	@Nullable
	I_M_PriceList getById(@Nullable PriceListId priceListId);

	I_M_PriceList getById(int priceListId);

	I_M_PriceList_Version getPriceListVersionById(PriceListVersionId priceListVersionId);

	I_M_PriceList_Version getPriceListVersionByIdInTrx(PriceListVersionId priceListVersionId);

	PriceListsCollection retrievePriceListsCollectionByPricingSystemId(PricingSystemId pricingSystemId);

	PriceListId retrievePriceListIdByPricingSyst(
			@Nullable PricingSystemId pricingSystemId,
			CountryId countryId,
			SOTrx soTrx);

	/**
	 * Returns a list containing all the PO price lists for a given pricing system and a country.<br>
	 * The method returns both price lists with the given country and without any country. The price list
	 * which has a country (if any) is ordered first.
	 *
	 * @param soTrx           sales, purchase or null to return both
	 */
	List<I_M_PriceList> retrievePriceLists(PricingSystemId pricingSystemId, CountryId countryId, SOTrx soTrx);

	/**
	 * @return the price list for the given pricing system and location or <code>null</code>.
	 */
	@Nullable
	PriceListId retrievePriceListIdByPricingSyst(PricingSystemId pricingSystemId, BPartnerLocationAndCaptureId bpartnerLocationId, SOTrx soTrx);

	/**
	 * Retrieves the plv for the given price list and date. Never returns <code>null</code>
	 *
	 * @param processed optional, can be <code>null</code>. Allow to filter by <code>I_M_PriceList.Processed</code>
	 */
	@Nullable
	I_M_PriceList_Version retrievePriceListVersionOrNull(org.compiere.model.I_M_PriceList priceList, ZonedDateTime date, @Nullable Boolean processed);

	/**
	 * Retrieves the plv for the given price list and date. Never returns <code>null</code>
	 *
	 * @param processed   optional, can be <code>null</code>. Allow to filter by <code>I_M_PriceList.Processed</code>
	 */
	@Nullable
	I_M_PriceList_Version retrievePriceListVersionOrNull(PriceListId priceListId, ZonedDateTime date, @Nullable Boolean processed);

	@Nullable
	PriceListVersionId retrievePriceListVersionIdOrNull(PriceListId priceListId, ZonedDateTime date, @Nullable Boolean processed);

	@Nullable
	default PriceListVersionId retrievePriceListVersionIdOrNull(final PriceListId priceListId, final ZonedDateTime date)
	{
		final Boolean processed = null;
		return retrievePriceListVersionIdOrNull(priceListId, date, processed);
	}

	default PriceListVersionId retrievePriceListVersionId(final PriceListId priceListId, final ZonedDateTime date)
	{
		final PriceListVersionId priceListVersionId = retrievePriceListVersionIdOrNull(priceListId, date);
		if (priceListVersionId == null)
		{
			throw new PriceListVersionNotFoundException(priceListId, date);
		}
		return priceListVersionId;
	}

	/**
	 * @return next product price's MatchSeqNo
	 */
	int retrieveNextMatchSeqNo(final I_M_ProductPrice productPrice);

	I_M_PriceList_Version retrievePriceListVersionWithExactValidDate(PriceListId priceListId, Date date);

	@Nullable
	I_M_PriceList_Version retrieveNewestPriceListVersion(PriceListId priceListId);

	Optional<PriceListVersionId> retrieveNewestPriceListVersionId(PriceListId priceListId);

	String getPricingSystemName(@Nullable final PricingSystemId pricingSystemId);

	String getPriceListName(final PriceListId priceListId);

	Set<CountryId> retrieveCountryIdsByPricingSystem(final PricingSystemId pricingSystemId);

	Set<ProductId> retrieveHighPriceProducts(BigDecimal minimumPrice, LocalDate date);

	default Stream<I_M_ProductPrice> retrieveProductPrices(@NonNull final PriceListVersionId priceListVersionId)
	{
		final Set<ProductId> productIdsToExclude = ImmutableSet.of();
		return retrieveProductPrices(priceListVersionId, productIdsToExclude);
	}

	Stream<I_M_ProductPrice> retrieveProductPrices(PriceListVersionId priceListVersionId, Set<ProductId> productIdsToExclude);

	ImmutableList<I_M_ProductPrice> retrieveProductPrices(PriceListVersionId priceListVersionId, ProductId productId);

	/**
	 * Retrieves product prices records of the given price list version
	 *
	 * @return iterator of product prices ordered by SeqNo and Name
	 */
	Iterator<I_M_ProductPrice> retrieveProductPricesOrderedBySeqNoAndProductIdAndMatchSeqNo(PriceListVersionId priceListVersionId);

	List<PriceListVersionId> getPriceListVersionIdsUpToBase(final PriceListVersionId startPriceListVersionId, final ZonedDateTime date);

	I_M_PriceList_Version getCreatePriceListVersion(ProductPriceCreateRequest request);

	I_M_PriceList getPriceListByPriceListVersionId(PriceListVersionId priceListVersionId);

	@Nullable
	I_M_PriceList_Version getBasePriceListVersionForPricingCalculationOrNull(PriceListVersionId priceListVersionId, ZonedDateTime date);

	@Nullable
	I_M_PriceList_Version getBasePriceListVersionForPricingCalculationOrNull(I_M_PriceList_Version priceListVersion, ZonedDateTime date);

	@Nullable
	PriceListVersionId getBasePriceListVersionIdForPricingCalculationOrNull(I_M_PriceList_Version priceListVersion, ZonedDateTime date);

	@Nullable
	PriceListVersionId getBasePriceListVersionIdForPricingCalculationOrNull(PriceListVersionId priceListVersionId, ZonedDateTime date);

	ProductPriceId addProductPrice(AddProductPriceRequest request);

	ProductPriceId copyProductPrice(CopyProductPriceRequest request);

	void updateProductPrice(UpdateProductPriceRequest request);

	void deleteProductPricesByIds(Set<ProductPriceId> productPriceIds);

	@Nullable
	I_M_PriceList_Version retrievePreviousVersionOrNull(I_M_PriceList_Version plv, boolean onlyProcessed);

	@Nullable
	I_M_PriceList_Version retrieveNextVersionOrNull(I_M_PriceList_Version plv, final boolean onlyProcessed);

	void mutateCustomerPrices(PriceListVersionId priceListVersionId, UserId userId);

	Optional<TaxCategoryId> getDefaultTaxCategoryByPriceListVersionId(final PriceListVersionId priceListVersionId);

	PricingSystemId getPricingSystemId(PriceListId priceListId);

	void updateProductPricesIsActive(@NonNull final IQueryFilter<I_M_Product> productFilter, @Nullable final LocalDate date, final boolean newIsActiveValue);

	CurrencyId getCurrencyId(final PriceListId priceListId);

	I_M_ProductScalePrice retrieveScalePriceForExactBreak(ProductPriceId productPriceId, BigDecimal scalePriceBreak);
}
