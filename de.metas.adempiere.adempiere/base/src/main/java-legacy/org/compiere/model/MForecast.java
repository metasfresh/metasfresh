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


/**
 * Forecast Model
 * 
 * @author victor.perez@e-evolution.com, www.e-evolution.com     
 */
public class MForecast extends  X_M_Forecast
{


	/**
	 * 
	 */
	private static final long serialVersionUID = 66771328316032322L;

	/**
	 * Standard Constructor
	 * @param ctx context
	 * @param M_ForecastLine_ID id
	 */
	public MForecast (Properties ctx, int M_Forecast_ID, String trxName)
	{
		super (ctx, M_Forecast_ID, trxName);
	}	//	MForecastLine

	/**
	 * Load Constructor
	 * @param ctx context
	 * @param rs result set
	 */
	public MForecast (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MForecast
}	//	MForecast
