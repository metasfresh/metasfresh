package de.metas.banking.payment.paymentallocation.contextMenuAction;

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


import org.adempiere.ui.OpenFormContextMenuAction;
import org.compiere.apps.form.FormFrame;

import de.metas.banking.payment.paymentallocation.form.PaymentAllocationForm;

/**
 * Opens {@link PaymentAllocationForm} from {@link de.metas.adempiere.model.I_C_PaySelectionLine#COLUMNNAME_C_BPartner_ID} context menu.
 *
 * @author tsa
 * @task 09393
 */
public class OpenPaymentAllocationFormForBPartnerContextMenuAction extends OpenFormContextMenuAction
{
	public OpenPaymentAllocationFormForBPartnerContextMenuAction()
	{
		super(PaymentAllocationForm.AD_FORM_ID);
	}

	/**
	 * @return true if C_BPartner_ID is set
	 */
	@Override
	protected boolean isFormRunnable()
	{
		final int bpartnerId = getC_BPartner_ID();
		if (bpartnerId <= 0)
		{
			return false;
		}

		return true;
	}

	@Override
	protected void customizeBeforeOpen(final FormFrame formFrame)
	{
		final PaymentAllocationForm formPanel = formFrame.getFormPanel(PaymentAllocationForm.class);

		// Set the current BPartner and refresh
		formPanel.setC_BPartner_ID(getC_BPartner_ID());
		formPanel.refresh();
	}

	/** @return C_BPartner_ID of the field where user right clicked */
	private final int getC_BPartner_ID()
	{
		final Object bpartnerIdObj = getContext().getEditor().getValue();
		if (bpartnerIdObj == null)
		{
			return -1;
		}
		else if (bpartnerIdObj instanceof Number)
		{
			final int bpartnerId = ((Number)bpartnerIdObj).intValue();
			return bpartnerId <= 0 ? -1 : bpartnerId;
		}
		else
		{
			return -1;
		}
	}
}
