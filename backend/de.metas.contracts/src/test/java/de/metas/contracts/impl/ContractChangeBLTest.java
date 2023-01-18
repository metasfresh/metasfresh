package de.metas.contracts.impl;

import de.metas.acct.GLCategoryRepository;
import de.metas.ad_reference.ADReferenceService;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.common.util.time.SystemTime;
import de.metas.contracts.IContractChangeBL;
import de.metas.contracts.IContractChangeBL.ContractChangeParameters;
import de.metas.contracts.IContractsDAO;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.IFlatrateBL.ContractExtendingRequest;
import de.metas.contracts.impl.ContractsTestBase.FixedTimeSource;
import de.metas.contracts.interceptor.C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Transition;
import de.metas.contracts.model.X_C_SubscriptionProgress;
import de.metas.contracts.order.ContractOrderService;
import de.metas.contracts.order.model.I_C_Order;
import de.metas.location.impl.DummyDocumentLocationBL;
import de.metas.process.PInstanceId;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ContractChangeBLTest extends AbstractFlatrateTermTest
{
	final private IContractChangeBL contractChangeBL = Services.get(IContractChangeBL.class);
	final private IContractsDAO contractsDAO = Services.get(IContractsDAO.class);

	final private static Timestamp startDate = TimeUtil.parseTimestamp("2017-09-10");
	final private static Timestamp cancelDate = TimeUtil.parseTimestamp("2017-12-10");
	final private static String terminationMemo = "note: cancelContract_test";
	final private static FixedTimeSource today = new FixedTimeSource(2017, 11, 10);
	final private static ContractChangeParameters contractChangeParameters = ContractChangeParameters.builder()
			.changeDate(cancelDate)
			.isCloseInvoiceCandidate(true)
			.terminationReason(X_C_Flatrate_Term.TERMINATIONREASON_General)
			.terminationMemo(terminationMemo)
			.build();

	@Override
	protected void afterInit()
	{
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(
				new C_Flatrate_Term(
						new ContractOrderService(),
						new DummyDocumentLocationBL(new BPartnerBL(new UserRepository())),
						ADReferenceService.newMocked(),
						new GLCategoryRepository()));
		SystemTime.setTimeSource(today);
	}

	@Test
	public void cancelContract_test()
	{
		final I_C_Flatrate_Term contract = prepareContractForTest(X_C_Flatrate_Transition.EXTENSIONTYPE_ExtendOne, startDate);
		contractChangeBL.cancelContract(contract, contractChangeParameters);
		assertFlatrateTerm(contract, cancelDate, X_C_Flatrate_Term.CONTRACTSTATUS_Quit);
		assertSubscriptionProgress(contract, 1);
		final I_C_Order order = InterfaceWrapperHelper.create(contract.getC_OrderLine_Term().getC_Order(), I_C_Order.class);
		assertThat(order.getContractStatus()).isEqualTo(I_C_Order.CONTRACTSTATUS_Cancelled);
	}

	@Test
	public void cancel_a_Contract_which_was_extended_using_a_date_from_initial_contract()
	{
		final I_C_Flatrate_Term contract = prepareContractForTest(X_C_Flatrate_Transition.EXTENSIONTYPE_ExtendOne, startDate);

		final ContractExtendingRequest context = ContractExtendingRequest.builder()
				.AD_PInstance_ID(PInstanceId.ofRepoId(1))
				.contract(contract)
				.forceExtend(true)
				.forceComplete(true)
				.nextTermStartDate(null)
				.build();

		Services.get(IFlatrateBL.class).extendContractAndNotifyUser(context);

		final I_C_Flatrate_Term extendedContract = contract.getC_FlatrateTerm_Next();
		assertThat(extendedContract).isNotNull();

		final I_C_Order order = InterfaceWrapperHelper.create(contract.getC_OrderLine_Term().getC_Order(), I_C_Order.class);
		assertThat(order.getContractStatus()).isEqualTo(I_C_Order.CONTRACTSTATUS_Active);

		contractChangeBL.cancelContract(contract, contractChangeParameters);

		InterfaceWrapperHelper.refresh(order);
		assertThat(order.getContractStatus()).isEqualTo(I_C_Order.CONTRACTSTATUS_Cancelled);
		assertFlatrateTerm(contract, cancelDate, X_C_Flatrate_Term.CONTRACTSTATUS_Quit);
		assertSubscriptionProgress(contract, 1);
		assertThat(contract.getMasterEndDate()).isEqualTo(cancelDate);
		assertFlatrateTerm(extendedContract, cancelDate, X_C_Flatrate_Term.CONTRACTSTATUS_Voided);
		assertSubscriptionProgress(extendedContract, 0);
	}

	@Test
	public void cancel_a_Contract_which_was_extended_using_a_date_from_extended_contract()
	{
		final I_C_Flatrate_Term contract = prepareContractForTest(X_C_Flatrate_Transition.EXTENSIONTYPE_ExtendOne, startDate);

		final ContractExtendingRequest context = ContractExtendingRequest.builder()
				.AD_PInstance_ID(PInstanceId.ofRepoId(1))
				.contract(contract)
				.forceExtend(true)
				.forceComplete(true)
				.nextTermStartDate(null)
				.build();

		Services.get(IFlatrateBL.class).extendContractAndNotifyUser(context);

		final I_C_Flatrate_Term extendedContract = contract.getC_FlatrateTerm_Next();
		assertThat(extendedContract).isNotNull();

		final I_C_Order order = InterfaceWrapperHelper.create(extendedContract.getC_OrderLine_Term().getC_Order(), I_C_Order.class);
		assertThat(order.getContractStatus()).isEqualTo(I_C_Order.CONTRACTSTATUS_Active);

		final Timestamp cancellingDate = TimeUtil.parseTimestamp("2018-12-10");
		final ContractChangeParameters changeParameters = ContractChangeParameters.builder()
				.changeDate(cancellingDate)
				.isCloseInvoiceCandidate(true)
				.terminationReason(X_C_Flatrate_Term.TERMINATIONREASON_General)
				.terminationMemo(terminationMemo)
				.build();

		contractChangeBL.cancelContract(extendedContract, changeParameters);

		InterfaceWrapperHelper.refresh(contract);
		InterfaceWrapperHelper.refresh(extendedContract);
		InterfaceWrapperHelper.refresh(order);

		assertThat(order.getContractStatus()).isEqualTo(I_C_Order.CONTRACTSTATUS_Cancelled);

		assertFlatrateTerm(contract, cancellingDate, X_C_Flatrate_Term.CONTRACTSTATUS_Quit);
		assertSubscriptionProgress(contract, 12);
		assertThat(contract.getMasterEndDate()).isEqualTo(cancellingDate);
		assertFlatrateTerm(extendedContract, cancellingDate, X_C_Flatrate_Term.CONTRACTSTATUS_Quit);
		assertSubscriptionProgress(extendedContract, 3);
		assertThat(contract.getMasterEndDate()).isEqualTo(extendedContract.getMasterEndDate());
	}

	private void assertFlatrateTerm(@NonNull final I_C_Flatrate_Term flatrateTerm, final Timestamp cancelinglDate, final String expectedContractStatus)
	{
		assertThat(flatrateTerm.getContractStatus()).isEqualTo(expectedContractStatus);
		assertThat(flatrateTerm.isAutoRenew()).isFalse();
		assertThat(flatrateTerm.getMasterStartDate()).isNotNull();
		assertThat(flatrateTerm.getMasterEndDate()).isEqualTo(cancelinglDate);
		assertThat(flatrateTerm.getTerminationMemo()).isEqualTo(terminationMemo);
		assertThat(flatrateTerm.getTerminationReason()).isEqualTo(X_C_Flatrate_Term.TERMINATIONREASON_General);
		assertThat(flatrateTerm.getTerminationDate()).isEqualTo(de.metas.common.util.time.SystemTime.asDayTimestamp());
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
}
