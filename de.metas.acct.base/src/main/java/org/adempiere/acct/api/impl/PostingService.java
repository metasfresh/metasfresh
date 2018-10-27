package org.adempiere.acct.api.impl;

import org.adempiere.acct.api.IPostingRequestBuilder;
import org.adempiere.acct.api.IPostingService;
import org.adempiere.service.ISysConfigBL;

import de.metas.util.Services;

public class PostingService implements IPostingService
{
	@Override
	public boolean isEnabled()
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		final boolean defaultValue = true; // enabled by default
		return sysConfigBL.getBooleanValue(SYSCONFIG_Enabled, defaultValue);
	}

	@Override
	public IPostingRequestBuilder newPostingRequest()
	{
		return new PostingRequestBuilder();
	}
}
