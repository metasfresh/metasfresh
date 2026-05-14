/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.organization.interceptors;

import de.metas.i18n.AdMessageKey;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

/**
 * Forward-guard for the invariant
 * "any warehouse referenced by {@code AD_OrgInfo.DropShip_Warehouse_ID} must have
 * {@code M_Warehouse.IsDropShipWarehouse='Y'}".
 * <p>
 * Fires on BEFORE_NEW / BEFORE_CHANGE of the {@code DropShip_Warehouse_ID} column. Loads the
 * chosen warehouse via {@link IWarehouseDAO} and throws a user-validation error when the
 * warehouse is not flagged as a dropship warehouse. Clearing the field (setting it to 0/NULL) is
 * always allowed.
 * <p>
 * Paired with:
 * <ul>
 * <li>backfill migration {@code 5802700_sys_backfill_IsDropShipWarehouse_from_AD_OrgInfo.sql}
 *     — flags warehouses that were already assigned before this guard existed.</li>
 * <li>val-rule migration {@code 5802710_sys_AD_OrgInfo_DropShipWarehouse_ValRule.sql}
 *     — restricts the dropdown to flagged warehouses (UI-side).</li>
 * <li>message migration {@code 5802720_sys_DropShipWarehouse_NotFlagged_message.sql}
 *     — defines the user-visible error text (EN + DE).</li>
 * </ul>
 */
@Interceptor(I_AD_OrgInfo.class)
@Component
public class AD_OrgInfo_DropShipWarehouse
{
	private static final AdMessageKey MSG_NOT_FLAGGED = AdMessageKey.of("DropShipWarehouse_NotFlagged");

	@NonNull private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);

	@ModelChange(
			timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = I_AD_OrgInfo.COLUMNNAME_DropShip_Warehouse_ID)
	public void validateDropShipWarehouseIsFlagged(@NonNull final I_AD_OrgInfo orgInfoRecord)
	{
		final int warehouseRepoId = orgInfoRecord.getDropShip_Warehouse_ID();
		if (warehouseRepoId <= 0)
		{
			// Clearing the field is always allowed.
			return;
		}

		final I_M_Warehouse warehouse = warehouseDAO.getById(WarehouseId.ofRepoId(warehouseRepoId));
		if (warehouse == null || !warehouse.isDropShipWarehouse())
		{
			throw new AdempiereException(MSG_NOT_FLAGGED)
					.markAsUserValidationError();
		}
	}
}
