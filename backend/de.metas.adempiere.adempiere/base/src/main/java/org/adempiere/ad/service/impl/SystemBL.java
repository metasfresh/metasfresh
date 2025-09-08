package org.adempiere.ad.service.impl;

import de.metas.cache.CCache;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.service.ADSystemInfo;
import org.adempiere.ad.service.ISystemBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_AD_System;

public class SystemBL implements ISystemBL
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, ADSystemInfo> cache = CCache.<Integer, ADSystemInfo>builder()
			.tableName(I_AD_System.Table_Name)
			.build();

	@Override
	public ADSystemInfo get()
	{
		return cache.getOrLoad(0, this::retrieveADSystemInfo);
	}

	private ADSystemInfo retrieveADSystemInfo()
	{
		final I_AD_System record = queryBL.createQueryBuilderOutOfTrx(I_AD_System.class)
				.create()
				.firstOnly(I_AD_System.class);

		// shall not happen
		if (record == null)
		{
			throw new AdempiereException("No AD_System record found");
		}

		return ADSystemInfo.builder()
				.dbVersion(record.getDBVersion())
				.systemStatus(record.getSystemStatus())
				.lastBuildInfo(record.getLastBuildInfo())
				.failOnMissingModelValidator(record.isFailOnMissingModelValidator())
				.build();
	}

	/*
	 * Allow remember me feature
	 * ZK_LOGIN_ALLOW_REMEMBER_ME and SWING_ALLOW_REMEMBER_ME parameter allow the next values
	 * U - Allow remember the username (default for zk)
	 * P - Allow remember the username and password (default for swing)
	 * N - None
	 *
	 * @return boolean representing if remember me feature is allowed
	 */
	public static final String SYSTEM_ALLOW_REMEMBER_USER = "U";
	public static final String SYSTEM_ALLOW_REMEMBER_PASSWORD = "P";

	@Override
	public boolean isRememberUserAllowed(@NonNull final String sysConfigKey)
	{
		String ca = Services.get(ISysConfigBL.class).getValue(sysConfigKey, SYSTEM_ALLOW_REMEMBER_PASSWORD);
		return (ca.equalsIgnoreCase(SYSTEM_ALLOW_REMEMBER_USER) || ca.equalsIgnoreCase(SYSTEM_ALLOW_REMEMBER_PASSWORD));
	}

	@Override
	public boolean isRememberPasswordAllowed(@NonNull final String sysConfigKey)
	{
		String ca = Services.get(ISysConfigBL.class).getValue(sysConfigKey, SYSTEM_ALLOW_REMEMBER_PASSWORD);
		return (ca.equalsIgnoreCase(SYSTEM_ALLOW_REMEMBER_PASSWORD));
	}
}
