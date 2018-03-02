package org.adempiere.service;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.util.Env;

public interface IOrgDAO extends ISingletonService
{
	I_AD_Org retrieveOrg(Properties ctx, int adOrgId);

	default I_AD_Org retrieveOrg(final int adOrgId)
	{
		return retrieveOrg(Env.getCtx(), adOrgId);
	}

	default String retrieveOrgName(final int adOrgId)
	{
		final I_AD_Org org = retrieveOrg(adOrgId);
		return org != null ? org.getName() : null;
	}

	I_AD_OrgInfo retrieveOrgInfo(Properties ctx, int adOrgId, String trxName);

	default I_AD_OrgInfo retrieveOrgInfo(int adOrgId)
	{
		return retrieveOrgInfo(Env.getCtx(), adOrgId, ITrx.TRXNAME_None);
	}

	/**
	 * Search for the organization when the value is known
	 * 
	 * @param ctx
	 * @param value
	 * @return AD_Org Object if the organization was found, null otherwise.
	 */
	I_AD_Org retrieveOrganizationByValue(Properties ctx, String value);

	List<I_AD_Org> retrieveClientOrgs(Properties ctx, int adClientId);

	List<I_AD_Org> retrieveChildOrgs(Properties ctx, int parentOrgId, int adTreeOrgId);
}
