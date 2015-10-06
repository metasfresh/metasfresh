/**
 * 
 */
package de.metas.async.api;

/*
 * #%L
 * de.metas.async
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


import java.util.Properties;

import org.adempiere.util.ISingletonService;

import de.metas.async.model.I_C_Async_Batch_Type;

/**
 * @author cg
 *
 */
public interface IAsyncBatchDAO extends ISingletonService
{
	/**
	 * Retrieve async batch type by internal name which must be unique.
	 * 
	 * @param ctx
	 * @param internalName
	 * @param trxName
	 * @return {@link I_C_Async_Batch_Type}; never returns null
	 */
	I_C_Async_Batch_Type retrieveAsyncBatchType(Properties ctx, String internalName);
}
