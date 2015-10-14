package org.adempiere.webui.apps.form;

/*
 * #%L
 * ADempiere ERP - ZkWebUI Lib
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
import java.util.Arrays;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.webui.LayoutUtils;
import org.adempiere.webui.component.GridFactory;
import org.adempiere.webui.component.Panel;
import org.adempiere.webui.editor.WNumberEditor;
import org.adempiere.webui.util.ZkUtil;
import org.compiere.grid.AbstractPaymentCheck;
import org.compiere.grid.VPaymentCheck;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MPaymentValidate;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;

public class WPaymentCheck extends AbstractPaymentCheck
{

	private Label sAmountLabel = new Label();
	private WNumberEditor sAmountField = new WNumberEditor();

	private Listbox sBankAccountCombo = new Listbox();
	private Label sBankAccountLabel = new Label();
	private ListModelList sBankAccountModel = new ListModelList();;

	private Textbox sCheckField = new Textbox();
	private Label sCheckLabel = new Label();

	private Listbox sCurrencyCombo = new Listbox();
	private Label sCurrencyLabel = new Label();
	private ListModelList sCurrencyModel = new ListModelList();

	private Panel sPanel = new Panel();
	private Grid sPanelLayout = GridFactory.newGridLayout();

	private Label sNumberLabel = new Label();
	private Textbox sNumberField = new Textbox();

	private Label sRoutingLabel = new Label();
	private Textbox sRoutingField = new Textbox();

	private Button sOnline = new Button();

	private Label sStatus = new Label();

	private int C_BP_BankAccount_ID = 0;

	// private int m_WindowNo = 0;

	public WPaymentCheck()
	{
		initUI();
	}

	@Override
	public Object getComponent()
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
		sStatus.setValue(payment.getR_PnRef());
		sAmountField.setValue(payment.getPayAmt());

	}

	private boolean readOnly = false;

	@Override
	public void setReadOnly(boolean readOnly)
	{
		this.readOnly = readOnly;
		sNumberField.setReadonly(readOnly);
		sRoutingField.setReadonly(readOnly);
		sCurrencyCombo.setDisabled(readOnly);
		sAmountField.setReadWrite(!readOnly);
		sCheckField.setReadonly(readOnly);
		sOnline.setDisabled(readOnly);
		sBankAccountCombo.setDisabled(readOnly);

	}

	@Override
	public boolean isReadOnly()
	{
		return readOnly;
	}

	@Override
	public int getC_Currency_ID()
	{
		final Listitem item = sCurrencyCombo.getSelectedItem();
		final KeyNamePair knp = (KeyNamePair)item.getValue();
		return knp != null ? knp.getKey() : -1;
	}

	@Override
	public void setC_Currency_ID(int C_Currency_ID)
	{
		final Map<Integer, KeyNamePair> currencies = VPaymentCheck.getCurrencies();
		final KeyNamePair currency = currencies.get(C_Currency_ID);

		ZkUtil.setSelectedValue(sCurrencyCombo, currency);
	}

	@Override
	public boolean validate(boolean isInTrx)
	{

		if (C_BP_BankAccount_ID <= 0)
		{
			throw new FillMandatoryException("C_BP_BankAccount_ID");
		}

		Listitem selected = sBankAccountCombo.getSelectedItem();
		Object value = selected.getAttribute("C_BP_BankAccount_ID");
		if (value != null)
			C_BP_BankAccount_ID = (Integer)value;
		String error = MPaymentValidate.validateRoutingNo(sRoutingField.getText());
		if (error.length() != 0)
		{
			throw new AdempiereException(error);
		}
		error = MPaymentValidate.validateAccountNo(sNumberField.getText());
		if (error.length() != 0)
		{
			throw new AdempiereException(error);
		}
		error = MPaymentValidate.validateCheckNo(sCheckField.getText());
		if (error.length() != 0)
		{
			throw new AdempiereException(error);
		}

		return true;
	}

	protected String getRoutingNo()
	{
		return sRoutingField.getText();
	}

	protected String getCheckNo()
	{
		return sCheckField.getText();
	}

	@Override
	public String getTargetTableName()
	{
		return I_C_Payment.Table_Name;
	}

	@Override
	public String getAccountNo()
	{
		return sNumberField.getText();
	}

	@Override
	public int getC_BP_BankAccount_ID()
	{
		final Listitem item = sBankAccountCombo.getSelectedItem();
		final KeyNamePair knp = (KeyNamePair)item.getValue();
		return knp != null ? knp.getKey() : -1;
	}

	@Override
	protected void loadBankAccounts()
	{

		final KeyNamePair[] knps = retrieveBankAccounts();
		sBankAccountModel = new ListModelList();
		sBankAccountModel.addAll(Arrays.asList(knps));

	}

	protected void loadCurrencies()
	{
		final KeyNamePair[] knps = retrieveCurrencies();
		sCurrencyModel.clear();
		sCurrencyModel.addAll(Arrays.asList(knps));
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
	protected void initUI()
	{
		sPanel.appendChild(sPanelLayout);
		sBankAccountLabel.setValue(Msg.translate(Env.getCtx(), "C_BP_BankAccount_ID"));
		sAmountLabel.setValue(Msg.getMsg(Env.getCtx(), "Amount"));
		sRoutingLabel.setValue(Msg.translate(Env.getCtx(), "RoutingNo"));
		sNumberLabel.setValue(Msg.translate(Env.getCtx(), "AccountNo"));
		sCheckLabel.setValue(Msg.translate(Env.getCtx(), "CheckNo"));
		sCheckField.setCols(8);
		sCurrencyLabel.setValue(Msg.translate(Env.getCtx(), "C_Currency_ID"));
		sNumberField.setWidth("100pt");
		sRoutingField.setWidth("70pt");
		sStatus.setValue(" ");
		sOnline.setLabel(Msg.getMsg(Env.getCtx(), "Online"));
		LayoutUtils.addSclass("action-text-button", sOnline);
		sPanel.setId("sPanel");
		sCurrencyCombo.setModel(sCurrencyModel);
		sBankAccountCombo.setModel(sBankAccountModel);

		final Rows rows = new Rows();
		rows.setParent(sPanelLayout);
		Row row = new Row();
		{
			row.appendChild(sBankAccountLabel);
			row.appendChild(sBankAccountCombo);
			row.appendChild(new Space());
			row.appendChild(new Space());
			rows.appendChild(row);
		}
		{
			row = new Row();
			row.appendChild(sCurrencyLabel);
			row.appendChild(sCurrencyCombo);
			row.appendChild(new Space());
			row.appendChild(new Space());
			rows.appendChild(row);
		}
		{
			row = new Row();
			row.appendChild(sAmountLabel);
			row.appendChild(sAmountField.getComponent());
			row.appendChild(new Space());
			row.appendChild(new Space());
			rows.appendChild(row);
		}
		{
			row = new Row();
			row.appendChild(sRoutingLabel);
			row.appendChild(sRoutingField);
			row.appendChild(new Space());
			row.appendChild(new Space());
			rows.appendChild(row);
		}
		{
			row = new Row();
			row.appendChild(sNumberLabel);
			row.appendChild(sNumberField);
			row.appendChild(new Space());
			row.appendChild(new Space());
			rows.appendChild(row);
		}
		{
			row = new Row();
			row.appendChild(sCheckLabel);
			row.appendChild(sCheckField);
			row.appendChild(new Space());
			row.appendChild(sOnline);
			rows.appendChild(row);
		}
		{
			row = new Row();
			row.setSpans("3,1");
			row.appendChild(sStatus);
			row.appendChild(new Space());
			rows.appendChild(row);
		}
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
	public int getC_CashBook_ID()
	{
		return -1;
	}
}
