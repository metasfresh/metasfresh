package de.metas.contracts.process;

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

import de.metas.acct.GLCategoryRepository;
import de.metas.contracts.FlatrateTermRequest.CreateFlatrateTermRequest;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.impl.AbstractFlatrateTermTest;
import de.metas.contracts.impl.FlatrateTermDataFactory.ProductAndPricingSystem;
import de.metas.contracts.interceptor.C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Transition;
import de.metas.contracts.model.X_C_Flatrate_Transition;
import de.metas.contracts.order.ContractOrderService;
import de.metas.location.impl.DummyDocumentLocationBL;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.sql.Timestamp;

import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * The end date entered in "Erzeuge Vertrag" is applied only when the conditions' transition has duration 0
 * (such a contract keeps the entered end date). For a transition with duration greater than 0 the entered date is
 * ignored, so the term keeps the end date computed from the transition, and the notice date derived from it.
 * Each case runs the gate of the process and then creates the term the way the process does.
 */
class C_Flatrate_Term_Create_For_BPartners_EndDate_Test extends AbstractFlatrateTermTest
{
	private IFlatrateBL flatrateBL;

	private final Timestamp startDate = TimeUtil.getDay(2020, 1, 1);
	private final Timestamp enteredEndDate = TimeUtil.getDay(2020, 12, 31);
	private ProductAndPricingSystem productAndPricingSystem;

	@BeforeEach
	void setUpBPartnerAndProduct()
	{
		flatrateBL = Services.get(IFlatrateBL.class);

		// the end-date computation (validatePeriods -> updateNoticeDateAndEndDate) runs from this interceptor on save
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(new C_Flatrate_Term(
				new ContractOrderService(),
				DummyDocumentLocationBL.newInstanceForUnitTesting(),
				new GLCategoryRepository()));

		prepareBPartner();
		productAndPricingSystem = createProductAndPricingSystem(startDate);
		createProductAcct(productAndPricingSystem);
	}

	private I_C_Flatrate_Conditions conditionsWithTermDuration(final int termDuration)
	{
		final I_C_Flatrate_Conditions conditions = createFlatrateConditions(productAndPricingSystem, null);
		final I_C_Flatrate_Transition transition = conditions.getC_Flatrate_Transition();
		transition.setTermDuration(termDuration);
		transition.setTermDurationUnit(X_C_Flatrate_Transition.TERMDURATIONUNIT_MonatE);
		save(transition);
		return conditions;
	}

	/** Gate the entered end date like the process does, then create the term like the process does. */
	private I_C_Flatrate_Term createTermAsProcess(final I_C_Flatrate_Conditions conditions, @Nullable final Timestamp endDateParam)
	{
		final Timestamp endDate = flatrateBL.getEndDateToApply(conditions, endDateParam);
		return flatrateBL.createTerm(CreateFlatrateTermRequest.builder()
				.context(helper.getContextProvider())
				.orgId(OrgId.ofRepoId(helper.getOrg().getAD_Org_ID()))
				.bPartner(getBpartner())
				.conditions(conditions)
				.startDate(startDate)
				.endDate(endDate)
				.productAndCategoryId(productAndPricingSystem.getProductAndCategoryId())
				.completeIt(false)
				.build());
	}

	@Test
	void durationZero_enteredEndDate_isKept()
	{
		final I_C_Flatrate_Conditions conditions = conditionsWithTermDuration(0);

		assertThat(flatrateBL.getEndDateToApply(conditions, enteredEndDate)).isEqualTo(enteredEndDate);

		final I_C_Flatrate_Term term = createTermAsProcess(conditions, enteredEndDate);
		assertThat(term.getEndDate()).isEqualTo(enteredEndDate);
		assertThat(term.getNoticeDate()).isEqualTo(enteredEndDate); // fixture notice = 0 days
	}

	@Test
	void durationGreaterThanZero_enteredEndDate_isIgnored_computedEndDateStands()
	{
		final I_C_Flatrate_Conditions conditions = conditionsWithTermDuration(3);

		assertThat(flatrateBL.getEndDateToApply(conditions, enteredEndDate)).isNull();

		final I_C_Flatrate_Term term = createTermAsProcess(conditions, enteredEndDate);
		// startDate (2020-01-01) + 3 months - 1 day; the notice date follows the computed end date
		assertThat(term.getEndDate()).isEqualTo(TimeUtil.getDay(2020, 3, 31));
		assertThat(term.getNoticeDate()).isEqualTo(TimeUtil.getDay(2020, 3, 31));
	}

	@Test
	void noEnteredEndDate_unchanged()
	{
		final I_C_Flatrate_Conditions conditions = conditionsWithTermDuration(3);

		assertThat(flatrateBL.getEndDateToApply(conditions, null)).isNull();

		final I_C_Flatrate_Term term = createTermAsProcess(conditions, null);
		assertThat(term.getEndDate()).isEqualTo(TimeUtil.getDay(2020, 3, 31));
	}
}
