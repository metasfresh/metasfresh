package de.metas.handlingunits.inventory.impl;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.model.X_C_DocType;

import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.inventory.IHUInventoryBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Inventory;
import de.metas.inventory.IInventoryDAO;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class HUInventoryBL implements IHUInventoryBL
{
	@Override
	public List<I_M_Inventory> moveToGarbage(final Collection<I_M_HU> husToDestroy, final Timestamp movementDate)
	{
		return HUInternalUseInventoryProducer.newInstance()
				.setMovementDate(movementDate)
				.setDocSubType(X_C_DocType.DOCSUBTYPE_MaterialDisposal)
				.addHUs(husToDestroy)
				.createInventories();
	}

	@Override
	public boolean isMaterialDisposal(final I_M_Inventory inventory)
	{
		final int disposalDocTypeId = Services.get(IDocTypeDAO.class)
				.getDocTypeIdOrNull(DocTypeQuery.builder()
						.docBaseType(X_C_DocType.DOCBASETYPE_MaterialPhysicalInventory)
						.docSubType(X_C_DocType.DOCSUBTYPE_MaterialDisposal)
						.adClientId(inventory.getAD_Client_ID())
						.adOrgId(inventory.getAD_Org_ID())
						.build());

		return disposalDocTypeId > 0 && disposalDocTypeId == inventory.getC_DocType_ID();
	}

	@Override
	public void assignHU(final I_M_InventoryLine inventoryLine, final I_M_HU topLevelHU)
	{
		final IHUAssignmentBL huAssignmentBL = Services.get(IHUAssignmentBL.class);
		huAssignmentBL.assignHU(inventoryLine, topLevelHU, ITrx.TRXNAME_ThreadInherited);
	}

	@Override
	public List<I_M_HU> getAssignedTopLevelHUs(final I_M_InventoryLine inventoryLine)
	{
		final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
		return huAssignmentDAO.retrieveTopLevelHUsForModel(inventoryLine);
	}

	@Override
	public List<I_M_HU> getAssignedTopLevelHUsByInventoryId(final int inventoryId)
	{
		// TODO: optimize it, avoid SQL N+1
		final IInventoryDAO inventoryDAO = Services.get(IInventoryDAO.class);
		return inventoryDAO.retrieveLinesForInventoryId(inventoryId)
				.stream()
				.flatMap(inventoryLine -> getAssignedTopLevelHUs(inventoryLine).stream())
				.collect(GuavaCollectors.toImmutableListExcludingDuplicates(I_M_HU::getM_HU_ID));
	}

}
