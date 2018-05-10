package de.metas.purchasecandidate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

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

@Data
class PurchaseCandidateState
{
	private final boolean locked;

	private boolean processed;
	@Setter(AccessLevel.NONE)
	private boolean processedInitial;

	@Builder
	private PurchaseCandidateState(
			final boolean processed,
			final boolean locked)
	{
		this.processed = processed;
		this.processedInitial = processed;
		this.locked = locked;
	}

	private PurchaseCandidateState(final PurchaseCandidateState from)
	{
		this.processed = from.processed;
		this.processedInitial = from.processedInitial;
		this.locked = from.locked;
	}

	public PurchaseCandidateState copy()
	{
		return new PurchaseCandidateState(this);
	}

	public void setProcessed()
	{
		processed = true;
	}

	public boolean hasChanges()
	{
		return processed != processedInitial;
	}

	public void markSaved()
	{
		processedInitial = processed;
	}

}
