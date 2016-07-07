package de.metas.banking.bankstatement.match.form;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Properties;

import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.apps.AEnv;
import org.compiere.apps.ConfirmPanel;
import org.compiere.apps.ConfirmPanelListener;
import org.compiere.swing.CButton;
import org.compiere.swing.CPanel;
import org.compiere.swing.CScrollPane;
import org.compiere.swing.table.AnnotatedJXTable;
import org.compiere.swing.table.AnnotatedTableFactory;
import org.compiere.swing.table.AnnotatedTableModel;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import com.google.common.base.Throwables;

import de.metas.adempiere.form.IClientUI;
import de.metas.banking.bankstatement.match.model.BankStatementPaymentMatching;
import de.metas.banking.bankstatement.match.model.BankStatementPaymentMatchingAutoBuilder;
import de.metas.banking.bankstatement.match.model.IBankStatementLine;
import de.metas.banking.bankstatement.match.model.IBankStatementPaymentMatching;
import de.metas.banking.bankstatement.match.model.IPayment;
import de.metas.banking.bankstatement.match.service.BankStatementMatchQuery;
import de.metas.banking.bankstatement.match.service.IBankStatementMatchDAO;
import de.metas.banking.bankstatement.match.service.impl.BankStatementPaymentMatchingProcessor;

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

/**
 *
 * @author metas-dev <dev@metasfresh.com>
 * @task 07994 Kontoauszugsimport (109116274972)
 */
class BankStatementMatchPanel extends CPanel
{
	private static final long serialVersionUID = 1L;
	
	private static final String MSG_BankStatementsToMatch = "de.metas.banking.bankstatement.match.form.BankStatementMatchPanel.BankStatementsToMatch";
	private static final String MSG_BankStatementsMatched = "de.metas.banking.bankstatement.match.form.BankStatementMatchPanel.BankStatementsMatched";

	// services
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private final transient IClientUI clientUI = Services.get(IClientUI.class);
	private final transient IBankStatementMatchDAO bankStatementMatchDAO = Services.get(IBankStatementMatchDAO.class);

	private final transient NumberFormat amountFormat = DisplayType.getNumberFormat(DisplayType.Amount);

	private final QueryPanel queryPanel = new QueryPanel();
	private final ConfirmPanel confirmPanel = ConfirmPanel
			.builder()
			.withCancelButton(true)
			.build(); 
	private final AnnotatedTableModel<IBankStatementLine> bankStatementLinesTableModel = new AnnotatedTableModel<>(IBankStatementLine.class);
	private final AnnotatedJXTable bankStatementLinesTable;
	private final AnnotatedTableModel<IPayment> paymentsTableModel = new AnnotatedTableModel<>(IPayment.class);
	private final AnnotatedJXTable paymentsTable;
	private final AnnotatedTableModel<IBankStatementPaymentMatching> matchingsTableModel = new AnnotatedTableModel<>(IBankStatementPaymentMatching.class);
	private final AnnotatedJXTable matchingsTable;
	private final AnnotatedTableModel<IPayment> machingPaymentsTableModel = new AnnotatedTableModel<>(IPayment.class);

	private final JLabel lblPayAmtSum = new JLabel("0");

	private final CButton btnMatch = new CButton();
	private final CButton btnMatchAuto = new CButton();
	private final CButton btnUnmatch = new CButton();

	public BankStatementMatchPanel()
	{
		super();
		final Properties ctx = Env.getCtx();

		//
		// Query Panel
		{
			queryPanel.setOnQueryCallback(new Runnable()
			{

				@Override
				public void run()
				{
					onQuery();
				}
			});
		}

		//
		// Bank Statement lines table
		bankStatementLinesTable = AnnotatedTableFactory.newInstance()
				.setColumnControlVisible(false)
				.setModel(bankStatementLinesTableModel)
				.setCreateStandardSelectActions(false)
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION)
				.addListSelectionListener(new ListSelectionListener()
				{
					@Override
					public void valueChanged(final ListSelectionEvent e)
					{
						onBankStatementLineSelectionChanged(e);
					}
				})
				.create();

		//
		// Payments table
		paymentsTable = AnnotatedTableFactory.newInstance()
				.setColumnControlVisible(false)
				.setModel(paymentsTableModel)
				.setCreateStandardSelectActions(false)
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION)
				.addListSelectionListener(new ListSelectionListener()
				{
					@Override
					public void valueChanged(final ListSelectionEvent e)
					{
						onPaymentTableSelectionChanged(e);
					}
				})
				.addPopupAction(new SelectPaymentsWithSameBatchAction())
				.addPopupAction(new OpenPaymentBatchWindowAction())
				.addPopupAction(new OpenPaymentWindowAction())
				.create();

		//
		// Bank Statement - Payment matching lines table
		matchingsTable = AnnotatedTableFactory.newInstance()
				.setColumnControlVisible(false)
				.setModel(matchingsTableModel)
				.setCreateStandardSelectActions(false)
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION)
				.addListSelectionListener(new ListSelectionListener()
				{

					@Override
					public void valueChanged(final ListSelectionEvent e)
					{
						onMatchingSelectionChanged(e);
					}
				})
				.create();
		matchingsTableModel.addTableModelListener(new TableModelListener()
		{
			@Override
			public void tableChanged(final TableModelEvent e)
			{
				onMatchingTableModelChanged(e);
			}
		});

		//
		// Payments of currently selected matching
		final AnnotatedJXTable matchingPaymentsTable = AnnotatedTableFactory.newInstance()
				.setColumnControlVisible(false)
				.setModel(machingPaymentsTableModel)
				.setCreateStandardSelectActions(false)
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION)
				.addPopupAction(new OpenPaymentWindowAction())
				.create();
		matchingPaymentsTable.setEditable(false);

		//
		// Match buttons panel
		final CPanel matchButtonsPanel = new CPanel();
		{
			matchButtonsPanel.setLayout(new MigLayout(
					// layoutConstraints
					new LC().fillX()
					// colConstraints
					, new AC()));

			// Button: match
			btnMatch.setText(msgBL.translate(ctx, "Match"));
			btnMatch.setEnabled(false);
			btnMatch.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(final ActionEvent e)
				{
					onMatch();
				}
			});

			// Button: match automatically
			btnMatchAuto.setText(msgBL.translate(ctx, "MatchAuto"));
			btnMatchAuto.setEnabled(false);
			btnMatchAuto.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(final ActionEvent e)
				{
					onMatchAutomatically();
				}
			});

			// Layout
			matchButtonsPanel.add(new JLabel(msgBL.translate(ctx, "PayAmt") + ": "), new CC());
			matchButtonsPanel.add(lblPayAmtSum, new CC().width("80px::").wrap());
			matchButtonsPanel.add(btnMatch, new CC().width("100px").spanX(2).growX().wrap());
			matchButtonsPanel.add(btnMatchAuto, new CC().width("100px").spanX(2).growX().wrap());
		}

		//
		// Un-match buttons panel
		final CPanel unmatchButtonsPanel = new CPanel();
		{
			unmatchButtonsPanel.setLayout(new MigLayout(
					// layoutConstraints
					new LC().fillX()
					// colConstraints
					, new AC().count(1)));

			btnUnmatch.setText(msgBL.translate(ctx, "Unmatch"));
			btnUnmatch.setEnabled(false);
			btnUnmatch.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(final ActionEvent e)
				{
					onUnmatch();
				}
			});

			// Layout
			unmatchButtonsPanel.add(btnUnmatch, new CC().width("100px").growX().wrap());
		}

		//
		// Confirm panel
		{
			confirmPanel.getOKButton().setEnabled(false);
			confirmPanel.getCancelButton().setEnabled(true);
			confirmPanel.setConfirmPanelListener(new ConfirmPanelListener()
			{
				@Override
				public void okButtonPressed(final ActionEvent e)
				{
					BankStatementMatchPanel.this.okButtonPressed();
				}

				@Override
				public void cancelButtonPressed(final ActionEvent e)
				{
					BankStatementMatchPanel.this.cancelButtonPressed();
				}
			});
		}

		//
		// Layout:
		{
			setLayout(new MigLayout(
					// layoutConstraints
					new LC().fill().wrapAfter(3)
					// colConstraints
					, new AC()
							// Column 0
							.index(0).grow(50).size("::600px").fill()
							// Column 1
							.index(1).grow(100).fill()
							// Column 2
							.index(2).grow(0).fill()
					// rowConstraints
					, new AC().fill()
			//
			));

			//
			// Query panel
			add(queryPanel, new CC().spanX().growY(Float.valueOf(0)).wrap());
			//
			// Bank statements / Payments to be matched
			final JLabel lblToMatch = new JLabel(msgBL.translate(ctx, MSG_BankStatementsToMatch) + ": ");
			add(lblToMatch, new CC().spanX().growY(Float.valueOf(0)).wrap());
			add(new CScrollPane(bankStatementLinesTable), new CC());
			add(new CScrollPane(paymentsTable), new CC());
			add(matchButtonsPanel, new CC());
			//
			// Bank statements / Payments matched
			final JLabel lblMatched = new JLabel(msgBL.translate(ctx, MSG_BankStatementsMatched) + ": ");
			add(lblMatched, new CC().spanX().growY(Float.valueOf(0)).wrap());
			add(new CScrollPane(matchingsTable), new CC());
			add(new CScrollPane(matchingPaymentsTable), new CC());
			add(unmatchButtonsPanel, new CC());
			//
			add(confirmPanel, new CC().newline().spanX().growY(Float.valueOf(0)).wrap());
		}

		//
		// Load
		// onQuery(); // NOTE: don't trigger this automatically
	}
	
	private final int getWindowNo()
	{
		return Env.getWindowNo(this);
	}

	private BankStatementMatchQuery createQuery()
	{
		final List<IBankStatementPaymentMatching> matchings = matchingsTableModel.getRows();
		return queryPanel.createQuery()
				.addExcludeMatchings(matchings)
				.build();
	}

	private void onQuery()
	{
		final BankStatementMatchQuery query = createQuery();

		//
		// Execute query
		List<IBankStatementLine> bankStatementLines = bankStatementMatchDAO.retrieveBankStatementLinesToMatch(query);
		List<IPayment> payments = bankStatementMatchDAO.retrievePaymentsToMatch(query);

		//
		// UI
		bankStatementLinesTableModel.setRows(bankStatementLines);
		paymentsTableModel.setRows(payments);
		updateMatchButtons();
	}

	private void onBankStatementLineSelectionChanged(final ListSelectionEvent e)
	{
		updateMatchButtons();
	}

	private void onPaymentTableSelectionChanged(final ListSelectionEvent e)
	{
		updateMatchButtons();
	}

	private void onMatchingSelectionChanged(final ListSelectionEvent e)
	{
		final List<IBankStatementPaymentMatching> matchings = getSelectedMatchings();

		btnUnmatch.setEnabled(!matchings.isEmpty());

		machingPaymentsTableModel.removeRows();
		for (final IBankStatementPaymentMatching matching : matchings)
		{
			machingPaymentsTableModel.addRows(matching.getPayments());
		}
	}

	private void onMatchingTableModelChanged(final TableModelEvent e)
	{
		updateConfirmPanelButtons();
	}

	private final void addMatching(final IBankStatementPaymentMatching matching)
	{
		bankStatementLinesTableModel.removeRow(matching.getBankStatementLine());
		paymentsTableModel.removeRows(matching.getPayments());

		matchingsTableModel.addRow(matching);
	}

	/**
	 * Manually match selected payments
	 */
	private void onMatch()
	{
		final IBankStatementPaymentMatching matching = BankStatementPaymentMatching.builder()
				.setBankStatementLine(getSelectedBankStatementLine())
				.setPayments(getSelectedPayments())
				.build();

		addMatching(matching);
	}

	/**
	 * Automatically match all payments
	 */
	private void onMatchAutomatically()
	{
		final List<IBankStatementPaymentMatching> matchings = BankStatementPaymentMatchingAutoBuilder.newBuilder()
				.setBankStatementLines(bankStatementLinesTableModel.getRows())
				.setPayments(paymentsTableModel.getRows())
				.build();

		for (final IBankStatementPaymentMatching matching : matchings)
		{
			addMatching(matching);
		}
	}

	private void onUnmatch()
	{
		final List<IBankStatementPaymentMatching> matchings = getSelectedMatchings();

		for (final IBankStatementPaymentMatching matching : matchings)
		{
			bankStatementLinesTableModel.addRow(matching.getBankStatementLine());
			paymentsTableModel.addRows(matching.getPayments());

			matchingsTableModel.removeRow(matching);
		}
	}

	private void updateMatchButtons()
	{
		final boolean validMatching = BankStatementPaymentMatching.builder()
				.setBankStatementLine(getSelectedBankStatementLine())
				.setPayments(getSelectedPayments())
				.isValid();
		btnMatch.setEnabled(validMatching);

		btnMatchAuto.setEnabled(bankStatementLinesTableModel.getRowCount() > 0 && paymentsTableModel.getRowCount() > 0);

		final BigDecimal payAmtSum = getSelectedPaymentsPayAmt();
		lblPayAmtSum.setText(amountFormat.format(payAmtSum));
	}

	private void updateConfirmPanelButtons()
	{
		final boolean hasMatchings = matchingsTableModel.getRowCount() > 0;
		confirmPanel.getOKButton().setEnabled(hasMatchings);
	}

	private IBankStatementLine getSelectedBankStatementLine()
	{
		return bankStatementLinesTableModel.getSelectedRow(bankStatementLinesTable.getSelectionModel(), bankStatementLinesTable.getConvertRowIndexToModelFunction());
	}

	private List<IPayment> getSelectedPayments()
	{
		return paymentsTableModel.getSelectedRows(paymentsTable.getSelectionModel(), paymentsTable.getConvertRowIndexToModelFunction());
	}

	private BigDecimal getSelectedPaymentsPayAmt()
	{
		BigDecimal payAmtSum = BigDecimal.ZERO;
		for (final IPayment payment : getSelectedPayments())
		{
			final BigDecimal payAmt = payment.getPayAmt();
			payAmtSum = payAmtSum.add(payAmt);
		}
		return payAmtSum;
	}

	private List<IBankStatementPaymentMatching> getSelectedMatchings()
	{
		return matchingsTableModel.getSelectedRows(matchingsTable.getSelectionModel(), matchingsTable.getConvertRowIndexToModelFunction());
	}

	private void okButtonPressed()
	{
		try
		{
			BankStatementPaymentMatchingProcessor.newInstance()
					.setContext(Env.getCtx())
					.setBankStatementPaymentMatchings(matchingsTableModel.getRows())
					.process();
			matchingsTableModel.removeRows();
			
			disposeFrame();
		}
		catch (Exception e)
		{
			clientUI.error(getWindowNo(), Throwables.getRootCause(e));

		}
	}

	private void cancelButtonPressed()
	{
		disposeFrame();
	}

	private final void disposeFrame()
	{
		final Window window = AEnv.getWindow(this);
		if (window == null)
		{
			return;
		}

		window.dispose();
	}

	public void setQuery(final BankStatementMatchQuery query)
	{
		queryPanel.setQuery(query);
	}

	public void setQueryReadOnly(final boolean ro)
	{
		queryPanel.setQueryReadOnly(ro);
	}
}
