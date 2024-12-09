package org.adempiere.server.rpl.api;

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

<<<<<<< HEAD

import java.util.Properties;

import org.w3c.dom.Document;

=======
import org.w3c.dom.Document;

import java.util.Properties;

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
/**
 * Helper used to actual import an XML document
 * 
 * @author tsa
 * 
 */
public interface IImportHelper
{

	/**
	 * After initialization API calls this method to configure initial context
<<<<<<< HEAD
	 * 
	 * @param initialCtx
	 */
	public void setInitialCtx(Properties initialCtx);
=======
	 */
	void setInitialCtx(Properties initialCtx);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Import XML document
	 * 
<<<<<<< HEAD
	 * @param ctx
	 * @param result
	 * @param documentToBeImported
	 * @param trxName
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * @return response document or null if there is no response
	 */
	Document importXMLDocument(StringBuilder result, Document documentToBeImported, String trxName);
}
