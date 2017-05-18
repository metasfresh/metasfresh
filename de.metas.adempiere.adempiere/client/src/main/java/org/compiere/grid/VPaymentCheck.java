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


import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.Map;

import javax.swing.JComponent;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.plaf.AdempierePLAF;
import org.compiere.grid.ed.VNumber;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MPaymentValidate;
import org.compiere.swing.CButton;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTextField;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

import de.metas.i18n.Msg;

public class VPaymentCheck extends AbstractPaymentCheck
{

	private final CPanel sPanel = new CPanel();
	private final GridBagLayout sPanelLayout = new GridBagLayout();
	private final CLabel sNumberLabel = new CLabel();
	private final CTextField sNumberField = new CTextField();
	private final CLabel sRoutingLabel = new CLabel();
	private final CTextField sRoutingField = new CTextField();
	private final CLabel sCurrencyLabel = new CLabel();
	private final CComboBox<KeyNamePair> sCurrencyCombo = new CComboBox<>();
	private final CLabel sAmountLabel = new CLabel();
	private final CTextField sCheckField = new CTextField();
	private final CLabel sCheckLabel = new CLabel();
	private final CButton sOnline = new CButton();
	private final CComboBox<KeyNamePair> sBankAccountCombo = new CComboBox<>();
	private final CLabel sBankAccountLabel = new CLabel();
	private final CLabel sStatus = new CLabel();
	private final VNumber sAmountField = new VNumber();

	private final JComponent[] fields = new JComponent[] {
			sNumberField,
			sRoutingField,
			sCurrencyCombo,
			sAmountField,
			sCheckField,
			sOnline,
			sBankAccountCombo,
	};
	private final VPaymentPanelUIHelper uiHelper = new VPaymentPanelUIHelper(this, fields);

	public VPaymentCheck()
	{
		initUI();
	}

	@Override
	protected void initUI()
	{
		sPanel.setLayout(sPanelLayout);
		sBankAccountLabel.setText(Msg.translate(Env.getCtx(), "C_BP_BankAccount_ID"));
		sAmountLabel.setText(Msg.getMsg(Env.getCtx(), "Amount"));
		// sAmountField.setText("");
		sRoutingLabel.setText(Msg.translate(Env.getCtx(), "RoutingNo"));
		sNumberLabel.setText(Msg.translate(Env.getCtx(), "AccountNo"));
		sCheckLabel.setText(Msg.translate(Env.getCtx(), "CheckNo"));
		sCheckField.setColumns(8);
		sCurrencyLabel.setText(Msg.translate(Env.getCtx(), "C_Currency_ID"));
		sNumberField.setPreferredSize(new Dimension(100, 21));
		sRoutingField.setPreferredSize(new Dimension(70, 21));
		sStatus.setText(" ");
		sOnline.setText(Msg.getMsg(Env.getCtx(), "Online"));
		sPanel.add(sBankAccountLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 2, 0), 0, 0));
		sPanel.add(sBankAccountCombo, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 2, 5), 0, 0));
		sPanel.add(sCurrencyLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 0, 2, 0), 0, 0));
		sPanel.add(sCurrencyCombo, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 2, 5), 0, 0));
		sPanel.add(sAmountLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 0, 5, 0), 0, 0));
		sPanel.add(sAmountField, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 5, 5), 0, 0));
		sPanel.add(sRoutingLabel, new GridBagConstraints(0, 3, 1, 2, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 2, 0), 0, 0));
		sPanel.add(sRoutingField, new GridBagConstraints(1, 3, 2, 1, 0.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 2, 0), 0, 0));
		sPanel.add(sNumberLabel, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 0, 2, 0), 0, 0));
		sPanel.add(sNumberField, new GridBagConstraints(1, 5, 2, 1, 0.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 2, 0), 0, 0));
		sPanel.add(sCheckLabel, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 0, 2, 0), 0, 0));
		sPanel.add(sCheckField, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 2, 0), 0, 0));
		sPanel.add(sOnline, new GridBagConstraints(3, 6, 1, 1, 0.0, 0.0
				, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		sPanel.add(sStatus, new GridBagConstraints(0, 7, 3, 1, 0.0, 0.0
				, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

		loadCurrencies();
		loadBankAccounts();

		sCurrencyCombo.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setAmount();
			}
		});
	}



	@Override
	public java.awt.Component getComponent()
	{
		return sPanel;
	}

	@Override
	public void setFrom(I_C_Payment payment)
	{
		if (payment == null)
			return;

		setC_BP_BankAccount_ID(payment.getC_BP_BankAccount_ID());

		sRoutingField.setText(payment.getRoutingNo());
		sNumberField.setText(payment.getAccountNo());
		sCheckField.setText(payment.getCheckNo());
		sStatus.setText(payment.getR_PnRef());
		sAmountField.setValue(payment.getPayAmt());
	}

	@Override
	protected void loadCurrencies()
	{
		Map<Integer, KeyNamePair> currencies = VPaymentCheck.getCurrencies();
		if (currencies != null)
		{
			for (KeyNamePair knp : currencies.values())
			{
				sCurrencyCombo.addItem(knp);
			}

			sCurrencyLabel.setVisible(true); // Check
			sCurrencyCombo.setVisible(true);
		}
		else
		{
			sCurrencyLabel.setVisible(false); // Check
			sCurrencyCombo.setVisible(false);
		}
	}

	@Override
	protected void loadBankAccounts()
	{

		VPayment.loadComboValues(sBankAccountCombo, retrieveBankAccounts(), -1);
	}

	@Override
	public void setC_Currency_ID(int C_Currency_ID)
	{
		final Map<Integer, KeyNamePair> currencies = VPaymentCheck.getCurrencies();
		if (currencies != null)
		{
			sCurrencyCombo.setSelectedItem(currencies.get(C_Currency_ID));
		}
	}

	@Override
	public int getC_Currency_ID()
	{
		KeyNamePair pp = sCurrencyCombo.getSelectedItem();
		if (pp == null)
			return -1;
		return pp.getKey();
	}

	@Override
	public int getC_BP_BankAccount_ID()
	{
		KeyNamePair kp = sBankAccountCombo.getSelectedItem();
		if (kp != null)
			return kp.getKey();
		else
			return -1;
	}

	@Override
	public void setC_BP_BankAccount_ID(int C_BP_BankAccount_ID)
	{
		for (int i = 0; i < sBankAccountCombo.getItemCount(); i++)
		{
			KeyNamePair knp = sBankAccountCombo.getItemAt(i);
			if (knp.getKey() == C_BP_BankAccount_ID)
			{
				sBankAccountCombo.setSelectedIndex(i);
				return;
			}
		}
		sBankAccountCombo.setSelectedItem(null);
	}

	@Override
	public String getRoutingNo()
	{
		return sRoutingField.getText();
	}

	@Override
	public String getAccountNo()
	{
		return sNumberField.getText();
	}

	@Override
	public String getCheckNo()
	{
		return sCheckField.getText();
	}

	@Override
	public boolean validate(boolean isInTrx)
	{
		if (getC_BP_BankAccount_ID() <= 0)
			throw new FillMandatoryException("C_BP_BankAccount_ID");

		String error = MPaymentValidate.validateRoutingNo(getRoutingNo());
		if (error.length() != 0)
		{
			sRoutingField.setBackground(AdempierePLAF.getFieldBackground_Error());
			throw new AdempiereException(error);
			// ADialog.error(m_WindowNo, this, error);
			// dataOK = false;
		}
		error = MPaymentValidate.validateAccountNo(sNumberField.getText());
		if (error.length() != 0)
		{
			sNumberField.setBackground(AdempierePLAF.getFieldBackground_Error());
			throw new AdempiereException(error);
			// ADialog.error(m_WindowNo, this, error);
			// dataOK = false;
		}
		error = MPaymentValidate.validateCheckNo(sCheckField.getText());
		if (error.length() != 0)
		{
			sCheckField.setBackground(AdempierePLAF.getFieldBackground_Error());
			throw new AdempiereException(error);
			// ADialog.error(m_WindowNo, this, error);
			// dataOK = false;
		}

		return true;
	}

	@Override
	public int getC_CashBook_ID()
	{
		return -1;
	}

	@Override
	public String getTargetTableName()
	{
		return I_C_Payment.Table_Name;
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
		// TODO Auto-generated method stub

	}

	private boolean readonly = true;

	@Override
	public void setReadOnly(boolean readOnly)
	{
		this.readonly = readOnly;
		uiHelper.updateFieldRO();
	}

	@Override
	public void setAmount(BigDecimal amount)
	{
		sAmountField.setValue(amount);
	}

	@Override
	public BigDecimal getAmount()
	{
		return (BigDecimal)sAmountField.getValue();
	}
	@Override
	public boolean isReadOnly()
	{
		return readonly;
	}
}
