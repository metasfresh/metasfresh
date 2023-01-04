package de.metas;

import de.metas.logging.LogManager;
import de.metas.util.Services;
import org.adempiere.ad.service.ADSystemInfo;
import org.adempiere.ad.service.ISystemBL;
import org.slf4j.Logger;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Component
public class MetasInfoContributor implements InfoContributor
{
	private static final Logger logger = LogManager.getLogger(MetasInfoContributor.class);

	@Override
	public void contribute(final Info.Builder builder)
	{
		builder.withDetail("AD_System", retrieveADSystemInfo());
	}

	/**
	 * Task https://github.com/metasfresh/metasfresh/issues/2601
	 */
	private static Map<String, String> retrieveADSystemInfo()
	{
		final Map<String, String> adSystemInfo = new HashMap<>();
		try
		{
			final ADSystemInfo adSystem = Services.get(ISystemBL.class).get();
			adSystemInfo.put("dbVersion", adSystem.getDbVersion());
		}
		catch (final Exception ex)
		{
			logger.warn("Failed fetching AD_System info", ex);
			adSystemInfo.put("error", ex.getLocalizedMessage());
		}

		return adSystemInfo;
	}
}
