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
import java.util.Locale;
import java.util.Properties;

import org.adempiere.service.IClientDAO;
import org.adempiere.util.Services;
import org.compiere.util.CCache;
import org.compiere.util.Env;

import de.metas.i18n.Msg;

/**
 *	Calendar Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MCalendar.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MCalendar extends X_C_Calendar
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7721451326626542420L;


	/**
	 * 	Get MCalendar from Cache
	 *	@param ctx context
	 *	@param C_Calendar_ID id
	 *	@return MCalendar
	 */
	public static MCalendar get (Properties ctx, int C_Calendar_ID)
	{
		Integer key = new Integer (C_Calendar_ID);
		MCalendar retValue = (MCalendar) s_cache.get (key);
		if (retValue != null)
			return retValue;
		retValue = new MCalendar (ctx, C_Calendar_ID, null);
		if (retValue.get_ID () != 0)
			s_cache.put (key, retValue);
		return retValue;
	}	//	get
	
	/**
	 * 	Get Default Calendar for Client
	 *	@param ctx context
	 *	@param AD_Client_ID id
	 *	@return MCalendar
	 */
	public static MCalendar getDefault (Properties ctx, int AD_Client_ID)
	{
		final I_AD_ClientInfo info = Services.get(IClientDAO.class).retrieveClientInfo(ctx, AD_Client_ID);
		return get (ctx, info.getC_Calendar_ID());
	}	//	getDefault
	
	/**
	 * 	Get Default Calendar for Client
	 *	@param ctx context
	 *	@return MCalendar
	 */
	public static MCalendar getDefault (Properties ctx)
	{
		return getDefault(ctx, Env.getAD_Client_ID(ctx));
	}	//	getDefault
	
	/**	Cache						*/
	private static CCache<Integer,MCalendar> s_cache
		= new CCache<Integer,MCalendar>("C_Calendar", 20);
	
	
	/*************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param C_Calendar_ID id
	 *	@param trxName transaction
	 */
	public MCalendar (Properties ctx, int C_Calendar_ID, String trxName)
	{
		super(ctx, C_Calendar_ID, trxName);
	}	//	MCalendar

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MCalendar (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MCalendar

	/**
	 * 	Parent Constructor
	 *	@param client parent
	 */
	public MCalendar (MClient client)
	{
		super(client.getCtx(), 0, client.get_TrxName());
		setClientOrg(client);
		setName(client.getName() + " " + Msg.translate(client.getCtx(), "C_Calendar_ID"));
	}	//	MCalendar
	
	/**
	 * 	Create (current) Calendar Year
	 * 	@param locale locale
	 *	@return The Year
	 */
	public MYear createYear(Locale locale)
	{
		if (get_ID() == 0)
			return null;
		MYear year = new MYear (this);
		if (year.save())
			year.createStdPeriods(locale);
		//
		return year;
	}	//	createYear
	
}	//	MCalendar
