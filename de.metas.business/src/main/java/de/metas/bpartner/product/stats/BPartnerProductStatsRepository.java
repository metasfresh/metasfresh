package de.metas.bpartner.product.stats;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_BPartner_Product_Stats;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import de.metas.bpartner.BPartnerId;
import de.metas.product.ProductId;
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
	public BPartnerProductStats getOrCreateByPartnerAndProduct(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final ProductId productId)
	{
		final BPartnerProductStats existingStats = getByPartnerAndProduct(bpartnerId, productId);
		if (existingStats != null)
		{
			return existingStats;
		}

		return BPartnerProductStats.newInstance(bpartnerId, productId);
	}

	public BPartnerProductStats getByPartnerAndProduct(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final ProductId productId)
	{
		final I_C_BPartner_Product_Stats record = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BPartner_Product_Stats.class)
				.addEqualsFilter(I_C_BPartner_Product_Stats.COLUMN_C_BPartner_ID, bpartnerId)
				.addEqualsFilter(I_C_BPartner_Product_Stats.COLUMN_M_Product_ID, productId)
				.create()
				.firstOnly(I_C_BPartner_Product_Stats.class);

		return record != null ? toBPartnerProductStats(record) : null;
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
			record.setC_BPartner_ID(stats.getBpartnerId().getRepoId());
			record.setM_Product_ID(stats.getProductId().getRepoId());
		}

		record.setLastShipDate(TimeUtil.asTimestamp(stats.getLastShipmentDate()));
		record.setLastReceiptDate(TimeUtil.asTimestamp(stats.getLastReceiptDate()));

		saveRecord(record);
		stats.setRepoId(record.getC_BPartner_Product_Stats_ID());
	}
}
