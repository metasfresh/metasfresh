package org.adempiere.ad.wrapper;

import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.PO;
import org.compiere.util.Evaluatee;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
/**
 * Implementors are responsible for the "type" specific handling of given <code>model</code>s,
 * depending one whether under the hood they are actually {@link org.compiere.model.PO}'s or {@link org.compiere.model.GridTab}s.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IInterfaceWrapperHelper
{
	boolean canHandled(Object model);

	<T> T wrap(Object model, Class<T> modelClass, boolean useOldValues);

	void refresh(Object model, boolean discardChanges);

	void refresh(Object model, String trxName);

	boolean hasModelColumnName(Object model, String columnName);

	/**
	 * Get context from model and setting in context AD_Client_ID and AD_Org_ID according to the model if useClientOrgFromModel is true
	 *
	 * @param model
	 * @param useClientOrgFromModel
	 * @return context
	 */
	Properties getCtx(final Object model, final boolean useClientOrgFromModel);

	/**
	 *
	 * @param model
	 * @param ignoreIfNotHandled if <code>true</code> and the given model can not be handeled (no PO, GridTab etc), then just return {@link ITrx#TRXNAME_None} without logging a warning.
	 *
	 * @return trxName
	 */
	String getTrxName(final Object model, final boolean ignoreIfNotHandled);

	/**
	 *
	 * @param model
	 * @param trxName
	 * @param ignoreIfNotHandled <code>true</code> and the given model can not be handled (no PO, GridTab etc), then don't throw an exception,
	 *
	 * @throws AdempiereException if the given model is not handled and ignoreIfNotHandled is <code>false</code>.
	 */
	default void setTrxName(final Object model, final String trxName, final boolean ignoreIfNotHandled)
	{
		if (!ignoreIfNotHandled)
		{
			throw new AdempiereException("Not supported model " + model + " (class:" + (model == null ? null : model.getClass()) + ")");
		}
	}

	int getId(final Object model);

	/**
	 * Get TableName of wrapped model.
	 *
	 * This method returns null when:
	 * <ul>
	 * <li>model is null
	 * <li>model is not supported
	 * </ul>
	 *
	 * @param model
	 * @return table name or null
	 */
	String getModelTableNameOrNull(Object model);

	/**
	 * @param model
	 * @return true if model is a new record (not yet saved in database)
	 */
	boolean isNew(Object model);

	<T> T getValue(final Object model,
			final String columnName,
			final boolean throwExIfColumnNotFound,
			final boolean useOverrideColumnIfAvailable);

	boolean setValue(final Object model, final String columnName, final Object value, final boolean throwExIfColumnNotFound);

	boolean isValueChanged(Object model, String columnName);
	
	/**
	 * @param model
	 * @param columnNames
	 * @return true if any of given column names where changed
	 */
	boolean isValueChanged(Object model, Set<String> columnNames);

	/**
	 * Checks if given columnName's value is <code>null</code>
	 *
	 * @param model
	 * @param columnName
	 * @return <code>true</code> if columnName's value is <code>null</code>
	 */
	boolean isNull(Object model, String columnName);
	
	<T> T getDynAttribute(final Object model, final String attributeName);

	Object setDynAttribute(final Object model, final String attributeName, final Object value);

	<T extends PO> T getPO(final Object model, final boolean strict);

	Evaluatee getEvaluatee(Object model);

	default boolean isCopy(final Object model)
	{
		return false;
	}

}
