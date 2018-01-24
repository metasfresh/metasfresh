package de.metas.ui.web.order.sales.purchasePlanning.view;

import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;

import de.metas.purchasecandidate.AvailabilityCheck.AvailabilityResult.Type;
import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.AccessLevel;
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
		return new PurchaseRowId(
				salesOrderLineId,
				-1, // vendorBPartnerId
				null, // availabilityType
				null, // availabilityDistinguisher
				null);  // documentId
	}

	public static PurchaseRowId lineId(final int salesOrderLineId, final int vendorBPartnerId)
	{
		return new PurchaseRowId(
				salesOrderLineId,
				vendorBPartnerId,
				null, // availabilityType
				null, // availabilityDistinguisher
				null); // documentId
	}

	public PurchaseRowId withAvailability(
			@NonNull final Type availabilityType,
			@NonNull final String availabilityDistinguisher)
	{
		Check.errorUnless(this.isLineRowId(),
				"The method withAvailabilityId may only be invoked on a line row id; this={}", this);

		return new PurchaseRowId(
				salesOrderLineId,
				vendorBPartnerId,
				availabilityType,
				availabilityDistinguisher,
				null); // documentId
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

	private static final PurchaseRowId fromJson(final String json, final DocumentId documentId)
	{
		final List<String> parts = PARTS_SPLITTER.splitToList(json);
		final int partsCount = parts.size();
		if (partsCount < 1 || partsCount == 3 || partsCount > 4)
		{
			throw new AdempiereException("Invalid format: " + json);
		}

		try
		{
			final int salesOrderLineId = Integer.parseInt(parts.get(0));
			final int vendorBPartnerId = partsCount >= 2 ? Integer.parseInt(parts.get(1)) : -1;
			final Type availabilityType = partsCount >= 3 ? Type.valueOf(parts.get(2)) : null;
			final String availabilityDistinguisher = partsCount >= 4 ? parts.get(3) : null;

			return new PurchaseRowId(
					salesOrderLineId,
					vendorBPartnerId,
					availabilityType,
					availabilityDistinguisher,
					documentId);
		}
		catch (Exception ex)
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

	private PurchaseRowId(
			final int salesOrderLineId,
			final int vendorBPartnerId,
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
		this.vendorBPartnerId = vendorBPartnerId > 0 ? vendorBPartnerId : -1;
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
			if (vendorBPartnerId > 0)
			{
				sb.append(PARTS_SEPARATOR);
				sb.append(vendorBPartnerId);
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
			return new PurchaseRowId(salesOrderLineId,
					-1, // vendorBPartnerId
					null, // availabilityType
					null, // availabilityDistinguisher
					null); // documentId
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
			return new PurchaseRowId(salesOrderLineId, vendorBPartnerId,
					null, // availabilityType
					null, // availabilityDistinguisher
					null); // documentId
		}
	}

	public boolean isGroupRowId()
	{
		return vendorBPartnerId <= 0;
	}

	public boolean isLineRowId()
	{
		return vendorBPartnerId > 0 && availabilityType == null;
	}

	public boolean isAvailabilityRowId()
	{
		return availabilityType != null;
	}
}
