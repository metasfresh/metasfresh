package de.metas.material.event.pporder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * metasfresh-material-event
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

@Value
public class PPOrderChangedEvent implements MaterialEvent
{
	public static final String TYPE = "PPOrderQtyChangedEvent";

	private final EventDescriptor eventDescriptor;

	Date newDatePromised;
	Date oldDatePromised;

	ProductDescriptor productDescriptor;

	int ppOrderId;

	BigDecimal oldQtyRequired;
	BigDecimal newQtyRequired;

	BigDecimal oldQtyDelivered;
	BigDecimal newQtyDelivered;

	String oldDocStatus;
	String newDocStatus;

	List<ChangedPPOrderLineDescriptor> ppOrderLineChanges;
	List<DeletedPPOrderLineDescriptor> deletedPPOrderLines;
	List<PPOrderLine> newPPOrderLines;

	@Builder
	@JsonCreator
	private PPOrderChangedEvent(
			@JsonProperty("eventDescriptor") @NonNull final EventDescriptor eventDescriptor,
			@JsonProperty("newDatePromised") @NonNull final Date newDatePromised,
			@JsonProperty("oldDatePromised") @NonNull final Date oldDatePromised,
			@JsonProperty("productDescriptor") @NonNull final ProductDescriptor productDescriptor,
			@JsonProperty("ppOrderId") final int ppOrderId,
			@JsonProperty("oldQtyRequired") @NonNull final BigDecimal oldQtyRequired,
			@JsonProperty("newQtyRequired") @NonNull final BigDecimal newQtyRequired,
			@JsonProperty("oldQtyDelivered") @NonNull final BigDecimal oldQtyDelivered,
			@JsonProperty("newQtyDelivered") @NonNull final BigDecimal newQtyDelivered,
			@JsonProperty("oldDocStatus") @NonNull final String oldDocStatus,
			@JsonProperty("newDocStatus") @NonNull final String newDocStatus,
			@JsonProperty("ppOrderLineChanges") @Singular final List<ChangedPPOrderLineDescriptor> ppOrderLineChanges,
			@JsonProperty("deletedPPOrderLines") @Singular final List<DeletedPPOrderLineDescriptor> deletedPPOrderLines,
			@JsonProperty("newPPOrderLines") @Singular final List<PPOrderLine> newPPOrderLines)
	{
		this.eventDescriptor = eventDescriptor;
		this.newDatePromised = newDatePromised;
		this.oldDatePromised = oldDatePromised;
		this.productDescriptor = productDescriptor;
		this.ppOrderId = Check.assumeGreaterThanZero(ppOrderId, "ppOrderId");
		this.oldQtyRequired = oldQtyRequired;
		this.newQtyRequired = newQtyRequired;
		this.oldQtyDelivered = oldQtyDelivered;
		this.newQtyDelivered = newQtyDelivered;
		this.oldDocStatus = oldDocStatus;
		this.newDocStatus = newDocStatus;
		this.ppOrderLineChanges = ppOrderLineChanges;
		this.deletedPPOrderLines = deletedPPOrderLines;
		this.newPPOrderLines = newPPOrderLines;
	}

	@Value
	public static class ChangedPPOrderLineDescriptor
	{
		int oldPPOrderLineId;

		int newPPOrderLineId;

		ProductDescriptor productDescriptor;

		Date issueOrReceiveDate;

		BigDecimal oldQtyRequired;

		BigDecimal newQtyRequired;

		BigDecimal oldQtyDelivered;

		BigDecimal newQtyDelivered;

		public BigDecimal computeOpenQtyDelta()
		{
			final BigDecimal oldOpenQty = oldQtyRequired.subtract(oldQtyDelivered);
			final BigDecimal newOpenQty = newQtyRequired.subtract(newQtyDelivered);

			return newOpenQty.subtract(oldOpenQty);
		}

		@Builder
		@JsonCreator
		private ChangedPPOrderLineDescriptor(
				@JsonProperty("oldPPOrderLineId") final int oldPPOrderLineId,
				@JsonProperty("newPPOrderLineId") final int newPPOrderLineId,
				@JsonProperty("productDescriptor") @NonNull final ProductDescriptor productDescriptor,
				@JsonProperty("issueOrReceiveDate") @NonNull final Date issueOrReceiveDate,
				@JsonProperty("oldQtyRequired") @NonNull final BigDecimal oldQtyRequired,
				@JsonProperty("newQtyRequired") @NonNull final BigDecimal newQtyRequired,
				@JsonProperty("oldQtyDelivered") @NonNull final BigDecimal oldQtyDelivered,
				@JsonProperty("newQtyDelivered") @NonNull final BigDecimal newQtyDelivered)
		{
			this.oldPPOrderLineId = Check.assumeGreaterThanZero(oldPPOrderLineId,"oldPPOrderLineId");
			this.newPPOrderLineId = Check.assumeGreaterThanZero(newPPOrderLineId,"newPPOrderLineId");
			this.productDescriptor = productDescriptor;
			this.issueOrReceiveDate = issueOrReceiveDate;
			this.oldQtyRequired = oldQtyRequired;
			this.newQtyRequired = newQtyRequired;
			this.oldQtyDelivered = oldQtyDelivered;
			this.newQtyDelivered = newQtyDelivered;
		}
	}

	@Value
	@Builder
	public static class DeletedPPOrderLineDescriptor
	{
		public static DeletedPPOrderLineDescriptor ofPPOrderLine(
				@NonNull final PPOrderLine ppOrderLine)
		{
			return DeletedPPOrderLineDescriptor.builder()
					.issueOrReceiveDate(ppOrderLine.getIssueOrReceiveDate())
					.ppOrderLineId(ppOrderLine.getPpOrderLineId())
					.productDescriptor(ppOrderLine.getProductDescriptor())
					.qtyRequired(ppOrderLine.getQtyRequired())
					.qtyDelivered(ppOrderLine.getQtyDelivered())
					.build();
		}

		int ppOrderLineId;

		ProductDescriptor productDescriptor;
		Date issueOrReceiveDate;
		BigDecimal qtyRequired;
		BigDecimal qtyDelivered;

		@JsonCreator
		private DeletedPPOrderLineDescriptor(
				@JsonProperty("ppOrderLineId") final int ppOrderLineId,
				@JsonProperty("productDescriptor") @NonNull final ProductDescriptor productDescriptor,
				@JsonProperty("issueOrReceiveDate") @NonNull final Date issueOrReceiveDate,
				@JsonProperty("qtyRequired") @NonNull final BigDecimal qtyRequired,
				@JsonProperty("qtyDelivered") @NonNull final BigDecimal qtyDelivered)
		{
			this.ppOrderLineId = Check.assumeGreaterThanZero(ppOrderLineId,"ppOrderLineId");
			this.productDescriptor = productDescriptor;
			this.issueOrReceiveDate = issueOrReceiveDate;
			this.qtyRequired = qtyRequired;
			this.qtyDelivered = qtyDelivered;
		}

	}
}
