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


import java.math.BigDecimal;
import java.util.Date;
import java.util.Properties;

import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.spi.impl.DefaultAttributeValuesProvider;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeValue;

/**
 * Attribute handler: implementations of this interface are used for attributes handling.
 * 
 * If the implementation it is also implementing {@link IAttributeValuesProviderFactory} then it will be used to create the {@link IAttributeValuesProvider}.
 * If not, and attribute is of type List, {@link DefaultAttributeValuesProvider} will be used.
 * 
 * If possible, instead of implementing this interface, please consider extending the {@link AbstractAttributeValueGenerator}.
 * 
 * @author tsa
 * 
 */
public interface IAttributeValueGenerator
{

	/**
	 * 
	 * See X_M_Attribute.ATTRIBUTEVALUETYPE_*.
	 * 
	 * @return value type
	 */
	String getAttributeValueType();

	/**
	 * Check if this generator can generate a value in given context.
	 * 
	 * 
	 * If it cannot (i.e. this method returns <code>false</code>), and one of the following generate methods are called, they will throw {@link UnsupportedOperationException}:
	 * <ul>
	 * <li>{@link #generateStringValue(Properties, IAttributeSet, I_M_Attribute)}
	 * <li>{@link #generateNumericValue(Properties, IAttributeSet, I_M_Attribute)}
	 * <li>{@link #generateDateValue(Properties, IAttributeSet, I_M_Attribute)}
	 * </ul>
	 * 
	 * NOTE: {@link #generateAttributeValue(Properties, int, int, boolean, String)} is NOT considered.
	 * 
	 * @param ctx
	 * @param attributeSet
	 * @param attribute
	 * @return true if a value can be generated
	 */
	boolean canGenerateValue(Properties ctx, IAttributeSet attributeSet, I_M_Attribute attribute);

	/**
	 * Generate a String value. Will throw {@link UnsupportedOperationException} for attributes of different types.
	 * 
	 * @param attributeSet attribute set
	 * @param attribute attribute on which the value will be generated
	 * 
	 * @return generated string value
	 * @throws UnsupportedOperationException if value cannot be generated
	 */
	String generateStringValue(Properties ctx, IAttributeSet attributeSet, I_M_Attribute attribute);

	/**
	 * Generate a numeric value. Will throw {@link UnsupportedOperationException} for attributes of different types.
	 * 
	 * @param ctx
	 * @param attributeSet attribute set
	 * @param attribute attribute on which the value will be generated
	 * @return generated numeric value
	 * @throws UnsupportedOperationException if value cannot be generated
	 */
	BigDecimal generateNumericValue(Properties ctx, IAttributeSet attributeSet, I_M_Attribute attribute);

	/**
	 * Generate a date value. Will throw {@link UnsupportedOperationException} for attributes of different types.
	 * 
	 * @param ctx
	 * @param attributeSet attribute set
	 * @param attribute attribute on which the value will be generated
	 * @return generated date value
	 * @throws UnsupportedOperationException if value cannot be generated
	 */
	Date generateDateValue(Properties ctx, IAttributeSet attributeSet, I_M_Attribute attribute);

	/**
	 * Generate an attribute value based on table and record ID.
	 * 
	 * Will be specific for each implementation. Will throw {@link UnsupportedOperationException} for attributes of different types.
	 * 
	 * @param ctx
	 * @param tableId
	 * @param recordId
	 * @param isSOTrx implementors might return different values for sales and purchase transactions
	 * @param trxName
	 * @return
	 * @throws UnsupportedOperationException if value cannot be generated
	 */
	I_M_AttributeValue generateAttributeValue(Properties ctx, int tableId, int recordId, boolean isSOTrx, String trxName);
}
