package de.metas.invoicecandidate.api;

import org.slf4j.Logger;
import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.swat.base
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

public class InvoiceCandidate_Constants
{
	public static final String LOGGERNAME_ROOT = InvoiceCandidate_Constants.class.getPackage().getName();
	
	public static final Logger getLogger(Class<?> clazz)
	{
		return LogManager.getLogger(clazz);
	}

	public static final String ENTITY_TYPE = "de.metas.invoicecandidate";
	
	public static final String JMX_BASE_NAME = ENTITY_TYPE;

	/**
	 * Internal name of the AD_Input_Data destination for invoice candidates candidates.
	 * 
	 * @see de.metas.impex.api.IInputDataSourceDAO#retrieveInputDataSource(java.util.Properties, String, boolean, String)
	 */
	public static final String DATA_DESTINATION_INTERNAL_NAME = "DEST." + ENTITY_TYPE;
	
	/**
	 * Internal Name for invoice candidate asycn batch
	 */
	public static final String C_Async_Batch_InternalName_InvoiceCandidate = "InvoiceCandidate";

}
