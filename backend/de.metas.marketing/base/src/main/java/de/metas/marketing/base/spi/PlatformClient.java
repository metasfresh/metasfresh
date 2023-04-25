package de.metas.marketing.base.spi;

import de.metas.marketing.base.model.Campaign;
import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.LocalToRemoteSyncResult;
import de.metas.marketing.base.model.RemoteToLocalSyncResult;
import lombok.NonNull;

import java.util.List;

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

public interface PlatformClient
{
	List<LocalToRemoteSyncResult> syncCampaignsLocalToRemote(List<Campaign> campaigns);

	List<LocalToRemoteSyncResult> syncContactPersonsLocalToRemote(Campaign campaign, List<ContactPerson> contactPersons);

	List<RemoteToLocalSyncResult> syncContactPersonsRemoteToLocal(Campaign campaign, List<ContactPerson> contactPersons);

	List<RemoteToLocalSyncResult> syncCampaignsRemoteToLocal(List<Campaign> campaigns);

	void sendEmailActivationForm(
			@NonNull String formId,
			@NonNull String email);
}
