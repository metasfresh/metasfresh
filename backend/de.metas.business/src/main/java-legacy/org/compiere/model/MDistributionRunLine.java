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
 *	Distribution Run List Line Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MDistributionRunLine.java,v 1.4 2006/07/30 00:51:02 jjanke Exp $
 */
public class MDistributionRunLine extends X_M_DistributionRunLine
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4106664830581774843L;


	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param M_DistributionRunLine_ID id
	 *	@param trxName transaction
	 */
	public MDistributionRunLine (Properties ctx, int M_DistributionRunLine_ID, String trxName)
	{
		super (ctx, M_DistributionRunLine_ID, trxName);
	}	//	MDistributionRunLine

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MDistributionRunLine (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MDistributionRunLine
	
	/**	Product						*/
	private MProduct		m_product = null;
	/**	Actual Qty					*/
	private BigDecimal 		m_actualQty = Env.ZERO;
	/**	Actual Min					*/
	private BigDecimal 		m_actualMin = Env.ZERO;
	/**	Actual Allocation			*/
	private BigDecimal 		m_actualAllocation = Env.ZERO;
	/**	Last Allocation Difference	*/
	private BigDecimal 		m_lastDifference = Env.ZERO;
	/**	Max Allocation 				*/
	private BigDecimal 		m_maxAllocation = Env.ZERO;

	
	/**
	 * 	Get Actual Qty
	 *	@return actual Qty
	 */
	public BigDecimal getActualQty()
	{
		return m_actualQty;
	}	//	getActualQty
	
	/**
	 * 	Add to Actual Qty
	 *	@param add number to add
	 */
	public void addActualQty(BigDecimal add)
	{
		m_actualQty = m_actualQty.add(add);
	}	//	addActualQty

	/**
	 * 	Get Actual Min Qty
	 *	@return actual Min Qty
	 */
	public BigDecimal getActualMin()
	{
		return m_actualMin;
	}	//	getActualMin
	
	/**
	 * 	Add to Actual Min Qty
	 *	@param add number to add
	 */
	public void addActualMin(BigDecimal add)
	{
		m_actualMin = m_actualMin.add(add);
	}	//	addActualMin

	/**
	 * 	Is Actual Min Greater than Total
	 *	@return true if act min > total
	 */
	public boolean isActualMinGtTotal()
	{
		return m_actualMin.compareTo(getTotalQty()) > 0;
	}	//	isActualMinGtTotal


	/**
	 * 	Get Actual Allocation Qty
	 *	@return actual Allocation Qty
	 */
	public BigDecimal getActualAllocation()
	{
		return m_actualAllocation;
	}	//	getActualAllocation
	
	/**
	 * 	Add to Actual Min Qty
	 *	@param add number to add
	 */
	public void addActualAllocation(BigDecimal add)
	{
		m_actualAllocation = m_actualAllocation.add(add);
	}	//	addActualAllocation

	/**
	 * 	Is Actual Allocation equals Total
	 *	@return true if act allocation = total
	 */
	public boolean isActualAllocationEqTotal()
	{
		return m_actualAllocation.compareTo(getTotalQty()) == 0;
	}	//	isActualAllocationEqTotal

	/**
	 * 	Get Allocation Difference
	 *	@return Total - Allocation Qty 
	 */
	public BigDecimal getActualAllocationDiff()
	{
		return getTotalQty().subtract(m_actualAllocation);
	}	//	getActualAllocationDiff

	
	/**
	 * 	Get Last Allocation Difference
	 *	@return difference
	 */
	public BigDecimal getLastDifference()
	{
		return m_lastDifference;
	}	//	getLastDifference
	
	/**
	 * 	Set Last Allocation Difference
	 *	@param difference difference
	 */
	public void setLastDifference(BigDecimal difference)
	{
		m_lastDifference = difference;
	}	//	setLastDifference
	
	/**
	 * 	Get Max Allocation
	 *	@return max allocation
	 */
	public BigDecimal getMaxAllocation()
	{
		return m_maxAllocation;
	}	//	getMaxAllocation
	
	/**
	 * 	Set Max Allocation if greater
	 *	@param max allocation
	 *	@param set set to max
	 */
	public void setMaxAllocation (BigDecimal max, boolean set)
	{
		if (set || max.compareTo(m_maxAllocation) > 0)
			m_maxAllocation = max;
	}	//	setMaxAllocation

	/**
	 * 	Reset Calculations
	 */
	public void resetCalculations()
	{
		m_actualQty = Env.ZERO;
		m_actualMin = Env.ZERO;
		m_actualAllocation = Env.ZERO;
	//	m_lastDifference = Env.ZERO;
		m_maxAllocation = Env.ZERO;
		
	}	//	resetCalculations
	
	
	/**************************************************************************
	 * 	Get Product
	 *	@return product
	 */
	public MProduct getProduct()
	{
		if (m_product == null)
			m_product = MProduct.get(getCtx(), getM_Product_ID());
		return m_product;
	}	//	getProduct
	
	/**
	 * 	Get Product Standard Precision
	 *	@return standard precision
	 */
	public int getUOMPrecision()
	{
		return getProduct().getUOMPrecision();
	}	//	getUOMPrecision
	
	
	/**************************************************************************
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MDistributionRunLine[")
			.append(get_ID()).append("-")
			.append(getInfo())
			.append ("]");
		return sb.toString ();
	}	//	toString
	
	/**
	 * 	Get Info
	 *	@return info
	 */
	public String getInfo()
	{
		StringBuffer sb = new StringBuffer ();
		sb.append("Line=").append(getLine())
			.append (",TotalQty=").append(getTotalQty())
			.append(",SumMin=").append(getActualMin())
			.append(",SumQty=").append(getActualQty())
			.append(",SumAllocation=").append(getActualAllocation())
			.append(",MaxAllocation=").append(getMaxAllocation())
			.append(",LastDiff=").append(getLastDifference());
		return sb.toString ();
	}	//	getInfo
	
}	//	MDistributionRunLine
