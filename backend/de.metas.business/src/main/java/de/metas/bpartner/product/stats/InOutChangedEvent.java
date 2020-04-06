package de.metas.bpartner.product.stats;

import java.time.Instant;
import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.product.stats.InOutChangedEvent.InOutChangedEventBuilder;
import de.metas.lang.SOTrx;
import de.metas.product.ProductId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
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

@Value
@JsonDeserialize(builder = InOutChangedEventBuilder.class)
public class InOutChangedEvent
{
	BPartnerId bpartnerId;
	Instant movementDate;
	SOTrx soTrx;
	boolean reversal;

	Set<ProductId> productIds;

	@Builder
	private InOutChangedEvent(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final Instant movementDate,
			@NonNull final SOTrx soTrx,
			final boolean reversal,
			@NonNull final Set<ProductId> productIds)
	{
		Check.assumeNotEmpty(productIds, "productIds is not empty");

		this.bpartnerId = bpartnerId;
		this.movementDate = movementDate;
		this.soTrx = soTrx;
		this.reversal = reversal;
		this.productIds = productIds;
	}

	@JsonPOJOBuilder(withPrefix = "")
	public static class InOutChangedEventBuilder
	{
	}

}
