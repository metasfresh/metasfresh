package de.metas.contracts.interceptor;

import de.metas.acct.GLCategoryRepository;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Transition;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.order.ContractOrderService;
import de.metas.location.impl.DummyDocumentLocationBL;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_Year;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class C_Flatrate_TermTest
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void prohibitReactivatingUnlessAllowed_wrong_term_throws_exception()
	{
		setupAllowProcurementReactivate();

		final I_C_Flatrate_Term emptyTerm = newInstance(I_C_Flatrate_Term.class);
		invokeMethodAndAssertExceptionThrown(emptyTerm);

		final I_C_Flatrate_Term subscriptionTerm = newInstance(I_C_Flatrate_Term.class);
		subscriptionTerm.setType_Conditions(X_C_Flatrate_Term.TYPE_CONDITIONS_Subscription);
		save(subscriptionTerm);

		invokeMethodAndAssertExceptionThrown(subscriptionTerm);
	}

	public void invokeMethodAndAssertExceptionThrown(final I_C_Flatrate_Term term)
	{
		try
		{

			final C_Flatrate_Term flatrateTermInterceptor = new C_Flatrate_Term(new ContractOrderService(), DummyDocumentLocationBL.newInstanceForUnitTesting(), new GLCategoryRepository());
			flatrateTermInterceptor.prohibitReactivatingUnlessAllowed(term);
			fail("Expected an AdempiereExeception");
		}
		catch (AdempiereException ae)
		{
			assertThat(ae.getMessage()).isEqualTo(MainValidator.MSG_FLATRATE_REACTIVATE_DOC_ACTION_NOT_SUPPORTED_0P.toAD_Message());
		}
	}

	@Test
	public void prohibitReactivatingUnlessAllowed_procurement_term_allowed()
	{
		setupAllowProcurementReactivate();

		final I_C_Flatrate_Term term = newInstance(I_C_Flatrate_Term.class);
		term.setType_Conditions(X_C_Flatrate_Term.TYPE_CONDITIONS_Procurement);
		save(term);

		final C_Flatrate_Term flatrateTermInterceptor = new C_Flatrate_Term(new ContractOrderService(), DummyDocumentLocationBL.newInstanceForUnitTesting(), new GLCategoryRepository());
		flatrateTermInterceptor.prohibitReactivatingUnlessAllowed(term); // shall return with no exception
	}

	/**
	 * The message "start date {0} of the first period of calendar {1} is after the start date of the contract period" must name the
	 * first period's start date, not the contract's start date.
	 */
	@Test
	public void validatePeriods_firstPeriodStartsAfterTermStart_namesTheFirstPeriodsStartDate()
	{
		final I_C_Calendar calendar = newInstance(I_C_Calendar.class);
		calendar.setName("Contract calendar");
		save(calendar);
		final I_C_Year year = newInstance(I_C_Year.class);
		year.setC_Calendar_ID(calendar.getC_Calendar_ID());
		year.setFiscalYear("2026");
		save(year);
		final Timestamp firstPeriodStart = Timestamp.valueOf("2026-02-01 00:00:00");
		final I_C_Period period = newInstance(I_C_Period.class);
		period.setC_Year_ID(year.getC_Year_ID());
		period.setStartDate(firstPeriodStart);
		period.setEndDate(Timestamp.valueOf("2026-12-31 00:00:00"));
		period.setPeriodNo(1);
		save(period);

		final I_C_Flatrate_Transition transition = newInstance(I_C_Flatrate_Transition.class);
		transition.setC_Calendar_Contract_ID(calendar.getC_Calendar_ID());
		save(transition);
		final I_C_Flatrate_Conditions conditions = newInstance(I_C_Flatrate_Conditions.class);
		conditions.setC_Flatrate_Transition_ID(transition.getC_Flatrate_Transition_ID());
		save(conditions);

		final Timestamp termStart = Timestamp.valueOf("2026-01-01 00:00:00");
		final I_C_Flatrate_Term term = newInstance(I_C_Flatrate_Term.class);
		term.setC_Flatrate_Conditions_ID(conditions.getC_Flatrate_Conditions_ID());
		term.setType_Conditions(X_C_Flatrate_Term.TYPE_CONDITIONS_Refundable);
		term.setStartDate(termStart);
		term.setEndDate(Timestamp.valueOf("2026-12-31 00:00:00"));
		term.setProcessed(true); // the end date is given, it is not computed from the transition
		save(term);

		final C_Flatrate_Term flatrateTermInterceptor = new C_Flatrate_Term(new ContractOrderService(), DummyDocumentLocationBL.newInstanceForUnitTesting(), new GLCategoryRepository());
		assertThatThrownBy(() -> flatrateTermInterceptor.validatePeriods(term))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("Term_Error_PeriodStartDate_After_TermStartDate")
				.hasMessageContaining(firstPeriodStart.toString())
				.hasMessageNotContaining(termStart.toString());
	}

	public void setupAllowProcurementReactivate()
	{
		final String sysConfigName = "de.metas.contracts.C_Flatrate_Term.allow_reactivate_" + X_C_Flatrate_Term.TYPE_CONDITIONS_Procurement;
		Services.get(ISysConfigBL.class)
				.setValue(sysConfigName, true, ClientId.SYSTEM, OrgId.ANY);
	}
}
