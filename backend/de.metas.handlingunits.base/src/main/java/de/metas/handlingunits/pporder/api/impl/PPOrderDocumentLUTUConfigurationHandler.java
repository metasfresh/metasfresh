package de.metas.handlingunits.pporder.api.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.impl.AbstractDocumentLUTUConfigurationHandler;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_PP_Order;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.PPOrderQuantities;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IPPOrderBL;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Properties;

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

/**
 * This class has the job of managing a {@link I_M_HU_LUTU_Configuration} for a particular {@link I_PP_Order}..it might retrieve that ppOrder's lutuConfig or create a new default one.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
/* package */class PPOrderDocumentLUTUConfigurationHandler extends AbstractDocumentLUTUConfigurationHandler<I_PP_Order>
{
	public static final transient PPOrderDocumentLUTUConfigurationHandler instance = new PPOrderDocumentLUTUConfigurationHandler();

	private PPOrderDocumentLUTUConfigurationHandler()
	{
		super();
	}

	@Override
	public I_M_HU_LUTU_Configuration createNewLUTUConfiguration(@NonNull final I_PP_Order ppOrder)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(ppOrder.getC_BPartner_ID());
		final I_M_HU_PI_Item_Product tuPIItemProduct = getM_HU_PI_Item_Product(ppOrder);
		final ProductId cuProductId = ProductId.ofRepoId(ppOrder.getM_Product_ID());
		final UomId cuUOMId = UomId.ofRepoId(ppOrder.getC_UOM_ID());

		//
		// LU/TU COnfiguration
		final ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);
		final I_M_HU_LUTU_Configuration lutuConfiguration = lutuConfigurationFactory.createLUTUConfiguration(
				tuPIItemProduct,
				cuProductId,
				cuUOMId,
				bpartnerId,
				true); // noLUForVirtualTU == true => for a "virtual" TU, we want the LU-part of the lutuconfig to be empty by default

		final BigDecimal cuPerTu = ILUTUConfigurationFactory.extractHUPIItemProduct(lutuConfiguration).getQty();
		if (cuPerTu.signum() > 0)
		{
			final UomId uomId = ILUTUConfigurationFactory.extractUomIdOrNull(lutuConfiguration);

			final PPOrderQuantities ppOrderQtys = Services.get(IPPOrderBOMBL.class).getQuantities(ppOrder, uomId);
			final Quantity undeliveredQtyCU = ppOrderQtys.getQtyRequiredToProduce().subtract(ppOrderQtys.getQtyReceived());

			final Quantity undeliveredQtyTU = undeliveredQtyCU.divide(cuPerTu, 0, RoundingMode.CEILING);
			lutuConfiguration.setQtyTU(undeliveredQtyTU.toBigDecimal().min(lutuConfiguration.getQtyTU()));

			final int qtyLU = lutuConfigurationFactory.calculateQtyLUForTotalQtyCUs(lutuConfiguration, undeliveredQtyCU);
			lutuConfiguration.setQtyLU(BigDecimal.valueOf(qtyLU));
		}

		// Update LU/TU configuration
		updateLUTUConfigurationFromDocumentLine(lutuConfiguration, ppOrder);

		return lutuConfiguration;
	}

	@Override
	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product(final I_PP_Order ppOrder)
	{
		final IHUPIItemProductDAO hupiItemProductDAO = Services.get(IHUPIItemProductDAO.class);
		final Properties ctx = InterfaceWrapperHelper.getCtx(ppOrder);

		//
		// First, try getting the M_HU_Item_Product the ppOrder's M_HU_LUTU_Configuration
		{
			final I_M_HU_LUTU_Configuration lutuConfiguration = ppOrder.getM_HU_LUTU_Configuration();
			final I_M_HU_PI_Item_Product pip = lutuConfiguration != null ? ILUTUConfigurationFactory.extractHUPIItemProductOrNull(lutuConfiguration) : null;
			if (pip != null)
			{
				return pip;
			}
		}

		final HUPIItemProductId packingMaterialId = HUPIItemProductId.ofRepoIdOrNull(ppOrder.getM_HU_PI_Item_Product_ID());
		if (packingMaterialId != null)
		{
			return hupiItemProductDAO.getRecordById(packingMaterialId);
		}

		//
		// Try getting the M_HU_Item_Product from directly linked Sales Order
		final I_C_OrderLine directOrderLine = Services.get(IPPOrderBL.class).getDirectOrderLine(ppOrder);
		if (directOrderLine != null)
		{
			final de.metas.handlingunits.model.I_C_OrderLine huOrderLine = InterfaceWrapperHelper.create(directOrderLine, de.metas.handlingunits.model.I_C_OrderLine.class);

			final I_M_HU_PI_Item_Product pip = huOrderLine.getM_HU_PI_Item_Product();
			if (pip != null && pip.getM_HU_PI_Item_Product_ID() > 0)
			{
				return pip;
			}
		}

		//
		// try to get the default M_HU_Item_Product from the product CU-TU allocation
		final ZonedDateTime date = TimeUtil.asZonedDateTime(ppOrder.getDatePromised());

		final Optional<I_M_HU_PI_Item_Product> defaultPip = hupiItemProductDAO.retrieveDefaultForProduct(
				ProductId.ofRepoId(ppOrder.getM_Product_ID()),
				BPartnerId.ofRepoIdOrNull(ppOrder.getC_BPartner_ID()),
				date);

		if (defaultPip.isPresent())
		{
			return defaultPip.get();
		}

		//
		// Fallback: return the virtual PI Item Product
		final I_M_HU_PI_Item_Product pipVirtual = hupiItemProductDAO.retrieveVirtualPIMaterialItemProduct(ctx);
		return pipVirtual;
	}

	@Override
	public void updateLUTUConfigurationFromDocumentLine(
			@NonNull final I_M_HU_LUTU_Configuration lutuConfiguration,
			@NonNull final I_PP_Order ppOrder)
	{
		lutuConfiguration.setC_BPartner_ID(ppOrder.getC_BPartner_ID());

		final int ppOrderReceiptLocatorId = ppOrder.getM_Locator_ID();
		lutuConfiguration.setM_Locator_ID(ppOrderReceiptLocatorId);

		lutuConfiguration.setHUStatus(X_M_HU.HUSTATUS_Active);
	}

	@Override
	public void setCurrentLUTUConfiguration(final I_PP_Order documentLine, final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		documentLine.setM_HU_LUTU_Configuration(lutuConfiguration);
	}

	@Override
	public I_M_HU_LUTU_Configuration getCurrentLUTUConfigurationOrNull(final I_PP_Order documentLine)
	{
		final I_M_HU_LUTU_Configuration lutuConfiguration = documentLine.getM_HU_LUTU_Configuration();
		if (lutuConfiguration == null || lutuConfiguration.getM_HU_LUTU_Configuration_ID() <= 0)
		{
			return null;
		}

		return lutuConfiguration;
	}
}
