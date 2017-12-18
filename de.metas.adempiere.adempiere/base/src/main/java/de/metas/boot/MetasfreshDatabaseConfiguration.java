package de.metas.boot;

import org.compiere.db.CConnection;
import org.compiere.model.MLanguage;
import org.compiere.util.DB;
import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;

import de.metas.logging.LogManager;

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

@Configuration(MetasfreshDatabaseConfiguration.BEANNAME)
public class MetasfreshDatabaseConfiguration
{
	static final String BEANNAME = "metasfreshDatabaseConfiguration";
	
	private static final Logger logger = LogManager.getLogger(MetasfreshDatabaseConfiguration.class);

	public MetasfreshDatabaseConfiguration(final MetasfreshPhase0Configuration initialConfig)
	{
		init();
	}

	private void init()
	{
		DB.setDBTarget(CConnection.get());

		MLanguage.setBaseLanguage(); // metas

		logger.info("Init done");
	}
}
