package de.metas.banking.model.validator;

import de.metas.banking.model.BankStatementAndLineAndRefId;
import de.metas.banking.model.BankStatementLineId;
import de.metas.banking.model.I_C_BankStatementLine;
import de.metas.banking.model.I_C_BankStatementLine_Ref;
import de.metas.banking.payment.IPaySelectionBL;
import de.metas.banking.service.BankStatementListenerAdapter;
import de.metas.util.Services;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
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
		final BankStatementLineId bankStatementLineId = BankStatementLineId.ofRepoId(bankStatementLine.getC_BankStatementLine_ID());

		Services.get(IPaySelectionBL.class).unlinkPaySelectionLineForBankStatement(bankStatementLineId);
	}

	@Override
	public void onBankStatementLineRefVoiding(I_C_BankStatementLine_Ref bankStatementLineRef)
	{
		final BankStatementAndLineAndRefId bankStatementLineAndRefId = extractBankStatementAndLineAndRefId(bankStatementLineRef);

		Services.get(IPaySelectionBL.class).unlinkPaySelectionLineForBankStatement(bankStatementLineAndRefId);
	}

	private static BankStatementAndLineAndRefId extractBankStatementAndLineAndRefId(I_C_BankStatementLine_Ref bankStatementLineRef)
	{
		return BankStatementAndLineAndRefId.ofRepoIds(
				bankStatementLineRef.getC_BankStatement_ID(),
				bankStatementLineRef.getC_BankStatementLine_ID(),
				bankStatementLineRef.getC_BankStatementLine_Ref_ID());
	}

}
