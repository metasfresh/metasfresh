package de.metas.contracts.order;

import de.metas.acct.GLCategoryRepository;
import de.metas.ad_reference.ADReferenceService;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.common.util.time.SystemTime;
import de.metas.contracts.IContractChangeBL;
import de.metas.contracts.IContractChangeBL.ContractChangeParameters;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.IFlatrateBL.ContractExtendingRequest;
import de.metas.contracts.impl.AbstractFlatrateTermTest;
import de.metas.contracts.impl.ContractsTestBase.FixedTimeSource;
import de.metas.contracts.interceptor.C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Transition;
import de.metas.contracts.order.model.I_C_Order;
import de.metas.contracts.order.model.I_C_OrderLine;
import de.metas.location.impl.DummyDocumentLocationBL;
import de.metas.pricing.tax.ProductTaxCategoryRepository;
import de.metas.pricing.tax.ProductTaxCategoryService;
import de.metas.process.PInstanceId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductAndCategoryId;
import de.metas.product.ProductId;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.*;

public class ContractOrderTest extends AbstractFlatrateTermTest
{
	final private static Timestamp startDate = TimeUtil.parseTimestamp("2017-09-10");
	final private static String terminationMemo = "note: cancelContract_test";
	final private static FixedTimeSource today = new FixedTimeSource(2017, 11, 10);

	@Override
	protected void afterInit()
	{
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(
				new C_Flatrate_Term(
						new ContractOrderService(),
						new DummyDocumentLocationBL(new BPartnerBL(new UserRepository())),
						ADReferenceService.newMocked(),
						new GLCategoryRepository()
						));
		SystemTime.setTimeSource(today);
		SpringContextHolder.registerJUnitBean(new ProductTaxCategoryService(new ProductTaxCategoryRepository()));
	}

	@Test
	public void extend_a_ContractOrder()
	{
		final I_C_Flatrate_Term contract = prepareContractForTest(X_C_Flatrate_Transition.EXTENSIONTYPE_ExtendOne, startDate);
		createInitialContractOrder(contract);

		// simulate extending order
		final I_C_Flatrate_Term newContract = simulateExtendingContractOrder(contract);

		final I_C_Order initialOrder = InterfaceWrapperHelper.create(contract.getC_OrderLine_Term().getC_Order(), I_C_Order.class);
		assertThat(initialOrder.getContractStatus()).isEqualTo(I_C_Order.CONTRACTSTATUS_Extended);

		// final I_C_Order order =
		InterfaceWrapperHelper.create(newContract.getC_OrderLine_Term().getC_Order(), I_C_Order.class);
	}

	@Test
	public void cancel_a_ContractOrder()
	{
		final I_C_Flatrate_Term contract = prepareContractForTest(X_C_Flatrate_Transition.EXTENSIONTYPE_ExtendOne, startDate);

		// extend the contract
		//
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

		// simulate extending order
		//
		final I_C_Flatrate_Term newContract = simulateExtendingContractOrder(contract);

		// cancel the contract
		//
		final Timestamp cancellingDate = TimeUtil.parseTimestamp("2018-12-10");
		final ContractChangeParameters changeParameters = ContractChangeParameters.builder()
				.changeDate(cancellingDate)
				.isCloseInvoiceCandidate(true)
				.terminationReason(X_C_Flatrate_Term.TERMINATIONREASON_General)
				.terminationMemo(terminationMemo)
				.build();
		contractChangeBL.cancelContract(newContract, changeParameters);

		//
		//
		InterfaceWrapperHelper.refresh(order);
		final I_C_Order extendedOrder = InterfaceWrapperHelper.create(newContract.getC_OrderLine_Term().getC_Order(), I_C_Order.class);

		assertThat(order.getContractStatus()).isEqualTo(I_C_Order.CONTRACTSTATUS_Cancelled);
		assertThat(extendedOrder.getContractStatus()).isEqualTo(I_C_Order.CONTRACTSTATUS_Cancelled);
	}

	@Test
	public void void_a_Term_of_a_ContractOrder()
	{
		final I_C_Flatrate_Term contract = prepareContractForTest(X_C_Flatrate_Transition.EXTENSIONTYPE_ExtendOne, startDate);

		// extend the contract
		//
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

		// simulate extending order
		//
		final I_C_Flatrate_Term newContract = simulateExtendingContractOrder(contract);

		// void the contract
		//
		final Timestamp cancellingDate = TimeUtil.parseTimestamp("2018-12-10");
		final ContractChangeParameters changeParameters = ContractChangeParameters.builder()
				.changeDate(cancellingDate)
				.isCloseInvoiceCandidate(true)
				.action(IContractChangeBL.ChangeTerm_ACTION_VoidSingleContract)
				.terminationReason(X_C_Flatrate_Term.TERMINATIONREASON_General)
				.terminationMemo(terminationMemo)
				.build();
		contractChangeBL.cancelContract(newContract, changeParameters);

		InterfaceWrapperHelper.refresh(order);
		final I_C_Order extendedtOrder = InterfaceWrapperHelper.create(newContract.getC_OrderLine_Term().getC_Order(), I_C_Order.class);

		assertThat(order.getContractStatus()).isEqualTo(I_C_Order.CONTRACTSTATUS_Active);
		assertThat(extendedtOrder.getContractStatus()).isEqualTo(I_C_Order.CONTRACTSTATUS_Cancelled);
	}

	@Test
	public void void_a_term_of_a_ContractOrder_with_more_the_one_term()
	{
		final I_C_Flatrate_Term contract = prepareContractForTest(X_C_Flatrate_Transition.EXTENSIONTYPE_ExtendOne, startDate);

		final I_C_Order order = InterfaceWrapperHelper.create(contract.getC_OrderLine_Term().getC_Order(), I_C_Order.class);
		assertThat(order.getContractStatus()).isEqualTo(I_C_Order.CONTRACTSTATUS_Active);

		// simulate extending the order
		//
		final I_C_Flatrate_Term newContract = simulateExtendingContractOrder(contract);
		InterfaceWrapperHelper.refresh(order);
		assertThat(order.getContractStatus()).isEqualTo(I_C_Order.CONTRACTSTATUS_Extended);

		// extend the contract
		//
		final ContractExtendingRequest context = ContractExtendingRequest.builder()
				.AD_PInstance_ID(PInstanceId.ofRepoId(1))
				.contract(newContract)
				.forceExtend(true)
				.forceComplete(true)
				.nextTermStartDate(null)
				.build();
		Services.get(IFlatrateBL.class).extendContractAndNotifyUser(context);
		final I_C_Flatrate_Term extendedContract = newContract.getC_FlatrateTerm_Next();
		assertThat(extendedContract).isNotNull();

		// void the contract
		//
		final Timestamp cancellingDate = TimeUtil.parseTimestamp("2018-12-10");
		final ContractChangeParameters changeParameters = ContractChangeParameters.builder()
				.changeDate(cancellingDate)
				.isCloseInvoiceCandidate(true)
				.action(IContractChangeBL.ChangeTerm_ACTION_VoidSingleContract)
				.terminationReason(X_C_Flatrate_Term.TERMINATIONREASON_General)
				.terminationMemo(terminationMemo)
				.build();
		contractChangeBL.cancelContract(extendedContract, changeParameters);

		InterfaceWrapperHelper.refresh(order);
		final I_C_Order extendedOrder = InterfaceWrapperHelper.create(newContract.getC_OrderLine_Term().getC_Order(), I_C_Order.class);

		assertThat(order.getContractStatus()).isEqualTo(I_C_Order.CONTRACTSTATUS_Extended);
		assertThat(extendedOrder.getContractStatus()).isEqualTo(I_C_Order.CONTRACTSTATUS_Active);
	}

	private I_C_OrderLine createInitialContractOrder(final I_C_Flatrate_Term contract)
	{
		final I_C_Order contractOrder = InterfaceWrapperHelper.create(contract.getC_OrderLine_Term().getC_Order(), I_C_Order.class);
		final I_C_OrderLine orderLine = createOrderAndOrderLine(
				contract.getC_Flatrate_Conditions(),
				ProductId.ofRepoId(contract.getM_Product_ID()));
		contractOrder.setContractStatus(I_C_Order.CONTRACTSTATUS_Active);
		InterfaceWrapperHelper.save(contractOrder);

		contract.setC_OrderLine_Term_ID(orderLine.getC_OrderLine_ID());
		contract.setC_Order_Term_ID(orderLine.getC_Order_ID());
		InterfaceWrapperHelper.save(contract);

		return orderLine;
	}

	private I_C_Flatrate_Term simulateExtendingContractOrder(final I_C_Flatrate_Term contract)
	{
		final ProductId productId = ProductId.ofRepoId(contract.getM_Product_ID());
		final ProductAndCategoryId productAndCategoryId = Services.get(IProductDAO.class).retrieveProductAndCategoryIdByProductId(productId);
		final I_C_Flatrate_Term newContract = createFlatrateTerm(
				contract.getC_Flatrate_Conditions(),
				productAndCategoryId,
				TimeUtil.addDays(contract.getEndDate(), 1));
		contract.setC_FlatrateTerm_Next(newContract);
		InterfaceWrapperHelper.save(contract);

		final I_C_OrderLine orderLineExtended = InterfaceWrapperHelper.create(newContract.getC_OrderLine_Term(), I_C_OrderLine.class);
		final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(contract.getC_OrderLine_Term(), I_C_OrderLine.class);
		final I_C_Order contractOrder = InterfaceWrapperHelper.create(orderLine.getC_Order(), I_C_Order.class);

		contractOrder.setRef_FollowupOrder_ID(orderLineExtended.getC_Order_ID());
		contractOrder.setContractStatus(I_C_Order.CONTRACTSTATUS_Extended);
		InterfaceWrapperHelper.save(contractOrder);
		return newContract;
	}
}
