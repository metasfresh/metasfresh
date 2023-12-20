package de.metas.pricing.rules.campaign_price;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Range;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.cache.CCache;
import de.metas.cache.CCache.CacheMapType;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.pricing.PricingSystemId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.compiere.model.I_C_Campaign_Price;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/*
 * #%L
 * de.metas.business
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

@Repository
public class CampaignPriceRepository
{
	private final IProductBL productsService = Services.get(IProductBL.class);

	private final CCache<CampaignPricePageKey, CampaignPricePage> cache = CCache.<CampaignPricePageKey, CampaignPricePage> builder()
			.cacheName("campaignPricePages")
			.cacheMapType(CacheMapType.LRU)
			.initialCapacity(100)
			.additionalTableNameToResetFor(I_C_Campaign_Price.Table_Name)
			.build();

	public Optional<CampaignPrice> getCampaignPrice(@NonNull final CampaignPriceQuery query)
	{
		final CampaignPricePageKey key = createPageKey(query);
		return cache
				.getOrLoad(key, this::retrievePage)
				.findPrice(query);
	}

	private static CampaignPricePageKey createPageKey(final CampaignPriceQuery query)
	{
		final CampaignPricePageKey key = CampaignPricePageKey.builder()
				.productId(query.getProductId())
				.month(YearMonth.from(query.getDate()))
				.build();
		return key;
	}

	private CampaignPricePage retrievePage(@NonNull final CampaignPricePageKey key)
	{
		final YearMonth month = key.getMonth();
		final LocalDate startDate = month.atDay(1);
		final LocalDate endDate = month.atEndOfMonth();

		final ImmutableList<CampaignPrice> prices = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_C_Campaign_Price.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Campaign_Price.COLUMNNAME_M_Product_ID, key.getProductId())
				.addCompareFilter(I_C_Campaign_Price.COLUMN_ValidFrom, Operator.LESS_OR_EQUAL, endDate)
				.addCompareFilter(I_C_Campaign_Price.COLUMN_ValidTo, Operator.GREATER_OR_EQUAL, startDate)
				//
				.orderByDescending(I_C_Campaign_Price.COLUMN_ValidFrom)
				.orderBy(I_C_Campaign_Price.COLUMN_C_Campaign_Price_ID)
				//
				.create()
				.stream()
				.map(this::toCampaignPrice)
				.collect(ImmutableList.toImmutableList());

		return CampaignPricePage.of(prices);
	}

	private CampaignPrice toCampaignPrice(final I_C_Campaign_Price record)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(record.getC_Currency_ID());

		final LocalDate validFrom = TimeUtil.asLocalDate(record.getValidFrom());
		final LocalDate validTo = TimeUtil.asLocalDate(record.getValidTo());

		return CampaignPrice.builder()
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.bpartnerId(BPartnerId.ofRepoIdOrNull(record.getC_BPartner_ID()))
				.bpGroupId(BPGroupId.ofRepoIdOrNull(record.getC_BP_Group_ID()))
				.pricingSystemId(PricingSystemId.ofRepoIdOrNull(record.getM_PricingSystem_ID()))
				.countryId(CountryId.ofRepoId(record.getC_Country_ID()))
				.validRange(Range.closed(validFrom, validTo))
				.priceList(Optional.ofNullable(record.getPriceList())
								   .map(priceList -> Money.of(priceList, currencyId))
								   .orElse(null))
				//
				.priceStd(Money.of(record.getPriceStd(), currencyId))
				.priceUomId(extractProductPriceUomId(record))
				.taxCategoryId(TaxCategoryId.ofRepoId(record.getC_TaxCategory_ID()))
				.invoicableQtyBasedOn(InvoicableQtyBasedOn.ofNullableCodeOrNominal(record.getInvoicableQtyBasedOn()))
				//
				.build();
	}

	private UomId extractProductPriceUomId(final I_C_Campaign_Price record)
	{
		final UomId productPriceUomId = UomId.ofRepoIdOrNull(record.getC_UOM_ID());
		if (productPriceUomId != null)
		{
			return productPriceUomId;
		}

		final ProductId productId = ProductId.ofRepoId(record.getM_Product_ID());
		return productsService.getStockUOMId(productId);
	}

	@Value
	@Builder
	private static class CampaignPricePageKey
	{
		@NonNull
		ProductId productId;

		@NonNull
		YearMonth month;
	}

	@Value
	private static class CampaignPricePage
	{
		public static CampaignPricePage of(final List<CampaignPrice> prices)
		{
			if (prices.isEmpty())
			{
				return EMPTY;
			}
			else
			{
				return new CampaignPricePage(prices);
			}
		}

		private static final CampaignPricePage EMPTY = new CampaignPricePage();

		private final ImmutableListMultimap<BPartnerId, CampaignPrice> bpartnerPrices;
		private final ImmutableListMultimap<BPGroupId, CampaignPrice> bpGroupPrices;
		private final ImmutableListMultimap<PricingSystemId, CampaignPrice> pricingSystemPrices;

		private CampaignPricePage(final List<CampaignPrice> prices)
		{
			bpartnerPrices = prices.stream()
					.filter(price -> price.getBpartnerId() != null)
					.sorted(Comparator.comparing(CampaignPrice::getValidFrom))
					.collect(GuavaCollectors.toImmutableListMultimap(CampaignPrice::getBpartnerId));
			bpGroupPrices = prices.stream()
					.filter(price -> price.getBpGroupId() != null)
					.sorted(Comparator.comparing(CampaignPrice::getValidFrom))
					.collect(GuavaCollectors.toImmutableListMultimap(CampaignPrice::getBpGroupId));

			pricingSystemPrices = prices.stream()
					.filter(price -> price.getPricingSystemId() != null)
					.sorted(Comparator.comparing(CampaignPrice::getValidFrom))
					.collect(GuavaCollectors.toImmutableListMultimap(CampaignPrice::getPricingSystemId));
		}

		private CampaignPricePage()
		{
			bpartnerPrices = ImmutableListMultimap.of();
			bpGroupPrices = ImmutableListMultimap.of();
			pricingSystemPrices = ImmutableListMultimap.of();
		}

		public Optional<CampaignPrice> findPrice(@NonNull final CampaignPriceQuery query)
		{
			Optional<CampaignPrice> result = findPrice(bpartnerPrices.get(query.getBpartnerId()), query);

			if (result.isPresent())
			{
				return result;
			}

			result = findPrice(bpGroupPrices.get(query.getBpGroupId()), query);

			if (result.isPresent()) {
				return result;
			}

			return findPrice(pricingSystemPrices.get(query.getPricingSystemId()), query);
		}

		private static Optional<CampaignPrice> findPrice(@NonNull final Collection<CampaignPrice> prices, @NonNull final CampaignPriceQuery query)
		{
			return prices.stream()
					.filter(query::isMatching)
					.findFirst();
		}

	}
}
