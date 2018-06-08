package org.adempiere.mm.attributes.spi.impl;

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
import java.util.Objects;
import java.util.Properties;

import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_Attribute;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Item;

public class WeightTareAttributeValueCallout
		extends AbstractWeightAttributeValueCallout
{
	/**
	 * Fires WeightGross recalculation based on existing WeightNet & the new WeightTare value
	 */
	@Override
	public void onValueChanged0(final IAttributeValueContext attributeValueContext,
			final IAttributeSet attributeSet,
			final I_M_Attribute attribute,
			final Object valueOld,
			final Object valueNew)
	{
		recalculateWeightGross(attributeSet);
	}

	/**
	 * Returns the summed weight of the packing material of the given <code>attributeSet</code>'s HU.
	 * <p>
	 * NOTE: does <b>not</b> descent into sub-HUs to also add their tare values. This is good, because this value is used in bottom-up-propagation, i.e. the children tare values are added during
	 * propagation.
	 */
	@Override
	public Object generateSeedValue(final IAttributeSet attributeSet, final I_M_Attribute attribute, final Object valueInitialDefault)
	{
		// we don't support a value different from null
		Check.assumeNull(valueInitialDefault, "valueInitialDefault null");

		final I_M_HU hu = Services.get(IHUAttributesBL.class).getM_HU(attributeSet);

		final BigDecimal weightTare = calculateWeightTare(hu);
		return weightTare;
	}

	/**
	 * Calculates Weight Tare for given HU.
	 *
	 * NOTE: this method calculates PI's tare weight without considering included HUs because we don't know how many are.
	 *
	 * @param piVersion
	 * @return weight tare
	 */
	public static BigDecimal calculateWeightTare(final I_M_HU hu)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		final BigDecimal weightTare;
		if (handlingUnitsBL.isAggregateHU(hu))
		{
			final BigDecimal qty = hu.getM_HU_Item_Parent().getQty();

			weightTare = handlingUnitsDAO.retrieveItems(hu).stream()
					// only packing material items..
					.filter(item -> Objects.equals(handlingUnitsBL.getItemType(item), X_M_HU_Item.ITEMTYPE_PackingMaterial))

					// .. get their M_HU_PackingMaterial and Qty, if they have both
					.map(item -> handlingUnitsBL.getHUPackingMaterial(item))
					.filter(packingmaterial -> packingmaterial != null)

					// multiply their M_HU_PackingMaterial's weight
					.map(packingmaterial -> getWeightTare(packingmaterial).multiply(qty))

					// sum it up, in case the HU has multiple packagings, such as abox with a lid
					.reduce(BigDecimal.ZERO, BigDecimal::add);
		}
		else
		{
			final I_M_HU_PI_Version piVersion = handlingUnitsBL.getPIVersion(hu);
			weightTare = getWeightTare(piVersion);
		}

		return weightTare;
	}

	/**
	 * Iterates the given <code>piVersion</code>'s active packing material items and sums of the weights of the attached <code>M_Product</code>s (if any).
	 * <p>
	 * NOTE: does <b>not</b> descent into sub-HUs, which is good, because this value is used in bottom-up/top-down propagation, i.e. the childrens' tare values are added during propagation.
	 */
	public static BigDecimal getWeightTare(final I_M_HU_PI_Version piVersion)
	{
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		BigDecimal weightTareTotal = BigDecimal.ZERO;

		final I_C_BPartner partner = null; // FIXME: get context C_BPartner

		for (final I_M_HU_PI_Item piItem : handlingUnitsDAO.retrievePIItems(piVersion, partner))
		{
			final String itemType = piItem.getItemType();
			if (!X_M_HU_PI_Item.ITEMTYPE_PackingMaterial.equals(itemType))
			{
				continue;
			}

			final I_M_HU_PackingMaterial huPackingMaterial = piItem.getM_HU_PackingMaterial();
			if (huPackingMaterial == null)
			{
				continue;
			}

			final BigDecimal weightTare = getWeightTare(huPackingMaterial);
			weightTareTotal = weightTareTotal.add(weightTare);
		}

		return weightTareTotal;
	}

	/**
	 * 
	 * @param huPackingMaterial
	 * @return never returns {@code null}.
	 */
	private static BigDecimal getWeightTare(final I_M_HU_PackingMaterial huPackingMaterial)
	{
		if (!huPackingMaterial.isActive())
		{
			return BigDecimal.ZERO;
		}

		final I_M_Product product = huPackingMaterial.getM_Product();
		if (product == null)
		{
			return BigDecimal.ZERO;
		}

		final BigDecimal weightTare = product.getWeight();

		return weightTare;
	}

	@Override
	public String getAttributeValueType()
	{
		return X_M_Attribute.ATTRIBUTEVALUETYPE_Number;
	}

	@Override
	public boolean canGenerateValue(final Properties ctx, final IAttributeSet attributeSet, final I_M_Attribute attribute)
	{
		return true;
	}

	@Override
	public BigDecimal generateNumericValue(final Properties ctx, final IAttributeSet attributeSet, final I_M_Attribute attribute)
	{
		final Object valueInitialDefault = null; // no initial default value
		return (BigDecimal)generateSeedValue(attributeSet, attribute, valueInitialDefault);
	}

	@Override
	protected boolean isExecuteCallout(final IAttributeValueContext attributeValueContext,
			final IAttributeSet attributeSet,
			final I_M_Attribute attribute,
			final Object valueOld,
			final Object valueNew)
	{
		return !Objects.equals(valueOld, valueNew);
	}
}
