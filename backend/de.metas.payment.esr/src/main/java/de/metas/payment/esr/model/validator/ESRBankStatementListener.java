package de.metas.payment.esr.model.validator;

import org.adempiere.util.Services;

import de.metas.banking.model.I_C_BankStatementLine;
import de.metas.banking.model.I_C_BankStatementLine_Ref;
import de.metas.banking.service.BankStatementListenerAdapter;
import de.metas.payment.esr.api.IESRImportBL;

/*
 * #%L
 * de.metas.payment.esr
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
 * Listens to bank statement events and manages the relation with ESR.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
class ESRBankStatementListener extends BankStatementListenerAdapter
{
	public static final transient ESRBankStatementListener instance = new ESRBankStatementListener();

	private ESRBankStatementListener()
	{
		super();
	}

	@Override
	public void onBankStatementLineVoiding(I_C_BankStatementLine bankStatementLine)
	{
		Services.get(IESRImportBL.class).unlinkESRImportLinesFor(bankStatementLine);
	}

	@Override
	public void onBankStatementLineRefVoiding(I_C_BankStatementLine_Ref bankStatementLineRef)
	{
		Services.get(IESRImportBL.class).unlinkESRImportLinesFor(bankStatementLineRef);
	}
}
