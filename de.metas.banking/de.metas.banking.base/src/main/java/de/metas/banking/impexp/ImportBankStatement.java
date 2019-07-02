package de.metas.banking.impexp;

import org.compiere.model.I_I_BankStatement;
import org.compiere.process.AbstractImportJavaProcess;

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

public class ImportBankStatement extends AbstractImportJavaProcess<I_I_BankStatement>
{
	public ImportBankStatement()
	{
		super(I_I_BankStatement.class);
	}
}
