/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.fresh.setup.process;

import de.metas.cache.CacheMgt;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.TranslatableStrings;
import de.metas.organization.OrgId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.X_AD_OrgInfo;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Client Quick Setup must explain a missing or deactivated organization partner
 * ({@code AD_OrgBP_ID}) instead of failing with a {@link NullPointerException}.
 */
class ClientSetupTest
{
	private static final String ORG_NAME = "QuickSetupOrg";
	private static final String MSG_NO_ACTIVE_ORG_BPARTNER = "ClientSetup_NoActiveOrgBPartner";

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		AdempiereTestHelper.setupContext_AD_Client_IfNotSet();
		AdempiereTestHelper.createClientInfo();

		final I_AD_Org org = newInstance(I_AD_Org.class);
		org.setAD_Org_ID(OrgId.MAIN.getRepoId());
		org.setName(ORG_NAME);
		org.setValue(ORG_NAME);
		org.setIsActive(true);
		saveRecord(org);

		final I_AD_OrgInfo orgInfo = newInstance(I_AD_OrgInfo.class);
		orgInfo.setAD_Org_ID(OrgId.MAIN.getRepoId());
		orgInfo.setStoreCreditCardData(X_AD_OrgInfo.STORECREDITCARDDATA_NichtSpeichern);
		saveRecord(orgInfo);
	}

	@Test
	void inactiveLinkedPartner_throwsUserValidationError()
	{
		saveLinkedPartner(false);
		CacheMgt.get().reset();

		assertThatThrownBy(() -> ClientSetup.newInstance(Env.getCtx()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(MSG_NO_ACTIVE_ORG_BPARTNER)
				.matches(throwable -> ((AdempiereException)throwable).isUserValidationError())
				.satisfies(ClientSetupTest::assertOrgNameIsMessageParameter);
	}

	@Test
	void noLinkedPartner_throwsUserValidationError()
	{
		CacheMgt.get().reset();

		assertThatThrownBy(() -> ClientSetup.newInstance(Env.getCtx()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(MSG_NO_ACTIVE_ORG_BPARTNER)
				.matches(throwable -> ((AdempiereException)throwable).isUserValidationError())
				.satisfies(ClientSetupTest::assertOrgNameIsMessageParameter);
	}

	@Test
	void activeLinkedPartner_getsPastTheMissingPartnerCheck()
	{
		saveLinkedPartner(true);
		CacheMgt.get().reset();

		// Construction still fails later on the existing org contact / bank account assumptions.
		assertThatThrownBy(() -> ClientSetup.newInstance(Env.getCtx()))
				.hasMessageNotContaining(MSG_NO_ACTIVE_ORG_BPARTNER);
	}

	/**
	 * The organization name is the message parameter. Unit-test {@code Msg} returns the raw key
	 * with no {@code {0}}, so {@code MessageFormat} can drop that argument from {@code getMessage()}.
	 */
	private static void assertOrgNameIsMessageParameter(final Throwable throwable)
	{
		assertThat(AdempiereException.extractMessageTrl(throwable))
				.isEqualTo(TranslatableStrings.adMessage(AdMessageKey.of(MSG_NO_ACTIVE_ORG_BPARTNER), ORG_NAME));
	}

	private static void saveLinkedPartner(final boolean active)
	{
		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		partner.setAD_OrgBP_ID(OrgId.MAIN.getRepoId());
		partner.setName("org-partner");
		partner.setValue("org-partner");
		partner.setIsActive(active);
		saveRecord(partner);
	}
}
