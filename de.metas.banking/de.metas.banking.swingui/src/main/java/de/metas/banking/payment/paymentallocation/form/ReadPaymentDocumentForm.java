package de.metas.banking.payment.paymentallocation.form;

/*
 * #%L
 * de.metas.banking.swingui
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


import java.awt.Dialog.ModalExclusionType;
import java.awt.Frame;

import org.adempiere.ad.process.ISvrProcessPrecondition;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.apps.AEnv;
import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.FormPanel;
import org.compiere.process.ProcessInfo;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.banking.payment.IPaymentRequestBL;
import de.metas.banking.payment.IPaymentRequestDAO;
import de.metas.payment.model.I_C_Payment_Request;

/**
 * Custom Form to read the payment string and create the {@link I_C_Payment_Request}.
 * 
 * Usually this form is called from Gear-Eingangsrechnung window.
 * 
 * @author al
 * @task 08762
 */
public class ReadPaymentDocumentForm implements FormPanel, ISvrProcessPrecondition
{
	private static final String MSG_PREFIX = PaymentAllocationForm.MSG_PREFIX;

	//
	// Services
	private final transient IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final transient IPaymentRequestDAO paymentRequestDAO = Services.get(IPaymentRequestDAO.class);
	private final transient IPaymentRequestBL paymentRequestBL = Services.get(IPaymentRequestBL.class);

	// Parameters
	private I_C_Invoice invoice = null;

	// UI
	private ReadPaymentDocumentPanel dialogComponent = null;

	@Override
	public void init(final int WindowNo, final FormFrame frame) throws Exception
	{
		final ProcessInfo pi = frame.getProcessInfo();

		invoice = pi.getRecord(I_C_Invoice.class);
		Check.assumeNotNull(invoice, "invoice not null");

		final int AD_Org_ID = invoice.getAD_Org_ID();

		dialogComponent = createAndBindPanel(WindowNo, frame, AD_Org_ID);

		//
		// Configure frame
		frame.setMenuBar(null); // no menu
		frame.setJMenuBar(null); // no menu
		frame.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		frame.getContentPane().add(dialogComponent.getComponent());
		frame.pack();
		frame.setResizable(false);

		//
		// Add to window manager
		AEnv.addToWindowManager(frame);
	}

	@Override
	public void dispose()
	{
		if (dialogComponent != null)
		{
			dialogComponent.onContainerDispose();
		}
		dialogComponent = null;
	}

	private ReadPaymentDocumentPanel createAndBindPanel(final int windowNo, final Frame frame, final int adOrgId)
	{
		final ReadPaymentDocumentPanel readPaymentPanel = new ReadPaymentDocumentPanel(windowNo, frame, adOrgId);
		frame.addWindowListener(new ReadPaymentDialogWindowAdapter(readPaymentPanel)
		{
			@Override
			protected void onResult(final ReadPaymentPanelResult result)
			{
				createPaymentRequest(result);
			}
		});

		return readPaymentPanel;
	}

	@Override
	public boolean isPreconditionApplicable(final PreconditionsContext context)
	{
		final I_C_Invoice invoice = context.getModel(I_C_Invoice.class);
		if (invoice == null)
		{
			return false;
		}

		// only completed invoiced
		if (!invoiceBL.isComplete(invoice))
		{
			return false;
		}

		return !invoice.isSOTrx(); // only PO Invoices (Eingangsrechnung)
	}

	private void createPaymentRequest(final ReadPaymentPanelResult result)
	{
		//
		// Get the payment request template
		final I_C_Payment_Request template = result.getPaymentRequestTemplate();
		if (template == null)
		{
			throw new AdempiereException("@" + MSG_PREFIX + "SelectPaymentRequestFirstException" + "@");
		}

		//
		// Get the selected invoice
		if (paymentRequestDAO.hasPaymentRequests(invoice))
		{
			throw new AdempiereException("@" + MSG_PREFIX + "PaymentRequestForInvoiceAlreadyExistsException" + "@");
		}

		//
		// Create the payment request
		paymentRequestBL.createPaymentRequest(invoice, template);
	}
}
