package de.metas.banking.impexp;

import org.compiere.model.I_I_BankStatement;

import de.metas.impexp.processing.IImportProcess;
import de.metas.impexp.processing.IImportProcessFactory;
import de.metas.process.JavaProcess;
import de.metas.process.RunOutOfTrx;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class ImportBankStatement extends JavaProcess
{
	private IImportProcess<I_I_BankStatement> importProcess;

	@Override
	protected void prepare()
	{
		importProcess = Services.get(IImportProcessFactory.class).newImportProcess(I_I_BankStatement.class);
		importProcess.setCtx(getCtx());
		importProcess.setParameters(getParameterAsIParams());
		importProcess.setLoggable(this);
	}

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		importProcess.run();
		return MSG_OK;
	}


}
