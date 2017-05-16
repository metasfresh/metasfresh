package org.adempiere.bpartner.service;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MOrg;
import org.compiere.util.Env;
import org.compiere.util.Util;

import de.metas.i18n.Msg;

public final class OrgHasNoBPartnerLinkException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3576321750973121078L;

	private static final String MSG = "NotExistsBPLinkedforOrgError";

	public final int orgId;

	public OrgHasNoBPartnerLinkException(final int orgId)
	{
		super(buildMsg(orgId, null));
		this.orgId = orgId;
	}

	public OrgHasNoBPartnerLinkException(final int orgId, final String additionalMessage)
	{
		super(buildMsg(orgId, additionalMessage));
		this.orgId = orgId;
	}
	
	private static String buildMsg(int orgId, String additionalMessage)
	{
		final Properties ctx = Env.getCtx();
		
		final StringBuffer sb = new StringBuffer();
		
		sb.append(Msg.getMsg(ctx, MSG));
		
		if (!Util.isEmpty(additionalMessage, true))
		{
			sb.append(" - ").append(additionalMessage);
		}
		
		if (orgId > 0)
		{
			String orgName = getOrgName(orgId);
			sb.append("(").append(Msg.translate(ctx, "AD_Org_ID")).append(": ").append(orgName).append(")");
		}
		
		return sb.toString();
	}
	
	private static final String getOrgName(int orgId)
	{
		if (orgId < 0)
		{
			return "?";
		}
		
		final Properties ctx = Env.getCtx();
		final MOrg org = MOrg.get(ctx, orgId);
		if (org == null)
		{
			return "<"+orgId+">";
		}
		
		return org.getName();
	}
}
