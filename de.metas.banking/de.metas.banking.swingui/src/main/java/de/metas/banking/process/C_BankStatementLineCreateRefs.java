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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.MAllocationHdr;
import org.compiere.model.MAllocationLine;
import org.compiere.model.MBankStatementLine;
import org.compiere.model.MPaySelection;
import org.compiere.model.MPaySelectionCheck;
import org.compiere.model.MPaySelectionLine;
import org.compiere.model.MPayment;
import org.compiere.model.Query;
import org.compiere.model.X_C_PaySelectionCheck;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.banking.interfaces.I_C_BankStatementLine_Ref;
import de.metas.banking.model.I_C_BankStatementLine;
import de.metas.banking.payment.IPaySelectionDAO;
import de.metas.process.SvrProcess;

/**
 * 
 * @deprecated tsa - this is legacy so please consider removing it
 *
 */
@Deprecated
public class C_BankStatementLineCreateRefs extends SvrProcess implements ActionListener
{

	private final IPaySelectionDAO paySelectionDAO = Services.get(IPaySelectionDAO.class);

	private boolean dontShowAllDD = true;

	private JButton button = null;
	private JCheckBox box = null;
	private JComboBox<String> comboBox = null;
	private JDialog frame = null;

	private List<MPaySelection> psCompleteList = null;
	private List<MPaySelection> psFittingList = null;

	private Map<MPaySelection, Collection<MPaySelectionCheck>> ps2psc = new HashMap<MPaySelection, Collection<MPaySelectionCheck>>();

	@Override
	protected String doIt() throws Exception
	{

		final MBankStatementLine bsl = new MBankStatementLine(getCtx(),
				getRecord_ID(), get_TrxName());

		// select eligible payselection records
		psCompleteList = new Query(
				getCtx(),
				I_C_PaySelection.Table_Name,
				"EXISTS (select * from C_PaySelectionCheck psc where psc.C_Payselection_ID=C_PaySelection.C_PaySelection_ID and psc.IsActive='Y' and psc.PaymentRule in ('P','D','T') and (psc.C_Payment_ID is null OR psc.C_Payment_ID=0))",
				get_TrxName()).setOnlyActiveRecords(true).setClient_ID().list();

		BigDecimal amt = BigDecimal.ZERO;

		psFittingList = new ArrayList<MPaySelection>();

		for (final MPaySelection ps : psCompleteList)
		{

			final Collection<MPaySelectionCheck> pscs = Services.get(IPaySelectionDAO.class).retrievePaySelectionChecks(ps);

			ps2psc.put(ps, pscs);

			for (final MPaySelectionCheck psc : pscs)
			{
				amt = amt.add(psc.getPayAmt());
			}

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

		final MPaySelection selectedPayselection;

		if (dontShowAllDD)
		{
			selectedPayselection = psFittingList.get(comboBox.getSelectedIndex());
		}
		else
		{
			selectedPayselection = psCompleteList.get(comboBox.getSelectedIndex());
		}

		final I_C_BankStatementLine bsl = InterfaceWrapperHelper.create(bslPO, I_C_BankStatementLine.class);
		bsl.setIsMultiplePaymentOrInvoice(true);
		bsl.setIsMultiplePayment(true);
		bslPO.saveEx();

		int i = DB.getSQLValueEx(get_TrxName(), "SELECT max(line) FROM "
				+ I_C_BankStatementLine_Ref.Table_Name + " WHERE "
				+ I_C_BankStatementLine.COLUMNNAME_C_BankStatementLine_ID + "="
				+ getRecord_ID()) + 10;

		for (final MPaySelectionCheck paySelectionCheck : ps2psc.get(selectedPayselection))
		{
			final MPayment payment = new MPayment(getCtx(), 0, get_TrxName());

			final String paymentRule = paySelectionCheck.getPaymentRule();

			final boolean isIncomingPayment;
			if (X_C_PaySelectionCheck.PAYMENTRULE_DirectDebit.equals(paymentRule))
			{
				isIncomingPayment = false;
			}
			else if (X_C_PaySelectionCheck.PAYMENTRULE_DirectDeposit.equals(paymentRule))
			{
				isIncomingPayment = true;
			}
			else
			{
				addLog("Skipping C_PaySelectionCheck " + paySelectionCheck + " because it has paymentRule " + paymentRule);
				continue;
			}

			payment.setC_DocType_ID(isIncomingPayment);
			if (isIncomingPayment)
			{
				payment.setTenderType(MPayment.TENDERTYPE_DirectDeposit);
			}
			else
			{
				payment.setTenderType(MPayment.TENDERTYPE_DirectDebit);
			}

			payment.setC_BP_BankAccount_ID(bsl.getC_BankStatement().getC_BP_BankAccount_ID());
			payment.setPayAmt(paySelectionCheck.getPayAmt());
			payment.setDateAcct(bsl.getStatementLineDate());
			payment.setDateTrx(bsl.getStatementLineDate());

			payment.setC_BPartner_ID(paySelectionCheck.getC_BPartner_ID());
			payment.setC_Currency_ID(selectedPayselection.getC_Currency_ID());
			payment.setDiscountAmt(paySelectionCheck.getDiscountAmt());
			InterfaceWrapperHelper.save(payment);

			paySelectionCheck.setC_Payment_ID(payment.get_ID());
			paySelectionCheck.saveEx();

			final MAllocationHdr alloc = new MAllocationHdr(getCtx(), isIncomingPayment,
					payment.getDateAcct(),
					payment.getC_Currency_ID(),
					"automated Payment (DirectDebit/Deposit)", get_TrxName());

			alloc.setAD_Org_ID(payment.getAD_Org_ID());
			InterfaceWrapperHelper.save(alloc);

			final Collection<MPaySelectionLine> paySelectionLines = paySelectionDAO.retrievePaySelectionLines(paySelectionCheck);

			for (final MPaySelectionLine paySelectionLine : paySelectionLines)
			{
				final I_C_BankStatementLine_Ref mslref =
						InterfaceWrapperHelper.newInstance(I_C_BankStatementLine_Ref.class,
								new PlainContextAware(getCtx(), getTrxName()));
				
				mslref.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));

				final I_C_Invoice invoice = paySelectionLine.getC_Invoice();

				mslref.setC_BPartner_ID(invoice.getC_BPartner_ID());
				mslref.setC_Invoice_ID(paySelectionLine.getC_Invoice_ID());
				mslref.setC_Payment_ID(payment.getC_Payment_ID());
				mslref.setC_Currency_ID(invoice.getC_Currency_ID());
				mslref.setC_BankStatementLine_ID(bsl.getC_BankStatementLine_ID());

				mslref.setLine(i);
				mslref.setTrxAmt(paySelectionLine.getPayAmt());

				InterfaceWrapperHelper.save(mslref);

				final MAllocationLine allocLine = new MAllocationLine(alloc);
				allocLine.setC_Invoice_ID(invoice.getC_Invoice_ID());
				allocLine.setC_Payment_ID(payment.get_ID());
				allocLine.setAmount(paySelectionLine.getPayAmt());
				InterfaceWrapperHelper.save(allocLine);

				log.debug("new Line saved (" + i + ")");

				i += 10;
			}
		}
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
