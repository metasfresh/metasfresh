package de.metas.contracts.impl;

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
import de.metas.contracts.impl.FlatrateTermDataFactory.ProductAndPricingSystem;
import de.metas.contracts.interceptor.C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Transition;
import de.metas.contracts.model.X_C_Flatrate_Transition;
import de.metas.contracts.order.ContractOrderService;
import de.metas.location.impl.DummyDocumentLocationBL;
import de.metas.organization.OrgId;
import de.metas.product.ProductAndCategoryId;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * {@link IFlatrateBL#createTerm(CreateFlatrateTermRequest)} end-date handling: a transition with
 * TermDuration 0 does not compute an end date automatically, so the term keeps the requested one;
 * without a requested end date the save is refused. A transition with TermDuration greater than 0
 * still computes the end date.
 */
public class FlatrateBL_createTerm_EndDate_Test extends AbstractFlatrateTermTest
{
	private IFlatrateBL flatrateBL;

	private Timestamp startDate;
	private ProductAndCategoryId productAndCategoryId;
	private ProductAndPricingSystem productAndPricingSystem;

	@BeforeEach
	void setUpBPartnerAndProduct()
	{
		flatrateBL = Services.get(IFlatrateBL.class);

		// register the C_Flatrate_Term model interceptor: production's end-date computation
		// (validatePeriods -> updateNoticeDateAndEndDate) runs from there on save(), and
		// AbstractFlatrateTermTest does not register it by default (see ContractOrderTest).
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(new C_Flatrate_Term(
				new ContractOrderService(),
				DummyDocumentLocationBL.newInstanceForUnitTesting(),
				new GLCategoryRepository()));

		prepareBPartner();

		startDate = TimeUtil.getDay(2020, 1, 1);

		productAndPricingSystem = createProductAndPricingSystem(startDate);
		createProductAcct(productAndPricingSystem);

		productAndCategoryId = productAndPricingSystem.getProductAndCategoryId();
	}

	private I_C_Flatrate_Conditions createConditionsWithTermDuration(final int termDuration, final String termDurationUnit)
	{
		final I_C_Flatrate_Conditions conditions = createFlatrateConditions(productAndPricingSystem, null);

		final I_C_Flatrate_Transition transition = conditions.getC_Flatrate_Transition();
		transition.setTermDuration(termDuration);
		transition.setTermDurationUnit(termDurationUnit);
		save(transition);

		return conditions;
	}

	private CreateFlatrateTermRequest.CreateFlatrateTermRequestBuilder requestBuilder(final I_C_Flatrate_Conditions conditions)
	{
		return CreateFlatrateTermRequest.builder()
				.context(helper.getContextProvider())
				.orgId(OrgId.ofRepoId(helper.getOrg().getAD_Org_ID()))
				.bPartner(getBpartner())
				.conditions(conditions)
				.startDate(startDate)
				.productAndCategoryId(productAndCategoryId)
				.completeIt(false);
	}

	/**
	 * Note on scope: these tests exercise {@code createTerm} only up to the (draft) save, not
	 * completion. Completing a term needs the additional setup {@link AbstractFlatrateTermTest}
	 * keeps private to its own {@code createFlatrateTerm} helper (an order + order line, a tax
	 * category, price and quantity) - Cucumber TS8 exercises the duration-0 path through a full
	 * save-and-complete flow instead.
	 */
	@Nested
	class CreateTerm
	{
		@Test
		public void durationZero_withRequestedEndDate_keepsEndDate()
		{
			final I_C_Flatrate_Conditions conditions = createConditionsWithTermDuration(0, X_C_Flatrate_Transition.TERMDURATIONUNIT_MonatE);

			final Timestamp requestedEndDate = TimeUtil.getDay(2020, 12, 31);

			final I_C_Flatrate_Term term = flatrateBL.createTerm(requestBuilder(conditions)
					.endDate(requestedEndDate)
					.build());

			assertThat(term.getEndDate()).isEqualTo(requestedEndDate);
			// fixture notice = 0 days (FlatrateTermDataFactory's default transition)
			assertThat(term.getNoticeDate()).isEqualTo(requestedEndDate);
		}

		@Test
		public void durationZero_withoutEndDate_saveRefused()
		{
			final I_C_Flatrate_Conditions conditions = createConditionsWithTermDuration(0, X_C_Flatrate_Transition.TERMDURATIONUNIT_MonatE);

			assertThatThrownBy(() -> flatrateBL.createTerm(requestBuilder(conditions).build()))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("FillMandatory:EndDate (Should have been set by the system!)");
		}

		@Test
		public void durationGreaterThanZero_withoutEndDate_computesEndDate()
		{
			final I_C_Flatrate_Conditions conditions = createConditionsWithTermDuration(3, X_C_Flatrate_Transition.TERMDURATIONUNIT_MonatE);

			final I_C_Flatrate_Term term = flatrateBL.createTerm(requestBuilder(conditions).build());

			// startDate (2020-01-01) + 3 months - 1 day
			assertThat(term.getEndDate()).isEqualTo(TimeUtil.getDay(2020, 3, 31));
		}
	}
}
