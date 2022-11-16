/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.deliveryplanning;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.order.OrderId;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.jpedal.fonts.tt.Loca;

import javax.annotation.Nullable;
import java.time.LocalDate;

@Data
@Builder
public class DeliveryPlanning
{
	@NonNull DeliveryPlanningId id;

	@NonNull OrderId orderId;

	@NonNull ProductId productId;

	@NonNull BPartnerId partnerId;

	@NonNull BPartnerLocationId bPartnerLocationId;

	@Nullable
	ShipmentScheduleId shipmentScheduleId;

	@Nullable
	ReceiptScheduleId receiptScheduleId;

	@Nullable
	LocalDateAndOrgId plannedLoadingDate;

	@Nullable
	LocalDateAndOrgId actualLoadingDate;

	@Nullable
	LocalDateAndOrgId plannedDeliveryDate;

	@Nullable
	LocalDateAndOrgId actualDeliveryDate;

	@Nullable
	Quantity qtyOredered;

	@Nullable
	Quantity qtyTotalOpen;

	@Nullable
	Quantity actualLoadQty;

	@Nullable
	Quantity actualDeliveredQty;

}
