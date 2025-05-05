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

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.exportaudit.APIExportStatus;
import de.metas.order.OrderAndLineId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.shipping.ShipperId;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;

import javax.annotation.Nullable;
import java.time.LocalDateTime;

@Data
@Builder
public class ShipmentSchedule
{
	@NonNull
	private final ShipmentScheduleId id;

	@NonNull
	private final OrgId orgId;

	@NonNull
	private final BPartnerId shipBPartnerId;

	@NonNull
	private final BPartnerLocationId shipLocationId;

	@Nullable
	private final BPartnerContactId shipContactId;

	@Nullable
	private final BPartnerId billBPartnerId;

	@Nullable
	private final BPartnerLocationId billLocationId;

	@Nullable
	private final BPartnerContactId billContactId;

	@Nullable
	private final OrderAndLineId orderAndLineId;

	@Nullable
	private final LocalDateTime dateOrdered;

	private int numberOfItemsForSameShipment;

	@NonNull
	private final ProductId productId;

	@NonNull
	private final Quantity quantityToDeliver;

	@NonNull
	private final Quantity orderedQuantity;

	@NonNull
	private final Quantity deliveredQuantity;

	@Nullable
	private final AttributeSetInstanceId attributeSetInstanceId;

	@NonNull
	private APIExportStatus exportStatus;

	@Nullable
	private final ShipperId shipperId;

	private final boolean isProcessed;
	private final boolean isClosed;
	private final boolean isDeliveryStop;

	public boolean hasAttributes(
			@NonNull final ImmutableSet<AttributeSetInstanceId> targetAsiIds,
			@NonNull final IAttributeDAO attributeDAO)
	{
		final ImmutableSet<AttributeSetInstanceId> nonNullTargetAsiIds = targetAsiIds
				.stream()
				.filter(asiId -> !AttributeSetInstanceId.NONE.equals(asiId))
				.collect(ImmutableSet.toImmutableSet());

		if (nonNullTargetAsiIds.isEmpty())
		{
			return true; // targetAsiIds was effectively empty, so we return true
		}

		if (getAttributeSetInstanceId() == null)
		{
			return false;
		}

		final ImmutableAttributeSet shipmentScheduleAsi = attributeDAO.getImmutableAttributeSetById(getAttributeSetInstanceId());

		return nonNullTargetAsiIds.stream()
				.map(attributeDAO::getImmutableAttributeSetById)
				.anyMatch(shipmentScheduleAsi::containsAttributeValues);
	}
}
