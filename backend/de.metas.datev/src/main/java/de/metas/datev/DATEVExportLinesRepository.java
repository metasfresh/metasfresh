package de.metas.datev;

import de.metas.cache.CacheMgt;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.datev.model.I_DATEV_Export;
import de.metas.datev.model.I_DATEV_ExportLine;
import de.metas.datev.model.I_RV_DATEV_Export_Fact_Acct_Invoice;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryInsertExecutor.QueryInsertExecutorResult;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.IQuery;
import org.springframework.stereotype.Component;

import static org.adempiere.model.InterfaceWrapperHelper.load;

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
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public int createLines(@NonNull final DATEVExportCreateLinesRequest request)
	{
		deleteLinesByExportId(request.getDatevExportId());

		final QueryInsertExecutorResult result = createSourceQuery(request)
				.insertDirectlyInto(I_DATEV_ExportLine.class)
				.mapCommonColumns()
				.mapPrimaryKey()
				.mapColumnToConstant(I_DATEV_ExportLine.COLUMNNAME_DATEV_Export_ID, request.getDatevExportId())
				.mapColumnToConstant(I_DATEV_ExportLine.COLUMNNAME_Created, request.getNow())
				.mapColumnToConstant(I_DATEV_ExportLine.COLUMNNAME_CreatedBy, request.getUserId())
				.mapColumnToConstant(I_DATEV_ExportLine.COLUMNNAME_Updated, request.getNow())
				.mapColumnToConstant(I_DATEV_ExportLine.COLUMNNAME_UpdatedBy, request.getUserId())
				.mapColumnToConstant(I_DATEV_ExportLine.COLUMNNAME_IsActive, true)
				.execute();

		CacheMgt.get().resetLocalNowAndBroadcastOnTrxCommit(ITrx.TRXNAME_ThreadInherited, CacheInvalidateMultiRequest.rootRecord(I_DATEV_Export.Table_Name, request.getDatevExportId()));

		return result.getRowsInserted();
	}

	private void deleteLinesByExportId(@NonNull final DATEVExportId datevExportId)
	{
		queryBL.createQueryBuilder(I_DATEV_ExportLine.class)
				.addEqualsFilter(I_DATEV_ExportLine.COLUMN_DATEV_Export_ID, datevExportId)
				.create()
				.deleteDirectly();
	}

	private static I_DATEV_Export getById(final DATEVExportId datevExportId)
	{
		return load(datevExportId, I_DATEV_Export.class);
	}

	private IQuery<I_RV_DATEV_Export_Fact_Acct_Invoice> createSourceQuery(final DATEVExportCreateLinesRequest request)
	{
		final I_DATEV_Export datevExport = getById(request.getDatevExportId());

		final IQueryBuilder<I_RV_DATEV_Export_Fact_Acct_Invoice> queryBuilder = queryBL.createQueryBuilder(I_RV_DATEV_Export_Fact_Acct_Invoice.class)
				.orderBy(I_RV_DATEV_Export_Fact_Acct_Invoice.COLUMNNAME_DateAcct)
				.orderBy(I_RV_DATEV_Export_Fact_Acct_Invoice.COLUMNNAME_C_Invoice_ID)
				.orderBy(I_RV_DATEV_Export_Fact_Acct_Invoice.COLUMNNAME_RV_DATEV_Export_Fact_Acct_Invoice_ID)
				.addEqualsFilter(I_RV_DATEV_Export_Fact_Acct_Invoice.COLUMNNAME_AD_Org_ID, datevExport.getAD_Org_ID());

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
			final IQuery<I_DATEV_ExportLine> exportLinesQuery = queryBL.createQueryBuilder(I_DATEV_ExportLine.class)
					.create();
			queryBuilder.addNotInSubQueryFilter(
					I_RV_DATEV_Export_Fact_Acct_Invoice.COLUMN_C_Invoice_ID,
					I_DATEV_ExportLine.COLUMN_C_Invoice_ID,
					exportLinesQuery);
		}

		return queryBuilder.create()
				.setSqlFromParameter("p_IsOneLinePerInvoiceTax", request.isOneLinePerInvoiceTax())
				.setSqlFromParameter("p_IsSwitchCreditMemo", datevExport.isSwitchCreditMemo())
				.setSqlFromParameter("p_IsNegateInboundAmounts", datevExport.isNegateInboundAmounts())
				;
	}

}
