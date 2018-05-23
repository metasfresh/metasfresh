package de.metas.allocation.api.impl;

/*
 * #%L
 * de.metas.swat.base
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


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_Payment;
import org.compiere.util.TimeUtil;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.allocation.api.DefaultAllocationBuilder;
import de.metas.allocation.api.IAllocationBL;
import de.metas.allocation.api.IAllocationBuilder;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.allocation.api.IAllocationLineBuilder;
import de.metas.document.engine.IDocument;
import de.metas.payment.api.IPaymentDAO;

public class AllocationBL implements IAllocationBL
{
	@Override
	public DefaultAllocationBuilder newBuilder(final IContextAware ctxProvider)
	{
		return newBuilder(ctxProvider, DefaultAllocationBuilder.class);
	}

	@Override
	public <T extends DefaultAllocationBuilder> T newBuilder(final IContextAware ctxProvider, Class<T> implClazz)
	{
		try
		{
			final Constructor<T> c = implClazz.getConstructor(IContextAware.class);
			final T builder = c.newInstance(ctxProvider);
			return builder;
		}
		catch (InvocationTargetException e)
		{
			throw new AdempiereException("Unable to create new IAllocationBuilder with class " + implClazz + ": " + ExceptionUtils.getRootCauseMessage(e), e.getTargetException());
		}
		catch (Exception e)
		{
			throw new AdempiereException("Unable to create new IAllocationBuilder with class " + implClazz, e);
		}
	}

	@Override
	public I_C_AllocationHdr autoAllocateAvailablePayments(final I_C_Invoice invoice)
	{
		if (invoice.isPaid())
		{
			return null;
		}
		if (!invoice.isSOTrx())
		{
			return null;
		}
		if (Services.get(IInvoiceBL.class).isCreditMemo(invoice))
		{
			return null;
		}

		final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);
		final IAllocationBL allocationBL = Services.get(IAllocationBL.class);
		final IAllocationDAO allocationDAO = Services.get(IAllocationDAO.class);
		final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

		final List<I_C_Payment> availablePayments = allocationDAO.retrieveAvailablePayments(invoice);

		if (availablePayments.isEmpty())
		{
			return null; // nothing to do
		}

		final BigDecimal invoiceOpenAmt = invoiceDAO.retrieveOpenAmt(invoice);

		Timestamp dateAcct = invoice.getDateAcct();
		Timestamp dateTrx = invoice.getDateInvoiced();
		IAllocationBuilder allocBuilder = allocationBL
				.newBuilder(InterfaceWrapperHelper.getContextAware(invoice))
				.setC_Currency_ID(invoice.getC_Currency_ID());

		BigDecimal sumAmt = BigDecimal.ZERO;

		for (final I_C_Payment payment : availablePayments)
		{
			final BigDecimal currentAmt = paymentDAO.getAvailableAmount(payment);

			sumAmt = sumAmt.add(currentAmt);
			
			dateAcct = TimeUtil.max(dateAcct, payment.getDateAcct());
			dateTrx = TimeUtil.max(dateTrx, payment.getDateTrx());

			Check.assume(invoice.getC_BPartner_ID() == payment.getC_BPartner_ID(), "{} and {} have the same C_BPartner_ID", invoice, payment);
			final IAllocationLineBuilder lineBuilder = allocBuilder.addLine()
					.setAD_Org_ID(invoice.getAD_Org_ID())
					.setC_BPartner_ID(invoice.getC_BPartner_ID())
					.setC_Invoice_ID(invoice.getC_Invoice_ID())
					.setC_Payment_ID(payment.getC_Payment_ID());

			if (sumAmt.compareTo(invoiceOpenAmt) < 0)
			{
				allocBuilder = lineBuilder
						.setAmount(currentAmt)
						.lineDone();
			}
			else
			{
				// make sure the allocated amt is not bigger than the open amt of the invoice
				allocBuilder = lineBuilder
						.setAmount(invoiceOpenAmt.subtract(sumAmt.subtract(currentAmt)))
						.lineDone();
				break;
			}
		}

		// Set allocation dates and create it
		return allocBuilder
				.setDateAcct(dateAcct)
				.setDateTrx(dateTrx)
				.createAndComplete();
	}

	@Override
	public I_C_AllocationHdr autoAllocateSpecificPayment(org.compiere.model.I_C_Invoice invoice, org.compiere.model.I_C_Payment payment, boolean ignoreIsAutoAllocateAvailableAmt)
	{
		if (invoice.isPaid())
		{
			return null;
		}
		if (!invoice.isSOTrx())
		{
			return null;
		}
		if (Services.get(IInvoiceBL.class).isCreditMemo(invoice))
		{
			return null;
		}

		// payment and invoice must have same partner
		if (payment.getC_BPartner_ID() != invoice.getC_BPartner_ID())
		{
			return null;
		}

		// payment must be completed
		if (!IDocument.STATUS_Completed.equals(payment.getDocStatus()))
		{
			return null;
		}

		// payment must be processed
		if (!payment.isProcessed())
		{
			return null;
		}

		// // Matching DocType
		if (payment.isReceipt() != invoice.isSOTrx())
		{
			return null;
		}

		if (!ignoreIsAutoAllocateAvailableAmt)
		{
			// payment must be autoallocatedAavilableAmt
			if (!payment.isAutoAllocateAvailableAmt())
			{
				return null;
			}
		}

		// payment must not be oallocated
		if (payment.isAllocated())
		{
			return null;
		}

		final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);
		final IAllocationBL allocationBL = Services.get(IAllocationBL.class);
		final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

		final BigDecimal invoiceOpenAmt = invoiceDAO.retrieveOpenAmt(invoice);
		final Timestamp dateAcct = TimeUtil.max(invoice.getDateAcct(), payment.getDateAcct());
		final Timestamp dateTrx = TimeUtil.max(invoice.getDateInvoiced(), payment.getDateTrx());

		IAllocationBuilder allocBuilder = allocationBL.newBuilder(InterfaceWrapperHelper.getContextAware(invoice))
				.setC_Currency_ID(invoice.getC_Currency_ID())
				.setDateAcct(dateAcct)
				.setDateTrx(dateTrx);

		BigDecimal sumAmt = BigDecimal.ZERO;

		{
			final BigDecimal currentAmt = paymentDAO.getAvailableAmount(payment);

			sumAmt = sumAmt.add(currentAmt);

			Check.assume(invoice.getC_BPartner_ID() == payment.getC_BPartner_ID(), "{} and {} have the same C_BPartner_ID", invoice, payment);

			final IAllocationLineBuilder lineBuilder = allocBuilder.addLine()
					.setAD_Org_ID(invoice.getAD_Org_ID())
					.setC_BPartner_ID(invoice.getC_BPartner_ID())
					.setC_Invoice_ID(invoice.getC_Invoice_ID())
					.setC_Payment_ID(payment.getC_Payment_ID());

			if (sumAmt.compareTo(invoiceOpenAmt) < 0)
			{
				allocBuilder = lineBuilder
						.setAmount(currentAmt)
						.lineDone();
			}
			else
			{
				// make sure the allocated amt is not bigger than the open amt of the invoice
				allocBuilder = lineBuilder
						.setAmount(invoiceOpenAmt.subtract(sumAmt.subtract(currentAmt)))
						.lineDone();
			}
		}
		return allocBuilder.create(true);
	}

	@Override
	public boolean isReversal(final I_C_AllocationHdr allocationHdr)
	{
		if (allocationHdr == null)
		{
			return false;
		}
		if (allocationHdr.getReversal_ID() <= 0)
		{
			return false;
		}
		// the reversal is always younger than the original document
		return allocationHdr.getC_AllocationHdr_ID() > allocationHdr.getReversal_ID();
	}
}
