package de.metas.banking.bankstatement.match.form;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.JComboBox;

import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.swing.CButton;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.ListComboBoxModel;
import org.compiere.util.Env;
import org.compiere.util.Util;

import de.metas.banking.bankstatement.match.model.BankAccount;
import de.metas.banking.bankstatement.match.model.BankStatement;
import de.metas.banking.bankstatement.match.service.BankStatementMatchQuery;
import de.metas.banking.bankstatement.match.service.IBankStatementMatchDAO;

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
 * The panel displayed on window's top which allows user to filter which bank statement lines and payments he/she will see.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
class QueryPanel extends CPanel
{
	private static final long serialVersionUID = 1L;

	// services
	private final transient IBankStatementMatchDAO bankStatementMatchDAO = Services.get(IBankStatementMatchDAO.class);
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	private final ListComboBoxModel<BankStatement> bankStatementsModel = new ListComboBoxModel<>();
	private final JComboBox<BankStatement> fBankStatement = new JComboBox<>(bankStatementsModel);
	private final ListComboBoxModel<BankAccount> bankAccountsModel = new ListComboBoxModel<>();
	private final JComboBox<BankAccount> fBankAccount = new JComboBox<>(bankAccountsModel);
	private final CButton btnQuery = new CButton();
	private Runnable onQueryCallback = null;

	public QueryPanel()
	{
		super();
		final Properties ctx = Env.getCtx();

		setLayout(new MigLayout(
				// layoutConstraints
				new LC().fill().noGrid().flowX()
				// colConstraints
				, new AC().fill().grow(0)
				// rowConstraints
				, new AC().fill().grow(0)
		//
		));

		//
		// Bank Statements
		{
			final CLabel lblBankStatement = new CLabel(msgBL.translate(ctx, org.compiere.model.I_C_BankStatement.COLUMNNAME_C_BankStatement_ID));
			add(lblBankStatement);
			add(fBankStatement, new CC().width("200px"));
		}

		//
		// Bank Accounts
		{
			final CLabel lblBankAccount = new CLabel(msgBL.translate(ctx, org.compiere.model.I_C_BP_BankAccount.COLUMNNAME_C_BP_BankAccount_ID));
			add(lblBankAccount);
			add(fBankAccount, new CC().width("200px"));
		}

		//
		// Button: Query
		{
			btnQuery.setText(msgBL.translate(ctx, "Refresh"));
			add(btnQuery);
			btnQuery.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(final ActionEvent e)
				{
					onQuery();
				}
			});
		}

		//
		// Load
		load();
	}

	private void load()
	{
		bankStatementsModel.set(bankStatementMatchDAO.retrieveBankStatementsToMatch());
		bankAccountsModel.set(bankStatementMatchDAO.retrieveBankAccountsToMatch());
	}

	/** @return new query based on currently selected values */
	public BankStatementMatchQuery.Builder createQuery()
	{
		return BankStatementMatchQuery.builder()
				.setBankStatement(bankStatementsModel.getSelectedItem())
				.setBankAccount(bankAccountsModel.getSelectedItem());
	}

	/** Sets the callback to be called when user presses the Query button */
	public void setOnQueryCallback(final Runnable onQueryCallback)
	{
		this.onQueryCallback = onQueryCallback;
	}

	private void onQuery()
	{
		if (onQueryCallback == null)
		{
			return;
		}

		onQueryCallback.run();
	}

	public void setQuery(final BankStatementMatchQuery query)
	{
		final BankStatement bankStatement = Util.coalesce(query.getBankStatement(), BankStatement.NULL);
		bankStatementsModel.addIfAbsent(bankStatement);
		bankStatementsModel.setSelectedItem(bankStatement);

		final BankAccount bankAccount = Util.coalesce(query.getBankAccount(), BankAccount.NULL);
		bankAccountsModel.addIfAbsent(bankAccount);
		bankAccountsModel.setSelectedItem(bankAccount);

		// Automatically execute query if the query it's not empty.
		// If it's empty, let the user execute it on demand, because it can be quite expensive.
		if (query != null && query != BankStatementMatchQuery.EMPTY)
		{
			onQuery();
		}
	}

	public void setQueryReadOnly(final boolean ro)
	{
		fBankStatement.setEnabled(!ro);
		fBankAccount.setEnabled(!ro);
	}

}
