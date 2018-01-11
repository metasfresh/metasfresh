package de.metas.payment.api;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.model.X_C_DocType;

import de.metas.builder.IBuilder;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;

public class DefaultPaymentBuilder implements IBuilder
{
	private final Object contextProvider;
	private final I_C_Payment payment;
	private String _docbaseType;
	private boolean _built = false;

	public enum TenderType
	{
		ACH("A"),
		CREDIT_CARD("C"),
		DIRECT_DEBIT("D"),
		CASH("X");

		private final String name;

		private TenderType(String name)
		{
			this.name = name;
		}

		@Override
		public String toString()
		{
			return name;
		}
	}

	public DefaultPaymentBuilder(Object contextProvider)
	{
		this.contextProvider = contextProvider;
		payment = InterfaceWrapperHelper.newInstance(I_C_Payment.class, contextProvider);
		payment.setProcessed(false);
		payment.setDocStatus(IDocument.STATUS_Drafted);
		payment.setDocAction(IDocument.ACTION_Complete);
	}
	
	private final void assertNotBuilt()
	{
		Check.assume(!_built, "payment already built");
	}
	
	private final void markAsBuilt()
	{
		assertNotBuilt();
		_built = true;
	}

	public final DefaultPaymentBuilder setAD_Org_ID(int AD_Org_ID)
	{
		assertNotBuilt();
		payment.setAD_Org_ID(AD_Org_ID);
		return this;
	}

	public final DefaultPaymentBuilder setIsReceipt(boolean isReceipt)
	{
		assertNotBuilt();
		payment.setIsReceipt(isReceipt);
		return this;
	}

	public final DefaultPaymentBuilder setDocbaseType(final String docBaseType)
	{
		assertNotBuilt();
		this._docbaseType = docBaseType;
		return this;
	}
	
	private final String getDocBaseType()
	{
		if (_docbaseType == null)
		{
			final boolean isReceipt = payment.isReceipt();
			return isReceipt ? X_C_DocType.DOCBASETYPE_ARReceipt : X_C_DocType.DOCBASETYPE_APPayment;
		}
		return _docbaseType;
	}
	
	private DefaultPaymentBuilder setDocAction(final String docAction)
	{
		assertNotBuilt();
		payment.setDocAction(docAction);
		return this;
	}
	
	/**
	 * Creates the payment but it does not save it.
	 * @return payment 
	 */
	public I_C_Payment createNoSave()
	{
		markAsBuilt();

		// note: the only reason why we are calling the "...OrNull" method is because some unit tests are failing.
		final int docTypeId = Services.get(IDocTypeDAO.class).getDocTypeIdOrNull(DocTypeQuery.builder()
				.docBaseType(getDocBaseType())
				.docSubType(DocTypeQuery.DOCSUBTYPE_Any)
				.adClientId(payment.getAD_Client_ID())
				.adOrgId(payment.getAD_Org_ID())
				.build());
		payment.setC_DocType_ID(docTypeId);
		
		return payment;
	}
	
	/**
	 * Creates the draft payment. 
	 * @return payment
	 */
	public I_C_Payment createDraft()
	{
		final I_C_Payment payment = createNoSave();
		InterfaceWrapperHelper.save(payment);
		return payment;
	}

	/**
	 * Creates and processes the payment.
	 * @param docAction
	 * @param expectedDocStatus
	 * @return payment
	 */
	public I_C_Payment createAndProcess(final String docAction, final String expectedDocStatus)
	{
		setDocAction(docAction);

		final I_C_Payment payment = createDraft();
		Services.get(IDocumentBL.class).processEx(payment, docAction, expectedDocStatus);

		return payment;
	}

	/**
	 * Creates and completes the payment
	 * @return payment
	 */
	public I_C_Payment createAndProcess()
	{
		return createAndProcess(IDocument.ACTION_Complete, IDocument.STATUS_Completed);
	}

	public final DefaultPaymentBuilder setC_BP_BankAccount_ID(int C_BP_BankAccount_ID)
	{
		assertNotBuilt();
		payment.setC_BP_BankAccount_ID(C_BP_BankAccount_ID);
		return this;
	}

	public final DefaultPaymentBuilder setAccountNo(String AccountNo)
	{
		assertNotBuilt();
		payment.setAccountNo(AccountNo);
		return this;
	}

	public final DefaultPaymentBuilder setDateAcct(Timestamp DateAcct)
	{
		assertNotBuilt();
		payment.setDateAcct(DateAcct);
		return this;
	}

	public final DefaultPaymentBuilder setDateTrx(Timestamp DateTrx)
	{
		assertNotBuilt();
		payment.setDateTrx(DateTrx);
		return this;
	}

	public final DefaultPaymentBuilder setC_BPartner_ID(int C_BPartner_ID)
	{
		assertNotBuilt();
		payment.setC_BPartner_ID(C_BPartner_ID);
		return this;
	}

	public final DefaultPaymentBuilder setPayAmt(BigDecimal payAmt)
	{
		assertNotBuilt();
		payment.setPayAmt(payAmt);
		return this;
	}

	public final DefaultPaymentBuilder setDiscountAmt(BigDecimal DiscountAmt)
	{
		assertNotBuilt();
		payment.setDiscountAmt(DiscountAmt);
		return this;
	}

	public final DefaultPaymentBuilder setWriteoffAmt(BigDecimal WriteoffAmt)
	{
		assertNotBuilt();
		payment.setWriteOffAmt(WriteoffAmt);
		return this;
	}

	public final DefaultPaymentBuilder setOverUnderAmt(BigDecimal OverUnderAmt)
	{
		assertNotBuilt();
		payment.setOverUnderAmt(OverUnderAmt);
		return this;
	}

	public final DefaultPaymentBuilder setC_Currency_ID(int c_Currency_ID)
	{
		assertNotBuilt();
		payment.setC_Currency_ID(c_Currency_ID);
		return this;
	}

	public final DefaultPaymentBuilder setTenderType(TenderType tenderType)
	{
		assertNotBuilt();
		payment.setTenderType(tenderType.toString());
		return this;
	}

	/**
	 * Sets the following fields using the given <code>invoice</code>:
	 * <ul>
	 * <li>C_Invoice_ID
	 * <li>C_BPartner_ID
	 * <li>C_Currency_ID
	 * <li>IsReceipt: set from the invoice's <code>SOTrx</code> (negated if the invoice is a credit memo)
	 * </ul>
	 * 
	 * @param invoice
	 * @return
	 */
	public final DefaultPaymentBuilder setC_Invoice(I_C_Invoice invoice)
	{
		assertNotBuilt();
		payment.setC_Invoice_ID(invoice.getC_Invoice_ID());
		payment.setC_BPartner_ID(invoice.getC_BPartner_ID());
		payment.setC_Currency_ID(invoice.getC_Currency_ID());

		final boolean isReceipt;
		if (Services.get(IInvoiceBL.class).isCreditMemo(invoice))
		{
			// SOTrx=Y, but credit memo => receipt=N
			isReceipt = !invoice.isSOTrx();
		}
		else
		{
			// SOTrx=Y => receipt=Y
			isReceipt = invoice.isSOTrx();
		}
		payment.setIsReceipt(isReceipt);

		return this;
	}
}
