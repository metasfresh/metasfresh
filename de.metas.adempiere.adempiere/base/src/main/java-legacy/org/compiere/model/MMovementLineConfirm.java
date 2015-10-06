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

import org.compiere.util.Env;

/**
 *	Inventory Movement Confirmation Line
 *	
 *  @author Jorg Janke
 *  @version $Id: MMovementLineConfirm.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MMovementLineConfirm extends X_M_MovementLineConfirm
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2406580342096137696L;

	/**
	 * 	Standard Constructor
	 *	@param ctx ctx
	 *	@param M_MovementLineConfirm_ID id
	 *	@param trxName transaction
	 */
	public MMovementLineConfirm (Properties ctx, int M_MovementLineConfirm_ID, String trxName)
	{
		super (ctx, M_MovementLineConfirm_ID, trxName);
		if (M_MovementLineConfirm_ID == 0)
		{
		//	setM_MovementConfirm_ID (0);	Parent
		//	setM_MovementLine_ID (0);
			setConfirmedQty (Env.ZERO);
			setDifferenceQty (Env.ZERO);
			setScrappedQty (Env.ZERO);
			setTargetQty (Env.ZERO);
			setProcessed (false);
		}	}	//	M_MovementLineConfirm

	/**
	 * 	M_MovementLineConfirm
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MMovementLineConfirm (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	M_MovementLineConfirm

	/**
	 * 	Parent Constructor
	 *	@param parent parent
	 */
	public MMovementLineConfirm (MMovementConfirm parent)
	{
		this (parent.getCtx(), 0, parent.get_TrxName());
		setClientOrg(parent);
		setM_MovementConfirm_ID(parent.getM_MovementConfirm_ID());
	}	//	MMovementLineConfirm

	/**	Movement Line			*/
	private MMovementLine 	m_line = null;
	
	/**
	 * 	Set Movement Line
	 *	@param line line
	 */
	public void setMovementLine (MMovementLine line)
	{
		setM_MovementLine_ID(line.getM_MovementLine_ID());
		setTargetQty(line.getMovementQty());
		setConfirmedQty(getTargetQty());	//	suggestion
		m_line = line;
	}	//	setMovementLine
	
	/**
	 * 	Get Movement Line
	 *	@return line
	 */
	public MMovementLine getLine()
	{
		if (m_line == null)
			m_line = new MMovementLine (getCtx(), getM_MovementLine_ID(), get_TrxName());
		return m_line;
	}	//	getLine
	
	
	/**
	 * 	Process Confirmation Line.
	 * 	- Update Movement Line
	 *	@return success
	 */
	public boolean processLine ()
	{
		MMovementLine line = getLine();
		
		line.setTargetQty(getTargetQty());
		line.setMovementQty(getConfirmedQty());
		line.setConfirmedQty(getConfirmedQty());
		line.setScrappedQty(getScrappedQty());
		
		return line.save(get_TrxName());
	}	//	processConfirmation
	
	/**
	 * 	Is Fully Confirmed
	 *	@return true if Target = Confirmed qty
	 */
	public boolean isFullyConfirmed()
	{
		return getTargetQty().compareTo(getConfirmedQty()) == 0;
	}	//	isFullyConfirmed
	
	
	/**
	 * 	Before Delete - do not delete
	 *	@return false 
	 */
	protected boolean beforeDelete ()
	{
		return false;
	}	//	beforeDelete
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		//	Calculate Difference = Target - Confirmed - Scrapped
		BigDecimal difference = getTargetQty();
		difference = difference.subtract(getConfirmedQty());
		difference = difference.subtract(getScrappedQty());
		setDifferenceQty(difference);
		//
		return true;
	}	//	beforeSave

	
}	//	M_MovementLineConfirm
