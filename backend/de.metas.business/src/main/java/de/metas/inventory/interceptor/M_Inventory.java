package de.metas.inventory.interceptor;

import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.event.IEventBusFactory;
import de.metas.i18n.AdMessageKey;
import de.metas.inventory.AggregationType;
import de.metas.inventory.IInventoryDAO;
import de.metas.inventory.InventoryId;
import de.metas.inventory.event.InventoryUserNotificationsProducer;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

/*
 * #%L
 * de.metas.swat.base
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

@Interceptor(I_M_Inventory.class)
@Component
public class M_Inventory
{
	public static final AdMessageKey MSG_NOT_ALL_LINES_COUNTED = AdMessageKey.of("de.metas.inventory.interceptor.NotAllLinesCounted");

	@Init
	public void onInit()
	{
		// Setup event bus topics on which swing client notification listener shall subscribe
		Services.get(IEventBusFactory.class).addAvailableUserNotificationsTopic(InventoryUserNotificationsProducer.EVENTBUS_TOPIC);
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_COMPLETE)
	public void onlyCompleteIfCounted(final I_M_Inventory inventory)
	{
		if (!isPhysicalInventoryDocType(inventory))
		{
			return; // nothing to do
		}

		final IInventoryDAO inventoryDAO = Services.get(IInventoryDAO.class);

		final InventoryId inventoryId = InventoryId.ofRepoId(inventory.getM_Inventory_ID());
		final boolean allLinesCounted = inventoryDAO.retrieveLinesForInventoryId(inventoryId)
				.stream()
				.allMatch(I_M_InventoryLine::isCounted);

		if (!allLinesCounted)
		{
			throw new AdempiereException(MSG_NOT_ALL_LINES_COUNTED)
					.markAsUserValidationError();
		}
	}

	private boolean isPhysicalInventoryDocType(final I_M_Inventory inventoryRecord)
	{
		final DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(inventoryRecord.getC_DocType_ID());
		if (docTypeId == null)
		{
			return false;
		}

		final IDocTypeDAO docTypesRepo = Services.get(IDocTypeDAO.class);
		final DocBaseAndSubType docBaseAndSubType = docTypesRepo.getDocBaseAndSubTypeById(docTypeId);

		return AggregationType.MULTIPLE_HUS.getDocBaseAndSubType().equals(docBaseAndSubType)
				|| AggregationType.SINGLE_HU.getDocBaseAndSubType().equals(docBaseAndSubType);
	}
}
