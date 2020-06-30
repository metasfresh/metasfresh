package de.metas.adempiere.modelvalidator;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Invoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.util.Services;

public class InvoiceLineTest
{
	private IInvoiceDAO invoiceDAO;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		invoiceDAO = Services.get(IInvoiceDAO.class);
	}

	@Test
	public void test_beforeDelete()
	{

		final I_C_InvoiceLine invoiceLine;
		{
			final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
			invoiceDAO.save(invoice);

			invoiceLine = newInstance(I_C_InvoiceLine.class);
			invoiceLine.setC_Invoice_ID(invoice.getC_Invoice_ID());
			invoiceDAO.save(invoiceLine);
		}

		final I_C_InvoiceLine refInvoiceLine = newInstance(I_C_InvoiceLine.class);
		refInvoiceLine.setRef_InvoiceLine_ID(invoiceLine.getC_InvoiceLine_ID());
		invoiceDAO.save(refInvoiceLine);

		final InvoiceLine interceptor = new InvoiceLine();
		interceptor.beforeDelete(invoiceLine);

		InterfaceWrapperHelper.refresh(refInvoiceLine);
		assertThat(refInvoiceLine.getRef_InvoiceLine_ID()).isLessThanOrEqualTo(0);
	}
}
