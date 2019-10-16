package de.metas.material.dispo.commons.repository.atp;

import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.util.HashMap;

import org.compiere.util.Util.ArrayKey;

import de.metas.material.dispo.commons.repository.DateAndSeqNo;
import de.metas.material.event.commons.AttributesKey;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * metasfresh-material-dispo-commons
 * %%
 * Copyright (C) 2019 metas GmbH
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
final class AvailableToPromiseResultGroupBuilder
{
	@Getter
	private final BPartnerClassifier bpartner;

	@Getter
	private final WarehouseClassifier warehouse;

	@Getter
	private final int productId;

	@Getter
	private final AttributesKey storageAttributesKey;

	@Getter
	private BigDecimal qty;

	/**
	 * Date of the latest underlying data record, less or equal than the query's date; never null, unless this result is empty.
	 * Analog to , required because multiple records can have the same date.
	 */
	final HashMap<ArrayKey, DateAndSeqNo> includedRequestKeys = new HashMap<>();

	@Builder
	private AvailableToPromiseResultGroupBuilder(
			@NonNull final BPartnerClassifier bpartner,
			@NonNull final WarehouseClassifier warehouse,
			final int productId,
			@NonNull final AttributesKey storageAttributesKey)
	{
		this.bpartner = bpartner;
		this.warehouse = warehouse;
		this.productId = Check.assumeGreaterThanZero(productId, "productId");
		this.storageAttributesKey = storageAttributesKey;

		this.qty = ZERO;
	}

	public AvailableToPromiseResultGroup build()
	{
		return AvailableToPromiseResultGroup.builder()
				.bpartner(bpartner)
				.warehouse(warehouse)
				.productId(productId)
				.storageAttributesKey(storageAttributesKey)
				.qty(qty)
				.build();
	}

	/**
	 * IMPORTANT: supposed to be used only from {@link AvailableToPromiseRepository},
	 * because it needs to be sure that the callers know what they do;
	 * in particular the requests be applied to applied in a particular ordering, with the no-bpartner-requests last.
	 * <p>
	 * Returns true if the given {@code request}
	 * <li>has no bPartnerID (i.e. are applicable to) all partners,
	 * <li>and has the same product, attributes and warehouse
	 * <li>and has a date not after this result group's date.
	 *
	 * That's because those "bpartner-unspecific" requests' quantities are already in the respective later specific requests.
	 * <p>
	 * Also see the service in materialdispo-services that is responsible for updating stock-candidates
	 */
	boolean isAlreadyIncluded(@NonNull final AddToResultGroupRequest request)
	{
		// assume it's matching
		// if (!isMatchting(request))
		// {
		// return false; // only matching requests were ever included
		// }

		if (request.getBpartner().isSpecificBPartner())
		{
			return false;
		}

		final ArrayKey key = request.computeKey();
		if (!includedRequestKeys.containsKey(key))
		{
			return false;
		}

		final DateAndSeqNo dateAndSeq = includedRequestKeys.get(key);

		// if our bpartnerless request is "earlier" than the latest request (with same key) that we already added, then the quantity of the bpartnerless request is contained within that other request which we already added
		return request.getDateAndSeqNo().isBefore(dateAndSeq);
	}

	public void addQty(@NonNull final AddToResultGroupRequest request)
	{
		qty = qty.add(request.getQty());

		final ArrayKey computeKey = request.computeKey();

		final DateAndSeqNo oldTimeAndSeqNo = includedRequestKeys.get(computeKey);
		final DateAndSeqNo latest = request.getDateAndSeqNo().max(oldTimeAndSeqNo);

		includedRequestKeys.put(computeKey, latest);
	}

	boolean isZeroQty()
	{
		return getQty().signum() == 0;
	}
}
