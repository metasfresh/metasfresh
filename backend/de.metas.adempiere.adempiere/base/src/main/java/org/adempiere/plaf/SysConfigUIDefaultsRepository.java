package org.adempiere.plaf;

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

import java.util.Map;
import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;

import javax.swing.UIDefaults;
import javax.swing.UIManager;

import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.ad.service.IDeveloperModeBL.ContextRunnable;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * {@link UIDefaults} SysConfig repository. It supports following actions:
 * <ul>
 * <li>load all UIDefaults from {@link I_AD_SysConfig} table: {@link #loadAllFromSysConfigTo(UIDefaults)}
 * <li>persist one value to {@link I_AD_SysConfig} table: {@link #setValue(Object, Object)}
 * </ul>
 *
 * @author tsa
 *
 */
public class SysConfigUIDefaultsRepository
{
	public static SysConfigUIDefaultsRepository ofLookAndFeelId(final String lookAndFeelId)
	{
		return new SysConfigUIDefaultsRepository(lookAndFeelId);
	}

	public static SysConfigUIDefaultsRepository ofCurrentLookAndFeelId()
	{
		final String lookAndFeelId = UIManager.getLookAndFeel().getID();
		return new SysConfigUIDefaultsRepository(lookAndFeelId);
	}

	// services

	private static final transient Logger logger = LogManager.getLogger(SysConfigUIDefaultsRepository.class);
	private final UIDefaultsSerializer serializer = new UIDefaultsSerializer();
	private final transient ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final transient IDeveloperModeBL developerModeBL = Services.get(IDeveloperModeBL.class);

	private static final String SYSCONFIG_PREFIX = "LAF.";

	private final String lookAndFeelId;

	public SysConfigUIDefaultsRepository(final String lookAndFeelId)
	{
		super();
		Check.assumeNotEmpty(lookAndFeelId, "lookAndFeelId not empty");
		this.lookAndFeelId = lookAndFeelId;
	}

	public void setValue(final Object key, final Object value)
	{
		final String sysconfigName = createSysConfigName(key);
		try
		{
			final String sysconfigValue = serializer.toString(value);
			saveSysConfig(sysconfigName, sysconfigValue);
		}
		catch (Exception e)
		{
			logger.error("Failed saving " + sysconfigName + ": " + value, e);
		}
	}

	private void saveSysConfig(final String sysconfigName, final String sysconfigValue)
	{
		if (!DB.isConnected())
		{
			logger.warn("DB not connected. Cannot write: " + sysconfigName + "=" + sysconfigValue);
			return;
		}

		developerModeBL.executeAsSystem(new ContextRunnable()
		{
			@Override
			public void run(Properties sysCtx)
			{
				sysConfigBL.setValue(sysconfigName, sysconfigValue, ClientId.SYSTEM, OrgId.ANY);
			}
		});
	}

	private String createSysConfigName(Object key)
	{
		return createSysConfigPrefix() + "." + key.toString();
	}

	private String createSysConfigPrefix()
	{
		return SYSCONFIG_PREFIX + lookAndFeelId;
	}

	public void loadAllFromSysConfigTo(final UIDefaults uiDefaults)
	{
		if (!DB.isConnected())
		{
			return;
		}

		final String prefix = createSysConfigPrefix() + ".";
		final boolean removePrefix = true;
		final Properties ctx = Env.getCtx();
		final int adClientId = Env.getAD_Client_ID(ctx);
		final int adOrgId = Env.getAD_Org_ID(ctx);

		final Map<String, String> map = sysConfigBL.getValuesForPrefix(prefix, removePrefix, adClientId, adOrgId);

		for (final Map.Entry<String, String> mapEntry : map.entrySet())
		{
			final String key = mapEntry.getKey();
			final String valueStr = mapEntry.getValue();

			try
			{
				final Object value = serializer.fromString(valueStr);
				uiDefaults.put(key, value);
			}
			catch (Exception ex)
			{
				logger.warn("Failed loading " + key + ": " + valueStr + ". Skipped.", ex);
			}
		}
	}
}
