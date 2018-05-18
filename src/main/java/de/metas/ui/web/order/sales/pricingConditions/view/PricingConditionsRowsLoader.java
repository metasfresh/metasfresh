package de.metas.ui.web.order.sales.pricingConditions.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.bpartner.service.IBPartnerBL;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.slf4j.Logger;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableSetMultimap;

import de.metas.logging.LogManager;
import de.metas.pricing.conditions.PricingConditions;
import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.pricing.conditions.PricingConditionsBreak.PriceOverrideType;
import de.metas.pricing.conditions.PricingConditionsBreakMatchCriteria;
import de.metas.pricing.conditions.service.IPricingConditionsRepository;
import de.metas.ui.web.document.filter.DocumentFiltersList;
import de.metas.ui.web.window.datatypes.LookupValue;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
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

class PricingConditionsRowsLoader
{
	// services
	private static final Logger logger = LogManager.getLogger(PricingConditionsRowsLoader.class);
	private final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
	private final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
	private final IPricingConditionsRepository pricingConditionsRepo = Services.get(IPricingConditionsRepository.class);

	private static final Comparator<PricingConditionsRow> ROWS_SORTING = Comparator.<PricingConditionsRow, Integer> comparing(row -> row.isEditable() ? 0 : 1)
			.thenComparing(row -> row.getBpartner().getDisplayName())
			.thenComparing(row -> row.isCustomer() ? 0 : 1);

	private final PricingConditionsRowLookups lookups;
	private final PricingConditionsBreaksExtractor pricingConditionsBreaksExtractor;
	private final DocumentFiltersList filters;
	private final int adClientId;
	private final SourceDocumentLine sourceDocumentLine;

	private ImmutableSetMultimap<Integer, DiscountSchemaInfo> discountSchemasInfoByDiscountSchemaId; // lazy

	@Builder
	private PricingConditionsRowsLoader(
			@NonNull final PricingConditionsRowLookups lookups,
			@NonNull final PricingConditionsBreaksExtractor pricingConditionsBreaksExtractor,
			final DocumentFiltersList filters,
			final int adClientId,
			@Nullable final SourceDocumentLine sourceDocumentLine)
	{
		Check.assumeGreaterThanZero(adClientId, "adClientId");

		this.lookups = lookups;
		this.pricingConditionsBreaksExtractor = pricingConditionsBreaksExtractor;
		this.filters = filters != null ? filters : DocumentFiltersList.EMPTY;
		this.adClientId = adClientId;
		this.sourceDocumentLine = sourceDocumentLine;
	}

	public PricingConditionsRowData load()
	{
		final ArrayList<PricingConditionsRow> rows = getAllDiscountSchemaIds()
				.stream()
				.flatMap(this::streamMatchingSchemaBreaks)
				.filter(Predicates.notNull())
				.flatMap(this::createPricingConditionsRows)
				.sorted(ROWS_SORTING)
				.collect(Collectors.toCollection(ArrayList::new));

		final PricingConditionsRow editableRow = removeCurrentPricingConditionsRow(rows)
				.map(PricingConditionsRow::copyAndChangeToEditable)
				.orElseGet(this::createEditablePricingConditionsRowOrNull);

		return PricingConditionsRowData.builder()
				.editableRow(editableRow)
				.rows(rows)
				.salesOrderLineId(sourceDocumentLine != null ? sourceDocumentLine.getSalesOrderLineId() : -1)
				.build()
				.filter(filters);
	}

	private Set<Integer> getAllDiscountSchemaIds()
	{
		return getBPartnerIdsIndexedByDiscountSchemaId().keySet();
	}

	private Set<DiscountSchemaInfo> getDiscountSchemaInfos(final int discountSchemaId)
	{
		return getBPartnerIdsIndexedByDiscountSchemaId().get(discountSchemaId);
	}

	private ImmutableSetMultimap<Integer, DiscountSchemaInfo> getBPartnerIdsIndexedByDiscountSchemaId()
	{
		if (discountSchemasInfoByDiscountSchemaId == null)
		{
			final ImmutableSetMultimap.Builder<Integer, DiscountSchemaInfo> builder = ImmutableSetMultimap.builder();

			// Vendor Discount Schemas
			bpartnersRepo.retrieveAllDiscountSchemaIdsIndexedByBPartnerId(adClientId, /* isSOTrx */false)
					.forEach((bpartnerId, discountSchemaId) -> builder.put(discountSchemaId, DiscountSchemaInfo.builder()
							.bpartnerId(bpartnerId)
							.discountSchemaId(discountSchemaId)
							.isSOTrx(false)
							.build()));

			// Customer Discount Schemas
			bpartnersRepo.retrieveAllDiscountSchemaIdsIndexedByBPartnerId(adClientId, /* isSOTrx */true)
					.forEach((bpartnerId, discountSchemaId) -> builder.put(discountSchemaId, DiscountSchemaInfo.builder()
							.bpartnerId(bpartnerId)
							.discountSchemaId(discountSchemaId)
							.isSOTrx(true)
							.build()));

			discountSchemasInfoByDiscountSchemaId = builder.build();
		}
		return discountSchemasInfoByDiscountSchemaId;
	}

	private Stream<PricingConditionsBreak> streamMatchingSchemaBreaks(final int discountSchemaId)
	{
		final PricingConditions pricingConditions = pricingConditionsRepo.getPricingConditionsById(discountSchemaId);
		return pricingConditionsBreaksExtractor.streamPricingConditionsBreaks(pricingConditions);
	}

	private Stream<PricingConditionsRow> createPricingConditionsRows(final PricingConditionsBreak pricingConditionsBreak)
	{
		return getDiscountSchemaInfos(pricingConditionsBreak.getDiscountSchemaId())
				.stream()
				.map(discountSchemaInfo -> createPricingConditionsRow(pricingConditionsBreak, discountSchemaInfo));
	}

	private PricingConditionsRow createPricingConditionsRow(final PricingConditionsBreak pricingConditionsBreak, final DiscountSchemaInfo discountSchemaInfo)
	{
		return PricingConditionsRow.builder()
				.lookups(lookups)
				.editable(false)
				//
				.bpartner(lookups.lookupBPartner(discountSchemaInfo.getBpartnerId()))
				.customer(discountSchemaInfo.isSOTrx())
				//
				.price(extractPrice(pricingConditionsBreak))
				//
				.discount(pricingConditionsBreak.getDiscount())
				.paymentTerm(lookups.lookupPaymentTerm(pricingConditionsBreak.getPaymentTermId()))
				//
				.discountSchemaId(pricingConditionsBreak.getDiscountSchemaId())
				.discountSchemaBreakId(pricingConditionsBreak.getDiscountSchemaBreakId())
				.breakMatchCriteria(pricingConditionsBreak.getMatchCriteria())
				.build();
	}

	private Price extractPrice(final PricingConditionsBreak pricingConditionsBreak)
	{
		final PriceOverrideType priceOverride = pricingConditionsBreak.getPriceOverride();
		if (priceOverride == PriceOverrideType.NONE)
		{
			return Price.none();
		}
		else if (priceOverride == PriceOverrideType.BASE_PRICING_SYSTEM)
		{
			final LookupValue pricingSystem = lookups.lookupPricingSystem(pricingConditionsBreak.getBasePricingSystemId());
			if (pricingSystem == null)
			{
				logger.warn("Cannot extract pricing system from {}. Returning NO price", pricingConditionsBreak);
				return Price.none();
			}

			final BigDecimal basePriceAddAmt = pricingConditionsBreak.getBasePriceAddAmt();

			return Price.basePricingSystem(pricingSystem, basePriceAddAmt);
		}
		else if (priceOverride == PriceOverrideType.FIXED_PRICE)
		{
			return Price.fixedPrice(pricingConditionsBreak.getFixedPrice());
		}
		else
		{
			logger.warn("Unknown priceOverride={} of {}. Returning NO price", priceOverride, pricingConditionsBreak);
			return Price.none();
		}
	}

	private Optional<PricingConditionsRow> removeCurrentPricingConditionsRow(final ArrayList<PricingConditionsRow> rows)
	{
		for (final Iterator<PricingConditionsRow> it = rows.iterator(); it.hasNext();)
		{
			final PricingConditionsRow row = it.next();
			if (isCurrentConditions(row))
			{
				it.remove();
				return Optional.of(row);
			}
		}

		return Optional.empty();
	}

	private boolean isCurrentConditions(final PricingConditionsRow row)
	{
		return sourceDocumentLine != null
				&& row.getBpartnerId() == sourceDocumentLine.getBpartnerId()
				&& (sourceDocumentLine.isSOTrx() ? row.isCustomer() : row.isVendor());
	}

	private PricingConditionsRow createEditablePricingConditionsRowOrNull()
	{
		if (sourceDocumentLine == null)
		{
			return null;
		}

		final int discountSchemaId = bpartnerBL.getDiscountSchemaId(sourceDocumentLine.getBpartnerId(), sourceDocumentLine.isSOTrx());
		return PricingConditionsRow.builder()
				.lookups(lookups)
				.editable(true)
				//
				.bpartner(lookups.lookupBPartner(sourceDocumentLine.getBpartnerId()))
				.customer(sourceDocumentLine.isSOTrx())
				//
				.price(Price.fixedPrice(sourceDocumentLine.getPriceEntered()))
				//
				.paymentTerm(lookups.lookupPaymentTerm(sourceDocumentLine.getPaymentTermId()))
				.discount(sourceDocumentLine.getDiscount())
				//
				.discountSchemaId(discountSchemaId)
				.breakMatchCriteria(PricingConditionsBreakMatchCriteria.builder()
						.breakValue(BigDecimal.ZERO)
						.productId(sourceDocumentLine.getProductId())
						.productCategoryId(sourceDocumentLine.getProductCategoryId())
						.build())
				.build();
	}

	@lombok.Value
	@lombok.Builder
	private static class DiscountSchemaInfo
	{
		int discountSchemaId;
		int bpartnerId;
		boolean isSOTrx;
	}

	@FunctionalInterface
	public static interface PricingConditionsBreaksExtractor
	{
		Stream<PricingConditionsBreak> streamPricingConditionsBreaks(PricingConditions pricingConditions);
	}

	@lombok.Value
	@lombok.Builder
	public static final class SourceDocumentLine
	{
		int salesOrderLineId;
		boolean isSOTrx;

		int bpartnerId;

		int productId;
		int productCategoryId;

		BigDecimal priceEntered;
		BigDecimal discount;
		int paymentTermId;
	}

	//
	//
	//
	//
	//

	public static class PricingConditionsRowsLoaderBuilder
	{
		public PricingConditionsRowData load()
		{
			return build().load();
		}
	}
}
