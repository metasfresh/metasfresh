/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.model;

import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.service.ADSystemInfo;
import org.adempiere.ad.service.ISystemBL;
import org.adempiere.util.net.NetUtils;
import org.compiere.Adempiere;
import org.compiere.util.DB;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Issue Report Model
 */
@SuppressWarnings("serial")
public class MIssue extends X_AD_Issue
{
	public MIssue(Properties ctx, int AD_Issue_ID, String trxName)
	{
		super(ctx, AD_Issue_ID, trxName);
		if (is_new())
		{
			setProcessed(false);	// N
			setSystemStatus(SYSTEMSTATUS_Evaluation);
			try
			{
				init(ctx);
			}
			catch (Exception e)
			{
				System.err.println("Failed initializing AD_Issue: " + e);
				e.printStackTrace();
			}
		}
	}

	public MIssue(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	private void init(final Properties ctx)
	{
		final ISystemBL systemBL = Services.get(ISystemBL.class);
		final ADSystemInfo system = systemBL.get();

		setName("-");
		setUserName("?");
		setDBAddress("-");
		setSystemStatus(X_AD_Issue.SYSTEMSTATUS_Implementation);
		setReleaseNo("-");
		setVersion(system.getDbVersion());
		setDatabaseInfo(DB.getDatabaseInfo());
		setOperatingSystemInfo(Adempiere.getOSInfo());
		setJavaInfo(Adempiere.getJavaInfo());
		setReleaseTag(Adempiere.getImplementationVersion());
		setLocal_Host(NetUtils.getLocalHost().toString());
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("MIssue[");
		sb.append(get_ID())
				.append("-").append(getIssueSummary())
				.append(",Record=").append(getRecord_ID())
				.append("]");
		return sb.toString();
	}

	@Override
	public void setIssueSummary(String IssueSummary)
	{
		final int maxLength = getPOInfo().getFieldLength(COLUMNNAME_IssueSummary);
		super.setIssueSummary(truncateExceptionsRelatedString(IssueSummary, maxLength));
	}

	@Override
	public void setStackTrace(String StackTrace)
	{
		final int maxLength = getPOInfo().getFieldLength(COLUMNNAME_StackTrace);
		super.setStackTrace(truncateExceptionsRelatedString(StackTrace, maxLength));
	}

	@Override
	public void setErrorTrace(String ErrorTrace)
	{
		final int maxLength = getPOInfo().getFieldLength(COLUMNNAME_ErrorTrace);
		super.setErrorTrace(truncateExceptionsRelatedString(ErrorTrace, maxLength));
	}

	private static String truncateExceptionsRelatedString(final String string, final int maxLength)
	{
		if (string == null || string.isEmpty())
		{
			return string;
		}

		String stringNorm = string;
		stringNorm = stringNorm.replace("java.lang.", "");
		stringNorm = stringNorm.replace("java.sql.", "");

		// Truncate the string if necessary
		if (maxLength > 0 && stringNorm.length() > maxLength)
		{
			stringNorm = stringNorm.substring(0, maxLength - 1);
		}

		return stringNorm;
	}
}
