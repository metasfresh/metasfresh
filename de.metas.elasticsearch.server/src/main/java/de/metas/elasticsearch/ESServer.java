package de.metas.elasticsearch;

import org.elasticsearch.common.logging.ESLoggerFactory;
import org.elasticsearch.common.logging.slf4j.Slf4jESLoggerFactory;

import de.metas.elasticsearch.config.ESModelIndexerConfigBuilder;
import de.metas.elasticsearch.impl.IESServer;
import de.metas.elasticsearch.indexer.IESModelIndexersRegistry;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.elasticsearch.server
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class ESServer implements IESServer
{
	public ESServer()
	{
		// Make sure slf4j is used.
		// (by default, log4j is used)
		ESLoggerFactory.setDefaultFactory(new Slf4jESLoggerFactory());
	}
	@Override
	public void installConfig(final ESModelIndexerConfigBuilder config)
	{
		Services.get(IESModelIndexersRegistry.class).addModelIndexer(config);
	}
}
