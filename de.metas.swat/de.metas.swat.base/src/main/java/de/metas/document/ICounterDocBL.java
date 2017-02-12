package de.metas.document;

import org.adempiere.ad.modelvalidator.IModelInterceptor;
import org.adempiere.util.ISingletonService;
import org.compiere.process.DocAction;

import de.metas.document.spi.ICounterDocHandler;

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
 * Note: there is still counter-doc logic in various {@link DocAction} implementors. As of now, we only deal with <code>C_Order</code>.
 *
 * @author metas-dev <dev@metasfresh.com>
 * @task http://dewiki908/mediawiki/index.php/09700_Counter_Documents_(100691234288)
 */
public interface ICounterDocBL extends ISingletonService
{
	/**
	 *
	 * @param document
	 * @return <code>true</code> iff the given document is a {@link DocAction}, an {@link ICounterDocHandler} is registered for the given <code>document</code> and if that handler's
	 *         {@link ICounterDocHandler#isCreateCounterDocument(DocAction)} method returns <code>true</code>.
	 */
	boolean isCreateCounterDocument(Object document);

	/**
	 * 
	 * @param document the document for which we need a counter doc
	 * @param async if <code>true</code> then the counter doc is not created directly, but a work package is enqueued
	 * @return the created counter document or <code>null</code> if either the {@link ICounterDocHandler} returned <code>null</code>, or if no handler was registered for the given
	 *         <code>document</code>'s have name.
	 */
	DocAction createCounterDocument(Object document, boolean async);

	/**
	 * Intended to be called from an {@link org.adempiere.ad.modelvalidator.AbstractModuleInterceptor}.<br>
	 * Registers the given handler for the given table and returns a model interceptor that is supposed to be registered with the system.
	 *
	 * @param handler
	 * @param tableName
	 * @return
	 */
	IModelInterceptor registerHandler(ICounterDocHandler handler, String tableName);

}
