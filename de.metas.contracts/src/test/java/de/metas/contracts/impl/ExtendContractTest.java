package de.metas.contracts.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;

import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.util.Services;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;

import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.IFlatrateBL.ContractExtendingRequest;
import de.metas.contracts.impl.FlatrateTermDataFactory.ProductAndPricingSystem;
import de.metas.contracts.interceptor.C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import lombok.NonNull;

public class ExtendContractTest extends AbstractFlatrateTermTest
{

	final private static Timestamp startDate = TimeUtil.parseTimestamp("2017-09-10");

	@Before
	public void before()
	{
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(C_Flatrate_Term.INSTANCE);
	}


	@Test
	public void extendContractWithAutoRenewOnYes_test()
	{
		final I_C_Flatrate_Term contract = prepareContractForTest(true);

		final ContractExtendingRequest context = ContractExtendingRequest.builder()
				.AD_PInstance_ID(1)
				.contract(contract)
				.forceExtend(true)
				.forceComplete(true)
				.nextTermStartDate(null)
				.build();

		Services.get(IFlatrateBL.class).extendContract(context);

		assertFlatrateTerm(contract);
		assertPartnerData(contract);
	}

	@Test
	public void extendContractWithAutoRenewOnNo_test()
	{
		final I_C_Flatrate_Term contract = prepareContractForTest(false);

		final ContractExtendingRequest context = ContractExtendingRequest.builder()
				.AD_PInstance_ID(1)
				.contract(contract)
				.forceExtend(true)
				.forceComplete(true)
				.nextTermStartDate(null)
				.build();

		Services.get(IFlatrateBL.class).extendContract(context);

		assertFlatrateTerm(contract);
		assertPartnerData(contract);
	}

	private I_C_Flatrate_Term prepareContractForTest(final boolean isAutoRenew)
	{
		prepareBPartner();
		final ProductAndPricingSystem productAndPricingSystem = createProductAndPricingSystem(startDate);
		createProductAcct(productAndPricingSystem);
		final I_C_Flatrate_Conditions conditions = createFlatrateConditions(productAndPricingSystem, isAutoRenew);
		final I_C_Flatrate_Term contract = createFlatrateTerm(conditions, productAndPricingSystem.getProduct(), startDate);
		return contract;
	}

	private void assertFlatrateTerm(@NonNull final I_C_Flatrate_Term currentflatrateTerm)
	{
		final I_C_Flatrate_Term nextflatrateTerm = currentflatrateTerm.getC_FlatrateTerm_Next();
		assertThat(nextflatrateTerm).isNotNull();
		assertThat(currentflatrateTerm.getC_Flatrate_Conditions()).isEqualTo(nextflatrateTerm.getC_Flatrate_Conditions());
		assertThat(currentflatrateTerm.getPlannedQtyPerUnit()).isEqualTo(nextflatrateTerm.getPlannedQtyPerUnit());

		final Timestamp startDateNewContract = TimeUtil.addDays(currentflatrateTerm.getEndDate(), 1);
		assertThat(nextflatrateTerm.getStartDate()).isEqualTo(startDateNewContract);

		assertThat(currentflatrateTerm.getMasterStartDate()).isEqualTo(nextflatrateTerm.getMasterStartDate());
		assertThat(currentflatrateTerm.getMasterEndDate()).isEqualTo(nextflatrateTerm.getMasterEndDate());
		assertThat(nextflatrateTerm.getMasterStartDate()).isNotNull();
		assertThat(currentflatrateTerm.isAutoRenew()).isEqualTo(nextflatrateTerm.isAutoRenew());
		if (currentflatrateTerm.isAutoRenew())
		{
			assertThat(currentflatrateTerm.getMasterEndDate()).isNull();
			assertThat(nextflatrateTerm.getMasterEndDate()).isNull();
		}
		else
		{
			assertThat(currentflatrateTerm.getMasterEndDate()).isNotNull();
			assertThat(nextflatrateTerm.getMasterEndDate()).isNotNull();

			final Timestamp expectedMasterEndDate = TimeUtil.addDays(TimeUtil.addYears(startDate, 2), -1);
			assertThat(currentflatrateTerm.getMasterEndDate()).isEqualTo(expectedMasterEndDate);
			assertThat(nextflatrateTerm.getMasterEndDate()).isEqualTo(expectedMasterEndDate);
		}
	}

	private void assertPartnerData(@NonNull final I_C_Flatrate_Term currentflatrateTerm)
	{
		final I_C_Flatrate_Term nextflatrateTerm = currentflatrateTerm.getC_FlatrateTerm_Next();

		assertThat(currentflatrateTerm.getBill_BPartner()).isEqualTo(nextflatrateTerm.getBill_BPartner());
		assertThat(currentflatrateTerm.getBill_Location()).isEqualTo(nextflatrateTerm.getBill_Location());
		assertThat(currentflatrateTerm.getBill_User()).isEqualTo(nextflatrateTerm.getBill_User());

		assertThat(currentflatrateTerm.getDropShip_BPartner()).isEqualTo(nextflatrateTerm.getDropShip_BPartner());
		assertThat(currentflatrateTerm.getDropShip_Location()).isEqualTo(nextflatrateTerm.getDropShip_Location());
		assertThat(currentflatrateTerm.getDropShip_User()).isEqualTo(nextflatrateTerm.getDropShip_User());
	}
}
