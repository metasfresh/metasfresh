package de.metas.banking.impexp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.adempiere.impexp.AbstractImportProcess;
import org.adempiere.util.lang.IMutable;

import de.metas.banking.model.I_C_BankStatement;

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

public class BankStatementImportProcess  extends AbstractImportProcess<I_C_BankStatement>
{

	@Override
	public Class<I_C_BankStatement> getImportModelClass()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getImportTableName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getTargetTableName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void updateAndValidateImportRecords()
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected String getImportOrderBySql()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected I_C_BankStatement retrieveImportRecord(Properties ctx, ResultSet rs) throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ImportRecordResult importRecord(IMutable<Object> state, I_C_BankStatement importRecord, boolean isInsertOnly) throws Exception
	{
		// TODO Auto-generated method stub
		return null;
	}

}
