package de.metas.banking.service;

import de.metas.banking.model.I_C_BankStatementLine;
import de.metas.banking.model.I_C_BankStatementLine_Ref;

/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2016 metas GmbH
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
 * Listens to bank statements events.
 * 
 * To implement a new listener, please consider extending {@link BankStatementListenerAdapter}.
 * 
 * To register a new listener, please use {@link IBankStatementListenerService}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IBankStatementListener
{
	/**
	 * @param bankStatementLine
	 */
	void onBankStatementLineVoiding(final I_C_BankStatementLine bankStatementLine);

	/**
	 * @param bankStatementLineRef
	 */
	void onBankStatementLineRefVoiding(final I_C_BankStatementLine_Ref bankStatementLineRef);
}
