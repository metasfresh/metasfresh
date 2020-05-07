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


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import de.metas.adempiere.form.IClientUI;

public abstract class ReadPaymentDialogWindowAdapter extends WindowAdapter
{
	private final ReadPaymentDocumentPanel readPaymentPanel;

	public ReadPaymentDialogWindowAdapter(final ReadPaymentDocumentPanel readPaymentPanel)
	{
		super();
		Check.assumeNotNull(readPaymentPanel, "readPaymentPanel not null");
		this.readPaymentPanel = readPaymentPanel;
	}

	@Override
	public final void windowClosed(final WindowEvent e)
	{
		// NOTE: we need to make sure we are not throwing events from here because
		// it might prevent other listeners to be executed.
		// Most dangerous is org.compiere.apps.AEnv.showCenterWindowModal(JFrame, JFrame, Runnable)
		try
		{
			windowClosed0();
		}
		catch (Exception ex)
		{
			final int windowNo = Env.getWindowNo(e.getWindow());
			Services.get(IClientUI.class).error(windowNo, ex);
		}
	}

	private final void windowClosed0() throws Exception
	{
		if (isParentWindowDisposed())
		{
			return; // don't update if the window was somehow closed
		}

		//
		// Update information
		final ReadPaymentPanelResult result = readPaymentPanel.getResultIfValid().orElse(null);
		if (result == null)
		{
			return;
		}
		
		onResult(result);
	}

	protected boolean isParentWindowDisposed()
	{
		return false;
	}

	/**
	 * Called after {@link ReadPaymentDocumentDialog} was closed and all data were valid.
	 * 
	 * @param result
	 */
	protected abstract void onResult(final ReadPaymentPanelResult result);
}
