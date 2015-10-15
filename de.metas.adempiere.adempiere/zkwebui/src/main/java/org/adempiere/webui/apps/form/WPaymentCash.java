package org.adempiere.webui.apps.form;

/*
 * #%L
 * de.metas.adempiere.adempiere.zkwebui
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


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Map;

import org.adempiere.webui.component.ListboxFactory;
import org.adempiere.webui.component.Panel;
import org.adempiere.webui.editor.WDateEditor;
import org.adempiere.webui.editor.WNumberEditor;
import org.adempiere.webui.util.ZkUtil;
import org.compiere.grid.AbstractPaymentCash;
import org.compiere.grid.VPaymentCheck;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

public class WPaymentCash extends AbstractPaymentCash
{
	// private GridTab m_mTab;
	private Panel bPanel = new Panel();
	private Grid bPanelLayout = new Grid();
	private Label bAmountLabel = new Label();
	private WNumberEditor bAmountField = new WNumberEditor();
	private WDateEditor bDateField;
	private Label bDateLabel = new Label();
	private Label bCurrencyLabel = new Label();
	private Combobox bCurrencyCombo = new Combobox();

	private ListModelList bCashBookModel = new ListModelList();
	private Label bCashBookLabel = new Label();
	private Listbox bCashBookCombo = ListboxFactory.newDropdownListbox();

	private Listbox bBankAccountCombo = ListboxFactory.newDropdownListbox();
	private Label bBankAccountLabel = new Label();

	private Listbox sBankAccountCombo = ListboxFactory.newDropdownListbox();
	private ListModelList sBankAccountModel = new ListModelList();
	private Label sBankAccountLabel = new Label();

	private Listbox sCurrencyCombo;
	private final ListModelList sCurrencyModel = new ListModelList();

	public WPaymentCash()
	{
		// initUI(); // delayed initialization: see setFrom(IPayableDocument)
	}

	@Override
	protected void initUI()
	{
		bCashBookLabel.setValue(Msg.translate(Env.getCtx(), "C_CashBook_ID"));
		bCurrencyLabel.setValue(Msg.translate(Env.getCtx(), "C_Currency_ID"));
		bBankAccountLabel.setValue(Msg.translate(Env.getCtx(), "C_BP_BankAccount_ID"));
		sCurrencyCombo.setModel(sCurrencyModel);
		bBankAccountCombo.setModel(sBankAccountModel);
		sBankAccountCombo.setModel(sBankAccountModel);
		bCashBookCombo.setModel(bCashBookModel);
		bPanel.appendChild(bPanelLayout);
		bAmountLabel.setValue(Msg.getMsg(Env.getCtx(), "Amount"));
		// bAmountField.setText("");
		bDateLabel.setValue(Msg.translate(Env.getCtx(), "DateAcct"));
		bPanel.setId("bPanel");

		sBankAccountLabel.setValue(Msg.translate(Env.getCtx(), "C_BP_BankAccount_ID"));
		final Rows rows = new Rows();
		rows.setParent(bPanel);
		Row row = new Row();
		{
			row.setParent(rows);
			row.appendChild(bBankAccountLabel);
			row.appendChild(bBankAccountCombo);
		}
		{
			row = new Row();
			row.setParent(rows);
			row.appendChild(bCurrencyLabel);
			row.appendChild(bCurrencyCombo);
		}
		{
			row = new Row();
			row.setParent(rows);
			row.appendChild(bDateLabel);
			row.appendChild(bDateField.getComponent());
		}
		{
			row = new Row();
			row.setParent(rows);
			row.appendChild(bAmountLabel);
			row.appendChild(bAmountField.getComponent());
		}

		bCurrencyCombo.addEventListener(Events.ON_SELECT, new EventListener()
		{
			public void onEvent(Event e)
			{
				setAmount();
			}
		});

	}

	@Override
	protected void loadCashBooks()
	{
		final KeyNamePair[] knps = retrieveCashBooks();
		bCashBookModel.clear();
		bCashBookModel.addAll(Arrays.asList(knps));

	}

	@Override
	protected void loadCashBankAccounts()
	{
		final KeyNamePair[] knps = retrieveCashBankAccounts();
		sBankAccountModel.clear();
		sBankAccountModel.addAll(Arrays.asList(knps));
	}

	@Override
	public Object getComponent()
	{
		return bPanel;
	}

	@Override
	public BigDecimal getAmount()
	{
		return (BigDecimal)bAmountField.getValue();
	}

	@Override
	public void setDate(Timestamp date)
	{
		bDateField.setValue(date);

	}

	@Override
	public Timestamp getDate()
	{
		return (Timestamp)bDateField.getValue();
	}

	@Override
	public int getC_Currency_ID()
	{
		final Comboitem item = bCurrencyCombo.getSelectedItem();
		final KeyNamePair knp = (KeyNamePair)item.getValue();
		return knp != null ? knp.getKey() : -1;
	}

	@Override
	public void setC_Currency_ID(int C_Currency_ID)
	{
		final Map<Integer, KeyNamePair> currencies = VPaymentCheck.getCurrencies();
		final KeyNamePair currency = currencies.get(C_Currency_ID);

		ZkUtil.setSelectedValue(bCurrencyCombo, currency);
	}

	@Override
	public int getC_CashBook_ID()
	{
		final Listitem item = bCashBookCombo.getSelectedItem();
		final KeyNamePair knp = (KeyNamePair)item.getValue();
		return knp != null ? knp.getKey() : -1;
	}

	@Override
	public void setAmount(BigDecimal amount)
	{
		bAmountField.setValue(amount);

	}

	@Override
	public int getC_BP_BankAccount_ID()
	{
		final Listitem item = sBankAccountCombo.getSelectedItem();
		final KeyNamePair knp = (KeyNamePair)item.getValue();
		return knp != null ? knp.getKey() : -1;
	}

	@Override
	public void setC_BP_BankAccount_ID(int C_BP_BankAccount_ID)
	{
		for (int i = 0; i < sBankAccountCombo.getItemCount(); i++)
		{
			KeyNamePair knp = (KeyNamePair)sBankAccountCombo.getItemAtIndex(i).getValue();
			if (knp.getKey() == C_BP_BankAccount_ID)
			{
				sBankAccountCombo.setSelectedIndex(i);
				return;
			}
		}
		sBankAccountCombo.setSelectedItem(null);

	}

	@Override
	public void setC_CashBook_ID(int c_CashBook_ID)
	{
		for (int i = 0; i < bCashBookCombo.getItemCount(); i++)
		{
			KeyNamePair knp = (KeyNamePair)bCashBookCombo.getItemAtIndex(i).getValue();
			if (knp.getKey() == c_CashBook_ID)
			{
				bCashBookCombo.setSelectedIndex(i);
				return;
			}
		}
		bCashBookCombo.setSelectedItem(null);

	}

	protected void loadCurrencies()
	{
		final KeyNamePair[] knps = retrieveCurrencies();
		sCurrencyModel.clear();
		sCurrencyModel.addAll(Arrays.asList(knps));
	}
}
