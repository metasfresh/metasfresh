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

import de.metas.cache.CCache;
import de.metas.logging.LogManager;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

/**
 *  Tax Model
 *
 *	@author Jorg Janke
 *	@version $Id: MTax.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 * 	red1 - FR: [ 2214883 ] Remove SQL code and Replace for Query 
 */
public class MTax extends X_C_Tax
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4140382472528327237L;


	/**
	 * 	Get Tax from Cache
	 *	@param ctx context
	 *	@param C_Tax_ID id
	 *	@return MTax
	 */
	public static MTax get (Properties ctx, int C_Tax_ID)
	{
		if (C_Tax_ID <= 0)
		{
			return null;
		}
		
		Integer key = new Integer (C_Tax_ID);
		MTax retValue = s_cache.get (key);
		if (retValue != null)
			return retValue;
		retValue = new MTax (ctx, C_Tax_ID, null);
		if (retValue.get_ID () != 0)
			s_cache.put (key, retValue);
		return retValue;
	}	//	get

	/**	Cache						*/
	private static final CCache<Integer,MTax> s_cache = new CCache<>(Table_Name, 5);
	/**	Static Logger	*/
	private static final Logger s_log = LogManager.getLogger(MTax.class);

	
	public MTax (Properties ctx, int C_Tax_ID, String trxName)
	{
		super (ctx, C_Tax_ID, trxName);
		if (is_new())
		{
		//	setC_Tax_ID (0);		PK
			setIsDefault (false);
			setIsDocumentLevel (true);
			setIsSummary (false);
			setIsTaxExempt (false);
		//	setName (null);
			setRate (BigDecimal.ZERO);
			setRequiresTaxCertificate (null);
		//	setC_TaxCategory_ID (0);	//	FK
			setSOPOType (SOPOTYPE_Both);
			setValidFrom (TimeUtil.getDay(1990,1,1));
			setIsSalesTax(false);
		}
	}	//	MTax

	public MTax (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MTax

	/**
	 * 	New Constructor
	 *	@param ctx
	 *	@param Name
	 *	@param Rate
	 *	@param C_TaxCategory_ID
	 *	@param trxName transaction
	 */
	public MTax (Properties ctx, String Name, BigDecimal Rate, int C_TaxCategory_ID, String trxName)
	{
		this (ctx, 0, trxName);
		setName (Name);
		setRate (Rate == null ? BigDecimal.ZERO : Rate);
		setC_TaxCategory_ID (C_TaxCategory_ID);	//	FK
	}	//	MTax

	/**	Child Taxes			*/
	private MTax[]			m_childTaxes = null;
	/**
	 * 	Get Child Taxes
	 * 	@param requery reload
	 *	@return array of taxes or null
	 */
	public MTax[] getChildTaxes (boolean requery)
	{
		if (!isSummary())
			return null;
		if (m_childTaxes != null && !requery)
			return m_childTaxes;
		//
		//FR: [ 2214883 ] Remove SQL code and Replace for Query - red1
		String whereClause = COLUMNNAME_Parent_Tax_ID+"=?";
		List<MTax> list = new Query(getCtx(), MTax.Table_Name, whereClause,  get_TrxName())
		.setParameters(new Object[]{getC_Tax_ID()})
	 	.list(MTax.class);	
		//red1 - end -
	 
		m_childTaxes = new MTax[list.size ()];
		list.toArray (m_childTaxes);
		return m_childTaxes;
	}	//	getChildTaxes
	
	/**
	 * @return true if tax rate is 0
	 */
	public boolean isZeroTax()
	{
		return getRate().signum() == 0;
	}	//	isZeroTax
	
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("MTax[")
			.append(get_ID())
			.append(", Name = ").append(getName())
			.append(", SO/PO=").append(getSOPOType())
			.append(", Rate=").append(getRate())
			.append(", C_TaxCategory_ID=").append(getC_TaxCategory_ID())
			.append(", Summary=").append(isSummary())
			.append(", Parent=").append(getParent_Tax_ID())
			.append(", Country=").append(getC_Country_ID()).append("|").append(getTo_Country_ID())
			.append(", Region=").append(getC_Region_ID()).append("|").append(getTo_Region_ID())
			.append("]");
		return sb.toString();
	}

	/**
	 * 	After Save
	 *	@param newRecord new
	 *	@param success success
	 *	@return success
	 */
	@Override
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (newRecord && success)
			insert_Accounting("C_Tax_Acct", "C_AcctSchema_Default", null);

		return success;
	}	//	afterSave

	/**
	 * 	Before Delete
	 *	@return true
	 */
	@Override
	protected boolean beforeDelete ()
	{
		return delete_Accounting("C_Tax_Acct"); 
	}	//	beforeDelete
}	//	MTax
