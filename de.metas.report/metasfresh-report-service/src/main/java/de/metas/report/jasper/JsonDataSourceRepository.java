/**
 *
 */
package de.metas.report.jasper;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.ISysConfigBL;
import org.compiere.util.DB;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * report-service
 * %%
 * Copyright (C) 2019 metas GmbH
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
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Repository
public class JsonDataSourceRepository
{
	private static final Logger logger = LogManager.getLogger(JsonDataSourceRepository.class);

	private static final String SYSCONFIG_RESTAPI_URL = "API_URL";

	public String getAPIUrlOrNull()
	{
		final String url = Services.get(ISysConfigBL.class).getValue(SYSCONFIG_RESTAPI_URL, "");
		if (Check.isEmpty(url, true) || "-".equals(url))
		{
			logger.warn("{} is not configured. Jasper Json reports will not work", SYSCONFIG_RESTAPI_URL);
			return null;
		}

		return url.trim();
	}

	public String retrieveSQLValue(@NonNull final String sqlFinal)
	{
		return DB.getSQLValueStringEx(ITrx.TRXNAME_ThreadInherited, sqlFinal);
	}

}
