package de.metas.document.documentNo.spi;

import de.metas.document.documentNo.IDocumentNoBL;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Implementors shall be registered using {@link IDocumentNoBL#registerDocumentNoListener(IDocumentNoListener)}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IDocumentNoListener
{
	/**
	 * Return the name of the table this listener is for. If required, replace this method with one that returns some sort of matcher or selector object.
	 *
	 * @return never return <code>null</code>
	 */
	String getTableName();

	void onDocumentNoChange(IDocumentNoAware model, String newDocumentNo);
}
