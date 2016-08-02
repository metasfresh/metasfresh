package org.adempiere.invoice.service.impl;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.LegacyAdapters;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_InvoiceTax;
import org.compiere.model.I_C_LandedCost;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MInvoiceTax;
import org.compiere.model.MLandedCost;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.logging.LogManager;

public class InvoiceDAO extends AbstractInvoiceDAO
{

	public static final Logger logger = LogManager.getLogger(InvoiceDAO.class);

	public I_C_Invoice createInvoice(String trxName)
	{
		return InterfaceWrapperHelper.create(Env.getCtx(), I_C_Invoice.class, trxName);
	}

	@Override
	public I_C_InvoiceLine createInvoiceLine(final org.compiere.model.I_C_Invoice invoice)
	{
		final MInvoice invoicePO = LegacyAdapters.convertToPO(invoice);
		final MInvoiceLine invoiceLinePO = new MInvoiceLine(invoicePO);
		return InterfaceWrapperHelper.create(invoiceLinePO, I_C_InvoiceLine.class);
	}

	@Override
	public I_C_InvoiceLine createInvoiceLine(final String trxName)
	{
		return InterfaceWrapperHelper.create(Env.getCtx(), I_C_InvoiceLine.class, trxName);
	}

	@Override
	public List<I_C_LandedCost> retrieveLandedCosts(
			final I_C_InvoiceLine invoiceLine, final String whereClause,
			final String trxName)
	{
		final List<I_C_LandedCost> list = new ArrayList<I_C_LandedCost>();

		String sql = "SELECT * FROM C_LandedCost WHERE C_InvoiceLine_ID=? ";
		if (whereClause != null)
			sql += whereClause;
		final PreparedStatement pstmt = DB.prepareStatement(sql, trxName);
		ResultSet rs = null;

		try
		{

			pstmt.setInt(1, invoiceLine.getC_InvoiceLine_ID());
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				MLandedCost lc = new MLandedCost(Env.getCtx(), rs, trxName);
				list.add(lc);
			}

		}
		catch (Exception e)
		{
			logger.error("getLandedCost", e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		return list;
	} // getLandedCost

	@Override
	public I_C_LandedCost createLandedCost(String trxName)
	{
		return new MLandedCost(Env.getCtx(), 0, trxName);
	}

	@Override
	public Collection<MInvoiceLine> retrieveReferringLines(
			final Properties ctx, final int invoiceLineId, final String trxName)
	{

		final String tableName = I_C_InvoiceLine.Table_Name;

		final String whereClause = I_C_InvoiceLine.COLUMNNAME_Ref_InvoiceLine_ID
				+ "=?";

		return new Query(ctx, tableName, whereClause, trxName).setParameters(
				invoiceLineId).setOnlyActiveRecords(true).setClient_ID().list();

	}

	@Override
	public I_C_Invoice retrieveInvoiceByInvoiceNoAndBPartnerID(Properties ctx, String invoiceNo, int bPartnerID)
	{
		if (bPartnerID <= 0)
		{
			return null;
		}

		if (invoiceNo == null)
		{
			return null;
		}
		final String trxName = ITrx.TRXNAME_None;

		final String whereClause = I_C_Invoice.COLUMNNAME_DocumentNo + "=? AND "
				+ I_C_Invoice.COLUMNNAME_C_BPartner_ID + "=?";

		final I_C_Invoice invoice = new Query(ctx, I_C_Invoice.Table_Name, whereClause, trxName)
				.setParameters(invoiceNo, bPartnerID)
				.setClient_ID()
				.firstOnly(I_C_Invoice.class);

		return invoice;
	}

	@Override
	public Iterator<I_C_Invoice> retrieveOpenInvoicesByOrg(final I_AD_Org adOrg)
	{
		final int adOrgID = adOrg.getAD_Org_ID();
		Check.assume(adOrgID > 0, "Valid transactional Org: {}", adOrg);

		final Properties ctx = InterfaceWrapperHelper.getCtx(adOrg);
		final String trxName = InterfaceWrapperHelper.getTrxName(adOrg);

		final StringBuilder whereClause = new StringBuilder();
		final List<Object> params = new ArrayList<Object>();

		whereClause.append(I_C_Invoice.COLUMNNAME_AD_Org_ID).append("=?");
		params.add(adOrgID);

		whereClause.append(" AND ").append(I_C_Invoice.COLUMNNAME_IsPaid).append("=?");
		params.add(false);

		return new Query(ctx, I_C_Invoice.Table_Name, whereClause.toString(), trxName)
				.setParameters(params)
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.iterate(I_C_Invoice.class, false);
	}

	@Override
	public List<I_C_InvoiceTax> retrieveTaxes(org.compiere.model.I_C_Invoice invoice)
	{
		final MInvoice invoicePO = LegacyAdapters.convertToPO(invoice);
		final MInvoiceTax[] resultArray = invoicePO.getTaxes(true);
		final List<I_C_InvoiceTax> result = new ArrayList<I_C_InvoiceTax>();
		for (final MInvoiceTax tax : resultArray)
		{
			result.add(tax);
		}
		
		// NOTE: make sure we are returning a read-write list because some API rely on this (doing sorting)

		return result;
	}
}
