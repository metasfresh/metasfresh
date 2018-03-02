package org.adempiere.bpartner.service.impl;

import org.adempiere.bpartner.service.IBPartnerStatisticsUpdater;
import org.adempiere.bpartner.service.IBPartnerStats;
import org.adempiere.bpartner.service.IBPartnerStatsBL;
import org.adempiere.bpartner.service.IBPartnerStatsDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;

import lombok.NonNull;

/**
 * Synchronous implementation; note that there is also an async implementation which sets up a work package to to the job later and in background.
 *
 */
public class BPartnerStatisticsUpdater implements IBPartnerStatisticsUpdater
{
	@Override
	public void updateBPartnerStatistics(@NonNull final BPartnerStatisticsUpdateRequest request)
	{
		final IBPartnerStatsDAO bpartnerStatsDAO = Services.get(IBPartnerStatsDAO.class);

		for (final int bpartnerId : request.getBpartnerIds())
		{
			final I_C_BPartner partner = InterfaceWrapperHelper.load(bpartnerId, I_C_BPartner.class);
			final IBPartnerStats stats = Services.get(IBPartnerStatsDAO.class).getCreateBPartnerStats(partner);

			if (request.isUpdateFromBPGroup())
			{
				Services.get(IBPartnerStatsBL.class).setCreditStatusBasedOnBPGroup(partner);
			}

			bpartnerStatsDAO.updateOpenItems(stats);
			bpartnerStatsDAO.updateActualLifeTimeValue(stats);
			bpartnerStatsDAO.updateSOCreditUsed(stats);
			bpartnerStatsDAO.updateSOCreditStatus(stats);
			bpartnerStatsDAO.updateCreditLimitIndicator(stats);
		}

	}
}
