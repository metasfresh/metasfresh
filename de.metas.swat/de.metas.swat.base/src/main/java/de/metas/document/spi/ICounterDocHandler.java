package de.metas.document.spi;

import org.compiere.process.DocAction;

/*
 * #%L
 * de.metas.swat.base
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

/**
 * Note: Implementers usually extend {@link CounterDocumentHandlerAdapter}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface ICounterDocHandler
{
	/**
	 *
	 * @param document
	 * @return <code>true</code> if the system is supposed to create a counter document for the given document.
	 */
	boolean isCreateCounterDocument(DocAction document);

	/**
	 *
	 * @param document
	 * @return <code>true</code> if the given <code>document</code> is actually a counter document.
	 */
	boolean isCounterDocument(DocAction document);

	DocAction createCounterDocument(DocAction document);
}
