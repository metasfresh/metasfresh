package de.metas.ui.web.order.sales.purchasePlanning.view;

import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.bpartner.BPartnerId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.apache.commons.lang3.RandomStringUtils;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;

import de.metas.purchasecandidate.PurchaseDemandId;
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
	public static PurchaseRowId groupId(final PurchaseDemandId purchaseDemandId)
	{
		return PurchaseRowId.builder()
				.purchaseDemandId(purchaseDemandId)
				.vendorId(null)
				.build();
	}

	public static PurchaseRowId lineId(
			final PurchaseDemandId purchaseDemandId,
			final BPartnerId vendorId,
			final int processedPurchaseCandidateId)
	{
		return PurchaseRowId.builder()
				.purchaseDemandId(purchaseDemandId)
				.vendorId(vendorId)
				.processedPurchaseCandidateId(processedPurchaseCandidateId)
				.build();
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

		return builder()
				.purchaseDemandId(purchaseDemandId)
				.vendorId(vendorId)
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
		if (partsCount <= 0 || partsCount > 6)
		{
			throw new AdempiereException("Invalid format: " + json);
		}

		try
		{
			final String purchaseDemandId_tableName = parts.get(0);
			final int purchaseDemandId_recordId = Integer.parseInt(parts.get(1));
			final PurchaseDemandId purchaseDemandId = PurchaseDemandId.ofTableAndRecordId(purchaseDemandId_tableName, purchaseDemandId_recordId);

			final BPartnerId vendorId = partsCount >= 3 ? BPartnerId.ofRepoIdOrNull(Integer.parseInt(parts.get(2))) : null;
			final int processedPurchaseCandidateId = partsCount >= 4 ? Integer.parseInt(parts.get(3)) : -1;
			final Type availabilityType = partsCount >= 5 ? Type.valueOf(parts.get(4)) : null;
			final String availabilityDistinguisher = partsCount >= 6 ? parts.get(5) : null;

			return builder()
					.purchaseDemandId(purchaseDemandId)
					.vendorId(vendorId)
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
	private final PurchaseDemandId purchaseDemandId;

	@Getter(AccessLevel.PACKAGE)
	@VisibleForTesting
	private final BPartnerId vendorId;

	@Getter
	private final Type availabilityType;

	@Getter(AccessLevel.PACKAGE)
	@VisibleForTesting
	private final String availabilityDistinguisher;

	private transient DocumentId _documentId; // lazy

	private final int processedPurchaseCandidateId;

	@Builder
	private PurchaseRowId(
			@NonNull final PurchaseDemandId purchaseDemandId,
			final BPartnerId vendorId,
			final int processedPurchaseCandidateId,
			final Type availabilityType,
			final String availabilityDistinguisher,
			final DocumentId documentId)
	{
		Check.errorIf(availabilityType == null ^ Check.isEmpty(availabilityDistinguisher, true),
				"The given availabilityType and availabilityDistinguisher need to be both either null/empty or both not-null/not-empty; availabilityType={}; availabilityDistinguisher={}",
				availabilityType, availabilityDistinguisher);

		Check.errorIf(availabilityDistinguisher != null && availabilityDistinguisher.contains(PARTS_SEPARATOR),
				"The given availabilityDistinguisher string may not contain the PARTS_SEPARATOR string; PARTS_SEPARATOR={};availabilityDistinguisher={}",
				PARTS_SEPARATOR, availabilityDistinguisher);

		this.purchaseDemandId = purchaseDemandId;
		this.vendorId = vendorId;
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

			sb.append(purchaseDemandId.getTableName());
			sb.append(PARTS_SEPARATOR);
			sb.append(purchaseDemandId.getRecordId());

			if (vendorId != null || processedPurchaseCandidateId > 0)
			{
				sb.append(PARTS_SEPARATOR);
				sb.append(BPartnerId.toRepoIdOr(vendorId, 0));

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
			return builder().purchaseDemandId(purchaseDemandId).build();
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
			return builder()
					.purchaseDemandId(purchaseDemandId)
					.vendorId(vendorId)
					.processedPurchaseCandidateId(processedPurchaseCandidateId)
					.build();
		}
	}

	public boolean isGroupRowId()
	{
		return (vendorId == null && processedPurchaseCandidateId <= 0);
	}

	public boolean isLineRowId()
	{
		return (vendorId != null || processedPurchaseCandidateId > 0) && availabilityType == null;
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
