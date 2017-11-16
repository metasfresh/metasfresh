package de.metas.contracts.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.sql.Timestamp;
import java.util.List;

import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;

import de.metas.contracts.IContractChangeBL;
import de.metas.contracts.IContractsDAO;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.interceptor.C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.model.X_C_SubscriptionProgress;
import lombok.NonNull;

public class ContractChangePriceQtyTest extends AbstractFlatrateTermTest
{
	final private IContractChangeBL contractChangeBL = Services.get(IContractChangeBL.class);
	final private IContractsDAO contractsDAO = Services.get(IContractsDAO.class);
	final private static Timestamp startDate = TimeUtil.parseTimestamp("2017-09-10");

	final private ContractChangePriceQtyRepository contractsRepository = Adempiere.getBean(ContractChangePriceQtyRepository.class);


	@Before
	public void before()
	{
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(C_Flatrate_Term.INSTANCE);
	}

	@Test
	public void changeQty()
	{
		final I_C_Flatrate_Term contract = prepareContractForTest(true, startDate);
		contractsRepository.changeFlatrateTermQty(contract, BigDecimal.valueOf(5));
		assertFlatrateTerm(contract);
		assertSubscriptionProgress(contract, 1);
	}

	@Test
	public void changePrice()
	{
		final I_C_Flatrate_Term contract = prepareContractForTest(true, startDate);
		contractsRepository.changeFlatrateTermPrice(contract, BigDecimal.valueOf(5));
		assertFlatrateTerm(contract);
		assertSubscriptionProgress(contract, 1);
	}


	private void assertFlatrateTerm(@NonNull final I_C_Flatrate_Term flatrateTerm)
	{
		assertThat(flatrateTerm.getContractStatus()).isEqualTo(X_C_Flatrate_Term.CONTRACTSTATUS_Quit);
		assertThat(flatrateTerm.isAutoRenew()).isFalse();
		assertThat(flatrateTerm.getMasterStartDate()).isNotNull();
	}

	private void assertSubscriptionProgress(@NonNull final I_C_Flatrate_Term flatrateTerm, final int expected)
	{
		final List<I_C_SubscriptionProgress> subscriptionProgress = contractsDAO.getSubscriptionProgress(flatrateTerm);
		assertThat(subscriptionProgress).hasSize(expected);

		subscriptionProgress.stream()
				.filter(progress -> progress.getEventDate().before(flatrateTerm.getMasterEndDate()))
				.peek(progress -> assertThat(progress.getContractStatus()).isEqualTo(X_C_SubscriptionProgress.CONTRACTSTATUS_Quit))
				.filter(progress -> progress.getEventDate().after(flatrateTerm.getMasterEndDate()))
				.peek(progress -> assertThat(progress.getContractStatus()).isEqualTo(X_C_SubscriptionProgress.CONTRACTSTATUS_Running));
	}

	private void assertClosedFlatrateTerm(@NonNull final I_C_Flatrate_Term flatrateTerm)
	{
		assertThat(flatrateTerm.getDocStatus()).isEqualTo(X_C_Flatrate_Term.DOCSTATUS_Completed);
		assertThat(flatrateTerm.getContractStatus()).isEqualTo(X_C_Flatrate_Term.CONTRACTSTATUS_Quit);
		assertThat(flatrateTerm.getMasterStartDate()).isNull();
		assertThat(flatrateTerm.getMasterEndDate()).isNull();
		assertThat(flatrateTerm.isAutoRenew()).isFalse();
		assertThat(flatrateTerm.getC_FlatrateTerm_Next()).isNull();
		assertThat(flatrateTerm.getAD_PInstance_EndOfTerm()).isNull();

		final I_C_Flatrate_Term ancestor = Services.get(IFlatrateDAO.class).retrieveAncestorFlatrateTerm(flatrateTerm);

		assertThat(ancestor).isNull();
	}

}
