package de.metas.bpartner.product.stats;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.product.stats.BPartnerProductStats.LastInvoiceInfo;
import de.metas.invoice.InvoiceId;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.I_C_BPartner_Product_Stats;
import org.compiere.model.I_C_BPartner_Product_Stats_InOut_Online_v;
import org.compiere.model.I_C_BPartner_Product_Stats_Invoice_Online_V;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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
public class BPartnerProductStatsRepository
{
	public ImmutableMap<ProductId, BPartnerProductStats> getByPartnerAndProducts(@NonNull final BPartnerId bpartnerId,
			@NonNull final Set<ProductId> productIds)
	{
		Check.assumeNotEmpty(productIds, "productIds is not empty");

		return Services.get(IQueryBL.class).createQueryBuilderOutOfTrx(I_C_BPartner_Product_Stats.class)
				.addEqualsFilter(I_C_BPartner_Product_Stats.COLUMN_C_BPartner_ID, bpartnerId)
				.addInArrayFilter(I_C_BPartner_Product_Stats.COLUMN_M_Product_ID, productIds).create()
				.stream(I_C_BPartner_Product_Stats.class).map(BPartnerProductStatsRepository::toBPartnerProductStats)
				.collect(ImmutableMap.toImmutableMap(BPartnerProductStats::getProductId, Function.identity()));
	}

	public List<BPartnerProductStats> getByProductIds(@NonNull final Set<ProductId> productIds,
			@Nullable final CurrencyId currencyId)
	{
		Check.assumeNotEmpty(productIds, "productIds is not empty");

		final IQueryBuilder<I_C_BPartner_Product_Stats> bpartnerStatsQueryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_C_BPartner_Product_Stats.class)
				.addInArrayFilter(I_C_BPartner_Product_Stats.COLUMN_M_Product_ID, productIds);

		if (currencyId != null)
		{
			bpartnerStatsQueryBuilder.addEqualsFilter(I_C_BPartner_Product_Stats.COLUMNNAME_LastSalesPrice_Currency_ID,
					currencyId);
		}

		return bpartnerStatsQueryBuilder
				.create()
				.stream(I_C_BPartner_Product_Stats.class)
				.map(BPartnerProductStatsRepository::toBPartnerProductStats)
				.collect(ImmutableList.toImmutableList());
	}

	private static BPartnerProductStats toBPartnerProductStats(final I_C_BPartner_Product_Stats record)
	{
		return BPartnerProductStats.builder().repoId(record.getC_BPartner_Product_Stats_ID())
				.bpartnerId(BPartnerId.ofRepoId(record.getC_BPartner_ID()))
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				//
				.lastShipmentDate(TimeUtil.asZonedDateTime(record.getLastShipDate()))
				.lastReceiptDate(TimeUtil.asZonedDateTime(record.getLastReceiptDate()))
				.lastSalesInvoice(extractLastSalesInvoiceInfo(record))
				//
				.build();
	}

	private static LastInvoiceInfo extractLastSalesInvoiceInfo(final I_C_BPartner_Product_Stats record)
	{
		final InvoiceId invoiceId = InvoiceId.ofRepoIdOrNull(record.getLastSales_Invoice_ID());
		if (invoiceId == null)
		{
			return null;
		}

		final LocalDate invoiceDate = TimeUtil.asLocalDate(record.getLastSalesInvoiceDate());
		if (invoiceDate == null)
		{
			return null;
		}

		final BigDecimal priceValue = record.getLastSalesPrice();
		final CurrencyId currencyId = CurrencyId.ofRepoIdOrNull(record.getLastSalesPrice_Currency_ID());
		final Money price = Money.ofOrNull(priceValue, currencyId);
		if (price == null)
		{
			return null;
		}

		return LastInvoiceInfo.builder().invoiceId(invoiceId).invoiceDate(invoiceDate).price(price).build();
	}

	public void recomputeStatistics(@NonNull final RecomputeStatisticsRequest request)
	{
		final BPartnerId bpartnerId = request.getBpartnerId();
		final Set<ProductId> productIds = request.getProductIds();

		final Map<ProductId, BPartnerProductStats> existingStatsByProductId = getByPartnerAndProducts(bpartnerId,
				productIds);

		Map<ProductId, I_C_BPartner_Product_Stats_InOut_Online_v> inoutOnlineRecords = null;
		if (request.isRecomputeInOutStatistics())
		{
			inoutOnlineRecords = Maps.uniqueIndex(retrieveInOutOnlineStats(bpartnerId, productIds),
					record -> ProductId.ofRepoId(record.getM_Product_ID()));
		}

		Map<ProductId, I_C_BPartner_Product_Stats_Invoice_Online_V> salesInvoiceOnlineRecords = null;
		if (request.isRecomputeInvoiceStatistics())
		{
			salesInvoiceOnlineRecords = Maps.uniqueIndex(
					retrieveInvoiceOnlineStats(bpartnerId, productIds, SOTrx.SALES),
					record -> ProductId.ofRepoId(record.getM_Product_ID()));
		}

		final List<BPartnerProductStats> statsToSave = new ArrayList<>();

		for (final ProductId productId : productIds)
		{
			BPartnerProductStats stats = existingStatsByProductId.get(productId);
			if (stats == null)
			{
				stats = BPartnerProductStats.newInstance(bpartnerId, productId);
			}

			if (inoutOnlineRecords != null)
			{
				updateStatsFromInOutOnlineRecord(stats, inoutOnlineRecords.get(productId));
			}

			if (salesInvoiceOnlineRecords != null)
			{
				updateStatsFromSalesInvoiceOnlineRecord(stats, salesInvoiceOnlineRecords.get(productId));
			}

			statsToSave.add(stats);
		}

		saveAll(statsToSave);
	}

	private void updateStatsFromInOutOnlineRecord(@NonNull final BPartnerProductStats stats,
			@Nullable final I_C_BPartner_Product_Stats_InOut_Online_v record)
	{
		final Timestamp lastReceiptDate = record != null ? record.getLastReceiptDate() : null;
		stats.setLastReceiptDate(TimeUtil.asZonedDateTime(lastReceiptDate));

		final Timestamp lastShipDate = record != null ? record.getLastShipDate() : null;
		stats.setLastShipmentDate(TimeUtil.asZonedDateTime(lastShipDate));
	}

	private void updateStatsFromSalesInvoiceOnlineRecord(@NonNull final BPartnerProductStats stats,
			@Nullable final I_C_BPartner_Product_Stats_Invoice_Online_V record)
	{
		final LastInvoiceInfo lastSalesInvoice = extractLastSalesInvoiceInfo(record);
		stats.setLastSalesInvoice(lastSalesInvoice);
	}

	private static LastInvoiceInfo extractLastSalesInvoiceInfo(
			@Nullable final I_C_BPartner_Product_Stats_Invoice_Online_V record)
	{
		if (record == null)
		{
			return null;
		}

		final InvoiceId invoiceId = InvoiceId.ofRepoId(record.getC_Invoice_ID());
		if (invoiceId == null) // shall not happen
		{
			return null;
		}

		final LocalDate invoiceDate = TimeUtil.asLocalDate(record.getDateInvoiced());
		if (invoiceDate == null) // shall not happen
		{
			return null;
		}

		final BigDecimal priceValue = record.getPriceActual();
		final CurrencyId currencyId = CurrencyId.ofRepoIdOrNull(record.getC_Currency_ID());
		final Money price = Money.ofOrNull(priceValue, currencyId);
		if (price == null) // shall not happen
		{
			return null;
		}

		return LastInvoiceInfo.builder().invoiceId(invoiceId).invoiceDate(invoiceDate).price(price).build();
	}

	private List<I_C_BPartner_Product_Stats_Invoice_Online_V> retrieveInvoiceOnlineStats(
			@NonNull final BPartnerId bpartnerId, @NonNull final Set<ProductId> productIds,
			@NonNull final SOTrx soTrx)
	{
		Check.assumeNotEmpty(productIds, "productIds is not empty");

		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_C_BPartner_Product_Stats_Invoice_Online_V.class)
				.addEqualsFilter(I_C_BPartner_Product_Stats_Invoice_Online_V.COLUMN_C_BPartner_ID, bpartnerId)
				.addInArrayFilter(I_C_BPartner_Product_Stats_Invoice_Online_V.COLUMN_M_Product_ID, productIds)
				.addEqualsFilter(I_C_BPartner_Product_Stats_Invoice_Online_V.COLUMN_IsSOTrx, soTrx.toBoolean()).create()
				.list(I_C_BPartner_Product_Stats_Invoice_Online_V.class);
	}

	private List<I_C_BPartner_Product_Stats_InOut_Online_v> retrieveInOutOnlineStats(
			@NonNull final BPartnerId bpartnerId, @NonNull final Set<ProductId> productIds)
	{
		Check.assumeNotEmpty(productIds, "productIds is not empty");

		return Services.get(IQueryBL.class).createQueryBuilderOutOfTrx(I_C_BPartner_Product_Stats_InOut_Online_v.class)
				.addEqualsFilter(I_C_BPartner_Product_Stats_InOut_Online_v.COLUMN_C_BPartner_ID, bpartnerId)
				.addInArrayFilter(I_C_BPartner_Product_Stats_InOut_Online_v.COLUMN_M_Product_ID, productIds).create()
				.list(I_C_BPartner_Product_Stats_InOut_Online_v.class);
	}

	public void saveAll(@NonNull final Collection<BPartnerProductStats> stats)
	{
		stats.forEach(this::save);
	}

	public void save(@NonNull final BPartnerProductStats stats)
	{
		I_C_BPartner_Product_Stats record = null;

		if (stats.getRepoId() > 0)
		{
			record = load(stats.getRepoId(), I_C_BPartner_Product_Stats.class);
		}

		if (record == null)
		{
			record = newInstance(I_C_BPartner_Product_Stats.class);
			record.setAD_Org_ID(OrgId.ANY.getRepoId());
			record.setC_BPartner_ID(stats.getBpartnerId().getRepoId());
			record.setM_Product_ID(stats.getProductId().getRepoId());
		}

		record.setLastShipDate(TimeUtil.asTimestamp(stats.getLastShipmentDate()));
		record.setLastReceiptDate(TimeUtil.asTimestamp(stats.getLastReceiptDate()));

		updateRecordLastSalesInvoiceInfo(record, stats.getLastSalesInvoice());

		saveRecord(record);
		stats.setRepoId(record.getC_BPartner_Product_Stats_ID());
	}

	private static void updateRecordLastSalesInvoiceInfo(@NonNull final I_C_BPartner_Product_Stats record,
			@Nullable final LastInvoiceInfo lastSalesInvoiceInfo)
	{
		final InvoiceId invoiceId = lastSalesInvoiceInfo != null ? lastSalesInvoiceInfo.getInvoiceId() : null;
		record.setLastSales_Invoice_ID(InvoiceId.toRepoId(invoiceId));

		final LocalDate invoiceDate = lastSalesInvoiceInfo != null ? lastSalesInvoiceInfo.getInvoiceDate() : null;
		record.setLastSalesInvoiceDate(TimeUtil.asTimestamp(invoiceDate));

		final Money price = lastSalesInvoiceInfo != null ? lastSalesInvoiceInfo.getPrice() : null;
		record.setLastSalesPrice(price != null ? price.toBigDecimal() : null);
		record.setLastSalesPrice_Currency_ID(price != null ? price.getCurrencyId().getRepoId() : -1);
	}
}
