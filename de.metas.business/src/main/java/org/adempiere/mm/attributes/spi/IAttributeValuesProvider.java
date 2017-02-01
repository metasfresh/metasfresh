package org.adempiere.mm.attributes.spi;

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

import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.CCache.CCacheStats;
import org.compiere.util.Evaluatee;
import org.compiere.util.NamePair;

/**
 * Implementations of this interface are providing a fixed set of values for a given attribute.
 * 
 * @author tsa
 * 
 */
public interface IAttributeValuesProvider
{
	/**
	 * @return {@link X_M_Attribute#ATTRIBUTEVALUETYPE_Number}, {@link X_M_Attribute#ATTRIBUTEVALUETYPE_StringMax40} or {@link X_M_Attribute#ATTRIBUTEVALUETYPE_Date} (i.e. {@link X_M_Attribute#ATTRIBUTEVALUETYPE_List} is <b>NOT</b> allowed)
	 */
	String getAttributeValueType();

	/**
	 * Checks if any value is allowed.
	 * 
	 * In case any value is allowed, system won't try to check if a given value is found in {@link #getAvailableValues(IAttributeSet, I_M_Attribute)}.
	 * 
	 * @return true if any value is allowed
	 */
	boolean isAllowAnyValue();
	
	Evaluatee prepareContext(final IAttributeSet attributeSet);

	/**
	 * List of available values.
	 * 
	 * NOTE: in case {@link #isHighVolume()} it might be that the returned list to be empty.
	 * 
	 * @return fixed list of attribute values that are accepted
	 */
	List<? extends NamePair> getAvailableValues(Evaluatee evalCtx);

	/**
	 * Gets the value {@link NamePair} for given "value" ID.
	 * 
	 * NOTE: if we are dealing with a high-volume attribute values list and if attribute is not found in loaded list, it will be loaded directly from database.
	 * 
	 * @param evalCtx
	 * @param valueKey
	 * @return attribute value or null
	 */
	NamePair getAttributeValueOrNull(final Evaluatee evalCtx, String valueKey);
	
	int getM_AttributeValue_ID(final String valueKey);

	/**
	 * Value to be used for "nulls".
	 * 
	 * In case the list has defined a particular value for Nulls, that one will be returned. If not, actual <code>null</code> will be returned.
	 * 
	 * @return {@link NamePair} for null or <code>null</code>
	 */
	NamePair getNullValue();

	/**
	 * @return true if he have a high volume values list
	 */
	public boolean isHighVolume();

	//
	// Caching
	String getCachePrefix();
	List<CCacheStats> getCacheStats();
}
