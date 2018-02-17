package de.metas.datev;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.sql.Timestamp;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryInsertExecutor.QueryInsertExecutorResult;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.IQuery;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import de.metas.datev.model.I_DATEV_Export;
import de.metas.datev.model.I_DATEV_ExportLine;
import de.metas.datev.model.I_RV_DATEV_Export_Fact_Acct_Invoice;

/*
 * #%L
 * metasfresh-datev
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Component
public class DATEVExportLinesRepository
{
	public int deleteAllByExportId(final int datevExportId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_DATEV_ExportLine.class)
				.addEqualsFilter(I_DATEV_ExportLine.COLUMN_DATEV_Export_ID, datevExportId)
				.create()
				.deleteDirectly();
	}

	public int createLinesFromConfig(final int datevExportId)
	{
		Check.assume(datevExportId > 0, "datevExportId > 0");
		final I_DATEV_Export datevExport = load(datevExportId, I_DATEV_Export.class);

		final Timestamp now = SystemTime.asTimestamp();
		final int userId = Env.getAD_User_ID();
		final QueryInsertExecutorResult result = createSourceQuery(datevExport)
				.insertDirectlyInto(I_DATEV_ExportLine.class)
				.mapCommonColumns()
				.mapPrimaryKey()
				.mapColumnToConstant(I_DATEV_ExportLine.COLUMNNAME_DATEV_Export_ID, datevExportId)
				.mapColumnToConstant(I_DATEV_ExportLine.COLUMNNAME_Created, now)
				.mapColumnToConstant(I_DATEV_ExportLine.COLUMNNAME_CreatedBy, userId)
				.mapColumnToConstant(I_DATEV_ExportLine.COLUMNNAME_Updated, now)
				.mapColumnToConstant(I_DATEV_ExportLine.COLUMNNAME_UpdatedBy, userId)
				.mapColumnToConstant(I_DATEV_ExportLine.COLUMNNAME_IsActive, true)
				.execute();

		return result.getRowsInserted();
	}

	private IQuery<I_RV_DATEV_Export_Fact_Acct_Invoice> createSourceQuery(final I_DATEV_Export datevExport)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_RV_DATEV_Export_Fact_Acct_Invoice> queryBuilder = queryBL.createQueryBuilder(I_RV_DATEV_Export_Fact_Acct_Invoice.class);
		if (datevExport.getDateAcctFrom() != null)
		{
			queryBuilder.addCompareFilter(I_RV_DATEV_Export_Fact_Acct_Invoice.COLUMN_DateAcct, Operator.GREATER_OR_EQUAL, datevExport.getDateAcctFrom());
		}
		if (datevExport.getDateAcctTo() != null)
		{
			queryBuilder.addCompareFilter(I_RV_DATEV_Export_Fact_Acct_Invoice.COLUMN_DateAcct, Operator.LESS_OR_EQUAL, datevExport.getDateAcctTo());
		}
		if (datevExport.isExcludeAlreadyExported())
		{
			// TODO: exclude already exported invoices
		}

		return queryBuilder.create();
	}

}
