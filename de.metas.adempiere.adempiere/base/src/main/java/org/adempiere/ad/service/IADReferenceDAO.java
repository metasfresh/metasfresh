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
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adempiere.util.ISingletonService;
import org.compiere.util.Env;

import de.metas.i18n.ITranslatableString;
import lombok.Builder;
import lombok.Value;

public interface IADReferenceDAO extends ISingletonService
{
	@Value
	@Builder
	public static final class ADRefListItem
	{
		private final String value;
		private final String valueName;
		private final ITranslatableString name;
		private final ITranslatableString description;
		/** AD_Ref_List_ID */
		private final int refListId;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param adReferenceId
	 * @return map of "Value" to {@link ADRefListItem}; never return null
	 */
	Map<String, ADRefListItem> retrieveListValuesMap(int adReferenceId);

	/**
	 * @param adReferenceId
	 * @return a collection of all active {@link ADRefListItem} items of given <code>adReferenceId</code>
	 */
	Collection<ADRefListItem> retrieveListItems(int adReferenceId);

	/**
	 * @param adReferenceId
	 * @return Set of active {@link ADRefListItem#getValue()}s.
	 */
	Set<String> retrieveListValues(int adReferenceId);

	/**
	 * Gets List Item's Name for given <code>value</code> translated to context language.
	 * 
	 * @param adReferenceId
	 * @param value
	 * @return list name translated or value if not list item found.
	 */
	String retrieveListNameTrl(final Properties ctx, int adReferenceId, String value);
	
	default String retrieveListNameTrl(final int adReferenceId, final String value)
	{
		return retrieveListNameTrl(Env.getCtx(), adReferenceId, value);
	}

	/**
	 * @param adReferenceId
	 * @param value
	 * @return true if an active {@link ADRefListItem} for given <code>adReferenceId</code> and <code>value</code> exists
	 */
	boolean existListValue(int adReferenceId, String value);

	/**
	 * @param adReferenceId
	 * @param value
	 * @return existing active {@link I_ADRefListItem} or null
	 */
	ADRefListItem retrieveListItemOrNull(int adReferenceId, String value);

	ITranslatableString retrieveListNameTranslatableString(int adReferenceId, String value);
}
