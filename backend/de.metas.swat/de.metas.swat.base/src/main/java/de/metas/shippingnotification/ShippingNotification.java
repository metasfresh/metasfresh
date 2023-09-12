/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.shippingnotification;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.document.DocTypeId;
import de.metas.document.engine.DocStatus;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.shipping.exception.ShipmentNotificationException;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode
@ToString
public class ShippingNotification
{
	@Nullable private ShippingNotificationId id;
	@NonNull private final OrgId orgId;
	@NonNull private final DocTypeId docTypeId;
	@NonNull private final BPartnerLocationId bpartnerAndLocationId;
	@NonNull private final BPartnerContactId contactId;
	private final int auctionId;
	@NonNull private final LocatorId locatorId;
	@NonNull private final OrderId orderId;
	@NonNull private final Instant physicalClearanceDate;
	@NonNull private final YearAndCalendarId harvestringYearId;
	@Nullable private final String poReference;
	@Nullable private final String description;

	@NonNull private final DocStatus docStatus;
	private boolean processed;
	@Nullable @Setter private ShippingNotificationId reversalId;

	private final ArrayList<ShippingNotificationLine> lines;

	@Builder(toBuilder = true)
	private ShippingNotification(
			@Nullable final ShippingNotificationId id,
			@NonNull final OrgId orgId,
			@NonNull final DocTypeId docTypeId,
			@NonNull final BPartnerLocationId bpartnerAndLocationId,
			@NonNull final BPartnerContactId contactId,
			final int auctionId,
			@NonNull final LocatorId locatorId,
			@NonNull final OrderId orderId,
			@NonNull final Instant physicalClearanceDate,
			@NonNull final YearAndCalendarId harvestringYearId,
			@Nullable final String poReference,
			@Nullable final String description,
			@NonNull final DocStatus docStatus,
			final boolean processed,
			@Nullable final List<ShippingNotificationLine> lines)
	{
		this.id = id;
		this.orgId = orgId;
		this.docTypeId = docTypeId;
		this.bpartnerAndLocationId = bpartnerAndLocationId;
		this.contactId = contactId;
		this.auctionId = auctionId;
		this.locatorId = locatorId;
		this.orderId = orderId;
		this.physicalClearanceDate = physicalClearanceDate;
		this.harvestringYearId = harvestringYearId;
		this.poReference = poReference;
		this.description = description;
		this.docStatus = docStatus;
		this.processed = processed;
		this.lines = lines != null ? new ArrayList<>(lines) : new ArrayList<>();
	}

	public void completeIt()
	{
		if (processed)
		{
			throw new ShipmentNotificationException("@Processed@=@Y@");
		}

		this.processed = true;
	}

	void markAsSaved(@NonNull final ShippingNotificationId id)
	{
		this.id = id;
	}

	public ImmutableList<ShippingNotificationLine> getLines() {return ImmutableList.copyOf(lines);}

	public ShippingNotification createReversal()
	{
		return toBuilder()
				.id(null)
				.docStatus(DocStatus.Drafted)
				.processed(false)
				.lines(lines.stream().map(ShippingNotificationLine::createReversal).collect(Collectors.toList()))
				.build();
	}
}
