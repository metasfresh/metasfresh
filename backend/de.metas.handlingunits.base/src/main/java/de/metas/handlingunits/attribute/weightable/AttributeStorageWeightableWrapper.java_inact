package de.metas.handlingunits.attribute.weightable;

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

import org.adempiere.mm.attributes.AttributeCode;
import org.compiere.model.I_C_UOM;
import org.slf4j.Logger;

import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.conversion.ConversionHelper;
import de.metas.logging.LogManager;
import de.metas.uom.UOMType;
import lombok.NonNull;
import lombok.ToString;

/**
 * Wraps and {@link IAttributeStorage} and expose all weightable methods.
 *
 * @author tsa
 *
 */
@ToString
final class AttributeStorageWeightableWrapper implements IWeightable
{
	private static final Logger logger = LogManager.getLogger(AttributeStorageWeightableWrapper.class);

	private final IAttributeStorage attributeStorage;

	AttributeStorageWeightableWrapper(@NonNull final IAttributeStorage attributeStorage)
	{
		this.attributeStorage = attributeStorage;
	}

	/**
	 * @return underlying attribute storage; never return null
	 */
	private IAttributeStorage getAttributeStorage()
	{
		return attributeStorage;
	}

	@Override
	public boolean isWeightable()
	{
		final IAttributeStorage attributeStorage = getAttributeStorage();
		final UOMType uomType = attributeStorage.getQtyUOMTypeOrNull(); // TODO: optimize this shit!
		return uomType != null && uomType.isWeight();
	}

	@Override
	public boolean isWeighted()
	{
		final AttributeCode weightNetAttribute = getWeightNetAttribute();
		if (weightNetAttribute == null)
		{
			return false;
		}

		final IAttributeStorage attributeStorage = getAttributeStorage();
		if (!attributeStorage.hasAttribute(weightNetAttribute))
		{
			return false;
		}

		final BigDecimal weight = attributeStorage.getValueAsBigDecimal(weightNetAttribute);
		if (weight == null || weight.signum() == 0)
		{
			return false;
		}

		return true;
	}

	@Override
	public boolean isWeightGrossAttribute(final AttributeCode attribute)
	{
		final AttributeCode attr_WeightGross = getWeightGrossAttribute();
		return isWeightAttribute(attribute, attr_WeightGross);
	}

	@Override
	public boolean isWeightNetAttribute(final AttributeCode attribute)
	{
		final AttributeCode attr_WeightNet = getWeightNetAttribute();
		return isWeightAttribute(attribute, attr_WeightNet);
	}

	@Override
	public boolean isWeightTareAttribute(final AttributeCode attribute)
	{
		final AttributeCode attr_WeightTare = getWeightTareAttribute();
		return isWeightAttribute(attribute, attr_WeightTare);
	}

	@Override
	public boolean isWeightTareAdjustAttribute(final AttributeCode attribute)
	{
		final AttributeCode attr_WeightTareAdjust = getWeightTareAdjustAttribute();
		return isWeightAttribute(attribute, attr_WeightTareAdjust);
	}

	private boolean isWeightAttribute(final AttributeCode attributeCode, final AttributeCode expectedWeightAttributeCode)
	{
		if (attributeCode == null)
		{
			return false;
		}
		if (expectedWeightAttributeCode == null)
		{
			return false;
		}

		if (!AttributeCode.equals(attributeCode, expectedWeightAttributeCode))
		{
			logger.trace("Expected: {}; Received: {}", expectedWeightAttributeCode, attributeCode);
			return false;
		}

		return true;
	}

	@Override
	public BigDecimal getWeightGross()
	{
		final AttributeCode attr_WeightGross = getWeightGrossAttribute();
		return getWeight(attr_WeightGross);
	}

	@Override
	public void setWeightGross(final BigDecimal weightGross)
	{
		final AttributeCode attr_WeightGross = getWeightGrossAttribute();
		setWeight(attr_WeightGross, weightGross);
	}

	@Override
	public AttributeCode getWeightGrossAttribute()
	{
		return Weightables.ATTR_WeightGross;
	}

	@Override
	public BigDecimal getWeightNet()
	{
		final AttributeCode attr_WeightNet = getWeightNetAttribute();
		return getWeight(attr_WeightNet);
	}

	@Override
	public BigDecimal getWeightNetOrNull()
	{
		final AttributeCode weightAttribute = getWeightNetAttribute();
		return getWeightOrNull(weightAttribute);
	}

	@Override
	public void setWeightNet(final BigDecimal weightNet)
	{
		final AttributeCode attr_WeightNet = getWeightNetAttribute();
		setWeight(attr_WeightNet, weightNet);
	}

	@Override
	public void setWeightNetNoPropagate(final BigDecimal weightNet)
	{
		final AttributeCode attr_WeightNet = getWeightNetAttribute();
		setWeightNoPropagate(attr_WeightNet, weightNet);
	}

	@Override
	public AttributeCode getWeightNetAttribute()
	{
		return Weightables.ATTR_WeightNet;
	}

	@Override
	public I_C_UOM getWeightNetUOM()
	{
		final AttributeCode attr_WeightNet = getWeightNetAttribute();
		return getWeightUOM(attr_WeightNet);
	}

	@Override
	public BigDecimal getWeightTare()
	{
		final AttributeCode attr_WeightTare = getWeightTareAttribute();
		return getWeight(attr_WeightTare);
	}

	@Override
	public BigDecimal getWeightTareInitial()
	{
		final AttributeCode attr_WeightTare = getWeightTareAttribute();
		return getWeightInitial(attr_WeightTare);
	}

	@Override
	public AttributeCode getWeightTareAttribute()
	{
		return Weightables.ATTR_WeightTare;
	}

	@Override
	public BigDecimal getWeightTareAdjust()
	{
		final AttributeCode attr_WeightTareAdjust = getWeightTareAdjustAttribute();
		return getWeight(attr_WeightTareAdjust);
	}

	@Override
	public void setWeightTareAdjust(BigDecimal weightTareAdjust)
	{
		setWeight(getWeightGrossAttribute(), weightTareAdjust);
	}

	@Override
	public AttributeCode getWeightTareAdjustAttribute()
	{
		return Weightables.ATTR_WeightTareAdjust;
	}

	private BigDecimal getWeight(final AttributeCode weightAttribute)
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
	private BigDecimal getWeightOrNull(final AttributeCode weightAttribute)
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

	private BigDecimal getWeightInitial(final AttributeCode weightAttribute)
	{
		final IAttributeStorage attributeStorage = getAttributeStorage();
		return attributeStorage.getValueInitialAsBigDecimal(weightAttribute);
	}

	private void setWeight(final AttributeCode weightAttribute, final BigDecimal weight)
	{
		final IAttributeStorage attributeStorage = getAttributeStorage();

		logger.debug("Setting {} attribute Value={} on {}", weightAttribute, weight, attributeStorage);
		attributeStorage.setValue(weightAttribute, weight);
	}

	/**
	 * Set the given weight directly, avoiding attribute propagation.
	 *
	 * @param weightAttribute
	 * @param weight
	 */
	private void setWeightNoPropagate(final AttributeCode weightAttribute, final BigDecimal weight)
	{
		final IAttributeStorage attributeStorage = getAttributeStorage();

		logger.debug("Setting {} INTERNAL attribute Value={} on {}", weightAttribute, weight, attributeStorage);
		attributeStorage.setValueNoPropagate(weightAttribute, weight); // directly set the correct value we're expecting

	}

	private I_C_UOM getWeightUOM(final AttributeCode weightAttribute)
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
		final AttributeCode attr_WeightGross = getWeightGrossAttribute();
		return hasWeightAttribute(attr_WeightGross);
	}

	/**
	 * @return true if has WeightNet attribute
	 */
	@Override
	public boolean hasWeightNet()
	{
		final AttributeCode attr_WeightNet = getWeightNetAttribute();
		return hasWeightAttribute(attr_WeightNet);
	}

	/**
	 * @return true if has WeightTare attribute
	 */
	@Override
	public boolean hasWeightTare()
	{
		final AttributeCode attr_WeightTare = getWeightTareAttribute();
		return hasWeightAttribute(attr_WeightTare);
	}

	/**
	 * @return true if has WeightTare(adjust) attribute
	 */
	@Override
	public boolean hasWeightTareAdjust()
	{
		final AttributeCode attr_WeightTareAdjust = getWeightTareAdjustAttribute();
		return hasWeightAttribute(attr_WeightTareAdjust);
	}

	/**
	 * @return true if given attribute exists
	 */
	private boolean hasWeightAttribute(final AttributeCode weightAttribute)
	{
		if (weightAttribute == null)
		{
			return false;
		}
		final IAttributeStorage attributeStorage = getAttributeStorage();
		return attributeStorage.hasAttribute(weightAttribute);
	}
}
