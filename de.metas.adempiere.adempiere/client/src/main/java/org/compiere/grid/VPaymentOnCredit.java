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

import org.compiere.grid.VPayment.ProcessingCtx;
import org.compiere.model.I_C_CashLine;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MPayment;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

import de.metas.i18n.Msg;

public class VPaymentOnCredit extends AbstractPaymentOnCredit
{
	private CPanel pPanel = new CPanel();;
	private CLabel pTermLabel = new CLabel();;
	private CComboBox<KeyNamePair> pTermCombo = new CComboBox<>();
	private GridBagLayout pPanelLayout = new GridBagLayout();

	private final JComponent[] fields = new JComponent[] {
			pTermCombo,
	};
	private final VPaymentPanelUIHelper uiHelper = new VPaymentPanelUIHelper(this, fields);

	public VPaymentOnCredit()
	{
		initUI();
	}

	@Override
	protected void initUI()
	{
		
		pPanel.setLayout(pPanelLayout);
		pTermLabel.setText(Msg.translate(Env.getCtx(), "C_PaymentTerm_ID"));
		pPanel.add(pTermLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 5, 2, 0), 0, 0));
		pPanel.add(pTermCombo, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 2, 5), 0, 0));

		loadPaymentTerms();
	}

	@Override
	public java.awt.Component getComponent()
	{
		return pPanel;
	}

	private void loadPaymentTerms()
	{
		VPayment.loadComboValues(pTermCombo, retrievePaymentTerms(), -1);
	}

	@Override
	public void setC_PaymentTerm_ID(int C_PaymentTerm_ID)
	{
		for (int i = 0; i < pTermCombo.getItemCount(); i++)
		{
			KeyNamePair knp = pTermCombo.getItemAt(i);
			if (knp.getKey() == C_PaymentTerm_ID)
			{
				pTermCombo.setSelectedIndex(i);
				return;
			}
		}
		pTermCombo.setSelectedItem(null);
	}

	@Override
	public int getC_PaymentTerm_ID()
	{
		KeyNamePair kp = pTermCombo.getSelectedItem();
		if (kp != null)
			return kp.getKey();
		else
			return -1;

	}

	@Override
	public boolean validate(boolean isInTrx)
	{
		return true;
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
	public void setEnabled(boolean enabled)
	{
		super.setEnabled(enabled);
		uiHelper.updateFieldRO();
	}

	@Override
	public void setFrom(IPayableDocument doc)
	{
		setC_PaymentTerm_ID(doc.getC_PaymentTerm_ID());
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
	public void setReadOnly(boolean readOnly)
	{
		super.setReadOnly(readOnly);
		uiHelper.updateFieldRO();
	}

	@Override
	public void updatePayment(ProcessingCtx pctx, MPayment payment)
	{
		// TODO Auto-generated method stub
		
	}

}
