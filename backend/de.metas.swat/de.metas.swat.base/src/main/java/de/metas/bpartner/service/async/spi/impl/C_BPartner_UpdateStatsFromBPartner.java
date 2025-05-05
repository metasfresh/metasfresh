/*
 * #%L
 * de.metas.swat.base
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

package de.metas.bpartner.service.async.spi.impl;

import com.google.common.collect.ImmutableMap;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.async.spi.WorkpackagesOnCommitSchedulerTemplate;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPartnerStats;
import de.metas.bpartner.service.IBPartnerStatisticsUpdater.BPartnerStatisticsUpdateRequest;
import de.metas.bpartner.service.impl.BPartnerStatsService;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.Env;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Update BPartner's TotalOpenBalance and SO_CreditUsed fields.
 *
 * @author tsa
 */
public class C_BPartner_UpdateStatsFromBPartner extends WorkpackageProcessorAdapter
{
	final static private String PARAM_ALSO_RESET_CREDITSTATUS_FROM_BP_GROUP = "alsoResetCreditStatusFromBPGroup";

	private final BPartnerStatsService bPartnerStatsService = SpringContextHolder.instance.getBean(BPartnerStatsService.class);

	public static void createWorkpackage(@NonNull final BPartnerStatisticsUpdateRequest request)
	{
		for (final int bpartnerId : request.getBpartnerIds())
		{
			SCHEDULER.schedule(BPartnerToUpdate.builder()
									   .bpartnerId(bpartnerId)
									   .alsoResetCreditStatusFromBPGroup(request.isAlsoResetCreditStatusFromBPGroup())
									   .build());
		}
	}

	@Builder
	@Value
	private static final class BPartnerToUpdate
	{
		private final int bpartnerId;
		private final boolean alsoResetCreditStatusFromBPGroup;
	}

	private static final WorkpackagesOnCommitSchedulerTemplate<BPartnerToUpdate> SCHEDULER = new WorkpackagesOnCommitSchedulerTemplate<BPartnerToUpdate>(C_BPartner_UpdateStatsFromBPartner.class)
	{
		@Override
		protected Properties extractCtxFromItem(final BPartnerToUpdate item)
		{
			return Env.getCtx();
		}

		@Override
		protected String extractTrxNameFromItem(final BPartnerToUpdate item)
		{
			return ITrx.TRXNAME_ThreadInherited;
		}

		@Override
		protected Object extractModelToEnqueueFromItem(final Collector collector, final BPartnerToUpdate item)
		{
			return TableRecordReference.of(I_C_BPartner.Table_Name, item.getBpartnerId());
		}

		@Override
		protected Map<String, Object> extractParametersFromItem(BPartnerToUpdate item)
		{
			return ImmutableMap.of(PARAM_ALSO_RESET_CREDITSTATUS_FROM_BP_GROUP, item.isAlsoResetCreditStatusFromBPGroup());
		}
	};

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workpackage, final String localTrxName)
	{
		final List<I_C_BPartner> bpartners = retrieveAllItems(I_C_BPartner.class);
		final boolean alsoSetCreditStatusBaseOnBPGroup = getParameters().getParameterAsBool(PARAM_ALSO_RESET_CREDITSTATUS_FROM_BP_GROUP);

		for (final I_C_BPartner bpartner : bpartners)
		{
			if (alsoSetCreditStatusBaseOnBPGroup)
			{
				bPartnerStatsService.resetSOCreditStatusFromBPGroup(bpartner);
			}

			final BPartnerId bPartnerId = BPartnerId.ofRepoId(bpartner.getC_BPartner_ID());
			final BPartnerStats stats = bPartnerStatsService.getCreateBPartnerStats(bPartnerId);
			bPartnerStatsService.updateBPartnerStatistics(stats);
		}

		return Result.SUCCESS;
	}

}
