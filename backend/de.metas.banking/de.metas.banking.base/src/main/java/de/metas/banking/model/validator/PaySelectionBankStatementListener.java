package de.metas.banking.model.validator;

import org.adempiere.util.Services;

import de.metas.banking.model.I_C_BankStatementLine;
import de.metas.banking.model.I_C_BankStatementLine_Ref;
import de.metas.banking.payment.IPaySelectionBL;
import de.metas.banking.service.BankStatementListenerAdapter;

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
 * Listens to bank statement events and manages the relation with pay selections.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
class PaySelectionBankStatementListener extends BankStatementListenerAdapter
{
	public static final transient PaySelectionBankStatementListener instance = new PaySelectionBankStatementListener();

	private PaySelectionBankStatementListener()
	{
		super();
	}

	@Override
	public void onBankStatementLineVoiding(I_C_BankStatementLine bankStatementLine)
	{
		Services.get(IPaySelectionBL.class).unlinkPaySelectionLineForBankStatement(bankStatementLine);
	}

	@Override
	public void onBankStatementLineRefVoiding(I_C_BankStatementLine_Ref bankStatementLineRef)
	{
		Services.get(IPaySelectionBL.class).unlinkPaySelectionLineForBankStatement(bankStatementLineRef);
	}

}
