package de.metas.globalid.impexp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.impexp.AbstractImportProcess;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_I_BPartner_GlobalID;
import org.compiere.model.X_I_BPartner_GlobalID;

import de.metas.util.Check;

/*
 * #%L
 * de.metas.business
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class BPartnerGlobalIDImportProcess extends AbstractImportProcess<I_I_BPartner_GlobalID>
{

	@Override
	public Class<I_I_BPartner_GlobalID> getImportModelClass()
	{
		return I_I_BPartner_GlobalID.class;
	}

	@Override
	public String getImportTableName()
	{
		return I_I_BPartner_GlobalID.Table_Name;
	}

	@Override
	protected String getTargetTableName()
	{
		return I_C_BPartner.Table_Name;
	}

	@Override
	protected void updateAndValidateImportRecords()
	{
		BPartnerGlobalIDImportTableSqlUpdater.updateBPartnerGlobalIDImortTable(getWhereClause());
	}

	@Override
	protected String getImportOrderBySql()
	{
		return I_I_BPartner_GlobalID.COLUMNNAME_globalid;
	}

	@Override
	protected I_I_BPartner_GlobalID retrieveImportRecord(Properties ctx, ResultSet rs) throws SQLException
	{
		return new X_I_BPartner_GlobalID(ctx, rs, ITrx.TRXNAME_ThreadInherited);
	}

	/*
	 * @param isInsertOnly ignored. This import is only for updates.
	 */
	@Override
	protected ImportRecordResult importRecord(IMutable<Object> state,
			I_I_BPartner_GlobalID importRecord,
			final boolean isInsertOnly)
	{
		if (importRecord.getC_BPartner_ID() > 0 && !Check.isEmpty(importRecord.getURL3(), true))
		{
			final I_C_BPartner bpartner = importRecord.getC_BPartner();
			bpartner.setURL3(importRecord.getURL3());
			InterfaceWrapperHelper.save(bpartner);
			return ImportRecordResult.Updated;
		}
		return ImportRecordResult.Nothing;
	}

}
