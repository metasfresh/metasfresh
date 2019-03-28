package de.metas.impexp.bpartner;

import static org.adempiere.impexp.AbstractImportProcess.COLUMNNAME_I_ErrorMsg;
import static org.adempiere.impexp.AbstractImportProcess.COLUMNNAME_I_IsImported;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.util.DB;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.vertical.pharma.model.I_I_Pharma_BPartner;
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
 * A helper class for {@link IFABPartnerImportProcess} that performs the "dirty" but efficient SQL updates on the {@link I_I_Pharma_BPartner} table.
 * Those updates complements the data from existing metasfresh records and flag those import records that can't yet be imported.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@UtilityClass
public class IFABPartnerImportTableSqlUpdater
{
	private static final transient Logger logger = LogManager.getLogger(IFABPartnerImportTableSqlUpdater.class);

	final public void updateBPartnerImportTable(@NonNull final String whereClause)
	{
		dbUpdateBPartners(whereClause);
		dbUpdateCountries(whereClause);
	}

	private void dbUpdateBPartners(@NonNull final String whereClause)
	{
		final StringBuilder sql = new StringBuilder("UPDATE ")
				.append(I_I_Pharma_BPartner.Table_Name + " i ")
				.append(" SET C_BPartner_ID=(SELECT C_BPartner_ID FROM C_BPartner p")
				.append(" WHERE i.B00ADRNR=p.IFA_Manufacturer AND i.AD_Client_ID=p.AD_Client_ID) ")
				.append("WHERE C_BPartner_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateCountries(@NonNull final String whereClause)
	{
		int no;
		StringBuilder sql;
		sql = new StringBuilder("UPDATE ")
				.append(I_I_Pharma_BPartner.Table_Name + " i ")
				.append("SET C_Country_ID=(SELECT C_Country_ID FROM C_Country c ")
				.append("WHERE (i.B00LAND=c.CountryCode AND c.AD_Client_ID IN (0, i.AD_Client_ID))) ")
				.append("WHERE C_Country_ID IS NULL ")
				.append("AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		sql = new StringBuilder("UPDATE ")
				.append(I_I_Pharma_BPartner.Table_Name + " i ")
				.append("SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid Country, ' ")
				.append("WHERE C_Country_ID IS NULL AND b00ssatz <> '2'") // do not try to match country for records that means to deactivate the partner
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Invalid Country={}", no);
	}
}
