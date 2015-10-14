package org.adempiere.ad.service;

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


import java.util.Collection;
import java.util.Properties;
import java.util.Set;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_Ref_List;

public interface IADReferenceDAO extends ISingletonService
{
	/**
	 * @param ctx
	 * @param adReferenceId
	 * @return a collection of all active {@link I_AD_Ref_List} items of given <code>adReferenceId</code>
	 */
	Collection<I_AD_Ref_List> retrieveListItems(Properties ctx, int adReferenceId);

	/**
	 * @param ctx
	 * @param adReferenceId
	 * @return Set of active {@link I_AD_Ref_List#getValue()}s.
	 */
	Set<String> retrieveListValues(Properties ctx, int adReferenceId);

	/**
	 * Gets List Item's Name for given <code>value</code>.
	 * 
	 * If list item ({@link I_AD_Ref_List}) was not found then <code>value</code> is returned.
	 * 
	 * @param ctx
	 * @param adReferenceId
	 * @param value
	 * @return list name or value if no list item found.
	 */
	String retriveListName(Properties ctx, int adReferenceId, String value);

	/**
	 * Same as {@link #retriveListName(Properties, int, String)} but the name is translated to context language.
	 * 
	 * @param ctx
	 * @param adReferenceId
	 * @param value
	 * @return list name translated or value if not list item found.
	 */
	String retrieveListNameTrl(Properties ctx, int adReferenceId, String value);

	/**
	 * Gets List Item's Description for given <code>value</code>.
	 * 
	 * The description is translated to context language.
	 * 
	 * @param ctx
	 * @param adReferenceId
	 * @param value
	 * @return list description or "" if no list item found.
	 */
	String retrieveListDescriptionTrl(Properties ctx, int adReferenceId, String value);

	/**
	 * @param ctx
	 * @param adReferenceId
	 * @param value
	 * @return true if an active {@link I_AD_Ref_List} for given <code>adReferenceId</code> and <code>value</code> exists
	 */
	boolean existListValue(Properties ctx, int adReferenceId, String value);

	/**
	 * @param ctx
	 * @param adReferenceId
	 * @param value
	 * @return existing active {@link I_AD_Ref_List} or null
	 */
	I_AD_Ref_List retrieveListItemOrNull(Properties ctx, int adReferenceId, String value);
}
