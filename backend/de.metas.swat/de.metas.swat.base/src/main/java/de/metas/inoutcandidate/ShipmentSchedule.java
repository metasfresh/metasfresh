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

package de.metas.inoutcandidate;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;

import javax.annotation.Nullable;

@Value
@Builder
public class ShipmentSchedule
{
	@NonNull
	ShipmentScheduleId id;

	@NonNull
	OrgId orgId;

	@NonNull
	BPartnerId bPartnerId;

	@NonNull
	BPartnerLocationId locationId;

	@Nullable
	BPartnerContactId contactId;

	@Nullable
	OrderId orderId;

	@NonNull
	ProductId productId;

	@NonNull
	Quantity quantityToDeliver;

	@Nullable
	AttributeSetInstanceId attributeSetInstanceId;
}
