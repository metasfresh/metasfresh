/**********************************************************************
* This file is part of Adempiere ERP Bazaar                           *
* http://www.adempiere.org                                            *
*                                                                     *
* Copyright (C) Carlos Ruiz - globalqss                               *
* Copyright (C) Contributors                                          *
*                                                                     *
* This program is free software; you can redistribute it and/or       *
* modify it under the terms of the GNU General Public License         *
* as published by the Free Software Foundation; either version 2      *
* of the License, or (at your option) any later version.              *
*                                                                     *
* This program is distributed in the hope that it will be useful,     *
* but WITHOUT ANY WARRANTY; without even the implied warranty of      *
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the        *
* GNU General Public License for more details.                        *
*                                                                     *
* You should have received a copy of the GNU General Public License   *
* along with this program; if not, write to the Free Software         *
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,          *
* MA 02110-1301, USA.                                                 *
*                                                                     *
* Contributors:                                                       *
* - Carlos Ruiz - globalqss                                           *
*                                                                     *
* Sponsors:                                                           *
* - Company (http://www.globalqss.com)                                *
***********************************************************************/

package org.adempiere.process;

import java.io.File;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_City;
import org.compiere.model.I_C_Currency;
import org.compiere.model.MSetup;
import org.compiere.print.PrintUtil;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

import de.metas.currency.ICurrencyDAO;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;

/**
 * 	Process to create a new client (tenant)
 *	
 *  @author Carlos Ruiz
 *    [ 2598506 ] FR - Implement Initial Client Setup
 */
public class InitialClientSetup extends JavaProcess
{
	
	// Process Parameters
	private String p_ClientName = null;
	private String p_OrgName = null;
	private String p_AdminUserName = null;
	private String p_NormalUserName = null;
	private int p_C_Currency_ID = 0;
	private int p_C_Country_ID = 0;
	private int p_C_Region_ID = 0;
	private String p_CityName = null;
	private int p_C_City_ID = 0;
	private boolean p_IsUseBPDimension = true;
	private boolean p_IsUseProductDimension = true;
	private boolean p_IsUseProjectDimension = false;
	private boolean p_IsUseCampaignDimension = false;
	private boolean p_IsUseSalesRegionDimension = false;
	private String p_CoAFile = null;

	/** WindowNo for this process */
	public static final int     WINDOW_THIS_PROCESS = 9999;

	/**
	 * 	Prepare
	 */
	@Override
	protected void prepare ()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("ClientName"))
				p_ClientName = (String) para[i].getParameter();
			else if (name.equals("OrgName"))
				p_OrgName = (String) para[i].getParameter();
			else if (name.equals("AdminUserName"))
				p_AdminUserName = (String) para[i].getParameter();
			else if (name.equals("NormalUserName"))
				p_NormalUserName = (String) para[i].getParameter();
			else if (name.equals("C_Currency_ID"))
				p_C_Currency_ID = para[i].getParameterAsInt();
			else if (name.equals("C_Country_ID"))
				p_C_Country_ID = para[i].getParameterAsInt();
			else if (name.equals("C_Region_ID"))
				p_C_Region_ID = para[i].getParameterAsInt();
			else if (name.equals("CityName"))
				p_CityName = (String) para[i].getParameter();
			else if (name.equals("C_City_ID"))
				p_C_City_ID = para[i].getParameterAsInt();
			else if (name.equals("IsUseBPDimension"))
				p_IsUseBPDimension = para[i].getParameter().equals("Y");
			else if (name.equals("IsUseProductDimension"))
				p_IsUseProductDimension = para[i].getParameter().equals("Y");
			else if (name.equals("IsUseProjectDimension"))
				p_IsUseProjectDimension = para[i].getParameter().equals("Y");
			else if (name.equals("IsUseCampaignDimension"))
				p_IsUseCampaignDimension = para[i].getParameter().equals("Y");
			else if (name.equals("IsUseSalesRegionDimension"))
				p_IsUseSalesRegionDimension = para[i].getParameter().equals("Y");
			else if (name.equals("CoAFile"))
				p_CoAFile = (String) para[i].getParameter();
			else
				log.error("Unknown Parameter: " + name);
		}
	}	//	prepare

	/**
	 * 	Process
	 *	@return info
	 *	@throws Exception
	 */
	@Override
	protected String doIt () throws Exception
	{
		log.info("InitialClientSetup"
				+ ": ClientName=" + p_ClientName
				+ ", OrgName=" + p_OrgName
				+ ", AdminUserName=" + p_AdminUserName
				+ ", NormalUserName=" + p_NormalUserName
				+ ", C_Currency_ID=" + p_C_Currency_ID
				+ ", C_Country_ID=" + p_C_Country_ID
				+ ", C_Region_ID=" + p_C_Region_ID
				+ ", CityName=" + p_CityName
				+ ", C_City_ID=" + p_C_City_ID
				+ ", IsUseBPDimension=" + p_IsUseBPDimension
				+ ", IsUseProductDimension=" + p_IsUseProductDimension
				+ ", IsUseProjectDimension=" + p_IsUseProjectDimension
				+ ", IsUseCampaignDimension=" + p_IsUseCampaignDimension
				+ ", IsUseSalesRegionDimension=" + p_IsUseSalesRegionDimension
				+ ", CoAFile=" + p_CoAFile
			);

		// Validations

		// Validate Mandatory parameters
		if (   p_ClientName == null || p_ClientName.length() == 0
			|| p_OrgName == null || p_OrgName.length() == 0
			|| p_AdminUserName == null || p_AdminUserName.length() == 0
			|| p_NormalUserName == null || p_NormalUserName.length() == 0
			|| p_C_Currency_ID <= 0
			|| p_C_Country_ID <= 0
			|| p_CoAFile == null || p_CoAFile.length() == 0
			)
			throw new IllegalArgumentException("Missing required parameters");

		// Validate Uniqueness of client and users name
		//	Unique Client Name
		if (DB.executeUpdate("UPDATE AD_CLient SET CreatedBy=0 WHERE Name=?", new Object[] {p_ClientName}, false, null) != 0)
			throw new AdempiereException("@NotUnique@ " + p_ClientName);

		//	Unique User Names
		if (DB.executeUpdate("UPDATE AD_User SET CreatedBy=0 WHERE Name=?", new Object[] {p_AdminUserName}, false, null) != 0)
			throw new AdempiereException("@NotUnique@ " + p_AdminUserName);
		if (DB.executeUpdate("UPDATE AD_User SET CreatedBy=0 WHERE Name=?", new Object[] {p_NormalUserName}, false, null) != 0)
			throw new AdempiereException("@NotUnique@ " + p_NormalUserName);

		// City_ID overrides CityName if both used
		if (p_C_City_ID > 0)
		{
			final I_C_City city = InterfaceWrapperHelper.create(getCtx(), p_C_City_ID, I_C_City.class, ITrx.TRXNAME_None);
			if (! city.getName().equals(p_CityName)) {
				log.info("City name changed from " + p_CityName + " to " + city.getName());
				p_CityName = city.getName();
			}
		}

		// Validate existence and read permissions on CoA file
		File coaFile = new File(p_CoAFile);
		if (!coaFile.exists())
			throw new AdempiereException("CoaFile " + p_CoAFile + " does not exist");
		if (!coaFile.canRead())
			throw new AdempiereException("Cannot read CoaFile " + p_CoAFile);
		if (!coaFile.isFile())
			throw new AdempiereException("CoaFile " + p_CoAFile + " is not a file");
		if (coaFile.length() <= 0L)
			throw new AdempiereException("CoaFile " + p_CoAFile + " is empty");

		// Process
		MSetup ms = new MSetup(Env.getCtx(), WINDOW_THIS_PROCESS);

		if (! ms.createClient(p_ClientName, p_OrgName, p_AdminUserName, p_NormalUserName)) {
			ms.rollback();
			throw new AdempiereException("Create client failed");
		}
			
		addLog(ms.getInfo());

		//  Generate Accounting
		final I_C_Currency currency = Services.get(ICurrencyDAO.class).retrieveCurrency(getCtx(), p_C_Currency_ID);
		KeyNamePair currency_kp = new KeyNamePair(p_C_Currency_ID, currency.getDescription());
		if (!ms.createAccounting(currency_kp,
			p_IsUseProductDimension, p_IsUseBPDimension, p_IsUseProjectDimension, p_IsUseCampaignDimension, p_IsUseSalesRegionDimension,
			coaFile)) {
			ms.rollback();
			throw new AdempiereException("@AccountSetupError@");
		}

		//  Generate Entities
		if (!ms.createEntities(p_C_Country_ID, p_CityName, p_C_Region_ID, p_C_Currency_ID)) {
			ms.rollback();
			throw new AdempiereException("@AccountSetupError@");
		}
		addLog(ms.getInfo());

		//	Create Print Documents
		PrintUtil.setupPrintForm(ms.getAD_Client_ID());

		return "@OK@";
	}	//	doIt

}	//	InitialClientSetup
