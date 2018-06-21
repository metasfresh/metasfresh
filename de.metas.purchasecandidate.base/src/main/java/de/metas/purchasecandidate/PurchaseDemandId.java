package de.metas.purchasecandidate;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.Getter;
import lombok.Value;

/*
 * #%L
 * de.metas.purchasecandidate.base
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

/**
 * Just like {@link PurchaseDemand} itself, this ID is not stored;
 * it's just there to support the grouping of {@link PurchaseCandidatesGroup}s that belong to the same {@link PurchaseDemand}.
 */
@Value
public class PurchaseDemandId
{
	public static PurchaseDemandId createNew()
	{
		return new PurchaseDemandId(nextAggregateId.incrementAndGet());
	}

	public static PurchaseDemandId ofId(final int id)
	{
		return new PurchaseDemandId(id);
	}

	private static final AtomicInteger nextAggregateId = new AtomicInteger(1);

	@Getter
	private final int id;

	private PurchaseDemandId(final int id)
	{
		this.id = id;
	}
}
