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


import static de.metas.banking.misc.ImportBankStatementModel.COL_IDX_OVER_UNDER;
import static de.metas.banking.misc.ImportBankStatementModel.COL_IDX_PAY_AMT;
import static de.metas.banking.misc.ImportBankStatementModel.COL_IDX_STMT_AMT;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.MInvoice;
import org.compiere.model.MOrder;
import org.compiere.model.MPaymentTerm;
import org.compiere.model.PO;
import org.compiere.util.Env;

import de.metas.adempiere.model.I_C_Order;
import de.metas.util.Check;
import de.schaeffer.compiere.mt940.Bankstatement;
import de.schaeffer.compiere.mt940.BankstatementLine;

public class ImportBankstatementCtrl {

	/** the logger */
	// private Logger logger =
	// CLogMgt.getLogger(ImportBankstatementCtrl.class);

	public ImportBankstatementCtrl(final Bankstatement statement,
			final List<MInvoice> invoiceList, final List<MOrder> orderList) {

		final ImportBankStatementModel model = new ImportBankStatementModel(
				statement);

		// Erzeugen einer Liste aus Object[2]
		// --> [0] = Line aus Ueberweisung / [1] = List<Invoices> die passen
		// koennten
		final List<Object[]> objectList = findFittingLines(statement,
				invoiceList, orderList);
		model.setObjectList(objectList);

		model.getTableModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(final TableModelEvent e) {

				final int row = e.getFirstRow();
				final MatchablePO iE = model.getIeForRow(row);
				if (iE == null) {
					return;
				}
				handleTableChange(model, row, iE);
			}
		});

		final ImportBankStatementView view = new ImportBankStatementView(model);

		view.addCreatButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				final BankstatementInvoiceComparisonBSCreate bsicc = new BankstatementInvoiceComparisonBSCreate(
						model);

				bsicc.addCreateButtonListener(new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						if (BankstatementInvoiceComparisonBSCreate.CREATE_BUTTON_ENABLED
								.equals(evt.getPropertyName())) {

							view.setCreateButtonEnabled((Boolean) evt
									.getNewValue());
						}

					}
				});

				bsicc.setVisible(true);
			}
		});
		view.setVisible(true);
	}

	/**
	 * Creates List of objectarrays[2]: object[0] = MT940-Bankstatementline
	 * object[1] = Invoices, that seem to fit for object[0]
	 *
	 * @return
	 */
	private List<Object[]> findFittingLines(final Bankstatement statement,
			final List<MInvoice> invoiceList, final List<MOrder> orderList) {

		final List<Object[]> fittingInvoicesList = new ArrayList<Object[]>();

		final List<MatchablePO> allMatchablePOs = new ArrayList<MatchablePO>();

		for (final PO po : invoiceList) {
			allMatchablePOs.add(new MatchablePO(po));
		}
		for (final PO po : orderList) {
			allMatchablePOs.add(new MatchablePO(po));
		}

		for (final BankstatementLine line : statement.getLines()) {

			final Object[] objectArray = new Object[2];
			objectArray[0] = line;

			final List<MatchablePO> matchingPOs = new ArrayList<MatchablePO>();

			for (final MatchablePO poToMatch : allMatchablePOs) {

				if (!lineMatchesInv(line, poToMatch)) {
					continue;
				}

				matchingPOs.add(poToMatch);

				// create an InvoiceExtended for this invoice and add it to
				// the list for this line.

				final BigDecimal paymentAmount = poToMatch.getOpenAmt();

				final BigDecimal invoiceDiscount = getInvoiceDiscount(poToMatch);

				final BigDecimal paymentAmountSkonto = (paymentAmount
						.subtract(paymentAmount.multiply((invoiceDiscount))))
						.setScale(2, BigDecimal.ROUND_HALF_UP);

				// Stimmt der Betrag �berein?
				final long discountMillis = mkDiscountMillis(poToMatch);

				final boolean withinDiscountTime = line.getBuchungsdatum()
						.getTime()
						- poToMatch.getDateDoc().getTime() < discountMillis;

				if (line.getBetrag().compareTo(paymentAmount) == 0
						|| (line.getBetrag().compareTo(paymentAmountSkonto) == 0 && withinDiscountTime)) {

					// Rechnungsnummer, Betrag stimmen
					poToMatch.setFittingLevel(1);
					if (line.getBetrag().compareTo(paymentAmount) == 0) {
						poToMatch.setPAmt(paymentAmount);
					} else {
						poToMatch.setPAmt(paymentAmountSkonto);
						poToMatch.setDAmt(paymentAmount
								.subtract(paymentAmountSkonto));
					}

				} else if (line.getBetrag().compareTo(paymentAmountSkonto) == 0) {

					// Rechnungsnummer stimmt, aber Skonto trotz Verzug
					if (paymentAmount.subtract(paymentAmountSkonto).compareTo(
							new BigDecimal("5")) < 0) {
						poToMatch.setFittingLevel(1);
					} else {
						poToMatch.setFittingLevel(2);
					}

					poToMatch.setPAmt(paymentAmountSkonto);
					poToMatch.setDAmt(paymentAmount
							.subtract(paymentAmountSkonto));

				} else {
					// Rechnungsnummer stimmt
					poToMatch.setFittingLevel(3);
					poToMatch.setPAmt(line.getBetrag());
				}

				break;
			}

			BigDecimal fullPayment = Env.ZERO;

			// find out if the invoices for the current line together match the
			// line amount
			for (final MatchablePO iE : allMatchablePOs) {

				final BigDecimal paymentAmount = iE.getOpenAmt();
				final BigDecimal invoiceDiscount = getInvoiceDiscount(iE);

				final BigDecimal paymentAmountSkonto = paymentAmount.subtract(
						paymentAmount.multiply(invoiceDiscount)).setScale(2,
						BigDecimal.ROUND_HALF_UP);

				if (line.getBuchungsdatum().getTime()
						- iE.getDateDoc().getTime() < mkDiscountMillis(iE)) {

					fullPayment = fullPayment.add(paymentAmountSkonto);
					iE.setPAmt(paymentAmountSkonto);
					iE.setDAmt(paymentAmount.subtract(paymentAmountSkonto));

				} else {
					fullPayment = fullPayment.add(paymentAmount);
					iE.setPAmt(paymentAmount);
				}
			}
			if (fullPayment.compareTo(line.getBetrag()) == 0) {
				for (final MatchablePO iE : allMatchablePOs) {
					iE.setFittingLevel(1);
					matchingPOs.add(iE);
				}
			}

			for (final MatchablePO ie : allMatchablePOs) {

				final BigDecimal invoiceDiscount = getInvoiceDiscount(ie);

				if (line.getBuchungsdatum().getTime()
						- ie.getDateDoc().getTime() < mkDiscountMillis(ie)) {
					ie.setIpAmt(ie.getOpenAmt().subtract(
							ie.getOpenAmt().multiply((invoiceDiscount)))
							.setScale(2, BigDecimal.ROUND_HALF_UP));
				} else {
					ie.setIpAmt(ie.getOpenAmt());
				}
			}

			Collections.sort(matchingPOs);
			objectArray[1] = matchingPOs;
			fittingInvoicesList.add(objectArray);
		}
		return fittingInvoicesList;
	}

	private long mkDiscountMillis(final MatchablePO ie) {
		return ie.getDiscountDays() * 24L * 60L * 60L * 1000L;
	}

	private BigDecimal getInvoiceDiscount(final MatchablePO invoice) {

		return invoice.getPaymentTermDiscount().divide(Env.ONEHUNDRED);
	}

	private boolean lineMatchesInv(final BankstatementLine line,
			final MatchablePO poToMatch) {

		// Ist die Rechnungsnummer vorhanden?
		final String trxReason = line.getVerwendungszweck();
		if (Check.isEmpty(trxReason)) {
			// nothing to match
			return false;
		}
		final String trxReasonSearch = "x" + trxReason.replaceAll(" ", "")
				+ "x";

		final String documentNo = poToMatch.getDocumentNo();

		final boolean poReferenceMatch;

		final boolean alsoUsePoReference = false;

		if (alsoUsePoReference) {
			// final String poReference = invoice.getPOReference();
			// poReferenceMatch = !Util.isEmpty(poReference)
			// && trxReasonSearch.split(poReference).length >= 2;
		} else {
			poReferenceMatch = alsoUsePoReference;
		}

		final boolean reasonMatchesInvoice = trxReason != null
				&& (trxReasonSearch.split(documentNo).length >= 2 || poReferenceMatch);

		return reasonMatchesInvoice;
	}

	private void handleTableChange(final ImportBankStatementModel model,
			final int row, final MatchablePO iE) {

		final TableModel tableModel = model.getTableModel();

		iE.setPAmt(new BigDecimal(tableModel.getValueAt(row, COL_IDX_PAY_AMT)
				.toString().replaceAll(",", ".")));
		iE.setDAmt(new BigDecimal(tableModel.getValueAt(row, 9).toString()
				.replaceAll(",", ".")));
		iE.setWAmt(new BigDecimal(tableModel.getValueAt(row, 10).toString()
				.replaceAll(",", ".")));

		final BigDecimal amtOverUnderPayment = (BigDecimal) tableModel
				.getValueAt(row, COL_IDX_OVER_UNDER);
		iE.setOUAmt(amtOverUnderPayment);

		// Berechnung nur f�r die Hauptzeile durchf�hren, falls mehrere
		// Rechnungen verkn�pft sind
		// if (getValueAt(row, 1) != null && !"".equals(getValueAt(row, 1))) {

		BigDecimal pTotal = Env.ZERO;
		BigDecimal dTotal = Env.ZERO;
		BigDecimal wTotal = Env.ZERO;
		BigDecimal ouTotal = Env.ZERO;
		BigDecimal ipTotal = Env.ZERO; // Initial Payment Amount

		BigDecimal grandTotal = Env.ZERO;
		BigDecimal paySkontoAmtTotal = Env.ZERO;

		// Die Summen ergeben die richtigen Betr�ge
		boolean sumIsValid = false;
		// Die Summen ergeben die richtigen Betr�ge und stimmen mit den
		// Zahlungsbedingungen �berein
		boolean sumIsValidAndCorrect = false;

		final BankstatementLine bankStmtLine = model.getBnksStmtLineForRow(row);

		// alle einer Zeile zugeordneten Rechnungen durchgehen und Betr�ge
		// zusammenfassen
		final Collection<de.metas.banking.misc.ImportBankstatementCtrl.MatchablePO> invoiceExtendedList = model
				.getIesForBnksStmtLine(bankStmtLine);

		for (final MatchablePO invoiceExtended : invoiceExtendedList) {

			grandTotal = grandTotal.add(invoiceExtended.getOpenAmt());

			pTotal = pTotal.add(invoiceExtended.getPAmt());
			dTotal = dTotal.add(invoiceExtended.getDAmt());
			wTotal = wTotal.add(invoiceExtended.getWAmt());
			ouTotal = ouTotal.add(invoiceExtended.getOUAmt());
			ipTotal = ipTotal.add(invoiceExtended.getIpAmt());

			paySkontoAmtTotal = paySkontoAmtTotal.add(
					invoiceExtended.getOpenAmt().subtract(
							invoiceExtended.getOpenAmt().multiply(
									(invoiceExtended.getPaymentTermDiscount()
											.divide(Env.ONEHUNDRED)))))
					.setScale(2, BigDecimal.ROUND_HALF_UP);

		}

		final Object stmtAmt = model.getTableModel().getValueAt(row,
				COL_IDX_STMT_AMT);

		if (stmtAmt != null && !"".equals(stmtAmt)) {

			// stimmt Buchungsbetrag mit Summe aller Zahlfelder �berein
			BigDecimal buchungsbetrag = new BigDecimal(stmtAmt.toString());
			int compareResult = buchungsbetrag.compareTo(ipTotal);

			if (compareResult == 0) {
				sumIsValidAndCorrect = true;
				sumIsValid = true;

				int counter = 0;
				boolean original = true;

				// TODO: bedeutung unklar

				// for (InvoiceExtended invoiceExtended : invoiceExtendedList) {
				// if (new BigDecimal(tableContentFinal[row + counter][8]
				// .toString()).compareTo(invoiceExtended.getPAmt()) != 0
				// || new BigDecimal(
				// tableContentFinal[row + counter][9]
				// .toString())
				// .compareTo(invoiceExtended.getDAmt()) != 0
				// || new BigDecimal(
				// tableContentFinal[row + counter][10]
				// .toString())
				// .compareTo(invoiceExtended.getWAmt()) != 0
				// || new BigDecimal(
				// tableContentFinal[row + counter][11]
				// .toString())
				// .compareTo(invoiceExtended.getOUAmt()) != 0) {
				//
				// original = false;
				// sumIsValidAndCorrect = false;
				// sumIsValid = false;
				// break;
				// }
				// counter++;
				// }

				if (!original) {
					if (buchungsbetrag.compareTo(pTotal) > 0) {
						if (buchungsbetrag.subtract(pTotal).compareTo(
								wTotal.negate().add(ouTotal.negate())) == 0) {
							sumIsValid = true;
							for (MatchablePO invoiceExtended : invoiceExtendedList) {
								if (invoiceExtended.getOpenAmt().compareTo(
										invoiceExtended.getPAmt().add(
												invoiceExtended.getDAmt())) != 0) {
									sumIsValid = false;
									break;
								}
							}
						}
					} else {
						sumIsValid = true;
						for (MatchablePO invoiceExtended : invoiceExtendedList) {
							if (invoiceExtended.getOpenAmt().compareTo(
									invoiceExtended.getPAmt().add(
											invoiceExtended.getDAmt()).add(
											invoiceExtended.getWAmt()).add(
											invoiceExtended.getOUAmt())) != 0
									|| buchungsbetrag.compareTo(pTotal) != 0) {
								sumIsValid = false;
								break;
							}
						}
					}
				}

			} else if (compareResult > 0) {
				// �berzahlung
				if (buchungsbetrag.subtract(pTotal).compareTo(
						wTotal.negate().add(ouTotal.negate())) == 0) {
					sumIsValid = true;
					for (MatchablePO invoiceExtended : invoiceExtendedList) {
						if (invoiceExtended.getOpenAmt().compareTo(
								invoiceExtended.getPAmt().add(
										invoiceExtended.getDAmt())) != 0) {
							sumIsValid = false;
							break;
						}
					}
				}
			} else {
				// Unterzahlung
				sumIsValid = true;
				for (MatchablePO invoiceExtended : invoiceExtendedList) {
					if (invoiceExtended.getOpenAmt().compareTo(
							invoiceExtended.getPAmt().add(
									invoiceExtended.getDAmt()).add(
									invoiceExtended.getWAmt()).add(
									invoiceExtended.getOUAmt())) != 0
							|| buchungsbetrag.compareTo(pTotal) != 0) {
						sumIsValid = false;
						break;
					}
				}
			}

			// Summe ist korrekt und stimmt mit Zahlungsbedingungen �berein,
			// alle Felder gr�n
			if (sumIsValid && sumIsValidAndCorrect) {
				int counter = 0;
				for (MatchablePO invoiceExtended : invoiceExtendedList) {

					model.setFitting(row + counter, 1);
					invoiceExtended.setFittingLevel(1);
					counter++;
				}
				// Summe ist korrekt, alle Felder gelb
			} else if (sumIsValid) {
				int counter = 0;
				for (final MatchablePO invoiceExtended : invoiceExtendedList) {

					model.setFitting(row + counter, 2);
					invoiceExtended.setFittingLevel(2);
					counter++;
				}
				// Summen sind nicht korrekt, alle Felder rot
			} else {
				int counter = 0;
				for (final MatchablePO invoiceExtended : invoiceExtendedList) {

					model.setFitting(row + counter, 3);
					invoiceExtended.setFittingLevel(3);
					counter++;
				}
			}
		}
	}

	// Rechnungsobjekt mit zusaetzlichem Parameter (Grad der Uebereinstimmung)
	public class MatchablePO implements Comparable<MatchablePO> {

		/**
		 *
		 */
		private static final long serialVersionUID = 131207185558136160L;

		private final PO poToMatch;

		private final boolean soTrx;

		public MatchablePO(final PO poToMatch) {

			if (!(poToMatch instanceof MInvoice)
					&& !(poToMatch instanceof MOrder)) {
				throw new IllegalArgumentException(poToMatch
						+ " must be a MOrder or MInvoice");
			}

			this.poToMatch = poToMatch;

			final Boolean isSOTrx = (Boolean) poToMatch
					.get_Value(I_C_Order.COLUMNNAME_IsSOTrx);
			if (isSOTrx == null) {
				throw new IllegalArgumentException(poToMatch
						+ " lacks a value for " + I_C_Order.COLUMNNAME_IsSOTrx);
			}
			soTrx = isSOTrx;
		}

		@Cached
		private MPaymentTerm retrievePaymentTerm(final PO poToMatch) {

			final Integer paymentTermId = (Integer) poToMatch
					.get_Value(I_C_Order.COLUMNNAME_C_PaymentTerm_ID);
			if (paymentTermId == null) {
				throw new IllegalArgumentException(poToMatch
						+ " lacks a value for "
						+ I_C_Order.COLUMNNAME_C_PaymentTerm_ID);
			}

			final MPaymentTerm paymentTerm = new MPaymentTerm(poToMatch
					.getCtx(), paymentTermId, poToMatch.get_TrxName());

			return paymentTerm;
		}

		private int fittingLevel = 0;
		private BigDecimal ipAmt = Env.ZERO;
		private BigDecimal pAmt = Env.ZERO;
		private BigDecimal dAmt = Env.ZERO;
		private BigDecimal wAmt = Env.ZERO;
		private BigDecimal oUAmt = Env.ZERO;

		public BigDecimal getOpenAmt() {

			final BigDecimal amt;
			if (poToMatch instanceof MInvoice) {
				amt = ((MInvoice) poToMatch).getOpenAmt();
			} else if (poToMatch instanceof MOrder) {

				amt = ((MOrder) poToMatch).getTotalLines();

			} else {
				amt = BigDecimal.ZERO;
			}

			if (soTrx) {
				return amt;
			} else {
				return amt.negate();
			}
		}

		public int getDiscountDays() {

			return retrievePaymentTerm(poToMatch).getDiscountDays();
		}

		public Timestamp getDateDoc() {

			if (poToMatch instanceof MInvoice) {
				return ((MInvoice) poToMatch).getDateInvoiced();

			} else if (poToMatch instanceof MOrder) {

				return ((MOrder) poToMatch).getDateOrdered();
			}
			throw new AdempiereException(poToMatch
					+ " must be a MInvoice or MOrder");
		}

		public BigDecimal getPaymentTermDiscount() {
			return retrievePaymentTerm(poToMatch).getDiscount();
		}

		public void setFittingLevel(int fittingLevel) {
			this.fittingLevel = fittingLevel;
		}

		public int getFittingLevel() {
			return fittingLevel;
		}

		public BigDecimal getPAmt() {
			return pAmt;
		}

		public void setPAmt(BigDecimal amt) {
			pAmt = amt;
		}

		public BigDecimal getDAmt() {
			return dAmt;
		}

		public void setDAmt(BigDecimal amt) {
			dAmt = amt;
		}

		public BigDecimal getWAmt() {
			return wAmt;
		}

		public void setWAmt(BigDecimal amt) {
			wAmt = amt;
		}

		public BigDecimal getOUAmt() {
			return oUAmt;
		}

		public void setOUAmt(BigDecimal amt) {
			oUAmt = amt;
		}

		public BigDecimal getIpAmt() {
			return ipAmt;
		}

		public void setIpAmt(BigDecimal ipAmt) {
			this.ipAmt = ipAmt;
		}

		// Sortiert die Liste nach Grad der Uebereinstimmung
		@Override
		public int compareTo(MatchablePO o) {

			if (getFittingLevel() == o.getFittingLevel()) {
				return 0;
			} else if (getFittingLevel() > o.getFittingLevel()) {
				return 1;
			} else {
				return -1;
			}
		}

		public String getDocumentNo() {

			if (poToMatch instanceof MInvoice) {

				return ((MInvoice) poToMatch).getDocumentNo();

			} else if (poToMatch instanceof MOrder) {

				return ((MOrder) poToMatch).getDocumentNo();
			}
			throw new AdempiereException(poToMatch
					+ " must be a MInvoice or MOrder");
		}

		public boolean isSOTrx() {
			return soTrx;
		}

		public int getC_BPartner_ID() {
			if (poToMatch instanceof MInvoice) {

				return ((MInvoice) poToMatch).getC_BPartner_ID();

			} else if (poToMatch instanceof MOrder) {

				return ((MOrder) poToMatch).getBill_BPartner_ID();
			}
			throw new AdempiereException(poToMatch
					+ " must be a MInvoice or MOrder");
		}

		public int getC_Currency_ID() {

			if (poToMatch instanceof MInvoice) {

				return ((MInvoice) poToMatch).getC_Currency_ID();

			} else if (poToMatch instanceof MOrder) {

				return ((MOrder) poToMatch).getC_Currency_ID();
			}
			throw new AdempiereException(poToMatch
					+ " must be a MInvoice or MOrder");
		}

		public int getC_Order_ID() {
			if (poToMatch instanceof MInvoice) {

				return ((MInvoice) poToMatch).getC_Order_ID();

			} else if (poToMatch instanceof MOrder) {

				return ((MOrder) poToMatch).getC_Order_ID();
			}
			throw new AdempiereException(poToMatch
					+ " must be a MInvoice or MOrder");
		}

		public int getC_Invoice_ID() {
			if (poToMatch instanceof MInvoice) {

				return ((MInvoice) poToMatch).getC_Invoice_ID();

			} else if (poToMatch instanceof MOrder) {

				return 0;
			}
			throw new AdempiereException(poToMatch
					+ " must be a MInvoice or MOrder");
		}

		public void setC_Payment_ID(int paymentId) {

			if (poToMatch instanceof MInvoice) {

				((MInvoice) poToMatch).setC_Payment_ID(paymentId);

			} else if (poToMatch instanceof MOrder) {

				((MOrder) poToMatch).setC_Payment_ID(paymentId);
			} else {
				throw new AdempiereException(poToMatch
						+ " must be a MInvoice or MOrder");
			}
		}

		public void saveEx() {
			poToMatch.saveEx();
		}
	}
}
