package de.metas.shippingnotification;

import de.metas.acct.api.AcctSchemaId;
import de.metas.bpartner.BPartnerId;
import de.metas.costing.CostElementId;
import de.metas.costrevaluation.CostRevaluationId;
import de.metas.document.engine.DocStatus;
import de.metas.order.OrderId;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;

import java.time.Instant;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
@Builder
public class ShippingNotification
{
	@NonNull ShippingNotificationId shippingNotificationId;
	@NonNull WarehouseId warehouseId;
	@NonNull BPartnerId bpartnerId;
	@NonNull OrderId orderId;
	@NonNull OrgId orgId;
	@NonNull Instant physicalClearanceDate;

	@NonNull DocStatus docStatus;
}
