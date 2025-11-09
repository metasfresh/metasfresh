package de.metas.allocation.api.impl;

import de.metas.allocation.api.IAllocationDAO;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.DocTypeId;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

class AllocationDAOTest
{
	private PlainAllocationDAO allocationDAO;
	private CurrencyId EUR;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		allocationDAO = (PlainAllocationDAO)Services.get(IAllocationDAO.class);

		EUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);
	}

	@NonNull
	private Money euro(String amount) {return Money.of(amount, EUR);}

	@Nested
	class retrieveOpenAmtInInvoiceCurrency
	{
		@SuppressWarnings("SameParameterValue")
		I_C_Invoice createInvoice(InvoiceDocBaseType docBaseType, final String grandTotal)
		{
			final DocTypeId docTypeId = createDocType(docBaseType);
			final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
			invoice.setC_DocType_ID(docTypeId.getRepoId());
			invoice.setIsSOTrx(docBaseType.isSales());
			invoice.setGrandTotal(new BigDecimal(grandTotal));
			invoice.setC_Currency_ID(EUR.getRepoId());
			InterfaceWrapperHelper.saveRecord(invoice);

			return invoice;
		}

		DocTypeId createDocType(final InvoiceDocBaseType docBaseType)
		{
			final I_C_DocType docType = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
			docType.setDocBaseType(docBaseType.getCode());
			docType.setIsSOTrx(docBaseType.isSales());
			docType.setName(docBaseType.name());
			InterfaceWrapperHelper.saveRecord(docType);

			return DocTypeId.ofRepoId(docType.getC_DocType_ID());
		}

		void createAllocation(I_C_Invoice invoice, String amount)
		{
			final I_C_AllocationHdr allocHdr = newInstance(I_C_AllocationHdr.class);
			allocHdr.setC_Currency_ID(EUR.getRepoId());
			save(allocHdr);

			final I_C_AllocationLine allocAmt2 = newInstance(I_C_AllocationLine.class);
			allocAmt2.setC_AllocationHdr_ID(allocHdr.getC_AllocationHdr_ID());
			allocAmt2.setAmount(new BigDecimal(amount));
			allocAmt2.setC_Invoice_ID(invoice.getC_Invoice_ID());
			save(allocAmt2);
		}

		@Test
		void IsPaid_is_true()
		{
			final I_C_Invoice invoice = createInvoice(InvoiceDocBaseType.VendorInvoice, "100");
			invoice.setIsPaid(true);
			save(invoice);

			Assertions.assertThat(allocationDAO.retrieveOpenAmtInInvoiceCurrency(invoice, false))
					.isEqualByComparingTo(euro("0"));
			Assertions.assertThat(allocationDAO.retrieveOpenAmtInInvoiceCurrency(invoice, true))
					.isEqualByComparingTo(euro("0"));
		}

		@Test
		void VendorInvoice()
		{
			final I_C_Invoice invoice = createInvoice(InvoiceDocBaseType.VendorInvoice, "100");
			createAllocation(invoice, "-10"); // we pay 10 EUR

			Assertions.assertThat(allocationDAO.retrieveOpenAmtInInvoiceCurrency(invoice, false))
					.isEqualByComparingTo(euro("90"));
			Assertions.assertThat(allocationDAO.retrieveOpenAmtInInvoiceCurrency(invoice, true))
					.isEqualByComparingTo(euro("90"));
		}

		@Test
		void VendorCreditMemo()
		{
			final I_C_Invoice invoice = createInvoice(InvoiceDocBaseType.VendorCreditMemo, "100");
			createAllocation(invoice, "10"); // we get back 10 EUR

			Assertions.assertThat(allocationDAO.retrieveOpenAmtInInvoiceCurrency(invoice, false))
					.isEqualByComparingTo(euro("90"));
			Assertions.assertThat(allocationDAO.retrieveOpenAmtInInvoiceCurrency(invoice, true))
					.isEqualByComparingTo(euro("-90"));
		}

		@Test
		void CustomerInvoice()
		{
			final I_C_Invoice invoice = createInvoice(InvoiceDocBaseType.CustomerInvoice, "100");
			createAllocation(invoice, "10"); // we receive 10 EUR

			Assertions.assertThat(allocationDAO.retrieveOpenAmtInInvoiceCurrency(invoice, false))
					.isEqualByComparingTo(euro("90"));
			Assertions.assertThat(allocationDAO.retrieveOpenAmtInInvoiceCurrency(invoice, true))
					.isEqualByComparingTo(euro("90"));
		}

		@Test
		void CustomerCreditMemo()
		{
			final I_C_Invoice invoice = createInvoice(InvoiceDocBaseType.CustomerCreditMemo, "100");
			createAllocation(invoice, "-10"); // we pay 10 EUR

			Assertions.assertThat(allocationDAO.retrieveOpenAmtInInvoiceCurrency(invoice, false))
					.isEqualByComparingTo(euro("90"));
			Assertions.assertThat(allocationDAO.retrieveOpenAmtInInvoiceCurrency(invoice, true))
					.isEqualByComparingTo(euro("-90"));
		}
	}
}
