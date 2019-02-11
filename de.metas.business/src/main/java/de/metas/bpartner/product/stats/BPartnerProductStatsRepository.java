package de.metas.bpartner.product.stats;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.service.OrgId;
import org.compiere.model.I_C_BPartner_Product_Stats;
import org.compiere.model.I_C_BPartner_Product_Stats_Online_V;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

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
class BPartnerProductStatsRepository
{
	public ImmutableMap<ProductId, BPartnerProductStats> getByPartnerAndProducts(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final Set<ProductId> productIds)
	{
		Check.assumeNotEmpty(productIds, "productIds is not empty");

		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_C_BPartner_Product_Stats.class)
				.addEqualsFilter(I_C_BPartner_Product_Stats.COLUMN_C_BPartner_ID, bpartnerId)
				.addInArrayFilter(I_C_BPartner_Product_Stats.COLUMN_M_Product_ID, productIds)
				.create()
				.stream(I_C_BPartner_Product_Stats.class)
				.map(record -> toBPartnerProductStats(record))
				.collect(ImmutableMap.toImmutableMap(BPartnerProductStats::getProductId, Function.identity()));
	}

	private static BPartnerProductStats toBPartnerProductStats(final I_C_BPartner_Product_Stats record)
	{
		return BPartnerProductStats.builder()
				.repoId(record.getC_BPartner_Product_Stats_ID())
				.bpartnerId(BPartnerId.ofRepoId(record.getC_BPartner_ID()))
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				//
				.lastShipmentDate(TimeUtil.asZonedDateTime(record.getLastShipDate()))
				.lastReceiptDate(TimeUtil.asZonedDateTime(record.getLastReceiptDate()))
				//
				.build();
	}

	public void refreshByPartnerAndProducts(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final Set<ProductId> productIds)
	{
		Check.assumeNotEmpty(productIds, "productIds is not empty");

		final HashMap<ProductId, BPartnerProductStats> existingStatsByProductId = new HashMap<>(getByPartnerAndProducts(bpartnerId, productIds));

		final List<BPartnerProductStats> statsToSave = new ArrayList<>();

		final List<I_C_BPartner_Product_Stats_Online_V> onlineRecords = retrieveOnlineStats(bpartnerId, productIds);
		for (final I_C_BPartner_Product_Stats_Online_V onlineRecord : onlineRecords)
		{
			final ProductId productId = ProductId.ofRepoId(onlineRecord.getM_Product_ID());
			BPartnerProductStats stats = existingStatsByProductId.remove(productId);
			if (stats == null)
			{
				stats = BPartnerProductStats.newInstance(bpartnerId, productId);
			}

			updateStatsFromOnlineRecord(stats, onlineRecord);

			statsToSave.add(stats);
		}

		saveAll(statsToSave);
		deleteAll(existingStatsByProductId.values());
	}

	private List<I_C_BPartner_Product_Stats_Online_V> retrieveOnlineStats(final BPartnerId bpartnerId, final Set<ProductId> productIds)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_C_BPartner_Product_Stats_Online_V.class)
				.addEqualsFilter(I_C_BPartner_Product_Stats_Online_V.COLUMN_C_BPartner_ID, bpartnerId)
				.addInArrayFilter(I_C_BPartner_Product_Stats_Online_V.COLUMN_M_Product_ID, productIds)
				.create()
				.list(I_C_BPartner_Product_Stats_Online_V.class);
	}

	private void updateStatsFromOnlineRecord(final BPartnerProductStats stats, final I_C_BPartner_Product_Stats_Online_V onlineRecord)
	{
		Check.assumeEquals(stats.getBpartnerId().getRepoId(), onlineRecord.getC_BPartner_ID(), "partner shall match: {}, {}", stats, onlineRecord);
		Check.assumeEquals(stats.getProductId().getRepoId(), onlineRecord.getM_Product_ID(), "product shall match: {}, {}", stats, onlineRecord);

		stats.setLastReceiptDate(TimeUtil.asZonedDateTime(onlineRecord.getLastReceiptDate()));
		stats.setLastShipmentDate(TimeUtil.asZonedDateTime(onlineRecord.getLastShipDate()));
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

		saveRecord(record);
		stats.setRepoId(record.getC_BPartner_Product_Stats_ID());
	}

	private void deleteAll(@NonNull final Collection<BPartnerProductStats> stats)
	{
		if (stats.isEmpty())
		{
			return;
		}

		final ImmutableSet<Integer> repoIds = stats.stream()
				.map(BPartnerProductStats::getRepoId)
				.filter(repoId -> repoId > 0)
				.collect(ImmutableSet.toImmutableSet());

		Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_C_BPartner_Product_Stats.class)
				.addInArrayFilter(I_C_BPartner_Product_Stats.COLUMN_C_BPartner_Product_Stats_ID, repoIds)
				.create()
				.deleteDirectly();
	}
}
