package de.metas.document.documentNo;

import org.adempiere.util.ISingletonService;

import de.metas.document.documentNo.spi.IDocumentNoListener;

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
 * Dedicated service for document numbers. Instead of handling document number change in the code, please invoke this service's {@link #fireDocumentNoChange(Object, String)} method.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IDocumentNoBL extends ISingletonService
{
	/**
	 * Register another listener.
	 *
	 * @param listener
	 */
	void registerDocumentNoListener(IDocumentNoListener listener);

	/**
	 * Notify the listeners of a changed documentNo.
	 *
	 * @param model
	 * @param newDocumentNo
	 */
	void fireDocumentNoChange(Object model, String newDocumentNo);
}
