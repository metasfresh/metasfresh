package de.metas.banking.bankstatement.match.service;

import java.util.List;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_Payment;

import de.metas.banking.bankstatement.match.model.BankAccount;
import de.metas.banking.bankstatement.match.model.BankStatement;
import de.metas.banking.bankstatement.match.model.IBankStatementLine;
import de.metas.banking.bankstatement.match.model.IPayment;
import de.metas.banking.bankstatement.match.spi.IPaymentBatch;

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
 * Internal service used by BankStatement matching UI to retrieve the data.
 *
 * @author metas-dev <dev@metasfresh.com>
 * @task 07994 Kontoauszugsimport (109116274972)
 */
public interface IBankStatementMatchDAO extends ISingletonService
{
	List<BankStatement> retrieveBankStatementsToMatch();

	List<BankAccount> retrieveBankAccountsToMatch();

	List<IBankStatementLine> retrieveBankStatementLinesToMatch(final BankStatementMatchQuery query);

	List<IPayment> retrievePaymentsToMatch(final BankStatementMatchQuery query);

	IPaymentBatch retrievePaymentBatch(final I_C_Payment payment);
}
