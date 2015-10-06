package de.metas.handlingunits;

/*
 * #%L
 * de.metas.handlingunits.base
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


import org.adempiere.util.ISingletonService;

/**
 * Used to register and create handlers for custom handling unit changes on documents.
 *
 * @author ad
 *
 */
public interface IHUDocumentHandlerFactory extends ISingletonService
{
	/**
	 * Creates an instance of the class that was previously registered for the given <code>tableName</code> and returns it. If no class was registered, the method returns <code>null</code>.
	 *
	 * @param tableName
	 * @return
	 */
	IHUDocumentHandler createHandler(String tableName);

	/**
	 * Registers the given <code>handler</code> class for the given <code>tableName</code>.
	 * <p>
	 * <b>IMPORTANT: the clas needs to have a default constructor.<b>
	 *
	 * @param tableName
	 * @param handler
	 */
	void registerHandler(String tableName, Class<? extends IHUDocumentHandler> handler);
}
