package de.metas.payment.esr.api.impl;

/*
 * #%L
 * de.metas.payment.esr
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.Query;
import org.compiere.util.DB;

import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.payment.esr.model.I_ESR_ImportLine;

public class ESRImportDAO extends AbstractESRImportDAO
{
	@Override
	public List<I_ESR_ImportLine> retrieveLinesForTrxTypes(final I_ESR_Import esrImport, final List<String> esrTrxTypes)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(esrImport);
		final String trxName = InterfaceWrapperHelper.getTrxName(esrImport);

		final StringBuffer whereClause = new StringBuffer();
		final List<Object> params = new ArrayList<Object>();

		whereClause.append(I_ESR_ImportLine.COLUMNNAME_ESR_Import_ID).append("=?");
		params.add(esrImport.getESR_Import_ID());

		if (esrTrxTypes != null && !esrTrxTypes.isEmpty())
		{
			whereClause.append(" AND ").append(I_ESR_ImportLine.COLUMNNAME_ESRTrxType).append(" IN ").append(DB.buildSqlList(esrTrxTypes, params));
		}

		final List<I_ESR_ImportLine> esrLines = new Query(ctx, I_ESR_ImportLine.Table_Name, whereClause.toString(), trxName)
				.setParameters(params)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(I_ESR_ImportLine.COLUMNNAME_LineNo + "," + I_ESR_ImportLine.COLUMNNAME_ESR_ImportLine_ID) // LineNo should suffice, but who knows :-)
				.list(I_ESR_ImportLine.class);

		return esrLines;
	}

	@Override
	List<I_ESR_ImportLine> fetchLinesForInvoice(final I_ESR_Import esrImport, final I_C_Invoice invoice)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(esrImport);
		final String trxName = InterfaceWrapperHelper.getTrxName(esrImport);

		final String whereClause = I_ESR_ImportLine.COLUMNNAME_ESR_Import_ID + "=? AND "
				+ I_ESR_ImportLine.COLUMNNAME_C_Invoice_ID + "=?";

		return new Query(ctx, I_ESR_ImportLine.Table_Name, whereClause, trxName)
				.setParameters(esrImport.getESR_Import_ID(), invoice.getC_Invoice_ID())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(I_ESR_ImportLine.COLUMNNAME_LineNo + "," + I_ESR_ImportLine.COLUMNNAME_ESR_ImportLine_ID) // LineNo should suffice, but who knows :-)
				.list(I_ESR_ImportLine.class);
	}

	@Override
	public int countLines(final I_ESR_Import esrImport, final Boolean processed)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(esrImport);
		final String trxName = InterfaceWrapperHelper.getTrxName(esrImport);

		final StringBuffer sb = new StringBuffer();
		final List<Object> params = new ArrayList<Object>();

		sb.append(I_ESR_ImportLine.COLUMNNAME_ESR_Import_ID + " = ? ");
		params.add(esrImport.getESR_Import_ID());

		if (processed != null)
		{
			sb.append(" AND " + I_ESR_ImportLine.COLUMNNAME_Processed + " = ? ");
			params.add(processed);
		}

		final String whereClause = sb.toString();

		return new Query(ctx, I_ESR_ImportLine.Table_Name, whereClause, trxName)
				.setParameters(params)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.count();
	}

	@Override
	public Iterator<I_ESR_Import> retrieveESRImports(final Properties ctx, final int orgID)
	{
		final String trxName = ITrx.TRXNAME_None;

		final String whereClause = I_ESR_Import.COLUMNNAME_AD_Org_ID + " =? ";

		return new Query(ctx, I_ESR_Import.Table_Name, whereClause, trxName)
				.setParameters(orgID)
				.setOnlyActiveRecords(true)
				.iterate(I_ESR_Import.class);
	}

	@Override
	public I_ESR_ImportLine fetchLineForESRLineText(final I_ESR_Import esrImport, final String esrImportLineText)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(esrImport);
		final String trxName = InterfaceWrapperHelper.getTrxName(esrImport);

		final StringBuffer whereClause = new StringBuffer();
		final List<Object> params = new ArrayList<Object>();

		whereClause.append(I_ESR_ImportLine.COLUMNNAME_ESR_Import_ID).append("=?");
		params.add(esrImport.getESR_Import_ID());
		whereClause.append(" AND ");
		whereClause.append(I_ESR_ImportLine.COLUMNNAME_ESRLineText).append(" ilike ?");
		final String strippedText = esrImportLineText.trim();
		params.add(strippedText);

		return new Query(ctx, I_ESR_ImportLine.Table_Name, whereClause.toString(), trxName)
				.setParameters(params)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.firstOnly(I_ESR_ImportLine.class);

	}
}
