package de.metas.elasticsearch;

import org.elasticsearch.common.logging.ESLoggerFactory;
import org.elasticsearch.common.logging.slf4j.Slf4jESLoggerFactory;

/*
 * #%L
 * de.metas.elasticsearch.server
 * %%
 * Copyright (C) 2018 metas GmbH
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
 * Sets SLF4J logging for elasticsearch.
 * 
 * It's called by {@link de.metas.elasticsearch.ESLoggingInit#init()}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class ESLoggingInitImpl
{
	static
	{
		ESLoggerFactory.setDefaultFactory(new Slf4jESLoggerFactory());
	}
}
