package de.metas.commission.modelvalidator;

/*
 * #%L
 * de.metas.commission.base
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


import java.util.Properties;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.user.api.IUserBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_User_Roles;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.MSysConfig;
import org.compiere.model.ModelValidator;

import de.metas.adempiere.model.I_AD_User;
import de.metas.commission.model.I_C_Sponsor_SalesRep;
import de.metas.commission.model.X_C_Sponsor_SalesRep;
import de.metas.commission.service.ISponsorDAO;

@Validator(I_C_Sponsor_SalesRep.class)
public class C_Sponsor_SalesRep
{
	final public static String SYS_CONFIG_PORTAL_ROLLE = "Portal_Rolle"; 
	
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void validate(final I_C_Sponsor_SalesRep sponsorSalesRep)
	{
		Services.get(ISponsorDAO.class).validate(sponsorSalesRep);
	}
	
	@ModelChange(
			timings = {ModelValidator.TYPE_AFTER_NEW}, 
			ifColumnsChanged = {I_C_Sponsor_SalesRep.COLUMNNAME_SponsorSalesRepType})
	public void updateContact(I_C_Sponsor_SalesRep rep)
	{
		final int portalRolle = MSysConfig.getIntValue(SYS_CONFIG_PORTAL_ROLLE, 0);
		if (portalRolle > 0 && X_C_Sponsor_SalesRep.SPONSORSALESREPTYPE_VP.equals(rep.getSponsorSalesRepType()))
		{
				final I_C_BPartner bp = rep.getC_BPartner();
				
				final I_AD_User defaultContact = Services.get(IBPartnerDAO.class).retrieveDefaultContactOrNull(bp, I_AD_User.class);
				Check.assume(defaultContact != null, "Partner {} has no default contact!", bp);
				defaultContact.setLogin(bp.getValue());
				defaultContact.setIsSystemUser(true);
				
				Services.get(IUserBL.class).generatedAndSetPassword(defaultContact);
				
				final Properties ctx = InterfaceWrapperHelper.getCtx(defaultContact);
				final String trxName = InterfaceWrapperHelper.getTrxName(defaultContact);
				
				final I_AD_User_Roles ur = InterfaceWrapperHelper.create(ctx, I_AD_User_Roles.class, trxName);
				ur.setAD_User_ID(defaultContact.getAD_User_ID());
				ur.setAD_Role_ID(portalRolle);
				InterfaceWrapperHelper.save(ur);
		}
		
	}
}
