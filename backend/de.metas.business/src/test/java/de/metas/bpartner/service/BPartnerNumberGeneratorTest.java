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
import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
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
 * The DB-executing override path is tested via the overrideFunctionExecutor seam.
 * No live DB required.
 */
class BPartnerNumberGeneratorTest
{
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
				.orgId(OrgId.ofRepoId(AD_ORG_ID))
				.bPartnerId(43)
				.isCustomer(false)
				.isVendor(true)
				.isCompany(false)
				.kind(Kind.CREDITOR)
				.build();
	}

	// ─── no-config branch ───────────────────────────────────────────────────

	@Test
	void generateNext_returnsEmpty_whenNoConfig()
	{
		// no sysconfig stubs → all return null

		final Optional<Integer> result = generator.generateNext(debtorCtx());

		assertThat(result).isEmpty();
		verify(dao, never()).drawNext(any());
	}

	@Test
	void reserveExplicit_isNoOp_whenNoConfig()
	{
		// no sysconfig stubs → no-op

		generator.reserveExplicit(debtorCtx(), 12345);

		verify(dao, never()).advancePast(any(), anyInt());
	}

	// ─── debtor sequence branch ──────────────────────────────────────────────

	@Test
	void generateNext_delegatesToDao_whenDebtorSeqConfigured()
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
	void reserveExplicit_delegatesToDao_whenDebtorSeqConfigured()
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
	void generateNext_delegatesToDao_whenCreditorSeqConfigured()
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

	// ─── override branch — identifier validation ─────────────────────────────

	@Test
	void generateNext_takesOverrideBranch_whenOverrideConfigured()
	{
		when(sysConfigBL.getValue(
				eq(BPartnerNumberGenerator.SYSCONFIG_OVERRIDE),
				isNull(),
				anyInt(),
				eq(AD_ORG_ID)))
				.thenReturn("fn_bpartner_no");

		// Inject a test executor that bypasses the real DB call
		generator.setOverrideFunctionExecutor((fnName, ctx, explicitValue) ->
				explicitValue == null ? Optional.of(42) : Optional.empty());

		final Optional<Integer> result = generator.generateNext(debtorCtx());

		assertThat(result).contains(42);
		// DAO must NOT be called when override is active
		verify(dao, never()).drawNext(any());
	}

	@Test
	void generateNext_throwsOnInvalidOverrideName_rejectingInjection()
	{
		when(sysConfigBL.getValue(
				eq(BPartnerNumberGenerator.SYSCONFIG_OVERRIDE),
				isNull(),
				anyInt(),
				eq(AD_ORG_ID)))
				.thenReturn("foo; DROP TABLE ad_sequence");

		assertThatThrownBy(() -> generator.generateNext(debtorCtx()))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("foo; DROP TABLE ad_sequence");
	}

	@Test
	void reserveExplicit_throwsOnInvalidOverrideName_rejectingInjection()
	{
		when(sysConfigBL.getValue(
				eq(BPartnerNumberGenerator.SYSCONFIG_OVERRIDE),
				isNull(),
				anyInt(),
				eq(AD_ORG_ID)))
				.thenReturn("fn(bad name)");

		assertThatThrownBy(() -> generator.reserveExplicit(debtorCtx(), 1))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("fn(bad name)");
	}
}
