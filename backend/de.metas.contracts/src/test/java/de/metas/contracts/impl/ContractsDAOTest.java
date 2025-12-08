package de.metas.contracts.impl;

import de.metas.common.util.time.SystemTime;
import de.metas.contracts.impl.ContractsTestBase.FixedTimeSource;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.*;

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

public class ContractsDAOTest
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void retrieveSubscriptionTermsWithMissingCandidates()
	{
		SystemTime.setTimeSource(new FixedTimeSource(2013, 5, 28)); // today

		final I_C_Flatrate_Conditions conditions = newInstance(I_C_Flatrate_Conditions.class);
		conditions.setType_Conditions(X_C_Flatrate_Term.TYPE_CONDITIONS_Subscription);
		conditions.setIsCreateNoInvoice(false);
		save(conditions);

		final I_C_Flatrate_Term term1 = newInstance(I_C_Flatrate_Term.class);
		POJOWrapper.setInstanceName(term1, "term1");

		term1.setDocStatus(X_C_Flatrate_Term.DOCSTATUS_Completed);
		term1.setC_Flatrate_Conditions(conditions);
		term1.setType_Conditions(X_C_Flatrate_Term.TYPE_CONDITIONS_Subscription);
		term1.setStartDate(TimeUtil.getDay(2013, 5, 27)); // yesterday
		save(term1);

		final List<I_C_Flatrate_Term> termsWithMissingCandidates = new ContractsDAO().retrieveSubscriptionTermsWithMissingCandidates(X_C_Flatrate_Term.TYPE_CONDITIONS_Subscription, QueryLimit.ofInt(50));

		assertThat(termsWithMissingCandidates).hasSize(1);
		assertThat(termsWithMissingCandidates.get(0)).isInstanceOf(I_C_Flatrate_Term.class);
		assertThat(termsWithMissingCandidates.get(0).getC_Flatrate_Term_ID()).isEqualTo(term1.getC_Flatrate_Term_ID());
	}

}
