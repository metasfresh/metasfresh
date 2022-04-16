package de.metas.marketing.base.process;

import de.metas.marketing.base.CampaignService;
import de.metas.marketing.base.ContactPersonService;
import de.metas.marketing.base.PlatformClientService;
import de.metas.marketing.base.model.Campaign;
import de.metas.marketing.base.model.CampaignId;
import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.ContactPersonRepository;
import de.metas.marketing.base.model.I_MKTG_Campaign;
import de.metas.marketing.base.model.SyncResult;
import de.metas.marketing.base.spi.PlatformClient;
import de.metas.process.JavaProcess;
import de.metas.util.Check;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

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

public abstract class MKTG_ContactPerson_Platform_Base extends JavaProcess
{
	private final CampaignService campaignService = SpringContextHolder.instance.getBean(CampaignService.class);
	private final ContactPersonService contactPersonService = SpringContextHolder.instance.getBean(ContactPersonService.class);
	private final PlatformClientService platformClientService = SpringContextHolder.instance.getBean(PlatformClientService.class);

	@Override
	protected String doIt()
	{
		Check.errorUnless(I_MKTG_Campaign.Table_Name.equals(getTableName()),
				"This process only with tableName={} MKTG_Campaign records; tableName",
				I_MKTG_Campaign.Table_Name, getTableName());

		final CampaignId campaignId = CampaignId.ofRepoId(getRecord_ID());

		final Campaign campaign = campaignService.getById(campaignId);
		final PlatformClient platformClient = platformClientService.createPlatformClient(campaign.getPlatformId());

		final List<ContactPerson> contactsToSync = contactPersonService.getByCampaignId(campaignId);
		final List<? extends SyncResult> syncResults = invokeClient(platformClient, campaign, contactsToSync);

		final List<ContactPerson> savedContacts = contactPersonService.saveSyncResults(syncResults);
		campaignService.addContactPersonsToCampaign(savedContacts, campaignId);

		return MSG_OK;
	}

	protected abstract List<? extends SyncResult> invokeClient(
			@NonNull final PlatformClient platformClient,
			@NonNull final Campaign campaign,
			@NonNull final List<ContactPerson> contactPersons);
}
