package de.metas.contracts.subscription.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.subscription.ISubscriptionDAO.SubscriptionProgressQuery;

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

public class SubscriptionDAOTest
{

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void subscriptionProgressQuery_startingWith()
	{
		final I_C_Flatrate_Term term = newInstance(I_C_Flatrate_Term.class);
		save(term);
		final I_C_SubscriptionProgress subscriptionProgress = newInstance(I_C_SubscriptionProgress.class);
		subscriptionProgress.setC_Flatrate_Term(term);
		subscriptionProgress.setEventDate(TimeUtil.parseTimestamp("2017-10-01"));
		subscriptionProgress.setSeqNo(10);
		save(subscriptionProgress);

		final SubscriptionProgressQuery query = SubscriptionProgressQuery.startingWith(subscriptionProgress).build();

		final List<I_C_SubscriptionProgress> resultList = new SubscriptionDAO().retrieveSubscriptionProgresses(query);
		assertThat(resultList).hasSize(1);
		assertThat(resultList.get(0).getC_SubscriptionProgress_ID()).isEqualTo(subscriptionProgress.getC_SubscriptionProgress_ID());

		final I_C_SubscriptionProgress singleResult = new SubscriptionDAO().retrieveFirstSubscriptionProgress(query);
		assertThat(singleResult.getC_SubscriptionProgress_ID()).isEqualTo(subscriptionProgress.getC_SubscriptionProgress_ID());
	}

}
