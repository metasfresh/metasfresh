package de.metas.material.event.pporder;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.document.engine.DocStatus;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MinMaxDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.eevolution.api.PPOrderAndBOMLineId;
import org.eevolution.model.I_PP_Order;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

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
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class PPOrderChangedEvent implements MaterialEvent
{
	public static final String TYPE = "PPOrderChangedEvent";

	@NonNull EventDescriptor eventDescriptor;

	@NonNull Instant newDatePromised;
	@NonNull Instant oldDatePromised;

	@NonNull PPOrder ppOrderAfterChanges;

	@NonNull BigDecimal oldQtyRequired;
	@NonNull BigDecimal newQtyRequired;

	@NonNull BigDecimal oldQtyDelivered;
	@NonNull BigDecimal newQtyDelivered;

	@NonNull DocStatus oldDocStatus;
	@NonNull DocStatus newDocStatus;

	@NonNull List<PPOrderLine> newPPOrderLines;
	@NonNull List<ChangedPPOrderLineDescriptor> ppOrderLineChanges;
	@NonNull List<DeletedPPOrderLineDescriptor> deletedPPOrderLines;

	@Builder
	@JsonCreator
	private PPOrderChangedEvent(
			@JsonProperty("eventDescriptor") @NonNull final EventDescriptor eventDescriptor,
			@JsonProperty("newDatePromised") @NonNull final Instant newDatePromised,
			@JsonProperty("oldDatePromised") @NonNull final Instant oldDatePromised,
			@JsonProperty("ppOrderAfterChanges") @NonNull final PPOrder ppOrderAfterChanges,
			@JsonProperty("oldQtyRequired") @NonNull final BigDecimal oldQtyRequired,
			@JsonProperty("newQtyRequired") @NonNull final BigDecimal newQtyRequired,
			@JsonProperty("oldQtyDelivered") @NonNull final BigDecimal oldQtyDelivered,
			@JsonProperty("newQtyDelivered") @NonNull final BigDecimal newQtyDelivered,
			@JsonProperty("oldDocStatus") @NonNull final DocStatus oldDocStatus,
			@JsonProperty("newDocStatus") @NonNull final DocStatus newDocStatus,
			@JsonProperty("ppOrderLineChanges") @Singular final List<ChangedPPOrderLineDescriptor> ppOrderLineChanges,
			@JsonProperty("deletedPPOrderLines") @Singular final List<DeletedPPOrderLineDescriptor> deletedPPOrderLines,
			@JsonProperty("newPPOrderLines") @Singular final List<PPOrderLine> newPPOrderLines)
	{
		Check.assumeGreaterThanZero(ppOrderAfterChanges.getPpOrderId(), "ppOrderAfterChanges shall be saved");

		this.eventDescriptor = eventDescriptor;
		this.newDatePromised = newDatePromised;
		this.oldDatePromised = oldDatePromised;
		this.ppOrderAfterChanges = ppOrderAfterChanges;
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

	public boolean isJustCompleted()
	{
		final DocStatus newDocStatus = getNewDocStatus();
		final DocStatus oldDocStatus = getOldDocStatus();

		return newDocStatus != null && newDocStatus.isCompleted()
				&& (oldDocStatus == null || oldDocStatus.isNotProcessed());
	}

	public int getPpOrderId()
	{
		return getPpOrderAfterChanges().getPpOrderId();
	}

	//
	//
	// --------
	//
	//

	@Value
	public static class ChangedPPOrderLineDescriptor
	{
		@NonNull PPOrderAndBOMLineId oldPPOrderLineId;

		@Nullable PPOrderAndBOMLineId newPPOrderLineId;

		ProductDescriptor productDescriptor;

		MinMaxDescriptor minMaxDescriptor;

		Instant issueOrReceiveDate;

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
				@JsonProperty("oldPPOrderLineId") @NonNull final PPOrderAndBOMLineId oldPPOrderLineId,
				@JsonProperty("newPPOrderLineId") @Nullable final PPOrderAndBOMLineId newPPOrderLineId,
				@JsonProperty("productDescriptor") @NonNull final ProductDescriptor productDescriptor,
				@JsonProperty("minMaxDescriptor") @Nullable final MinMaxDescriptor minMaxDescriptor,
				@JsonProperty("issueOrReceiveDate") @NonNull final Instant issueOrReceiveDate,
				@JsonProperty("oldQtyRequired") @NonNull final BigDecimal oldQtyRequired,
				@JsonProperty("newQtyRequired") @NonNull final BigDecimal newQtyRequired,
				@JsonProperty("oldQtyDelivered") @NonNull final BigDecimal oldQtyDelivered,
				@JsonProperty("newQtyDelivered") @NonNull final BigDecimal newQtyDelivered)
		{
			if (!oldPPOrderLineId.isSameOrderAs(newPPOrderLineId))
			{
				throw new AdempiereException("Old and New lines shall be part of the same order");
			}
			this.oldPPOrderLineId = oldPPOrderLineId;
			this.newPPOrderLineId = newPPOrderLineId;
			this.productDescriptor = productDescriptor;
			this.minMaxDescriptor = minMaxDescriptor;
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
		public static DeletedPPOrderLineDescriptor ofPPOrderLine(@NonNull final PPOrderLine ppOrderLine)
		{
			final PPOrderLineData ppOrderLineData = ppOrderLine.getPpOrderLineData();
			return DeletedPPOrderLineDescriptor.builder()
					.issueOrReceiveDate(ppOrderLineData.getIssueOrReceiveDate())
					.ppOrderLineId(ppOrderLine.getPpOrderLineId())
					.productDescriptor(ppOrderLineData.getProductDescriptor())
					.qtyRequired(ppOrderLineData.getQtyRequired())
					.qtyDelivered(ppOrderLineData.getQtyDelivered())
					.build();
		}

		int ppOrderLineId;

		ProductDescriptor productDescriptor;
		Instant issueOrReceiveDate;
		BigDecimal qtyRequired;
		BigDecimal qtyDelivered;

		@JsonCreator
		private DeletedPPOrderLineDescriptor(
				@JsonProperty("ppOrderLineId") final int ppOrderLineId,
				@JsonProperty("productDescriptor") @NonNull final ProductDescriptor productDescriptor,
				@JsonProperty("issueOrReceiveDate") @NonNull final Instant issueOrReceiveDate,
				@JsonProperty("qtyRequired") @NonNull final BigDecimal qtyRequired,
				@JsonProperty("qtyDelivered") @NonNull final BigDecimal qtyDelivered)
		{
			this.ppOrderLineId = Check.assumeGreaterThanZero(ppOrderLineId, "ppOrderLineId");
			this.productDescriptor = productDescriptor;
			this.issueOrReceiveDate = issueOrReceiveDate;
			this.qtyRequired = qtyRequired;
			this.qtyDelivered = qtyDelivered;
		}
	}

	@Nullable
	@Override
	public TableRecordReference getSourceTableReference()
	{
		return TableRecordReference.ofNullable(I_PP_Order.Table_Name, ppOrderAfterChanges.getPpOrderId());
	}

	@Override
	public String getEventName() {return TYPE;}
}
