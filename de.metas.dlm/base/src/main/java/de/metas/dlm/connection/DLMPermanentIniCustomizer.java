package de.metas.dlm.connection;

import javax.annotation.concurrent.Immutable;

import org.adempiere.util.Check;
import org.compiere.util.Ini;

/*
 * #%L
 * metasfresh-dlm
 * %%
 * Copyright (C) 2016 metas GmbH
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
/**
 * Customizes the connection using {@link Ini}, but invokes  {@link DLMPermanentSysConfigCustomizer} in case the {@link Ini} does not yet have such a setting.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Immutable
public class DLMPermanentIniCustomizer extends AbstractDLMCustomizer
{
	public static final String INI_P_DLM_COALESCE_LEVEL = "de.metas.dlm.DLM_Coalesce_Level";
	public static final String INI_P_DLM_DLM_LEVEL = "de.metas.dlm.DLM_Level";

	public static AbstractDLMCustomizer INSTANCE = new DLMPermanentIniCustomizer();

	private DLMPermanentIniCustomizer()
	{
	}

	/**
	 * Returns the dlm level set in the {@link Ini} (metasfresh.properties).<br>
	 * <b>Side Effect:</b> if none is set there yet, then it gets the value from {@link DLMPermanentSysConfigCustomizer} and adds it to the {@link Ini}.
	 */
	@Override
	public int getDlmLevel()
	{
		final int dlmLevel;
		final String iniLevel = Ini.getProperty(INI_P_DLM_DLM_LEVEL);
		if (Check.isEmpty(iniLevel))
		{
			dlmLevel = DLMPermanentSysConfigCustomizer.PERMANENT_SYSCONFIG_INSTANCE.getDlmLevel();
			Ini.setProperty(INI_P_DLM_DLM_LEVEL, dlmLevel);
			Ini.saveProperties();
		}
		else
		{
			dlmLevel = Integer.parseInt(iniLevel);
		}
		return dlmLevel;
	}

	/**
	 * Returns the coalesce level set in the {@link Ini} (metasfresh.properties).<br>
	 * <b>Side Effect:</b> if none is set there yet, then it gets the value from {@link DLMPermanentSysConfigCustomizer} and adds it to the {@link Ini}.
	 */
	@Override
	public int getDlmCoalesceLevel()
	{
		final int dlmCoalesceLevel;
		final String iniLevel = Ini.getProperty(INI_P_DLM_COALESCE_LEVEL);
		if (Check.isEmpty(iniLevel))
		{
			dlmCoalesceLevel = DLMPermanentSysConfigCustomizer.PERMANENT_SYSCONFIG_INSTANCE.getDlmCoalesceLevel();
			Ini.setProperty(INI_P_DLM_COALESCE_LEVEL, dlmCoalesceLevel);
			Ini.saveProperties();
		}
		else
		{
			dlmCoalesceLevel = Integer.parseInt(iniLevel);
		}
		return dlmCoalesceLevel;
	}
}
