package org.adempiere.tax.api.impl;

/*
 * #%L
 * ADempiere ERP - Base
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


import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.TaxCriteriaNotFoundException;
import org.adempiere.exceptions.TaxForChangeNotFoundException;
import org.adempiere.exceptions.TaxNoExemptFoundException;
import org.adempiere.exceptions.TaxNotFoundException;
import org.adempiere.tax.api.ITaxBL;
import org.compiere.model.I_C_Tax;
import org.compiere.model.MLocation;
import org.compiere.model.MTax;
import org.compiere.model.MTaxPostal;
import org.compiere.util.CLogMgt;
import org.compiere.util.CLogger;
import org.compiere.util.DB;

/**
 * <b>IMPORTANT:</b> note that we actually don't use this class, but instead manually register <code>de.metas.tax.api.impl.TaxBL</code> (from de.metas.swat) as the service implementation of
 * {@link ITaxBL}.
 *
 */
public class TaxBL implements ITaxBL
{
	/**	Logger							*/
	static private CLogger			log = CLogger.getCLogger (TaxBL.class);

	
	/**************************************************************************
	 *	Get Tax ID - converts parameters to call Get Tax.
	 *  <pre>
	 *		M_Product_ID/C_Charge_ID	->	C_TaxCategory_ID
	 *		billDate, shipDate			->	billDate, shipDate
	 *		AD_Org_ID					->	billFromC_Location_ID
	 *		M_Warehouse_ID				->	shipFromC_Location_ID
	 *		billC_BPartner_Location_ID  ->	billToC_Location_ID
	 *		shipC_BPartner_Location_ID 	->	shipToC_Location_ID
	 *
	 *  if IsSOTrx is false, bill and ship are reversed
	 *  </pre>
	 * 	@param ctx	context
	 * 	@param M_Product_ID product
	 * 	@param C_Charge_ID product
	 * 	@param billDate invoice date
	 * 	@param shipDate ship date (ignored)
	 * 	@param AD_Org_ID org
	 * 	@param M_Warehouse_ID warehouse (ignored)
	 * 	@param billC_BPartner_Location_ID invoice location
	 * 	@param shipC_BPartner_Location_ID ship location (ignored)
	 * 	@param IsSOTrx is a sales trx
	 * 	@return C_Tax_ID
	 *  @throws TaxCriteriaNotFoundException if a criteria was not found
	 */
	@Override
	public int get (Properties ctx, int M_Product_ID, int C_Charge_ID,
		Timestamp billDate, Timestamp shipDate,
		int AD_Org_ID, int M_Warehouse_ID,
		int billC_BPartner_Location_ID, int shipC_BPartner_Location_ID,
		boolean IsSOTrx)
	{
		if (M_Product_ID != 0)
			return getProduct (ctx, M_Product_ID, billDate, shipDate, AD_Org_ID, M_Warehouse_ID,
				billC_BPartner_Location_ID, shipC_BPartner_Location_ID, IsSOTrx);
		else if (C_Charge_ID != 0)
			return getCharge (ctx, C_Charge_ID, billDate, shipDate, AD_Org_ID, M_Warehouse_ID,
				billC_BPartner_Location_ID, shipC_BPartner_Location_ID, IsSOTrx);
		else
			return getExemptTax (ctx, AD_Org_ID);
	}	//	get

	/**
	 *	Get Tax ID - converts parameters to call Get Tax.
	 *  <pre>
	 *		C_Charge_ID					->	C_TaxCategory_ID
	 *		billDate					->	billDate
	 *		shipDate					->	shipDate (ignored)
	 *		AD_Org_ID					->	billFromC_Location_ID
	 *		M_Warehouse_ID				->	shipFromC_Location_ID (ignored)
	 *		billC_BPartner_Location_ID  ->	billToC_Location_ID
	 *		shipC_BPartner_Location_ID 	->	shipToC_Location_ID (ignored)
	 *
	 *  if IsSOTrx is false, bill and ship are reversed
	 *  </pre>
	 * 	@param ctx	context
	 * 	@param C_Charge_ID product
	 * 	@param billDate invoice date
	 * 	@param shipDate ship date (ignored)
	 * 	@param AD_Org_ID org
	 * 	@param M_Warehouse_ID warehouse (ignored)
	 * 	@param billC_BPartner_Location_ID invoice location
	 * 	@param shipC_BPartner_Location_ID ship location (ignored)
	 * 	@param IsSOTrx is a sales trx
	 * 	@return C_Tax_ID
	 *  @throws TaxForChangeNotFoundException if criteria not found for given change
	 *  @throws TaxCriteriaNotFoundException if a criteria was not found
	 */
	public int getCharge (Properties ctx, int C_Charge_ID,
		Timestamp billDate, Timestamp shipDate,
		int AD_Org_ID, int M_Warehouse_ID,
		int billC_BPartner_Location_ID, int shipC_BPartner_Location_ID,
		boolean IsSOTrx)
	{
		/* ship location from warehouse is plainly ignored below */
		// if (M_Warehouse_ID <= 0)
			// M_Warehouse_ID = Env.getContextAsInt(ctx, "M_Warehouse_ID");
		// if (M_Warehouse_ID <= 0)
		// {
			// throw new TaxForChangeNotFoundException(C_Charge_ID, AD_Org_ID, M_Warehouse_ID,
						// billC_BPartner_Location_ID, shipC_BPartner_Location_ID,
						// "@NotFound@ @M_Warehouse_ID@");
		// }
		int C_TaxCategory_ID = 0;
		int shipFromC_Location_ID = 0;
		int shipToC_Location_ID = 0;
		int billFromC_Location_ID = 0;
		int billToC_Location_ID = 0;
		String IsTaxExempt = null;
		String IsSOTaxExempt = null;
		String IsPOTaxExempt = null;

		//	Get all at once
		final String sql = "SELECT c.C_TaxCategory_ID, il.C_Location_ID, b.IsTaxExempt, b.IsPOTaxExempt,"
			 + " w.C_Location_ID, sl.C_Location_ID "
			 + "FROM C_Charge c, AD_OrgInfo o,"
			 + " C_BPartner_Location il INNER JOIN C_BPartner b ON (il.C_BPartner_ID=b.C_BPartner_ID) "
			 + " LEFT OUTER JOIN M_Warehouse w ON (w.M_Warehouse_ID=?), C_BPartner_Location sl "
			 + "WHERE c.C_Charge_ID=?"
			 + " AND o.AD_Org_ID=?"
			 + " AND il.C_BPartner_Location_ID=?"
			 + " AND sl.C_BPartner_Location_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt (1, M_Warehouse_ID);
			pstmt.setInt (2, C_Charge_ID);
			pstmt.setInt (3, AD_Org_ID);
			pstmt.setInt (4, billC_BPartner_Location_ID);
			pstmt.setInt (5, shipC_BPartner_Location_ID);
			rs = pstmt.executeQuery ();
			boolean found = false;
			if (rs.next ())
			{
				C_TaxCategory_ID = rs.getInt (1);
				billToC_Location_ID = rs.getInt (2);
				IsSOTaxExempt = rs.getString (3);
				IsPOTaxExempt = rs.getString (4);
				IsTaxExempt = IsSOTrx ? IsSOTaxExempt : IsPOTaxExempt;
				shipFromC_Location_ID = rs.getInt (5);
				shipToC_Location_ID = rs.getInt (6);
				found = true;
			}
			DB.close(rs, pstmt);
			//
			if (!found)
			{
				throw new TaxForChangeNotFoundException(C_Charge_ID, AD_Org_ID, M_Warehouse_ID,
						billC_BPartner_Location_ID, shipC_BPartner_Location_ID,
						null);
			}
			else if ("Y".equals (IsTaxExempt))
			{
				return getExemptTax (ctx, AD_Org_ID);
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		//	Reverese for PO
		if (!IsSOTrx)
		{
			int temp = billFromC_Location_ID;
			billFromC_Location_ID = billToC_Location_ID;
			billToC_Location_ID = temp;
			temp = shipFromC_Location_ID;
			shipFromC_Location_ID = shipToC_Location_ID;
			shipToC_Location_ID = temp;
		}
		//
		log.fine("getCharge - C_TaxCategory_ID=" + C_TaxCategory_ID
		  + ", billFromC_Location_ID=" + billFromC_Location_ID
		  + ", billToC_Location_ID=" + billToC_Location_ID
		  + ", shipFromC_Location_ID=" + shipFromC_Location_ID
		  + ", shipToC_Location_ID=" + shipToC_Location_ID);
		return get (ctx, C_TaxCategory_ID, IsSOTrx,
		  shipDate, shipFromC_Location_ID, shipToC_Location_ID,
		  billDate, billFromC_Location_ID, billToC_Location_ID);
	}	//	getCharge


	/**
	 *	Get Tax ID - converts parameters to call Get Tax.
	 *  <pre>
	 *		M_Product_ID				->	C_TaxCategory_ID
	 *		billDate					->	billDate
	 *		shipDate					->	shipDate (ignored)
	 *		AD_Org_ID					->	billFromC_Location_ID
	 *		M_Warehouse_ID				->	shipFromC_Location_ID (ignored)
	 *		billC_BPartner_Location_ID  ->	billToC_Location_ID
	 *		shipC_BPartner_Location_ID 	->	shipToC_Location_ID (ignored)
	 *
	 *  if IsSOTrx is false, bill and ship are reversed
	 *  </pre>
	 * 	@param ctx	context
	 * 	@param M_Product_ID product
	 * 	@param billDate invoice date
	 * 	@param shipDate ship date (ignored)
	 * 	@param AD_Org_ID org
	 * 	@param M_Warehouse_ID warehouse (ignored)
	 * 	@param billC_BPartner_Location_ID invoice location
	 * 	@param shipC_BPartner_Location_ID ship location (ignored)
	 * 	@param IsSOTrx is a sales trx
	 * 	@return C_Tax_ID
	 *  If error it returns 0 and sets error log (TaxCriteriaNotFound)
	 */
	public int getProduct (Properties ctx, int M_Product_ID,
		Timestamp billDate, Timestamp shipDate,
		int AD_Org_ID, int M_Warehouse_ID,
		int billC_BPartner_Location_ID, int shipC_BPartner_Location_ID,
		boolean IsSOTrx)
	{
		String variable = "";
		int C_TaxCategory_ID = 0;
		int shipFromC_Location_ID = 0;
		int shipToC_Location_ID = 0;
		int billFromC_Location_ID = 0;
		int billToC_Location_ID = 0;
		String IsTaxExempt = null;
		String IsSOTaxExempt = null;
		String IsPOTaxExempt = null;

		String sql = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			//	Get all at once
			sql = "SELECT p.C_TaxCategory_ID, il.C_Location_ID, b.IsTaxExempt, b.IsPOTaxExempt, "
				+ " w.C_Location_ID, sl.C_Location_ID "
				+ "FROM M_Product p, AD_OrgInfo o,"
				+ " C_BPartner_Location il INNER JOIN C_BPartner b ON (il.C_BPartner_ID=b.C_BPartner_ID) "
				+ " LEFT OUTER JOIN M_Warehouse w ON (w.M_Warehouse_ID=?), C_BPartner_Location sl "
				+ "WHERE p.M_Product_ID=?"
				+ " AND o.AD_Org_ID=?"
				+ " AND il.C_BPartner_Location_ID=?"
				+ " AND sl.C_BPartner_Location_ID=?";
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, M_Warehouse_ID);
			pstmt.setInt(2, M_Product_ID);
			pstmt.setInt(3, AD_Org_ID);
			pstmt.setInt(4, billC_BPartner_Location_ID);
			pstmt.setInt(5, shipC_BPartner_Location_ID);
			rs = pstmt.executeQuery();
			boolean found = false;
			if (rs.next())
			{
				C_TaxCategory_ID = rs.getInt(1);
				billToC_Location_ID = rs.getInt(2);
				IsSOTaxExempt = rs.getString(3);
				IsPOTaxExempt = rs.getString(4);
				IsTaxExempt = IsSOTrx ? IsSOTaxExempt : IsPOTaxExempt;
				shipFromC_Location_ID = rs.getInt(5);
				shipToC_Location_ID = rs.getInt(6);
				found = true;
			}
			DB.close(rs, pstmt);
			//
			if (found && "Y".equals(IsTaxExempt))
			{
				log.fine("getProduct - Business Partner is Tax exempt");
				return getExemptTax(ctx, AD_Org_ID);
			}
			else if (found)
			{
				if (!IsSOTrx)
				{
					int temp = billFromC_Location_ID;
					billFromC_Location_ID = billToC_Location_ID;
					billToC_Location_ID = temp;
					temp = shipFromC_Location_ID;
					shipFromC_Location_ID = shipToC_Location_ID;
					shipToC_Location_ID = temp;
				}
				log.fine("getProduct - C_TaxCategory_ID=" + C_TaxCategory_ID
					+ ", billFromC_Location_ID=" + billFromC_Location_ID
					+ ", billToC_Location_ID=" + billToC_Location_ID
					+ ", shipFromC_Location_ID=" + shipFromC_Location_ID
					+ ", shipToC_Location_ID=" + shipToC_Location_ID);
				return get(ctx, C_TaxCategory_ID, IsSOTrx,
					shipDate, shipFromC_Location_ID, shipToC_Location_ID,
					billDate, billFromC_Location_ID, billToC_Location_ID);
			}

			// ----------------------------------------------------------------

			//	Detail for error isolation

		//	M_Product_ID				->	C_TaxCategory_ID
			variable = "M_Product_ID";
			sql = "SELECT C_TaxCategory_ID FROM M_Product WHERE M_Product_ID=?"; // metas 05129: WARNING: C_TaxCategory_ID not linked to M_Product
			C_TaxCategory_ID = DB.getSQLValueEx(null, sql, M_Product_ID);
			found = C_TaxCategory_ID != -1;
			if (C_TaxCategory_ID <= 0)
			{
				throw new TaxCriteriaNotFoundException(variable, M_Product_ID);
			}
			log.fine("getProduct - C_TaxCategory_ID=" + C_TaxCategory_ID);

		//	AD_Org_ID					->	billFromC_Location_ID
			variable = "AD_Org_ID";
			sql = "SELECT C_Location_ID FROM AD_OrgInfo WHERE AD_Org_ID=?";
			billFromC_Location_ID = DB.getSQLValueEx(null, sql, AD_Org_ID);
			found = billFromC_Location_ID != -1;
			if (billFromC_Location_ID <= 0)
			{
				throw new TaxCriteriaNotFoundException(variable, AD_Org_ID);
			}

		//	billC_BPartner_Location_ID  ->	billToC_Location_ID
			variable = "BillTo_ID";
			sql = "SELECT l.C_Location_ID, b.IsTaxExempt, b.IsPOTaxExempt "
				+ " FROM C_BPartner_Location l"
				+ " INNER JOIN C_BPartner b ON (l.C_BPartner_ID=b.C_BPartner_ID) "
				+ " WHERE C_BPartner_Location_ID=?";
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, billC_BPartner_Location_ID);
			rs = pstmt.executeQuery();
			found = false;
			if (rs.next())
			{
				billToC_Location_ID = rs.getInt(1);
				IsSOTaxExempt = rs.getString(2);
				IsPOTaxExempt = rs.getString(3);
				IsTaxExempt = IsSOTrx ? IsSOTaxExempt : IsPOTaxExempt;
				found = true;
			}
			DB.close(rs, pstmt);
			if (billToC_Location_ID <= 0)
			{
				throw new TaxCriteriaNotFoundException(variable, billC_BPartner_Location_ID);
			}
			if ("Y".equals(IsTaxExempt))
				return getExemptTax(ctx, AD_Org_ID);

			//  Reverse for PO
			if (!IsSOTrx)
			{
				int temp = billFromC_Location_ID;
				billFromC_Location_ID = billToC_Location_ID;
				billToC_Location_ID = temp;
			}
			log.fine("getProduct - billFromC_Location_ID = " + billFromC_Location_ID);
			log.fine("getProduct - billToC_Location_ID = " + billToC_Location_ID);

			//-----------------------------------------------------------------

		//	M_Warehouse_ID				->	shipFromC_Location_ID
			variable = "M_Warehouse_ID";
			sql = "SELECT C_Location_ID FROM M_Warehouse WHERE M_Warehouse_ID=?";
			shipFromC_Location_ID = DB.getSQLValueEx(null, sql, M_Warehouse_ID);
			found = shipFromC_Location_ID != -1;
			if (shipFromC_Location_ID <= 0)
			{
				throw new TaxCriteriaNotFoundException(variable, M_Warehouse_ID);
			}

		//	shipC_BPartner_Location_ID 	->	shipToC_Location_ID
			variable = "C_BPartner_Location_ID";
			sql = "SELECT C_Location_ID FROM C_BPartner_Location WHERE C_BPartner_Location_ID=?";
			shipToC_Location_ID = DB.getSQLValueEx(null, sql, shipC_BPartner_Location_ID);
			found = shipToC_Location_ID != -1;
			if (shipToC_Location_ID <= 0)
			{
				throw new TaxCriteriaNotFoundException(variable, shipC_BPartner_Location_ID);
			}

			//  Reverse for PO
			if (!IsSOTrx)
			{
				int temp = shipFromC_Location_ID;
				shipFromC_Location_ID = shipToC_Location_ID;
				shipToC_Location_ID = temp;
			}
			log.fine("getProduct - shipFromC_Location_ID = " + shipFromC_Location_ID);
			log.fine("getProduct - shipToC_Location_ID = " + shipToC_Location_ID);
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		return get (ctx, C_TaxCategory_ID, IsSOTrx,
			shipDate, shipFromC_Location_ID, shipToC_Location_ID,
			billDate, billFromC_Location_ID, billToC_Location_ID);
	}	//	getProduct

	/**
	 * Get Exempt Tax Code
	 * @param ctx context
	 * @param AD_Org_ID org to find client
	 * @return C_Tax_ID
	 * @throws TaxNoExemptFoundException if no tax exempt found
	 */
	@Override
	public int getExemptTax (Properties ctx, int AD_Org_ID)
	{
		final String sql = "SELECT t.C_Tax_ID "
			+ "FROM C_Tax t"
			+ " INNER JOIN AD_Org o ON (t.AD_Client_ID=o.AD_Client_ID) "
			+ "WHERE t.IsTaxExempt='Y' AND o.AD_Org_ID=? "
			+ "ORDER BY t.Rate DESC";
		int C_Tax_ID = DB.getSQLValueEx(null, sql, AD_Org_ID);
		log.fine("getExemptTax - TaxExempt=Y - C_Tax_ID=" + C_Tax_ID);
		if (C_Tax_ID <= 0)
		{
			throw new TaxNoExemptFoundException(AD_Org_ID);
		}
		else
		{
			return C_Tax_ID;
		}
	}	//	getExemptTax

	
	/**************************************************************************
	 *	Get Tax ID (Detail).
	 *  @param ctx context
	 *	@param C_TaxCategory_ID tax category
	 * 	@param IsSOTrx Sales Order Trx
	 *	@param shipDate ship date (ignored)
	 *	@param shipFromC_Location_ID ship from (ignored)
	 *	@param shipToC_Location_ID ship to (ignored)
	 *	@param billDate invoice date
	 *	@param billFromC_Location_ID invoice from
	 *	@param billToC_Location_ID invoice to
	 *	@return C_Tax_ID
	 *  @throws TaxNotFoundException if no tax found for given criteria
	 */
	protected static int get (Properties ctx,
		int C_TaxCategory_ID, boolean IsSOTrx,
		Timestamp shipDate, int shipFromC_Location_ID, int shipToC_Location_ID,
		Timestamp billDate, int billFromC_Location_ID, int billToC_Location_ID)
	{
		//	C_TaxCategory contains CommodityCode
		
		//	API to Tax Vendor comes here

		if (CLogMgt.isLevelFine())
		{
			log.info("get(Detail) - Category=" + C_TaxCategory_ID 
				+ ", SOTrx=" + IsSOTrx);
			log.config("get(Detail) - BillFrom=" + billFromC_Location_ID 
				+ ", BillTo=" + billToC_Location_ID + ", BillDate=" + billDate);
		}

		MTax[] taxes = MTax.getAll (ctx);
		MLocation lFrom = new MLocation (ctx, billFromC_Location_ID, null); 
		MLocation lTo = new MLocation (ctx, billToC_Location_ID, null); 
		log.finer("From=" + lFrom);
		log.finer("To=" + lTo);
		
		for (int i = 0; i < taxes.length; i++)
		{
			MTax tax = taxes[i];
			log.finest(tax.toString());
			//
			if (tax.getC_TaxCategory_ID() != C_TaxCategory_ID
				|| !tax.isActive() 
				|| tax.getParent_Tax_ID() != 0)	//	user parent tax
				continue;
			if (IsSOTrx && MTax.SOPOTYPE_PurchaseTax.equals(tax.getSOPOType()))
				continue;
			if (!IsSOTrx && MTax.SOPOTYPE_SalesTax.equals(tax.getSOPOType()))
				continue;
			
			log.finest("From Country - " + (tax.getC_Country_ID() == lFrom.getC_Country_ID() 
				|| tax.getC_Country_ID() == 0));
			log.finest("From Region - " + (tax.getC_Region_ID() == lFrom.getC_Region_ID() 
				|| tax.getC_Region_ID() == 0));
			log.finest("To Country - " + (tax.getTo_Country_ID() == lTo.getC_Country_ID() 
				|| tax.getTo_Country_ID() == 0));
			log.finest("To Region - " + (tax.getTo_Region_ID() == lTo.getC_Region_ID() 
				|| tax.getTo_Region_ID() == 0));
			log.finest("Date valid - " + (!tax.getValidFrom().after(billDate)));
			
				//	From Country
			if ((tax.getC_Country_ID() == lFrom.getC_Country_ID() 
					|| tax.getC_Country_ID() == 0)
				//	From Region
				&& (tax.getC_Region_ID() == lFrom.getC_Region_ID() 
					|| tax.getC_Region_ID() == 0)
				//	To Country
				&& (tax.getTo_Country_ID() == lTo.getC_Country_ID() 
					|| tax.getTo_Country_ID() == 0)
				//	To Region
				&& (tax.getTo_Region_ID() == lTo.getC_Region_ID() 
					|| tax.getTo_Region_ID() == 0)
				//	Date
				&& !tax.getValidFrom().after(billDate)
				)
			{
				if (!tax.isPostal())
					return tax.getC_Tax_ID();
				//
				MTaxPostal[] postals = tax.getPostals(false);
				for (int j = 0; j < postals.length; j++)
				{
					MTaxPostal postal = postals[j];
					if (postal.isActive()
						//	Postal From is mandatory
						&& postal.getPostal().startsWith(lFrom.getPostal())
						//	Postal To is optional
						&& (postal.getPostal_To() == null 
							|| postal.getPostal_To().startsWith(lTo.getPostal()))
						)
						return tax.getC_Tax_ID();
				}	//	for all postals
			}
		}	//	for all taxes

		//	Default Tax
		for (int i = 0; i < taxes.length; i++)
		{
			MTax tax = taxes[i];
			if (!tax.isDefault() || !tax.isActive()
				|| tax.getParent_Tax_ID() != 0)	//	user parent tax
				continue;
			if (IsSOTrx && MTax.SOPOTYPE_PurchaseTax.equals(tax.getSOPOType()))
				continue;
			if (!IsSOTrx && MTax.SOPOTYPE_SalesTax.equals(tax.getSOPOType()))
				continue;
			log.fine("get (default) - " + tax);
			return tax.getC_Tax_ID();
		}	//	for all taxes
		
		throw new TaxNotFoundException(C_TaxCategory_ID, IsSOTrx,
				shipDate, shipFromC_Location_ID, shipToC_Location_ID,
				billDate, billFromC_Location_ID, billToC_Location_ID);
	}	//	get

	/**
	 * Throws an {@link UnsupportedOperationException}.
	 */
	@Override
	public int retrieveTaxIdForCategory(Properties ctx, int countryFromId, int orgId, org.compiere.model.I_C_BPartner_Location bpLocTo, Timestamp billDate, int taxCategoryId, boolean isSOTrx, String trxName)
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Throws an {@link UnsupportedOperationException}.
	 */
	@Override
	public BigDecimal calculateTax(I_C_Tax tax, BigDecimal amount, boolean taxIncluded, int scale)
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Throws an {@link UnsupportedOperationException}. 
	 */
	@Override
	public void setupIfIsWholeTax(I_C_Tax tax)
	{
		throw new UnsupportedOperationException();
	}
}
