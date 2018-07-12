package org.adempiere.acct.api.impl;

import org.adempiere.acct.api.IPostingRequestBuilder;
import org.adempiere.acct.api.IPostingService;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;

public class PostingService implements IPostingService
{
	/** Flag indicating that the whole accounting module is enabled or disabled */
	private static final String SYSCONFIG_Enabled = "org.adempiere.acct.Enabled";

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
