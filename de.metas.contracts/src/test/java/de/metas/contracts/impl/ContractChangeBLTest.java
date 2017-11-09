package de.metas.contracts.impl;

import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

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

import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.util.Services;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;

import de.metas.contracts.IContractChangeBL;
import de.metas.contracts.IContractChangeBL.ContractChangeParameters;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.impl.FlatrateTermDataFactory.ProductAndPricingSystem;
import de.metas.contracts.interceptor.C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import lombok.NonNull;

public class ContractChangeBLTest extends AbstractFlatrateTermTest
{
	final private IContractChangeBL contractChangeBL = Services.get(IContractChangeBL.class);
	
	final private static Timestamp startDate = TimeUtil.parseTimestamp("2017-09-10");
	final private static Timestamp cancelDate = TimeUtil.parseTimestamp("2017-12-10");
	final private static String terminationMemo = "note: cancelContract_test";
	final private static ContractChangeParameters contractChangeParameters = ContractChangeParameters.builder()
			.changeDate(cancelDate)
			.isCloseInvoiceCandidate(true)
			.terminationReason(X_C_Flatrate_Term.TERMINATIONREASON_General)
			.terminationMemo(terminationMemo)
			.build();

	@Before
	public void before()
	{
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(C_Flatrate_Term.INSTANCE);
	}

	@Test
	public void cancelContract_test()
	{
		final I_C_Flatrate_Term contract = prepareContractForTest(true);
		contractChangeBL.cancelContract(contract, contractChangeParameters);
		assertFlatrateTerm(contract);
	}

	@Test
	public void cancel_a_Contract_which_was_extended()
	{
		final I_C_Flatrate_Term contract = prepareContractForTest(true);
		Services.get(IFlatrateBL.class).extendContract(contract, true, true, null, null);
		save(contract);
		
		final I_C_Flatrate_Term extendedContract = contract.getC_FlatrateTerm_Next();
		assertThat(extendedContract).isNotNull();
		
		contractChangeBL.cancelContract(contract, contractChangeParameters);
		assertFlatrateTerm(contract);
		assertThat(contract.getMasterEndDate()).isEqualTo(cancelDate);
		assertFlatrateTerm(extendedContract);
	}

	private I_C_Flatrate_Term prepareContractForTest(final boolean isAutoRenew)
	{
		prepareBPartner();
		final ProductAndPricingSystem productAndPricingSystem = createProductAndPricingSystem(startDate);
		createProductAcct(productAndPricingSystem);
		final I_C_Flatrate_Conditions conditions = createFlatrateConditions(productAndPricingSystem, isAutoRenew);
		createContractChange(conditions);
		final I_C_Flatrate_Term contract = createFlatrateTerm(conditions, productAndPricingSystem.getProduct(), startDate);
		return contract;
	}

	private void assertFlatrateTerm(@NonNull final I_C_Flatrate_Term currentflatrateTerm)
	{
		assertThat(currentflatrateTerm.getContractStatus()).isEqualTo(X_C_Flatrate_Term.CONTRACTSTATUS_Quit);
		assertThat(currentflatrateTerm.isAutoRenew()).isFalse();
		assertThat(currentflatrateTerm.getMasterStartDate()).isNotNull();
		assertThat(currentflatrateTerm.getMasterEndDate()).isEqualTo(cancelDate);
		assertThat(currentflatrateTerm.getTerminationMemo()).isEqualTo(terminationMemo);
		assertThat(currentflatrateTerm.getTerminationReason()).isEqualTo(X_C_Flatrate_Term.TERMINATIONREASON_General);
		
	}
}
