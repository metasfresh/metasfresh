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

import de.metas.bpartner.service.BPartnerNumberContext.Kind;
import de.metas.document.sequence.DocSequenceId;
import de.metas.interfaces.I_C_BPartner;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

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
 * The DB-executing override path is tested via a mocked {@link BPartnerNumberSequenceDAO}.
 * No live DB required.
 */
class BPartnerNumberGeneratorTest
{
	private static final int AD_CLIENT_ID = 1000001;
	private static final int AD_ORG_ID = 1000000;

	private ISysConfigBL sysConfigBL;
	private BPartnerNumberSequenceDAO dao;
	private BPartnerNumberGenerator generator;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		sysConfigBL = mock(ISysConfigBL.class);
		Services.registerService(ISysConfigBL.class, sysConfigBL);

		dao = mock(BPartnerNumberSequenceDAO.class);
		generator = new BPartnerNumberGenerator(dao);
	}

	private BPartnerNumberContext debtorCtx()
	{
		return BPartnerNumberContext.builder()
				.clientId(ClientId.ofRepoId(AD_CLIENT_ID))
				.orgId(OrgId.ofRepoId(AD_ORG_ID))
				.bPartnerId(42)
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
				.bPartnerId(43)
				.isCustomer(false)
				.isVendor(true)
				.isCompany(false)
				.kind(Kind.CREDITOR)
				.build();
	}

	// ─── no-config branch ───────────────────────────────────────────────────

	@Nested
	class GenerateNext
	{
		@Test
		void returnsEmpty_whenNoConfig()
		{
			// no sysconfig stubs → all return null

			final Optional<Integer> result = generator.generateNext(debtorCtx());

			assertThat(result).isEmpty();
			verify(dao, never()).drawNext(any());
		}

		@Test
		void delegatesToDao_whenDebtorSeqConfigured()
		{
			when(sysConfigBL.getValue(
					eq(BPartnerNumberGenerator.SYSCONFIG_DEBTOR_SEQ),
					isNull(),
					anyInt(),
					eq(AD_ORG_ID)))
					.thenReturn("540123");

			when(dao.drawNext(DocSequenceId.ofRepoId(540123))).thenReturn(7);

			final Optional<Integer> result = generator.generateNext(debtorCtx());

			assertThat(result).contains(7);
		}

		@Test
		void delegatesToDao_whenCreditorSeqConfigured()
		{
			when(sysConfigBL.getValue(
					eq(BPartnerNumberGenerator.SYSCONFIG_CREDITOR_SEQ),
					isNull(),
					anyInt(),
					eq(AD_ORG_ID)))
					.thenReturn("540456");

			when(dao.drawNext(DocSequenceId.ofRepoId(540456))).thenReturn(55);

			final Optional<Integer> result = generator.generateNext(creditorCtx());

			assertThat(result).contains(55);
		}

		@Test
		void takesOverrideBranch_whenOverrideConfigured()
		{
			when(sysConfigBL.getValue(
					eq(BPartnerNumberGenerator.SYSCONFIG_OVERRIDE),
					isNull(),
					anyInt(),
					eq(AD_ORG_ID)))
					.thenReturn("fn_bpartner_no");

			when(dao.callOverrideFunction(eq("fn_bpartner_no"), any(BPartnerNumberContext.class), isNull()))
					.thenReturn(Optional.of(42));

			final Optional<Integer> result = generator.generateNext(debtorCtx());

			assertThat(result).contains(42);
			verify(dao, never()).drawNext(any());
		}

		/**
		 * The SQL-injection guard lives in {@link BPartnerNumberSequenceDAO#callOverrideFunction}.
		 * Validation fires before any DB access, so the real DAO throws without needing a database.
		 * The generator must propagate that exception.
		 */
		@Test
		void throwsOnInvalidOverrideName_rejectingInjection()
		{
			final String badName = "foo; DROP TABLE ad_sequence";
			when(sysConfigBL.getValue(
					eq(BPartnerNumberGenerator.SYSCONFIG_OVERRIDE),
					isNull(),
					anyInt(),
					eq(AD_ORG_ID)))
					.thenReturn(badName);

			final BPartnerNumberGenerator gen = new BPartnerNumberGenerator(new BPartnerNumberSequenceDAO());

			assertThatThrownBy(() -> gen.generateNext(debtorCtx()))
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

			verify(dao, never()).advancePast(any(), anyInt());
		}

		@Test
		void delegatesToDao_whenDebtorSeqConfigured()
		{
			when(sysConfigBL.getValue(
					eq(BPartnerNumberGenerator.SYSCONFIG_DEBTOR_SEQ),
					isNull(),
					anyInt(),
					eq(AD_ORG_ID)))
					.thenReturn("540123");

			generator.reserveExplicit(debtorCtx(), 999);

			verify(dao).advancePast(DocSequenceId.ofRepoId(540123), 999);
		}

		@Test
		void throwsOnInvalidOverrideName_rejectingInjection()
		{
			final String badName = "fn(bad name)";
			when(sysConfigBL.getValue(
					eq(BPartnerNumberGenerator.SYSCONFIG_OVERRIDE),
					isNull(),
					anyInt(),
					eq(AD_ORG_ID)))
					.thenReturn(badName);

			final BPartnerNumberGenerator gen = new BPartnerNumberGenerator(new BPartnerNumberSequenceDAO());

			assertThatThrownBy(() -> gen.reserveExplicit(creditorCtx(), 1))
					.isInstanceOf(IllegalArgumentException.class)
					.hasMessageContaining(badName);
		}
	}

	@Nested
	class ReserveExplicitIfChanged
	{
		@Test
		void fires_whenIsNew()
		{
			when(sysConfigBL.getValue(
					eq(BPartnerNumberGenerator.SYSCONFIG_DEBTOR_SEQ),
					isNull(),
					anyInt(),
					eq(AD_ORG_ID)))
					.thenReturn("540123");

			final I_C_BPartner bpartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
			// isNew=true on a freshly created POJO record

			generator.reserveExplicitIfChanged(bpartner, debtorCtx(), true, I_C_BPartner.COLUMNNAME_DebtorId, 7777);

			verify(dao).advancePast(DocSequenceId.ofRepoId(540123), 7777);
		}

		@Test
		void skips_whenNotNewAndColumnUnchanged()
		{
			final I_C_BPartner bpartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
			// isNew=false, column not changed → no-op

			generator.reserveExplicitIfChanged(bpartner, debtorCtx(), false, I_C_BPartner.COLUMNNAME_DebtorId, 7777);

			verify(dao, never()).advancePast(any(), anyInt());
		}
	}

	@Nested
	class DaoCallOverrideFunction
	{
		@Test
		void throwsOnBlankName()
		{
			final BPartnerNumberSequenceDAO realDao = new BPartnerNumberSequenceDAO();
			assertThatThrownBy(() -> realDao.callOverrideFunction("   ", debtorCtx(), null))
					.isInstanceOf(IllegalArgumentException.class)
					.hasMessageContaining("blank");
		}

		@Test
		void throwsOnInjectionAttempt()
		{
			final BPartnerNumberSequenceDAO realDao = new BPartnerNumberSequenceDAO();
			final String badName = "foo; DROP TABLE ad_sequence";
			assertThatThrownBy(() -> realDao.callOverrideFunction(badName, debtorCtx(), null))
					.isInstanceOf(IllegalArgumentException.class)
					.hasMessageContaining(badName);
		}
	}
}
