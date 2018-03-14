package de.metas.ordercandidate.api;

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


import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;

import de.metas.interfaces.I_C_OrderLine;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.model.I_C_Order_Line_Alloc;

public interface IOLCandDAO extends ISingletonService
{
	<T extends I_C_OLCand> List<T> retrieveOLCands(I_C_OrderLine ol, Class<T> clazz);

	/**
	 * Loads the order line candidates whose AD_Table_ID and Record_ID columns match the given parameters.
	 * 
	 * @param ctx
	 * @param tableName
	 * @param recordId
	 * @param trxName
	 * @return matched order candidates
	 */
	List<I_C_OLCand> retrieveReferencing(Properties ctx, String tableName, int recordId, String trxName);

	/**
	 * Loads an returns all <code>C_Order_Line_Alloc</code> records that reference the given order line.<br>
	 * Note that this includes records with <code>IsActive='N'</code> as well as records that have a different <code>AD_Client_ID</code>.
	 * 
	 * @param ol
	 * @return
	 */
	List<I_C_Order_Line_Alloc> retrieveAllOlas(I_C_OrderLine ol);

	/**
	 * Loads an returns all <code>C_Order_Line_Alloc</code> records that reference the given order candidate.<br>
	 * Note that this includes records with <code>IsActive='N'</code> as well as records that have a different <code>AD_Client_ID</code>.
	 * 
	 * @param ol
	 * @return
	 */
	List<I_C_Order_Line_Alloc> retrieveAllOlas(I_C_OLCand olCand);
}
