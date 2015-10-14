package de.metas.banking.misc;

/*
 * #%L
 * de.metas.banking.base
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


import static de.metas.banking.misc.ImportBankStatementModel.COL_IDX_OK;
import static de.metas.banking.misc.ImportBankStatementModel.COL_IDX_OVER_UNDER;
import static de.metas.banking.misc.ImportBankStatementModel.COL_IDX_PAY_AMT;
import static de.metas.banking.misc.ImportBankStatementModel.COL_IDX_REASON;
import static de.metas.banking.misc.ImportBankStatementModel.COL_IDX_STMT_DATE;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import de.schaeffer.compiere.tools.TableHelper;

public class ImportBankStatementView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1754516184703634390L;

	private JButton createButton = null;

	private JTable table = null;

	private final ImportBankStatementModel model;

	public ImportBankStatementView(final ImportBankStatementModel model) {

		// TODO -> AD_Message
		super("Bankauszug");

		this.model = model;

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocation(100, 100);
		this.setSize(1024, 800);
		this.setLayout(new BorderLayout());

		this.add(mkMainPanel(), BorderLayout.CENTER);

	}

	private JPanel mkMainPanel() {

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setSize(1280, 1024);

		table = new JTable(model.getTableModel()) {

			private static final long serialVersionUID = -1332415554100681149L;

			@Override
			public Component prepareRenderer(TableCellRenderer renderer,
					int row, int column) {

				final Component component = super.prepareRenderer(renderer,
						row, column);

				component.setForeground(Color.BLACK);

				if (column == COL_IDX_OK) {

					// set the checkbox background color
					// setzen der Farben der Checkbox nach Grad der
					// Uebereinstimmung
					// Gruen: Preis stimmt, Rechnungsnummer kommt im VZ vor
					// Gelb: Preis stimmt, aber Skonto gezahlt nach Ablauf der
					// Skontofrist /
					// Summe ist richtig, aber stimmt nicht mit eigentlichen
					// Zahlungsbedingungen �beriein
					// Rot: Preis stimmt nicht, aber Rechnungsnummer kommt im VZ
					// vor
					final int rowFitting = ImportBankStatementView.this.model
							.getRowFitting(row);
					
					if (rowFitting == 1) {

						component.setBackground(Color.GREEN);
						
					} else if (rowFitting == 2) {

						component.setBackground(Color.YELLOW);

					} else if (rowFitting == 3) {

						component.setBackground(Color.RED);
						
					} else {
						component.setBackground(new Color(200, 200, 200));
					}
				} else {
					// set alternating row colors
					if (model.isRowColored(row)) {

						if (column >= COL_IDX_STMT_DATE
								&& column <= COL_IDX_REASON)
							component.setBackground(new Color(240, 240, 240));

						else if (column >= 3 && column <= 6) {
							component.setBackground(new Color(220, 220, 220));

						} else if (column >= COL_IDX_PAY_AMT
								&& column <= COL_IDX_OVER_UNDER) {
							component.setBackground(new Color(200, 200, 200));
						}
					} else {
						component.setBackground(Color.WHITE);
					}
				}
				return component;
			}

			@Override
			public String getToolTipText(MouseEvent e) {

				final Point p = e.getPoint();
				final int rowIndex = rowAtPoint(p);
				final int colIndex = convertColumnIndexToModel(columnAtPoint(p));

				final Object value = getModel().getValueAt(rowIndex, colIndex);
				if (value != null && value instanceof String) {
					return (String) value;
				}
				return null;
			}
		};

		table.getTableHeader().setReorderingAllowed(false);

		// final ImportRenderer renderer = new ImportRenderer();

		// for (int i = 0; i < table.getColumnCount(); i++) {
		// table.getColumnModel().getColumn(i).setCellRenderer(renderer);
		// }

		final JCheckBox checkBox = new JCheckBox();
		checkBox.setHorizontalAlignment(JLabel.CENTER);

		// final DefaultCellEditor checkBoxEditor = new
		// DefaultCellEditor(checkBox);
		// table.getColumn("OK").setCellEditor(checkBoxEditor);
		// table.getColumn("OK").setCellRenderer(new CheckBoxRenderer());

		table.getColumn("Verwendungszweck").setMaxWidth(600);
		table.setShowVerticalLines(true);
		table.setShowHorizontalLines(false);
		table.setGridColor(Color.LIGHT_GRAY);

		TableHelper.initColumnSizes(table);

		JScrollPane scrollPane = new JScrollPane(table);

		scrollPane.setSize(1280, 900);
		mainPanel.add(scrollPane, BorderLayout.CENTER);
		// TODO -> AD_Message
		createButton = new JButton("Erzeuge Belege");

		mainPanel.add(createButton, BorderLayout.SOUTH);

		return mainPanel;
	}

	public void addCreatButtonListener(ActionListener l) {
		createButton.addActionListener(l);
	}

	public void setCreateButtonEnabled(Boolean newValue) {
		createButton.setEnabled(newValue);
	}

}
