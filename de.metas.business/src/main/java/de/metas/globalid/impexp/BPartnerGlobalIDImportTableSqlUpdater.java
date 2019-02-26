package de.metas.globalid.impexp;

import static org.adempiere.impexp.AbstractImportProcess.COLUMNNAME_I_ErrorMsg;
import static org.adempiere.impexp.AbstractImportProcess.COLUMNNAME_I_IsImported;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_I_BPartner_GlobalID;
import org.compiere.util.DB;
import org.slf4j.Logger;

import de.metas.interfaces.I_C_BPartner;
import de.metas.logging.LogManager;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * A helper class for {@link BPartnerGlobalIDImportProcess} that updates {@link I_I_BPartner_GlobalID} table.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@UtilityClass
public class BPartnerGlobalIDImportTableSqlUpdater
{
	private static final transient Logger logger = LogManager.getLogger(BPartnerGlobalIDImportTableSqlUpdater.class);

	public void updateBPartnerGlobalIDImortTable(@NonNull final String whereClause)
	{
		dbUpdateCbPartnerIdsFromGlobalID(whereClause);

		dbUpdateErrorMessages(whereClause);
	}

	private void dbUpdateCbPartnerIdsFromGlobalID(final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE " + I_I_BPartner_GlobalID.Table_Name + " i ")
				.append("SET C_BPartner_ID=(SELECT C_BPartner_ID FROM C_BPartner p ")
				.append("WHERE i." + I_I_BPartner_GlobalID.COLUMNNAME_globalid)
				.append("=p." + I_C_BPartner.COLUMNNAME_globalid)
				.append(" AND p.AD_Client_ID=i.AD_Client_ID) ")
				.append("WHERE C_BPartner_ID IS NULL AND " + I_I_BPartner_GlobalID.COLUMNNAME_globalid + " IS NOT NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "='N'")
				.append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Found BPartner={}", no);
	}

	private void dbUpdateErrorMessages(final String whereClause)
	{

		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE " + I_I_BPartner_GlobalID.Table_Name)
				.append(" SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=GlobalID is mandatory, ' ")
				.append("WHERE Value IS NULL ")
				.append("AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Value is mandatory={}", no);
	}
}
