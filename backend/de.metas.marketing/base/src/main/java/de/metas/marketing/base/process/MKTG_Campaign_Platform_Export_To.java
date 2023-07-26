package de.metas.marketing.base.process;

import java.util.List;

import de.metas.marketing.base.model.Campaign;
import de.metas.marketing.base.model.SyncResult;
import de.metas.marketing.base.spi.PlatformClient;
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

public class MKTG_Campaign_Platform_Export_To extends MKTG_Campaign_Platform_Base
{
	@Override
	protected List<? extends SyncResult> invokeClientMethod(
			@NonNull final List<Campaign> locallyExistingCampaigns,
			@NonNull final PlatformClient platformClient)
	{
		return platformClient.syncCampaignsLocalToRemote(locallyExistingCampaigns);
	}
}
