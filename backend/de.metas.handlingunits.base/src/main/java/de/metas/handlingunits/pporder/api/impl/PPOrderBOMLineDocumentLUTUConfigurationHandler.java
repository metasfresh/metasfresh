package de.metas.handlingunits.pporder.api.impl;

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

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.impl.AbstractDocumentLUTUConfigurationHandler;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_PP_Order;
import de.metas.handlingunits.model.I_PP_Order_BOMLine;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.PPOrderUtil;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.Properties;

/**
 * Handles {@link I_PP_Order_BOMLine} lines from which we are able to receive materials
 *
 * @author tsa
 *
 */
/* package */class PPOrderBOMLineDocumentLUTUConfigurationHandler extends AbstractDocumentLUTUConfigurationHandler<I_PP_Order_BOMLine>
{
	public static final transient PPOrderBOMLineDocumentLUTUConfigurationHandler instance = new PPOrderBOMLineDocumentLUTUConfigurationHandler();

	private PPOrderBOMLineDocumentLUTUConfigurationHandler()
	{
		super();
	}

	@Override
	public I_M_HU_LUTU_Configuration createNewLUTUConfiguration(final I_PP_Order_BOMLine ppOrderBOMLine)
	{
		PPOrderUtil.assertReceipt(ppOrderBOMLine);

		final org.eevolution.model.I_PP_Order ppOrder = ppOrderBOMLine.getPP_Order();
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(ppOrder.getC_BPartner_ID());
		final I_M_HU_PI_Item_Product tuPIItemProduct = getM_HU_PI_Item_Product(ppOrderBOMLine);
		final ProductId cuProductId = ProductId.ofRepoId(ppOrderBOMLine.getM_Product_ID());
		final UomId cuUOMId = Services.get(IPPOrderBOMBL.class).getBOMLineUOMId(ppOrderBOMLine);

		// LU/TU COnfiguration
		final ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);
		final I_M_HU_LUTU_Configuration lutuConfiguration = lutuConfigurationFactory.createLUTUConfiguration(
				tuPIItemProduct,
				cuProductId,
				cuUOMId,
				bpartnerId,
				true); // noLUForVirtualTU == true => for a "virtual" TU, we want the LU-part of the lutuconfig to be empty by default
		updateLUTUConfigurationFromDocumentLine(lutuConfiguration, ppOrderBOMLine);

		return lutuConfiguration;
	}

	@Override
	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product(@NonNull final I_PP_Order_BOMLine ppOrderBOMLine)
	{
		final IHUPIItemProductDAO hupiItemProductDAO = Services.get(IHUPIItemProductDAO.class);
		final Properties ctx = InterfaceWrapperHelper.getCtx(ppOrderBOMLine);

		//
		// First, try getting the M_HU_Item_Product the ppOrder's M_HU_LUTU_Configuration
		{
			final I_M_HU_LUTU_Configuration lutuConfiguration = ppOrderBOMLine.getM_HU_LUTU_Configuration();
			final I_M_HU_PI_Item_Product pip = lutuConfiguration != null ? ILUTUConfigurationFactory.extractHUPIItemProductOrNull(lutuConfiguration) : null;
			if (pip != null)
			{
				return pip;
			}
		}

		//
		// Fallback: return the virtual PI Item Product
		final I_M_HU_PI_Item_Product pipVirtual = hupiItemProductDAO.retrieveVirtualPIMaterialItemProduct(ctx);
		return pipVirtual;
	}

	@Override
	public void updateLUTUConfigurationFromDocumentLine(@NonNull final I_M_HU_LUTU_Configuration lutuConfiguration, @NonNull final I_PP_Order_BOMLine documentLine)
	{
		final I_PP_Order ppOrder = InterfaceWrapperHelper.create(documentLine.getPP_Order(), I_PP_Order.class);
		PPOrderDocumentLUTUConfigurationHandler.instance.updateLUTUConfigurationFromDocumentLine(lutuConfiguration, ppOrder);
	}

	@Override
	public void setCurrentLUTUConfiguration(@NonNull final I_PP_Order_BOMLine documentLine, final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		documentLine.setM_HU_LUTU_Configuration(lutuConfiguration);
	}

	@Override
	public I_M_HU_LUTU_Configuration getCurrentLUTUConfigurationOrNull(@NonNull final I_PP_Order_BOMLine documentLine)
	{
		final I_M_HU_LUTU_Configuration lutuConfiguration = documentLine.getM_HU_LUTU_Configuration();
		if (lutuConfiguration == null || lutuConfiguration.getM_HU_LUTU_Configuration_ID() <= 0)
		{
			return null;
		}

		return lutuConfiguration;
	}
}
