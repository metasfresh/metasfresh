/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.error;

import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.service.ADSystemInfo;
import org.adempiere.ad.service.ISystemBL;
import org.adempiere.util.net.NetUtils;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Issue;
import org.compiere.util.DB;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

@UtilityClass
public class AdIssueFactory
{
	public I_AD_Issue prepareNewIssueRecord(@NonNull final Properties ctx)
	{
		final I_AD_Issue issue = newInstance(I_AD_Issue.class);
		return prepareNewIssueRecord(ctx, issue);
	}

	public I_AD_Issue prepareNewIssueRecord(@NonNull final Properties ctx, @NonNull final I_AD_Issue issue)
	{
		final ISystemBL systemBL = Services.get(ISystemBL.class);
		final ADSystemInfo system = systemBL.get();

		issue.setName("-");
		issue.setUserName("?");
		issue.setDBAddress("-");
		issue.setSystemStatus(system.getSystemStatus());
		issue.setReleaseNo("-");
		issue.setVersion(system.getDbVersion());
		issue.setDatabaseInfo(DB.getDatabaseInfo());
		issue.setOperatingSystemInfo(Adempiere.getOSInfo());
		issue.setJavaInfo(Adempiere.getJavaInfo());
		issue.setReleaseTag(Adempiere.getImplementationVersion());
		issue.setLocal_Host(NetUtils.getLocalHost().toString());

		final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes instanceof ServletRequestAttributes)
		{
			final ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)requestAttributes;
			final HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();
			issue.setRemote_Addr(httpServletRequest.getRemoteAddr());
			issue.setRemote_Host(httpServletRequest.getRemoteHost());

			final String userAgent = httpServletRequest.getHeader("User-Agent");
			issue.setUserAgent(userAgent);
		}

		return issue;
	}

}
