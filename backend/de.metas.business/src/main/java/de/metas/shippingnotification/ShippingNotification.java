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
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.document.DocTypeId;
import de.metas.document.dimension.Dimension;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.location.DocumentLocation;
import de.metas.inout.ShipmentScheduleId;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.shipping.exception.ShipmentNotificationException;
import de.metas.util.Check;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.lang.SeqNoProvider;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode
@ToString
public class ShippingNotification
{
	@Nullable private ShippingNotificationId id;

	@NonNull private final ClientAndOrgId clientAndOrgId;
	@NonNull private final DocTypeId docTypeId;
	@Nullable @Setter private String documentNo;
	@NonNull private final BPartnerLocationId bpartnerAndLocationId;
	@Nullable private final BPartnerContactId contactId;
	private final int auctionId;
	@NonNull private final LocatorId locatorId;
	@NonNull private final OrderId salesOrderId;
	@NonNull private final Instant dateAcct;
	@NonNull private final Instant physicalClearanceDate;
	@Nullable private final YearAndCalendarId harvestingYearId;
	@Nullable private final String poReference;
	@Nullable private final String description;
	@NonNull @Setter private DocStatus docStatus;
	@Nullable private String docAction;
	@NonNull private final ArrayList<ShippingNotificationLine> lines;
	private boolean processed;
	@Nullable @Setter private ShippingNotificationId reversalId;
	@Nullable private String bpaddress;

	private static final String REVERSE_INDICATOR = "^";

	@Builder(toBuilder = true)
	private ShippingNotification(
			@Nullable final ShippingNotificationId id,
			@NonNull final ClientAndOrgId clientAndOrgId,
			@NonNull final DocTypeId docTypeId,
			@Nullable final String documentNo,
			@NonNull final BPartnerLocationId bpartnerAndLocationId,
			@Nullable final BPartnerContactId contactId,
			final int auctionId,
			@NonNull final LocatorId locatorId,
			@NonNull final OrderId salesOrderId,
			final @NonNull Instant dateAcct, @NonNull final Instant physicalClearanceDate,
			@Nullable final YearAndCalendarId harvestingYearId,
			@Nullable final String poReference,
			@Nullable final String description,
			@NonNull final DocStatus docStatus,
			@Nullable final String docAction,
			final boolean processed,
			@Nullable ShippingNotificationId reversalId,
			@Nullable final List<ShippingNotificationLine> lines)
	{
		this.id = id;
		this.clientAndOrgId = clientAndOrgId;
		this.docTypeId = docTypeId;
		this.documentNo = documentNo;
		this.bpartnerAndLocationId = bpartnerAndLocationId;
		this.contactId = contactId;
		this.auctionId = auctionId;
		this.locatorId = locatorId;
		this.salesOrderId = salesOrderId;
		this.dateAcct = dateAcct;
		this.physicalClearanceDate = physicalClearanceDate;
		this.harvestingYearId = harvestingYearId;
		this.poReference = poReference;
		this.description = description;
		this.docStatus = docStatus;
		this.docAction = docAction;
		this.processed = processed;
		this.reversalId = reversalId;
		this.lines = lines != null ? new ArrayList<>(lines) : new ArrayList<>();
	}

	public ShippingNotificationId getIdNotNull() {return Check.assumeNotNull(id, "Shipment notification is expected to be saved at this point: {}", this);}

	public ClientId getClientId() {return clientAndOrgId.getClientId();}

	public OrgId getOrgId() {return clientAndOrgId.getOrgId();}

	public BPartnerId getBPartnerId()
	{
		return bpartnerAndLocationId.getBpartnerId();
	}

	public DocumentLocation getLocation()
	{
		return DocumentLocation.builder()
				.bpartnerId(bpartnerAndLocationId.getBpartnerId())
				.bpartnerLocationId(bpartnerAndLocationId)
				.contactId(contactId)
				.build();
	}

	public void completeIt()
	{
		if (processed)
		{
			throw new ShipmentNotificationException("@Processed@=@Y@");
		}

		this.processed = true;

		if (isReversal())
		{
			this.docStatus = DocStatus.Reversed;
			this.docAction = IDocument.ACTION_None;
		}
		else
		{
			this.docStatus = DocStatus.Completed;
			this.docAction = IDocument.ACTION_Reverse_Correct;
		}
	}

	public void updateBPAddress(@NonNull final Function<DocumentLocation, String> addressRenderer)
	{
		this.bpaddress = addressRenderer.apply(DocumentLocation.builder()
				.bpartnerId(bpartnerAndLocationId.getBpartnerId())
				.bpartnerLocationId(bpartnerAndLocationId)
				.contactId(contactId)
				.build());
	}

	void markAsSaved(@NonNull final ShippingNotificationId id)
	{
		this.id = id;
	}

	public ImmutableList<ShippingNotificationLine> getLines()
	{
		return ImmutableList.copyOf(lines);
	}

	public ShippingNotification createReversal()
	{
		return toBuilder()
				.id(null)
				.reversalId(getIdNotNull())
				.documentNo(documentNo != null ? documentNo + REVERSE_INDICATOR : null)
				.docStatus(DocStatus.Drafted)
				.docAction(IDocument.ACTION_Complete)
				.processed(false)
				.lines(lines.stream().map(ShippingNotificationLine::createReversal).collect(Collectors.toList()))
				.build();
	}

	public void renumberLines()
	{
		final SeqNoProvider lineNoProvider = SeqNoProvider.ofInt(10);
		lines.forEach(line -> line.setLine(lineNoProvider.getAndIncrement()));
	}

	public ImmutableSet<ShipmentScheduleId> getShipmentScheduleIds()
	{
		return lines.stream()
				.map(ShippingNotificationLine::getShipmentScheduleId)
				.collect(ImmutableSet.toImmutableSet());
	}

	public boolean isReversal() {return reversalId != null && id != null && id.getRepoId() > reversalId.getRepoId();}

	public Dimension getDimension()
	{
		return Dimension.builder()
				.salesOrderId(salesOrderId)
				.harvestingYearAndCalendarId(harvestingYearId)
				.build();
	}

	public Optional<ShippingNotificationLine> getLineBySalesOrderLineId(@NonNull final OrderAndLineId salesOrderAndLineId)
	{
		return CollectionUtils.singleElementOrEmptyIfNotFound(lines, line -> OrderAndLineId.equals(line.getSalesOrderAndLineId(), salesOrderAndLineId));
	}
}
