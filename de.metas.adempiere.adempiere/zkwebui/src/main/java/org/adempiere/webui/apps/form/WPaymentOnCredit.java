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

import org.adempiere.webui.component.Panel;
import org.adempiere.webui.util.ZkUtil;
import org.compiere.grid.AbstractPaymentOnCredit;
import org.compiere.grid.IPayableDocument;
import org.compiere.grid.VPayment.ProcessingCtx;
import org.compiere.model.I_C_CashLine;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MPayment;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

public class WPaymentOnCredit extends AbstractPaymentOnCredit
{

	private final Panel pPanel = new Panel();
	private final Grid pPanelLayout = new Grid();

	private final Label pTermLabel = new Label();
	private final Listbox pTermCombo = new Listbox();
	private final ListModelList pTermModel = new ListModelList();

	public WPaymentOnCredit()
	{
		initUI();
	}

	@Override
	public Object getComponent()
	{
		return pPanel;
	}

	@Override
	public void setFrom(I_C_Payment payment)
	{
	}

	@Override
	public void setFrom(I_C_CashLine cashline)
	{
	}

	@Override
	public void setFrom(IPayableDocument doc)
	{
		setC_PaymentTerm_ID(doc.getC_PaymentTerm_ID());
	}

	@Override
	protected void initUI()
	{
		pPanel.appendChild(pPanelLayout);
		pTermLabel.setValue(Msg.translate(Env.getCtx(), "C_PaymentTerm_ID"));
		pPanel.setId("pPanel");

		pTermCombo.setModel(pTermModel);

		final Rows rows = new Rows();
		rows.setParent(pPanelLayout);

		final Row row = new Row();
		row.setParent(rows);

		// row.appendChild(pTermLabel.rightAlign());
		row.appendChild(pTermLabel);
		row.appendChild(pTermCombo);

		loadPaymentTerms();
	}

	@Override
	public int getC_CashBook_ID()
	{
		return -1;
	}

	@Override
	public String getTargetTableName()
	{
		return null; // nothing: don't generate, don't reverse
	}

	@Override
	public int getC_PaymentTerm_ID()
	{
		final KeyNamePair knp = ZkUtil.getSelectedValue(pTermCombo);
		return knp != null ? knp.getKey() : -1;
	}

	private void loadPaymentTerms()
	{
		final KeyNamePair[] knps = retrievePaymentTerms();

		pTermModel.clear();
		pTermModel.addAll(Arrays.asList(knps));
	}

	@Override
	public void setC_PaymentTerm_ID(int C_PaymentTerm_ID)
	{
		for (Object o : pTermModel)
		{
			final KeyNamePair knp = (KeyNamePair)o;
			if (knp != null && knp.getKey() == C_PaymentTerm_ID)
			{
				ZkUtil.setSelectedValue(pTermCombo, knp);
				return;
			}
		}
		
		ZkUtil.setSelectedValue(pTermCombo, null);
	}

	public void setReadOnly(boolean readOnly)
	{
		super.setReadOnly(readOnly);
		pTermCombo.setDisabled(readOnly);
	}

	public void setEnabled(boolean enabled)
	{
		super.setEnabled(enabled);
		pTermCombo.setDisabled(!enabled);
	}

	@Override
	public void updatePayment(ProcessingCtx pctx, MPayment payment)
	{
		// TODO Auto-generated method stub

	}
}
