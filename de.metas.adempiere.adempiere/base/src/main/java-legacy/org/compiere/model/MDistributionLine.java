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
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *	GL Distribution Line Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MDistributionLine.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MDistributionLine extends X_GL_DistributionLine
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6148743556518054326L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param GL_DistributionLine_ID id
	 *	@param trxName transaction
	 */
	public MDistributionLine (Properties ctx, int GL_DistributionLine_ID, String trxName)
	{
		super (ctx, GL_DistributionLine_ID, trxName);
		if (GL_DistributionLine_ID == 0)
		{
		//	setGL_Distribution_ID (0);		//	Parent
		//	setLine (0);
			//
			setOverwriteAcct (false);
			setOverwriteActivity (false);
			setOverwriteBPartner (false);
			setOverwriteCampaign (false);
			setOverwriteLocFrom (false);
			setOverwriteLocTo (false);
			setOverwriteOrg (false);
			setOverwriteOrgTrx (false);
			setOverwriteProduct (false);
			setOverwriteProject (false);
			setOverwriteSalesRegion (false);
			setOverwriteUser1 (false);
			setOverwriteUser2 (false);
			//
			setPercent (Env.ZERO);
		}	
	}	//	MDistributionLine

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MDistributionLine (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MDistributionLine

	/**	The Parent						*/
	private MDistribution		m_parent = null;
	/** The Amount						*/
	private BigDecimal			m_amt = null;
	/** The Quantity					*/
	private BigDecimal			m_qty = null;
	/** The Base Account				*/
	private MAccount			m_account = null;

	/**
	 * 	Get Parent
	 *	@return Returns the parent.
	 */
	public MDistribution getParent ()
	{
		if (m_parent == null)
			m_parent = new MDistribution (getCtx(), getGL_Distribution_ID(), get_TrxName()); 
		return m_parent;
	}	//	getParent
	
	/**
	 * 	Set Parent
	 *	@param parent The parent to set.
	 */
	public void setParent (MDistribution parent)
	{
		m_parent = parent;
	}	//	setParent
	
	/**
	 * 	Set Account
	 *	@param acct account
	 */
	public void setAccount (MAccount acct)
	{
		m_account = acct;
	}	//	setAccount
	
	/**
	 * 	Get Account Combination based on Account and Overwrite
	 *	@return account
	 */
	public MAccount getAccount()
	{
		MAccount acct = MAccount.get(getCtx(), 
			m_account.getAD_Client_ID(), 
			isOverwriteOrg() && getOrg_ID() != 0 ? getOrg_ID() : m_account.getAD_Org_ID(), 
			m_account.getC_AcctSchema_ID(),
			isOverwriteAcct() && getAccount_ID() != 0 ? getAccount_ID() : m_account.getAccount_ID(),
				m_account.getC_SubAcct_ID(),
			//	
			isOverwriteProduct() ? getM_Product_ID() : m_account.getM_Product_ID(), 
			isOverwriteBPartner() ? getC_BPartner_ID() : m_account.getC_BPartner_ID(), 
			isOverwriteOrgTrx() ? getAD_OrgTrx_ID() : m_account.getAD_OrgTrx_ID(), 
			isOverwriteLocFrom() ? getC_LocFrom_ID() : m_account.getC_LocFrom_ID(), 
			isOverwriteLocTo() ? getC_LocTo_ID() : m_account.getC_LocTo_ID(), 
			isOverwriteSalesRegion() ? getC_SalesRegion_ID() : m_account.getC_SalesRegion_ID(), 
			isOverwriteProject() ? getC_Project_ID() : m_account.getC_Project_ID(), 
			isOverwriteCampaign() ? getC_Campaign_ID() : m_account.getC_Campaign_ID(), 
			isOverwriteActivity() ? getC_Activity_ID() : m_account.getC_Activity_ID(),
			isOverwriteUser1() ? getUser1_ID() : m_account.getUser1_ID(), 
			isOverwriteUser2() ? getUser2_ID() : m_account.getUser2_ID(),
				m_account.getUserElement1_ID(),
				m_account.getUserElement2_ID());
		return acct;
	}	//	setAccount

	
	/**************************************************************************
	 * 	Get Distribution Amount
	 *	@return Returns the amt.
	 */
	public BigDecimal getAmt ()
	{
		return m_amt;
	}	//	getAmt
	
	/**
	 * 	Set Distribution Amount
	 *	@param amt The amt to set.
	 */
	public void setAmt (BigDecimal amt)
	{
		m_amt = amt;
	}	//	setAmt
	
	/**************************************************************************
	 * 	Get Distribution Quantity
	 *	@return Returns the qty.
	 */
	public BigDecimal getQty ()
	{
		return m_qty;
	}	//	getQty
	
	/**
	 * 	Set Distribution Quantity
	 *	@param qty The qty to set.
	 */
	public void setQty (BigDecimal qty)
	{
		m_qty = qty;
	}	//	setQty
	
	/**
	 * 	Set Distribution Amount
	 *	@param amt The amt to set to be multiplied by percent.
	 *	@param precision precision
	 */
	public void calculateAmt (BigDecimal amt, int precision)
	{
		m_amt = amt.multiply(getPercent());
		m_amt = m_amt.divide(Env.ONEHUNDRED, precision, BigDecimal.ROUND_HALF_UP);
	}	//	setAmt

	/**
	 * 	Set Distribution Quantity
	 *	@param qty The qty to set to be multiplied by percent.
	 */
	public void calculateQty (BigDecimal qty)
	{
		m_qty = qty.multiply(getPercent());
		m_qty = m_qty.divide(Env.ONEHUNDRED, BigDecimal.ROUND_HALF_UP);
	}	//	setAmt

	
	/**************************************************************************
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		if (getLine() == 0)
		{
			String sql = "SELECT COALESCE(MAX(Line),0)+10 FROM GL_DistributionLine WHERE GL_Distribution_ID=?";
			int ii = DB.getSQLValue (get_TrxName(), sql, getGL_Distribution_ID());
			setLine (ii);
		}
		//	Reset not selected Overwrite
		if (!isOverwriteAcct() && getAccount_ID() != 0)
			setAccount_ID(0);
		if (!isOverwriteActivity() && getC_Activity_ID() != 0)
			setC_Activity_ID(0);
		if (!isOverwriteBPartner() && getC_BPartner_ID() != 0)
			setC_BPartner_ID(0);
		if (!isOverwriteCampaign() && getC_Campaign_ID() != 0)
			setC_Campaign_ID(0);
		if (!isOverwriteLocFrom() && getC_LocFrom_ID() != 0)
			setC_LocFrom_ID(0);
		if (!isOverwriteLocTo() && getC_LocTo_ID() != 0)
			setC_LocTo_ID(0);
		if (!isOverwriteOrg() && getOrg_ID() != 0)
			setOrg_ID(0);
		if (!isOverwriteOrgTrx() && getAD_OrgTrx_ID() != 0)
			setAD_OrgTrx_ID(0);
		if (!isOverwriteProduct() && getM_Product_ID() != 0)
			setM_Product_ID(0);
		if (!isOverwriteProject() && getC_Project_ID() != 0)
			setC_Project_ID(0);
		if (!isOverwriteSalesRegion() && getC_SalesRegion_ID() != 0)
			setC_SalesRegion_ID(0);
		if (!isOverwriteUser1() && getUser1_ID() != 0)
			setUser1_ID(0);
		if (!isOverwriteUser2() && getUser2_ID() != 0)
			setUser2_ID(0);
		
		//	Account Overwrite cannot be 0
		if (isOverwriteAcct() && getAccount_ID() <= 0)
		{
			throw new AdempiereException("@Account_ID@ = 0");
		}
		//	Org Overwrite cannot be 0
		if (isOverwriteOrg() && getOrg_ID() <= 0)
		{
			throw new AdempiereException("@Org_ID@ = 0");
		}
		return true;
	}	//	beforeSave
	
	/**
	 * 	After Save
	 *	@param newRecord new
	 *	@param success success
	 *	@return success
	 */
	@Override
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		getParent();
		m_parent.validate();
		m_parent.save();
		return success;
	}	//	afterSave

}	//	MDistributionLine
