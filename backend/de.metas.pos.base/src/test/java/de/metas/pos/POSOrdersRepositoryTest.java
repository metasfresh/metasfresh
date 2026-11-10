package de.metas.pos;

import de.metas.pos.repository.model.I_C_POS_Order;
import de.metas.pos.repository.model.I_C_POS_Payment;
import de.metas.util.Services;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Issue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class POSOrdersRepositoryTest
{
	private static final POSTerminalId TERMINAL_1 = POSTerminalId.ofRepoId(1);
	private static final POSTerminalId TERMINAL_2 = POSTerminalId.ofRepoId(2);

	private POSOrdersRepository repository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		repository = new POSOrdersRepository(new POSOrderEventDispatcher(Optional.empty()));
	}

	private int newOrder(final POSTerminalId terminalId)
	{
		final I_C_POS_Order order = InterfaceWrapperHelper.newInstance(I_C_POS_Order.class);
		order.setC_POS_ID(terminalId.getRepoId());
		InterfaceWrapperHelper.saveRecord(order);
		return order.getC_POS_Order_ID();
	}

	private POSPaymentId newPayment(final int orderId, final POSPaymentMethod method, final String status)
	{
		final I_C_POS_Payment payment = InterfaceWrapperHelper.newInstance(I_C_POS_Payment.class);
		payment.setC_POS_Order_ID(orderId);
		payment.setPOSPaymentMethod(method.getCode());
		payment.setPOSPaymentProcessingStatus(status);
		InterfaceWrapperHelper.saveRecord(payment);
		return POSPaymentId.ofRepoId(payment.getC_POS_Payment_ID());
	}

	private void newIssue(final String tableName, final int recordId, final String errorCode)
	{
		final I_AD_Issue issue = InterfaceWrapperHelper.newInstance(I_AD_Issue.class);
		issue.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(tableName));
		issue.setRecord_ID(recordId);
		issue.setErrorCode(errorCode);
		InterfaceWrapperHelper.saveRecord(issue);
	}

	@Test
	void listCardPaymentStatuses_isScopedToTerminal_cardOnly_andOldestFirst()
	{
		final int order1 = newOrder(TERMINAL_1);
		final int order1b = newOrder(TERMINAL_1);
		final int order2 = newOrder(TERMINAL_2);

		final POSPaymentId first = newPayment(order1, POSPaymentMethod.CARD, "UNKNOWN");
		newPayment(order1, POSPaymentMethod.CASH, "SUCCESSFUL");
		newPayment(order2, POSPaymentMethod.CARD, "SUCCESSFUL");
		final POSPaymentId second = newPayment(order1b, POSPaymentMethod.CARD, "SUCCESSFUL");

		final List<POSCardPaymentStatus> result = repository.listCardPaymentStatusesByTerminalId(TERMINAL_1);

		assertThat(result).containsExactly(
				new POSCardPaymentStatus(first, "UNKNOWN"),
				new POSCardPaymentStatus(second, "SUCCESSFUL"));
		assertThat(repository.listCardPaymentStatusesByTerminalId(POSTerminalId.ofRepoId(99))).isEmpty();
	}

	@Test
	void listIssueErrorCodes_matchesByPaymentRecord_only_andKeepsIssuesWithoutErrorCode()
	{
		final int order = newOrder(TERMINAL_1);
		final POSPaymentId payment = newPayment(order, POSPaymentMethod.CARD, "UNKNOWN");
		final POSPaymentId otherPayment = newPayment(order, POSPaymentMethod.CARD, "UNKNOWN");

		newIssue(I_C_POS_Payment.Table_Name, payment.getRepoId(), "CodeA");
		newIssue(I_C_POS_Payment.Table_Name, payment.getRepoId(), null);
		newIssue(I_C_POS_Payment.Table_Name, otherPayment.getRepoId(), "CodeOther");
		// same record id, different table: must not match
		newIssue(I_C_POS_Order.Table_Name, payment.getRepoId(), "CodeWrongTable");

		assertThat(repository.listIssueErrorCodesByPaymentId(payment)).containsExactly("CodeA", POSOrdersRepository.NO_ERROR_CODE);
		assertThat(repository.listIssueErrorCodesByPaymentId(otherPayment)).containsExactly("CodeOther");
	}
}
