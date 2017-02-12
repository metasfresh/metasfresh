package de.metas.logging;

import org.slf4j.Logger;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


/**
 * Implementations of this interface are used to customize {@link Logger} when they are returned to callers.
 *
 * @author tsa
 *
 */
public interface ILoggerCustomizer
{
	void customize(Logger logger);

	/**
	 *
	 * @return information that is useful to understand the customizer's behavior
	 * @task https://github.com/metasfresh/metasfresh/issues/288
	 */
	String dumpConfig();
}
