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
 * {@link IBankStatementListener} adapter.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class BankStatementListenerAdapter implements IBankStatementListener
{
	protected BankStatementListenerAdapter()
	{
		super();
	}

	@Override
	public void onBankStatementLineVoiding(I_C_BankStatementLine bankStatementLine)
	{
	}

	@Override
	public void onBankStatementLineRefVoiding(I_C_BankStatementLine_Ref bankStatementLineRef)
	{
	}

}
