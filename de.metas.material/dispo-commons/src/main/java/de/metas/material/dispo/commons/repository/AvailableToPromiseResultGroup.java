package de.metas.material.dispo.commons.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.compiere.util.Util;

import de.metas.material.dispo.commons.repository.AvailableToPromiseResult.AddToResultGroupRequest;
import de.metas.material.event.commons.AttributesKey;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * metasfresh-material-dispo-commons
 * %%
 * Copyright (C) 2018 metas GmbH
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

@ToString(exclude = "storageAttributesKeyMatcher" /* because it's just gibberish most of the time */)
@EqualsAndHashCode
@Getter
public final class AvailableToPromiseResultGroup
{
	static final int WAREHOUSE_ID_ANY = -1;

	private final int warehouseId;
	private final int productId;
	private final AttributesKey storageAttributesKey;
	private final Predicate<AttributesKey> storageAttributesKeyMatcher;
	private final int bpartnerId;
	private BigDecimal qty;

	private boolean empty;

	/** Date of the latest underlying data record, less or equal than the query's date; never null, unless this result is empty. */
	private LocalDateTime date;

	private int seqNo;

	@Builder
	public AvailableToPromiseResultGroup(
			final int warehouseId,
			final int productId,
			@NonNull final AttributesKey storageAttributesKey,
			@Nullable final Predicate<AttributesKey> storageAttributesKeyMatcher,
			final int bpartnerId,
			@Nullable final BigDecimal qty,
			@Nullable final LocalDateTime date,
			final int seqNo,
			@Nullable final Boolean empty)
	{
		this.warehouseId = warehouseId > 0 ? warehouseId : WAREHOUSE_ID_ANY;
		this.productId = Check.assumeGreaterThanZero(productId, "productId");
		this.storageAttributesKey = storageAttributesKey;
		this.storageAttributesKeyMatcher = storageAttributesKeyMatcher != null
				? storageAttributesKeyMatcher
				: AvailableToPromiseResult.createStorageAttributesKeyMatcher(storageAttributesKey);

		this.qty = qty == null ? BigDecimal.ZERO : qty;

		this.date = date;
		this.seqNo = seqNo;
		this.empty = Util.coalesce(empty, true);

		if (bpartnerId == AvailableToPromiseQuery.BPARTNER_ID_ANY
				|| bpartnerId == AvailableToPromiseQuery.BPARTNER_ID_NONE
				|| bpartnerId > 0)
		{
			this.bpartnerId = bpartnerId;
		}
		else
		{
			throw new AdempiereException("Invalid bpartnerId: " + bpartnerId);
		}
	}

	public boolean isMatchting(@NonNull final AddToResultGroupRequest request)
	{
		if (productId != request.getProductId())
		{
			return false;
		}

		if (!isWarehouseMatching(request.getWarehouseId()))
		{
			return false;
		}

		if (!isBPartnerMatching(request.getBpartnerId()))
		{
			return false;
		}

		if (!isStorageAttributesKeyMatching(request.getStorageAttributesKey()))
		{
			return false;
		}

		return true;
	}

	/**
	 * IMPORTANT: supposed to be used only from {@link AvailableToPromiseRepository},
	 * because it needs to be sure that the callers know what they do;
	 * in particular the requests be applied to applied in a particular ordering, with the no-bpartner-requests last.
	 * <p>
	 * Returns true if the given {@code request}
	 * <li>has no bPartnerID (i.e. are applicable to) all partners,
	 * <li>has the same product, warehouse etc.
	 * <li>has a date not after this result group's date.
	 *
	 * That's because those "bpartner-unspecific" requests' quantities are already in the respective later specific requests.
	 * <p>
	 * Also see the service in materialdispo-services that is responsible for updating stock-candidates
	 */
	public boolean isAlreadyIncluded(@NonNull final AddToResultGroupRequest request)
	{
		if (!isMatchting(request))
		{
			return false;
		}

		final boolean requestHasBPartnerId = request.getBpartnerId() > 0;
		if (requestHasBPartnerId)
		{
			return false;
		}

		return isRequestEarlier(request);
	}

	private boolean isRequestHasEqualDateAndLaterSeqNo(@NonNull final AddToResultGroupRequest request)
	{
		return date != null && date.equals(request.getDate()) && seqNo < request.getSeqNo();
	}

	private boolean isRequestHasLaterDate(@NonNull final AddToResultGroupRequest request)
	{
		return date == null || date.isBefore(request.getDate());
	}

	private boolean isRequestEarlier(@NonNull final AddToResultGroupRequest request)
	{
		return isRequestHasEarlierDate(request) || isRequestHasEqualDateAndEarlierSeqNo(request);
	}

	private boolean isRequestHasEarlierDate(@NonNull final AddToResultGroupRequest request)
	{
		if (empty)
		{
			return false; // empty == true means that nothing was yet added to this instance, so the request does *not* have an earlier date
		}
		return date != null && request.getDate().isBefore(date);
	}

	private boolean isRequestHasEqualDateAndEarlierSeqNo(@NonNull final AddToResultGroupRequest request)
	{
		if (empty)
		{
			return false; // empty == true means that nothing was yet added to this instance, so the request does *not* have an earlier seqno
		}
		return date != null && date.equals(request.getDate()) && request.getSeqNo() < seqNo;
	}

	public void addQty(@NonNull final AddToResultGroupRequest request)
	{
		qty = qty.add(request.getQty());

		if (isRequestHasLaterDate(request))
		{
			date = request.getDate();
			seqNo = request.getSeqNo();
		}
		else if (isRequestHasEqualDateAndLaterSeqNo(request))
		{
			seqNo = request.getSeqNo();
		}
		empty = false;
	}

	private boolean isWarehouseMatching(final int warehouseIdToMatch)
	{
		return warehouseId == WAREHOUSE_ID_ANY
				|| warehouseId == warehouseIdToMatch;
	}

	private boolean isBPartnerMatching(final int bpartnerIdToMatch)
	{
		return AvailableToPromiseQuery.isBPartnerMatching(bpartnerId, bpartnerIdToMatch);
	}

	private boolean isStorageAttributesKeyMatching(final AttributesKey storageAttributesKeyToMatch)
	{
		return storageAttributesKeyMatcher.test(storageAttributesKeyToMatch);
	}
}
