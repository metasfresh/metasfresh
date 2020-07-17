/*
 * #%L
 * de.metas.swat.base
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

package de.metas.inoutcandidate.exportaudit;

import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.HashMap;
import java.util.Map;

@Data
public class ShipmentScheduleExportAudit
{
	private final String transactionId;

	private final Map<ShipmentScheduleId, ShipmentScheduleExportAuditItem> items;

	@Builder
	private ShipmentScheduleExportAudit(
			@NonNull final String transactionId,
			@NonNull @Singular final Map<ShipmentScheduleId, ShipmentScheduleExportAuditItem> items)
	{
		this.transactionId = transactionId;
		this.items = new HashMap<>(items);
	}

	public ShipmentScheduleExportAuditItem getItemById(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		return items.get(shipmentScheduleId);
	}

	public void addItem(@NonNull final ShipmentScheduleExportAuditItem auditRecord)
	{
		items.put(auditRecord.getShipmentScheduleId(), auditRecord);
	}
}
