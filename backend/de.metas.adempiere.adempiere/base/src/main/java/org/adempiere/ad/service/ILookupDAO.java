/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package org.adempiere.ad.service;

import de.metas.i18n.ExplainedOptional;
import de.metas.util.ISingletonService;
import de.metas.util.collections.BlindIterator;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.ad.validationRule.IValidationRule;
import org.compiere.model.ILookupDisplayColumn;
import org.compiere.model.MLookupInfo;
import org.compiere.util.KeyNamePair;
import org.compiere.util.NamePair;
import org.compiere.util.ValueNamePair;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Field Lookup DAO methods
 *
 * @author tsa
 */
public interface ILookupDAO extends ISingletonService
{
	interface IColumnInfo
	{
		int getAD_Val_Rule_ID();

		boolean isParent();

		int getAD_Reference_Value_ID();

		String getColumnName();

		String getTableName();
	}

	interface ILookupDisplayInfo
	{
		boolean isTranslated();

		AdWindowId getZoomWindowPO();

		AdWindowId getZoomWindow();

		List<ILookupDisplayColumn> getLookupDisplayColumns();
	}

	/**
	 * Contains a set of {@link ValueNamePair} or {@link KeyNamePair} elements.
	 *
	 * @author tsa
	 */
	interface INamePairIterator extends BlindIterator<NamePair>, AutoCloseable
	{
		/**
		 * @return true if this iterator is valid
		 */
		boolean isValid();

		/**
		 * Gets validation key of data in loaded context.
		 */
		Object getValidationKey();

		@Override
		NamePair next();

		/**
		 * @return true if last value was active
		 */
		boolean wasActive();

		/**
		 * @return true if underlying elements are of type{@link KeyNamePair}; if false then elements are of type {@link ValueNamePair}
		 */
		boolean isNumericKey();

		@Override
		void close();
	}

	IColumnInfo retrieveColumnInfo(int adColumnId);

	ExplainedOptional<TableRefInfo> getTableRefInfo(int referenceRepoId);

	/**
	 * In case the {@link TableRefInfo} was not found, an warning is logged.
	 *
	 * @return table reference info or <code>null</code> if not found
	 */
	@Nullable
	TableRefInfo retrieveTableRefInfo(int AD_Reference_Value_ID);

	/**
	 * @return true if given reference is a table reference
	 */
	boolean isTableReference(int AD_Reference_Value_ID);

	TableRefInfo retrieveTableDirectRefInfo(String columnName);

	TableRefInfo retrieveAccountTableRefInfo();

	ILookupDisplayInfo retrieveLookupDisplayInfo(TableRefInfo tableRefInfo);

	boolean isReferenceOrderByValue(int adReferenceId);

	/**
	 * Retrieves all elements of <code>lookupInfo</code> in given <code>validationCtx</code> context
	 */
	INamePairIterator retrieveLookupValues(IValidationContext validationCtx, MLookupInfo lookupInfo);

	/**
	 * Retrieves all elements of <code>lookupInfo</code> in given <code>validationCtx</code> context
	 *
	 * @param additionalValidationRule optional additional validation rule to be applied on top of lookupInfo's validation rule
	 */
	INamePairIterator retrieveLookupValues(IValidationContext validationCtx, MLookupInfo lookupInfo, IValidationRule additionalValidationRule);

	/**
	 * Directly retrieves a data element identified by <code>key</code>.
	 */
	NamePair retrieveLookupValue(IValidationContext validationCtx, MLookupInfo lookupInfo, Object key);

	/**
	 * Creates a validation key to be used when checking if data is valid in a given context
	 */
	Object createValidationKey(IValidationContext validationCtx, MLookupInfo lookupInfo);
}
