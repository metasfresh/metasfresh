package org.adempiere.bpartner.service.async.spi.impl;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.bpartner.service.IBPartnerStats;
import org.adempiere.bpartner.service.IBPartnerStatsDAO;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;

import com.google.common.base.MoreObjects;

import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.async.spi.WorkpackagesOnCommitSchedulerTemplate;

/**
 * Update BPartner's TotalOpenBalance and SO_CreditUsed fields.
 * 
 * @author tsa
 *
 */
public class C_BPartner_UpdateStatsFromBPartner extends WorkpackageProcessorAdapter
{
	public static void createWorkpackage(final Properties ctx, final Set<Integer> bpartnerIds, final String trxName)
	{
		if (bpartnerIds == null || bpartnerIds.isEmpty())
		{
			return;
		}

		for (final Integer bpartnerId : bpartnerIds)
		{
			if (bpartnerId == null || bpartnerId <= 0)
			{
				continue;
			}

			SCHEDULER.schedule(BPartnerToUpdate.of(ctx, bpartnerId, trxName));
		}
	}

	private static final class BPartnerToUpdate
	{
		public static BPartnerToUpdate of(Properties ctx, int bpartnerId, String trxName)
		{
			return new BPartnerToUpdate(ctx, bpartnerId, trxName);
		}

		private final Properties ctx;
		private final String trxName;
		private final int bpartnerId;

		private BPartnerToUpdate(Properties ctx, int bpartnerId, String trxName)
		{
			super();
			this.ctx = ctx;
			this.bpartnerId = bpartnerId;
			this.trxName = trxName;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("bpartnerId", bpartnerId)
					.toString();
		}

		public Properties getCtx()
		{
			return ctx;
		}

		public String getTrxName()
		{
			return trxName;
		}

		public int getC_BPartner_ID()
		{
			return bpartnerId;
		}
	}

	private static final WorkpackagesOnCommitSchedulerTemplate<BPartnerToUpdate> SCHEDULER = new WorkpackagesOnCommitSchedulerTemplate<BPartnerToUpdate>(C_BPartner_UpdateStatsFromBPartner.class)
	{
		@Override
		protected boolean isEligibleForScheduling(final BPartnerToUpdate model)
		{
			return model != null && model.getC_BPartner_ID() > 0;
		};

		@Override
		protected Properties extractCtxFromItem(final BPartnerToUpdate item)
		{
			return item.getCtx();
		}

		@Override
		protected String extractTrxNameFromItem(final BPartnerToUpdate item)
		{
			return item.getTrxName();
		}

		@Override
		protected Object extractModelToEnqueueFromItem(final Collector collector, final BPartnerToUpdate item)
		{
			return new TableRecordReference(I_C_BPartner.Table_Name, item.getC_BPartner_ID());
		}
	};

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workpackage, final String localTrxName)
	{
		// Services
		final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
		final IBPartnerStatsDAO bpartnerStatsDAO = Services.get(IBPartnerStatsDAO.class);

		final List<I_C_BPartner> bpartners = queueDAO.retrieveItems(workpackage, I_C_BPartner.class, localTrxName);

		for (final I_C_BPartner bpartner : bpartners)
		{
			final IBPartnerStats stats = Services.get(IBPartnerStatsDAO.class).retrieveBPartnerStats(bpartner);

			bpartnerStatsDAO.updateTotalOpenBalance(stats);
			bpartnerStatsDAO.updateActualLifeTimeValue(stats);
			bpartnerStatsDAO.updateSOCreditUsed(stats);
			bpartnerStatsDAO.updateSOCreditStatus(stats);

		}

		return Result.SUCCESS;
	}
}
