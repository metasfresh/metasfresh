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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

import javax.swing.JComponent;

import org.adempiere.util.time.SystemTime;
import org.compiere.grid.ed.VDate;
import org.compiere.grid.ed.VNumber;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

import de.metas.i18n.Msg;

public class VPaymentCash extends AbstractPaymentCash
{
	private final CPanel bPanel = new CPanel();
	private final CLabel bCurrencyLabel = new CLabel();
	private final CComboBox<KeyNamePair> bCurrencyCombo = new CComboBox<>();
	private final CLabel bAmountLabel = new CLabel();
	private final VNumber bAmountField = new VNumber();
	private final VDate bDateField = new VDate("DateAcct", false, false, true, DisplayType.Date, "DateAcct");
	private final CLabel bDateLabel = new CLabel();
	private final GridBagLayout bPanelLayout = new GridBagLayout();
	private final CLabel sBankAccountLabel = new CLabel();
	private final CComboBox<KeyNamePair> sBankAccountCombo = new CComboBox<>();
	private final CLabel bCashBookLabel = new CLabel();
	private final CComboBox<KeyNamePair> bCashBookCombo = new CComboBox<>();

	private final JComponent[] fields = new JComponent[] {
			bCurrencyCombo,
			bAmountField,
			bDateField,
			sBankAccountCombo,
			bCashBookCombo,
	};
	private final VPaymentPanelUIHelper uiHelper = new VPaymentPanelUIHelper(this, fields);

	public VPaymentCash()
	{
		super();
		// initUI(); // delayed initialization: see setFrom(IPayableDocument)
	}

	@Override
	protected void initUI()
	{
		// bDateField = new VDate("DateAcct", false, false, true, DisplayType.Date, "DateAcct");
		bDateField.setValue(SystemTime.asTimestamp());

		bCurrencyLabel.setText(Msg.translate(Env.getCtx(), "C_Currency_ID"));

		bPanel.setLayout(bPanelLayout);
		bAmountLabel.setText(Msg.getMsg(Env.getCtx(), "Amount"));
		// bAmountField.setText("");
		bDateLabel.setText(Msg.translate(Env.getCtx(), "DateAcct"));

		if (isCashAsPayment())
		{
			sBankAccountLabel.setText(Msg.translate(Env.getCtx(), "C_CashBook_ID"));
			bPanel.add(sBankAccountLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
					, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 0, 2, 0), 0, 0));
			bPanel.add(sBankAccountCombo, new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0
					, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 2, 5), 0, 0));

		}
		else
		{
			bCashBookLabel.setText(Msg.translate(Env.getCtx(), "C_CashBook_ID"));
			bPanel.add(bCashBookLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
					, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 0, 2, 0), 0, 0));
			bPanel.add(bCashBookCombo, new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0
					, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 2, 5), 0, 0));

		}

		bPanel.add(bCurrencyLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 0, 2, 0), 0, 0));
		bPanel.add(bCurrencyCombo, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 2, 5), 0, 0));
		bPanel.add(bDateLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 2, 0), 0, 0));
		bPanel.add(bDateField, new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 2, 5), 0, 0));
		bPanel.add(bAmountLabel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 2, 0), 0, 0));
		bPanel.add(bAmountField, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 2, 5), 0, 0));

		loadCurrencies();
		loadCashBooks();

		bCurrencyCombo.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setAmount();
			}
		});

	}

	@Override
	protected void loadCashBooks()
	{
		if (isLoaded(bCashBookCombo))
			return;

		if (isCashAsPayment())
			return;

		final KeyNamePair[] knps = retrieveCashBooks();
		loadComboValues(bCashBookCombo, knps, -1);
		// if (m_C_CashBook_ID == 0)
		// m_C_CashBook_ID = kp.getKey(); // set to default to avoid 'cashbook changed' message
	}

	@Override
	protected void loadCashBankAccounts()
	{
		if (isLoaded(sBankAccountCombo))
			return;

		if (!isCashAsPayment())
			return;

		final KeyNamePair[] knps = retrieveCashBankAccounts();
		loadComboValues(sBankAccountCombo, knps, -1);
	}

	private boolean isLoaded(JComponent comp)
	{
		if (comp == null)
			return false;
		Boolean loaded = (Boolean)comp.getClientProperty(PROP_Loaded);
		return loaded != null && loaded.booleanValue() == true;
	}

	private void loadComboValues(CComboBox<KeyNamePair> combo, KeyNamePair[] values, int defaultKey)
	{
		VPayment.loadComboValues(combo, values, defaultKey);
		combo.putClientProperty(PROP_Loaded, true);
	}

	@Override
	public java.awt.Component getComponent()
	{
		return bPanel;
	}

	@Override
	public void setC_CashBook_ID(int C_CashBook_ID)
	{
		for (int i = 0; i < bCashBookCombo.getItemCount(); i++)
		{
			KeyNamePair knp = bCashBookCombo.getItemAt(i);
			if (knp.getKey() == C_CashBook_ID)
			{
				bCashBookCombo.setSelectedIndex(i);
				return;
			}
		}
		bCashBookCombo.setSelectedItem(null);
	}

	@Override
	public void setAmount(BigDecimal amount)
	{
		bAmountField.setValue(amount);
	}

	private void loadCurrencies()
	{
		Map<Integer, KeyNamePair> currencies = VPaymentCheck.getCurrencies();
		if (currencies != null)
		{
			for (KeyNamePair knp : currencies.values())
			{
				bCurrencyCombo.addItem(knp);
			}

			bCurrencyLabel.setVisible(true); // Check
			bCurrencyCombo.setVisible(true);
		}
		else
		{
			bCurrencyLabel.setVisible(false); // Check
			bCurrencyCombo.setVisible(false);
		}
	}

	@Override
	public void setC_Currency_ID(int C_Currency_ID)
	{
		final Map<Integer, KeyNamePair> currencies = VPaymentCheck.getCurrencies();
		if (currencies != null)
		{
			bCurrencyCombo.setSelectedItem(currencies.get(C_Currency_ID));
		}
	}

	@Override
	public int getC_Currency_ID()
	{
		KeyNamePair pp = bCurrencyCombo.getSelectedItem();
		if (pp == null)
			return -1;
		return pp.getKey();
	}

	@Override
	public void setDate(Timestamp date)
	{
		bDateField.setValue(date);
	}

	@Override
	public Timestamp getDate()
	{
		return bDateField.getValue();
	}

	@Override
	public BigDecimal getAmount()
	{
		return (BigDecimal)bAmountField.getValue();
	}

	@Override
	public int getC_CashBook_ID()
	{
		KeyNamePair kp = bCashBookCombo.getSelectedItem();
		if (kp != null)
			return kp.getKey();
		else
			return -1;
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
}
