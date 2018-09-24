package de.metas.bpartner.service.impl;

import de.metas.bpartner.service.IBPartnerStatisticsUpdater;
import de.metas.bpartner.service.async.spi.impl.C_BPartner_UpdateStatsFromBPartner;
import lombok.NonNull;

public class BPartnerStatisticsUpdater implements IBPartnerStatisticsUpdater
{

	@Override
	public void updateBPartnerStatistics(@NonNull final BPartnerStatisticsUpdateRequest request)
	{
		C_BPartner_UpdateStatsFromBPartner.createWorkpackage(request);
	}
}
