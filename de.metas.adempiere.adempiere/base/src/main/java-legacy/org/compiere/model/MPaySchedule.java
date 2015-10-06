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

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.Env;

/**
 *	Payment Term Schedule Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MPaySchedule.java,v 1.3 2006/07/30 00:51:04 jjanke Exp $
 */
public class MPaySchedule extends X_C_PaySchedule
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7773501661681911294L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param C_PaySchedule_ID id
	 *	@param trxName transaction
	 */
	public MPaySchedule(Properties ctx, int C_PaySchedule_ID, String trxName)
	{
		super(ctx, C_PaySchedule_ID, trxName);
		if (C_PaySchedule_ID == 0)
		{
		//	setC_PaymentTerm_ID (0);	//	Parent
			setPercentage (Env.ZERO);
			setDiscount (Env.ZERO);
			setDiscountDays (0);
			setGraceDays (0);
			setNetDays (0);
			setIsValid (false);
		}	
	}	//	MPaySchedule

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MPaySchedule(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MPaySchedule

	/**	Parent					*/
	public MPaymentTerm		m_parent = null;
	
	/**
	 * @return Returns the parent.
	 */
	public MPaymentTerm getParent ()
	{
		if (m_parent == null)
			m_parent = new MPaymentTerm (getCtx(), getC_PaymentTerm_ID(), get_TrxName());
		return m_parent;
	}	//	getParent
	
	/**
	 * @param parent The parent to set.
	 */
	public void setParent (MPaymentTerm parent)
	{
		m_parent = parent;
	}	//	setParent
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		if (is_ValueChanged("Percentage") || is_ValueChanged("IsActive"))
		{
			log.fine("beforeSave");
			setIsValid(false);
		}
		return true;
	}	//	beforeSave

	/**
	 * 	After Save
	 *	@param newRecord new
	 *	@param success success
	 *	@return success
	 */
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (newRecord || is_ValueChanged("Percentage") || is_ValueChanged("IsActive"))
		{
			log.fine("afterSave");
			getParent();
			m_parent.validate();
			m_parent.save();
		}
		return success;
	}	//	afterSave

	@Override
	protected boolean afterDelete(boolean success) {
		if (!success)
			return false;
		getParent();
		m_parent.validate();
		m_parent.save();
		return true;
	}
	
	
}	//	MPaySchedule
