package de.schaeffer.pay.misc;

/*
 * #%L
 * de.metas.banking.base
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


import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.Properties;

import org.adempiere.util.Services;
import org.compiere.apps.AEnv;
import org.compiere.apps.APanel;
import org.compiere.swing.CDialog;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;

import de.metas.adempiere.gui.CreditCardPanel;
import de.schaeffer.pay.exception.TelecashRuntimeException;
import de.schaeffer.pay.service.ITelecashBL;

public class CCValidate extends CDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4212251407751081129L;

	public CCValidate(final Properties ctx, final APanel parent) {

		super(Env.getWindow(parent.getWindowNo()), Msg.getMsg(Env.getCtx(),
				"ValidateCreditCard"), true);

		final CreditCardPanel panel = new CreditCardPanel(
				CreditCardPanel.MODE_VALIDATE);

		panel.addOnlineButtonListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				handlePanelAction(ctx, panel);
			}
		});

		getContentPane().add((Component)panel.getComponent());
		pack();

		AEnv.positionCenterWindow(Env.getWindow(parent.getWindowNo()), this);
	}

	private void handlePanelAction(final Properties ctx,
			final CreditCardPanel panel) {

		final Cursor origCursor = getCursor();
		final Cursor waitCursor = Cursor
				.getPredefinedCursor(Cursor.WAIT_CURSOR);

		try {
			setCursor(waitCursor);

			boolean error = false;

			final String expMMYY = panel.getExp();
			if (expMMYY.length() != 4) {

				final PropertyChangeEvent evt = new PropertyChangeEvent(this,
						CreditCardPanel.EXP_ERROR, null, true);
				panel.propertyChange(evt);
				error = true;
			}
			final String ccNumber = panel.getNumber();
			if (Util.isEmpty(ccNumber)) {
				final PropertyChangeEvent evt = new PropertyChangeEvent(this,
						CreditCardPanel.NUMBER_ERROR, null, true);
				panel.propertyChange(evt);
				error = true;
			}
			final String cardCode = panel.getCardCode();
			if (Util.isEmpty(cardCode)) {
				final PropertyChangeEvent evt = new PropertyChangeEvent(this,
						CreditCardPanel.APPROVAL_ERROR, null, true);
				panel.propertyChange(evt);
				error = true;
			}

			if (error) {
				return;
			}

			final int adClientId = Env.getAD_Client_ID(ctx);
			final int adOrgId = Env.getAD_Org_ID(ctx);
			
			final String expYY = expMMYY.substring(2);
			final String expMM = expMMYY.substring(0, 2);
			
			final ITelecashBL telecashBL = Services.get(ITelecashBL.class);
			String result = null;
			try {
				result = telecashBL.validateCard(adClientId, adOrgId, ccNumber,
						expMM, expYY, cardCode);

			} catch (TelecashRuntimeException ex) {
				result = ex.getMessage();
			}
			if (Util.isEmpty(result)) {
				// TODO -> AD_Message
				result = "Karte OK";
			}
			final PropertyChangeEvent evt = new PropertyChangeEvent(this,
					CreditCardPanel.STATUS, null, result);
			panel.propertyChange(evt);

		} finally {
			setCursor(origCursor);
		}
	}

}
