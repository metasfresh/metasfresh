package de.metas.ui.web.order.sales.purchasePlanning.view;

import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.apache.commons.lang3.RandomStringUtils;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;

import de.metas.bpartner.BPartnerId;
import de.metas.purchasecandidate.PurchaseDemandId;
import de.metas.purchasecandidate.availability.AvailabilityResult.Type;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.util.Check;
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
	public static PurchaseRowId groupId(@NonNull final PurchaseDemandId purchaseDemandId)
	{
		final DocumentId documentId = null;
		return new PurchaseRowId(purchaseDemandId, documentId);
	}

	public static PurchaseRowId lineId(
			@NonNull final PurchaseDemandId purchaseDemandId,
			@NonNull final BPartnerId vendorId,
			final boolean readonly)
	{
		final DocumentId documentId = null;
		return new PurchaseRowId(purchaseDemandId, vendorId, readonly, documentId);
	}

	public static PurchaseRowId availabilityDetailId(
			@NonNull final PurchaseDemandId purchaseDemandId,
			@NonNull final BPartnerId vendorId,
			@NonNull final Type availabilityType,
			@NonNull final String availabilityDistinguisher)
	{
		final DocumentId documentId = null;
		return new PurchaseRowId(purchaseDemandId, vendorId, availabilityType, availabilityDistinguisher, documentId);
	}

	public PurchaseRowId withAvailabilityAndRandomDistinguisher(@NonNull final Type availabilityType)
	{
		final String availabilityDistinguisher = RandomStringUtils.random(8, /* includeLetters */true, /* includeNumbers */true);
		return withAvailability(availabilityType, availabilityDistinguisher);
	}

	public PurchaseRowId withAvailability(
			@NonNull final Type availabilityType,
			@NonNull final String availabilityDistinguisher)
	{
		Check.errorUnless(this.isLineRowId(),
				"The method withAvailabilityId may only be invoked on a line row id; this={}", this);

		return availabilityDetailId(purchaseDemandId, vendorId, availabilityType, availabilityDistinguisher);
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

	/** Please make sure this splitter is not included in the enum values of {@link Type}. */
	@VisibleForTesting
	static final String PARTS_SEPARATOR = "-";

	private static final Splitter PARTS_SPLITTER = Splitter.on(PARTS_SEPARATOR).omitEmptyStrings();

	@Getter
	private final PurchaseRowType type;

	@Getter
	private final PurchaseDemandId purchaseDemandId;

	@Getter(AccessLevel.PACKAGE)
	@VisibleForTesting
	private final BPartnerId vendorId;

	private boolean readonly;

	@Getter
	private final Type availabilityType;

	@Getter(AccessLevel.PACKAGE)
	@VisibleForTesting
	private final String availabilityDistinguisher;

	private transient DocumentId _documentId; // lazy

	/** Group */
	private PurchaseRowId(
			@NonNull final PurchaseDemandId purchaseDemandId,
			final DocumentId documentId)
	{
		this.type = PurchaseRowType.GROUP;
		this.purchaseDemandId = purchaseDemandId;
		this._documentId = documentId;

		this.vendorId = null;
		this.availabilityType = null;
		this.availabilityDistinguisher = null;
	}

	/** Line */
	private PurchaseRowId(
			@NonNull final PurchaseDemandId purchaseDemandId,
			@NonNull final BPartnerId vendorId,
			final boolean readonly,
			final DocumentId documentId)
	{
		this.type = PurchaseRowType.LINE;
		this.purchaseDemandId = purchaseDemandId;
		this._documentId = documentId;

		this.vendorId = vendorId;
		this.readonly = readonly;

		this.availabilityType = null;
		this.availabilityDistinguisher = null;

	}

	/** Availability Detail */
	private PurchaseRowId(
			@NonNull final PurchaseDemandId purchaseDemandId,
			@NonNull final BPartnerId vendorId,
			@NonNull final Type availabilityType,
			@NonNull final String availabilityDistinguisher,
			final DocumentId documentId)
	{
		Check.assumeNotEmpty(availabilityDistinguisher, "availabilityDistinguisher is not empty");
		Check.errorIf(availabilityDistinguisher.contains(PARTS_SEPARATOR),
				"The given availabilityDistinguisher string may not contain the PARTS_SEPARATOR string; PARTS_SEPARATOR={};availabilityDistinguisher={}",
				PARTS_SEPARATOR, availabilityDistinguisher);

		this.type = PurchaseRowType.AVAILABILITY_DETAIL;
		this.purchaseDemandId = purchaseDemandId;
		this._documentId = documentId;

		this.vendorId = vendorId;
		this.availabilityType = availabilityType;
		this.availabilityDistinguisher = availabilityDistinguisher;
	}

	public DocumentId toDocumentId()
	{
		DocumentId documentId = _documentId;
		if (documentId == null)
		{
			documentId = _documentId = createDocumentId();
		}
		return documentId;
	}

	/**
	 * NOTE: keep in sync with {@link #fromJson(String, DocumentId)}
	 */
	private DocumentId createDocumentId()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(type.getCode());

		sb.append(PARTS_SEPARATOR).append(purchaseDemandId.getId());

		if (type == PurchaseRowType.GROUP)
		{
		}
		else if (type == PurchaseRowType.LINE)
		{
			sb.append(PARTS_SEPARATOR).append(vendorId.getRepoId());
			sb.append(PARTS_SEPARATOR).append(encodeReadonly(readonly));
		}
		else if (type == PurchaseRowType.AVAILABILITY_DETAIL)
		{
			sb.append(PARTS_SEPARATOR).append(vendorId.getRepoId());
			sb.append(PARTS_SEPARATOR).append(availabilityType.toString());
			sb.append(PARTS_SEPARATOR).append(availabilityDistinguisher);
		}
		else
		{
			throw new AdempiereException("Type " + type + " is not supported");
		}

		return DocumentId.ofString(sb.toString());
	}

	/**
	 * NOTE: keep in sync with {@link #createDocumentId()}
	 */
	private static final PurchaseRowId fromJson(
			@NonNull final String json,
			@Nullable final DocumentId documentId)
	{
		final List<String> parts = PARTS_SPLITTER.splitToList(json);
		final int partsCount = parts.size();
		if (partsCount < 2) // we expect at least the type and purchaseDemandId to be encoded in the json string
		{
			throw new AdempiereException("Invalid format: " + json);
		}

		try
		{
			final PurchaseRowType type = PurchaseRowType.ofCode(parts.get(0));

			final PurchaseDemandId purchaseDemandId = PurchaseDemandId.ofId(Integer.parseInt(parts.get(1)));

			if (type == PurchaseRowType.GROUP)
			{
				return new PurchaseRowId(purchaseDemandId, documentId);
			}

			final BPartnerId vendorId = BPartnerId.ofRepoId(Integer.parseInt(parts.get(2)));
			if (type == PurchaseRowType.LINE)
			{
				final boolean readonly = decodeReadonly(parts.get(3));
				return new PurchaseRowId(purchaseDemandId, vendorId, readonly, documentId);
			}

			final Type availabilityType = Type.valueOf(parts.get(3));
			final String availabilityDistinguisher = parts.get(4);
			if (type == PurchaseRowType.AVAILABILITY_DETAIL)
			{
				return new PurchaseRowId(purchaseDemandId, vendorId, availabilityType, availabilityDistinguisher, documentId);
			}

			//
			throw new AdempiereException("Type " + type + " is not supported");
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Cannot convert '" + json + "' to " + PurchaseRowId.class, ex);
		}
	}

	private static final boolean decodeReadonly(final String readonlyStr)
	{
		return "ro".equals(readonlyStr);
	}

	private static final String encodeReadonly(final boolean readonly)
	{
		return readonly ? "ro" : "rw";
	}

	public void assertRowType(@NonNull final PurchaseRowType expectedRowType)
	{
		final PurchaseRowType rowType = getType();
		if (rowType != expectedRowType)
		{
			throw new AdempiereException("Expected " + expectedRowType + " but it was " + rowType + ": " + this);
		}
	}

	public PurchaseRowId toGroupRowId()
	{
		if (isGroupRowId())
		{
			return this;
		}
		else
		{
			return groupId(purchaseDemandId);
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
			final boolean readonly = false;
			return lineId(purchaseDemandId, vendorId, readonly);
		}
	}

	public boolean isGroupRowId()
	{
		return type == PurchaseRowType.GROUP;
	}

	public boolean isLineRowId()
	{
		return type == PurchaseRowType.LINE;
	}

	public boolean isAvailabilityRowId()
	{
		return type == PurchaseRowType.AVAILABILITY_DETAIL;
	}

	public boolean isAvailableOnVendor()
	{
		assertRowType(PurchaseRowType.AVAILABILITY_DETAIL);
		return getAvailabilityType().equals(Type.AVAILABLE);
	}
}
