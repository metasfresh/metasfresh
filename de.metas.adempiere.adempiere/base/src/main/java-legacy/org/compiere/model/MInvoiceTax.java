/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.util.Services;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.tax.api.ITaxBL;

/**
 *	Invoice Tax Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MInvoiceTax.java,v 1.5 2006/10/06 00:42:24 jjanke Exp $
 *  
 *  @author Teo Sarca, www.arhipac.ro
 *  		<li>FR [ 2214883 ] Remove SQL code and Replace for Query
 */
public class MInvoiceTax extends X_C_InvoiceTax
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5560880305482497098L;


	/**
	 * 	Get Tax Line for Invoice Line
	 *	@param line invoice line
	 *	@param precision currency precision
	 *	@param oldTax if true old tax is returned
	 *	@param trxName transaction name
	 *	@return existing or new tax
	 */
	public static MInvoiceTax get (MInvoiceLine line, int precision, boolean oldTax, String trxName)
	{
		if (line == null || line.getC_Invoice_ID() <= 0)
		{
			return null;
		}
		
		int C_Tax_ID = line.getC_Tax_ID();
		final boolean isOldTax = oldTax && line.is_ValueChanged(MInvoiceLine.COLUMNNAME_C_Tax_ID); 
		if (isOldTax)
		{
			Object old = line.get_ValueOld(MInvoiceLine.COLUMNNAME_C_Tax_ID);
			if (old == null)
				return null;
			C_Tax_ID = ((Integer)old).intValue();
		}
		
		if (C_Tax_ID <= 0)
		{
			final AdempiereException e = new AdempiereException("@NotFound@ @C_Tax_ID@ ("+line+")");
			s_log.log(Level.WARNING, e.getLocalizedMessage(), e);
			return null;
		}
		
		MInvoiceTax retValue = new Query(line.getCtx(), Table_Name, "C_Invoice_ID=? AND C_Tax_ID=?", trxName)
						.setParameters(new Object[]{line.getC_Invoice_ID(), C_Tax_ID})
						.firstOnly();
		if (retValue != null)
		{
			retValue.set_TrxName(trxName);
			retValue.setPrecision(precision);
			s_log.fine("(old=" + oldTax + ") " + retValue);
			return retValue;
		}
		// If the old tax was required and there is no MInvoiceTax for that
		// return null, and not create another MInvoiceTax - teo_sarca [ 1583825 ]
		else
		{
			if (isOldTax)
			{
				return null;
			}
		}
		
		final boolean taxIncluded = Services.get(IInvoiceBL.class).isTaxIncluded(line);
		final I_C_Tax tax = line.getC_Tax();

		
		//	Create New
		retValue = new MInvoiceTax(line.getCtx(), 0, trxName);
		retValue.set_TrxName(trxName);
		retValue.setClientOrg(line);
		retValue.setC_Invoice_ID(line.getC_Invoice_ID());
		retValue.setC_Tax(tax);
		retValue.setIsWholeTax(tax.isWholeTax());
		retValue.setPrecision(precision);
		retValue.setIsTaxIncluded(taxIncluded);
		s_log.fine("(new) " + retValue);
		return retValue;
	}	//	get
	
	/**	Static Logger	*/
	private static CLogger	s_log	= CLogger.getCLogger (MInvoiceTax.class);
	
	
	/**************************************************************************
	 * 	Persistency Constructor
	 *	@param ctx context
	 *	@param ignored ignored
	 *	@param trxName transaction
	 */
	public MInvoiceTax (Properties ctx, int ignored, String trxName)
	{
		super(ctx, 0, trxName);
		if (ignored != 0)
			throw new IllegalArgumentException("Multi-Key");
		setTaxAmt (Env.ZERO);
		setTaxBaseAmt (Env.ZERO);
		setIsTaxIncluded(false);
	}	//	MInvoiceTax

	/**
	 * 	Load Constructor.
	 * 	Set Precision and TaxIncluded for tax calculations!
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MInvoiceTax (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MInvoiceTax
	
	/** Tax							*/
	private MTax 		m_tax = null;
	/** Cached Precision			*/
	private Integer		m_precision = null;
	
	
	/**
	 * 	Get Precision
	 * 	@return Returns the precision or 2
	 */
	private int getPrecision ()
	{
		if (m_precision == null)
			return 2;
		return m_precision.intValue();
	}	//	getPrecision

	/**
	 * 	Set Precision
	 *	@param precision The precision to set.
	 */
	protected void setPrecision (int precision)
	{
		m_precision = new Integer(precision);
	}	//	setPrecision

	/**
	 * 	Get Tax
	 *	@return tax
	 */
	protected MTax getTax()
	{
		if (m_tax == null)
			m_tax = MTax.get(getCtx(), getC_Tax_ID());
		return m_tax;
	}	//	getTax
	
	
	/**************************************************************************
	 * Calculate/Set Tax Base Amt from Invoice Lines.
	 * 
	 * If there were no invoice lines found for this tax, this record will be inactivated. In this way, the caller method can know about this and it can decide if this record will be deleted.
	 * 
	 * @return true if tax calculated
	 */
	public boolean calculateTaxFromLines ()
	{
		final ITaxBL taxBL = Services.get(ITaxBL.class);
		
		BigDecimal taxBaseAmt = Env.ZERO;
		BigDecimal taxAmt = Env.ZERO;
		boolean foundInvoiceLines = false;
		//
		final boolean documentLevel = getTax().isDocumentLevel();
		final I_C_Tax tax = getTax();
		//
		final String sql = "SELECT il.LineNetAmt, COALESCE(il.TaxAmt,0), i.IsSOTrx "
			+ "FROM C_InvoiceLine il"
			+ " INNER JOIN C_Invoice i ON (il.C_Invoice_ID=i.C_Invoice_ID) "
			+ "WHERE il.C_Invoice_ID=? AND il.C_Tax_ID=? AND il.IsActive='Y'";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt (1, getC_Invoice_ID());
			pstmt.setInt (2, getC_Tax_ID());
			rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				foundInvoiceLines = true;
				//	BaseAmt
				BigDecimal baseAmt = rs.getBigDecimal(1);
				taxBaseAmt = taxBaseAmt.add(baseAmt);
				//	TaxAmt
				BigDecimal amt = rs.getBigDecimal(2);
				if (amt == null)
					amt = Env.ZERO;
				boolean isSOTrx = "Y".equals(rs.getString(3));
				//
				// phib [ 1702807 ]: manual tax should never be amended
				// on line level taxes
				if (!documentLevel && amt.signum() != 0 && !isSOTrx)	//	manually entered
					;
				else if (documentLevel || baseAmt.signum() == 0)
					amt = Env.ZERO;
				else	// calculate line tax
					amt = taxBL.calculateTax(tax, baseAmt, isTaxIncluded(), getPrecision());
				//
				taxAmt = taxAmt.add(amt);
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
		
		//	Calculate Tax
		if (documentLevel || taxAmt.signum() == 0)
		{
			taxAmt = taxBL.calculateTax(tax, taxBaseAmt, isTaxIncluded(), getPrecision());
		}
		setTaxAmt(taxAmt);

		//	Set Base
		if (isTaxIncluded())
			setTaxBaseAmt (taxBaseAmt.subtract(taxAmt));
		else
			setTaxBaseAmt (taxBaseAmt);
		
		// Deactivate InvoiceTax if there were no invoice lines matching our C_Tax_ID
		// Active it otherwise
		setIsActive(foundInvoiceLines);
		
		return true;
	}	//	calculateTaxFromLines

	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MInvoiceTax[");
		sb.append("C_Invoice_ID=").append(getC_Invoice_ID())
			.append(",C_Tax_ID=").append(getC_Tax_ID())
			.append(", Base=").append(getTaxBaseAmt()).append(",Tax=").append(getTaxAmt())
			.append ("]");
		return sb.toString ();
	}	//	toString

}	//	MInvoiceTax
