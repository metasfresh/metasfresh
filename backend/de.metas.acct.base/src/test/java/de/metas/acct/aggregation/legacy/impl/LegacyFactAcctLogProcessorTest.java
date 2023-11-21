/*
 * #%L
 * de.metas.acct.base
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

package de.metas.acct.aggregation.legacy.impl;

import de.metas.acct.aggregation.legacy.ILegacyFactAcctLogDAO;
import de.metas.acct.aggregation.legacy.IFactAcctSummaryKey;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.acct.model.I_Fact_Acct_Log;
import de.metas.acct.model.I_Fact_Acct_Summary;
import de.metas.acct.model.X_Fact_Acct_Log;
import de.metas.util.Services;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_Year;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(AdempiereTestWatcher.class)
public class LegacyFactAcctLogProcessorTest
{
	// services
	private LegacyFactAcctLogDAO legacyFactAcctLogDAO;

	private static final AcctSchemaId C_AcctSchema_ID1 = AcctSchemaId.ofRepoId(1);
	private final ElementValueId C_ElementValue_ID1 = ElementValueId.ofRepoId(1);

	private I_C_Period year2014_p1;

	private I_C_Period year2015_p1;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		Env.setClientId(Env.getCtx(), ClientId.ofRepoId(10));

		legacyFactAcctLogDAO = (LegacyFactAcctLogDAO)Services.get(ILegacyFactAcctLogDAO.class);

		// Master data:
		final I_C_Year year2014 = FactAcctLogBLTestHelper.createYear(2014);
		year2014_p1 = FactAcctLogBLTestHelper.createPeriod(year2014, Month.JANUARY);
		FactAcctLogBLTestHelper.createPeriod(year2014, Month.FEBRUARY);

		final I_C_Year year2015 = FactAcctLogBLTestHelper.createYear(2015);
		year2015_p1 = FactAcctLogBLTestHelper.createPeriod(year2015, Month.JANUARY);
		FactAcctLogBLTestHelper.createPeriod(year2015, Month.FEBRUARY);
	}

	@Test
	public void test_Simple()
	{
		//
		// Create consecutive logs
		final I_Fact_Acct_Log log1 = newFactAcctLogBuilder()
				.setDateAcct("2015-01-07")
				.setC_ElementValue_ID(C_ElementValue_ID1)
				.setAction(X_Fact_Acct_Log.ACTION_Insert)
				.setAmtAcctDr(100)
				.build();

		@SuppressWarnings("unused")
		final I_Fact_Acct_Log log2 = newFactAcctLogBuilder()
				.setDateAcct("2015-01-07")
				.setC_ElementValue_ID(C_ElementValue_ID1)
				.setAction(X_Fact_Acct_Log.ACTION_Delete)
				.setAmtAcctDr(30)
				.build();

		@SuppressWarnings("unused")
		final I_Fact_Acct_Log log3 = newFactAcctLogBuilder()
				.setDateAcct("2015-01-08")
				.setC_ElementValue_ID(C_ElementValue_ID1)
				.setAction(X_Fact_Acct_Log.ACTION_Insert)
				.setAmtAcctDr(10)
				.build();

		//
		// Process the logs
		assertHasLogs();
		processAllLogs();

		//
		// Check the result
		{
			final List<I_Fact_Acct_Summary> summaries = retrieveAllFactAcctSummariesFor(FactAcctSummaryKey.of(log1));
			assertThat(summaries).hasSize(2);

			final I_Fact_Acct_Summary summary1 = summaries.get(0);
			assertEquals("Summary AmtAcctDr", 100 - 30, summary1.getAmtAcctDr().intValueExact());
			assertEquals("Summary AmtAcctCr", 0, summary1.getAmtAcctCr().intValueExact());

			final I_Fact_Acct_Summary summary2 = summaries.get(1);
			assertEquals("Summary AmtAcctDr", 100 - 30 + 10, summary2.getAmtAcctDr().intValueExact());
			assertEquals("Summary AmtAcctCr", 0, summary2.getAmtAcctCr().intValueExact());
		}

		//
		// Create one log entry in the past and check if the aggregation
		{
			newFactAcctLogBuilder()
					.setDateAcct("2015-01-06")
					.setC_ElementValue_ID(C_ElementValue_ID1)
					.setAction(X_Fact_Acct_Log.ACTION_Insert)
					.setAmtAcctDr(1000)
					.build();

			processAllLogs();

			final List<I_Fact_Acct_Summary> summaries = retrieveAllFactAcctSummariesFor(FactAcctSummaryKey.of(log1));
			assertEquals("Summary AmtAcctDr", 1000, summaries.get(0).getAmtAcctDr().intValueExact());
			assertEquals("Summary AmtAcctDr", 100 - 30 + 1000, summaries.get(1).getAmtAcctDr().intValueExact());
			assertEquals("Summary AmtAcctDr", 100 - 30 + 10 + 1000, summaries.get(2).getAmtAcctDr().intValueExact());
		}
	}

	@Test
	public void test_YearToDate()
	{
		final I_Fact_Acct_Log log1 = newFactAcctLogBuilder()
				.setC_Period(year2014_p1)
				.setDateAcct("2014-01-07")
				.setC_ElementValue_ID(C_ElementValue_ID1)
				.setAction(X_Fact_Acct_Log.ACTION_Insert)
				.setAmtAcctDr(100)
				.build();
		processAllLogs();

		// Check
		{
			final List<I_Fact_Acct_Summary> summaries = retrieveAllFactAcctSummariesFor(FactAcctSummaryKey.of(log1));
			assertEquals("Summary records count", 1, summaries.size());
			assertEquals("Summary AmtAcctDr", 100, summaries.get(0).getAmtAcctDr().intValueExact());
			assertEquals("Summary AmtAcctDr_YTD", 100, summaries.get(0).getAmtAcctDr_YTD().intValueExact());
		}

		final I_Fact_Acct_Log log2 = newFactAcctLogBuilder()
				.setC_Period(year2015_p1)
				.setDateAcct("2015-01-07")
				.setC_ElementValue_ID(C_ElementValue_ID1)
				.setAction(X_Fact_Acct_Log.ACTION_Insert)
				.setAmtAcctDr(50)
				.build();
		processAllLogs();

		// Check
		{
			final List<I_Fact_Acct_Summary> summaries = retrieveAllFactAcctSummariesFor(FactAcctSummaryKey.of(log2));
			assertThat(summaries).hasSize(2);
			//
			assertEquals("Summary AmtAcctDr", 100, summaries.get(0).getAmtAcctDr().intValueExact());
			assertEquals("Summary AmtAcctDr_YTD", 100, summaries.get(0).getAmtAcctDr_YTD().intValueExact());
			//
			assertEquals("Summary AmtAcctDr", 100 + 50, summaries.get(1).getAmtAcctDr().intValueExact());
			assertEquals("Summary AmtAcctDr_YTD", 50, summaries.get(1).getAmtAcctDr_YTD().intValueExact());
		}

	}

	private Fact_Acct_Log_Builder newFactAcctLogBuilder()
	{
		return Fact_Acct_Log_Builder.newBuilder()
				.setC_AcctSchema_ID(C_AcctSchema_ID1)
				.setPostingType(X_Fact_Acct_Log.POSTINGTYPE_Actual)
				.setC_Period(year2014_p1)
		//
		;
	}

	private void assertEquals(final String description, final int expected, final int actual)
	{
		assertThat(actual).as(description).isEqualTo(expected);
	}

	private void assertHasLogs()
	{
		assertThat(legacyFactAcctLogDAO.hasLogs(Env.getCtx(), ILegacyFactAcctLogDAO.PROCESSINGTAG_NULL))
				.as("has logs")
				.isTrue();
	}

	private void assertNoLogs()
	{
		assertThat(legacyFactAcctLogDAO.hasLogs(Env.getCtx(), ILegacyFactAcctLogDAO.PROCESSINGTAG_NULL))
				.as("has logs")
				.isFalse();
	}

	private void processAllLogs()
	{
		new LegacyFactAcctLogProcessor().processAll(QueryLimit.NO_LIMIT);
		assertNoLogs();
	}

	private List<I_Fact_Acct_Summary> retrieveAllFactAcctSummariesFor(final IFactAcctSummaryKey key)
	{
		return legacyFactAcctLogDAO.createFactAcctSummaryQueryForKeyNoDateAcct(Env.getCtx(), key)
				.orderBy()
				.addColumn(I_Fact_Acct_Summary.COLUMN_DateAcct)
				.endOrderBy()
				.create()
				.list(I_Fact_Acct_Summary.class);
	}

}
