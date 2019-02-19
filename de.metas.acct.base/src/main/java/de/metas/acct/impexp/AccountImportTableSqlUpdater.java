package de.metas.acct.impexp;

import static org.adempiere.impexp.AbstractImportProcess.COLUMNNAME_I_ErrorMsg;
import static org.adempiere.impexp.AbstractImportProcess.COLUMNNAME_I_IsImported;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.util.DB;
import org.slf4j.Logger;

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
 * A helper class for {@link AccountImportProcess} that performs SQL updates on the {@link I_ElementValue} table.
 * Those updates complements the data from existing metasfresh records and flag those import records that can't yet be imported.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@UtilityClass
public class AccountImportTableSqlUpdater
{
	private static final transient Logger logger = LogManager.getLogger(AccountImportTableSqlUpdater.class);

	public void updateAccountImportTable(@NonNull final String whereClause)
	{
		dbUpdateElement(whereClause);
		dbUpdateParentElementValue(whereClause);
		dbUpdateErrorMessages(whereClause);
	}

	private void dbUpdateElement(final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE I_ElementValue i ")
				.append("SET C_Element_ID = (SELECT C_Element_ID FROM C_Element e WHERE e.Name = i.ElementName ")
				.append("AND e.AD_Client_ID IN (0, i.AD_Client_ID) ")
				.append("AND e.AD_Org_ID IN (0, i.AD_Org_ID) ) ")
				.append("WHERE ElementName IS NOT NULL AND C_Element_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set Element Default={}" + no);
	}

	public void dbUpdateParentElementValue(final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE I_ElementValue i ")
				.append("SET ParentElementValue_ID=(SELECT C_ElementValue_ID ")
				.append("FROM C_ElementValue ev WHERE i.C_Element_ID=ev.C_Element_ID ")
				.append("AND i.ParentValue=ev.Value AND i.AD_Client_ID=ev.AD_Client_ID ")
				.append("AND e.AD_Org_ID IN (0, i.AD_Org_ID) ) ")
				.append("WHERE ParentElementValue_ID IS NULL ")
				.append("AND " + COLUMNNAME_I_IsImported + "<>'Y' ")
				.append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Found Parent ElementValue=" + no);
	}
	
	public void dbUpdateTreeElementValue(final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE I_ElementValue i ")
				.append("SET ParentElementValue_ID=(SELECT C_ElementValue_ID ")
				.append("FROM C_ElementValue ev WHERE i.C_Element_ID=ev.C_Element_ID ")
				.append("AND i.ParentValue=ev.Value AND i.AD_Client_ID=ev.AD_Client_ID ")
				.append("AND e.AD_Org_ID IN (0, i.AD_Org_ID) ) ")
				.append("WHERE ParentElementValue_ID IS NULL ")
				.append("AND " + COLUMNNAME_I_IsImported + "<>'Y' ")
				.append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Found Parent ElementValue=" + no);
	}

	private void dbUpdateErrorMessages(final String whereClause)
	{

		StringBuilder sql;
		int no;

		sql = new StringBuilder("UPDATE I_ElementValue i ")
				.append("SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid ElementName, ' ")
				.append("WHERE C_Element_ID IS NULL AND ElementName IS NOT NULL ")
				.append("AND " + COLUMNNAME_I_IsImported + "<>'Y' ")
				.append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Invalid Element ={}", no);

		sql = new StringBuilder("UPDATE I_ElementValue ")
				.append("SET I_ErrorMsg=I_ErrorMsg||'Info=ParentNotFound, ' ")
				.append("WHERE ParentElementValue_ID IS NULL AND ParentValue IS NOT NULL ")
				.append("AND " + COLUMNNAME_I_IsImported + "<>'Y' ")
				.append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Not Found Parent ElementValue=" + no);

		sql = new StringBuilder("UPDATE I_ElementValue "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Value is mandatory, ' "
				+ "WHERE Value IS NULL "
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Value is mandatory={}", no);
	}
}
