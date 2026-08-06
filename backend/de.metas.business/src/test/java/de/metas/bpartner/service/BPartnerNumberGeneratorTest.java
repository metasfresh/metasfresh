package de.metas.bpartner.service;

/*
 * #%L
 * de.metas.business
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

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPartnerNumberContext.Kind;
import de.metas.document.IDocumentSequenceDAO;
import de.metas.document.sequence.DocSequenceId;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.interfaces.I_C_BPartner;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link BPartnerNumberGenerator}.
 * <p>
 * The DB-executing override path is tested via a mocked {@link BPartnerNumberService}.
 * No live DB required.
 */
class BPartnerNumberGeneratorTest
{
	private static final int AD_CLIENT_ID = 1000001;
	private static final int AD_ORG_ID = 1000000;
	private static final ClientAndOrgId CLIENT_AND_ORG_ID = ClientAndOrgId.ofClientAndOrg(
			ClientId.ofRepoId(AD_CLIENT_ID),
			OrgId.ofRepoId(AD_ORG_ID));

	private ISysConfigBL sysConfigBL;
	private BPartnerNumberService numberService;
	private BPartnerNumberGenerator generator;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		sysConfigBL = mock(ISysConfigBL.class);
		Services.registerService(ISysConfigBL.class, sysConfigBL);

		// The real BPartnerNumberService resolves IDocumentSequenceDAO via Services.get; register a mock so the
		// validation-only tests (which build a real service — see ServiceCallOverrideFunction) can construct it
		// without a Spring context. IDocumentNoBuilderFactory is constructor-injected (a mock is passed in below).
		// Either way the function-name guard fires before any collaborator is touched.
		Services.registerService(IDocumentSequenceDAO.class, mock(IDocumentSequenceDAO.class));

		numberService = mock(BPartnerNumberService.class);
		generator = new BPartnerNumberGenerator(numberService);
	}

	private BPartnerNumberContext debtorCtx()
	{
		return BPartnerNumberContext.builder()
				.clientId(ClientId.ofRepoId(AD_CLIENT_ID))
				.orgId(OrgId.ofRepoId(AD_ORG_ID))
				.bPartnerId(BPartnerId.ofRepoId(42))
				.isCustomer(true)
				.isVendor(false)
				.isCompany(false)
				.kind(Kind.DEBTOR)
				.build();
	}

	private BPartnerNumberContext creditorCtx()
	{
		return BPartnerNumberContext.builder()
				.clientId(ClientId.ofRepoId(AD_CLIENT_ID))
				.orgId(OrgId.ofRepoId(AD_ORG_ID))
				.bPartnerId(BPartnerId.ofRepoId(43))
				.isCustomer(false)
				.isVendor(true)
				.isCompany(false)
				.kind(Kind.CREDITOR)
				.build();
	}

	private I_C_BPartner bpartner(final boolean isCustomer, final boolean isVendor)
	{
		final I_C_BPartner bp = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		// AD_Client_ID has no model setter (context-driven) — set it generically, as other tests do.
		InterfaceWrapperHelper.setValue(bp, I_C_BPartner.COLUMNNAME_AD_Client_ID, AD_CLIENT_ID);
		bp.setAD_Org_ID(AD_ORG_ID);
		bp.setIsCustomer(isCustomer);
		bp.setIsVendor(isVendor);
		return bp;
	}

	/**
	 * Unit-level coverage of the resolver's branch selection (override / sequence / no-config) and the
	 * SQL-injection guard. The end-to-end behaviour — the interceptor firing on a real {@code C_BPartner}
	 * save, drawing from a real sequence, and a customer-AND-vendor partner getting BOTH numbers — is
	 * proven by the cucumber feature {@code bpartner_number_generation.feature}, not here against mocks.
	 */
	@Nested
	class GenerateNumbers
	{
		@Test
		void returnsEmpty_whenNoConfig()
		{
			// no sysconfig stubs → all return null / 0

			final BPartnerNumbers result = generator.generateNumbers(bpartner(true, false));

			assertThat(result.getDebtorId()).isNull();
			assertThat(result.getCreditorId()).isNull();
			verify(numberService, never()).drawNext(any(), any());
		}

		@Test
		void generatesDebtor_whenDebtorSeqConfigured()
		{
			when(sysConfigBL.getIntValue(
					eq(BPartnerNumberGenerator.SYSCONFIG_DEBTOR_SEQ),
					eq(-1),
					eq(CLIENT_AND_ORG_ID)))
					.thenReturn(540123);

			when(numberService.drawNext(ClientId.ofRepoId(AD_CLIENT_ID), DocSequenceId.ofRepoId(540123))).thenReturn(7);

			final BPartnerNumbers result = generator.generateNumbers(bpartner(true, false));

			assertThat(result.getNo(Kind.DEBTOR)).hasValue(7);
			assertThat(result.getNo(Kind.CREDITOR)).isEmpty();
		}

		@Test
		void generatesCreditor_whenCreditorSeqConfigured()
		{
			when(sysConfigBL.getIntValue(
					eq(BPartnerNumberGenerator.SYSCONFIG_CREDITOR_SEQ),
					eq(-1),
					eq(CLIENT_AND_ORG_ID)))
					.thenReturn(540456);

			when(numberService.drawNext(ClientId.ofRepoId(AD_CLIENT_ID), DocSequenceId.ofRepoId(540456))).thenReturn(55);

			final BPartnerNumbers result = generator.generateNumbers(bpartner(false, true));

			assertThat(result.getNo(Kind.CREDITOR)).hasValue(55);
			assertThat(result.getNo(Kind.DEBTOR)).isEmpty();
		}

		@Test
		void takesOverrideBranch_whenOverrideConfigured()
		{
			when(sysConfigBL.getValue(
					eq(BPartnerNumberGenerator.SYSCONFIG_OVERRIDE),
					eq(CLIENT_AND_ORG_ID)))
					.thenReturn("fn_bpartner_no");

			when(numberService.callOverrideFunction(eq("fn_bpartner_no"), any(BPartnerNumberContext.class), isNull()))
					.thenReturn(42);

			final BPartnerNumbers result = generator.generateNumbers(bpartner(true, false));

			assertThat(result.getNo(Kind.DEBTOR)).hasValue(42);
			verify(numberService, never()).drawNext(any(), any());
		}

		/**
		 * The SQL-injection guard lives in {@link BPartnerNumberService#callOverrideFunction}.
		 * Validation fires before any DB access, so the real DAO throws without needing a database.
		 * The generator must propagate that exception.
		 */
		@Test
		void throwsOnInvalidOverrideName_rejectingInjection()
		{
			final String badName = "foo; DROP TABLE ad_sequence";
			when(sysConfigBL.getValue(
					eq(BPartnerNumberGenerator.SYSCONFIG_OVERRIDE),
					eq(CLIENT_AND_ORG_ID)))
					.thenReturn(badName);

			final BPartnerNumberGenerator gen = new BPartnerNumberGenerator(new BPartnerNumberService(mock(IDocumentNoBuilderFactory.class)));

			assertThatThrownBy(() -> gen.generateNumbers(bpartner(true, false)))
					.isInstanceOf(IllegalArgumentException.class)
					.hasMessageContaining(badName);
		}
	}

	@Nested
	class ReserveExplicit
	{
		@Test
		void isNoOp_whenNoConfig()
		{
			// no sysconfig stubs → no-op

			generator.reserveExplicit(debtorCtx(), 12345);

			verify(numberService, never()).advancePast(any(), anyInt());
		}

		@Test
		void delegatesToDao_whenDebtorSeqConfigured()
		{
			when(sysConfigBL.getIntValue(
					eq(BPartnerNumberGenerator.SYSCONFIG_DEBTOR_SEQ),
					eq(-1),
					eq(CLIENT_AND_ORG_ID)))
					.thenReturn(540123);

			generator.reserveExplicit(debtorCtx(), 999);

			verify(numberService).advancePast(DocSequenceId.ofRepoId(540123), 999);
		}

		@Test
		void delegatesToDao_whenOverrideConfigured()
		{
			when(sysConfigBL.getValue(
					eq(BPartnerNumberGenerator.SYSCONFIG_OVERRIDE),
					eq(CLIENT_AND_ORG_ID)))
					.thenReturn("fn_bpartner_no");

			generator.reserveExplicit(debtorCtx(), 12345);

			verify(numberService).callOverrideFunction(eq("fn_bpartner_no"), any(BPartnerNumberContext.class), eq(12345));
			verify(numberService, never()).advancePast(any(), anyInt());
		}

		@Test
		void throwsOnInvalidOverrideName_rejectingInjection()
		{
			final String badName = "fn(bad name)";
			when(sysConfigBL.getValue(
					eq(BPartnerNumberGenerator.SYSCONFIG_OVERRIDE),
					eq(CLIENT_AND_ORG_ID)))
					.thenReturn(badName);

			final BPartnerNumberGenerator gen = new BPartnerNumberGenerator(new BPartnerNumberService(mock(IDocumentNoBuilderFactory.class)));

			assertThatThrownBy(() -> gen.reserveExplicit(creditorCtx(), 1))
					.isInstanceOf(IllegalArgumentException.class)
					.hasMessageContaining(badName);
		}
	}

	@Nested
	class ServiceCallOverrideFunction
	{
		@Test
		void throwsOnBlankName()
		{
			final BPartnerNumberService realService = new BPartnerNumberService(mock(IDocumentNoBuilderFactory.class));
			assertThatThrownBy(() -> realService.callOverrideFunction("   ", debtorCtx(), null))
					.isInstanceOf(IllegalArgumentException.class)
					.hasMessageContaining("blank");
		}

		@Test
		void throwsOnInjectionAttempt()
		{
			final BPartnerNumberService realService = new BPartnerNumberService(mock(IDocumentNoBuilderFactory.class));
			final String badName = "foo; DROP TABLE ad_sequence";
			assertThatThrownBy(() -> realService.callOverrideFunction(badName, debtorCtx(), null))
					.isInstanceOf(IllegalArgumentException.class)
					.hasMessageContaining(badName);
		}
	}
}
