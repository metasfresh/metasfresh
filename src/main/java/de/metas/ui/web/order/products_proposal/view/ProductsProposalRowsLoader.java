package de.metas.ui.web.order.products_proposal.view;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.product.stats.BPartnerProductStats;
import de.metas.bpartner.product.stats.BPartnerProductStatsService;
import de.metas.currency.Amount;
import de.metas.currency.ICurrencyDAO;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.order.OrderId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.ProductPriceId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.ProductId;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdIntSequence;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;

/*
 * #%L
 * metasfresh-webui-api
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

final class ProductsProposalRowsLoader
{
	public static ProductsProposalRowsLoaderBuilder prepareFrom(final ProductsProposalRowsData rowsData)
	{
		return ProductsProposalRowsLoader.builder()
				.priceListVersionIds(rowsData.getPriceListVersionIds())
				// TODO .productIdsToExclude(rowsData.getPr)
				// TODO .bpartnerProductStatsService(bpartnerProductStatsService)
				.bpartnerId(rowsData.getBpartnerId())
				.soTrx(rowsData.getSoTrx())
				.orderId(rowsData.getOrderId());
	}

	// services
	private final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);
	private final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	private final ICurrencyDAO currenciesRepo = Services.get(ICurrencyDAO.class);
	private final BPartnerProductStatsService bpartnerProductStatsService;
	private final LookupDataSource productLookup;

	private final ImmutableSet<PriceListVersionId> priceListVersionIds;
	private final OrderId orderId;
	private final BPartnerId bpartnerId;
	private final SOTrx soTrx;
	private final ImmutableSet<ProductId> productIdsToExclude;

	private final ZonedDateTime now = SystemTime.asZonedDateTime();
	private final DocumentIdIntSequence nextRowIdSequence = DocumentIdIntSequence.newInstance();

	@Builder
	private ProductsProposalRowsLoader(
			@NonNull final BPartnerProductStatsService bpartnerProductStatsService,
			@NonNull @Singular final ImmutableSet<PriceListVersionId> priceListVersionIds,
			@Nullable final OrderId orderId,
			@NonNull final BPartnerId bpartnerId,
			@NonNull final SOTrx soTrx,
			@Nullable final Set<ProductId> productIdsToExclude)
	{
		Check.assumeNotEmpty(priceListVersionIds, "priceListVersionIds is not empty");

		this.bpartnerProductStatsService = bpartnerProductStatsService;
		productLookup = LookupDataSourceFactory.instance.searchInTableLookup(I_M_Product.Table_Name);

		this.priceListVersionIds = priceListVersionIds;

		this.orderId = orderId;
		this.bpartnerId = bpartnerId;
		this.soTrx = soTrx;
		this.productIdsToExclude = productIdsToExclude != null ? ImmutableSet.copyOf(productIdsToExclude) : ImmutableSet.of();
	}

	public ProductsProposalRowsData load()
	{
		List<ProductsProposalRow> rows = loadRows();
		rows = updateLastShipmentDays(rows);

		return ProductsProposalRowsData.builder()
				.nextRowIdSequence(nextRowIdSequence)
				.rows(rows)
				.priceListVersionIds(priceListVersionIds)
				.orderId(orderId)
				.bpartnerId(bpartnerId)
				.soTrx(soTrx)
				.build();
	}

	private List<ProductsProposalRow> loadRows()
	{
		return priceListVersionIds.stream()
				.flatMap(this::loadAndStreamRowsForPriceListVersionId)
				.sorted(Comparator.comparing(ProductsProposalRow::getProductName))
				.collect(ImmutableList.toImmutableList());
	}

	private Stream<ProductsProposalRow> loadAndStreamRowsForPriceListVersionId(final PriceListVersionId priceListVersionId)
	{
		final String currencyCode = getCurrencyCodeByPriceListVersionId(priceListVersionId);

		return priceListsRepo.retrieveProductPrices(priceListVersionId, productIdsToExclude)
				.map(productPriceRecord -> toProductsProposalRowOrNull(productPriceRecord, currencyCode))
				.filter(Predicates.notNull());
	}

	private ProductsProposalRow toProductsProposalRowOrNull(final I_M_ProductPrice record, final String currencyCode)
	{
		final LookupValue product = productLookup.findById(record.getM_Product_ID());
		if (!product.isActive())
		{
			return null;
		}

		final DocumentId id = nextRowIdSequence.nextDocumentId();
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(record.getM_AttributeSetInstance_ID());

		return ProductsProposalRow.builder()
				.id(id)
				.product(product)
				.asiDescription(attributeSetInstanceBL.getASIDescriptionById(asiId))
				.standardPrice(Amount.of(record.getPriceStd(), currencyCode))
				.qty(null)
				.lastShipmentDays(null) // will be populated later
				.productPriceId(ProductPriceId.ofRepoId(record.getM_ProductPrice_ID()))
				.build();
	}

	private String getCurrencyCodeByPriceListVersionId(final PriceListVersionId priceListVersionId)
	{
		final I_M_PriceList priceList = priceListsRepo.getPriceListByPriceListVersionId(priceListVersionId);
		return currenciesRepo.getISOCodeById(CurrencyId.ofRepoId(priceList.getC_Currency_ID()));
	}

	private List<ProductsProposalRow> updateLastShipmentDays(final List<ProductsProposalRow> rows)
	{
		final Set<ProductId> productIds = rows.stream().map(ProductsProposalRow::getProductId).collect(ImmutableSet.toImmutableSet());
		if (productIds.isEmpty())
		{
			return rows;
		}

		final Map<ProductId, BPartnerProductStats> statsByProductId = bpartnerProductStatsService.getByPartnerAndProducts(bpartnerId, productIds);

		return rows.stream()
				.map(row -> updateRowFromStats(row, statsByProductId.get(row.getProductId())))
				.collect(ImmutableList.toImmutableList());
	}

	private ProductsProposalRow updateRowFromStats(
			@NonNull final ProductsProposalRow row,
			@Nullable final BPartnerProductStats stats)
	{
		final Integer lastShipmentOrReceiptInDays = calculateLastShipmentOrReceiptInDays(stats);
		return row.withLastShipmentDays(lastShipmentOrReceiptInDays);
	}

	private Integer calculateLastShipmentOrReceiptInDays(final BPartnerProductStats stats)
	{
		if (stats == null)
		{
			return null;
		}

		final ZonedDateTime lastShipmentOrReceiptDate = extractLastShipmentOrReceiptDate(stats);
		if (lastShipmentOrReceiptDate == null)
		{
			return null;
		}

		return (int)Duration.between(lastShipmentOrReceiptDate, now).toDays();
	}

	private ZonedDateTime extractLastShipmentOrReceiptDate(final BPartnerProductStats stats)
	{
		if (soTrx.isSales())
		{
			return stats.getLastShipmentDate();
		}
		else
		{
			return stats.getLastReceiptDate();
		}
	}
}
