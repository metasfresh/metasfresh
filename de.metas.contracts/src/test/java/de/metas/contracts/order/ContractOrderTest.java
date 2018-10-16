package de.metas.contracts.order;

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
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.contracts.ContractLibraryConfiguration;
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
import de.metas.util.Services;
import de.metas.util.time.SystemTime;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class, ShutdownListener.class,
		ContractLibraryConfiguration.class
		})
public class ContractOrderTest extends AbstractFlatrateTermTest
{
	final private IContractChangeBL contractChangeBL = Services.get(IContractChangeBL.class);

	final private static Timestamp startDate = TimeUtil.parseTimestamp("2017-09-10");
	final private static String terminationMemo = "note: cancelContract_test";
	final private static FixedTimeSource today = new FixedTimeSource(2017, 11, 10);

	@Before
	public void before()
	{
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(C_Flatrate_Term.INSTANCE);
		SystemTime.setTimeSource(today);
	}

	@Test
	public void cancel_a_ContractOrder()
	{
		final I_C_Flatrate_Term contract = prepareContractForTest(X_C_Flatrate_Transition.EXTENSIONTYPE_ExtendOne, startDate);

		final ContractExtendingRequest context = ContractExtendingRequest.builder()
				.AD_PInstance_ID(1)
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
		final I_C_OrderLine orderLineExtended = extendContractOrder(contract);

		final I_C_Flatrate_Term newContract = prepareContractForTest(X_C_Flatrate_Transition.EXTENSIONTYPE_ExtendOne, startDate);
		newContract.setC_OrderLine_Term(orderLineExtended);
		InterfaceWrapperHelper.save(newContract);

		final Timestamp cancellingDate = TimeUtil.parseTimestamp("2018-12-10");
		final ContractChangeParameters changeParameters = ContractChangeParameters.builder()
				.changeDate(cancellingDate)
				.isCloseInvoiceCandidate(true)
				.terminationReason(X_C_Flatrate_Term.TERMINATIONREASON_General)
				.terminationMemo(terminationMemo)
				.build();

		contractChangeBL.cancelContract(newContract, changeParameters);

		InterfaceWrapperHelper.refresh(newContract);
		InterfaceWrapperHelper.refresh(order);

		assertThat(order.getContractStatus()).isEqualTo(I_C_Order.CONTRACTSTATUS_Cancelled);
		final I_C_Order extendedtOrder = InterfaceWrapperHelper.create(orderLineExtended.getC_Order(), I_C_Order.class);
		assertThat(extendedtOrder.getContractStatus()).isEqualTo(I_C_Order.CONTRACTSTATUS_Cancelled);
	}

	@Test
	public void void_a_Term_of_a_ContractOrder()
	{
		final I_C_Flatrate_Term contract = prepareContractForTest(X_C_Flatrate_Transition.EXTENSIONTYPE_ExtendOne, startDate);

		final ContractExtendingRequest context = ContractExtendingRequest.builder()
				.AD_PInstance_ID(1)
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
		final I_C_OrderLine orderLineExtended = extendContractOrder(contract);

		final I_C_Flatrate_Term newContract = prepareContractForTest(X_C_Flatrate_Transition.EXTENSIONTYPE_ExtendOne, startDate);
		newContract.setC_OrderLine_Term(orderLineExtended);
		InterfaceWrapperHelper.save(newContract);

		final Timestamp cancellingDate = TimeUtil.parseTimestamp("2018-12-10");
		final ContractChangeParameters changeParameters = ContractChangeParameters.builder()
				.changeDate(cancellingDate)
				.isCloseInvoiceCandidate(true)
				.action(IContractChangeBL.ChangeTerm_ACTION_VoidSingleContract)
				.terminationReason(X_C_Flatrate_Term.TERMINATIONREASON_General)
				.terminationMemo(terminationMemo)
				.build();

		contractChangeBL.cancelContract(newContract, changeParameters);

		InterfaceWrapperHelper.refresh(newContract);
		InterfaceWrapperHelper.refresh(order);

		assertThat(order.getContractStatus()).isEqualTo(I_C_Order.CONTRACTSTATUS_Active);
		final I_C_Order extendedtOrder = InterfaceWrapperHelper.create(orderLineExtended.getC_Order(), I_C_Order.class);
		assertThat(extendedtOrder.getContractStatus()).isEqualTo(I_C_Order.CONTRACTSTATUS_Cancelled);
	}

	@Test
	public void void_a_term_of_a_ContractOrder_with_more_the_one_term()
	{
		final I_C_Flatrate_Term contract = prepareContractForTest(X_C_Flatrate_Transition.EXTENSIONTYPE_ExtendOne, startDate);


		final I_C_Order order = InterfaceWrapperHelper.create(contract.getC_OrderLine_Term().getC_Order(), I_C_Order.class);
		assertThat(order.getContractStatus()).isEqualTo(I_C_Order.CONTRACTSTATUS_Active);

		// simulate extending order
		final I_C_OrderLine orderLineExtended = extendContractOrder(contract);

		InterfaceWrapperHelper.refresh(order);
		assertThat(order.getContractStatus()).isEqualTo(I_C_Order.CONTRACTSTATUS_Extended);

		final I_C_Flatrate_Term newContract = prepareContractForTest(X_C_Flatrate_Transition.EXTENSIONTYPE_ExtendOne, startDate);
		newContract.setC_OrderLine_Term(orderLineExtended);
		InterfaceWrapperHelper.save(newContract);


		final ContractExtendingRequest context = ContractExtendingRequest.builder()
				.AD_PInstance_ID(1)
				.contract(newContract)
				.forceExtend(true)
				.forceComplete(true)
				.nextTermStartDate(null)
				.build();

		Services.get(IFlatrateBL.class).extendContractAndNotifyUser(context);

		final I_C_Flatrate_Term extendedContract = newContract.getC_FlatrateTerm_Next();
		assertThat(extendedContract).isNotNull();


		final Timestamp cancellingDate = TimeUtil.parseTimestamp("2018-12-10");
		final ContractChangeParameters changeParameters = ContractChangeParameters.builder()
				.changeDate(cancellingDate)
				.isCloseInvoiceCandidate(true)
				.action(IContractChangeBL.ChangeTerm_ACTION_VoidSingleContract)
				.terminationReason(X_C_Flatrate_Term.TERMINATIONREASON_General)
				.terminationMemo(terminationMemo)
				.build();

		contractChangeBL.cancelContract(extendedContract, changeParameters);

		InterfaceWrapperHelper.refresh(newContract);
		InterfaceWrapperHelper.refresh(order);

		assertThat(order.getContractStatus()).isEqualTo(I_C_Order.CONTRACTSTATUS_Extended);
		final I_C_Order extendedOrder = InterfaceWrapperHelper.create(orderLineExtended.getC_Order(), I_C_Order.class);
		assertThat(extendedOrder.getContractStatus()).isEqualTo(I_C_Order.CONTRACTSTATUS_Active);
	}

	private I_C_OrderLine extendContractOrder(final I_C_Flatrate_Term contract)
	{
		final I_C_Order contractOrder = InterfaceWrapperHelper.create(contract.getC_OrderLine_Term().getC_Order(), I_C_Order.class);
		final I_C_OrderLine orderLineExtended = createOrderAndOrderLine(contract.getC_Flatrate_Conditions(), contract.getM_Product());
		contractOrder.setRef_FollowupOrder_ID(orderLineExtended.getC_Order_ID());
		contractOrder.setContractStatus(I_C_Order.CONTRACTSTATUS_Extended);
		InterfaceWrapperHelper.save(contractOrder);
		return orderLineExtended;
	}
}
