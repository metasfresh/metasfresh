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

package de.metas.handlingunits.inout.returns.customer;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.impl.AbstractDocumentLUTUConfigurationHandler;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.inout.IInOutDAO;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_M_InOut;

import java.math.BigDecimal;

public class CustomerReturnLUTUConfigurationHandler
		extends AbstractDocumentLUTUConfigurationHandler<I_M_InOutLine>
{
	private final ILUTUConfigurationFactory lutuFactory = Services.get(ILUTUConfigurationFactory.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IHUPIItemProductBL piItemProductBL = Services.get(IHUPIItemProductBL.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

	@Override
	public I_M_HU_LUTU_Configuration getCurrentLUTUConfigurationOrNull(@NonNull final I_M_InOutLine documentLine)
	{
		final I_M_HU_LUTU_Configuration lutuConfiguration = documentLine.getM_HU_LUTU_Configuration();
		if (lutuConfiguration == null || lutuConfiguration.getM_HU_LUTU_Configuration_ID() <= 0)
		{
			return null;
		}

		return lutuConfiguration;
	}

	@Override
	public void setCurrentLUTUConfiguration(final I_M_InOutLine documentLine, final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		documentLine.setM_HU_LUTU_Configuration(lutuConfiguration);
	}

	@Override
	public I_M_HU_LUTU_Configuration createNewLUTUConfiguration(final I_M_InOutLine documentLine)
	{
		final I_M_HU_PI_Item_Product tuPIItemProduct = getM_HU_PI_Item_Product(documentLine);
		final ProductId cuProductId = ProductId.ofRepoId(documentLine.getM_Product_ID());
		final UomId cuUOMId = UomId.ofRepoId(documentLine.getC_UOM_ID());

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(documentLine.getM_InOut().getC_BPartner_ID());
		final I_M_HU_LUTU_Configuration lutuConfiguration = lutuFactory.createLUTUConfiguration(
				tuPIItemProduct,
				cuProductId,
				cuUOMId,
				bpartnerId,
				false); // noLUForVirtualTU == false => allow placing the CU (e.g. a packing material product) directly on the LU

		//
		// Update LU/TU configuration
		updateLUTUConfigurationFromDocumentLine(lutuConfiguration, documentLine);

		// NOTE: don't save it

		return lutuConfiguration;
	}

	@Override
	public void updateLUTUConfigurationFromDocumentLine(@NonNull final I_M_HU_LUTU_Configuration lutuConfiguration, @NonNull final I_M_InOutLine documentLine)
	{
		final I_M_InOut customerReturn = documentLine.getM_InOut();
		//
		// Set BPartner / Location to be used
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(customerReturn.getC_BPartner_ID());
		final int bpartnerLocationId = customerReturn.getC_BPartner_Location_ID();
		lutuConfiguration.setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
		lutuConfiguration.setC_BPartner_Location_ID(bpartnerLocationId);

		//
		// Set Locator
		final WarehouseId warehouseId = WarehouseId.ofRepoId(customerReturn.getM_Warehouse_ID());
		final LocatorId locatorId = warehouseBL.getOrCreateDefaultLocatorId(warehouseId);
		lutuConfiguration.setM_Locator_ID(locatorId.getRepoId());

		//
		// Set HUStatus=Planning because receipt schedules are always about planning
		lutuConfiguration.setHUStatus(X_M_HU.HUSTATUS_Planning);

		lutuConfiguration.setQtyLU(BigDecimal.ONE);
		lutuConfiguration.setIsInfiniteQtyLU(false);

		lutuConfiguration.setQtyTU(documentLine.getQtyEnteredTU().signum() == 0 ? BigDecimal.ONE: documentLine.getQtyEnteredTU());
		lutuConfiguration.setIsInfiniteQtyTU(false);

		lutuConfiguration.setQtyCUsPerTU(documentLine.getMovementQty());
		lutuConfiguration.setIsInfiniteQtyCU(false);
	}

	@Override
	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product(@NonNull final I_M_InOutLine inOutLine)
	{
		final HUPIItemProductId piItemProductId = HUPIItemProductId.ofRepoIdOrNone(inOutLine.getM_HU_PI_Item_Product_ID());
		return piItemProductBL.getRecordById(piItemProductId);
	}

	@Override
	public void save(@NonNull final I_M_InOutLine documentLine)
	{
		inOutDAO.save(documentLine);
	}
}
