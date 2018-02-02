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
public class PurchaseCandidateState
{
	private boolean processed;

	private final boolean locked;

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

	// don't use @lombok.AllArgsConstructor, because we might get the parameter ordering wrong.
	private PurchaseCandidateState(
			final boolean processed,
			final boolean processedInitial,
			final boolean locked)
	{
		this.processed = processed;
		this.processedInitial = processedInitial;
		this.locked = locked;
	}

	public PurchaseCandidateState createCopy()
	{
		return new PurchaseCandidateState(processed, processedInitial, locked);
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
