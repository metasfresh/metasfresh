/**
 * 
 */
package de.metas.callcenter.model;

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


import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.X_R_Group;
import org.compiere.model.X_R_Request;

/**
 * @author teo_sarca
 *
 */
public class CallCenterValidator implements ModelValidator
{
	public static final String ENTITYTYPE="de.metas.callcenter";
	
//	private final Logger log = CLogMgt.getLogger(getClass());
	
	private int m_AD_Client_ID = -1;

	//@Override
	public int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}

	//@Override
	public void initialize(ModelValidationEngine engine, MClient client)
	{
		if (client != null)
			m_AD_Client_ID = client.getAD_Client_ID();
		engine.addModelChange(X_R_Request.Table_Name, this);
		engine.addModelChange(X_R_Group.Table_Name, this);
	}

	//@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		return null;
	}

	//@Override
	public String modelChange(PO po, int type) throws Exception
	{
		if (po instanceof X_R_Request && type == TYPE_AFTER_NEW)
		{
			X_R_Request r = (X_R_Request)po;
			MRGroupProspect.linkRequest(r.getCtx(), r, r.get_TrxName());
		}
		else if (po instanceof X_R_Group && (type == TYPE_AFTER_NEW || type == TYPE_AFTER_CHANGE))
		{
			X_R_Group bundle = (X_R_Group)po;
			BundleUtil.updateCCM_Bundle_Status(bundle);
		}
		return null;
	}

	//@Override
	public String docValidate(PO po, int timing)
	{
		return null;
	}
}
