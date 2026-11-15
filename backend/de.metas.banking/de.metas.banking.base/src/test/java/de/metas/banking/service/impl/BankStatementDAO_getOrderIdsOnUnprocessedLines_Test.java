package de.metas.banking.service.impl;

import de.metas.banking.service.IBankStatementDAO;
import de.metas.order.OrderId;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BankStatementLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

public class BankStatementDAO_getOrderIdsOnUnprocessedLines_Test
{
	private IBankStatementDAO bankStatementDAO;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		bankStatementDAO = Services.get(IBankStatementDAO.class);
	}

	private OrderId line(final int orderRepoId, final boolean processed, final boolean active)
	{
		final I_C_BankStatementLine line = newInstance(I_C_BankStatementLine.class);
		line.setC_Order_ID(orderRepoId);
		line.setProcessed(processed);
		line.setIsActive(active);
		saveRecord(line);
		return OrderId.ofRepoId(orderRepoId);
	}

	/** Only an active, not yet processed line carries its order; orders on no such line are not returned. */
	@Test
	void onlyActiveUnprocessedLinesCarryTheirOrder()
	{
		final OrderId unprocessed = line(1, false, true);
		final OrderId processed = line(2, true, true);
		final OrderId inactive = line(3, false, false);
		final OrderId onNoLine = OrderId.ofRepoId(4);

		assertThat(bankStatementDAO.getOrderIdsOnUnprocessedLines(ImmutableList.of(unprocessed, processed, inactive, onNoLine)))
				.containsExactly(unprocessed);
		assertThat(bankStatementDAO.getOrderIdsOnUnprocessedLines(ImmutableList.of(processed, onNoLine))).isEmpty();
	}
}
