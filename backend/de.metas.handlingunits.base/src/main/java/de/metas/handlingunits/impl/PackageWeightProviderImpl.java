/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.handlingunits.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.common.util.CoalesceUtil;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IPackageWeightProvider;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.inout.IHUPackingMaterialDAO;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HuPackingMaterial;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PackageWeightProviderImpl implements IPackageWeightProvider
{
	private final IHUPIItemProductBL hupiItemProductBL = Services.get(IHUPIItemProductBL.class);
	private final ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);
	private final IHUPackingMaterialDAO packingMaterialDAO = Services.get(IHUPackingMaterialDAO.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	@Override
	public @Nullable BigDecimal computeGrossWeightKg(
			@NonNull final I_C_Order order,
			@NonNull final I_C_OrderLine orderLine,
			@NonNull final BigDecimal tuQtyForPackage)
	{
		if (tuQtyForPackage.signum() <= 0)
		{
			return null;
		}

		final de.metas.handlingunits.model.I_C_OrderLine huOrderLine = InterfaceWrapperHelper.create(orderLine, de.metas.handlingunits.model.I_C_OrderLine.class);
		final I_M_HU_PI_Item_Product tuPIItemProduct = hupiItemProductBL.extractHUPIItemProduct(order, huOrderLine);
		final UomId cuUomId = UomId.ofRepoId(orderLine.getC_UOM_ID());

		final I_M_HU_LUTU_Configuration lutuConfiguration = lutuConfigurationFactory.createLUTUConfiguration(
				tuPIItemProduct,
				ProductId.ofRepoId(orderLine.getM_Product_ID()),
				cuUomId,
				BPartnerId.ofRepoId(order.getC_BPartner_ID()),
				false,
				HuPackingInstructionsId.ofRepoIdOrNull(huOrderLine.getM_LU_HU_PI_ID()),
				huOrderLine.getQtyLU());

		final BigDecimal qtyCUsPerTU = lutuConfiguration.getQtyCUsPerTU();
		if (qtyCUsPerTU == null || qtyCUsPerTU.signum() <= 0)
		{
			return null;
		}

		final BigDecimal qtyCUsForPkg = tuQtyForPackage.multiply(qtyCUsPerTU);

		final ProductId productId = ProductId.ofRepoId(orderLine.getM_Product_ID());
		final Quantity cuQty = Quantitys.of(qtyCUsForPkg, cuUomId);
		final Quantity cuWeight = productBL.computeGrossWeight(productId, cuQty).orElse(null);
		if (cuWeight == null)
		{
			return null;
		}
		final Quantity contentWeightInKgQty = uomConversionBL.convertToKilogram(cuWeight, productId);
		final BigDecimal contentWeightKg = contentWeightInKgQty.getAsBigDecimal();

		// Tare (LU) in kg, if available
		BigDecimal tareKg = null;
		final HuPackingInstructionsId luPiId = CoalesceUtil.coalesceSuppliers(
				() -> HuPackingInstructionsId.ofRepoIdOrNull(huOrderLine.getM_LU_HU_PI_ID()),
				() -> HuPackingInstructionsId.ofRepoIdOrNull(lutuConfiguration.getM_LU_HU_PI_ID()),
				() -> {
					final I_M_HU_PI defaultLU = handlingUnitsDAO.retrieveDefaultLUOrNull(InterfaceWrapperHelper.getCtx(order), order.getAD_Org_ID());
					return defaultLU != null ? HuPackingInstructionsId.ofRepoId(defaultLU.getM_HU_PI_ID()) : null;
				}
		);
		if (luPiId != null)
		{
			final HuPackingMaterial huPm = packingMaterialDAO.getLUPIItemForHUPI(BPartnerId.ofRepoId(order.getC_BPartner_ID()), luPiId).orElse(null);
			if (huPm != null && huPm.getProductId() != null)
			{
				final ProductId pmProductId = huPm.getProductId();
				final Quantity pmGrossPerOne = productBL.getGrossWeight(pmProductId).orElse(null);
				if (pmGrossPerOne != null)
				{
					final Quantity pmGrossPerOneKg = uomConversionBL.convertToKilogram(pmGrossPerOne, pmProductId);
					tareKg = pmGrossPerOneKg.getAsBigDecimal();
				}
			}
		}

		// TODO Tare (TU) in kg and KG precision

		return (tareKg != null ? contentWeightKg.add(tareKg) : contentWeightKg)
				.setScale(3, RoundingMode.HALF_UP);
	}
}
