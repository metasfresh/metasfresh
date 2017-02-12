package de.metas.banking.bankstatement.match.form;

import java.awt.BorderLayout;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.FormPanel;
import org.compiere.model.I_C_BankStatement;

import de.metas.banking.bankstatement.match.model.BankStatement;
import de.metas.banking.bankstatement.match.service.BankStatementMatchQuery;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessPreconditionsResolution;

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
public class BankStatementMatchForm
		implements FormPanel
		, IProcessPrecondition
{
	@Override
	public void init(final int WindowNo, final FormFrame frame) throws Exception
	{
		final BankStatementMatchPanel mainPanel = new BankStatementMatchPanel();
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);

		//
		// Set initial query
		final BankStatementMatchQuery query = createInitialQuery(frame);
		mainPanel.setQuery(query);
		mainPanel.setQueryReadOnly(query != null && query != BankStatementMatchQuery.EMPTY);
	}

	private final BankStatementMatchQuery createInitialQuery(final FormFrame frame)
	{
		//
		// Enforce current bank statement (in case the form was opened from Bank Statement window)
		final ProcessInfo processInfo = frame.getProcessInfo();
		if (processInfo != null)
		{
			final I_C_BankStatement bankStatementPO = processInfo.getRecordIfApplies(I_C_BankStatement.class, ITrx.TRXNAME_None).orElse(null);
			if (bankStatementPO != null)
			{
				final BankStatement bankStatement = BankStatement.of(bankStatementPO);
				return BankStatementMatchQuery.builder()
						.setBankStatement(bankStatement)
						.setBankAccount(bankStatement.getBankAccount())
						.build();
			}
		}

		return BankStatementMatchQuery.EMPTY;
	}

	@Override
	public void dispose()
	{
		// nothing
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		// TODO Auto-generated method stub

		return ProcessPreconditionsResolution.accept();
	}

}
