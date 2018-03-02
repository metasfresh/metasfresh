package org.adempiere.bpartner.service.impl;

import org.adempiere.bpartner.service.IBPartnerStatisticsUpdater;
import org.adempiere.bpartner.service.async.spi.impl.C_BPartner_UpdateStatsFromBPartner;

import lombok.NonNull;

public class AsyncBPartnerStatisticsUpdater implements IBPartnerStatisticsUpdater
{

	@Override
	public void updateBPartnerStatistics(@NonNull final BPartnerStatisticsUpdateRequest request)
	{
		C_BPartner_UpdateStatsFromBPartner.createWorkpackage(request);
	}
}
