package de.metas.handlingunits.inventory.impl;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import org.compiere.model.X_C_DocType;

import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.handlingunits.inventory.IHUInventoryBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Inventory;
import de.metas.product.acct.api.ActivityId;
import de.metas.util.Services;

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
	public List<I_M_Inventory> moveToGarbage(
			final Collection<I_M_HU> husToDestroy,
			final Timestamp movementDate,
			final ActivityId activityId,
			final String description,
			final boolean isCompleteInventory,
			final boolean isCreateMovement)
	{
		return HUInternalUseInventoryProducer.newInstance()
				.setMovementDate(movementDate)
				.setDocSubType(X_C_DocType.DOCSUBTYPE_InternalUseInventory)
				.addHUs(husToDestroy)
				.setActivityId(activityId)
				.setDescription(description)
				.setIsCompleteInventory(isCompleteInventory)
				.setIsCreateMovement(isCreateMovement)
				.createInventories();
	}

	@Override
	public boolean isMaterialDisposal(final I_M_Inventory inventory)
	{
		final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

		final DocTypeQuery query = DocTypeQuery.builder()
				.docBaseType(X_C_DocType.DOCBASETYPE_MaterialPhysicalInventory)
				.docSubType(X_C_DocType.DOCSUBTYPE_InternalUseInventory)
				.adClientId(inventory.getAD_Client_ID())
				.adOrgId(inventory.getAD_Org_ID())
				.build();

		final DocTypeId disposalDocTypeId = docTypeDAO.getDocTypeIdOrNull(query);

		return disposalDocTypeId != null && disposalDocTypeId.getRepoId() == inventory.getC_DocType_ID();
	}
}
