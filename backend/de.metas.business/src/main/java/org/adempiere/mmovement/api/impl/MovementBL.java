package org.adempiere.mmovement.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.IProductActivityProvider;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mmovement.api.IMovementBL;
import org.adempiere.mmovement.api.IMovementDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Movement;
import org.compiere.model.I_M_MovementLine;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_C_DocType;

import java.math.BigDecimal;

public class MovementBL implements IMovementBL
{
	private final IMovementDAO movementDAO = Services.get(IMovementDAO.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);

	@Override
	public DocTypeId getDocTypeId(@NonNull final ClientAndOrgId clientAndOrgId)
	{
		return docTypeDAO.getDocTypeId(DocTypeQuery.builder()
				.docBaseType(X_C_DocType.DOCBASETYPE_MaterialMovement)
				.adClientId(clientAndOrgId.getClientId().getRepoId())
				.adOrgId(clientAndOrgId.getOrgId().getRepoId())
				.build());
	}

	@Override
	public I_C_UOM getC_UOM(final I_M_MovementLine movementLine)
	{
		return uomDAO.getById(getUomId(movementLine));
	}

	private UomId getUomId(final I_M_MovementLine movementLine)
	{
		return productBL.getStockUOMId(movementLine.getM_Product_ID());
	}

	@Override
	public Quantity getMovementQty(final I_M_MovementLine movementLine, final I_C_UOM uom)
	{
		final BigDecimal movementQtyValue = movementLine.getMovementQty();
		final I_C_UOM movementQtyUOM = getC_UOM(movementLine);
		final Quantity movementQty = new Quantity(movementQtyValue, movementQtyUOM);

		final int productId = movementLine.getM_Product_ID();
		final UOMConversionContext uomConversionCtx = UOMConversionContext.of(productId);

		return uomConversionBL.convertQuantityTo(movementQty, uomConversionCtx, uom);
	}

	@Override
	public void setMovementQty(final I_M_MovementLine movementLine, final Quantity movementQty)
	{
		final ProductId productId = ProductId.ofRepoId(movementLine.getM_Product_ID());
		final UomId stockingUomId = getUomId(movementLine);

		final Quantity movementQtyConv = uomConversionBL.convertQuantityTo(movementQty, productId, stockingUomId);

		movementLine.setMovementQty(movementQtyConv.toBigDecimal());
	}

	@Override
	public void setC_Activities(final I_M_MovementLine movementLine)
	{
		final ActivityId productActivityId = Services.get(IProductActivityProvider.class).retrieveActivityForAcct(
				ClientId.ofRepoId(movementLine.getAD_Client_ID()),
				OrgId.ofRepoId(movementLine.getAD_Org_ID()),
				ProductId.ofRepoId(movementLine.getM_Product_ID()));

		final I_M_Locator locatorFrom = warehouseDAO.getLocatorByRepoId(movementLine.getM_Locator_ID());
		final ActivityId activityFromId = getActivity(locatorFrom, productActivityId);

		movementLine.setC_ActivityFrom_ID(ActivityId.toRepoId(activityFromId));

		final I_M_Locator locatorTo = warehouseDAO.getLocatorByRepoId(movementLine.getM_LocatorTo_ID());
		final ActivityId activityToId = getActivity(locatorTo, productActivityId);

		movementLine.setC_Activity_ID(ActivityId.toRepoId(activityToId));

		InterfaceWrapperHelper.save(movementLine);
	}

	/**
	 * @return the activity from the warehouse, if it exists. Otherwise, return the defaultValue.
	 */
	private ActivityId getActivity(@NonNull final I_M_Locator locator, final ActivityId defaultActivityId)
	{
		final WarehouseId warehouseId = WarehouseId.ofRepoId(locator.getM_Warehouse_ID());
		final I_M_Warehouse warehouse = warehouseDAO.getById(warehouseId);

		final ActivityId warehouseActivityId = ActivityId.ofRepoIdOrNull(warehouse.getC_Activity_ID());
		return warehouseActivityId != null ? warehouseActivityId : defaultActivityId;
	}

	@Override
	public boolean isReversal(final I_M_Movement movement)
	{
		Check.assumeNotNull(movement, "movement not null");

		// Check if we have a counter-part reversal document
		final int reversalId = movement.getReversal_ID();
		if (reversalId <= 0)
		{
			return false;
		}

		// Make sure this is the actual reversal and not the original document which was reversed
		// i.e. this document was created after the reversal (so Reversal_ID is less than this document's ID)
		final int movementId = movement.getM_Movement_ID();
		//noinspection UnnecessaryLocalVariable
		final boolean reversal = movementId > reversalId;
		return reversal;
	}

	@Override
	public void complete(@NonNull final I_M_Movement movement)
	{
		documentBL.processEx(movement, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
	}

	@Override
	public void voidMovement(final I_M_Movement movement)
	{
		documentBL.processEx(movement, IDocument.ACTION_Void, IDocument.STATUS_Reversed);
	}

	@Override
	public void save(@NonNull final I_M_Movement movement)
	{
		movementDAO.save(movement);
	}

	@Override
	public void save(@NonNull final I_M_MovementLine movementLine)
	{
		movementDAO.save(movementLine);
	}
}
