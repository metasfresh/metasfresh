package de.metas.banking.misc;

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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.MiscUtils;
import org.compiere.grid.ed.Calendar;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.MAllocationHdr;
import org.compiere.model.MAllocationLine;
import org.compiere.model.MBank;
import org.compiere.model.MBankStatement;
import org.compiere.model.MBankStatementLine;
import org.compiere.model.MPayment;
import org.compiere.model.Query;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Trx;

import de.metas.banking.interfaces.I_C_BankStatementLine_Ref;
import de.metas.banking.misc.ImportBankstatementCtrl.MatchablePO;
import de.metas.banking.model.I_C_BankStatement;
import de.metas.banking.model.I_C_BankStatementLine;
import de.schaeffer.compiere.mt940.Bankstatement;
import de.schaeffer.compiere.mt940.BankstatementLine;

public class BankstatementInvoiceComparisonBSCreate extends JFrame implements
		ActionListener, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7914366946958955830L;

	public static final String CREATE_BUTTON_ENABLED = "createButton.enabled";

	private JPanel choicePanel = null;
	private JPanel buttonPanel = null;
	private JPanel mainPanelNew = null;
	private JPanel mainPanelExist = null;

	private JButton useNewBS = null;
	private JButton useExistingBS = null;
	private JButton createUseExistingBS = null;
	private JButton createNewBS = null;
	private JButton chooseDate = null;

	private JComboBox<String> comboBox = null;
	private JTextField auszugsnummer = null;
	private JTextField datum = null;
	private JLabel name = null;

	private Date date = null;

	private List<MBankStatement> statementList = null;
	private List<I_C_BP_BankAccount> bankAccountList = null;

	private final ImportBankStatementModel model;

	public BankstatementInvoiceComparisonBSCreate(ImportBankStatementModel model) {

		super("Bankauszug waehlen");

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.model = model;

		this.setLocation(600, 400);
		this.setSize(600, 200);
		this.setLayout(new BorderLayout());
		this.add(getJContentPane(), BorderLayout.CENTER);

	}

	private JPanel getJContentPane() {

		if (choicePanel == null) {
			choicePanel = new JPanel();
			choicePanel.setLayout(new BorderLayout());
			choicePanel.add(getButtonPanel(), BorderLayout.NORTH);
		}
		return choicePanel;
	}

	/**
	 * @return Buttonpanel to choose between amend existing or create new
	 *         Bankstatement
	 */
	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridBagLayout());
			GridBagConstraints g1 = new GridBagConstraints();
			g1.gridx = 0;
			g1.gridy = 0;
			GridBagConstraints g2 = new GridBagConstraints();
			g2.gridx = 1;
			g2.gridy = 0;
			useNewBS = new JButton("Neuen Auszug erstellen");
			useExistingBS = new JButton("Existierenden Auszug nutzen");
			useNewBS.addActionListener(this);
			useExistingBS.addActionListener(this);

			buttonPanel.add(useNewBS, g1);
			buttonPanel.add(useExistingBS, g2);
		}
		return buttonPanel;
	}

	/**
	 * Creates a JPanel to create a new bankstatement, with options to choose
	 * bank, statementno etc.
	 * 
	 * @return JPanel
	 */
	private JPanel getMainPanelNew() {
		if (mainPanelNew == null) {
			mainPanelNew = new JPanel();
			mainPanelNew.setLayout(new GridBagLayout());
			GridBagConstraints g1 = new GridBagConstraints();
			g1.gridx = 0;
			g1.gridy = 0;
			g1.anchor = GridBagConstraints.WEST;
			GridBagConstraints g2 = new GridBagConstraints();
			g2.gridx = 0;
			g2.gridy = 1;
			g2.anchor = GridBagConstraints.WEST;
			GridBagConstraints g3 = new GridBagConstraints();
			g3.gridx = 0;
			g3.gridy = 2;
			g3.anchor = GridBagConstraints.WEST;
			GridBagConstraints g4 = new GridBagConstraints();
			g4.gridx = 0;
			g4.gridy = 3;
			g4.anchor = GridBagConstraints.WEST;
			GridBagConstraints g5 = new GridBagConstraints();
			g5.gridx = 1;
			g5.gridy = 0;
			GridBagConstraints g6 = new GridBagConstraints();
			g6.gridx = 1;
			g6.gridy = 1;
			GridBagConstraints g7 = new GridBagConstraints();
			g7.gridx = 1;
			g7.gridy = 2;
			GridBagConstraints g8 = new GridBagConstraints();
			g8.gridx = 1;
			g8.gridy = 3;
			GridBagConstraints g9 = new GridBagConstraints();
			g9.gridx = 2;
			g9.gridy = 2;
			GridBagConstraints g10 = new GridBagConstraints();
			g10.gridx = 1;
			g10.gridy = 4;

			bankAccountList = getAllBankaccounts();
			String[] comboBoxContent = new String[bankAccountList.size()];
			for (int i = 0; i < comboBoxContent.length; i++) {
				comboBoxContent[i] = new MBank(Env.getCtx(), bankAccountList
						.get(i).getC_Bank_ID(), null).getName();
			}

			date = new Date(System.currentTimeMillis());
			comboBox = new JComboBox<>(comboBoxContent);
			auszugsnummer = new JTextField(15);
			datum = new JTextField(15);
			datum.setText(date.toString());
			datum.setEditable(false);
			name = new JLabel("");
			comboBox.addActionListener(this);
			auszugsnummer.addKeyListener(this);
			chooseDate = new JButton("Datum");
			chooseDate.addActionListener(this);
			createNewBS = new JButton("Erzeuge Bankauszug");
			createNewBS.addActionListener(this);

			mainPanelNew.add(new JLabel("Bank"), g1);
			mainPanelNew.add(new JLabel("Auszugsnummer"), g2);
			mainPanelNew.add(new JLabel("Auszugsdatum"), g3);
			mainPanelNew.add(new JLabel("Auszugsname"), g4);
			mainPanelNew.add(comboBox, g5);
			mainPanelNew.add(auszugsnummer, g6);
			mainPanelNew.add(datum, g7);
			mainPanelNew.add(name, g8);
			mainPanelNew.add(chooseDate, g9);
			mainPanelNew.add(createNewBS, g10);
		}
		return mainPanelNew;
	}

	/**
	 * Creates a JPanel to amend a bankstatement (with possibility to choose an
	 * existing, drafted bankstatement)
	 * 
	 * @return JPanel
	 */
	private JPanel getMainPanelExist() {
		if (mainPanelExist == null) {
			mainPanelExist = new JPanel();

			statementList = getOpenBankstatements();

			String[] comboBoxContent = new String[statementList.size()];
			for (int i = 0; i < comboBoxContent.length; i++) {
				comboBoxContent[i] = statementList.get(i).getName();
			}

			comboBox = new JComboBox<>(comboBoxContent);
			mainPanelExist.setLayout(new GridBagLayout());
			GridBagConstraints g1 = new GridBagConstraints();
			g1.gridx = 0;
			g1.gridy = 0;
			GridBagConstraints g2 = new GridBagConstraints();
			g2.gridx = 1;
			g2.gridy = 0;
			createUseExistingBS = new JButton("Ergaenze Bankauszug");
			createUseExistingBS.addActionListener(this);
			mainPanelExist.add(comboBox, g1);
			mainPanelExist.add(createUseExistingBS, g2);
		}
		return mainPanelExist;
	}

	/**
	 * @return List of all Bankaccounts, that belong to the own organisation
	 */
	private List<I_C_BP_BankAccount> getAllBankaccounts() {

		final String whereClause = Env.getUserRolePermissions().getOrgWhere(false);

		final List<I_C_BP_BankAccount> bankAccountList = new Query(Env.getCtx(),
				I_C_BP_BankAccount.Table_Name, whereClause, null).setClient_ID()
				.setOnlyActiveRecords(true).list();

		return bankAccountList;
	}

	/**
	 * @return all drafted bankstatements
	 */
	private List<MBankStatement> getOpenBankstatements() {

		final String whereClause = Env.getUserRolePermissions().getOrgWhere(false)
				+ " AND docstatus = 'DR'";

		final List<MBankStatement> bankStatementList = new Query(Env.getCtx(),
				I_C_BankStatement.Table_Name, whereClause, null).setClient_ID()
				.setOnlyActiveRecords(true).setOrderBy(
						I_C_BankStatement.COLUMNNAME_StatementDate).list();

		return bankStatementList;
	}

	private void createUseNewBS() {
		final Trx trx = Trx.get("BankstatementInvoiceComparisonNewBS", true);

		final List<Object[]> objectList = model.getObjectList();
		final Bankstatement statement = model.getStatement();

		final MBankStatement newBankstatementPO = new MBankStatement(Env
				.getCtx(), 0, trx.getTrxName());
		final I_C_BP_BankAccount selectedBankAccount = bankAccountList.get(comboBox.getSelectedIndex());

		newBankstatementPO.setAD_Org_ID(selectedBankAccount.getAD_Org_ID());

		final I_C_BankStatement newBankstatement = InterfaceWrapperHelper.create(
				newBankstatementPO, I_C_BankStatement.class);

		newBankstatement.setStatementDate(new Timestamp(date.getTime()));
		newBankstatement.setC_BP_BankAccount_ID(selectedBankAccount.getC_BP_BankAccount_ID());
		newBankstatement.setName(name.getText());
		newBankstatement.setDocumentNo(auszugsnummer.getText());

		newBankstatement.setBeginningBalance(statement.getAnfangsSaldo()
				.getBetrag());
		newBankstatement.setEndingBalance(statement.getSchlussSaldo()
				.getBetrag());
		newBankstatementPO.saveEx();

		int lineNo = 0;
		int tableRow = 0;

		for (int i = 0; i < objectList.size(); i++) {

			final MBankStatementLine linePO = new MBankStatementLine(Env
					.getCtx(), 0, trx.getTrxName());
			final I_C_BankStatementLine line = InterfaceWrapperHelper.create(linePO,
					I_C_BankStatementLine.class);

			final BankstatementLine mt940Line = (BankstatementLine) objectList
					.get(i)[0];

			line.setC_BankStatement_ID(newBankstatementPO.get_ID());
			setLineData(lineNo, mt940Line, line);

			final List<MatchablePO> invoiceExtendedList = (List<MatchablePO>) objectList.get(i)[1];

			if (model.isTrxOk(tableRow)) {

				if (invoiceExtendedList.size() > 0) {

					final MPayment payment = new MPayment(Env.getCtx(), 0, trx
							.getTrxName());
					payment.setC_BP_BankAccount_ID(newBankstatement.getC_BP_BankAccount_ID());
					payment.setPayAmt(mt940Line.getBetrag());
					payment.setDateAcct(new Timestamp(mt940Line.getBuchungsdatum().getTime()));
					payment.setDateTrx(new Timestamp(mt940Line
							.getBuchungsdatum().getTime()));
					if (invoiceExtendedList.get(0).isSOTrx()) {
						payment.setC_DocType_ID(true);
					} else {
						payment.setC_DocType_ID(false);
					}
					payment.setTenderType(MPayment.TENDERTYPE_DirectDeposit);
					payment.setC_BPartner_ID(invoiceExtendedList.get(0)
							.getC_BPartner_ID());
					payment.setC_Currency_ID(invoiceExtendedList.get(0)
							.getC_Currency_ID());
					payment.setDiscountAmt(Env.ZERO);
					payment.saveEx();

					MAllocationHdr alloc = new MAllocationHdr(Env.getCtx(),
							false, payment.getDateAcct(), payment
									.getC_Currency_ID(),
							"automated BankstatementComparison Payment", trx
									.getTrxName());
					alloc.setAD_Org_ID(payment.getAD_Org_ID());
					alloc.saveEx();

					int refline = 10;
					for (final MatchablePO matchingPO : invoiceExtendedList) {

						final BigDecimal discount = matchingPO.getDAmt();
						final BigDecimal writeOff = matchingPO.getWAmt();
						final BigDecimal overUnder = matchingPO.getOUAmt();

						if (invoiceExtendedList.size() != 1) {

							createAllocLine(payment, alloc, matchingPO);
						}
						matchingPO.setC_Payment_ID(payment.get_ID());
						matchingPO.saveEx();

						if (invoiceExtendedList.size() == 1) {

							tableRow++;
							payment.setDiscountAmt(discount);
							if (matchingPO.getC_Invoice_ID() > 0) {
								payment.setC_Invoice_ID(matchingPO
										.getC_Invoice_ID());
							}
							if (matchingPO.getC_Order_ID() > 0) {
								payment.setC_Order_ID(matchingPO
										.getC_Order_ID());
							}
							line
									.setC_BPartner_ID(matchingPO
											.getC_BPartner_ID());
							line.setIsMultiplePayment(false);
							line.setIsMultiplePaymentOrInvoice(false);
							line
									.setC_Currency_ID(matchingPO
											.getC_Currency_ID());
							line.setDiscountAmt(discount);
							line.setWriteOffAmt(writeOff);
							line.setOverUnderAmt(overUnder);
							line.setIsOverUnderPayment(line.getOverUnderAmt()
									.compareTo(Env.ZERO) != 0);

							payment.setDiscountAmt(discount);
							payment.setWriteOffAmt(writeOff);
							payment.setOverUnderAmt(overUnder);
							payment
									.setIsOverUnderPayment(payment
											.getOverUnderAmt().compareTo(
													Env.ZERO) != 0);

							alloc.deleteEx(true);
							alloc = null;

							payment.saveEx();

						} else {
							tableRow++;
							line.setIsMultiplePayment(false);
							line.setIsMultiplePaymentOrInvoice(true);

							payment.setDiscountAmt(payment.getDiscountAmt()
									.add(discount));
							payment.setWriteOffAmt(payment.getWriteOffAmt()
									.add(writeOff));
							payment.setIsOverUnderPayment(overUnder
									.compareTo(Env.ZERO) != 0);
							payment.setOverUnderAmt(payment.getOverUnderAmt()
									.add(overUnder));
							line.setDiscountAmt(line.getDiscountAmt().add(
									discount));
							line.setWriteOffAmt(line.getWriteOffAmt().add(
									writeOff));
							line.setOverUnderAmt(line.getOverUnderAmt().add(
									overUnder));
							line.setIsOverUnderPayment(line.getOverUnderAmt()
									.compareTo(Env.ZERO) != 0);

							linePO.saveEx();
							final I_C_BankStatementLine_Ref bslr =
									InterfaceWrapperHelper.newInstance(I_C_BankStatementLine_Ref.class,
											new PlainContextAware(Env.getCtx(), trx.getTrxName()));

							bslr.setC_Payment_ID(payment.get_ID());
							if (matchingPO.getC_Invoice_ID() > 0) 
							{
								bslr.setC_Invoice_ID(matchingPO.getC_Invoice_ID());
							}
							// TODO: Add c_order_id column to bslr?

							bslr.setC_BankStatementLine_ID(line.getC_BankStatementLine_ID());
							bslr.setC_Currency_ID(matchingPO.getC_Currency_ID());
							bslr.setC_BPartner_ID(matchingPO.getC_BPartner_ID());
							bslr.setDiscountAmt(discount);
							bslr.setTrxAmt(matchingPO.getPAmt());
							bslr.setWriteOffAmt(writeOff);
							bslr.setOverUnderAmt(overUnder);
							bslr.setIsOverUnderPayment(bslr.getOverUnderAmt().compareTo(Env.ZERO) != 0);

							bslr.setLine(refline);

							refline += 10;

							InterfaceWrapperHelper.save(bslr);
						}
					}
					completeAlloc(alloc);

					// Switch - Vorzeichenwechsel wegen Invoice vendor (siehe
					// InvoiceExtended inner class ->
					// BankstatementInvoiceComparison)
					if (!payment.isReceipt()) {
						payment.setPayAmt(payment.getPayAmt().negate());
						payment.setDiscountAmt(payment.getDiscountAmt()
								.negate());
						payment.setOverUnderAmt(payment.getOverUnderAmt()
								.negate());
						payment.setWriteOffAmt(payment.getWriteOffAmt()
								.negate());
					}

					linePO.setPayment(payment);
					completePayment(payment);

				} else {
					tableRow++;
					line.setStmtAmt(mt940Line.getBetrag());
					line.setTrxAmt(mt940Line.getBetrag());
				}
				line.setDescription(mt940Line.getPartnerName() + " / "
						+ mt940Line.getVerwendungszweck());

				linePO.saveEx();

			} else {
				if (invoiceExtendedList.size() > 0) {
					tableRow += invoiceExtendedList.size();
				} else {
					tableRow++;
				}
				line.setDescription(mt940Line.getPartnerName() + " / "
						+ mt940Line.getVerwendungszweck());
				line.setStmtAmt(mt940Line.getBetrag());
				line.setTrxAmt(mt940Line.getBetrag());
				linePO.saveEx();
			}
		}

		trx.commit();
		trx.close();
		this.dispose();
	}

	private void completePayment(final MPayment payment) {

		payment.setDocAction(MPayment.ACTION_Complete);

		if (payment.processIt(MPayment.ACTION_Complete)) {
			payment.saveEx();
		} else {
			throw new AdempiereException("Completion failed: " + payment
					+ " Logger: " + MiscUtils.loggerMsgs());
		}
	}

	private void completeAlloc(MAllocationHdr alloc) {
		if (alloc != null) {

			if (alloc.processIt(MAllocationHdr.DOCACTION_Complete)) {
				alloc.saveEx();
			} else {
				throw new AdempiereException("Unable to complete " + alloc
						+ "; Logger: " + MiscUtils.loggerMsgs());
			}
		}
	}

	private void createUseExistBS() {

		final Trx trx = Trx.get("BankstatementInvoiceComparisonExistingBS",
				true);

		final MBankStatement existingBankstatement = statementList.get(comboBox
				.getSelectedIndex());
		int lineNo = existingBankstatement.getLines(true).length * 10;
		int tableRow = 0;

		final List<Object[]> objectList = model.getObjectList();

		for (int i = 0; i < objectList.size(); i++) {

			final BankstatementLine mt940Line = (BankstatementLine) objectList
					.get(i)[0];

			lineNo += 10;

			final MBankStatementLine linePO = new MBankStatementLine(Env
					.getCtx(), 0, trx.getTrxName());
			final I_C_BankStatementLine line = InterfaceWrapperHelper.create(linePO,
					I_C_BankStatementLine.class);

			line.setC_BankStatement_ID(existingBankstatement.get_ID());
			setLineData(lineNo, mt940Line, line);

			final List<MatchablePO> invoiceExtendedList = (List<MatchablePO>) objectList.get(i)[1];

			if (Boolean.TRUE.equals(model.getTableModel().getValueAt(tableRow,
					7))) {

				if (invoiceExtendedList.size() > 0) {

					final Timestamp mt940DateAcct = toTimestamp(mt940Line
							.getBuchungsdatum());

					// create payment and alloction
					final MPayment payment = new MPayment(Env.getCtx(), 0, trx.getTrxName());
					payment.setC_BP_BankAccount_ID(existingBankstatement.getC_BP_BankAccount_ID());
					payment.setPayAmt(mt940Line.getBetrag());
					payment.setDateAcct(mt940DateAcct);
					payment.setDateTrx(mt940DateAcct);
					payment.setTenderType(MPayment.TENDERTYPE_DirectDeposit);
					payment.setC_BPartner_ID(invoiceExtendedList.get(0)
							.getC_BPartner_ID());
					payment.setC_Currency_ID(invoiceExtendedList.get(0)
							.getC_Currency_ID());
					payment.setDiscountAmt(Env.ZERO);
					payment.saveEx();

					final MAllocationHdr alloc = new MAllocationHdr(Env
							.getCtx(), false, payment.getDateAcct(), payment
							.getC_Currency_ID(),
							"automated BankstatementComparison Payment", trx
									.getTrxName());
					alloc.setAD_Org_ID(payment.getAD_Org_ID());
					alloc.saveEx();

					int refline = 10;

					for (final MatchablePO matchingPO : invoiceExtendedList) {

						final BigDecimal discount = matchingPO.getDAmt();
						final BigDecimal writeOff = matchingPO.getWAmt();
						final BigDecimal overUnder = matchingPO.getOUAmt();

						if (invoiceExtendedList.size() != 1) {

							createAllocLine(payment, alloc, matchingPO);
						}

						matchingPO.setC_Payment_ID(payment.get_ID());
						matchingPO.saveEx();

						if (invoiceExtendedList.size() == 1) {

							tableRow++;
							payment.setDiscountAmt(discount);
							if (matchingPO.getC_Invoice_ID() > 0) 
							{
								payment.setC_Invoice_ID(matchingPO.getC_Invoice_ID());
								line.setC_Invoice_ID(matchingPO.getC_Invoice_ID());
							}
							if (matchingPO.getC_Order_ID() > 0) 
							{
								payment.setC_Order_ID(matchingPO.getC_Order_ID());
							}

							line.setC_BPartner_ID(matchingPO.getC_BPartner_ID());
							line.setIsMultiplePayment(false);
							line.setIsMultiplePaymentOrInvoice(false);
							line.setC_Currency_ID(matchingPO.getC_Currency_ID());
							line.setDiscountAmt(discount);
							line.setWriteOffAmt(writeOff);
							line.setOverUnderAmt(overUnder);
							line.setIsOverUnderPayment(line.getOverUnderAmt().signum() != 0);

							payment.setDiscountAmt(discount);
							payment.setWriteOffAmt(writeOff);
							payment.setOverUnderAmt(overUnder);
							payment.setIsOverUnderPayment(payment.getOverUnderAmt().signum() != 0);

							alloc.deleteEx(true);
							payment.saveEx();

						} else {

							tableRow++;
							line.setIsMultiplePayment(false);
							line.setIsMultiplePaymentOrInvoice(true);

							payment.setDiscountAmt(payment.getDiscountAmt().add(discount));
							payment.setWriteOffAmt(payment.getWriteOffAmt().add(writeOff));
							payment.setIsOverUnderPayment(overUnder.compareTo(Env.ZERO) != 0);
							payment.setOverUnderAmt(payment.getOverUnderAmt().add(overUnder));

							line.setDiscountAmt(line.getDiscountAmt().add(discount));
							line.setWriteOffAmt(line.getWriteOffAmt().add(writeOff));
							line.setOverUnderAmt(line.getOverUnderAmt().add(overUnder));
							line.setIsOverUnderPayment(line.getOverUnderAmt().signum() != 0);

							linePO.saveEx();

							final I_C_BankStatementLine_Ref bslr =
									InterfaceWrapperHelper.newInstance(I_C_BankStatementLine_Ref.class,
											new PlainContextAware(Env.getCtx(), trx.getTrxName()));

							bslr.setC_Payment_ID(payment.get_ID());
							if (matchingPO.getC_Invoice_ID() > 0) {
								bslr.setC_Invoice_ID(matchingPO
										.getC_Invoice_ID());
							}
							// TODO: Add c_order_id column to bslr?

							bslr.setC_BankStatementLine_ID(line
									.getC_BankStatementLine_ID());
							bslr
									.setC_Currency_ID(matchingPO
											.getC_Currency_ID());
							bslr
									.setC_BPartner_ID(matchingPO
											.getC_BPartner_ID());
							bslr.setDiscountAmt(discount);
							bslr.setTrxAmt(matchingPO.getPAmt());
							bslr.setWriteOffAmt(writeOff);
							bslr.setOverUnderAmt(overUnder);
							bslr.setIsOverUnderPayment(bslr.getOverUnderAmt()
									.compareTo(Env.ZERO) != 0);

							bslr.setLine(refline);

							refline += 10;

							InterfaceWrapperHelper.save(bslr, trx.getTrxName());

						}
					}
					completeAlloc(alloc);

					linePO.setPayment(payment);

					completePayment(payment);

				} else {
					tableRow++;
					line.setStmtAmt(mt940Line.getBetrag());
					line.setTrxAmt(mt940Line.getBetrag());
				}
				line.setDescription(mt940Line.getPartnerName() + " / "
						+ mt940Line.getVerwendungszweck());

				linePO.saveEx();

			} else {
				if (invoiceExtendedList.size() > 0) {
					tableRow += invoiceExtendedList.size();
				} else {
					tableRow++;
				}
				line.setDescription(mt940Line.getPartnerName() + " / "
						+ mt940Line.getVerwendungszweck());
				line.setStmtAmt(mt940Line.getBetrag());
				line.setTrxAmt(mt940Line.getBetrag());
				linePO.saveEx();

			}
		}

		trx.commit();
		trx.close();
		this.dispose();
	}

	private void createAllocLine(final MPayment payment,
			final MAllocationHdr alloc, final MatchablePO matchingPO) {

		final BigDecimal discount = matchingPO.getDAmt();
		final BigDecimal writeOff = matchingPO.getWAmt();
		final BigDecimal overUnder = matchingPO.getOUAmt();

		final MAllocationLine aLine = new MAllocationLine(alloc);

		aLine.setAmount(matchingPO.getPAmt());
		aLine.setDiscountAmt(discount);
		aLine.setWriteOffAmt(writeOff);
		aLine.setOverUnderAmt(overUnder);
		aLine.setDocInfo(matchingPO.getC_BPartner_ID(), matchingPO
				.getC_Order_ID(), matchingPO.getC_Invoice_ID());
		aLine.setC_Payment_ID(payment.get_ID());
		
		aLine.saveEx();
	}

	private void setLineData(int lineNo, final BankstatementLine mt940Line,
			final I_C_BankStatementLine line) {

		final Timestamp mt940DateAcct = toTimestamp(mt940Line
				.getBuchungsdatum());

		line.setLine(lineNo);
		line.setStatementLineDate(mt940DateAcct);
		line.setDateAcct(mt940DateAcct);
		line.setValutaDate(toTimestamp(mt940Line.getValuta()));
		line.setC_Currency_ID(bankAccountList.get(comboBox.getSelectedIndex())
				.getC_Currency_ID());
		line.setDiscountAmt(Env.ZERO);

		line.setEftCheckNo(model.getStatement().getAuszugsNr().toString());

		line.setEftAmt(mt940Line.getBetrag());
		line.setEftCurrency(mt940Line.getWaehrung());
		line.setEftMemo(mt940Line.getBuchungstext());
		line.setEftStatementLineDate(mt940DateAcct);
		line.setEftTrxID(mt940Line.getReferenz());
		line.setEftTrxType(mt940Line.getBuchungsschluessel() + " - "
				+ mt940Line.getGeschaeftsvorfallCode().toString());
		line.setEftPayee(mt940Line.getPartnerName());
		line.setEftPayeeAccount(mt940Line.getPartnerBlz() + " / "
				+ mt940Line.getPartnerKtoNr());
		line.setEftValutaDate(toTimestamp(mt940Line.getValuta()));

	}

	private Timestamp toTimestamp(final Date tate) {
		return new Timestamp(tate.getTime());
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == useNewBS) {
			choicePanel.removeAll();
			comboBox = null;
			mainPanelNew = null;
			mainPanelExist = null;
			buttonPanel = null;
			choicePanel.add(getButtonPanel(), BorderLayout.NORTH);
			choicePanel.add(getMainPanelNew(), BorderLayout.CENTER);
			choicePanel.validate();
		} else if (e.getSource() == useExistingBS) {
			choicePanel.removeAll();
			comboBox = null;
			mainPanelNew = null;
			mainPanelExist = null;
			buttonPanel = null;
			choicePanel.add(getButtonPanel(), BorderLayout.NORTH);
			choicePanel.add(getMainPanelExist(), BorderLayout.CENTER);
			choicePanel.validate();
		} else if (e.getSource() == comboBox) {
			name.setText(comboBox.getSelectedItem() + " "
					+ auszugsnummer.getText() + "-"
					+ datum.getText().substring(0, 4));
		} else if (e.getSource() == chooseDate) {
			
			final Timestamp datePicked = Calendar.builder()
					.setParentComponent(chooseDate)
					.setDialogTitle("Datum")
					.setDisplayType(DisplayType.Date)
					.setDate(new Timestamp(System.currentTimeMillis()))
					.buildAndGet();
			date = new Date(datePicked.getTime());
			datum.setText(date.toString());
			name.setText(comboBox.getSelectedItem() + " " + auszugsnummer.getText() + "-" + datum.getText().substring(0, 4));
		} else if (e.getSource() == createNewBS
				&& auszugsnummer.getText() != null
				&& !"".equals(auszugsnummer.getText())) {
			createUseNewBS();
			propertyChangeSupport.firePropertyChange(CREATE_BUTTON_ENABLED,
					true, false);

		} else if (e.getSource() == createUseExistingBS
				&& auszugsnummer.getText() != null
				&& !"".equals(auszugsnummer.getText())) {
			createUseExistBS();
			propertyChangeSupport.firePropertyChange(CREATE_BUTTON_ENABLED,
					false, true);
		}
	}

	final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

	public void addCreateButtonListener(final PropertyChangeListener l) {
		propertyChangeSupport.addPropertyChangeListener(l);
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getSource() == auszugsnummer) {
			auszugsnummer
					.setText(auszugsnummer.getText().replaceAll("\\D", ""));
			name.setText(comboBox.getSelectedItem() + " "
					+ auszugsnummer.getText() + "-"
					+ datum.getText().substring(0, 4));
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}