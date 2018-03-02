package org.adempiere.bpartner.service.impl;

import java.util.Set;

import org.adempiere.bpartner.service.IBPartnerStatisticsUpdater;
import org.adempiere.bpartner.service.async.spi.impl.C_BPartner_UpdateStatsFromBPartner;

public class AsyncBPartnerStatisticsUpdater implements IBPartnerStatisticsUpdater
{

	@Override
	public void updateBPartnerStatistics(final Set<Integer> bpartnerIds)
	{
		C_BPartner_UpdateStatsFromBPartner.createWorkpackage(bpartnerIds);
	}
}
