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
package org.compiere.process;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.logging.Level;

import org.compiere.model.MLocation;
import org.compiere.model.MSystem;
import org.compiere.model.M_Registration;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.WebEnv;

/**
 *	System Registration
 *	
 *  @author Jorg Janke
 *  @version $Id: RegisterSystem.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class RegisterSystem extends SvrProcess
{
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else
				log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
		}
	}	//	prepare

	/**
	 * 	DoIt
	 *	@return Message
	 *	@throws Exception
	 */
	protected String doIt() throws Exception
	{
		int AD_Registration_ID = getRecord_ID();
		log.info("doIt - AD_Registration_ID=" + AD_Registration_ID);
		//	Check Ststem
		MSystem sys = MSystem.get(getCtx());
		if (sys.getName().equals("?") || sys.getName().length()<2)
			throw new AdempiereUserError("Set System Name in System Record");
		if (sys.getUserName().equals("?") || sys.getUserName().length()<2)
			throw new AdempiereUserError("Set User Name (as in Web Store) in System Record");
		if (sys.getPassword().equals("?") || sys.getPassword().length()<2)
			throw new AdempiereUserError("Set Password (as in Web Store) in System Record");
		//	Registration
		M_Registration reg = new M_Registration (getCtx(), AD_Registration_ID, get_TrxName());
		//	Location
		MLocation loc = null;
		if (reg.getC_Location_ID() > 0)
		{
			loc = new MLocation (getCtx(), reg.getC_Location_ID(), get_TrxName());
			if (loc.getCity() == null || loc.getCity().length() < 2)
				throw new AdempiereUserError("No City in Address");
		}
		if (loc == null)
			throw new AdempiereUserError("Please enter Address with City");

		//	Create Query String
		String enc = WebEnv.ENCODING;
		//	Send GET Request
		StringBuffer urlString = new StringBuffer ("http://www.adempiere.com")
			.append("/wstore/registrationServlet?");
		//	System Info
		urlString.append("Name=").append(URLEncoder.encode(sys.getName(), enc))
			.append("&UserName=").append(URLEncoder.encode(sys.getUserName(), enc))
			.append("&Password=").append(URLEncoder.encode(sys.getPassword(), enc));
		//	Registration Info
		if (reg.getDescription() != null && reg.getDescription().length() > 0)
			urlString.append("&Description=").append(URLEncoder.encode(reg.getDescription(), enc));
		urlString.append("&IsInProduction=").append(reg.isInProduction() ? "Y" : "N");
		if (reg.getStartProductionDate() != null)
			urlString.append("&StartProductionDate=").append(URLEncoder.encode(String.valueOf(reg.getStartProductionDate()), enc));
		urlString.append("&IsAllowPublish=").append(reg.isAllowPublish() ? "Y" : "N")
			.append("&NumberEmployees=").append(URLEncoder.encode(String.valueOf(reg.getNumberEmployees()), enc))
			.append("&C_Currency_ID=").append(URLEncoder.encode(String.valueOf(reg.getC_Currency_ID()), enc))
			.append("&SalesVolume=").append(URLEncoder.encode(String.valueOf(reg.getSalesVolume()), enc));
		if (reg.getIndustryInfo() != null && reg.getIndustryInfo().length() > 0)
			urlString.append("&IndustryInfo=").append(URLEncoder.encode(reg.getIndustryInfo(), enc));
		if (reg.getPlatformInfo() != null && reg.getPlatformInfo().length() > 0)
			urlString.append("&PlatformInfo=").append(URLEncoder.encode(reg.getPlatformInfo(), enc));
		urlString.append("&IsRegistered=").append(reg.isRegistered() ? "Y" : "N")
			.append("&Record_ID=").append(URLEncoder.encode(String.valueOf(reg.getRecord_ID()), enc));
		//	Address
		urlString.append("&City=").append(URLEncoder.encode(loc.getCity(), enc))
			.append("&C_Country_ID=").append(URLEncoder.encode(String.valueOf(loc.getC_Country_ID()), enc));
		//	Statistics
		if (reg.isAllowStatistics())
		{
			urlString.append("&NumClient=").append(URLEncoder.encode(String.valueOf(
					DB.getSQLValue(null, "SELECT Count(*) FROM AD_Client")), enc))
				.append("&NumOrg=").append(URLEncoder.encode(String.valueOf(
					DB.getSQLValue(null, "SELECT Count(*) FROM AD_Org")), enc))
				.append("&NumBPartner=").append(URLEncoder.encode(String.valueOf(
					DB.getSQLValue(null, "SELECT Count(*) FROM C_BPartner")), enc))
				.append("&NumUser=").append(URLEncoder.encode(String.valueOf(
					DB.getSQLValue(null, "SELECT Count(*) FROM AD_User")), enc))
				.append("&NumProduct=").append(URLEncoder.encode(String.valueOf(
					DB.getSQLValue(null, "SELECT Count(*) FROM M_Product")), enc))
				.append("&NumInvoice=").append(URLEncoder.encode(String.valueOf(
					DB.getSQLValue(null, "SELECT Count(*) FROM C_Invoice")), enc));
		}
		log.fine(urlString.toString());
		
		//	Send it
		URL url = new URL (urlString.toString());
		StringBuffer sb = new StringBuffer();
		try
		{
			URLConnection uc = url.openConnection();
			InputStreamReader in = new InputStreamReader(uc.getInputStream());
			int c;
			while ((c = in.read()) != -1)
				sb.append((char)c);
			in.close();
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "Connect - " + e.toString());
			throw new IllegalStateException("Cannot connect to Server - Please try later");
		}
		//
		String info = sb.toString();
		log.info("Response=" + info);
		//	Record at the end
		int index = sb.indexOf("Record_ID=");
		if (index != -1)
		{
			try
			{
				int Record_ID = Integer.parseInt(sb.substring(index+10));
				reg.setRecord_ID(Record_ID);
				reg.setIsRegistered(true);
				reg.save();
				//
				info = info.substring(0, index);
			}
			catch (Exception e)
			{
				log.log(Level.SEVERE, "Record - ", e);
			}
		}
		
		return info;
	}	//	doIt

}	//	RegisterSystem
