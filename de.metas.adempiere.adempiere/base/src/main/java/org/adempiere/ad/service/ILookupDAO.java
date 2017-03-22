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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;

import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.util.ISingletonService;
import org.adempiere.util.collections.BlindIterator;
import org.compiere.model.ILookupDisplayColumn;
import org.compiere.model.MLookupInfo;
import org.compiere.util.KeyNamePair;
import org.compiere.util.NamePair;
import org.compiere.util.ValueNamePair;

/**
 * Field Lookup DAO methods
 * 
 * @author tsa
 * 
 */
public interface ILookupDAO extends ISingletonService
{
	interface IColumnInfo
	{
		int getAD_Val_Rule_ID();

		boolean isParent();

		int getAD_Reference_Value_ID();

		String getColumnName();
	}

	/**
	 * Immutable Table Reference definition
	 */
	interface ITableRefInfo
	{
		String getName();
		
		String getTableName();

		String getKeyColumn();

		/**
		 * 
		 * @return actual ColumnName to be displayed or null
		 */
		String getDisplayColumn();

		/**
		 * 
		 * @return actual ColumnName SQL to be displayed or null
		 */
		String getDisplayColumnSQL();

		String getOrderByClause();

		String getWhereClause();

		boolean isTranslated();

		boolean isValueDisplayed();

		int getZoomWindow();

		int getOverrideZoomWindow();

		int getZoomWindowPO();

		boolean isAutoComplete();

		/**
		 * Check if the keyColumn ends with "_ID"
		 * 
		 * @return
		 */
		boolean isNumericKey();
	}

	interface ILookupDisplayInfo
	{
		boolean isTranslated();

		int getZoomWindowPO();

		int getZoomWindow();

		List<ILookupDisplayColumn> getLookupDisplayColumns();
	}

	/**
	 * Contains a set of {@link ValueNamePair} or {@link KeyNamePair} elements.
	 * 
	 * @author tsa
	 * 
	 */
	public interface INamePairIterator extends BlindIterator<NamePair>, AutoCloseable
	{
		/**
		 * 
		 * @return true if this iterator is valid
		 */
		boolean isValid();

		/**
		 * Gets validation key of data in loaded context.
		 * 
		 * @return
		 */
		Object getValidationKey();

		@Override
		public NamePair next();

		/**
		 * 
		 * @return true if last value was active
		 */
		boolean wasActive();

		/**
		 * 
		 * @return true if underlying elements are of type{@link KeyNamePair}; if false then elements are of type {@link ValueNamePair}
		 */
		boolean isNumericKey();

		@Override
		void close();
	}

	IColumnInfo retrieveColumnInfo(int adColumnId);

	/**
	 * Same as {@link #retrieveTableRefInfoOrNull(int)} but in case the {@link ITableRefInfo} was not found, an warning is logged
	 * 
	 * @param AD_Reference_Value_ID
	 * @return table reference info
	 */
	ITableRefInfo retrieveTableRefInfo(int AD_Reference_Value_ID);

	/**
	 * @param AD_Reference_Value_ID
	 * @return true if given reference is a table reference
	 */
	boolean isTableReference(int AD_Reference_Value_ID);

	ITableRefInfo retrieveTableDirectRefInfo(String columnName);
	
	ITableRefInfo retrieveAccountTableRefInfo();

	ILookupDisplayInfo retrieveLookupDisplayInfo(ITableRefInfo tableRefInfo);

	boolean isReferenceOrderByValue(int adReferenceId);

	/**
	 * Retrieves all elements of <code>lookupInfo</code> in given <code>validationCtx</code> context
	 * 
	 * @param validationCtx
	 * @param lookupInfo
	 */
	INamePairIterator retrieveLookupValues(IValidationContext validationCtx, MLookupInfo lookupInfo);

	/**
	 * Retrieves all elements of <code>lookupInfo</code> in given <code>validationCtx</code> context
	 * 
	 * @param validationCtx
	 * @param lookupInfo
	 * @param additionalValidationRule optional additional validation rule to be applied on top of lookupInfo's validation rule
	 */
	INamePairIterator retrieveLookupValues(IValidationContext validationCtx, MLookupInfo lookupInfo, IValidationRule additionalValidationRule);

	/**
	 * Directly retrieves a data element identified by <code>key</code>.
	 * 
	 * @param validationCtx
	 * @param lookupInfo
	 * @param key
	 * @return
	 */
	NamePair retrieveLookupValue(IValidationContext validationCtx, MLookupInfo lookupInfo, Object key);

	/**
	 * Creates a validation key to be used when checking if data is valid in a given context
	 * 
	 * @param validationCtx
	 * @param lookupInfo
	 * @return
	 */
	Object createValidationKey(IValidationContext validationCtx, MLookupInfo lookupInfo);

	/**
	 * Retrieve TableRefInfo or null
	 * 
	 * @param AD_Reference_ID
	 * @return
	 */
	ITableRefInfo retrieveTableRefInfoOrNull(int AD_Reference_ID);
}
