package org.compiere.grid;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComponent;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.plaf.AdempierePLAF;
import org.compiere.grid.VPayment.ProcessingCtx;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MPayment;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTextField;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

import de.metas.i18n.Msg;

public class VPaymentDirectDebit extends AbstractPaymentDirectDebit
{
	private final CPanel tPanel = new CPanel();
	private final GridBagLayout tPanelLayout = new GridBagLayout();
	private final CLabel tBPAccountLabel = new CLabel();
	private final CComboBox<KeyNamePair> tBPAccountCombo = new CComboBox<>();
	private final CLabel tAccountLabel = new CLabel();
	private final CComboBox<KeyNamePair> tAccountCombo = new CComboBox<>();
	private final CTextField tRoutingField = new CTextField();
	private final CTextField tNumberField = new CTextField();
	private final CLabel tStatus = new CLabel();
	private final CLabel tRoutingText = new CLabel();
	private final CLabel tNumberText = new CLabel();
	// private final CButton tOnline = new CButton();

	private final JComponent[] fields = new JComponent[] {
			tBPAccountCombo,
			tAccountCombo,
			tRoutingField,
			tNumberField,
	};
	private final VPaymentPanelUIHelper uiHelper = new VPaymentPanelUIHelper(this, fields);

	private int m_C_BPartner_ID = -1;

	public VPaymentDirectDebit()
	{
		initUI();
	}

	@Override
	protected void initUI()
	{
		tPanel.setLayout(tPanelLayout);
		tAccountLabel.setText(Msg.translate(Env.getCtx(), "C_BankAccount_ID"));
		tBPAccountLabel.setText(Msg.translate(Env.getCtx(), "C_BP_BankAccount_ID"));
		tRoutingField.setColumns(8);
		tNumberField.setColumns(10);
		tRoutingText.setText(Msg.translate(Env.getCtx(), "RoutingNo"));
		tNumberText.setText(Msg.translate(Env.getCtx(), "AccountNo"));
		// tOnline.setText(Msg.getMsg(Env.getCtx(), "Online"));
		tStatus.setText(" ");
		tPanel.add(tAccountLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
		tPanel.add(tAccountCombo, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

		tPanel.add(tBPAccountLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
		tPanel.add(tBPAccountCombo, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

		tPanel.add(tRoutingText, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
		tPanel.add(tRoutingField, new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));

		tPanel.add(tNumberText, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
		tPanel.add(tNumberField, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));

		tPanel.add(tStatus, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0
				, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

		// tPanel.add(tOnline, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
		// , GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
	}

	@Override
	public java.awt.Component getComponent()
	{
		return tPanel;
	}

	public void setRountingNo(String routingNo)
	{
		tRoutingField.setText(routingNo);
	}

	public String getRountingNo()
	{
		return tRoutingField.getText();
	}

	public void setAccountNo(String accountNo)
	{
		tNumberField.setText(accountNo);
	}

	public String getAccountNo()
	{
		return tNumberField.getText();
	}

	public KeyNamePair getBPAccountKNP()
	{
		return tBPAccountCombo.getSelectedItem();
	}

	public void setBPAccountError(boolean error)
	{
		if (error)
			tBPAccountCombo.setBackground(AdempierePLAF.getFieldBackground_Error());
		else
			tBPAccountCombo.setBackground(AdempierePLAF.getFieldBackground_Normal());
	}

	public KeyNamePair getAccountKNP()
	{
		return tAccountCombo.getSelectedItem();
	}

	public void setAccountError(boolean error)
	{
		if (error)
			tAccountCombo.setBackground(AdempierePLAF.getFieldBackground_Error());
		else
			tAccountCombo.setBackground(AdempierePLAF.getFieldBackground_Normal());
	}

	public void setStatus(String rPnRef)
	{
		tStatus.setText(rPnRef);
	}

	public void setC_BPartner_ID(int C_BPartner_ID)
	{
		int oldBPartnerId = m_C_BPartner_ID;
		m_C_BPartner_ID = C_BPartner_ID;

		if (oldBPartnerId != m_C_BPartner_ID || tBPAccountCombo.getItemCount() == 0)
		{
			loadBPBankAccounts(tBPAccountCombo, m_C_BPartner_ID);
		}
	}

	private static void loadBPBankAccounts(CComboBox<KeyNamePair> tBPAccountCombo, int m_C_BPartner_ID)
	{
		VPayment.loadComboValues(tBPAccountCombo, retrieveBPBankAccounts(m_C_BPartner_ID), m_C_BPartner_ID);
	}

	private static void loadBankAccounts(CComboBox<KeyNamePair> tAccountCombo)
	{
		VPayment.loadComboValues(tAccountCombo, retrieveBankAccounts(), 0);
	}

	public int getC_BP_BankAccount_ID()
	{
		KeyNamePair kp = tBPAccountCombo.getSelectedItem();
		if (kp != null)
			return kp.getKey();
		else
			return -1;

	}

	public int getC_BankAccount_ID()
	{
		KeyNamePair kp = tAccountCombo.getSelectedItem();
		if (kp != null)
			return kp.getKey();
		else
			return -1;

	}

	@Override
	public void setFrom(I_C_Payment payment)
	{
		if (payment == null)
			return;

		setRountingNo(payment.getRoutingNo());
		setAccountNo(payment.getAccountNo());
		setStatus(payment.getR_PnRef());
	}

	@Override
	public boolean validate(boolean isInTrx)
	{
		if (getC_BP_BankAccount_ID() <= 0)
		{
			setBPAccountError(true);
			throw new AdempiereException("@PaymentBPBankNotFound@");
		}
		if (getC_BankAccount_ID() <= 0)
		{
			setAccountError(true);
			throw new AdempiereException("@PaymentBankNotFound@");
		}
		return true;
	}

	@Override
	public void setEnabled(boolean enabled)
	{
		super.setEnabled(enabled);
		uiHelper.updateFieldRO();
	}

	@Override
	public void setReadOnly(boolean readOnly)
	{
		super.setReadOnly(readOnly);
		uiHelper.updateFieldRO();
	}

	private IPayableDocument doc;

	@Override
	public void setFrom(IPayableDocument doc)
	{
		this.doc = doc;
		setC_BPartner_ID(doc.getC_BPartner_ID());
		loadBankAccounts(tAccountCombo);
	}

	@Override
	public void updatePayment(ProcessingCtx pctx, MPayment payment)
	{
		int C_BP_BankAccount_ID = this.getC_BP_BankAccount_ID();
		payment.setBankACH(C_BP_BankAccount_ID, doc.isSOTrx(), pctx.newPaymentRule,
				this.getRountingNo(), this.getAccountNo());
	}

}
