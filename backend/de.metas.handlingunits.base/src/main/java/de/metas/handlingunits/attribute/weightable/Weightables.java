package de.metas.handlingunits.attribute.weightable;

import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.mm.attributes.AttributeCode;

import java.math.BigDecimal;
import java.util.stream.Stream;

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

@UtilityClass
public class Weightables
{
	public static final AttributeCode ATTR_WeightGross = AttributeCode.ofString("WeightGross");
	public static final AttributeCode ATTR_WeightNet = AttributeCode.ofString("WeightNet");
	public static final AttributeCode ATTR_WeightTare = AttributeCode.ofString("WeightTare");
	public static final AttributeCode ATTR_WeightTareAdjust = AttributeCode.ofString("WeightTareAdjust");

	/**
	 * Boolean property which if set, it will allow user to change weights but ONLY on VHU level
	 * <p>
	 * task http://dewiki908/mediawiki/index.php/08270_Wareneingang_POS_multiple_lines_in_1_TU_%28107035315495%29
	 */
	public static final String PROPERTY_WeightableOnlyIfVHU = IWeightable.class.getName() + ".WeightableOnlyIfVHU";

	public IWeightable wrap(@NonNull final IAttributeStorage attributeStorage)
	{
		return new AttributeStorageWeightableWrapper(attributeStorage);
	}

	public PlainWeightable plainOf(@NonNull final IAttributeStorage attributeStorage)
	{
		return PlainWeightable.copyOf(wrap(attributeStorage));
	}

	public static void updateWeightNet(@NonNull final IWeightable weightable)
	{
		// NOTE: we calculate WeightGross, no matter if our HU is allowed to be weighted by user
		// final boolean weightUOMFriendly = weightable.isWeightable();

		final BigDecimal weightTare = weightable.getWeightTareTotal();
		final BigDecimal weightGross = weightable.getWeightGross();
		final BigDecimal weightNet = weightGross.subtract(weightTare);
		final BigDecimal weightNetActual;

		//
		// If Gross < Tare, we need to propagate the net value with the initial container's Tare value re-added (+) to preserve the real mathematical values
		if (weightNet.signum() >= 0)
		{
			weightNetActual = weightNet; // propagate net value below normally

			weightable.setWeightNet(weightNetActual);
		}
		else
		{
			weightNetActual = weightNet.add(weightable.getWeightTareInitial()); // only subtract seed value (the container's weight)

			weightable.setWeightNet(weightNetActual);
			weightable.setWeightNetNoPropagate(weightNet); // directly set the correct value we're expecting
		}

	}

	public static boolean isWeightableAttribute(@NonNull final AttributeCode attributeCode)
	{
		return Weightables.ATTR_WeightGross.equals(attributeCode)
				|| Weightables.ATTR_WeightNet.equals(attributeCode)
				|| Weightables.ATTR_WeightTare.equals(attributeCode)
				|| Weightables.ATTR_WeightTareAdjust.equals(attributeCode);

	}

	public static boolean isWeightRelatedAttribute(@NonNull final AttributeCode attributeCode)
	{
		return Stream.of(ATTR_WeightGross, ATTR_WeightNet, ATTR_WeightTare, ATTR_WeightTareAdjust)
				.anyMatch(weightCode -> AttributeCode.equals(weightCode, attributeCode));
	}
}
