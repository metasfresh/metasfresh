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

import de.metas.handlingunits.HUItemType;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_Attribute;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.inout.IHUPackingMaterialDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_UOM;

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
	 * <p>
	 * NOTE: this method calculates PI's tare weight without considering included HUs because we don't know how many are.
	 *
	 * @return weight tare
	 */
	public static BigDecimal calculateWeightTare(final I_M_HU hu)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IHUPackingMaterialDAO packingMaterialDAO = Services.get(IHUPackingMaterialDAO.class);

		final BigDecimal weightTare;
		if (handlingUnitsBL.isAggregateHU(hu))
		{
			final BigDecimal qty = hu.getM_HU_Item_Parent().getQty();

			weightTare = handlingUnitsBL
					// only packing material items..
					.retrieveItems(hu, HUItemType.PackingMaterial).stream()

					// .. get their M_HU_PackingMaterial and Qty, if they have both
					.map(item -> packingMaterialDAO.retrieveHUPackingMaterialOrNull(item))
					.filter(Objects::nonNull)

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

		// In addition to the structural packing-material tare, include the per-CU packaging
		// contribution from the product master (M_Product.GrossWeight − M_Product.Weight) × storage qty.
		// Mirrors the incremental write done by WeightGenerateHUTrxListener so recompute paths
		// (AggregateHUTrxListener, AbstractProducerDestination#loadFinished, attribute seed) stay consistent.
		return weightTare.add(calculateProductPackagingDelta(hu));
	}

	/**
	 * Sums the per-CU packaging contribution {@code (gross − net) × qty} across the HU's product
	 * storages, expressed in the net-weight UOM (KG). Returns 0 when the HU has no storage,
	 * when no product on the storage maintains a real net weight (default 0), or when gross/net
	 * are equal.
	 */
	private static BigDecimal calculateProductPackagingDelta(final I_M_HU hu)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IProductBL productBL = Services.get(IProductBL.class);
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

		final IHUStorage huStorage = handlingUnitsBL.getStorageFactory().getStorage(hu);

		BigDecimal total = BigDecimal.ZERO;
		for (final IHUProductStorage productStorage : huStorage.getProductStorages())
		{
			final Quantity storageQty = productStorage.getQty();
			if (storageQty.signum() <= 0)
			{
				continue;
			}

			final ProductId productId = productStorage.getProductId();
			final I_C_UOM storageUOM = storageQty.getUOM();

			// Per-CU gross/net weights, scaled to the storage UOM.
			final Quantity productGross = productBL.getGrossWeight(productId, storageUOM).orElse(null);
			if (productGross == null || productGross.signum() <= 0)
			{
				continue;
			}

			final I_M_Product product = productBL.getById(productId);
			final Quantity productNet = productBL.getNetWeight(product, storageUOM).orElse(null);
			if (productNet == null || productNet.signum() <= 0)
			{
				continue;
			}

			// Convert gross to net's UOM before subtracting (net is hardcoded KG; gross may be e.g. grams).
			final Quantity productGrossInNetUOM = UomId.equals(productGross.getUomId(), productNet.getUomId())
					? productGross
					: uomConversionBL.convertQuantityTo(productGross, productId, productNet.getUOM());

			final BigDecimal perUnitDeltaBD = productGrossInNetUOM.toBigDecimal().subtract(productNet.toBigDecimal());
			if (perUnitDeltaBD.signum() <= 0)
			{
				continue;
			}

			total = total.add(perUnitDeltaBD.multiply(storageQty.toBigDecimal()));
		}
		return total;
	}

	/**
	 * Iterates the given <code>piVersion</code>'s active packing material items and sums of the weights of the attached <code>M_Product</code>s (if any).
	 * <p>
	 * NOTE: does <b>not</b> descent into sub-HUs, which is good, because this value is used in bottom-up/top-down propagation, i.e. the childrens' tare values are added during propagation.
	 */
	public static BigDecimal getWeightTare(final I_M_HU_PI_Version piVersion)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		BigDecimal weightTareTotal = BigDecimal.ZERO;

		final BPartnerId partnerId = null; // FIXME: get context C_BPartner

		for (final I_M_HU_PI_Item piItem : handlingUnitsBL.retrievePIItems(piVersion, partnerId))
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
	 * @param huPackingMaterial
	 * @return never returns {@code null}.
	 */
	private static BigDecimal getWeightTare(final I_M_HU_PackingMaterial huPackingMaterial)
	{
		if (!huPackingMaterial.isActive())
		{
			return BigDecimal.ZERO;
		}

		final I_M_Product product = IHUPackingMaterialDAO.extractProductOrNull(huPackingMaterial);
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

	@Override
	public boolean isDisplayedUI(@NonNull final IAttributeSet attributeSet, @NonNull final I_M_Attribute attribute)
	{
		return isLUorTUorTopLevelVHU(attributeSet);
	}
}
