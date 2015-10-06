/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
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
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved.               *
 * Contributor(s): Victor Perez www.e-evolution.com                           *
 *****************************************************************************/
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import org.adempiere.exceptions.WarehouseInvalidForOrgException;


/**
 * Forecast Line Model
 * 
 * @author Victor Perez www.e-evolution.com     
 * @version $Id: MForecastLine.java,v 1.11 2005/05/17 05:29:52 vpj-cd Exp $
 */
public class MForecastLine extends  X_M_ForecastLine
{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3420900505079279058L;
	/** Parent					*/
	private MForecast			m_parent = null;

	/**
	 * Standard Constructor
	 * @param ctx context
	 * @param M_ForecastLine_ID id
	 */
	public MForecastLine (Properties ctx, int M_ForecastLine_ID, String trxName)
	{
		super (ctx, M_ForecastLine_ID, trxName);
	}	//	MForecastLine

	/**
	 * Load Constructor
	 * @param ctx context
	 * @param rs result set
	 */
	public MForecastLine (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MForecastLine
	
	/**************************************************************************
	 * 	Before Save
	 *	@param newRecord
	 *	@return true if it can be saved
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		if (newRecord 
			|| is_ValueChanged("AD_Org_ID") || is_ValueChanged("M_Warehouse_ID"))
		{	
			MWarehouse wh = MWarehouse.get(getCtx(), getM_Warehouse_ID());
			if (wh.getAD_Org_ID() != getAD_Org_ID())
			{
				throw new WarehouseInvalidForOrgException(wh.getName(), MOrg.get(getCtx(), getAD_Org_ID()).getName());
			}
		}
		return true;
	}
	
	/**
	 * 	Get Parent
	 *	@return parent
	 */
	public MForecast getParent()
	{
		if (m_parent == null)
			m_parent = new MForecast(getCtx(), getM_Forecast_ID(), get_TrxName());
		return m_parent;
	}	//	getParent
	
}	//	MForecastLine
