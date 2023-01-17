/*
 * #%L
 * marketing-activecampaign
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.marketing.gateway.activecampaign;

import de.metas.common.util.Check;
import de.metas.common.util.time.SystemTime;
import de.metas.marketing.base.model.Campaign;
import de.metas.marketing.base.model.CampaignId;
import de.metas.marketing.base.model.CampaignRepository;
import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.ContactPersonId;
import de.metas.marketing.base.model.ContactPersonRepository;
import de.metas.marketing.base.model.I_MKTG_Campaign;
import de.metas.marketing.base.model.I_MKTG_Campaign_ContactPerson;
import de.metas.marketing.base.model.I_MKTG_ContactPerson;
import de.metas.marketing.base.model.I_MKTG_Platform;
import de.metas.marketing.base.model.PlatformId;
import de.metas.marketing.base.model.X_MKTG_Platform;
import de.metas.marketing.gateway.activecampaign.model.I_MKTG_ActiveCampaign_Config;
import lombok.Builder;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.model.InterfaceWrapperHelper;

import javax.annotation.Nullable;

@UtilityClass
public class CampaignTestUtil
{
	@NonNull
	public ActiveCampaignConfig createLocalConfig(@NonNull final PlatformId platformId)
	{
		final I_MKTG_ActiveCampaign_Config configRecord = InterfaceWrapperHelper.newInstance(I_MKTG_ActiveCampaign_Config.class);

		configRecord.setMKTG_Platform_ID(platformId.getRepoId());
		configRecord.setBaseURL("baseUrl");
		configRecord.setApiKey("apiKey");

		InterfaceWrapperHelper.save(configRecord);

		return ActiveCampaignConfigRepository.fromRecord(configRecord);
	}

	@NonNull
	public PlatformId createPlatformRecord(@NonNull final String namePrefix)
	{
		final I_MKTG_Platform platformRecord = InterfaceWrapperHelper.newInstance(I_MKTG_Platform.class);

		platformRecord.setName(namePrefix + " - " + SystemTime.asInstant());
		platformRecord.setMarketingPlatformGatewayId(X_MKTG_Platform.MARKETINGPLATFORMGATEWAYID_ActiveCampaign);

		InterfaceWrapperHelper.save(platformRecord);

		return PlatformId.ofRepoId(platformRecord.getMKTG_Platform_ID());
	}

	@Builder(builderMethodName = "createMKTGCampaignRecordBuilder")
	public Campaign createCampaignRecord(
			@NonNull final String namePrefix,
			@NonNull final PlatformId platformId,
			@Nullable final String remoteId)
	{
		final I_MKTG_Campaign campaignRecord = InterfaceWrapperHelper.newInstance(I_MKTG_Campaign.class);

		campaignRecord.setMKTG_Platform_ID(platformId.getRepoId());
		campaignRecord.setName(namePrefix);

		if (Check.isNotBlank(remoteId))
		{
			campaignRecord.setRemoteRecordId(remoteId);
		}

		InterfaceWrapperHelper.save(campaignRecord);

		return CampaignRepository.fromRecord(campaignRecord);
	}

	@Builder(builderMethodName = "createMKTGContactRecordBuilder")
	public ContactPerson createMKTGContactRecord(
			@NonNull final String email,
			@NonNull final PlatformId platformId,
			@Nullable final String remoteId)
	{
		final I_MKTG_ContactPerson contactPerson = InterfaceWrapperHelper.newInstance(I_MKTG_ContactPerson.class);

		contactPerson.setEMail(email);
		contactPerson.setMKTG_Platform_ID(platformId.getRepoId());

		if (Check.isNotBlank(remoteId))
		{
			contactPerson.setRemoteRecordId(remoteId);
		}

		InterfaceWrapperHelper.save(contactPerson);

		return ContactPersonRepository.toContactPerson(contactPerson);
	}

	@NonNull
	public void assignMKTGContactToCampaign(@NonNull final ContactPersonId contactPersonId, @NonNull final CampaignId campaignId)
	{
		final I_MKTG_Campaign_ContactPerson campaignContactPerson = InterfaceWrapperHelper.newInstance(I_MKTG_Campaign_ContactPerson.class);

		campaignContactPerson.setMKTG_ContactPerson_ID(contactPersonId.getRepoId());
		campaignContactPerson.setMKTG_Campaign_ID(campaignId.getRepoId());

		InterfaceWrapperHelper.save(campaignContactPerson);
	}
}
