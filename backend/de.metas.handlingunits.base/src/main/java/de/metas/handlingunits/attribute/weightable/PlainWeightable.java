package de.metas.handlingunits.attribute.weightable;

import java.math.BigDecimal;

import org.adempiere.mm.attributes.AttributeCode;
import org.compiere.model.I_C_UOM;

import de.metas.util.lang.CoalesceUtil;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.handlingunits.base
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

@EqualsAndHashCode
@ToString
public class PlainWeightable implements IWeightable
{
	private I_C_UOM uom;
	private BigDecimal weightGross;
	private BigDecimal weightNet;
	private final BigDecimal weightTareInitial;
	private final BigDecimal weightTare;
	private BigDecimal weightTareAdjust;

	@Builder
	private PlainWeightable(
			@NonNull final I_C_UOM uom,
			BigDecimal weightGross,
			BigDecimal weightNet,
			BigDecimal weightTare,
			BigDecimal weightTareAdjust)
	{
		this.uom = uom;
		this.weightGross = CoalesceUtil.coalesce(weightGross, BigDecimal.ZERO);
		this.weightNet = CoalesceUtil.coalesce(weightNet, BigDecimal.ZERO);
		this.weightTare = CoalesceUtil.coalesce(weightTare, BigDecimal.ZERO);
		this.weightTareInitial = this.weightTare;
		this.weightTareAdjust = CoalesceUtil.coalesce(weightTareAdjust, BigDecimal.ZERO);
	}

	@Override
	public boolean isWeighted()
	{
		return true;
	}

	@Override
	public boolean isWeightable()
	{
		return true;
	}

	@Override
	public boolean hasWeightTareAdjust()
	{
		return true;
	}

	@Override
	public boolean hasWeightTare()
	{
		return true;
	}

	@Override
	public boolean hasWeightNet()
	{
		return true;
	}

	@Override
	public boolean hasWeightGross()
	{
		return true;
	}

	@Override
	public AttributeCode getWeightTareAdjustAttribute()
	{
		return Weightables.ATTR_WeightTareAdjust;
	}

	@Override
	public BigDecimal getWeightTareAdjust()
	{
		return weightTareAdjust;
	}

	@Override
	public void setWeightTareAdjust(BigDecimal weightTareAdjust)
	{
		this.weightTareAdjust = weightTareAdjust;
	}

	@Override
	public AttributeCode getWeightTareAttribute()
	{
		return Weightables.ATTR_WeightTare;
	}

	@Override
	public BigDecimal getWeightTareInitial()
	{
		return weightTareInitial;
	}

	@Override
	public BigDecimal getWeightTare()
	{
		return weightTare;
	}

	@Override
	public AttributeCode getWeightNetAttribute()
	{
		return Weightables.ATTR_WeightNet;
	}

	@Override
	public void setWeightNetNoPropagate(final BigDecimal weightNet)
	{
		this.weightNet = weightNet;
	}

	@Override
	public void setWeightNet(final BigDecimal weightNet)
	{
		setWeightNetNoPropagate(weightNet);
	}

	@Override
	public BigDecimal getWeightNetOrNull()
	{
		return getWeightNet();
	}

	@Override
	public BigDecimal getWeightNet()
	{
		return weightNet;
	}

	@Override
	public I_C_UOM getWeightNetUOM()
	{
		return uom;
	}

	@Override
	public AttributeCode getWeightGrossAttribute()
	{
		return Weightables.ATTR_WeightGross;
	}

	@Override
	public void setWeightGross(final BigDecimal weightGross)
	{
		this.weightGross = weightGross;
	}

	@Override
	public BigDecimal getWeightGross()
	{
		return weightGross;
	}

	@Override
	public boolean isWeightTareAdjustAttribute(final AttributeCode attribute)
	{
		return AttributeCode.equals(attribute, Weightables.ATTR_WeightTareAdjust);
	}

	@Override
	public boolean isWeightTareAttribute(final AttributeCode attribute)
	{
		return AttributeCode.equals(attribute, Weightables.ATTR_WeightTare);
	}

	@Override
	public boolean isWeightNetAttribute(final AttributeCode attribute)
	{
		return AttributeCode.equals(attribute, Weightables.ATTR_WeightNet);
	}

	@Override
	public boolean isWeightGrossAttribute(final AttributeCode attribute)
	{
		return AttributeCode.equals(attribute, Weightables.ATTR_WeightGross);
	}
}
