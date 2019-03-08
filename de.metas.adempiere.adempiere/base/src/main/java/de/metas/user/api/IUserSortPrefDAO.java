package de.metas.user.api;

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


import java.util.List;
import java.util.Properties;

import org.compiere.model.I_AD_User;
import org.compiere.model.I_AD_User_SortPref_Hdr;
import org.compiere.model.I_AD_User_SortPref_Line;
import org.compiere.model.I_AD_User_SortPref_Line_Product;

import de.metas.util.ISingletonService;

/**
 * User default sorting preferences for sequencing in Application Windows (applies to all types of windows)
 *
 * @author al
 */
public interface IUserSortPrefDAO extends ISingletonService
{
	/**
	 * Returns the record for the given user, action and recordId which has <code>IsConference='N'</code>.<br>
	 * If there is none for the given user, but one with <code>AD_User_ID==null</code>, then that one is returned instead.
	 * 
	 * @param user
	 * @param action
	 * @param recordId the record id of the a window, form, or info-window
	 * @return sort preference header
	 */
	I_AD_User_SortPref_Hdr retrieveSortPreferenceHdr(I_AD_User user, String action, int recordId);

	I_AD_User_SortPref_Hdr retrieveDefaultSortPreferenceHdrOrNull(Properties ctx, String action, int recordId);

	/**
	 * @param ctx
	 * @param action
	 * @param recordId
	 * @return conference sort preference header
	 */
	I_AD_User_SortPref_Hdr retrieveConferenceSortPreferenceHdr(Properties ctx, String action, int recordId);

	/**
	 * @param hdr
	 * @return sort preference lines
	 */
	List<I_AD_User_SortPref_Line> retrieveSortPreferenceLines(I_AD_User_SortPref_Hdr hdr);

	/**
	 * Deletes all {@link I_AD_User_SortPref_Line}s and {@link I_AD_User_SortPref_Line_Product}s for the given <code>hdr</code>.
	 * 
	 * @param hdr
	 * @return the number of records (both {@link I_AD_User_SortPref_Line}s and {@link I_AD_User_SortPref_Line_Product}s) that were deleted
	 */
	int clearSortPreferenceLines(I_AD_User_SortPref_Hdr hdr);

	/**
	 *
	 * @param user
	 * @param action
	 * @param recordId
	 * @return sort preference lines for header found by retrieving through {@link #retrieveSortPreferenceHdr(I_AD_User, String, int)}
	 */
	List<I_AD_User_SortPref_Line> retrieveSortPreferenceLines(I_AD_User user, String action, int recordId);

	/**
	 * @param ctx
	 * @param action
	 * @param recordId
	 * @return conference sort preference lines
	 */
	List<I_AD_User_SortPref_Line> retrieveConferenceSortPreferenceLines(Properties ctx, String action, int recordId);

	/**
	 * @param user
	 * @param action
	 * @param recordId
	 * @return sort preference line products for sort preference line (products and sequences which shall be prioritized in sorting)
	 */
	List<I_AD_User_SortPref_Line_Product> retrieveSortPreferenceLineProducts(I_AD_User_SortPref_Line sortPreferenceLine);
}
