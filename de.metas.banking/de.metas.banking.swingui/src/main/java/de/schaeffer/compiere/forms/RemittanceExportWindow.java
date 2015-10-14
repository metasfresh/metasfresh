package de.schaeffer.compiere.forms;

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


import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.adempiere.banking.model.I_C_Invoice;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BankAccount;
import org.compiere.model.MBPBankAccount;
import org.compiere.model.MDirectDebit;
import org.compiere.model.MInvoice;
import org.compiere.model.MOrg;
import org.compiere.model.MRecurrentPaymentLine;
import org.compiere.model.PO;
import org.compiere.model.X_C_BP_BankAccount;
import org.compiere.model.X_C_DirectDebitLine;
import org.compiere.util.Env;
import org.jdtaus.banking.Textschluessel;
import org.jdtaus.banking.TextschluesselVerzeichnis;
import org.jdtaus.banking.dtaus.Transaction;
import org.jdtaus.core.container.ContainerFactory;

import de.metas.banking.model.I_C_RecurrentPaymentLine;
import de.metas.banking.service.IBankingBL;
import de.schaeffer.compiere.tools.DtaHelper;

public class RemittanceExportWindow extends JDialog implements ActionListener
{

	private List<MInvoiceWithAccounts> vendorInvoiceList = new ArrayList<MInvoiceWithAccounts>();
	private List<MInvoiceWithAccounts> recurrentInvoiceList = new ArrayList<MInvoiceWithAccounts>();

	private InvoicePanel vendorPanel = null;
	private InvoicePanel recurrentPanel = null;

	private JButton createPayments = null;

	private String trxName = null;

	private MDirectDebit directDebit = null;

	private String errorMessage = null;

	public RemittanceExportWindow(List<MInvoice> vendorInvoiceList, List<MInvoice> recurrentInvoiceList, String trxName, JFrame frame)
	{

		super(frame, true);

		this.trxName = trxName;

		for (MInvoice inv : vendorInvoiceList)
		{
			this.vendorInvoiceList.add(new MInvoiceWithAccounts(Env.getCtx(), inv.get_ID(), trxName));
		}
		for (MInvoice inv : recurrentInvoiceList)
		{
			this.recurrentInvoiceList.add(new MInvoiceWithAccounts(Env.getCtx(), inv.get_ID(), trxName));
		}

		directDebit = new MDirectDebit(Env.getCtx(), 0, trxName);

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		// this.setLocation(400, 400);
		this.setSize(1000, 800);
		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(getMainPanel()), BorderLayout.CENTER);
		// this.setVisible(true);

	}

	// Constructs the mainPanel, holding recurrent and vendor invoicepanels
	private JPanel getMainPanel()
	{

		JPanel panel = new JPanel();

		panel.setLayout(new GridBagLayout());
		GridBagConstraints g1 = new GridBagConstraints();
		g1.gridx = 0;
		g1.gridy = 0;
		g1.gridwidth = 6;
		g1.insets = new Insets(10, 10, 10, 10);

		GridBagConstraints g2 = new GridBagConstraints();
		g2.gridx = 0;
		g2.gridy = 1;

		GridBagConstraints g3 = new GridBagConstraints();
		g3.gridx = 0;
		g3.gridy = 2;
		g3.gridwidth = 6;
		g3.insets = new Insets(10, 10, 10, 10);

		GridBagConstraints g4 = new GridBagConstraints();
		g4.gridx = 0;
		g4.gridy = 3;

		GridBagConstraints g5 = new GridBagConstraints();
		g5.gridx = 0;
		g5.gridy = 4;
		g5.gridwidth = 6;
		g5.insets = new Insets(10, 10, 10, 10);

		createPayments = new JButton("Erstelle Zahlungen");
		createPayments.addActionListener(this);

		vendorPanel = new InvoicePanel(vendorInvoiceList);
		recurrentPanel = new InvoicePanel(recurrentInvoiceList);

		panel.add(new JLabel("Lieferantenrechnungen"), g1);
		panel.add(vendorPanel, g2);
		panel.add(new JLabel("Wiederkehrende Rechnungen"), g3);
		panel.add(recurrentPanel, g4);
		panel.add(createPayments, g5);

		return panel;

	}

	private List<Transaction> getTransactions(List<MInvoiceWithAccounts> invoiceList) throws ParseException
	{

		List<Transaction> transactionList = new ArrayList<Transaction>();

		final TextschluesselVerzeichnis textschluesselVerzeichnis =
				(TextschluesselVerzeichnis)ContainerFactory.getContainer().
						getObject(TextschluesselVerzeichnis.class.getName());

		final Textschluessel textschluessel =
				textschluesselVerzeichnis.getTextschluessel(51, 0, new java.util.Date());

		for (MInvoiceWithAccounts invoice : invoiceList)
		{

			System.out.println("open " + invoice.getOpenAmt());

			System.out.println(invoice.getDiscountAmt());

			BigDecimal payAmt = invoice.getOpenAmt();
			if (invoice.isDiscount())
			{
				payAmt = payAmt.subtract(invoice.getDiscountAmt());
			}

			System.out.println("pay " + payAmt);

			X_C_DirectDebitLine directDebitLine = new X_C_DirectDebitLine(Env.getCtx(), 0, trxName);

			directDebitLine.setAD_Org_ID(Env.getAD_Org_ID(Env.getCtx()));
			directDebitLine.setC_Invoice_ID(invoice.get_ID());
			directDebitLine.setC_DirectDebit_ID(directDebit.get_ID());
			directDebitLine.save(trxName);

			// VZ darf nur aus alphanummerischen Zeichen bestehen

			transactionList.add(DtaHelper.buildTransaction(
					textschluessel,
					"VZ1",
					"RE NR" + ((invoice.getPOReference() != null) ? invoice.getPOReference() : invoice.getDocumentNo()),
					invoice.getCurrencyISO(),
					payAmt,
					new MOrg(Env.getCtx(), Env.getAD_Org_ID(Env.getCtx()), trxName).getName().toUpperCase(),
					new BigInteger(invoice.getSelectedBankAccount().getAccountNo()),
					new BigInteger(invoice.getSelectedBankAccount().getC_Bank().getRoutingNo()),
					invoice.getC_BPartner().getName().toUpperCase(),
					new BigInteger(invoice.getSelectedBPBankAccount().getAccountNo()),
					new BigInteger(invoice.getSelectedBPBankAccount().getC_Bank().getRoutingNo())
					));

		}
		return transactionList;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == createPayments)
		{
			List<MInvoiceWithAccounts> toPayInvoiceList = new ArrayList<MInvoiceWithAccounts>();
			for (MInvoiceWithAccounts inv : vendorPanel.getAllInvoiceToPay())
			{
				toPayInvoiceList.add(inv);
			}
			for (MInvoiceWithAccounts inv : recurrentPanel.getAllInvoiceToPay())
			{
				toPayInvoiceList.add(inv);
			}

			try
			{
				directDebit.setAD_Org_ID(Env.getAD_Org_ID(Env.getCtx()));
				directDebit.setIsRemittance(true);
				directDebit.save(trxName);

				DtaHelper.createFile(getTransactions(toPayInvoiceList), directDebit, true, trxName);

				if (directDebit != null && directDebit.save(trxName))
				{
					this.dispose();
				}
				else
				{
					errorMessage = "Fehler";
				}
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
				errorMessage = e1.getMessage();
			}
			catch (ParseException e1)
			{
				e1.printStackTrace();
				errorMessage = e1.getMessage();
			}
		}
	}

	public String getErrorMessage()
	{
		return errorMessage;
	}

	/**
	 * @author Jan R��ler, jr@schaeffer-ag.de Class that represents a panel to show the Invoices, that are set in the constructor
	 */
	class InvoicePanel extends JPanel implements ActionListener
	{

		private List<JCheckBox> checkBoxList = new ArrayList<JCheckBox>();
		private List<JCheckBox> checkBoxDiscountList = new ArrayList<JCheckBox>();
		private List<JComboBox> fromComboBoxList = new ArrayList<JComboBox>();
		private List<JComboBox> toComboBoxList = new ArrayList<JComboBox>();

		private List<MInvoiceWithAccounts> invoiceList = null;

		public InvoicePanel(List<MInvoiceWithAccounts> invoiceList)
		{

			this.invoiceList = invoiceList;

			setLayout(new GridBagLayout());

			this.add(new JLabel("Rechnungsnr"), new GridBagConstraints(0, 0, 1, 1, 0, 0, 10, 0, new Insets(0, 10, 0, 10), 0, 0));
			this.add(new JLabel("Datum"), new GridBagConstraints(1, 0, 1, 1, 0, 0, 10, 0, new Insets(0, 10, 0, 10), 0, 0));
			this.add(new JLabel("GP-Name"), new GridBagConstraints(2, 0, 1, 1, 0, 0, 10, 0, new Insets(0, 10, 0, 10), 0, 0));
			this.add(new JLabel("Betrag"), new GridBagConstraints(3, 0, 1, 1, 0, 0, 10, 0, new Insets(0, 10, 0, 10), 0, 0));
			this.add(new JLabel("Skonto"), new GridBagConstraints(4, 0, 1, 1, 0, 0, 10, 0, new Insets(0, 10, 0, 10), 0, 0));
			this.add(new JLabel("Von"), new GridBagConstraints(5, 0, 1, 1, 0, 0, 10, 0, new Insets(0, 10, 0, 10), 0, 0));
			this.add(new JLabel("Nach"), new GridBagConstraints(6, 0, 1, 1, 0, 0, 10, 0, new Insets(0, 10, 0, 10), 0, 0));
			this.add(new JLabel("Anweisen"), new GridBagConstraints(7, 0, 1, 1, 0, 0, 10, 0, new Insets(0, 10, 0, 10), 0, 0));

			for (int i = 0; i < invoiceList.size(); i++)
			{

				this.add(new JLabel(invoiceList.get(i).getDocumentNo()), new GridBagConstraints(0, i + 1, 1, 1, 0, 0, GridBagConstraints.EAST, 0, new Insets(0, 10, 0, 10), 0, 0));
				this.add(new JLabel("" + invoiceList.get(i).getDateInvoiced().toLocaleString()), new GridBagConstraints(1, i + 1, 1, 1, 0, 0, 10, 0, new Insets(0, 10, 0, 10), 0, 0));
				this.add(new JLabel(invoiceList.get(i).getC_BPartner().getName()), new GridBagConstraints(2, i + 1, 1, 1, 0, 0, GridBagConstraints.WEST, 0, new Insets(0, 10, 0, 10), 0, 0));
				this.add(new JLabel("" + invoiceList.get(i).getOpenAmt()), new GridBagConstraints(3, i + 1, 1, 1, 0, 0, GridBagConstraints.EAST, 0, new Insets(0, 10, 0, 10), 0, 0));
				JCheckBox boxSkonto = new JCheckBox("", invoiceList.get(i).isDiscountAllowed());
				boxSkonto.addActionListener(this);
				if (!invoiceList.get(i).isValid())
				{
					boxSkonto.setEnabled(false);
				}
				checkBoxDiscountList.add(boxSkonto);
				this.add(checkBoxDiscountList.get(i), new GridBagConstraints(4, i + 1, 1, 1, 0, 0, 10, 0, new Insets(0, 10, 0, 10), 0, 0));
				// fill the combobox list
				JComboBox cb1 = new JComboBox(getFromBankAccounts(invoiceList.get(i)));
				if (invoiceList.get(i).getSelectedBankAccountIndex() != 0)
				{
					cb1.setSelectedIndex(invoiceList.get(i).getSelectedBankAccountIndex());
				}
				cb1.addActionListener(this);
				fromComboBoxList.add(cb1);
				this.add(fromComboBoxList.get(i), new GridBagConstraints(5, i + 1, 1, 1, 0, 0, 10, 0, new Insets(0, 10, 0, 10), 0, 0));
				// fill the combobox list
				JComboBox cb2 = new JComboBox(getToBankAccounts(invoiceList.get(i)));
				if (invoiceList.get(i).getSelectedBPBankAccountIndex() != 0)
				{
					cb2.setSelectedIndex(invoiceList.get(i).getSelectedBPBankAccountIndex());
				}
				cb2.addActionListener(this);
				toComboBoxList.add(cb2);
				this.add(toComboBoxList.get(i), new GridBagConstraints(6, i + 1, 1, 1, 0, 0, 10, 0, new Insets(0, 10, 0, 10), 0, 0));
				// fill the checkbox list
				JCheckBox box = new JCheckBox("", invoiceList.get(i).isChecked());
				box.addActionListener(this);
				if (!invoiceList.get(i).isValid())
				{
					box.setEnabled(false);
				}
				checkBoxList.add(box);
				this.add(checkBoxList.get(i), new GridBagConstraints(7, i + 1, 1, 1, 0, 0, 10, 0, new Insets(0, 10, 0, 10), 0, 0));

			}

		}

		private String[] getFromBankAccounts(MInvoiceWithAccounts invoice)
		{
			List<I_C_BP_BankAccount> bankAccountList = invoice.getAccountList();

			String[] bankAccounts = new String[bankAccountList.size()];

			for (int i = 0; i < bankAccountList.size(); i++)
			{
				bankAccounts[i] = Services.get(IBankingBL.class).createBankAccountName(bankAccountList.get(i));
			}

			return bankAccounts;
		}

		private String[] getToBankAccounts(MInvoiceWithAccounts invoice)
		{
			List<MBPBankAccount> bankAccountList = invoice.getBpAccountList();

			String[] bankAccounts = new String[bankAccountList.size()];

			for (int i = 0; i < bankAccountList.size(); i++)
			{
				bankAccounts[i] = bankAccountList.get(i).getAccountNo() + " / " + bankAccountList.get(i).getRoutingNo();
			}

			return bankAccounts;
		}

		private List<MInvoiceWithAccounts> getAllInvoiceToPay()
		{
			List<MInvoiceWithAccounts> payList = new ArrayList<MInvoiceWithAccounts>();
			for (MInvoiceWithAccounts inv : invoiceList)
			{
				if (inv.isChecked)
				{
					payList.add(inv);
				}
			}
			return payList;
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			for (int i = 0; i < checkBoxList.size(); i++)
			{
				if (e.getSource() == checkBoxList.get(i))
				{
					if (invoiceList.get(i).isValid())
					{
						invoiceList.get(i).setChecked(checkBoxList.get(i).isSelected());
						System.out.println(invoiceList.get(i).getDocumentNo() + " " + checkBoxList.get(i).isSelected());
					}
				}
			}
			for (int i = 0; i < fromComboBoxList.size(); i++)
			{
				if (e.getSource() == fromComboBoxList.get(i))
				{
					invoiceList.get(i).setSelectedBankAccountIndex(fromComboBoxList.get(i).getSelectedIndex());
					System.out.println(invoiceList.get(i).getDocumentNo() + " " + fromComboBoxList.get(i).getSelectedIndex());
				}
			}
			for (int i = 0; i < toComboBoxList.size(); i++)
			{
				if (e.getSource() == toComboBoxList.get(i))
				{
					invoiceList.get(i).setSelectedBPBankAccountIndex(toComboBoxList.get(i).getSelectedIndex());
					System.out.println(invoiceList.get(i).getDocumentNo() + " " + toComboBoxList.get(i).getSelectedIndex());
				}
			}
			for (int i = 0; i < checkBoxDiscountList.size(); i++)
			{
				if (e.getSource() == checkBoxDiscountList.get(i))
				{
					invoiceList.get(i).setDiscount(checkBoxDiscountList.get(i).isSelected());
					System.out.println(invoiceList.get(i).getDocumentNo() + " " + checkBoxDiscountList.get(i).isSelected());
				}
			}
		}
	}

	/**
	 * @author Karsten Thiemann, kt@schaeffer-ag.de Invoices with additional fields, to create invoices afterwards
	 */
	class MInvoiceWithAccounts extends MInvoice implements I_C_Invoice
	{

		private List<MBPBankAccount> bpAccountList = new ArrayList<MBPBankAccount>();

		private List<I_C_BP_BankAccount> accountList = new ArrayList<I_C_BP_BankAccount>();

		private int indexFromAccount = 0;

		private int indexToAccount = 0;

		private boolean isChecked = false;
		private boolean isDiscount = false;

		private BigDecimal discountAmt = Env.ZERO;

		public MInvoiceWithAccounts(Properties ctx, int C_Invoice_ID, String trxName)
		{
			super(ctx, C_Invoice_ID, trxName);

			// fill bpAccountList

			int[] baIds = PO.getAllIDs(I_C_BankAccount.Table_Name,
					I_C_BankAccount.COLUMNNAME_IsActive + " = 'Y' AND "
							+ I_C_BankAccount.COLUMNNAME_AD_Client_ID + " = "
							+ Env.getAD_Client_ID(Env.getCtx()), trxName);

			for (int i = 0; i < baIds.length; i++)
			{
				accountList.add(new X_C_BP_BankAccount(Env.getCtx(), baIds[i], trxName));
			}

			int[] bmpbIds = MBPBankAccount
					.getAllIDs(MBPBankAccount.Table_Name, MBPBankAccount.COLUMNNAME_C_BPartner_ID
							+ " = " + this.getC_BPartner_ID() + " AND " + MBPBankAccount.COLUMNNAME_IsACH + " = 'Y' ", trxName);

			for (int i = 0; i < bmpbIds.length; i++)
			{
				bpAccountList.add(new MBPBankAccount(Env.getCtx(), bmpbIds[i], trxName));
			}

			if (this.getC_RecurrentPaymentLine() != null)
			{
				int bankaccountOwnId = this.getC_RecurrentPaymentLine().getC_BP_BankAccount_Own_ID();
				int bpBankaccountId = this.getC_RecurrentPaymentLine().getC_BP_BankAccount_ID();

				for (int i = 0; i < accountList.size(); i++)
				{
					if (accountList.get(i).getC_BP_BankAccount_ID() == bankaccountOwnId)
					{
						indexFromAccount = i;
					}
				}
				for (int i = 0; i < bpAccountList.size(); i++)
				{
					if (bpAccountList.get(i).getC_BP_BankAccount_ID() == bpBankaccountId)
					{
						indexToAccount = i;
					}
				}
			}

			discountAmt = this.getOpenAmt().multiply(this.getC_PaymentTerm().getDiscount().divide(Env.ONEHUNDRED));

			isDiscount = isDiscountAllowed();
		}

		public boolean isValid()
		{
			if (accountList.size() > 0 && bpAccountList.size() > 0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}

		public void setSelectedBankAccountIndex(int index)
		{
			indexFromAccount = index;
		}

		public void setSelectedBPBankAccountIndex(int index)
		{
			indexToAccount = index;
		}

		public int getSelectedBankAccountIndex()
		{
			return indexFromAccount;
		}

		public int getSelectedBPBankAccountIndex()
		{
			return indexToAccount;
		}

		public boolean isChecked()
		{
			return isChecked;
		}

		public void setChecked(boolean isChecked)
		{
			this.isChecked = isChecked;
		}

		public I_C_BP_BankAccount getSelectedBankAccount()
		{
			return accountList.get(indexFromAccount);
		}

		public MBPBankAccount getSelectedBPBankAccount()
		{
			return bpAccountList.get(indexToAccount);
		}

		public List<MBPBankAccount> getBpAccountList()
		{
			return bpAccountList;
		}

		public List<I_C_BP_BankAccount> getAccountList()
		{
			return accountList;
		}

		public boolean isDiscountAllowed()
		{
			if (System.currentTimeMillis() > this.getDateInvoiced().getTime() + this.getC_PaymentTerm().getDiscountDays() * 1000L * 60L * 60L * 24L)
			{
				return false;
			}
			return true;
		}

		public boolean isDiscount()
		{
			return isDiscount;
		}

		public void setDiscount(boolean isDiscount)
		{
			this.isDiscount = isDiscount;
		}

		public BigDecimal getDiscountAmt()
		{
			return discountAmt;
		}

		@Override
		public I_C_RecurrentPaymentLine getC_RecurrentPaymentLine()
				throws RuntimeException
		{

			final int recurrentPaymentLineId = get_ValueAsInt(COLUMNNAME_C_RecurrentPaymentLine_ID);

			if (recurrentPaymentLineId > 0)
			{
				return new MRecurrentPaymentLine(getCtx(),
						recurrentPaymentLineId, get_TrxName());
			}
			return null;
		}

		@Override
		public int getC_RecurrentPaymentLine_ID()
		{

			return get_ValueAsInt(COLUMNNAME_C_RecurrentPaymentLine_ID);
		}

		@Override
		public void setC_RecurrentPaymentLine_ID(final int C_RecurrentPaymentLine_ID)
		{

			set_Value(COLUMNNAME_C_RecurrentPaymentLine_ID,
					C_RecurrentPaymentLine_ID);
		}
	}
}
