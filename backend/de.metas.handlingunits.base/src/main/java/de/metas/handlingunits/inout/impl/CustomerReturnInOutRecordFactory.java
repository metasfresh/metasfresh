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

package de.metas.handlingunits.inout.impl;

import de.metas.handlingunits.model.I_M_InOut;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.inout.IInOutDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.springframework.stereotype.Repository;

import static org.compiere.model.X_M_InOut.MOVEMENTTYPE_CustomerReturns;
import static org.compiere.util.TimeUtil.asTimestamp;

@Repository
public class CustomerReturnInOutRecordFactory
{
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);


	public I_M_InOut createCustomerReturnHeader(@NonNull final CreateCustomerReturnHeaderReq request)
	{
		final I_M_InOut inOut = InterfaceWrapperHelper.newInstance(I_M_InOut.class);
		inOut.setAD_Org_ID(request.getOrgId().getRepoId());

		ReturnsInOutHeaderFiller.newInstance()
				.setMovementDate(asTimestamp(request.getMovementDate()))
				.setDateReceived(asTimestamp(request.getDateReceived()))
				.setMovementType(MOVEMENTTYPE_CustomerReturns)
				.setReturnsDocTypeIdProvider(request.getReturnDocTypeIdProvider())
				.setBPartnerId(request.getBPartnerLocationId().getBpartnerId().getRepoId())
				.setBPartnerLocationId(request.getBPartnerLocationId().getRepoId())
				.setWarehouseId(request.getWarehouseId().getRepoId())
				.setOrder(request.getOrder())
				.setExternalId(request.getExternalId())
				.setExternalResourceURL(request.getExternalResourceURL())
				.fill(inOut);

		InterfaceWrapperHelper.saveRecord(inOut);

		return inOut;
	}

	public I_M_InOutLine createReturnLine(@NonNull final CreateCustomerReturnLineReq request)
	{
		final I_M_InOut returnHeader = inOutDAO.getById(request.getHeaderId(), I_M_InOut.class);

		final I_M_InOutLine returnLine = InterfaceWrapperHelper.newInstance(I_M_InOutLine.class);

		returnLine.setAD_Org_ID(returnHeader.getAD_Org_ID());
		returnLine.setM_InOut_ID(request.getHeaderId().getRepoId());

		returnLine.setM_Product_ID(request.getProductId().getRepoId());
		returnLine.setC_UOM_ID(request.getCommonUOMId().getRepoId());

		returnLine.setMovementQty(request.getMovementQty().toBigDecimal());
		returnLine.setQtyEntered(request.getQtyEntered().toBigDecimal());

		final WarehouseId warehouseId = WarehouseId.ofRepoId(returnHeader.getM_Warehouse_ID());
		returnLine.setM_Locator_ID(warehouseBL.getDefaultLocatorId(warehouseId).getRepoId());

		if (request.getAttributeSetInstanceId() != null)
		{
			returnLine.setM_AttributeSetInstance_ID(request.getAttributeSetInstanceId().getRepoId());
		}

		if (request.getHupiItemProductId() != null && request.getQtyTU() != null)
		{
			returnLine.setQtyEnteredTU(request.getQtyTU());
			returnLine.setM_HU_PI_Item_Product_ID(request.getHupiItemProductId().getRepoId());
		}

		if (request.getOriginShipmentLineId() != null)
		{
			returnLine.setReturn_Origin_InOutLine_ID(request.getOriginShipmentLineId().getRepoId());
		}

		InterfaceWrapperHelper.save(returnLine);

		return returnLine;
	}
}
