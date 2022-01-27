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
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.service.ISystemBL;
import org.adempiere.util.net.NetUtils;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Issue;
import org.compiere.model.I_AD_System;
import org.compiere.util.DB;

import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.create;
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
		final I_AD_System system = systemBL.get(ctx);

		issue.setName(system.getName());
		issue.setUserName(system.getUserName());
		issue.setDBAddress(system.getDBAddress());
		issue.setSystemStatus(system.getSystemStatus());
		issue.setReleaseNo(system.getReleaseNo());    // DB

		final String version = StringUtils.trimBlankToOptional(Adempiere.getDateVersion()).orElse("?");
		issue.setVersion(version);

		issue.setDatabaseInfo(DB.getDatabaseInfo());
		issue.setOperatingSystemInfo(Adempiere.getOSInfo());
		issue.setJavaInfo(Adempiere.getJavaInfo());
		issue.setReleaseTag(Adempiere.getImplementationVersion());
		issue.setLocal_Host(NetUtils.getLocalHost().toString());
		if (system.isAllowStatistics())
		{
			// NOTE: there is no need to recalculate how many tenants, bpartners, invoices etc are in the system.
			// An aproximative information is also acceptable.
			// Also counting all those infos could be quite expensive.
			final boolean recalc = false;
			issue.setStatisticsInfo(systemBL.getStatisticsInfo(recalc));
			issue.setProfileInfo(systemBL.getProfileInfo(recalc));
		}
		return issue;
	}

}
