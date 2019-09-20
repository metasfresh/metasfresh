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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.banking.model.I_C_Payment_Request;
import de.metas.banking.process.paymentdocumentform.AlmightyKeeperOfEverything;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Check;
import org.compiere.SpringContextHolder;
import org.compiere.apps.AEnv;
import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.FormPanel;

import java.awt.Dialog.ModalExclusionType;
import java.awt.*;

/**
 * Custom Form to read the payment string and create the {@link I_C_Payment_Request}.
 * <p>
 * Usually this form is called from Gear-Eingangsrechnung window.
 */
public class ReadPaymentDocumentForm implements FormPanel, IProcessPrecondition
{
	private final transient AlmightyKeeperOfEverything almightyKeeperOfEverything = SpringContextHolder.instance.getBean(AlmightyKeeperOfEverything.class);

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

		// gh #781: provide the invoice's bPartner so the panel can filter matching accounts by relevance
		readPaymentPanel.setContextBPartner_ID(invoice == null ? -1 : invoice.getC_BPartner_ID());

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
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		return almightyKeeperOfEverything.checkPreconditionsApplicable(context);
	}

	private void createPaymentRequest(final ReadPaymentPanelResult result)
	{
		almightyKeeperOfEverything.createPaymentRequestFromTemplate(invoice, result.getPaymentRequestTemplate());
	}
}
