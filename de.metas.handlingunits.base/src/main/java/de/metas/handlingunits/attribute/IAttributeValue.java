package de.metas.handlingunits.attribute;

/*
 * #%L
 * de.metas.handlingunits.base
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

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.adempiere.mm.attributes.spi.IAttributeValueCallout;
import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.adempiere.mm.attributes.spi.IAttributeValueGenerator;
import org.adempiere.mm.attributes.spi.IAttributeValuesProvider;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.util.NamePair;

import de.metas.handlingunits.attribute.exceptions.InvalidAttributeValueException;
import de.metas.handlingunits.attribute.strategy.IAttributeAggregationStrategy;
import de.metas.handlingunits.attribute.strategy.IAttributeSplitterStrategy;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferStrategy;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;

public interface IAttributeValue
{
	/**
	 * @return attribute; never return null
	 */
	I_M_Attribute getM_Attribute();

	/**
	 * Gets attribute's value type (see X_M_Attribute.ATTRIBUTEVALUETYPE_*)
	 *
	 * @return attribute's value type; never null
	 */
	String getAttributeValueType();

	/**
	 * Sets attribute value and calls the {@link IAttributeValueListener} that were added to this instance.
	 * <p>
	 * NOTE: if you use this method to set the value directly, it won't be propagated (unless one of the registered handlers causes the propagation to happen). If you want it to be propagated, call
	 * {@link de.metas.handlingunits.attribute.storage.IAttributeStorage#setValue(I_M_Attribute, Object)} instead.
	 *
	 * @param attributeValueContext the context to be passed to the listeners when they are invoked at the end of this method implementation.
	 * @param value the (new) value
	 * @see #addAttributeValueListener(IAttributeValueListener)
	 */
	void setValue(IAttributeValueContext attributeValueContext, Object value);

	/**
	 * Gets attribute value
	 *
	 * @return
	 */
	Object getValue();

	/**
	 * Gets initial (seed) attribute value
	 *
	 * @return
	 */
	Object getValueInitial();

	/**
	 * Sets initial (seed) attribute value. Note that the initial value is generally created by
	 * {@link IAttributeValueCallout#generateSeedValue(org.adempiere.mm.attributes.api.IAttributeSet, I_M_Attribute)}.
	 *
	 * NOTE: don't call it directly. API will call it when a new attribute is generated/created.
	 *
	 * @param valueInitial
	 */
	void setValueInitial(Object valueInitial);

	/**
	 * @return numeric value if {@link #isNumericValue()}, {@link BigDecimal#ZERO} otherwise
	 */
	BigDecimal getValueAsBigDecimal();

	/**
	 * @return integer value if {@link #isNumericValue()}, <code>0</code> otherwise
	 */
	int getValueAsInt();

	BigDecimal getValueInitialAsBigDecimal();

	/**
	 * @return string value if {@link #isStringValue()}, null otherwise
	 */
	String getValueAsString();

	String getValueInitialAsString();

	Date getValueAsDate();

	Date getValueInitialAsDate();

	/**
	 * @return true if it's a numeric attribute
	 */
	boolean isNumericValue();

	/**
	 * @return true if it's a string attribute
	 */
	boolean isStringValue();

	/**
	 * @return true if it's a date attribute
	 */
	boolean isDateValue();

	/**
	 * @return true if the value Check.isEmpty(value) is true, false otherwise
	 */
	boolean isEmpty();

	/**
	 * Gets propagation type (see {@link X_M_HU_PI_Attribute}.PROPAGATIONTYPE_*.
	 *
	 * @return propagation type
	 */
	String getPropagationType();

	/**
	 * @return {@link IAttributeAggregationStrategy} instance
	 */
	IAttributeAggregationStrategy retrieveAggregationStrategy();

	/**
	 * @return {@link IAttributeSplitterStrategy} instance
	 */
	IAttributeSplitterStrategy retrieveSplitterStrategy();

	/**
	 * @return {@link IAttributeValueCallout} instance; never return null
	 */
	IAttributeValueCallout getAttributeValueCallout();

	/**
	 * @return {@link IAttributeValueGenerator} instance or <code>null</code>
	 */
	IAttributeValueGenerator getAttributeValueGeneratorOrNull();

	/**
	 * @return {@link IHUAttributeTransferStrategy} instance
	 */
	IHUAttributeTransferStrategy retrieveTransferStrategy();

	/**
	 * @return the default empty value of an attribute (hard-coded)
	 */
	Object getEmptyValue();

	void addAttributeValueListener(IAttributeValueListener listener);

	void removeAttributeValueListener(IAttributeValueListener listener);

	/**
	 *
	 * @return true if this attribute provides a list of values (via {@link #getAvailableValues()});
	 */
	boolean isList();

	/**
	 * @return available values to set
	 * @throws InvalidAttributeValueException if this is not a list attribute
	 */
	List<? extends NamePair> getAvailableValues();

	IAttributeValuesProvider getAttributeValuesProvider();

	I_C_UOM getC_UOM();

	/**
	 * @return true if attribute is readonly for user
	 */
	boolean isReadonlyUI();

	/**
	 * @return true if attribute is displayed to user
	 */
	boolean isDisplayedUI();
	
	boolean isMandatory();

	/**
	 * @return true if the attribute shall be displayed only if present in the product's attributeSet.
	 */
	boolean isOnlyIfInProductAttributeSet();

	/**
	 * @return ordering sequence no
	 */
	int getDisplaySeqNo();

	/**
	 * @return true if new attributes will be created in ASI (copied from the M_HU_PI_Attribute) - i.e on Transfer
	 */
	boolean isUseInASI();

	/**
	 *
	 * @return
	 * 		<ul>
	 *         <li>true if this attribute was defined by the standard template
	 *         <li>false if this attribute is a customization on a particular element (e.g.HU, ASI etc)
	 *         </ul>
	 * @task FRESH-578 #275
	 */
	boolean isDefinedByTemplate();

	/**
	 * Get the "null" attribute value, in case it was set.
	 *
	 * @return
	 */
	NamePair getNullAttributeValue();

	/**
	 *
	 * @return true is this attribute was newly generated
	 */
	boolean isNew();
}
