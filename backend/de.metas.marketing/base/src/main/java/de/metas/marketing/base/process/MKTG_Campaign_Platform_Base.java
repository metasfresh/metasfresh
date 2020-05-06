package de.metas.marketing.base.process;

import java.util.List;

import org.compiere.Adempiere;

import de.metas.marketing.base.PlatformClientService;
import de.metas.marketing.base.model.Campaign;
import de.metas.marketing.base.model.CampaignRepository;
import de.metas.marketing.base.model.I_MKTG_Platform;
import de.metas.marketing.base.model.PlatformId;
import de.metas.marketing.base.model.SyncResult;
import de.metas.marketing.base.spi.PlatformClient;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.Process;
import lombok.NonNull;

/*
 * #%L
 * marketing-base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Process(requiresCurrentRecordWhenCalledFromGear = false)
public abstract class MKTG_Campaign_Platform_Base extends JavaProcess
{
	private final CampaignRepository campaignRepository = Adempiere.getBean(CampaignRepository.class);

	private final PlatformClientService platformClientService = Adempiere.getBean(PlatformClientService.class);

	@Param(mandatory = true, parameterName = I_MKTG_Platform.COLUMNNAME_MKTG_Platform_ID)
	private int platformRepoId;

	@Override
	protected final String doIt() throws Exception
	{
		final PlatformId platformId = PlatformId.ofRepoId(platformRepoId);

		final PlatformClient platformClient = platformClientService.createPlatformClient(platformId);

		final List<Campaign> locallyExistingCampaigns = campaignRepository.getByPlatformId(platformId);
		final List<? extends SyncResult> syncResults = invokeClientMethod(locallyExistingCampaigns, platformClient);

		for (final SyncResult syncResult : syncResults)
		{
			campaignRepository.saveCampaignSyncResult(syncResult);
		}

		return MSG_OK;
	}

	protected abstract List<? extends SyncResult> invokeClientMethod(
			@NonNull List<Campaign> locallyExistingCampaigns,
			@NonNull PlatformClient platformClient);
}
