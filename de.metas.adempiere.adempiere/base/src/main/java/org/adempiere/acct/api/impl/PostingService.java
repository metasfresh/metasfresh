package org.adempiere.acct.api.impl;

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


import org.adempiere.acct.api.ClientAccountingStatus;
import org.adempiere.acct.api.IPostingRequestBuilder;
import org.adempiere.acct.api.IPostingService;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;

public class PostingService implements IPostingService
{
	/** Flag indicating that the whole accounting module is enabled or disabled */
	private static final String SYSCONFIG_Enabled = "org.adempiere.acct.Enabled";
	
	/** Client Account Status */
	private static final String SYSCONFIG_ClientAccountingStatus = "CLIENT_ACCOUNTING";

	@Override
	public boolean isEnabled()
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		final boolean defaultValue = true; // enabled by default
		return sysConfigBL.getBooleanValue(SYSCONFIG_Enabled, defaultValue); 
	}

	@Override
	public ClientAccountingStatus getClientAccountingStatus()
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

		final int adClientId = Env.getAD_Client_ID(Env.getCtx());
		final String statusCode = sysConfigBL.getValue(SYSCONFIG_ClientAccountingStatus,
				ClientAccountingStatus.Disabled.getStatusCode(), // default
				adClientId);

		return ClientAccountingStatus.forStatusCode(statusCode);
	}

	@Override
	public void setClientAccountingStatus(final ClientAccountingStatus status)
	{
		Check.assumeNotNull(status, "status not null");

		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

		final int adOrgId = 0;
		sysConfigBL.setValue(SYSCONFIG_ClientAccountingStatus, status.getStatusCode(), adOrgId);
	}

	@Override
	public boolean isClientAccountingEnabled()
	{
		final ClientAccountingStatus status = getClientAccountingStatus();
		return ClientAccountingStatus.Immediate == status
				|| ClientAccountingStatus.Queue == status;
	}

	@Override
	public IPostingRequestBuilder newPostingRequest()
	{
		return new PostingRequestBuilder();
	}
}
