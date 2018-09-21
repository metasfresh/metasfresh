package de.metas.handlingunits.attribute.impl;

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
import org.slf4j.Logger;

import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.IWeightable;
import de.metas.handlingunits.attribute.IWeightableBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.conversion.ConversionHelper;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Wraps and {@link IAttributeStorage} and expose all weightable methods.
 *
 * @author tsa
 *
 */
/* package */class AttributeStorageWeightable implements IWeightable
{
	// services
	private final static transient Logger logger = LogManager.getLogger(AttributeStorageWeightable.class);
	private final IWeightableBL weightableBL = Services.get(IWeightableBL.class);

	private final I_M_Attribute attr_WeightGross;
	private final I_M_Attribute attr_WeightNet;
	private final I_M_Attribute attr_WeightTare;
	private final I_M_Attribute attr_WeightTareAdjust;

	private final IAttributeStorage _attributeStorage;

	/* package */AttributeStorageWeightable(
			@NonNull final WeightableFactory factory,
			@NonNull final IAttributeStorage attributeStorage)
	{
		_attributeStorage = attributeStorage;

		//
		// Get weight attributes definitions from factory
		attr_WeightGross = factory.getWeightGrossAttribute();
		attr_WeightNet = factory.getWeightNetAttribute();
		attr_WeightTare = factory.getWeightTareAttribute();
		attr_WeightTareAdjust = factory.getWeightTareAdjustAttribute();
	}

	/**
	 * @return underlying attribute storage; never return null
	 */
	private final IAttributeStorage getAttributeStorage()
	{
		return _attributeStorage;
	}

	@Override
	public final boolean isWeightable()
	{
		final IAttributeStorage attributeStorage = getAttributeStorage();
		final String uomType = attributeStorage.getQtyUOMTypeOrNull(); // TODO: optimize this shit!
		return weightableBL.isWeightableUOMType(uomType);
	}

	@Override
	public final boolean isWeighted()
	{
		final I_M_Attribute weightNetAttribute = getWeightNetAttribute();
		if (weightNetAttribute == null)
		{
			return false;
		}

		final IAttributeStorage attributeSet = getAttributeStorage();
		if (!attributeSet.hasAttribute(weightNetAttribute))
		{
			return false;
		}

		final BigDecimal weight = attributeSet.getValueAsBigDecimal(weightNetAttribute);
		if (weight == null || weight.signum() == 0)
		{
			return false;
		}

		return true;
	}

	@Override
	public boolean isWeightGrossAttribute(final I_M_Attribute attribute)
	{
		final I_M_Attribute attr_WeightGross = getWeightGrossAttribute();
		return isWeightAttribute(attribute, attr_WeightGross);
	}

	@Override
	public boolean isWeightNetAttribute(final I_M_Attribute attribute)
	{
		final I_M_Attribute attr_WeightNet = getWeightNetAttribute();
		return isWeightAttribute(attribute, attr_WeightNet);
	}

	@Override
	public boolean isWeightTareAttribute(final I_M_Attribute attribute)
	{
		final I_M_Attribute attr_WeightTare = getWeightTareAttribute();
		return isWeightAttribute(attribute, attr_WeightTare);
	}

	@Override
	public boolean isWeightTareAdjustAttribute(final I_M_Attribute attribute)
	{
		final I_M_Attribute attr_WeightTareAdjust = getWeightTareAdjustAttribute();
		return isWeightAttribute(attribute, attr_WeightTareAdjust);
	}

	private final boolean isWeightAttribute(final I_M_Attribute attribute, final I_M_Attribute expectedWeightAttribute)
	{
		if (attribute == null)
		{
			return false;
		}
		if (expectedWeightAttribute == null)
		{
			return false;
		}

		if (attribute.getM_Attribute_ID() != expectedWeightAttribute.getM_Attribute_ID())
		{
			logger.trace("Expected: {}; Received: {}", new Object[] { expectedWeightAttribute.getValue(), attribute.getValue() });
			return false;
		}

		return true;
	}

	@Override
	public BigDecimal getWeightGross()
	{
		final I_M_Attribute attr_WeightGross = getWeightGrossAttribute();
		return getWeight(attr_WeightGross);
	}

	@Override
	public void setWeightGross(final BigDecimal weightGross)
	{
		final I_M_Attribute attr_WeightGross = getWeightGrossAttribute();
		setWeight(attr_WeightGross, weightGross);
	}

	@Override
	public final I_M_Attribute getWeightGrossAttribute()
	{
		return attr_WeightGross;
	}

	@Override
	public BigDecimal getWeightNet()
	{
		final I_M_Attribute attr_WeightNet = getWeightNetAttribute();
		return getWeight(attr_WeightNet);
	}

	@Override
	public BigDecimal getWeightNetOrNull()
	{
		final I_M_Attribute weightAttribute = getWeightNetAttribute();
		return getWeightOrNull(weightAttribute);
	}

	@Override
	public void setWeightNet(final BigDecimal weightNet)
	{
		final I_M_Attribute attr_WeightNet = getWeightNetAttribute();
		setWeight(attr_WeightNet, weightNet);
	}

	@Override
	public void setWeightNetNoPropagate(final BigDecimal weightNet)
	{
		final I_M_Attribute attr_WeightNet = getWeightNetAttribute();
		setWeightNoPropagate(attr_WeightNet, weightNet);
	}

	@Override
	public final I_M_Attribute getWeightNetAttribute()
	{
		return attr_WeightNet;
	}

	@Override
	public I_C_UOM getWeightNetUOM()
	{
		final I_M_Attribute attr_WeightNet = getWeightNetAttribute();
		return getWeightUOM(attr_WeightNet);
	}

	@Override
	public BigDecimal getWeightTare()
	{
		final I_M_Attribute attr_WeightTare = getWeightTareAttribute();
		return getWeight(attr_WeightTare);
	}

	@Override
	public BigDecimal getWeightTareInitial()
	{
		final I_M_Attribute attr_WeightTare = getWeightTareAttribute();
		return getWeightInitial(attr_WeightTare);
	}

	@Override
	public BigDecimal getWeightTareTotal()
	{
		BigDecimal weightTareTotal = BigDecimal.ZERO;

		if (hasWeightTare())
		{
			final BigDecimal weightTare = getWeightTare();
			weightTareTotal = weightTareTotal.add(weightTare);
		}

		// 07023_Tara_changeable_in_LU
		// We have to also check if there is any tare adjust
		if (hasWeightTareAdjust())
		{
			final BigDecimal weightTareAdjust = getWeightTareAdjust();
			weightTareTotal = weightTareTotal.add(weightTareAdjust);
		}

		return weightTareTotal;
	}

	@Override
	public final I_M_Attribute getWeightTareAttribute()
	{
		return attr_WeightTare;
	}

	@Override
	public BigDecimal getWeightTareAdjust()
	{
		final I_M_Attribute attr_WeightTareAdjust = getWeightTareAdjustAttribute();
		return getWeight(attr_WeightTareAdjust);
	}

	@Override
	public final I_M_Attribute getWeightTareAdjustAttribute()
	{
		return attr_WeightTareAdjust;
	}

	private final BigDecimal getWeight(final I_M_Attribute weightAttribute)
	{
		final IAttributeStorage attributeStorage = getAttributeStorage();
		final Object weightObj = attributeStorage.getValue(weightAttribute);
		final BigDecimal weight = ConversionHelper.toBigDecimal(weightObj);
		return weight;
	}

	/**
	 *
	 * @return weight or null if weight is not available
	 */
	private BigDecimal getWeightOrNull(final I_M_Attribute weightAttribute)
	{
		if (weightAttribute == null)
		{
			return null;
		}

		if (!hasWeightAttribute(weightAttribute))
		{
			return null;
		}

		return getWeight(weightAttribute);
	}

	private final BigDecimal getWeightInitial(final I_M_Attribute weightAttribute)
	{
		final IAttributeStorage attributeStorage = getAttributeStorage();
		return attributeStorage.getValueInitialAsBigDecimal(weightAttribute);
	}

	private final void setWeight(final I_M_Attribute weightAttribute, final BigDecimal weight)
	{
		final IAttributeStorage attributeStorage = getAttributeStorage();

		logger.debug("Setting {} attribute Value={} on {}", new Object[] { weightAttribute.getValue(), weight, attributeStorage });
		attributeStorage.setValue(weightAttribute, weight);
	}

	/**
	 * Set the given weight directly, avoiding attribute propagation.
	 *
	 * @param weightAttribute
	 * @param weight
	 */
	private final void setWeightNoPropagate(final I_M_Attribute weightAttribute, final BigDecimal weight)
	{
		final IAttributeStorage attributeStorage = getAttributeStorage();

		logger.debug("Setting {} INTERNAL attribute Value={} on {}", new Object[] { weightAttribute.getValue(), weight, attributeStorage });
		attributeStorage.setValueNoPropagate(weightAttribute, weight); // directly set the correct value we're expecting

	}

	private final I_C_UOM getWeightUOM(final I_M_Attribute weightAttribute)
	{
		final IAttributeStorage attributeStorage = getAttributeStorage();
		final IAttributeValue attributeValue = attributeStorage.getAttributeValue(weightAttribute);
		if (attributeValue == null)
		{
			return null;
		}

		final I_C_UOM uom = attributeValue.getC_UOM();
		return uom;
	}

	/**
	 * @return true if has WeightGross attribute
	 */
	@Override
	public boolean hasWeightGross()
	{
		final I_M_Attribute attr_WeightGross = getWeightGrossAttribute();
		return hasWeightAttribute(attr_WeightGross);
	}

	/**
	 * @return true if has WeightNet attribute
	 */
	@Override
	public boolean hasWeightNet()
	{
		final I_M_Attribute attr_WeightNet = getWeightNetAttribute();
		return hasWeightAttribute(attr_WeightNet);
	}

	/**
	 * @return true if has WeightTare attribute
	 */
	@Override
	public boolean hasWeightTare()
	{
		final I_M_Attribute attr_WeightTare = getWeightTareAttribute();
		return hasWeightAttribute(attr_WeightTare);
	}

	/**
	 * @return true if has WeightTare(adjust) attribute
	 */
	@Override
	public boolean hasWeightTareAdjust()
	{
		final I_M_Attribute attr_WeightTareAdjust = getWeightTareAdjustAttribute();
		return hasWeightAttribute(attr_WeightTareAdjust);
	}

	/**
	 * @param weightAttribute
	 * @return true if given attribute exists
	 */
	private final boolean hasWeightAttribute(final I_M_Attribute weightAttribute)
	{
		if (weightAttribute == null)
		{
			return false;
		}
		final IAttributeStorage attributeStorage = getAttributeStorage();
		return attributeStorage.hasAttribute(weightAttribute);
	}
}
