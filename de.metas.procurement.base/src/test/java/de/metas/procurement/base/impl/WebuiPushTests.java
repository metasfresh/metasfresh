package de.metas.procurement.base.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_BPartner;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.metas.procurement.base.IAgentSyncBL;
import de.metas.procurement.base.model.I_AD_User;
import de.metas.procurement.sync.protocol.SyncBPartner;
import de.metas.procurement.sync.protocol.SyncBPartnersRequest;
import de.metas.procurement.sync.protocol.SyncInfoMessageRequest;
import de.metas.procurement.sync.protocol.SyncProductsRequest;
import de.metas.procurement.sync.protocol.SyncUser;
import de.metas.util.Services;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class WebuiPushTests
{
	private static final String BPARTNER_NAME = "myName";

	private static final String LANGUAGE = "mylanguage";

	@Rule
	public AdempiereTestWatcher testWatcher = new AdempiereTestWatcher();

	@Mocked
	IAgentSyncBL agentSync;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		Services.registerService(IAgentSyncBL.class, agentSync); // registering our mock
	}

	/**
	 * Sync a BPartner without any user.
	 */
	@Test
	public void testPushBPartnerWithoutContract_NoUsers()
	{
		final I_C_BPartner bpartner = createBPartner();

		performTestNoUsersDeletePartner(bpartner);
	}

	/**
	 * Sync a BPartner with a user, but that user has <code>MFProcurementUser='N'</code>.
	 */
	@Test
	public void testPushBPartnerWithoutContract_NoProcurementUsers1()
	{
		final I_C_BPartner bpartner = createBPartner();

		final I_AD_User user = InterfaceWrapperHelper.newInstance(I_AD_User.class);
		user.setC_BPartner(bpartner);
		user.setEMail("mail@email");
		user.setProcurementPassword("validPassword");
		user.setIsMFProcurementUser(false);
		InterfaceWrapperHelper.save(user);

		performTestNoUsersDeletePartner(bpartner);
	}

	/**
	 * Sync a BPartner with a user that has <code>MFProcurementUser='Y'</code>, but no email address.
	 */
	@Test
	public void testPushBPartnerWithoutContract_NoProcurementUsers2()
	{
		final I_C_BPartner bpartner = createBPartner();

		final I_AD_User user = InterfaceWrapperHelper.newInstance(I_AD_User.class);
		user.setC_BPartner(bpartner);
		user.setEMail(null);
		user.setProcurementPassword("validPassword");
		user.setIsMFProcurementUser(true);
		InterfaceWrapperHelper.save(user);

		performTestNoUsersDeletePartner(bpartner);
	}

	/**
	 * Sync a BPartner with a user that a valid procurement user, but is not a vendor.
	 */
	@Test
	public void testPushBPartnerWithoutContract_NotVendor()
	{
		final I_C_BPartner bpartner = createBPartner();
		bpartner.setIsVendor(false);
		InterfaceWrapperHelper.save(bpartner);

		final I_AD_User user = InterfaceWrapperHelper.newInstance(I_AD_User.class);
		user.setC_BPartner(bpartner);
		user.setEMail("mail@email");
		user.setProcurementPassword("validPassword");
		user.setIsMFProcurementUser(true);
		InterfaceWrapperHelper.save(user);

		performTestNoUsersDeletePartner(bpartner);
	}

	private I_C_BPartner createBPartner()
	{
		final I_C_BPartner bpartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		bpartner.setAD_Language(LANGUAGE);
		bpartner.setName(BPARTNER_NAME);
		bpartner.setIsVendor(true);
		InterfaceWrapperHelper.save(bpartner);
		return bpartner;
	}

	/**
	 * Invokes {@link WebuiPush#pushBPartnerAndUsers(I_C_BPartner)} and verifies, that only a delete-bpartner-sync without users is submitted.
	 *
	 * @param bpartner
	 */
	private void performTestNoUsersDeletePartner(final I_C_BPartner bpartner)
	{
		expectOnlySyncBPartnersCalled();

		new WebuiPush().pushBPartnerAndUsers(bpartner);

		// @formatter:off
		new Verifications()
		{{
				SyncBPartnersRequest syncBPartnersRequest;
				agentSync.syncBPartners(syncBPartnersRequest = withCapture());

				assertNotNull(syncBPartnersRequest);
				assertThat(syncBPartnersRequest.getBpartners().size(), is(1));

				final SyncBPartner syncBPartner = syncBPartnersRequest.getBpartners().get(0);
				assertThat(syncBPartner.getName(), is(bpartner.getName()));
				assertThat("syncBPartner.isSyncContracts", syncBPartner.isSyncContracts(), is(false));
				assertThat("syncBPartner.isDeleted", syncBPartner.isDeleted(), is(true));

				assertThat(syncBPartner.getUsers().isEmpty(), is(true));
				assertThat(syncBPartner.getContracts().isEmpty(), is(true));
		}};
		// @formatter:on
	}

	private void expectOnlySyncBPartnersCalled()
	{
		// @formatter:off
		new Expectations() {{
			agentSync.syncInfoMessage((SyncInfoMessageRequest)any); times=0;
			agentSync.syncProducts((SyncProductsRequest)any); times=0;
			agentSync.syncBPartners((SyncBPartnersRequest)any); times=1;
	    }};
	    // @formatter:on
	}

	/**
	 * Sync a valid BPartner with a valid user.
	 */
	@Test
	public void testPushBPartnerWithoutContract_with_user()
	{
		final I_C_BPartner bpartner = createBPartner();

		final I_AD_User user = InterfaceWrapperHelper.newInstance(I_AD_User.class);
		user.setC_BPartner(bpartner);
		user.setEMail("mail@email");
		user.setProcurementPassword("validPassword");
		user.setIsMFProcurementUser(true);
		InterfaceWrapperHelper.save(user);

		expectOnlySyncBPartnersCalled();

		new WebuiPush().pushBPartnerAndUsers(bpartner);

		// @formatter:off
		new Verifications()
		{{
			SyncBPartnersRequest syncBPartnersRequest;
			agentSync.syncBPartners(syncBPartnersRequest = withCapture());

			assertNotNull(syncBPartnersRequest);
			assertThat(syncBPartnersRequest.getBpartners().size(), is(1));

			final SyncBPartner syncBPartner = syncBPartnersRequest.getBpartners().get(0);
			assertThat(syncBPartner.getUsers().size(), is(1));
			assertThat(syncBPartner.getContracts().isEmpty(), is(true));
			assertThat(syncBPartner.getName(), is(bpartner.getName()));
			assertThat("syncBPartner.isSyncContracts", syncBPartner.isSyncContracts(), is(false));
			assertThat("syncBPartner.isDeleted", syncBPartner.isDeleted(), is(false));

			final SyncUser syncUser = syncBPartner.getUsers().get(0);
			assertThat(syncUser.isDeleted(), is(false));
			assertThat(syncUser.getEmail(), is(user.getEMail()));
			assertThat(syncUser.getPassword(), is(user.getProcurementPassword()));
			assertThat(syncUser.getLanguage(), is(bpartner.getAD_Language()));
		}};
		// @formatter:on
	}

}
