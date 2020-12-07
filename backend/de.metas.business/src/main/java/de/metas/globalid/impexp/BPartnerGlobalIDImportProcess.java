/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.globalid.impexp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_I_BPartner_GlobalID;
import org.compiere.model.X_I_BPartner_GlobalID;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.impexp.processing.SimpleImportProcessTemplate;
import de.metas.util.Check;
import de.metas.util.Services;

public class BPartnerGlobalIDImportProcess extends SimpleImportProcessTemplate<I_I_BPartner_GlobalID>
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
		final ImportRecordsSelection selection = getImportRecordsSelection();
		BPartnerGlobalIDImportTableSqlUpdater.updateBPartnerGlobalIDImortTable(selection);
	}

	@Override
	protected String getImportOrderBySql()
	{
		return I_I_BPartner_GlobalID.COLUMNNAME_GlobalId;
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
	protected ImportRecordResult importRecord(@NonNull IMutable<Object> state,
			@NonNull I_I_BPartner_GlobalID importRecord,
			final boolean isInsertOnly)
	{
		final IBPartnerDAO partnerDAO = Services.get(IBPartnerDAO.class);

		if (importRecord.getC_BPartner_ID() > 0 && !Check.isEmpty(importRecord.getURL3(), true))
		{
			final I_C_BPartner bpartner = partnerDAO.getById(BPartnerId.ofRepoId(importRecord.getC_BPartner_ID()));
			bpartner.setURL3(importRecord.getURL3());
			InterfaceWrapperHelper.save(bpartner);
			return ImportRecordResult.Updated;
		}
		return ImportRecordResult.Nothing;
	}

}
