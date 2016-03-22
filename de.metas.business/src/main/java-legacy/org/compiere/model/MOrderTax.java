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
import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.exceptions.DBException;
import org.adempiere.util.Services;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.adempiere.service.IOrderLineBL;
import de.metas.tax.api.ITaxBL;

/**
 * Order Tax Model
 *
 * @author Jorg Janke
 * @version $Id: MOrderTax.java,v 1.4 2006/07/30 00:51:04 jjanke Exp $
 */
public class MOrderTax extends X_C_OrderTax
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6776007249310373908L;

	/**
	 * Get Tax Line for Order Line
	 *
	 * @param line Order line
	 * @param precision currency precision
	 * @param oldTax get old tax
	 * @param trxName transaction
	 * @return existing or new tax
	 */
	public static MOrderTax get(MOrderLine line, int precision,
			boolean oldTax, String trxName)
	{
		if (line == null || line.getC_Order_ID() == 0)
		{
			s_log.debug("No Order");
			return null;
		}
		int C_Tax_ID = line.getC_Tax_ID();
		boolean isOldTax = oldTax && line.is_ValueChanged(MOrderTax.COLUMNNAME_C_Tax_ID);
		if (isOldTax)
		{
			Object old = line.get_ValueOld(MOrderTax.COLUMNNAME_C_Tax_ID);
			if (old == null)
			{
				s_log.debug("No Old Tax");
				return null;
			}
			C_Tax_ID = ((Integer)old).intValue();
		}
		if (C_Tax_ID <= 0)
		{
			s_log.debug("No Tax");
			return null;
		}

		MOrderTax retValue = null;
		final String sql = "SELECT * FROM C_OrderTax WHERE C_Order_ID=? AND C_Tax_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, line.getC_Order_ID());
			pstmt.setInt(2, C_Tax_ID);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				retValue = new MOrderTax(line.getCtx(), rs, trxName);
			}
		}
		catch (Exception e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		if (retValue != null)
		{
			retValue.setPrecision(precision);
			retValue.set_TrxName(trxName);
			s_log.debug("(old=" + oldTax + ") " + retValue);
			return retValue;
		}
		// If the old tax was required and there is no MOrderTax for that
		// return null, and not create another MOrderTax - teo_sarca [ 1583825 ]
		else
		{
			if (isOldTax)
				return null;
		}

		final boolean taxIncluded = Services.get(IOrderLineBL.class).isTaxIncluded(line);
		final I_C_Tax tax = line.getC_Tax();

		//
		// Create New
		retValue = new MOrderTax(line.getCtx(), 0, trxName);
		retValue.set_TrxName(trxName);
		retValue.setClientOrg(line);
		retValue.setC_Order_ID(line.getC_Order_ID());
		retValue.setC_Tax(tax);
		retValue.setIsWholeTax(tax.isWholeTax());
		retValue.setPrecision(precision);
		retValue.setIsTaxIncluded(taxIncluded);
		s_log.debug("(new) " + retValue);
		return retValue;
	}	// get

	/** Static Logger */
	private static Logger s_log = LogManager.getLogger(MOrderTax.class);

	/**************************************************************************
	 * Persistence Constructor
	 *
	 * @param ctx context
	 * @param ignored ignored
	 * @param trxName transaction
	 */
	public MOrderTax(Properties ctx, int ignored, String trxName)
	{
		super(ctx, 0, trxName);
		if (ignored != 0)
			throw new IllegalArgumentException("Multi-Key");
		setTaxAmt(Env.ZERO);
		setTaxBaseAmt(Env.ZERO);
		setIsTaxIncluded(false);
	}	// MOrderTax

	/**
	 * Load Constructor. Set Precision and TaxIncluded for tax calculations!
	 *
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public MOrderTax(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MOrderTax

	/** Tax */
	private MTax m_tax = null;
	/** Cached Precision */
	private Integer m_precision = null;

	/**
	 * Get Precision
	 * 
	 * @return Returns the precision or 2
	 */
	private int getPrecision()
	{
		if (m_precision == null)
			return 2;
		return m_precision.intValue();
	}	// getPrecision

	/**
	 * Set Precision
	 *
	 * @param precision The precision to set.
	 */
	protected void setPrecision(int precision)
	{
		m_precision = new Integer(precision);
	}	// setPrecision

	/**
	 * Get Tax
	 *
	 * @return tax
	 */
	protected MTax getTax()
	{
		if (m_tax == null)
			m_tax = MTax.get(getCtx(), getC_Tax_ID());
		return m_tax;
	}	// getTax

	/**************************************************************************
	 * Calculate/Set Tax Amt from Order Lines
	 * 
	 * If there were no invoice lines found for this tax, this record will be inactivated. In this way, the caller method can know about this and it can decide if this record will be deleted.
	 * 
	 * @return true if calculated
	 */
	public boolean calculateTaxFromLines()
	{
		final ITaxBL taxBL = Services.get(ITaxBL.class);

		BigDecimal taxBaseAmt = Env.ZERO;
		BigDecimal taxAmt = Env.ZERO;
		boolean foundInvoiceLines = false;
		//
		final boolean documentLevel = getTax().isDocumentLevel();
		final I_C_Tax tax = getTax();
		//
		String sql = "SELECT LineNetAmt FROM C_OrderLine WHERE C_Order_ID=? AND C_Tax_ID=?";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getC_Order_ID());
			pstmt.setInt(2, getC_Tax_ID());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				foundInvoiceLines = true;

				BigDecimal baseAmt = rs.getBigDecimal(1);
				taxBaseAmt = taxBaseAmt.add(baseAmt);
				//
				if (!documentLevel)		// calculate line tax
					taxAmt = taxAmt.add(taxBL.calculateTax(tax, baseAmt, isTaxIncluded(), getPrecision()));
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.error(get_TrxName(), e);
			taxBaseAmt = null;
		}
		try
		{
			if (pstmt != null)
				pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		//
		if (taxBaseAmt == null)
			return false;

		// Calculate Tax
		if (documentLevel)		// document level
			taxAmt = taxBL.calculateTax(tax, taxBaseAmt, isTaxIncluded(), getPrecision());
		setTaxAmt(taxAmt);

		// Set Base
		if (isTaxIncluded())
			setTaxBaseAmt(taxBaseAmt.subtract(taxAmt));
		else
			setTaxBaseAmt(taxBaseAmt);

		// Deactivate InvoiceTax if there were no invoice lines matching our C_Tax_ID
		// Active it otherwise
		setIsActive(foundInvoiceLines);

		return true;
	}	// calculateTaxFromLines

	/**
	 * String Representation
	 *
	 * @return info
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer("MOrderTax[")
				.append("C_Order_ID=").append(getC_Order_ID())
				.append(", C_Tax_ID=").append(getC_Tax_ID())
				.append(", Base=").append(getTaxBaseAmt())
				.append(", Tax=").append(getTaxAmt())
				.append("]");
		return sb.toString();
	}	// toString

}	// MOrderTax
