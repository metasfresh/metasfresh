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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;

import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;

import de.metas.handlingunits.attribute.storage.IAttributeStorage;

/**
 * Defines something which has weighting capabilities. Use {@link IWeightableFactory#createWeightableOrNull(org.adempiere.mm.attributes.api.IAttributeSet)} to get an instance.
 *
 * Mainly, it wraps an {@link IAttributeStorage} or (something else) and expose a lot of methods around the "Weightable HU" concern.
 *
 * @author tsa
 *
 */
public interface IWeightable
{
	/**
	 *
	 * @return true if this weightable was weighted by the user (i.e. {@link #getWeightNet()} is set).
	 */
	boolean isWeighted();

	/**
	 * Checks if attached HU storage is weightable.
	 *
	 * @param attributeStorage
	 * @return true if this "weightable" allows the user to weight it; i.e.
	 *         <ul>
	 *         <li><code>true</code> if the attached HU Storage is weightable
	 *         <li><code>false</code> if the attached HU Storage is not weightable (i.e. if the UOM is not found OR not a weight UOM for the attribute storage OR the attribute storage itself is not
	 *         HUAware)
	 *         </ul>
	 */
	boolean isWeightable();

	/**
	 * @return true if it supports weight tare adjustment (i.e. WeightTareAdjust attribute is present)
	 */
	boolean hasWeightTareAdjust();

	/**
	 * @return true if it supports weight tare (i.e. WeightTare attribute is present)
	 */
	boolean hasWeightTare();

	/**
	 * @return true if it supports weight net (i.e. WeightNet attribute is present)
	 */
	boolean hasWeightNet();

	/**
	 * @return true if it supports weight gross (i.e. WeightGross attribute is present)
	 */
	boolean hasWeightGross();

	I_M_Attribute getWeightTareAdjustAttribute();

	BigDecimal getWeightTareAdjust();

	I_M_Attribute getWeightTareAttribute();

	/** @return Weight Tare's initial value (seed value) */
	BigDecimal getWeightTareInitial();

	BigDecimal getWeightTare();

	/** @return sum of all WeightTare values (i.e. {@link #getWeightTare()}, {@link #getWeightTareAdjust()}) */
	public BigDecimal getWeightTareTotal();

	I_M_Attribute getWeightNetAttribute();

	void setWeightNetNoPropagate(final BigDecimal weightNet);

	void setWeightNet(final BigDecimal weightNet);

	/** @return net weight or null if weight net is not available */
	BigDecimal getWeightNetOrNull();

	/** @return weight net; never return null */
	BigDecimal getWeightNet();

	I_C_UOM getWeightNetUOM();

	I_M_Attribute getWeightGrossAttribute();

	void setWeightGross(final BigDecimal weightGross);

	BigDecimal getWeightGross();

	/**
	 * @param attribute
	 * @return true if given attribute is the attribute used for the weight tare adjust
	 */
	boolean isWeightTareAdjustAttribute(final I_M_Attribute attribute);

	/**
	 * @param attribute
	 * @return true if given attribute is the attribute used for the weight tare
	 */
	boolean isWeightTareAttribute(final I_M_Attribute attribute);

	/**
	 * @param attribute
	 * @return true if given attribute is the attribute used for the weight net
	 */
	boolean isWeightNetAttribute(final I_M_Attribute attribute);

	/**
	 * @param attribute
	 * @return true if given attribute is the attribute used for the weight gross
	 */
	boolean isWeightGrossAttribute(final I_M_Attribute attribute);
}
