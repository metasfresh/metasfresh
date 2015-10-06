/**
 * 
 */
package de.metas.shipping.model.validator;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.POWrapper;
import org.adempiere.util.Check;
import org.compiere.model.MClient;
import org.compiere.model.MInOut;
import org.compiere.model.MOrder;
import org.compiere.model.MPackageLine;
import org.compiere.model.MSysConfig;
import org.compiere.model.MUser;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.CLogger;
import org.compiere.util.EMail;
import org.compiere.util.Env;

import de.metas.interfaces.I_C_BPartner;
import de.metas.letters.model.IEMailEditor;
import de.metas.letters.model.MADBoilerPlate;
import de.metas.shipping.model.MMShipperTransportation;
import de.metas.shipping.model.MMShippingPackage;

/**
 * @author Cristina Ghita, metas.ro
 *
 */
public class ShipperTransportationMailNotification implements ModelValidator 
{
	final private String SYS_CONFIG_SHIP = "SHIP_MAIL_NOTIFICATION";
	
	private int m_AD_Client_ID = -1;
	private int p_SMTPRetriesNo = 3;
	
	private CLogger log = CLogger.getCLogger (getClass());
	
	@Override
	public String docValidate(PO po, int type)
	{
		if (po instanceof MMShipperTransportation && type == TIMING_AFTER_COMPLETE  )
		{
			MMShipperTransportation ship = (MMShipperTransportation)po;
			{
				Properties ctx = Env.getCtx();
				int AD_BoilerPlate_ID = MSysConfig.getIntValue(SYS_CONFIG_SHIP, 0, Env.getAD_Client_ID(ctx), Env.getAD_Org_ID(ctx));
				final MADBoilerPlate text = MADBoilerPlate.get(ctx, AD_BoilerPlate_ID);
				
				
					for (MMShippingPackage sp : getShippingPackage(ship.getM_ShipperTransportation_ID()))
					{
						//get user for sendind mail
						MUser user = null;
						MUser orderUser = null;
						// check first user from order
						MOrder order = (MOrder)sp.getM_InOut().getC_Order();
						if (order != null)
							user = (MUser)order.getAD_User();
						if (user == null)
							user = (MUser)sp.getM_InOut().getAD_User();
						else
							orderUser = user;
						//
						I_C_BPartner partnerPO = POWrapper.create(user.getC_BPartner(), I_C_BPartner.class);
						if (partnerPO.isShippingNotificationEmail() && user.get_ValueAsBoolean("IsDefaultContact"))
						{
							String message = sendEMail(text, (MInOut)sp.getM_InOut(), orderUser);
							if (Check.isEmpty(message, true))
							{
								for (MPackageLine pl : getPackageLine(sp.getM_Package_ID()))
								{
									setNotified(pl);
									pl.saveEx();
								}
							}
						}
					}
			}
		}
		return null;
	}
	
	private MMShippingPackage[] getShippingPackage(int M_ShipperTransportation_ID)
	{
		String whereClause = MMShippingPackage.COLUMNNAME_M_ShipperTransportation_ID + " = ?";
		List<MMShippingPackage> list = new Query(Env.getCtx(), MMShippingPackage.Table_Name, whereClause, null)
								.setClient_ID()
								.setParameters(M_ShipperTransportation_ID)
								.list();
		return list.toArray(new MMShippingPackage[list.size()]);
	}
	
	private MPackageLine[] getPackageLine(int M_Package_ID)
	{
		String whereClause = MPackageLine.COLUMNNAME_M_Package_ID + " = ?";
		List<MMShippingPackage> list = new Query(Env.getCtx(), MPackageLine.Table_Name, whereClause, null)
								.setClient_ID()
								.setParameters(M_Package_ID)
								.list();
		return list.toArray(new MPackageLine[list.size()]);
	}

	@Override
	public int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}

	@Override
	public void initialize(ModelValidationEngine engine, MClient client)
	{
		if (client != null)
			m_AD_Client_ID = client.getAD_Client_ID();
		//
		engine.addDocValidate(MMShipperTransportation.Table_Name, this);
		
	}

	@Override
	public String login(int arg0, int arg1, int arg2)
	{
		return null;
	}

	@Override
	public String modelChange(PO arg0, int arg1) throws Exception
	{
		return null;
	}
	
	private String sendEMail(final MADBoilerPlate text, final MInOut io, final MUser orderUser)
	{
		final Properties ctx = Env.getCtx();
		MADBoilerPlate.sendEMail(new IEMailEditor() {
			public Object getBaseObject()
			{
				return io;
			}
			public int getAD_Table_ID()
			{
				return io.get_Table_ID();
			}
			public int getRecord_ID()
			{
				return io.get_ID();
			}
			public EMail sendEMail(MUser from, String toEmail, String subject, Map<String, Object> variables)
			{
				variables.put(MADBoilerPlate.VAR_UserPO, io);
				//
				String message = text.getTextSnippetParsed(variables);
				//
				if (Check.isEmpty(message, true))
					return null;
				//
				StringTokenizer st = new StringTokenizer(toEmail, " ,;", false);
				String to = st.nextToken();
				MClient client = MClient.get(ctx, Env.getAD_Client_ID(ctx));
				if (orderUser !=null)
					to = orderUser.getEMail();
				EMail email = client.createEMail(null, to,
						text.getName(),
						message,
						true);
				if (email == null)
				{
					throw new AdempiereException("Cannot create email. Check log.");
				}
				while (st.hasMoreTokens())
					email.addTo(st.nextToken());
				send(email);
				return email;
			}
		}, false);
		
		return "";
	}

	private void send(EMail email)
	{
		int maxRetries = p_SMTPRetriesNo > 0 ? p_SMTPRetriesNo : 0;
		int count = 0;
		do
		{
			final String status = email.send();
			count++;
			if (email.isSentOK())
				return;
			// Timeout => retry
			if (status != null && status.indexOf("Could not connect to SMTP host:") != -1
					&& count < maxRetries)
			{
				log.warning("SMTP error: "+status+" [ Retry "+count+" ]");
			}
			else
			{
				throw new AdempiereException(status);
			}
		}
		while(true);
	}
	
	private void setNotified(MPackageLine packageLine)
	{
		packageLine.set_ValueOfColumn("IsSentMailNotification", true);
	}
	
}
