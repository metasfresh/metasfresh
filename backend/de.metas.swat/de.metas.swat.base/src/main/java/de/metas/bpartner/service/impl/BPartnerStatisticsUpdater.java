package de.metas.bpartner.service.impl;

import de.metas.bpartner.service.IBPartnerStatisticsUpdater;
import de.metas.bpartner.service.async.spi.impl.C_BPartner_UpdateStatsFromBPartner;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;

public class BPartnerStatisticsUpdater implements IBPartnerStatisticsUpdater
{
	private final static String SYS_CONFIG_BPartnerStatisticsUpdater_ENABLED = "de.metas.bpartner.service.impl.BPartnerStatisticsUpdater.enabled";

	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	@Override
	public void updateBPartnerStatistics(@NonNull final BPartnerStatisticsUpdateRequest request)
	{
		if(isBPartnerStatsUpdateEnabled())
		{
			C_BPartner_UpdateStatsFromBPartner.createWorkpackage(request);
		}
	}

	private boolean isBPartnerStatsUpdateEnabled()
	{
		return sysConfigBL.getBooleanValue(SYS_CONFIG_BPartnerStatisticsUpdater_ENABLED, true);
	}
}
