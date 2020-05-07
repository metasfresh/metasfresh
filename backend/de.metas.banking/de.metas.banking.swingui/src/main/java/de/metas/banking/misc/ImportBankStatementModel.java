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


import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import org.compiere.util.Env;

import de.metas.banking.misc.ImportBankstatementCtrl.MatchablePO;
import de.schaeffer.compiere.mt940.Bankstatement;
import de.schaeffer.compiere.mt940.BankstatementLine;

public class ImportBankStatementModel {

	public static final int COL_IDX_STMT_DATE = 0;

	public static final int COL_IDX_STMT_AMT = 1;

	public static final int COL_IDX_REASON = 2;

	public static final int COL_IDX_OK = 7;

	public static final int COL_IDX_PAY_AMT = 8;

	public static final int COL_IDX_OVER_UNDER = 11;

	public static final Object[] tableHeader = { "B-Datum", "B-Betrag",
			"Verwendungszweck", "DocNo", "Betrag", "S-Betrag", "Re-Datum",
			"OK", "Zahlbetrag", "Skonto", "Abschreibung", "\u00DC/U-Zahlung" };

	public static final Class<?>[] collumnClasses = { Date.class,
			BigDecimal.class, String.class, String.class, BigDecimal.class,
			BigDecimal.class, Date.class, Boolean.class, BigDecimal.class,
			BigDecimal.class, BigDecimal.class, BigDecimal.class };

	private final Bankstatement statement;

	private boolean[] colors = null;
	private int[] fitting = null;

	private Map<Integer, MatchablePO> row2Ie = new HashMap<Integer, MatchablePO>();

	private Map<Integer, BankstatementLine> row2BankStmtLine = new HashMap<Integer, BankstatementLine>();

	private Map<BankstatementLine, Collection<MatchablePO>> bankStmtLine2Ies = new HashMap<BankstatementLine, Collection<MatchablePO>>();

	/**
	 * List of arrays. Each element is an Array [statement line, List of
	 * matching invoices]
	 */
	private List<Object[]> objectList = null;

	public ImportBankStatementModel(final Bankstatement statement) {

		this.statement = statement;
	}

	public List<Object[]> getObjectList() {
		return objectList;
	}

	/**
	 * 
	 * @param objectList
	 */
	public void setObjectList(List<Object[]> objectList) {

		this.objectList = objectList;

		final int tableRowCount = getTableRowCount();

		// Array um zu bestimmen, welche Zeilen farbig sein sollen
		colors = new boolean[tableRowCount];

		// Array, welches den Grad der �bereinstimmung pro Zeile speichert, um
		// Farben zu definieren
		fitting = new int[tableRowCount];

		boolean color = false;

		final Object[][] tableContent = new Object[tableRowCount][12];

		int rowIdx = 0;

		// f�r jede Zeile des Kontoauszuges
		for (final Object[] a : objectList) {

			color = !color;
			// alle passenden Rechnungen anzeigen
			List<MatchablePO> invoiceExtendedList = (List<MatchablePO>) a[1];
			final BankstatementLine line = (BankstatementLine) a[0];

			bankStmtLine2Ies.put(line, invoiceExtendedList);

			tableContent[rowIdx][COL_IDX_STMT_DATE] = line.getBuchungsdatum();
			tableContent[rowIdx][COL_IDX_STMT_AMT] = line.getBetrag();
			tableContent[rowIdx][COL_IDX_REASON] = line.getVerwendungszweck();

			if (invoiceExtendedList.size() > 0) {

				for (int i = 0; i < invoiceExtendedList.size(); i++) {
					if (i > 0) {
						rowIdx++;
						tableContent[rowIdx][COL_IDX_STMT_DATE] = null;
						tableContent[rowIdx][COL_IDX_STMT_AMT] = BigDecimal.ZERO;
						tableContent[rowIdx][COL_IDX_REASON] = "";
					}

					final MatchablePO invoiceExtended = invoiceExtendedList
							.get(i);
					tableContent[rowIdx][3] = invoiceExtended.getDocumentNo();
					tableContent[rowIdx][4] = invoiceExtended.getOpenAmt();
					BigDecimal gtSkonto = BigDecimal.ZERO;
					try {
						gtSkonto = invoiceExtended.getOpenAmt().subtract(
								invoiceExtended.getOpenAmt().multiply(
										invoiceExtended
												.getPaymentTermDiscount()
												.divide(Env.ONEHUNDRED)))
								.setScale(2, BigDecimal.ROUND_HALF_UP);
					} catch (Exception e) {
						e.printStackTrace();
					}
					tableContent[rowIdx][5] = gtSkonto;
					tableContent[rowIdx][6] = new Date(invoiceExtended
							.getDateDoc().getTime());

					final int fittingLevel = invoiceExtended.getFittingLevel();

					tableContent[rowIdx][COL_IDX_OK] = fittingLevel == 1;

					tableContent[rowIdx][COL_IDX_PAY_AMT] = invoiceExtended
							.getPAmt();
					tableContent[rowIdx][9] = invoiceExtended.getDAmt();
					tableContent[rowIdx][10] = invoiceExtended.getWAmt();
					tableContent[rowIdx][COL_IDX_OVER_UNDER] = invoiceExtended
							.getOUAmt();
					colors[rowIdx] = color;

					fitting[rowIdx] = fittingLevel;

					row2Ie.put(rowIdx, invoiceExtended);
					row2BankStmtLine.put(rowIdx, line);
				}
			} else {
				tableContent[rowIdx][3] = "";
				tableContent[rowIdx][4] = null;
				tableContent[rowIdx][5] = null;
				tableContent[rowIdx][6] = null;
				tableContent[rowIdx][COL_IDX_OK] = null;
				tableContent[rowIdx][COL_IDX_PAY_AMT] = null;
				tableContent[rowIdx][9] = null;
				tableContent[rowIdx][10] = null;
				tableContent[rowIdx][COL_IDX_OVER_UNDER] = null;

				fitting[rowIdx] = 0;
				colors[rowIdx] = color;
				row2BankStmtLine.put(rowIdx, line);
			}
			rowIdx++;
		}
		tableModel.setDataVector(tableContent, tableHeader);
	}

	public int getTableRowCount() {

		// berechnen, wie viele Zeilen die Tabelle ben�tigt
		int tableRows = objectList.size();

		for (final Object[] array : objectList) {

			final List<MatchablePO> invoiceExtendedList = (List<MatchablePO>) array[1];
			if (invoiceExtendedList.size() > 1) {
				tableRows += invoiceExtendedList.size() - 1;
			}
		}
		return tableRows;
	}

	public MatchablePO getIeForRow(final int row) {
		return row2Ie.get(row);
	}

	public BankstatementLine getBnksStmtLineForRow(final int row) {
		return row2BankStmtLine.get(row);
	}

	public Collection<MatchablePO> getIesForBnksStmtLine(
			final BankstatementLine line) {

		return bankStmtLine2Ies.get(line);
	}

	public TableModel getTableModel() {

		return tableModel;
	}

	public boolean isRowColored(final int row) {

		return colors[row];
	}

	public int getRowFitting(final int row) {

		return fitting[row];
	}

	public void setFitting(final int row, final int fitting) {
		this.fitting[row] = fitting;
	}

	public boolean isTrxOk(final int rowIdx) {

		return Boolean.TRUE.equals(tableModel.getValueAt(rowIdx, COL_IDX_OK));
	}

	private final TableModel tableModel = new TableModel();

	public class TableModel extends DefaultTableModel {

		@Override
		public Class<?> getColumnClass(int columnIndex) {

			return ImportBankStatementModel.collumnClasses[columnIndex];
		}

		@Override
		public void setValueAt(Object aValue, int row, int column) {
			super.setValueAt(aValue, row, column);
		}

		/**
		 * 
		 */
		private static final long serialVersionUID = 1894907211149590753L;

		public boolean isCellEditable(int row, int column) {

			// TODO understand and remove setValue

			final int rowCount = getTableRowCount();

			for (final Object[] array : objectList) {

				final List<MatchablePO> matchablePOs = (List<MatchablePO>) array[1];

				for (final MatchablePO iE : matchablePOs) {

					if (iE.getDocumentNo().equals(getDocNo(row))) {

						if (column == 7 && getValueAt(row, column) != null) {

							final Object stmtDate = getValueAt(row,
									COL_IDX_STMT_DATE);

							if (!(stmtDate == null || stmtDate.equals(""))
									&& (iE.getFittingLevel() == 1 || iE
											.getFittingLevel() == 2)) {

								int x = row + 1;

								final Object value = getValueAt(x,
										COL_IDX_STMT_DATE);

								if (x < rowCount && value == null
										|| value.equals("")) {

									while (value == null || value.equals("")) {

										boolean ok = (Boolean) getValueAt(row,
												COL_IDX_OK);
										setValueAt(!ok, x, COL_IDX_OK);
										x++;
									}
								}
								return true;
							}
						}
						if (column > COL_IDX_OK
								&& getValueAt(row, column) != null) {
							return true;
						}
					}
				}
			}
			return false;
		}

		public String getDocNo(final int row) {
			return String.valueOf(getValueAt(row, 3));
		}
	}

	public Bankstatement getStatement() {
		return statement;
	}
}
