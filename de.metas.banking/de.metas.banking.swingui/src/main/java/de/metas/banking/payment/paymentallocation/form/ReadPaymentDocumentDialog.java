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


import java.awt.Container;
import java.awt.Frame;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Properties;

import org.adempiere.util.Services;
import org.compiere.swing.CDialog;

import de.metas.i18n.IMsgBL;

/**
 * Payment Document Reader Dialog
 *
 * @author al
 */
final class ReadPaymentDocumentDialog
		extends CDialog
{
	/**
	 *
	 */
	private static final long serialVersionUID = 5069654601970071033L;

	public static ReadPaymentDocumentDialog create(final Properties ctx,
			final Frame parentFrame,
			final int AD_Org_ID)
	{
		//
		// Services
		final IMsgBL msgBL = Services.get(IMsgBL.class);

		final String title = msgBL.getMsg(ctx, ReadPaymentDocumentPanel.HEADER_READ_PAYMENT_STRING);
		final ReadPaymentDocumentDialog readPaymentDocumentDialog = new ReadPaymentDocumentDialog(parentFrame, title, AD_Org_ID);

		//
		// Forward dialog's focus to it's main panel
		readPaymentDocumentDialog.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusGained(final FocusEvent e)
			{
				final ReadPaymentDocumentPanel dialogComponent = readPaymentDocumentDialog.getDialogComponent();
				dialogComponent.focusGained(e);
			}
		});
		return readPaymentDocumentDialog;
	}

	private final ReadPaymentDocumentPanel _dialogComponent;

	/**
	 * @param parentFrame parent frame
	 * @param AD_Org_ID
	 * @param bPartnerId if the parent frame knows the <code>C_BPartner_ID</code>, if will be provided here, so that this form can create a C_BP_BankAccount on the fly, if required.
	 */
	private ReadPaymentDocumentDialog(final Frame parentFrame, final String title, final int AD_Org_ID)
	{
		super(parentFrame, title, true); // modal (always)

		_dialogComponent = new ReadPaymentDocumentPanel(this, AD_Org_ID);

		final Container container = getContentPane();
		container.add(_dialogComponent.getComponent());
		
		pack();
		setResizable(false);
	}

	protected ReadPaymentDocumentPanel getDialogComponent()
	{
		return _dialogComponent;
	}

	@Override
	public void dispose()
	{
		getDialogComponent().onContainerDispose();
		super.dispose();
	}
}
