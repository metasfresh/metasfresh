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


import java.util.Arrays;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.webui.LayoutUtils;
import org.adempiere.webui.component.Button;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.GridFactory;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.Listbox;
import org.adempiere.webui.component.ListboxFactory;
import org.adempiere.webui.component.Panel;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.component.Textbox;
import org.compiere.grid.AbstractPaymentDirectDebit;
import org.compiere.grid.IPayableDocument;
import org.compiere.grid.VPayment.ProcessingCtx;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MPayment;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;

import org.adempiere.webui.component.ListItem;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Space;

public class WPaymentDirectDebit extends AbstractPaymentDirectDebit
{
	private Panel tPanel = new Panel();
	private Grid tPanelLayout = GridFactory.newGridLayout();

	private Label tAccountLabel = new Label();
	private Listbox tAccountCombo = ListboxFactory.newDropdownListbox();
	private ListModelList tAccountModel = null;

	private Label tBPAccountLabel = new Label();
	private Listbox tBPAccountCombo = ListboxFactory.newDropdownListbox();
	private ListModelList tBPAccountModel = null;

	private Button tOnline = new Button();
	private Textbox tRoutingField = new Textbox();
	private Textbox tNumberField = new Textbox();
	private Label tStatus = new Label();
	private Label tRoutingText = new Label();
	private Label tNumberText = new Label();

	private IPayableDocument doc;

	private int m_C_BPartner_ID = -1;

	public WPaymentDirectDebit()
	{
		initUI();
	}

	@Override
	public Object getComponent()
	{
		return tPanel;
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

	private void setStatus(String rPnRef)
	{
		tStatus.setText(rPnRef);

	}

	private void setAccountNo(String accountNo)
	{
		tNumberField.setText(accountNo);

	}

	private void setRountingNo(String routingNo)
	{
		tRoutingField.setText(routingNo);

	}

	@Override
	public void setFrom(IPayableDocument doc)
	{
		this.doc = doc;
		setC_BPartner_ID(doc.getC_BPartner_ID());
		tAccountCombo.removeAllItems();

		final KeyNamePair[] knps = retrieveBankAccounts();
		tAccountModel = new ListModelList();
		tAccountModel.addAll(Arrays.asList(knps));

		tAccountCombo.setModel(tAccountModel);

	}

	private void setC_BPartner_ID(int C_BPartner_ID)
	{
		int oldBPartnerId = m_C_BPartner_ID;
		m_C_BPartner_ID = C_BPartner_ID;

		if (oldBPartnerId != m_C_BPartner_ID || tBPAccountCombo.getItemCount() == 0)
		{
			loadBPBankAccounts(m_C_BPartner_ID);
		}

	}

	private void loadBPBankAccounts(int C_BPartner_ID)
	{
		final KeyNamePair[] knps = retrieveBPBankAccounts(C_BPartner_ID);
		tBPAccountModel = new ListModelList();
		tBPAccountModel.addAll(Arrays.asList(knps));

		tBPAccountCombo.setModel(tBPAccountModel);
	}

	@Override
	public boolean validate(boolean isInTrx)
	{
		if (getC_BP_BankAccount_ID() <= 0)
		{
			throw new AdempiereException("@PaymentBPBankNotFound@");
		}
		if (getC_BankAccount_ID() <= 0)
		{
			throw new AdempiereException("@PaymentBankNotFound@");
		}
		return true;
	}

	@Override
	public void updatePayment(ProcessingCtx pctx, MPayment payment)
	{
		int C_BankAccount_ID = this.getC_BankAccount_ID();
		payment.setBankACH(C_BankAccount_ID, doc.isSOTrx(), pctx.newPaymentRule,
				this.getRountingNo(), this.getAccountNo());
	}

	private String getAccountNo()
	{
		return tNumberField.getText();
	}

	private String getRountingNo()
	{
		return tRoutingField.getText();
	}

	private int getC_BP_BankAccount_ID()
	{
		final ListItem item = tAccountCombo.getSelectedItem();
		final KeyNamePair knp = (KeyNamePair)item.getValue();
		return knp != null ? knp.getKey() : -1;
	}

	private int getC_BankAccount_ID()
	{
		final ListItem item = tBPAccountCombo.getSelectedItem();
		final KeyNamePair knp = (KeyNamePair)item.getValue();
		return knp != null ? knp.getKey() : -1;
	}

	@Override
	protected void initUI()
	{
		tPanel.appendChild(tPanelLayout);
		tAccountLabel.setText(Msg.translate(Env.getCtx(), "C_BankAccount_ID"));
		tBPAccountLabel.setText(Msg.translate(Env.getCtx(), "C_BP_BankAccount_ID"));
		tRoutingField.setCols(8);
		tNumberField.setCols(10);
		tRoutingText.setText(Msg.translate(Env.getCtx(), "RoutingNo"));
		tNumberText.setText(Msg.translate(Env.getCtx(), "AccountNo"));
		tOnline.setLabel(Msg.getMsg(Env.getCtx(), "Online"));
		LayoutUtils.addSclass("action-text-button", tOnline);
		tStatus.setText(" ");
		tPanel.setId("tPanel");

		Rows rows = tPanelLayout.newRows();
		Row row = rows.newRow();
		row.appendChild(tAccountLabel.rightAlign());
		row.appendChild(tAccountCombo);
		row.appendChild(new Space());
		row.appendChild(new Space());

		row = rows.newRow();
		row.appendChild(tBPAccountLabel.rightAlign());
		row.appendChild(tBPAccountCombo);
		row.appendChild(new Space());
		row.appendChild(new Space());

		row = rows.newRow();
		row.appendChild(tRoutingText.rightAlign());
		row.appendChild(tRoutingField);
		row.appendChild(new Space());
		row.appendChild(new Space());

		row = rows.newRow();
		row.appendChild(tNumberText.rightAlign());
		row.appendChild(tNumberField);
		row.appendChild(new Space());
		row.appendChild(tOnline);

		row = rows.newRow();
		row.setSpans("3,1");
		row.appendChild(tStatus);
		row.appendChild(new Space());

	}

	public void setReadOnly(boolean readOnly)
	{
		super.setReadOnly(readOnly);

		tBPAccountCombo.setEnabled(!readOnly);
		tAccountCombo.setEnabled(!readOnly);
		tRoutingField.setReadonly(readOnly);
		tNumberField.setReadonly(readOnly);
	}

	public void setEnabled(boolean enabled)
	{
		super.setEnabled(enabled);

		tBPAccountCombo.setEnabled(enabled);
		tAccountCombo.setEnabled(enabled);
		tRoutingField.setReadonly(!enabled);
		tNumberField.setReadonly(!enabled);
	}
}
