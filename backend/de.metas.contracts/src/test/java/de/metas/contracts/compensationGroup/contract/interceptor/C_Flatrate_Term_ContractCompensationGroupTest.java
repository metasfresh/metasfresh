package de.metas.contracts.compensationGroup.contract.interceptor;

import de.metas.common.util.time.SystemTime;
import de.metas.contracts.FlatrateTermStatus;
import de.metas.contracts.compensationGroup.contract.ContractCompensationGroupTermService;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.document.engine.DocStatus;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.TimeZone;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.contracts
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

class C_Flatrate_Term_ContractCompensationGroupTest
{
	/** The code reads "today" from SystemTime's zone and the term's dates via {@code Timestamp#toLocalDateTime} (JVM default zone); as on a server, both are the same zone here. */
	private TimeZone jvmTimezoneBackup;

	private C_Flatrate_Term_ContractCompensationGroup interceptor;

	@BeforeEach
	void beforeEach()
	{
		jvmTimezoneBackup = TimeZone.getDefault();
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Berlin"));
		AdempiereTestHelper.get().init();
		SystemTime.setFixedTimeSource("2026-07-01T10:00:00+02:00[Europe/Berlin]");
		interceptor = new C_Flatrate_Term_ContractCompensationGroup(ContractCompensationGroupTermService.newInstanceForUnitTesting());
	}

	@AfterEach
	void afterEach()
	{
		SystemTime.resetTimeSource();
		TimeZone.setDefault(jvmTimezoneBackup);
	}

	private static I_C_Flatrate_Term draftTerm(final TypeConditions typeConditions)
	{
		final I_C_Flatrate_Term term = newInstance(I_C_Flatrate_Term.class);
		term.setType_Conditions(typeConditions.getCode());
		term.setDocStatus(DocStatus.Drafted.getCode());
		term.setStartDate(TimeUtil.asTimestamp(LocalDate.parse("2026-08-01")));
		term.setEndDate(TimeUtil.asTimestamp(LocalDate.parse("2026-12-31")));
		saveRecord(term);
		return term;
	}

	@Test
	void compensationGroupTerm_getsStatusAndDates()
	{
		final I_C_Flatrate_Term term = draftTerm(TypeConditions.COMPENSATION_GROUP);

		interceptor.setContractStatusAndDatesBeforeComplete(term);

		assertThat(term.getContractStatus()).isEqualTo(FlatrateTermStatus.Waiting.getCode());
		assertThat(TimeUtil.asLocalDate(term.getDateContracted())).isEqualTo(LocalDate.parse("2026-07-01"));
		assertThat(TimeUtil.asLocalDate(term.getMasterStartDate())).isEqualTo(LocalDate.parse("2026-08-01"));
	}

	@Test
	void otherContractTypes_untouched()
	{
		final I_C_Flatrate_Term term = draftTerm(TypeConditions.SUBSCRIPTION);

		interceptor.setContractStatusAndDatesBeforeComplete(term);

		assertThat(term.getContractStatus()).isNull();
		assertThat(term.getDateContracted()).isNull();
		assertThat(term.getMasterStartDate()).isNull();
	}
}
