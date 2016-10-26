package de.metas.connection;

import java.util.List;

import org.adempiere.util.ISingletonService;

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

public interface IConnectionCustomizerService extends ISingletonService
{
	/**
	 * Registers the given instance to be applied whenever a connected is checked out of the database connection pool.
	 *
	 * @param connectionCustomizer should be a singleton
	 */
	void registerPermanentCustomizer(IConnectionCustomizer connectionCustomizer);

	/**
	 *
	 * @return both the permanent customizers and the thread-local temporary customizers
	 */
	List<IConnectionCustomizer> getRegisteredCustomizers();

	/**
	 * Add the given customizer to a the <b>thread-local</b> list of temporary customizers and wrap it into an {@link AutoCloseable}.
	 * Its {@link AutoCloseable#close()} method will remove the customizer from the list and invoke {@link ITemporaryConnectionCustomizer#undoCustomization()}.
	 * <p>
	 * Hint: use this method within a <code>try</code>-with-resources statement.
	 *
	 * @param connectionCustomizer
	 * @return
	 */
	AutoCloseable registerTemporaryCustomizer(ITemporaryConnectionCustomizer connectionCustomizer);

}
