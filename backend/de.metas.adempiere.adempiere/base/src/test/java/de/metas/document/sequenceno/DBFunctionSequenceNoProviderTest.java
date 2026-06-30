/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.document.sequenceno;

import de.metas.adempiere.model.IPOReferenceAware;
import de.metas.document.DocumentSequenceInfo;
import de.metas.util.Services;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the applicability / fail-loud contract of {@link DBFunctionSequenceNoProvider}.
 * The DB-backed {@code provideSequenceNo} (which calls the configured PL/pgSQL function) is exercised by the
 * integration test, not here.
 */
class DBFunctionSequenceNoProviderTest
{
	private static final String SEQ_NAME = "TestLotSeq";
	// The provider must derive exactly this per-sequence key from the sequence Name:
	private static final String EXPECTED_SYSCONFIG_KEY =
			"de.metas.document.seqNo.DBFunctionSequenceNoProvider." + SEQ_NAME + ".dbFunctionName";

	private ISysConfigBL sysConfigBL;
	private DBFunctionSequenceNoProvider provider;

	private static DocumentSequenceInfo docSeqInfo()
	{
		return DocumentSequenceInfo.builder().name(SEQ_NAME).build();
	}

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		sysConfigBL = mock(ISysConfigBL.class);
		Services.registerService(ISysConfigBL.class, sysConfigBL);

		provider = new DBFunctionSequenceNoProvider();
	}

	@Test
	void isApplicable_true_whenPerSequenceFunctionConfiguredAndRecordIdPresent()
	{
		// the function-name SysConfig is set for THIS sequence's derived key:
		when(sysConfigBL.getValue(EXPECTED_SYSCONFIG_KEY, (String)null)).thenReturn("fn_lotno_test");
		final Evaluatee context = Evaluatees.ofSingleton(IPOReferenceAware.COLUMNNAME_Record_ID, 12345);

		assertThat(provider.isApplicable(context, docSeqInfo())).isTrue();
	}

	@Test
	void isApplicable_false_whenSysConfigBlank()
	{
		// no stub -> getValue returns null -> not configured for this sequence
		final Evaluatee context = Evaluatees.ofSingleton(IPOReferenceAware.COLUMNNAME_Record_ID, 12345);

		assertThat(provider.isApplicable(context, docSeqInfo())).isFalse();
	}

	@Test
	void isApplicable_false_whenConfiguredButNoRecordId()
	{
		when(sysConfigBL.getValue(EXPECTED_SYSCONFIG_KEY, (String)null)).thenReturn("fn_lotno_test");
		final Evaluatee context = Evaluatees.empty(); // no Record_ID

		assertThat(provider.isApplicable(context, docSeqInfo())).isFalse();
	}

	@Test
	void doesNotUseIncrementSeqNoAsPrefix()
	{
		assertThat(provider.isUseIncrementSeqNoAsPrefix()).isFalse();
	}

	@Test
	void isValidFunctionName_acceptsPlainAndSchemaQualified()
	{
		assertThat(DBFunctionSequenceNoProvider.isValidFunctionName("fn_lotno_custom")).isTrue();
		assertThat(DBFunctionSequenceNoProvider.isValidFunctionName("myschema.fn_lotno")).isTrue();
		assertThat(DBFunctionSequenceNoProvider.isValidFunctionName("  fn_lotno  ")).isTrue(); // trimmed
	}

	@Test
	void isValidFunctionName_rejectsMalformedOrInjection()
	{
		assertThat(DBFunctionSequenceNoProvider.isValidFunctionName(null)).isFalse();
		assertThat(DBFunctionSequenceNoProvider.isValidFunctionName("")).isFalse();
		assertThat(DBFunctionSequenceNoProvider.isValidFunctionName("fn_lotno; DROP TABLE ad_sequence")).isFalse();
		assertThat(DBFunctionSequenceNoProvider.isValidFunctionName("fn lotno")).isFalse();
		assertThat(DBFunctionSequenceNoProvider.isValidFunctionName("schema.sub.fn")).isFalse(); // only one schema segment
	}
}
