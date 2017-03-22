package de.metas.connection;

import java.sql.Connection;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * Registered to the system via {@link IConnectionCustomizerService#registerPermanentCustomizer(IConnectionCustomizer)}.
 * <p>
 * About thread-safety: I can't prove that implementors need to be thread-safe without further research,
 * but unless you proved they don't need to be thread-safe, i strongly urge everyone to make them thread-safe.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IConnectionCustomizer
{
	/**
	 * Make customizations to the given <code>connection</code>. If this customizer was registered, then this method is called when the given <code>connection</code> was checked out from the connection pool.
	 *
	 * @param connection
	 *
	 * @see IConnectionCustomizerService
	 * @see com.mchange.v2.c3p0.AbstractConnectionCustomizer#onCheckOut(Connection, String)
	 */
	void customizeConnection(Connection connection);
}
