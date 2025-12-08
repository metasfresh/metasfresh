package de.metas.impexp.bpartner;

import static de.metas.impexp.format.ImportTableDescriptor.COLUMNNAME_I_IsImported;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.util.DB;

import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.vertical.pharma.model.I_I_Pharma_BPartner;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * metasfresh-pharma
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
	final public void updateBPartnerImportTable(@NonNull final ImportRecordsSelection selection)
	{
		dbUpdateBPartners(selection);
		dbUpdateCountries(selection);
	}

	private void dbUpdateBPartners(@NonNull final ImportRecordsSelection selection)
	{
		final StringBuilder sql = new StringBuilder("UPDATE ")
				.append(I_I_Pharma_BPartner.Table_Name + " i ")
				.append(" SET C_BPartner_ID=(SELECT C_BPartner_ID FROM C_BPartner p")
				.append(" WHERE i.B00ADRNR=p.IFA_Manufacturer AND i.AD_Client_ID=p.AD_Client_ID) ")
				.append("WHERE C_BPartner_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateCountries(@NonNull final ImportRecordsSelection selection)
	{
		StringBuilder sql;
		sql = new StringBuilder("UPDATE ")
				.append(I_I_Pharma_BPartner.Table_Name + " i ")
				.append("SET C_Country_ID=(SELECT C_Country_ID FROM C_Country c ")
				.append("WHERE (i.B00LAND=c.CountryCode AND c.AD_Client_ID IN (0, i.AD_Client_ID))) ")
				.append("WHERE C_Country_ID IS NULL ")
				.append("AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}
}
