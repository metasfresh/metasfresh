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

package de.metas.marketing.activecampaign;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.util.time.SystemTime;
import de.metas.marketing.base.CampaignService;
import de.metas.marketing.base.CampaignSyncService;
import de.metas.marketing.base.ContactPersonService;
import de.metas.marketing.base.PlatformClientFactoryRegistry;
import de.metas.marketing.base.PlatformClientService;
import de.metas.marketing.base.model.Campaign;
import de.metas.marketing.base.model.CampaignRepository;
import de.metas.marketing.base.model.ContactPersonRepository;
import de.metas.marketing.base.model.I_MKTG_Campaign;
import de.metas.marketing.base.model.I_MKTG_Platform;
import de.metas.marketing.base.model.PlatformId;
import de.metas.marketing.base.model.PlatformRepository;
import de.metas.marketing.base.model.SyncDirection;
import de.metas.marketing.base.model.X_MKTG_Platform;
import de.metas.marketing.base.spi.PlatformClientFactory;
import de.metas.marketing.base.sync.CampaignServiceSync;
import de.metas.marketing.base.sync.ContactPersonServiceSync;
import de.metas.marketing.gateway.activecampaign.ActiveCampaignClientFactory;
import de.metas.marketing.gateway.activecampaign.ActiveCampaignConfigRepository;
import de.metas.marketing.gateway.activecampaign.model.I_MKTG_ActiveCampaign_Config;
import de.metas.marketing.gateway.activecampaign.restapi.ResponseErrorHandler;
import de.metas.marketing.gateway.activecampaign.restapi.RestService;
import de.metas.marketing.gateway.activecampaign.restapi.exception.RateLimitService;
import de.metas.marketing.gateway.activecampaign.restapi.model.CampaignWrapper;
import de.metas.user.UserRepository;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

public class ActiveCampaignSyncServiceTest
{
	private PlatformId platformId;
	private CampaignServiceSync campaignServiceSync;
	private ContactPersonServiceSync contactPersonServiceSync;
	private CampaignSyncService campaignSyncService;
	private RestService restService;

	private final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();

	@Before
	public void before()
	{
		AdempiereTestHelper.get().init();

		restService = Mockito.mock(RestService.class);

		final PlatformRepository platformRepository = new PlatformRepository();
		final ContactPersonRepository contactPersonRepository = new ContactPersonRepository();
		final CampaignRepository campaignRepository = new CampaignRepository();
		final ContactPersonService contactPersonService = new ContactPersonService(new ContactPersonRepository(), new UserRepository());

		final CampaignService campaignService = new CampaignService(contactPersonRepository,
																	campaignRepository,
																	platformRepository);

		final ActiveCampaignClientFactory factory = new ActiveCampaignClientFactory(new ActiveCampaignConfigRepository(), restService);

		final PlatformClientService platformClientService = new PlatformClientService(platformRepository, new PlatformClientFactoryRegistry(Optional.of(new ArrayList<>(Collections.singleton((PlatformClientFactory)factory)))));
		campaignServiceSync = new CampaignServiceSync(campaignService, platformClientService);
		contactPersonServiceSync = new ContactPersonServiceSync(platformClientService, contactPersonService, campaignService);

		campaignSyncService = new CampaignSyncService(campaignServiceSync, contactPersonServiceSync);
	}

	@Test
	public void givenSyncDirectionLocalToRemote_whenSyncCampaigns_thenSyncLocalToRemoteCalled()
	{
		final String remoteCampaignWrpapper = "/de/metas/marketing/activecampaign/CampaignWrapper.json";
		final CampaignWrapper remoteCampaignWrapper = readResource(remoteCampaignWrpapper, CampaignWrapper.class);

		//spy


		createPlatformRecord("platform_1");
		createLocalConfig();
		createLocalCampaignRecord("campaign_1");

		campaignSyncService.syncCampaigns(platformId, SyncDirection.LOCAL_TO_REMOTE);

	}

	@NonNull
	private void createLocalConfig()
	{
		final I_MKTG_ActiveCampaign_Config configRecord = InterfaceWrapperHelper.newInstance(I_MKTG_ActiveCampaign_Config.class);

		configRecord.setMKTG_Platform_ID(platformId.getRepoId());
		configRecord.setApiKey("apiKey");
		configRecord.setBaseURL("url");

		InterfaceWrapperHelper.save(configRecord);
	}

	@NonNull
	private void createPlatformRecord(@NonNull final String namePrefix)
	{
		final I_MKTG_Platform platformRecord = InterfaceWrapperHelper.newInstance(I_MKTG_Platform.class);

		platformRecord.setName(namePrefix + " - " + SystemTime.asInstant());
		platformRecord.setMarketingPlatformGatewayId(X_MKTG_Platform.MARKETINGPLATFORMGATEWAYID_ActiveCampaign);

		InterfaceWrapperHelper.save(platformRecord);

		platformId = PlatformId.ofRepoId(platformRecord.getMKTG_Platform_ID());
	}

	@NonNull
	private Campaign createLocalCampaignRecord(@NonNull final String namePrefix)
	{
		final I_MKTG_Campaign campaignRecord = InterfaceWrapperHelper.newInstance(I_MKTG_Campaign.class);

		campaignRecord.setMKTG_Platform_ID(platformId.getRepoId());
		campaignRecord.setName(namePrefix + " - " + SystemTime.asInstant());

		InterfaceWrapperHelper.save(campaignRecord);

		InterfaceWrapperHelper.refresh(campaignRecord);

		final Campaign campaign = CampaignRepository.fromRecord(campaignRecord);
		System.out.println("Created campaign: " + campaign);

		return campaign;
	}

	private <T> T readResource(@NonNull final String path, @NonNull final Class<T> modelClass)
	{
		final InputStream inputStream = this.getClass().getResourceAsStream(path);

		try
		{
			return objectMapper.readValue(inputStream, modelClass);
		}
		catch (final IOException ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}
}
