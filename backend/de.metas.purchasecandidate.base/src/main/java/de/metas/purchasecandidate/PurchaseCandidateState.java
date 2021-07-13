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

	private boolean prepared;
	@Setter(AccessLevel.NONE)
	private boolean preparedInitial;

	private boolean processed;
	@Setter(AccessLevel.NONE)
	private boolean processedInitial;

	private boolean reqCreated;
	@Setter(AccessLevel.NONE)
	private boolean reqCreatedInitial;

	@Builder
	private PurchaseCandidateState(
			final boolean prepared,
			final boolean processed,
			final boolean locked,
			final boolean reqCreated)
	{
		this.prepared = prepared;
		this.preparedInitial = prepared;

		this.processed = processed;
		this.processedInitial = processed;

		this.reqCreated = reqCreated;
		this.reqCreatedInitial = reqCreated;

		this.locked = locked;
	}

	private PurchaseCandidateState(final PurchaseCandidateState from)
	{
		this.prepared = from.prepared;
		this.preparedInitial = from.preparedInitial;

		this.processed = from.processed;
		this.processedInitial = from.processedInitial;

		this.reqCreated = from.reqCreated;
		this.reqCreatedInitial = from.reqCreatedInitial;

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
		return prepared != preparedInitial
				|| processed != processedInitial
				|| reqCreated != reqCreatedInitial;

	}

	public void markSaved()
	{
		preparedInitial = prepared;
		processedInitial = processed;
		reqCreatedInitial = reqCreated;
	}

}
