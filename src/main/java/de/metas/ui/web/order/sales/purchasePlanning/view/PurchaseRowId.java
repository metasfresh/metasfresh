package de.metas.ui.web.order.sales.purchasePlanning.view;

import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;

import de.metas.purchasecandidate.availability.AvailabilityResult.Type;
import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * metasfresh-webui-api
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

@ToString
@EqualsAndHashCode
public final class PurchaseRowId
{
	public static PurchaseRowId groupId(final int salesOrderLineId)
	{
		return PurchaseRowId.builder()
				.salesOrderLineId(salesOrderLineId)
				.vendorBPartnerId(-1)
				.build();
	}

	public static PurchaseRowId lineId(
			final int salesOrderLineId,
			final int vendorBPartnerId,
			final int processedPurchaseCandidateId)
	{
		return PurchaseRowId.builder()
				.salesOrderLineId(salesOrderLineId)
				.vendorBPartnerId(vendorBPartnerId)
				.processedPurchaseCandidateId(processedPurchaseCandidateId)
				.build();
	}

	public PurchaseRowId withAvailability(
			@NonNull final Type availabilityType,
			@NonNull final String availabilityDistinguisher)
	{
		Check.errorUnless(this.isLineRowId(),
				"The method withAvailabilityId may only be invoked on a line row id; this={}", this);

		return PurchaseRowId.builder()
				.salesOrderLineId(salesOrderLineId)
				.vendorBPartnerId(vendorBPartnerId)
				.processedPurchaseCandidateId(processedPurchaseCandidateId)
				.availabilityType(availabilityType)
				.availabilityDistinguisher(availabilityDistinguisher)
				.build();
	}

	public static PurchaseRowId fromDocumentId(final DocumentId documentId)
	{
		return fromJson(documentId.toJson(), documentId);
	}

	public static final PurchaseRowId fromJson(final String json)
	{
		final DocumentId documentId = null;
		return fromJson(json, documentId);
	}

	private static final PurchaseRowId fromJson(
			@NonNull final String json,
			@Nullable final DocumentId documentId)
	{
		final List<String> parts = PARTS_SPLITTER.splitToList(json);
		final int partsCount = parts.size();
		if (partsCount < 1 || partsCount == 4 || partsCount > 5)
		{
			throw new AdempiereException("Invalid format: " + json);
		}

		try
		{
			final int salesOrderLineId = Integer.parseInt(parts.get(0));
			final int vendorBPartnerId = partsCount >= 2 ? Integer.parseInt(parts.get(1)) : -1;
			final int processedPurchaseCandidateId = partsCount >= 3 ? Integer.parseInt(parts.get(2)) : -1;
			final Type availabilityType = partsCount >= 4 ? Type.valueOf(parts.get(3)) : null;
			final String availabilityDistinguisher = partsCount >= 5 ? parts.get(4) : null;

			return PurchaseRowId.builder()
					.salesOrderLineId(salesOrderLineId)
					.vendorBPartnerId(vendorBPartnerId)
					.processedPurchaseCandidateId(processedPurchaseCandidateId)
					.availabilityType(availabilityType)
					.availabilityDistinguisher(availabilityDistinguisher).build();

		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Cannot convert '" + json + "' to " + PurchaseRowId.class, ex);
		}
	}

	/** Please make sure this splitter is not included in the enum values of {@link Type}. */
	@VisibleForTesting
	static final String PARTS_SEPARATOR = "-";

	private static final Splitter PARTS_SPLITTER = Splitter.on(PARTS_SEPARATOR).omitEmptyStrings();

	@Getter
	private final int salesOrderLineId;

	@Getter(AccessLevel.PACKAGE) // visible for testing
	private final int vendorBPartnerId;

	@Getter
	private final Type availabilityType;

	@Getter(AccessLevel.PACKAGE) // visible for testing
	private final String availabilityDistinguisher;

	private transient DocumentId _documentId; // lazy

	private final int processedPurchaseCandidateId;

	@Builder
	private PurchaseRowId(
			final int salesOrderLineId,
			final int vendorBPartnerId,
			final int processedPurchaseCandidateId,
			final Type availabilityType,
			final String availabilityDistinguisher,
			final DocumentId documentId)
	{
		Check.errorIf(salesOrderLineId <= 0, "The given salesOrderLineId={} may not be <= 0", salesOrderLineId);

		Check.errorIf(availabilityType == null ^ Check.isEmpty(availabilityDistinguisher, true),
				"The given availabilityType and availabilityDistinguisher need to be both either null/empty or both not-null/not-empty; availabilityType={}; availabilityDistinguisher={}",
				availabilityType, availabilityDistinguisher);

		Check.errorIf(availabilityDistinguisher != null && availabilityDistinguisher.contains(PARTS_SEPARATOR),
				"The given availabilityDistinguisher string may not contain the PARTS_SEPARATOR string; PARTS_SEPARATOR={};availabilityDistinguisher={}",
				PARTS_SEPARATOR, availabilityDistinguisher);

		this.salesOrderLineId = salesOrderLineId;
		this.vendorBPartnerId = vendorBPartnerId > 0 ? vendorBPartnerId : 0;
		this.processedPurchaseCandidateId = processedPurchaseCandidateId > 0 ? processedPurchaseCandidateId : 0;

		this.availabilityType = availabilityType;
		this.availabilityDistinguisher = availabilityDistinguisher;

		this._documentId = documentId;
	}

	public DocumentId toDocumentId()
	{
		if (_documentId == null)
		{
			final StringBuilder sb = new StringBuilder();
			sb.append(salesOrderLineId);
			if (vendorBPartnerId > 0 || processedPurchaseCandidateId > 0)
			{
				sb.append(PARTS_SEPARATOR);
				sb.append(vendorBPartnerId);

				sb.append(PARTS_SEPARATOR);
				sb.append(processedPurchaseCandidateId);
			}
			if (availabilityType != null)
			{
				sb.append(PARTS_SEPARATOR);
				sb.append(availabilityType.toString());
				sb.append(PARTS_SEPARATOR);
				sb.append(availabilityDistinguisher); // we verified in the constructor that it's not empty if availabilityType != null
			}
			_documentId = DocumentId.ofString(sb.toString());
		}
		return _documentId;
	}

	public PurchaseRowId toGroupRowId()
	{
		if (isGroupRowId())
		{
			return this;
		}
		else
		{
			return PurchaseRowId.builder()
					.salesOrderLineId(salesOrderLineId).build();
		}
	}

	public PurchaseRowId toLineRowId()
	{
		if (isLineRowId())
		{
			return this;
		}
		else
		{
			return PurchaseRowId.builder()
					.salesOrderLineId(salesOrderLineId)
					.vendorBPartnerId(vendorBPartnerId)
					.processedPurchaseCandidateId(processedPurchaseCandidateId).build();
		}
	}

	public boolean isGroupRowId()
	{
		return (vendorBPartnerId <= 0 && processedPurchaseCandidateId <= 0);
	}

	public boolean isLineRowId()
	{
		return (vendorBPartnerId > 0 || processedPurchaseCandidateId > 0) && availabilityType == null;
	}

	public boolean isAvailabilityRowId()
	{
		return availabilityType != null;
	}

	public int getProcessedPurchaseCandidateId()
	{
		return processedPurchaseCandidateId;
	}
}
