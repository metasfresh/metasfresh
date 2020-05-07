package org.adempiere.misc.service;

import org.adempiere.model.InterfaceWrapperHelper;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.util.ISingletonService;
import org.compiere.model.PO;

/**
 * 
 * @author metas-dev <dev@metasfresh.com>
 * @deprecated this service is deprecated <b>big time</b>. Please use {@link InterfaceWrapperHelper} instead.
 */
@Deprecated
public interface IPOService extends ISingletonService
{

	public static final String IS_ACTIVE = "IsActive";
	public static final String AD_ORG_ID = "AD_Org_ID";
	public static final String AD_CLIENT_ID = "AD_Client_ID";

	/**
	 * Tries to save the given PO with the given transaction. Throws a
	 * {@link RuntimeException}, if {@link PO#save(String)} returns
	 * <code>false</code>.
	 * 
	 * @param po
	 * @param trxName
	 * @throws RuntimeException
	 *             if the po's save fails
	 * @throws IllegalArgumentException
	 *             if the given object is not <code>instanceof PO</code>
	 * 
	 */
	void save(Object po, String trxName);

	/**
	 * Tries to delete the given po with the given transaction. Throws a
	 * {@link RuntimeException}, if {@link PO#delete(boolean, String)} returns
	 * <code>false</code>.
	 * 
	 * @param po
	 * @param force
	 *            passed to {@link PO#delete(boolean, String)}
	 * @param trxName
	 *            {@link PO#delete(boolean, String)}
	 * @throws IllegalArgumentException
	 *             if the given object is not <code>instanceof PO</code>
	 */
	void delete(Object po, boolean force, String trxName);

	/**
	 * If the table of the given PO has a column with the given name, the PO's
	 * value is returned.
	 * 
	 * This method can be used to access non-standard columns that are not
	 * present in every ADempiere database.
	 * 
	 * @param po
	 * @param columnName
	 * @return the PO's value of the given column or <code>null</code> if the PO
	 *         doesn't have a column with the given name.
	 * @throws IllegalArgumentException
	 *             if the given object is not <code>instanceof PO</code>
	 */
	Object getValue(Object po, String columnName);

	Object getOldValue(Object po, String columnName);

	/**
	 * If the table of the given PO has a column with the given name, the PO's
	 * value is set to the given value.
	 * 
	 * This method can be used to access non-standard columns that are not
	 * present in every ADempiere database.
	 * 
	 * @param po
	 * @param columnName
	 * @param value
	 *            may be <code>null</code>
	 * @throws IllegalArgumentException
	 *             if the given object is not <code>instanceof PO</code>
	 */
	void setValue(Object po, String columnName, Object value);

	/**
	 * Convenience method. Invokes {@link #getValue(Object, String)} and
	 * {@link #setValue(Object, String, Object)}.
	 * 
	 * @param source
	 * @param dest
	 * @param columnName
	 * @throws IllegalArgumentException
	 *             if the given object is not <code>instanceof PO</code>
	 */
	void copyValue(Object source, Object dest, String columnName);

	/**
	 * Convenience method. Sets AD_Client_ID and AD_Org_ID of the given po.
	 * 
	 * @param po
	 * @param adClientId
	 * @param adOrgId
	 * @throws IllegalArgumentException
	 *             if the given object is not <code>instanceof PO</code>
	 */
	void setClientOrg(Object po, int adClientId, int adOrgId);

	/**
	 * Convenience method. Sets the AD_Client_ID and AD_Org_ID of
	 * <code>source</code> to <code>target</code>.
	 * 
	 * @param source
	 * @param dest
	 * @throws IllegalArgumentException
	 *             if the given object is not <code>instanceof PO</code>
	 */
	void copyClientOrg(Object source, Object dest);

	/**
	 * Sets the <code>IsActive</code> column of the given PO.
	 * 
	 * @param po
	 * @param active
	 * @throws IllegalArgumentException
	 *             if the given object is not <code>instanceof PO</code>
	 */
	void setIsActive(Object po, boolean active);

	/**
	 * 
	 * @param po
	 * @return The names of the given <code>po</code>'s key columns.
	 * @throws IllegalArgumentException
	 *             if the given object is not <code>instanceof PO</code>
	 */
	String[] getKeyColumns(Object po);

	/**
	 * 
	 * @param po
	 * @return
	 * @throws IllegalArgumentException
	 *             if the given object is not <code>instanceof PO</code>
	 */
	int getID(Object po);

	/**
	 * Invokes {@link PO#copyValues(PO, PO)} for <code>poFrom</code> and
	 * <code>poTo</code>.
	 * 
	 * @param poFrom
	 * @param poTo
	 * @throws IllegalArgumentException
	 *             if the given object is not <code>instanceof PO</code>
	 */
	void copyValues(Object poFrom, Object poTo);

	/**
	 * 
	 * @param po
	 * @return the result of ((PO)object).get_TrxName()
	 */
	String getTrxName(Object po);

}
