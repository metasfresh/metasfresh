package de.metas.inventory.model.interceptor;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.model.ModelValidator;
import org.compiere.model.X_C_DocType;

import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.event.IEventBusFactory;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.inventory.IInventoryDAO;
import de.metas.inventory.event.InventoryUserNotificationsProducer;
import de.metas.util.Services;

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

@Validator(I_M_Inventory.class)
public class M_Inventory
{
	final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	final IInventoryDAO inventoryDAO = Services.get(IInventoryDAO.class);
	final IMsgBL msgBL = Services.get(IMsgBL.class);

	public static final String MSG_NOT_ALL_LINES_COUNTED = "de.metas.inventory.model.interceptor.NotAllLinesCounted";

	@Init
	public void onInit()
	{
		// Setup event bus topics on which swing client notification listener shall subscribe
		Services.get(IEventBusFactory.class).addAvailableUserNotificationsTopic(InventoryUserNotificationsProducer.EVENTBUS_TOPIC);
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_COMPLETE)
	public void onlyCompleteIfCounted(final I_M_Inventory inventory)
	{
		final int phisicalInventoryDocTypeId = getPhysicalInventoryDocTypeId(inventory);

		if (phisicalInventoryDocTypeId != inventory.getC_DocType_ID())
		{
			// nothing to do
			return;
		}

		final boolean allLinesCounted = inventoryDAO.retrieveLinesForInventoryId(inventory.getM_Inventory_ID())
				.stream()
				.allMatch(I_M_InventoryLine::isCounted);

		if (!allLinesCounted)
		{
			final ITranslatableString msgNotAllLinesCounted = msgBL.getTranslatableMsgText(MSG_NOT_ALL_LINES_COUNTED);
			throw new AdempiereException(msgNotAllLinesCounted);
		}
	}

	private int getPhysicalInventoryDocTypeId(final I_M_Inventory inventory)
	{
		return docTypeDAO.getDocTypeId(DocTypeQuery.builder()
				.docBaseType(X_C_DocType.DOCBASETYPE_MaterialPhysicalInventory)
				.docSubType(DocTypeQuery.DOCSUBTYPE_NONE)
				.adClientId(inventory.getAD_Client_ID())
				.adOrgId(inventory.getAD_Org_ID())
				.build());

	}
}
