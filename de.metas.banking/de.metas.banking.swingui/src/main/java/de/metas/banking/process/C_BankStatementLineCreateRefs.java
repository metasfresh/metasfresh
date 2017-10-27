package de.metas.banking.process;

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


import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.MBankStatementLine;
import org.compiere.model.MPaySelection;

import de.metas.banking.model.I_C_BankStatementLine;
import de.metas.process.JavaProcess;

/**
 * 
 * @deprecated tsa - this is legacy so please consider removing it
 *
 */
@Deprecated
public class C_BankStatementLineCreateRefs extends JavaProcess implements ActionListener
{
	private boolean dontShowAllDD = true;

	private JButton button = null;
	private JCheckBox box = null;
	private JComboBox<String> comboBox = null;
	private JDialog frame = null;

	private List<MPaySelection> psCompleteList = null;
	private List<MPaySelection> psFittingList = null;

	

	@Override
	protected String doIt() throws Exception
	{

		final MBankStatementLine bsl = new MBankStatementLine(getCtx(),
				getRecord_ID(), get_TrxName());

		// select eligible payselection records
		

		BigDecimal amt = BigDecimal.ZERO;

		psFittingList = new ArrayList<MPaySelection>();

		for (final MPaySelection ps : psCompleteList)
		{

			

		

			if (amt.compareTo(bsl.getStmtAmt()) == 0)
			{
				psFittingList.add(ps);
			}
		}

		frame = new JDialog();
		frame.setSize(500, 400);
		frame.setLocation(550, 400);
		frame.setLayout(new FlowLayout());
		frame.setContentPane(getContentPane());
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setModal(true);
		frame.setVisible(true);

		return "";
	}

	private JPanel getContentPane()
	{

		JPanel panel = new JPanel();

		panel.setLayout(new GridBagLayout());

		box = new JCheckBox("Nur passende", dontShowAllDD);
		box.addActionListener(this);

		if (dontShowAllDD)
		{
			comboBox = getComboBox(psFittingList);
		}
		else
		{
			comboBox = getComboBox(psCompleteList);
		}

		if (comboBox.getItemCount() > 0)
		{
			comboBox.setSelectedItem(0);
		}

		button = new JButton("OK");
		button.addActionListener(this);

		GridBagConstraints g1 = new GridBagConstraints();
		g1.gridx = 0;
		g1.gridy = 0;
		GridBagConstraints g2 = new GridBagConstraints();
		g2.gridx = 1;
		g2.gridy = 0;
		GridBagConstraints g3 = new GridBagConstraints();
		g3.gridx = 0;
		g3.gridy = 1;
		g3.gridwidth = 2;

		panel.add(comboBox, g1);
		panel.add(box, g2);
		panel.add(button, g3);

		return panel;
	}

	private JComboBox<String> getComboBox(final List<MPaySelection> ddList)
	{
		final String[] comboBoxContent = new String[ddList.size()];

		int i = 0;
		for (final MPaySelection dd : ddList)
		{
			comboBoxContent[i] =
					dd.getName()
							+ " (" + dd.getLines(false).length + ") "
							+ dd.getTotalAmt().setScale(2, RoundingMode.HALF_UP)
							+ "";
			i++;
		}
		return new JComboBox<String>(comboBoxContent);
	}

	@Override
	protected void prepare()
	{

		// nothing to do
	}

	private void createPaymentsAndRefs()
	{
		final MBankStatementLine bslPO = new MBankStatementLine(getCtx(), getRecord_ID(), get_TrxName());

		final I_C_BankStatementLine bsl = InterfaceWrapperHelper.create(bslPO, I_C_BankStatementLine.class);
		bsl.setIsMultiplePaymentOrInvoice(true);
		bsl.setIsMultiplePayment(true);
		bslPO.saveEx();

		
		
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == box)
		{
			dontShowAllDD = box.isSelected();
			// System.out.println(dontShowAllDD);
			// frame.removeAll();
			frame.setContentPane(getContentPane());
			frame.validate();
			frame.repaint();
		}
		else if (e.getSource() == button)
		{
			if (comboBox.getItemCount() > 0)
			{
				createPaymentsAndRefs();
				frame.dispose();
			}
		}

	}
}
