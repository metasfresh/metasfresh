package de.metas.handlingunits.receiptschedule.impl;



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
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.impl.AbstractDocumentLUTUConfigurationHandler;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;

import java.util.Properties;

/* package */class ReceiptScheduleDocumentLUTUConfigurationHandler extends AbstractDocumentLUTUConfigurationHandler<I_M_ReceiptSchedule>
{
	public static final ReceiptScheduleDocumentLUTUConfigurationHandler instance = new ReceiptScheduleDocumentLUTUConfigurationHandler();
	private final IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);
	private final ILUTUConfigurationFactory lutuFactory = Services.get(ILUTUConfigurationFactory.class);
	private final IHUPIItemProductDAO hupiItemProductDAO = Services.get(IHUPIItemProductDAO.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	private ReceiptScheduleDocumentLUTUConfigurationHandler()
	{
		super();
	}

	@Override
	public I_M_HU_LUTU_Configuration getCurrentLUTUConfigurationOrNull(final I_M_ReceiptSchedule documentLine)
	{
		Check.assumeNotNull(documentLine, "documentLine not null");

		final I_M_HU_LUTU_Configuration lutuConfiguration = documentLine.getM_HU_LUTU_Configuration();
		if (lutuConfiguration == null || lutuConfiguration.getM_HU_LUTU_Configuration_ID() <= 0)
		{
			return null;
		}

		return lutuConfiguration;
	}

	@Override
	public void setCurrentLUTUConfiguration(final I_M_ReceiptSchedule documentLine, final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		documentLine.setM_HU_LUTU_Configuration(lutuConfiguration);
	}

	@Override
	public I_M_HU_LUTU_Configuration createNewLUTUConfiguration(final I_M_ReceiptSchedule documentLine)
	{
		final I_M_HU_PI_Item_Product tuPIItemProduct = getM_HU_PI_Item_Product(documentLine);
		final ProductId cuProductId = ProductId.ofRepoId(documentLine.getM_Product_ID());
		final UomId cuUOMId = UomId.ofRepoId(documentLine.getC_UOM_ID());
		final BPartnerId bpartnerId = receiptScheduleBL.getBPartnerEffectiveId(documentLine);
		final I_C_OrderLine orderLine = orderDAO.getOrderLineById(OrderLineId.ofRepoId(documentLine.getC_OrderLine_ID()), I_C_OrderLine.class);

		final I_M_HU_LUTU_Configuration lutuConfiguration = lutuFactory.createLUTUConfiguration(
				tuPIItemProduct,
				cuProductId,
				cuUOMId,
				bpartnerId,
				false, // noLUForVirtualTU == false => allow placing the CU (e.g. a packing material product) directly on the LU);
				HuPackingInstructionsId.ofRepoIdOrNull(orderLine.getM_LU_HU_PI_ID()),
				orderLine.getQtyLU());

		// Update LU/TU configuration
		updateLUTUConfigurationFromDocumentLine(lutuConfiguration, documentLine);

		// NOTE: don't save it...we might use it as "in-memory POJO"

		return lutuConfiguration;
	}

	@Override
	public void updateLUTUConfigurationFromDocumentLine(final I_M_HU_LUTU_Configuration lutuConfiguration, final I_M_ReceiptSchedule documentLine)
	{
		Check.assumeNotNull(lutuConfiguration, "lutuConfiguration not null");
		Check.assumeNotNull(documentLine, "documentLine not null");

		//
		// Set BPartner / Location to be used
		final int bpartnerId = receiptScheduleBL.getC_BPartner_Effective_ID(documentLine);
		final int bpartnerLocationId = receiptScheduleBL.getC_BPartner_Location_Effective_ID(documentLine);
		lutuConfiguration.setC_BPartner_ID(bpartnerId);
		lutuConfiguration.setC_BPartner_Location_ID(bpartnerLocationId);

		//
		// Set Locator
		final LocatorId locatorId = receiptScheduleBL.getLocatorEffectiveId(documentLine);
		lutuConfiguration.setM_Locator_ID(locatorId.getRepoId());

		//
		// Set HUStatus=Planning because receipt schedules are always about planning
		lutuConfiguration.setHUStatus(X_M_HU.HUSTATUS_Planning);
	}

	@Override
	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product(final I_M_ReceiptSchedule receiptSchedule)
	{
		final I_M_HU_PI_Item_Product tuPIItemProduct = receiptSchedule.getM_HU_PI_Item_Product();
		if (tuPIItemProduct != null && tuPIItemProduct.getM_HU_PI_Item_Product_ID() > 0)
		{
			return tuPIItemProduct;
		}

		//
		// Fallback: return Virtual PI item
		final Properties ctx = InterfaceWrapperHelper.getCtx(receiptSchedule);
		return hupiItemProductDAO.retrieveVirtualPIMaterialItemProduct(ctx);
	}
}
