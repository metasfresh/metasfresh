package de.metas.contracts.impl;

import de.metas.acct.GLCategoryRepository;
import de.metas.ad_reference.ADReferenceService;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.IFlatrateBL.ContractExtendingRequest;
import de.metas.contracts.impl.FlatrateTermDataFactory.ProductAndPricingSystem;
import de.metas.contracts.interceptor.C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Transition;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_Transition;
import de.metas.contracts.order.ContractOrderService;
import de.metas.contracts.order.model.I_C_Order;
import de.metas.location.impl.DummyDocumentLocationBL;
import de.metas.pricing.tax.ProductTaxCategoryRepository;
import de.metas.pricing.tax.ProductTaxCategoryService;
import de.metas.process.PInstanceId;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.*;

public class ExtendContractTest extends AbstractFlatrateTermTest
{
	final private static Timestamp startDate = TimeUtil.parseTimestamp("2017-09-10");

	@Override
	protected void afterInit()
	{
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(
				new C_Flatrate_Term(
						new ContractOrderService(),
						new DummyDocumentLocationBL(new BPartnerBL(new UserRepository())),
						ADReferenceService.newMocked(),
						new GLCategoryRepository()));
		SpringContextHolder.registerJUnitBean(new ProductTaxCategoryService(new ProductTaxCategoryRepository()));
		SpringContextHolder.registerJUnitBean(new ProductTaxCategoryService(new ProductTaxCategoryRepository()));
	}

	@Test
	public void extendContractWithExtendingOnePeriod_test()
	{
		final I_C_Flatrate_Term contract = prepareContractForTest(X_C_Flatrate_Transition.EXTENSIONTYPE_ExtendOne);

		final ContractExtendingRequest context = ContractExtendingRequest.builder()
				.AD_PInstance_ID(PInstanceId.ofRepoId(1))
				.contract(contract)
				.forceExtend(true)
				.forceComplete(true)
				.nextTermStartDate(null)
				.build();

		Services.get(IFlatrateBL.class).extendContractAndNotifyUser(context);

		assertFlatrateTerm(contract);
		assertPartnerData(contract);
	}

	@Test
	public void extendContractWithNoExtensionType_test()
	{
		final I_C_Flatrate_Term contract = prepareContractForTest(null);

		final ContractExtendingRequest context = ContractExtendingRequest.builder()
				.AD_PInstance_ID(PInstanceId.ofRepoId(1))
				.contract(contract)
				.forceExtend(true)
				.forceComplete(true)
				.nextTermStartDate(null)
				.build();

		Services.get(IFlatrateBL.class).extendContractAndNotifyUser(context);

		assertFlatrateTerm(contract);
		assertPartnerData(contract);
	}

	@Test
	public void extendContractWithExtendingAll_test()
	{
		final I_C_Flatrate_Term contract = prepareContractForExtrendingAllTest(false);

		final ContractExtendingRequest context = ContractExtendingRequest.builder()
				.AD_PInstance_ID(PInstanceId.ofRepoId(1))
				.contract(contract)
				.forceExtend(false)
				.forceComplete(true)
				.nextTermStartDate(null)
				.build();

		Services.get(IFlatrateBL.class).extendContractAndNotifyUser(context);

		I_C_Flatrate_Term curentContract = contract;
		do
		{
			assertPartnerData(curentContract);
			final I_C_Flatrate_Term nextflatrateTerm = curentContract.getC_FlatrateTerm_Next();
			assertThat(nextflatrateTerm).isNotNull();
			assertThat(curentContract.getC_Flatrate_Conditions().getC_Flatrate_Transition().getC_Flatrate_Conditions_Next()).isEqualTo(nextflatrateTerm.getC_Flatrate_Conditions());

			final Timestamp startDateNewContract = TimeUtil.addDays(curentContract.getEndDate(), 1);
			assertThat(nextflatrateTerm.getStartDate()).isEqualTo(startDateNewContract);

			assertThat(curentContract.getMasterStartDate()).isEqualTo(nextflatrateTerm.getMasterStartDate());
			assertThat(nextflatrateTerm.getMasterStartDate()).isNotNull();
			assertThat(curentContract.getMasterEndDate()).isNotNull();
			assertThat(nextflatrateTerm.getMasterEndDate()).isNotNull();
			assertThat(curentContract.getMasterEndDate()).isEqualTo(nextflatrateTerm.getMasterEndDate());

			curentContract = nextflatrateTerm;
		}
		while (curentContract.getC_FlatrateTerm_Next() != null);
	}

	@Test
	public void collisionDetectWhenExtendContractWithExtendingAll_test()
	{
		final I_C_Flatrate_Term contract = prepareContractForExtrendingAllTest(true);

		final ContractExtendingRequest context = ContractExtendingRequest.builder()
				.AD_PInstance_ID(PInstanceId.ofRepoId(1))
				.contract(contract)
				.forceExtend(false)
				.forceComplete(true)
				.nextTermStartDate(null)
				.build();

		assertThatThrownBy(() -> {
			Services.get(IFlatrateBL.class).extendContractAndNotifyUser(context);
		}).isInstanceOf(AdempiereException.class)
				.hasMessageContaining(FlatrateBL.MSG_INFINITE_LOOP.toAD_Message());
	}

	private I_C_Flatrate_Term prepareContractForTest(final String autoExtension)
	{
		prepareBPartner();
		final ProductAndPricingSystem productAndPricingSystem = createProductAndPricingSystem(startDate);
		createProductAcct(productAndPricingSystem);
		final I_C_Flatrate_Conditions conditions = createFlatrateConditions(productAndPricingSystem, autoExtension);
		return createFlatrateTerm(
				conditions,
				productAndPricingSystem.getProductAndCategoryId(),
				startDate);
	}

	private I_C_Flatrate_Term prepareContractForExtrendingAllTest(final boolean infiniteLoop)
	{
		prepareBPartner();
		final ProductAndPricingSystem productAndPricingSystem = createProductAndPricingSystem(startDate);
		createProductAcct(productAndPricingSystem);

		final List<I_C_Flatrate_Conditions> conditions = new ArrayList<>();
		final List<I_C_Flatrate_Transition> transitions = new ArrayList<>();
		for (int i = 0; i < 5; i++)
		{
			final I_C_Flatrate_Conditions condition = newInstance(I_C_Flatrate_Conditions.class);
			condition.setM_PricingSystem_ID(productAndPricingSystem.getPricingSystem().getM_PricingSystem_ID());
			condition.setInvoiceRule(X_C_Flatrate_Conditions.INVOICERULE_Immediate);
			condition.setType_Conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Subscription);
			condition.setOnFlatrateTermExtend(X_C_Flatrate_Conditions.ONFLATRATETERMEXTEND_CalculatePrice);
			condition.setName("Abo " + i);
			save(condition);
			conditions.add(condition);

			final I_C_Flatrate_Transition transition = FlatrateTermDataFactory.flatrateTransitionNew()
					.calendar(getCalendar())
					.deliveryInterval(1)
					.deliveryIntervalUnit(X_C_Flatrate_Transition.DELIVERYINTERVALUNIT_MonatE)
					.termDuration(1)
					.termDurationUnit(X_C_Flatrate_Transition.TERMDURATIONUNIT_JahrE)
					.isAutoCompleteNewTerm(true)
					.extensionType(X_C_Flatrate_Transition.EXTENSIONTYPE_ExtendAll)
					.build();
			transitions.add(transition);
		}

		for (int i = 0; i < 5; i++)
		{
			final I_C_Flatrate_Conditions condition = conditions.get(i);
			final I_C_Flatrate_Transition transition = transitions.get(i);
			condition.setC_Flatrate_Transition(transition);
			save(condition);
			if (i < 4)
			{
				transition.setC_Flatrate_Conditions_Next(conditions.get(i + 1));
			}
			else
			{
				if (infiniteLoop)
				{
					transition.setC_Flatrate_Conditions_Next(conditions.get(0));
				}
				else
				{
					transition.setExtensionType(null);
				}
			}
			save(transition);
		}

		return createFlatrateTerm(
				conditions.get(0),
				productAndPricingSystem.getProductAndCategoryId(),
				startDate);
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

		final I_C_Order order = InterfaceWrapperHelper.create(currentflatrateTerm.getC_OrderLine_Term().getC_Order(), I_C_Order.class);
		assertThat(order.getContractStatus()).isEqualTo(I_C_Order.CONTRACTSTATUS_Active);
	}

	private void assertPartnerData(@NonNull final I_C_Flatrate_Term currentflatrateTerm)
	{
		final I_C_Flatrate_Term nextflatrateTerm = currentflatrateTerm.getC_FlatrateTerm_Next();

		assertThat(currentflatrateTerm.getBill_BPartner_ID()).isEqualTo(nextflatrateTerm.getBill_BPartner_ID());
		assertThat(currentflatrateTerm.getBill_Location_ID()).isEqualTo(nextflatrateTerm.getBill_Location_ID());
		assertThat(currentflatrateTerm.getBill_User_ID()).isEqualTo(nextflatrateTerm.getBill_User_ID());

		assertThat(currentflatrateTerm.getDropShip_BPartner_ID()).isEqualTo(nextflatrateTerm.getDropShip_BPartner_ID());
		assertThat(currentflatrateTerm.getDropShip_Location_ID()).isEqualTo(nextflatrateTerm.getDropShip_Location_ID());
		assertThat(currentflatrateTerm.getDropShip_User_ID()).isEqualTo(nextflatrateTerm.getDropShip_User_ID());
	}
}
