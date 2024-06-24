/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.acct.aggregation;

import ch.qos.logback.classic.Level;
import de.metas.acct.aggregation.legacy.FactAcctLogProcessResult;
import de.metas.acct.aggregation.legacy.ILegacyFactAcctLogDAO;
import de.metas.acct.aggregation.legacy.impl.FactAcctLogBLTestHelper;
import de.metas.acct.aggregation.legacy.impl.Fact_Acct_Log_Builder;
import de.metas.acct.aggregation.legacy.impl.LegacyFactAcctLogDAO;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.acct.model.X_Fact_Acct_Log;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.AbstractBooleanAssert;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_Year;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.*;

class FactAcctLogDBTableWatcherTest
{
	private LegacyFactAcctLogDAO legacyFactAcctLogDAO;
	private ISysConfigBL sysConfigBL;
	private FactAcctLogDBTableWatcher watcher;

	private I_C_Period year2022_p1;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		LogManager.setLoggerLevel(FactAcctLogDBTableWatcher.class, Level.DEBUG);

		//
		// Services + service under test
		legacyFactAcctLogDAO = (LegacyFactAcctLogDAO)Services.get(ILegacyFactAcctLogDAO.class);
		sysConfigBL = Services.get(ISysConfigBL.class);

		watcher = FactAcctLogDBTableWatcher.builder()
				.factAcctLogService(new FactAcctLogService())
				.sysConfigBL(sysConfigBL)
				.build();

		//
		// master data
		Env.setClientId(Env.getCtx(), ClientId.ofRepoId(10));

		final I_C_Year year2022 = FactAcctLogBLTestHelper.createYear(2022);
		year2022_p1 = FactAcctLogBLTestHelper.createPeriod(year2022, Month.JANUARY);
	}

	@SuppressWarnings("SameParameterValue")
	private void setRetrieveBatchSize(final int retrieveBatchSize)
	{
		sysConfigBL.setValue(FactAcctLogDBTableWatcher.SYSCONFIG_RetrieveBatchSize, retrieveBatchSize, ClientId.SYSTEM, OrgId.ANY);
	}

	private Fact_Acct_Log_Builder newFactAcctLogBuilder()
	{
		return Fact_Acct_Log_Builder.newBuilder()
				.setC_AcctSchema_ID(AcctSchemaId.ofRepoId(1))
				.setPostingType(X_Fact_Acct_Log.POSTINGTYPE_Actual)
				.setC_Period(year2022_p1)
				.setC_ElementValue_ID(ElementValueId.ofRepoId(2))
				//
				;
	}

	private void generateLogs(final int count)
	{
		for (int i = 1; i <= count; i++)
		{
			newFactAcctLogBuilder()
					.setDateAcct(LocalDate.parse("2022-01-15"))
					.setAction(X_Fact_Acct_Log.ACTION_Insert)
					.setAmtAcctDr(1)
					.build();
		}
	}

	private AbstractBooleanAssert<?> assertHasLogs()
	{
		return assertThat(legacyFactAcctLogDAO.hasLogs(Env.getCtx(), ILegacyFactAcctLogDAO.PROCESSINGTAG_NULL));
	}

	@Nested
	class processNow
	{
		@Test
		void lessLogs_than_RetrieveBatchSize()
		{
			setRetrieveBatchSize(5);

			generateLogs(2);
			assertHasLogs().isTrue();

			final FactAcctLogProcessResult result = watcher.processNow();

			assertHasLogs().isFalse();
			assertThat(result)
					.usingRecursiveComparison()
					.ignoringFields("duration")
					.isEqualTo(FactAcctLogProcessResult.builder()
					.iterations(1)
					.processedLogRecordsCount(2)
					.build());
		}

		@Test
		void moreLogs_than_RetrieveBatchSize()
		{
			setRetrieveBatchSize(5);

			generateLogs(10);
			assertHasLogs().isTrue();

			final FactAcctLogProcessResult result = watcher.processNow();

			assertHasLogs().isFalse();
			assertThat(result)
					.usingRecursiveComparison()
					.ignoringFields("duration")
					.isEqualTo(FactAcctLogProcessResult.builder()
							.iterations(3)
							.processedLogRecordsCount(10)
							.build());
		}
	}
}