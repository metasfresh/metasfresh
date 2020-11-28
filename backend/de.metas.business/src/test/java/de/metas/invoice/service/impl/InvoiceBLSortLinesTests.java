package de.metas.invoice.service.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_InOutLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.currency.CurrencyRepository;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

public class InvoiceBLSortLinesTests
{
	@BeforeEach
	public final void beforeEach()
	{
		AdempiereTestHelper.get().init();

		SpringContextHolder.registerJUnitBean(new CurrencyRepository());
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		sysConfigBL.setValue(AbstractInvoiceBL.SYSCONFIG_SortILsByShipmentLineOrders, false, ClientId.SYSTEM, OrgId.ANY);
	}

	@Builder(builderMethodName = "invoiceLine", builderClassName = "$InvoiceLineBuilder")
	private I_C_InvoiceLine createInvoiceLine(
			@NonNull final String name,
			final boolean freightCost,
			final int lineNo,
			final int inoutId)
	{
		final I_C_InvoiceLine invoiceLine = newInstance(I_C_InvoiceLine.class);
		POJOWrapper.setInstanceName(invoiceLine, name);
		invoiceLine.setIsFreightCostLine(freightCost);
		invoiceLine.setLine(lineNo);

		if (inoutId > 0)
		{
			final I_M_InOutLine inoutLine = newInstance(I_M_InOutLine.class);
			inoutLine.setM_InOut_ID(inoutId);
			saveRecord(inoutLine);
			invoiceLine.setM_InOutLine_ID(inoutLine.getM_InOutLine_ID());
		}

		saveRecord(invoiceLine);

		return invoiceLine;
	}

	private void assertInOrder(final List<I_C_InvoiceLine> lines, String... expectedInstanceNamesInOrder)
	{
		final ImmutableList<String> actualInstanceNames = lines.stream()
				.map(POJOWrapper::getInstanceName)
				.collect(ImmutableList.toImmutableList());

		assertThat(actualInstanceNames).containsExactly(expectedInstanceNamesInOrder);
	}

	/**
	 * Test Case 1: no changes, when already sorted.
	 */
	@Test
	public void sortDontChange()
	{
		final I_C_InvoiceLine il1 = invoiceLine().name("IL1").lineNo(10).inoutId(-1).build();
		final I_C_InvoiceLine il2 = invoiceLine().name("IL2").lineNo(20).inoutId(11).build();
		final I_C_InvoiceLine il3 = invoiceLine().name("IL3").lineNo(30).inoutId(11).freightCost(true).build();
		final I_C_InvoiceLine il4 = invoiceLine().name("IL4").lineNo(40).inoutId(12).build();

		final List<I_C_InvoiceLine> sorted = Arrays.asList(il1, il2, il3, il4);
		new InvoiceBL().sortLines(sorted);

		assertInOrder(sorted, "IL1", "IL2", "IL3", "IL4");
	}

	/**
	 * Test Case 2: Sort InOut_IDs
	 */
	@Test
	public void sortInOuts()
	{
		final I_C_InvoiceLine il1 = invoiceLine().name("IL1").lineNo(10).inoutId(12).build();
		final I_C_InvoiceLine il2 = invoiceLine().name("IL2").lineNo(10).inoutId(11).build();
		final I_C_InvoiceLine il3 = invoiceLine().name("IL3").lineNo(10).inoutId(12).build();
		final I_C_InvoiceLine il4 = invoiceLine().name("IL4").lineNo(10).inoutId(10).build();

		final List<I_C_InvoiceLine> sorted = Arrays.asList(il1, il2, il3, il4);
		new InvoiceBL().sortLines(sorted);

		assertInOrder(sorted, "IL4", "IL2", "IL1", "IL3");
		// the Last two lines are in the same order they were before
	}

	/**
	 * Test Case 3: Seek InOut_ID for Lines Without InOut_ID
	 */
	@Test
	public void sortSeekInOut()
	{
		final I_C_InvoiceLine il1 = invoiceLine().name("IL1").lineNo(8).inoutId(0).build();
		final I_C_InvoiceLine il2 = invoiceLine().name("IL2").lineNo(9).inoutId(0).build();
		final I_C_InvoiceLine il3 = invoiceLine().name("IL3").lineNo(10).inoutId(12).build();
		final I_C_InvoiceLine il4 = invoiceLine().name("IL4").lineNo(10).inoutId(10).build();

		final List<I_C_InvoiceLine> sorted = Arrays.asList(il1, il2, il3, il4);
		new InvoiceBL().sortLines(sorted);

		assertInOrder(sorted, "IL4", "IL1", "IL2", "IL3");
		// the Last three lines are also ordered by lineNo
	}

	/**
	 * Test Case 4: Sort Freight Cost Lines
	 */
	@Test
	public void sortFreightCost()
	{
		final I_C_InvoiceLine il1 = invoiceLine().name("IL1").lineNo(10).inoutId(11).freightCost(true).build();
		final I_C_InvoiceLine il2 = invoiceLine().name("IL2").lineNo(10).inoutId(11).freightCost(false).build();
		final I_C_InvoiceLine il3 = invoiceLine().name("IL3").lineNo(10).inoutId(11).freightCost(true).build();
		final I_C_InvoiceLine il4 = invoiceLine().name("IL4").lineNo(10).inoutId(11).freightCost(false).build();

		final List<I_C_InvoiceLine> sorted = Arrays.asList(il1, il2, il3, il4);
		new InvoiceBL().sortLines(sorted);

		assertInOrder(sorted, "IL2", "IL4", "IL1", "IL3");
	}

	/**
	 * Test Case 5: Sort Lines after Line number
	 */
	@Test
	public void sortLineNo()
	{
		final I_C_InvoiceLine il1 = invoiceLine().name("IL1").lineNo(20).inoutId(11).build();
		final I_C_InvoiceLine il2 = invoiceLine().name("IL2").lineNo(10).inoutId(11).build();
		final I_C_InvoiceLine il3 = invoiceLine().name("IL3").lineNo(20).inoutId(11).build();
		final I_C_InvoiceLine il4 = invoiceLine().name("IL4").lineNo(10).inoutId(11).build();

		final List<I_C_InvoiceLine> sorted = Arrays.asList(il1, il2, il3, il4);
		new InvoiceBL().sortLines(sorted);

		assertInOrder(sorted, "IL2", "IL4", "IL1", "IL3");
	}

	/**
	 * Test Case 6: Sort by all criteria
	 */
	@Test
	public void sortComplete()
	{
		final I_C_InvoiceLine il1 = invoiceLine().name("IL1").lineNo(10).inoutId(12).build();
		final I_C_InvoiceLine il2 = invoiceLine().name("IL2").lineNo(9).inoutId(0).build();
		final I_C_InvoiceLine il3 = invoiceLine().name("IL3").lineNo(8).inoutId(0).build();
		final I_C_InvoiceLine il4 = invoiceLine().name("IL4").lineNo(10).inoutId(11).freightCost(true).build();
		final I_C_InvoiceLine il5 = invoiceLine().name("IL5").lineNo(10).inoutId(11).build();

		final List<I_C_InvoiceLine> sorted = Arrays.asList(il1, il2, il3, il4, il5);
		new InvoiceBL().sortLines(sorted);

		assertInOrder(sorted, "IL3", "IL2", "IL5", "IL4", "IL1");
	}

	/**
	 * Test Case 7 (08295): Sort with override for order/line order first
	 */
	@Test
	public void sortWith_SYSCONFIG_SortILsByShipmentLineOrders()
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		sysConfigBL.setValue(AbstractInvoiceBL.SYSCONFIG_SortILsByShipmentLineOrders, true, ClientId.METASFRESH, OrgId.ANY); // configure override

		final I_C_InvoiceLine il1 = invoiceLine().name("IL1").lineNo(10).inoutId(12).build();
		final I_C_InvoiceLine il2 = invoiceLine().name("IL2").lineNo(9).inoutId(0).build();
		final I_C_InvoiceLine il3 = invoiceLine().name("IL3").lineNo(8).inoutId(0).build();
		final I_C_InvoiceLine il4 = invoiceLine().name("IL4").lineNo(10).inoutId(11).freightCost(true).build();
		final I_C_InvoiceLine il5 = invoiceLine().name("IL5").lineNo(10).inoutId(11).build();

		final List<I_C_InvoiceLine> sorted = Arrays.asList(il1, il2, il3, il4, il5);
		new InvoiceBL().sortLines(sorted);

		assertInOrder(sorted, "IL5", "IL4", "IL1", "IL3", "IL2");
	}
}
