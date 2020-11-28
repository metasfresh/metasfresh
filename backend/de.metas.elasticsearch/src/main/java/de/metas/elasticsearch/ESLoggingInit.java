package de.metas.elasticsearch;

import org.slf4j.Logger;

import de.metas.logging.LogManager;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.elasticsearch
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

@UtilityClass
public class ESLoggingInit
{
	private static final Logger logger = LogManager.getLogger(ESLoggingInit.class);

	private static final String IMPL_CLASSNAME = "de.metas.elasticsearch.ESLoggingInitImpl";

	public static void init()
	{
		try
		{
			Thread.currentThread()
					.getContextClassLoader()
					.loadClass(IMPL_CLASSNAME)
					.newInstance();
		}
		catch (final ClassNotFoundException ex)
		{
			logger.debug("No {} class found. Do nothing.", IMPL_CLASSNAME);
		}
		catch (final Throwable ex)
		{
			logger.warn("Failed loading {}. Skip.", IMPL_CLASSNAME, ex);
		}
	}
}
